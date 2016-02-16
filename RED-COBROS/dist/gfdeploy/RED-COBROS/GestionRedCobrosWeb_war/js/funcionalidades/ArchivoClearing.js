function pantallaARCHIVO_CLEARING_COMISION(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosArchivoClearingComision()]
    };
    var win = new Ext.Window({
        // id : 'idPanelARCHIVO_CLEARING',
        title:'Generar Archivo de Clearing Comisión',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function pantallaARCHIVO_CLEARING_FACTURACION(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosArchivoClearingFacturacion()]
    };
    var win = new Ext.Window({
        //id : 'idPanelARCHIVO_CLEARING',
        title:'Generar Archivo de Clearing Facturación',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function pantallaARCHIVO_CLEARING_MANUAL(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosArchivoClearingManual()]
    };
    var win = new Ext.Window({

        title:'Generar Archivo de Clearing Manual',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosArchivoClearingManual(){
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    var fecha = new Ext.form.DateField({
        fieldLabel : 'FECHA',
        name : 'FECHA',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true
    });
    var comboCreditoDebito =  new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        emptyText: 'Tipo...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['CREDITO', 'CREDITO'],
            ['DEBITO',  'DEBITO']]
        })
    });
    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [fecha,comboRED,comboCreditoDebito],
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
                            src : 'ARCHIVO_CLEARING?OP=MANUAL&ARCHIVO=ARCHIVO&GENERAR_CLEARING=on&DEBITO_CREDITO='+comboCreditoDebito.getValue()+'&DESDE='+fecha.getRawValue()+'&RED='+comboRED.getValue()
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
function parametrosArchivoClearingFacturacion(){
    var reclacular =  new Ext.form.Checkbox ({
        fieldLabel: 'RECALCULAR',
        vertical: true,
        columns: 1,
        name: 'RECALCULAR',
        items: [{
            boxLabel: 'RECALCULAR'
        }]
    });
    var comboSALIDA =  new Ext.form.ComboBox({
        fieldLabel: 'SALIDA',
        hiddenName : 'ARCHIVO',
        valueField : 'ARCHIVO',
        emptyText: 'Salida...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['ARCHIVO', 'descripcion'],
            data : [['ARCHIVO', 'ARCHIVO'],
            ['REPORTE',  'REPORTE']]
        })
    });
    var comboTIPO =  new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName : 'DEBITO_CREDITO',
        valueField : 'DEBITO_CREDITO',
        emptyText: 'Tipo...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['DEBITO_CREDITO', 'descripcion'],
            data : [['CREDITO', 'CRÉDITO'],
            ['DEBITO',  'DÉBITO']]
        })
    });
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    comboRED.allowBlank = false;
    comboSALIDA.addListener( 'select',function(esteCombo, record,  index  ){
        if(esteCombo.getValue() == 'ARCHIVO'){
            comboTIPO.enable();
            hasta.disable();
        }else{
            comboTIPO.disable();
            hasta.enable();

        }
    }, null,null ) ;
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboTIPO.disable();
            hasta.disable();
        }
    }, null,null  ) ;
    comboSALIDA.allowBlank=false;
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
    hasta.allowBlank = false;
    desde.allowBlank = false;
    hasta.disable();
    comboTIPO.disable();
    var trabajando = false;
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
        items : [comboRED,comboSALIDA,comboTIPO,desde,hasta,formatoDeDescarga,reclacular],
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
                            src : 'ARCHIVO_CLEARING?OP=FACTURACION&RECALCULAR='+reclacular.getValue()+'&ARCHIVO='+comboSALIDA.getValue()+'&GENERAR_CLEARING=on&DEBITO_CREDITO='+comboTIPO.getValue()+'&RED='+comboRED.getValue()+'&DESDE='+desde.getRawValue()+'&HASTA='+hasta.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
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

function parametrosArchivoClearingComision(){
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    var comboSERVICIO = getCombo("SERVICIO_CLEARING", "SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO");
    comboSERVICIO.allowBlank = false;
    comboRED.allowBlank = false;
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['RED'] = record.data.RED;


    }, null,null ) ;
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboSERVICIO.disable();
            comboSERVICIO.reset();
        }

    }, null,null  ) ;
    comboSERVICIO.disable();

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
    var trabajando = false;
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
        items : [comboRED,comboSERVICIO,desde,hasta, formatoDeDescarga],
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
                            src : 'ARCHIVO_CLEARING?OP=COMISION&ARCHIVO=REPORTE&GENERAR_CLEARING=on&'+'RED='+comboRED.getValue()+'&SERVICIO='+comboSERVICIO.getValue()+'&DESDE='+desde.getRawValue()+'&HASTA='+hasta.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
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