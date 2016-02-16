<%-- 
    Document   : confirmacion1
    Created on : Aug 18, 2010, 1:23:39 PM
    Author     : konecta
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="org.netbeans.xml.schema.common.ElementoMoneda"%>
<%@page import="org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras.Monedas"%>
<%@page import="org.netbeans.xml.schema.common.RespuestaConsultaCodigoBarras"%>
<%@page import ="java.lang.String,java.util.Map, java.util.List, javax.naming.Context,
        javax.naming.InitialContext,
        py.com.konecta.redcobros.ejb.EntidadFinancieraFacade,
        py.com.konecta.redcobros.entities.EntidadFinanciera;" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <style type="text/css">
        #labelConfirm{
            font-size:10px;
        }
        #confirm_box{
            background-color:rgb(223,232,246);
            -webkit-border-radius: 30px;
            -moz-border-radius: 30px;
            border-radius: 30px;
        }
        #confirm_container_box{
            background-color:#ffffff;

        }
        .importante {
            background: #a8bbe0 url(images/important.png) center no-repeat;
            background-position: 15px 50%; /* x-pos y-pos */
            text-align: left;
            padding: 5px 20px 5px 45px;
            font-size:12px;
            border: 2px solid #3c5ea3;
            text-decoration: blink;
            text-align: center;
            /*border-bottom: 2px solid #FBAB95;*/
            color:#000000;
        }

    </style>
    <script type="text/javascript" language='javascript'>
        <%RespuestaConsultaCodigoBarras respuestaCB = (RespuestaConsultaCodigoBarras) session.getAttribute("respuestaCB");
            Long idLogTrx = respuestaCB.getEstado().getIdLogTransaccion();
            NumberFormat nf = NumberFormat.getInstance();
        %>
            var confirmcb = false;
            function ticket(){
            
                var formaDePago = document.getElementById("idNroCheque<%=idLogTrx%>").disabled?"Efectivo":"Cheque";
                var entidadFinanciera = document.getElementById("idBanco<%=idLogTrx%>").value;
                var nroCheque = document.getElementById("idNroCheque<%=idLogTrx%>").value;
                var fechaCheque = document.getElementById("idFechaCheque<%=idLogTrx%>").value;
                if(formaDePago == "Cheque" && entidadFinanciera==""){
                    alert("Favor seleccionar un Banco");
                }else if(formaDePago == "Cheque" && nroCheque==""){
                    alert("Favor Ingresar Nro. de Cheque");
                }else if(formaDePago == "Efectivo" || (formaDePago == "Cheque" && checkFechaCheque(fechaCheque))){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg : '¿Está seguro que desea abonar en '+formaDePago+'?',
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {
                            if (btn == "ok") {
                                if(!confirmcb){
                                    confirmcb=true;
                            
                                    Ext.Msg.wait('Procesando... Por Favor espere...');
                                    Ext.getCmp('panelTicketCB').load({
        <%if (request.getAttribute("backTO").equals("CB") || request.getAttribute("backTO").equals("PAGO_DESDE_ARCHIVO")) {%>
                                        url: 'COBRO_SERVICIOS?op=PROCESAR_SERVICIO&ID_RANDOM=<%=respuestaCB.getEstado().getIdLogTransaccion()%>', 
        <%} else {%>
                                        url: 'COBRO_SERVICIOS?op=PROCESAR_SERVICIO&ct=TRUE&ID_RANDOM=<%=respuestaCB.getEstado().getIdLogTransaccion()%>', 
        <%}%>
                                        params:{
                                            ID_ENTIDAD : document.getElementById("idBanco<%=idLogTrx%>").value,
                                            TIPO_DE_PAGO : document.getElementById("idFormaDePago<%=idLogTrx%>").value,
                                            NRO_DE_CHEQUE : document.getElementById("idNroCheque<%=idLogTrx%>").value,
                                            ID_TRANSACCION : document.getElementById("idTransaccion<%=idLogTrx%>").value,
                                            MONEDA : document.getElementById("idMonedaCB<%=idLogTrx%>").value,
                                            FECHA_CHEQUE: document.getElementById("idFechaCheque<%=idLogTrx%>").value
                                        }, 
                                        discardUrl: false,
                                        nocache: true,
                                        text: "Procesando... Por Favor espere...",
                                        timeout: TIME_OUT_AJAX,
                                        scripts:true
                                    }); 
                                    Ext.Msg.hide();
                                    confirmcb = false;
                                    Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                                }
                            }
                        }
                    });
                }
            }
    </script>
    <body>
        <table style="background: url(images/waterMark<%
            out.print(respuestaCB.getEstado().getDescripcion().replace(" ", "") + ".png");
               %>)"  cellspacing="5" cellpadding="20" width="100%" height="100%" border="0">
            <tbody>
                <tr>
                    <td>
                        <table id="confirm_container_box" align="center" border="1" cellpadding="5" cellspacing="5">
                            <tr>
                                <td>
                                    <table id="confirm_box" align="center" border="0" cellpadding="1" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <img alt="<%=respuestaCB.getEstado().getDescripcion()%>" src="images/<%out.println(respuestaCB.getEstado().getDescripcion().replace(" ", "") + ".png");%>"/><br>
                                                </td>
                                            </tr>
                                            <%if (respuestaCB.getIdServicio() != null
                                                        && respuestaCB.getIdServicio() == 3) {%>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <p class="importante">Si su factura corresponde a una de Desconexion, la misma no puede ser cobrada</p>
                                                </td>

                                            </tr>
                                            <%}%>
                                            <%if (respuestaCB.getIdServicio() != null
                                                        && respuestaCB.getIdServicio() == 88) {%>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <p class="importante">Favor verificar la fecha de vencimiento de la factura, si ya venció no cobre</p>
                                                </td>

                                            </tr>
                                            <%}%>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <label style="font-size:12px;font-weight:bold;"><%=respuestaCB.getMensaje()%></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <label style="font-size:12px;"> Importe: </label><input  id="idImporteCB<%=idLogTrx%>"  disabled="disabled" value="<%=nf.format(respuestaCB.getMonto())%>">
                                                    <select id="idMonedaCB<%=idLogTrx%>">
                                                        <%
                                                            List<ElementoMoneda> listMonedas = respuestaCB.getMonedas().getMoneda();

                                                            for (ElementoMoneda mon : listMonedas) {
                                                        %>
                                                        <option value="<%=mon.getIdMoneda() + "#" + mon.getTasa()%>" ><%= mon.getAbreviatura()%></option>
                                                        <%}%>
                                                    </select>
                                                    <span style="visibility: hidden">
                                                        <label style="font-size:12px;" id="idLabelTasaCB<%=idLogTrx%>"> Tasa: </label><input id="idTasaCB<%=idLogTrx%>"  disabled="disabled" value="" size="10">
                                                   </span>
                                                    <script type="text/javascript">
                                                            document.getElementById('idMonedaCB<%=idLogTrx%>').addEventListener('change', changeidMonedaCB<%=idLogTrx%>, false);
                                                            function changeidMonedaCB<%=idLogTrx%>(){
                                                                var datoMoneda = document.getElementById('idMonedaCB<%=idLogTrx%>').value.split("#");
                                                                var moneda = datoMoneda[0];
                                                                var tasa = datoMoneda[1];
                                                                var monto;
                                                                if(moneda == 600){ 
                                                                    monto = addCommas(String(tasa * parseFloat(deleteCommas(document.getElementById('idImporteCB<%=idLogTrx%>').value))));
                                                                    document.getElementById('idImporteCB<%=idLogTrx%>').value = monto;                                                                    
                                                                    document.getElementById("idTasaCB<%=idLogTrx%>").value = addCommas(String(tasa));
                                                                    document.getElementById("idLabelTasaCB<%=idLogTrx%>").style.visibility = "visible";
                                                                    document.getElementById("idTasaCB<%=idLogTrx%>").style.visibility = "visible";
                                                                }else{
                                                                    monto = addCommas(String(parseInt(deleteCommas(document.getElementById('idImporteCB<%=idLogTrx%>').value))/parseInt(deleteCommas(document.getElementById("idTasaCB<%=idLogTrx%>").value))));
                                                                    document.getElementById('idImporteCB<%=idLogTrx%>').value = monto;                                                                    
                                                                    document.getElementById("idLabelTasaCB<%=idLogTrx%>").style.visibility = "hidden";
                                                                    document.getElementById("idTasaCB<%=idLogTrx%>").style.visibility = "hidden";
                                                                }
                                                            }
                                                    </script> 
                                                </td>
                                            </tr>                                            
                                            <tr>
                                                <td><input id="idEfectivo" type="radio" name="TIPO_DE_PAGO<%=idLogTrx%>"  onkeypress="onKeyPressed(event,'idBtnEnviar');" onclick="isCheque(1);"value="1" checked="true"/><label id="labelConfirm">Efectivo</label></td>
                                                <td><input id="idChequeLocal" type="radio" name="TIPO_DE_PAGO<%=idLogTrx%>" onclick="isCheque(3);" value="2"/><label id="labelConfirm">Cheque Continental</label></td>
                                                <td><input id="idChequeOtros" type="radio" name="TIPO_DE_PAGO<%=idLogTrx%>" onkeypress="onKeyPressed(event,'idBanco<%=idLogTrx%>');" onclick="isCheque(2);" value="2"/><label id="labelConfirm">Cheque Otros Bancos</label></td>

                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><label id="labelConfirm">Banco</label></td><td align="left">
                                                    <select id="idBanco<%=idLogTrx%>" disabled="disabled">
                                                        <option value=""></option>
                                                        <%
                                                            Context context = new InitialContext();
                                                            EntidadFinanciera entity = new EntidadFinanciera();
                                                            EntidadFinancieraFacade entidadFinancieraFacade = (EntidadFinancieraFacade) context.lookup(EntidadFinancieraFacade.class.getName());
                                                            List<Map<String, Object>> lista;
                                                            lista = entidadFinancieraFacade.listAtributos(entity, new String[]{"idEntidadFinanciera", "descripcion"});
                                                            for (Map<String, Object> e : lista) {%>
                                                        <option value="<%= e.get("idEntidadFinanciera")%>" ><%= e.get("descripcion")%></option>
                                                        <%}%>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><label id="labelConfirm">Nro. de Cheque:</label></td><td align="left"><input id="idNroCheque<%=idLogTrx%>" autocomplete="off" size="15" name="NRO_DE_CHEQUE" align="right" onkeypress="onKeyPressed(event,'idFechaCheque<%=idLogTrx%>');" onkeyup="this.value=replaceAllNoNumbers(this.value);" onblur="this.value=replaceAllNoNumbers(this.value);" disabled="disabled"></td>
                                            </tr>
                                            <tr>                                                    
                                                <td></td>
                                                <td><label id="labelConfirm">Fecha Cheque</label></td><td align="left"><input id="idFechaCheque<%=idLogTrx%>" autocomplete="off" size="15" name="" align="right" onkeypress="onKeyPressed(event,'idBtnEnviar');" onkeyup="this.value=replaceAllNoNumbers(this.value)" onblur="checkFechaCheque(this.value);this.value=replaceAllNoNumbers(this.value);" disabled="disabled"></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input style="width:150px" type="reset" name="btnCancelar" id="idBtnCancelar" value="Cancelar" onkeypress="onKeyPressed(event,'idEfectivo');" onclick="cancelOp();"/>
                                                </td>
                                                <td>
                                                    <input style="width:150px" type="submit" name="btnEnviar" id="idBtnEnviar" value="Confirmar" onclick="ticket();"/>

                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </table>
                </tr>
            </tbody>
        </table>

        <input type="hidden" value='<%=idLogTrx%>' name="ID" id="idTransaccion<%=idLogTrx%>">
        <input type="hidden" value='1' name="ID_FORMA_PAGO" id="idFormaDePago<%=idLogTrx%>">
        <script type="text/javascript">
            function cancelOp(){
                Ext.Msg.show({
                    title : "Transacción",
                    msg : "Está seguro que desea cancelar la operación?",
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn, text) {
                        if(btn=='ok'){
            <%if (request.getAttribute("backTO").equals("CB")) {%>
                                Ext.getCmp('content-panel').layout.setActiveItem('panelCobroConCB');
                                Ext.getCmp('idFormPanelCodigoDeBarras').getForm().reset();
                                Ext.getCmp('idCodigoBarra').focus(true,true);
            <%} else if (request.getAttribute("backTO").equals("PAGO_DESDE_ARCHIVO")) {%>
                                cardInicial();
            <%} else if (request.getAttribute("backTO").equals("PAGO_TARJETA")) {%>
                                Ext.getCmp('content-panel').layout.setActiveItem('panelCobroTarjetas');
                                Ext.getCmp('idFomTarjeta').getForm().reset();
                                Ext.getCmp('idMontoPagarTarjeta').show();
                                Ext.getCmp('idCodigoBarraTarjeta').focus(true,true);
            <%}%>
                            }else if(btn=='cancel'){
                                close();
                            }
                        }
                    });
                }

                document.getElementById('idEfectivo').focus();
                document.getElementById('idBanco<%=idLogTrx%>').addEventListener('change', function(){
                    document.getElementById('idNroCheque<%=idLogTrx%>').focus();
                }, false);
                function onKeyPressed(e,d){
                    var keyPressed;

                    //Browser compatibility check
                    if (document.all)
                    {
                        //Browser used: Internet Explorer 6
                        keyPressed = e.keyCode;
                    }
                    else
                    {
                        //Browser used: Firefox
                        keyPressed = e.which;
                    }

                    //13 = ASCII code for Enter key
                    if (keyPressed == 13 || keyPressed == 9 )
                    {
                        document.getElementById(d).focus();
                    }
                }

                function isCheque(tipoPago){
                    if(tipoPago==1){
                        document.getElementById("idBanco<%=idLogTrx%>").disabled = true;
                        document.getElementById("idNroCheque<%=idLogTrx%>").disabled = true;
                        document.getElementById("idFechaCheque<%=idLogTrx%>").disabled = true;
                        document.getElementById("idBanco<%=idLogTrx%>").value = "";
                        document.getElementById('idNroCheque<%=idLogTrx%>').value ="";
                        document.getElementById("idFechaCheque<%=idLogTrx%>").value = "";
                        document.getElementById("idFormaDePago<%=idLogTrx%>").value = 1;
                    }else if(tipoPago==2){
                        document.getElementById("idBanco<%=idLogTrx%>").disabled = false;
                        document.getElementById("idNroCheque<%=idLogTrx%>").disabled = false;
                        document.getElementById("idFechaCheque<%=idLogTrx%>").disabled = false;
                        document.getElementById("idBanco<%=idLogTrx%>").value = "";
                        document.getElementById("idFormaDePago<%=idLogTrx%>").value = 2;
                    }else if(tipoPago==3){
                        document.getElementById("idBanco<%=idLogTrx%>").disabled = true;
                        document.getElementById("idNroCheque<%=idLogTrx%>").disabled = false;                        
                        document.getElementById("idBanco<%=idLogTrx%>").value = 3;
                        document.getElementById("idFechaCheque<%=idLogTrx%>").disabled = false;
                        document.getElementById('idNroCheque<%=idLogTrx%>').focus();
                        document.getElementById("idFormaDePago<%=idLogTrx%>").value = 2;
                    }
                }
        </script>
    </body>
</html>
