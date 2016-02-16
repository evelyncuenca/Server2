var idLOCALIDADSeleccionadoID_LOCALIDAD ;
function gridLOCALIDAD(){

    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'LOCALIDAD?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'LOCALIDAD',
            totalProperty : 'TOTAL',
            id : 'ID_LOCALIDAD',
            fields : [ 'ID_LOCALIDAD','CIUDAD','NOMBRE' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });var anchoDefaultColumnas= 10;var colModel = new Ext.grid.ColumnModel([{
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    },{
        header:'CIUDAD',
        width: anchoDefaultColumnas,
        dataIndex: 'CIUDAD'
    } ]);var gridLOCALIDAD = new Ext.grid.GridPanel({
        title:'LOCALIDAD',
        id:'gridLOCALIDAD',
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
                if(Ext.getCmp('agregarLOCALIDAD') == undefined) pantallaAgregarLOCALIDAD().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idLOCALIDADSeleccionadoID_LOCALIDAD!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'LOCALIDAD?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_LOCALIDAD : idLOCALIDADSeleccionadoID_LOCALIDAD
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridLOCALIDAD').store.reload();
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
                idLOCALIDADSeleccionadoID_LOCALIDAD = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarLOCALIDAD") == undefined)) {
                    idLOCALIDADSeleccionadoID_LOCALIDAD = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarLOCALIDAD().show();
                }
            }
        }
    });return gridLOCALIDAD;
}
function pantallaModificarLOCALIDAD() {
    var comboCIUDAD =getCombo('CIUDAD','CIUDAD','CIUDAD','CIUDAD','DESCRIPCION_CIUDAD','CIUDAD','CIUDAD','DESCRIPCION_CIUDAD','CIUDAD','CIUDAD');
    comboCIUDAD.allowBlank = false;
    comboCIUDAD.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var localidadModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarLOCALIDAD',
        labelWidth : 80,
        frame : true,
        autoWidth : true,
        monitorValid : true,
        items: [{
            fieldLabel:'ID LOCALIDAD',
            name:'ID_LOCALIDAD',
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
                items: [comboCIUDAD]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarCIUDAD().show();
                        }
                    }]
                }]
            }]
        }]
        ,
        buttons : [{
            id : 'btnmodificarLOCALIDAD',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idLOCALIDADSeleccionadoID_LOCALIDAD!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                localidadModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'LOCALIDAD?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarLOCALIDAD').close();Ext.getCmp('gridLOCALIDAD').store.reload();
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
                Ext.getCmp('modificarLOCALIDAD').close();
            }
        }]
    });localidadModificarFormPanel.getForm().load({
        url : 'LOCALIDAD?op=DETALLE',
        method : 'POST',
        params:{
            ID_LOCALIDAD: idLOCALIDADSeleccionadoID_LOCALIDAD
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar LOCALIDAD',
        id : 'modificarLOCALIDAD',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [localidadModificarFormPanel]
    });return win;
}
function pantallaAgregarLOCALIDAD() {
    var comboCIUDAD =getCombo('CIUDAD','CIUDAD','CIUDAD','CIUDAD','DESCRIPCION_CIUDAD','CIUDAD','CIUDAD','DESCRIPCION_CIUDAD','CIUDAD','CIUDAD');
    comboCIUDAD.allowBlank = false;
    var localidadAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarLOCALIDAD',
        labelWidth : 80,
        frame : true,
        autoWidth : true,
        monitorValid : true,
        items: [{
            fieldLabel:'ID LOCALIDAD',
            name:'ID_LOCALIDAD',
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
                items: [comboCIUDAD]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarCIUDAD().show();
                        }
                    }]
                }]
            }]
        }],
        buttons : [{
            id : 'btnAgregarLOCALIDAD',
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
                            localidadAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'LOCALIDAD?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarLOCALIDAD').close();Ext.getCmp('gridLOCALIDAD').store.reload();
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
                Ext.getCmp('agregarLOCALIDAD').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar LOCALIDAD',
        id : 'agregarLOCALIDAD',
        width : 300,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items : [localidadAgregarFormPanel]
    });return win;
}



