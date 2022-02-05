// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using NetworkUtil;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TankModel;
using TankWars;

namespace Server
{
    class WebServer
    {
        /// <summary>
        /// Creates a WebServer with 
        /// </summary>
        /// <param name="world"></param>
        public WebServer()
        {

        }

        /// <summary>
        /// Start accepting Tcp sockets connections from clients
        /// </summary>
        public void StartServer()
        {
            Networking.StartServer(HandleHTTPClient, 80);
        }

        /// <summary>
        /// Deals with intial process of a http client connecting to the Web Server
        /// </summary>
        /// <param name="state"></param>
        private void HandleHTTPClient(SocketState state)
        {
            if (state.ErrorOccured)
                return;
            state.OnNetworkAction = ReceiveHTTPRequest;
            Networking.GetData(state);
        }

        /// <summary>
        /// Deals with HTTP Requests that are being sent
        /// </summary>
        /// <param name="state"></param>
        private void ReceiveHTTPRequest(SocketState state)
        {
            if (state.ErrorOccured)
                return;

            string data = state.GetData();
            
            if (data.Contains("GET /games?player="))
            {
                string name = GetPlayerName(data);
                Networking.Send(state.TheSocket, WebViews.GetPlayerGames(name, DataBaseHandling.createSessionModel(name)));
            }
            else if (data.Contains("GET /games"))
            {
                Networking.Send(state.TheSocket, WebViews.GetAllGames(DataBaseHandling.AllGames()));
            }
                
            else
                Networking.Send(state.TheSocket, WebViews.Get404());

            
            state.TheSocket.Close();
        }

        /// <summary>
        /// Retrieves a name from an http request
        /// </summary>
        private string GetPlayerName(string data)
        {
            int startIndex = 17;
            int length = data.IndexOf("HTTP") - (startIndex + 1);
            return data.Substring(startIndex + 1, length);
        }
    }
}
