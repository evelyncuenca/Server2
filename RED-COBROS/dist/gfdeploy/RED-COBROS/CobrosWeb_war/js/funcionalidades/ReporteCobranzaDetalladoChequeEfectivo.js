function pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO(){
    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-DET')]
    }

    var win = new Ext.Window({
        title:'Reporte Cobranza Detallado Cheque-Efectivo',
        id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO',       
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}