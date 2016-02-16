<%--
    Document   : areaTrabajo
    Created on : 03-feb-2009, 17:00:19
    Author     : DIego Mendez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="java.util.*, py.com.konecta.redcobros.cobrosweb.utiles.*"%>
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
        <script type="text/javascript"  language="javascript" src="js/funcionalidades/Utiles.js"></script>
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="js/ext/ux/css/RowEditor.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.notice.css" />

        <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="js/ext/ext-all.js"></script>
        <script type="text/javascript" language='javascript' src='js/funcionalidades/cambiarContrasenha.js'></script>
        <script type="text/javascript" src="js/ext/ux/SearchField.js"></script>
        <script type="text/javascript" src="js/JQuery/jQuery.js"></script>

        <script type="text/javascript" language="javascript" src="js/ext/ux/MultiSelect.js"></script>
        <script type="text/javascript" language="javascript" src="js/ext/ux/ItemSelector.js"></script>

        <script type="text/javascript" language="javascript" src="js/ext/ux/RowEditor.js"></script>

        <script type="text/javascript" language="javascript" src="js/ext/ux/Ext.ux.Plugin.RemoteComponent.js"></script>
        <script type="text/javascript" language='javascript' src='js/funcionalidades/jquery.notice.js'></script>
        <script type="text/javascript" language='javascript' src='js/funcionalidades/shortcut.js'></script>
        <script type="text/javascript" language='javascript' src='js/JQuery/jquery-1.9.0.js'></script>
        <script type="text/javascript" language='javascript' src='js/JQuery/blockUI.js'></script>
        <jsp:include page="js/funcionalidades/validadorDetalleFormularios.jsp"/>


        <link rel="stylesheet" type="text/css" href="tasks.css" />
        <link rel="stylesheet" type="text/css" href="layout-browser.css" />
        <link rel="stylesheet" type="text/css" href="docs.css" />
        <link rel="stylesheet" type="text/css" href="css/combos.css" />
        <style type="text/css">

            .timer {
                text-align: center;
                background: white;
                font-family: Arial;
                font-weight: bold;
                border: 0px ;
                border-left: 0px ;
                border-top: 0px ;
            }

        </style>


        <%

            String DIGITACION_DIGI_FORMS = "DIGI_FORMS";
            String DIGITACION_PAGO_FORMS = "COBRO_CON_FORMULARIO";
            String DIGITACION_PAGO_BOLETAS = "COBRO_CON_BOLETA_DE_PAGO";

            String OPERACIONES_APERTURA_SIMPLE = "APERTURA_SIMPLE";
            String OPERACIONES_APERTURA_MULTIPLE = "APERTURA_MULTIPLE";
            String OPERACIONES_CERRAR_GESTION = "CERRAR_GESTION";
            String OPERACIONES_ANULAR_TRANSACCION = "ANULAR_TRANSACCION";
            String OPERACIONES_ANULAR_DIGITACION = "ANULAR_DIGITACION";
            String OPERACIONES_REIMPRESION = "REIMPRIMIR";
            String OPERACIONES_REIMPRESION_FORMULARIO = "REIMPRIMIR_FORMULARIO";
            String OPERACIONES_REIMPRESION_TICKET_CS = "REIMPRIMIR_CS";
            String OPERACIONES_ANULAR_TRANSACCION_CS = "ANULAR_TRANSACCION_CS";
            String OPERACIONES_LOGOUT = "LOGOUT";
            String OPERACIONES_CAMBIAR_PASSWORD = "CAMBIAR_PASSWORD";
            String OPERACIONES_DETALLE_TRANSA_A_ANULAR = "DETALLE_TRANSA_A_ANULAR";
            String OPERACIONES_DETALLE_DIGITACION_A_ANULAR = "DETALLE_DIGITACION_A_ANULAR";
            String OPERACIONES_CONSULTAR_CONTRIBUYENTES = "CONSULTAR_CONTRIBUYENTE";
            String OPERACIONES_COBRO_SERVICIOS_CODIGO_BARRA = "COBRO_SERVICIOS_CB";
            String OPERACIONES_COBRO_TARJETAS = "COBRO_TARJETAS";
            String OPERACIONES_COBRANZA_MULTIPLE = "COBRANZA_MULTIPLE";
            String OPERACIONES_DESCARGAR_ARCHIVO_COBRANZA = "DESCARGAR_ARCHIVO_COBRANZA";
            String REPORTE_CLEARING_COMISION_RESUMIDO = "REPORTE_CLEARING_COMISION_RESUMIDO";
            String REPORTE_RESUMEN_FACTURADOR = "REPORTE_RESUMEN_FACTURADOR";
            String REPORTE_DIGITACION = "REPORTE-DIGITACION";

            String OPERACIONES_AYUDA = "AYUDA";
            String OPERACIONES_EVENTO = "EVENTO";
            String OPERACIONES_ENVIOS_PERSONAL = "ENVIOS_PERSONAL";
            String OPERACIONES_FAST_BOX = "FAST_BOX";
            String OPERACIONES_GANEMAX = "GANEMAX";
            String OPERACIONES_ICED = "ICED";
            String OPERACIONES_PURPURA = "PURPURA";
            String OPERACIONES_CARGILL = "CARGILL";
            String OPERACIONES_REMESAS_CONTI = "REMESAS_CONTI";
            String OPERACIONES_FPJ_TARJETA = "FPJ_TARJETA";
            String OPERACIONES_FPJ_ADELANTO = "FPJ_ADELANTO";
            String OPERACIONES_MORE_MT = "MORE_MT";
            String OPERACIONES_DEPOSITO_EXTRACCION = "DEPOSITO_EXTRACCION";
            String OPERACIONES_PODER_JUDICIAL = "PODER_JUDICIAL";
            String OPERACIONES_COBRO_DESDE_ARCHIVO = "COBRO_DESDE_ARCHIVO";
            String OPERACIONES_CONSULTA_PAGO = "CONSULTA_Y_PAGO";
            String OPERACIONES_VENTA_DE_MINUTOS = "VENTA_DE_MINUTOS";
            String OPERACIONES_ALTA_CUENTAS_A_COBRAR = "ALTA_CUENTAS_A_COBRAR";
            String OPERACIONES_ADELANTO_EFECTIVO = "ADELANTO_EFECTIVO";
            String OPERACIONES_DESEMBOLSO_MASIVO = "DESEMBOLSO_MASIVO";
            String OPERACIONES_REMESAS = "REMESAS";
            String OPERACIONES_REMESAS_2 = "REMESAS_V2";

            String OPERACIONES_CONTROL_FORMULARIOS_BOLETAS = "CONTROL_FORMULARIOS_BOLETAS";

            String REPORTES_BOLETA_NUM_ORDEN = "BP-ORDEN";
            String REPORTES_CIERRE_DETALLADO = "CIERRE-DETALLADO";
            String REPORTES_CIERRE_RESUMIDO = "CIERRE-RESUMIDO";
            String REPORTES_CIERRE_CS = "CIERRE-COBRO-SERVICIOS";
            String REPORTES_COBRANZA_DETALLADO_CHEQUE = "COB-DET-CHEQUE";
            String REPORTES_COBRANZA_DETALLADO_CHEQUE_EFECTIVO = "COB-DET";
            String REPORTES_COBRANZA_DETALLADO_EFECTIVO = "COB-DET-EFECT";
            String REPORTES_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO = "COB-RESUM";
            String REPORTES_DECLARACION_NUM_ORDEN = "DDJJ-ORDEN";
            String REPORTES_TAPALOTE_DETALLADO = "TAPA-LOTE-GROUP-DET";
            String REPORTES_TAPALOTE_RESUMIDO = "TAPA-LOTE-GROUP";
            String REPORTES_AUTORIZAR_REPORTE = "AUTORIZAR_REPORTE";
            String REPORTES_RESUMEN_DE_TRANSACCIONES = "RESUMEN_DE_TRANSACCIONES";
            String REPORTES_CIERRE_USUARIOS = "REPORTES_CIERRE_USUARIOS";
            String REPORTES_COBRANZA_CS = "REPORTES_COBRANZA_CS";
            String RESUMEN_COBRANZA_CS = "RESUMEN_COBRANZA_CS";
            String REPORTE_CIERRE_CS = "REPORTE_CIERRE_CS";
            String REPORTE_FACTURADOR_GESTION = "REPORTE_FACTURADOR_GESTION";

            String CONFIGURACIONES_USUARIO = "USUARIO";
            String CONFIGURACIONES_TERMINAL = "TERMINAL";
            String CONFIGURACIONES_USUARIO_TERMINAL = "USUARIO_TERMINAL";
            String CONFIGURACIONES_FRANJA_HORARIA = "FRANJA_HORARIA";
            String CONFIGURACIONES_ADMIN_PERMISOS = "ADMIN_PERMISOS";
            String CONFIGURACIONES_ADMIN_EVENTOS = "ADMIN_EVENTOS";
            String CONFIGURACIONES_ADMIN_GESTORES = "ADMIN_GESTORES";
            String CONFIGURACIONES_SORTEADOR_GESTOR = "SORTEADOR_GESTOR";
            String CONFIGURACIONES_ADMIN_FACTURAS = "ADMIN_FACTURAS";
            Boolean reportesCS = false;
            Boolean reportesDGR = false;
            Boolean operacionesCS = false;
            Boolean operacionesDGR = false;

            String COBRANZAS_MULTICANAL = "COBRANZAS_MULTICANAL";

            HashMap<String, HashMap<String, String>> task = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("TASKS");
            String nombreUsuario = (String) request.getSession().getAttribute("nombresUsuario");
            String apellidosUsuario = (String) request.getSession().getAttribute("apellidosUsuario");
            String codTerminal = (String) request.getSession().getAttribute("codTerminal");
            Long timeToEndSesion = (Long) request.getSession().getAttribute("timeToEndSesion");
            Boolean refresh = request.getSession().getAttribute("refresh") != null ? (Boolean) request.getSession().getAttribute("refresh") : true;
            request.getSession().setAttribute("refresh", false);
            if (task.get("MULTICANAL") != null) {
                if (task.get("MULTICANAL").get(COBRANZAS_MULTICANAL) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Multicanal.js'></script>");
                }

            }
            if (task.get("DIGITACION") != null) {
                if (task.get("DIGITACION").get(DIGITACION_DIGI_FORMS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Digitacion.js'></script>");
                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_FORMS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobranzaConFormulario.js'></script>");
                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_BOLETAS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobranzaConBoletaPago.js'></script>");
                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_BOLETAS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobranzaConBoletaPagoCyp.js'></script>");
                }
            }
            if (task.get("OPERACIONES") != null) {
                if ((task.get("OPERACIONES").get(OPERACIONES_APERTURA_MULTIPLE) != null) || (task.get("OPERACIONES").get(OPERACIONES_APERTURA_SIMPLE) != null)) {
                    out.println("<script language='javascript' src='js/funcionalidades/AbrirGestion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_DIGITACION) != null && (task.get("OPERACIONES").get(OPERACIONES_DETALLE_DIGITACION_A_ANULAR) != null)) {
                    out.println("<script language='javascript' src='js/funcionalidades/AnularDigitacion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION) != null && (task.get("OPERACIONES").get(OPERACIONES_DETALLE_TRANSA_A_ANULAR) != null)) {
                    out.println("<script language='javascript' src='js/funcionalidades/AnularTransaccion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION_CS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/AnularTransaccionCS.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CERRAR_GESTION) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CerrarGestion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReImpresion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_FORMULARIO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReImpresionFormulario.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CONSULTAR_CONTRIBUYENTES) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ConsultaContribuyentes.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_SERVICIOS_CODIGO_BARRA) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobroServiciosCodigoBarra.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_DESDE_ARCHIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobranzaDesdeArchivo.js'></script>");
                }
                if (task.get("COMODINES").get(OPERACIONES_AYUDA) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Ayuda.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_EVENTO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Evento.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_TICKET_CS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReImpresionTicketCS.js'></script>");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_CONTROL_FORMULARIOS_BOLETAS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ControlFormularios.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CONSULTA_PAGO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ConsultaYPago.js'></script>");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_VENTA_DE_MINUTOS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Recargas.js'></script>");
                    out.println("<script language='javascript' src='js/funcionalidades/PagoFacturaTelefonias.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRANZA_MULTIPLE) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobranzaMultiple.js'></script>");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_TARJETAS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/CobroTarjetas.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ALTA_CUENTAS_A_COBRAR) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/AltaCuentasACobrar.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_DESCARGAR_ARCHIVO_COBRANZA) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ArchivoCobranza.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ENVIOS_PERSONAL) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/EnviosPersonal.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_FAST_BOX) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/fastBox.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_GANEMAX) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ganemax.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ICED) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/iced.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_PURPURA) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/purpura.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CARGILL) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/cargill.js'></script>");
                }              
                if (task.get("OPERACIONES").get(OPERACIONES_REMESAS_CONTI) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/RemesasConti.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REMESAS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/PractiGiros.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REMESAS_2) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/PractiGiros_2.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_MORE_MT) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/MoreMT.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_FPJ_TARJETA) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/FpjTarjeta.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_FPJ_ADELANTO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/FpjAdelanto.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_DEPOSITO_EXTRACCION) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/DepositoExtraccion.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_PODER_JUDICIAL) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/PoderJudicial.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ADELANTO_EFECTIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/Adelanto.js'></script>");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_DESEMBOLSO_MASIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/DesembolsoMasivo.js'></script>");
                }

            }
            if (task.get("REPORTES") != null) {
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCierreCS.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCobranzaChequeEfectivoCS.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_BOLETA_NUM_ORDEN) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteBoletaPorNumeroOrden.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_DETALLADO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCierreDetallado.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_RESUMIDO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCierreResumido.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCobranzaDetalladoCheque.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE_EFECTIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCobranzaDetalladoChequeEfectivo.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_EFECTIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCobranzaDetalladoEfectivo.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteCobranzaResumidoChequeEfectivo.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_DECLARACION_NUM_ORDEN) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteDeclaracionPorNumeroOrden.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_TAPALOTE_DETALLADO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteTapaLoteDetallado.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_TAPALOTE_RESUMIDO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteTapaLoteResumido.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_RESUMEN_DE_TRANSACCIONES) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteResumenDeTransacciones.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_USUARIOS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteUsuarioSET.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_CS) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ResumenCobranzasCS.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTE_CLEARING_COMISION_RESUMIDO) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ResumenClearingComision.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTE_RESUMEN_FACTURADOR) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteResumenFacturador.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTE_DIGITACION) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteDigitacion.js'></script>");
                }
                if (task.get("REPORTES").get(REPORTE_FACTURADOR_GESTION) != null) {
                    out.println("<script language='javascript' src='js/funcionalidades/ReporteResumenFacturadorGestion.js'></script>");
                }
            }
            if (true) {
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Usuario.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Terminal.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/UsuarioTerminal.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/FranjaHoraria.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/AdminPermisos.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Persona.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Localidad.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Ciudad.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Departamento.js'></script>");
                }
                if (true) {
                    out.println("<script language='javascript' src='js/funcionalidades/Pais.js'></script>");
                }
                if (task.get("CONFIGURACIONES") != null) {
                    if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_GESTORES) != null) {
                        out.println("<script language='javascript' src='js/funcionalidades/AdminGestores.js'></script>");
                    }
                    if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_SORTEADOR_GESTOR) != null) {
                        out.println("<script language='javascript' src='js/funcionalidades/SorteadorGestores.js'></script>");
                    }
                    if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_FACTURAS) != null) {
                        out.println("<script language='javascript' src='js/funcionalidades/AdminFacturas.js'></script>");
                    }
                    if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_EVENTOS) != null) {
                        out.println("<script language='javascript' src='js/funcionalidades/AdminEventos.js'></script>");
                    }
                }
            }
            out.println("<script language='javascript' src='js/areaTrabajo.js'></script>");
        %>
        <title>Documenta S.A.</title>
    </head>
    <body <%out.print(refresh ? "onload=\"document.location.reload(true);\"" : "");%>>

        <div style="visibility:hidden">
            <applet id ="applet" NAME="appletAutorizadorTerminal" width="0" height="0"  code="org.appletinfo.AppletInfo" archive="AppletInfo.jar"></applet>
            <applet id ="applet" NAME="appletGenBarCode" width="0" height="0"  code="py.com.documenta.appletGeneratorBC.BarCodeGenerator" archive="AppletGenBarCode.jar"></applet>
        </div>

        <div id="CH_dtimer1_digits" class="timer" align="left"></div>
        <div style="visibility:hidden" >
            <label >Horas:
                <input id="CH_dtimer1_hours" type="text" size="2" disabled="disabled" />
            </label>
            <label >Minutos:
                <input id="CH_dtimer1_minutes"  type="text" size="2" disabled="disabled"  />
            </label>
            <label >Segundos:
                <input id="CH_dtimer1_seconds" type="text" size="2" disabled="disabled" />
            </label>
        </div>
        <div style="visibility:hidden" >
            <input id="CH_dtimer1_start" type="button" value="Start" disabled="disabled" />
            <input id="CH_dtimer1_pause" type="button" value="Pause" disabled="disabled" />
            <input id="CH_dtimer1_resume"
                   type="button" value="Resume" disabled="disabled" />
            <input id="CH_dtimer1_reset" type="button" value="Reset" disabled="disabled" />
        </div>

        <div class="x-hide-display" id="tabs" align="center" ></div>
        <div id="header" style="height: 45px;">
            <div  class ="api-title" align="center">
                <b><p title="">RED DE COBROS - DOCUMENTA</p></b>
            </div>            
            <div class ="api-title" align="right">          
                <script>var timeToEnd = <%out.println(timeToEndSesion);%></script>
                <b><p>Usuario: <%out.print(apellidosUsuario + ", " + nombreUsuario);%> - Terminal:<%out.print(codTerminal);%><input style="width:150px" type="submit" id="idGetPase" value="Generar Pase" onclick="generarPase();"/></p></b>                
            </div>            
        </div>

        <div id="start-div">
            <h1 style="font-size:160%;background-color:red">EVITE EL FRAUDE, Recuerde que NADIE le puede solicitar en forma telefónica, realizar  operaciones de cobro en el sistema (Recargas, Envíos de Dinero, pago de factura), si accede lo más probable es que sea víctima de una estafa</h1>
            <div style="float:left;" ><img src="images/documenta.jpg" /></div>
            <div style="margin-left:5px;">
                <br/>
                <h1>  Bienvenido!</h1>
                <p></p>
                <p>  Seleccione un servicio para trabajar.</p>
                <p>  Shortcut a los servicios "Alt+código"</p>

            </div>
        </div>
        <div id="help-div"></div>
        <script type="text/javascript" src="js/funcionalidades/coolCount.js"></script>
        <%
            List<String> componentes = new ArrayList();
            List<String> listaDeDivs = new ArrayList();

            /*
             * listaDeDivs.add("div-view-digitacion-formulario");
             * listaDeDivs.add("div-view-cobro-servicios-CB");
             * listaDeDivs.add("div-action-pago-formulario");
             * listaDeDivs.add("div-view-cobro-servicios-manual");
             listaDeDivs.add("div-action-pago-boleta-pago");
             */
            componentes.add("start()");

            if (task.get("OPERACIONES") != null) {
                if (task.get("OPERACIONES").get(OPERACIONES_FPJ_TARJETA) != null) {
                    out.println("<div id='div-view-fpj-tarjeta' >");

                    out.println("<A HREF='#'  ><img ONCLICK='cardFPJTarjeta();' src='images/btnFPJTarjeta.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelFPJTarjeta()");
                    listaDeDivs.add("div-view-fpj-tarjeta");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_FPJ_ADELANTO) != null) {
                    out.println("<div id='div-view-fpj-adelanto' >");

                    out.println("<A HREF='#'  ><img ONCLICK='cardFPJAdelanto();' src='images/btnFPJAdelanto.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelFPJAdelanto()");
                    listaDeDivs.add("div-view-fpj-adelanto");

                }
            }
            if (task.get("DIGITACION") != null) {
                // out.println("<ul id='task-set' class='x-hidden'>");

                if (task.get("DIGITACION").get(DIGITACION_DIGI_FORMS) != null) {
                    out.println("<div id='div-view-digitacion-formulario'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardDigitacionFormulario();' src='images/btnDigitarFormulariosSet.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");

                    //out.println("<A HREF='#' onmouseout= document.getElementById('idImagenDigForm').src='images/btnDigitarFormulariosSet.PNG' onmouseover= document.getElementById('idImagenDigForm').src='images/btnDigitarFormulariosSet2.PNG'; ONCLICK='cardDigitacionFormulario();' style='cursor:hand'><img id = 'idImagenDigForm' src='images/btnDigitarFormulariosSet.PNG' /></A>");
                    out.println(" </div>");
                    // out.println("<li >");
                    // out.println("<img src='images/s.gif' class='digitacion-formulario'/>");
                    // out.println(" <a id='view-digitacion-formulario' href='#'>Digitación Formulario<span class='s'>s</span> </a>");
                    //  out.println("</li >");
                    componentes.add("panelRegistroCargaFormulario()");
                    listaDeDivs.add("div-view-digitacion-formulario");
                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_FORMS) != null) {
                    out.println("<div id='div-action-pago-formulario'>");
                    out.println("<A HREF='#' ><img ONCLICK='cardPagoFormualario();' src='images/btnPagoFormularioSet.PNG' style='opacity:0.4;filter:alpha(opacity=40); cursor:hand'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4'/></A>");
                    // out.println("<A HREF='#' onmouseout= document.getElementById('idImagenPagoForm').src='images/btnPagoFormularioSet.PNG' onmouseover= document.getElementById('idImagenPagoForm').src='images/btnPagoFormularioSet2.PNG'; ONCLICK='cardPagoFormualario();' style='cursor:hand'><img id ='idImagenPagoForm' src='images/btnPagoFormularioSet.PNG' /></A>");
                    out.println(" </div>");
                    /*
                     * out.println("<li >"); out.println("<img
                     * src='images/s.gif' class='pago-con-formulario'/>");
                     * out.println(" <a id='action-pago-formulario'
                     * href='#'>Pago con Formulario<span class='s'>s</span>
                     * </a>"); out.println("</li >");
                     *
                     */
                    componentes.add("panelCobranzaPagoConFormulario()");
                    listaDeDivs.add("div-action-pago-formulario");

                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_BOLETAS) != null) {
                    out.println("<div id='div-action-pago-boleta-pago'>");
                    out.println("<A HREF='#' ><img ONCLICK='cardPagoBoleta();' src='images/btnPagoBoletaset.PNG' style='opacity:0.4;filter:alpha(opacity=40); cursor:hand'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4'/></A>");
                    // out.println("<A HREF='#' onmouseout= document.getElementById('idImagenPagoBoleta').src='images/btnPagoBoletaset.PNG' onmouseover= document.getElementById('idImagenPagoBoleta').src='images/btnPagoBoletaset2.PNG'; ONCLICK='cardPagoBoleta();' style='cursor:hand' >  <img id ='idImagenPagoBoleta' src='images/btnPagoBoletaset.PNG' /></A>");
                    out.println(" </div>");

                    /*
                     * out.println("<li >"); out.println("<img
                     * src='images/s.gif' class='pago-con-boleta'/>");
                     * out.println("<a id='action-pago-boleta-pago'
                     * href='#'>Pago con Boleta de Pago<span
                     * class='s'>s</span></a>"); out.println("</li >");
                     */
                    componentes.add("panelCobranzaPagoConBoletaDePago()");
                    listaDeDivs.add("div-action-pago-boleta-pago");

                }
                if (task.get("DIGITACION").get(DIGITACION_PAGO_BOLETAS) != null) {
                    out.println("<div id='div-action-pago-boleta-pago-cyp'>");
                    out.println("<A HREF='#' ><img ONCLICK='cardPagoBoletaCyp();' src='images/btnConsultaPagoSet.PNG' style='opacity:0.4;filter:alpha(opacity=40); cursor:hand'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4'/></A>");
                    // out.println("<A HREF='#' onmouseout= document.getElementById('idImagenPagoBoleta').src='images/btnPagoBoletaset.PNG' onmouseover= document.getElementById('idImagenPagoBoleta').src='images/btnPagoBoletaset2.PNG'; ONCLICK='cardPagoBoleta();' style='cursor:hand' >  <img id ='idImagenPagoBoleta' src='images/btnPagoBoletaset.PNG' /></A>");
                    out.println(" </div>");

                    /*
                     * out.println("<li >"); out.println("<img
                     * src='images/s.gif' class='pago-con-boleta'/>");
                     * out.println("<a id='action-pago-boleta-pago'
                     * href='#'>Pago con Boleta de Pago<span
                     * class='s'>s</span></a>"); out.println("</li >");
                     */
                    componentes.add("panelCobranzaPagoConBoletaDePagoCyp()");
                    listaDeDivs.add("div-action-pago-boleta-pago-cyp");

                }
                if (task.get("MULTICANAL") != null) {

                    if (task.get("MULTICANAL").get(COBRANZAS_MULTICANAL) != null) {
                        out.println("<div id='div-view-cobranzas-multicanal'>");

                        out.println("<A HREF='#'  ><img ONCLICK='cardMulticanal();' src='images/btnMulticanal.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                                + "onmouseover='this.style.opacity=1'"
                                + "onmouseout='this.style.opacity=0.4 ' /></A>");
                        out.println(" </div>");

                        // componentes.add("panelCobranzaMuticanal()");
                        componentes.add("cobranzasMulticanal()");
                        listaDeDivs.add("div-view-cobranzas-multicanal");
                    }
                }
                //out.println(" </ul>");
            }
            if (task.get("OPERACIONES") != null) {
                out.println(" <ul id='task-operaciones-sys' class='x-hidden'>");
                if ((task.get("OPERACIONES").get(OPERACIONES_APERTURA_MULTIPLE) != null) || (task.get("OPERACIONES").get(OPERACIONES_APERTURA_SIMPLE) != null)) {
                    out.println("<li >");
                    out.println("<img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("<a id='ABRIR_GESTION' href='#'>Apertura de Caja<span class='s'></span> </a>");
                    out.println("</li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_CERRAR_GESTION) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='CERRAR_GESTION' href='#'>Cierre de Caja<span class='s'></span> </a>");
                    out.println("   </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_DESCARGAR_ARCHIVO_COBRANZA) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='DESCARGAR_ARCHIVO_COBRANZA' href='#'>Descargar Arch. Cobranza<span class='s'></span> </a>");
                    out.println("   </li >");

                }
                if (task.get("COMODINES").get(OPERACIONES_CAMBIAR_PASSWORD) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='CAMBIAR_PASSWORD' href='#'>Cambiar Password<span class='s'></span> </a>");
                    out.println("  </li >");

                }
                if (task.get("COMODINES").get(OPERACIONES_LOGOUT) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='logout'/>");
                    out.println("    <a id='LOGOUT' href='#'>Cerrar Sesion<span class='s'></span> </a>");
                    out.println("  </li >");

                }
                out.println(" </ul>");
            }

            if (task.get("OPERACIONES") != null) {
                out.println(" <ul id='task-operaciones' class='x-hidden'>");
                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION) != null) {
                    operacionesDGR = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='RE_IMPRESION' href='#'>Reimpresión Transacción<span class='s'></span> </a>");
                    out.println(" </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_FORMULARIO) != null) {
                    operacionesDGR = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='RE_IMPRESION_FORMULARIO' href='#'>Reimpresión Formulario<span class='s'></span> </a>");
                    out.println(" </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_CONSULTAR_CONTRIBUYENTES) != null) {
                    operacionesDGR = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='CONSULTA_CONTRIBUYENTES' href='#'>Consulta Contribuyentes<span class='s'></span> </a>");
                    out.println(" </li >");
                    componentes.add("panelConsultaContribuyentes()");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_TARJETAS) != null) {
                    out.println("<div id='div-view-cobro-servicios-tarjetas'>");
                    out.println("<A HREF='#'><img ONCLICK='cardCobroTarjetas();' src='images/btnPagoTarjetas.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelCobroTarjetas()");
                    componentes.add("panelConfirmacionTarjetas()");
                    componentes.add("panelTicketTarjetas()");
                    listaDeDivs.add("div-view-cobro-servicios-tarjetas");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_SERVICIOS_CODIGO_BARRA) != null) {
                    out.println("<div id='div-view-cobro-servicios-CB'>");
                    out.println("<A HREF='#'><img ONCLICK='cardCobroServiciosCodigoBarra();' src='images/btnCobroServiciosCB.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelCobroConCB()");
                    componentes.add("panelConfirmacionCB()");
                    componentes.add("panelTicketCB()");
                    listaDeDivs.add("div-view-cobro-servicios-CB");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRO_DESDE_ARCHIVO) != null) {
                    out.println("<div id='div-view-cobro-servicios-desde-archivo'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardCobroDesdeArchivo();' src='images/btnCobroDesdeArchivo.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    //componentes.add("panelCobroDesdeArchivo()");
                    listaDeDivs.add("div-view-cobro-servicios-desde-archivo");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CONSULTA_PAGO) != null) {
                    out.println("<div id='div-view-consulta-y-pago'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardConsultaPago();' src='images/btnConsultaPago.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    // componentes.add("panelCabeceraServicio()");
                    componentes.add("panelConsultaYPago()");
                    componentes.add("panelConsulta()");
                    //componentes.add("panelGrillaServicios()");
                    //componentes.add("panelConsulta()");
                    listaDeDivs.add("div-view-consulta-y-pago");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_VENTA_DE_MINUTOS) != null) {
                    out.println("<div id='div-view-venta-de-minutos'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardTelefonias();' src='images/btnVentaMinutos.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelCabeceraServicioXC()");
                    componentes.add("panelVentaDeMinutos()");
                    componentes.add("panelPagoFacXNroTel()");
                    componentes.add("panelPagoFacturaPorNumeroCuenta()");
                    listaDeDivs.add("div-view-venta-de-minutos");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_COBRANZA_MULTIPLE) != null) {
                    out.println("<div id='div-view-cobranza-multiple'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardCobrosMultiples();' src='images/btnCobrosMultiples.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelCobroMultipleConCB()");
                    listaDeDivs.add("div-view-cobranza-multiple");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ENVIOS_PERSONAL) != null) {
                    out.println("<div id='div-view-envios-personal' >");

                    out.println("<A HREF='#'  ><img ONCLICK='cardEnviosPersonal();' src='images/btnEnviosPersonal.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");

                    out.println("<div id='div-view-ayuda-envios-personal' >");
                    out.println("<A HREF=\"http://www.personal.com.py/financieras/envios.html\" target=\"_blank\">Ver ayuda nueva version</A>");
                    out.println(" </div>");
                    out.println(" </div>");

                    componentes.add("panelEnviosPersonal()");
                    listaDeDivs.add("div-view-envios-personal");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_FAST_BOX) != null) {
                    out.println(" <div id='div-view-fast-box'>");
                    out.println("<A HREF='#'  ><img ONCLICK='cardFastBox();' src='images/btnFastBox.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A></ div> </div > ");

                    componentes.add("panelFastBox()");
                    listaDeDivs.add("div-view-fast-box");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_GANEMAX) != null) {
                    out.println(" <div id='div-view-ganemax'>");
                    out.println("<A HREF='#'  ><img ONCLICK='cardGanemax();' src='images/btnGanemax.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A></ div> </div > ");

                    componentes.add("panelGanemax()");
                    listaDeDivs.add("div-view-ganemax");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ICED) != null) {
                    out.println(" <div id='div-view-iced'>");
                    out.println("<A HREF='#'  ><img ONCLICK='cardIced();' src='images/btnIced.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A></ div> </div > ");

                    componentes.add("panelIced()");
                    listaDeDivs.add("div-view-iced");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_PURPURA) != null) {
                    out.println(" <div id='div-view-purpura'>");
                    out.println("<A HREF='#'  ><img ONCLICK='cardPurpura();' src='images/btnPurpura.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A></ div> </div > ");

                    componentes.add("panelPurpura()");
                    listaDeDivs.add("div-view-purpura");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_CARGILL) != null) {
                    out.println(" <div id='div-view-cargill'>");
                    out.println("<A HREF='#'  ><img ONCLICK='cardCargill();' src='images/btnCargill.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A></ div> </div > ");

                    componentes.add("panelCargill()");
                    listaDeDivs.add("div-view-cargill");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_REMESAS_CONTI) != null) {
                    out.println("<div id='div-view-remesas-conti'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardRemesasConti();' src='images/btnRemesasConti.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelPRC()");
                    componentes.add("panelERC()");
                    listaDeDivs.add("div-view-remesas-conti");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_REMESAS) != null) {
                    out.println("<div id='div-view-practi-giro'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardPractiGiros();' src='images/btnPractiGiros.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelPractiRetiro()");
                    componentes.add("panelPractiEnvio()");
                    listaDeDivs.add("div-view-practi-giro");
                }
                
                 if (task.get("OPERACIONES").get(OPERACIONES_REMESAS_2) != null) {
                    out.println("<div id='div-view-practi-giro-2'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardPractiGirosV2();' src='images/btnPractiGiros.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelPractiRetiroV2()");
                    componentes.add("panelPractiEnvioV2()");
                    listaDeDivs.add("div-view-practi-giro-2");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_ADELANTO_EFECTIVO) != null) {
                    out.println("<div id='div-view-adelanto-efectivo' >");

                    out.println("<A HREF='#'  ><img ONCLICK='cardAdelantoEfectivo();' src='images/btnMore.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelAdelantoEfectivo()");
                    listaDeDivs.add("div-view-adelanto-efectivo");

                }

                if (task.get("OPERACIONES").get(OPERACIONES_DESEMBOLSO_MASIVO) != null) {
                    out.println("<div id='div-view-desembolso-masivo' >");

                    out.println("<A HREF='#'  ><img ONCLICK='cardDesembolsoMasivo();' src='images/btnFPJAdelanto.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");

                    componentes.add("panelDesembolsoMasivo()");
                    listaDeDivs.add("div-view-desembolso-masivo");

                }

                if (task.get("OPERACIONES").get(OPERACIONES_MORE_MT) != null) {
                    out.println("<div id='div-view-more-mt'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardRemesasMoreMT();' src='images/btnMore.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    //componentes.add("panelEnvioMoreMT()");
                    componentes.add("panelRetiroMoreMT()");
                    listaDeDivs.add("div-view-more-mt");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_PODER_JUDICIAL) != null) {
                    out.println("<div id='div-view-poder-judicial'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardPoderJudicial();' src='images/btnPoderJudicial.png' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelPoderJudicial()");
                    listaDeDivs.add("div-view-poder-judicial");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_DEPOSITO_EXTRACCION) != null) {
                    out.println("<div id='div-view-deposito-extraccion'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardDepositoExtraccion();' src='images/btnDepExt.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelDepositoExtraccion()");
                    listaDeDivs.add("div-view-deposito-extraccion");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_EVENTO) != null) {
                    out.println("<div id='div-view-evento'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardEvento();' src='images/btnEvento.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    componentes.add("panelEvento()");
                    listaDeDivs.add("div-view-evento");
                }
                if (task.get("COMODINES").get(OPERACIONES_AYUDA) != null) {
                    out.println("<div id='div-view-ayuda'>");

                    out.println("<A HREF='#'  ><img ONCLICK='cardAyuda();' src='images/btnAyuda.PNG' style='opacity:0.4;filter:alpha(opacity=40);'"
                            + "onmouseover='this.style.opacity=1'"
                            + "onmouseout='this.style.opacity=0.4 ' /></A>");
                    out.println(" </div>");
                    //componentes.add("panelCabeceraAyuda()");
                    componentes.add("panelGrillaAyuda()");
                    listaDeDivs.add("div-view-ayuda");
                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_DIGITACION) != null && (task.get("OPERACIONES").get(OPERACIONES_DETALLE_DIGITACION_A_ANULAR) != null)) {
                    operacionesDGR = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='ANULAR_DIGITACION' href='#'>Anular Digitación<span class='s'></span> </a>");
                    out.println(" </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION) != null && (task.get("OPERACIONES").get(OPERACIONES_DETALLE_TRANSA_A_ANULAR) != null)) {
                    operacionesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='ANULAR_TRANSACCION' href='#'>Anular Pago DGR<span class='s'></span> </a>");
                    out.println("  </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_CONTROL_FORMULARIOS_BOLETAS) != null) {
                    operacionesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='ControlFormularios' href='#'>Control Formularios/Boletas<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("panelControlFormularios()");

                }
                out.println("  <li >");
                out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                out.println("    <a id='CONSULTA_SET' href='#'>Consultar en la SET<span class='s'></span> </a>");
                out.println("  </li >");
                out.println(" </ul>");
            }
            if (task.get("OPERACIONES") != null) {
                out.println(" <ul id='task-operaciones-cs' class='x-hidden'>");

                if (task.get("OPERACIONES").get(OPERACIONES_ALTA_CUENTAS_A_COBRAR) != null) {
                    operacionesCS = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='ALTA_CUENTAS_A_COBRAR' href='#'>Alta de Cuentas<span class='s'></span> </a>");
                    out.println(" </li >");
                    componentes.add("panelAltaCuentasACobrar()");
                }

                if (task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_TICKET_CS) != null) {
                    operacionesCS = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='RE_IMPRESION_TICKET_CS' href='#'>Reimpresión Ticket Servicios<span class='s'></span> </a>");
                    out.println(" </li >");

                }
                if (task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION_CS) != null) {
                    operacionesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='ANULAR_TRANSACCION_CS' href='#'>Anular Pago Servicios<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                out.println(" </ul>");
            }
            if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_AUTORIZAR_REPORTE) != null) {
                out.println("<ul id='task-reportes' class='x-hidden'>");
                if (task.get("REPORTES").get(REPORTE_CLEARING_COMISION_RESUMIDO) != null) {
                    reportesDGR = true;
                    out.println(" <li >");
                    out.println("  <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("  <a id='REPORTE_CLEARING_COMISION_RESUMIDO' href='#'>Resumen Clearing Comisión<span class='s'></span> </a>");
                    out.println(" </li >");

                }
                if (task.get("REPORTES").get(REPORTES_DECLARACION_NUM_ORDEN) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_DECLARACION_POR_NUMERO_ORDEN' href='#'>DDJJ por Núm. Orden<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_DECLARACION_POR_NUMERO_ORDEN()");
                }
                if (task.get("REPORTES").get(REPORTES_BOLETA_NUM_ORDEN) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN' href='#'>Boleta Pago por Núm. Orden<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN()");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO' href='#'>Cobranza Cheque-Efectivo<span class='s'></span> </a>");
                    out.println("  </li >");
                    // componentes.add("panelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO()");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE_EFECTIVO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO' href='#'>Cob. Det. Cheque-Efectivo<span class='s'></span> </a>");
                    out.println("  </li >");
                    // componentes.add("panelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO()");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_COBRANZA_DETALLADO_CHEQUE' href='#'>Cobranza Det. Cheque<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_COBRANZA_DETALLADO_CHEQUE()");
                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_EFECTIVO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_COBRANZA_DETALLADO_EFECTIVO' href='#'>Cobranza Det. Efectivo<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_COBRANZA_DETALLADO_EFECTIVO()");
                }
                if (task.get("REPORTES").get(REPORTES_TAPALOTE_RESUMIDO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='TAPA_LOTE' href='#'>Tapa Lote<span class='s'></span> </a>");
                    out.println("  </li >");
                    // componentes.add("panelREPORTE_TAPALOTE()");
                }
                if (task.get("REPORTES").get(REPORTE_DIGITACION) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_DIGITACION' href='#'>Reporte de Digitaciones<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                if (task.get("REPORTES").get(REPORTES_TAPALOTE_DETALLADO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='TAPA_LOTE_DETALLADO' href='#'>Tapa Lote Detallado<span class='s'></span> </a>");
                    out.println("  </li >");
                    // componentes.add("panelREPORTE_TAPALOTE_DETALLADO()");
                }
                if (task.get("REPORTES").get(REPORTES_RESUMEN_DE_TRANSACCIONES) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTES_RESUMEN_DE_TRANSACCIONES' href='#'>Resumen de Pagos/DDJJ<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_RESUMIDO) != null) {
                    reportesDGR = true;
                    out.println("   <li >");
                    out.println("     <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("     <a id='REPORTE_CIERRE_RESUMIDO' href='#'>Cierre Resumido<span class='s'></span> </a>");
                    out.println("   </li >");
                    //componentes.add("panelREPORTE_CIERRE_RESUMIDO()");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_DETALLADO) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_CIERRE_DETALLADO' href='#'>Cierre Detallado<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_CIERRE_DETALLADO()");
                }
                if (task.get("REPORTES").get(REPORTES_CIERRE_USUARIOS) != null) {
                    reportesDGR = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTES_CIERRE_USUARIOS' href='#'>Detalle de Pagos/DDJJ<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("panelREPORTE_CIERRE_DETALLADO()");
                }
                out.println(" </ul>");
            }
            if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_AUTORIZAR_REPORTE) != null) {
                out.println("<ul id='task-reportes-cs' class='x-hidden'>");
                if (task.get("REPORTES").get(REPORTE_CIERRE_CS) != null) {
                    reportesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_CIERRE_CS' href='#'>Reporte Cierre<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                if (task.get("REPORTES").get(REPORTE_FACTURADOR_GESTION) != null) {
                    reportesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_FACTURADOR_GESTION' href='#'>Reporte Facturador Gestion<span class='s'></span> </a>");
                    out.println("  </li >");

                }
                if (task.get("REPORTES").get(REPORTES_COBRANZA_CS) != null) {
                    reportesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_COBRANZA_CS' href='#'>Reporte Cobranza<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                if (task.get("REPORTES").get(RESUMEN_COBRANZA_CS) != null) {
                    reportesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='RESUMEN_COBRANZA_CS' href='#'>Resumen de cobranzas<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                if (task.get("REPORTES").get(REPORTE_RESUMEN_FACTURADOR) != null) {
                    reportesCS = true;
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='REPORTE_RESUMEN_FACTURADOR' href='#'>Resumen Facturador<span class='s'></span> </a>");
                    out.println("  </li >");
                }
                out.println(" </ul>");
            }

            if (task.get("CONFIGURACIONES") != null) {
                out.println("<ul id='task-configuraciones' class='x-hidden'>");

                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_USUARIO) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Usuarios' href='#'>Usuarios<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridUSUARIO()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_TERMINAL) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Terminal' href='#'>Terminal<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridTERMINAL()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_USUARIO_TERMINAL) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Usuario_Terminal' href='#'>Usuario Terminal<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridUSUARIO_TERMINAL()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_FRANJA_HORARIA) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Franja_Horaria' href='#'>Franja Horaria<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("panelFranjaHoraria()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_PERMISOS) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Admin_Permisos' href='#'>Admin. Permisos<span class='s'></span> </a>");
                    out.println("  </li >");
                    //componentes.add("administrarPermisos()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_GESTORES) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Admin_Gestores' href='#'>Administracion de Gestores<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridGESTOR()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_EVENTOS) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Admin_Eventos' href='#'>Administracion de Eventos<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("panelEventos()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_SORTEADOR_GESTOR) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Sorteador_Gestores' href='#'>Sorteador de Gestores<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridSORTEADOR_GESTOR()");
                }
                if (task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_FACTURAS) != null) {
                    out.println("  <li >");
                    out.println("    <img src='images/s.gif' class='cerrar-cajas'/>");
                    out.println("    <a id='Admin_Facturas' href='#'>Administracion de Facturas<span class='s'></span> </a>");
                    out.println("  </li >");
                    componentes.add("gridADMIN_FACTURAS()");
                }

                out.println(" </ul>");
            }
        %>


        <script type="text/javascript">
                    function tabOperaciones() {
                        var contentPanel = {
                            id: 'content-panel',
                            region: 'center',
                            layout: 'card',
                            margins: '2 5 5 0',
                            activeItem: 0,
                            border: false,
                            autoScroll: true,
                            items: [<%
                                String cadenaComponentes = "";
                                for (String o : componentes) {
                                    cadenaComponentes += o + ",";
                                }

                                out.print(Utiles.ripLastComa(cadenaComponentes));
            %>]};

            <% componentes.clear();
                /*
                 * if (task.get("DIGITACION") != null) {
                 * componentes.add("taskSET");
                 *
                 *
                 */%>
                        /*
                         var taskSET = new Ext.Panel({
                         frame:true,
                         title: 'SET',
                         collapsible:true,
                         contentEl:'task-set',
                         autoScroll : true,
                         titleCollapse: true
                         });
                         */
            <%/*
                 * }
                 */%>

                        var taskINFO = new Ext.Panel({
                            frame: true,
                            title: 'Info  Version:05.07.14<br/>(<%=request.getLocalName()%>)',
                            collapsible: true,
                            contentEl: 'CH_dtimer1_digits',
                            autoScroll: true,
                            titleCollapse: true
                        });
                        var taskOPERACIONES_SYS = new Ext.Panel({
                            frame: true,
                            title: 'Operaciones de Sistema',
                            collapsible: true,
                            contentEl: 'task-operaciones-sys',
                            iconCls: 'system',
                            autoScroll: true,
                            titleCollapse: true
                        });
            <%  componentes.add("taskINFO");
                componentes.add("taskOPERACIONES_SYS");%>
            <%if (task.get("OPERACIONES") != null) {
                    if (operacionesDGR) {
                        componentes.add("taskOPERACIONES");
            %>
                        var taskOPERACIONES = new Ext.Panel({
                            frame: true,
                            title: 'Operaciones DGR',
                            contentEl: 'task-operaciones',
                            collapsible: true,
                            titleCollapse: true,
                            autoScroll: true,
                            iconCls: 'operaciones'
                        });
            <%}%>
            <%}%>
            <%if (task.get("OPERACIONES") != null) {
                    if (operacionesCS) {
                        componentes.add("taskOPERACIONES_CS");
            %>
                        var taskOPERACIONES_CS = new Ext.Panel({
                            frame: true,
                            title: 'Operaciones CS',
                            contentEl: 'task-operaciones-cs',
                            collapsible: true,
                            titleCollapse: true,
                            autoScroll: true,
                            iconCls: 'operaciones'
                        });
            <%}%>
            <%}%>

            <% if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_AUTORIZAR_REPORTE) != null) {%>
            <%if (reportesDGR) {
                    componentes.add("taskREPORTES");
            %>
                        var taskREPORTES = new Ext.Panel({
                            frame: true,
                            title: 'Reportes DGR',
                            collapsible: true,
                            contentEl: 'task-reportes',
                            titleCollapse: true,
                            autoScroll: true,
                            iconCls: 'reporte'
                        });
            <%}%>
            <%if (reportesCS) {
                    componentes.add("taskREPORTES_CS");
            %>

                        var taskREPORTES_CS = new Ext.Panel({
                            frame: true,
                            title: 'Reportes CS',
                            collapsible: true,
                            contentEl: 'task-reportes-cs',
                            titleCollapse: true,
                            autoScroll: true,
                            iconCls: 'reporte'
                        });
            <%}%>
            <%}%>
            <% if (task.get("CONFIGURACIONES") != null) {
                    componentes.add("taskCONFIGURACIONES");
            %>
                        var taskCONFIGURACIONES = new Ext.Panel({
                            frame: true,
                            title: 'Configuraciones',
                            collapsible: true,
                            contentEl: 'task-configuraciones',
                            titleCollapse: true,
                            autoScroll: true,
                            iconCls: 'configuration'
                        });
            <%}%>

            <%String cadenaComponentes2 = "";
                for (String o : componentes) {
                    cadenaComponentes2 += o + ",";
                }
            %>
                        var actionPanel = new Ext.Panel({
                            id: 'action-panel',
                            region: 'west',
                            split: true,
                            collapsible: true,
                            margins: '0 0 0 5',
                            collapseMode: 'mini',
                            width: 200,
                            minWidth: 180,
                            border: false,
                            baseCls: 'x-plain',
                            autoScroll: true,
                            items: [<%out.print(Utiles.ripLastComa(cadenaComponentes2));%>]
                        });
                        var viewport = new Ext.Viewport({
                            layout: 'border',
                            autoscroll: true,
                            items: [new Ext.BoxComponent({
                                    xtype: 'box',
                                    region: 'north',
                                    el: 'header',
                                    height: 50,
                                    tbar: [{
                                            text: 'Aceptar',
                                            iconCls: 'logout',
                                            handler: function () {
                                                cardInicial();
                                            }
                                        }]
                                }), contentPanel,
                                actionPanel
                            ]
                        });

                        var actions = {
                            'CERRAR_GESTION': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_CERRAR_GESTION) != null) {
                    out.print("cerrarGESTION();");
                }%>
                            },
                            'ABRIR_GESTION': function () {
            <%  if ((task.get("OPERACIONES").get(OPERACIONES_APERTURA_MULTIPLE) != null) || (task.get("OPERACIONES").get(OPERACIONES_APERTURA_SIMPLE) != null)) {
                    out.print("abrirGESTION();");
                }%>
                            },
                            'ControlFormularios': function () {
            <%  if (true) {
                    out.print("ControlFormulariosBoletas();");
                }%>
                            },
                            'LOGOUT': function () {
                                logout();
                            },
                            'CAMBIAR_PASSWORD': function () {
                                pantallaModificarContrasenhaDeLogeo();
                            },
                            'ANULAR_TRANSACCION': function () {

            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION) != null) {%>

                                if (GESTION_ABIERTA) {
                                    anularTransaccion();
                                } else {
                                    Ext.Msg.show({
                                        title: 'Info',
                                        msg: MSG_DEBE_ABRIR_GESTION_PARA_REALIZAR_OP,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.MessageBox.ERROR


                                    });
                                }
            <%}%>

                            },
                            'ANULAR_DIGITACION': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_ANULAR_DIGITACION) != null) {%>

                                if (GESTION_ABIERTA) {
                                    anularDigitacion();
                                } else {
                                    Ext.Msg.show({
                                        title: 'Info',
                                        msg: MSG_DEBE_ABRIR_GESTION_PARA_REALIZAR_OP,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.MessageBox.ERROR
                                    });
                                }
            <%}%>
                            },
                            'RE_IMPRESION': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_REIMPRESION) != null) {
                    out.print("reImprimir();");
                }%>
                            },
                            'ANULAR_TRANSACCION_CS': function () {

            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_ANULAR_TRANSACCION_CS) != null) {%>

                                if (GESTION_ABIERTA) {
                                    anularTransaccionCS();
                                } else {
                                    Ext.Msg.show({
                                        title: 'Info',
                                        msg: MSG_DEBE_ABRIR_GESTION_PARA_REALIZAR_OP,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.MessageBox.ERROR


                                    });
                                }
            <%}%>

                            },
                            'RE_IMPRESION_FORMULARIO': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_FORMULARIO) != null) {
                    out.print("reImprimirFormulario();");
                }%>
                            },
                            'RE_IMPRESION_TICKET_CS': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_REIMPRESION_TICKET_CS) != null) {
                    out.print("reImprimirTicketCS();");
                }%>
                            },
                            'REPORTE_CLEARING_COMISION_RESUMIDO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTE_CLEARING_COMISION_RESUMIDO) != null) {
                    out.print("pantallaRESUMEN_CLEARING_COMISION();");
                }%>
                            },
                            'REPORTE_RESUMEN_FACTURADOR': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTE_RESUMEN_FACTURADOR) != null) {
                    out.print("pantallaREPORTE_RESUMEN_FACTURADOR();");
                }%>
                            },
                            'ALTA_CUENTAS_A_COBRAR': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_ALTA_CUENTAS_A_COBRAR) != null) {
                    out.print("cardAltaCuentasACobrar();");
                }%>
                            },
                            'CONSULTA_CONTRIBUYENTES': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_CONSULTAR_CONTRIBUYENTES) != null) {
                    out.print("cardConsultaContribuyentes();");
                }%>
                            },
                            'REPORTE_DECLARACION_POR_NUMERO_ORDEN': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_DECLARACION_NUM_ORDEN) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN');");
                }%>

                            },
                            'REPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_BOLETA_NUM_ORDEN) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN');");
                }%>

                            },
                            'REPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO');");
                }%>
                            },
                            'REPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE_EFECTIVO) != null) {
                    out.print(" autorizarOpcion('idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO');");
                }%>
                            },
                            'REPORTE_COBRANZA_DETALLADO_CHEQUE': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_CHEQUE) != null) {
                    out.print(" autorizarOpcion('idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE');");
                }%>

                            },
                            'REPORTE_COBRANZA_DETALLADO_EFECTIVO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_COBRANZA_DETALLADO_EFECTIVO) != null) {
                    out.print(" autorizarOpcion('idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO');");
                }%>

                            },
                            'TAPA_LOTE': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_TAPALOTE_RESUMIDO) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_TAPALOTE');");
                }%>

                            },
                            'TAPA_LOTE_DETALLADO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_TAPALOTE_DETALLADO) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_TAPALOTE_DETALLADO');");
                }%>
                            },
                            'REPORTE_DIGITACION': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTE_DIGITACION) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_DIGITACION');");
                }%>

                            },
                            'REPORTE_CIERRE_RESUMIDO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_CIERRE_RESUMIDO) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_CIERRE_RESUMIDO');");
                }%>

                            },
                            'REPORTES_RESUMEN_DE_TRANSACCIONES': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_RESUMEN_DE_TRANSACCIONES) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_RESUMEN_DE_TRANSACCIONES');");
                }%>

                            },
                            'REPORTES_CIERRE_USUARIOS': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_CIERRE_USUARIOS) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_CIERRE_USUARIO');");
                }%>
                            },
                            'REPORTE_CIERRE_DETALLADO': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_CIERRE_DETALLADO) != null) {
                    out.print("autorizarOpcion('idPanelREPORTE_CIERRE_DETALLADO');");
                }%>
                            },
                            'RESUMEN_COBRANZA_CS': function () {
            <%if (task.get("REPORTES") != null && task.get("REPORTES").get(REPORTES_COBRANZA_CS) != null) {
                    out.print("autorizarOpcion('idPanelRESUMEN_COBRANZA_CS');");
                }%>
                            },
                            'REPORTE_CIERRE_CS': function () {

            <%out.print("autorizarOpcion('idPanelREPORTE_CIERRE_CS');");%>

                            },
                            'REPORTE_FACTURADOR_GESTION': function () {

            <%out.print("autorizarOpcion('idPanelREPORTE_RESUMEN_FACTURADOR_GESTION');");%>

                            }
                            , 'REPORTE_COBRANZA_CS': function () {

            <%out.print("autorizarOpcion('idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS');");%>

                            },
                            'Usuarios': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_USUARIO) != null) {
                    out.print("ConfUsuarios();");
                }%>
                            },
                            'Terminal': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_TERMINAL) != null) {
                    out.print("ConfTerminal();");
                }%>
                            },
                            'Usuario_Terminal': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_USUARIO_TERMINAL) != null) {
                    out.print("ConfUsuarioTerminal();");
                }%>
                            },
                            'Franja_Horaria': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_FRANJA_HORARIA) != null) {
                    out.print("ConfFranjaHoraria();");
                }%>
                            },
                            'Admin_Permisos': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_PERMISOS) != null) {
                    out.print("ConfAdminPermisos();");
                }%>
                            },
                            'Admin_Gestores': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_GESTORES) != null) {
                    out.print("confAdminGestores();");
                }%>
                            },
                            'Admin_Eventos': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_EVENTOS) != null) {
                    out.print("confAdminEventos();");
                }%>
                            },
                            'Admin_Facturas': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_ADMIN_FACTURAS) != null) {
                    out.print("confAdminFacturas();");
                }%>
                            },
                            'Sorteador_Gestores': function () {
            <%if (task.get("CONFIGURACIONES") != null && task.get("CONFIGURACIONES").get(CONFIGURACIONES_SORTEADOR_GESTOR) != null) {
                    out.print("ConfSorteadorGestores();");
                }%>
                            },
                            'DESCARGAR_ARCHIVO_COBRANZA': function () {
            <%if (task.get("OPERACIONES") != null && task.get("OPERACIONES").get(OPERACIONES_DESCARGAR_ARCHIVO_COBRANZA) != null) {
                    out.print("descargarArchivoCobranza();");
                }%>
                            },
                            'CONSULTA_SET': function () {
                                newPopup('https://marangatu.set.gov.py/eset/perfilPublicoContribIService.do');
                            }

                        };
                        function doAction(e, t) {
                            //console.log(t)

                            actions[t.id]();
                        }
                        var ab = actionPanel.body;

                        ab.on('mousedown', doAction, null, {
                            delegate: 'a'
                        });
                        ab.on('click', Ext.emptyFn, null, {
                            delegate: 'a',
                            preventDefault: true
                        });
                    }
            <%String itemsStart = "";

                for (String oo : listaDeDivs) {
                    itemsStart += "," + "{bodyStyle: 'padding:4px', border:false,contentEl:'" + oo + "'}";
                }
                itemsStart = itemsStart.replaceFirst(",", "");

            %>
                    function start() {
                        var start = {
                            id: 'start-panel',
                            title: 'Página Inicial',
                            bodyStyle: 'padding:20px',
                            contentEl: 'start-div',
                            layout: 'table',
                            autoScroll: true,
                            layoutConfig: {
                                columns: 4
                            },
                            items: [<%out.print(itemsStart);%>]
                        };
                        return start;
                    }
        </script>


    </body>
</html>

