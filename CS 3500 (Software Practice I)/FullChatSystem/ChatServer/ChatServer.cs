using System;
using System.Collections.Generic;
using NetworkUtil;
using System.Text.RegularExpressions;

namespace ChatServer
{

  /// <summary>
  /// A simple server for receiving simple text messages from multiple clients
  /// </summary>
  class ChatServer
  {
    // A set of clients that are connected.
    private Dictionary<long, SocketState> clients;

    static void Main(string[] args)
    {
      ChatServer server = new ChatServer();
      server.StartServer();

      // Sleep to prevent the program from closing,
      // since all the real work is done in separate threads.
      // StartServer is non-blocking.
      Console.Read();
    }



    public ChatServer()
    {
      clients = new Dictionary<long, SocketState>();
    }

    /// <summary>
    /// Start accepting Tcp sockets connections from clients
    /// </summary>
    public void StartServer()
    {
      // This begins an "event loop"
      NetworkingTest.StartServer(NewClientConnected, 11000);

      Console.WriteLine("Server is running");
    }

    private void NewClientConnected(SocketState state)
    {
      if (state.ErrorOccured)
        return;

      // Save the client state
      // Need to lock here because clients can disconnect at any time
      lock (clients)
      {
        clients[state.ID] = state;
      }

      state.OnNetworkAction = ReceiveMessage;
      NetworkingTest.GetData(state);
    }

    private void ReceiveMessage(SocketState state)
    {
      if (state.ErrorOccured)
      {
        RemoveClient(state.ID);
        return;
      }

      ProcessMessage(state);
      // Continue the event loop that receives messages from this client
      NetworkingTest.GetData(state);
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
      string totalData = state.GetData();

      string[] parts = Regex.Split(totalData, @"(?<=[\n])");

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
          break;

        Console.WriteLine("received message from client " + state.ID + ": \"" + p + "\"");

        // Remove it from the SocketState's growable buffer
        state.RemoveData(0, p.Length);

        // Broadcast the message to all clients
        // Lock here beccause we can't have new connections 
        // adding while looping through the clients list.
        // We also need to remove any disconnected clients.
        HashSet<long> disconnectedClients = new HashSet<long>();
        lock (clients)
        {
          foreach (SocketState client in clients.Values)
          {
            if (!NetworkingTest.Send(client.TheSocket, "Message from client " + state.ID + ": " + p))
              disconnectedClients.Add(client.ID);
          }
        }
        foreach (long id in disconnectedClients)
          RemoveClient(id);
      }
    }

    private void RemoveClient(long id)
    {
      Console.WriteLine("Client " + id + " disconnected");
      lock (clients)
      {
        clients.Remove(id);
      }
    }
  }
}

