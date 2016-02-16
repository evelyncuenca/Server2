
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import=" java.text.SimpleDateFormat, java.util.Date;"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Last-Modified" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
        <meta http-equiv="Pragma" content="no-cache">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documenta S.A.</title>
        <link rel="shortcut icon" href="images/docu.ico"/>
        <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
        <!-- VERSION 29_NOV_2013 -->
        <!-- GC -->
        <!-- LIBS -->
        <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
        <!-- ENDLIBS -->
        <script type="text/javascript" src="js/ext/ext-all.js"></script>

        <script language="JavaScript">
            <!--
            function getMac(accion)
            {
               
                try
                {
                    document.appletAutorizadorTerminal.getMac(accion);
                    //document.a.macCalculado.value =  document.appletAutorizadorTerminal.getMac();
                    return true;
                }
                catch(err)
                {

                    return err.toString();
            }
            
                } 
            //-->
        </script>
    </head>
    <body >
        <script >
            Ext.onReady(function() {
                Ext.QuickTips.init();
                var resultado = getMac(1);
                
                var  userField = new Ext.form.TextField({
                    id:'idUserNameLogin',
                    fieldLabel : 'User name',
                    name : 'loginUsername',
                    allowBlank : false,
                    enableKeyEvents:true,
                    listeners : {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13){
                                Ext.getCmp('passwordLogin').focus(true,true);
                            }

                        }
                    }
                });
                var  passwordField = new Ext.form.TextField( {
                    id:'passwordLogin',
                    fieldLabel : 'Password',
                    name : 'loginPassword',
                    inputType : 'password',
                    allowBlank : false,
                    enableKeyEvents:true,
                    listeners : {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13){
                                buttonLogin.focus(true,true);
                            }

                        }
                    }
                });

                var buttonLogin = new Ext.Button({
                    text : 'Login',
                    formBind : true,
                    repeat:false,
                    handler : function() {
                       
                        buttonLogin.disable();
                        win.focus();
                       
                        if (resultado){

                        login.getForm().submit({
                            method : 'POST',
                            waitTitle : 'Conectando',
                            waitMsg : 'Enviando Datos...',
                            timeout : 300,
                            params:{
                                    mac1: document.appletAutorizadorTerminal.getMac(0),
                                    mac2: document.appletAutorizadorTerminal.getMac(1)
                            },
                            url : 'inicial?op=login',
                            success : function(form,accion) {

                                var obj = Ext.util.JSON.decode(accion.response.responseText);

                                if(obj.success){
                                    var redirect = 'inicial?op=logeado';
                                    window.location = redirect;
                                }
                            },
                            failure : function(form, action) {
                                try{
                                    var obj = Ext.util.JSON.decode(action.response.responseText);
                                    Ext.Msg.show({
                                        title : 'Atención',
                                        msg : obj.motivo,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });

                                }catch(err){
                                    Ext.Msg.show({
                                        title : 'Atención',
                                        msg : 'No se puede realizar el login. Por favor, inténtelo de nuevo',
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });

                                }

                                    

                            }
                        });
                        }

                    }
                });

                var login = new Ext.FormPanel({
                    id : 'idLoginPanel',
                    url : 'inicial?op=login',
                    labelWidth : 80,
                    frame : true,
                    autoWidth : true,
                    defaultType : 'textfield',
                    monitorValid : true,
                    items : [userField,passwordField],

                    buttons : [buttonLogin]
                });
                <%  boolean isALO = request.getParameter("lo")!= null ? true : false;
                    String mensaje = "Su sesi�n ha expirado, favor iniciar sesi�n";

        %>
                var win = new Ext.Window({
                    html : '<p align="center">Copyright   <%
                        Date fecha = new Date();
                        SimpleDateFormat spdf = new SimpleDateFormat("yyyy");
                        out.print(spdf.format(fecha));
            %> Documenta S.A. </p ><p align="center">Todos los Derechos Reservados</p>',
                        width : 300,
                        height : 'auto',
                        closable : false,
                        resizable : false,
                        items : [{
                                title : 'Iniciar Sesión',
                                html : '<p align="center"><img src="images/documenta.jpg" onClick="checkMac();"></p></br></br>'
                            },login]
                    });                           
                    win.show();                          
                        
                           
                });


        </script>       
        <script type="text/javascript" src="js/funcionalidades/deployJava.js"></script>
        <script>
            var attributes = {
                code:       "org.appletinfo.AppletInfo",
                archive:    "AppletInfo.jar",
                name:       "appletAutorizadorTerminal",
                width:      300,
                height:     300
            };
            var parameters = {jnlp_href:"launch.jnlp"}; <!-- Applet Parameters -->
            var version = "1.6"; <!-- Required Java Version -->
            deployJava.runApplet(attributes, parameters, version);
        </script>  
        <!--
         <div style="visibility:hidden">        
             <applet code = "org.appletinfo.AppletInfo"  jnlp_href = "launch.jnlp" name="appletAutorizadorTerminal"  width = "300" height = "300"></applet>
         </div>
        -->

    </body>
</html>
