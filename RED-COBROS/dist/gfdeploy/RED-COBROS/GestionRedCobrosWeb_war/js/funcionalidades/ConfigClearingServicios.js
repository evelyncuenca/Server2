var idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO ;
function gridCONFIG_CLEARING_SERVICIO(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CONFIG_CLEARING_SERVICIO?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'CONFIG_CLEARING_SERVICIO',
            totalProperty : 'TOTAL',
            id : 'ID_CONFIG_CLEARING_SERVICIO',
            fields : ['ID_CONFIG_CLEARING_SERVICIO','DESDE','HASTA','VALOR','RED','SERVICIO','TIPO_CLEARING','SERVICIO_RC','MONTO','HABILITADO']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'DESDE',
        width: anchoDefaultColumnas,
        dataIndex: 'DESDE'
    },{
        header:'HASTA',
        width: anchoDefaultColumnas,
        dataIndex: 'HASTA'
    },{
        header:'VALOR',
        width: anchoDefaultColumnas,
        dataIndex: 'VALOR'
    },{
        header:'MONTO',
        width: anchoDefaultColumnas,
        dataIndex: 'MONTO'
    },{
        header:'RED',
        width: anchoDefaultColumnas,
        dataIndex: 'RED'
    },{
        header:'SERVICIO',
        width: anchoDefaultColumnas,
        dataIndex: 'SERVICIO'
    },{
        header:'TIPO_CLEARING',
        width: anchoDefaultColumnas,
        dataIndex: 'TIPO_CLEARING'
    },{
        header:'SERVICIO RC',
        width: anchoDefaultColumnas,
        dataIndex: 'SERVICIO_RC'
    },{
        header:'HABILITADO',
        width: anchoDefaultColumnas,
        dataIndex: 'HABILITADO'
    }]);
    var gridCONFIG_CLEARING_SERVICIO = new Ext.grid.GridPanel({
        title:'Administración de Configuración Clearing Servicios',
        id:'gridCONFIG_CLEARING_SERVICIO',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
        tbar:[{
            text:'Agregar',
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarCONFIG_CLEARING_SERVICIO') == undefined) pantallaAgregarCONFIG_CLEARING_SERVICIO().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();
                                conn.request({
                                    url : 'CONFIG_CLEARING_SERVICIO?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_CONFIG_CLEARING_SERVICIO : idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO
                                    },
                                    success : function(action) {
                                        var  obj = Ext.util.JSON .decode(action.responseText);
                                        if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('gridCONFIG_CLEARING_SERVICIO').store.reload();
                                        }else{
                                            Ext.Msg.show({
                                                title : 'Estado',
                                                msg : obj.motivo,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                                        }
                                    },
                                    failure : function(action) {
                                        Ext.Msg.show({
                                            title :FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.ERROR,
                                            animEl : 'elId',
                                            icon : Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        },{
            text:'CONFIG_CLEARING_SERVICIO',
            id:'idBusquedaCONFIG_CLEARING_SERVICIO',
            xtype:'textfield',
            emptyText:'CONFIG_CLEARING_SERVICIO..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridCONFIG_CLEARING_SERVICIO').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        }],
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : st,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarCONFIG_CLEARING_SERVICIO") == undefined)) {
                    idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarCONFIG_CLEARING_SERVICIO().show().center();
                }
            }
        }
    });
    return gridCONFIG_CLEARING_SERVICIO;
}
function pantallaModificarCONFIG_CLEARING_SERVICIO() {
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboSERVICIOS_DISPONIBLES = getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIOS','ID_SERVICIO_RC','DESCRIPCION_SERVICIO','SERVICIO','SERVICIOS');
    var comboTIPO_CLEARING = getCombo("TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO CLEARING", "ID_TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO_CLEARING", "TIPO CLEARING");

    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDesdeConfigClearingServicios').focus(true, true);
    }, null,null ) ;

    comboSERVICIOS_DISPONIBLES.addListener( 'select',function(esteCombo, record,  index  ){
        comboTIPO_CLEARING.focus(true, true);
    }, null,null ) ;

    comboTIPO_CLEARING.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idMontoConfigClearingServicios').focus(true, true);
    }, null,null ) ;

    comboRED.allowBlank=false;
    comboTIPO_CLEARING.allowBlank=false;

    var comboHABILITADO = new Ext.form.ComboBox({
        fieldLabel: 'HABILITADO',
        id:'idHabilitadoConfigClearingServicios',
        name : 'HABILITADO',
        hiddenName : 'HABILITADO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth : 180,
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N',  'NO']]
        }),
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                Ext.getCmp('btnagregarCONFIG_CLEARING_SERVICIO').focus(true,true);
            }
        }
    });

    var configClearingServicioModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarCONFIG_CLEARING_SERVICIO',
        labelWidth : 100,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID CONFIG_CLEARING_SERVICIO',
            name:'ID_CONFIG_CLEARING_SERVICIO',
            xtype:'textfield',
            inputType:'hidden'
        },comboRED,{
            fieldLabel:'DESDE',
            name:'DESDE',
            id  :'idDesdeConfigClearingServicios',
            xtype:'numberfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idHastaConfigClearingServicios').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'HASTA',
            name:'HASTA',
            id  :'idHastaConfigClearingServicios',
            xtype:'numberfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idValorConfigClearingServicios').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'VALOR',
            id  :'idValorConfigClearingServicios',
            name:'VALOR',
            xtype:'textfield',
             allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        comboSERVICIOS_DISPONIBLES.focus(true,true);
                    }
                }
            }
        },comboSERVICIOS_DISPONIBLES,comboTIPO_CLEARING,{
            fieldLabel:'MONTO',
            id  :'idMontoConfigClearingServicios',
            name:'MONTO',
            xtype:'textfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        comboHABILITADO.focus(true,true);
                    }
                }
            }

        },comboHABILITADO],
        buttons : [{
            id : 'btnmodificarCONFIG_CLEARING_SERVICIO',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "ok") {
                                configClearingServicioModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : 'Conectando',
                                    waitMsg : 'Modificando...',
                                    url : 'CONFIG_CLEARING_SERVICIO?op=MODIFICAR',
                                    timeout : 600000,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarCONFIG_CLEARING_SERVICIO').close();
                                        Ext.getCmp('gridCONFIG_CLEARING_SERVICIO').store.reload();
                                    },
                                    failure : function(form, action) {
                                        var obj = Ext.util.JSON .decode(action.response.responseText);
                                        if(obj.motivo != undefined ){
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : obj.motivo,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                                        }else{
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : FAIL_CUERPO_GENERAL,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarCONFIG_CLEARING_SERVICIO').close();
            }
        }]
    });
    configClearingServicioModificarFormPanel.getForm().load({
        url : 'CONFIG_CLEARING_SERVICIO?op=DETALLE',
        method : 'POST',
        params:{
            ID_CONFIG_CLEARING_SERVICIO: idCONFIG_CLEARING_SERVICIOSeleccionadoID_CONFIG_CLEARING_SERVICIO
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar Config Clearing Servicios',
        id : 'modificarCONFIG_CLEARING_SERVICIO',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [configClearingServicioModificarFormPanel]
    });
    return win;

}
function pantallaAgregarCONFIG_CLEARING_SERVICIO() {
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboSERVICIOS_DISPONIBLES = getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIOS','ID_SERVICIO_RC','DESCRIPCION_SERVICIO','SERVICIO','SERVICIOS');
    var comboTIPO_CLEARING = getCombo("TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO CLEARING", "ID_TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO_CLEARING", "TIPO CLEARING");

    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDesdeConfigClearingServicios').focus(true, true);
    }, null,null ) ;

    comboSERVICIOS_DISPONIBLES.addListener( 'select',function(esteCombo, record,  index  ){
        comboTIPO_CLEARING.focus(true, true);
    }, null,null ) ;

    comboTIPO_CLEARING.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idMontoConfigClearingServicios').focus(true, true);
    }, null,null ) ;

    comboRED.allowBlank=false;
    comboTIPO_CLEARING.allowBlank=false;

    var comboHABILITADO = new Ext.form.ComboBox({
        fieldLabel: 'HABILITADO',
        id:'idHabilitadoConfigClearingServicios',
        name : 'HABILITADO',
        hiddenName : 'HABILITADO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth : 180,
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N',  'NO']]
        }),
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                Ext.getCmp('btnagregarCONFIG_CLEARING_SERVICIO').focus(true,true);
            }
        }
    });

    var configClearingServicioAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarCONFIG_CLEARING_SERVICIO',
        labelWidth : 100,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID CONFIG_CLEARING_SERVICIO',
            name:'ID_CONFIG_CLEARING_SERVICIO',
            xtype:'textfield',
            inputType:'hidden'
        },comboRED,{
            fieldLabel:'DESDE',
            name:'DESDE',
            id  :'idDesdeConfigClearingServicios',
            xtype:'numberfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idHastaConfigClearingServicios').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'HASTA',
            name:'HASTA',
            id  :'idHastaConfigClearingServicios',
            xtype:'numberfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idValorConfigClearingServicios').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'VALOR',
            id  :'idValorConfigClearingServicios',
            name:'VALOR',
            xtype:'textfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        comboSERVICIOS_DISPONIBLES.focus(true,true);
                    }
                }
            }
        },comboSERVICIOS_DISPONIBLES,comboTIPO_CLEARING,{
            fieldLabel:'MONTO',
            id  :'idMontoConfigClearingServicios',
            name:'MONTO',
            xtype:'textfield',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        comboHABILITADO.focus(true,true);
                    }
                }
            }

        },comboHABILITADO],
        buttons : [{
            id : 'btnagregarCONFIG_CLEARING_SERVICIO',
            text : 'Agregar',
            formBind : true,
            handler : function() {
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            configClearingServicioAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'CONFIG_CLEARING_SERVICIO?op=AGREGAR',
                                timeout : 600000,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarCONFIG_CLEARING_SERVICIO').close();
                                    Ext.getCmp('gridCONFIG_CLEARING_SERVICIO').store.reload();
                                },
                                failure : function(form, action) {
                                    var obj = Ext.util.JSON .decode(action.response.responseText);
                                    if(obj.motivo != undefined ){
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : obj.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR
                                        });
                                    }else{
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarCONFIG_CLEARING_SERVICIO').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar Config Clearing Servicios',
        id : 'agregarCONFIG_CLEARING_SERVICIO',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [configClearingServicioAgregarFormPanel]
    });
    return win;
}