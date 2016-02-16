/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author gustavo
 */
public class EvaluadorExpresion {
    private String expresion;
    private HashMap variables;
    private List<Token> postFija;
    public EvaluadorExpresion(String expresion, HashMap variables) {
        this.expresion=expresion.replaceAll(" ", "");
        this.variables=variables;
    }

    private List<Token> toPostFija () throws Exception{

        String token;
        Stack pila=new Stack();
        List<Token> listaPostFija=new ArrayList<Token>();
        Token t=null;

        for (int i=0;i<this.expresion.length();i++) {
            token=this.expresion.substring(i, i+1);
            if (letras.indexOf(token)>=0) {
                //es una variable
                int j=i+1;
                while (j<this.expresion.length() &&(digitos.indexOf(this.expresion.substring(j, j+1))>=0
                        || letras.indexOf(this.expresion.substring(j, j+1))>=0)) {
                    token=token+this.expresion.substring(j, j+1);
                    j++;
                }
                //desde aqui se sigue procesando la cadena
                i=j-1;
                //debo buscar el valor de la variable que esta en el hash
                String valor=null;
                try {
                    valor= (String) this.variables.get(token);
                } catch (Exception e) {
                    throw new Exception("Error al obtener el valor de la variable: "+token);
                }

                if (valor==null) {
                    //variable no definida
                    throw new Exception("Variable no definida: "+token);
                }

                t=new Token();
                t.setToken(valor);
                t.setTipoToken(Token.TOKEN_CAMPO);
                listaPostFija.add(t);

            } else if (digitos.indexOf(token)>=0) {
                //es un numero
                int j=i+1;
                while (j<this.expresion.length() && (digitos.indexOf(this.expresion.substring(j, j+1))>=0
                        ||separadorDecimal.indexOf(this.expresion.substring(j, j+1))>=0)) {
                    token=token+this.expresion.substring(j, j+1);
                    j++;
                }
                //es un operando, se agrega a la salida
                t=new Token();
                t.setToken(token);
                t.setTipoToken(Token.TOKEN_CAMPO);
                listaPostFija.add(t);
                //desde aqui se sigue procesando la cadena
                i=j-1;
            } else if (token.equals(caracterInicioFuncion)) {
                //es una funcion
                int j=i+1;
                token="";
                while (j<this.expresion.length() && letras.indexOf(this.expresion.substring(j, j+1))>=0) {
                    token=token+this.expresion.substring(j, j+1);
                    j++;
                }
                List<String> parametros=null;

                String parametro="";
                if (abrir.equals(this.expresion.substring(j, j+1))) {
                    parametros=new ArrayList<String>();

                    //salto el parentesis
                    j++;
                    //se empieza a cargar cadenas que representen
                    //los parametros de la funcion
                    int paridadParentesis=1;
                    while (j<this.expresion.length()){
                        if (this.expresion.substring(j, j+1).equals(abrir)) {
                            paridadParentesis++;
                            parametro=parametro+this.expresion.substring(j, j+1);
                            j++;
                        } else if (this.expresion.substring(j, j+1).equals(cerrar)) {
                            paridadParentesis--;
                            if (paridadParentesis==0) {
                                parametros.add(parametro);
                                j++;
                                break;
                            } else {
                                parametro=parametro+this.expresion.substring(j, j+1);
                                j++;
                            }
                        } else if (this.expresion.substring(j, j+1).equals(caracterSeparadorParametros)) {
                            if (paridadParentesis==1) {
                                parametros.add(parametro);
                                parametro="";
                                j++;
                            } else {
                                parametro=parametro+this.expresion.substring(j, j+1);
                                j++;
                            }
                        } else {
                            parametro=parametro+this.expresion.substring(j, j+1);
                            j++;
                        }
                    }

                } else {
                    //no vino parentesis abierto luego del nombre de la funcion
                    throw new Exception("Falta parentesis izquierdo luego de: "+token);
                }




                List<String> parametrosTerminales= new ArrayList<String>();
                EvaluadorExpresion eval;
                String res;
                for (int x=0;x<parametros.size();x++) {
                    eval=new EvaluadorExpresion(parametros.get(x), variables);
                    res=eval.evaluarExpresion();
                    parametrosTerminales.add(res);
                }


                if (token.equalsIgnoreCase(Funciones.funcionSi)) {
                    t=new Token();
                    t.setToken(Funciones.si(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionMayor)) {
                    t=new Token();
                    t.setToken(Funciones.mayor(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionMenor)) {
                    t=new Token();
                    t.setToken(Funciones.menor(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionElegir)) {
                    t=new Token();
                    t.setToken(Funciones.elegir(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionCalcularInteres)) {
                    t=new Token();
                    t.setToken(Funciones.calcularInteres(parametrosTerminales,variables));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionCalcularMora)) {
                    t=new Token();
                    t.setToken(Funciones.calcularMora(parametrosTerminales,variables));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionCalcularMulta)) {
                    t=new Token();
                    t.setToken(Funciones.calcularMulta(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionBuscarTasa)) {
                    t=new Token();
                    t.setToken(Funciones.buscarTasa(parametrosTerminales,variables));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionBuscarValor)) {
                    t=new Token();
                    t.setToken(Funciones.buscarValor(parametrosTerminales));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionValidarFecha)) {
                    t=new Token();
                    t.setToken(Funciones.ValidarFecha(parametrosTerminales, variables));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else if (token.equalsIgnoreCase(Funciones.funcionFechaEntre)) {
                    t=new Token();
                    t.setToken(Funciones.FechaEntre(parametrosTerminales, variables));
                    t.setTipoToken(Token.TOKEN_CAMPO);
                    listaPostFija.add(t);
                } else {
                    //funcion no definida
                    throw new Exception("Funcion no definida: "+token);
                }

                //desde aqui se sigue procesando la cadena
                i=j-1;
            } else if (operadores.contains(token)){
                //es un operador
                int j=i+1;
                while (j<this.expresion.length() && operadores.indexOf(this.expresion.substring(j, j+1))>=0) {
                    token=token+this.expresion.substring(j, j+1);
                    j++;
                }
                //desde aqui se sigue procesando la cadena
                i=j-1;

                int prioridad=this.obtenerPrioridad(token);
                //mientras haya en la pila operadores de mayor prioridad o
                //de igual prioridad pero asociativos por la izquierda, los saco
                //y agrego a la salida
                if (!pila.empty()) {
                    int prioridadTopePila=this.obtenerPrioridad(((Token)pila.peek()).getToken());
                    while (!((Token)pila.peek()).getToken().equals(abrir)
                            && (prioridad<prioridadTopePila
                            || (prioridad==prioridadTopePila
                                && asociacionIzquierdaDerecha.indexOf(((Token)pila.peek()).getToken())>=0))) {
                            listaPostFija.add((Token)pila.pop());
                        if (!pila.empty()) {
                            prioridadTopePila=this.obtenerPrioridad(((Token)pila.peek()).getToken());
                        } else {
                            break;
                        }
                    }
                }

                //agrego el operador actual a la pila
                t=new Token();
                t.setToken(token);
                t.setTipoToken(Token.TOKEN_OPERACION_BINARIA);
                pila.push(t);


            } else if (abrir.equals(token)) {
                //se abre parentesis
                //agrego el parentesis a la pila, ya que se considera de maxima prioridad
                t=new Token();
                t.setToken(token);
                t.setTipoToken(Token.TOKEN_PARENTESIS);
                pila.push(t);

            } else if (cerrar.equals(token)) {
                //se cierra parentesis
                //desapilamos y tiramos a la salida hasta encontrar un parentesis izquierdo
                while (!pila.empty() && !((Token)pila.peek()).getToken().equals(abrir)) {
                    listaPostFija.add((Token)pila.pop());
                }
                //saco el parentesis izquierdo
                pila.pop();

            } else {
                //token no identificado
                throw new Exception("Token no identificado: "+token);
            }
        }
        //descargamos lo que hay en la pila a la salida
        while (!pila.empty()) {
            if (!((Token)pila.peek()).getToken().equals(abrir) &&
                    !((Token)pila.peek()).getToken().equals(cerrar)) {
                listaPostFija.add((Token)pila.pop());
            } else {
                pila.pop();
            }
        }
        return listaPostFija;
    }

    public String evaluarExpresion () throws Exception {
        Stack<String> pila=new Stack<String>();
        this.postFija=this.toPostFija();
        double t1,t2;

        for (Token t : this.postFija) {
            if (t.getTipoToken()==Token.TOKEN_CAMPO) {

              pila.add(t.getToken());
            } else {
                if (t.getTipoToken()==Token.TOKEN_OPERACION_BINARIA) {
                    t2=Double.parseDouble(pila.pop());
                    t1=Double.parseDouble(pila.pop());


                    if (t.getToken().equals(suma)) {
                        pila.add(((Double)(t1+t2)).toString());
                    } else if (t.getToken().equals(resta)) {
                        pila.add(((Double)(t1-t2)).toString());
                    } else if (t.getToken().equals(multiplicacion)) {
                        pila.add(((Double)(t1*t2)).toString());
                    } else if (t.getToken().equals(division)) {
                        pila.add(((Double)(t1/t2)).toString());
                    } else if (t.getToken().equals(mayor)) {
                        if (t1>t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(mayorIgual)) {
                        if (t1>=t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(menor)) {
                        if (t1<t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(menorIgual)) {
                        if (t1<=t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(igual)) {
                        if (t1==t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(diferente)) {
                        if (t1!=t2) {
                            pila.add("1.0");
                        } else {
                            pila.add("0.0");
                        }
                    } else if (t.getToken().equals(potencia)) {
                        pila.add(((Double)Math.pow(t1, t2)).toString());
                    }
                }
            }
        }
        String res = pila.pop();
        return res;
    }

    private int obtenerPrioridad (String operador) throws Exception {
        for (int i=0;i<prioridadesOperaciones.length;i++) {
            if (prioridadesOperaciones[i].indexOf(operador)>=0) {
                return i;
            }
        }
        throw new Exception("Operador no definido: "+operador);
    }


    public String postFijaToString() throws Exception{
        String cadena="";
        if (this.postFija!=null) {
            for (Token t : this.postFija) {
                cadena=cadena+t+" ";
            }
        }
        return cadena;
    }





    private String letras="abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String digitos="1234567890";
    private String separadorDecimal=".";
    private String suma = "+";
    private String resta = "-";
    private String multiplicacion = "*";
    private String division = "/";
    private String mayor = ">";
    private String mayorIgual = ">=";
    private String menor = "<";
    private String menorIgual = "<=";
    private String diferente = "<>";
    private String igual = "=";
    private String abrir = "(";
    private String cerrar = ")";
    private String potencia = "^";

    private String asociacionIzquierdaDerecha = "+-/*>>=<<=<>=";
    //private String asociacionDerechaIzquierda = "^";

    private String operadores=">>=<<=<>=+-*/^";

    private String [] prioridadesOperaciones= new String[] {
        ">>=<<=<>=","+-","*/","^","()"
    };

    private String caracterInicioFuncion = "#";
    private String caracterSeparadorParametros = ",";



    
}
