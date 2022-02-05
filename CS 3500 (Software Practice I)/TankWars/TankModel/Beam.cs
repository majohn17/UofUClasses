// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    [JsonObject(MemberSerialization.OptIn)]
    public class Beam : GameObject
    {
        /// <summary>
        /// ID of beam given by Json
        /// </summary>
        [JsonProperty(PropertyName = "beam")]
        private readonly int ID;
        /// <summary>
        /// where the beam origininates given by Json
        /// </summary>
        [JsonProperty(PropertyName = "org")]
        private Vector2D origin;
        /// <summary>
        /// Direction of beam given by Json
        /// </summary>
        [JsonProperty(PropertyName = "dir")]
        private Vector2D direction;
        /// <summary>
        /// ID of Tank that fired this beam given by Json
        /// </summary>
        [JsonProperty(PropertyName = "owner")]
        private int owner;
        /// <summary>
        /// Used to remove beams after a certain number of frames
        /// </summary>
        private int timesDrawn = 0;

        public Beam()
        {

        }

        /// <summary>
        /// Allows a beam to be initialized through given parameters
        /// </summary>
        /// <param name="id"></param>
        /// <param name="origin"></param>
        /// <param name="direction"></param>
        /// <param name="OwnerId"></param>
        public Beam(int id, Vector2D origin, Vector2D direction, int OwnerId)
        {
            this.ID = id;
            this.origin = origin;
            this.direction = direction;
            this.owner = OwnerId;
        }

        /// <summary>
        /// returns ID of tank that fired the beam
        /// </summary>
        /// <returns></returns>
        public int GetOwner()
        {
            return owner;
        }

        /// <summary>
        /// returns if the beam needs to be deleted
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
            return timesDrawn >= 50;
        }

        /// <summary>
        /// returns ID of Beam
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return ID;
        }

        /// <summary>
        /// reports if the beam should be removed.
        /// should be called everytime drawn.
        /// </summary>
        /// <returns></returns>
        public bool Remove()
        {
            timesDrawn++;
            return timesDrawn >= 50;
        }

        /// <summary>
        /// gives direction Beam travels 
        /// </summary>
        /// <returns></returns>
        public Vector2D GetDirection()
        {
            return direction;
        }

        /// <summary>
        /// Returns origin of the beam
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return origin;
        }
    }
}
