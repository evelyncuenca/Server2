
Ext.onReady(function () {
    overrideExtDate();
    Ext.MessageBox.buttonText.yes = "OK";
    Ext.MessageBox.buttonText.cancel = "Cerrar";
    Ext.QuickTips.init();
    tabOperaciones();
});
/**********************T.USUARIO_TERMINAL****************************/
var idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL;

function overrideExtDate() {
    Ext.DatePicker.override({
        update: Ext.DatePicker.prototype.update.createSequence(function () {
            var w = 190;
            this.el.setWidth(w + this.el.getBorderWidth("lr"));
            Ext.fly(this.el.dom.firstChild).setWidth(w);
        })
    });
}
function gridUSUARIO_TERMINAL() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'USUARIO_TERMINAL?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'USUARIO_TERMINAL',
            totalProperty: 'TOTAL',
            id: 'ID_USUARIO_TERMINAL',
            fields: ['ID_USUARIO_TERMINAL', 'FRANJA_HORARIA_CAB', 'USUARIO', 'TERMINAL', 'USUARIO', 'LOGUEADO', 'RECAUDADOR', 'SUCURSAL']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'USUARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'USUARIO'
        }, {
            header: 'TERMINAL',
            width: anchoDefaultColumnas,
            dataIndex: 'TERMINAL'
        }, {
            header: 'RECAUDADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'RECAUDADOR'
        }, {
            header: 'SUCURSAL',
            width: anchoDefaultColumnas,
            dataIndex: 'SUCURSAL'
        }, {
            header: 'FRANJA HORARIA',
            width: anchoDefaultColumnas,
            dataIndex: 'FRANJA_HORARIA_CAB'
        }, {
            header: 'LOGUEADO',
            width: anchoDefaultColumnas,
            dataIndex: 'LOGUEADO'
        }]);
    var gridUSUARIO_TERMINAL = new Ext.grid.GridPanel({
        title: 'USUARIO TERMINAL',
        id: 'gridUSUARIO_TERMINAL',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarUSUARIO_TERMINAL') == undefined)
                        pantallaAgregarUSUARIO_TERMINAL().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'USUARIO_TERMINAL?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_USUARIO_TERMINAL: idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: 'Estado',
                                                msg: 'No se pudo realizar la operación.',
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Usuario',
                id: 'idBusquedaUsarioTerminal',
                xtype: 'textfield',
                emptyText: 'Usuario..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridUSUARIO_TERMINAL').store.load({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    USUARIO: esteObjeto.getValue()
                                }
                            });

                        }
                    }
                }
            }, {
                text: 'Terminal',
                id: 'idBusquedaUsTerminal',
                xtype: 'textfield',
                emptyText: 'Terminal..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridUSUARIO_TERMINAL').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    TERMINAL: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarUSUARIO_TERMINAL") == undefined)) {
                    idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarUSUARIO_TERMINAL().show();
                }
            }
        }
    });
    return gridUSUARIO_TERMINAL;
}
function pantallaModificarUSUARIO_TERMINAL() {
    var comboUSUARIO = getCombo('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO');
    var comboTERMINAL = getCombo('TERMINAL', 'TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL');
    var comboFRANJA_HORARIA_CAB = getCombo('FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'DESCRIPCION_FRANJA_HORARIA_CAB', 'FRANJA HORARIA', 'FRANJA_HORARIA_CAB', 'DESCRIPCION_FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA HORARIA');
    comboTERMINAL.listWidth = 300;
    comboUSUARIO.allowBlank = false;
    comboTERMINAL.allowBlank = false;
    comboFRANJA_HORARIA_CAB.allowBlank = false;

    var logueado = new Ext.form.Checkbox({
        fieldLabel: 'LOGUEADO',
        vertical: true,
        columns: 1,
        name: 'LOGUEADO',
        items: [{
                boxLabel: 'LOGUEADO'
            }]
    });
    comboTERMINAL.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboUSUARIO.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboFRANJA_HORARIA_CAB.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboUSUARIO.addListener('select', function (esteCombo, record, index) {
        comboTERMINAL.store.baseParams['USUARIO'] = record.data.USUARIO;
        comboTERMINAL.store.reload();
        comboTERMINAL.enable();

    }, null, null);
    comboTERMINAL.addListener('select', function (esteCombo, record, index) {

        comboFRANJA_HORARIA_CAB.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboFRANJA_HORARIA_CAB.store.reload();
        comboFRANJA_HORARIA_CAB.enable();
    }, null, null);
    var usuario_terminalModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarUSUARIO_TERMINAL',
        labelAlign: 'left',
        labelWidth: 120,
        width: 'auto',
        monitorValid: true,
        frame: true,
        items: [{
                fieldLabel: 'ID USUARIO TERMINAL',
                name: 'ID_USUARIO_TERMINAL',
                xtype: 'textfield',
                inputType: 'hidden'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboUSUARIO, comboTERMINAL, comboFRANJA_HORARIA_CAB]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarUSUARIO().show();
                                        }
                                    }]
                            }, {
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarTERMINAL().show();

                                        }
                                    }]
                            }]
                    }]
            }, logueado],
        buttons: [{
                id: 'btnmodificarUSUARIO_TERMINAL',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    usuario_terminalModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'USUARIO_TERMINAL?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarUSUARIO_TERMINAL').close();
                                            Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarUSUARIO_TERMINAL').close();
                }
            }]
    });

    usuario_terminalModificarFormPanel.getForm().load({
        url: 'USUARIO_TERMINAL?op=DETALLE',
        method: 'POST',
        params: {
            ID_USUARIO_TERMINAL: idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL
        },
        waitMsg: 'Cargando...'
    });

    var win = new Ext.Window({
        title: 'Modificar USUARIO TERMINAL',
        id: 'modificarUSUARIO_TERMINAL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [usuario_terminalModificarFormPanel]
    });
    return win;
}
function pantallaAgregarUSUARIO_TERMINAL() {

    var comboTERMINAL = getCombo('TERMINAL', 'TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL');
    var comboFRANJA_HORARIA_CAB = getCombo('FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'DESCRIPCION_FRANJA_HORARIA_CAB', 'FRANJA HORARIA', 'FRANJA_HORARIA_CAB', 'DESCRIPCION_FRANJA_HORARIA_CAB', 'FRANJA_HORARIA_CAB', 'FRANJA HORARIA');
    var comboUSUARIO = getCombo('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO');

    comboUSUARIO.allowBlank = false;
    comboTERMINAL.allowBlank = false;
    comboFRANJA_HORARIA_CAB.allowBlank = false;

    comboTERMINAL.disable();
    comboFRANJA_HORARIA_CAB.disable();
    comboUSUARIO.addListener('select', function (esteCombo, record, index) {
        comboTERMINAL.store.baseParams['USUARIO'] = record.data.USUARIO;
        comboTERMINAL.store.reload();
        comboTERMINAL.enable();
    }, null, null);
    comboTERMINAL.addListener('select', function (esteCombo, record, index) {
        comboFRANJA_HORARIA_CAB.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboFRANJA_HORARIA_CAB.store.reload();
        comboFRANJA_HORARIA_CAB.enable();
    }, null, null);
    var usuario_terminalAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarUSUARIO_TERMINAL',
        labelAlign: 'left',
        labelWidth: 120,
        width: 'auto',
        monitorValid: true,
        frame: true,
        items: [{
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboUSUARIO, comboTERMINAL, comboFRANJA_HORARIA_CAB]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarUSUARIO().show();
                                        }
                                    }]
                            }, {
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
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
        buttons: [{
                id: 'btnAgregarUSUARIO_TERMINAL',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                usuario_terminalAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'USUARIO_TERMINAL?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarUSUARIO_TERMINAL').close();
                                        Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarUSUARIO_TERMINAL').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar USUARIO TERMINAL',
        id: 'agregarUSUARIO_TERMINAL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [usuario_terminalAgregarFormPanel]
    });
    return win;
}
/**********************T.USUARIO****************************/
var idUSUARIOSeleccionadoID_USUARIO;
function gridUSUARIO() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'USUARIO?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'USUARIO',
            totalProperty: 'TOTAL',
            id: 'ID_USUARIO',
            fields: ['ES_SUPERVISOR', 'CODIGO', 'CODIGO_EXTERNO', 'ID_USUARIO', 'PERSONA', 'NOMBRE_USUARIO', 'PERSONA', 'TELEFONO_PERSONA', 'RECAUDADOR']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓDIGO',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO'
        }, {
            header: 'CÓD. EXTERNO',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO_EXTERNO'
        }, {
            header: 'NOMBRE USUARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE_USUARIO'
        }, {
            header: 'RECAUDADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'RECAUDADOR'
        }, {
            header: 'PERSONA',
            width: anchoDefaultColumnas,
            dataIndex: 'PERSONA'
        }, {
            header: 'TELÉFONO PERSONA',
            width: anchoDefaultColumnas,
            dataIndex: 'TELEFONO_PERSONA'
        }, {
            header: 'ES SUPERVISOR',
            width: anchoDefaultColumnas,
            dataIndex: 'ES_SUPERVISOR'
        }]);
    var btnAsignarSupervisor = new Ext.Button({
        text: 'Asignar Supervisor',
        id: 'iBtnAsignarSupervisor',
        tooltip: 'Asigna Supervisor al usuario',
        iconCls: 'add',
        disabled: true,
        handler: function () {
            if (Ext.getCmp('idAsignacionSupervisores') == undefined) {

                itemSelectorSupervisores(idUSUARIOSeleccionadoID_USUARIO).show();

            }
        }
    });
    var gridUSUARIO = new Ext.grid.GridPanel({
        title: 'USUARIO',
        id: 'gridUSUARIO',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarUSUARIO') == undefined)
                        pantallaAgregarUSUARIO().show();
                }
            }, btnAsignarSupervisor, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idUSUARIOSeleccionadoID_USUARIO != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'USUARIO?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_USUARIO: idUSUARIOSeleccionadoID_USUARIO
                                        },
                                        success: function (action) {
                                            var obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridUSUARIO').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.ERROR,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Usuario',
                id: 'idBusquedaUsario',
                xtype: 'textfield',
                emptyText: 'Usuario..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridUSUARIO').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    NOMBRE_USUARIO: esteObjeto.getValue()
                                }
                            });

                        }
                    }
                }
            }, {
                text: 'Persona',
                id: 'idBusquedaPersona',
                xtype: 'textfield',
                emptyText: 'Persona..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridUSUARIO').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    PERSONA: esteObjeto.getValue()
                                }
                            });

                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idUSUARIOSeleccionadoID_USUARIO = esteObjeto.getStore().getAt(rowIndex).id;

                if (esteObjeto.getStore().getAt(rowIndex).data.ES_SUPERVISOR == "N") {
                    btnAsignarSupervisor.enable();

                } else {
                    btnAsignarSupervisor.disable();
                }


            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if (esteObjeto.getStore().getAt(rowIndex).data.ES_SUPERVISOR == "N") {
                    btnAsignarSupervisor.enable();

                } else {
                    btnAsignarSupervisor.disable();
                }
                if ((Ext.getCmp("modificarUSUARIO") == undefined)) {
                    idUSUARIOSeleccionadoID_USUARIO = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarUSUARIO().show();
                }
            }
        }
    });
    return gridUSUARIO;
}
function pantallaModificarUSUARIO() {
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    comboPERSONA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboRECAUDADOR.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var esSupervisor = new Ext.form.Checkbox({
        fieldLabel: 'ES SUPERVISOR',
        vertical: true,
        columns: 1,
        name: 'ES_SUPERVISOR',
        items: [
            {
                boxLabel: 'ES SUPERVISOR',
                inputValue: 'S'
            }

        ]
    });
    var usuarioModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarUSUARIO',
        labelWidth: 120,
        labelAlign: 'left',
        width: 'auto',
        monitorValid: true,
        frame: true,
        items: [{
                fieldLabel: 'ID USUARIO',
                name: 'ID_USUARIO',
                xtype: 'textfield',
                inputType: 'hidden'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                allowBlank: false,
                anchor: '95%'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'CÓD. EXTERNO',
                name: 'CODIGO_EXTERNO',
                allowBlank: true,
                anchor: '95%'
            }, {
                fieldLabel: 'NOMBRE USUARIO',
                name: 'NOMBRE_USUARIO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRECAUDADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaModificarRECAUDADOR().show();

                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'CONTRASENHA',
                name: 'CONTRASENHA',
                xtype: 'textfield',
                allowBlank: true,
                inputType: 'password'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();

                                        }
                                    }]
                            }]
                    }]
            }, esSupervisor],
        buttons: [{
                id: 'btnmodificarUSUARIO',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idUSUARIOSeleccionadoID_USUARIO != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    usuarioModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'USUARIO?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarUSUARIO').close();
                                            Ext.getCmp('gridUSUARIO').store.reload();
                                        },
                                        failure: function (form, action) {
                                            var obj = Ext.util.JSON.decode(action.response.responseText);

                                            if (obj.motivo != undefined) {
                                                Ext.Msg.show({
                                                    title: FAIL_TITULO_GENERAL,
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            } else {
                                                Ext.Msg.show({
                                                    title: FAIL_TITULO_GENERAL,
                                                    msg: FAIL_CUERPO_GENERAL,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
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
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarUSUARIO').close();
                }
            }]
    });
    usuarioModificarFormPanel.getForm().load({
        url: 'USUARIO?op=DETALLE',
        method: 'POST',
        params: {
            ID_USUARIO: idUSUARIOSeleccionadoID_USUARIO
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar USUARIO',
        id: 'modificarUSUARIO',
        autoWidth: true,
        modal: true,
        height: 'auto',
        closable: false,
        resizable: false,
        items: [usuarioModificarFormPanel]
    });
    return win;
}
function pantallaAgregarUSUARIO() {
    var esSupervisor = new Ext.form.Checkbox({
        fieldLabel: 'ES SUPERVISOR',
        vertical: true,
        columns: 1,
        name: 'ES_SUPERVISOR',
        items: [
            {
                boxLabel: 'ES SUPERVISOR',
                inputValue: 'S'
            }

        ]
    });

    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    comboPERSONA.allowBlank = false;
    comboRECAUDADOR.allowBlank = false;
    var usuarioAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarUSUARIO',
        labelWidth: 120,
        labelAlign: 'left',
        width: 'auto',
        monitorValid: true,
        frame: true,
        items: [{
                fieldLabel: 'ID USUARIO',
                name: 'ID_USUARIO',
                xtype: 'textfield',
                inputType: 'hidden'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                allowBlank: false,
                anchor: '95%'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'CÓD. EXTERNO',
                name: 'CODIGO_EXTERNO',
                allowBlank: true,
                anchor: '95%'
            }, {
                fieldLabel: 'NOMBRE USUARIO',
                name: 'NOMBRE_USUARIO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRECAUDADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaModificarRECAUDADOR().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'CONTRASENHA',
                name: 'CONTRASENHA',
                xtype: 'textfield',
                allowBlank: false,
                inputType: 'password'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                bodyStyle: 'padding:4px 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();

                                        }
                                    }]
                            }]
                    }]
            }, esSupervisor],
        buttons: [{
                id: 'btnAgregarUSUARIO',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                usuarioAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'USUARIO?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarUSUARIO').close();
                                        Ext.getCmp('gridUSUARIO').store.reload();
                                    },
                                    failure: function (form, action) {
                                        var obj = Ext.util.JSON.decode(action.response.responseText);
                                        if (obj.motivo != undefined) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: obj.motivo,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        } else {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarUSUARIO').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar USUARIO',
        id: 'agregarUSUARIO',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [usuarioAgregarFormPanel]
    });
    return win;
}
/**********************T.FRANJA_HORARIA_CAB****************************/
var idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB;
function gridFRANJA_HORARIA_CAB() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'FRANJA_HORARIA_CAB?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'FRANJA_HORARIA_CAB',
            totalProperty: 'TOTAL',
            id: 'ID_FRANJA_HORARIA_CAB',
            fields: ['ID_FRANJA_HORARIA_CAB', 'DESCRIPCION', 'COMENTARIO', 'RECAUDADOR']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'COMENTARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'COMENTARIO'
        }, {
            header: 'RECAUDADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'RECAUDADOR'
        }]);
    var gridFRANJA_HORARIA_CAB = new Ext.grid.GridPanel({
        title: 'FRANJA HORARIA',
        id: 'gridFRANJA_HORARIA_CAB',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarFRANJA_HORARIA_CAB') == undefined)
                        pantallaAgregarFRANJA_HORARIA_CAB().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'FRANJA_HORARIA_CAB?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Recaudador',
                id: 'idBusquedaRecFranjaHoraria',
                xtype: 'textfield',
                emptyText: 'Recaudador..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    RECAUDADOR: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB = esteObjeto.getStore().getAt(rowIndex).id;
                Ext.getCmp('gridFRANJA_HORARIA_DET').store.baseParams['FRANJA_HORARIA_CAB'] = esteObjeto.getStore().getAt(rowIndex).id;
                Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
                Ext.getCmp('idBtnAgregarFranjaHorariaDet').enable();
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                Ext.getCmp('idBtnAgregarFranjaHorariaDet').enable();
                if ((Ext.getCmp("modificarFRANJA_HORARIA_CAB") == undefined)) {
                    idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarFRANJA_HORARIA_CAB().show();
                }
            }
        }
    });
    return gridFRANJA_HORARIA_CAB;
}
function pantallaModificarFRANJA_HORARIA_CAB() {

    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    comboRECAUDADOR.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var franja_horaria_cabModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarFRANJA_HORARIA_CAB',
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'ID FRANJA HORARIA CAB',
                name: 'ID_FRANJA_HORARIA_CAB',
                inputType: 'hidden'
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false
            }, {
                fieldLabel: 'COMENTARIO',
                name: 'COMENTARIO'
            }, comboRECAUDADOR],
        buttons: [{
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {

                                    franja_horaria_cabModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'FRANJA_HORARIA_CAB?op=MODIFICAR',
                                        //params:{RECAUDADOR:comboRECAUDADOR},
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarFRANJA_HORARIA_CAB').close();
                                            Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarFRANJA_HORARIA_CAB').close();
                }
            }]
    });
    franja_horaria_cabModificarFormPanel.getForm().load({
        url: 'FRANJA_HORARIA_CAB?op=DETALLE',
        method: 'POST',
        params: {
            ID_FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar FRANJA HORARIA',
        id: 'modificarFRANJA_HORARIA_CAB',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [franja_horaria_cabModificarFormPanel]
    });
    return win;
}
function pantallaAgregarFRANJA_HORARIA_CAB() {

    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var franja_horaria_cabAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarFRANJA_HORARIA_CAB',
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false
            }, {
                fieldLabel: 'COMENTARIO',
                name: 'COMENTARIO'
            }, comboRECAUDADOR],
        buttons: [{
                id: 'btnAgregarFRANJA_HORARIA_CAB',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                franja_horaria_cabAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'FRANJA_HORARIA_CAB?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarFRANJA_HORARIA_CAB').close();
                                        Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarFRANJA_HORARIA_CAB').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar FRANJA HORARIA',
        id: 'agregarFRANJA_HORARIA_CAB',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [franja_horaria_cabAgregarFormPanel]
    });
    return win;
}
/**********************T.SERVICIO****************************/
var idSERVICIOSeleccionadoID_SERVICIO;
function gridSERVICIO() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'SERVICIO?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'SERVICIO',
            totalProperty: 'TOTAL',
            id: 'ID_SERVICIO',
            fields: ['CODIGO', 'FACTURABLE', 'FACTURA_INCLUYE_COMISION', 'ID_SERVICIO', 'FACTURADOR', 'DESCRIPCION', 'COMENTARIO', 'TAMANHO_GRUPO', 'CODIGO']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓDIGO',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO'
        }, {
            header: 'FACTURADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'TAMANHO GRUPO',
            width: anchoDefaultColumnas,
            dataIndex: 'TAMANHO_GRUPO'
        }, {
            header: 'FACTURABLE',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURABLE'
        }, {
            header: 'FACT. INCL. COMISIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURA_INCLUYE_COMISION'
        }, {
            header: 'COMENTARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'COMENTARIO'
        }]);
    var gridSERVICIO = new Ext.grid.GridPanel({
        title: 'SERVICIO',
        id: 'gridSERVICIO',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        //        tbar:[{
        //            text:'Agregar',
        //            tooltip:'Agregar SERVICIO',
        //            iconCls:'add',
        //            handler:function(){
        //                if(Ext.getCmp('agregarSERVICIO') == undefined) pantallaAgregarSERVICIO().show();
        //            }
        //        },{
        //            text:'Quitar',
        //            tooltip:'Elimina SERVICIO',
        //            iconCls:'remove',
        //            handler: function(){
        //                if(idSERVICIOSeleccionadoID_SERVICIO!=undefined){
        //                    Ext.Msg.show({
        //                        title : 'Estado',
        //                        msg : 'Esta Seguro que Desea Eliminar el/la SERVICIO',
        //                        buttons : Ext.Msg.OKCANCEL,
        //                        animEl : 'elId',
        //                        icon : Ext.MessageBox.WARNING,
        //                        fn:function(btn){
        //                            if(btn=="ok"){
        //                                var conn = new Ext.data.Connection(); conn.request({
        //                                    url : 'SERVICIO?op=BORRAR',
        //                                    method : 'POST',
        //                                    params : {
        //                                        ID_SERVICIO : idSERVICIOSeleccionadoID_SERVICIO
        //                                    },
        //                                    success : function(action) {
        //                                        obj = Ext.util.JSON .decode(action.responseText); if(obj.success){
        //                                            Ext.Msg.show({
        //                                                title : 'Estado',
        //                                                msg : "Operacion Exitosa",
        //                                                buttons : Ext.Msg.OK,
        //                                                icon : Ext.MessageBox.INFO
        //                                            }); Ext.getCmp('gridSERVICIO').store.reload();
        //                                        }else{
        //                                            Ext.Msg.show({
        //                                                title : 'Estado',
        //                                                msg : obj.motivo,
        //                                                buttons : Ext.Msg.OK,
        //                                                icon : Ext.MessageBox.ERROR
        //                                            });
        //                                        }
        //                                    },
        //                                    failure : function(action) {
        //                                        Ext.Msg.show({
        //                                            title : 'Estado',
        //                                            msg : 'No se pudo realizar la operacion',
        //                                            buttons : Ext.Msg.OK,
        //                                            animEl : 'elId',
        //                                            icon : Ext.MessageBox.ERROR
        //                                        });
        //                                    }
        //                                });
        //                            }
        //                        }
        //                    });
        //                }
        //            }
        //        }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idSERVICIOSeleccionadoID_SERVICIO = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarSERVICIO") == undefined)) {
                    idSERVICIOSeleccionadoID_SERVICIO = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarSERVICIO().show();
                }
            }
        }
    });
    return gridSERVICIO;
}
function pantallaModificarSERVICIO() {
    var comboFACTURADOR = getCombo('FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR');
    var facturable = new Ext.form.Checkbox({
        fieldLabel: 'FACTURABLE',
        vertical: true,
        columns: 1,
        name: 'FACTURABLE',
        items: [{
                boxLabel: 'FACTURABLE'
            }]
    });
    var facturaIncluyeComision = new Ext.form.Checkbox({
        fieldLabel: 'FACT. INCL. COMISIÓN',
        vertical: true,
        columns: 1,
        name: 'FACTURA_INCLUYE_COMISION',
        items: [{
                boxLabel: 'FACT. INCL. COMISIÓN'
            }]
    });
    comboFACTURADOR.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var servicioModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarSERVICIO',
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                xtype: 'textfield',
                anchor: '95%'
            }, {
                fieldLabel: 'ID SERVICIO',
                name: 'ID_SERVICIO',
                inputType: 'hidden',
                xtype: 'textfield'

            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield',
                anchor: '95%'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'TAMANHO GRUPO',
                name: 'TAMANHO_GRUPO',
                allowBlank: false,
                anchor: '95%'
            }, {
                xtype: 'textarea',
                anchor: '95%',
                fieldLabel: 'COMENTARIO',
                name: 'COMENTARIO',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboFACTURADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarFACTURADOR().show();
                                        }
                                    }]
                            }]
                    }]
            }, facturable, facturaIncluyeComision],
        buttons: [{
                id: 'btnmodificarSERVICIO',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idSERVICIOSeleccionadoID_SERVICIO != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    servicioModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'SERVICIO?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarSERVICIO').close();
                                            Ext.getCmp('gridSERVICIO').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarSERVICIO').close();
                }
            }]
    });
    servicioModificarFormPanel.getForm().load({
        url: 'SERVICIO?op=DETALLE',
        method: 'POST',
        params: {
            ID_SERVICIO: idSERVICIOSeleccionadoID_SERVICIO
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar SERVICIO',
        id: 'modificarSERVICIO',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [servicioModificarFormPanel]
    });
    return win;
}
function pantallaAgregarSERVICIO() {
    var comboFACTURADOR = getCombo('FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR');
    var servicioAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarSERVICIO',
        labelWidth: 180,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                xtype: 'textfield'
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield',
                anchor: '95%'
            }, {
                xtype: 'numberfield',
                fieldLabel: 'TAMANHO GRUPO',
                name: 'TAMANHO_GRUPO',
                allowBlank: false,
                anchor: '95%'
            }, {
                xtype: 'textarea',
                anchor: '95%',
                fieldLabel: 'COMENTARIO',
                name: 'COMENTARIO',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboFACTURADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarFACTURADOR().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarSERVICIO',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                servicioAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'SERVICIO?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarSERVICIO').close();
                                        Ext.getCmp('gridSERVICIO').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarSERVICIO').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar SERVICIO',
        id: 'agregarSERVICIO',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [servicioAgregarFormPanel]
    });
    return win;
}
/**********************T.CIUDAD****************************/
var idCIUDADSeleccionadoID_CIUDAD;
function gridCIUDAD() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'CIUDAD?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'CIUDAD',
            totalProperty: 'TOTAL',
            id: 'ID_CIUDAD',
            fields: ['ID_CIUDAD', 'DEPARTAMENTO', 'NOMBRE']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRE',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE'
        }, {
            header: 'DEPARTAMENTO',
            width: anchoDefaultColumnas,
            dataIndex: 'DEPARTAMENTO'
        }]);
    var gridCIUDAD = new Ext.grid.GridPanel({
        title: 'CIUDAD',
        id: 'gridCIUDAD',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        }
        ,
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarCIUDAD') == undefined)
                        pantallaAgregarCIUDAD().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idCIUDADSeleccionadoID_CIUDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'CIUDAD?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_CIUDAD: idCIUDADSeleccionadoID_CIUDAD
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridCIUDAD').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
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
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idCIUDADSeleccionadoID_CIUDAD = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarCIUDAD") == undefined)) {
                    idCIUDADSeleccionadoID_CIUDAD = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarCIUDAD().show();
                }
            }
        }
    });
    return gridCIUDAD;
}
function pantallaModificarCIUDAD() {
    var comboDEPARTAMENTO = getCombo('DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO');
    comboDEPARTAMENTO.allowBlank = false;
    comboDEPARTAMENTO.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var ciudadModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarCIUDAD',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID CIUDAD',
                name: 'ID_CIUDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboDEPARTAMENTO]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarDEPARTAMENTO().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarCIUDAD',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idCIUDADSeleccionadoID_CIUDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    ciudadModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'CIUDAD?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarCIUDAD').close();
                                            Ext.getCmp('gridCIUDAD').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarCIUDAD').close();
                }
            }]
    });
    ciudadModificarFormPanel.getForm().load({
        url: 'CIUDAD?op=DETALLE',
        method: 'POST',
        params: {
            ID_CIUDAD: idCIUDADSeleccionadoID_CIUDAD
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar CIUDAD',
        id: 'modificarCIUDAD',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [ciudadModificarFormPanel]
    });
    return win;
}
function pantallaAgregarCIUDAD() {
    var comboDEPARTAMENTO = getCombo('DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO');
    comboDEPARTAMENTO.allowBlank = false;
    var ciudadAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarCIUDAD',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID CIUDAD',
                name: 'ID_CIUDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboDEPARTAMENTO]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarDEPARTAMENTO().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarCIUDAD',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                ciudadAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'CIUDAD?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarCIUDAD').close();
                                        Ext.getCmp('gridCIUDAD').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarCIUDAD').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar CIUDAD',
        id: 'agregarCIUDAD',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [ciudadAgregarFormPanel]
    });
    return win;
}
/**********************T.LOCALIDAD****************************/
var idLOCALIDADSeleccionadoID_LOCALIDAD;
function gridLOCALIDAD() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'LOCALIDAD?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'LOCALIDAD',
            totalProperty: 'TOTAL',
            id: 'ID_LOCALIDAD',
            fields: ['ID_LOCALIDAD', 'CIUDAD', 'NOMBRE']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRE',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE'
        }, {
            header: 'CIUDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'CIUDAD'
        }]);
    var gridLOCALIDAD = new Ext.grid.GridPanel({
        title: 'LOCALIDAD',
        id: 'gridLOCALIDAD',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarLOCALIDAD') == undefined)
                        pantallaAgregarLOCALIDAD().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idLOCALIDADSeleccionadoID_LOCALIDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'LOCALIDAD?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_LOCALIDAD: idLOCALIDADSeleccionadoID_LOCALIDAD
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridLOCALIDAD').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idLOCALIDADSeleccionadoID_LOCALIDAD = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarLOCALIDAD") == undefined)) {
                    idLOCALIDADSeleccionadoID_LOCALIDAD = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarLOCALIDAD().show();
                }
            }
        }
    });
    return gridLOCALIDAD;
}
function pantallaModificarLOCALIDAD() {
    var comboCIUDAD = getCombo('CIUDAD', 'CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD');
    comboCIUDAD.allowBlank = false;
    comboCIUDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var localidadModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarLOCALIDAD',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID LOCALIDAD',
                name: 'ID_LOCALIDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboCIUDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarCIUDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }]
        ,
        buttons: [{
                id: 'btnmodificarLOCALIDAD',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idLOCALIDADSeleccionadoID_LOCALIDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    localidadModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'LOCALIDAD?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarLOCALIDAD').close();
                                            Ext.getCmp('gridLOCALIDAD').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarLOCALIDAD').close();
                }
            }]
    });
    localidadModificarFormPanel.getForm().load({
        url: 'LOCALIDAD?op=DETALLE',
        method: 'POST',
        params: {
            ID_LOCALIDAD: idLOCALIDADSeleccionadoID_LOCALIDAD
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar LOCALIDAD',
        id: 'modificarLOCALIDAD',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [localidadModificarFormPanel]
    });
    return win;
}
function pantallaAgregarLOCALIDAD() {
    var comboCIUDAD = getCombo('CIUDAD', 'CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD');
    comboCIUDAD.allowBlank = false;
    var localidadAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarLOCALIDAD',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID LOCALIDAD',
                name: 'ID_LOCALIDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboCIUDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarCIUDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarLOCALIDAD',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                localidadAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'LOCALIDAD?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarLOCALIDAD').close();
                                        Ext.getCmp('gridLOCALIDAD').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarLOCALIDAD').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar LOCALIDAD',
        id: 'agregarLOCALIDAD',
        width: 300,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [localidadAgregarFormPanel]
    });
    return win;
}
/**********************T.DEPARTAMENTO****************************/
var idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO;
function gridDEPARTAMENTO() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'DEPARTAMENTO?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'DEPARTAMENTO',
            totalProperty: 'TOTAL',
            id: 'ID_DEPARTAMENTO',
            fields: ['ID_DEPARTAMENTO', 'PAIS', 'NOMBRE']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRE',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE'
        }, {
            header: 'PAÍS',
            width: anchoDefaultColumnas,
            dataIndex: 'PAIS'
        }]);
    var gridDEPARTAMENTO = new Ext.grid.GridPanel({
        title: 'DEPARTAMENTO',
        id: 'gridDEPARTAMENTO',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarDEPARTAMENTO') == undefined)
                        pantallaAgregarDEPARTAMENTO().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'DEPARTAMENTO?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_DEPARTAMENTO: idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridDEPARTAMENTO').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarDEPARTAMENTO") == undefined)) {
                    idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarDEPARTAMENTO().show();
                }
            }
        }
    });
    return gridDEPARTAMENTO;
}
function pantallaModificarDEPARTAMENTO() {
    var comboPAIS = getCombo('PAIS', 'PAIS', 'PAIS', 'PAIS', 'DESCRIPCION_PAIS', 'PAÍS', 'PAIS', 'DESCRIPCION_PAIS', 'PAIS', 'PAÍS');
    comboPAIS.allowBlank = false;
    comboPAIS.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var departamentoModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarDEPARTAMENTO',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID DEPARTAMENTO',
                name: 'ID_DEPARTAMENTO',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPAIS]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPAIS().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarDEPARTAMENTO',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    departamentoModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'DEPARTAMENTO?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarDEPARTAMENTO').close();
                                            Ext.getCmp('gridDEPARTAMENTO').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarDEPARTAMENTO').close();
                }
            }]
    });
    departamentoModificarFormPanel.getForm().load({
        url: 'DEPARTAMENTO?op=DETALLE',
        method: 'POST',
        params: {
            ID_DEPARTAMENTO: idDEPARTAMENTOSeleccionadoID_DEPARTAMENTO
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar DEPARTAMENTO',
        id: 'modificarDEPARTAMENTO',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [departamentoModificarFormPanel]
    });
    return win;
}
function pantallaAgregarDEPARTAMENTO() {
    var comboPAIS = getCombo('PAIS', 'PAIS', 'PAIS', 'PAIS', 'DESCRIPCION_PAIS', 'PAÍS', 'PAIS', 'DESCRIPCION_PAIS', 'PAIS', 'PAÍS');
    comboPAIS.allowBlank = false;
    var departamentoAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarDEPARTAMENTO',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPAIS]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPAIS().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarDEPARTAMENTO',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                departamentoAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'DEPARTAMENTO?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarDEPARTAMENTO').close();
                                        Ext.getCmp('gridDEPARTAMENTO').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarDEPARTAMENTO').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar DEPARTAMENTO',
        id: 'agregarDEPARTAMENTO',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [departamentoAgregarFormPanel]
    });
    return win;
}
/**********************T.ENTIDAD_FINANCIERA****************************/
var idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA;
function gridENTIDAD_FINANCIERA() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'ENTIDAD_FINANCIERA?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'ENTIDAD_FINANCIERA',
            totalProperty: 'TOTAL',
            id: 'ID_ENTIDAD_FINANCIERA',
            fields: ['ID_ENTIDAD_FINANCIERA', 'NUMERO_CUENTA', 'ENTIDAD', 'DESCRIPCION', 'CONTACTO']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'NUMERO CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_CUENTA'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }]);
    var gridENTIDAD_FINANCIERA = new Ext.grid.GridPanel({
        title: 'ENTIDAD FINANCIERA',
        id: 'gridENTIDAD_FINANCIERA',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarENTIDAD_FINANCIERA') == undefined)
                        pantallaAgregarENTIDAD_FINANCIERA().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'ENTIDAD_FINANCIERA?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_ENTIDAD_FINANCIERA: idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridENTIDAD_FINANCIERA').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Entidad Financiera',
                id: 'idBusquedaEntidadFinanciera',
                xtype: 'textfield',
                emptyText: 'Nombre..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridENTIDAD_FINANCIERA').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    DESCRIPCION: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarENTIDAD_FINANCIERA") == undefined)) {
                    idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarENTIDAD_FINANCIERA().show();
                }
            }
        }
    });
    return gridENTIDAD_FINANCIERA;
}
function pantallaModificarENTIDAD_FINANCIERA() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    comboENTIDAD.allowBlank = false;
    comboPERSONA.allowBlank = false;
    comboENTIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboPERSONA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });

    var entidad_financieraModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarENTIDAD_FINANCIERA',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID ENTIDAD FINANCIERA',
                name: 'ID_ENTIDAD_FINANCIERA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NUMERO CUENTA',
                name: 'NUMERO_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD, comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }, {
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarENTIDAD_FINANCIERA',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    entidad_financieraModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'ENTIDAD_FINANCIERA?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarENTIDAD_FINANCIERA').close();
                                            Ext.getCmp('gridENTIDAD_FINANCIERA').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarENTIDAD_FINANCIERA').close();
                }
            }]
    });
    entidad_financieraModificarFormPanel.getForm().load({
        url: 'ENTIDAD_FINANCIERA?op=DETALLE',
        method: 'POST',
        params: {
            ID_ENTIDAD_FINANCIERA: idENTIDAD_FINANCIERASeleccionadoID_ENTIDAD_FINANCIERA
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar ENTIDAD FINANCIERA',
        id: 'modificarENTIDAD_FINANCIERA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidad_financieraModificarFormPanel]
    });
    return win;
}
function pantallaAgregarENTIDAD_FINANCIERA() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    comboENTIDAD.allowBlank = false;
    comboPERSONA.allowBlank = false;

    var entidad_financieraAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarENTIDAD_FINANCIERA',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID ENTIDAD FINANCIERA',
                name: 'ID_ENTIDAD_FINANCIERA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NUMERO CUENTA',
                name: 'NUMERO_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD, comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }, {
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarENTIDAD_FINANCIERA',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                entidad_financieraAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'ENTIDAD_FINANCIERA?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarENTIDAD_FINANCIERA').close();
                                        Ext.getCmp('gridENTIDAD_FINANCIERA').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarENTIDAD_FINANCIERA').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar ENTIDAD FINANCIERA',
        id: 'agregarENTIDAD_FINANCIERA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidad_financieraAgregarFormPanel]
    });
    return win;
}
/**********************T.FACTURADOR****************************/
var idFACTURADORSeleccionadoID_FACTURADOR;
function gridFACTURADOR() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'FACTURADOR?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'FACTURADOR',
            totalProperty: 'TOTAL',
            id: 'ID_FACTURADOR',
            fields: ['CODIGO', 'ID_FACTURADOR', 'ENTIDAD', 'DESCRIPCION', 'CONTACTO', 'CODIGO', 'DIRECCION', 'LOCALIDAD', 'TELEFONO']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓDIGO',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO'
        }, {
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'DIRECCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DIRECCION'
        }, {
            header: 'LOCALIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'LOCALIDAD'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }, {
            header: 'TELÉFONO',
            width: anchoDefaultColumnas,
            dataIndex: 'TELEFONO'
        }]);
    var gridFACTURADOR = new Ext.grid.GridPanel({
        title: 'FACTURADOR',
        id: 'gridFACTURADOR',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarFACTURADOR') == undefined)
                        pantallaAgregarFACTURADOR().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idFACTURADORSeleccionadoID_FACTURADOR != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'FACTURADOR?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_FACTURADOR: idFACTURADORSeleccionadoID_FACTURADOR
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridFACTURADOR').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idFACTURADORSeleccionadoID_FACTURADOR = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarFACTURADOR") == undefined)) {
                    idFACTURADORSeleccionadoID_FACTURADOR = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarFACTURADOR().show();
                }
            }
        }
    });
    return gridFACTURADOR;
}
function pantallaModificarFACTURADOR() {
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');

    comboENTIDAD.allowBlank = false;

    comboENTIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboPERSONA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });

    var facturadorModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarFACTURADOR',
        frame: true,
        autoWidth: true,
        monitorValid: true,
        labelWidth: 100,
        items: [{
                fieldLabel: 'ID FACTURADOR',
                name: 'ID_FACTURADOR',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarFACTURADOR',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idFACTURADORSeleccionadoID_FACTURADOR != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    facturadorModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'FACTURADOR?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarFACTURADOR').close();
                                            Ext.getCmp('gridFACTURADOR').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarFACTURADOR').close();
                }
            }]
    });
    facturadorModificarFormPanel.getForm().load({
        url: 'FACTURADOR?op=DETALLE',
        method: 'POST',
        params: {
            ID_FACTURADOR: idFACTURADORSeleccionadoID_FACTURADOR
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar FACTURADOR',
        id: 'modificarFACTURADOR',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [facturadorModificarFormPanel]
    });
    return win;
}
function pantallaAgregarFACTURADOR() {
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'PERSONA');
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    comboENTIDAD.allowBlank = false;
    var facturadorAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarFACTURADOR',
        frame: true,
        autoWidth: true,
        monitorValid: true,
        labelWidth: 100,
        items: [{
                fieldLabel: 'CÓDIGO',
                name: 'CODIGO',
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarFACTURADOR',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                facturadorAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'FACTURADOR?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarFACTURADOR').close();
                                        Ext.getCmp('gridFACTURADOR').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarFACTURADOR').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar FACTURADOR',
        id: 'agregarFACTURADOR',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [facturadorAgregarFormPanel]
    });
    return win;
}
/**********************T.TERMINAL****************************/
var idTERMINALSeleccionadoID_TERMINAL;
function gridTERMINAL() {


    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'TERMINAL?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'TERMINAL',
            totalProperty: 'TOTAL',
            id: 'ID_TERMINAL',
            fields: ['ID_TERMINAL', 'SUCURSAL', 'DESCRIPCION', 'URL_IMPRESORA', 'CODIGO_TERMINAL', 'CODIGO_CAJA_SET', 'RECAUDADOR']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓD. TERMINAL',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO_TERMINAL'
        }, {
            header: 'CÓD. CAJA SET',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO_CAJA_SET'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'RECAUDADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'RECAUDADOR'
        }, {
            header: 'SUCURSAL',
            width: anchoDefaultColumnas,
            dataIndex: 'SUCURSAL'
        }, {
            header: 'URL IMPRESORA',
            width: anchoDefaultColumnas,
            dataIndex: 'URL_IMPRESORA'
        }]);
    var gridTERMINAL = new Ext.grid.GridPanel({
        title: 'TERMINAL',
        id: 'gridTERMINAL',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarTERMINAL') == undefined)
                        pantallaAgregarTERMINAL().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idTERMINALSeleccionadoID_TERMINAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'TERMINAL?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_TERMINAL: idTERMINALSeleccionadoID_TERMINAL
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridTERMINAL').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Terminal',
                id: 'idBusquedaTerminal',
                xtype: 'textfield',
                emptyText: 'Terminal..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridTERMINAL').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    ID_TERMINAL: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idTERMINALSeleccionadoID_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarTERMINAL") == undefined)) {
                    idTERMINALSeleccionadoID_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarTERMINAL().show();
                }
            }
        }
    });
    return gridTERMINAL;
}
function pantallaModificarTERMINAL() {

    var comboSUCURSAL = getCombo('SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL');
    comboSUCURSAL.allowBlank = false;
    comboSUCURSAL.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var terminalModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarTERMINAL',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID TERMINAL',
                name: 'ID_TERMINAL',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓD. TERMINAL',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'CODIGO_TERMINAL'

            }, {
                fieldLabel: 'CÓD. CAJA SET ',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'CODIGO_CAJA_SET'

            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'URL IMPRESORA',
                name: 'URL_IMPRESORA',
                xtype: 'textfield'

            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboSUCURSAL]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarSUCURSAL().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [{
                                id: 'idTextFieldHashTerminal',
                                fieldLabel: 'HASH',
                                name: 'HASH',
                                allowBlank: true,
                                inputType: 'password',
                                xtype: 'textfield'
                            }]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            if (getMac(1)) {
                                                //Ext.getCmp('idTextFieldHashTerminal').setValue(document.getElementById('macCalculadoId').value);
                                                Ext.getCmp('idTextFieldHashTerminal').setValue(document.appletAutorizadorTerminal.getMac(1));
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Atención',
                                                    msg: 'No se puede obtner la MAC de esta Terminal',
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });

                                            }


                                        }
                                    }]
                            }]
                    }]
            }]
        ,
        buttons: [{
                id: 'btnmodificarTERMINAL',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idTERMINALSeleccionadoID_TERMINAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    terminalModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'TERMINAL?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarTERMINAL').close();
                                            Ext.getCmp('gridTERMINAL').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarTERMINAL').close();
                }
            }]
    });
    terminalModificarFormPanel.getForm().load({
        url: 'TERMINAL?op=DETALLE',
        method: 'POST',
        params: {
            ID_TERMINAL: idTERMINALSeleccionadoID_TERMINAL
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar TERMINAL',
        id: 'modificarTERMINAL',
        autoWidth: true,
        height: 'auto',
        modal: true,
        closable: false,
        resizable: false,
        items: [terminalModificarFormPanel]
    });
    return win;
}
function pantallaAgregarTERMINAL() {

    var comboSUCURSAL = getCombo('SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL');
    comboSUCURSAL.allowBlank = false;
    var terminalAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarTERMINAL',
        labelWidth: 120,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID TERMINAL',
                name: 'ID_TERMINAL',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓD. TERMINAL',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'CODIGO_TERMINAL'

            }, {
                fieldLabel: 'CÓD. CAJA SET ',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'CODIGO_CAJA_SET'

            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'URL IMPRESORA',
                name: 'URL_IMPRESORA',
                xtype: 'textfield'

            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboSUCURSAL]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarSUCURSAL().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [{
                                id: 'idTextFieldHashTerminal',
                                fieldLabel: 'HASH',
                                name: 'HASH',
                                allowBlank: false,
                                inputType: 'password',
                                xtype: 'textfield'
                            }]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            if (getMac(1)) {
                                                Ext.getCmp('idTextFieldHashTerminal').setValue(document.appletAutorizadorTerminal.getMac(1));

                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Atención',
                                                    msg: 'No se puede obtner la MAC de esta Terminal',
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });

                                            }


                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarTERMINAL',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                terminalAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'TERMINAL?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarTERMINAL').close();
                                        Ext.getCmp('gridTERMINAL').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarTERMINAL').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar TERMINAL',
        id: 'agregarTERMINAL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [terminalAgregarFormPanel]
    });
    return win;
}
/**********************T.FRANJA_HORARIA_DET****************************/
var idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET;
function gridFRANJA_HORARIA_DET() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'FRANJA_HORARIA_DET?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'FRANJA_HORARIA_DET',
            totalProperty: 'TOTAL',
            id: 'ID_FRANJA_HORARIA_DET',
            fields: ['ID_FRANJA_HORARIA_DET', 'DIA', 'HORA_INI', 'HORA_FIN']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'DIA',
            width: anchoDefaultColumnas,
            dataIndex: 'DIA'
        }, {
            header: 'HORA INI',
            width: anchoDefaultColumnas,
            dataIndex: 'HORA_INI'
        }, {
            header: 'HORA FIN',
            width: anchoDefaultColumnas,
            dataIndex: 'HORA_FIN'
        }]);
    var gridFRANJA_HORARIA_DET = new Ext.grid.GridPanel({
        title: 'FRANJA HORARIA DETALLE',
        id: 'gridFRANJA_HORARIA_DET',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                id: 'idBtnAgregarFranjaHorariaDet',
                text: 'Agregar',
                disabled: true,
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarFRANJA_HORARIA_DET') == undefined)
                        pantallaAgregarFRANJA_HORARIA_DET().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'FRANJA_HORARIA_DET?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_FRANJA_HORARIA_DET: idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarFRANJA_HORARIA_DET") == undefined)) {
                    idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarFRANJA_HORARIA_DET().show();
                }
            }
        }
    });
    return gridFRANJA_HORARIA_DET;
}
function pantallaModificarFRANJA_HORARIA_DET() {
    var comboDays = new Ext.form.ComboBox({
        fieldLabel: 'Dias',
        hiddenName: 'DIA',
        valueField: 'DIA',
        emptyText: 'Dias de la semana',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['DIA', 'descripcion'],
            data: [['1', 'Domingo'],
                ['2', 'Lunes'],
                ['3', 'Martes'],
                ['4', 'Miercoles'],
                ['5', 'Jueves'],
                ['6', 'Viernes'],
                ['7', 'Sabado']]
        })
    });


    var franja_horaria_detModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarFRANJA_HORARIA_DET',
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'ID FRANJA HORARIA DET',
                name: 'ID_FRANJA_HORARIA_DET',
                inputType: 'hidden'
            }, {
                xtype: 'timefield',
                format: 'H:i:s',
                anchor: '95%',
                fieldLabel: 'HORA INI',
                name: 'HORA_INI',
                allowBlank: false
            }, {
                xtype: 'timefield',
                format: 'H:i:s',
                anchor: '95%',
                fieldLabel: 'HORA FIN',
                name: 'HORA_FIN',
                allowBlank: false
            }, comboDays],
        buttons: [{
                id: 'btnmodificarFRANJA_HORARIA_DET',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    franja_horaria_detModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        params: {
                                            FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
                                        },
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'FRANJA_HORARIA_DET?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarFRANJA_HORARIA_DET').close();
                                            Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarFRANJA_HORARIA_DET').close();
                }
            }]
    });
    franja_horaria_detModificarFormPanel.getForm().load({
        url: 'FRANJA_HORARIA_DET?op=DETALLE',
        method: 'POST',
        params: {
            ID_FRANJA_HORARIA_DET: idFRANJA_HORARIA_DETSeleccionadoID_FRANJA_HORARIA_DET
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar FRANJA HORARIA DETALLE',
        id: 'modificarFRANJA_HORARIA_DET',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [franja_horaria_detModificarFormPanel]
    });
    return win;
}
function pantallaAgregarFRANJA_HORARIA_DET() {
    var comboDays = new Ext.form.ComboBox({
        fieldLabel: 'Dias',
        hiddenName: 'DIA',
        valueField: 'DIA',
        emptyText: 'Dias de la semana',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['DIA', 'descripcion'],
            data: [['1', 'Domingo'],
                ['2', 'Lunes'],
                ['3', 'Martes'],
                ['4', 'Miercoles'],
                ['5', 'Jueves'],
                ['6', 'Viernes'],
                ['7', 'Sabado']]
        })
    });

    var franja_horaria_detAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarFRANJA_HORARIA_DET',
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                xtype: 'timefield',
                format: 'H:i:s',
                anchor: '95%',
                fieldLabel: 'HORA INI',
                name: 'HORA_INI',
                allowBlank: false
            }, {
                xtype: 'timefield',
                format: 'H:i:s',
                anchor: '95%',
                fieldLabel: 'HORA FIN',
                name: 'HORA_FIN',
                allowBlank: false
            }, comboDays],
        buttons: [{
                id: 'btnAgregarFRANJA_HORARIA_DET',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                franja_horaria_detAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    params: {
                                        FRANJA_HORARIA_CAB: idFRANJA_HORARIA_CABSeleccionadoID_FRANJA_HORARIA_CAB
                                    },
                                    waitMsg: 'Asignando...',
                                    url: 'FRANJA_HORARIA_DET?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarFRANJA_HORARIA_DET').close();
                                        Ext.getCmp('gridFRANJA_HORARIA_DET').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarFRANJA_HORARIA_DET').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar FRANJA HORARIA DETALLE',
        id: 'agregarFRANJA_HORARIA_DET',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [franja_horaria_detAgregarFormPanel]
    });
    return win;
}
/**********************T.PERSONA****************************/
var idPERSONASeleccionadoID_PERSONA;
function gridPERSONA() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'PERSONA?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'PERSONA',
            totalProperty: 'TOTAL',
            id: 'ID_PERSONA',
            fields: ['ID_PERSONA', 'NOMBRES', 'APELLIDOS', 'DIRECCION', 'TELEFONO', 'RUC_CI', 'LOCALIDAD']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRES',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRES'
        }, {
            header: 'APELLIDOS',
            width: anchoDefaultColumnas,
            dataIndex: 'APELLIDOS'
        }, {
            header: 'DIRECCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DIRECCION'
        }, {
            header: 'TELÉFONO',
            width: anchoDefaultColumnas,
            dataIndex: 'TELEFONO'
        }, {
            header: 'RUC CI',
            width: anchoDefaultColumnas,
            dataIndex: 'RUC_CI'
        }, {
            header: 'LOCALIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'LOCALIDAD'
        }]);
    var gridPERSONA = new Ext.grid.GridPanel({
        title: 'PERSONA',
        id: 'gridPERSONA',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarPERSONA') == undefined)
                        pantallaAgregarPERSONA().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idPERSONASeleccionadoID_PERSONA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'PERSONA?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_PERSONA: idPERSONASeleccionadoID_PERSONA
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridPERSONA').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Nombre',
                id: 'idBusquedaNombrePersona',
                xtype: 'textfield',
                emptyText: 'Nombre..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridPERSONA').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    NOMBRES: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }, {
                text: 'Apellido',
                id: 'idBusquedaApellidoPersona',
                xtype: 'textfield',
                emptyText: 'Apellido..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridPERSONA').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    APELLIDOS: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idPERSONASeleccionadoID_PERSONA = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarPERSONA") == undefined)) {
                    idPERSONASeleccionadoID_PERSONA = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarPERSONA().show();
                }
            }
        }
    });
    return gridPERSONA;
}
function pantallaModificarPERSONA() {
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    comboLOCALIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var personaModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarPERSONA',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID PERSONA',
                name: 'ID_PERSONA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRES',
                name: 'NOMBRES',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'APELLIDOS',
                name: 'APELLIDOS',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'RUC CI',
                name: 'RUC_CI',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarPERSONA',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idPERSONASeleccionadoID_PERSONA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    personaModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'PERSONA?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarPERSONA').close();
                                            Ext.getCmp('gridPERSONA').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarPERSONA').close();
                }
            }]
    });
    personaModificarFormPanel.getForm().load({
        url: 'PERSONA?op=DETALLE',
        method: 'POST',
        params: {
            ID_PERSONA: idPERSONASeleccionadoID_PERSONA
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar PERSONA',
        id: 'modificarPERSONA',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [personaModificarFormPanel]
    });
    return win;
}
function pantallaAgregarPERSONA() {
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    var personaAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarPERSONA',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID PERSONA',
                name: 'ID_PERSONA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRES',
                name: 'NOMBRES',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'APELLIDOS',
                name: 'APELLIDOS',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'RUC CI',
                name: 'RUC_CI',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarPERSONA',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                personaAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'PERSONA?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarPERSONA').close();
                                        Ext.getCmp('gridPERSONA').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarPERSONA').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar PERSONA',
        id: 'agregarPERSONA',
        width: 300,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [personaAgregarFormPanel]
    });
    return win;
}
/**********************T.ENTIDAD****************************/
var idENTIDADSeleccionadoID_ENTIDAD;
function gridENTIDAD() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'ENTIDAD?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'ENTIDAD',
            totalProperty: 'TOTAL',
            id: 'ID_ENTIDAD',
            fields: ['ID_ENTIDAD', 'APELLIDO', 'NOMBRE', 'RUC_CI', 'LOCALIDAD', 'TELEFONO', 'DIRECCION']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRE',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE'
        }, {
            header: 'RUC CI',
            width: anchoDefaultColumnas,
            dataIndex: 'RUC_CI'
        }, {
            header: 'DIRECCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DIRECCION'
        }, {
            header: 'LOCALIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'LOCALIDAD'
        }, {
            header: 'TELÉFONO',
            width: anchoDefaultColumnas,
            dataIndex: 'TELEFONO'
        }]);
    var gridENTIDAD = new Ext.grid.GridPanel({
        title: 'ENTIDAD',
        id: 'gridENTIDAD',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarENTIDAD') == undefined)
                        pantallaAgregarENTIDAD().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idENTIDADSeleccionadoID_ENTIDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'ENTIDAD?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_ENTIDAD: idENTIDADSeleccionadoID_ENTIDAD
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridENTIDAD').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Entidad',
                id: 'idBusquedaEntidad',
                xtype: 'textfield',
                emptyText: 'Nombre..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridENTIDAD').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    NOMBRE: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idENTIDADSeleccionadoID_ENTIDAD = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarENTIDAD") == undefined)) {
                    idENTIDADSeleccionadoID_ENTIDAD = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarENTIDAD().show();
                }
            }
        }
    });
    return gridENTIDAD;
}
function pantallaModificarENTIDAD() {
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    comboLOCALIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var entidadModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarENTIDAD',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID ENTIDAD',
                name: 'ID_ENTIDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'RUC CI',
                name: 'RUC_CI',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarENTIDAD',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idENTIDADSeleccionadoID_ENTIDAD != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    entidadModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'ENTIDAD?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarENTIDAD').close();
                                            Ext.getCmp('gridENTIDAD').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarENTIDAD').close();
                }
            }]
    });
    entidadModificarFormPanel.getForm().load({
        url: 'ENTIDAD?op=DETALLE',
        method: 'POST',
        params: {
            ID_ENTIDAD: idENTIDADSeleccionadoID_ENTIDAD
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar ENTIDAD',
        id: 'modificarENTIDAD',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidadModificarFormPanel]
    });
    return win;
}
function pantallaAgregarENTIDAD() {
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    comboLOCALIDAD.allowBlank = false;
    var entidadAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarENTIDAD',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID ENTIDAD',
                name: 'ID_ENTIDAD',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'RUC CI',
                name: 'RUC_CI',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarENTIDAD',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                entidadAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'ENTIDAD?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarENTIDAD').close();
                                        Ext.getCmp('gridENTIDAD').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarENTIDAD').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar ENTIDAD',
        id: 'agregarENTIDAD',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidadAgregarFormPanel]
    });
    return win;
}
/**********************T.RED****************************/
var idREDSeleccionadoID_RED;
function gridRED() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RED?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'RED',
            totalProperty: 'TOTAL',
            id: 'ID_RED',
            fields: ['ID_RED', 'BANCO_CLEARING', 'NUMERO_CUENTA', 'NUMERO_CUENTA_PROCESADOR', 'ENTIDAD', 'DESCRIPCION', 'COD_ERA', 'CUENTA_BCP_IMPUESTOS', 'CUENTA_BCP_OTROS_CONCEPTOS', 'CUENTA_BCP_PENALIDADES']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓDIGO DE ERA',
            width: anchoDefaultColumnas,
            dataIndex: 'COD_ERA'
        }, {
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'BANCO CLEARING',
            width: anchoDefaultColumnas,
            dataIndex: 'BANCO_CLEARING'
        }, {
            header: 'NÚM. CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_CUENTA'
        }, {
            header: 'NÚM. CUENTA PROCESADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_CUENTA_PROCESADOR'
        }, {
            header: 'CUENTA BCP IMPUESTOS',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA_BCP_IMPUESTOS'
        }, {
            header: 'CUENTA BCP OTROS CONCEPTOS',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA_BCP_OTROS_CONCEPTOS'
        }, {
            header: 'CUENTA BCP PENALIDADES',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA_BCP_PENALIDADES'
        }]);
    var gridRED = new Ext.grid.GridPanel({
        title: 'RED',
        id: 'gridRED',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarRED') == undefined)
                        pantallaAgregarRED().show();
                }
            }
            //        ,{
            //            text:'Quitar',
            //            tooltip:TOOL_TIP_QUITAR,
            //            iconCls:'remove',
            //            handler: function(){
            //                if(idREDSeleccionadoID_RED!=undefined){
            //                    Ext.Msg.show({
            //                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
            //                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
            //                        buttons : Ext.Msg.OKCANCEL,
            //                        animEl : 'elId',
            //                        icon : Ext.MessageBox.WARNING,
            //                        fn:function(btn){
            //                            if(btn=="ok"){
            //                                var conn = new Ext.data.Connection(); conn.request({
            //                                    url : 'RED?op=BORRAR',
            //                                    method : 'POST',
            //                                    params : {
            //                                        ID_RED : idREDSeleccionadoID_RED
            //                                    },
            //                                    success : function(action) {
            //                                        obj = Ext.util.JSON .decode(action.responseText); if(obj.success){
            //                                            Ext.Msg.show({
            //                                                title :TITULO_ACTUALIZACION_EXITOSA,
            //                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
            //                                                buttons : Ext.Msg.OK,
            //                                                icon : Ext.MessageBox.INFO
            //                                            }); Ext.getCmp('gridRED').store.reload();
            //                                        }else{
            //                                            Ext.Msg.show({
            //                                                title : 'Estado',
            //                                                msg : obj.motivo,
            //                                                buttons : Ext.Msg.OK,
            //                                                icon : Ext.MessageBox.ERROR
            //                                            });
            //                                        }
            //                                    },
            //                                    failure : function(action) {
            //                                        Ext.Msg.show({
            //                                            title : FAIL_TITULO_GENERAL,
            //                                            msg : FAIL_CUERPO_GENERAL,
            //                                            buttons : Ext.Msg.OK,
            //                                            animEl : 'elId',
            //                                            icon : Ext.MessageBox.ERROR
            //                                        });
            //                                    }
            //                                });
            //                            }
            //                        }
            //                    });
            //                }
            //            }
            //        }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idREDSeleccionadoID_RED = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarRED") == undefined)) {
                    idREDSeleccionadoID_RED = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarRED().show();
                }
            }
        }
    });
    return gridRED;
}
function pantallaModificarRED() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboBANCO_CLEARING = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'BANCO CLEARING', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA');
    comboENTIDAD.allowBlank = false;
    comboBANCO_CLEARING.allowBlank = false;
    comboBANCO_CLEARING.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboENTIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var redModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarRED',
        labelWidth: 210,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID RED',
                name: 'ID_RED',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓDIGO DE ERA',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'COD_ERA'

            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboBANCO_CLEARING]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD_FINANCIERA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD, {
                                fieldLabel: 'NÚMERO CUENTA',
                                name: 'NUMERO_CUENTA',
                                xtype: 'textfield',
                                allowBlank: false
                            }, {
                                fieldLabel: 'NÚMERO CUENTA PROCESADOR',
                                name: 'NUMERO_CUENTA_PROCESADOR',
                                xtype: 'textfield',
                                allowBlank: false
                            }, {
                                fieldLabel: 'CUENTA BCP IMPUESTOS',
                                name: 'CUENTA_BCP_IMPUESTOS',
                                xtype: 'textfield',
                                allowBlank: false
                            }, {
                                fieldLabel: 'CUENTA BCP PENALIDADES',
                                name: 'CUENTA_BCP_PENALIDADES',
                                xtype: 'textfield',
                                allowBlank: false
                            }, {
                                fieldLabel: 'CUENTA BCP OTROS CONCEPTOS',
                                name: 'CUENTA_BCP_OTROS_CONCEPTOS',
                                xtype: 'textfield',
                                allowBlank: false
                            }]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarRED',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idREDSeleccionadoID_RED != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    redModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'RED?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarRED').close();
                                            Ext.getCmp('gridRED').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarRED').close();
                }
            }]
    });
    redModificarFormPanel.getForm().load({
        url: 'RED?op=DETALLE',
        method: 'POST',
        params: {
            ID_RED: idREDSeleccionadoID_RED
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar RED',
        id: 'modificarRED',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [redModificarFormPanel]
    });
    return win;
}
function pantallaAgregarRED() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboBANCO_CLEARING = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'BANCO CLEARING', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA');
    comboENTIDAD.allowBlank = false;
    comboBANCO_CLEARING.allowBlank = false;
    var redAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarRED',
        labelWidth: 210,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID RED',
                name: 'ID_RED',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓDIGO DE ERA',
                allowBlank: false,
                xtype: 'numberfield',
                name: 'COD_ERA'

            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboBANCO_CLEARING]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD_FINANCIERA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'NÚMERO CUENTA',
                name: 'NUMERO_CUENTA',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'NÚMERO CUENTA PROCESADOR',
                name: 'NUMERO_CUENTA_PROCESADOR',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'CUENTA BCP IMPUESTOS',
                name: 'CUENTA_BCP_IMPUESTOS',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'CUENTA BCP PENALIDADES',
                name: 'CUENTA_BCP_PENALIDADES',
                xtype: 'textfield',
                allowBlank: false
            }, {
                fieldLabel: 'CUENTA BCP OTROS CONCEPTOS',
                name: 'CUENTA_BCP_OTROS_CONCEPTOS',
                xtype: 'textfield',
                allowBlank: false
            }],
        buttons: [{
                id: 'btnAgregarRED',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                redAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'RED?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarRED').close();
                                        Ext.getCmp('gridRED').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarRED').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar RED',
        id: 'agregarRED',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [redAgregarFormPanel]
    });
    return win;
}
/**********************T.ENTIDAD POLITICA****************************/
var idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA;
function gridENTIDAD_POLITICA() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'ENTIDAD_POLITICA?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'ENTIDAD_POLITICA',
            totalProperty: 'TOTAL',
            id: 'ID_ENTIDAD_POLITICA',
            fields: ['ID_ENTIDAD_POLITICA', 'NUMERO_CUENTA', 'DESCRIPCION_ENTIDAD', 'DESCRIPCION_RED']
        })
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION_ENTIDAD'
        }, {
            header: 'NUMERO CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_CUENTA'
        }, {
            header: 'DESCRIPCIÓN RED',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION_RED'
        }]);
    var gridENTIDAD_POLITICA = new Ext.grid.GridPanel({
        title: 'ENTIDAD POLÍTICA',
        id: 'gridENTIDAD_POLITICA',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarENTIDAD_POLITICA') == undefined)
                        pantallaAgregarENTIDAD_POLITICA().show();
                }
            }, {
                text: 'Entidad Politica',
                id: 'idBusquedaEntidadPolitica',
                xtype: 'textfield',
                emptyText: 'Entidad Politica..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridENTIDAD_POLITICA').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    ID_ENTIDAD_POLITICA: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {

                idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                //console.log(esteObjeto.getStore())
                idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA = esteObjeto.getStore().getAt(rowIndex).id;
                pantallaModificarENTIDAD_POLITICA().show();

            }
        }
    });
    return gridENTIDAD_POLITICA;
}
function pantallaModificarENTIDAD_POLITICA() {
    var comboENTIDAD_FINANCIERA_POLITICA = getCombo('ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'DESCRIPCION_ENTIDADES_NO_POLITICAS', 'ENTIDADES NO POLITICAS', 'ENTIDADES_NO_POLITICAS', 'DESCRIPCION_ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES NO POLITICAS');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    comboRED.allowBlank = false;
    comboRED.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboENTIDAD_FINANCIERA_POLITICA.allowBlank = false;

    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboENTIDAD_FINANCIERA_POLITICA.disable();
            comboENTIDAD_FINANCIERA_POLITICA.reset();


        }
        comboENTIDAD_FINANCIERA_POLITICA.store.baseParams['ID_RED'] = newValue;
        comboENTIDAD_FINANCIERA_POLITICA.store.baseParams['limit'] = 1000;
        comboENTIDAD_FINANCIERA_POLITICA.store.baseParams['start'] = 0;
        comboENTIDAD_FINANCIERA_POLITICA.store.reload();

    }, null, null);

    comboRED.addListener('select', function (esteCombo, record, index) {
        comboENTIDAD_FINANCIERA_POLITICA.enable();
        comboENTIDAD_FINANCIERA_POLITICA.store.baseParams['ID_RED'] = record.data.RED;
        comboENTIDAD_FINANCIERA_POLITICA.store.reload();
    }, null, null);


    comboENTIDAD_FINANCIERA_POLITICA.disable();
    var entidadPoliticaModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarENTIDAD_POLITICA',
        labelWidth: 210,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                name: 'ID_ENTIDAD_POLITICA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, comboRED, {
                fieldLabel: 'NÚMERO CUENTA',
                name: 'NUMERO_CUENTA',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRED]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarRED().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD_FINANCIERA_POLITICA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    entidadPoliticaModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'ENTIDAD_POLITICA?op=MODIFICAR',
                                        params: {
                                            ID_ENTIDAD_POLITICA2: idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA
                                        },
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            win.close();
                                            Ext.getCmp('gridENTIDAD_POLITICA').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });

    entidadPoliticaModificarFormPanel.getForm().load({
        url: 'ENTIDAD_POLITICA?op=DETALLE',
        method: 'POST',
        params: {
            ID_ENTIDAD_POLITICA: idENTIDAD_POLITICASeleccionadoID_ENTIDAD_POLITICA
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar Entidad Política',
        id: 'modificarENTIDAD_POLITICA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidadPoliticaModificarFormPanel]
    });
    return win;
}
function pantallaAgregarENTIDAD_POLITICA() {
    var ENTIDADES_NO_POLITICAS = getCombo('ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'DESCRIPCION_ENTIDADES_NO_POLITICAS', 'ENTIDADES NO POLITICAS', 'ENTIDADES_NO_POLITICAS', 'DESCRIPCION_ENTIDADES_NO_POLITICAS', 'ENTIDADES_NO_POLITICAS', 'ENTIDADES NO POLITICAS');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    comboRED.allowBlank = false;
    comboRED.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    ENTIDADES_NO_POLITICAS.allowBlank = false;
    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            ENTIDADES_NO_POLITICAS.disable();
            ENTIDADES_NO_POLITICAS.reset();
        }
        ENTIDADES_NO_POLITICAS.store.baseParams['ID_RED'] = newValue;
        ENTIDADES_NO_POLITICAS.store.baseParams['limit'] = 1000;
        ENTIDADES_NO_POLITICAS.store.baseParams['start'] = 0;
        ENTIDADES_NO_POLITICAS.store.reload();
    }, null, null);

    comboRED.addListener('select', function (esteCombo, record, index) {
        ENTIDADES_NO_POLITICAS.enable();
        ENTIDADES_NO_POLITICAS.store.baseParams['ID_RED'] = record.data.RED;
        ENTIDADES_NO_POLITICAS.store.reload();
    }, null, null);


    ENTIDADES_NO_POLITICAS.disable();
    var entidadPoliticaModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarENTIDAD_POLITICA',
        labelWidth: 210,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [comboRED, {
                fieldLabel: 'NÚMERO CUENTA',
                name: 'NUMERO_CUENTA',
                xtype: 'textfield',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRED]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarRED().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [ENTIDADES_NO_POLITICAS]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                entidadPoliticaModificarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'ENTIDAD_POLITICA?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        win.close();
                                        Ext.getCmp('gridENTIDAD_POLITICA').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar ENTIDAD POLÍTICA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [entidadPoliticaModificarFormPanel]
    });
    return win;
}
/**********************T.RECAUDADOR****************************/
var idRECAUDADORSeleccionadoID_RECAUDADOR;
function gridRECAUDADOR() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RECAUDADOR?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'RECAUDADOR',
            totalProperty: 'TOTAL',
            id: 'ID_RECAUDADOR',
            fields: ['ID_RECAUDADOR', 'CONTACTO', 'ENTIDAD', 'DESCRIPCION', 'CODIGO_RECAUDADOR', 'CUENTA', 'RED', 'RUC', 'NUMERO_ORDEN_TAM_RANGO', 'CONCILIAR_CAJA', 'HABILITADO', 'RETENIDO']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 5;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓDIGO',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO_RECAUDADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'RUC',
            width: anchoDefaultColumnas,
            dataIndex: 'RUC'
        }, {
            header: 'CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }, {
            header: 'RED',
            width: anchoDefaultColumnas,
            dataIndex: 'RED'
        }, {
            header: 'TAM. RANGO NUM. ORDEN',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_ORDEN_TAM_RANGO'
        }, {
            header: 'CONCILIAR',
            width: anchoDefaultColumnas,
            dataIndex: 'CONCILIAR_CAJA'
        }, {
            header: 'HABILITADO',
            width: anchoDefaultColumnas,
            dataIndex: 'HABILITADO'
        }, {
            header: 'RETENIDO',
            width: anchoDefaultColumnas,
            dataIndex: 'RETENIDO'
        }]);
    var gridRECAUDADOR = new Ext.grid.GridPanel({
        title: 'RECAUDADOR',
        id: 'gridRECAUDADOR',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarRECAUDADOR') == undefined)
                        pantallaAgregarRECAUDADOR().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idRECAUDADORSeleccionadoID_RECAUDADOR != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'RECAUDADOR?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_RECAUDADOR: idRECAUDADORSeleccionadoID_RECAUDADOR
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridRECAUDADOR').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Recaudador',
                id: 'idBusquedaRecaudador',
                xtype: 'textfield',
                emptyText: 'Recaudador..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridRECAUDADOR').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    DESCRIPCION: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idRECAUDADORSeleccionadoID_RECAUDADOR = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarRECAUDADOR") == undefined)) {
                    idRECAUDADORSeleccionadoID_RECAUDADOR = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarRECAUDADOR().show();
                }
            }
        }
    });
    return gridRECAUDADOR;
}
function pantallaModificarRECAUDADOR() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'CONTACTO', 'CONTACTO', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    comboPERSONA.allowBlank = false;
    comboENTIDAD.allowBlank = false;
    comboRED.allowBlank = false;
    var comboHABILITADO = new Ext.form.ComboBox({
        fieldLabel: 'HABILITADO',
        id: 'idHabilitadoRecaudador',
        name: 'HABILITADO',
        hiddenName: 'HABILITADO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth: 180,
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['S', 'SI'],
                ['N', 'NO'],
                ['B', 'BLOQUEADO']]
        }),
        listeners: {
            'select': function (esteObjeto, esteEvento) {
                Ext.getCmp('idModConcilirRecaudador').focus(true, true);
            }
        }
    });
    var comboCONCILIAR = new Ext.form.ComboBox({
        fieldLabel: 'CONCILIAR CAJA',
        id: 'idModConcilirRecaudador',
        name: 'CONCILIAR_CAJA',
        hiddenName: 'CONCILIAR_CAJA',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth: 180,
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['S', 'SI'],
                ['N', 'NO']]
        }),
        listeners: {
            'select': function (esteObjeto, esteEvento) {
                Ext.getCmp('btnmodificarRECAUDADOR').focus(true, true);
            }
        }
    });
    var comboRETENIDO = new Ext.form.ComboBox({
        fieldLabel: 'Retenido',
        id: 'idRecaudadorRetenido',
        name: 'RETENIDO',
        hiddenName: 'RETENIDO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth: 180,
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['S', 'SI'],
                ['N', 'NO']]
        }),
        listeners: {
            'select': function (esteObjeto, esteEvento) {
                Ext.getCmp('btnmodificarRECAUDADOR').focus(true, true);
            }
        }
    });

    comboPERSONA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboENTIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboRED.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var recaudadorModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarRECAUDADOR',
        labelWidth: 180,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID RECAUDADOR',
                name: 'ID_RECAUDADOR',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓDIGO',
                xtype: 'numberfield',
                name: 'CODIGO_RECAUDADOR',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [{
                                fieldLabel: 'CUENTA',
                                name: 'CUENTA',
                                allowBlank: false,
                                xtype: 'textfield'
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                margins: '5 0 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRED]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                margins: '5 0 0 0',
                                items: [{
                                        xtype: 'panel',
                                        items: [{
                                                xtype: 'button',
                                                iconCls: 'add2',
                                                handler: function () {
                                                    pantallaAgregarRED().show();
                                                }
                                            }]
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'TAM. RANGO NUM. ORDEN',
                name: 'NUMERO_ORDEN_TAM_RANGO',
                allowBlank: false,
                xtype: 'numberfield'
            }, comboCONCILIAR, comboHABILITADO, comboRETENIDO],
        buttons: [{
                id: 'btnmodificarRECAUDADOR',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idRECAUDADORSeleccionadoID_RECAUDADOR != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    recaudadorModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'RECAUDADOR?op=MODIFICAR',
                                        success: function (form, action) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarRECAUDADOR').close();
                                            Ext.getCmp('gridRECAUDADOR').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarRECAUDADOR').close();
                }
            }]
    });
    recaudadorModificarFormPanel.getForm().load({
        url: 'RECAUDADOR?op=DETALLE',
        method: 'POST',
        params: {
            ID_RECAUDADOR: idRECAUDADORSeleccionadoID_RECAUDADOR
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar RECAUDADOR',
        id: 'modificarRECAUDADOR',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [recaudadorModificarFormPanel]
    });
    return win;
}
function pantallaAgregarRECAUDADOR() {
    var comboENTIDAD = getCombo('ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD', 'DESCRIPCION_ENTIDAD', 'ENTIDAD', 'ENTIDAD');
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'CONTACTO', 'CONTACTO', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    comboPERSONA.allowBlank = false;
    comboENTIDAD.allowBlank = false;
    comboRED.allowBlank = false;
    var comboHABILITADO = new Ext.form.ComboBox({
        fieldLabel: 'HABILITADO',
        id: 'idHabilitadoRecaudador',
        name: 'HABILITADO',
        hiddenName: 'HABILITADO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth: 180,
        allowBlank: false,
        value: 'S',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['S', 'SI'],
                ['N', 'NO'],
                ['B', 'BLOQUEADO']]
        }),
        listeners: {
            'select': function (esteObjeto, esteEvento) {
                Ext.getCmp('idConcilirRecaudador').focus(true, true);
            }
        }
    });
    var comboCONCILIAR = new Ext.form.ComboBox({
        fieldLabel: 'CONCILIAR CAJA',
        id: 'idConcilirRecaudador',
        name: 'CONCILIAR_CAJA',
        hiddenName: 'CONCILIAR_CAJA',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth: 180,
        allowBlank: false,
        value: 'N',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['S', 'SI'],
                ['N', 'NO']]
        }),
        listeners: {
            'select': function (esteObjeto, esteEvento) {
                Ext.getCmp('btnAgregarRECAUDADOR').focus(true, true);
            }
        }
    });
    var recaudadorAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarRECAUDADOR',
        labelWidth: 180,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID RECAUDADOR',
                name: 'ID_RECAUDADOR',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓDIGO',
                xtype: 'numberfield',
                name: 'CODIGO_RECAUDADOR',
                allowBlank: false
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [{
                                fieldLabel: 'CUENTA',
                                name: 'CUENTA',
                                allowBlank: false,
                                xtype: 'textfield'
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                margins: '5 0 0 0',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRED]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                margins: '5 0 0 0',
                                items: [{
                                        xtype: 'panel',
                                        items: [{
                                                xtype: 'button',
                                                iconCls: 'add2',
                                                handler: function () {
                                                    pantallaAgregarRED().show();
                                                }
                                            }]
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'TAM. RANGO NUM. ORDEN',
                name: 'NUMERO_ORDEN_TAM_RANGO',
                allowBlank: false,
                xtype: 'numberfield'
            }, comboCONCILIAR, comboHABILITADO],
        buttons: [{
                id: 'btnAgregarRECAUDADOR',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                recaudadorAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'RECAUDADOR?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarRECAUDADOR').close();
                                        Ext.getCmp('gridRECAUDADOR').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarRECAUDADOR').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar RECAUDADOR',
        id: 'agregarRECAUDADOR',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [recaudadorAgregarFormPanel]
    });
    return win;
}
/**********************T.SUCURSAL****************************/
var idSUCURSALSeleccionadoID_SUCURSAL;
function gridSUCURSAL() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'SUCURSAL?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'SUCURSAL',
            totalProperty: 'TOTAL',
            id: 'ID_SUCURSAL',
            fields: ['ID_SUCURSAL', 'NUMERO_CUENTA', 'CODIGO_SUCURSAL_SET', 'LOCALIDAD', 'RECAUDADOR', 'DESCRIPCION', 'DIRECCION', 'CONTACTO', 'TELEFONO', 'CODIGO_SUCURSAL', 'ZONA', 'ES_TIGO']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'CÓD. SUCURSAL',
            width: anchoDefaultColumnas,
            dataIndex: 'CODIGO_SUCURSAL'
        }, {
            header: 'CÓD. SUCURSAL SET',
            width: 13,
            dataIndex: 'CODIGO_SUCURSAL_SET'
        }, {
            header: 'NRO. CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'NUMERO_CUENTA'
        }, {
            header: 'RECAUDADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'RECAUDADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'DIRECCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DIRECCION'
        }, {
            header: 'LOCALIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'LOCALIDAD'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }, {
            header: 'TELÉFONO',
            width: anchoDefaultColumnas,
            dataIndex: 'TELEFONO'
        }, {
            header: 'ZONA',
            width: anchoDefaultColumnas,
            dataIndex: 'ZONA'
        }, {
            header: 'ES TIGO',
            width: anchoDefaultColumnas,
            dataIndex: 'ES_TIGO'
        }]);
    var gridSUCURSAL = new Ext.grid.GridPanel({
        title: 'SUCURSAL',
        id: 'gridSUCURSAL',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarSUCURSAL') == undefined)
                        pantallaAgregarSUCURSAL().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idSUCURSALSeleccionadoID_SUCURSAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'SUCURSAL?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_SUCURSAL: idSUCURSALSeleccionadoID_SUCURSAL
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridSUCURSAL').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Sucursal',
                id: 'idBusquedaSucursal',
                xtype: 'textfield',
                emptyText: 'Sucursal..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridSUCURSAL').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    DESCRIPCION: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }, {
                text: 'Recaudador',
                id: 'idBusquedaSucursalRecaudador',
                xtype: 'textfield',
                emptyText: 'Recaudador..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridSUCURSAL').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    RECAUDADOR: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idSUCURSALSeleccionadoID_SUCURSAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarSUCURSAL") == undefined)) {
                    idSUCURSALSeleccionadoID_SUCURSAL = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarSUCURSAL().show();
                }
            }
        }
    });
    return gridSUCURSAL;
}
function pantallaModificarSUCURSAL() {

    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'CONTACTO', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO');
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    var zona = new Ext.form.ComboBox({
        fieldLabel: 'Zona',
        hiddenName: 'ZONA',
        valueField: 'value',
        //anchor:'20%',
        //width:100,
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data: [['1', 'CAPITAL'],
                ['2', 'INTERIOR']]
        })
    });
    var esTigo = new Ext.form.ComboBox({
        fieldLabel: 'Es Tigo',
        hiddenName: 'ES_TIGO',
        valueField: 'value',
        //anchor:'20%',
        //width:100,
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data: [['S', 'S'],
                ['N', 'N']]
        })
    });
    comboLOCALIDAD.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboPERSONA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboRECAUDADOR.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var sucursalModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarSUCURSAL',
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID SUCURSAL',
                name: 'ID_SUCURSAL',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓD. SUCURSAL',
                name: 'CODIGO_SUCURSAL',
                allowBlank: false,
                xtype: 'numberfield'
            }, {
                fieldLabel: 'CÓD. SUCURSAL SET',
                name: 'CODIGO_SUCURSAL_SET',
                xtype: 'numberfield'
            }, {
                fieldLabel: 'NRO. CUENTA',
                name: 'NUMERO_CUENTA',
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRECAUDADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarRECAUDADOR().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                allowBlank: false,
                xtype: 'textfield'
            }, zona, esTigo],
        buttons: [{
                id: 'btnmodificarSUCURSAL',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idSUCURSALSeleccionadoID_SUCURSAL != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    sucursalModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'SUCURSAL?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarSUCURSAL').close();
                                            Ext.getCmp('gridSUCURSAL').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarSUCURSAL').close();
                }
            }]
    });
    sucursalModificarFormPanel.getForm().load({
        url: 'SUCURSAL?op=DETALLE',
        method: 'POST',
        params: {
            ID_SUCURSAL: idSUCURSALSeleccionadoID_SUCURSAL
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar SUCURSAL',
        id: 'modificarSUCURSAL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [sucursalModificarFormPanel]
    });
    return win;
}
function pantallaAgregarSUCURSAL() {
    var comboPERSONA = getCombo('PERSONA', 'PERSONA', 'PERSONA', 'PERSONA', 'DESCRIPCION_PERSONA', 'CONTACTO', 'PERSONA', 'DESCRIPCION_PERSONA', 'PERSONA', 'CONTACTO');
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var comboLOCALIDAD = getCombo('LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD', 'DESCRIPCION_LOCALIDAD', 'LOCALIDAD', 'LOCALIDAD');
    var zona = new Ext.form.ComboBox({
        fieldLabel: 'Zona',
        hiddenName: 'ZONA',
        valueField: 'value',
        //anchor:'20%',
        //width:100,
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data: [['1', 'CAPITAL'],
                ['2', 'INTERIOR']]
        })
    });
    var esTigo = new Ext.form.ComboBox({
        fieldLabel: 'Es Tigo',
        hiddenName: 'ES_TIGO',
        valueField: 'value',
        //anchor:'20%',
        //width:100,
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data: [['S', 'S'],
                ['N', 'N']]
        })
    });
    var sucursalAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarSUCURSAL',
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID SUCURSAL',
                name: 'ID_SUCURSAL',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'CÓD. SUCURSAL',
                name: 'CODIGO_SUCURSAL',
                allowBlank: false,
                xtype: 'numberfield'
            }, {
                fieldLabel: 'CÓD. SUCURSAL SET',
                name: 'CODIGO_SUCURSAL_SET',
                xtype: 'numberfield'
            }, {
                fieldLabel: 'NRO. CUENTA',
                name: 'NUMERO_CUENTA',
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboRECAUDADOR]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarRECAUDADOR().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'DIRECCIÓN',
                name: 'DIRECCION',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboLOCALIDAD]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarLOCALIDAD().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboPERSONA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarPERSONA().show();
                                        }
                                    }]
                            }]
                    }]
            }, {
                fieldLabel: 'TELÉFONO',
                name: 'TELEFONO',
                allowBlank: false,
                xtype: 'textfield'
            }, zona, esTigo],
        buttons: [{
                id: 'btnAgregarSUCURSAL',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                sucursalAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'SUCURSAL?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarSUCURSAL').close();
                                        Ext.getCmp('gridSUCURSAL').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarSUCURSAL').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar SUCURSAL',
        id: 'agregarSUCURSAL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [sucursalAgregarFormPanel]
    });
    return win;
}


/**********************T.PAIS****************************/
var idPAISSeleccionadoID_PAIS;
function gridPAIS() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'PAIS?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'PAIS',
            totalProperty: 'TOTAL',
            id: 'ID_PAIS',
            fields: ['ID_PAIS', 'NOMBRE']
        })
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NOMBRE',
            width: anchoDefaultColumnas,
            dataIndex: 'NOMBRE'
        }]);
    var gridPAIS = new Ext.grid.GridPanel({
        title: 'PAIS',
        id: 'gridPAIS',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarPAIS') == undefined)
                        pantallaAgregarPAIS().show();
                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idPAISSeleccionadoID_PAIS != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'PAIS?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_PAIS: idPAISSeleccionadoID_PAIS
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridPAIS').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idPAISSeleccionadoID_PAIS = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarPAIS") == undefined)) {
                    idPAISSeleccionadoID_PAIS = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarPAIS().show();
                }
            }
        }
    });
    return gridPAIS;
}
function pantallaModificarPAIS() {

    var paisModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarPAIS',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'ID PAIS',
                name: 'ID_PAIS',
                inputType: 'hidden'
            }, {
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false
            }],
        buttons: [{
                id: 'btnmodificarPAIS',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idPAISSeleccionadoID_PAIS != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    paisModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'PAIS?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarPAIS').close();
                                            Ext.getCmp('gridPAIS').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarPAIS').close();
                }
            }]
    });
    paisModificarFormPanel.getForm().load({
        url: 'PAIS?op=DETALLE',
        method: 'POST',
        params: {
            ID_PAIS: idPAISSeleccionadoID_PAIS
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar PAIS',
        id: 'modificarPAIS',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [paisModificarFormPanel]
    });
    return win;
}
function pantallaAgregarPAIS() {
    var paisAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarPAIS',
        labelWidth: 80,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'NOMBRE',
                name: 'NOMBRE',
                allowBlank: false
            }],
        buttons: [{
                id: 'btnAgregarPAIS',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                paisAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'PAIS?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarPAIS').close();
                                        Ext.getCmp('gridPAIS').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarPAIS').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar PAIS',
        id: 'agregarPAIS',
        width: 300,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [paisAgregarFormPanel]
    });
    return win;
}

/**********************T.CUENTA****************************/
var idCUENTASeleccionadoID_CUENTA;
function gridCUENTA() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'CUENTA?op=LISTAR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'CUENTA',
            totalProperty: 'TOTAL',
            id: 'ID_CUENTA',
            fields: ['ID_CUENTA', 'NRO_CUENTA', 'ALIAS_CUENTA', 'ENTIDAD_FINANCIERA']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'NRO CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'NRO_CUENTA'
        }, {
            header: 'ALIAS CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'ALIAS_CUENTA'
        }, {
            header: 'ENTIDAD FINANCIERA',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD_FINANCIERA'
        }]);
    var gridCUENTA = new Ext.grid.GridPanel({
        title: 'CUENTA',
        id: 'gridCUENTA',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add',
                handler: function () {
                    if (Ext.getCmp('agregarCUENTA') == undefined) {
                        pantallaAgregarCUENTA().show();

                    }

                }
            }, {
                text: 'Quitar',
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (idCUENTASeleccionadoID_CUENTA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                            buttons: Ext.Msg.OKCANCEL,
                            animEl: 'elId',
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn) {
                                if (btn == "ok") {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        url: 'CUENTA?op=BORRAR',
                                        method: 'POST',
                                        params: {
                                            ID_CUENTA: idCUENTASeleccionadoID_CUENTA
                                        },
                                        success: function (action) {
                                            obj = Ext.util.JSON.decode(action.responseText);
                                            if (obj.success) {
                                                Ext.Msg.show({
                                                    title: TITULO_ACTUALIZACION_EXITOSA,
                                                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.INFO
                                                });
                                                Ext.getCmp('gridCUENTA').store.reload();
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cuenta',
                id: 'idBusquedaCuenta',
                xtype: 'textfield',
                emptyText: 'Cuenta..',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            Ext.getCmp('gridCUENTA').store.reload({
                                params: {
                                    start: 0,
                                    limit: 20,
                                    ID_CUENTA: esteObjeto.getValue()
                                }
                            });
                        }
                    }
                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                idCUENTASeleccionadoID_CUENTA = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                if ((Ext.getCmp("modificarCUENTA") == undefined)) {
                    idCUENTASeleccionadoID_CUENTA = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarCUENTA().show();
                }
            }
        }
    });
    return gridCUENTA;
}
function pantallaModificarCUENTA() {
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA');
    comboENTIDAD_FINANCIERA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var cuentaModificarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelModificarCUENTA',
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID CUENTA',
                name: 'ID_CUENTA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NRO CUENTA',
                name: 'NRO_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'ALIAS CUENTA',
                name: 'ALIAS_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD_FINANCIERA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD_FINANCIERA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnmodificarCUENTA',
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    if (idCUENTASeleccionadoID_CUENTA != undefined) {
                        Ext.Msg.show({
                            title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.MessageBox.WARNING,
                            fn: function (btn, text) {
                                if (btn == "yes") {
                                    cuentaModificarFormPanel.getForm().submit({
                                        method: 'POST',
                                        waitTitle: WAIT_TITLE_MODIFICANDO,
                                        waitMsg: WAIT_MSG_MODIFICANDO,
                                        url: 'CUENTA?op=MODIFICAR',
                                        success: function (form, accion) {
                                            Ext.Msg.show({
                                                title: TITULO_ACTUALIZACION_EXITOSA,
                                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('modificarCUENTA').close();
                                            Ext.getCmp('gridCUENTA').store.reload();
                                        },
                                        failure: function (form, action) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('modificarCUENTA').close();
                }
            }]
    });
    cuentaModificarFormPanel.getForm().load({
        url: 'CUENTA?op=DETALLE',
        method: 'POST',
        params: {
            ID_CUENTA: idCUENTASeleccionadoID_CUENTA
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar CUENTA',
        id: 'modificarCUENTA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [cuentaModificarFormPanel]
    });
    return win;
}
function pantallaAgregarCUENTA() {
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD FINANCIERA');
    comboENTIDAD_FINANCIERA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var cuentaAgregarFormPanel = new Ext.FormPanel({
        id: 'idFormPanelAgregarCUENTA',
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [{
                fieldLabel: 'ID CUENTA',
                name: 'ID_CUENTA',
                inputType: 'hidden',
                xtype: 'textfield'
            }, {
                fieldLabel: 'NRO CUENTA',
                name: 'NRO_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                fieldLabel: 'ALIAS CUENTA',
                name: 'ALIAS_CUENTA',
                allowBlank: false,
                xtype: 'textfield'
            }, {
                layout: 'column',
                items: [{
                        columnWidth: .9,
                        layout: 'form',
                        items: [comboENTIDAD_FINANCIERA]
                    }, {
                        columnWidth: .1,
                        layout: 'form',
                        items: [{
                                xtype: 'panel',
                                items: [{
                                        xtype: 'button',
                                        iconCls: 'add2',
                                        handler: function () {
                                            pantallaAgregarENTIDAD_FINANCIERA().show();
                                        }
                                    }]
                            }]
                    }]
            }],
        buttons: [{
                id: 'btnAgregarCUENTA',
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "ok") {
                                cuentaAgregarFormPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Asignando...',
                                    url: 'CUENTA?op=AGREGAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarCUENTA').close();
                                        Ext.getCmp('gridCUENTA').store.reload();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('agregarCUENTA').close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar CUENTA',
        id: 'agregarCUENTA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        modal: true,
        resizable: false,
        items: [cuentaAgregarFormPanel]
    });
    return win;
}

var treeSeleccionado = 'nada';
function start() {
    var start = {
        id: 'start-panel',
        title: 'Página Inicial',
        layout: 'fit',
        bodyStyle: 'padding:25px',
        contentEl: 'start-div'
    };
    return start;

}
function tabOperaciones() {
    var treeCRUDS_TABLAS = new Ext.tree.TreePanel({
        id: 'im-tree',
        loader: new Ext.tree.TreeLoader({
            dataUrl: 'CrudTablas?op=LISTAR'
        }),
        rootVisible: false,
        overCls: 'folder',
        lines: true,
        autoScroll: true,
        tools: [{
                id: 'refresh',
                on: {
                    click: function () {
                        var tree = Ext.getCmp('im-tree');
                        tree.body.mask('Loading', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function () {
                            tree.body.unmask();
                            tree.root.expand(true, true);
                        }, 1000);
                    }
                }
            }],
        root: new Ext.tree.AsyncTreeNode({
            id: 'usuarioroles',
            text: 'Usuarios-Roles',
            draggable: false

        }),
        listeners: {
            'click': function (nodo, eventObject) {
                nodo.expand();
                if (nodo.text == 'Esq. Comis.') {
                    //  pantallaConfiguracionEsquemaComisional();
                    Ext.getCmp('content-panel').layout.setActiveItem('idPanelConfiguracionEsquemaComisional');

                } else if (nodo.text == 'Config. Clearing Servicios') {
                    //  pantallaConfiguracionEsquemaComisional();
                    Ext.getCmp('content-panel').layout.setActiveItem('gridCONFIG_CLEARING_SERVICIO');
                    Ext.getCmp('gridCONFIG_CLEARING_SERVICIO').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });

                }
                else if (nodo.text == "Admin. Permisos") {
                    administrarPermisos();

                } else if (nodo.text == "Entidades Politicas") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridENTIDAD_POLITICA');
                    Ext.getCmp('gridENTIDAD_POLITICA').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });

                }
                else if (nodo.text == "Sucursal") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridSUCURSAL');
                    Ext.getCmp('gridSUCURSAL').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });

                } else if (nodo.text == "Entidad Financiera") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridENTIDAD_FINANCIERA');
                    Ext.getCmp('gridENTIDAD_FINANCIERA').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                } else if (nodo.text == "Terminal") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridTERMINAL');
                    Ext.getCmp('gridTERMINAL').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                } else if (nodo.text == "Usuario") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridUSUARIO');
                    Ext.getCmp('gridUSUARIO').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }

                else if (nodo.text == "Entidad") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridENTIDAD');
                    Ext.getCmp('gridENTIDAD').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                } else if (nodo.text == "Franja Horaria") {
                    Ext.getCmp('content-panel').layout.setActiveItem('idPanelFranjaHoraria');
                    Ext.getCmp('gridFRANJA_HORARIA_CAB').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                } else if (nodo.text == "Servicio") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridSERVICIO');
                    Ext.getCmp('gridSERVICIO').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                } else if (nodo.text == "Red") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridRED');
                    Ext.getCmp('gridRED').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Recaudador") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridRECAUDADOR');
                    Ext.getCmp('gridRECAUDADOR').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Facturador") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridFACTURADOR');
                    Ext.getCmp('gridFACTURADOR').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Usuario Terminal") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridUSUARIO_TERMINAL');
                    Ext.getCmp('gridUSUARIO_TERMINAL').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Localidad") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridLOCALIDAD');
                    Ext.getCmp('gridLOCALIDAD').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Ciudad") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridCIUDAD');
                    Ext.getCmp('gridCIUDAD').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Departamento") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridDEPARTAMENTO');
                    Ext.getCmp('gridDEPARTAMENTO').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Pais") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridPAIS');
                    Ext.getCmp('gridPAIS').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Persona") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridPERSONA');
                    Ext.getCmp('gridPERSONA').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }
                else if (nodo.text == "Cuenta") {
                    Ext.getCmp('content-panel').layout.setActiveItem('gridCUENTA');
                    Ext.getCmp('gridCUENTA').store.load({
                        params: {
                            start: 0,
                            limit: 20
                        }
                    });
                }


            }
        }
    });
    var contentPanel = {
        id: 'content-panel',
        region: 'center',
        layout: 'card',
        margins: '2 5 5 0',
        activeItem: 0,
        border: false,
        items: [start(), panelClearingManual(), gridCONFIG_CLEARING_SERVICIO(), pantallaConfiguracionEsquemaComisional(), panelFranjaHoraria(), panelaHabilitacionFacturadoresRed(), panelaHabilitacionServiciosRecaudadores(), gridUSUARIO_TERMINAL(), gridCUENTA(), gridPERSONA(), gridLOCALIDAD(), gridPAIS(), gridDEPARTAMENTO(), gridCIUDAD(), gridENTIDAD_POLITICA(), gridRED(), gridRECAUDADOR(), gridFACTURADOR(), gridUSUARIO(), gridENTIDAD(), gridSUCURSAL(), gridENTIDAD_FINANCIERA(), gridTERMINAL(), gridUSUARIO(), gridENTIDAD(), gridSERVICIO(), gridADMIN_INFO_SYS(), panelControlFormularios(), panelControlServicios(), panelConsultaContribuyentes(), panelAltaCuentasACobrar(), gridADMIN_RED_RECAUDADOR(), gridADMIN_MAIL(), panelCambiarImagenes()]
    };
    var taskOPERACIONES = new Ext.Panel({
        frame: true,
        title: 'Operaciones DGR',
        contentEl: 'task-operaciones',
        collapsible: true,
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });
    var taskOPERACIONES_CS = new Ext.Panel({
        frame: true,
        title: 'Operaciones CS',
        contentEl: 'task-operaciones-cs',
        collapsible: true,
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });
    var taskCRUDS_TABLAS = new Ext.Panel({
        frame: true,
        title: 'Configuraciones',
        collapsible: true,
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'cruds',
        items: [treeCRUDS_TABLAS]
    });
    var taskWIZARD_CRUDS = new Ext.Panel({
        frame: true,
        title: 'Wizard CRUDs',
        collapsible: true,
        contentEl: 'task-complejas',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'wizard-wand'
    });
    var taskREPORTES = new Ext.Panel({
        frame: true,
        title: 'Reportes DGR',
        collapsible: true,
        contentEl: 'task-reportes',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });

    var taskREPORTES_CS = new Ext.Panel({
        frame: true,
        title: 'Reportes CS',
        collapsible: true,
        contentEl: 'task-reportes-cs',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });

    var toolsDocumenta = new Ext.Panel({
        frame: true,
        title: 'Herramientas',
        collapsible: true,
        contentEl: 'toolsDocumenta',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });

    var taskREPORTES_FAC = new Ext.Panel({
        frame: true,
        title: 'Reportes p/Facturadores',
        collapsible: true,
        contentEl: 'task-reportes-fac',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });
    var taskREPORTES_REC = new Ext.Panel({
        frame: true,
        title: 'Reportes p/Recaudadores',
        collapsible: true,
        contentEl: 'task-reportes-rec',
        titleCollapse: true,
        autoScroll: true,
        iconCls: 'operaciones'
    });
    var actionPanel = new Ext.Panel({
        id: 'action-panel',
        region: 'west',
        // split:true,
        collapsible: true,
        margins: '0 0 0 5',
        collapseMode: 'mini',
        width: 200,
        minWidth: 150,
        border: false,
        //baseCls:'x-plain',
        autoScroll: true,
        items: [taskCRUDS_TABLAS, taskWIZARD_CRUDS, taskOPERACIONES, taskOPERACIONES_CS, taskREPORTES_FAC, taskREPORTES_REC, taskREPORTES, taskREPORTES_CS, toolsDocumenta]
    });
    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [new Ext.BoxComponent({
                xtype: 'box',
                region: 'north',
                el: 'header',
                height: 50
            }), contentPanel,
            actionPanel
        ]
    });

    var actions = {
        'HABILITACION_FACTURADORES_RED': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('idPanelHabilitacionFacturadoresRed');
        },
        'HABILITACION_RECAUDADORES_RED': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('idPanelHabilitacionRecaudadoresRed');
        },
        'HABILITACION_SEVICIOS_RECAUDADORES': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('idPanelaHabilitacionServiciosRecaudadores');
        },
        'GENERAR_ERA_FORMULARIOS': function () {
            filtrosGeneracionArchivosEra('ERA_FORMULARIO', 'ERA FORMULARIOS');
        },
        'GENERAR_ERA_BOLETAS_PAGOS_EFECTIVO': function () {
            filtrosGeneracionArchivosEra('ERA_BOLETA_PAGO_EFECTIVO', 'ERA BOLETAS PAGOS EFECTIVO');
        },
        'GENERAR_ERA_BOLETAS_PAGOS_CHEQUE': function () {
            filtrosGeneracionArchivosEra('ERA_BOLETA_PAGO_CHEQUE', 'ERA BOLETAS PAGOS CHEQUE');
        },
        'GENERAR_ARCHIVO_CLEARING_COMISION': function () {
            pantallaARCHIVO_CLEARING_COMISION();
        },
        'GENERAR_ARCHIVO_CLEARING_FACTURACION': function () {
            pantallaARCHIVO_CLEARING_FACTURACION();
        },
        'GENERAR_CLEARING_MANUAL': function () {
            pantallaARCHIVO_CLEARING_MANUAL();
        },
        'CLEARING_MANUAL': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('panelClearingManual');
        },
        'CONSULTA_CONTRIBUYENTES': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('panelConsultaContribuyentes');
        },
        'CERRAR_GRUPOS_RED': function () {
            pantallaCERRAR_GRUPOS_RED();
        },
        'CONSULTAR_PASE': function () {
            pantallaCONSULTAR_PASE();
        },
        'CONTROL_FORMULARIOS': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('panelControlFormularios');
            Ext.getCmp('gridControlFormularios').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
            Ext.getCmp('gridControlFormulariosBoletas').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });

        },
        'CONTROL_SERVICIOS': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('panelControlServicios');
            Ext.getCmp('gridControlServicios').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
        },
        'CAMBIAR_IMAGEN_SERV': function () {
            Ext.Msg.wait('Procesando... Por Favor espere...');
            $.ajax({
                type: "POST",
                url: 'Servicios',
                async: false,
                success: function (data) {
                    Ext.Msg.hide();
                    localStorage.setItem("facturadores", data);
                    Ext.getCmp('content-panel').layout.setActiveItem('panelCambiarImagen');
                },
                error: function () {
                    alert('No se pudo obtener los facturadores');
                }
            });

        },
        'LOGOUT': function () {
            Ext.Msg.show({
                title: 'Estado',
                msg: '¿Está seguro que desea cerrar la sesión?',
                icon: Ext.MessageBox.WARNING,
                buttons: Ext.Msg.OKCANCEL,
                fn: function (btn) {
                    if (btn == 'ok') {
                        var conn = new Ext.data.Connection();
                        conn.request({
                            url: 'inicial?op=LOGOUT',
                            method: 'POST',
                            success: function (action) {
                                var obj = Ext.util.JSON.decode(action.responseText);
                                if (obj.success) {
                                    var redirect = 'login.jsp';
                                    window.location = redirect;
                                } else {
                                    Ext.Msg.show({
                                        title: 'Estado',
                                        msg: obj.motivo,
                                        animEl: 'elId',
                                        icon: Ext.MessageBox.ERROR,
                                        buttons: Ext.Msg.OK

                                    });
                                }

                            },
                            failure: function (action) {
                                Ext.Msg.show({
                                    title: 'Estado',
                                    msg: 'No se puede cerrar la sesión',
                                    animEl: 'elId',
                                    icon: Ext.MessageBox.ERROR,
                                    buttons: Ext.Msg.OK

                                });

                            }
                        });
                    }
                }

            });
        },
        'RE_IMPRESION_FORMULARIO': function () {
            reImprimirFormulario();
        },
        'CAMBIAR_PASSWORD': function () {
            pantallaModificarContrasenhaDeLogeo();
        },
        'CAPTURADOR_ARCHIVO': function () {
            pantallaCapturadorArchivos();
        },
        'MULTICANAL_CARGAR_ARCHIVO_FACTURACION': function () {
            pantallaMulticanalCargarArchivoFacturacion();
        },
        'REPORTE_DECLARACION_POR_NUMERO_ORDEN': function () {
            lanzarReporte('idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN');
        },
        'REPORTE_TAPA_CAJA': function () {
            lanzarReporte('idPanelREPORTE_TAPA_CAJA');
        },
        'REPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN': function () {
            lanzarReporte('idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN');
        },
        'REPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO': function () {
            lanzarReporte('idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO');
        },
        'REPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO': function () {
            lanzarReporte('idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO');
        },
        'REPORTE_COBRANZA_DETALLADO_CHEQUE': function () {
            lanzarReporte('idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE');
        },
        'REPORTE_COBRANZA_DETALLADO_EFECTIVO': function () {
            lanzarReporte('idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO');
        },
        'TAPA_LOTE': function () {
            lanzarReporte('idPanelREPORTE_TAPALOTE');
        },
        'TAPA_LOTE_DETALLADO': function () {
            lanzarReporte('idPanelREPORTE_TAPALOTE_DETALLADO');
        },
        'REPORTE_CIERRE_RESUMIDO': function () {
            lanzarReporte('idPanelREPORTE_CIERRE_RESUMIDO');
        },
        'REPORTE_CIERRE_DETALLADO': function () {
            lanzarReporte('idPanelREPORTE_CIERRE_DETALLADO');
        },
        'REPORTE_FORMULARIO_OT': function () {
            lanzarReporte('idPanelREPORTE_FORMULARIO_OT');
        },
        'REPORTE_CLEARING_COMISION_RESUMIDO': function () {
            lanzarReporte('idPanelRESUMEN_CLEARING_COMISION');
        },
        'REPORTE_RECAUDACION': function () {
            lanzarReporte('idPanelREPORTE_RESUMEN_DE_TRANSACCIONES');
        },
        'REPORTE_CIERRE_CS': function () {
            lanzarReporte('idPanelREPORTE_CIERRE_CS');
        },
        'REPORTE_COBRANZA_CS': function () {
            lanzarReporte('idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS');
        },
        'REPORTE_CIERRE_USUARIOS': function () {
            lanzarReporte('idPanelREPORTE_CIERRE_USUARIO');
        },
        'REPORTE_TERMINALES_ABIERTAS': function () {
            lanzarReporte('idPanelREPORTE_TERMINALES_ABIERTAS');
        },
        'REPORTE_FACTURADOR_CS': function () {
            lanzarReporte('idPanelREPORTE_RESUMEN_FACTURADOR');
        },
        'REPORTE_COMISION_CS': function () {
            lanzarReporte('idPanelREPORTE_COMISION_CS');
        },
        'REPORTE_MOVIMIENTO_FACTURADORES': function () {
            lanzarReporte('idPanelREPORTE_MOVIMIENTO_FACTURADORES');
        },
        'REPORTE_CLEARING_CS': function () {
            lanzarReporte('idPanelREPORTE_CLEARING_CS');
        },
        'REPORTE_SAN_CRISTOBAL': function () {
            lanzarReporte('idPanelREPORTE_SAN_CRISTOBAL');
        },
        'REPORTE_FACTURACION_ADMINISTRACION': function () {
            lanzarReporte('idPanelREPORTE_FACTURACION_ADMINISTRACION');
        },
        'REPORTE_COMISION_DET_REC': function () {
            lanzarReporte('idPanelREPORTE_COMISION_DET_REC');
        },
        'REPORTE_COMISION_DET_FAC': function () {
            lanzarReporte('idPanelREPORTE_COMISION_DET_FAC');
        },
        'REPORTE_COMISION_RES_GRAL_REC': function () {
            lanzarReporte('idPanelREPORTE_COMISION_RES_GRAL_REC');
        },
        'REPORTE_COMISION_RES_FAC': function () {
            lanzarReporte('idPanelREPORTE_COMISION_RES_FAC');
        },
        'SOLICITUD_TRANSACCION': function () {
            lanzarReporte('SOLICITUD_TRANSACCION');
        },
        'REPORTE_DIGITACION': function () {
            lanzarReporte('REPORTE_DIGITACION');
        },
        'REPORTE_DESEMBOLSO': function () {
            lanzarReporte('REPORTE_DESEMBOLSO');
        },
        'INFORMACION_SYS': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('gridADMIN_INFO_SYS');
            Ext.getCmp('gridADMIN_INFO_SYS').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
        },
        'ALTA_CUENTAS_A_COBRAR': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('panelAltaCuentasACobrar');
        },
        'ADMIN_RED_RECAUDADOR': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('gridADMIN_RED_RECAUDADOR');
            Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
        },
        'ADMIN_MAIL': function () {
            Ext.getCmp('content-panel').layout.setActiveItem('gridADMIN_MAIL');
            Ext.getCmp('gridADMIN_MAIL').store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
        }


    };
    function doAction(e, t) {
        try {
            actions[t.id]();
        } catch (err) {
            ;
        }
    }
    var ab = actionPanel.body;
    ab.on('mousedown', doAction, null, {
        delegate: 'a'
    });
    ab.on('click', Ext.emptyFn, null, {
        delegate: 'a',
        preventDefault: true
    });
}
var GRID_COMISIONAL_ENDTIDAD_FINANCIERA = "";
var GRID_COMISIONAL_ID_COMISION = "";
var GRID_COMISIONAL_ROL_COMISIONISTA = "";
var GRID_COMISIONAL_VALOR = "";
var GRID_CONCEPTO_COMISION = "";
var ID_REGISTRO_COMISIONAL_SELECCIONADO = null;

function gridPanelEsquemaComisional() {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'CONFIGURACION_COMISIONAL?op=OBTENER_PLANTILLA_CONFIGURACION'
        }),
        reader: new Ext.data.JsonReader({
            root: 'DETALLE',
            totalProperty: 'TOTAL',
            id: 'ID_FILA',
            fields: ['ID_FILA', 'BENEFICIARIO_ID', 'ROL_COMISIONISTA', 'BENEFICIARIO_DESCRIPCION', 'VALOR', 'ROL_COMISIONISTA_DESCRIPCION', 'ROL_COMISIONISTA']
        })
    });
    var anchoDefaultColumnas = 180;
    var colModel = new Ext.grid.ColumnModel([
        {
            header: 'ROL',
            width: anchoDefaultColumnas,
            dataIndex: 'ROL_COMISIONISTA_DESCRIPCION'
        }, {
            header: 'BENEFICIARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'BENEFICIARIO_DESCRIPCION'
        }, {
            header: 'VALOR',
            width: anchoDefaultColumnas,
            dataIndex: 'VALOR'
        }]);

    var grid = new Ext.grid.GridPanel({
        id: 'idGridComisional',
        store: st,
        loadMask: true,
        height: 200,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                id: 'idBtnAgregarGridComisional',
                text: 'Agregar',
                tooltip: 'Agrega un registro',
                iconCls: 'add',
                disabled: true,
                handler: function () {
                    pantallaEsquemaComisionalBeneficiarioValorAgregar();
                }
            }, {
                id: 'idBtnGuardarGridComisional',
                text: 'Guardar',
                tooltip: 'Guardar o agregar registro',
                iconCls: 'add',
                disabled: true,
                handler: function () {
                    guardarConfiguracionComisional();
                }
            }],
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'cellclick': function (esteObjeto, rowIndex, columnIndex, e) {
                ID_REGISTRO_COMISIONAL_SELECCIONADO = esteObjeto.getStore().getAt(rowIndex).id;
                GRID_COMISIONAL_VALOR = "";
                GRID_CONCEPTO_COMISION = "";
                GRID_ENTIDAD_FINANCIERA = "";
                Ext.each(
                        esteObjeto.getSelectionModel().getSelections(),
                        function (row) {
                            GRID_COMISIONAL_VALOR = row.data.VALOR;
                            GRID_CONCEPTO_COMISION = row.data.CONCEPTO_COMISION;
                            GRID_ENTIDAD_FINANCIERA = row.data.BENEFICIARIO_ID;
                        });

                var selector = esteObjeto.getStore().getAt(rowIndex).data.ROL_COMISIONISTA;
                selectorCardLayoutGridComisionalDetalle(selector, false);

            }
        }
    });

    return grid;
}

function selectorCardLayoutGridComisionalDetalle(selector, conSelectioModel) {
    if (conSelectioModel) {
        Ext.each(
                Ext.getCmp('idGridComisional').getSelectionModel().getSelections(),
                function (row) {

                    selector = row.data.ROL_COMISIONISTA;
                });
    }
    if (selector == 1 || selector == 2 || selector == 4 || selector == 5 || selector == 3) {
        Ext.getCmp('cardFormulariosEsquemaComisional').layout.setActiveItem('idpanelEsquemaComisionalConceptoComisionValor');
        // Ext.getCmp('idpanelEsquemaComisionalConceptoComisionValor').findById('idEsqComiConceptoComiValorComboComision').setValue(GRID_CONCEPTO_COMISION);
        Ext.getCmp('idpanelEsquemaComisionalConceptoComisionValor').findById('idEsqComiConceptoComiValorFieldValor').setValue(GRID_COMISIONAL_VALOR);
    }
    //    else if(selector == 3){
    //        Ext.getCmp('cardFormulariosEsquemaComisional').layout.setActiveItem('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor');
    //        //  Ext.getCmp('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor').findById('idEsqComiBeneficiarioConceptoComisionValorComboComision').setValue(GRID_CONCEPTO_COMISION);
    //        Ext.getCmp('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor').findById('idEsqComiBeneficiarioConceptoComisionValorComboBeneficiario').setValue(GRID_ENTIDAD_FINANCIERA);
    //        Ext.getCmp('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor').findById('idEsqComiBeneficiarioConceptoComisionValorFieldValor').setValue(GRID_COMISIONAL_VALOR);
    //
    //    }
    else {
        Ext.getCmp('cardFormulariosEsquemaComisional').layout.setActiveItem('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor');
        //Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorComboComision').setValue(GRID_CONCEPTO_COMISION);
        Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorComboBeneficiario').setValue(GRID_ENTIDAD_FINANCIERA);
        Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorFieldValor').setValue(GRID_COMISIONAL_VALOR);
    }

}
function gridPanelEsquemaComisionalCabeceraInferior(comboRED, comboSERVICIO, comboTIPO_CLEARING, comboRECAUDADOR, comboSUCURSAL) {
    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'CONFIGURACION_COMISIONAL?op=VER_RANGOS'
        }),
        reader: new Ext.data.JsonReader({
            root: 'RANGOS',
            totalProperty: 'TOTAL',
            id: 'ID_RANGO',
            fields: ['ID_RANGO', 'DESDE', 'HASTA', 'TOTAL_REPARTIR']
        })
    });
    var anchoDefaultColumnas = 130;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'DESDE',
            width: 60,
            dataIndex: 'DESDE'
        }, {
            header: 'HASTA',
            width: 60,
            dataIndex: 'HASTA'
        }, {
            header: 'TOTAL REPARTIR',
            width: anchoDefaultColumnas,
            dataIndex: 'TOTAL_REPARTIR'
        }]);

    var grid = new Ext.grid.GridPanel({
        id: 'idGridComisionalCabeceraInferior',
        store: st,
        height: 200,
        cm: colModel,
        autoScroll: true,
        loadMask: true,
        margins: '5px 5px 5px 5px',
        frame: true,
        border: false,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Agregar',
                id: 'idBtnAgregarCabeceraInferior',
                tooltip: 'Agregar registro',
                iconCls: 'add',
                disabled: true,
                handler: function () {
                    pantallaAgregarGridRangosRepartir(grid, comboRED, comboSERVICIO, comboTIPO_CLEARING);
                }
            }, {
                text: 'Quitar',
                id: 'idBtnQuitarCabeceraInferior',
                tooltip: 'Quitar registro',
                iconCls: 'remove',
                disabled: true,
                handler: function () {
                    var idRango;
                    Ext.each(
                            grid.getSelectionModel().getSelections(),
                            function (row) {
                                idRango = row.data.ID_RANGO
                            });

                    var conn = new Ext.data.Connection();
                    conn.request({
                        url: 'CONFIGURACION_COMISIONAL?op=ELIMINAR_RANGO_ASIGNAR',
                        method: 'POST',
                        params: {
                            ID_RANGO: idRango
                        },
                        success: function (action) {
                            var obj = Ext.util.JSON.decode(action.responseText);
                            if (obj.success) {
                                grid.store.reload();
                            } else {
                                Ext.Msg.show({
                                    title: 'Estado',
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                        },
                        failure: function (action) {
                            Ext.Msg.show({
                                title: 'Estado',
                                msg: 'No se pudo realizar la operación.',
                                buttons: Ext.Msg.OK,
                                animEl: 'elId',
                                icon: Ext.MessageBox.ERROR
                            });
                        }
                    });
                }
            }],
        iconCls: 'icon-grid'
    });
    grid.addListener('cellclick', function (esteObjeto, rowIndex, columnIndex, e) {
        cargarDetalleEsquemaComisional(grid, Ext.getCmp('idGridComisional'), Ext.getCmp('idFormCabeceraEsquemaComisional'));
        Ext.getCmp('idBtnQuitarCabeceraInferior').enable();
        Ext.getCmp('idBtnAgregarGridComisional').enable();
        Ext.getCmp('idBtnGuardarGridComisional').enable();
        //Ext.getCmp('idBtnDetalleCabecera').enable();
        comboRECAUDADOR.enable();
        comboSUCURSAL.enable();
    }, null, null);
    grid.addListener('celldblclick', function (esteObjeto, rowIndex, columnIndex, e) {
        pantallaModificarGridRangosRepartir(grid, esteObjeto.getStore().getAt(rowIndex).id, comboRED.getValue(), comboSERVICIO.getValue(), comboTIPO_CLEARING.getValue());
    }, null, null);
    return grid;
}


function panelCabeceraEsquemaComisional(elGridComisional) {

    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    var comboSERVICIO = getCombo("SERVICIO_COMISIONAL", "SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO", "DESCRIPCION_SERVICIO", "SERVICIO", "SERVICIO");
    var comboTIPO_CLEARING = getCombo("TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO CLEARING", "TIPO_CLEARING", "DESCRIPCION_TIPO_CLEARING", "TIPO_CLEARING", "TIPO CLEARING");
    var comboRECAUDADOR = getCombo("RECAUDADOR_COMISIONAL", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    var gridCabeceraInferior = gridPanelEsquemaComisionalCabeceraInferior(comboRED, comboSERVICIO, comboTIPO_CLEARING, comboRECAUDADOR, comboSUCURSAL);

    comboRED.addListener('select', function (esteCombo, record, index) {
        //  console.log( Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorComboBeneficiario'));

        Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorComboBeneficiario').store.baseParams['ID_RED'] = record.data.RED;
    }, null, null);
    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {
        // console.log(newValue);
        if (esteCombo.getRawValue().length == 0) {
            Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').findById('idEsqComiBeneficiarioEntidadConceptoComisionValorComboBeneficiario').store.baseParams['ID_RED'] = "";
        }

    }, null, null);


    comboRED.id = 'idCabEsquemaComiComboRed';
    comboSERVICIO.id = 'idCabEsquemaComiComboServicio';
    comboRECAUDADOR.id = 'idCabEsquemaComiComboRecaudador';
    comboSUCURSAL.id = 'idCabEsquemaComiComboSucursal';
    comboTIPO_CLEARING.id = 'idCabEsquemaComiComboTipoClearing';
    comboSERVICIO.disable();
    comboSERVICIO.allowBlank = false;
    comboRED.allowBlank = false;
    comboRECAUDADOR.allowBlank = true;
    comboSUCURSAL.allowBlank = true;
    comboTIPO_CLEARING.allowBlank = false;

    comboRED.addListener('select', function (esteCombo, record, index) {
        resetBtnEsquemaComisionalCabecera();
        comboRECAUDADOR.reset();
        comboSUCURSAL.reset();
        comboRECAUDADOR.disable();
        comboSUCURSAL.disable();
        //Ext.getCmp('idBtnDetalleCabecera').disable();
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['RED'] = record.data.RED;
        verRango(formPanel, gridCabeceraInferior, comboRED, comboSERVICIO, comboTIPO_CLEARING);

    }, null, null);

    comboTIPO_CLEARING.addListener('select', function (esteCombo, newValue, oldValue) {
        resetBtnEsquemaComisionalCabecera();
        comboRECAUDADOR.reset();
        comboSUCURSAL.reset();
        comboRECAUDADOR.disable();
        comboSUCURSAL.disable();
        verRango(formPanel, gridCabeceraInferior, comboRED, comboSERVICIO, comboTIPO_CLEARING);

        //        if(formPanel.getForm().isValid()){
        //            alert("LLAMAR");
        //            verRango(formPanel, gridCabeceraInferior, comboRED,comboSERVICIO,comboTIPO_CLEARING);
        //        }
        //Ext.getCmp('idBtnDetalleCabecera').disable();
    }, null, null);
    comboRECAUDADOR.addListener('select', function (esteCombo, record, index) {
        cargarDetalleEsquemaComisional(Ext.getCmp('idGridComisionalCabeceraInferior'), Ext.getCmp('idGridComisional'), Ext.getCmp('idFormCabeceraEsquemaComisional'));
        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR
        comboSUCURSAL.store.baseParams['limit'] = 10;
        comboSUCURSAL.store.baseParams['start'] = 0;
        comboSUCURSAL.store.reload();
    }, null, null);
    comboSERVICIO.addListener('select', function (esteCombo, record, index) {
        resetBtnEsquemaComisionalCabecera();
        comboRECAUDADOR.reset();
        comboSUCURSAL.reset();
        comboRECAUDADOR.disable();
        comboSUCURSAL.disable();
        comboRECAUDADOR.store.baseParams['ID_SERVICIO'] = record.data.SERVICIO
        comboRECAUDADOR.store.baseParams['limit'] = 10;
        comboRECAUDADOR.store.baseParams['start'] = 0;
        comboRECAUDADOR.store.reload();
        verRango(formPanel, gridCabeceraInferior, comboRED, comboSERVICIO, comboTIPO_CLEARING);
    }, null, null);
    comboSUCURSAL.addListener('select', function (esteCombo, record, index) {
        cargarDetalleEsquemaComisional(Ext.getCmp('idGridComisionalCabeceraInferior'), Ext.getCmp('idGridComisional'), Ext.getCmp('idFormCabeceraEsquemaComisional'));
    }, null, null);
    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            resetBtnEsquemaComisionalCabecera();
            comboSERVICIO.disable();
            comboSERVICIO.reset();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();
        }
    }, null, null);
    comboSERVICIO.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            resetBtnEsquemaComisionalCabecera();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();
        }
    }, null, null);
    comboTIPO_CLEARING.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            resetBtnEsquemaComisionalCabecera();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();

        }
    }, null, null);
    comboRECAUDADOR.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            cargarDetalleEsquemaComisional(Ext.getCmp('idGridComisionalCabeceraInferior'), Ext.getCmp('idGridComisional'), Ext.getCmp('idFormCabeceraEsquemaComisional'));
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
        }
    }, null, null);
    comboSUCURSAL.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            cargarDetalleEsquemaComisional(Ext.getCmp('idGridComisionalCabeceraInferior'), Ext.getCmp('idGridComisional'), Ext.getCmp('idFormCabeceraEsquemaComisional'));
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
        }
    }, null, null);
    comboSUCURSAL.disable();
    comboRECAUDADOR.disable();
    //    var btnVerRango =  new Ext.Button({
    //        formBind : true,
    //        text : 'Ver Rango',
    //        handler:function(){
    //
    //            if(formPanel.getForm().isValid()){
    //                gridCabeceraInferior.store.load({
    //                    params:{
    //                        RED: comboRED.getValue(),
    //                        SERVICIO:comboSERVICIO.getValue(),
    //                        TIPO_CLEARING :comboTIPO_CLEARING.getValue(),
    //                        start:0,
    //                        limit:10
    //                    },
    //                    waitMsg : 'Cargando...'
    //                });
    //            }
    //        }
    //    });

    var formPanel = new Ext.FormPanel({
        id: 'idFormCabeceraEsquemaComisional',
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboRED, comboSERVICIO, comboTIPO_CLEARING, {
                xtype: 'panel',
                height: 5
            }, gridCabeceraInferior, {
                xtype: 'panel',
                height: 5
            }, comboRECAUDADOR, comboSUCURSAL],
        buttons: [
            //            {
            //            text : 'Detalle',
            //            id:'idBtnDetalleCabecera',
            //            disabled:true,
            //            //formBind : true,
            //            handler : function() {
            //                cargarDetalleEsquemaComisional(gridCabeceraInferior,elGridComisional,formPanel);
            //                var idRango ;
            //                Ext.each(
            //                    gridCabeceraInferior.getSelectionModel().getSelections(),
            //                    function(row) {
            //                        idRango = row.data.ID_RANGO
            //                    }
            //                    );
            //                elGridComisional.store.baseParams['ID_RANGO'] = idRango;
            //                elGridComisional.store.baseParams['RECAUDADOR'] = formPanel.getForm().getValues().RECAUDADOR;
            //                elGridComisional.store.baseParams['RED'] =   formPanel.getForm().getValues().RED;
            //                elGridComisional.store.baseParams['SERVICIO'] = formPanel.getForm().getValues().SERVICIO;
            //                elGridComisional.store.baseParams['SUCURSAL'] = formPanel.getForm().getValues().SUCURSAL;
            //                elGridComisional.store.baseParams['TIPO_CLEARING'] = formPanel.getForm().getValues().TIPO_CLEARING;
            //                elGridComisional.store.reload();
            //            }
            //        },
            {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    resetBtnEsquemaComisionalCabecera();
                    //                Ext.getCmp('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor').getForm().reset();
                    //                Ext.getCmp('idpanelEsquemaComisionalConceptoComisionValor').getForm().reset();
                    //                Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').getForm().reset();
                    //                Ext.getCmp('idGridComisional').getStore().removeAll();
                    //                Ext.getCmp('idGridComisionalCabeceraInferior').getStore().removeAll();
                    //                Ext.getCmp('idBtnDetalleCabecera').disable();
                }
            }]
    });
    //Hace un loop infinito. Eliminar si hay problemas.
    formPanel.addListener('clientvalidation', function (esteObjeto, valido) {
        if (valido) {

            Ext.getCmp('idBtnAgregarCabeceraInferior').enable();
            //Ext.getCmp('idBtnQuitarCabeceraInferior').enable();

        } else {

            Ext.getCmp('idBtnAgregarCabeceraInferior').disable();

        }

    }, null, null);

    return formPanel;
}
function verRango(elFormPanel, elGridCabeceraInferior, elComboRED, elComboSERVICIO, elComboTIPO_CLEARING) {
    if (elFormPanel.getForm().isValid()) {
        elGridCabeceraInferior.store.load({
            params: {
                RED: elComboRED.getValue(),
                SERVICIO: elComboSERVICIO.getValue(),
                TIPO_CLEARING: elComboTIPO_CLEARING.getValue()
            },
            waitMsg: 'Cargando...'
        });
    }

}
function cargarDetalleEsquemaComisional(elGridCabeceraInferior, elGridComisional, elFormPanel) {
    var idRango;
    Ext.each(
            elGridCabeceraInferior.getSelectionModel().getSelections(),
            function (row) {
                idRango = row.data.ID_RANGO
            });
    elGridComisional.store.baseParams['ID_RANGO'] = idRango;
    elGridComisional.store.baseParams['RECAUDADOR'] = elFormPanel.getForm().getValues().RECAUDADOR;
    elGridComisional.store.baseParams['RED'] = elFormPanel.getForm().getValues().RED;
    elGridComisional.store.baseParams['SERVICIO'] = elFormPanel.getForm().getValues().SERVICIO;
    elGridComisional.store.baseParams['SUCURSAL'] = elFormPanel.getForm().getValues().SUCURSAL;
    elGridComisional.store.baseParams['TIPO_CLEARING'] = elFormPanel.getForm().getValues().TIPO_CLEARING;
    elGridComisional.store.reload();

}
function resetBtnEsquemaComisionalCabecera() {
    Ext.getCmp('cardFormulariosEsquemaComisional').layout.setActiveItem('idPanelEsquemaComisionalStartCard');
    Ext.getCmp('idpanelEsquemaComisionalBeneficiarioConceptoComisionValor').getForm().reset();
    Ext.getCmp('idpanelEsquemaComisionalConceptoComisionValor').getForm().reset();
    Ext.getCmp('idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor').getForm().reset();
    Ext.getCmp('idGridComisional').getStore().removeAll();
    Ext.getCmp('idGridComisionalCabeceraInferior').getStore().removeAll();
    Ext.getCmp('idBtnQuitarCabeceraInferior').disable();
    Ext.getCmp('idBtnAgregarGridComisional').disable();
    Ext.getCmp('idBtnGuardarGridComisional').disable();
// Ext.getCmp('idBtnDetalleCabecera').disable();
}

function pantallaConfiguracionEsquemaComisional() {
    var gridComisional = gridPanelEsquemaComisional();
    var panel = new Ext.Panel({
        id: 'idPanelConfiguracionEsquemaComisional',
        title: ' Configuración Esquema Comisional',
        layout: 'border',
        closable: true,
        resizable: true,
        items: [{
                title: 'Cabecera',
                region: 'west',
                margins: '5 0 0 0',
                cmargins: '5 5 0 0',
                width: 300,
                frame: true,
                items: panelCabeceraEsquemaComisional(gridComisional)
            }, {
                title: 'Detalle',
                collapsible: false,
                region: 'center',
                margins: '5 0 0 0',
                cmargins: '5 5 0 0',
                width: 'auto',
                frame: true,
                items: [{
                        layout: 'anchor',
                        items: [{
                                height: 'auto',
                                anchor: '100%',
                                items: gridComisional
                            }, {
                                height: 5,
                                anchor: '100%'
                            }, {
                                anchor: '100%',
                                items: cardFormulariosEsquemaComisional()

                            }]
                    }]
            }]
    });

    //    var win = new Ext.Window({
    //        title:' Configuración Esquema Comisional',
    //        width : 730,
    //        height : 450,
    //        layout: 'border',
    //        closable : true,
    //        resizable : true,
    //        modal:true,
    //        items: [{
    //            title: 'Cabecera',
    //            region:'west',
    //            margins: '5 0 0 0',
    //            cmargins: '5 5 0 0',
    //            width: 300,
    //            frame : true,
    //            items: panelCabeceraEsquemaComisional(gridComisional)
    //        },{
    //            title: 'Detalle',
    //            collapsible: false,
    //            region:'center',
    //            margins: '5 0 0 0',
    //            cmargins: '5 5 0 0',
    //            width:'auto',
    //            frame : true,
    //            items:[{
    //                layout: 'anchor',
    //                items: [{
    //                    height:240,
    //                    anchor: '100%',
    //                    items: gridComisional
    //                },{
    //                    height: 5,
    //                    anchor: '100%'
    //                },{
    //                    anchor: '100%',
    //
    //                    items:cardFormulariosEsquemaComisional()
    //
    //                }]
    //            }]
    //        }]
    //
    //    });
    // win.show();
    return panel;
}
function cardFormulariosEsquemaComisional() {
    var cardPanel = {
        id: 'cardFormulariosEsquemaComisional',
        layout: 'card',
        margins: '2 5 5 0',
        activeItem: 0,
        border: true,
        items: [panelEsquemaComisionalStartCard(), panelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor(), panelEsquemaComisionalConceptoComisionValor(), panelEsquemaComisionalBeneficiarioConceptoComisionValor()]
    };

    return cardPanel;
}
function panelEsquemaComisionalConceptoComisionValor() {


    var valorTextField = new Ext.form.TextField({
        id: 'idEsqComiConceptoComiValorFieldValor',
        fieldLabel: 'VALOR',
        name: 'VALOR',
        xtype: 'textfield',
        enableKeyEvents: true,
        listeners: {
            'specialkey': function (esteObjeto, esteEvento) {
                if (esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                    refreshGridComisional(valorTextField.getRawValue());

                }
            },
            'change': function (esteObjeto) {
                esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                refreshGridComisional(valorTextField.getRawValue());

            },
            'keyup': function (esteObjeto, esteEvento) {
                //   console.log(esteEvento.getKey());
                // if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){


                //esteObjeto.setRawValue(replaceAllNoNumbers3(esteObjeto.getRawValue()));
                //esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                //       }else
                if (esteEvento.getKey() == 40) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));

                }
            }
        }
    });



    var formPanel = new Ext.FormPanel({
        id: 'idpanelEsquemaComisionalConceptoComisionValor',
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [valorTextField]
    });
    return formPanel;

}
function panelEsquemaComisionalBeneficiarioConceptoComisionValor() {

    var comboENTIDAD_FINANCIERA = getCombo("ENTIDAD_FINANCIERA", "ENTIDAD_FINANCIERA", "ENTIDAD_FINANCIERA", "ENTIDAD_FINANCIERA", "DESCRIPCION_ENTIDAD_FINANCIERA", "BENEFICIARIO", "ENTIDAD_FINANCIERA", "DESCRIPCION_ENTIDAD_FINANCIERA", "ENTIDAD_FINANCIERA", "BENEFICIARIO");

    comboENTIDAD_FINANCIERA.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    comboENTIDAD_FINANCIERA.id = 'idEsqComiBeneficiarioConceptoComisionValorComboBeneficiario';

    comboENTIDAD_FINANCIERA.addListener('select', function (esteCombo, record, index) {
        Ext.getCmp('idGridComisional').getStore().getById(ID_REGISTRO_COMISIONAL_SELECCIONADO).data.BENEFICIARIO_DESCRIPCION = record.data.DESCRIPCION_ENTIDAD_FINANCIERA;
        Ext.getCmp('idGridComisional').getStore().getById(ID_REGISTRO_COMISIONAL_SELECCIONADO).data.BENEFICIARIO_ID = record.data.ENTIDAD_FINANCIERA;
        Ext.getCmp('idGridComisional').getView().refresh();

    }, null, null);
    var valorTextField = new Ext.form.TextField({
        id: 'idEsqComiBeneficiarioConceptoComisionValorFieldValor',
        fieldLabel: 'VALOR',
        name: 'VALOR',
        xtype: 'textfield',
        enableKeyEvents: true,
        listeners: {
            'specialkey': function (esteObjeto, esteEvento) {
                if (esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                    refreshGridComisional(valorTextField.getRawValue());

                }
            },
            'change': function (esteObjeto) {
                esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                refreshGridComisional(valorTextField.getRawValue());

            },
            'keyup': function (esteObjeto, esteEvento) {

                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000.0'));
                //                }else
                if (esteEvento.getKey() == 40) {

                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                }

            }
        }
    });

    var formPanel = new Ext.FormPanel({
        id: 'idpanelEsquemaComisionalBeneficiarioConceptoComisionValor',
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboENTIDAD_FINANCIERA, valorTextField]
    });
    return formPanel;
}
function panelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor() {
    var comboENTIDAD = getCombo("ENTIDAD", "ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD");
    comboENTIDAD.store.baseParams['ID_RED'] = Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRed').getValue();
    //  console.log(Ext.getCmp('idFormCabeceraEsquemaComisional'));
    //  console.log(Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRed'));
    comboENTIDAD.id = 'idEsqComiBeneficiarioEntidadConceptoComisionValorComboBeneficiario';
    comboENTIDAD.addListener('select', function (esteCombo, record, index) {
        if (ID_REGISTRO_COMISIONAL_SELECCIONADO != null) {
            Ext.getCmp('idGridComisional').getStore().getById(ID_REGISTRO_COMISIONAL_SELECCIONADO).data.BENEFICIARIO_DESCRIPCION = record.data.DESCRIPCION_ENTIDAD;
            Ext.getCmp('idGridComisional').getStore().getById(ID_REGISTRO_COMISIONAL_SELECCIONADO).data.BENEFICIARIO_ID = record.data.ENTIDAD;
            Ext.getCmp('idGridComisional').getView().refresh();
        }
    }, null, null);
    var valorTextField = new Ext.form.TextField({
        id: 'idEsqComiBeneficiarioEntidadConceptoComisionValorFieldValor',
        fieldLabel: 'VALOR',
        name: 'VALOR',
        xtype: 'textfield',
        enableKeyEvents: true,
        listeners: {
            'specialkey': function (esteObjeto, esteEvento) {
                if (esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                    refreshGridComisional(valorTextField.getRawValue());
                }
            },
            'change': function (esteObjeto) {
                esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                refreshGridComisional(valorTextField.getRawValue());

            },
            'keyup': function (esteObjeto, esteEvento) {

                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000.0'));
                //                }else
                if (esteEvento.getKey() == 40 || esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                }
            }
        }
    });
    var formPanel = new Ext.FormPanel({
        id: 'idpanelEsquemaComisionalBeneficiarioEntidadConceptoComisionValor',
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboENTIDAD, valorTextField]
    });
    return formPanel;
}
function refreshGridComisional(valor) {
    Ext.getCmp('idGridComisional').getStore().getById(ID_REGISTRO_COMISIONAL_SELECCIONADO).data.VALOR = valor;
    Ext.getCmp('idGridComisional').getView().refresh();

}
function panelEsquemaComisionalStartCard() {
    var panel = {
        id: 'idPanelEsquemaComisionalStartCard',
        xtype: 'panel',
        layout: 'fit',
        border: false,
        autoScroll: true
    }
    return panel;

}
function panelEsquemaComisionalBeneficiarioValorParaAgregar() {

    var comboENTIDAD = getCombo("ENTIDAD", "ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD");
    comboENTIDAD.store.baseParams['ID_RED'] = Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRed').getValue();
    comboENTIDAD.store.load({
        params: {
            start: 0,
            limit: 25

        }
    });

    comboENTIDAD.allowBlank = false;
    comboENTIDAD.id = 'idComboEntidadAgregarEsquemaComisional';
    comboENTIDAD.allowBlank = false;
    var valorTextField = new Ext.form.TextField({
        id: 'idFieldValorAgregarDetalleEsquemaComisional',
        fieldLabel: 'VALOR',
        name: 'VALOR',
        xtype: 'textfield',
        allowBlank: false,
        enableKeyEvents: true,
        listeners: {
            'keyup': function (esteObjeto, esteEvento) {
                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000.0'));
                //                }else
                if (esteEvento.getKey() == 40 || esteEvento.getKey() == 13) {

                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000.00000'));
                }
            }
        }
    });

    var formPanel = new Ext.FormPanel({
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboENTIDAD, valorTextField],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    if (comboENTIDAD.getValue() != '' && comboENTIDAD.getRawValue() != '' && valorTextField.getValue() != '') {
                        var myNewRecord = new RecordDetalleEsquemaComisional({
                            BENEFICIARIO_ID: comboENTIDAD.getValue(),
                            BENEFICIARIO_DESCRIPCION: comboENTIDAD.getRawValue(),
                            ROL_COMISIONISTA: 6,
                            ROL_COMISIONISTA_DESCRIPCION: 'ENTIDAD POLITICA',
                            VALOR: valorTextField.getValue()
                        });
                        Ext.getCmp('idGridComisional').store.add(myNewRecord);
                        Ext.getCmp('idGridComisional').view.refresh(true);
                    }
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    Ext.getCmp('idPantallaEsquemaComisionalBeneficiarioValorAgregar').close();
                }
            }]
    });

    return formPanel;
}
function panelRangosRepartir() {

    var desde = new Ext.form.TextField({
        fieldLabel: 'DESDE',
        name: 'DESDE',
        enableKeyEvents: true,
        listeners: {
            'keyup': function (esteObjeto, esteEvento) {

                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000'));
                //                }
                if (esteEvento.getKey() == 40 || esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000'));
                }
            }
        }
    });
    var hasta = new Ext.form.TextField({
        fieldLabel: 'HASTA',
        name: 'HASTA',
        enableKeyEvents: true,
        listeners: {
            'keyup': function (esteObjeto, esteEvento) {

                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000'));
                //                }
                if (esteEvento.getKey() == 40 || esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000'));
                }
            }
        }
    });
    var totalRepartir = new Ext.form.TextField({
        fieldLabel: 'TOTAL REPARTIR',
        name: 'TOTAL_REPARTIR',
        enableKeyEvents: true,
        allowBlank: false,
        listeners: {
            'keyup': function (esteObjeto, esteEvento) {

                //                if(esteEvento.getKey()!=8 &&esteEvento.getKey()!=37 && esteEvento.getKey()!=38 &esteEvento.getKey()!=39 && esteEvento.getKey()!=40){
                //                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                //                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(),'0,000.00'));
                //                }
                if (esteEvento.getKey() == 40 || esteEvento.getKey() == 13) {
                    esteObjeto.setRawValue(replaceAllNoNumbers2(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(Ext.util.Format.number(esteObjeto.getRawValue(), '0,000'));
                }
            }
        }

    });

    var formPanel = new Ext.FormPanel({
        labelWidth: 130,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [desde, hasta, totalRepartir]
    });

    return formPanel;
}
var RecordDetalleEsquemaComisional = Ext.data.Record.create([
    {
        name: 'BENEFICIARIO_ID'
    },
    {
        name: 'ROL_COMISIONISTA_DESCRIPCION'
    },
    {
        name: 'BENEFICIARIO_DESCRIPCION'
    },
    {
        name: 'ROL_COMISIONISTA'
    }, {
        name: 'VALOR'
    }
]);
function pantallaEsquemaComisionalBeneficiarioValorAgregar() {
    var formulario = panelEsquemaComisionalBeneficiarioValorParaAgregar();
    // var copiaComboBeneficiario =formulario.findById('idComboEntidadAgregarEsquemaComisional');
    //  var copiaCampoValor = formulario.findById('idFieldValorAgregarDetalleEsquemaComisional');
    var win = new Ext.Window({
        id: 'idPantallaEsquemaComisionalBeneficiarioValorAgregar',
        title: 'Agregar Detalles al Esquema Comisional',
        autoWidth: true,
        height: 'auto',
        closable: true,
        resizable: false,
        modal: true,
        items: [formulario]
                //        ,
                //        buttons:[{
                //            text : 'Agregar',
                //            formBind : true,
                //            handler : function() {
                //                if(copiaComboBeneficiario.getValue()!= '' && copiaComboBeneficiario.getRawValue()!= '' && copiaCampoValor.getValue()!=''  ){
                //                    var myNewRecord = new RecordDetalleEsquemaComisional({
                //                        BENEFICIARIO_ID: copiaComboBeneficiario.getValue(),
                //                        BENEFICIARIO_DESCRIPCION: copiaComboBeneficiario.getRawValue(),
                //                        ROL_COMISIONISTA: 6,
                //                        ROL_COMISIONISTA_DESCRIPCION:'ENTIDAD POLITICA',
                //                        VALOR: copiaCampoValor.getValue()
                //                    });
                //                    Ext.getCmp('idGridComisional').store.add(myNewRecord);
                //                    Ext.getCmp('idGridComisional').view.refresh(true);
                //                }
                //            }
                //        },{
                //            text : 'Cancelar',
                //            formBind : true,
                //            handler : function() {
                //                win.close();
                //            }
                //        }]
    });
    win.show();
}
function guardarConfiguracionComisional() {

    var arrayRolComisionista = "";
    var arrayValor = "";
    var arrayBeneficiario = "";
    Ext.each(
            Ext.getCmp('idGridComisional').getStore().data.items,
            function (row) {

                arrayValor += row.data.VALOR + ";";
                arrayRolComisionista += row.data.ROL_COMISIONISTA + ";";
                arrayBeneficiario += row.data.BENEFICIARIO_ID + ";";

            });


    var conn = new Ext.data.Connection();
    var idRango;
    var desde;
    var hasta;
    Ext.each(
            Ext.getCmp('idGridComisionalCabeceraInferior').getSelectionModel().getSelections(),
            function (row) {
                idRango = row.data.ID_RANGO
                desde = row.data.DESDE;
                hasta = row.data.HASTA;
            }
    );

    var idSucursal = null;
    var idRecaudador = null;

    if (Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboServicio') != null) {
        idSucursal = Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboSucursal').getValue();
    }
    if (Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRecaudador') != null) {
        idRecaudador = Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRecaudador').getValue();
    }
    conn.request({
        url: 'CONFIGURACION_COMISIONAL?op=GUARDAR_CONFIGURACION',
        params: {
            RED: Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboRed').getValue(),
            RECAUDADOR: idRecaudador,
            SERVICIO: Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboServicio').getValue(),
            TIPO_CLEARING: Ext.getCmp('idFormCabeceraEsquemaComisional').findById('idCabEsquemaComiComboTipoClearing').getValue(),
            ID_RANGO: idRango,
            DESDE: desde,
            HASTA: hasta,
            SUCURSAL: idSucursal,
            VALOR: arrayValor,
            ROL_COMISIONISTA: arrayRolComisionista,
            BENEFICIARIO: arrayBeneficiario
        },
        method: 'POST',
        success: function (respuesta) {
            var obj = Ext.util.JSON.decode(respuesta.responseText);
            if (obj.success) {
                Ext.Msg.show({
                    title: TITULO_ACTUALIZACION_EXITOSA,
                    msg: CUERPO_ACTUALIZACION_EXITOSA,
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.INFO
                });

            } else {
                Ext.Msg.show({
                    title: FAIL_TITULO_GENERAL,
                    msg: obj.motivo,
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR
                });

            }
        },
        failure: function (respuesta) {
            Ext.Msg.show({
                title: FAIL_TITULO_GENERAL,
                msg: FAIL_CUERPO_GENERAL,
                buttons: Ext.Msg.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    });

}
function pantallaAgregarGridRangosRepartir(esteObjeto, comboRED, comboSERVICIO, comboTIPO_CLEARING) {
    var formulario = panelRangosRepartir();
    var win = new Ext.Window({
        title: 'Agregar Rangos a Repartir',
        autoWidth: true,
        height: 'auto',
        closable: true,
        resizable: false,
        modal: true,
        items: [formulario],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    formulario.getForm().submit({
                        url: 'CONFIGURACION_COMISIONAL?op=GUARDAR_RANGO_ASIGNAR',
                        params: {
                            RED: comboRED.getValue(),
                            SERVICIO: comboSERVICIO.getValue(),
                            TIPO_CLEARING: comboTIPO_CLEARING.getValue()
                        },
                        success: function () {
                            Ext.Msg.show({
                                title: TITULO_ACTUALIZACION_EXITOSA,
                                msg: CUERPO_ACTUALIZACION_EXITOSA,
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.INFO
                            });
                            win.close();
                            esteObjeto.store.load({
                                url: 'CONFIGURACION_COMISIONAL?op=VER_RANGOS',
                                params: {
                                    RED: comboRED.getValue(),
                                    SERVICIO: comboSERVICIO.getValue(),
                                    TIPO_CLEARING: comboTIPO_CLEARING.getValue()
                                }
                            });

                        },
                        failure: function (form, action) {
                            var obj = Ext.util.JSON.decode(action.response.responseText);
                            try {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                            catch (er) {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: FAIL_CUERPO_GENERAL,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                        }


                    })


                }
            }, {
                text: 'Cancelar',
                formBind: true,
                handler: function () {
                    win.close();
                }
            }]
    });
    win.show();
}
function pantallaModificarGridRangosRepartir(esteObjeto, idElementoModificar, idRed, idServicio, idTipoClering) {
    var formulario = panelRangosRepartir();
    formulario.getForm().load({
        url: 'CONFIGURACION_COMISIONAL?op=VER_RANGOS_DETALLE',
        method: 'POST',
        params: {
            ID_RANGO: idElementoModificar
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar Rangos a Asignar',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [formulario],
        buttons: [{
                text: 'Modificar',
                formBind: true,
                handler: function () {
                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons: Ext.Msg.YESNO,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "yes") {
                                formulario.getForm().submit({
                                    method: 'POST',
                                    waitTitle: WAIT_TITLE_MODIFICANDO,
                                    waitMsg: WAIT_MSG_MODIFICANDO,
                                    params: {
                                        RED: idRed,
                                        SERVICIO: idServicio,
                                        TIPO_CLEARING: idTipoClering,
                                        ID_RANGO: idElementoModificar
                                    },
                                    url: 'CONFIGURACION_COMISIONAL?op=GUARDAR_RANGO_ASIGNAR',
                                    success: function (form, accion) {
                                        Ext.Msg.show({
                                            title: TITULO_ACTUALIZACION_EXITOSA,
                                            msg: CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                        win.close();
                                        esteObjeto.store.load({
                                            url: 'CONFIGURACION_COMISIONAL?op=VER_RANGOS',
                                            params: {
                                                RED: idRed,
                                                SERVICIO: idServicio,
                                                TIPO_CLEARING: idTipoClering
                                            }

                                        });
                                    },
                                    failure: function (form, action) {
                                        var obj = Ext.util.JSON.decode(action.response.responseText);
                                        try {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: obj.motivo,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }
                                        catch (er) {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });
                                        }

                                    }
                                });
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                formBind: true,
                handler: function () {
                    win.close();
                }
            }]
    });
    win.show();
}

function administrarPermisos() {

    var tree1 = getTreePanelAdminUsuarios('tree1', 'Usuarios - Roles', 'USUARIOS_ROLES?op=LISTAR', 'Usuarios - Roles', 'USUARIOS_ROLES');
    var tree2 = getTreePanelAdminUsuarios('tree2', 'Permisos - Roles', 'PERMISOS_ROLES?op=LISTAR', 'Permisos - Roles', 'PERMISOS_ROLES');
    var tree3 = getTreePanelAdminUsuarios('tree3', 'Roles', 'ROLES?op=LISTAR', 'Roles', 'ROLES');
    var tree4 = getTreePanelAdminUsuarios('tree4', 'Permisos', 'PERMISOS?op=LISTAR', 'Permisos', 'PERMISOS');
    var win = new Ext.Window({
        title: ' Administración de Permisos',
        resizable: true,
        width: 400,
        height: 400,
        layout: 'accordion',
        border: false,
        layoutConfig: {
            animate: false
        },
        modal: true,
        tbar: [{
                tooltip: TOOL_TIP_AGREGAR,
                iconCls: 'add2',
                scope: this,
                handler: function () {

                    if (treeSeleccionado == 'nada') {
                        treeSeleccionado = tree1;
                    }
                    if (treeSeleccionado.id == tree1.id) {

                        pantallaAgregarRolesUsuario(tree1).show();

                    } else if (treeSeleccionado.id == tree2.id) {
                        pantallaPermisosRoles(tree2).show();
                    } else if (treeSeleccionado.id == tree3.id) {
                        pantallaAgregarRoles(tree3).show();
                    }
                }
            }, ' ', {
                tooltip: TOOL_TIP_QUITAR,
                iconCls: 'remove',
                handler: function () {
                    if (treeSeleccionado == 'nada') {
                        treeSeleccionado = tree1;
                    }
                    var elementoBorrar = treeSeleccionado.selModel.selNode;
                    if (treeSeleccionado.id == tree1.id && elementoBorrar != null) {
                        Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID, CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                                function (obj) {
                                    if (obj == 'yes') {
                                        borrarElementoAccordionAdminSys('USUARIOS_ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                                    }
                                });
                    } else if (treeSeleccionado.id == tree2.id && elementoBorrar != null) {

                        Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID, CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                                function (obj) {
                                    if (obj == 'yes') {
                                        borrarElementoAccordionAdminSys('PERMISOS_ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                                    }
                                }

                        )

                    } else if (treeSeleccionado.id == tree3.id && elementoBorrar != null) {

                        Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID, CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                                function (obj) {
                                    if (obj == 'yes') {
                                        borrarElementoAccordionAdminSys('ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                                    }
                                })
                    }
                }
            }],
        items: [tree1, tree2, tree3, tree4]
    });
    tree3.on('dblclick', function (esteNodo, esteEvento) {
        pantallaModificarRoles(tree3, esteNodo.id).show();
    });

    tree1.on('beforeexpand', function () {
        treeSeleccionado = tree1;
        tree1.root.reload();
    });

    tree2.on('beforeexpand', function () {
        treeSeleccionado = tree2;
        tree2.root.reload();
    });
    tree3.on('beforeexpand', function () {
        treeSeleccionado = tree3;
        tree3.root.reload();
    });
    tree4.on('beforeexpand', function () {

        treeSeleccionado = tree4;
        tree4.root.reload();
    });
    win.show();

}
function pantallaPermisosRoles(tree) {
    var comboPERMISOS = getCombo('PERMISOS', 'PERMISOS', 'PERMISOS', 'PERMISOS', 'DESCRIPCION_PERMISOS', 'PERMISOS', 'PERMISOS', 'DESCRIPCION_PERMISOS', 'PERMISOS', 'PERMISOS...');
    var comboROLES = getCombo('ROLES', 'ROLES', 'ROLES', 'ROLES', 'DESCRIPCION_ROLES', 'ROLES', 'ROLES', 'DESCRIPCION_ROLES', 'ROLES', 'ROLES...');
    var comboAPLICACIONES = getCombo('APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES...');

    comboAPLICACIONES.addListener('select', function (esteCombo, record, index) {

        comboROLES.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboROLES.store.reload();
        comboPERMISOS.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboPERMISOS.store.reload();
    }, null, null);
    var formPanel = new Ext.FormPanel({
        labelWidth: 110,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboAPLICACIONES, comboROLES, comboPERMISOS],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    formPanel.getForm().submit({
                        method: 'POST',
                        waitTitle: WAIT_TITLE_AGREGANDO,
                        waitMsg: WAIT_MSG_AGREGANDO,
                        url: 'PERMISOS_ROLES?op=AGREGAR',
                        success: function (form, accion) {
                            win.close();
                            tree.root.reload();
                        },
                        failure: function (form, action) {
                            var obj = Ext.util.JSON.decode(action.response.responseText);
                            try {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                            catch (er) {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: FAIL_CUERPO_GENERAL,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });

                            }

                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar Permisos a Roles',
        id: 'idAgregarPermisosRoles',
        width: 300,
        height: 'auto',
        closable: false,
        resizable: false,
        items: [formPanel]
    });
    return win;

}
function pantallaAgregarRolesUsuario(tree) {
    var comboUSUARIO = getCombo('USUARIO_PERSONA', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIOS...');
    var comboROLES = getCombo('ROLES', 'ROLES', 'ROLES', 'ROLES', 'DESCRIPCION_ROLES', 'ROLES', 'ROLES', 'DESCRIPCION_ROLES', 'ROLES', 'ROLES...');
    var comboAPLICACIONES = getCombo('APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES...');

    comboAPLICACIONES.addListener('select', function (esteCombo, record, index) {

        comboROLES.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboROLES.store.reload();

    }, null, null);
    var formPanel = new Ext.FormPanel({
        labelWidth: 110,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboAPLICACIONES, comboROLES, comboUSUARIO],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    formPanel.getForm().submit({
                        method: 'POST',
                        waitTitle: WAIT_TITLE_AGREGANDO,
                        waitMsg: WAIT_MSG_AGREGANDO,
                        url: 'USUARIOS_ROLES?op=AGREGAR',
                        success: function (form, accion) {
                            win.close();
                            tree.root.reload();
                        },
                        failure: function (form, action) {
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: FAIL_CUERPO_GENERAL,
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.ERROR
                            });
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar Usuarios a Roles',
        id: 'idAgregarUsuariosRoles',
        width: 300,
        height: 'auto',
        closable: false,
        resizable: false,
        items: [formPanel]
    });
    return win;

}
function pantallaAgregarRoles(tree) {
    var comboAPLICACIONES = getCombo('APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES...');

    var formPanel = new Ext.FormPanel({
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboAPLICACIONES, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false
            }],
        buttons: [{
                text: 'Agregar',
                formBind: true,
                handler: function () {
                    formPanel.getForm().submit({
                        method: 'POST',
                        waitTitle: WAIT_TITLE_AGREGANDO,
                        waitMsg: WAIT_MSG_AGREGANDO,
                        url: 'ROLES?op=AGREGAR',
                        success: function (form, accion) {
                            win.close();
                            tree.root.reload();
                        },
                        failure: function (form, action) {
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: FAIL_CUERPO_GENERAL,
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.ERROR
                            });
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Agregar ROL',
        id: 'idAgregarRol',
        width: 300,
        height: 'auto',
        closable: false,
        resizable: false,
        items: [formPanel]
    });
    return win;

}
function pantallaModificarRoles(tree, elIdRol) {
    var comboAPLICACIONES = getCombo('APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES', 'DESCRIPCION_APLICACIONES', 'APLICACIONES', 'APLICACIONES...');
    comboAPLICACIONES.store.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var formPanel = new Ext.FormPanel({
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboAPLICACIONES, {
                name: 'ID_ROL',
                inputType: 'hidden'
            }, {
                fieldLabel: 'DESCRIPCIÓN',
                name: 'DESCRIPCION',
                allowBlank: false
            }],
        buttons: [{
                text: 'Modificar',
                formBind: true,
                handler: function () {

                    Ext.Msg.show({
                        title: TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg: CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons: Ext.Msg.YESNO,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (btn, text) {
                            if (btn == "yes") {
                                formPanel.getForm().submit({
                                    method: 'POST',
                                    waitTitle: WAIT_TITLE_MODIFICANDO,
                                    waitMsg: WAIT_MSG_MODIFICANDO,
                                    url: 'ROLES?op=MODIFICAR',
                                    success: function (form, accion) {


                                        tree.root.reload();
                                        win.close();

                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });

                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    formPanel.getForm().load({
        url: 'ROLES?op=DETALLE',
        method: 'POST',
        params: {
            ID_ROL: elIdRol
        },
        waitMsg: 'Cargando...'
    });
    var win = new Ext.Window({
        title: 'Modificar Rol',
        id: 'modificarROL',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        items: [formPanel]
    });
    return win;

}
function borrarElementoAccordionAdminSys(suUrl, suElementoABorrar, treeSeleccionado) {
    var conn = new Ext.data.Connection();
    conn.request({
        url: suUrl,
        params: {
            op: 'BORRAR',
            elementoABorrar: suElementoABorrar.id
        },
        method: 'POST',
        success: function (respuesta) {
            var obj = Ext.util.JSON.decode(respuesta.responseText);
            if (obj.success) {

                treeSeleccionado.root.reload();
            } else {
                Ext.Msg.show({
                    title: FAIL_TITULO_GENERAL,
                    msg: FAIL_CUERPO_GENERAL,
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR
                });

            }
        },
        failure: function (respuesta) {

            var obj = Ext.util.JSON.decode(respuesta.responseText);

            Ext.Msg.show({
                title: FAIL_TITULO_GENERAL,
                msg: FAIL_CUERPO_GENERAL,
                buttons: Ext.Msg.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    });

}

function getWindows(elPanel) {

    var win = new Ext.createWindow({
        id: 'formCrearRoles-win',
        title: 'Crear Roles',
        width: 350,
        height: 350,
        iconCls: 'icon-grid',
        shim: false,
        animCollapse: false,
        constrainHeader: true,
        layout: 'fit',
        items: elPanel


    });

    return win;

}

function getTreePanelAdminUsuarios(suId, suTitle, suDataUrl, suTextAsyncTreeNode, suIdAsyncTreeNode) {

    var tree = new Ext.tree.TreePanel({
        title: suTitle,
        loader: new Ext.tree.TreeLoader({
            dataUrl: suDataUrl
        }),
        rootVisible: false,
        lines: false,
        autoScroll: true,
        tools: [{
                id: 'refresh',
                on: {
                    click: function () {

                        tree.body.mask('Cargando...', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function () {
                            tree.body.unmask();
                            tree.root.expand(true, true);
                        }, 1000);
                    }
                }
            }],
        root: new Ext.tree.AsyncTreeNode({
            text: suTextAsyncTreeNode,
            draggable: false,
            id: suIdAsyncTreeNode
        })
    });

    return tree;


}
function filtrosGeneracionArchivosEra(laOpUrl, titulo) {
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    var fecha = new Ext.form.DateField({
        fieldLabel: 'Fecha',
        name: 'FECHA',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'd/m/Y'
    });
    var filtroFormPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fecha, comboRED],
        buttons: [{
                formBind: true,
                text: 'Generar Archivo',
                handler: function () {

                    var conn = new Ext.data.Connection();
                    conn.request({
                        url: 'ARCHIVO_ERA?op=EXISTE_' + laOpUrl,
                        method: 'POST',
                        params: {
                            FECHA: fecha.getRawValue(),
                            RED: comboRED.getValue()
                        },
                        success: function (action) {
                            obj = Ext.util.JSON.decode(action.responseText);
                            if (obj.success && obj.existe == 'S') {
                                filtroFormPanel.getForm().submit({
                                    url: 'ARCHIVO_ERA?op=' + laOpUrl,
                                    method: 'POST',
                                    waitTitle: 'Conectando',
                                    waitMsg: 'Generando el archivo...',
                                    timeout: 14400,
                                    success: function (form, accion) {
                                        var obj = Ext.util.JSON.decode(accion.response.responseText);
                                        if (obj.success) {
                                            var archivo_generado = obj.archivo_generado;
                                            try {
                                                Ext.destroy(Ext.get('downloadIframe'));
                                                Ext.DomHelper.append(document.body, {
                                                    tag: 'iframe',
                                                    id: 'downloadIframe',
                                                    frameBorder: 0,
                                                    width: 0,
                                                    height: 0,
                                                    css: 'display:yes;visibility:hidden;height:0px;',
                                                    src: 'download?archivo_generado=' + archivo_generado
                                                });
                                            } catch (e) {
                                                Ext.Msg.show({
                                                    title: FAIL_TITULO_GENERAL,
                                                    msg: FAIL_CUERPO_GENERAL,
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                            }

                                        } else {
                                            Ext.Msg.show({
                                                title: FAIL_TITULO_GENERAL,
                                                msg: FAIL_CUERPO_GENERAL,
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.MessageBox.ERROR
                                            });

                                        }

                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.show({
                                            title: FAIL_TITULO_GENERAL,
                                            msg: FAIL_CUERPO_GENERAL,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                    }
                                });

                            } else {
                                Ext.Msg.show({
                                    title: 'Estado',
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                        },
                        failure: function (action) {
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: FAIL_CUERPO_GENERAL,
                                buttons: Ext.Msg.OK,
                                animEl: 'elId',
                                icon: Ext.MessageBox.ERROR
                            });
                        }
                    });

                }
            }, {
                text: 'Cancelar',
                handler: function () {

                    win.close();

                }
            }]
    });
    var win = new Ext.Window({
        title: titulo + ' - Seleccionar Filtro',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [filtroFormPanel]
    });
    win.show();

}
var idCuentaAsignar = 0;
var valueComboCuentaAAsignar = "";

function  asignarCuenta(idGridAsignado, idGridNoAsignado, laUrl, elCombo, idRecaudador) {
    valueComboCuentaAAsignar = "";
    // var comboCUENTA_a_ASIGNAR =getCombo('CUENTA','CUENTA','CUENTA','CUENTA','DESCRIPCION_CUENTA','CUENTA','CUENTA','DESCRIPCION_CUENTA','CUENTA','CUENTA');
    var cuentaAsginar = new Ext.form.TextField({
        fieldLabel: 'CUENTA',
        allowBlank: false,
        name: 'CUENTA'

    });
    var asignarCuentaFormPanel = new Ext.FormPanel({
        labelWidth: 60,
        frame: true,
        autoWidth: true,
        monitorValid: true,
        items: [cuentaAsginar],
        buttons: [{
                text: 'Asignar',
                formBind: true,
                handler: function () {
                    Ext.each(
                            Ext.getCmp(idGridNoAsignado).getSelectionModel().getSelections(),
                            function (row) {
                                Ext.getCmp(idGridNoAsignado).getStore().remove(row);
                                Ext.getCmp(idGridAsignado).getStore().add(row);
                            }
                    );
                    Ext.each(
                            Ext.getCmp(idGridAsignado).getStore().data.items,
                            function (row) {
                                if (row.data.CUENTA != "" && row.data.CUENTA != undefined) {

                                    valueComboCuentaAAsignar += row.data.CUENTA + ",";
                                }
                            }
                    );
                    valueComboCuentaAAsignar += cuentaAsginar.getRawValue();

                    handlerElementoSeleccionado(idGridAsignado, idGridAsignado, idGridNoAsignado, elCombo, laUrl, idRecaudador);
                    win.close();
                }
            }, {
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'ELEGIR CUENTA',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: [asignarCuentaFormPanel]
    });
    return win;

}

function gridFacturadoresNoAsignados() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RED?op=LISTAR_FACTURADORES'
        }),
        reader: new Ext.data.JsonReader({
            root: 'FACTURADOR',
            totalProperty: 'TOTAL',
            id: 'FACTURADOR',
            fields: ['FACTURADOR', 'ENTIDAD', 'DESCRIPCION', 'CONTACTO', 'ALIAS', 'CUENTA']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ID FACTURADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA'
        }, {
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }]);
    var gridFACTURADOR = new Ext.grid.GridPanel({
        title: 'Facturadores No Asignados',
        id: 'idGridFacturadoresNoAsignados',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Asignar',
                tooltip: 'Asignar Facturador...',
                iconCls: 'add',
                handler: function () {
                    asignarCuenta('idGridFacturadoresAsignados', 'idGridFacturadoresNoAsignados', 'RED?op=AGREGAR_FACTURADORES', 'idComboHabilitarFacturadores').show();

                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {
                asignarCuenta('idGridFacturadoresAsignados', 'idGridFacturadoresNoAsignados', 'RED?op=AGREGAR_FACTURADORES', 'idComboHabilitarFacturadores').show();



            }
        }
    });

    return gridFACTURADOR;

}
function gridFacturadoresAsignados() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RED?op=LISTAR_FACTURADORES'
        }),
        reader: new Ext.data.JsonReader({
            root: 'FACTURADOR',
            totalProperty: 'TOTAL',
            id: 'FACTURADOR',
            fields: ['FACTURADOR', 'ENTIDAD', 'DESCRIPCION', 'CONTACTO', 'ALIAS', 'CUENTA']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ID FACTURADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'CUENTA',
            width: anchoDefaultColumnas,
            dataIndex: 'CUENTA'
        }, {
            header: 'ENTIDAD',
            width: anchoDefaultColumnas,
            dataIndex: 'ENTIDAD'
        }, {
            header: 'CONTACTO',
            width: anchoDefaultColumnas,
            dataIndex: 'CONTACTO'
        }]);
    var gridFACTURADOR = new Ext.grid.GridPanel({
        title: 'Facturadores Asignados',
        id: 'idGridFacturadoresAsignados',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Quitar',
                tooltip: 'Desasignar Racaudador',
                iconCls: 'remove',
                handler: function () {

                    Ext.each(
                            Ext.getCmp('idGridFacturadoresAsignados').getSelectionModel().getSelections(),
                            function (row) {
                                Ext.getCmp('idGridFacturadoresAsignados').getStore().remove(row);
                            }

                    );
                    handlerElementoSeleccionado('idGridFacturadoresAsignados', 'idGridFacturadoresAsignados', 'idGridFacturadoresNoAsignados', 'idComboHabilitarFacturadores', 'RED?op=AGREGAR_FACTURADORES');


                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {

                Ext.each(
                        Ext.getCmp('idGridFacturadoresAsignados').getSelectionModel().getSelections(),
                        function (row) {
                            Ext.getCmp('idGridFacturadoresAsignados').getStore().remove(row);
                        }
                );

                handlerElementoSeleccionado('idGridFacturadoresAsignados', 'idGridFacturadoresAsignados', 'idGridFacturadoresNoAsignados', 'idComboHabilitarFacturadores', 'RED?op=AGREGAR_FACTURADORES');


            }
        }
    });

    return gridFACTURADOR;

}
function gridServiciosRecaudadoresNoAsignados() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RECAUDADOR?op=LISTAR_SERVICIOS_RECAUDADOR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'SERVICIO',
            totalProperty: 'TOTAL',
            id: 'SERVICIO',
            fields: ['SERVICIO', 'FACTURADOR', 'DESCRIPCION', 'COMENTARIO', 'TAMANHO_GESTION']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ID SERVICIO',
            width: anchoDefaultColumnas,
            dataIndex: 'SERVICIO'
        }, {
            header: 'FACTURADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'COMENTARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'COMENTARIO'
        }, {
            header: 'TAMANHO GESTION',
            width: anchoDefaultColumnas,
            dataIndex: 'TAMANHO_GESTION'
        }]);
    var grid = new Ext.grid.GridPanel({
        title: 'Servicios NO Habilitados',
        id: 'idGridServiciosRecaudadoresNoAsignados',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Asignar',
                tooltip: 'Asignar Servicio a Recaudador...',
                iconCls: 'add',
                handler: function () {


                    Ext.each(
                            Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').getSelectionModel().getSelections(),
                            function (row) {
                                Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').getStore().remove(row);
                                Ext.getCmp('idGridServiciosRecaudadoresAsignados').getStore().add(row);
                            }

                    );

                    handlerElementoSeleccionado('idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresNoAsignados', 'idComboREDHabilitarServiciosRecaudadores', 'RECAUDADOR?op=AGREGAR_SERVICIOS_RECAUDADOR', Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').getValue());

                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {


                Ext.each(
                        Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').getSelectionModel().getSelections(),
                        function (row) {
                            Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').getStore().remove(row);
                            Ext.getCmp('idGridServiciosRecaudadoresAsignados').getStore().add(row);
                        }

                );


                handlerElementoSeleccionado('idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresNoAsignados', 'idComboREDHabilitarServiciosRecaudadores', 'RECAUDADOR?op=AGREGAR_SERVICIOS_RECAUDADOR', Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').getValue());

            }
        }
    });

    return grid;

}
function gridServiciosRecaudadoresAsignados() {

    var st = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'RECAUDADOR?op=LISTAR_SERVICIOS_RECAUDADOR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'SERVICIO',
            totalProperty: 'TOTAL',
            id: 'SERVICIO',
            fields: ['SERVICIO', 'FACTURADOR', 'DESCRIPCION', 'COMENTARIO', 'TAMANHO_GESTION']
        }),
        listeners: {
            'beforeload': function (store, options) {
            }
        }
    });
    var anchoDefaultColumnas = 10;
    var colModel = new Ext.grid.ColumnModel([{
            header: 'ID SERVICIO',
            width: anchoDefaultColumnas,
            dataIndex: 'SERVICIO'
        }, {
            header: 'FACTURADOR',
            width: anchoDefaultColumnas,
            dataIndex: 'FACTURADOR'
        }, {
            header: 'DESCRIPCIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'DESCRIPCION'
        }, {
            header: 'COMENTARIO',
            width: anchoDefaultColumnas,
            dataIndex: 'COMENTARIO'
        }, {
            header: 'TAMANHO GESTIÓN',
            width: anchoDefaultColumnas,
            dataIndex: 'TAMANHO_GESTION'
        }]);
    var grid = new Ext.grid.GridPanel({
        title: 'Servicios Habilitados',
        id: 'idGridServiciosRecaudadoresAsignados',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        tbar: [{
                text: 'Quitar',
                tooltip: 'Desasignar Racaudador...',
                iconCls: 'remove',
                handler: function () {

                    Ext.each(
                            Ext.getCmp('idGridServiciosRecaudadoresAsignados').getSelectionModel().getSelections(),
                            function (row) {
                                Ext.getCmp('idGridServiciosRecaudadoresAsignados').getStore().remove(row);
                            }

                    );
                    handlerElementoSeleccionado('idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresNoAsignados', 'idComboREDHabilitarServiciosRecaudadores', 'RECAUDADOR?op=AGREGAR_SERVICIOS_RECAUDADOR', Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').getValue());


                }
            }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No exiten Datos que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        listeners: {
            'celldblclick': function (esteObjeto, rowIndex, columnIndex, e) {

                Ext.each(
                        Ext.getCmp('idGridServiciosRecaudadoresAsignados').getSelectionModel().getSelections(),
                        function (row) {
                            Ext.getCmp('idGridServiciosRecaudadoresAsignados').getStore().remove(row);
                        }

                );

                handlerElementoSeleccionado('idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresAsignados', 'idGridServiciosRecaudadoresNoAsignados', 'idComboREDHabilitarServiciosRecaudadores', 'RECAUDADOR?op=AGREGAR_SERVICIOS_RECAUDADOR', Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').getValue());


            }
        }
    });

    return grid;

}
function handlerElementoSeleccionado(idGrid, idGridAsignado, idGridNoAsignado, idCombo, laUrl, elIdRecaudador) {

    var asignados = "";
    var elCombo = "";
    if (Ext.getCmp(idCombo) != undefined) {
        elCombo = Ext.getCmp(idCombo).getValue()

    }
    if (valueComboCuentaAAsignar == undefined || valueComboCuentaAAsignar == "") {
        Ext.each(
                Ext.getCmp(idGrid).getStore().data.items,
                function (row) {
                    asignados += row.id + ", ";

                    valueComboCuentaAAsignar += row.data.ID_CUENTA + ", "

                }
        );
    } else {

        Ext.each(
                Ext.getCmp(idGrid).getStore().data.items,
                function (row) {

                    asignados += row.id + ", ";

                }
        );
    }
    var conn = new Ext.data.Connection();
    conn.request({
        url: laUrl,
        method: 'POST',
        params: {
            ID_RED: elCombo,
            ID_CUENTA: valueComboCuentaAAsignar,
            A_ASIGNAR: asignados,
            ID_RECAUDADOR: elIdRecaudador
        },
        success: function (action) {
            var obj = Ext.util.JSON.decode(action.responseText);
            if (obj.success) {

                Ext.getCmp(idGridAsignado).getStore().reload();
                Ext.getCmp(idGridNoAsignado).getStore().reload();

            } else {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: obj.motivo,
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR
                });
            }
        },
        failure: function (action) {
            Ext.Msg.show({
                title: FAIL_TITULO_GENERAL,
                msg: FAIL_CUERPO_GENERAL,
                buttons: Ext.Msg.OK,
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR
            });

        }
    });
    valueComboCuentaAAsignar = "";
    return true;


}

function panelaHabilitacionFacturadoresRed() {


    var gridFacturadoresNOASIGNADOS = gridFacturadoresNoAsignados();
    var gridFacturadoresASIGNADOS = gridFacturadoresAsignados();

    Ext.getCmp('idGridFacturadoresAsignados').store.baseParams['FLAG_ASIGNACION'] = 'SI';
    Ext.getCmp('idGridFacturadoresNoAsignados').store.baseParams['FLAG_ASIGNACION'] = 'NO';

    var ds_providersRED = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'combos?op=RED'
        }),
        reader: new Ext.data.JsonReader({
            root: 'RED',
            id: 'RED',
            totalProperty: 'total'
        }, [{
                name: 'RED'
            }, {
                name: 'DESCRIPCION_RED'
            }])
    });
    ds_providersRED.load({
        params: {
            start: 0,
            limit: 25
        }
    });
    var comboRED = new Ext.form.ComboBox({
        id: 'idComboHabilitarFacturadores',
        fieldLabel: 'RED',
        hiddenName: 'RED',
        store: ds_providersRED,
        displayField: 'DESCRIPCION_RED',
        valueField: 'RED',
        triggerAction: 'all',
        emptyText: 'RED...',
        selectOnFocus: true,
        anchor: '95%',
        pageSize: 25,
        listWidth: 250,
        forceSelection: true,
        loadingText: 'Cargando...',
        typeAhead: true,
        minChars: 2,
        listeners: {
            'select': function (combo, record, index) {
                Ext.getCmp('idGridFacturadoresNoAsignados').store.baseParams['ID_RED'] = record.data.RED;
                Ext.getCmp('idGridFacturadoresNoAsignados').store.reload();
                Ext.getCmp('idGridFacturadoresAsignados').store.baseParams['ID_RED'] = record.data.RED;
                Ext.getCmp('idGridFacturadoresAsignados').store.reload();
            }
        }
    });

    var panel = {
        id: 'idPanelHabilitacionFacturadoresRed',
        title: 'Habilitación de Facturadores a Red',
        xtype: 'panel',
        layout: 'border',
        border: false,
        tbar: [{
                text: 'RED:'
            }, comboRED],
        autoScroll: true,
        items: [{
                collapsible: false,
                region: 'center',
                layout: 'fit',
                margins: '5 0 0 0',
                autoScroll: true,
                minSize: 15,
                items: [gridFacturadoresNOASIGNADOS]
            }, {
                collapsible: false,
                layout: 'fit',
                region: 'south',
                margins: '5 0 0 0',
                autoScroll: true,
                split: true,
                height: 350,
                minSize: 75,
                maxSize: 450,
                items: [gridFacturadoresASIGNADOS]
            }]
    }
    return panel;

}
function panelaHabilitacionServiciosRecaudadores() {

    var gridServiciosRecaudadoresNOASIGNADOS = gridServiciosRecaudadoresNoAsignados();
    var gridServiciosRecaudadoresASIGNADOS = gridServiciosRecaudadoresAsignados();

    Ext.getCmp('idGridServiciosRecaudadoresAsignados').store.baseParams['FLAG_ASIGNACION'] = 'SI';
    Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').store.baseParams['FLAG_ASIGNACION'] = 'NO';

    var ds_providersRECAUDADOR = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'combos?op=RECAUDADOR'
        }),
        reader: new Ext.data.JsonReader({
            root: 'RECAUDADOR',
            id: 'RECAUDADOR',
            totalProperty: 'total'
        }, [{
                name: 'RECAUDADOR'
            }, {
                name: 'DESCRIPCION_RECAUDADOR'
            }])
    });
    ds_providersRECAUDADOR.load({
        params: {
            start: 0,
            limit: 25
        }
    });


    var comboRECAUDADOR = new Ext.form.ComboBox({
        id: 'comboRECAUDADORHabilitarServiciosRecaudadores',
        fieldLabel: 'RECAUDADOR',
        hiddenName: 'RECAUDADOR',
        store: ds_providersRECAUDADOR,
        displayField: 'DESCRIPCION_RECAUDADOR',
        valueField: 'RECAUDADOR',
        triggerAction: 'all',
        emptyText: 'RECAUDADOR...',
        selectOnFocus: true,
        pageSize: 25,
        listWidth: 250,
        forceSelection: true,
        loadingText: 'Cargando...',
        typeAhead: true,
        minChars: 2,
        width: 200,
        listeners: {
            'select': function (combo, record, index) {

                // Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').store.baseParams['ID_RED'] = Ext.getCmp('idComboREDHabilitarServiciosRecaudadores').getValue();
                Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
                Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').store.reload();

                // Ext.getCmp('idGridServiciosRecaudadoresAsignados').store.baseParams['ID_RED'] =  Ext.getCmp('idComboREDHabilitarServiciosRecaudadores').getValue();
                Ext.getCmp('idGridServiciosRecaudadoresAsignados').store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
                Ext.getCmp('idGridServiciosRecaudadoresAsignados').store.reload();

            }
        }
    });
    //    var ds_providersRED = new Ext.data.Store({
    //        proxy : new Ext.data.HttpProxy({
    //            method : 'post',
    //            url : 'combos?op=RED'
    //        }),
    //        reader : new Ext.data.JsonReader({
    //            root : 'RED',
    //            id : 'RED',
    //            totalProperty : 'total'
    //        }, [{
    //            name : 'RED'
    //        }, {
    //            name : 'DESCRIPCION_RED'
    //        }])
    //    });
    //    ds_providersRED.load({
    //        params : {
    //            start : 0,
    //            limit : 25
    //        }
    //    });


    //    var comboRED = new Ext.form.ComboBox({
    //        id:'idComboREDHabilitarServiciosRecaudadores',
    //        fieldLabel : 'RED',
    //        hiddenName : 'RED',
    //        store : ds_providersRED,
    //        displayField : 'DESCRIPCION_RED',
    //        valueField : 'RED',
    //        triggerAction : 'all',
    //        emptyText : 'RED...',
    //        selectOnFocus : true,
    //        pageSize : 25,
    //        listWidth : 250,
    //        forceSelection : true,
    //        loadingText : 'Cargando...',
    //        typeAhead : true,
    //        minChars :2,
    //        width:80,
    //        listeners : {
    //            'select' : function( combo,  record,  index )   {
    //
    //                Ext.getCmp('idGridServiciosRecaudadoresNoAsignados').store.removeAll();
    //                Ext.getCmp('idGridServiciosRecaudadoresAsignados').store.removeAll();
    //
    //                Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').clearValue();
    //                Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').store.removeAll();
    //                Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').store.baseParams['ID_RED'] = record.data.RED;
    //                Ext.getCmp('comboRECAUDADORHabilitarServiciosRecaudadores').store.reload();
    //
    //            }
    //        }
    //
    //    });



    var panel = {
        id: 'idPanelaHabilitacionServiciosRecaudadores',
        title: 'Habilitación de Servicios Recaudadores',
        xtype: 'panel',
        layout: 'border',
        border: false,
        tbar: new Ext.Toolbar({
            width: 100,
            items: ['RECAUDADOR: ', comboRECAUDADOR]
        }),
        bbar: {
            text: 'Agregar',
            tooltip: TOOL_TIP_AGREGAR,
            iconCls: 'add',
            handler: function () {
                if (Ext.getCmp('agregarUSUARIO_TERMINAL') == undefined)
                    pantallaAgregarUSUARIO_TERMINAL().show();
            }
        }
        ,
        autoScroll: true,
        items: [{
                collapsible: false,
                region: 'center',
                layout: 'fit',
                margins: '5 0 0 0',
                autoScroll: true,
                minSize: 75,
                items: [gridServiciosRecaudadoresNOASIGNADOS]
            }, {
                collapsible: false,
                layout: 'fit',
                region: 'south',
                margins: '5 0 0 0',
                autoScroll: true,
                split: true,
                height: 350,
                minSize: 75,
                maxSize: 450,
                items: [gridServiciosRecaudadoresASIGNADOS]
            }]
    }
    return panel;

}
function panelFranjaHoraria() {

    var gridCabecera = gridFRANJA_HORARIA_CAB();
    var gridDetalle = gridFRANJA_HORARIA_DET();

    var panel = {
        id: 'idPanelFranjaHoraria',
        title: 'Franja Horaria',
        xtype: 'panel',
        layout: 'border',
        border: false,
        autoScroll: true,
        items: [{
                collapsible: false,
                region: 'center',
                layout: 'fit',
                margins: '5 0 0 0',
                autoScroll: true,
                items: [gridCabecera]
            }, {
                collapsible: false,
                layout: 'fit',
                region: 'south',
                margins: '5 0 0 0',
                autoScroll: true,
                height: 250,
                split: true,
                minSize: 75,
                maxSize: 350,
                items: [gridDetalle]
            }]
    }
    return panel;

}

/**********************REPORTES-FUNCIONES CABECERAS****************************/
function getFormPanelReporteSimple(opDelServlet) {
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    comboRED.alloBlank = false;
    var numeroOrden = new Ext.form.TextField({
        fieldLabel: 'Numero Orden',
        anchor: '95%',
        name: 'numeroOrden',
        allowBlank: false
    })

    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });

    var formPanel = new Ext.FormPanel({
        id: opDelServlet,
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboRED, numeroOrden, formatoDeDescarga],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {

                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag: 'iframe',
                            id: 'downloadIframe',
                            frameBorder: 0,
                            width: 0,
                            height: 0,
                            css: 'display:yes;visibility:hidden;height:0px;',
                            src: 'reportes?op=' + opDelServlet + '&numeroOrden=' + numeroOrden.getValue() + '&idRed=' + comboRED.getValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                        });
                    } catch (e) {

                    }

                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();

                }
            }]
    });

    return formPanel;

}
function getFormPanelReporteCompuestoCobranzas(opDelServlet) {
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    var comboFACTURADOR = getCombo('FACTURADORES_HABILITADOS', 'FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR');
    var comboSERVICIO = getCombo('SERVICIO', 'SERVICIO', 'SERVICIO', 'SERVICIO', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'SERVICIO', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'SERVICIO');
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'GESTIÓN', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'REGISTRO_GESTION', 'GESTIONES...');
    var comboUSUARIO = getCombo('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO');
    var comboTERMINAL = getCombo('TERMINAL', 'TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL', 'DESCRIPCION_TERMINAL', 'TERMINAL', 'TERMINAL');
    var comboSUCURSAL = getCombo('SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'SUCURSAL');

    var comboEstadoTransaccion = new Ext.form.ComboBox({
        fieldLabel: 'ESTADO TRANSACCIÓN',
        hiddenName: 'estadoTransaccion',
        valueField: 'estadoTransaccion',
        emptyText: 'ESTADO TRANSACCIÓN...',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['estadoTransaccion', 'descripcion'],
            data: [['null', 'Cualquiera'],
                ['S', 'Anualdo'],
                ['N', 'No Anulado']]
        })
    });


    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboFACTURADOR.disable();
            comboRECAUDADOR.disable();
            comboFACTURADOR.disable();
            comboSERVICIO.disable();
            comboTERMINAL.disable();
            comboREGISTRO_GESTION.disable();
            comboUSUARIO.disable();

            comboFACTURADOR.reset();
            comboRECAUDADOR.reset();
            comboFACTURADOR.reset();
            comboSERVICIO.reset();
            comboTERMINAL.reset();
            comboREGISTRO_GESTION.reset();
            comboUSUARIO.reset();
        }

    }, null, null);

    comboRECAUDADOR.addListener('change', function (esteCombo, newValue, oldValue) {
        //alert("ENTRE!!!")
        if (esteCombo.getRawValue().length == 0) {
            comboUSUARIO.disable();
            comboTERMINAL.disable();
            comboSUCURSAL.disable();
            comboUSUARIO.reset();
            comboTERMINAL.reset();
            comboSUCURSAL.reset();

        }

    }, null, null);

    comboFACTURADOR.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboSERVICIO.disable();

            comboSERVICIO.reset();


        }

    }, null, null);

    comboUSUARIO.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0 && comboTERMINAL.getRawValue().length == 0) {
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }

        comboREGISTRO_GESTION.store.baseParams['ID_USUARIO'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null, null);
    comboTERMINAL.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0 && comboUSUARIO.getRawValue().length == 0) {
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null, null);

    comboRED.addListener('select', function (esteCombo, record, index) {
        comboRECAUDADOR.enable();
        comboFACTURADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();
        comboFACTURADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboFACTURADOR.store.reload();
    }, null, null);

    comboRECAUDADOR.addListener('select', function (esteCombo, record, index) {
        comboUSUARIO.enable();
        comboTERMINAL.enable();
        comboSUCURSAL.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null, null);

    comboFACTURADOR.addListener('select', function (esteCombo, record, index) {
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null, null);

    comboUSUARIO.addListener('select', function (esteCombo, record, index) {
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);

    comboTERMINAL.addListener('select', function (esteCombo, record, index) {
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);

    comboRECAUDADOR.disable();
    comboFACTURADOR.disable();
    comboSERVICIO.disable();
    comboTERMINAL.disable();
    comboREGISTRO_GESTION.disable();
    comboSUCURSAL.disable();
    comboUSUARIO.disable();
    comboRED.allowBlank = false;
    comboTERMINAL.allowBlank = true;
    comboRECAUDADOR.allowBlank = true;
    comboUSUARIO.allowBlank = true;
    comboFACTURADOR.allowBlank = true;
    comboSERVICIO.allowBlank = true;
    comboREGISTRO_GESTION.allowBlank = true;
    comboSUCURSAL.allowBlank = true;


    var fechaIni = new Ext.form.DateField({
        fieldLabel: 'Fecha Inicio',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'

    });
    var fechaFin = new Ext.form.DateField({
        fieldLabel: 'Fecha Fin',
        height: '22',
        anchor: '95%',
        allowBlank: true,
        format: 'dmY'

    });

    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });
    var formPanel = new Ext.FormPanel({
        id: opDelServlet,
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboRED, comboRECAUDADOR, comboSUCURSAL, comboUSUARIO, comboTERMINAL, comboREGISTRO_GESTION, comboFACTURADOR, comboSERVICIO, comboEstadoTransaccion, fechaIni, fechaFin, formatoDeDescarga],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {

                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag: 'iframe',
                            id: 'downloadIframe',
                            frameBorder: 0,
                            width: 0,
                            height: 0,
                            css: 'display:yes;visibility:hidden;height:0px;',
                            //src : 'reportes?op='+opDelServlet+'&tipoConsulta='+radioTipoTerminal.getValue().inputValue+'&idUsuario='+comboUSUARIO.getValue()+'&codigoServicio='+comboSERVICIO.getValue()+'&idFacturador='+comboFACTURADOR.getValue()+'&idGestion='+comboREGISTRO_GESTION.getValue()+'&estadoTransaccion='+comboEstadoTransaccion.getValue()+'&fechaFin='+fechaFin.getRawValue()+'&fechaIni='+fechaIni.getRawValue()
                            src: 'reportes?op=' + opDelServlet + '&idTerminal=' + comboTERMINAL.getValue() + '&idSucursal=' + comboSUCURSAL.getValue() + '&idRecaudador=' + comboRECAUDADOR.getValue() + '&idRed=' + comboRED.getValue() + '&idUsuario=' + comboUSUARIO.getValue() + '&codigoServicio=' + comboSERVICIO.getValue() + '&idFacturador=' + comboFACTURADOR.getValue() + '&idGestion=' + comboREGISTRO_GESTION.getValue() + '&estadoTransaccion=' + comboEstadoTransaccion.getValue() + '&fechaFin=' + fechaFin.getRawValue() + '&fechaIni=' + fechaIni.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                        });
                    } catch (e) {

                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    comboFACTURADOR.disable();
                    comboRECAUDADOR.disable();
                    comboFACTURADOR.disable();
                    comboSERVICIO.disable();
                    comboTERMINAL.disable();
                    comboREGISTRO_GESTION.disable();
                    comboUSUARIO.disable();
                }
            }]
    });
    return formPanel;

}

function getReporteParameters(opcion) {
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");


    comboRECAUDADOR.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
        }

    }, null, null);

    comboRECAUDADOR.addListener('select', function (esteCombo, record, index) {
        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null, null);

    comboSUCURSAL.disable();
    comboRECAUDADOR.allowBlank = true;
    comboSUCURSAL.allowBlank = true;


    var fechaDesde = new Ext.form.DateField({
        fieldLabel: 'FECHA DESDE',
        name: 'FECHA_DESDE',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY',
        enableKeyEvents: true
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel: 'FECHA HASTA',
        name: 'FECHA_HASTA',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY',
        enableKeyEvents: true
    });
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'FORMATO DE DESCARGA',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });

    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fechaDesde, fechaHasta, comboRECAUDADOR, comboSUCURSAL, formatoDeDescarga],
        buttons: [{
                text: 'Generar Archivo',
                formBind: true,
                handler: function () {
                    if (!trabajando) {
                        trabajando = true;
                        try {
                            Ext.destroy(Ext.get('downloadIframe'));
                            Ext.DomHelper.append(document.body, {
                                tag: 'iframe',
                                id: 'downloadIframe',
                                frameBorder: 0,
                                width: 0,
                                height: 0,
                                css: 'display:yes;visibility:hidden;height:0px;',
                                src: 'reportes?op=' + opcion + '&fechaIni=' + fechaDesde.getRawValue() + '&fechaFin=' + fechaHasta.getRawValue() + '&idRecaudador=' + comboRECAUDADOR.getValue() + '&idSucursal=' + comboSUCURSAL.getValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                            });
                            trabajando = false;
                        } catch (e) {

                        }
                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    comboSUCURSAL.disable();
                }
            }]
    });
    return formPanel;
}
function getFormPanelReporteCierres(opDelServlet) {

    var comboGESTIONES = getCombo('GESTION', 'GESTION', 'GESTION', 'GESTION', 'DESCRIPCION_GESTION', 'GESTIÓN', 'GESTION', 'DESCRIPCION_GESTION', 'GESTION', 'GESTIONES...');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');
    var comboUSUARIO = getCombo('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO');
    comboUSUARIO.addListener('select', function (esteCombo, record, index) {
        comboGESTIONES.enable();
        comboGESTIONES.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboGESTIONES.store.baseParams['limit'] = 10;
        comboGESTIONES.store.baseParams['start'] = 0;
        comboGESTIONES.store.reload();

    }, null, null);
    comboRED.addListener('select', function (esteCombo, record, index) {
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null, null);

    comboRECAUDADOR.addListener('select', function (esteCombo, record, index) {
        comboUSUARIO.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();

    }, null, null);

    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            comboRECAUDADOR.disable();
            comboGESTIONES.disable();
            comboUSUARIO.disable();
            comboRECAUDADOR.reset();
            comboUSUARIO.reset();
            comboGESTIONES.reset();
        }

    }, null, null);

    comboRECAUDADOR.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            comboUSUARIO.disable();
            comboUSUARIO.reset();
        }
    }, null, null);

    comboUSUARIO.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            comboGESTIONES.disable();
            comboGESTIONES.reset();
        }
    }, null, null);
    comboGESTIONES.disable();
    comboRECAUDADOR.disable();
    comboUSUARIO.disable();

    comboGESTIONES.allowBlank = true
    comboRED.allowBlank = false
    var fecha = new Ext.form.DateField({
        fieldLabel: 'Fecha',
        height: '22',
        anchor: '95%',
        allowBlank: true,
        format: 'dmY'
    });
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });

    fecha.addListener('valid', function (esteObjeto) {
        comboGESTIONES.store.baseParams['fecha'] = fecha.getRawValue();
        comboGESTIONES.store.reload();
    }, null, null);
    var formPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fecha, comboRED, comboRECAUDADOR, comboUSUARIO, comboGESTIONES, formatoDeDescarga],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {

                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag: 'iframe',
                            id: 'downloadIframe',
                            frameBorder: 0,
                            width: 0,
                            height: 0,
                            css: 'display:yes;visibility:hidden;height:0px;',
                            src: 'reportes?op=' + opDelServlet + '&idUsuario=' + comboUSUARIO.getValue() + '&idRecaudador=' + comboRECAUDADOR.getValue() + '&idRed=' + comboRED.getValue() + '&idGestion=' + comboGESTIONES.getValue() + '&fechaIni=' + fecha.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                        });
                    } catch (e) {

                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    comboGESTIONES.disable();
                    comboRECAUDADOR.disable();
                    comboUSUARIO.disable();

                }
            }]
    });

    return formPanel;

}
function getFormPanelReporteTapalotes(opDelServlet) {
    var comboGRUPOS_USUARIO_FECHA = getCombo('GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'DESCRIPCION_GRUPOS_USUARIO_FECHA', 'GRUPO', 'GRUPOS_USUARIO_FECHA', 'DESCRIPCION_GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS...');
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    var comboRECAUDADOR = getCombo('RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR', 'DESCRIPCION_RECAUDADOR', 'RECAUDADOR', 'RECAUDADOR');


    var comboUSUARIO = getCombo('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO', 'DESCRIPCION_USUARIO', 'USUARIO', 'USUARIO');
    comboUSUARIO.addListener('select', function (esteCombo, record, index) {
        comboGRUPOS_USUARIO_FECHA.enable();
        comboGRUPOS_USUARIO_FECHA.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboGRUPOS_USUARIO_FECHA.store.reload();

    }, null, null);
    comboRED.addListener('select', function (esteCombo, record, index) {
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null, null);

    comboRECAUDADOR.addListener('select', function (esteCombo, record, index) {
        comboUSUARIO.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();

    }, null, null);

    comboRED.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            comboRECAUDADOR.disable();
            comboGRUPOS_USUARIO_FECHA.disable();
            comboUSUARIO.disable();
            comboRECAUDADOR.reset();
            comboUSUARIO.reset();
            comboGRUPOS_USUARIO_FECHA.reset();
        }

    }, null, null);

    comboRECAUDADOR.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue().length == 0) {
            comboUSUARIO.disable();
            comboUSUARIO.reset();
        }
    }, null, null);

    comboUSUARIO.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboGRUPOS_USUARIO_FECHA.disable();
            comboGRUPOS_USUARIO_FECHA.reset();


        }


    }, null, null);

    comboGRUPOS_USUARIO_FECHA.allowBlank = true;
    comboRED.allowBlank = false;
    comboUSUARIO.allowBlank = true;
    comboRECAUDADOR.allowBlank = true;
    comboGRUPOS_USUARIO_FECHA.disable();
    comboRECAUDADOR.disable();
    comboUSUARIO.disable();
    var fechaDesde = new Ext.form.DateField({
        fieldLabel: 'Fecha Desde',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel: 'Fecha Hasta',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'
    });


    fechaDesde.addListener('valid', function (esteObjeto) {

        comboGRUPOS_USUARIO_FECHA.store.baseParams['fechaIni'] = fechaDesde.getRawValue();
        comboGRUPOS_USUARIO_FECHA.store.baseParams['start'] = 0;
        comboGRUPOS_USUARIO_FECHA.store.baseParams['limit'] = 10;
        comboGRUPOS_USUARIO_FECHA.store.reload();

    }, null, null);
    fechaHasta.addListener('valid', function (esteObjeto) {

        comboGRUPOS_USUARIO_FECHA.store.baseParams['fechaFin'] = fechaHasta.getRawValue();
        comboGRUPOS_USUARIO_FECHA.store.baseParams['start'] = 0;
        comboGRUPOS_USUARIO_FECHA.store.baseParams['limit'] = 10;
        comboGRUPOS_USUARIO_FECHA.store.reload();

    }, null, null);

    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });

    var formPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fechaDesde, fechaHasta, comboRED, comboRECAUDADOR, comboUSUARIO, comboGRUPOS_USUARIO_FECHA, formatoDeDescarga],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {
                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag: 'iframe',
                            id: 'downloadIframe',
                            frameBorder: 0,
                            width: 0,
                            height: 0,
                            css: 'display:yes;visibility:hidden;height:0px;',
                            src: 'reportes?op=' + opDelServlet + '&idRed=' + comboRED.getValue() + '&idUsuario=' + comboUSUARIO.getValue() + '&idRecaudador=' + comboRECAUDADOR.getValue() + '&GRUPO=' + comboGRUPOS_USUARIO_FECHA.getValue() + '&fechaIni=' + fechaDesde.getRawValue() + '&fechaFin=' + fechaHasta.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                        });
                    } catch (e) {

                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    comboGRUPOS_USUARIO_FECHA.disable();
                    comboRECAUDADOR.disable();
                    comboUSUARIO.disable();

                }
            }]
    });

    return formPanel;

}
function getFormPanelReporteTapaCaja(opDelServlet) {
    var comboRED = getCombo('RED', 'RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED', 'DESCRIPCION_RED', 'RED', 'RED');
    var comboTIPO = new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        emptyText: 'Tipos...',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['REPORTE', 'REPORTE'],
                ['ROTULO', 'RÓTULO'], ['ROTULO_DETALLE', 'RÓTULO DETALLE']]

        })
    });
    var fechaDESDE = new Ext.form.DateField({
        fieldLabel: 'Fecha Desde',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'
    });
    var fechaHASTA = new Ext.form.DateField({
        fieldLabel: 'Fecha Hasta',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'
    });
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name: 'formatoDescarga',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['pdf', 'PDF'],
                ['xls', 'XLS']]
        })
    });
    var formPanel = new Ext.FormPanel({
        id: opDelServlet,
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboRED, comboTIPO, fechaDESDE, fechaHASTA, formatoDeDescarga],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {
                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag: 'iframe',
                            id: 'downloadIframe',
                            frameBorder: 0,
                            width: 0,
                            height: 0,
                            css: 'display:yes;visibility:hidden;height:0px;',
                            src: 'reportes?op=' + opDelServlet + '&idRed=' + comboRED.getValue() + '&TIPO=' + comboTIPO.getValue() + '&fechaIni=' + fechaDESDE.getRawValue() + '&fechaFin=' + fechaHASTA.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
                        });
                    } catch (e) {
                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                }
            }]
    });
    return formPanel;
}

function alertNoExisteResuladoReporte() {
    Ext.Msg.show({
        title: TITULO_NO_EXISTE_REPORTE,
        msg: CUERPO_NO_EXISTE_REPORTE,
        buttons: Ext.Msg.OK,
        icon: Ext.MessageBox.ERROR
    });

}
/************Item Selector********************/
function itemSelectorSupervisores(elUsuario) {

    var ds_providersDisponible = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'USUARIO?op=LISTAR_SUPERVISORES'

        }),
        reader: new Ext.data.JsonReader({
            root: 'SUPERVISORES',
            id: 'ID_USUARIO',
            totalProperty: 'TOTAL'
        }, [{
                name: 'ID_USUARIO'
            }, {
                name: 'PERSONA'
            }])
    });

    ds_providersDisponible.load({
        params: {
            ID_USUARIO: elUsuario,
            FLAG_ASIGNACION: 'NO'
        }
    });
    var ds_providersSeleccionado = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'USUARIO?op=LISTAR_SUPERVISORES'

        }),
        reader: new Ext.data.JsonReader({
            root: 'SUPERVISORES',
            id: 'ID_USUARIO',
            totalProperty: 'TOTAL'
        }, [{
                name: 'ID_USUARIO'
            }, {
                name: 'PERSONA'
            }])
    });

    ds_providersSeleccionado.load({
        params: {
            ID_USUARIO: elUsuario,
            FLAG_ASIGNACION: 'SI'
        }
    });

    var formulario = new Ext.form.FormPanel({
        bodyStyle: 'padding:10px;',
        items: [{
                xtype: 'itemselector',
                name: 'A_ASIGNAR',
                hideLabel: true,
                imagePath: 'images/',
                multiselects: [{
                        width: 250,
                        height: 200,
                        store: ds_providersDisponible,
                        displayField: 'PERSONA',
                        valueField: 'ID_USUARIO'
                    }, {
                        width: 250,
                        height: 200,
                        store: ds_providersSeleccionado,
                        displayField: 'PERSONA',
                        valueField: 'ID_USUARIO'

                    }]
            }],
        buttons: [{
                text: 'Guardar',
                handler: function () {
                    if (formulario.getForm().isValid()) {

                        formulario.getForm().submit({
                            method: 'POST',
                            waitTitle: 'Conectando',
                            waitMsg: 'Asignando los Supervisores...',
                            url: 'USUARIO?op=GUARDAR_SUPERVISORES',
                            params: {
                                ID_USUARIO: idUSUARIOSeleccionadoID_USUARIO
                            },
                            success: function (form, accion) {
                                ds_providersSeleccionado.load({
                                    params: {
                                        ID_USUARIO: elUsuario,
                                        FLAG_ASIGNACION: 'SI'
                                    }
                                });
                                ds_providersDisponible.load({
                                    params: {
                                        ID_USUARIO: elUsuario,
                                        FLAG_ASIGNACION: 'NO'
                                    }
                                });
                            },
                            failure: function (form, action) {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: FAIL_CUERPO_GENERAL,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                        })
                    }
                }
            }, {
                text: 'Cerrar',
                handler: function () {
                    win.close();
                }
            }]
    });
    var win = new Ext.Window({
        title: 'Asignacion de Supervisores',
        id: 'idAsignacionSupervisores',
        autoWidth: true,
        height: 'auto',
        closable: true,
        resizable: false,
        items: [formulario]
    });
    return win;
}