// Authors: Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace NetworkUtil
{
    public static class NetworkingTest
    {
        /////////////////////////////////////////////////////////////////////////////////////////
        // Server-Side Code
        /////////////////////////////////////////////////////////////////////////////////////////

        /// <summary>
        /// Starts a TcpListener on the specified port and starts an event-loop to accept new clients.
        /// The event-loop is started with BeginAcceptSocket and uses AcceptNewClient as the callback.
        /// AcceptNewClient will continue the event-loop.
        /// </summary>
        /// <param name="toCall">The method to call when a new connection is made</param>
        /// <param name="port">The the port to listen on</param>
        public static TcpListener StartServer(Action<SocketState> toCall, int port)
        {
            TcpListener server = null;
            try
            {
                server = new TcpListener(IPAddress.Any, port);
                server.Start();
                Tuple<Action<SocketState>, TcpListener> stateListener = new Tuple<Action<SocketState>, TcpListener>(toCall, server);
                server.BeginAcceptSocket(AcceptNewClient, stateListener);
            }
            catch (Exception)
            {
                return null;
            }
            return server;
        }

        /// <summary>
        /// To be used as the callback for accepting a new client that was initiated by StartServer, and 
        /// continues an event-loop to accept additional clients.
        ///
        /// Uses EndAcceptSocket to finalize the connection and create a new SocketState. The SocketState's
        /// OnNetworkAction should be set to the delegate that was passed to StartServer.
        /// Then invokes the OnNetworkAction delegate with the new SocketState so the user can take action. 
        /// 
        /// If anything goes wrong during the connection process (such as the server being stopped externally), 
        /// the OnNetworkAction delegate should be invoked with a new SocketState with its ErrorOccured flag set to true 
        /// and an appropriate message placed in its ErrorMessage field. The event-loop should not continue if
        /// an error occurs.
        ///
        /// If an error does not occur, after invoking OnNetworkAction with the new SocketState, an event-loop to accept 
        /// new clients should be continued by calling BeginAcceptSocket again with this method as the callback.
        /// </summary>
        /// <param name="ar">The object asynchronously passed via BeginAcceptSocket. It must contain a tuple with 
        /// 1) a delegate so the user can take action (a SocketState Action), and 2) the TcpListener</param>
        private static void AcceptNewClient(IAsyncResult ar)
        {
            Tuple<Action<SocketState>, TcpListener> tuple = (Tuple<Action<SocketState>, TcpListener>)ar.AsyncState;
            TcpListener server = tuple.Item2;
            Action<SocketState> toCall = tuple.Item1;
            try
            {
                SocketState ss = new SocketState(toCall, server.EndAcceptSocket(ar));
                ss.OnNetworkAction(ss);
                server.BeginAcceptSocket(AcceptNewClient, tuple);
            }
            catch (Exception)
            {
                SocketState ss = new SocketState(toCall, null);
                ss.ErrorOccured = true;
                ss.ErrorMessage = "Unable to accept clients.";
                ss.OnNetworkAction(ss);
                return;
            }
        }

        /// <summary>
        /// Stops the given TcpListener.
        /// </summary>
        public static void StopServer(TcpListener listener)
        {
            listener.Stop();
        }

        /////////////////////////////////////////////////////////////////////////////////////////
        // Client-Side Code
        /////////////////////////////////////////////////////////////////////////////////////////

        /// <summary>
        /// Begins the asynchronous process of connecting to a server via BeginConnect, 
        /// and using ConnectedCallback as the method to finalize the connection once it's made.
        /// 
        /// If anything goes wrong during the connection process, toCall should be invoked 
        /// with a new SocketState with its ErrorOccured flag set to true and an appropriate message 
        /// placed in its ErrorMessage field. Between this method and ConnectedCallback, toCall should 
        /// only be invoked once on error.
        ///
        /// This connection process should timeout and produce an error (as discussed above) 
        /// if a connection can't be established within 3 seconds of starting BeginConnect.
        /// 
        /// </summary>
        /// <param name="toCall">The action to take once the connection is open or an error occurs</param>
        /// <param name="hostName">The server to connect to</param>
        /// <param name="port">The port on which the server is listening</param>
        public static void ConnectToServer(Action<SocketState> toCall, string hostName, int port)
        {
            // Establish the remote endpoint for the socket.
            IPHostEntry ipHostInfo;
            IPAddress ipAddress = IPAddress.None;

            // Determine if the server address is a URL or an IP
            try
            {
                ipHostInfo = Dns.GetHostEntry(hostName);
                bool foundIPV4 = false;
                foreach (IPAddress addr in ipHostInfo.AddressList)
                    if (addr.AddressFamily != AddressFamily.InterNetworkV6)
                    {
                        foundIPV4 = true;
                        ipAddress = addr;
                        break;
                    }
                // Didn't find any IPV4 addresses
                if (!foundIPV4)
                {
                    SocketState sserror = new SocketState(toCall, null);
                    sserror.ErrorOccured = true;
                    sserror.ErrorMessage = "No IPV4 found";
                    sserror.OnNetworkAction(sserror);
                    return;
                }
            }
            catch (Exception)
            {
                // See if host name is a valid ipaddress
                try
                {
                    ipAddress = IPAddress.Parse(hostName);
                }
                catch (Exception)
                {
                    Console.WriteLine("IPAddress could not be parsed.");
                }
            }

            try
            {
                // Create a TCP/IP socket.
                Socket socket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
                // This disables Nagle's algorithm (google if curious!)
                // Nagle's algorithm can cause problems for a latency-sensitive 
                // game like ours will be 
                socket.NoDelay = true;

                SocketState ss = new SocketState(toCall, socket);

                IAsyncResult result = socket.BeginConnect(ipAddress, port, ConnectedCallback, ss);
                bool success = result.AsyncWaitHandle.WaitOne(3000, true);
                if (!success)
                {
                    ss.ErrorOccured = true;
                    ss.ErrorMessage = "Unable to connect, connection timed out.";
                    ss.OnNetworkAction(ss);
                    return;
                }
            }
            catch (Exception)
            {
                SocketState ss = new SocketState(toCall, null);
                ss.ErrorOccured = true;
                ss.ErrorMessage = "Unable to connect, connection timed out.";
                ss.OnNetworkAction(ss);
                return;
            }
        }

        /// <summary>
        /// To be used as the callback for finalizing a connection process that was initiated by ConnectToServer.
        ///
        /// Uses EndConnect to finalize the connection.
        /// 
        /// As stated in the ConnectToServer documentation, if an error occurs during the connection process,
        /// either this method or ConnectToServer (not both) should indicate the error appropriately.
        /// 
        /// If a connection is successfully established, invokes the toCall Action that was provided to ConnectToServer (above)
        /// with a new SocketState representing the new connection.
        /// 
        /// </summary>
        /// <param name="ar">The object asynchronously passed via BeginConnect</param>
        private static void ConnectedCallback(IAsyncResult ar)
        {
            SocketState server = (SocketState)ar.AsyncState;
            try
            {
                server.TheSocket.EndConnect(ar);
                server.OnNetworkAction(server);
            }
            catch (Exception)
            {
                server.ErrorOccured = true;
                server.ErrorMessage = "Unable to finish connection, connection timed out.";
                server.OnNetworkAction(server);
                return;
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////
        // Server and Client Common Code
        /////////////////////////////////////////////////////////////////////////////////////////

        /// <summary>
        /// Begins the asynchronous process of receiving data via BeginReceive, using ReceiveCallback 
        /// as the callback to finalize the receive and store data once it has arrived.
        /// The object passed to ReceiveCallback via the AsyncResult should be the SocketState.
        /// 
        /// If anything goes wrong during the receive process, the SocketState's ErrorOccured flag should 
        /// be set to true, and an appropriate message placed in ErrorMessage, then the SocketState's
        /// OnNetworkAction should be invoked. Between this method and ReceiveCallback, OnNetworkAction should only be 
        /// invoked once on error.
        /// 
        /// </summary>
        /// <param name="state">The SocketState to begin receiving</param>
        public static void GetData(SocketState state)
        {
            try
            {
                state.TheSocket.BeginReceive(state.buffer, 0, state.buffer.Length, SocketFlags.None, ReceiveCallback, state);
            }
            catch (Exception)
            {
                state.ErrorOccured = true;
                state.ErrorMessage = "could not receive data";
                state.OnNetworkAction(state);
                return;
            }
        }

        /// <summary>
        /// To be used as the callback for finalizing a receive operation that was initiated by GetData.
        /// 
        /// Uses EndReceive to finalize the receive.
        ///
        /// As stated in the GetData documentation, if an error occurs during the receive process,
        /// either this method or GetData (not both) should indicate the error appropriately.
        /// 
        /// If data is successfully received:
        ///  (1) Read the characters as UTF8 and put them in the SocketState's unprocessed data buffer (its string builder).
        ///      This must be done in a thread-safe manner with respect to the SocketState methods that access or modify its 
        ///      string builder.
        ///  (2) Call the saved delegate (OnNetworkAction) allowing the user to deal with this data.
        /// </summary>
        /// <param name="ar"> 
        /// This contains the SocketState that is stored with the callback when the initial BeginReceive is called.
        /// </param>
        private static void ReceiveCallback(IAsyncResult ar)
        {
            SocketState state = (SocketState)ar.AsyncState;
            try
            {
                int numBytes = state.TheSocket.EndReceive(ar);
                if (numBytes == 0)
                {
                    state.TheSocket.Close();
                    state.ErrorOccured = true;
                    state.ErrorMessage = "could not receive data";
                    state.OnNetworkAction(state);
                    return;
                }
                else
                {
                    lock (state.data)
                    {
                        string message = Encoding.UTF8.GetString(state.buffer, 0, numBytes);
                        state.data.Append(message);
                    }
                    state.OnNetworkAction(state);
                }
            }
            catch (Exception)
            {
                state.ErrorOccured = true;
                state.ErrorMessage = "could not receive data";
                state.OnNetworkAction(state);
                return;
            }
        }

        /// <summary>
        /// Begin the asynchronous process of sending data via BeginSend, using SendCallback to finalize the send process.
        /// 
        /// If the socket is closed, does not attempt to send.
        /// 
        /// If a send fails for any reason, this method ensures that the Socket is closed before returning.
        /// </summary>
        /// <param name="socket">The socket on which to send the data</param>
        /// <param name="data">The string to send</param>
        /// <returns>True if the send process was started, false if an error occurs or the socket is already closed</returns>
        public static bool Send(Socket socket, string data)
        {
            return SendMessage(socket, data, SendCallback);
        }

        /// <summary>
        /// To be used as the callback for finalizing a send operation that was initiated by Send.
        ///
        /// Uses EndSend to finalize the send.
        /// 
        /// This method must not throw, even if an error occured during the Send operation.
        /// </summary>
        /// <param name="ar">
        /// This is the Socket (not SocketState) that is stored with the callback when
        /// the initial BeginSend is called.
        /// </param>
        private static void SendCallback(IAsyncResult ar)
        {
            try
            {
                Socket client = (Socket)ar.AsyncState;
                client.EndSend(ar);
            }
            catch (Exception)
            {
                //Do nothing
            }
        }

        /// <summary>
        /// Begin the asynchronous process of sending data via BeginSend, using SendAndCloseCallback to finalize the send process.
        /// This variant closes the socket in the callback once complete. This is useful for HTTP servers.
        /// 
        /// If the socket is closed, does not attempt to send.
        /// 
        /// If a send fails for any reason, this method ensures that the Socket is closed before returning.
        /// </summary>
        /// <param name="socket">The socket on which to send the data</param>
        /// <param name="data">The string to send</param>
        /// <returns>True if the send process was started, false if an error occurs or the socket is already closed</returns>
        public static bool SendAndClose(Socket socket, string data)
        {
            return SendMessage(socket, data, SendAndCloseCallback);
        }

        /// <summary>
        /// To be used as the callback for finalizing a send operation that was initiated by SendAndClose.
        ///
        /// Uses EndSend to finalize the send, then closes the socket.
        /// 
        /// This method must not throw, even if an error occured during the Send operation.
        /// 
        /// This method ensures that the socket is closed before returning.
        /// </summary>
        /// <param name="ar">
        /// This is the Socket (not SocketState) that is stored with the callback when
        /// the initial BeginSend is called.
        /// </param>
        private static void SendAndCloseCallback(IAsyncResult ar)
        {
            try
            {
                Socket client = (Socket)ar.AsyncState;
                client.EndSend(ar);
                client.Close();
            }
            catch (Exception)
            {
                //Do nothing
            }
        }

        /// <summary>
        /// Allows passing of a callback for sending data
        /// </summary>
        /// <param name="socket"></param>
        /// <param name="data"></param>
        /// <param name="callback"></param>
        /// <returns></returns>
        private static bool SendMessage(Socket socket, string data, AsyncCallback callback)
        {
            if (!socket.Connected)
            {
                return false;
            }

            byte[] messageBytes = Encoding.UTF8.GetBytes(data);

            // Begin sending the message
            try
            {
                socket.BeginSend(messageBytes, 0, messageBytes.Length, SocketFlags.None, callback, socket);
            }
            catch (Exception)
            {
                Console.WriteLine("Error: could not send the message");
                socket.Close();
                return false;
            }
            return true;
        }
    }
}