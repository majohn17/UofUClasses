/* This utility generates 3D points from given candidates and point_cloud
 * text files and determines a score for each candidate point then returns
 * the best point and its score
 *
 * Matthew Johnsen (u1173601)
 * January 10, 2020
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cmath>
#include "pntvec.h"

std::vector<pntvec> readPoints(std::string filename);
double calculateScore(pntvec candidate, std::vector<pntvec> points);

/* Main -- the application entry point.
 * 
 * Parameters:
 *      none
 *
 * Returns:
 *      an integer exit code, we return non-zero if an error occurred
 */
int main() {
    std::vector<pntvec> candidates = readPoints("candidates.txt");
    std::vector<pntvec> points = readPoints("point_cloud.txt");
    pntvec winner;
    double bestScore;
    for (int i = 0; i < candidates.size(); i++)
    {
        double score = calculateScore(candidates[i], points);
        if (i == 0)
        {
            winner = candidates[i];
            bestScore = score;
        }
        else
        {
            if (score < bestScore)
            {
                winner = candidates[i];
                bestScore = score;
            }
        }
    }
    std::cout << winner.x << " " << winner.y << " " << winner.z << std::endl;
    std::cout << bestScore << std::endl;
    return 0;
}

/* Obtains 3D points from a file and stores them in a vector
 *
 * Parameters
 *      filename - the name of the file where the 3D points are stored
 *
 * Returns:
 *      a vector of 3D points from the file
 */
std::vector<pntvec> readPoints(std::string filename) {
    std::vector<pntvec> points;
    std::ifstream reader(filename.c_str());
    double x,y,z;
    while (reader >> x >> y >> z)
    {
        pntvec p;
        p.x = x;
        p.y = y;
        p.z = z;
        points.push_back(p);
    }
    return points;
}

/* Takes the sum of the squares of the distances from the candidate point to every
 * other point in the point cloud to obtain a score
 *
 * Parameters:
 *      candidate - the candidate 3D point
 *      points - a vector of 3D points containing all points in the point cloud
 *
 * Returns:
 *      the score calculated for the candidate point
 */
double calculateScore(pntvec candidate, std::vector<pntvec> points) {
    double score = 0;
    for (int i = 0; i < points.size(); i++)
    {
        double xVal = std::pow((candidate.x - points[i].x), 2);
        double yVal = std::pow((candidate.y - points[i].y), 2);
        double zVal = std::pow((candidate.z - points[i].z), 2);
        score += (xVal + yVal + zVal);
    }
    return score;
}
