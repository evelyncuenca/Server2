function pantallaREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteSimple('BP-ORDEN')]
    }
    var win = new Ext.Window({
        title:'Reporte Boleta de Pago por Número de Orden',
        id : 'idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    

    return win.show();

}