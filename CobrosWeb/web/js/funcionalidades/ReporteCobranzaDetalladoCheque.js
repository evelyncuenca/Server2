function pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCompuestoCobranzas('COB-DET-CHEQUE')]
    }
   
    var win = new Ext.Window({
        title:'Reporte Cobranza Detallado Cheque',
        id : 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}