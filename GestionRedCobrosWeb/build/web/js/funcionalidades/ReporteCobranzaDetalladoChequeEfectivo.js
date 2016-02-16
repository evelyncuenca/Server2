function pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO(){
    var panel = {
//        id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO',
//        title:'REPORTE COBRANZA DETALLADO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-DET')]
    }
   // return panel;
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO',
        title:'REPORTE COBRANZA DETALLADO CHEQUE EFECTIVO',
         width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}