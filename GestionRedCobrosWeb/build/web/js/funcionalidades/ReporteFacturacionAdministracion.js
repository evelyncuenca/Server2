/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function pantallaREPORTE_FACTURACION_ADMINISTRACION(){

    var panel = {
        id : 'idPanelREPORTE_FACTURACION_ADMINISTRACION',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteAdministracion()]
    }
    //
    var win = new Ext.Window({
        title:'REPORTE ADMINISTRACION',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}

function FormPanelReporteAdministracion(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');
    var comboSERVICIO =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    comboRECAUDADOR.disable();
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
           
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();
            comboFACTURADOR.reset();
            comboSERVICIO.reset();
        }        
    }, null,null  ) ;

     comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;

    
    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null,null ) ;

    
    var comboTIPO_PAGO = new Ext.form.ComboBox({
        fieldLabel: 'TIPO PAGO',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : true,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'EFECTIVO'],
            ['2',  'CHEQUE']]
        })
    });

    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Ini',
        id:'idFechaIni',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });

    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFin',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
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
    var comboTIPO_REPORTE = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Reporte',
        name : 'tipoReporte',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'RESUMIDO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['RESUMIDO',  'RESUMIDO'],
            ['SET','SET']]
        })
    });
    

    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [comboRED, comboRECAUDADOR, comboFACTURADOR, comboSERVICIO, comboTIPO_PAGO, fechaIni, fechaFin, {
            xtype:'numberfield',
            fieldLabel: 'Dias Anteriores',
            id :'idDiasAnterioresAdm',
            allowBlank : true,           
            value:1
        },comboTIPO_REPORTE, formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE_ADMINISTRACION'+'&fechaIni='+fechaIni.getRawValue() +'&fechaFin='+fechaFin.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idRed='+comboRED.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&idServicio='+comboSERVICIO.getValue()+'&tipoPago='+comboTIPO_PAGO.getValue()+'&tipoReporte='+comboTIPO_REPORTE.getValue()+'&diasAnteriores='+Ext.getCmp('idDiasAnterioresAdm').getValue()
                    });

                } catch (e) {
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                comboRECAUDADOR.disable();
                formPanel.getForm().reset();
            }
        }]
    });

    return formPanel;

}





