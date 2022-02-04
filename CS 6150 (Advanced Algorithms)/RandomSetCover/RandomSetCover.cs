using System;
using System.IO;
using System.Threading.Tasks;
using System.Collections.Generic;


namespace RandomSetCover
{
    /* Generates a random set cover example for n = m = numPeople/skillRange where the n people that each posses numSkills must cover the set of all skills in the skill range */
    /* The text functions that are output to a file are intended to be pasted into the LPsolve IDE (sourceforge.net/projects/lpsolve/) */
    class Program
    {
        public static void Main(string[] args)
        {
            int numPeople = 500;
            int skillRange = 500;
            int numSkills = 25;

            List<Person> people = new List<Person>();

            for (int i = 0; i < numPeople; i++)
            {
                people.Add(new Person(numSkills, skillRange));
            }

            // CREATE A FILE FOR LP DATA (each text line represents a person with a set of skills)
            for (int i = 0; i < numPeople; i++)
            {
                using (StreamWriter w = File.AppendText("LPRandomSample.txt"))
                {
                    w.WriteLine(people[i].GenerateText());
                }
            }

            // CREATE THE TEXT TO PLUG INTO LP SOLVE
            // Create objective function (minimize the number of random variables used to deal with constraints)
            string objFunc = "min: v_1 ";
            for (int i = 2; i <= numPeople; i++)
            {
                objFunc += "+ v_" + i + " ";
            }
            objFunc = objFunc.Substring(0, objFunc.Length - 1);
            objFunc += ";";
            using (StreamWriter w = File.AppendText("LPSolveInfo.txt"))
            {
                w.WriteLine(objFunc);
            }

            // Create the constraints (1 for each skill)
            for (int i = 1; i <= numPeople; i++)
            {
                string constraint = "r" + i + ": ";
                for (int j = 1; j <= people.Count; j++)
                {
                    if (people[j - 1].skills.Contains(i))
                    {
                        constraint += "v_" + j + " + ";
                    }
                }
                constraint = constraint.Substring(0, constraint.Length - 2);
                constraint += ">= 1;";
                using (StreamWriter w = File.AppendText("LPSolveInfo.txt"))
                {
                    w.WriteLine(constraint);
                }
            }

            /*
            // If we want to test with binary LP values not just the relaxation
            string bin = "bin ";
            for (int i = 1; i <= numPeople; i++)
            {
                bin += "v_" + i + ", ";
            }
            bin = bin.Substring(0, bin.Length - 2);
            bin += ";";
            using (StreamWriter w = File.AppendText("LPSolveInfo.txt"))
            {
                w.WriteLine(bin);
            }
            */

            // DO ROUNDING FOR THE RELAXATION AND DETERMINE HIREING RATES AMOUNT HIRED (FOR EXPORTED HTML OBJECTIVE VALS FROM LPSOLVE)
            // Must break on ReadAllLines to get objects values from LPSolve IDE and copy them to ObjectiveVals.txt including the V's
            string[] lines = File.ReadAllLines("ObjectiveVals.txt");
            double[] weights = new double[numPeople];
            for (int i = 0; i < numPeople; i++)
            {
                weights[i] = Double.Parse(lines[i].Split("\t")[1]);
            }
            OutputStats(people, weights, 1, numPeople);
            OutputStats(people, weights, 2, numPeople);
            OutputStats(people, weights, 4, numPeople);
            OutputStats(people, weights, 8, numPeople);
            Console.WriteLine("Done");
        }

        static void OutputStats(List<Person> p, double[] w, int t, int n)
        {
            Console.WriteLine("For t = " + t + ":");
            bool[] hired = new bool[n];
            bool[] covered = new bool[n];
            for (int i = 0; i < n; i++)
            {
                hired[i] = false;
                covered[i] = false;
            }

            Random rand = new Random();
            for (int i = 0; i < n; i++)
            {
                if (rand.NextDouble() <= Math.Min(1.0, (w[i]*t)))
                {
                    hired[i] = true;
                    foreach(int s in p[i].skills)
                    {
                        covered[s - 1] = true;
                    }
                }
            }

            int u = 0;
            int h = 0;
            for (int i = 0; i < n; i++)
            {
                if (hired[i] == true)
                    h++;
                if (covered[i] == false)
                    u++;
            }

            Console.WriteLine("\tNumber of people hired: " + h);
            Console.WriteLine("\tNumber skills uncovered: " + u);
        }
    }

    public class Person
    {

        public List<int> skills;
        public Person(int numSkills, int skillRange)
        {
            skills = new List<int>();
            Random rand = new Random();
            for (int i = 0; i < numSkills; i++)
            {
                int temp = rand.Next(1, skillRange + 1);
                if (skills.Contains(temp))
                    i--;
                else
                    skills.Add(temp);
            }
        }

        public string GenerateText()
        {
            string result = "{";

            foreach (int s in skills)
            {
                result += s + ", ";
            }

            result = result.Substring(0, result.Length - 2);
            result += "}";

            return result;
        }
    }
}
