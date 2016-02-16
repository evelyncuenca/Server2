var idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL ;
function gridUSUARIO_TERMINAL(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'USUARIO_TERMINAL?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'USUARIO_TERMINAL',
            totalProperty : 'TOTAL',
            id : 'ID_USUARIO_TERMINAL',
            fields : [ 'ID_USUARIO_TERMINAL','FRANJA_HORARIA_CAB','CONF_USUARIO','TERMINAL','CONF_USUARIO','LOGUEADO','RECAUDADOR','SUCURSAL']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'USUARIO',
        width: anchoDefaultColumnas,
        dataIndex: 'CONF_USUARIO'
    }, {
        header:'TERMINAL',
        width: anchoDefaultColumnas,
        dataIndex: 'TERMINAL'
    },{
        header:'RECAUDADOR',
        width: anchoDefaultColumnas,
        dataIndex: 'RECAUDADOR'
    },{
        header:'SUCURSAL',
        width: anchoDefaultColumnas,
        dataIndex: 'SUCURSAL'
    },{
        header:'FRANJA HORARIA',
        width: anchoDefaultColumnas,
        dataIndex: 'FRANJA_HORARIA_CAB'
    },{
        header:'LOGUEADO',
        width: anchoDefaultColumnas,
        dataIndex: 'LOGUEADO'
    } ]);
    var gridUSUARIO_TERMINAL = new Ext.grid.GridPanel({
        title:'USUARIO TERMINAL',
        id:'gridUSUARIO_TERMINAL',
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
                if(Ext.getCmp('agregarUSUARIO_TERMINAL') == undefined) pantallaAgregarUSUARIO_TERMINAL().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL!=undefined){
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
                                    url : 'USUARIO_TERMINAL?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_USUARIO_TERMINAL : idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);
                                        if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
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
                                            title : 'Estado',
                                            msg : 'No se pudo realizar la operación.',
                                            buttons : Ext.Msg.OK,
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
            text:'Usuario',
            id:'idBusquedaUsarioTerminal',
            xtype:'textfield',
            emptyText:'Usuario..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridUSUARIO_TERMINAL').store.load({
                            params: {
                                start:0,
                                limit:20,
                                CONF_USUARIO:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        },{
            text:'Terminal',
            id:'idBusquedaUSTerminal',
            xtype:'textfield',
            emptyText:'Terminal..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridUSUARIO_TERMINAL').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                TERMINAL:esteObjeto.getValue()
                            }
                        });
                    }
                }
            }
        },{
            text:'Salir',
            tooltip:'Salir',
            iconCls:'logout',
            handler:function(){
                limpiarPaneles("CANCELAR");
                cardInicial();
            }
        }],
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : st,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarUSUARIO_TERMINAL") == undefined)) {
                    idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarUSUARIO_TERMINAL().show();
                }
            }
        }
    });
    return gridUSUARIO_TERMINAL;
}
function pantallaModificarUSUARIO_TERMINAL() {
    var comboUSUARIO =     getCombo('CONF_USUARIO','CONF_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    var comboFRANJA_HORARIA_CAB = getCombo('FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','DESCRIPCION_FRANJA_HORARIA_CAB','FRANJA HORARIA','FRANJA_HORARIA_CAB','DESCRIPCION_FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA HORARIA');
    comboTERMINAL.listWidth=300;
    comboUSUARIO.allowBlank = false;
    comboTERMINAL.allowBlank = false;
    comboFRANJA_HORARIA_CAB.allowBlank = false;

    var logueado =  new Ext.form.Checkbox ({
        fieldLabel: 'LOGUEADO',
        vertical: true,
        columns: 1,
        name: 'LOGUEADO',
        items: [{
            boxLabel: 'LOGUEADO'
        }]
    });
    comboTERMINAL.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    comboUSUARIO.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    comboFRANJA_HORARIA_CAB.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    comboUSUARIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboTERMINAL.store.baseParams['USUARIO'] = record.data.USUARIO;
        comboTERMINAL.store.reload();
        comboTERMINAL.enable();

    }, null,null ) ;
    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){

        comboFRANJA_HORARIA_CAB.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboFRANJA_HORARIA_CAB.store.reload();
        comboFRANJA_HORARIA_CAB.enable();
    }, null,null ) ;
    var usuario_terminalModificarFormPanel =new Ext.FormPanel({
        id : 'idFormPanelModificarUSUARIO_TERMINAL',
        labelAlign: 'left',
        labelWidth : 120,
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID USUARIO TERMINAL',
            name:'ID_USUARIO_TERMINAL',
            xtype:'textfield',
            inputType:'hidden'
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboUSUARIO,comboTERMINAL,comboFRANJA_HORARIA_CAB]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',
                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarUSUARIO().show();
                        }
                    }]
                },{
                    xtype:'panel',
                    bodyStyle:'padding:4px 0 0',
                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarTERMINAL().show();

                        }
                    }]
                }]
            }]
        },logueado],

        buttons : [{
            id : 'btnmodificarUSUARIO_TERMINAL',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                usuario_terminalModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'USUARIO_TERMINAL?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarUSUARIO_TERMINAL').close();
                                        Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                                    },
                                    failure : function(form, action) {
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR
                                        });
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
                Ext.getCmp('modificarUSUARIO_TERMINAL').close();
            }
        }]
    });

    usuario_terminalModificarFormPanel.getForm().load({
        url : 'USUARIO_TERMINAL?op=DETALLE',
        method : 'POST',
        params:{
            ID_USUARIO_TERMINAL: idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar USUARIO TERMINAL',
        id : 'modificarUSUARIO_TERMINAL',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items : [usuario_terminalModificarFormPanel]
    });
    return win;
}
function pantallaAgregarUSUARIO_TERMINAL() { 

    var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    var comboFRANJA_HORARIA_CAB = getCombo('FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','DESCRIPCION_FRANJA_HORARIA_CAB','FRANJA HORARIA','FRANJA_HORARIA_CAB','DESCRIPCION_FRANJA_HORARIA_CAB','FRANJA_HORARIA_CAB','FRANJA HORARIA');
    var comboUSUARIO =     getCombo('CONF_USUARIO','CONF_USUARIO','CONF_USUARIO','CONF_USUARIO','DESCRIPCION_USUARIO','USUARIO','CONF_USUARIO','DESCRIPCION_USUARIO','CONF_USUARIO','USUARIO');

    comboUSUARIO.allowBlank = false;
    comboTERMINAL.allowBlank = false;
    comboFRANJA_HORARIA_CAB.allowBlank = false;

    comboTERMINAL.disable();
    comboFRANJA_HORARIA_CAB.disable();
    comboUSUARIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboTERMINAL.store.baseParams['USUARIO'] = record.data.USUARIO;
        comboTERMINAL.store.reload();
        comboTERMINAL.enable();
    }, null,null ) ;
    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){
        comboFRANJA_HORARIA_CAB.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboFRANJA_HORARIA_CAB.store.reload();
        comboFRANJA_HORARIA_CAB.enable();
    }, null,null ) ;
    var usuario_terminalAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarUSUARIO_TERMINAL',
        labelAlign: 'left',
        labelWidth : 120,
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboUSUARIO,comboTERMINAL,comboFRANJA_HORARIA_CAB]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarUSUARIO().show();
                        }
                    }]
                },{
                    xtype:'panel',
                    bodyStyle:'padding:4px 0 0',
                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarTERMINAL().show();

                        }
                    }]
                }]
            }]
        }],
        //        items : [{
        //            fieldLabel:'CODIGO CAJERO',
        //            xtype:'numberfield',
        //            name:'CODIGO_CAJERO'
        //        },comboUSUARIO,comboTERMINAL,comboFRANJA_HORARIA_CAB ],
        buttons : [{
            id : 'btnAgregarUSUARIO_TERMINAL',
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
                            usuario_terminalAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'USUARIO_TERMINAL?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarUSUARIO_TERMINAL').close();
                                    Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                                },
                                failure : function(form, action) {
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : FAIL_CUERPO_GENERAL,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });
                                }
                            });
                        }
                    }
                });
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarUSUARIO_TERMINAL').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar USUARIO TERMINAL',
        id : 'agregarUSUARIO_TERMINAL',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items : [usuario_terminalAgregarFormPanel]
    });
    return win;
}
