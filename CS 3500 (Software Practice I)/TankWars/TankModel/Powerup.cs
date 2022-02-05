// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    public class Powerup : GameObject
    {
        /// <summary>
        /// ID of powerup given by Json
        /// </summary>
        [JsonProperty(PropertyName = "power")]
        private readonly int ID;
        /// <summary>
        /// location of powerup given by Json
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;
        /// <summary>
        /// death status given by Json
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool died = false;

        public Powerup()
        {

        }

        /// <summary>
        /// Creates a powerup with a id and location
        /// </summary>
        public Powerup(int id, Vector2D location)
        {
            this.ID = id;
            this.location = location;
        }

        /// <summary>
        /// returns ID of powerup
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return ID;
        }
        /// <summary>
        /// returns location of powerup
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return location;
        }
        /// <summary>
        /// returns death status of powerup
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
            return died;
        }

        /// <summary>
        /// sets death to true
        /// </summary>
        public void pickup()
        {
            died = true;
        }
    }
}
