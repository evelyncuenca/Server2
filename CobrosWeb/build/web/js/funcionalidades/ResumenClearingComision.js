function pantallaRESUMEN_CLEARING_COMISION(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosResumenClearingComision()]
    };
    var win = new Ext.Window({
        id : 'idPanelRESUMEN_CLEARING_COMISION',
        title:'Generar Resumen de Clearing Comisión',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function parametrosResumenClearingComision(){
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    //var comboSERVICIO = getCombo("SERVICIO_CLEARING", "SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO");
    //comboSERVICIO.allowBlank = false;
    comboRED.allowBlank = false;
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
       // comboSERVICIO.enable();
       // comboSERVICIO.store.baseParams['RED'] = record.data.RED;


    }, null,null ) ;
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            //comboSERVICIO.disable();
            //comboSERVICIO.reset();
        }

    }, null,null  ) ;
    //comboSERVICIO.disable();

    var desde = new Ext.form.DateField({
        fieldLabel : 'DESDE',
        name : 'DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    var hasta = new Ext.form.DateField({
        fieldLabel : 'HASTA',
        name : 'HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY'
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
        items : [comboRED,desde,hasta,formatoDeDescarga],
        buttons : [{
            text : 'Generar Resumen',
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
                            src : 'reportes?op=RESUMEN-CLEARING-COMISION&'+'idRed='+comboRED.getValue()+'&fechaIni='+desde.getRawValue()+'&fechaFin='+hasta.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()

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