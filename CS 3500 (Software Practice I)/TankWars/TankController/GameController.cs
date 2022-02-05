// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using TankModel;
using NetworkUtil;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using TankWars;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Collections.Generic;

namespace TankController
{
    public class GameController
    {
       
        SocketState server { get; set; }
        public World theWorld { get; set; }
        private string playerName { get; set; }
        /// <summary>
        /// Allows retrying connection if connection fails
        /// </summary>
        private Action retryConnection { get; set; }

        public Vector2D turret { get; private set; }
        public string moving { get; private set; }
        public string firing { get; private set; }

        Dictionary<string, bool> direction;
       

        public delegate void ServerUpdateHandler();
        private event ServerUpdateHandler UpdateArrived;

        /// <summary>
        /// creates a GameController and World
        /// </summary>
        public GameController()
        {
            direction = new Dictionary<string, bool>();
            direction["up"]= false;
            direction["down"]= false;
            direction["left"]= false;
            direction["right"]= false;
            theWorld = new World();
            moving = "none";
            turret = new Vector2D(0, -1);
        }
        /// <summary>
        /// adds a method to be called when an update is recieved by the server
        /// </summary>
        /// <param name="h"></param>
        public void RegisterServerUpdateHandler(ServerUpdateHandler h)
        {
            UpdateArrived += h;
        }
        /// <summary>
        /// creates connection to server and starts handshake to receive server info
        /// </summary>
        /// <param name="playerName"></param>
        public void Start(String playerName, string connection, Action enableConnect)
        {
            this.retryConnection = enableConnect;
            if (playerName == "")
                this.playerName = "Player";
            else
                this.playerName = playerName;
            Networking.ConnectToServer(HandShake, connection, 11000);
        }

        /// <summary>
        /// used as a call back for the connectToServer.
        /// uses socketstate and sends the name of the player.
        /// </summary>
        /// <param name="state"></param>
        private void HandShake(SocketState state)
        {

            server = state;
            if (state.ErrorOccured)
            {
                MessageBox.Show("Error connecting to server.");
                retryConnection();
                return;
            }
            state.OnNetworkAction = setWorld;

            // Save the SocketState so we can use it to send messages


            Networking.Send(server.TheSocket, playerName + "\n");
            // Start an event loop to receive messages from the server


            Networking.GetData(state);

        }
        /// <summary>
        /// retreives player ID and world size durring handshake
        /// </summary>
        /// <param name="state"></param>
        private void setWorld(SocketState state)
        {
            if (state.ErrorOccured)
            {
                MessageBox.Show("Error while receiving. Please restart the client.");
                return;
            }
            String message = state.GetData();
            string[] messages = message.Split('\n');
            state.RemoveData(0, (messages[0].Length + messages[1].Length + 2));

            int.TryParse(messages[0], out int id);
            int.TryParse(messages[1], out int worldSize);
            theWorld.setWorld(id, worldSize);

            state.OnNetworkAction = paint;

            Networking.GetData(state);
        }
        /// <summary>
        /// sends command to server and processes the information from the server
        /// UpdateArrived event is triggered causeing interface to paint.
        /// </summary>
        /// <param name="state"></param>
        private void paint(SocketState state)
        {
            if (state.ErrorOccured)
            {
                MessageBox.Show("Error while receiving. Please restart the client.");
                return;
            }
            bool finish;
            lock (theWorld)
            {
                finish = DeserializeJSON(state);
            }
            SendCommand();
            if (finish == true)
                UpdateArrived();
            Networking.GetData(state);
        }
        /// <summary>
        /// sends command to server based on inputs
        /// </summary>
        private void SendCommand()
        {
            if (turret is null)
                return;
            directionToMove();
            Networking.Send(server.TheSocket, "{ \"moving\":\"" + moving + "\",\"fire\":\"" + firing + "\",\"tdir\":{ \"x\":" + turret.GetX() + ",\"y\":" + turret.GetY() + "} }\n");
            if(firing =="alt")
            {
                firing = "none";
            }

        }/// <summary>
        /// sets direction to move when sending data to server
        /// </summary>
        private void directionToMove()
        {
            if (direction["up"])
            {
                moving = "up";
            }
            else if (direction["down"])
            {
                moving = "down";

            }
            else if (direction["left"])
            {
                moving = "left";
            }
            else if (direction["right"])
            {
                moving = "right";
            }
            else
            {
                moving = "none";
            }
        }
        

        /// <summary>
        /// indicates which type of fire to use
        ///
        /// </summary>
        /// <param name="mainCannon"></param>
        public void fire(string cannon)
        {

            firing = cannon;

        }
        /// <summary>
        /// creates a vector angle based on the mouse position to be given to the server
        /// </summary>
        /// <param name="mouseX"></param>
        /// <param name="mouseY"></param>
        public void aim(int mouseX, int mouseY)
        {
            if (server is null || server.ErrorOccured)
                return;
            Vector2D vec = new Vector2D(mouseX, mouseY);
            vec.Normalize();
             turret = vec;
        }
        /// <summary>
        /// toggles direction that the tank has been requested to move
        /// </summary>
        /// <param name="direction"></param>
        /// <param name="toggled"></param>
        public void ToggleMoveDirection(string direction, bool toggled)
        {
            this.direction[direction] = toggled;
        }

        /// <summary>
        /// Deserializes JSON messages received from the server and determines what type of GameObject needs to be created
        /// </summary>
        /// <param name="message"></param>
        private bool DeserializeJSON(SocketState state)
        {
            string totalData = state.GetData();
            string[] parts = Regex.Split(totalData, @"(?<=[\n])");
            bool finish = true;

            // Loop until we have processed all messages.
            // We may have received more than one.

            foreach (string p in parts)
            {
                // Ignore empty strings added by the regex splitter
                if (p.Length == 0)
                    continue;
                // The regex splitter will include the last string even if it doesn't end with a '\n',
                // So we need to ignore it if this happens. 
                if (p[p.Length - 1] != '\n')
                {
                    finish = false;
                    break;
                }

                string type = p.Split('"')[1];
                switch (type)
                {
                    case "tank":
                        {
                            Tank rebuilt = JsonConvert.DeserializeObject<Tank>(p);
                            theWorld.insert(rebuilt);
                            break;
                        }
                    case "proj":
                        {
                            Projectile rebuilt = JsonConvert.DeserializeObject<Projectile>(p);
                            theWorld.insert(rebuilt);
                            break;
                        }
                    case "wall":
                        {
                            Wall rebuilt = JsonConvert.DeserializeObject<Wall>(p);
                            theWorld.insert(rebuilt);
                            break;
                        }
                    case "beam":
                        {
                            Beam rebuilt = JsonConvert.DeserializeObject<Beam>(p);
                            theWorld.insert(rebuilt);
                            break;
                        }
                    case "power":
                        {
                            Powerup rebuilt = JsonConvert.DeserializeObject<Powerup>(p);
                            theWorld.insert(rebuilt);
                            break;
                        }
                }
                // Then remove it from the SocketState's growable buffer
                state.RemoveData(0, p.Length);
            }
            return finish;
        }
    }
}
