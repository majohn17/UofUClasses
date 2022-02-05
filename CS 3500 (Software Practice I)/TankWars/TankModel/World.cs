// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Collections.Generic;
using System.Text;

namespace TankModel
{
    public class World
    {
        public Dictionary<int, Tank> Tanks { get; private set; }
        public Dictionary<int, Powerup> Powerups { get; private set; }
        public Dictionary<int, Wall> Walls { get; private set; }
        public Dictionary<int, Projectile> Projectiles { get; private set; }
        public Dictionary<int, Beam> Beams { get; private set; }
        public Dictionary<int, Explosion> Explosions { get; private set; }
        public int PlayerID { get; private set; }
        public int WorldSize { get; private set; }
        /// <summary>
        /// sets up world 
        /// </summary>
        public World()
        {
            Tanks = new Dictionary<int, Tank>();
            Powerups = new Dictionary<int, Powerup>();
            Walls = new Dictionary<int, Wall>();
            Projectiles = new Dictionary<int, Projectile>();
            Beams = new Dictionary<int, Beam>();
            Explosions = new Dictionary<int, Explosion>();
            PlayerID = -1;
        }

        /// <summary>
        /// Sets the intial world size and stores the current players ID
        /// </summary>
        /// <param name="size"></param>
        public void setWorld(int id, int size)
        {
            PlayerID = id;
            WorldSize = size;
        }
        /// <summary>
        /// creates explosion at location of tank with same ID as the tank
        /// </summary>
        /// <param name="tank"></param>
        public void tankDied(Tank tank)
        {
            Explosions[tank.GetID()] = new Explosion(tank.GetID(),tank.GetLocation());
        }

        /// <summary>
        /// inserts a Tank to the world
        /// </summary>
        /// <param name="obj"></param>
        public void insert(Tank obj)
        {
           
            Tanks[obj.GetID()] = obj;
        }

        /// <summary>
        /// inserts a Powerup to the world
        /// </summary>
        /// <param name="obj"></param>
        public void insert(Powerup obj)
        {
            if (obj.GetDeathStatus())
            {
                Powerups.Remove(obj.GetID());
                return;
            }
            Powerups[obj.GetID()] = obj;
        }

        /// <summary>
        /// inserts a Wall to the world
        /// </summary>
        /// <param name="obj"></param>
        public void insert(Wall obj)
        {
            Walls[obj.GetID()] = obj;
        }

        /// <summary>
        /// inserts a Beam to the world
        /// </summary>
        /// <param name="obj"></param>
        public void insert(Beam obj)
        {

            Beams[obj.GetID()] = obj;
        }

        /// <summary>
        /// inserts a Projectile to the world
        /// </summary>
        /// <param name="obj"></param>
        public void insert(Projectile obj)
        {
            if (obj.GetDeathStatus())
            {
                Projectiles.Remove(obj.GetID());
                return;
            }
            Projectiles[obj.GetID()] = obj;
        }
    }
}
