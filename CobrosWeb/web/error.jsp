<%-- 
    Document   : error
    Created on : Aug 18, 2010, 11:54:54 AM
    Author     : konecta
--%>


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
        <table style="background: url(images/waterMark<%String sTexto = request.getParameter("servicio") != null ? request.getParameter("servicio") : "All";
            String fondo = "";
            for (int x = 0; x < sTexto.length(); x++) {
                if (sTexto.charAt(x) != ' ') {
                    fondo += sTexto.charAt(x);
                }
            }
            out.println(fondo + ".png");%>)" cellspacing="5" cellpadding="10" width="100%" height="100%" border="0">
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
                                                    <label style="font-size:15px"><%String mensaje = (String) session.getAttribute("mensajeError");

                                                        out.print(mensaje != null && !mensaje.isEmpty() ? mensaje.replace("\n", "<br>") : "Servicio no disponible.<br> Favor vuelva a intentarlo en unos minutos.");
                                                        %></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td colspan="2" align="center" >
                                                    <input style="width:100px"type="button"  name="btnVolver" id="idBtnVolver" value="Volver" onclick="cardInicial();"/>
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
            document.getElementById('idBtnVolver').focus();
        </script>

    </body>
</html>
