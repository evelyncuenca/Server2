<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="py.com.konecta.redcobros.gestionweb.web.Constants, java.text.SimpleDateFormat,java.util.Date;"%>

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
        <link rel="shortcut icon" href="images/docu.ico"/>
        <title>Documenta S.A.</title>
        <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />       
        <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="js/ext/ext-all.js"></script>        
        <script language="JavaScript">
            function getMac(accion)
            {
                try {
                    document.appletAutorizadorTerminal.getMac(accion);
                    //document.a.macCalculado.value =  document.appletAutorizadorTerminal.getMac();
                    return true;
                }
                catch (err)
                {
                    //console.log(err);
                    return false;
                }

            }
        </script>
        <script type="text/javascript" >
            Ext.onReady(function () {
                Ext.QuickTips.init();

                var login = new Ext.FormPanel({
                    id: 'login',
                    labelWidth: 80,
                    frame: true,
                    autoWidth: true,
                    defaultType: 'textfield',
                    monitorValid: true,
                    items: [{
                            fieldLabel: 'Usuario',
                            name: '<%=Constants.LOGIN_USERNAME%>',
                            allowBlank: false
                        }, {
                            fieldLabel: 'Password',
                            name: '<%=Constants.LOGIN_PASSWORD%>',
                            inputType: 'password',
                            allowBlank: false,
                            enableKeyEvents: true,
                            listeners: {
                                'specialkey': function (esteObjeto, esteEvento) {

                                    if (esteEvento.getKey() == 13) {
                                        Ext.getCmp('btnLogin').focus(true, true);

                                    }
                                }
                            }
                        }],
                    buttons: [{
                            id: 'btnLogin',
                            text: 'Login',
                            formBind: true,
                            handler: function () {
                                var resultado = getMac(1);
                                if (resultado) {
                                    login.getForm().submit({
                                        method: 'POST',
                                        waitTitle: 'Conectando',
                                        waitMsg: 'Enviando Datos...',
                                        params: {
                                            mac1: document.appletAutorizadorTerminal.getMac(0),
                                            mac2: document.appletAutorizadorTerminal.getMac(1)
                                        },
                                        url: '<%=Constants.LOGIN_URL%>',
                                        success: function (accion) {
                                            var redirect = '<%=Constants.LOGED_URL%>';
                                            window.location = redirect;
                                        },
                                        failure: function (form, action) {
                                            if (action.failureType == 'server') {
                                                obj = Ext.util.JSON.decode(action.response.responseText);
                                                Ext.Msg.alert('Login Fallido!', obj.errors.reason);
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Atención',
                                                    msg: 'El Servidor de Autenticación no esta disponible',
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        }
                                    });
                                }

                            }
                        }]
                });

                var win = new Ext.Window({
                    id: 'ventanaLogin',
                    html: '<p align="center">Copyright\n\
            <%
                Date fecha = new Date();
                SimpleDateFormat spdf = new SimpleDateFormat("yyyy");
                out.print(spdf.format(fecha));
            %> Documenta S.A. </p ><p align="center">Todos los Derechos Reservados</p>',
                    width: 300,
                    height: 'auto',
                    closable: false,
                    resizable: false,
                    items: [{
                            title: 'Iniciar Sesión',
                            html: '<p align="center"><img src="images/documenta.jpg"></p></br></br>'
                        }, login]
                });
                win.show();
            });
        </script>       
    </head>
    <body>
        <script type="text/javascript" src="js/funcionalidades/deployJava.js"></script>
        <script>
            var attributes = {
                code: "org.appletinfo.AppletInfo",
                archive: "AppletInfo.jar",
                name: "appletAutorizadorTerminal",
                width: 300,
                height: 300
            };
            var parameters = {jnlp_href: "launch.jnlp"};<!-- Applet Parameters -->
            var version = "1.6";<!-- Required Java Version -->
            deployJava.runApplet(attributes, parameters, version);
        </script>  
        <!--
         <div style="visibility:hidden">        
             <applet code = "org.appletinfo.AppletInfo"  jnlp_href = "launch.jnlp" name="appletAutorizadorTerminal"  width = "300" height = "300"></applet>
         </div>
        -->
    </body>
</html>
