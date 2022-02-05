using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System;
using System.Collections.Generic;

namespace DevelopmentTests
{
    [TestClass]
    public class FormulaTest
    {
        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula1()
        {
            Formula f = new Formula("((2 + 3) * 9");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula2()
        {
            Formula f = new Formula("(2 + 3) * 9)");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula3()
        {
            Formula f = new Formula("(2 + 3) + 4) * 9");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula4()
        {
            Formula f = new Formula(")(2 + 3) + 4) * 9");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula5()
        {
            Formula f = new Formula("((2 + 3) + 4) * 9(");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula6()
        {
            Formula f = new Formula("()(2 + 3) + 4) * 9(");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula7()
        {
            Formula f = new Formula("((2 + 3 3) + 4) * 9(");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula8()
        {
            Formula f = new Formula("((2 + 3y) + 4) * 9(");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestInvalidFormula9()
        {
            Formula f = new Formula("((2 + $%@^@) + 4) * 9(");
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestEmptyFormula()
        {
            Formula f = new Formula("");
        }

        [TestMethod(), Timeout(5000)]
        public void TestNormalizeAndValid1()
        {
            Formula f = new Formula("(2 + a8) / 9", s => s.ToUpper(), s => (s == s.ToUpper()) ? true : false);
            Assert.AreEqual("(2+A8)/9", f.ToString());
        }

        [TestMethod(), Timeout(5000)]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestNormalizeAndValid2()
        {
            Formula f = new Formula("2 + a8", s => s.ToUpper(), s => s == "a8");
        }

        [TestMethod(), Timeout(5000)]
        public void TestToString1()
        {
            Formula f = new Formula("(2 + 3 + 2) * (3 * 9)");
            Assert.AreEqual("(2+3+2)*(3*9)", f.ToString());
        }

        [TestMethod(), Timeout(5000)]
        public void TestToString2()
        {
            Formula f = new Formula("2       +   19 * (3              * 9)");
            Assert.AreEqual("2+19*(3*9)", f.ToString());
        }

        [TestMethod(), Timeout(5000)]
        public void TestToString3()
        {
            Formula f = new Formula("(2   +9) * (8+    4-       2*3)    +   17 * (3              / (_323   + 9))");
            Assert.AreEqual("(2+9)*(8+4-2*3)+17*(3/(_323+9))", f.ToString());
        }

        [TestMethod(), Timeout(5000)]
        public void TestForumlaError1()
        {
            Formula f = new Formula("2+(3/0)");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod(), Timeout(5000)]
        public void TestForumlaError2()
        {
            Formula f = new Formula("2+(3/a8)");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod(), Timeout(5000)]
        public void TestForumlaError3()
        {
            Formula f = new Formula("2+(3/aB)");
            Assert.IsInstanceOfType(f.Evaluate(s => (s == "aA") ? 3 : throw new ArgumentException()), typeof(FormulaError));
        }

        [TestMethod(), Timeout(5000)]
        public void TestForumlaError4()
        {
            Formula f = new Formula("2+(3/aB)");
            Assert.IsInstanceOfType(f.Evaluate(s => (s == "aA") ? 0 : throw new ArgumentException()), typeof(FormulaError));
        }

        [TestMethod(), Timeout(5000)]
        public void TestForumlaErrorMessage()
        {
            Formula f = new Formula("2/0");
            FormulaError e = (FormulaError) f.Evaluate(s => 0);
            Assert.AreEqual("Error: Cannot Divide by 0", e.Reason);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEvaluateNoVariables1()
        {
            Formula f = new Formula("2+(3/6)");
            Assert.AreEqual(2.5 ,(double)f.Evaluate(s => 0), 1e-9);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEvaluateNoVariables2()
        {
            Formula f = new Formula("((38 + 94) - 2.1) * (10 * 2.5)");
            Assert.AreEqual(3247.5, (double)f.Evaluate(s => 0), 1e-9);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEvaluateVariables1()
        {
            Formula f = new Formula("2+(3/6) * _asdgasdg_asdg_asg_sdfasdga");
            Assert.AreEqual(4.5, (double)f.Evaluate(s => 5), 1e-9);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEvaluateVariables2()
        {
            Formula f = new Formula("a1 + (5/a2) - 5.57 + (5*a1)");
            Assert.AreEqual(144.93, (double)f.Evaluate(s => (s == "a1") ? 25 : 10), 1e-9);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEquals1()
        {
            Formula f = new Formula("(a1 + a2)", s => s.ToUpper(), s => true);
            Assert.AreEqual(false, f.Equals(3));
        }

        [TestMethod(), Timeout(5000)]
        public void TestEquals2()
        {
            Formula f = new Formula("(a1 + a2)", s => s.ToUpper(), s => true);
            Assert.AreEqual(false, f.Equals(null));
        }

        [TestMethod(), Timeout(5000)]
        public void TestEquals3()
        {
            Formula f = new Formula("(a1 + a2)", s => s.ToUpper(), s => true);
            Assert.AreEqual(false, f.Equals(new Formula("A1+A2")));
        }

        [TestMethod(), Timeout(5000)]
        public void TestEqualOp1()
        {
            Formula f = null;
            Formula g = null;
            Assert.AreEqual(true, f == g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEqualOp2()
        {
            Formula f = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Formula g = null;
            Assert.AreEqual(false, f == g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEqualOp3()
        {
            Formula f = null;
            Formula g = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Assert.AreEqual(false, f == g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestEqualOp4()
        {
            Formula f = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Formula g = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Assert.AreEqual(true, f == g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestNotEqualOp1()
        {
            Formula f = null;
            Formula g = null;
            Assert.AreEqual(false, f != g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestNotEqualOp2()
        {
            Formula f = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Formula g = null;
            Assert.AreEqual(true, f != g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestNotEqualOp3()
        {
            Formula f = null;
            Formula g = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Assert.AreEqual(true, f != g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestNotEqualOp4()
        {
            Formula f = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Formula g = new Formula("1 + 1", s => s.ToUpper(), s => true);
            Assert.AreEqual(false, f != g);
        }

        [TestMethod(), Timeout(5000)]
        public void TestGetVariables()
        {
            Formula f = new Formula("(2 * 9.8) + 17 - a1 + b1 * (c3 + 12.5)", s => s.ToUpper(), s => true);
            List<string> l1 =  new List<string>{ "A1", "B1", "C3" };
            List<string> l2 = new List<string>(f.GetVariables());
            Assert.AreEqual(l1[0], l2[0]);
            Assert.AreEqual(l1[1], l2[1]);
            Assert.AreEqual(l1[2], l2[2]);
        }
    }
}
