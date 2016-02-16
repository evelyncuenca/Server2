function pantallaREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO(){

    var panel = {
//        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
//        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-RESUM')]
    }
    //return panel;
     var win = new Ext.Window({
        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}