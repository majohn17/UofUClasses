// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    public class Explosion : GameObject
    {
        private int id;

        public Vector2D pos { get; private set; }

        private int timesDrawn = 0;

        /// <summary>
        /// returns if the Explosion has been drawn for long enough
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
           return timesDrawn >= 50;
        }
        /// <summary>
        /// creates a new explosion with the same ID as a tank and same position
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="position"></param>
        public Explosion(int ID, Vector2D position)
        {
            id = ID;
            pos = position;
        }
        /// <summary>
        /// returns ID of explosion
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return id;
        }
        /// <summary>
        /// returns location of Explosion
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return pos;
        }

        /// <summary>
        /// returns if the explosion should stop being drawn
        /// </summary>
        /// <returns></returns>
        public bool Remove()
        {
            timesDrawn++;
            return timesDrawn >= 50;

        }
    }
}
