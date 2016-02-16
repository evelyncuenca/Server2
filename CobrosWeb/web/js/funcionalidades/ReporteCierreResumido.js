function pantallaREPORTE_CIERRE_RESUMIDO(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormPanelReporteCierres('CIERRE-RESUMIDO')]
    }
  
    var win = new Ext.Window({
        title:'Reporte Cierre Resumido',
        id : 'idPanelREPORTE_CIERRE_RESUMIDO',       
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}