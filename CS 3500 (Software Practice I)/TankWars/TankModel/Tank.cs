// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace TankModel
{
    [JsonObject(MemberSerialization.OptIn)]
    public class Tank : GameObject
    {
        /// <summary>
        /// ID of Tank given by Json
        /// </summary>
        [JsonProperty(Order = 1, PropertyName = "tank")]
        public int ID { get; private set; }
        /// <summary>
        /// name of tank given by Json
        /// </summary>
        [JsonProperty(PropertyName = "name")]
        private string name;
        /// <summary>
        /// location of tank given by Json
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;
        /// <summary>
        /// direction the body of tank faces given by Json
        /// </summary>
        [JsonProperty(PropertyName = "bdir")]
        private Vector2D orientation;
        /// <summary>
        /// direction the turret of tank faces given by Json
        /// </summary>
        [JsonProperty(PropertyName = "tdir")]
        private Vector2D aiming = new Vector2D(0, -1);
        /// <summary>
        /// score of tank given by Json
        /// </summary>
        [JsonProperty(PropertyName = "score")]
        private int score = 0;
        /// <summary>
        /// health of tank given by Json
        /// </summary>
        [JsonProperty(PropertyName = "hp")]
        private int hitPoints = Constants.MaxHP;
        /// <summary>
        /// deathstatus of tank given by Json
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool died = false;
        /// <summary>
        /// if the tank disconnected given by Json
        /// </summary>
        [JsonProperty(PropertyName = "dc")]
        private bool disconnected = false;
        /// <summary>
        /// if the tank joined this turn given by Json
        /// </summary>
        [JsonProperty(PropertyName = "join")]
        private bool joined = false;

        /// <summary>
        /// Determines the number of frames tank has been dead for
        /// </summary>
        private int framesSinceDeath = 0;
        /// <summary>
        /// frames since this tank last shot
        /// </summary>
        private int framesSinceShot = 0;
        /// <summary>
        /// How many powerups the tank currently haas
        /// </summary>
        public int powerupsStored { get; private set; }
        /// <summary>
        /// How many shots fired this current game
        /// </summary>
        public uint shotsFired { get; private set; }
        /// <summary>
        /// How many shots hit this game
        /// </summary>
        public uint shotsHit { get; private set; }
        /// <summary>
        /// Calculates accuracy this game
        /// </summary>
        public uint accuracy
        {
            get
            {
                return (uint)(((double)shotsHit / (double)shotsFired) * 100);
            }
        }

        public Tank()
        {
            powerupsStored = 0;
            shotsFired = 0;
        }
        /// <summary>
        /// creates a new tank when player joins
        /// </summary>
        /// <param name="id"></param>
        /// <param name="name"></param>
        /// <param name="location"></param>
        public Tank(int id, string name, Vector2D location, int maxHP) : this()
        {

            ID = id;
            this.name = name;
            this.location = location;
            orientation = new Vector2D(0, -1);
            joined = true;
            hitPoints = maxHP;
        }
        /// <summary>
        /// returns orientation of body
        /// </summary>
        /// <returns></returns>
        public double GetAngle()
        {
            return orientation.ToAngle();
        }
        /// <summary>
        /// returns turret direction
        /// </summary>
        /// <returns></returns>
        public Vector2D GetTurretDirection()
        {
            return aiming;
        }
        /// <summary>
        /// returns orientation of turret
        /// </summary>
        /// <returns></returns>
        public double GetTurretAngle()
        {
            return aiming.ToAngle();
        }
        /// <summary>
        /// returns name of tank
        /// </summary>
        /// <returns></returns>
        public string GetName()
        {
            return name;
        }
        /// <summary>
        /// returns hitpoints of the tank
        /// </summary>
        /// <returns></returns>
        public int GetHP()
        {
            return hitPoints;
        }
        /// <summary>
        /// returns score of tank
        /// </summary>
        /// <returns></returns>
        public int GetScore()
        {
            return score;
        }
        /// <summary>
        /// returns deathstatus
        /// </summary>
        /// <returns></returns>
        public override bool GetDeathStatus()
        {
            return died;
        }

        /// <summary>
        /// Sets the death status to false
        /// </summary>
        public void SetDiedToFalse()
        {
            died = false;
        }

        /// <summary>
        /// returns ID of tank
        /// </summary>
        /// <returns></returns>
        public override int GetID()
        {
            return ID;
        }
        /// <summary>
        /// returns location of Tank
        /// </summary>
        /// <returns></returns>
        public override Vector2D GetLocation()
        {
            return location;
        }
        /// <summary>
        /// returns the orientation of tank
        /// </summary>
        /// <returns></returns>
        public Vector2D GetOrientation()
        {
            return orientation;
        }

        /// <summary>
        /// decreases the hp when hit by a projectilen or beam
        /// returns true if this tank is killed
        /// </summary>
        public bool hit(int damage)
        {
            hitPoints -= damage;
            if (hitPoints <= 0)
            {
                hitPoints = 0;
                died = true;
            }
            return died;
        }
        /// <summary>
        /// increases score when a projectile or
        /// </summary>
        public void scoreIncrease()
        {
            score++;
        }

        /// <summary>
        /// Respawns the tank
        /// </summary>
        public void Respawn(int maxHitpoints, Vector2D location)
        {
            this.hitPoints = maxHitpoints;
            this.location = location;
            this.aiming = new Vector2D(0, -1);
            this.orientation = new Vector2D(0, -1);
            framesSinceDeath = 0;
        }

        /// <summary>
        /// Determines if the tank needs to be respawned
        /// </summary>
        /// <param name="respawnRate"></param>
        /// <returns></returns>
        public bool RespawnTimer(int respawnRate)
        {
            framesSinceDeath++;
            return framesSinceDeath >= respawnRate;
        }

        /// <summary>
        /// moves tank in the indicated direction and will wrap around the world if drives over world
        /// barrier
        /// </summary>
        /// <param name="direction"></param>
        /// <param name="tankspeed"></param>
        /// <param name="worldsize"></param>
        public void move(string direction, double tankspeed, int worldsize)
        {
            if (hitPoints <= 0)
                return;

            Vector2D directionVec = directionVector(direction);

            location = location + (directionVec * tankspeed);
            if (directionVec.GetX() != 0 || directionVec.GetY() != 0)
                orientation = directionVec;

            if (Math.Abs(location.GetX()) > worldsize / 2)
            {
                // loops to other side of world
                location = new Vector2D(directionVec.GetX() + (location.GetX() * -1), location.GetY());
            }
            if (Math.Abs(location.GetY()) > worldsize / 2)
            {
                // loops to other side of world
                location = new Vector2D(location.GetX(), directionVec.GetY() + (location.GetY() * -1));
            }

        }

        /// <summary>
        /// Overrides move but allows a tank to be facing a new direction after it moves
        /// </summary>
        /// <param name="direction"></param>
        /// <param name="tankspeed"></param>
        /// <param name="worldsize"></param>
        /// <param name="facing"></param>
        public void move(string direction, double tankspeed, int worldsize, Vector2D facing)
        {
            move(direction, tankspeed, worldsize);
            orientation = facing;
        }

        /// <summary>
        /// Changes where the turret aims
        /// </summary>
        /// <param name="direction"></param>
        public void AimTurret(Vector2D direction)
        {
            aiming = direction;
        }

        /// <summary>
        /// returns the vector direction the tank is being moved
        /// </summary>
        /// <param name="direction"></param>
        /// <returns></returns>
        private Vector2D directionVector(string direction)
        {
            if (direction.Equals("left"))
            {
                return new Vector2D(-1, 0);
            }
            else if (direction.Equals("right"))
            {
                return new Vector2D(1, 0);
            }
            else if (direction.Equals("up"))
            {
                return new Vector2D(0, -1);
            }
            else if (direction.Equals("down"))
            {
                return new Vector2D(0, 1);
            }
            else
            {
                return new Vector2D(0, 0);
            }
        }

        /// <summary>
        /// if a shot was fired and needs to cooldown before the next shot
        /// </summary>
        private bool shot = false;
        /// <summary>
        /// returns true if the tank can fire the main cannon
        /// </summary>
        /// <param name="framesPerShot"></param>
        /// <returns></returns>
        public bool CanShoot(int framesPerShot)
        {
            if (!shot)
            {
                return true;
            }
            framesSinceShot++;
            shot = framesSinceShot <= framesPerShot;
            return !shot;
        }

        /// <summary>
        /// says that the main cannon was fired
        /// </summary>
        public void FireCannon()
        {
            shot = true;
            framesSinceShot = 0;
            shotsFired++;
        }

        /// <summary>
        /// Increments shots tanks have hit
        /// </summary>
        public void hitEnemyTank()
        {
            shotsHit++;
        }

        /// <summary>
        /// adds Powerup that the tank can use to fire the beam
        /// will not add past the max number of powerups
        /// returns true if a powerup was added
        /// </summary>
        public bool addPowerUp(int maxPowerUps)
        {
            if (powerupsStored < maxPowerUps)
            {
                powerupsStored++;
                return true;
            }
            else
            {
                return false;
            }

        }

        /// <summary>
        /// returns true if the tank can shoot a beam and will
        /// decrement the amount of powerups that has been stored
        /// </summary>
        /// <returns></returns>
        public bool shootBeam()
        {
            if (powerupsStored > 0)
            {
                powerupsStored--;
                return true;
            }
            else
                return false;
        }
    }
}
