function pantallaREPORTE_DECLARACION_POR_NUMERO_ORDEN(){

    var panel = {
//        id : 'idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN',
//        title:'REPORTE DECLARACION POR NUMERO ORDEN',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,

        items: [getFormPanelReporteSimple('DDJJ-ORDEN')]
    }
   // return panel;
   var win = new Ext.Window({
        id : 'idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN',
        title:'REPORTE DECLARACIÓN POR NÚMERO ORDEN',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}