<%--
    Document   : error
    Created on : Aug 18, 2010, 11:54:54 AM
    Author     : konecta
--%>
<%@page import="py.com.documenta.onlinemanager.ws.OlResponseConsulta"%>
<%@page import ="org.netbeans.xml.schema.common.RespuestaCobranza;" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <style type="text/css">
        #box{
            background-color:#04819e;
            -webkit-border-radius: 14px;
            -moz-border-radius: 14px;
            border-radius: 14px;
        }
        #container_box{
            background-color:#38b2ce;
        }
    </style>
    <body>
        <table style="background: url(images/waterMark<%String sTexto = session.getAttribute("facturador") != null ? (String) session.getAttribute("facturador"):session.getAttribute("servicio")!=null?(String)session.getAttribute("servicio"):"All";
                                                                String fondo= "";
                                                                for (int x = 0; x < sTexto.length(); x++) {
                                                                    if (sTexto.charAt(x) != ' ') {
                                                                        fondo += sTexto.charAt(x);
                                                                    }
                                                                }
                                                                out.println(fondo+".png");%>)" cellspacing="5" cellpadding="10" width="100%" height="100%" border="0">
            <tbody>
                <tr>
                    <td>
                        <table id="box" align="center" border="2" cellpadding="1" cellspacing="5">
                            <tr>
                                <td>
                                    <table id="container_box" align="center" border="0" cellpadding="1" cellspacing="10">
                                        <tbody>
                                            <tr><td></td>
                                                <td>
                                                    <label style="font-size:15px"><%
                                                        if(session.getAttribute("respuestaConsulta")!=null && session.getAttribute("error")!=null){
                                                            OlResponseConsulta respConsulta = (OlResponseConsulta)session.getAttribute("respuestaConsulta");
                                                            out.println(respConsulta.getMensajeOperacion().replace("\n", "<br>"));
                                                        }else if(session.getAttribute("respuestaCobranza")!=null){
                                                            RespuestaCobranza respCobranza = (RespuestaCobranza)session.getAttribute("respuestaCobranza");
                                                            out.println(respCobranza.getEstado().getDescripcion());
                                                        }session.removeAttribute("respuestaConsulta");session.removeAttribute("respuestaCobranza");session.removeAttribute("error");
                                                                                %></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td colspan="2" align="center" >
                                                    <input style="width:100px"type="button"  name="btnVolver" id="idBtnVolverCyP" value="Volver" onclick="panelCyP(1);"/>
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
        <script type="text/javascript">
            document.getElementById('idBtnVolverCyP').focus();
        </script>

    </body>
</html>
