/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.clases;

//import controller.SchemaQuery;
//import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;
import py.com.konecta.redcobros.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import py.com.konecta.redcobros.ejb.ParamTasasFacade;
import py.com.konecta.redcobros.ejb.ParametrosFacade;
import py.com.konecta.redcobros.ejb.UtilFacade;
import java.util.HashMap;

/**
 *
 * @author gustavo
 */
public class Funciones {
    public static String funcionMayor = "MAYOR";
    public static String funcionMenor = "MENOR";
    public static String funcionElegir = "ELEGIR";
    public static String funcionBuscarTasa = "BUSCARTASA";
    public static String funcionBuscarValor = "BUSCARVALOR";
    public static String funcionSi = "SI";
    public static String funcionCalcularMora = "CALCULARMORA";
    public static String funcionCalcularMulta = "CALCULARMULTA";
    public static String funcionCalcularInteres = "CALCULARINTERES";
    public static String funcionValidarFecha = "VALIDARFECHA";
    public static String funcionFechaEntre = "FECHAENTRE";

    public static int numParamMayor=2;
    public static int numParamMenor=2;
    //-1:impar,-2:par,-3:cualquiera
    public static int numParamElegir=-3;
    public static int numParamBuscarTasa=1;
    public static int numParamBuscarValor=1;
    public static int numParamSi=3;
    public static int numParamCalcularMora=1;
    public static int numParamCalcularMulta=2;
    public static int numParamCalcularInteres=1;
    public static int numParamValidarFecha=1;
    public static int numParamFechaEntre=4;

    private static UtilFacade utilfacade;
    private static DominiosFacade dominiosFacade;
    static {
        try {
            utilfacade = (UtilFacade)((new InitialContext()).lookup(UtilFacade.class.getName()));
            dominiosFacade = (DominiosFacade)((new InitialContext()).lookup(DominiosFacade.class.getName()));
        }
        catch(Exception e){}
    }

    public static void validarLlamado(String funcion,int cantidadParametros,
            int cantidadParametrosRecibidos) throws Exception {
        if (cantidadParametros!=cantidadParametrosRecibidos) {
            if (cantidadParametros>0) {
                throw new Exception("Numero de parametros incorrectos " +
                        "al llamar a "+funcion+
                        ". Debe ser "+cantidadParametros+".");
            } else if (cantidadParametrosRecibidos==0) {
                throw new Exception("Numero de parametros incorrectos " +
                        "al llamar a "+funcion+". Debe ser par.");
            } else if (cantidadParametros==-1
                    && cantidadParametrosRecibidos%2==0) {
                throw new Exception("Numero de parametros incorrectos " +
                        "al llamar a "+funcion+". Debe ser par.");
            } else if (cantidadParametros==-2
                    && cantidadParametrosRecibidos%2!=0) {
                throw new Exception("Numero de parametros incorrectos " +
                        "al llamar a "+funcion+". Debe ser par.");
            }
        }
    }
    public static String si(List<String> parametros) throws Exception {
        validarLlamado(funcionSi, numParamSi, parametros.size());
        if (parametros.get(0).toString().equals("0.0")) {
            return parametros.get(2).toString();
        } else {
            return parametros.get(1).toString();
        }

    }

    public static String elegir(List<String> parametros) throws Exception {
        validarLlamado(funcionElegir, numParamElegir, parametros.size());
        int i=1;
        String expresion=parametros.get(0).toString();
        while (true) {
            if (i<parametros.size()) {
                if (i+1<parametros.size()) {
                    //tenemos esperado y respuesta
                    if (expresion.equalsIgnoreCase(parametros.get(i).toString())) {
                        return parametros.get(i+1).toString();
                    } else {
                        //avanzamos al siguiente esperado
                        i+=2;
                    }
                } else {
                    //tenemos solo esperado, por tanto este no es esperado
                    //sino que es default
                    return parametros.get(i).toString();
                }
            } else {
                break;
            }
        }
        return "0";
    }

    public static String mayor(List<String> parametros) throws Exception {
        validarLlamado(funcionMayor, numParamMayor, parametros.size());
        if (Double.parseDouble(parametros.get(0).toString()) >
                Double.parseDouble(parametros.get(1).toString())) {
            return parametros.get(0).toString();
        } else {
            return parametros.get(1).toString();
        }
    }

    public static String menor(List<String> parametros) throws Exception {
        validarLlamado(funcionMenor, numParamMenor, parametros.size());
        if (Double.parseDouble(parametros.get(0).toString()) >
                Double.parseDouble(parametros.get(1).toString())) {
            return parametros.get(1).toString();
        } else {
            return parametros.get(0).toString();
        }
    }

    public static String buscarTasa(List<String> parametros,HashMap variables) throws Exception {
        validarLlamado(funcionBuscarTasa, numParamBuscarTasa, parametros.size());
        double p1 = Double.parseDouble(parametros.get(0).toString());


        /*//obtener tasa a partir de p desde la bd
        SchemaQuery sq=new SchemaQuery();

        return ""+sq.getTasa(Integer.parseInt((String)variables.get("NUMFORMULARIO")),
                (int)p1,
                (String)variables.get("PERIODOFISCAL"),
                Integer.parseInt((String)variables.get("VERSION")));*/
        Context context = new InitialContext();
        ParamTasasFacade ptF = (ParamTasasFacade)context.lookup(ParamTasasFacade.class.getName());
        Double valor=ptF.obtenerTasa(Integer.parseInt((String)variables.get("NUMFORMULARIO")),
                (int)p1,
                (String)variables.get("PERIODOFISCAL"),
                Integer.parseInt((String)variables.get("VERSION")));
       
        return ""+valor;
    }

    public static String buscarValor(List<String> parametros) throws Exception {
        validarLlamado(funcionBuscarValor, numParamBuscarValor, parametros.size());
        double p = Double.parseDouble(parametros.get(0).toString());
        /*SchemaQuery sq=new SchemaQuery();

        return ""+sq.getValor((int)p);*/
        Context context = new InitialContext();
        ParamTasasFacade ptF = (ParamTasasFacade)context.lookup(ParamTasasFacade.class.getName());
        Double valor=ptF.obtenerValor((int)p);
      
        return ""+valor;
    }

    public static String calcularMora(List<String> parametros,HashMap variables) throws Exception {
        validarLlamado(funcionCalcularMora, numParamCalcularMora, parametros.size());
        double p1 = Double.parseDouble(parametros.get(0).toString());

        //obtener mora a partir de p desde la bd
        /*SchemaQuery sq=new SchemaQuery();
        return ""+sq.getMora(p1,Integer.parseInt((String)variables.get("MESESATRASOS")));*/
        Context context = new InitialContext();
        ParametrosFacade pF = (ParametrosFacade)context.lookup(ParametrosFacade.class.getName());
        Double valor=pF.obtenerMora(p1, Integer.parseInt((String)variables.get("MESESATRASOS")));
      
        return ""+valor;
    }
    public static String calcularMulta(List<String> parametros) throws Exception {
        validarLlamado(funcionCalcularMulta, numParamCalcularMulta, parametros.size());
        double p1 = Double.parseDouble(parametros.get(0).toString());
        double p2 = Double.parseDouble(parametros.get(1).toString());
        //obtener multa a partir de p1 y p2 desde la bd
        /*SchemaQuery sq=new SchemaQuery();
        return ""+sq.getMulta((int)p1,p2);*/
        Context context = new InitialContext();
        ParametrosFacade pF = (ParametrosFacade)context.lookup(ParametrosFacade.class.getName());
        Double valor=pF.obtenerMulta((int)p1, p2);
       
        return ""+valor;
    }
    public static String calcularInteres(List<String> parametros,HashMap variables) throws Exception {
        validarLlamado(funcionCalcularInteres, numParamCalcularInteres, parametros.size());
        double p1 = Double.parseDouble(parametros.get(0).toString());
        //obtener mora a partir de p desde la bd
        /*SchemaQuery sq=new SchemaQuery();
        return ""+sq.getInteres(p1,Integer.parseInt((String)variables.get("DIASATRASOS")));*/
        Context context = new InitialContext();
        ParametrosFacade pF = (ParametrosFacade)context.lookup(ParametrosFacade.class.getName());
        Double valor=pF.obtenerInteres(p1, Integer.parseInt((String)variables.get("DIASATRASOS")));
       
        return ""+valor;
    }

    public static String ValidarFecha(List<String> parametros,HashMap variables) throws Exception{
        validarLlamado(funcionValidarFecha, numParamValidarFecha, parametros.size());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy",Locale.getDefault());
        Date fecha = null;
//        String tmp = new String (parametros.get(0)).toString();
//
//        String valor[] = tmp.split("E", 2);
//
//        tmp = valor[0].replace(".","");
        try {
            formatoFecha.setLenient(false);
            fecha = formatoFecha.parse(parametros.get(0));
            return "1.0";
        } catch (Exception ex) {
            return "0.0";
        }
    }

    public static String FechaEntre(List<String> parametros, HashMap<String, String> variables) throws Exception{
        validarLlamado(funcionFechaEntre, numParamFechaEntre, parametros.size());

        if(parametros.get(3).equals("0")){
            return("0.0");
        }
        int numeroFormulario, version, numeroImpuesto;
        String ruc;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy",Locale.getDefault());
        String atrasos;
        String []fechatmp=parametros.get(1).split("/");
                String cadena=null;
                if (fechatmp.length==3) {
                    cadena=fechatmp[0]+fechatmp[1]+fechatmp[2];
                } 
        try {
            formatoFecha.setLenient(false);
            Date fechaIni = formatoFecha.parse(parametros.get(0));
            Date fechaFin = formatoFecha.parse(cadena);
            Date fecha = formatoFecha.parse(parametros.get(2));
            if(fecha.compareTo(fechaIni) >= 0 && fecha.compareTo(fechaFin) <= 0) {
                numeroFormulario = Integer.parseInt((String)variables.get("NUMFORMULARIO"));
                version = Integer.parseInt((String)variables.get("VERSION"));
                numeroImpuesto = Integer.parseInt((String)variables.get("NUMIMPUESTO"));
                ruc = (String)variables.get("RUC");
                atrasos = utilfacade.calcularVencimientoForm90(numeroFormulario, version, numeroImpuesto, ruc, parametros.get(2), cadena);
                String []diasmesesatrasos = atrasos.split(":");

                    variables.put("DIASATRASOS", diasmesesatrasos[1]);
                    variables.put("MESESATRASOS",diasmesesatrasos[2]);

                return "1.0";
            }
            return "0.0";
        } catch (Exception ex) {
            variables.put("DIASATRASOS", "0");
            variables.put("MESESATRASOS", "0");
            return "0.0";
        }
    }
}
