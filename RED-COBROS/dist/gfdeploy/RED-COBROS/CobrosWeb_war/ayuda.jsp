<%-- 
    Document   : ayuda
    Created on : Oct 11, 2011, 7:12:24 PM
    Author     : documenta
--%>
<%@page import="py.com.konecta.redcobros.entities.Facturador"%>
<%@page import="py.com.konecta.redcobros.ejb.FacturadorFacade"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<table border="1" cellspacing="1" cellpadding="20" width="100%">
    <tr>

        <%  int con = 0;
            Context context = new InitialContext();
            FacturadorFacade facturadorFacade = (FacturadorFacade) context.lookup(FacturadorFacade.class.getName());
            String servicio = request.getParameter("SERVICIO_AYUDA") != null && !request.getParameter("SERVICIO_AYUDA").isEmpty() ? request.getParameter("SERVICIO_AYUDA") : null;
            List<Facturador> lFacturador = facturadorFacade.getFacturadoresWithServicioRcHabilitados(servicio);
            for (Facturador it : lFacturador) {
                if(it.getIdFacturador()!=2L && it.getIdFacturador()!= 1L){//No incluye SET ni DOCUMENTA
                    if (con % 6 == 0) {
                        out.println("<tr>");
                    }
                    out.println("<td align='center' width='110' height='80'>");
            %>
        <a href="#"><img class="icono" style='opacity:0.4;filter:alpha(opacity=40);'onmouseover='this.style.opacity=1' onmouseout='this.style.opacity=0.4' alt="<%=it.getDescripcion()%>" onclick="tabServices(<%=it.getIdFacturador()%>);" src="images/<%=it.getDescripcion().replace(" ", "").replace(".","")+".png"%>"></a>
        <%    out.println("</td>");
                con++;
                if (con % 6 == 0 && con != 0) {
                    out.println("</tr>");
                }
                }

        }%>
</table>
