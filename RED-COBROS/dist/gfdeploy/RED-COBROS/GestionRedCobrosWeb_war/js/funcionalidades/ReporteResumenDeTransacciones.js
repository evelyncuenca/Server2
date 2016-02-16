/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function pantallaREPORTE_RESUMEN_DE_TRANSACCIONES(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosREPORTE_RECAUDACION()]
    };
    var win = new Ext.Window({
        id : 'idPanelREPORTE_RESUMEN_DE_TRANSACCIONES',
        title:'Generar Reporte de Transacciones',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosREPORTE_RECAUDACION(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    comboRECAUDADOR.disable();
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null ) ;

    comboSUCURSAL.disable();
    comboRECAUDADOR.allowBlank= true;
    comboSUCURSAL.allowBlank=true;
    comboRED.allowBlank=true;


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
        allowBlank:false,
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

    var tipoReporte = new Ext.form.ComboBox({
        fieldLabel: 'TIPO DE REPORTE',
        name : 'tipoReporte',
        hiddenName : 'TIPO_REPORTE',
        valueField : 'TIPO_REPORTE',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'resumido',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO_REPORTE', 'descripcion'],
            data : [['resumido', 'RESUMIDO'],
            ['detallado',  'DETALLADO']]
        })
    });

    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [comboRED,comboRECAUDADOR,comboSUCURSAL,fechaDesde,fechaHasta,tipoReporte,formatoDeDescarga],
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
                            src : 'reportes?op=RESUMEN_DE_TRANSACCIONES&fechaIni='+fechaDesde.getRawValue()+'&fechaFin='+fechaHasta.getRawValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoReporte='+tipoReporte.getValue()+'&idRed='+comboRED.getValue()
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
                comboRECAUDADOR.disable();
                comboSUCURSAL.disable();
            }
        }]
    });
    return formPanel;
}
