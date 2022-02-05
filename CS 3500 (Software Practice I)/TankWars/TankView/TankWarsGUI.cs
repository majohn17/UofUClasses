// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TankController;
using TankModel;
using NetworkUtil;

namespace TankView
{
    public partial class TankWarsGUI : Form
    {
        private World theWorld;
        DrawingPanel drawingPanel;
        GameController Controller;

        /// <summary>
        /// Opens form application and creates controller for the Tank Wars game
        /// </summary>
        public TankWarsGUI()
        {
            InitializeComponent();

            Controller = new GameController();
            theWorld = Controller.theWorld;
            
            nameTextBox.Focus();

            // creates drawing panel
            ClientSize = new Size(800, 835);
            drawingPanel = new DrawingPanel(theWorld);
            drawingPanel.Location = new Point(0, 35);
            drawingPanel.Size = new Size(800, 800);
            drawingPanel.BackColor = Color.Black;
            this.Controls.Add(drawingPanel);
            this.drawingPanel.MouseDown += new System.Windows.Forms.MouseEventHandler(this.DrawingPanel_MouseDown);
            this.drawingPanel.MouseUp += new System.Windows.Forms.MouseEventHandler(this.DrawingPanel_MouseUp);
            this.drawingPanel.MouseDown += new System.Windows.Forms.MouseEventHandler(this.DrawingPanel_MousePress);
            this.drawingPanel.MouseMove += new System.Windows.Forms.MouseEventHandler(this.DrawingPanel_MouseMove);
          
        }

       
        /// <summary>
        /// Updates drawing panel when a new Frame needs to be drawn.
        /// </summary>
        private void OnFrame()
        {
            
            if (!IsHandleCreated)
                return;
            try
            {
                this.Invoke(new MethodInvoker(() => this.Invalidate(true)));
            }
            catch (ObjectDisposedException)
            {

            }
        }

        /// <summary>
        /// informs the controller that a connection needs to be made on the server
        /// indicated by server IP and with the name indicated.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void connectButton_Click(object sender, EventArgs e)
        {
            string name = nameTextBox.Text;
            string IP = ServerTextBox.Text;
            if(IP.Length == 0)
            {
                MessageBox.Show("Please enter address");
                return;
            }
            if (name.Length == 0)
                MessageBox.Show("Please enter a name and try again.");
            else if (name.Length > 16)
                MessageBox.Show("Name must be 16 characters or less. Try Again.");
            else
            {
                connectButton.Enabled = false;
                nameTextBox.Enabled = false;
                ServerTextBox.Enabled = false;
                Controller.Start(name, IP, EnableConnect);
                Controller.RegisterServerUpdateHandler(OnFrame);
            }
        }

        /// <summary>
        /// Enables the option to connect again if connection fails
        /// </summary>
        public void EnableConnect()
        {
            this.Invoke(new MethodInvoker(() =>
            {
                connectButton.Enabled = true;
                nameTextBox.Enabled = true;
                ServerTextBox.Enabled = true;
                return;
            }
            ));


           
        }

        /// <summary>
        /// displays help message
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void helpButton_Click(object sender, EventArgs e)
        {
            MessageBox.Show("W:\t\tMove Up\nA:\t\tMove Left\nS:\t\tMove Down\nD:\t\tMove Right" +
                "\nMouse:\t\tAim\nLeft click:\t\tFire projectile\nRight click:\tFire beam\n", "Controls");
        }

    
      /// <summary>
      /// informs Controller that the mouse was moved to a point on the drawing panel.
      /// </summary>
      /// <param name="sender"></param>
      /// <param name="e"></param>
        private void DrawingPanel_MouseMove(object sender, MouseEventArgs e)
        {
            Controller.aim(e.X - 400, e.Y - 400);
        }
        /// <summary>
        /// informs Controller that a mouse button was released to stop firing.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawingPanel_MouseUp(object sender, MouseEventArgs e)
        {
            Controller.fire("none");
        }
        /// <summary>
        /// informs Controller that the right mouse button was pressed and 
        /// intends to fire the beam
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawingPanel_MousePress(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                Controller.fire("alt");
            }
        }

        /// <summary>
        /// informs Controller that the right mouse button was pressed and 
        /// intends to fire the main cannon
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawingPanel_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                Controller.fire("main");
            }
           
        }

        /// <summary>
        /// informs the controller what key was released
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Form1_KeyUp(object sender, KeyEventArgs e)
        {


            //  Controller.move("none");
            if (e.KeyCode == Keys.W)
            {
                  Controller.ToggleMoveDirection("up",false);
            
            }
            if (e.KeyCode == Keys.S)
            {
                    Controller.ToggleMoveDirection("down",false);
            
            }
            if (e.KeyCode == Keys.A)
            {
                     Controller.ToggleMoveDirection("left",false);
              
            }
            if (e.KeyCode == Keys.D)
            {
                    Controller.ToggleMoveDirection("right",false);
               
            }


        }

        /// <summary>
        /// informs the controller what key was pressed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Form1_KeyDown(object sender, KeyEventArgs e)
        {
            
            if (e.KeyCode == Keys.W)
            {
                Controller.ToggleMoveDirection("up",true);
             
            }
            if (e.KeyCode == Keys.S)
            {
                Controller.ToggleMoveDirection("down",true);
              
            }
            if (e.KeyCode == Keys.A)
            {
                Controller.ToggleMoveDirection("left",true);
               
            }
            if (e.KeyCode == Keys.D)
            {
                Controller.ToggleMoveDirection("right",true);
              
            }
        }
    }
}
