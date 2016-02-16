function pantallaREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS(){

    var panel = {
        //        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        //        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteCobranzas()]
    }
    //return panel;
    var win = new Ext.Window({
        id : 'idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS',
        title:'REPORTE COBRANZA CHEQUE EFECTIVO COBRO SERVICIOS',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        y:'140',
        items : [panel]
    });
    return win.show();
}

function FormPanelReporteCobranzas(){
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboFACTURADOR =getCombo('FACTURADOR','FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR','DESCRIPCION_FACTURADOR','FACTURADOR','FACTURADOR');
    var comboSERVICIO =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    //var comboGESTION  = getCombo("GESTION", "GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION");
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','GESTIÓN','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','REGISTRO_GESTION','GESTIONES...');
    var comboUSUARIO = getCombo('USUARIO','USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
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
        value : 'DETALLADO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['DETALLADO', 'DETALLADO'],
            ['RESUMIDO',  'RESUMIDO']]
        })
    });
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
    var comboTIPO_CONSULTA =  new Ext.form.ComboBox({
        fieldLabel: 'TIPO CONSULTA',
        hiddenName : 'tipo_consulta',
        valueField : 'TIPO_CONSULTA',
        emptyText: 'TIPO CONSULTA...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'DESCRIPCION_TIPO_CONSULTA',
        mode: 'local',
        allowBlank : true,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO_CONSULTA', 'DESCRIPCION_TIPO_CONSULTA'],
            data : [['1', 'Todas mis terminales'],
            ['2',  'Sólo esta terminal']]
        })
    });
    
    comboTIPO_CONSULTA.addListener( 'select',function(esteCombo, record,  index  ){

        comboREGISTRO_GESTION.store.baseParams['tipo_consulta'] = record.data.TIPO_CONSULTA;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;
    
    var comboTIPO_COBRO = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Cobro',
        name : 'tipoCobro',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'COB-CS',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['COB-CS', 'CHEQUE Y EFECTIVO'],
            ['COB-CHEQUE-CS', 'CHEQUE'],
            ['COB-EFECT-CS', 'EFECTIVO'],
            ['COB-POR-SERVICIO',  'POR SERVICIO'],
            ['COB-PARA-FAC',  'PARA FACTURADOR']]
        })
    });




    var comboEstadoTransaccion =  new Ext.form.ComboBox({
        fieldLabel: 'ESTADO TRANSACCIÓN',
        hiddenName : 'estadoTransaccion',
        valueField : 'estadoTransaccion',
        emptyText: 'ESTADO TRANSACCIÓN...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        value: 'null',

        store: new Ext.data.SimpleStore({
            fields: ['estadoTransaccion', 'descripcion'],
            data : [['null', 'Cualquiera'],
            ['S',  'Anulado'],
            ['N', 'No Anulado']]
        })
    });


    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
           
            comboRECAUDADOR.disable();
           
            comboSERVICIO.disable();
            comboTERMINAL.disable();
            comboREGISTRO_GESTION.disable();
            comboUSUARIO.disable();

            comboRECAUDADOR.reset();
            comboFACTURADOR.reset();
            comboSERVICIO.reset();
            comboTERMINAL.reset();
            comboREGISTRO_GESTION.reset();
            comboUSUARIO.reset();
        }        
    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboUSUARIO.disable();
            comboTERMINAL.disable();
            comboSUCURSAL.disable();
            comboUSUARIO.reset();
            comboTERMINAL.reset();
            comboSUCURSAL.reset();

        }

    }, null,null  ) ;

    comboFACTURADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){
            comboSERVICIO.disable();

            comboSERVICIO.reset();


        }

    }, null,null  ) ;

    comboUSUARIO.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 && comboTERMINAL.getRawValue().length==0){
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }

        comboREGISTRO_GESTION.store.baseParams['ID_USUARIO'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null,null  ) ;
    comboTERMINAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 && comboUSUARIO.getRawValue().length==0){
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null,null  ) ;

    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboFACTURADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboUSUARIO.enable();
        comboTERMINAL.enable();
        comboSUCURSAL.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null ) ;

    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null,null ) ;

    comboUSUARIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;

    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;

    comboTIPO_COBRO.addListener('change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue() == 'CHEQUE' || esteCombo.getRawValue() == 'EFECTIVO'){
            comboTIPO_REPORTE.disable();
            comboTIPO_REPORTE.setValue('DETALLADO');
        }else{
            comboTIPO_REPORTE.enable();
        }
    }, null,null ) ;

    comboRECAUDADOR.disable();
    comboSERVICIO.disable();
    comboTERMINAL.disable();
    comboREGISTRO_GESTION.disable();
    comboSUCURSAL.disable();
    comboUSUARIO.disable();
    comboTERMINAL.allowBlank= true;
    comboRECAUDADOR.allowBlank= true;
    comboUSUARIO.allowBlank= true;
    comboFACTURADOR.allowBlank=true;
    comboSERVICIO.allowBlank=true;
    comboREGISTRO_GESTION.allowBlank=true;
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
    fechaDeIni.addListener( 'change',function(esteCampo, nuevoValor,  viejoValor  ){

        comboREGISTRO_GESTION.store.baseParams['fechaIni'] = esteCampo.getRawValue();
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();
    }, null,null );

    fechaDeFin.addListener( 'change',function(esteCampo, nuevoValor,  viejoValor  ){

        if(fechaDeIni.getRawValue()!=""){
            comboREGISTRO_GESTION.store.baseParams['fechaFin'] = esteCampo.getRawValue();
            comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
            comboREGISTRO_GESTION.store.baseParams['start'] = 0;
            comboREGISTRO_GESTION.store.reload();
        }

    }, null,null );

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
        items : [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [comboRED,comboRECAUDADOR,comboSUCURSAL,comboUSUARIO,comboTERMINAL,comboFACTURADOR,comboSERVICIO,comboZONA]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [comboEstadoTransaccion,fechaDeIni,fechaDeFin,comboTIPO_CONSULTA,comboREGISTRO_GESTION,comboTIPO_REPORTE, comboTIPO_COBRO, formatoDeDescarga]
            }]
        }],
  
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
                        //src : 'reportes?op='+opDelServlet+'&tipoConsulta='+radioTipoTerminal.getValue().inputValue+'&idUsuario='+comboUSUARIO.getValue()+'&codigoServicio='+comboSERVICIO.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&idGestion='+comboREGISTRO_GESTION.getValue()+'&estadoTransaccion='+comboEstadoTransaccion.getValue()+'&fechaFin='+fechaFin.getRawValue()+'&fechaIni='+fechaIni.getRawValue()
                        src : 'reportes?op='+comboTIPO_COBRO.getValue()+'&idTerminal='+comboTERMINAL.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idRed='+comboRED.getValue()+'&idUsuario='+comboUSUARIO.getValue()+'&codigoServicio='+comboSERVICIO.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&idGestion='+comboREGISTRO_GESTION.getValue()+'&estadoTransaccion='+comboEstadoTransaccion.getValue()+'&fechaFin='+fechaDeFin.getRawValue()+'&fechaIni='+fechaDeIni.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoConsulta='+comboTIPO_CONSULTA.getValue()+'&tipoReporte='+comboTIPO_REPORTE.getRawValue()+'&zona='+comboZONA.getValue()
                    });
                } catch (e) {
                    alert(e);
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
                comboTIPO_REPORTE.enable();
                comboRECAUDADOR.disable();
                comboSERVICIO.disable();
                comboTERMINAL.disable();
                comboREGISTRO_GESTION.disable();
                comboUSUARIO.disable();
            }
        }]
    });
    return formPanel;
}