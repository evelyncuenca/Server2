<%-- 
    Document   : consulta
    Created on : Oct 4, 2010, 7:49:06 AM
    Author     : konecta
--%>

<%@page import="py.com.documenta.onlinemanager.ws.Moneda"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="py.com.konecta.redcobros.ejb.ServicioRcFacade"%>
<%@page import="py.com.konecta.redcobros.entities.ServicioRc"%>
<%@page import="py.com.documenta.onlinemanager.ws.OlDetalleReferencia"%>
<%@page import="py.com.documenta.onlinemanager.ws.OlDetalleServicio"%>
<%@page import="py.com.documenta.onlinemanager.ws.OlResponseConsulta"%>
<%@page import="py.com.documenta.onlinemanager.ws.HashMapContainer.Map.Entry"%>
<%@page import="py.com.documenta.onlinemanager.ws.HashMapContainer"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import ="java.lang.String,java.util.Map, java.util.List, javax.naming.Context,
        javax.naming.InitialContext,
        py.com.konecta.redcobros.ejb.EntidadFinancieraFacade,
        py.com.konecta.redcobros.entities.EntidadFinanciera,
        py.com.konecta.redcobros.cobrosweb.utiles.Utiles;" contentType="text/html" pageEncoding="UTF-8"%>
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
    </style>

    <body>
        <table style="background: url(images/waterMark<%String sTexto = session.getAttribute("facturador") != null ? (String) session.getAttribute("facturador") : "All";
            String fondo = "";
            for (int x = 0; x < sTexto.length(); x++) {
                if (sTexto.charAt(x) != ' ') {
                    fondo += sTexto.charAt(x);
                }
            }
            fondo = fondo.replaceAll("/", "");
            out.print(fondo + ".png");%>)"  cellspacing="5" cellpadding="20" width="100%" height="100%" border="0">
            <tbody>
                <tr>
                    <td>
                        <table id="confirm_container_box" align="center" border="1" cellpadding="5" cellspacing="5">
                            <tr>
                                <td>
                                    <table id="confirm_box" align="center" border="0" cellpadding="1" cellspacing="10">
                                        <tbody>                                            
                                            <%
                                                OlResponseConsulta respConsulta = (OlResponseConsulta) session.getAttribute("respuestaConsulta");
                                                List<OlDetalleServicio> lDetalleServicio = respConsulta.getDetalleServicios();
                                                boolean isSrcExp = false;
                                                boolean calcComision = true;
                                                boolean showCheque = true;
                                                Boolean cambiarMonto = respConsulta.isCambioMontoPermitido();
                                                int nroReferencia = 0;
                                                int idServicio = 1;
                                                int servicio = 100;
                                                int opcion;
                                                for (OlDetalleServicio detSer : lDetalleServicio) {

                                                    List<OlDetalleReferencia> lDetalleReferencia = detSer.getDetalleReferencias();
                                                    String tipoSeleccion = detSer.getTipoSeleccion().value().equals("RADIO_BUTTON") ? "radio" : "checkbox";
                                            %>
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <%Context context = new InitialContext();
                                                        ServicioRcFacade serivicioRcFacade = (ServicioRcFacade) context.lookup(ServicioRcFacade.class.getName());
                                                        ServicioRc servicioEntity = serivicioRcFacade.get(detSer.getIdServicio());
                                                        idServicio = servicioEntity.getIdServicio();
                                                        String lTexto = servicioEntity.getDescripcion();
                                                        String logoServicio = "";
                                                        isSrcExp = (idServicio == 89 || idServicio == 140) ? true : false;//caso excepcional para Tu Financiera y Credimas
                                                        showCheque = (idServicio == 141 ? false : true);//adelanto fpj
                                                        for (int x = 0; x < lTexto.length(); x++) {
                                                            if (lTexto.charAt(x) != ' ') {
                                                                logoServicio += lTexto.charAt(x);
                                                            }
                                                        }
                                                    %>
                                                    <img alt="<%out.print(logoServicio);%>"  src="images/<%out.print(logoServicio + ".png");%>"/><br>
                                                    <%%>
                                                </td>
                                            </tr>
                                            <%for (OlDetalleReferencia detRef : lDetalleReferencia) {
                                            %>
                                            <tr>
                                                <td colspan="5" align="center">
                                                    <%if (detRef.getDescipcion() != null && !detRef.getDescipcion().isEmpty()) {%>
                                                    <label style="font-size:12px; font-weight:bold;"><%out.print("Descripción: " + detRef.getDescipcion());%></label>
                                                    <br>
                                                    <%}%>
                                                    <input type="<%=tipoSeleccion%>"  name="NRO_PAGO" value="<%opcion = nroReferencia + servicio;
                                                        out.print(opcion);%>"  <%
                                                               /*if (lDetalleServicio.size() == 1 && lDetalleReferencia.size() == 1) {
                                                                out.print(" checked=\"true\" ");
                                                                }*/
                                                               if (detSer.getTipoAcumulado().value().equals("PAGO_ANTERIORES")) {
                                                                   out.print("onclick=\" calcularMontoAnteriores(document.getElementsByName('NRO_PAGO'));\"");
                                                               }
                                                               if (detSer.getTipoAcumulado().value().equals("PAGO_INDEPENDIENTE")) {
                                                                   out.print("onclick=\" calcularMontoIndependiente(document.getElementsByName('NRO_PAGO'));\"");
                                                               }
                                                               if (detSer.getTipoAcumulado().value().equals("PAGO_TOTAL")) {
                                                                   out.print("onclick=\" showMonto(document.getElementsByName('NRO_PAGO'));\"");
                                                               }
                                                               if (lDetalleReferencia.size() == 1) {
                                                                   out.print(" checked ");
                                                               }
                                                           %>>                                                    
                                                    <input type="hidden" value="<%=detRef.getMonto()%>" id="idHiddenMonto<%=servicio + nroReferencia++%>">                                                    
                                                    <label style="font-size:12px;">
                                                        <%
                                                            NumberFormat nf = NumberFormat.getInstance();

                                                            out.print("Referencia: " + detRef.getReferenciaPago() + "<br>");
                                                            //out.print("Importe: " + nf.format(detRef.getMonto()) + "<br>");
                                                            HashMapContainer hmap = detRef.getHashMapContainer();
                                                            if (hmap != null) {
                                                                List<Entry> lEntry = hmap.getMap().getEntry();
                                                                for (Entry it : lEntry) {
                                                                    if (it.getValue().isVisible()) {
                                                                        String value = it.getValue().getValue().matches("[0-9]+") ? nf.format(Long.parseLong(it.getValue().getValue())) : it.getValue().getValue();
                                                                        out.print(it.getValue().getLegend() + value + "<br>");
                                                                    }
                                                                    if (isSrcExp) {
                                                                        if (it.getKey().equals("MENSAJE")) {
                                                                            if (it.getValue().getValue().equalsIgnoreCase("Exonerado")) {
                                                                                calcComision = false;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        %>
                                                    </label>
                                                    <label style="font-size:12px;"> Importe: </label><input  id="idImporte<%=opcion%>"  disabled="disabled" value="<%=nf.format(detRef.getMonto())%>">
                                                    <input type="hidden" id="idMonedaActual" value="">
                                                    <select id="idMonedaCyP<%=opcion%>">
                                                        <%
                                                            List<Moneda> listMonedas = detSer.getMonedas();
                                                            for (Moneda mon : listMonedas) {%>
                                                        <option value="<%=mon.getIdMoneda() + "#" + mon.getTasa()%>" ><%= mon.getAbreviatura()%></option>
                                                        <%}%>
                                                    </select>                                                   
                                                    <span style="visibility: hidden">
                                                        <label style="font-size:12px;" id="idLabelTasa<%=opcion%>"> Tasa: </label><input id="idTasa<%=opcion%>"  disabled="disabled" value="" size="10">
                                                    </span>
                                                    <input type="hidden" value="<%=listMonedas.get(0).getIdMoneda()%>" id="idHiddenMoneda<%=opcion%>">
                                                    <script type="text/javascript">
                                                        document.getElementById('idMonedaCyP<%=opcion%>').addEventListener('change', changeidMonedaCyP<%=opcion%>, false);
                                                        function changeidMonedaCyP<%=opcion%>() {
                                                            var datoMoneda = document.getElementById('idMonedaCyP<%=opcion%>').value.split("#");
                                                            var moneda = datoMoneda[0];
                                                            var tasa = datoMoneda[1];
                                                            var monto;
                                                            if (moneda == 600) {
                                                                monto = addCommas(String(tasa * parseFloat(deleteCommas(document.getElementById('idImporte<%=opcion%>').value))));
                                                                document.getElementById('idMontoAPagar').value = monto;
                                                                document.getElementById('idImporte<%=opcion%>').value = monto;
                                                                document.getElementById('idHiddenMonto<%=opcion%>').value = deleteCommas(monto);
                                                                document.getElementById("idTasa<%=opcion%>").value = addCommas(String(tasa));
                                                                document.getElementById("idLabelTasa<%=opcion%>").style.visibility = "visible";
                                                                document.getElementById("idTasa<%=opcion%>").style.visibility = "visible";
                                                            } else {
                                                                monto = addCommas(String(document.getElementById('idHiddenMonto<%=opcion%>').value / parseInt(deleteCommas(document.getElementById("idTasa<%=opcion%>").value))));
                                                                document.getElementById('idMontoAPagar').value = monto;
                                                                document.getElementById('idImporte<%=opcion%>').value = monto;
                                                                document.getElementById('idHiddenMonto<%=opcion%>').value = deleteCommas(monto);
                                                                document.getElementById("idLabelTasa<%=opcion%>").style.visibility = "hidden";
                                                                document.getElementById("idTasa<%=opcion%>").style.visibility = "hidden";
                                                            }
                                                        }
                                                    </script>                                                    
                                                </td>
                                            </tr>
                                            <%}
                                                servicio += 100;
                                                nroReferencia = 0;%>                                            
                                            <tr>

                                                <td colspan="3" align="center">
                                                    <%if (!isSrcExp) {%>   
                                                    <label style="font-size:18px;"> El monto acumulado es: <%
                                                        NumberFormat nf = NumberFormat.getInstance();
                                                        out.print(nf.format(lDetalleReferencia.get(lDetalleReferencia.size() - 1).getAcumulado()));%></label>
                                                        <%} else {%>
                                                        <%if (calcComision) {%>      
                                                    <label style="font-size:18px;">Atencion: Al monto se le calculara la comision a pagar</label>
                                                    <%}%>
                                                    <%}%>
                                                </td>

                                            </tr>                                                
                                            <%}%>
                                            <tr>
                                                <%if (!cambiarMonto) {%>
                                                <td colspan="3" align="center">
                                                    <label style="font-size:18px;"> El monto a pagar es: </label><input  id="idMontoAPagar"  disabled="disabled" value="">
                                                </td>
                                               
                                                <%} else {%>
                                                <td colspan="3" align="center">
                                                    <label style="font-size:18px;"> Ingrese el Monto a Pagar: </label><input  id="idMontoAPagar"  onKeyPress="return numbersAndDecimalsOnly(this, event)" value="0" onblur="checkValue(<%=isSrcExp%>, <%=calcComision%>, <%=idServicio%>);" onfocus="getRadiosChecked();"> 
                                                </td>
                                                <%}%>
                                            </tr>
                                            <tr>
                                                <td><input id="idEfectivoCyP" type="radio" name="TIPO_DE_PAGO"  onkeypress="onKeyPressed(event, 'idBtnEnviarCyP');" onclick="isCheque2(1);"value="1" checked="true"><label id="labelConfirm">Efectivo</label></td>                                                   
                                                <td><input id="idChequeLocal"  <%=(!showCheque ? "disabled=\"disabled\"" : "")%> type="radio" name="TIPO_DE_PAGO" onkeypress="onKeyPressed(event, 'idBancoCyP');" onclick="isCheque2(3);" value="2"><label id="labelConfirm">Cheque Continental</label></td>
                                                <td><input id="idChequeOtras"  <%=(!showCheque ? "disabled=\"disabled\"" : "")%> type="radio" name="TIPO_DE_PAGO" onkeypress="onKeyPressed(event, 'idBancoCyP');" onclick="isCheque2(2);" value="2"><label id="labelConfirm">Cheque Otros Bancos</label></td>                                                    
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td><label id="labelConfirm">Banco</label></td><td align="left">
                                                    <select id="idBancoCyP" disabled="disabled" >
                                                        <option value="" size="20"></option>
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
                                                <td><label id="labelConfirm">Nro. de Cheque</label></td><td align="left"><input id="idNroChequeCyP" autocomplete="off" size="15" name="NRO_DE_CHEQUE" align="right"  onkeyup="this.value = replaceAllNoNumbers(this.value)" onblur="this.value = replaceAllNoNumbers(this.value);" onkeypress="onKeyPressed(event, 'idFechaChequeCyP');" disabled="disabled"></td>
                                            </tr>    
                                            <tr>                                                    
                                                <td></td>
                                                <td><label id="labelConfirm">Fecha Cheque</label></td><td align="left"><input id="idFechaChequeCyP" autocomplete="off" size="15" name="" align="right" onkeypress="onKeyPressed(event, 'idBtnEnviarCyP');" onkeyup="this.value = replaceAllNoNumbers(this.value)" onblur="checkFechaCheque(this.value)" disabled="disabled"></td>
                                            </tr>

                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input style="width:150px" type="reset" name="btnCancelar" id="idBtnCancelar" value="Cancelar" onkeypress="onKeyPressed(event, 'idEfectivoCyP');" onclick="panelCyP(<%=session.getAttribute("panel")%>);">
                                                </td>
                                                <td>
                                                    <input style="width:150px" type="submit" name="btnEnviar" id="idBtnEnviarCyP" value="Confirmar" onclick="ticketCyP();">
                                                    <input type="hidden" value='<%=session.getAttribute("servicio")%>' name="SERVICIO" id="idServicioCyP">
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
        <script type="text/javascript">
            document.getElementById('idEfectivoCyP').focus();
            document.getElementById('idBancoCyP').addEventListener('change', changed, false);
            function changed() {
                document.getElementById('idNroChequeCyP').focus();
            }
            function onKeyPressed(e, d) {
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
                if (keyPressed == 13 || keyPressed == 9)
                {
                    document.getElementById(d).focus();
                }
            }
            function isCheque2(tipoPago) {

                if (tipoPago == 1) {
                    document.getElementById("idBancoCyP").disabled = true;
                    document.getElementById("idNroChequeCyP").disabled = true;
                    document.getElementById("idNroChequeCyP").value = "";
                    document.getElementById("idFechaChequeCyP").value = "";
                    document.getElementById("idBancoCyP").value = "";
                    document.getElementById("idFechaChequeCyP").disabled = true;
                    document.getElementById("idBtnEnviarCyP").disabled = false;
                } else if (tipoPago == 2) {
                    document.getElementById("idBancoCyP").disabled = false;
                    document.getElementById("idNroChequeCyP").disabled = false;
                    document.getElementById("idFechaChequeCyP").disabled = false;
                    document.getElementById("idBancoCyP").value = "";
                    document.getElementById('idBancoCyP').focus();
                } else if (tipoPago == 3) {
                    document.getElementById("idBancoCyP").disabled = true;
                    document.getElementById("idNroChequeCyP").disabled = false;
                    document.getElementById("idFechaChequeCyP").disabled = false;
                    document.getElementById("idBancoCyP").value = 3;
                    document.getElementById('idNroChequeCyP').focus();
                }
            }

            function calcularMontoAnteriores(ctrl)
            {
                var marcado;
                for (i = 0; i < ctrl.length; i++)
                    if (ctrl[i].checked)
                        marcado = i;

                var sum = 0;
                for (i = 0; i < marcado + 1; i++) {
                    ctrl[i].checked = true;
                }

                for (i = 0; i <= marcado; i++) {
                    sum += parseFloat(document.getElementById("idHiddenMonto" + ctrl[i].value).value);
                }
                document.getElementById("idMontoAPagar").value = addCommas(String(sum));
            }

            function calcularMontoIndependiente(ctrl)
            {
                var sum = 0;
                for (i = 0; i < ctrl.length; i++) {

                    if (ctrl[i].checked) {
                        sum += parseFloat(document.getElementById("idHiddenMonto" + ctrl[i].value).value);
                    }
                }
                document.getElementById("idMontoAPagar").value = addCommas(String(sum));
                checkValue(<%=isSrcExp%>, <%=calcComision%>, <%=idServicio%>);
            }

            function showMonto(nroPago)
            {
                var marcado;
                for (i = 0; i < nroPago.length; i++)
                    if (nroPago[i].checked)
                        marcado = i;

                document.getElementById("idMontoAPagar").value = addCommas(String(parseFloat(document.getElementById("idHiddenMonto" + nroPago[marcado].value).value)));
            }

            function numbersAndDecimalsOnly(myfield, e, dec)
            {
//                console.log('xxx');
//                console.log(document.getElementById('idMonedaActual').value);
                if (document.getElementById('idMonedaActual').value == 600) {
//                    console.log('holis');
                    return numbersonly(myfield, e, dec);
                }

                var value = document.getElementById('idMontoAPagar').value;
//                console.log(value);
                var key;
                var keychar;


                if (window.event)
                    key = window.event.keyCode;
                else if (e)
                    key = e.which;
                else
                    return true;
                keychar = String.fromCharCode(key);

                // control keys
                if ((key == null) || (key == 0) || (key == 8) ||
                        (key == 9) || (key == 13) || (key == 27) || (key === 188))
                    return true;
                // numbers
                else if (value.indexOf(".") > -1 && keychar === ".")
                    return false;
                else if (value.indexOf(".") > -1 && "0123456789".indexOf(keychar) > -1) {
//                    console.log(value);
                    var sp = value.split(".");
                    if (sp[1].length < 2)
                        return true;
                    else
                        return false;
                }
                else if ((("0123456789.").indexOf(keychar) > -1))
                    return true;
                // decimal point jump
                else if (dec && (keychar == "."))
                {
                    myfield.form.elements[dec].focus();
                    return false;
                }
                else
                    return false;
            }

            function numbersonly(myfield, e, dec)
            {
                var key;
                var keychar;

                if (window.event)
                    key = window.event.keyCode;
                else if (e)
                    key = e.which;
                else
                    return true;
                keychar = String.fromCharCode(key);

                // control keys
                if ((key == null) || (key == 0) || (key == 8) ||
                        (key == 9) || (key == 13) || (key == 27))
                    return true;

                // numbers
                else if ((("0123456789").indexOf(keychar) > -1))
                    return true;

                // decimal point jump
                else if (dec && (keychar == "."))
                {
                    myfield.form.elements[dec].focus();
                    return false;
                }
                else
                    return false;
            }

            function getRadiosChecked() {

                var radios = document.getElementsByName('NRO_PAGO');
                var on = null;
                for (var i = 0, length = radios.length; i < length; i++) {
                    if (radios[i].checked) {
                        on = radios[i].value;
//                        console.log(on);
                        break;
                    }
                }

                if (on !== null) {

                    var select = document.getElementById('idMonedaCyP' + on);
                    var selectedMoneda = select.options[select.selectedIndex].value;
//                    console.log('selectedMoneda');
//                    console.log(selectedMoneda);
                    document.getElementById('idMonedaActual').value = selectedMoneda.split("#")[0];
//                    console.log(document.getElementById('idMonedaActual').value);
                }
            }
        </script>
    </body>
</html>
