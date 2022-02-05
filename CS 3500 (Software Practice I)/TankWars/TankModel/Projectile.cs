// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    [JsonObject(MemberSerialization.OptIn)]
    public class Projectile : GameObject
    {
        /// <summary>
        /// Id of Projectile given by Json
        /// </summary>
        [JsonProperty(PropertyName = "proj")]
        private readonly int ID;
        /// <summary>
        /// location of Projectile given by Json
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;
        /// <summary>
        /// direction of Projectile given by Json
        /// </summary>
        [JsonProperty(PropertyName = "dir")]
        private Vector2D direction;
        /// <summary>
        /// death status of Projectile given by Json
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool died = false;
        /// <summary>
        /// ID of tank that fired the Projectile
        /// </summary>
        [JsonProperty(PropertyName = "owner")]
        private int owner;


        public Projectile()
        {

        }
        /// <summary>
        /// Allows projectiles to be initialized at a specific spot
        /// </summary>
        /// <param name="id"></param>
        /// <param name="location"></param>
        /// <param name="direction"></param>
        /// <param name="OwnerID"></param>
        public Projectile(int id, Vector2D location, Vector2D direction, int OwnerID)
        {
            this.ID = id;
            this.location = location;
            this.direction = direction;
            this.owner = OwnerID;
        }

        /// <summary>
        /// returns ID of Projectile
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return ID;
        }

        /// <summary>
        /// returns location of Projectile
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return location;
        }

        /// <summary>
        /// returns ID of tank that fired Projectile
        /// </summary>
        /// <returns></returns>
        public int GetOwner()
        {
            return owner;
        }

        /// <summary>
        /// returns angle of Projectile
        /// </summary>
        /// <returns></returns>
        public double GetAngle()
        {
            return direction.ToAngle();
        }

        /// <summary>
        /// returns deathstatus of Projectile
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
            return died;
        }

        /// <summary>
        /// Moves a projectile
        /// </summary>
        /// <param name="speed"></param>
        /// <param name="worldsize"></param>
        public void Move(double speed, int worldsize)
        {
          location +=  direction * speed;
            if(Math.Abs(location.GetX())>worldsize || Math.Abs(location.GetY()) > worldsize)
            {
                died = true;
            }
        }
        
        /// <summary>
        /// Sets died to true
        /// </summary>
        public void Collision()
        {
            died = true;
        }

      
    }
}
