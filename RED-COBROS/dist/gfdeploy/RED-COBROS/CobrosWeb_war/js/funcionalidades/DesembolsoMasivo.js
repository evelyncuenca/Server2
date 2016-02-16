function cardDesembolsoMasivo() {
    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelDesembolsoMasivo');
        Ext.getCmp('idCedulaDesembolsoMasivo').focus(true, true);
        Ext.getCmp('idHeaderDesembolsoMasivo').getForm().reset();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function panelDesembolsoMasivo() {
    var panelDesembolsoMasivo = {
        id: 'panelDesembolsoMasivo',
        xtype: 'panel',
        title: 'DesembolsoMasivo  ',
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
                items: [cabeceraDesembolsoMasivo()]
            }]
    };

    return panelDesembolsoMasivo;
}

function cabeceraDesembolsoMasivo() {
    var items = {
        xtype: 'form',
        id: 'idHeaderDesembolsoMasivo',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        url: "DesembolsoMasivo?proc=1",
        items: [
            {
                xtype: 'textfield',
                name: 'CEDULA',
                fieldLabel: 'Cedula',
                id: 'idCedulaDesembolsoMasivo',
                allowBlank: false,
                width: '250',
                enableKeyEvents: true,
                monitorValid: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoDesembolsoMasivo').focus(true, true);
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                name: 'TELEFONO',
                fieldLabel: 'Telefono',
                id: 'idTelefonoDesembolsoMasivo',
                allowBlank: false,
                width: '250',
                enableKeyEvents: true,
                monitorValid: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPinDesembolsoMasivo').focus(true, true);
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                inputType: 'password',
                name: 'PIN',
                fieldLabel: 'Pin',
                id: 'idPinDesembolsoMasivo',
                allowBlank: false,
                width: '250',
                enableKeyEvents: true,
                monitorValid: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idMontoDesembolsoMasivo').focus(true, true);
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Monto',
                name: 'MONTO',
                id: 'idMontoDesembolsoMasivo',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addPuntos(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBtnAceptarDesembolsoMasivo').focus(true, true);
                        }
                    }
                }
            }
        ],
        buttonAlign: 'left',
        buttons: [{
                text: 'Aceptar',
                id: 'idBtnAceptarDesembolsoMasivo',
                width: '130',
//                formBind: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    Ext.getCmp('idHeaderDesembolsoMasivo').getForm().submit({
                        success: function (form, result) {
                            unBlockUI();
                            console.log('estoy aqui');
                            Ext.getCmp('panelTicketCB').load({
                                url: 'DesembolsoMasivo?proc=2',
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
                            unBlockUI();
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: 'El archivo no se proceso correctamente, intentelo nuevamente',
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.ERROR,
                                fn: function (btn) {
                                    if (btn === 'ok') {
                                        Ext.getCmp('idArchivoDesembolsoMasivo').focus(true, true);
                                    }
                                }
                            });
                        }
                    });

                }
            },
            {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderDesembolsoMasivo').getForm().reset();
                    Ext.getCmp('idNroCIDesembolsoMasivo').focus(true, true);
                }
            }]
    };
    return items;
}