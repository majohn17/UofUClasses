using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace FormulaEvaluator
{
    public static class Evaluator
    {
        /// <summary>
        /// Delegate used to find the value of variables
        /// </summary>
        /// <param name="v"></param>
        /// <returns></returns>
        public delegate int Lookup(String v);

        /// <summary>
        /// Evaluates the infix expression passed based on the lookup padded if there are any variables in the expression
        /// </summary>
        /// <param name="exp"></param>
        /// <param name="varEval"></param>
        /// <returns></returns>
        public static int Evaluate(String exp, Lookup varEval)
        {
            Stack<int> vals = new Stack<int>();
            Stack<char> ops = new Stack<char>();
            string[] tokens = Regex.Split(exp, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");
            int result;

            // Process each token in the statement
            foreach (string s in tokens)
            {
                // Remove whitespace from Token
                string t = s.Trim();
                // Deal with Whitespace/Blank Tokens
                if (s == " " || s.Length == 0)
                    continue;
                // Is the token an integer
                else if (int.TryParse(t, out int temp))
                {
                    if (vals.Count > 0 && ops.Count > 0)
                    {
                        if (ops.Peek() == '/' || ops.Peek() == '*')
                        {
                            result = Operate(temp, vals.Pop(), ops.Pop());
                            vals.Push(result);
                        }
                        else
                            vals.Push(temp);
                    }
                    else
                        vals.Push(temp);
                }
                // Is the token a variable
                else if (IsVariable(t))
                {
                    int num = varEval(t);
                    if (vals.Count > 0 && ops.Count > 0)
                    {
                        if (ops.Peek() == '/' || ops.Peek() == '*')
                        {
                            result = Operate(num, vals.Pop(), ops.Pop());
                            vals.Push(result);
                        }
                        else
                            vals.Push(num);
                    }
                    else
                        vals.Push(num);
                }
                // Is the token + or -
                else if (t == "+" || t == "-")
                {
                    if (ops.Count > 0 && (ops.Peek() == '+' || ops.Peek() == '-'))
                    {
                        if (vals.Count < 2)
                            throw new ArgumentException();
                        vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                    }
                    ops.Push(char.Parse(t));
                }
                // Is the token * or /
                else if (t == "*" || t == "/")
                    ops.Push(char.Parse(t));
                // Is the token (
                else if (t == "(")
                    ops.Push(char.Parse(t));
                // Is the token )
                else if (t == ")")
                {
                    if (ops.Count > 0 && (ops.Peek() == '+' || ops.Peek() == '-'))
                    {
                        if (vals.Count < 2)
                            throw new ArgumentException();
                        vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                    }
                    if (ops.Count < 1 || ops.Peek() != '(')
                        throw new ArgumentException();
                    ops.Pop();
                    if (ops.Count > 0 && (ops.Peek() == '*' || ops.Peek() == '/'))
                    {
                        if (vals.Count < 2)
                            throw new ArgumentException();
                        vals.Push(Operate(vals.Pop(), vals.Pop(), ops.Pop()));
                    }
                }
            }
            // Finalize remaining values in the stack
            if (ops.Count == 0)
            {
                if (vals.Count == 1)
                    return vals.Pop();
                throw new ArgumentException();
            }
            else
            {
                if (ops.Count != 1 && (ops.Peek() != '+' || ops.Peek() != '-'))
                    throw new ArgumentException();
                else if (vals.Count != 2)
                    throw new ArgumentException();
                return Operate(vals.Pop(), vals.Pop(), ops.Pop());
            }
        }
        
        /// <summary>
        /// Helper method that takes in 2 its and performs a string operation on them
        /// </summary>
        /// <param name="i1"></param>
        /// <param name="i2"></param>
        /// <param name="op"></param>
        /// <returns></returns>
        private static int Operate(int i1, int i2, char op)
        {
            switch(op)
            {
                case '/':
                    if (i1 == 0)
                        throw new ArgumentException();
                    return i2 / i1;
                case '*':
                    return i2 * i1;
                case '+':
                    return i2 + i1;
                case '-':
                    return i2 - i1;
            }
            throw new ArgumentException();
        }

        /// <summary>
        /// Determines if the passed string is a valid variable
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsVariable(string s)
        {
            bool foundLetter = false;
            bool foundDigit = false;

            int i;
            for (i = 0; i < s.Length; i++)
            {
                if (Char.IsLetter(s[i]))
                    foundLetter = true;
                else
                    break;
            }

            for (; i < s.Length; i++)
            {
                if (Char.IsDigit(s[i]))
                    foundDigit = true;
                else
                    return false;
            }

            return foundLetter && foundDigit;
        }
    }
}
