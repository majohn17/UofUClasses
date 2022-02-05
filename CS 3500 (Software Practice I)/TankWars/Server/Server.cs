// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;
using NetworkUtil;
using Newtonsoft.Json;
using TankModel;
using TankWars;

namespace Server
{
    class Server
    {
        /// <summary>
        /// Runs when the server is started
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            // Starts the servers
            Server server = new Server();
            WebServer scoreServer = new WebServer();
            server.StartServer();
            Stopwatch gameDuration = Stopwatch.StartNew();
            scoreServer.StartServer();

            // Starts the frame loop
            Thread update = new Thread(new ThreadStart(() => server.UpdateLoop()));
            update.Start();

            // Sends the highscores to the HTTP server
            Console.Read();
            gameDuration.Stop();
            DataBaseHandling.SendGameToDatabase(server.theWorld, (int)(gameDuration.ElapsedMilliseconds / 1000));
            Environment.Exit(0);
        }
        /// <summary>
        /// The world that holds all the game objects
        /// </summary>
        private World theWorld;
        /// <summary>
        /// Retrieves the settings for the server
        /// </summary>
        public SettingsReader settings { get; private set; }
        /// <summary>
        /// A set of all clients that are connected
        /// </summary>
        private Dictionary<long, SocketState> clients;
        /// <summary>
        /// Stores the command requests sent by the clinets
        /// </summary>
        private Dictionary<long, Command> clientCommands;
        /// <summary>
        /// Used for unique id's of each shot and beam
        /// </summary>
        private int shotID = 0;
        /// <summary>
        /// Used for unique id's of beams
        /// </summary>
        private int powerupID = 0;
        /// <summary>
        /// Frames since a powerup has spawned
        /// </summary>
        private int framesSincePowerup = 0;
        /// <summary>
        /// Total number of powerups in the world
        /// </summary>
        private int powerupCount = 0;

        public Server()
        {
            theWorld = new World();
            settings = new SettingsReader();
            clients = new Dictionary<long, SocketState>();
            clientCommands = new Dictionary<long, Command>();
            addWalls();
        }

        /// <summary>
        /// Used to start the infinite frame update loop on a different thread
        /// </summary>
        private void UpdateLoop()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            while (true)
            {
                if (stopwatch.ElapsedMilliseconds > settings.MSPerFrame)
                {
                    if (clients.Count() > 0)
                    {
                        string update = UpdateWorld();

                        SendMessages(update);
                    }
                    stopwatch.Restart();
                }
            }
        }

        /// <summary>
        /// Sends the updated world to all the clients
        /// </summary>
        private void SendMessages(string message)
        {
            lock (clients)
            {
                foreach (SocketState ss in clients.Values)
                {
                    if (theWorld.Tanks.ContainsKey((int)ss.ID))
                        Networking.Send(ss.TheSocket, message);
                }
            }
        }

        /// <summary>
        /// Start accepting Tcp sockets connections from clients
        /// </summary>
        public void StartServer()
        {
            Networking.StartServer(HandleGameClient, 11000);
            Console.WriteLine("Server is running. Accepting clients.");
        }

        /// <summary>
        /// Deals with intial process of a game client connecting
        /// </summary>
        /// <param name="state"></param>
        private void HandleGameClient(SocketState state)
        {
            if (state.ErrorOccured)
                return;

            // Save the client state
            // Need to lock here because clients can disconnect at any time
            lock (clients)
            {
                clients[state.ID] = state;
            }

            state.OnNetworkAction = SendInitial;
            Networking.GetData(state);
        }

        /// <summary>
        /// Deals with sending each client the initial information
        /// </summary>
        /// <param name="state"></param>
        private void SendInitial(SocketState state)
        {
            if (state.ErrorOccured)
            {
                RemoveClient(state.ID);
                return;
            }

            // Sends the ID and World Size
            Networking.Send(state.TheSocket, state.ID + "\n" + settings.worldSize + "\n");
            StringBuilder walls = new StringBuilder();

            // Sends the walls
            foreach (String s in settings.walls)
            {
                walls.Append(s);
                walls.Append("\n");
            }
            Networking.Send(state.TheSocket, walls.ToString());

            // Adds a tank to the world
            string data = state.GetData();
            string playerName = data.Substring(0, data.Length - 1);
            theWorld.Tanks[(int)state.ID] = CreateTank((int)state.ID, playerName, GetValidLocation(settings.TankSize));

            // Continues the event loop
            state.OnNetworkAction = ReceiveMessage;
            Networking.GetData(state);
        }

        private void ReceiveMessage(SocketState state)
        {
            if (state.ErrorOccured)
            {
                RemoveClient(state.ID);
                lock (theWorld)
                {
                    theWorld.Tanks.Remove((int)state.ID);
                }
                return;
            }

            ProcessMessage(state);
            //Continue the event loop that receives messages from this client
            state.OnNetworkAction = ReceiveMessage;
            Networking.GetData(state);
        }

        /// <summary>
        /// Given the data that has arrived so far, 
        /// potentially from multiple receive operations, 
        /// determine if we have enough to make a complete message,
        /// and process it (print it and broadcast it to other clients).
        /// </summary>
        /// <param name="sender">The SocketState that represents the client</param>
        private void ProcessMessage(SocketState state)
        {
            string[] messages = state.GetData().Split('\n');
            lock (clientCommands)
            {
                clientCommands[state.ID] = JsonConvert.DeserializeObject<Command>(messages[messages.Length - 2]);
            }
        }

        /// <summary>
        /// Used to remove a client from the dictionary of clients that are connected
        /// </summary>
        /// <param name="id"></param>
        private void RemoveClient(long id)
        {
            Console.WriteLine("Client " + id + " disconnected");
            lock (clients)
            {
                clients.Remove(id);
            }
        }

        /// <summary>
        /// adds walls from the settings to the world
        /// </summary>
        private void addWalls()
        {
            foreach (String wall in settings.walls)
            {
                theWorld.insert(JsonConvert.DeserializeObject<Wall>(wall));
            }
            return;
        }

        /// <summary>
        /// Finds a valid random location
        /// </summary>
        /// <returns></returns>
        private Vector2D GetValidLocation(int size)
        {
            bool valid = false;
            Random rand = new Random();
            Vector2D location = new Vector2D();
            // Checks through all the collisions to determine if the randomly generated location is not resulting in a collision
            while (!valid)
            {
                bool temp = false;
                double x = rand.Next(settings.worldSize) - (settings.worldSize / 2);
                double y = rand.Next(settings.worldSize) - (settings.worldSize / 2);
                location = new Vector2D(x, y);

                foreach (Wall w in theWorld.Walls.Values)
                {
                    if (Collisions.WallAndTankCollision(location, size, settings.WallSize, w.GetLocation(), w.GetLocation2()))
                    {
                        temp = true;
                        break;
                    }
                }
                if (temp)
                    continue;

                foreach (Projectile p in theWorld.Projectiles.Values)
                {
                    if (Collisions.CollisionDetected(location, size, p.GetLocation()))
                    {
                        temp = true;
                        break;
                    }
                }
                if (temp)
                    continue;

                foreach (Tank t in theWorld.Tanks.Values)
                {
                    if (Collisions.WallAndTankCollision(location, size, settings.TankSize, t.GetLocation(), t.GetLocation()))
                    {
                        temp = true;
                        break;
                    }
                }
                if (temp)
                    continue;
                valid = true;
            }
            return location;
        }

        /// <summary>
        /// moves the projectile
        /// returns if the projectile moved without collision
        /// </summary>
        /// <param name="projectile"></param>
        /// <returns></returns>
        private bool projectileMove(Projectile projectile)
        {
            projectile.Move(settings.ProjectileSpeed, settings.worldSize);
            foreach (Tank t in theWorld.Tanks.Values)
            {
                if (t.ID == projectile.GetOwner() || t.GetHP() < 1)
                    continue;

                if (Collisions.CollisionDetected(t.GetLocation(), settings.TankSize, projectile.GetLocation()))
                {
                    Tank owner = theWorld.Tanks[projectile.GetOwner()];
                    if (t.hit(1))
                    {
                        owner.scoreIncrease();

                    }
                    projectile.Collision();
                    owner.hitEnemyTank();
                    return false;
                }
            }
            foreach (Wall w in theWorld.Walls.Values)
            {
                Vector2D p1 = new Vector2D(w.GetX() - 25, w.GetY() - 25);
                Vector2D p2 = new Vector2D(w.GetX2() + 25, w.GetY2() + 25);
                if (Collisions.CollisionDetected(p1, p2, projectile.GetLocation()))
                {
                    projectile.Collision();
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// Deals with beams firing and their collisions
        /// </summary>
        /// <param name="beam"></param>
        private void beamFired(Beam beam)
        {
            foreach (Tank t in theWorld.Tanks.Values)
            {
                if (t.ID == beam.GetOwner() || t.GetDeathStatus())
                    continue;

                if (Collisions.Intersects(beam.GetLocation(), beam.GetDirection(), t.GetLocation(), 30))
                {
                    Tank owner = theWorld.Tanks[beam.GetOwner()];
                    owner.hitEnemyTank();
                    if (t.hit(3))
                    {
                        owner.scoreIncrease();
                    }
                }
            }
        }

        /// <summary>
        /// moves a tank in the specified direction if it does not collide with a wall.
        /// Wraps around the world if the tank reaches the edge.
        /// returns false if the tank collides with a wall and will not move.
        /// </summary>
        /// <param name="tank"></param>
        /// <param name="direction"></param>
        /// <returns></returns>
        private bool tankMove(Tank tank, string direction)
        {
            tank.move(direction, settings.TankSpeed, settings.worldSize);

            // Checks for tank-wall collisions
            foreach (Wall w in theWorld.Walls.Values)
            {
                if (Collisions.WallAndTankCollision(tank.GetLocation(), settings.TankSize, settings.WallSize, w.GetLocation(), w.GetLocation2()))
                {
                    tank.move(OppositeDirection(direction), settings.TankSpeed, settings.worldSize, tank.GetOrientation());

                    return false;
                }
            }

            // Checks for tank-powerup collisions
            foreach (Powerup p in theWorld.Powerups.Values)
            {
                if (Collisions.CollisionDetected(tank.GetLocation(), settings.TankSize, p.GetLocation()))
                {
                    tank.addPowerUp(settings.MaxPowerups);
                    p.pickup();
                    powerupCount--;
                }
            }

            return true;
        }

        /// <summary>
        /// returns the opposite direction to move:
        /// [left:right:up:down]
        /// returns "none" if there is not an opposite
        /// </summary>
        /// <param name="direction"></param>
        /// <returns></returns>
        private string OppositeDirection(string direction)
        {
            if (direction == "left")
            {
                return "right";
            }
            if (direction == "right")
            {
                return "left";
            }
            if (direction == "up")
            {
                return "down";
            }
            if (direction == "down")
            {
                return "up";
            }
            return "none";
        }

        /// <summary>
        /// Creates a tank with a specified name, id, and location
        /// </summary>
        /// <param name="id"></param>
        /// <param name="name"></param>
        /// <param name="location"></param>
        /// <returns></returns>
        private Tank CreateTank(int id, string name, Vector2D location) => new Tank(id, name, location, settings.MaxHitPoints);

        /// <summary>
        /// Add's a powerup to the server
        /// </summary>
        private void AddPowerup()
        {
            framesSincePowerup++;
            if (framesSincePowerup >= settings.MaxPowerupDelay)
            {
                // Limits server to 5 powerups
                if (powerupCount < 5)
                {
                    lock (theWorld)
                    {
                        theWorld.Powerups.Add(powerupID, new Powerup(powerupID, GetValidLocation(settings.PowerupSize)));
                    }
                    powerupID++;
                    powerupCount++;
                }
                framesSincePowerup = 0;
            }
        }

        /// <summary>
        /// Updates the movement of objects from commands
        /// </summary>
        /// <returns></returns>
        private string UpdateWorld()
        {
            StringBuilder world = new StringBuilder();
            lock (clientCommands)
            {
                // Deals with command requests from clients
                foreach (int id in clientCommands.Keys)
                {
                    if (!theWorld.Tanks.ContainsKey(id))
                    {
                        continue;
                    }
                    Tank t = theWorld.Tanks[id];
                    bool canShoot = t.CanShoot(settings.FramesPerShot);
                    if (t.GetHP() < 1)
                    {
                        continue;
                    }
                    Command c = clientCommands[id];
                    tankMove(t, c.moving);
                    t.AimTurret(c.tdir);

                    if (c.fire == "alt" && t.shootBeam())
                    {

                        theWorld.insert(new Beam(shotID, t.GetLocation(), t.GetTurretDirection(), t.ID));
                        shotID++;
                    }
                    else if (canShoot && c.fire == "main")
                    {

                        theWorld.insert(new Projectile(shotID, t.GetLocation(), t.GetTurretDirection(), t.GetID()));
                        shotID++;
                        t.FireCannon();

                    }
                }
                clientCommands.Clear();
            }

            // Updates projectiles
            List<Projectile> remove = new List<Projectile>();
            foreach (Projectile p in theWorld.Projectiles.Values)
            {
                bool moved = projectileMove(p);
                if (!moved)
                {
                    p.Collision();
                }

                if (p.GetDeathStatus())
                {
                    remove.Add(p);
                }
                world.Append(JsonConvert.SerializeObject(p));
                world.Append("\n");

            }
            foreach (Projectile p in remove)
            {
                theWorld.Projectiles.Remove(p.GetID());
            }

            // Updates powerups
            AddPowerup();
            List<Powerup> removePowerups = new List<Powerup>();
            foreach (Powerup p in theWorld.Powerups.Values)
            {
                world.Append(JsonConvert.SerializeObject(p));
                world.Append("\n");
                if (p.GetDeathStatus())
                {
                    removePowerups.Add(p);
                }
            }
            foreach (Powerup p in removePowerups)
            {
                theWorld.Powerups.Remove(p.GetID());
            }

            // Updates beams
            foreach (Beam b in theWorld.Beams.Values)
            {
                beamFired(b);
                world.Append(JsonConvert.SerializeObject(b));
                world.Append("\n");
            }
            theWorld.Beams.Clear();

            // Updates tanks
            foreach (Tank t in theWorld.Tanks.Values)
            {
                if (t.GetHP() <= 0 && t.RespawnTimer(settings.RespawnRate))
                {
                    t.Respawn(settings.MaxHitPoints, GetValidLocation(settings.TankSize));
                }
                world.Append(JsonConvert.SerializeObject(t));
                world.Append("\n");
                if (t.GetDeathStatus() == true)
                    t.SetDiedToFalse();
            }
            return world.ToString();
        }

        /// <summary>
        /// Used to more easily serialize command requests
        /// </summary>
        [JsonObject(MemberSerialization.OptIn)]
        private class Command
        {
            [JsonProperty(PropertyName = "moving")]
            public string moving;

            [JsonProperty(PropertyName = "fire")]
            public string fire;

            [JsonProperty(PropertyName = "tdir")]
            public Vector2D tdir;
            public Command()
            {

            }
        }
    }
}
