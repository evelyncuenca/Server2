
function pantallaREPORTE_TERMINALES_ABIERTAS(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [getReporteParameters('REPORTE-TERMINALES-ABIERTAS')]
    };
    var win = new Ext.Window({
        id : 'idPanelREPORTE_TERMINALES_ABIERTAS',
        title:'Generar Reporte de Terminales Abiertas',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}