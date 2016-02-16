var idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB ;
function gridFRANJA_HORARIA_CAB(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'FRANJA_HORARIA_CAB?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'FRANJA_HORARIA_CAB',
            totalProperty : 'TOTAL',
            id : 'ID_FRANJA_HORARIA_CAB',
            fields : [ 'ID_FRANJA_HORARIA_CAB','DESCRIPCION','COMENTARIO','RECAUDADOR' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([ {
        header:'DESCRIPCIÓN',
        width: anchoDefaultColumnas,
        dataIndex: 'DESCRIPCION'
    },{
        header:'COMENTARIO',
        width: anchoDefaultColumnas,
        dataIndex: 'COMENTARIO'
    },{
        header:'RECAUDADOR',
        width: anchoDefaultColumnas,
        dataIndex: 'RECAUDADOR'
    } ]);
    var gridFRANJA_HORARIA_CAB = new Ext.grid.GridPanel({
        title:'FRANJA HORARIA',
        id:'gridFRANJA_HORARIA_CAB',
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
                if(Ext.getCmp('agregarFRANJA_HORARIA_CAB') == undefined) pantallaAgregarFRANJA_HORARIA_CAB().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB!=undefined){
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
                                    url : 'FRANJA_HORARIA_CAB?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_FRANJA_HORARIA_CAB : idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
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
                                            Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
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
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB = esteObjeto.getStore().getAt(rowIndex).id;
                Ext.getCmp('gridFRANJA_HORARIA_DET').store.baseParams['FRANJA_HORARIA_CAB'] = esteObjeto.getStore().getAt(rowIndex).id ;
                Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
                Ext.getCmp('idBtnAgregarFranjaHorariaDet').enable();
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                Ext.getCmp('idBtnAgregarFranjaHorariaDet').enable();
                if ( (Ext.getCmp("modificarFRANJA_HORARIA_CAB") == undefined)) {
                    idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarFRANJA_HORARIA_CAB().show();
                }
            }
        }
    });
    return gridFRANJA_HORARIA_CAB;
}
function pantallaModificarFRANJA_HORARIA_CAB() {

    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    comboRECAUDADOR.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var franja_horaria_cabModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarFRANJA_HORARIA_CAB',
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'ID FRANJA HORARIA CAB',
            name:'ID_FRANJA_HORARIA_CAB',
            inputType:'hidden'
        },{
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false
        },{
            fieldLabel:'COMENTARIO',
            name:'COMENTARIO'
        },comboRECAUDADOR],
        buttons : [{

            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {

                                franja_horaria_cabModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'FRANJA_HORARIA_CAB?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    //params:{RECAUDADOR:comboRECAUDADOR},
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarFRANJA_HORARIA_CAB').close();
                                        Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
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
                Ext.getCmp('modificarFRANJA_HORARIA_CAB').close();
            }
        }]
    });
    franja_horaria_cabModificarFormPanel.getForm().load({
        url : 'FRANJA_HORARIA_CAB?op=DETALLE',
        method : 'POST',
        params:{
            ID_FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar FRANJA HORARIA',
        id : 'modificarFRANJA_HORARIA_CAB',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [franja_horaria_cabModificarFormPanel]
    });
    return win;
}
function pantallaAgregarFRANJA_HORARIA_CAB() {

    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var franja_horaria_cabAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarFRANJA_HORARIA_CAB',
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false
        },{
            fieldLabel:'COMENTARIO',
            name:'COMENTARIO'
        },comboRECAUDADOR ],
        buttons : [{
            id : 'btnAgregarFRANJA_HORARIA_CAB',
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
                            franja_horaria_cabAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'FRANJA_HORARIA_CAB?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarFRANJA_HORARIA_CAB').close();
                                    Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
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
                Ext.getCmp('agregarFRANJA_HORARIA_CAB').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar FRANJA HORARIA',
        id : 'agregarFRANJA_HORARIA_CAB',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [franja_horaria_cabAgregarFormPanel]
    });
    return win;
}

var idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET ;
function gridFRANJA_HORARIA_DET(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'FRANJA_HORARIA_DET?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'FRANJA_HORARIA_DET',
            totalProperty : 'TOTAL',
            id : 'ID_FRANJA_HORARIA_DET',
            fields : [ 'ID_FRANJA_HORARIA_DET','DIA','HORA_INI','HORA_FIN' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([ {
        header:'DIA',
        width: anchoDefaultColumnas,
        dataIndex: 'DIA'
    },{
        header:'HORA INI',
        width: anchoDefaultColumnas,
        dataIndex: 'HORA_INI'
    },{
        header:'HORA FIN',
        width: anchoDefaultColumnas,
        dataIndex: 'HORA_FIN'
    } ]);
    var gridFRANJA_HORARIA_DET = new Ext.grid.GridPanel({
        title:'FRANJA HORARIA DETALLE',
        id:'gridFRANJA_HORARIA_DET',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
        tbar:[{
            id:'idBtnAgregarFranjaHorariaDet',
            text:'Agregar',
            disabled:true,
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarFRANJA_HORARIA_DET') == undefined) pantallaAgregarFRANJA_HORARIA_DET().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET!=undefined){
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
                                    url : 'FRANJA_HORARIA_DET?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_FRANJA_HORARIA_DET : idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET
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
                                            Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
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
                idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarFRANJA_HORARIA_DET") == undefined)) {
                    idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarFRANJA_HORARIA_DET().show();
                }
            }
        }
    });
    return gridFRANJA_HORARIA_DET;
}
function pantallaModificarFRANJA_HORARIA_DET() {
    var comboDays =  new Ext.form.ComboBox({
        fieldLabel: 'Dias',
        hiddenName : 'DIA',
        valueField : 'DIA',
        emptyText: 'Dias de la semana',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['DIA', 'descripcion'],
            data : [['1', 'Domingo'],
            ['2',  'Lunes'],
            ['3', 'Martes'],
            ['4', 'Miercoles'],
            ['5', 'Jueves'],
            ['6', 'Viernes'],
            ['7', 'Sabado']]
        })
    });


    var franja_horaria_detModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarFRANJA_HORARIA_DET',
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'ID FRANJA HORARIA DET',
            name:'ID_FRANJA_HORARIA_DET',
            inputType:'hidden'
        },{
            xtype:'timefield',
            format:'H:i:s',
            anchor : '95%',
            fieldLabel:'HORA INI',
            name:'HORA_INI',
            minValue: '7:00',
            maxValue: '20:00',
            increment:30,
            allowBlank : false
        },{
            xtype:'timefield',
            format:'H:i:s',
            anchor : '95%',
            fieldLabel:'HORA FIN',
            minValue: '7:00',
            maxValue: '20:00',
            name:'HORA_FIN',
            increment:30,
            allowBlank : false
        },comboDays ],
        buttons : [{
            id : 'btnmodificarFRANJA_HORARIA_DET',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                franja_horaria_detModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    timeout : TIME_OUT_AJAX,
                                    params:{
                                        FRANJA_HORARIA_CAB:idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
                                    },
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'FRANJA_HORARIA_DET?op=MODIFICAR',
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarFRANJA_HORARIA_DET').close();
                                        Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
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
                Ext.getCmp('modificarFRANJA_HORARIA_DET').close();
            }
        }]
    });
    franja_horaria_detModificarFormPanel.getForm().load({
        url : 'FRANJA_HORARIA_DET?op=DETALLE',
        method : 'POST',
        params:{
            ID_FRANJA_HORARIA_DET: idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar FRANJA HORARIA DETALLE',
        id : 'modificarFRANJA_HORARIA_DET',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [franja_horaria_detModificarFormPanel]
    });
    return win;
}
function pantallaAgregarFRANJA_HORARIA_DET() {
    var comboDays =  new Ext.form.ComboBox({
        fieldLabel: 'Dias',
        hiddenName : 'DIA',
        valueField : 'DIA',
        emptyText: 'Dias de la semana',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['DIA', 'descripcion'],
            data : [['1', 'Domingo'],
            ['2',  'Lunes'],
            ['3', 'Martes'],
            ['4', 'Miercoles'],
            ['5', 'Jueves'],
            ['6', 'Viernes'],
            ['7', 'Sabado']]
        })
    });

    var franja_horaria_detAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarFRANJA_HORARIA_DET',
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [{
            xtype:'timefield',
            format:'H:i:s',
            anchor : '95%',
            fieldLabel:'HORA INI',
            name:'HORA_INI',
            allowBlank : false
        },{
            xtype:'timefield',
            format:'H:i:s',
            anchor : '95%',
            fieldLabel:'HORA FIN',
            name:'HORA_FIN',
            allowBlank : false
        },comboDays ],
        buttons : [{
            id : 'btnAgregarFRANJA_HORARIA_DET',
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
                            franja_horaria_detAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                timeout : TIME_OUT_AJAX,
                                params:{
                                    FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
                                },
                                waitMsg : 'Asignando...',
                                url : 'FRANJA_HORARIA_DET?op=AGREGAR',
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarFRANJA_HORARIA_DET').close();
                                    Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
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
                Ext.getCmp('agregarFRANJA_HORARIA_DET').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar FRANJA HORARIA DETALLE',
        id : 'agregarFRANJA_HORARIA_DET',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [franja_horaria_detAgregarFormPanel]
    });
    return win;
}



function panelFranjaHoraria(){

    var gridCabecera = gridFRANJA_HORARIA_CAB();
    var gridDetalle = gridFRANJA_HORARIA_DET();

    var panel = {
        id : 'idPanelFranjaHoraria',
        title:'Franja Horaria',
        xtype : 'panel',
        layout   : 'border',
        border : false,
        autoScroll : true ,
        items: [{
            collapsible: false,
            region:'center',
            layout:'fit',
            margins: '5 0 0 0',
            autoScroll : true ,
            items:[gridCabecera]
        },{
            collapsible: false,
            layout:'fit',
            region:'south',
            margins: '5 0 0 0',
            autoScroll : true,
            height: 250,
            split:true,
            minSize: 75,
            maxSize: 350,
            items:[gridDetalle]
        }]
    }
    return panel;

}