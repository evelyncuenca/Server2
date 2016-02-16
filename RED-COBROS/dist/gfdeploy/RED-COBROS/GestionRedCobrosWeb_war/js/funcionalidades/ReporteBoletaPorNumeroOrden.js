function pantallaREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN(){

    var panel = {
        //id : 'idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN',
        //title:'REPORTE BOLETA PAGO POR NUMERO ORDEN',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteSimple('BP-ORDEN')]
    }
    //return panel;

    var win = new Ext.Window({
        title:'REPORTE BOLETA PAGO POR NÚMERO ORDEN',
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