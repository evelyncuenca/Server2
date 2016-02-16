function pantallaREPORTE_COBRANZA_TAPA_CAJA(){
    var panel = {
//        id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO',
//        title:'REPORTE COBRANZA DETALLADO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteTapaCaja('TPC')]
    }
   // return panel;
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COBRANZA_TAPA_CAJA',
        title:'REPORTE TAPA CAJA',
         width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}