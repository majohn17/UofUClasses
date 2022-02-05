namespace TankView
{
    partial class TankWarsGUI
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.ServerLabel = new System.Windows.Forms.Label();
            this.ServerTextBox = new System.Windows.Forms.TextBox();
            this.nameLabel = new System.Windows.Forms.Label();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.connectButton = new System.Windows.Forms.Button();
            this.helpButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // ServerLabel
            // 
            this.ServerLabel.AutoSize = true;
            this.ServerLabel.Location = new System.Drawing.Point(12, 15);
            this.ServerLabel.Name = "ServerLabel";
            this.ServerLabel.Size = new System.Drawing.Size(51, 16);
            this.ServerLabel.TabIndex = 0;
            this.ServerLabel.Text = "Server:";
            // 
            // ServerTextBox
            // 
            this.ServerTextBox.Location = new System.Drawing.Point(72, 11);
            this.ServerTextBox.Name = "ServerTextBox";
            this.ServerTextBox.Size = new System.Drawing.Size(141, 22);
            this.ServerTextBox.TabIndex = 1;
            this.ServerTextBox.Text = "localhost";
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Location = new System.Drawing.Point(253, 15);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(51, 16);
            this.nameLabel.TabIndex = 2;
            this.nameLabel.Text = "Name: ";
            // 
            // nameTextBox
            // 
            this.nameTextBox.Location = new System.Drawing.Point(310, 11);
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(183, 22);
            this.nameTextBox.TabIndex = 3;
            this.nameTextBox.Text = "Player";
            // 
            // connectButton
            // 
            this.connectButton.Location = new System.Drawing.Point(511, 10);
            this.connectButton.Name = "connectButton";
            this.connectButton.Size = new System.Drawing.Size(148, 26);
            this.connectButton.TabIndex = 4;
            this.connectButton.Text = "connect";
            this.connectButton.UseVisualStyleBackColor = true;
            this.connectButton.Click += new System.EventHandler(this.connectButton_Click);
            // 
            // helpButton
            // 
            this.helpButton.Location = new System.Drawing.Point(965, 10);
            this.helpButton.Name = "helpButton";
            this.helpButton.Size = new System.Drawing.Size(81, 26);
            this.helpButton.TabIndex = 5;
            this.helpButton.Text = "Help";
            this.helpButton.UseVisualStyleBackColor = true;
            this.helpButton.Click += new System.EventHandler(this.helpButton_Click);
            // 
            // TankWarsGUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1060, 450);
            this.Controls.Add(this.helpButton);
            this.Controls.Add(this.connectButton);
            this.Controls.Add(this.nameTextBox);
            this.Controls.Add(this.nameLabel);
            this.Controls.Add(this.ServerTextBox);
            this.Controls.Add(this.ServerLabel);
            this.DoubleBuffered = true;
            this.KeyPreview = true;
            this.Name = "TankWarsGUI";
            this.Text = "Tank Wars";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.Form1_KeyDown);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.Form1_KeyUp);
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Label ServerLabel;
        private System.Windows.Forms.TextBox ServerTextBox;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.TextBox nameTextBox;
        private System.Windows.Forms.Button connectButton;
        private System.Windows.Forms.Button helpButton;
 
    }
}

