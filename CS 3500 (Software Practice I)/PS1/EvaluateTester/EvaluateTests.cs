using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EvaluateTester
{
    class EvaluateTests
    {
        // Testing lookup with specific variable
        public static int SimpleLookup(string s)
        {
            if (s == "A4")
                return 7;
            throw new ArgumentException("Invalid Argument");
        }

        // Testing lookup with a variety of potential variables
        public static int ManyLookup(string s)
        {
            if (s == "A1")
                return 1;
            if (s == "B1")
                return 4;
            if (s == "C1")
                return 9;
            if (s == "D1")
                return 16;
            if (s == "AA1")
                return 25;
            if (s == "BB1")
                return 36;
            if (s == "CC1")
                return 49;
            if (s == "DD1")
                return 64;
            throw new ArgumentException("Invalid Argument");
        }

        // Testing expression with no lookup variables
        public static int NoVariables(string s)
        {
            throw new ArgumentException("Invalid Argument");
        }

        static void Main(string[] args)
        {
            try
            {
                Console.WriteLine("Should be 14");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("2 * (3 + 4)", NoVariables));
                Console.WriteLine("Should be 10");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("2 * 3 + 4", NoVariables));
                Console.WriteLine("Should be 1");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("2 * 3 / 4", NoVariables));
                Console.WriteLine("Should be 0");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("2 * (3 / 4)", NoVariables));
                Console.WriteLine("Should be 101");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("2 * (3 + ( 4 * AA1) - 2) / 2", ManyLookup));
                Console.WriteLine("Should be 39");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("300 / AA1 * 25 / (1 + 3) - BB1", ManyLookup));
                Console.WriteLine("Should throw Exception");
                Console.WriteLine(FormulaEvaluator.Evaluator.Evaluate("300 / AA1 * 25 / (1 + 3) - BB 1", ManyLookup));
            }
            catch (Exception)
            {
                Console.WriteLine("Invalid Expression");
            }
            Console.Read();
        }
    }
}
