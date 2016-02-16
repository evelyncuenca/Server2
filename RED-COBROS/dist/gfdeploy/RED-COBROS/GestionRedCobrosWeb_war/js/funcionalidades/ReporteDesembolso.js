/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function pantallaREPORTE_DESEMBOLSO(){

    var panel = {
        id : 'idPanelREPORTE_DESEMBOLSO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteDesembolso()]
    }
    //
    var win = new Ext.Window({
        title:'REPORTE DESEMBOLSO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}

function FormPanelReporteDesembolso(){
    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Ini',
        id:'idFechaIni',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'Y-m-d'
    });

    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFin',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'Y-m-d'
    });
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
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


    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [fechaIni, fechaFin, formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE-DESEMBOLSO'+'&fechaIni='+fechaIni.getRawValue() +'&fechaFin='+fechaFin.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
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





