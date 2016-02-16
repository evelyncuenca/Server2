<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="java.util.*;"%>
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
        <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.notice.css" />
        <script language="JavaScript">

            function getMac(accion)
            {
                try
                {
                    document.appletAutorizadorTerminal.getMac(accion)
                    //document.a.macCalculado.value =  document.appletAutorizadorTerminal.getMac();
                    return true;
                }
                catch (err)
                {

                    return false;
                }

            }

        </script>

        <link rel="stylesheet" type="text/css" href="forms.css" />
        <link rel="stylesheet" type="text/css" href="js/ext/ux/css/RowEditor.css" />
        <script type="text/javascript" language="javascript" src="js/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" language="javascript" src="js/ext/ext-all.js"></script>
        <script type="text/javascript" language="javascript" language="javascript" src="js/areaTrabajo.js"></script>
        <script type="text/javascript" language="javascript" src="js/ext/ux/MultiSelect.js"></script>
        <script type="text/javascript" language="javascript" src="js/ext/ux/ItemSelector.js"></script>
        <script type="text/javascript" src="js/JQuery/jQuery.js"></script>
        <script type="text/javascript" language='javascript' src='js/funcionalidades/jquery.notice.js'></script>

        <script type="text/javascript" language="javascript" src="js/ext/ux/RowEditor.js"></script>

        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteBoletaPorNumeroOrden.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCierreDetallado.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCierreResumido.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaDetalladoCheque.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaDetalladoChequeEfectivo.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaDetalladoEfectivo.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaResumidoChequeEfectivo.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaTapaCaja.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteDeclaracionPorNumeroOrden.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteTapaLoteDetallado.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteTapaLoteResumido.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/Utiles.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/cambiarContrasenha.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ArchivoClearing.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/CerrarGruposRed.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ControlFormularios.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ControlServicios.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ClearingManual.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReImpresionFormulario.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteFormularioOT.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ResumenClearingComision.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteResumenDeTransacciones.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteComisionCS.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteMovimientoFacturadores.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ConsultaContribuyentes.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ConfigClearingServicios.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteTerminalesAbiertas.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCobranzaChequeEfectivoCS.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteCierreCS.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteUsuarioSET.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteResumenFacturador.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteClearingCS.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteFacturacionCS.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/AdminInformacion.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteSanCristobal.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/AltaCuentasACobrar.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteFacturacionAdministracion.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteComision.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/AdminRedRecaudador.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteDigitacion.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ConsultarPase.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/cambiar-imagen.js'></script>
        <script type="text/javascript" language="javascript"  src='js/funcionalidades/ReporteDesembolso.js'></script>
        <!--script type='text/javascript'    src='http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js'></script-->
        <!--script language="javascript" src="js/funcionalidades/areaTrabajo.js"></script-->

        <script language='javascript' src='js/funcionalidades/SolicitudTransaccion.js'></script>
        <script language='javascript' src='js/funcionalidades/AdminEMail.js'></script>

        <!--link rel="stylesheet" type="text/css" href="/js/ext/css/examples.css" /-->
        <link rel="stylesheet" type="text/css" href="tasks.css" />
        <!--link rel="stylesheet" type="text/css" href="layout-browser.css" /-->

        <link rel="stylesheet" type="text/css" href="docs.css" />
        <title>Documenta S.A.</title>
    </head>
    <body>
        <%
            String nombreUsuario = (String) request.getSession().getAttribute("nombresUsuario");
            String apellidosUsuario = (String) request.getSession().getAttribute("apellidosUsuario");
        %>
        <div style="visibility:hidden">
            <applet id ="applet" NAME="appletAutorizadorTerminal" width="0" height="0"  code="org.appletinfo.AppletInfo" archive="AppletInfo.jar"></applet>>
        </div>
        <form name=a  >
            <input id="macCalculadoId" type=hidden name=macCalculado >
        </form>
        <div id="header" style="height: 45px;">
            <div  class ="api-title" align="center">
                <b><p>RED DE COBROS - DOCUMENTA</p></b>
            </div>
            <div  class ="api-title" align="right">
                <b><p>Usuario: <%out.print(apellidosUsuario + ", " + nombreUsuario);%>-V?05.07.14-<%=request.getLocalName()%></p></b>
            </div>
        </div>

        <!-- Contenido del Start -->
        <div id="start-div">
            <div style="float:left;" ><img src="images/layout-icon.gif" /></div>
            <div style="margin-left:100px;">
                <h2>Bienvenido!</h2>
                <p></p>
                <p>Seleccione una opción para trabajar</p>
            </div>
        </div>
        <div  id="center" align="center">


        </div>
        <ul id="task-complejas" class="x-hidden">

            <li >
                <img src="images/s.gif" class="hab-fact-red"/>
                <a id="HABILITACION_FACTURADORES_RED" href="#">Hab. Facturadores a Red<span class="s"></span> </a>
            </li>

            <li >
                <img src="images/s.gif"  class="hab-serv-rec"/>
                <a id="HABILITACION_SEVICIOS_RECAUDADORES" href="#">Hab. Serv. a Recaudadores<span class="s"></span> </a>
            </li>


        </ul>

        <ul id="task-operaciones" class="x-hidden">
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CONSULTAR_PASE" href="#">Consultar Pase<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CERRAR_GRUPOS_RED" href="#">Cerrar Grupos de la Red<span class="s"></span> </a>
            </li>

            <li >
                <img src="images/s.gif" class="generar-archivos"/>
                <a id="GENERAR_ERA_FORMULARIOS" href="#">Generar ERA Formularios<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif"  class="generar-archivos"/>
                <a id="GENERAR_ERA_BOLETAS_PAGOS_EFECTIVO" href="#">Gen. ERA Bol. Pago-Efect.<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif"  class="generar-archivos"/>
                <a id="GENERAR_ERA_BOLETAS_PAGOS_CHEQUE" href="#">Gen. ERA Bol. Pago-Cheque<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif"  class="generar-archivos"/>
                <a id="GENERAR_ARCHIVO_CLEARING_COMISION" href="#">Gen. Clear. Comisión<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif"  class="generar-archivos"/>
                <a id="GENERAR_ARCHIVO_CLEARING_FACTURACION" href="#">Gen. Clear. Facturación<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif"  class="generar-archivos"/>
                <a id="GENERAR_CLEARING_MANUAL" href="#">Gen. Arch. Clearing Manual<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CONSULTA_CONTRIBUYENTES" href="#">Consulta Contribuyentes<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CLEARING_MANUAL" href="#">Clearing Manual<span class="s"></span> </a>
            </li>

            <li>
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='RE_IMPRESION_FORMULARIO' href='#'>Reimpresión Formulario<span class='s'></span> </a>
            </li>

            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CAMBIAR_PASSWORD" href="#">Cambiar Password<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="CAPTURADOR_ARCHIVO" href="#">Capturador Archivos<span class="s"></span> </a>
            </li>
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='CONTROL_FORMULARIOS' href='#'>Control Formularios/Boletas<span class='s'></span> </a>
            </li >
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="MULTICANAL_CARGAR_ARCHIVO_FACTURACION" href="#">Archivo Facturacion<span class="s"></span> </a>
            </li>
            <li >
                <img src="images/s.gif" class="cerrar-cajas"/>
                <a id="LOGOUT" href="#">Logout<span class="s"></span> </a>
            </li>
            <li >
                <img src='images/save.gif' class='cerrar-cajas'/>
                <a id='CAMBIAR_IMAGEN_SERV' href='#'>Cambiar Imagenes<span class='s'></span> </a>
            </li >
        </ul>
        <ul id="task-operaciones-cs" class="x-hidden">
            <li>
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='CONTROL_SERVICIOS' href='#'>Control Transacciones<span class='s'></span> </a>
            </li>
            <li>
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='INFORMACION_SYS' href='#'>Admin Sys Info<span class='s'></span> </a>
            </li>
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='ALTA_CUENTAS_A_COBRAR' href='#'>Upload Archivo Facturas<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='ADMIN_RED_RECAUDADOR' href='#'>Admin Red Recaudador<span class='s'></span> </a>
            </li >
        </ul>
        <ul id="task-reportes" class="x-hidden">

            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_DECLARACION_POR_NUMERO_ORDEN' href='#'>DDJJ por Núm. Orden<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_TAPA_CAJA' href='#'>Tapa Caja<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN' href='#'>Boleta Pago por Núm. Orden<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO' href='#'>Cobranza Cheque-Efectivo<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO' href='#'>Cob. Det. Cheque-Efectivo<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COBRANZA_DETALLADO_CHEQUE' href='#'>Cobranza Det. Cheque<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COBRANZA_DETALLADO_EFECTIVO' href='#'>Cobranza Det. Efectivo<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_FORMULARIO_OT' href='#'>Formulario OT<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_RECAUDACION' href='#'>Resumen de Pagos/DDJJ<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CLEARING_COMISION_RESUMIDO' href='#'>Resumen Clearing Comisión<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='TAPA_LOTE' href='#'>Tapa Lote<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='TAPA_LOTE_DETALLADO' href='#'>Tapa Lote Detallado<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_DIGITACION' href='#'>Reporte de Digitaciones<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CIERRE_RESUMIDO' href='#'>Cierre Resumido<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CIERRE_DETALLADO' href='#'>Cierre Detallado<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CIERRE_USUARIOS' href='#'>Detalle de Pagos/DDJJ<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_TERMINALES_ABIERTAS' href='#'>Terminales Abiertas<span class='s'></span> </a>
            </li >
        </ul>
        <ul id="task-reportes-fac" class="x-hidden">
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COMISION_DET_FAC' href='#'>Comision Detallado<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COMISION_RES_FAC' href='#'>Comision Res. Servicio/SET<span class='s'></span> </a>
            </li >
        </ul>
        <ul id="task-reportes-rec" class="x-hidden">
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COMISION_DET_REC' href='#'>Comision Detallado<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COMISION_RES_GRAL_REC' href='#'>Comision Resumido/Gral<span class='s'></span> </a>
            </li >
        </ul>
        <ul id="task-reportes-cs" class="x-hidden">
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_FACTURACION_ADMINISTRACION' href='#'>Reporte Administracion<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_SAN_CRISTOBAL' href='#'>Reporte San Cristobal<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CIERRE_CS' href='#'>Reporte Cierre<span class='s'></span> </a>
            </li >

            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COBRANZA_CS' href='#'>Reporte Cobranza<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_FACTURADOR_CS' href='#'>Reporte Facturador<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_COMISION_CS' href='#'>Reporte Comision<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_MOVIMIENTO_FACTURADORES' href='#'>Reporte Mov. Facturador<span class='s'></span> </a>
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_CLEARING_CS' href='#'>Reporte Clearing CS<span class='s'></span> </a>
            </li >     
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='REPORTE_DESEMBOLSO' href='#'>Reporte Desembolso<span class='s'></span> </a>
            </li >   
        </ul>
        <!--/***** HERRAMIENTAS *****/-->
        <ul id='toolsDocumenta' class='x-hidden'>
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='SOLICITUD_TRANSACCION' href='#'>Generar PDF Transacciones<span class='s'></span> </a
            </li >
            <li >
                <img src='images/s.gif' class='cerrar-cajas'/>
                <a id='ADMIN_MAIL' href='#'>Admin E-Mail<span class='s'></span> </a>
            </li >
        </ul>  
    </body>
</html>
