using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TankWars;
using Server;
using System.Collections.Generic;

namespace CollisionTesting
{
    [TestClass]
    public class CollisionTests
    {
        [TestMethod]
        public void TestCollisionwithTwoRectanglePoints()
        {
            Vector2D p1 = new Vector2D(0, 0);
            Vector2D p2 = new Vector2D(5, 5);
            Vector2D other = new Vector2D(1, 1);

            Assert.IsTrue(Collisions.CollisionDetected(p1, p2, other));
            Assert.IsTrue(Collisions.CollisionDetected(p2, p1, other));

            // outside of square
            other = new Vector2D(10, 10);
            Assert.IsFalse(Collisions.CollisionDetected(p1, p2, other));
            Assert.IsFalse(Collisions.CollisionDetected(p2, p1, other));

            p1 = new Vector2D(0, 5);
            p2 = new Vector2D(5, 0);
            other = new Vector2D(1, 1);
            Assert.IsTrue(Collisions.CollisionDetected(p1, p2, other));
            Assert.IsTrue(Collisions.CollisionDetected(p2, p1, other));

            // outside of square
            other = new Vector2D(10, 10);
            Assert.IsFalse(Collisions.CollisionDetected(p1, p2, other));
            Assert.IsFalse(Collisions.CollisionDetected(p2, p1, other));
        }

        [TestMethod]
        public void TestCollisionwithOneRectanglePointAndSize()
        {
            int width = 15;
            Vector2D p1 = new Vector2D(0, 0);

            Vector2D other = new Vector2D(1, 1);
            Assert.IsTrue(Collisions.CollisionDetected(p1, width, other));

            other = new Vector2D(-5, -5);
            Assert.IsTrue(Collisions.CollisionDetected(p1, width, other));


            // outside of square
            other = new Vector2D(10, 10);
            Assert.IsFalse(Collisions.CollisionDetected(p1, width, other));

        }
        [TestMethod]
        public void TestWallTankCollision()
        {
            //testing method tank width
            int tankWidth = 50;
            int wallWidth = 50;

            Vector2D Wall1 = new Vector2D(100, 0);
            Vector2D Wall2 = new Vector2D(-100, 50);

            Vector2D tankCenter = new Vector2D(50, 50);
            Assert.IsTrue(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(75, 99);
            Assert.IsTrue(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(-100, 100);
            Assert.IsFalse(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(75, 125);
            Assert.IsFalse(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(2000, 30);
            Assert.IsFalse(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(75, -1000);
            Assert.IsFalse(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));

            tankCenter = new Vector2D(-2000, -1000);
            Assert.IsFalse(Collisions.WallAndTankCollision(tankCenter, tankWidth, wallWidth, Wall1, Wall2));
        }
        [TestMethod]
        public void DataBaseGatherPlayers()
        {

           Dictionary<uint,GameModel> games =DataBaseHandling.AllGames();
            Assert.AreEqual(2,games.Count);
        }

    }
}
        

