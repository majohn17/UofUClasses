using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2
{
    class Program
    {
        /// <summary>
        /// This is the Main method similar to what Java's main method does
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args) 
        {
            int i = 1;
            int j = 3;
            while (i < 10)
            {
                j *= i;
                i++;
            }
            Console.WriteLine(j);
            j = 0;
            Console.WriteLine(j);
            Console.Read();
        }
    }
}
