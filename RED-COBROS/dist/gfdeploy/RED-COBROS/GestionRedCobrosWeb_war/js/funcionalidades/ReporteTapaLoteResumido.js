function pantallaREPORTE_TAPALOTE(){

    var panel = {
//        id : 'idPanelREPORTE_TAPALOTE',
//        title:'REPORTE TAPA LOTE',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteTapalotes('TAPA-LOTE-GROUP')]
    }
    //return panel;
    var win = new Ext.Window({
       id : 'idPanelREPORTE_TAPALOTE',
        title:'REPORTE TAPA LOTE',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();

}