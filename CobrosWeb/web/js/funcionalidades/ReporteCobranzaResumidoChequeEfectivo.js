function pantallaREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-RESUM')]
    }
   
     var win = new Ext.Window({     
        title:'Reporte Cobranza Resumido Cheque-Efectivo',
        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}