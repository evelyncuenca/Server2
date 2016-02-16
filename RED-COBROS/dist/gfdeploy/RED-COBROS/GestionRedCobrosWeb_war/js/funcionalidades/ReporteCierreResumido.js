function pantallaREPORTE_CIERRE_RESUMIDO(){

    var panel = {
//        id : 'idPanelREPORTE_CIERRE_RESUMIDO',
//        title:'REPORTE CIERRE RESUMIDO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCierres('CIERRE-RESUMIDO')]
    }
    //
      var win = new Ext.Window({
        id : 'idPanelREPORTE_CIERRE_RESUMIDO',
        title:'REPORTE CIERRE RESUMIDO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}