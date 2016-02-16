function pantallaREPORTE_CIERRE_CS(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : true,
        autoScroll : true ,
        items: [FormPanelReporteCierres()]
    }
    //
    var win = new Ext.Window({
        id : 'idPanelREPORTE_CIERRE_CS',
        title:'REPORTE CIERRE COBRO SERVICIOS',
        width : '320',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : true,
        items : [panel]
    });
    return win.show();


}

function FormPanelReporteCierres(){

    var comboGESTIONES = getCombo('GESTION','GESTION','GESTION','GESTION','DESCRIPCION_GESTION','GESTIÓN','idGestion','DESCRIPCION_GESTION','GESTION','GESTIONES...');
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','idFacturador','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');
    var comboSERVICIO =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','codigoServicio','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    
    comboSERVICIO.disable();
    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null,null ) ;
    
    comboFACTURADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboSERVICIO.disable();
            comboSERVICIO.reset();
        }
    }, null,null  ) ;
    //var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    //var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    //var comboUSUARIO = getCombo('USUARIO','USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    var comboTIPO = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Cierre',
        name : 'tipoCierre',
        hiddenName : 'tipoCierre',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'CIERRE-DETALLADO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['CIERRE-DETALLADO', 'DETALLADO'],
            ['CIERRE-RESUMIDO',  'RESUMIDO']]
        })
    });

    /* comboUSUARIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboGESTIONES.enable();
        comboGESTIONES.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboGESTIONES.store.baseParams['limit'] = 10;
        comboGESTIONES.store.baseParams['start'] = 0;
        comboGESTIONES.store.reload();

    }, null,null ) ;
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboUSUARIO.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();

    }, null,null ) ;

    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboGESTIONES.disable();
            comboUSUARIO.disable();
            comboRECAUDADOR.reset();
            comboUSUARIO.reset();
            comboGESTIONES.reset();
        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboUSUARIO.disable();
            comboUSUARIO.reset();
        }
    }, null,null  ) ;

    comboUSUARIO.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboGESTIONES.disable();
            comboGESTIONES.reset();
        }
    }, null,null  ) ;*/
    //comboGESTIONES.disable();
    //comboRECAUDADOR.disable();
    //comboUSUARIO.disable();

    comboGESTIONES.allowBlank = false;
    //comboRED.allowBlank = false
    var fecha = new Ext.form.DateField({
        fieldLabel : 'Fecha',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name : 'formatoDescarga',
        hiddenName : 'formatoDescarga',
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

    fecha.addListener('valid', function( esteObjeto){
        comboGESTIONES.store.baseParams['fecha']= fecha.getRawValue();
        //comboGESTIONES.store.reload();
    }, null, null);
    var tmu =  new Ext.form.Checkbox ({
        fieldLabel: 'Imprimir en TMU',
        columns: 1,
        name: 'tmu'        
    });
    var formPanel = new Ext.FormPanel({

        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [fecha, comboGESTIONES,comboTIPO,comboFACTURADOR, comboSERVICIO, formatoDeDescarga, tmu],
        buttons : [{
            formBind : true,
            text : 'Reporte',
            handler : function() {
                if(tmu.getValue() && comboTIPO.getValue()=="CIERRE-RESUMIDO"){
                    formPanel.getForm().submit({
                        url : 'reportes?op=CIERRE-CS',
                        method : 'POST',
                        timeout : TIME_OUT_AJAX,
                        success : function(form,action1) {
                            var obj = Ext.util.JSON .decode(action1.response.responseText);
                            if(obj.success){
                                var win12 = new Ext.Window({
                                    title:'Reporte cierre Resumido',
                                    autoWidth : true,
                                    autoHeight : false,
                                    autoScroll:true,
                                    height: 300,
                                    closable : false,
                                    resizable : false,
                                    modal:true,
                                    buttons : [{
                                        text : 'Cancelar',
                                        handler : function() {
                                            win12.close();
                                        }
                                    },{
                                        text : 'Imprimir',
                                        formBind : true,
                                        handler : function() {
                                            imprimirImpresoraExterna(obj.reporteCierreImpresora);
                                        }
                                    } ],
                                    items : [{
                                        html:'<H1>'+obj.reporteCierrePantalla+'</H1>'
                                    }]
                                });
                                win12.show();

                            }else{

                                callBackServidor(obj);
                            }
                        },
                        failure : function(form,action) {
                            var obj = Ext.util.JSON .decode(action.response.responseText);
                            callBackServidor(obj);
                        }
                    });

                }else{
                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag : 'iframe',
                            id : 'downloadIframe',
                            frameBorder : 0,
                            width : 0,
                            height : 0,
                            css : 'display:yes;visibility:hidden;height:0px;',
                            src : 'reportes?op=CIERRE-CS'+'&idGestion='+comboGESTIONES.getValue()+'&fechaIni='+fecha.getRawValue() +'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoCierre='+comboTIPO.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&codigoServicio='+comboSERVICIO.getValue()
                        });
                    
                    } catch (e) {
                    }
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

