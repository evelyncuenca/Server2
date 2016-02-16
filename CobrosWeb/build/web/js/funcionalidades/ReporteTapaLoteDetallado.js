function pantallaREPORTE_TAPALOTE_DETALLADO(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteTapalotes('TAPA-LOTE-GROUP-DET')]
    }

    var win = new Ext.Window({
        title:'Reporte Tapa Lote Detallado',
        id : 'idPanelREPORTE_TAPALOTE_DETALLADO',       
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}