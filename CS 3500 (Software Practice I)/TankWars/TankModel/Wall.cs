// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    [JsonObject(MemberSerialization.OptIn)]
    public class Wall : GameObject
    {
        /// <summary>
        /// ID of wall given by JSon
        /// </summary>
        [JsonProperty(PropertyName = "wall")]
        private int ID;
        /// <summary>
        /// first point of wall given by JSon
        /// </summary>
        [JsonProperty(PropertyName = "p1")]
        private Vector2D p1;
        /// <summary>
        /// second point of wall given by JSon
        /// </summary>
        [JsonProperty(PropertyName = "p2")]
        private Vector2D p2;

        public Wall()
        {

        }
        /// <summary>
        /// returns death status
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
            return false;
        }
        /// <summary>
        /// returns ID of wall
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return ID;
        }
        /// <summary>
        /// returns first point of wall
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return p1;
        }
        /// <summary>
        /// returns second point of wall
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation2()
        {
            return p2;
        }
        /// <summary>
        /// returns X of second point of wall
        /// </summary>
        /// <returns></returns>
        public double GetX2()
        {
            return p2.GetX();
        }
        /// <summary>
        /// returns Y of second point of wall
        /// </summary>
        /// <returns></returns>
        public double GetY2()
        {
            return p2.GetY();
        }
    }
}
