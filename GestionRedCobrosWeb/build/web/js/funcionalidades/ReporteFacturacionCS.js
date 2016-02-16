/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function pantallaREPORTE_COMISION_RESUMIDO_REC(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelFacturacionCS()]
    }
    //
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COMISION_RESUMIDO_REC',
        title:'REPORTE FACTURACION DE SERVICIOS',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function FormPanelFacturacionCS(){

    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.focus(true,true);
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
           
            comboRECAUDADOR.disable();           
            comboRECAUDADOR.reset();            
        }        
    }, null,null  ) ;

    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Desde',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Hasta',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    var comboTIPO_PAGO = new Ext.form.ComboBox({
        fieldLabel: 'TIPO PAGO',
        name : 'TIPO_PAGO_FAC',
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
            ['GENERAL',  'GENERAL']]
        })
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

        items : [comboRED, comboRECAUDADOR,comboTIPO_PAGO,comboTIPO_REPORTE,fechaIni,fechaFin,{
            xtype:'numberfield',
            fieldLabel: 'Dias Anteriores',
            id :'idDiasAnteriores',
            allowBlank : true,           
            value:1
        },formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE_COMISION_FAC_REC'+'&idRed='+comboRED.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&tipoPago='+comboTIPO_PAGO.getValue()+'&fechaIni='+fechaIni.getRawValue()+'&fechaFin='+fechaFin.getRawValue()+'&tipoReporte='+comboTIPO_REPORTE.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&diasAnteriores='+Ext.getCmp('idDiasAnteriores').getValue()+'&entidad=REC'
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


