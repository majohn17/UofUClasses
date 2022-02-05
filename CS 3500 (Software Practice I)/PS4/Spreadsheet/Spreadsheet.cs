using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml;
using SpreadsheetUtilities;

namespace SS
{
    /// <summary>
    /// A spreadsheet consists of an infinite number of named cells.
    /// 
    /// A string is a cell name if and only if it consists of one or more letters,
    /// followed by one or more digits AND it satisfies the predicate IsValid.
    /// For example, "A15", "a15", "XY032", and "BC7" are cell names so long as they
    /// satisfy IsValid.  On the other hand, "Z", "X_", and "hello" are not cell names,
    /// regardless of IsValid.
    /// 
    /// Any valid incoming cell name, whether passed as a parameter or embedded in a formula,
    /// must be normalized with the Normalize method before it is used by or saved in 
    /// this spreadsheet.  For example, if Normalize is s => s.ToUpper(), then
    /// the Formula "x3+a5" should be converted to "X3+A5" before use.
    /// 
    /// A spreadsheet contains a cell corresponding to every possible cell name.  
    /// In addition to a name, each cell has a contents and a value.  The distinction is
    /// important.
    /// 
    /// The contents of a cell can be (1) a string, (2) a double, or (3) a Formula.  If the
    /// contents is an empty string, we say that the cell is empty.  (By analogy, the contents
    /// of a cell in Excel is what is displayed on the editing line when the cell is selected.)
    /// 
    /// In a new spreadsheet, the contents of every cell is the empty string.
    ///  
    /// The value of a cell can be (1) a string, (2) a double, or (3) a FormulaError.  
    /// (By analogy, the value of an Excel cell is what is displayed in that cell's position
    /// in the grid.)
    /// 
    /// If a cell's contents is a string, its value is that string.
    /// 
    /// If a cell's contents is a double, its value is that double.
    /// 
    /// If a cell's contents is a Formula, its value is either a double or a FormulaError,
    /// as reported by the Evaluate method of the Formula class.  The value of a Formula,
    /// of course, can depend on the values of variables.  The value of a variable is the 
    /// value of the spreadsheet cell it names (if that cell's value is a double) or 
    /// is undefined (otherwise).
    /// 
    /// Spreadsheets are never allowed to contain a combination of Formulas that establish
    /// a circular dependency.  A circular dependency exists when a cell depends on itself.
    /// For example, suppose that A1 contains B1*2, B1 contains C1*2, and C1 contains A1*2.
    /// A1 depends on B1, which depends on C1, which depends on A1.  That's a circular
    /// dependency.
    /// </summary>
    public class Spreadsheet : AbstractSpreadsheet
    {
        /// <summary>
        /// Stores the names of cells in the spreadsheet paired with a cell containing its value
        /// </summary>
        private Dictionary<string, Cell> cells;
        /// <summary>
        /// Stores the dependencies of one cell to another
        /// </summary>
        private DependencyGraph deps;
        /// <summary>
        /// True if this spreadsheet has been modified since it was created or saved                  
        /// (whichever happened most recently); false otherwise.
        /// </summary>
        public override bool Changed { get; protected set; }

        /// <summary>
        /// Constructs a spreadsheet with no the validity check, no normalization, and a version of default.
        /// </summary>
        public Spreadsheet() : this(s => true, s => s, "default")
        {
            cells = new Dictionary<string, Cell>();
            deps = new DependencyGraph();
            Changed = false;
        }

        /// <summary>
        /// Constructs an spreadsheet by recording its variable validity test,
        /// its normalization method, and its version information.  The variable validity
        /// test is used throughout to determine whether a string that consists of one or
        /// more letters followed by one or more digits is a valid cell name.  The variable
        /// equality test should be used thoughout to determine whether two variables are
        /// equal.
        /// </summary>
        public Spreadsheet(Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        {
            if (isValid is null || normalize is null)
                throw new ArgumentNullException();
            cells = new Dictionary<string, Cell>();
            deps = new DependencyGraph();
            Changed = false;
        }

        /// <summary>
        /// Constructs an spreadsheet by recording its variable validity test,
        /// its normalization method, and its version information.  The variable validity
        /// test is used throughout to determine whether a string that consists of one or
        /// more letters followed by one or more digits is a valid cell name.  The variable
        /// equality test should be used thoughout to determine whether two variables are
        /// equal.
        /// </summary>
        public Spreadsheet(String filePath, Func<string, bool> isValid, Func<string, string> normalize, string version) : this(isValid, normalize, version)
        {
            cells = new Dictionary<string, Cell>();
            deps = new DependencyGraph();
            if (GetSavedVersion(filePath) != version)
                throw new SpreadsheetReadWriteException("Version of saved spreadsheet doesn't match the version parameters provided to constructor.");
            Changed = false;
        }

        /// <summary>
        /// Returns the version information of the spreadsheet saved in the named file.
        /// If there are any problems opening, reading, or closing the file, the method
        /// should throw a SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override string GetSavedVersion(string filename)
        {
            string version = "";
            try
            {
                //Temp variables used to determine if were currently still in a cell and heirarchy
                bool startCheck = false;
                bool cellCheck = false;
                bool nameCheck = false;
                bool contentCheck = false;
                string name = "";
                string content = "";
                using (XmlReader reader = XmlReader.Create(filename))
                {
                    while (reader.Read())
                    {
                        if (reader.IsStartElement())
                        {
                            switch (reader.Name)
                            {
                                case "spreadsheet":
                                    startCheck = true;
                                    version = reader["version"];
                                    if (version is null)
                                        throw new SpreadsheetReadWriteException("File doesn't contain version information.");
                                    break;
                                case "cell":
                                    if (startCheck && !cellCheck)
                                        cellCheck = true;
                                    else
                                        throw new SpreadsheetReadWriteException("Invalid XML formatting, spreadsheet heirarchy incorrect.");
                                    break;
                                case "name":
                                    if (startCheck && cellCheck && !nameCheck)
                                    {
                                        nameCheck = true;
                                        reader.Read();
                                        name = reader.Value;
                                    }
                                    else
                                        throw new SpreadsheetReadWriteException("Invalid XML formatting, spreadsheet heirarchy incorrect.");
                                    break;
                                case "contents":
                                    if (startCheck && cellCheck && !contentCheck)
                                    {
                                        contentCheck = true;
                                        reader.Read();
                                        content = reader.Value;
                                    }
                                    else
                                        throw new SpreadsheetReadWriteException("Invalid XML formatting, spreadsheet heirarchy incorrect.");
                                    break;
                                default:
                                    throw new SpreadsheetReadWriteException("File contains an invalid name.");
                            }
                        }
                        else // If it's not a start element, it's probably an end element
                        {
                            if (reader.Name == "cell")
                            {
                                SetContentsOfCell(name, content);
                                cellCheck = false;
                                nameCheck = false;
                                contentCheck = false;
                            }
                        }
                    }
                }
            }
            catch (ArgumentNullException)
            {
                throw new SpreadsheetReadWriteException("Invalid filepath, unable to read. The filePath cannnot be null.");
            }
            catch (FileNotFoundException)
            {
                throw new SpreadsheetReadWriteException("Invalid file, no file exsits as provided filePath.");
            }
            catch (XmlException e)
            {
                throw new SpreadsheetReadWriteException("Invalid file, XML is incorrectly formatted on line " + e.LineNumber + ".");
            }
            catch (FormulaFormatException)
            {
                throw new SpreadsheetReadWriteException("XML contains an invalid formula in the contents of a cell.");
            }
            catch (CircularException)
            {
                throw new SpreadsheetReadWriteException("Creation of spreadsheet resulted in a circular dependency.");
            }
            catch (DirectoryNotFoundException)
            {
                throw new SpreadsheetReadWriteException("Invalid filepath, unable to read. Must provide a valid file path.");
            }
            return version;
        }

        /// <summary>
        /// Writes the contents of this spreadsheet to the named file using an XML format.
        /// The XML elements should be structured as follows:
        /// 
        /// <spreadsheet version="version information goes here">
        /// 
        /// <cell>
        /// <name>
        /// cell name goes here
        /// </name>
        /// <contents>
        /// cell contents goes here
        /// </contents>    
        /// </cell>
        /// 
        /// </spreadsheet>
        /// 
        /// There should be one cell element for each non-empty cell in the spreadsheet.  
        /// If the cell contains a string, it should be written as the contents.  
        /// If the cell contains a double d, d.ToString() should be written as the contents.  
        /// If the cell contains a Formula f, f.ToString() with "=" prepended should be written as the contents.
        /// 
        /// If there are any problems opening, writing, or closing the file, the method should throw a
        /// SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override void Save(string filename)
        {
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "  ";

            try
            {
                using (XmlWriter writer = XmlWriter.Create(filename, settings))
                {
                    writer.WriteStartDocument();
                    writer.WriteStartElement("spreadsheet");
                    writer.WriteAttributeString("version", Version);

                    foreach (string k in cells.Keys)
                    {
                        writer.WriteStartElement("cell");
                        writer.WriteElementString("name", k);
                        writer.WriteElementString("contents", GetContentString(cells[k].Contents));
                        writer.WriteEndElement();
                    }

                    writer.WriteEndElement();
                    writer.WriteEndDocument();
                }
                Changed = false;
            }
            catch (ArgumentNullException)
            {
                throw new SpreadsheetReadWriteException("Invalid filename, unable to write. The name of the file cannnot be null.");
            }
            catch (DirectoryNotFoundException)
            {
                throw new SpreadsheetReadWriteException("Invalid filepath, unable to write. The spreadsheet must be saved to a valid file path.");
            }
        }

        /// <summary>
        /// Used to detmine the content type to get the string for the XML saving
        /// </summary>
        private string GetContentString(Object o)
        {
            if (o is double)
                return o.ToString();
            else if (o is string)
                return (string)o;
            else
                return "=" + o.ToString();
        }

        /// <summary>
        /// Enumerates the names of all the non-empty cells in the spreadsheet.
        /// </summary>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells() => cells.Keys;

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        /// </summary>
        public override object GetCellContents(string name)
        {
            if (!IsValidName(Normalize(name)))
                throw new InvalidNameException();
            string normal = Normalize(name);
            if (!cells.ContainsKey(normal))
                return "";
            return cells[normal].Contents;
        }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the value (as opposed to the contents) of the named cell.  The return
        /// value should be either a string, a double, or a SpreadsheetUtilities.FormulaError.
        /// </summary>
        public override object GetCellValue(string name)
        {
            if (!IsValidName(name))
                throw new InvalidNameException();
            else
            {
                string normal = Normalize(name);
                if (!cells.ContainsKey(normal))
                    return "";
                if (cells[normal].Contents is double)
                    return (double)cells[normal].CellValue;
                else if (cells[normal].Contents is string)
                    return (string)cells[normal].CellValue;
                else
                {
                    if (cells[normal].CellValue is double)
                        return (double)cells[normal].CellValue;
                    else
                        return (FormulaError)cells[normal].CellValue;
                }  
            }
        }

        /// <summary>
        /// If content is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if content parses as a double, the contents of the named
        /// cell becomes that double.
        /// 
        /// Otherwise, if content begins with the character '=', an attempt is made
        /// to parse the remainder of content into a Formula f using the Formula
        /// constructor.  There are then three possibilities:
        /// 
        ///   (1) If the remainder of content cannot be parsed into a Formula, a 
        ///       SpreadsheetUtilities.FormulaFormatException is thrown.
        ///       
        ///   (2) Otherwise, if changing the contents of the named cell to be f
        ///       would cause a circular dependency, a CircularException is thrown,
        ///       and no change is made to the spreadsheet.
        ///       
        ///   (3) Otherwise, the contents of the named cell becomes f.
        /// 
        /// Otherwise, the contents of the named cell becomes content.
        /// 
        /// If an exception is not thrown, the method returns a list consisting of
        /// name plus the names of all other cells whose value depends, directly
        /// or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<String> SetContentsOfCell(string name, string content)
        {
            if (content is null)
                throw new ArgumentNullException();
            else if (!IsValidName(name))
                throw new InvalidNameException();
            else
            {
                string normal = Normalize(name);
                if (double.TryParse(content, out double d))
                {
                    List<string> temp = new List<string>(SetCellContents(normal, d));
                    RecalculateCells(temp);
                    return temp;
                }
                else if (content.StartsWith("="))
                {
                    List<string> temp = new List<string>(SetCellContents(normal, new Formula(content.Substring(1), Normalize, IsValid)));
                    RecalculateCells(temp);
                    return temp;
                }
                else
                {
                    List<string> temp = new List<string>(SetCellContents(normal, content));
                    RecalculateCells(temp);
                    return temp;
                }
            }
        }

        /// <summary>
        /// Recalculates the values of the cells based on list returned from the set contents methods.
        /// </summary>
        /// <param name="names"></param>
        private void RecalculateCells(List<string> names)
        {
            Func<string, double> CellLookup = s => (!cells.ContainsKey(s) || cells[s].CellValue is string || cells[s].CellValue is FormulaError) ?
                throw new ArgumentException() : (double)cells[s].CellValue;
            string first = names[0];
            if (cells.ContainsKey(first))
            {
                if (cells[first].Contents is string || cells[first].Contents is double)
                    cells[first].CellValue = cells[first].Contents;
                else
                {
                    Formula temp = (Formula)cells[first].Contents;
                    cells[first].CellValue = temp.Evaluate(CellLookup);
                }
            }
            for (int i = 1; i < names.Count; i++)
            {
                Formula temp = (Formula)cells[names[i]].Contents;
                cells[names[i]].CellValue = temp.Evaluate(CellLookup);
            }
        }

        /// <summary>
        /// The contents of the named cell becomes number.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<String> SetCellContents(string name, double number)
        {
            if (cells.ContainsKey(name))
            {
                cells[name].Contents = number;
                Changed = true;
            }
            else
            {
                cells.Add(name, new Cell(number));
                Changed = true;
            }
            deps.ReplaceDependees(name, new HashSet<string>());
            return new List<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// The contents of the named cell becomes text.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<String> SetCellContents(string name, string text)
        {
            if (text == "")
            {
                if (cells.ContainsKey(name))
                {
                    cells.Remove(name);
                    Changed = true;
                }
            }
            else
            {
                if (cells.ContainsKey(name))
                {
                    cells[name].Contents = text;
                    Changed = true;
                }
                else
                {
                    cells.Add(name, new Cell(text));
                    Changed = true;
                }
            }
            deps.ReplaceDependees(name, new HashSet<string>());
            return new List<string>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// If changing the contents of the named cell to be the formula would cause a 
        /// circular dependency, throws a CircularException, and no change is made to the spreadsheet.
        /// 
        /// Otherwise, the contents of the named cell becomes formula. The method returns a
        /// list consisting of name plus the names of all other cells whose value depends,
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<String> SetCellContents(string name, Formula formula)
        {
            // Stores previous values in case of circular exception
            HashSet<string> prevDeps = new HashSet<string>(deps.GetDependees(name));
            object prev = new object();
            bool newCell = false;
            bool prevChanged = Changed;

            if (cells.ContainsKey(name))
            {
                prev = cells[name].Contents;
                cells[name].Contents = formula;
                Changed = true;
            }
            else
            {
                newCell = true;
                cells.Add(name, new Cell(formula));
                Changed = true;
            }
            deps.ReplaceDependees(name, formula.GetVariables());
            try
            {
                return new List<string>(GetCellsToRecalculate(name));
            }
            catch (CircularException)
            {
                // Undo cell content and dependency changes
                if (newCell == true)
                    cells.Remove(name);
                else
                    cells[name].Contents = prev;
                deps.ReplaceDependees(name, prevDeps);
                Changed = prevChanged;
                // Throw Exception after undoing state of spreadsheet
                throw new CircularException();
            }
        }

        /// <summary>
        /// Returns an enumeration, without duplicates, of the names of all cells whose
        /// values depend directly on the value of the named cell.  In other words, returns
        /// an enumeration, without duplicates, of the names of all cells that contain
        /// formulas containing name.
        /// 
        /// For example, suppose that
        /// A1 contains 3
        /// B1 contains the formula A1 * A1
        /// C1 contains the formula B1 + A1
        /// D1 contains the formula B1 - C1
        /// The direct dependents of A1 are B1 and C1
        /// </summary>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            return deps.GetDependents(name);
        }

        /// <summary>
        /// Determines if a cell name is valid. Cell names are valid if they aren't null and contain
        /// 1 or more letters followed by 1 or more digits and they meet the specified validity condition.
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        private bool IsValidName(string name)
        {
            if (!(name is null))
            {
                if (Regex.IsMatch(name, "^[a-zA-Z]+\\d+$") && IsValid(name))
                    return Regex.IsMatch(Normalize(name), "^[a-zA-Z]+\\d+$") && IsValid(Normalize(name));
            }
            return false;
        }

        /// <summary>
        /// Represents a cell of the overall spreadsheet. 
        /// </summary>
        private class Cell
        {
            /// <summary>
            /// The contents of the cell.
            /// </summary>
            private object contents;
            /// <summary>
            /// The value of the cell.
            /// </summary>
            private object cellValue;

            /// <summary>
            /// Creates a cell with a string as the contents
            /// </summary>
            /// <param name="content"></param>
            public Cell(string content) => contents = content;

            /// <summary>
            /// Creates a cell with a double as the contents
            /// </summary>
            /// <param name="content"></param>
            public Cell(double content) => contents = content;

            /// <summary>
            /// Creates a cell with a Formula object as the contents
            /// </summary>
            /// <param name="content"></param>
            public Cell(Formula content) => contents = content;

            public object Contents { get => contents; set => contents = value; }
            public object CellValue { get => cellValue; set => cellValue = value; }
        }
    }
}
