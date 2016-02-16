<%-- 
    Document   : ticket
    Created on : Aug 18, 2010, 11:55:26 AM
    Author     : konecta
--%>

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
        function volver(){
            cardInicial();
        }
        var cadenaDeImpresion = "<%=request.getAttribute("ticketImpresora")%>";
        Ext.onReady(function() {
            imprimirImpresoraExterna(cadenaDeImpresion);
        });
        document.getElementById('idBtnAceptarTicket').focus();
    </script>
    <table style="background: url(images/waterMark<%String sTexto = request.getAttribute("servicio") != null ? (String)request.getAttribute("servicio") : "All";
                String fondo = "";
                for (int x = 0; x < sTexto.length(); x++) {
                        if (sTexto.charAt(x) != ' ') {
                            fondo += sTexto.charAt(x);
                        }
                    }
                    out.println(fondo + ".png");%>)" cellspacing="5" cellpadding="20" width="100%" height="100%" border="0">
        <tbody>
            <tr>
                <td>
                    <table id="ticket_box" align="center" border="5" cellpadding="1" cellspacing="5">
                        <tr>
                            <td>
                                <table id="ticket_container_box" cellspacing="2" cellpadding="1" width="100%" height="100%" border="1">
                                    <tbody>
                                        <tr>
                                            <td align="center" style="background-color:#ffffff; padding:30px;">
                                                <pre style="font-size:10px"><%=request.getAttribute("mensaje")%></pre>
                                            </td>
                                        </tr>
                                        <% if (request.getParameter("ID_TRANSACCION") != null) {%>
                                        <%/*<tr>
                                             <td align="center" style="background-color:#00b64f">
                                             <label style="font-size:20px">Número de Transacción: <%=request.getParameter("ID_TRANSACCION")</label>
                                                 </td>
                                                 </tr>*/%>
                                        <%}%>
                                        <tr>
                                            <td align="center"  style="background-color:#ffffff; padding:25px;">
                                                <input style="width:120px" type="button" name="btnAceptarTicket" id="idBtnAceptarTicket" value="Aceptar" onclick="volver();">
                                                <input style="width:120px" type="button" name="btnImprimirTicket" id="idBtnImprimirTicket" value="Imprimir" onclick=" imprimirImpresoraExterna(cadenaDeImpresion);">
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

