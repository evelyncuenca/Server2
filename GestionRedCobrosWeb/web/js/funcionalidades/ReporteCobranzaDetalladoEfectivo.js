function pantallaREPORTE_COBRANZA_DETALLADO_EFECTIVO(){
    var panel = {
//        id : 'idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO',
//        title:'Reporte COBRANZA DETALLADO EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-DET-EFECT')]
    }
   // return panel;
     var win = new Ext.Window({
       id : 'idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO',
        title:'REPORTE COBRANZA DETALLADO EFECTIVO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}