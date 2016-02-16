/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb.core;

/**
 *
 * @author konecta
 */
public class Parametros {

    // Errores
    public static Integer MSG_OK = 0;
    public static Integer MSG_TRANSACCION_REGISTRADA            = 1;
    public static Integer ERROR_INST_SERV_NO_ENCONTRADO         = 1001;
    public static Integer ERROR_EST_TRAN_NO_ENCONTRADO          = 1002;
    public static Integer ERROR_USU_NO_ENCONTRADO               = 1003;
    public static Integer ERROR_TERM_NO_ENCONTRADA              = 1004;
    public static Integer ERROR_USU_TERM_NO_ENCONTRADO          = 1005;
    public static Integer ERROR_MON_NO_ENCONTRADA               = 1006;
    public static Integer ERROR_MON_NO_SOPORTADA                = 1007;
    public static Integer ERROR_FEC_HOR_RED_INCORRECTA          = 1008;
    public static Integer ERROR_FEC_HOR_TER_INCORRECTA          = 1009;
    public static Integer ERROR_TERM_TIPO_TERM_NO_ENCONTRADO    = 1010;
    public static Integer ERROR_SUC_NO_ENCONTRADA               = 1011;
    public static Integer ERROR_REC_NO_ENCONTRADO               = 1012;
    public static Integer ERROR_RED_NO_ENCONTRADA               = 1013;
    public static Integer ERROR_TIPO_PAGO_NO_ENCONTRADO         = 1014;
    public static Integer ERROR_TIPO_PAGO_NO_SOPORTADO          = 1015;
    public static Integer ERROR_LOG_HISTORIA_NO_INSERTADO       = 1016;
    public static Integer ERROR_REC_RED_NO_ENCONTRADO           = 1017;
    public static Integer ERROR_SUC_REC_NO_ENCONTRADA           = 1018;
    public static Integer ERROR_TER_SUC_NO_ENCONTRADA           = 1019;
    public static Integer ERROR_USU_NOMB_CONT_NO_ENCONTRADO     = 1020;
    public static Integer ERROR_REC_RED_NO_HABILITADO           = 1021;
    public static Integer ERROR_PEDIDO_TRANSACCION_RECIBIDO_EXITOSAMENTE = 1022;
    public static Integer ERROR_SERV_REC_NO_HABILITADO                   = 1023;
    public static Integer ERROR_TRANS_INCORRECTA                         = 1024;
    public static Integer ERROR_TRANS_NO_ENLAZADA_AL_SERVICIO            = 1025;
    public static Integer ERROR_NO_EXISTE_EL_CONTRIBUYENTE               = 1026;
    public static Integer ERROR_NO_EXISTE_EL_FORMULARIO                  = 1027;
    public static Integer ERROR_NO_EXISTE_SERVICIO                       = 1028;
    public static Integer ERROR_29                                       = 1029;
    public static Integer ERROR_30                                     = 1030;
    // Sentido
    public static char IN_CORE = '1';
    public static char OUT_CORE = '2';

    // Estados Transaccionales
    public static Integer ESTADO_INICIAL = 1;
    public static Integer ESTADO_2 = 2;
    public static Integer ESTADO_3 = 3;
    public static Integer ESTADO_4 = 4;
    public static Integer ESTADO_5 = 5;
    public static Integer ESTADO_6 = 6;
}
