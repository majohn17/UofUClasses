using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TankWars;

namespace Server
{
    public class Collisions
    {

        /// <summary>
        /// Detects collision between a rectangle and a point 
        /// </summary>
        /// <param name="rectangleCenter"></param>
        /// <param name="width"></param>
        /// <param name="point"></param>
        /// <returns></returns>
        public static bool CollisionDetected (Vector2D SPoint1, Vector2D SPoint2, Vector2D OtherPoint )
        {
            // will need to check that Spoint1 X is not greater than SPoint2 X same with Y values 
            bool p1XisMinium = SPoint1.GetX() <= SPoint2.GetX();
            bool p1YisMinium = SPoint1.GetY() <= SPoint2.GetY();

            // other point is between the 2 X points
            if (p1XisMinium)
            {
                if (OtherPoint.GetX() <= SPoint1.GetX())
                    return false;
                else if (OtherPoint.GetX() >= SPoint2.GetX())
                    return false;
            }
            else
            {
                if (OtherPoint.GetX() <= SPoint2.GetX())
                    return false;
                else if (OtherPoint.GetX() >= SPoint1.GetX())
                    return false;
            }

            // other point is between the 2 Y points
            if (p1YisMinium)
            {
                if (OtherPoint.GetY() <= SPoint1.GetY() && p1YisMinium)
                    return false;
                else if (OtherPoint.GetY() >= SPoint2.GetY() && p1YisMinium)
                    return false;
            }
            else
            {
                if (OtherPoint.GetY() <= SPoint2.GetY())
                    return false;
                else if (OtherPoint.GetY() >= SPoint1.GetY())
                    return false;
            }

                return true;
        }

        /// <summary>
        /// Detects a collision between a point and a rectangle
        /// </summary>
        /// <param name="SquareCenter"></param>
        /// <param name="width"></param>
        /// <param name="OtherPoint"></param>
        /// <returns></returns>
        public static bool CollisionDetected (Vector2D SquareCenter, int width, Vector2D OtherPoint)
        {
            width = width / 2;
            Vector2D SPoint1 = new Vector2D(SquareCenter.GetX() - width,SquareCenter.GetY() - width);
            Vector2D SPoint2= new Vector2D(SquareCenter.GetX() + width, SquareCenter.GetY() + width);
            return CollisionDetected(SPoint1,SPoint2, OtherPoint);
        }

        /// <summary>
        /// returns true if the wall and tank overlap
        /// </summary>
        /// <param name="tankCenter"></param>
        /// <param name="tankWidth"></param>
        /// <param name="WallWidth"></param>
        /// <param name="WallStartPoint"></param>
        /// <param name="WallEndPoint"></param>
        /// <returns></returns>
        public static bool WallAndTankCollision(Vector2D tankCenter, int tankWidth, int WallWidth, Vector2D WallStartPoint, Vector2D WallEndPoint)
        {
            WallWidth = WallWidth / 2;
            tankWidth = tankWidth / 2;
            int totalDistance = WallWidth + tankWidth;
            int XbetweenWalls = (int)Math.Abs(WallStartPoint.GetX() - WallEndPoint.GetX());
            int YbetweenWalls = (int)Math.Abs(WallStartPoint.GetY() - WallEndPoint.GetY());

            bool betweenX = XbetweenWalls == (Math.Abs(tankCenter.GetX() - WallStartPoint.GetX()) + Math.Abs(tankCenter.GetX() - WallEndPoint.GetX()));
            bool betweenY = YbetweenWalls == (Math.Abs(tankCenter.GetY() - WallStartPoint.GetY()) + Math.Abs(tankCenter.GetY() - WallEndPoint.GetY()));
            if(betweenX)
            {

            }
            else if  (Math.Abs(tankCenter.GetX() - WallStartPoint.GetX()) >= totalDistance && Math.Abs(tankCenter.GetX() - WallEndPoint.GetX()) >= totalDistance)
            {
                return false;
            }

            if(betweenY)
            {

            }
            else if (Math.Abs(tankCenter.GetY() - WallStartPoint.GetY()) >= totalDistance && Math.Abs(tankCenter.GetY() - WallEndPoint.GetY()) >= totalDistance)
            {
                return false;
            }
            return true;
        }

        /// <summary>
        /// Determines if a ray interescts a circle
        /// </summary>
        /// <param name="rayOrig">The origin of the ray</param>
        /// <param name="rayDir">The direction of the ray</param>
        /// <param name="center">The center of the circle</param>
        /// <param name="r">The radius of the circle</param>
        /// <returns></returns>
        public static bool Intersects(Vector2D rayOrig, Vector2D rayDir, Vector2D center, double r)
        {
            // ray-circle intersection test
            // P: hit point
            // ray: P = O + tV
            // circle: (P-C)dot(P-C)-r^2 = 0
            // substitute to solve for t gives a quadratic equation:
            // a = VdotV
            // b = 2(O-C)dotV
            // c = (O-C)dot(O-C)-r^2
            // if the discriminant is negative, miss (no solution for P)
            // otherwise, if both roots are positive, hit

            double a = rayDir.Dot(rayDir);
            double b = ((rayOrig - center) * 2.0).Dot(rayDir);
            double c = (rayOrig - center).Dot(rayOrig - center) - r * r;

            // discriminant
            double disc = b * b - 4.0 * a * c;

            if (disc < 0.0)
                return false;

            // find the signs of the roots
            // technically we should also divide by 2a
            // but all we care about is the sign, not the magnitude
            double root1 = -b + Math.Sqrt(disc);
            double root2 = -b - Math.Sqrt(disc);

            return (root1 > 0.0 && root2 > 0.0);
        }

    }
}
