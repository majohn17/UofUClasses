﻿// ------------------------------------------------------------------------------
//  <auto-generated>
//      This code was generated by coded UI test builder.
//      Version: 16.0.0.0
//
//      Changes to this file may cause incorrect behavior and will be lost if
//      the code is regenerated.
//  </auto-generated>
// ------------------------------------------------------------------------------

namespace TipCalculatorTestProject
{
    using System;
    using System.CodeDom.Compiler;
    using System.Collections.Generic;
    using System.Drawing;
    using System.Text.RegularExpressions;
    using System.Windows.Input;
    using Microsoft.VisualStudio.TestTools.UITest.Extension;
    using Microsoft.VisualStudio.TestTools.UITesting;
    using Microsoft.VisualStudio.TestTools.UITesting.WinControls;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Keyboard = Microsoft.VisualStudio.TestTools.UITesting.Keyboard;
    using Mouse = Microsoft.VisualStudio.TestTools.UITesting.Mouse;
    using MouseButtons = System.Windows.Forms.MouseButtons;
    
    
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public partial class UIMap
    {
        
        /// <summary>
        /// RecordedMethod1 - Use 'RecordedMethod1Params' to pass parameters into this method.
        /// </summary>
        public void RecordedMethod1()
        {
            #region Variable Declarations
            WinWindow uIBillTextBoxWindow = this.UIForm1Window.UIForm1Client.UIBillTextBoxWindow;
            WinEdit uIBillTextBoxEdit = this.UIForm1Window.UIBillTextBoxWindow.UIBillTextBoxEdit;
            WinEdit uITipTextBoxEdit = this.UIForm1Window.UITipTextBoxWindow.UITipTextBoxEdit;
            WinWindow uIComputeWindow = this.UIForm1Window.UIForm1Client.UIComputeWindow;
            #endregion

            // Launch '%USERPROFILE%\source\repos\u1173601\Lab6\TipCalculator\bin\Debug\TipCalculator.exe'
            ApplicationUnderTest uIForm1Window = ApplicationUnderTest.Launch(this.RecordedMethod1Params.UIForm1WindowExePath, this.RecordedMethod1Params.UIForm1WindowAlternateExePath);

            // Click 'billTextBox' window
            Mouse.Click(uIBillTextBoxWindow, new Point(209, 81));

            // Type '5000' in 'billTextBox' text box
            uIBillTextBoxEdit.Text = this.RecordedMethod1Params.UIBillTextBoxEditText;

            // Type '{Tab}' in 'billTextBox' text box
            Keyboard.SendKeys(uIBillTextBoxEdit, this.RecordedMethod1Params.UIBillTextBoxEditSendKeys, ModifierKeys.None);

            // Type '20' in 'tipTextBox' text box
            uITipTextBoxEdit.Text = this.RecordedMethod1Params.UITipTextBoxEditText;

            // Click 'Compute' window
            Mouse.Click(uIComputeWindow, new Point(142, 123));
        }
        
        #region Properties
        public virtual RecordedMethod1Params RecordedMethod1Params
        {
            get
            {
                if ((this.mRecordedMethod1Params == null))
                {
                    this.mRecordedMethod1Params = new RecordedMethod1Params();
                }
                return this.mRecordedMethod1Params;
            }
        }
        
        public UIForm1Window UIForm1Window
        {
            get
            {
                if ((this.mUIForm1Window == null))
                {
                    this.mUIForm1Window = new UIForm1Window();
                }
                return this.mUIForm1Window;
            }
        }
        #endregion
        
        #region Fields
        private RecordedMethod1Params mRecordedMethod1Params;
        
        private UIForm1Window mUIForm1Window;
        #endregion
    }
    
    /// <summary>
    /// Parameters to be passed into 'RecordedMethod1'
    /// </summary>
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public class RecordedMethod1Params
    {
        
        #region Fields
        /// <summary>
        /// Launch '%USERPROFILE%\source\repos\u1173601\Lab6\TipCalculator\bin\Debug\TipCalculator.exe'
        /// </summary>
        public string UIForm1WindowExePath = "C:\\Users\\matth\\source\\repos\\u1173601\\Lab6\\TipCalculator\\bin\\Debug\\TipCalculator.e" +
            "xe";
        
        /// <summary>
        /// Launch '%USERPROFILE%\source\repos\u1173601\Lab6\TipCalculator\bin\Debug\TipCalculator.exe'
        /// </summary>
        public string UIForm1WindowAlternateExePath = "%USERPROFILE%\\source\\repos\\u1173601\\Lab6\\TipCalculator\\bin\\Debug\\TipCalculator.ex" +
            "e";
        
        /// <summary>
        /// Type '5000' in 'billTextBox' text box
        /// </summary>
        public string UIBillTextBoxEditText = "5000";
        
        /// <summary>
        /// Type '{Tab}' in 'billTextBox' text box
        /// </summary>
        public string UIBillTextBoxEditSendKeys = "{Tab}";
        
        /// <summary>
        /// Type '20' in 'tipTextBox' text box
        /// </summary>
        public string UITipTextBoxEditText = "20";
        #endregion
    }
    
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public class UIForm1Window : WinWindow
    {
        
        public UIForm1Window()
        {
            #region Search Criteria
            this.SearchProperties[WinWindow.PropertyNames.Name] = "Form1";
            this.SearchProperties.Add(new PropertyExpression(WinWindow.PropertyNames.ClassName, "WindowsForms10.Window", PropertyExpressionOperator.Contains));
            this.WindowTitles.Add("Form1");
            #endregion
        }
        
        #region Properties
        public UIForm1Client UIForm1Client
        {
            get
            {
                if ((this.mUIForm1Client == null))
                {
                    this.mUIForm1Client = new UIForm1Client(this);
                }
                return this.mUIForm1Client;
            }
        }
        
        public UIBillTextBoxWindow UIBillTextBoxWindow
        {
            get
            {
                if ((this.mUIBillTextBoxWindow == null))
                {
                    this.mUIBillTextBoxWindow = new UIBillTextBoxWindow(this);
                }
                return this.mUIBillTextBoxWindow;
            }
        }
        
        public UITipTextBoxWindow UITipTextBoxWindow
        {
            get
            {
                if ((this.mUITipTextBoxWindow == null))
                {
                    this.mUITipTextBoxWindow = new UITipTextBoxWindow(this);
                }
                return this.mUITipTextBoxWindow;
            }
        }
        #endregion
        
        #region Fields
        private UIForm1Client mUIForm1Client;
        
        private UIBillTextBoxWindow mUIBillTextBoxWindow;
        
        private UITipTextBoxWindow mUITipTextBoxWindow;
        #endregion
    }
    
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public class UIForm1Client : WinClient
    {
        
        public UIForm1Client(UITestControl searchLimitContainer) : 
                base(searchLimitContainer)
        {
            #region Search Criteria
            this.SearchProperties[WinControl.PropertyNames.Name] = "Form1";
            this.WindowTitles.Add("Form1");
            #endregion
        }
        
        #region Properties
        public WinWindow UIBillTextBoxWindow
        {
            get
            {
                if ((this.mUIBillTextBoxWindow == null))
                {
                    this.mUIBillTextBoxWindow = new WinWindow(this);
                    #region Search Criteria
                    this.mUIBillTextBoxWindow.SearchProperties[WinWindow.PropertyNames.AccessibleName] = "Tip%";
                    this.mUIBillTextBoxWindow.SearchProperties.Add(new PropertyExpression(WinWindow.PropertyNames.ClassName, "WindowsForms10.EDIT", PropertyExpressionOperator.Contains));
                    this.mUIBillTextBoxWindow.WindowTitles.Add("Form1");
                    #endregion
                }
                return this.mUIBillTextBoxWindow;
            }
        }
        
        public WinWindow UIComputeWindow
        {
            get
            {
                if ((this.mUIComputeWindow == null))
                {
                    this.mUIComputeWindow = new WinWindow(this);
                    #region Search Criteria
                    this.mUIComputeWindow.SearchProperties[WinWindow.PropertyNames.Name] = "Compute";
                    this.mUIComputeWindow.SearchProperties.Add(new PropertyExpression(WinWindow.PropertyNames.ClassName, "WindowsForms10.BUTTON", PropertyExpressionOperator.Contains));
                    this.mUIComputeWindow.WindowTitles.Add("Form1");
                    #endregion
                }
                return this.mUIComputeWindow;
            }
        }
        #endregion
        
        #region Fields
        private WinWindow mUIBillTextBoxWindow;
        
        private WinWindow mUIComputeWindow;
        #endregion
    }
    
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public class UIBillTextBoxWindow : WinWindow
    {
        
        public UIBillTextBoxWindow(UITestControl searchLimitContainer) : 
                base(searchLimitContainer)
        {
            #region Search Criteria
            this.SearchProperties[WinWindow.PropertyNames.ControlName] = "billTextBox";
            this.WindowTitles.Add("Form1");
            #endregion
        }
        
        #region Properties
        public WinEdit UIBillTextBoxEdit
        {
            get
            {
                if ((this.mUIBillTextBoxEdit == null))
                {
                    this.mUIBillTextBoxEdit = new WinEdit(this);
                    #region Search Criteria
                    this.mUIBillTextBoxEdit.SearchProperties[WinEdit.PropertyNames.Name] = "Tip%";
                    this.mUIBillTextBoxEdit.WindowTitles.Add("Form1");
                    #endregion
                }
                return this.mUIBillTextBoxEdit;
            }
        }
        #endregion
        
        #region Fields
        private WinEdit mUIBillTextBoxEdit;
        #endregion
    }
    
    [GeneratedCode("Coded UITest Builder", "16.0.28315.86")]
    public class UITipTextBoxWindow : WinWindow
    {
        
        public UITipTextBoxWindow(UITestControl searchLimitContainer) : 
                base(searchLimitContainer)
        {
            #region Search Criteria
            this.SearchProperties[WinWindow.PropertyNames.ControlName] = "tipTextBox";
            this.WindowTitles.Add("Form1");
            #endregion
        }
        
        #region Properties
        public WinEdit UITipTextBoxEdit
        {
            get
            {
                if ((this.mUITipTextBoxEdit == null))
                {
                    this.mUITipTextBoxEdit = new WinEdit(this);
                    #region Search Criteria
                    this.mUITipTextBoxEdit.WindowTitles.Add("Form1");
                    #endregion
                }
                return this.mUITipTextBoxEdit;
            }
        }
        #endregion
        
        #region Fields
        private WinEdit mUITipTextBoxEdit;
        #endregion
    }
}