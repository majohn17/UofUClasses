using System;
using System.Windows.Forms;
using NetworkUtil;
using System.Net.Sockets;
using System.Text.RegularExpressions;

namespace ChatClient
{
  public partial class Form1 : Form
  {

    private SocketState theServer;

    public Form1()
    {
      InitializeComponent();
      messageToSendBox.KeyDown += new KeyEventHandler(MessageEnterHandler);
      FormClosed += OnExit;
    }

    private void OnExit(object sender, FormClosedEventArgs e)
    {
      if (theServer != null)
        theServer.TheSocket.Shutdown(SocketShutdown.Both);
    }

    /// <summary>
    /// Connect button event handler
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void connectButton_Click(object sender, EventArgs e)
    {
      // TODO: This needs better error handling, left as an exercise.
      // If the server box is empty, it gives a message, 
      // but doesn't allow us to try to reconnect.
      if (serverAddress.Text == "")
      {
        MessageBox.Show("Please enter a server address");
        return;
      }

      // Disable the controls and try to connect
      connectButton.Enabled = false;
      serverAddress.Enabled = false;

      NetworkingTest.ConnectToServer(OnConnect, serverAddress.Text, 11000);
    }


    private void OnConnect(SocketState state)
    {
      if (state.ErrorOccured)
      {
        // TODO: Left as an exercise, allow the user to try to reconnect
        MessageBox.Show("Error connecting to server. Please restart the client.");
        return;
      }

      // Save the SocketState so we can use it to send messages
      theServer = state;

      // Start an event loop to receive messages from the server
      state.OnNetworkAction = ReceiveMessage;
      NetworkingTest.GetData(state);
    }

    private void ReceiveMessage(SocketState state)
    {
      if (state.ErrorOccured)
      {
        // TODO: Left as an exercise, allow the user to try to reconnect
        MessageBox.Show("Error while receiving. Please restart the client.");
        return;
      }
      ProcessMessages(state);
      // Continue the event loop
      NetworkingTest.GetData(state);
    }

    private void ProcessMessages(SocketState state)
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

        // Display the message
        // "messages" is the big message text box in the form.
        // We must use a MethodInvoker, because only the thread 
        // that created the GUI can modify it.
        this.Invoke(new MethodInvoker(
          () => messages.AppendText(p + Environment.NewLine)));

        // Then remove it from the SocketState's growable buffer
        state.RemoveData(0, p.Length);
      }
    }


    /// <summary>
    /// This is the event handler when the enter key is pressed in the messageToSend box
    /// </summary>
    /// <param name="sender">The Form control that fired the event</param>
    /// <param name="e">The key event arguments</param>
    private void MessageEnterHandler(object sender, KeyEventArgs e)
    {
      if (e.KeyCode == Keys.Enter)
      {
        // Append a newline, since that is our protocol's terminating character for a message.
        string message = messageToSendBox.Text + "\n";
        // Reset the textbox
        messageToSendBox.Text = "";
        // Send the message to the server
        NetworkingTest.Send(theServer.TheSocket, message);
      }
    }
  }
}

