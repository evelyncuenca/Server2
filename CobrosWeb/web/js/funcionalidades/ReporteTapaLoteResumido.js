function pantallaREPORTE_TAPALOTE(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteTapalotes('TAPA-LOTE-GROUP')]
    }
   
    var win = new Ext.Window({
        title:'Reporte Tapa Lote Resumido',
        id : 'idPanelREPORTE_TAPALOTE',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();

}