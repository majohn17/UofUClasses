using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TipCalculator
{
  public partial class Form1 : Form
  {
    public Form1()
    {
      InitializeComponent();
    }

    private void ComputeButton_Click(object sender, EventArgs e)
    {
      double tip = computeTip(Double.Parse(billTextBox.Text), Double.Parse(tipTextBox.Text));
      showTip(tip);
    }

    private void BillTextBox_TextChanged(object sender, EventArgs e)
    {
      //updateTip();
      checkInputSanity();
    }

    private void TipTextBox_TextChanged(object sender, EventArgs e)
    {
      //updateTip();
      checkInputSanity();
    }


    private void updateTip()
    {
      if (!checkInputSanity())
        return;
      double tip = computeTip(Double.Parse(billTextBox.Text), Double.Parse(tipTextBox.Text));
      showTip(tip);
    }

    private double computeTip(double bill, double tipPercent)
    {
      return bill * (tipPercent / 100);
    }

    private void showTip(double tip)
    {
      resultBox.Text = tip.ToString();
    }

    private bool checkInputSanity()
    {
      if (!Double.TryParse(billTextBox.Text, out double unused) ||
        !Double.TryParse(tipTextBox.Text, out double unused2))
      {
        computeButton.Enabled = false;
        return false;
      }
      else
      {
        computeButton.Enabled = true;
        return true;
      }
    }

  }
}
