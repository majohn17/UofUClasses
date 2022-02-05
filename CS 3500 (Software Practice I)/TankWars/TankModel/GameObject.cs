// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    public abstract class GameObject
    {
        /// <summary>
        /// returns the ID of the GameObject
        /// </summary>
        /// <returns></returns>
        abstract public int GetID();
        /// <summary>
        /// returns the location of the GameObject
        /// </summary>
        /// <returns></returns>
        abstract public Vector2D GetLocation();

        /// <summary>
        /// returns the X location of the game object
        /// </summary>
        /// <returns></returns>
        public double GetX()
        {
            return GetLocation().GetX();
        }

        /// <summary>
        /// returns the Y location of the game object
        /// </summary>
        /// <returns></returns>
        public double GetY()
        {
            return GetLocation().GetY();
        }

        /// <summary>
        /// returns the GameObjects death status
        /// </summary>
        /// <returns></returns>
        abstract public bool GetDeathStatus();
    }
}
