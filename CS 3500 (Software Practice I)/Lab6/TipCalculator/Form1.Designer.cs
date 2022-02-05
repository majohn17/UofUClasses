namespace TipCalculator
{
  partial class Form1
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
      this.billLabel = new System.Windows.Forms.Label();
      this.tipLabel = new System.Windows.Forms.Label();
      this.billTextBox = new System.Windows.Forms.TextBox();
      this.tipTextBox = new System.Windows.Forms.TextBox();
      this.computeButton = new System.Windows.Forms.Button();
      this.resultBox = new System.Windows.Forms.TextBox();
      this.SuspendLayout();
      // 
      // billLabel
      // 
      this.billLabel.AutoSize = true;
      this.billLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.billLabel.Location = new System.Drawing.Point(36, 91);
      this.billLabel.Name = "billLabel";
      this.billLabel.Size = new System.Drawing.Size(225, 37);
      this.billLabel.TabIndex = 0;
      this.billLabel.Text = "Enter Total Bill";
      // 
      // tipLabel
      // 
      this.tipLabel.AutoSize = true;
      this.tipLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.tipLabel.Location = new System.Drawing.Point(40, 203);
      this.tipLabel.Name = "tipLabel";
      this.tipLabel.Size = new System.Drawing.Size(90, 37);
      this.tipLabel.TabIndex = 1;
      this.tipLabel.Text = "Tip%";
      // 
      // billTextBox
      // 
      this.billTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.billTextBox.Location = new System.Drawing.Point(346, 91);
      this.billTextBox.Name = "billTextBox";
      this.billTextBox.Size = new System.Drawing.Size(189, 44);
      this.billTextBox.TabIndex = 2;
      this.billTextBox.TextChanged += new System.EventHandler(this.BillTextBox_TextChanged);
      // 
      // tipTextBox
      // 
      this.tipTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.tipTextBox.Location = new System.Drawing.Point(346, 203);
      this.tipTextBox.Name = "tipTextBox";
      this.tipTextBox.Size = new System.Drawing.Size(189, 44);
      this.tipTextBox.TabIndex = 3;
      this.tipTextBox.TextChanged += new System.EventHandler(this.TipTextBox_TextChanged);
      // 
      // computeButton
      // 
      this.computeButton.Enabled = false;
      this.computeButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.computeButton.Location = new System.Drawing.Point(47, 310);
      this.computeButton.Name = "computeButton";
      this.computeButton.Size = new System.Drawing.Size(214, 57);
      this.computeButton.TabIndex = 4;
      this.computeButton.Text = "Compute";
      this.computeButton.UseVisualStyleBackColor = true;
      this.computeButton.Click += new System.EventHandler(this.ComputeButton_Click);
      // 
      // resultBox
      // 
      this.resultBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.resultBox.Location = new System.Drawing.Point(346, 310);
      this.resultBox.Name = "resultBox";
      this.resultBox.ReadOnly = true;
      this.resultBox.Size = new System.Drawing.Size(189, 44);
      this.resultBox.TabIndex = 5;
      // 
      // Form1
      // 
      this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
      this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
      this.ClientSize = new System.Drawing.Size(724, 472);
      this.Controls.Add(this.resultBox);
      this.Controls.Add(this.computeButton);
      this.Controls.Add(this.tipTextBox);
      this.Controls.Add(this.billTextBox);
      this.Controls.Add(this.tipLabel);
      this.Controls.Add(this.billLabel);
      this.Name = "Form1";
      this.Text = "Form1";
      this.ResumeLayout(false);
      this.PerformLayout();

    }

    #endregion

    private System.Windows.Forms.Label billLabel;
    private System.Windows.Forms.Label tipLabel;
    private System.Windows.Forms.TextBox billTextBox;
    private System.Windows.Forms.TextBox tipTextBox;
    private System.Windows.Forms.Button computeButton;
    private System.Windows.Forms.TextBox resultBox;
  }
}

