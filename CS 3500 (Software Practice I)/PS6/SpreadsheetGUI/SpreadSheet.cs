// Matthew Johnsen

using Microsoft.VisualBasic;
using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace SS
{
    /// <summary>
    /// Example of using a SpreadsheetPanel object
    /// </summary>
    public partial class SpreadSheet : Form
    {
        //The Model of the Spreadsheet GUI
        private Spreadsheet data;
        //The File Path if it's been saved before
        private string filePath;
        
        /// <summary>
        /// Creates a new SpreadsheetGUI
        /// </summary>
        public SpreadSheet()
        {
            InitializeComponent();
            data = new Spreadsheet(s => true, s => s, "ps6");
            filePath = "";
            spreadsheetPanel1.SelectionChanged += OnSelectionChanged;
        }

        /// <summary>
        /// Sets the contentsBox to the contents of the cell and jumps to the box
        /// </summary>
        private void OnSelectionChanged(SpreadsheetPanel ss)
        {
            ss.GetSelection(out int col, out int row);
            ss.GetValue(col, row, out string value);
            if (value != "")
            {
                object contents = data.GetCellContents(ColToName());
                contentsBox.Text = contents is Formula ? "=" + contents.ToString() : contents.ToString();
            }
            else
                contentsBox.Text = "";
            contentsBox.Focus();
        }

        //Deals with multiple keydown methods.
        private void contentsBox_KeyDown(object sender, KeyEventArgs e)
        {
            contentsBox.KeyDown += ShiftFocus;
            contentsBox.KeyDown += SetContents;
        }

        /// <summary>
        /// Sets the value of all the cells effected by a change
        /// </summary>
        private void SetContents(object sender, KeyEventArgs e)
        {
            if (e.KeyCode.Equals(Keys.Return))
            {
                e.SuppressKeyPress = true;
                UpdateCellValues(false);
            }
        }

        /// <summary>
        /// Updates the values of the cells, if openCheck is true, it's updating values in the case of a file being opened
        /// </summary>
        private void UpdateCellValues(bool openCheck)
        {
            try
            {
                if (!openCheck)
                {
                    List<string> cells = new List<string>(data.SetContentsOfCell(ColToName(), contentsBox.Text));
                    foreach (string s in cells)
                    {
                        spreadsheetPanel1.SetValue(NameToCol(s), Int32.Parse(s.Substring(1)) - 1, GetValue(data.GetCellValue(s)));
                    }
                }
                else
                {
                    List<string> cells = new List<string>(data.GetNamesOfAllNonemptyCells());
                    foreach (string s in cells)
                    {
                        spreadsheetPanel1.SetValue(NameToCol(s), Int32.Parse(s.Substring(1)) - 1, GetValue(data.GetCellValue(s)));
                    }
                }
            }
            catch (CircularException)
            {
                spreadsheetPanel1.GetSelection(out int col, out int row);
                spreadsheetPanel1.SetValue(col, row, "Circular Error");
            }
            catch (FormulaFormatException)
            {
                spreadsheetPanel1.GetSelection(out int col, out int row);
                spreadsheetPanel1.SetValue(col, row, "Bad Formula");
            }
        }

        /// <summary>
        /// Focus goes to Spreadsheet Panel if tab is pressed
        /// </summary>
        private void ShiftFocus(object sender, KeyEventArgs e)
        {
            if (e.KeyCode.Equals(Keys.Tab))
            {
                e.SuppressKeyPress = true;
                spreadsheetPanel1.Focus();
            }
        }

        /// <summary>
        /// Gives a message with a specified cell if a cell contains given value
        /// </summary>
        private void Find(object sender, KeyEventArgs e)
        {
            
            if (e.Control == true && e.KeyCode == Keys.F)
            {
                spreadsheetPanel1.Focus();
                if (e.KeyCode == Keys.F)
                {
                    string answer = Interaction.InputBox("Enter the string or double value you want to find (empty strings are invalid).", "Find", "*Value*", -1, -1);
                    if (answer == "")
                    {
                        MessageBox.Show("Cannot Provide Empty String", "Find");
                    }
                    else
                    {
                        List<string> cells = new List<string>(data.GetNamesOfAllNonemptyCells());
                        bool found = false;
                        string result = answer + " was found in cell(s): {";
                        foreach (string s in cells)
                        {
                            int col = NameToCol(s);
                            int row = Int32.Parse(s.Substring(1)) - 1;
                            spreadsheetPanel1.GetValue(col, row, out string value);
                            if (answer == value)
                            {
                                result += s + ", ";
                                found = true;
                            }
                        }
                        if (found == true)
                        {
                            result = result.Substring(0, result.Length - 2) + "}";
                            MessageBox.Show(result, "Find");
                        }
                        else
                        {
                            MessageBox.Show(answer + " was not found in any cell.", "Find");
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Returns the string representation of the value
        /// </summary>
        private string GetValue(object o)
        {
            if (o is FormulaError e)
                return e.Reason;
            return o.ToString();
        }

        /// <summary>
        /// Converts cell column and row to cell name
        /// </summary>
        private string ColToName()
        {
            spreadsheetPanel1.GetSelection(out int col, out int row);
            string result = "";
            Char c = (Char)(65 + col);
            result = "" + c + (row + 1);
            return result;
        }

        /// <summary>
        /// Converts cell name to column and row
        /// </summary>
        private int NameToCol(string s)
        {
            char c = s[0];
            int col = c - 65;
            return col;
        }

        // Deals with the New menu
        private void newToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Tell the application context to run the form on the same
            // thread as the other forms.
            SSApplicationContext.getAppContext().RunForm(new SpreadSheet());
        }

        // Deals with the Close menu
        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (data.Changed == true)
            {
                if (UnsavedData())
                    Close();
            }
            else
                Close();
        }

        // Deals with form closing
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (data.Changed == true)
            {
                if (!UnsavedData())
                    e.Cancel = true;
            }
        }

        // Deals with the Open menu
        private void OpenToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Spreadsheet temp = data;
            try
            {
                OpenFileDialog ofd = new OpenFileDialog();
                ofd.Filter = "Spredsheet (*.sprd)|*.sprd|All Files (*.*)|*.*";
                ofd.FilterIndex = 1;
                if (ofd.ShowDialog() == DialogResult.OK)
                {
                    if (UnsavedData())
                    {
                        List<string> cells = new List<string>(data.GetNamesOfAllNonemptyCells());
                        data = new Spreadsheet(ofd.FileName, s => true, s => s, "ps6");
                        foreach (string s in cells)
                        {
                            spreadsheetPanel1.SetValue(NameToCol(s), Int32.Parse(s.Substring(1)) - 1, "");
                        }
                        filePath = ofd.FileName;
                        UpdateCellValues(true);
                        spreadsheetPanel1.SetSelection(0, 0);
                        spreadsheetPanel1.GetValue(0, 0, out string val);
                        contentsBox.Text = val;
                    }
                }
            }
            catch (SpreadsheetReadWriteException error)
            {
                data = temp;
                MessageBox.Show(error.Message, "Error");
            }
        }

        /// <summary>
        /// Gives user the chance to save if data will be lost
        /// </summary>
        private bool UnsavedData()
        {
            DialogResult result = DialogResult.No;
            DialogResult saveResult = DialogResult.OK;
            if (data.Changed == true)
            {
                result = UnsavedDataDialog();
                if (result == DialogResult.Yes)
                {
                    saveResult = SaveFile();
                }
            }
            if (result == DialogResult.No || (result == DialogResult.Yes && saveResult == DialogResult.OK))
                return true;
            else
                return false;
        }

        /// <summary>
        /// Creates dialog about there being unsaved data
        /// </summary>
        private DialogResult UnsavedDataDialog()
        {
            DialogResult result = MessageBox.Show("Would you like to save?", "Warning: Unsaved Data", MessageBoxButtons.YesNoCancel, MessageBoxIcon.None);
            return result;
        }

        // Deals with the Save menu
        private void SaveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveFile();
        }

        // Deals with Save As so you can save somewhere else but not effect the current spreadsheet
        private void SaveAsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                SaveFileDialog sfd = new SaveFileDialog();
                sfd.Filter = "Spredsheet (*.sprd)|*.sprd|All Files (*.*)|*.*";
                sfd.FilterIndex = 1;
                sfd.AddExtension = true;
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    data.Save(sfd.FileName);
                }
            }
            catch (SpreadsheetReadWriteException error)
            {
                MessageBox.Show(error.Message, "Error");
            }
            
        }

        /// <summary>
        /// Saves a file based on whether or not its been saved before
        /// </summary>
        private DialogResult SaveFile()
        {
            try
            {
                if (filePath == "")
                {
                    DialogResult result;
                    SaveFileDialog sfd = new SaveFileDialog();
                    sfd.Filter = "Spredsheet (*.sprd)|*.sprd|All Files (*.*)|*.*";
                    sfd.FilterIndex = 1;
                    sfd.AddExtension = true;
                    result = sfd.ShowDialog();
                    if (result == DialogResult.OK)
                    {
                        data.Save(sfd.FileName);
                        filePath = sfd.FileName;
                    }
                    return result;
                }
                else
                {
                    data.Save(filePath);
                    return DialogResult.OK;
                }
            }
            catch (SpreadsheetReadWriteException e)
            {
                MessageBox.Show(e.Message, "Error");
                return DialogResult.Cancel;
            }
        }

        // If the menu bar is clicked focus shifts back to panel
        private void MenuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            spreadsheetPanel1.Focus();
        }

        private void HelpToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("- The creation, opening, closing, and saving of files can be found in the file menu. " +
                "\n\n- Selecting a cell will take you to the text box in the top left and display the current cell contents (if any)." +
                "\n- In the contents box, you can type in a string, double, or formula (string starting with '=') and press enter to " +
                "set the selected cell's contents to that information." +
                "\n- While in the contents box, you can press tab to select the Spreadsheet, then press Control + F to use the find function that " +
                "finds any cells that contain your specified value." +
                "\n- The focus will return to the text box that contains the contents when you select a cell.", "Help", MessageBoxButtons.OK, MessageBoxIcon.None);
        }
    }
}
