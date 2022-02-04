using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PopulationTesting
{
    class PopulationTest
    {
        static void Main(string[] args)
        {
            string[] pop = GeneratePopulation();
            Console.WriteLine(ProbMajority(pop, 184));
            Console.WriteLine("done");
        }

        private static string[] GeneratePopulation()
        {
            string[] population = new string[10000000];
            int i = 0;
            for (i = 0; i < 5500000; i++)
            {
                population[i] = "a";
            }
            for (i = 5500000; i < 10000000; i++)
            {
                population[i] = "b";
            }
            return population;
        }

        private static double ProbMajority(string[] pop, int sampSize)
        {
            double totalMaj = 0;
            Random rnd = new Random();
            for (int i = 0; i < 5000000; i++)
            {
                double numA = 0;
                for (int j = 0; j < sampSize; j++)
                {
                    if (pop[rnd.Next(0, 9999999)].Equals("a"))
                    {
                        numA += 1;
                    }
                }
                if (numA > (sampSize/2))
                {
                    totalMaj += 1;
                }
            }
            return totalMaj / 5000000;
        }
    }

}
