var idCIUDADSeleccionadoID_CIUDAD ;
function gridCIUDAD(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CIUDAD?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'CIUDAD',
            totalProperty : 'TOTAL',
            id : 'ID_CIUDAD',
            fields : [ 'ID_CIUDAD','DEPARTAMENTO','NOMBRE' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });var anchoDefaultColumnas= 10;var colModel = new Ext.grid.ColumnModel([ {
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    },{
        header:'DEPARTAMENTO',
        width: anchoDefaultColumnas,
        dataIndex: 'DEPARTAMENTO'
    } ]);var gridCIUDAD = new Ext.grid.GridPanel({
        title:'CIUDAD',
        id:'gridCIUDAD',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        }
        ,
        tbar:[{
            text:'Agregar',
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarCIUDAD') == undefined) pantallaAgregarCIUDAD().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idCIUDADSeleccionadoID_CIUDAD!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'CIUDAD?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_CIUDAD : idCIUDADSeleccionadoID_CIUDAD
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridCIUDAD').store.reload();
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
        }]
        ,
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
                idCIUDADSeleccionadoID_CIUDAD = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarCIUDAD") == undefined)) {
                    idCIUDADSeleccionadoID_CIUDAD = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarCIUDAD().show();
                }
            }
        }
    });return gridCIUDAD;
}
function pantallaModificarCIUDAD() {
    var comboDEPARTAMENTO =getCombo('DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DESCRIPCION_DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DESCRIPCION_DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO');
    comboDEPARTAMENTO.allowBlank = false;
    comboDEPARTAMENTO.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var ciudadModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarCIUDAD',
        labelWidth : 120,
        frame : true,
        autoWidth : true,
        monitorValid : true,
        items: [{
            fieldLabel:'ID CIUDAD',
            name:'ID_CIUDAD',
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
                items: [comboDEPARTAMENTO]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarDEPARTAMENTO().show();
                        }
                    }]
                }]
            }]
        }],

        buttons : [{
            id : 'btnmodificarCIUDAD',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idCIUDADSeleccionadoID_CIUDAD!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                ciudadModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'CIUDAD?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarCIUDAD').close();Ext.getCmp('gridCIUDAD').store.reload();
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
                Ext.getCmp('modificarCIUDAD').close();
            }
        }]
    });ciudadModificarFormPanel.getForm().load({
        url : 'CIUDAD?op=DETALLE',
        method : 'POST',
        params:{
            ID_CIUDAD: idCIUDADSeleccionadoID_CIUDAD
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar CIUDAD',
        id : 'modificarCIUDAD',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [ciudadModificarFormPanel]
    });return win;
}
function pantallaAgregarCIUDAD() {
    var comboDEPARTAMENTO =getCombo('DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DESCRIPCION_DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO','DESCRIPCION_DEPARTAMENTO','DEPARTAMENTO','DEPARTAMENTO');
    comboDEPARTAMENTO.allowBlank = false;
    var ciudadAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarCIUDAD',
        labelWidth : 120,
        frame : true,
        autoWidth : true,
        monitorValid : true,
        items: [{
            fieldLabel:'ID CIUDAD',
            name:'ID_CIUDAD',
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
                items: [comboDEPARTAMENTO]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarDEPARTAMENTO().show();
                        }
                    }]
                }]
            }]
        }],
        buttons : [{
            id : 'btnAgregarCIUDAD',
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
                            ciudadAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'CIUDAD?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarCIUDAD').close();Ext.getCmp('gridCIUDAD').store.reload();
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
                Ext.getCmp('agregarCIUDAD').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar CIUDAD',
        id : 'agregarCIUDAD',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [ciudadAgregarFormPanel]
    });return win;
}