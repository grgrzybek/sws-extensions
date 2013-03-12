﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.18034
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

// 
// This source code was auto-generated by Microsoft.VSDesigner, Version 4.0.30319.18034.
// 
#pragma warning disable 1591

namespace AsmxWebServices.Axis1Case1RpcEncoded12 {
    using System;
    using System.Web.Services;
    using System.Diagnostics;
    using System.Web.Services.Protocols;
    using System.Xml.Serialization;
    using System.ComponentModel;
    
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.17929")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Web.Services.WebServiceBindingAttribute(Name="EchoEndpointRpcEncoded12SoapBinding", Namespace="http://codefirst.case1.axis1.ws.springframework.org")]
    public partial class EchoEndpointService : System.Web.Services.Protocols.SoapHttpClientProtocol {
        
        private System.Threading.SendOrPostCallback oneParamOperationCompleted;
        
        private System.Threading.SendOrPostCallback twoParamsOperationCompleted;
        
        private bool useDefaultCredentialsSetExplicitly;
        
        /// <remarks/>
        public EchoEndpointService() {
            this.Url = global::AsmxWebServices.Properties.Settings.Default.AsmxWebServices_RpcEncoded12_EchoEndpointService;
            if ((this.IsLocalFileSystemWebService(this.Url) == true)) {
                this.UseDefaultCredentials = true;
                this.useDefaultCredentialsSetExplicitly = false;
            }
            else {
                this.useDefaultCredentialsSetExplicitly = true;
            }
        }
        
        public new string Url {
            get {
                return base.Url;
            }
            set {
                if ((((this.IsLocalFileSystemWebService(base.Url) == true) 
                            && (this.useDefaultCredentialsSetExplicitly == false)) 
                            && (this.IsLocalFileSystemWebService(value) == false))) {
                    base.UseDefaultCredentials = false;
                }
                base.Url = value;
            }
        }
        
        public new bool UseDefaultCredentials {
            get {
                return base.UseDefaultCredentials;
            }
            set {
                base.UseDefaultCredentials = value;
                this.useDefaultCredentialsSetExplicitly = true;
            }
        }
        
        /// <remarks/>
        public event oneParamCompletedEventHandler oneParamCompleted;
        
        /// <remarks/>
        public event twoParamsCompletedEventHandler twoParamsCompleted;
        
        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("oneParam", RequestNamespace="http://codefirst.case1.axis1.ws.springframework.org", ResponseNamespace="http://codefirst.case1.axis1.ws.springframework.org")]
        [return: System.Xml.Serialization.SoapElementAttribute("oneParamReturn")]
        public string oneParam(string param1) {
            object[] results = this.Invoke("oneParam", new object[] {
                        param1});
            return ((string)(results[0]));
        }
        
        /// <remarks/>
        public void oneParamAsync(string param1) {
            this.oneParamAsync(param1, null);
        }
        
        /// <remarks/>
        public void oneParamAsync(string param1, object userState) {
            if ((this.oneParamOperationCompleted == null)) {
                this.oneParamOperationCompleted = new System.Threading.SendOrPostCallback(this.OnoneParamOperationCompleted);
            }
            this.InvokeAsync("oneParam", new object[] {
                        param1}, this.oneParamOperationCompleted, userState);
        }
        
        private void OnoneParamOperationCompleted(object arg) {
            if ((this.oneParamCompleted != null)) {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.oneParamCompleted(this, new oneParamCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }
        
        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("twoParams", RequestNamespace="http://codefirst.case1.axis1.ws.springframework.org", ResponseNamespace="http://codefirst.case1.axis1.ws.springframework.org")]
        [return: System.Xml.Serialization.SoapElementAttribute("twoParamsReturn")]
        public string twoParams(string param1, string param2) {
            object[] results = this.Invoke("twoParams", new object[] {
                        param1,
                        param2});
            return ((string)(results[0]));
        }
        
        /// <remarks/>
        public void twoParamsAsync(string param1, string param2) {
            this.twoParamsAsync(param1, param2, null);
        }
        
        /// <remarks/>
        public void twoParamsAsync(string param1, string param2, object userState) {
            if ((this.twoParamsOperationCompleted == null)) {
                this.twoParamsOperationCompleted = new System.Threading.SendOrPostCallback(this.OntwoParamsOperationCompleted);
            }
            this.InvokeAsync("twoParams", new object[] {
                        param1,
                        param2}, this.twoParamsOperationCompleted, userState);
        }
        
        private void OntwoParamsOperationCompleted(object arg) {
            if ((this.twoParamsCompleted != null)) {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.twoParamsCompleted(this, new twoParamsCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }
        
        /// <remarks/>
        public new void CancelAsync(object userState) {
            base.CancelAsync(userState);
        }
        
        private bool IsLocalFileSystemWebService(string url) {
            if (((url == null) 
                        || (url == string.Empty))) {
                return false;
            }
            System.Uri wsUri = new System.Uri(url);
            if (((wsUri.Port >= 1024) 
                        && (string.Compare(wsUri.Host, "localHost", System.StringComparison.OrdinalIgnoreCase) == 0))) {
                return true;
            }
            return false;
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.17929")]
    public delegate void oneParamCompletedEventHandler(object sender, oneParamCompletedEventArgs e);
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.17929")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class oneParamCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs {
        
        private object[] results;
        
        internal oneParamCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) : 
                base(exception, cancelled, userState) {
            this.results = results;
        }
        
        /// <remarks/>
        public string Result {
            get {
                this.RaiseExceptionIfNecessary();
                return ((string)(this.results[0]));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.17929")]
    public delegate void twoParamsCompletedEventHandler(object sender, twoParamsCompletedEventArgs e);
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.17929")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class twoParamsCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs {
        
        private object[] results;
        
        internal twoParamsCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) : 
                base(exception, cancelled, userState) {
            this.results = results;
        }
        
        /// <remarks/>
        public string Result {
            get {
                this.RaiseExceptionIfNecessary();
                return ((string)(this.results[0]));
            }
        }
    }
}

#pragma warning restore 1591