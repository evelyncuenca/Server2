//Variables globales
var editorServicios = new Ext.ux.grid.RowEditor({
    saveText: 'Actualizar'
});
var editorServiciosActivo = null;

function panelControlServicios() {
    var panel = new Ext.TabPanel({
        id : 'panelControlServicios',
        activeTab: 0,
        autoScroll:true,
        frame:true,
        defaults:{
            autoScroll: true
        },
        items:[{
            title: 'Servicios',
            xtype:'panel',
            frame:true,
            items:[cabeceraControlServicios(),gridControlServicios(),{
                id: 'detailPanel',

                bodyStyle: {
                    background: '#ffffff',
                    padding: '7px'
                },
                html: 'Seleccionar un registro para ver detalles ...'
            }]

        }]
    });
    return panel;
}

function cabeceraControlServicios(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    var comboTERMINAL = getCombo("TERMINAL", "TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL");
    // var comboGESTION = getCombo("GESTION", "GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION");
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','GESTIÓN','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','REGISTRO_GESTION','GESTIONES...');

    var fechaDesde = new Ext.form.DateField({
        fieldLabel : 'Desde',
        name : 'FECHA_DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y'
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel : 'Hasta',
        name : 'FECHA_HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y'
    });
    
    /*******************/


    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboRECAUDADOR.disable();
            comboSUCURSAL.disable();
            comboTERMINAL.disable();
            comboREGISTRO_GESTION.disable();

            comboRECAUDADOR.reset();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();
            comboREGISTRO_GESTION.reset();

        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboTERMINAL.disable();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();

        }

    }, null,null  ) ;

    comboSUCURSAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
            comboTERMINAL.disable();
            comboTERMINAL.reset();
        }

        comboTERMINAL.store.baseParams['sUCURSAL'] = newValue;
        comboTERMINAL.store.baseParams['limit'] = 10;
        comboTERMINAL.store.baseParams['start'] = 0;
        comboTERMINAL.store.reload();

    }, null,null  ) ;
    comboTERMINAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
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

        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){

        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
    }, null,null ) ;

    comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_SUCURSAL'] = record.data.SUCURSAL;
        comboTERMINAL.store.reload();


    }, null,null ) ;



    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;

    comboRECAUDADOR.disable();

    comboTERMINAL.disable();
    comboREGISTRO_GESTION.disable();
    comboSUCURSAL.disable();

    comboRED.allowBlank= false;
    comboTERMINAL.allowBlank= true;
    comboRECAUDADOR.allowBlank= true;
    comboREGISTRO_GESTION.allowBlank=true;
    comboSUCURSAL.allowBlank = false;

    /*********************/
    var idTransaccion = {
        id:'idTransaccionControl',
        name :'ID_TRANSACCION',
        xtype:'textfield',
        fieldLabel: 'TRANSACCION',
        allowBlank : true,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  || esteEvento.getKey() == 9){
                    Ext.getCmp('idFiltroControlServicios').focus(true,true);
                }
            }
        }

    };

    var formPanel = new Ext.form.FormPanel({
        title   : 'Control de Servicios',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        buttons : [{
            text : 'Filtrar',
            id :'idFiltroControlServicios',
            formBind : true,
            handler : function() {
                Ext.getCmp('gridControlServicios').store.baseParams['ID_TRANSACCION'] = Ext.getCmp('idTransaccionControl').getValue();
                Ext.getCmp('gridControlServicios').store.baseParams['FECHA_DESDE'] = fechaDesde.getRawValue();
                Ext.getCmp('gridControlServicios').store.baseParams['FECHA_HASTA'] = fechaHasta.getRawValue();
                Ext.getCmp('gridControlServicios').store.baseParams['ID_RECAUDADOR'] = comboRECAUDADOR.getValue();
                Ext.getCmp('gridControlServicios').store.baseParams['ID_RED'] = comboRED.getValue();
                Ext.getCmp('gridControlServicios').store.baseParams['ID_GESTION'] = comboREGISTRO_GESTION.getValue();
                Ext.getCmp('gridControlServicios').store.baseParams['ID_SUCURSAL'] = comboSUCURSAL.getValue();
                Ext.getCmp('gridControlServicios').store.baseParams['ID_TERMINAL'] = comboTERMINAL.getValue();       

                Ext.getCmp('gridControlServicios').store.reload();
            }
        },{
            text : 'Eliminar Filtros',
            handler : function() {
                Ext.getCmp('gridControlServicios').store.baseParams['ID_TRANSACCION'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['FECHA_DESDE'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['FECHA_HASTA'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['ID_RECAUDADOR'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['ID_GESTION'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['ID_SUCURSAL'] = "";
                Ext.getCmp('gridControlServicios').store.baseParams['ID_TERMINAL'] = "";               
                Ext.getCmp('gridControlServicios').store.baseParams['ID_RED']  ="";

                Ext.getCmp('gridControlServicios').store.reload();               

                fechaDesde.reset();
                fechaHasta.reset();
                comboREGISTRO_GESTION.reset();
                comboRECAUDADOR.reset();
                comboSUCURSAL.reset();
                comboTERMINAL.reset();
                comboRED.reset();
                Ext.getCmp('idTransaccionControl').reset();

            }
        }],
        items   : [
        {
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [fechaDesde,comboRED,comboRECAUDADOR, comboSUCURSAL]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [fechaHasta,comboTERMINAL,comboREGISTRO_GESTION,idTransaccion]
            }]
        }]
    });


    return formPanel;

}

function gridControlServicios(){
    var proxy = new Ext.data.HttpProxy({
        url: 'CONTROL_SERVICIOS?op=LISTAR'
    });
    var reader = new Ext.data.JsonReader({
        root : 'TRANSACCIONES',
        totalProperty : 'TOTAL',
        id : 'ID_TRANSACCION',
        fields : ["ID_TRANSACCION", "CODIGO_TERMINAL", "SERVICIO_DESCRIPCION", "NRO_BOLETA", "FORMA_PAGO", "FECHA_COBRO", "REFERENCIA_PAGO", "ANULADO","MONTO_TOTAL","USUARIO"]
    });
    var writer = new Ext.data.JsonWriter();
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,
        listeners: {
            'update'  : function ( esteObjeto,  record, operation ) {
                actualizarServicio( esteObjeto,  record, operation, true, 1);
            }
        }
    });
    
    var comboANULADO=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'opcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'opcion'],
            data : [['S', 'SI'],
            ['N',  'NO']]
        })
    });

    var anchoDefault= 20;
    var smFormGrid = new Ext.grid.CheckboxSelectionModel({
        singleSelect: false
    });
    var userColumns =  [
    smFormGrid.valueOf(),{
        header :"Transaccion",
        width :15,
        dataIndex:'ID_TRANSACCION'
    },{
        header : "Terminal",
        width : 15,
        dataIndex : 'CODIGO_TERMINAL'

    },{
        header : "Usuario",
        width : 15,
        dataIndex : 'USUARIO'

    }, {
        header : "Servicio",
        width : 15,
        dataIndex : 'SERVICIO_DESCRIPCION'

    },{
        header : "Nro. Boleta",
        width : anchoDefault,
        dataIndex : 'NRO_BOLETA'

    },{
        header : "Forma Pago",
        width : 15,
        dataIndex : 'FORMA_PAGO'

    },{
        header : "Fecha Cobro",
        width : anchoDefault,
        dataIndex : 'FECHA_COBRO'        
    },{
        header : "Ref. Pago",
        width : 15,
        dataIndex : 'REFERENCIA_PAGO'
    },{
        header : "Monto",
        width : 15,
        dataIndex : 'MONTO_TOTAL'
    },{
        header : "Anulado",
        width : 15,
        dataIndex : 'ANULADO',
        editor: comboANULADO
    }];
    store.load();
    editorServicios.id = "idEditor";
    var bookTplMarkup = [
    '<div >',
    '<table>',

    '<tr>',
    '<td>',
    'MONTO TOTAL: {MONTO_TOTAL}',
    '</td>',
    '</tr>',
    '<tr>',
    '<td>',
    '{CAMPO_VALOR}',
    '</td>',
    '</tr>',

    '</table>',
    '</div>'
    ];
    var bookTpl = new Ext.Template(bookTplMarkup);

    var gridControlServicio = new Ext.grid.GridPanel({
        id:'gridControlServicios',
        height:400,
        sm:smFormGrid,
        // margins: '0 5 5 5',
        plugins: [editorServicios],
        store: store,
        // autoScroll:true,
        columns:userColumns,
        viewConfig: {
            forceFit:true
        },
        tbar:[],
        //split: true,
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : store,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        // frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                editorServiciosActivo = editorServicios.id;
            }
        }
    });

    gridControlServicio.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
        var detailPanel = Ext.getCmp('detailPanel');
        bookTpl.overwrite(detailPanel.body, r.data);
    });

    return gridControlServicio;

}
//function detalleControlServicios(elIdTransaccion,laOpcion){
//
//    var individual = [{
//        anchor:'95%',
//        bodyStyle: 'padding-right:5px;',
//        items: {
//            xtype: 'fieldset',
//            title: 'Cabecera Servicios',
//            autoHeight: true,
//            defaultType: 'checkbox',
//            items: [{
//                xtype: 'textfield',
//                name: 'NUMERO_Servicio',
//                fieldLabel: 'Servicio'
//            }, {
//                xtype: 'textfield',
//                name: 'FECHA_COBRO',
//                fieldLabel: 'Fecha Cobro'
//            },{
//                xtype: 'textfield',
//                name: 'NUMERO_ORDEN',
//                fieldLabel: 'Nro. Orden'
//            }, {
//                xtype: 'textfield',
//                name: 'RUC',
//                fieldLabel: 'RUC'
//            }, {
//                xtype: 'textfield',
//                name: 'CONSECUTIVO',
//                fieldLabel: 'Consecutivo'
//            }]
//        }
//    }, {
//        bodyStyle: 'padding-left:5px;',
//        items: {
//            anchor:'95%',
//            xtype: 'fieldset',
//            title: 'Lugar de Cobro',
//            autoHeight: true,
//            defaultType: 'radio',
//            items: [{
//                xtype: 'textfield',
//                name: 'CODIGO_TERMINAL',
//                fieldLabel: 'Terminal'
//            }, {
//                xtype: 'textfield',
//                name: 'CODIGO_RECAUDADOR',
//                fieldLabel: 'Recaudador'
//            },{
//                xtype: 'textfield',
//                name: 'DESCRIPCION_RECAUDADOR',
//                fieldLabel: 'Descripcion'
//            },{
//                xtype: 'textfield',
//                name: 'CODIGO_CAJA_SET',
//                fieldLabel: 'Caja Nro.'
//            }]
//        }
//    }];
//
//    var individual2 = [{
//        bodyStyle: 'padding-right:5px;',
//        items: {
//            xtype: 'fieldset',
//            title: 'Importe y Forma de Pago',
//            autoHeight: true,
//            defaultType: 'checkbox',
//            items: [{
//                xtype: 'textfield',
//                name: 'MONTO_TOTAL',
//                fieldLabel: 'Monto Total'
//            }, {
//                xtype: 'textfield',
//                name: 'MONTO_PAGADO',
//                fieldLabel: 'Monto Pagado'
//            },{
//                xtype: 'textfield',
//                name: 'FECHA_COBRO',
//                fieldLabel: 'Fecha Cobro'
//            },{
//                xtype: 'textfield',
//                name: 'FORMA_PAGO',
//                fieldLabel: 'Forma de Pago'
//            },{
//                xtype: 'textfield',
//                name: 'NUMERO_CHEQUE',
//                fieldLabel: 'Nro. de Cheque'
//            },{
//                xtype: 'textfield',
//                name: 'ID_BANCO',
//                fieldLabel: 'Banco'
//            },{
//                xtype: 'textfield',
//                name: 'DESCRIPCION_BANCO',
//                fieldLabel: 'Descripción'
//            },{
//                xtype: 'textfield',
//                name: 'RECHAZADO',
//                fieldLabel: 'Estado Cheque'
//            }]
//        }
//    }, {
//        bodyStyle: 'padding-left:5px;',
//        items: {
//            xtype: 'fieldset',
//            title: 'Orden de Transaferencia',
//            autoHeight: true,
//            defaultType: 'radio',
//            items: [{
//                xtype: 'textfield',
//                name: 'NUMERO_OT',
//                fieldLabel: 'O.T Nro.'
//            },{
//                xtype: 'textfield',
//                name: 'FECHA_RECIBIDO',
//                fieldLabel: 'Fecha Recepción'
//            },{
//                xtype: 'textfield',
//                name: 'FECHA_CONTROLADO',
//                fieldLabel: 'Fecha Controlado'
//            },{
//                xtype: 'textfield',
//                name: 'ENVIADO',
//                fieldLabel: 'Enviado'
//            },{
//                xtype: 'textfield',
//                name: 'txt-test1',
//                fieldLabel: 'Fecha Valor BCO'
//            },{
//                xtype: 'textfield',
//                name: 'txt-test1',
//                fieldLabel: 'Fecha Valor BCP'
//            },{
//                xtype: 'textfield',
//                name: 'txt-test1',
//                fieldLabel: 'Fecha Venc. Doc.'
//            },{
//                xtype: 'textfield',
//                name: 'txt-test1',
//                fieldLabel: 'Fecha Entrega'
//            }]
//        }
//    }];
//    var fp = new Ext.FormPanel({
//
//        frame: true,
//        labelWidth: 110,
//        width: 600,
//        bodyStyle: 'padding:0 10px 0;',
//        items: [
//        {
//            layout: 'column',
//            border: false,
//
//            defaults: {
//                columnWidth: '.5',
//                border: false
//            },
//            items: individual
//        },
//        {
//            layout: 'column',
//            border: false,
//
//            defaults: {
//                columnWidth: '.5',
//                border: false
//            },
//            items: individual2
//        }
//        ],
//        buttons: [{
//            text: 'Salir',
//            handler: function(){
//                win.close();
//            }
//        }]
//    });
//
//    fp.getForm().load({
//        url : 'CONTROL_SERVICIOS?op='+laOpcion,
//        method : 'POST',
//        params:{
//            ID_FORM_CONTRIB: elIdTransaccion
//        },
//        waitMsg : 'Cargando...'
//    });
//
//
//    var win = new Ext.Window({
//        title:'Detalle Control de Servicios',
//        autoWidth : true,
//        height : 'auto',
//        closable : false,
//        modal:true,
//        y:100,
//        resizable : false,
//        items : [fp]
//    });
//    win.show();
//
//
//
//}



//function callExternalFunctionServicios(){
//    //Poner el hook correspondiente a la funcion que se quiere llamar
//
//    if(editorServiciosActivo == 'idEditor'){
//        editorServicios.editing = false;
//        editorServicios.hide();
//        detalleControlServicios(Ext.getCmp('gridControlServicios').getSelectionModel().getSelected().data.ID_FORM_CONTRIB,'DETALLE');
//    }else if(editorServiciosActivo == 'idEditor2'){
//        editorServicios2.editing = false;
//        editorServicios2.hide();
//        detalleControlServicios(Ext.getCmp('gridControlServiciosBoletas').getSelectionModel().getSelected().data.ID_TRANSACCION,'DETALLE_BOLETAS');
//    }
//
//}

function actualizarServicio( esteObjeto,  record, operation, reload, isFecha){
    pantallaAgregarMotivo( esteObjeto,  record, operation, reload, isFecha)
    
}
function pantallaAgregarMotivo(esteObjeto,  record, operation, reload, isFecha){
   
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosAgregarMotivo(esteObjeto,  record, operation, reload, isFecha)]
    };
    var win = new Ext.Window({
        id:'idWinMotGest',
        title:'Motivo Anulacion',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosAgregarMotivo( esteObjeto,  record, operation, reload, isFecha){

    var formPanel = new Ext.FormPanel({
        labelWidth : 70,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [{
            xtype:'textarea',
            id:'idMotGest',
            name:'MOTIVO',
            fieldLabel: 'Motivo',
            allowBlank : false
        }],
        buttons : [{
            text : 'Anular',
            formBind : true,
            handler : function() {
                formPanel.getForm().submit({
                    url : 'CONTROL_SERVICIOS?op=MODIFICAR',
                    method : 'POST',
                    waitTitle : 'Estado',
                    waitMsg : 'Anulando..',
                    params : {
                        ID_TRANSACCION : record.data.ID_TRANSACCION,
                        ANULADO : record.data.ANULADO
                    },
                    timeout : TIME_OUT_AJAX,
                    success : function(form,action1) {                       
                        var obj = Ext.util.JSON.decode(action1.response.responseText);
                        if(obj.success){
                            Ext.getCmp('idWinMotGest').close();
                            Ext.Msg.show({
                                title : 'Info',
                                msg :obj.motivo,
                                icon : Ext.MessageBox.INFO,
                                buttons : Ext.Msg.OK

                            });
                            if (reload) {
                                Ext.getCmp('gridControlServicios').store.reload();
                            }
                        }else{
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg :obj.motivo,
                                icon : Ext.MessageBox.ERROR,
                                buttons : Ext.Msg.OK

                            });
                            Ext.getCmp('gridControlServicios').store.reload();
                        }
                    } ,
                    failure : function(form,action) { 
                        Ext.Msg.show({
                            title : FAIL_TITULO_GENERAL,
                            msg :FAIL_CUERPO_GENERAL,
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK

                        });
                        Ext.getCmp('gridControlServicios').store.reload();
                    }
                });             
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