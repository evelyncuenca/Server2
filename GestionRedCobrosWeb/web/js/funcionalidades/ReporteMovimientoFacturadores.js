/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



function pantallaREPORTE_MOVIMIENTO_FACTURADORES(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosREPORTE_MOVIMIENTO_FACTURADORES()]
    };
    var win = new Ext.Window({
        id : 'idPanelREPORTE_MOVIMIENTO_FACTURADORES',
        title:'Generar Reporte Movimiento Facturadores',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function parametrosREPORTE_MOVIMIENTO_FACTURADORES(){
   
    var fechaDesde = new Ext.form.DateField({
        fieldLabel : 'FECHA DESDE',
        name : 'FECHA_DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel : 'FECHA HASTA',
        name : 'FECHA_HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY',
        enableKeyEvents:true
    });
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

    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [fechaDesde,fechaHasta,formatoDeDescarga],
        buttons : [{
            text : 'Generar Archivo',
            formBind : true,
            handler : function() {
                if(!trabajando){
                    trabajando = true;
                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag : 'iframe',
                            id : 'downloadIframe',
                            frameBorder : 0,
                            width : 0,
                            height : 0,
                            css : 'display:yes;visibility:hidden;height:0px;',
                            src : 'reportes?op=REPORTE_MOVIMIENTO_FACTURADORES&fechaIni='+fechaDesde.getRawValue()+'&fechaFin='+fechaHasta.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
                        });
                        trabajando = false;
                    } catch (e) {

                    }
                }
            }
        }, {
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();                
            }
        }]
    });
    return formPanel;
}