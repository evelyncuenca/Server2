var idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO ;
function gridDEPARTAMENTO(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'DEPARTAMENTO?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'DEPARTAMENTO',
            totalProperty : 'TOTAL',
            id : 'ID_DEPARTAMENTO',
            fields : [ 'ID_DEPARTAMENTO','PAIS','NOMBRE' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });var anchoDefaultColumnas= 10;var colModel = new Ext.grid.ColumnModel([{
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    },{
        header:'PAÍS',
        width: anchoDefaultColumnas,
        dataIndex: 'PAIS'
    } ]);var gridDEPARTAMENTO = new Ext.grid.GridPanel({
        title:'DEPARTAMENTO',
        id:'gridDEPARTAMENTO',
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
                if(Ext.getCmp('agregarDEPARTAMENTO') == undefined) pantallaAgregarDEPARTAMENTO().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'DEPARTAMENTO?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_DEPARTAMENTO : idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridDEPARTAMENTO').store.reload();
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
                                            title : FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
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
                idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarDEPARTAMENTO") == undefined)) {
                    idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarDEPARTAMENTO().show();
                }
            }
        }
    });return gridDEPARTAMENTO;
}
function pantallaModificarDEPARTAMENTO() {
    var comboPAIS =getCombo('PAIS','PAIS','PAIS','PAIS','DESCRIPCION_PAIS','PAÍS','PAIS','DESCRIPCION_PAIS','PAIS','PAÍS');
    comboPAIS.allowBlank = false;
    comboPAIS.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var departamentoModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarDEPARTAMENTO',
        labelWidth : 80,
        frame : true,
        autoWidth : true,

        monitorValid : true,
        items: [{
            fieldLabel:'ID DEPARTAMENTO',
            name:'ID_DEPARTAMENTO',
            inputType:'hidden',
            xtype:'textfield'
        },{
            fieldLabel:'NOMBRE',
            name:'NOMBRE',
            allowBlank : false,
            xtype:'textfield'
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboPAIS]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarPAIS().show();
                        }
                    }]
                }]
            }]
        }],

        buttons : [{
            id : 'btnmodificarDEPARTAMENTO',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                departamentoModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'DEPARTAMENTO?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarDEPARTAMENTO').close();Ext.getCmp('gridDEPARTAMENTO').store.reload();
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
                Ext.getCmp('modificarDEPARTAMENTO').close();
            }
        }]
    });departamentoModificarFormPanel.getForm().load({
        url : 'DEPARTAMENTO?op=DETALLE',
        method : 'POST',
        params:{
            ID_DEPARTAMENTO: idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar DEPARTAMENTO',
        id : 'modificarDEPARTAMENTO',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [departamentoModificarFormPanel]
    });return win;
}
function pantallaAgregarDEPARTAMENTO() {
    var comboPAIS =getCombo('PAIS','PAIS','PAIS','PAIS','DESCRIPCION_PAIS','PAÍS','PAIS','DESCRIPCION_PAIS','PAIS','PAÍS');
    comboPAIS.allowBlank = false;
    var departamentoAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarDEPARTAMENTO',
        labelWidth : 80,
        frame : true,
        autoWidth : true,

        monitorValid : true,
        items: [{
            fieldLabel:'NOMBRE',
            name:'NOMBRE',
            allowBlank : false,
            xtype:'textfield'
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboPAIS]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarPAIS().show();
                        }
                    }]
                }]
            }]
        }],
        buttons : [{
            id : 'btnAgregarDEPARTAMENTO',
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
                            departamentoAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'DEPARTAMENTO?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarDEPARTAMENTO').close();Ext.getCmp('gridDEPARTAMENTO').store.reload();
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
                Ext.getCmp('agregarDEPARTAMENTO').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar DEPARTAMENTO',
        id : 'agregarDEPARTAMENTO',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [departamentoAgregarFormPanel]
    });return win;
}


