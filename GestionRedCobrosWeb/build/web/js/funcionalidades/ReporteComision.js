/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function pantallaREPORTE_COMISION(entidad){
    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [getFormReporteComision(entidad)]
    }
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COMISION',
        title:'REPORTE COMISION '+entidad,
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}

function getFormReporteComision(entidad){
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');
    var comboSERVICIO =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    
    var comboZONA = new Ext.form.ComboBox({
        fieldLabel: 'Zona',
        name : 'zona',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['001', 'CAPITAL'],
            ['002',  'INTERIOR']]
        })
    });
    
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){           
            comboRECAUDADOR.disable();           
            comboSERVICIO.disable();
            comboRECAUDADOR.reset();
            comboFACTURADOR.reset();
            comboSERVICIO.reset();

        }        
    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
        }

    }, null,null  ) ;

    comboFACTURADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboSERVICIO.disable();

            comboSERVICIO.reset();


        }

    }, null,null  ) ;

    
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboFACTURADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null ) ;

    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null,null ) ;


    comboRECAUDADOR.disable();
    comboSERVICIO.disable();
    comboSUCURSAL.disable();
    comboRECAUDADOR.allowBlank= true;
    comboFACTURADOR.allowBlank=true;
    comboSERVICIO.allowBlank=true;
    comboSUCURSAL.allowBlank=true;


    var fechaDeIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        height : '22',
        anchor : '95%',
        id: 'idFecha',
        allowBlank:false,
        format:'dmY'
    });
    var fechaDeFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        height : '22',
        anchor : '95%',
        allowBlank:true,
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
    var formPanel = new Ext.FormPanel({
        labelWidth : 165,
        frame : true,
        autoHeight:true,
        monitorValid : true,
        items : [comboRED,comboRECAUDADOR,comboSUCURSAL,comboFACTURADOR,comboSERVICIO,comboZONA,fechaDeIni,fechaDeFin, formatoDeDescarga],
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
                        timeout : 30000,
                        css : 'display:yes;visibility:hidden;height:0px;',
                        src : 'reportes?op=REPORTE_COMISION_FAC_REC'+'&idSucursal='+comboSUCURSAL.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idRed='+comboRED.getValue()+'&codigoServicio='+comboSERVICIO.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&fechaFin='+fechaDeFin.getRawValue()+'&fechaIni='+fechaDeIni.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoReporte=DETALLADO'+'&zona='+comboZONA.getValue()+'&entidad='+entidad
                    });
                } catch (e) {
                    alert(e);
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
                comboRECAUDADOR.disable();
                comboSERVICIO.disable();               
            }
        }]
    });
    return formPanel;
}
