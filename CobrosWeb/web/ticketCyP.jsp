<%--
    Document   : ticket
    Created on : Aug 18, 2010, 11:55:26 AM
    Author     : konecta
--%>
<%@page import="py.com.konecta.redcobros.cobrosweb.utiles.StringUtils"%>
<%@page import ="org.netbeans.xml.schema.common.RespuestaCobranza;" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
    <style type="text/css">
        #ticket_box{
            background-color:#04819e;
            -webkit-border-radius: 14px;
            -moz-border-radius: 14px;
            border-radius: 14px;
        }
        #ticket_container_box{
            background-color:#38b2ce;
        }
    </style>
        <script type="text/javascript">
        <%
            String tipoRestriccionPractigiros = String.valueOf(request.getSession().getAttribute("tipoRestriccionPractigiros"));
            String mensajeRestriccionPractigiros = String.valueOf(request.getSession().getAttribute("mensajeRestriccionPractigiros"));
        %>

        <%out.println("console.log('" + tipoRestriccionPractigiros + "');");%>
        <%out.println("console.log('" + mensajeRestriccionPractigiros + "');");%>

        <% if ("4".equalsIgnoreCase(tipoRestriccionPractigiros)) { %>
        <%if (!"null".equalsIgnoreCase(mensajeRestriccionPractigiros)) {%>
        Ext.Msg.show({
            title: 'Alerta Practigiros!',
            msg: <%out.print("'" + mensajeRestriccionPractigiros + "'");%>,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.INFO
        });
        <%}%>
        <%}%>
    </script>
    <script type="text/javascript">
        var cadenaDeImpresion = <% RespuestaCobranza respCobranza = (RespuestaCobranza) session.getAttribute("respuestaCobranza");
            
            Boolean printTicket = session.getAttribute("printTicket") != null ?(Boolean)session.getAttribute("printTicket") : true;
            session.removeAttribute("printTicket");
            String ticket = respCobranza.getTicket();
            String ticketCopy = respCobranza.getTicket(); 
            String mensajeImpresora = ticket.replaceAll("\n", ";;;").replaceAll("###", "");
            String[] ticketImpresora = mensajeImpresora.split(";;;");
            String cadenaImpresionTicket = "";
            int repTicket = 1;
            Boolean isDDJJ = false;      
            String tipoOp = "";
            String idTipoDoc = "";
            String nroDoc = "";
            String idServicio = (String)session.getAttribute("idServicio"); 
            if(idServicio!=null && !idServicio.isEmpty()){
                if(idServicio.equals("131")){
                    tipoOp = "1";
                    ticket = ticketCopy.substring(0,ticket.indexOf("###")) + ticketCopy.substring(ticket.lastIndexOf("###"), ticket.length());                    
                }else if(idServicio.equals("132")){ 
                    tipoOp = "2";
                }
                if(idServicio.equals("131") || idServicio.equals("132") ){
                    repTicket = 2; 
                   String val =  StringUtils.getVal(respCobranza.getAdicional(), "CLI_DDJJ");
                   if(val != null){
                       if(Integer.valueOf(val) == 1){
                           isDDJJ = true;   
                       }
                   }
                   val =  StringUtils.getVal(respCobranza.getAdicional(), "NRO_DOC");
                   if(val != null){
                       nroDoc = val;
                   } 
                   val =  StringUtils.getVal(respCobranza.getAdicional(), "TIPO_DOC");
                   if(val != null){
                       idTipoDoc = val;
                   }                   
                }
            }

            ticket = ticket.replaceAll("\n", "<br>");
            int c = 0;
            for (String ooo : ticketImpresora) {
                cadenaImpresionTicket += 1 + ";;" + "N" + ";;" + ooo + ";;;";
                if (session.getAttribute("nombreTicket") != null && c == 8) {
                       cadenaImpresionTicket += 1 + ";;" + "N" + ";;" + (String) session.getAttribute("nombreTicket") + ";;;";
                       session.removeAttribute("nombreTicket");
                }
                c++;
            }
            cadenaImpresionTicket = cadenaImpresionTicket.replace("%", "");
            out.println("\"" + cadenaImpresionTicket + "\"");%>;
            
            <%if(printTicket){%>
                Ext.onReady(function() {    
                    <%for(int i = 0; i < repTicket; i++){%>
                        imprimirImpresoraExterna(cadenaDeImpresion);
                    <%}%>                    
                });
            <%}%>

    </script>
    <table style="background: url(images/waterMark<%String sTexto = session.getAttribute("servicio") != null ? (String) session.getAttribute("servicio") : session.getAttribute("facturador") != null ? (String) session.getAttribute("facturador") : "All";
        String fondo = "";
        for (int x = 0; x < sTexto.length(); x++) {
                if (sTexto.charAt(x) != ' ') {
                    fondo += sTexto.charAt(x);
                }
            }
            out.println(fondo + ".png");%>)" cellspacing="1" cellpadding="1" width="100%" height="100%" border="0">
        <tbody>
            <tr>
                <td>
                    <table id="ticket_box" align="center" border="5" cellpadding="1" cellspacing="5">
                        <tr>
                            <td>
                                <table id="ticket_container_box" cellspacing="1" cellpadding="1" width="100%" height="100%" border="1">
                                    <tbody>
                                        <tr>
                                            <td align="center" style="background-color:#ffffff; padding:30px;">
                                                <label style="font-size:10px"><%out.println(ticket);%></label>
                                            </td>
                                        </tr>
                                        <% if (respCobranza.getIdTransaccion() != null) {%>
                                        <% /*<tr>
                                            <td align="center" style="background-color:#00b64f">
                                            <!pre style="font-size:20px">Número de Transacción: <%=respCobranza.getIdTransaccion()</pre>
                                               </td>
                                               </tr>*/%>
                                        <%session.removeAttribute("respuestaConsulta");
                                                    session.removeAttribute("respuestaCobranza");
                                                    session.removeAttribute("error");
                                                }%>
                                        <tr>
                                            <td align="center"  style="background-color:#ffffff; padding:25px;">
                                                <%if(!isDDJJ){%>
                                                    <input style="width:120px" type="button" name="btnAceptarTicket" id="idBtnAceptarTicketCyP" value="Aceptar" onclick="panelCyP(1);">
                                                <%}else{%>
                                                    <input style="width:120px" type="button" name="btnOKDDJJCyP" id="idBtnOKDDJJCyP" value="Presento DDJJ" onclick="presentoDDJJ(<%=idTipoDoc%>,<%=nroDoc%>,'S' ,<%=tipoOp%>,<%=respCobranza.getIdTransaccion()%>);"> 
                                                    <input style="width:120px" type="button" name="btnNOKDDJJCyP" id="idBtnNOKDDJJCyP" value="No Presento DDJJ" onclick="presentoDDJJ(<%=idTipoDoc%>,<%=nroDoc%>,'N' ,<%=tipoOp%>,<%=respCobranza.getIdTransaccion()%>);">
                                                <%}%>
                                                <input style="width:120px" type="button" name="btnImprimirTicket" id="idBtnImprimirTicketCyP" value="Imprimir" onclick=" imprimirImpresoraExterna(cadenaDeImpresion);">
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>

