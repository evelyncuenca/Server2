<%--
    Document   : servicios
    Created on : Oct 19, 2010, 5:23:47 PM
    Author     : konecta
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import=" java.text.SimpleDateFormat,
        java.util.Date,
        java.util.List,
        py.com.documenta.ws.DefinicionServicio;"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" type="text/css" href="css/style.css" />

    </head>
    <script type="text/javascript" language='javascript'>
        function getCabeceraServicio(servicio, tagRef, idServicio){
            document.getElementById("idServiceCyP").value = idServicio;
            getMainServicePanel(servicio, tagRef);
            Ext.getCmp('content-panel').layout.setActiveItem('panelCabeceraServicio');
        }
    </script>
    <body>

        <div id="container">

            <div class="image_box"><div class="image" style='opacity:0.4;filter:alpha(opacity=2);'onmouseover='this.style.opacity=50' onmouseout='this.style.opacity=0.4'>
                    <a href="#"><img class="icono" alt="Home" onclick="cardInicial();" src="images/Home.png" /></a><div class="bottomtext" >Inicio</div></div>
            </div>
            <% List<DefinicionServicio> lServicio = (List<DefinicionServicio>) session.getAttribute("SERVICIOS");
                        for (DefinicionServicio defSer : lServicio) {%>
            <div class="image_box"><div class="image" style='opacity:0.4;filter:alpha(opacity=2);'onmouseover='this.style.opacity=50' onmouseout='this.style.opacity=0.4'>
                    <a href="#"><img class="icono" alt="<%=defSer.getDescripcion()%>" onclick="getCabeceraServicio('<%=defSer.getDescripcion()%>','<%=defSer.getTagReferencia()%>','<%=defSer.getIdServicio()%>');" src="images/<%
                                                String sTexto = defSer.getDescripcion();
                                                String fondo = "";
                                                for (int x = 0; x < sTexto.length(); x++) {
                                                    if (sTexto.charAt(x) != ' ') {
                                                        fondo += sTexto.charAt(x);
                                                    }
                                                }
                                                out.println(fondo + ".png");%>" /></a><div class="bottomtext" ><%=defSer.getDescripcion()%></div></div>
            </div>
            <%}%>
        </div>
        <input type="hidden" value='' name="SERVICIO" id="idServiceCyP">
    </body>
</html>
