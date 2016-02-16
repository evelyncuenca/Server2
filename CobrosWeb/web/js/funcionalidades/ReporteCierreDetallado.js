function pantallaREPORTE_CIERRE_DETALLADO(){

    var panel = {

        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormReporteCierreDetallado('CIERRE-DETALLADO')]
    }

    var win = new Ext.Window({
   
        title:'Reporte Cierre Detallado',
        id : 'idPanelREPORTE_CIERRE_DETALLADO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();

}
function FormReporteCierreDetallado(opDelServlet){
    var comboGESTIONES = getCombo('GESTION','GESTION','GESTION','GESTION','DESCRIPCION_GESTION','GESTIÓN','GESTION','DESCRIPCION_GESTION','GESTION','GESTIONES...');
    comboGESTIONES.allowBlank = true
    var fecha = new Ext.form.DateField({
        fieldLabel : 'Fecha',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY'
    });
    fecha.addListener('valid', function( esteObjeto){
        comboGESTIONES.store.baseParams['fecha']= fecha.getRawValue();
    }, null, null);
    var primeraVez = true;
    comboGESTIONES.addListener('focus',function(esteCombo){
        if(!primeraVez){
            esteCombo.store.load({
                params : {
                    start : 0,
                    limit:20
                },
                waitMsg : 'Cargando...'
            });
        }
        primeraVez = false;
    }, null,null )
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'FORMATO DE DESCARGA',
        name : 'formatoDescarga',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['pdf', 'PDF'],
            ['xls',  'XLS']]
        })
    });
    var tipoReporte = new Ext.form.ComboBox({
        fieldLabel: 'Tipo Reporte',
        name : 'tipoReporte',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'DETALLADO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['DETALLADO', 'DETALLADO'],
            ['CHEQUE',  'CHEQUE']]
        })
    });
    var formPanel = new Ext.FormPanel({

        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [fecha,comboGESTIONES,tipoReporte, formatoDeDescarga],
        buttons : [{
            formBind : true,
            text : 'Reporte',
            handler : function() {

                try {
                    Ext.destroy(Ext.get('downloadIframe'));
                    Ext.DomHelper.append(document.body, {
                        tag : 'iframe',
                        id : 'downloadIframe',
                        frameBorder : 0,
                        width : 0,
                        height : 0,
                        css : 'display:yes;visibility:hidden;height:0px;',
                        src : 'reportes?op='+opDelServlet+'&GESTION='+comboGESTIONES.getValue()+'&fechaIni='+fecha.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoReporte='+tipoReporte.getValue()
                    });
                } catch (e) {

                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();

            }
        }]
    });

    return formPanel;

}
