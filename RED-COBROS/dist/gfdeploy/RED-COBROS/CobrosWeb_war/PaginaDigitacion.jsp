<%--
    Document   : PaginaDigitacion
    Created on : Sep 2, 2009, 1:10:34 PM
    Author     : gustavo
--%>

<%@page import="java.util.*, javax.naming.*, py.com.konecta.redcobros.ejb.*,py.com.konecta.redcobros.cobrosweb.clases.ConstructorFormulario ,py.com.konecta.redcobros.entities.*;" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Last-Modified" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
        <meta http-equiv="Pragma" content="no-cache">
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            
    </head>
    <body>
        <%
        //SchemaQuery shcemaQ = (SchemaQuery) session.getAttribute("schemaQ");
        HashMap<String, String> mapa = (HashMap) session.getAttribute("camposDerivados");
       //  CampoControlador cc=new CampoControlador();
        //out.println("hola "+mapa);
        String versionForm = request.getParameter("idFormulario")!=null && !request.getParameter("idFormulario").isEmpty()?request.getParameter("idFormulario"):null;
        if(versionForm.equals("-1")){
            if(mapa!=null)mapa=null;
        }
        if (mapa == null || mapa.size() == 0) {
        %>
    </body>
</html>
<%
            return;
        }
        //Campo[] campos;
        List<Campo> lcampos = null;
        String formulario = null;
        String version = null;
        Context context = new InitialContext();
        try {
            formulario = mapa.get("numeroFormulario");

            version = mapa.get("version");
            Formulario f=new Formulario();
            f.setNumeroFormulario(Integer.parseInt(formulario));
            f.setVersion(Integer.parseInt(version));
            Campo campo=new Campo();
            campo.setFormulario(f);

            CampoFacade campoF = (CampoFacade)context.lookup(CampoFacade.class.getName());
            //lcampos = shcemaQ.getCampos(Integer.parseInt(formulario), Integer.parseInt(version));
            lcampos=campoF.list(campo,new String [] {"idRubro","numero"},new String [] {"asc","asc"});
        } catch (Exception exc) {

            exc.printStackTrace();
        }
        if ((lcampos != null) && (lcampos.size() > 0)) {
            ConstructorFormulario cf = new ConstructorFormulario(lcampos.toArray(new Campo[lcampos.size()]));
            boolean construir = cf.construirFormulario();
           
            if (construir) {

                String numeroImpuesto = mapa.get("numeroImpuesto");
                String ruc = mapa.get("ruc");
                String fechaPresentacion = mapa.get("fechaPresentacion");
                String periodoFiscal = mapa.get("periodoFiscal");

                String[] param = null;
                
                if (!mapa.get("numeroFormulario").equalsIgnoreCase("90")){
                    //String periodoFiscal = mapa.get("periodoFiscal");
                

                
                    try {
                        /*String res = shcemaQ.getVencimiento(
                                Integer.parseInt(formulario),
                                Integer.parseInt(version),
                                Integer.parseInt(numeroImpuesto),
                                ruc, fechaPresentacion, periodoFiscal);*/


                        UtilFacade utilF = (UtilFacade)context.lookup(UtilFacade.class.getName());
                        String res = utilF.calcularVencimiento(
                                Integer.parseInt(formulario),
                                Integer.parseInt(version),
                                Integer.parseInt(numeroImpuesto),
                                ruc, fechaPresentacion, periodoFiscal);

                        param = res.split(":");

                    } catch (Exception e) {
                        e.printStackTrace();
    %>

</body>
</html>
<%
                        return;
                    }
                 }
                 /*else{

                     String fechaForm90 = mapa.get("fechaForm90");



                     try {
                        String res = shcemaQ.getVencimiento(
                                Integer.parseInt(formulario),
                                Integer.parseInt(version),
                                Integer.parseInt(numeroImpuesto),
                                ruc, fechaPresentacion, periodoFiscal);


                        UtilFacade utilF = (UtilFacade)context.lookup(UtilFacade.class.getName());
                        String res = utilF.calcularVencimiento(
                                Integer.parseInt(formulario),
                                Integer.parseInt(version),
                                Integer.parseInt(numeroImpuesto),
                                ruc, fechaPresentacion, fechaForm90);

                        param = res.split(":");

                     } catch (Exception e) {
                        e.printStackTrace();
                        return;
                     }

                }*/
                

%>

<div  align="center" style="background: #DBE2F1">
    <h1>Formulario</h1>
    <br>
    <form name="formulario" action="" method="get">

<%if (!mapa.get("numeroFormulario").equalsIgnoreCase("90")){%>
        <h1>Fecha de vencimiento: <%= param[0]%></h1>
        <h1>Días de atrasos: <%= param[1]%></h1>

<%}%>
     

            <table  id="tablacampos" bgcolor="#DBE2F1" border="1">
            <INPUT NAME="minimoCampo" ID="minimoCampo" TYPE=HIDDEN value="<%= cf.getMinimoNumeroCampo()%>">
            <INPUT NAME="maximoCampo" ID="maximoCampo" TYPE=HIDDEN value="<%= cf.getMaximoNumeroCampo()%>">
            <INPUT NAME="numeroFormulario" ID="numeroFormulario" TYPE=HIDDEN value="<%= formulario%>">
            <INPUT NAME="numeroImpuesto" ID="numeroImpuesto" TYPE=HIDDEN value="<%= numeroImpuesto%>">
            <INPUT NAME="version" ID="version" TYPE=HIDDEN value="<%= version%>">
            <INPUT NAME="fechaPresentacion" ID="fechaPresentacion" TYPE=HIDDEN value="<%= mapa.get("fechaPresentacion")%>">
<%if (!mapa.get("numeroFormulario").equalsIgnoreCase("90")){%>
            <INPUT NAME="fechaVencimiento" ID="fechaVencimiento" TYPE=HIDDEN value="<%= param[0]%>">
            <INPUT NAME="numeroDiasAtraso" ID="numeroDiasAtraso" TYPE=HIDDEN value="<%= param[1]%>">
            <INPUT NAME="numeroMesesAtraso" ID="numeroMesesAtraso" TYPE=HIDDEN value="<%= param[2]%>">
            <INPUT NAME="decJuradaOrig" ID="decJuradaOrig" TYPE=HIDDEN value="<%= mapa.get("decJuradaOrig")%>">
            <INPUT NAME="decJuradaRectif" ID="decJuradaRectif" TYPE=HIDDEN value="<%= mapa.get("decJuradaRectif")%>">
            <INPUT NAME="decNumeroRectif" ID="decNumeroRectif" TYPE=HIDDEN value="<%= mapa.get("decNumeroRectif")%>">
            <INPUT NAME="decJuradaClausuraCese" ID="decJuradaClausuraCese" TYPE=HIDDEN value="<%= mapa.get("decJuradaClausuraCese")%>">
            <INPUT NAME="periodoFiscal" ID="periodoFiscal" TYPE=HIDDEN value="<%= mapa.get("periodoFiscal")%>">
            
<%}%>
            <INPUT NAME="ruc" ID="ruc" TYPE=HIDDEN value="<%= ruc%>">
            <INPUT NAME="dv" ID="dv" TYPE=HIDDEN value="<%= mapa.get("dv")%>">
            
<%if (mapa.get("numeroFormulario").equalsIgnoreCase("90")){%>
   <INPUT NAME="C4"  ID="C4"  TYPE=HIDDEN value="<%= mapa.get("fechaPresentacion")%>">
   <INPUT NAME="C10" ID="C10" TYPE=HIDDEN value="<%= mapa.get("radioFormulario90").equalsIgnoreCase("10")?"10":"0"%>">
   <INPUT NAME="C11" ID="C11" TYPE=HIDDEN value="<%= mapa.get("radioFormulario90").equalsIgnoreCase("11")?"11":"0"%>">
   <INPUT NAME="C12" ID="C12" TYPE=HIDDEN value="<%= mapa.get("radioFormulario90").equalsIgnoreCase("12")?"12":"0"%>">
   <INPUT NAME="C13" ID="C13" TYPE=HIDDEN value="<%= mapa.get("radioFormulario90").equalsIgnoreCase("13")?"13":"0"%>">
   <INPUT NAME="C14" ID="C14" TYPE=HIDDEN value="<%= mapa.get("radioFormulario90").equalsIgnoreCase("14")?"14":"0"%>">
<%}%>        
            <INPUT NAME="validado" ID="validado" TYPE=HIDDEN value="2">
            
            <thead>
                <tr>


                </tr>
            </thead>
            <tbody>
                <%
    Campo[][] matrizFormulario = cf.getMatriz();
    //System.out.println(cf.getMaxFila());
    for (int i = 0; i < cf.getMaxFila(); i++) {
        //System.out.println(cf.getMaxColumnas()[i]);
        out.println("<tr>");
        out.println("<td>");
        if (cf.getRubrosFilas()[i] != null) {
            out.println(cf.getRubrosFilas()[i]);
        } else {
            out.println("-");
        }
        out.println("</td>");
        out.println("<td>");
        if (cf.getIncisosFilas()[i] != null) {
            out.println(cf.getIncisosFilas()[i]);
        } else {
            out.println("-");
        }
        out.println("</td>");
        for (int j = 0; j < cf.getMaxColumnas()[i]; j++) {
            out.println("<td>");
            if (matrizFormulario[i][j] != null) {

                %>
                <!--%= matrizFormulario[i][j].getNumero()%><input type="text" id="<%= matrizFormulario[i][j].getEtiqueta()%>" name="<%= matrizFormulario[i][j].getDescripcion()%>  "   onblur="miFuncion('<%= matrizFormulario[i][j].getEtiqueta()%>');" value="0"/-->
                 <%= matrizFormulario[i][j].getNumero()%><input onkeyup= "replaceAllNoNumbers2(value,id)" type="text" id="<%= matrizFormulario[i][j].getEtiqueta()%>" name="<%= matrizFormulario[i][j].getDescripcion()%>  "   value="0"/>

                <%
                        } else {
                %>

                <%                        }
            out.println("</td>");
        }
        out.println("</tr>");
        mapa.clear();
    }
                %>
            </tbody>
        </table>
    </form>
</div>
<%
        }
    } else {
%>

<%  }

%>
</body>
</html>
