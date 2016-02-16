function pantallaREPORTE_DECLARACION_POR_NUMERO_ORDEN(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,

        items: [getFormPanelReporteSimple('DDJJ-ORDEN')]
    }
   
    var win = new Ext.Window({
        title:'Reporte Declaración por Número de Orden',
        id : 'idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN',       
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}