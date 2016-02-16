function pantallaREPORTE_TAPALOTE_DETALLADO(){

    var panel = {
//        id : 'idPanelREPORTE_TAPALOTE_DETALLADO',
//        title:'REPORTE TAPA LOTE DETALLADO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteTapalotes('TAPA-LOTE-GROUP-DET')]
    }
   // return panel;
   var win = new Ext.Window({
        id : 'idPanelREPORTE_TAPALOTE_DETALLADO',
        title:'REPORTE TAPA LOTE DETALLADO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}