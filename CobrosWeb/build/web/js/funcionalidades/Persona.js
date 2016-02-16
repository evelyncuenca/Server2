var idPERSONASeleccionadoID_PERSONA ;
function gridPERSONA(){

    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'PERSONA?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'PERSONA',
            totalProperty : 'TOTAL',
            id : 'ID_PERSONA',
            fields : [ 'ID_PERSONA','NOMBRES','APELLIDOS','DIRECCION','TELEFONO','RUC_CI','LOCALIDAD' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });var anchoDefaultColumnas= 10;var colModel = new Ext.grid.ColumnModel([ {
        header:'NOMBRES',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRES'
    },{
        header:'APELLIDOS',
        width: anchoDefaultColumnas,
        dataIndex: 'APELLIDOS'
    },{
        header:'DIRECCIÓN',
        width: anchoDefaultColumnas,
        dataIndex: 'DIRECCION'
    },{
        header:'TELÉFONO',
        width: anchoDefaultColumnas,
        dataIndex: 'TELEFONO'
    },{
        header:'RUC CI',
        width: anchoDefaultColumnas,
        dataIndex: 'RUC_CI'
    },{
        header:'LOCALIDAD',
        width: anchoDefaultColumnas,
        dataIndex: 'LOCALIDAD'
    } ]);var gridPERSONA = new Ext.grid.GridPanel({
        title:'PERSONA',
        id:'gridPERSONA',
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
                if(Ext.getCmp('agregarPERSONA') == undefined) pantallaAgregarPERSONA().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idPERSONASeleccionadoID_PERSONA!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'PERSONA?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_PERSONA : idPERSONASeleccionadoID_PERSONA
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridPERSONA').store.reload();
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
                idPERSONASeleccionadoID_PERSONA = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarPERSONA") == undefined)) {
                    idPERSONASeleccionadoID_PERSONA = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarPERSONA().show();
                }
            }
        }
    });return gridPERSONA;
}
function pantallaModificarPERSONA() {
    var comboLOCALIDAD=getCombo('LOCALIDAD','LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    comboLOCALIDAD.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var personaModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarPERSONA',
        labelWidth : 80,
        frame : true,
        autoWidth : true,

        monitorValid : true,
        items: [{
            fieldLabel:'ID PERSONA',
            name:'ID_PERSONA',
            inputType:'hidden',
            xtype:'textfield'
        },{
            fieldLabel:'NOMBRES',
            name:'NOMBRES',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'APELLIDOS',
            name:'APELLIDOS',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'DIRECCIÓN',
            name:'DIRECCION',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'TELÉFONO',
            name:'TELEFONO',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'RUC CI',
            name:'RUC_CI',
            xtype:'textfield',
            allowBlank : false
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboLOCALIDAD]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarLOCALIDAD().show();
                        }
                    }]
                }]
            }]
        }],

        buttons : [{
            id : 'btnmodificarPERSONA',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idPERSONASeleccionadoID_PERSONA!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                personaModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'PERSONA?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarPERSONA').close();Ext.getCmp('gridPERSONA').store.reload();
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
                Ext.getCmp('modificarPERSONA').close();
            }
        }]
    });personaModificarFormPanel.getForm().load({
        url : 'PERSONA?op=DETALLE',
        method : 'POST',
        params:{
            ID_PERSONA: idPERSONASeleccionadoID_PERSONA
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar PERSONA',
        id : 'modificarPERSONA',
        width : 300,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [personaModificarFormPanel]
    });return win;
}
function pantallaAgregarPERSONA() {
    var comboLOCALIDAD=getCombo('LOCALIDAD','LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    var personaAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarPERSONA',
        labelWidth : 80,
        frame : true,
        autoWidth : true,

        monitorValid : true,
        items: [{
            fieldLabel:'ID PERSONA',
            name:'ID_PERSONA',
            inputType:'hidden',
            xtype:'textfield'
        },{
            fieldLabel:'NOMBRES',
            name:'NOMBRES',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'APELLIDOS',
            name:'APELLIDOS',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'DIRECCIÓN',
            name:'DIRECCION',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'TELÉFONO',
            name:'TELEFONO',
            xtype:'textfield',
            allowBlank : false
        },{
            fieldLabel:'RUC CI',
            name:'RUC_CI',
            xtype:'textfield',
            allowBlank : false
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboLOCALIDAD]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarLOCALIDAD().show();
                        }
                    }]
                }]
            }]
        }],
        buttons : [{
            id : 'btnAgregarPERSONA',
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
                            personaAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'PERSONA?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarPERSONA').close();Ext.getCmp('idFormPanelAgregarUSUARIO').store.reload();
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
                Ext.getCmp('agregarPERSONA').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar PERSONA',
        id : 'agregarPERSONA',
        width : 300,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items : [personaAgregarFormPanel]
    });return win;
}