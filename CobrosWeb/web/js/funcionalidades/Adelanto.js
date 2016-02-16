var st = new Ext.data.Store({
    id: 'storeAdelantosEfectivo',
    proxy: new Ext.data.HttpProxy({
        method: 'post',
        url: 'AdelantoEfectivo?proc=2',
        autoLoad: false
    }),
    reader: new Ext.data.JsonReader({
        root: 'clientes',
        totalProperty: 'total',
        id: 'idCliente',
        fields: ['idCliente', 'nombresApellidos', 'cedula', 'fechaNac', 'direccion', 'sexo', 'telefonoFijo', 'telefonoMovil', 'monto', 'status']
    }),
    listeners: {
        'beforeload': function (store, options) {
        },
        'load': function (store, records, successful, eOpts) {
            unBlockUI();
            if (!successful) {
                Ext.Msg.show({
                    title: FAIL_TITULO_GENERAL,
                    msg: 'Ha ocurrido un error, favor intente m&aacute; tarde',
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR, fn: function (btn) {
                        if (btn === 'ok') {
                        }
                    }
                });
            } else {
                var cantRegistros = store.getTotalCount();
                if (cantRegistros === 0) {
                    Ext.Msg.show({
                        title: FAIL_TITULO_GENERAL,
                        msg: 'No se han encontrado resultados. Favor verifique los datos.',
                        buttons: Ext.Msg.OK,
                        icon: Ext.MessageBox.ERROR,
                        fn: function (btn) {
                            if (btn === 'ok') {
                            }
                        }
                    });
                } else {
                    Ext.getCmp('gridAdelantosEfectivo').show();
                    Ext.getCmp('gridAdelantosEfectivo').getView().refresh();
                }
            }
        }
    }
});

function cardAdelantoEfectivo() {
    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelAdelantoEfectivo');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function panelAdelantoEfectivo() {
    var panelAdelantoEfectivo = {
        id: 'panelAdelantoEfectivo',
        xtype: 'panel',
        title: 'AdelantoEfectivo  ',
        //layout : 'fit',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
                text: 'Salir (Alt+q)',
                iconCls: 'logout',
                handler: function () {
                    cardInicial();
                }
            }],
        items: [{
                xtype: 'panel',
                items: [cabeceraAdelantoEfectivo()]
            }]
    }
    return panelAdelantoEfectivo;
}

function cabeceraAdelantoEfectivo() {
    var items = {
        xtype: 'form',
        id: 'idHeaderAdelantoEfectivo',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        fileUpload: true,
        url: "AdelantoEfectivo?proc=1",
        items: [
            {
                xtype: 'textfield',
                inputType: 'file',
                name: 'ARCHIVO_XLS',
                fieldLabel: 'Archivo',
                id: 'idArchivoAdelantoEfectivo',
                allowBlank: false,
                width: '250',
                enableKeyEvents: true,
                monitorValid: true
            },
            {
                xtype: 'button',
                text: 'Subir Archivo',
                id: 'idSubirArchivoAdeltantoEfectivo',
                iconCls: 'add2',
                handler: function () {
                    blockUI();
                    Ext.getCmp('idHeaderAdelantoEfectivo').getForm().submit({
                        success: function (form, result) {
                            Ext.getCmp('idBtnAceptarAdelantoEfectivo').enable();
                            st.reload();
                        },
                        failure: function () {
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: 'El archivo no se proceso correctamente, intentelo nuevamente',
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.ERROR,
                                fn: function (btn) {
                                    if (btn === 'ok') {
                                        Ext.getCmp('idArchivoAdelantoEfectivo').focus(true, true);
                                    }
                                }
                            });
                        }
                    });
                }
            },
            gridAdelantosEfectivo()
        ],
        buttonAlign: 'left',
        buttons: [{
                text: 'Realizar Acreditación',
                id: 'idBtnAceptarAdelantoEfectivo',
                width: '130',
                disabled: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    $.ajax({
                        type: "POST",
                        url: 'AdelantoEfectivo?proc=3',
                        success: function (data) {
                            Ext.getCmp('idHeaderAdelantoEfectivo').getForm().submit({
                                success: function (form, result) {
                                    Ext.Msg.hide();
                                    console.log('estoy aqui');
                                    Ext.getCmp('panelTicketCB').load({
                                        url: 'AdelantoEfectivo?proc=4',
                                        method: 'POST',
                                        callback: function (r, options, success) {
                                            console.log('estoy aqui 2');
                                            unBlockUI();
                                        },
                                        params: {
                                        }
                                    });
                                    Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                                },
                                failure: function () {
                                    Ext.Msg.show({
                                        title: FAIL_TITULO_GENERAL,
                                        msg: 'El archivo no se proceso correctamente, intentelo nuevamente',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.MessageBox.ERROR,
                                        fn: function (btn) {
                                            if (btn === 'ok') {
                                                Ext.getCmp('idArchivoAdelantoEfectivo').focus(true, true);
                                            }
                                        }
                                    });
                                }
                            });
                        },
                        error: function () {
                            alert('No se pudo realizar la acreditacion');
                        }
                    });

                }
            },
            {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderAdelantoEfectivo').getForm().reset();
                    Ext.getCmp('idNroCIAdelantoEfectivo').focus(true, true);
                }
            }]
    };
    return items;
}

function gridAdelantosEfectivo() {

    function renderMoneda(value) {
        if (value > 0) {
            return addPuntos(replaceAllNoNumbers(String(value))) + ' Gs.';
        } else {
            return String(value) + ' Gs.';

        }
    }

    function renderNumero(value) {
        if (value > 0) {
            return addPuntos(replaceAllNoNumbers(String(value)));
        } else {
            return String(value);

        }
    }

    var anchoDefaultColumnas = 200;
    var colModel = new Ext.grid.ColumnModel([{
            header: '<b>Nro.</b>',
            width: 20,
            dataIndex: 'idCliente'
        },
        {
            header: '<b>Nombre y Apellido</b>',
            flex: 1,
            dataIndex: 'nombresApellidos',
            width: 250
        },
        {
            header: '<b>Nro. C&eacute;dula</b>',
            width: 100,
            dataIndex: 'cedula',
            renderer: renderNumero
        },
        {
            header: '<b>Fecha Nac.</b>',
            width: 100,
            dataIndex: 'fechaNac'
        },
        {
            header: '<b>Direccion</b>',
            width: 250,
            dataIndex: 'direccion'
        },
        {
            header: '<b>Sexo</b>',
            width: 100,
            dataIndex: 'sexo'
        }, {
            header: '<b>Telefono Fijo</b>',
            width: 150,
            dataIndex: 'telefonoFijo'
        },
        {
            header: '<b>Telefono Movil</b>',
            width: 150,
            dataIndex: 'telefonoMovil'
        },
        {
            header: '<b>Monto</b>',
            width: 150,
            dataIndex: 'monto',
            renderer: renderMoneda
        }, {
            header: '<b>Estado</b>',
            width: 150,
            dataIndex: 'status'
        }]);

    var grilla = new Ext.grid.GridPanel({
        title: 'Clientes',
        id: 'gridAdelantosEfectivo',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit: true
        },
        bbar: new Ext.PagingToolbar({
            pageSize: 5,
            store: st,
            displayInfo: true,
            displayMsg: 'Mostrando Clientes {0} - {1} de {2}',
            emptyMsg: "No hay Clientes que mostrar",
            items: ['-']
        }),
        frame: true,
        iconCls: 'icon-grid',
        hidden: true
    });

    return grilla;
}