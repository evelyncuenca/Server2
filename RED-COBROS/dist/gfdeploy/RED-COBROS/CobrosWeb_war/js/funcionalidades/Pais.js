var idPAISSeleccionadoID_PAIS ;
function gridPAIS(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'PAIS?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'PAIS',
            totalProperty : 'TOTAL',
            id : 'ID_PAIS',
            fields : [ 'ID_PAIS','NOMBRE' ]
        })
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([ {
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    } ]);var gridPAIS = new Ext.grid.GridPanel({
        title:'PAIS',
        id:'gridPAIS',
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
                if(Ext.getCmp('agregarPAIS') == undefined) pantallaAgregarPAIS().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idPAISSeleccionadoID_PAIS!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'PAIS?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_PAIS : idPAISSeleccionadoID_PAIS
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridPAIS').store.reload();
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
                idPAISSeleccionadoID_PAIS = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarPAIS") == undefined)) {
                    idPAISSeleccionadoID_PAIS = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarPAIS().show();
                }
            }
        }
    });return gridPAIS;
}
function pantallaModificarPAIS() {

    var paisModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarPAIS',
        labelWidth : 80,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'ID PAIS',
            name:'ID_PAIS',
            inputType:'hidden'
        },{
            fieldLabel:'NOMBRE',
            name:'NOMBRE',
            allowBlank : false
        } ],
        buttons : [{
            id : 'btnmodificarPAIS',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idPAISSeleccionadoID_PAIS!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                paisModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'PAIS?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarPAIS').close();Ext.getCmp('gridPAIS').store.reload();
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
                Ext.getCmp('modificarPAIS').close();
            }
        }]
    });paisModificarFormPanel.getForm().load({
        url : 'PAIS?op=DETALLE',
        method : 'POST',
        params:{
            ID_PAIS: idPAISSeleccionadoID_PAIS
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar PAIS',
        id : 'modificarPAIS',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [paisModificarFormPanel]
    });return win;
}
function pantallaAgregarPAIS() {
    var paisAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarPAIS',
        labelWidth : 80,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'NOMBRE',
            name:'NOMBRE',
            allowBlank : false
        } ],
        buttons : [{
            id : 'btnAgregarPAIS',
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
                            paisAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'PAIS?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarPAIS').close();Ext.getCmp('gridPAIS').store.reload();
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
                Ext.getCmp('agregarPAIS').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar PAIS',
        id : 'agregarPAIS',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [paisAgregarFormPanel]
    });return win;
}


