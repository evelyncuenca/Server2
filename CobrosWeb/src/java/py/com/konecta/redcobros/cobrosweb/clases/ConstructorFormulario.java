/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.konecta.redcobros.cobrosweb.clases;

//import entities.Campo;


import java.math.BigInteger;
import py.com.konecta.redcobros.entities.Campo;

/**
 *
 * @author gustavo
 */
public class ConstructorFormulario {
    private Campo[][] matriz;
    private int cantidad;
    private Campo[] campos;
    private int[] maxColumnas;
    private int maxFila;
    private BigInteger[] rubrosFilas;
    private String[] incisosFilas;
    private int minimoNumeroCampo;
    private int maximoNumeroCampo;

    public int getMaximoNumeroCampo() {
        return maximoNumeroCampo;
    }

    public int getMinimoNumeroCampo() {
        return minimoNumeroCampo;
    }
    private static String[] abecedario= new String[] {
      "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
      "p","q","r","s","t","u","v","w","x","y","z",
      "a01","a02","a03","a04","a05","a06","a07","a08","a09","a10",
      "a11","a12","a13","a14","a15","a16","a17","a18","a19","a20",
      "a21","a22","a23","a24","a25","a26","a27","a28","a29","a30",
      "a31","a32","a33","a34","a35","a36","a37","a38","a39","a40",
      "a41","a42","a43","a44","a45","a46","a47","a48","a49","a50",
      "a51","a52","a53","a54","a55","a56","a57","a58","a59","a60",
      "a61","a62","a63","a64","a65","a66","a67","a68","a69","a70",
      "a71","a72","a73","a74","a75","a76","a77","a78","a79","a80",
      "a81","a82","a83","a84","a85","a86","a87","a88","a89","a90",
      "a91","a92","a93","a94","a95","a96","a97","a98","a99"
    };

    public int[] getMaxColumnas() {
        return maxColumnas;
    }

    private int obtenerPosicion(String inciso) {
        int posicion=-1;
        for (int i=0;i<abecedario.length;i++) {
            if (abecedario[i].compareTo(inciso)==0) {
                posicion=i;
                break;
            }
        }
        return posicion;
    }

    public String[] getIncisosFilas() {
        return incisosFilas;
    }

    public void setIncisosFilas(String[] incisosFilas) {
        this.incisosFilas = incisosFilas;
    }





    public int getMaxFila() {
        return maxFila;
    }
    public ConstructorFormulario(Campo[] campos) {
        this.campos=campos;
        this.cantidad=campos.length;
        this.matriz=new Campo[cantidad*2][cantidad];
        this.maxColumnas=new int[cantidad*2];
        this.rubrosFilas=new BigInteger[cantidad*2];
        this.incisosFilas=new String[cantidad*2];
        //this.matriz=new Campo[cantidad][cantidad];
//        this.maxColumnas=new int[cantidad];
//        this.rubrosFilas=new BigInteger[cantidad];
//        this.incisosFilas=new String[cantidad];
        this.minimoNumeroCampo=campos[0].getNumero().intValue();
        this.maximoNumeroCampo=campos[0].getNumero().intValue();
        for (int i=1;i<this.campos.length;i++) {
            if (this.minimoNumeroCampo>
                    campos[i].getNumero().intValue()) {
                this.minimoNumeroCampo=campos[i].getNumero().intValue();
            }
            if (this.maximoNumeroCampo<
                    campos[i].getNumero().intValue()) {
                this.maximoNumeroCampo=campos[i].getNumero().intValue();
            }
        }


    }

    public boolean construirFormulario() {
        BigInteger rubroActual=null;
        int filaActual,columnaActual;
        int indiceMinRubroActual=-1,indiceMaxRubroActual=-1;
        for (int i=0;i<this.campos.length;i++) {
            if (campos[i].getIdRubro()==null) {
                columnaActual=0;
                filaActual=indiceMaxRubroActual+1;
                maxColumnas[filaActual]=1;

                matriz[filaActual][columnaActual]=campos[i];
                indiceMinRubroActual=filaActual;
                indiceMaxRubroActual=filaActual;
                this.maxFila=filaActual+1;

            } else {
                int maxColumnaRubroActual;
                indiceMinRubroActual=indiceMaxRubroActual+1;
                //rubroActual=campos[i].getIdRubro().getIdRubro();
                rubroActual=campos[i].getIdRubro();
                int indiceCampoMaxRubroActual=-1;
                int maximoInciso=this.obtenerPosicion(this.campos[i].getInciso());
                for (int j=i+1;j<this.campos.length;j++) {
                   // if (this.campos[j].getIdRubro()==null || this.campos[j].getIdRubro().getIdRubro().compareTo(
                     if (this.campos[j].getIdRubro()==null || this.campos[j].getIdRubro().compareTo(rubroActual)!=0) {
                        indiceCampoMaxRubroActual=j-1;
                        break;
                    } else {
                        if (this.obtenerPosicion(this.campos[j].getInciso())>maximoInciso) {
                            maximoInciso=this.obtenerPosicion(this.campos[j].getInciso());
                        }

                    }
                }

                if (indiceCampoMaxRubroActual==-1) {
                    indiceCampoMaxRubroActual=this.campos.length-1;
                }
                indiceMaxRubroActual=indiceMinRubroActual+maximoInciso;
                this.maxFila=indiceMaxRubroActual+1;
                //filaActual=indiceMinRubroActual;

                maxColumnaRubroActual=-1;
                int posicionIncisoActual;
                for (int j=i;j<=indiceCampoMaxRubroActual;j++) {
                    posicionIncisoActual=this.obtenerPosicion(this.campos[j].getInciso());
                    filaActual=indiceMinRubroActual+posicionIncisoActual;
                    columnaActual=this.campos[j].getNumeroColumna().intValue()-1;

                    matriz[filaActual][columnaActual]=campos[j];
                    if (this.maxColumnas[filaActual]<campos[j].getNumeroColumna().intValue()) {
                        this.maxColumnas[filaActual]=campos[j].getNumeroColumna().intValue();

                    }
                    if (this.maxColumnas[filaActual]>maxColumnaRubroActual) {
                        maxColumnaRubroActual=this.maxColumnas[filaActual];
                    }

                }
                int k=-1;
                for (int j=indiceMinRubroActual;j<=indiceMaxRubroActual;j++) {
                    this.rubrosFilas[j]=rubroActual;
                    k++;
                    this.incisosFilas[j]=abecedario[k];
                    this.maxColumnas[j]=maxColumnaRubroActual;
                }
                i=indiceCampoMaxRubroActual;
            }
        }
        return true;
    }

    public Campo[][] getMatriz() {

        return matriz;
    }

    public BigInteger[] getRubrosFilas() {
        return rubrosFilas;
    }



}
