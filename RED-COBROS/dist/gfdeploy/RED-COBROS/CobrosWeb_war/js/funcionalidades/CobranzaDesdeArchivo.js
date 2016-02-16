/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function cobrarDesdeArchivo(){
    var pathFile= "C:\\POS\\TELEPOS.TXT";
    Ext.Msg.wait('Procesando... Por Favor espere...');
    var datos = new Array();
    datos = document.appletGenBarCode.genBarCodeFromFile(pathFile);
    if(datos!=null && datos != ""){
        Ext.getCmp('panelConfirmacionDePago').load({
            url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO',
            params: {
                CODIGO_DE_BARRA : datos[0],
                NOMBRE:datos[1],
                BACK_TO : "PAGO_DESDE_ARCHIVO"
            },
            discardUrl: false,
            nocache: true,
            text: "Cargando...",
            timeout: TIME_OUT_AJAX,
            scripts: true
        });
        Ext.Msg.hide();
        Ext.getCmp('content-panel').layout.setActiveItem('panelConfirmacionDePago');
    }else{
        Ext.Msg.show({
            title   : "Error",
            msg     : "No se encuentra archivo de Cobranza",
            buttons : Ext.Msg.OK,
            icon    : Ext.MessageBox.ERROR
        });        
    }
}