using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using SS;
using System;
using System.Collections.Generic;
using System.Xml;

namespace SpreadsheetTests
{
    [TestClass]
    public class SpreadsheetTest
    {
        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestGetCellContentsInvalidName1()
        {
            Spreadsheet s = new Spreadsheet();
            _ = s.GetCellContents("1A");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestGetCellContentsInvalidName2()
        {
            Spreadsheet s = new Spreadsheet();
            s.GetCellContents(null);
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetCellContentsEmptyCell()
        {
            Spreadsheet s = new Spreadsheet();
            Assert.AreEqual("", s.GetCellContents("A1"));
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetCellContents1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=B2+(C3*9)");
            Assert.AreEqual(new Formula("B2+(C3*9)"), s.GetCellContents("A1"));
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetCellContents2()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "13.3");
            Assert.AreEqual(13.3, s.GetCellContents("A1"));
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetCellContents3()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "Hello");
            Assert.AreEqual("Hello", s.GetCellContents("A1"));
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(CircularException))]
        public void TestCircularDependency1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=1+A1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(CircularException))]
        public void TestCircularDependency2()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=1+B1");
            s.SetContentsOfCell("B1", "=2+C1");
            s.SetContentsOfCell("C1", "=3+D1");
            s.SetContentsOfCell("D1", "=4+E1");
            s.SetContentsOfCell("E1", "=5+A1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(CircularException))]
        public void TestCircularDependency3()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=C1+B1");
            s.SetContentsOfCell("B1", "=9+(3/4)");
            s.SetContentsOfCell("C1", "=23+B1");
            s.SetContentsOfCell("D1", "=A1+E1");
            s.SetContentsOfCell("E1", "=D1+A1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(CircularException))]
        public void TestCircularDependency4()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=1+2");
            s.SetContentsOfCell("B1", "=A1*2");
            s.SetContentsOfCell("C1", "=A1+B1");
            s.SetContentsOfCell("A1", "=B1-3");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("C1^", "=23+B1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName2()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("1C", "12.2");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName3()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("D1111*", "Hello");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName4()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "=23+B1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName5()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "12.2");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetContentsOfCellInvalidName6()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "Hello");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetContentsOfCellInvalidText()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A23523_23652sdfga23452", (string)null);
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetContentsOfCellNull()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A23523_23652sdfga23452", null);
        }

        [TestMethod(), Timeout(5000)]
        public void TestSetContentsOfCell1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=12+D1");
            s.SetContentsOfCell("B1", "=9+A1");
            s.SetContentsOfCell("C1", "=23+B1");
            List<string> correct = new List<string> { "D1", "A1", "B1", "C1" };
            List<string> check = new List<string>(s.SetContentsOfCell("D1", "=2+E1"));
            Assert.AreEqual(correct[0], check[0]);
            Assert.AreEqual(correct[1], check[1]);
            Assert.AreEqual(correct[2], check[2]);
            Assert.AreEqual(correct[3], check[3]);
        }

        [TestMethod(), Timeout(5000)]
        public void TestSetContentsOfCell2()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "12.2");
            s.SetContentsOfCell("B1", "=A1*A1");
            s.SetContentsOfCell("C1", "=2 * B1");
            List<string> correct = new List<string> { "D1"};
            List<string> check = new List<string>(s.SetContentsOfCell("D1", "Hello"));
            Assert.AreEqual(correct[0], check[0]);
        }

        [TestMethod(), Timeout(5000)]
        public void TestSetContentsOfCellReplace()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "12.2");
            s.SetContentsOfCell("A1", "12.32");
            Assert.AreEqual(12.32, s.GetCellContents("A1"));
            s.SetContentsOfCell("A1", "Testing");
            Assert.AreEqual("Testing", s.GetCellContents("A1"));
            s.SetContentsOfCell("A1", "=1*2");
            Assert.AreEqual(new Formula("1 * 2"), s.GetCellContents("A1"));
        }

        [TestMethod(), Timeout(5000)]
        public void TestSetContentsOfCellEmptryStringRemove()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "12.2");
            s.SetContentsOfCell("B1", "Test");
            s.SetContentsOfCell("C1", "=1+1");
            List<string> check = new List<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(3, check.Count);
            s.SetContentsOfCell("A1", "");
            check = new List<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(2, check.Count);
            s.SetContentsOfCell("B1", "");
            check = new List<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(1, check.Count);
            s.SetContentsOfCell("C1", "");
            check = new List<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(0, check.Count);
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetNonemptyCellNames()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=12+D1");
            s.SetContentsOfCell("B1", "=9+A1");
            s.SetContentsOfCell("C1", "=23+B1");
            List<string> correct = new List<string> { "A1", "B1", "C1" };
            List<string> check = new List<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(correct[0], check[0]);
            Assert.AreEqual(correct[1], check[1]);
            Assert.AreEqual(correct[2], check[2]);
        }

        

        [TestMethod(), Timeout(5000)]
        public void TestSave1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "1.2");
            s.SetContentsOfCell("B1", "test");
            s.Save("test.txt");
        }

        [TestMethod(), Timeout(5000)]
        public void TestSave2()
        {
            Spreadsheet s = new Spreadsheet();
            for (int i = 1; i <= 10; i++)
            {
                s.SetContentsOfCell("A1" + i, "=B" + i + "+1");
                s.SetContentsOfCell("C1" + i, "=A" + i + "+ B" + i);
            }
            s.Save("test.txt");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestSaveException()
        {
            Spreadsheet s = new Spreadsheet();
            for (int i = 1; i <= 10; i++)
            {
                s.SetContentsOfCell("A1" + i, "=B" + i + "+1");
            }
            s.Save(null);
        }

        [TestMethod(), Timeout(5000)]
        public void TestReadSpreadsheet()
        {
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "  ";
            using (XmlWriter writer = XmlWriter.Create("save.txt", settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "B1");
                writer.WriteElementString("contents", "12.1");
                writer.WriteEndElement();

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "C1");
                writer.WriteElementString("contents", "=B1 * 2");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }

            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
            List<string> correct = new List<string> { "A1", "B1", "C1" };
            List<string> check = new List<string>(ss.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(correct[0], check[0]);
            Assert.AreEqual(correct[1], check[1]);
            Assert.AreEqual(correct[2], check[2]);
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException1()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "versionException");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException2()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException3()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException4()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("name", "A2");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException5()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteElementString("contents", "hello1");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException6()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("test", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteElementString("contents", "hello1");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException7()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet(null, s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException8()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save1.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException9()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "=#$^#$#&^@");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void TestReadSpreadsheetException10()
        {
            using (XmlWriter writer = XmlWriter.Create("save.txt"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "=B1");
                writer.WriteEndElement();

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "B1");
                writer.WriteElementString("contents", "=A1");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetValue()
        {
            AbstractSpreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=12+D1");
            s.SetContentsOfCell("B1", "=9+A1");
            s.SetContentsOfCell("C1", "=23+B1");
            s.SetContentsOfCell("E5", "hello");
            s.SetContentsOfCell("G5", "13.2");
            s.SetContentsOfCell("H5", "=G5*3");
            Assert.IsInstanceOfType(s.GetCellValue("A1"), typeof(FormulaError));
            Assert.AreEqual("hello", s.GetCellValue("E5"));
            Assert.AreEqual(13.2, (double)s.GetCellValue("G5"), 1e-9);
            Assert.AreEqual(39.6, (double)s.GetCellValue("H5"), 1e-9);
            Assert.AreEqual("", s.GetCellValue("E2353255"));
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestGetValueException()
        {
            AbstractSpreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=12+D1");
            s.GetCellValue("1");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestNullDelegate()
        {
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", null, s => s, "");
        }

        // STRESS TESTS
        [TestMethod(), Timeout(5000)]
        public void TestStress()
        {
            Spreadsheet s = new Spreadsheet();
            for (int i = 1; i <= 10000; i++)
            {
                s.SetContentsOfCell("A" + i, "=B" + i + "+1");
                s.SetContentsOfCell("C" + i, "=A" + i + "+ B" + i);
            }
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress1()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=B1+B2");
            s.SetContentsOfCell("B1", "=C1-C2");
            s.SetContentsOfCell("B2", "=C3*C4");
            s.SetContentsOfCell("C1", "=D1*D2");
            s.SetContentsOfCell("C2", "=D3*D4");
            s.SetContentsOfCell("C3", "=D5*D6");
            s.SetContentsOfCell("C4", "=D7*D8");
            s.SetContentsOfCell("D1", "=E1");
            s.SetContentsOfCell("D2", "=E1");
            s.SetContentsOfCell("D3", "=E1");
            s.SetContentsOfCell("D4", "=E1");
            s.SetContentsOfCell("D5", "=E1");
            s.SetContentsOfCell("D6", "=E1");
            s.SetContentsOfCell("D7", "=E1");
            s.SetContentsOfCell("D8", "=E1");
            IList<String> cells = s.SetContentsOfCell("E1", "0");
            Assert.IsTrue(new HashSet<string>() { "A1", "B1", "B2", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "E1" }.SetEquals(cells));
        }

        // Repeated for extra weight
        [TestMethod(), Timeout(5000)]
        public void TestStress1a()
        {
            TestStress1();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress1b()
        {
            TestStress1();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress1c()
        {
            TestStress1();
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress2()
        {
            Spreadsheet s = new Spreadsheet();
            ISet<String> cells = new HashSet<string>();
            for (int i = 1; i < 200; i++)
            {
                cells.Add("A" + i);
                Assert.IsTrue(cells.SetEquals(s.SetContentsOfCell("A" + i, "=A" + (i + 1))));
            }
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress2a()
        {
            TestStress2();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress2b()
        {
            TestStress2();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress2c()
        {
            TestStress2();
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress3()
        {
            Spreadsheet s = new Spreadsheet();
            for (int i = 1; i < 200; i++)
            {
                s.SetContentsOfCell("A" + i, "=A" + (i + 1));
            }
            try
            {
                s.SetContentsOfCell("A150", "=A50");
                Assert.Fail();
            }
            catch (CircularException)
            {
            }
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress3a()
        {
            TestStress3();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress3b()
        {
            TestStress3();
        }
        [TestMethod(), Timeout(5000)]
        public void TestStress3c()
        {
            TestStress3();
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress4()
        {
            RunRandomizedTest(47, 2519);
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress5()
        {
            RunRandomizedTest(48, 2521);
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress6()
        {
            RunRandomizedTest(49, 2526);
        }

        [TestMethod(), Timeout(5000)]
        public void TestStress7()
        {
            RunRandomizedTest(50, 2521);
        }

        /// <summary>
        /// Sets random contents for a random cell 10000 times
        /// </summary>
        /// <param name="seed">Random seed</param>
        /// <param name="size">The known resulting spreadsheet size, given the seed</param>
        public void RunRandomizedTest(int seed, int size)
        {
            Spreadsheet s = new Spreadsheet();
            Random rand = new Random(seed);
            for (int i = 0; i < 10000; i++)
            {
                try
                {
                    switch (rand.Next(3))
                    {
                        case 0:
                            s.SetContentsOfCell(randomName(rand), "3.14");
                            break;
                        case 1:
                            s.SetContentsOfCell(randomName(rand), "hello");
                            break;
                        case 2:
                            s.SetContentsOfCell(randomName(rand), randomFormula(rand));
                            break;
                    }
                }
                catch (CircularException)
                {
                }
            }
            ISet<string> set = new HashSet<string>(s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(size, set.Count);
        }

        /// <summary>
        /// Generates a random cell name with a capital letter and number between 1 - 99
        /// </summary>
        /// <param name="rand"></param>
        /// <returns></returns>
        private String randomName(Random rand)
        {
            return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".Substring(rand.Next(26), 1) + (rand.Next(99) + 1);
        }

        /// <summary>
        /// Generates a random Formula
        /// </summary>
        /// <param name="rand"></param>
        /// <returns></returns>
        private String randomFormula(Random rand)
        {
            String f = randomName(rand);
            for (int i = 0; i < 10; i++)
            {
                switch (rand.Next(4))
                {
                    case 0:
                        f += "+";
                        break;
                    case 1:
                        f += "-";
                        break;
                    case 2:
                        f += "*";
                        break;
                    case 3:
                        f += "/";
                        break;
                }
                switch (rand.Next(2))
                {
                    case 0:
                        f += 7.2;
                        break;
                    case 1:
                        f += randomName(rand);
                        break;
                }
            }
            return f;
        }

    }

    //[TestMethod(), Timeout(5000)]
    //public void TestForumlaError1()
    //{
    //Formula f = "=2+(3/0)");
    //Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
    //}
}
