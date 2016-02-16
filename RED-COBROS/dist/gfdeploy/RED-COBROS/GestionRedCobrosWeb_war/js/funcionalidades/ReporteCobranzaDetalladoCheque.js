function pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-DET-CHEQUE')]
    }
  
    var win = new Ext.Window({
       id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE',
        title:'REPORTE COBRANZA DETALLADO CHEQUE',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}