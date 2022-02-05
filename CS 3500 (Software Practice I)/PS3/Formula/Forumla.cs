// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

// (Daniel Kopta) 
// Version 1.2 (9/10/17) 

// Change log:
//  (Version 1.2) Changed the definition of equality with regards
//                to numeric tokens

// (Matthew Johnsen)
// Version 1.3 (9/9/19)

// Change log:
//  (Version 1.3) Implmented the methods for the Forumla class

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace SpreadsheetUtilities
{
    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules. The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax (without unary preceeding '-' or '+'); 
    /// variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {
        // Used to store valid forumla tokens
        private readonly List<string> formulaTokens = new List<string>();
        // Stores valid normalized variables
        private readonly HashSet<string> normalVariables = new HashSet<string>();
        // Used to store valid toString
        private readonly string normalizedString;

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.  
        /// </summary>
        public Formula(String formula) :
            this(formula, s => s, s => true)
        {
        }

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            List<string> tokens = new List<string>(GetTokens(formula));
            // Ensure theres at least one token
            if (tokens.Count() < 1)
                throw new FormulaFormatException("Forumla contains no tokens");
            // Ensures all valid tokens in a syntactically valid formula
            SyntaxRules(tokens);
            foreach (string token in tokens)
            {
                // Deal with doubles normalization
                if (double.TryParse(token, out double d))
                {
                    formulaTokens.Add(d.ToString());
                    normalizedString += d.ToString();
                }
                // Deal with variables validation and normalization
                else if (Regex.IsMatch(token, "^[a-zA-Z_]([a-zA-Z_]|\\d)*$"))
                {
                    string v = normalize(token);
                    if (!isValid(v))
                        throw new FormulaFormatException("Forumla contains invalid normalized variables");
                    formulaTokens.Add(v);
                    normalVariables.Add(v);
                    normalizedString += v;
                }
                // Deal with any other token
                else
                {
                    formulaTokens.Add(token);
                    normalizedString += token;
                }
            }
        }

        /// <summary>
        /// Determines whether a token in the formula is one of the general allowed tokens or not
        /// and returns a identifier string for the type of token it is
        /// </summary>
        private static bool TokenCheck(string token, out string id)
        {
            if (token == "(")
            {
                id = "open";
                return true;
            }
            if (token == ")")
            {
                id = "close";
                return true;
            }
            if (token == "+" || token == "-" || token == "*" || token == "/")
            {
                id = "op";
                return true;
            }
            if (Regex.IsMatch(token, "^[a-zA-Z_]([a-zA-Z_]|\\d)*$"))
            {
                id = "var";
                return true;
            }
            if (Double.TryParse(token, out _))
            {
                id = "double";
                return true;
            }
            id = null;
            return false;
        }

        /// <summary>
        /// Determines whether the equation will be invalid based on rules that each equation must follow
        /// </summary>
        /// <returns></returns>
        private static void SyntaxRules(List<string> tokens)
        {
            int openCount = 0;
            int closeCount = 0;
            bool opFollow = false;
            bool numFollow = false;
            bool firstToken = false;
            int count = 0;
            int size = tokens.Count();
            foreach (string s in tokens)
            {
                if (TokenCheck(s, out string id))
                {
                    count++;
                    // Deals with first token
                    if (firstToken == false)
                    {
                        if (!(id == "double" || id == "var" || id == "open"))
                            throw new FormulaFormatException("Invalid first token. The first token of the formula " +
                                "must be a number, variable, or open parenthesis");
                        firstToken = true;
                    }
                    // Deals with last token
                    else if (count == size)
                    {
                        if (!(id == "double" || id == "var" || id == "close"))
                            throw new FormulaFormatException("Invalid final token. The final token of the formula " +
                                "must be a number, variable, or close parenthesis");
                    }
                    // Deals with tokens following an opening parenthesis or operator
                    if (opFollow == true)
                    {
                        if (!(id == "double" || id == "var" || id == "open"))
                            throw new FormulaFormatException("Invalid following token. A token following a open parenthesis or operator " +
                                "must be a number, variable, or open parenthesis");
                        opFollow = false;
                    }
                    // Deals with tokens following an opening parenthesis or operator
                    else if (numFollow == true)
                    {
                        if (!(id == "op" || id == "close"))
                            throw new FormulaFormatException("Invalid following token. A token following a number, variable, close parenthesis " +
                                "must be an operator or closing parenthesis");
                        numFollow = false;
                    }
                    if (id == "open")
                    {
                        openCount++;
                        opFollow = true;
                    }
                    else if (id == "close")
                    {
                        closeCount++;
                        if (closeCount > openCount)
                            throw new FormulaFormatException("The number of closing parentheses was greater than opening parentheses." +
                                "Make sure to match every open and close paretheses in the equation.");
                        numFollow = true;
                    }
                    else if (id == "op")
                        opFollow = true;
                    else if (id == "var")
                        numFollow = true;
                    else
                        numFollow = true;
                }
                else
                    throw new FormulaFormatException("Invalid token in formula, ensure forumla contains only: " +
                        "parentheses (open and close), doubles, the 4 valid operators, and valid variables.");
            }
            if (closeCount != openCount)
            {
                throw new FormulaFormatException("The number of parentheses is not balanced. Make sure every" +
                    "open parenthesis has a close parenthesis");
            }
        }

        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            try
            {
                Stack<double> vals = new Stack<double>();
                Stack<string> ops = new Stack<string>();
                // Process each token in the statement
                foreach (string s in formulaTokens)
                {
                    if (double.TryParse(s, out double d))
                    {
                        if (vals.Count > 0)
                        {
                            if (ops.IsOnTop("*", "/"))
                                vals.Push(Operate(d, vals.Pop(), ops.Pop()));
                            else
                                vals.Push(d);
                        }
                        else
                            vals.Push(d);
                    }
                    // Token is + or -
                    else if (s == "+" || s == "-")
                    {
                        if (ops.IsOnTop("+", "-"))
                            vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                        ops.Push(s);
                    }
                    // Token is * or /
                    else if (s == "*" || s == "/")
                        ops.Push(s);
                    // Token is (
                    else if (s == "(")
                        ops.Push(s);
                    // Token is )
                    else if (s == ")")
                    {
                        if (ops.IsOnTop("+", "-"))
                            vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                        ops.Pop();
                        if (ops.IsOnTop("*", "/"))
                            vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                    }
                    // Token is variable (already valid based on constructor)
                    else
                    {
                        double num = lookup(s);
                        if (vals.Count > 0)
                        {
                            if (ops.IsOnTop("*", "/"))
                                vals.Push(Operate(num, vals.Pop(), ops.Pop()));
                            else
                                vals.Push(num);
                        }
                        else
                            vals.Push(num);
                    }
                }
                // Finalize remaining values in the stack
                if (ops.Count == 0)
                    return vals.Pop();
                else
                    return Operate(vals.Pop(), vals.Pop(), ops.Pop());
            }
            catch (DivideByZeroException)
            {
                return new FormulaError("*Divide by 0*");
            }
            catch (ArgumentException)
            {
                return new FormulaError("*Unknown Var*");
            }
        }

        /// <summary>
        /// Helper method that takes in 2 doubles and performs a operation on them
        /// </summary>
        /// <param name="i1"></param>
        /// <param name="i2"></param>
        /// <param name="op"></param>
        /// <returns></returns>
        private static double Operate(double i1, double i2, string op)
        {
            switch (op)
            {
                case "/":
                    if (i1 == 0)
                        throw new DivideByZeroException();
                    return i2 / i1;
                case "*":
                    return i2 * i1;
                case "+":
                    return i2 + i1;
                case "-":
                    return i2 - i1;
            }
            return -1;
        }

        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {
            return normalVariables;
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            return normalizedString;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens and variable tokens.
        /// Numeric tokens are considered equal if they are equal after being "normalized" 
        /// by C#'s standard conversion from string to double, then back to string. This 
        /// eliminates any inconsistencies due to limited floating point precision.
        /// Variable tokens are considered equal if their normalized forms are equal, as 
        /// defined by the provided normalizer.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            if (obj == null || !(obj is Formula))
                return false;
            return GetHashCode() == obj.GetHashCode();
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            if (f1 is null && f2 is null)
                return true;
            else if (f1 is null && !(f2 is null))
                return false;
            return f1.Equals(f2);
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            if (f1 is null && f2 is null)
                return false;
            else if (f1 is null && !(f2 is null))
                return true;
            return !(f1.Equals(f2));
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// </summary>
        public override int GetHashCode()
        {
            return this.ToString().GetHashCode();
        }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?: [a-zA-Z_]|\d)*";
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String spacePattern = @"\s+";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s;
                }
            }
        }
    }
    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }

    // Used to add extension to stacks that can be used in the evaluate method
    static class StackExtensions
    {
        /// <summary>
        /// Determines whether or not s1 or s2 are on top of the stack
        /// </summary>
        /// <param name="s"></param>
        /// <param name="s1"></param>
        /// <param name="s2"></param>
        /// <returns></returns>
        public static bool IsOnTop(this Stack<string> s, string s1, string s2)
        {
            if (s.Count < 1)
                return false;
            return s.Peek() == s1 || s.Peek() == s2;
        }
    }
}