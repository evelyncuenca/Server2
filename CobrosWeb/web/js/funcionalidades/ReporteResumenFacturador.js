/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function pantallaREPORTE_RESUMEN_FACTURADOR(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosResumenFacturador()]
    };
    var win = new Ext.Window({
        id : 'idPanelREPORTE_RESUMEN_FACTURADOR',
        title:'Generar Reporte Resumen Facturadores',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function parametrosResumenFacturador(){
//    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
//    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');

//    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
//        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
//        comboRECAUDADOR.store.reload();
//        comboFACTURADOR.store.baseParams['ID_RED'] = record.data.RED;
//        comboFACTURADOR.store.reload();
//    }, null,null ) ;
//
//    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
//
//        if(esteCombo.getRawValue().length==0){
//            comboSUCURSAL.reset();
//        }
//
//    }, null,null  ) ;
//
//    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
//        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
//        comboSUCURSAL.store.reload();
//    }, null,null ) ;

//    comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){
//
//    }, null,null ) ;

    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        fechaDesde.focus(true, true);
        }, null,null ) ;
//    comboRED.allowBlank = false;
//    comboRECAUDADOR.allowBlank = true;
    comboFACTURADOR.allowBlank = true;
    comboSUCURSAL.allowBlank = true;


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

    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [comboSUCURSAL,comboFACTURADOR,fechaDesde,fechaHasta,formatoDeDescarga],
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
                            src : 'reportes?op=REPORTE_RESUMEN_FACTURADOR'+'&idFacturador='+comboFACTURADOR.getValue()+'&fechaIni='+fechaDesde.getRawValue()+'&fechaFin='+fechaHasta.getRawValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
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