/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



function pantallaREPORTE_COMISION_CS(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosREPORTE_COMISION()]
    };
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COMISION_CS',
        title:'Generar Reporte de Comisiones',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function parametrosREPORTE_COMISION(){
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboSERVICIO =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){ 
        comboRECAUDADOR.enable();
        comboFACTURADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();      
    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
        comboSERVICIO.focus(true,true);
    }, null,null ) ;   
    comboSERVICIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboTipoPago.focus(true,true);
    }, null,null ) ;
    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
        comboSERVICIO.focus(true, true);
    }, null,null ) ;
    comboRECAUDADOR.allowBlank= true;
   // comboROL.allowBlank=true;
    comboSERVICIO.allowBlank=true;


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
        allowBlank : true,
        value : 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['pdf', 'PDF'],
            ['xls',  'XLS']]
        })
    });
    var comboTipoPago = new Ext.form.ComboBox({
        fieldLabel: 'TIPO PAGO',
        name : 'tipoPagoComision',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : true,
        //value : 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'EFECTIVO'],
            ['2',  'CHEQUE']]
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
        value : 'RESUMIDO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO_REPORTE', 'descripcion'],
            data : [['RESUMIDO', 'RESUMIDO'],
            ['DETALLADO',  'DETALLADO'],
            ['RESUMIDO-REC','RESUMIDO-REC'],
            ['DETALLADO-REC','DETALLADO-REC']]
        })
    });

    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [comboRED,comboRECAUDADOR,comboSUCURSAL,comboFACTURADOR,comboSERVICIO,comboTipoPago,fechaDesde,fechaHasta,tipoReporte,formatoDeDescarga],
        buttons : [{
            text : 'Generar Reporte',
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
                            src : 'reportes?op=REPORTE_COMISION_CS&fechaIni='+fechaDesde.getRawValue()+'&fechaFin='+fechaHasta.getRawValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoReporte='+tipoReporte.getValue()+'&idRed='+comboRED.getValue()+'&idServicio='+comboSERVICIO.getValue()+'&tipoPago='+comboTipoPago.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&idFacturador='+comboFACTURADOR.getValue()
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