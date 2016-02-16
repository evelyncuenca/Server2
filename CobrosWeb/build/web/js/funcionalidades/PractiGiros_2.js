var minDateY = new Date();
minDateY.setYear(minDateY.getYear() - 18);
function cardPractiGirosV2() {
    if (GESTION_ABIERTA) {
        opPractiGirosV2().center();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function opPractiGirosV2() {
    var win = new Ext.Window({
        title: 'Elija una opcion de PractiGIROS',
        autoWidth: true,
        height: 'auto',
        id: 'idWinopPractiGirosV2',
        closable: false,
        resizable: false,
        modal: true,
        defaultButton: 0,
        buttonAlign: 'right',
        buttons: [{
                text: 'Cancelar',
                handler: function () {
                    win.close();
                }
            }],
        items: [{
                html: '<a href=\'#\'  ><img onclick=\'cardPractiEnvioV2();\' src=\'images/btnPractiEnvio.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                      <a href=\'#\'  ><img onclick=\'cardPractiRetiroV2();\' src=\'images/btnPractiRetiro.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>'
            }]

    });
    return win.show();
}


function panelPractiEnvioV2() {
    var panelPractiEnvioV2 = {
        id: 'panelPractiEnvioV2',
        xtype: 'panel',
        title: 'PractiEnvio Remesa Pura',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
                text: 'Salir (Alt + Q)',
                iconCls: 'logout',
                handler: function () {
                    Ext.Msg.show({
                        title: 'Info',
                        msg: 'Seguro que desea salir?',
                        animEl: 'elId',
                        icon: Ext.MessageBox.WARNING,
                        buttons: Ext.Msg.OKCANCEL,
                        fn: function (btn) {
                            if (btn === "ok") {
                                Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
                                cardInicial();
                            } else {

                            }
                        }
                    });
                }
            }],
        items: [envioPractiGiroV2(), activarClienteOrigenV2()]
    };
    return panelPractiEnvioV2;
}

function panelPractiRetiroV2() {
    var panelPractiRetiroV2 = {
        id: 'panelPractiRetiroV2',
        xtype: 'panel',
        title: 'PractiRetiro Remesa Pura',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
                text: 'Salir',
                iconCls: 'logout',
                handler: function () {
                    Ext.Msg.show({
                        title: 'Info',
                        msg: 'Seguro que desea salir?',
                        animEl: 'elId',
                        icon: Ext.MessageBox.WARNING,
                        buttons: Ext.Msg.OKCANCEL,
                        fn: function (btn) {
                            if (btn === "ok") {
                                Ext.getCmp('idHeaderRetiroV2sPractiGiro').getForm().reset();
                                cardInicial();
                            } else {

                            }
                        }
                    });
                }
            }],
        items: [retiroPractiGiroV2(), activarClienteDestinoV2()]
    };
    return panelPractiRetiroV2;
}



function cardPractiEnvioV2() {
    Ext.getCmp('idWinopPractiGirosV2').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelPractiEnvioV2');
    Ext.getCmp('idHeaderEnvioV2sPractiGiro').show();
    Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
    Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').hide();
    Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').getForm().reset();
}

function cardPractiRetiroV2() {
    Ext.getCmp('idWinopPractiGirosV2').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelPractiRetiroV2');
    Ext.getCmp('idHeaderRetiroV2sPractiGiro').show();
    //    Ext.getCmp('idHeaderRetiroV2sPractiGiro').hide();
    Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').hide();
    //    Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').show();
    Ext.getCmp('idHeaderRetiroV2sPractiGiro').getForm().reset();
    Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').getForm().reset();
}

function retiroPractiGiroV2() {

    var items = {
        xtype: 'form',
        id: 'idHeaderRetiroV2sPractiGiro',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        title: 'Datos de la Remesa',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items: [{
                xtype: 'textfield',
                fieldLabel: 'Cédula',
                id: 'idCedulaRetiroV2PractiGiro',
                name: 'CEDULA',
                allowBlank: false,
                width: '100',
                labelStyle: 'font-weight:bold; width: 150px;',
                maxLength: 10,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idConfirmarCedulaRetiroV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Confirmar Cédula',
                id: 'idConfirmarCedulaRetiroV2PractiGiro',
                allowBlank: false,
                width: '100',
                labelStyle: 'font-weight:bold; width: 150px;',
                maxLength: 10,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoRetiroV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Teléfono',
                id: 'idTelefonoRetiroV2PractiGiro',
                name: 'TELEFONO',
                allowBlank: false,
                width: '100',
                labelStyle: 'font-weight:bold; width: 150px;',
                maxLength: 10,
                enableKeyEvents: true,
                listeners: {
                    'focus': function (esteObjeto, esteEvento) {
                        if (Ext.getCmp('idConfirmarCedulaRetiroV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaRetiroV2PractiGiro').getRawValue()) {
                            mostrarError('No coincide la Cédula  con la confirmación');
                            
                        } else {
                            Ext.getCmp('idTelefonoRetiroV2PractiGiro').focus(true, true);
                        }
                    },
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idConfirmarTelefonoRetiroV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Confirmar Teléfono',
                id: 'idConfirmarTelefonoRetiroV2PractiGiro',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100',
                maxLength: 10,
                enableKeyEvents: true,
                listeners: {
                    'focus': function (esteObjeto, esteEvento) {
                        if (Ext.getCmp('idConfirmarCedulaRetiroV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaRetiroV2PractiGiro').getRawValue()) {
                            mostrarError('No coincide la Cédula  con la confirmación');
                            
                        
                        }
                    },
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        Ext.getCmp('idPinRetiroV2PractiGiro').focus(true, true);
                    }
                }
            }, {
                xtype: 'textfield',
                inputType: 'password',
                fieldLabel: 'Pin de Retiro',
                id: 'idPinRetiroV2PractiGiro',
                name: 'PIN',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100',
                maxLength: 4,
                enableKeyEvents: true,
                listeners: {
                    'focus': function (esteObjeto, esteEvento) {
                        if (Ext.getCmp('idConfirmarTelefonoRetiroV2PractiGiro').getRawValue() !== Ext.getCmp('idTelefonoRetiroV2PractiGiro').getRawValue()) {
                            mostrarError('No coincide el Teléfono con la confirmación');
                            
                        
                        }

                    },
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBtnConsultarRetiroV2PractiGiro').focus(true, true);

                        }
                    }
                }
            },
            {
                xtype: 'button',
                text: 'Consultar',
                width: '130',
                iconCls: 'help',
                autoEl: 'center',
                id: 'idBtnConsultarRetiroV2PractiGiro',
                handler: function () {

                    if (Ext.getCmp('idConfirmarCedulaRetiroV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaRetiroV2PractiGiro').getRawValue()) {
                        mostrarError('No coincide la Cédula  con la confirmación');
                        
                    } else if (Ext.getCmp('idConfirmarTelefonoRetiroV2PractiGiro').getRawValue() !== Ext.getCmp('idTelefonoRetiroV2PractiGiro').getRawValue()) {
                        mostrarError('No coincide el Teléfono con la confirmación');
                        
                    } else {

                        var randomNumber = Math.floor((Math.random() * 10000000) + 1);

                        $.ajax({
                            type: "POST",
                            url: 'COBRO_SERVICIOS?op=CONSULTAR_RETIRO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                            data: {
                                CEDULA: Ext.getCmp('idCedulaRetiroV2PractiGiro').getRawValue(),
                                TELEFONO: Ext.getCmp('idTelefonoRetiroV2PractiGiro').getRawValue(),
                                PIN: Ext.getCmp('idPinRetiroV2PractiGiro').getRawValue()
                            },
                            success: function (data) {
                                unBlockUI();
                                var jsonData = Ext.util.JSON.decode(data);
                                console.log(jsonData);
                                if (!jsonData.success) {
                                    mostrarError("Pin, Telefono, o Cedula Incorrectos")
                                } else {
                                    var monto = jsonData.monto;
                                    Ext.getCmp('idNMontoRetiroV2PractiGiro').setValue(addPuntos(monto.toString()));
                                    Ext.getCmp('idBtnConfirmarRetiroV2PractiGiro').focus(true, true);
                                    Ext.getCmp('idBtnConfirmarRetiroV2PractiGiro').enable();
                                }
                            },
                            error: function () {
                                alert('No se pudo obtener el monto de Retiro');
                            }
                        });
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Monto a retirar',
                id: 'idNMontoRetiroV2PractiGiro',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100', disabled: true
            }
        ],
        buttonAlign: 'left',
        buttons: [{
                text: 'Confirmar',
                id: 'idBtnConfirmarRetiroV2PractiGiro',
                width: '130',
                formBind: true,
                disabled: true,
                handler: function () {//llamada al autorizador 
                    Ext.Msg.show({
                        title: 'Info',
                        msg: 'Confirma los datos de retiro?',
                        animEl: 'elId',
                        icon: Ext.MessageBox.INFO,
                        buttons: Ext.Msg.OKCANCEL,
                        fn: function (btn) {
                            blockUI();
                            if (btn === "ok") {
                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                Ext.getCmp('idBtnConfirmarRetiroV2PractiGiro').disable();

                                $.ajax({
                                    type: "POST",
                                    url: 'COBRO_SERVICIOS?op=REALIZAR_RETIRO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                                    data: {
                                        CEDULA: Ext.getCmp('idCedulaRetiroV2PractiGiro').getRawValue(),
                                        TELEFONO: Ext.getCmp('idTelefonoRetiroV2PractiGiro').getRawValue(),
                                        PIN: Ext.getCmp('idPinRetiroV2PractiGiro').getRawValue(),
                                        MONTO_RETIRO: Ext.getCmp('idNMontoRetiroV2PractiGiro').getRawValue()
                                    },
                                    success: function (data) {
                                        unBlockUI();
                                        var jsonData = Ext.util.JSON.decode(data);
                                        console.log(jsonData);
                                        if (!jsonData.success) {
                                            if (jsonData.estado === 20) {
                                                Ext.Msg.show({
                                                    title: 'Info',
                                                    msg: jsonData.mensaje,
                                                    animEl: 'elId',
                                                    icon: Ext.MessageBox.INFO,
                                                    buttons: Ext.Msg.OKCANCEL,
                                                    fn: function (btn) {
                                                        if (btn === "ok") {
                                                            Ext.getCmp('idHeaderRetiroV2sPractiGiro').hide();
                                                            Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').show();
                                                        } else {
                                                            Ext.getCmp('idHeaderRetiroV2sPractiGiro').getForm().reset();
//                                                            Ext.getCmp('idHeaderRetiroV2sPractiGiro').hide();
//                                                            Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                mostrarError(jsonData.mensaje);
                                            }
                                        } else {
                                            var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                            Ext.Msg.wait('Procesando... Por Favor espere...');
                                            Ext.Msg.hide();
                                            Ext.getCmp('panelTicketCB').load({
                                                url: 'COBRO_SERVICIOS?op=TICKET_RETIRO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                                discardUrl: false,
                                                nocache: true,
                                                text: "Procesando... Por Favor espere...",
                                                scripts: true
                                            });
                                            Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                                        }
                                    },
                                    error: function () {
                                        alert('No se pudo obtener el monto de Retiro');
                                    }
                                });

                            } else {
                                unBlockUI();
                            }
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderRetiroV2sPractiGiro').getForm().reset();
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreRetiroV2PractiGiro').disable();
                    Ext.getCmp('idApellidoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocRetiroV2PractiGiro').disable();
                    Ext.getCmp('idDireccionRetiroV2PractiGiro').disable();
                    Ext.getCmp('idTelefonoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idProfesionRetiroV2PractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();
                }
            }]
    };
    return items;
}

function activarClienteOrigenV2() {

    var sexos = '[{"descripcion": "Masculino", codigo: "M"}, {"descripcion": "Femenino", codigo: "F"}]';
    var estadosCiviles = '[{"descripcion": "Soltero", codigo: "S"}, {"descripcion": "Casado", codigo: "C"}]';

    var mystore = new Ext.data.JsonStore({
        fields: [
            {type: 'string', name: 'codigo'},
            {type: 'string', name: 'descripcion'}
        ]
    });

    var mystoreES = new Ext.data.JsonStore({
        fields: [
            {type: 'string', name: 'codigo'},
            {type: 'string', name: 'descripcion'}
        ]
    });

    mystore.loadData(Ext.decode(sexos));
    mystoreES.loadData(Ext.decode(estadosCiviles));

    var comboSexo = new Ext.form.ComboBox({
        fieldLabel: 'Sexo(*)',
        id: 'idSexoClienteEnvioV2PractiGiro',
        store: mystore,
        emptyText: 'Seleccione una opción...',
        forceSelection: true,
        displayField: 'descripcion',
        valueField: 'codigo',
        mode: 'local',
        name: 'SEXO',
        width: 160,
        listWidth: 160,
        triggerAction: 'all',
        selectOnFocus: true,
        loadingText: 'Cargando...',
        listeners: {
            select: function () {
            }

        }
    });

    var comboEstadoCivil = new Ext.form.ComboBox({
        fieldLabel: 'EstadoCivil(*)',
        id: 'idEstadoCivilClienteEnvioV2PractiGiro',
        store: mystoreES,
        emptyText: 'Seleccione una opción...',
        forceSelection: true,
        displayField: 'descripcion',
        valueField: 'codigo',
        mode: 'local',
        name: 'ESTADO_CIVIL',
        width: 160,
        listWidth: 160,
        triggerAction: 'all',
        selectOnFocus: true,
        loadingText: 'Cargando...',
        listeners: {
            select: function () {
            }

        }
    });


    var items = {
        xtype: 'form',
        id: 'idHeaderActivarClienteEnvioV2sPractiGiro',
        monitorValid: true,
        height: 'auto',
        hidden: true,
        width: 'auto',
        frame: true,
        title: 'Datos del Cliente',
        bodyStyle: 'padding-left:10px;',
        fileUpload: true,
        url: "ActivarCliente?proc=4",
        items: [{
                xtype: 'textfield',
                fieldLabel: 'Primer Nombre(*)',
                id: 'idPrimerNombreClienteEnvioV2PractiGiro',
                name: 'PRIMER_NOMBRE',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoNombreClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Nombre',
                id: 'idSegundoNombreClienteEnvioV2PractiGiro',
                name: 'SEGUNDO_NOMBRE',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPrimerApellidoClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Primer Apellido(*)',
                id: 'idPrimerApellidoClienteEnvioV2PractiGiro',
                name: 'PRIMER_APELLIDO',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoApellidoClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Apellido',
                id: 'idSegundoApellidoClienteEnvioV2PractiGiro',
                name: 'SEGUNDO_APELLIDO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idNroDocumentoEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Nro. Documento(*)',
                id: 'idNroDocumentoEnvioV2PractiGiro',
                name: 'NRO_DOC',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idFechaNacimientoEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'datefield',
                fieldLabel: 'Fecha Nacimiento(*)',
                id: 'idFechaNacimientoEnvioV2PractiGiro',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                maxValue: minDateY,
                name: 'FECHA_NAC',
                format: 'd/m/Y',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idRucClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Ruc',
                id: 'idRucClienteEnvioV2PractiGiro',
                name: 'RUC',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDireccionClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Dirección(*)',
                id: 'idDireccionClienteEnvioV2PractiGiro',
                name: 'DIRECCION',
                allowBlank: false,
                width: '150',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSexoClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            comboSexo,
            comboEstadoCivil,
            {
                xtype: 'textfield',
                fieldLabel: 'Teléfono Fijo',
                id: 'idTelefonoFijoClienteEnvioV2PractiGiro',
                name: 'TELEFONO_FIJO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoMovilClienteEnvioV2PractiGiro').enable();
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Telefono Movil(*)',
                id: 'idTelefonoMovilClienteEnvioV2PractiGiro',
                name: 'TELEFONO_MOVIL',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBarrioClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Barrio',
                id: 'idBarrioClienteEnvioV2PractiGiro',
                name: 'BARRIO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCiudadClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Ciudad',
                id: 'idCiudadClienteEnvioV2PractiGiro',
                name: 'CIUDAD',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCodigoPostalClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Profesion(*)',
                id: 'idCodigoPostalClienteEnvioV2PractiGiro',
                name: 'CODIGO_POSTAL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDptoClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    },
                    'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Lugar Nacimiento(*)',
                id: 'idDptoClienteEnvioV2PractiGiro',
                name: 'DEPARTAMENTO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPisoClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Tipo Documento(*)',
                id: 'idPisoClienteEnvioV2PractiGiro',
                name: 'PISO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idMailClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Email',
                id: 'idMailClienteEnvioV2PractiGiro',
                name: 'EMAIL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCedulaFrontalClienteEnvioV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            }],
        buttonAlign: 'left',
        buttons: [{
                text: 'Confirmar',
                id: 'idBtnConfirmarActivarClienteEnvioV2PractiGiro',
                width: '130',
                formBind: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').getForm().submit({
                        success: function (form, result) {
                            Ext.Msg.hide();
                            console.log('estoy aqui');

                            Ext.Msg.wait('Procesando... Por Favor espere...');
                            Ext.Msg.hide();
                            var fp = 1;
                            var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                            Ext.getCmp('panelTicketCB').load({
                                url: 'ActivarCliente?proc=2&ID_RANDOM=' + randomNumber,
                                discardUrl: false,
                                nocache: true,
                                text: "Procesando... Por Favor espere...",
                                scripts: true
                            });
                            Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');

                        },
                        failure: function (form, action) {
                            Ext.Msg.hide();
                            var obj = Ext.util.JSON.decode(action.response.responseText);

                            if (obj.motivo !== undefined) {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            } else {
                                Ext.Msg.show({
                                    title: 'Estado',
                                    msg: 'Ha ocurrido un error en el proceso de activacion',
                                    icon: Ext.MessageBox.ERROR,
                                    buttons: Ext.Msg.OK,
                                    fn: function (btn) {
                                        if (btn === "ok") {
                                            cardInicial();
                                        } else {
                                            cardInicial();
                                        }
                                    }
                                });
                            }

                        }
                    });
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
                    Ext.getCmp('idDocumentoEnvioV2PractiGiro').enable();
                    tipoDoc.focus(true, true);
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreEnvioV2PractiGiro').disable();
                    Ext.getCmp('idApellidoEnvioV2PractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoEnvioV2PractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocEnvioV2PractiGiro').disable();
                    Ext.getCmp('idDireccionEnvioV2PractiGiro').disable();
                    Ext.getCmp('idTelefonoEnvioV2PractiGiro').disable();
                    Ext.getCmp('idProfesionEnvioV2PractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();


                }
            }]
    };
    return items;

}
function activarClienteDestinoV2() {
    var sexos = '[{"descripcion": "Masculino", codigo: "M"}, {"descripcion": "Femenino", codigo: "F"}]';
    var estadosCiviles = '[{"descripcion": "Soltero", codigo: "S"}, {"descripcion": "Casado", codigo: "C"}]';

    var mystore = new Ext.data.JsonStore({
        fields: [
            {type: 'string', name: 'codigo'},
            {type: 'string', name: 'descripcion'}
        ]
    });

    var mystoreES = new Ext.data.JsonStore({
        fields: [
            {type: 'string', name: 'codigo'},
            {type: 'string', name: 'descripcion'}
        ]
    });

    mystore.loadData(Ext.decode(sexos));
    mystoreES.loadData(Ext.decode(estadosCiviles));

    var comboSexo = new Ext.form.ComboBox({
        fieldLabel: 'Sexo(*)',
        id: 'idSexoClienteRetiroV2PractiGiro',
        store: mystore,
        emptyText: 'Seleccione una opción...',
        forceSelection: true,
        displayField: 'descripcion',
        valueField: 'codigo',
        mode: 'local',
        name: 'SEXO',
        width: 160,
        listWidth: 160,
        triggerAction: 'all',
        selectOnFocus: true,
        loadingText: 'Cargando...',
        listeners: {
            select: function () {
            }

        }
    });

    var comboEstadoCivil = new Ext.form.ComboBox({
        fieldLabel: 'EstadoCivil(*)',
        id: 'idEstadoCivilClienteRetiroV2PractiGiro',
        store: mystoreES,
        emptyText: 'Seleccione una opción...',
        forceSelection: true,
        displayField: 'descripcion',
        valueField: 'codigo',
        mode: 'local',
        name: 'ESTADO_CIVIL',
        width: 160,
        listWidth: 160,
        triggerAction: 'all',
        selectOnFocus: true,
        loadingText: 'Cargando...',
        listeners: {
            select: function () {
            }

        }
    });


    var items = {
        xtype: 'form',
        id: 'idHeaderActivarClienteRetiroV2sPractiGiro',
        monitorValid: true,
        height: 'auto',
        hidden: true,
        width: 'auto',
        frame: true,
        title: 'Datos del Cliente',
        bodyStyle: 'padding-left:10px;',
        fileUpload: true,
        url: "ActivarCliente?proc=1",
        items: [{
                xtype: 'textfield',
                fieldLabel: 'Primer Nombre(*)',
                id: 'idPrimerNombreClienteRetiroV2PractiGiro',
                name: 'PRIMER_NOMBRE',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoNombreClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Nombre',
                id: 'idSegundoNombreClienteRetiroV2PractiGiro',
                name: 'SEGUNDO_NOMBRE',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPrimerApellidoClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Primer Apellido(*)',
                id: 'idPrimerApellidoClienteRetiroV2PractiGiro',
                name: 'PRIMER_APELLIDO',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoApellidoClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Apellido',
                id: 'idSegundoApellidoClienteRetiroV2PractiGiro',
                name: 'SEGUNDO_APELLIDO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idNroDocumentoRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Nro. Documento(*)',
                id: 'idNroDocumentoRetiroV2PractiGiro',
                name: 'NRO_DOC',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idFechaNacimientoRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'datefield',
                fieldLabel: 'Fecha Nacimiento(*)',
                id: 'idFechaNacimientoRetiroV2PractiGiro',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                maxValue: minDateY,
                name: 'FECHA_NAC',
                format: 'd/m/Y',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idRucClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Ruc',
                id: 'idRucClienteRetiroV2PractiGiro',
                name: 'RUC',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDireccionClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Dirección(*)',
                id: 'idDireccionClienteRetiroV2PractiGiro',
                name: 'DIRECCION',
                allowBlank: false,
                width: '150',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSexoClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            comboSexo,
            comboEstadoCivil,
            {
                xtype: 'textfield',
                fieldLabel: 'Teléfono Fijo',
                id: 'idTelefonoFijoClienteRetiroV2PractiGiro',
                name: 'TELEFONO_FIJO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoMovilClienteRetiroV2PractiGiro').enable();
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Telefono Movil(*)',
                id: 'idTelefonoMovilClienteRetiroV2PractiGiro',
                name: 'TELEFONO_MOVIL',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBarrioClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Barrio',
                id: 'idBarrioClienteRetiroV2PractiGiro',
                name: 'BARRIO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCiudadClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Ciudad',
                id: 'idCiudadClienteRetiroV2PractiGiro',
                name: 'CIUDAD',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCodigoPostalClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Profesion(*)',
                id: 'idCodigoPostalClienteRetiroV2PractiGiro',
                name: 'CODIGO_POSTAL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDptoClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Lugar Nacimiento(*)',
                id: 'idDptoClienteRetiroV2PractiGiro',
                name: 'DEPARTAMENTO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPisoClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Tipo Documento(*)',
                id: 'idPisoClienteRetiroV2PractiGiro',
                name: 'PISO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idMailClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Email',
                id: 'idMailClienteRetiroV2PractiGiro',
                name: 'EMAIL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCedulaFrontalClienteRetiroV2PractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        var value = esteObjeto.getRawValue().toString().trim();
                        if (value.length > 0) {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().toUpperCase());
                        } else {
                            esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                        }
                    }
                }
            }],
        buttonAlign: 'left',
        buttons: [{
                text: 'Confirmar',
                id: 'idBtnConfirmarActivarClienteRetiroV2PractiGiro',
                width: '130',
                formBind: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    Ext.getCmp('idHeaderActivarClienteRetiroV2sPractiGiro').getForm().submit({
                        success: function (form, result) {
                            Ext.Msg.hide();
                            console.log('estoy aqui');

                            Ext.Msg.wait('Procesando... Por Favor espere...');
                            Ext.Msg.hide();
                            var fp = 1;
                            var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                            Ext.getCmp('panelTicketCB').load({
                                url: 'ActivarCliente?proc=2&ID_RANDOM=' + randomNumber,
                                discardUrl: false,
                                nocache: true,
                                text: "Procesando... Por Favor espere...",
                                scripts: true
                            });
                            Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                        },
                        failure: function (form, action) {
                            Ext.Msg.hide();
                            var obj = Ext.util.JSON.decode(action.response.responseText);

                            if (obj.motivo !== undefined) {
                                Ext.Msg.show({
                                    title: FAIL_TITULO_GENERAL,
                                    msg: obj.motivo,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            } else {
                                Ext.Msg.show({
                                    title: 'Estado',
                                    msg: 'Ha ocurrido un error en el proceso de activacion',
                                    icon: Ext.MessageBox.ERROR,
                                    buttons: Ext.Msg.OK,
                                    fn: function (btn) {
                                        if (btn === "ok") {
                                            cardInicial();
                                        } else {
                                            cardInicial();
                                        }
                                    }
                                });
                            }

                            console.log('estoy aqui');

                        }
                    });
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderRetiroV2sPractiGiro').getForm().reset();
                    Ext.getCmp('idDocumentoRetiroV2PractiGiro').enable();
                    tipoDoc.focus(true, true);
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreRetiroV2PractiGiro').disable();
                    Ext.getCmp('idApellidoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocRetiroV2PractiGiro').disable();
                    Ext.getCmp('idDireccionRetiroV2PractiGiro').disable();
                    Ext.getCmp('idTelefonoRetiroV2PractiGiro').disable();
                    Ext.getCmp('idProfesionRetiroV2PractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();


                }
            }]
    };
    return items;

}

function envioPractiGiroV2() {
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'Bancos', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'Bancos');
    comboENTIDAD_FINANCIERA.id = "idComboEntidadFinancieraEnvioV2PractiGiro";
    comboENTIDAD_FINANCIERA.addListener('select', function (esteCombo, record, index) {
        Ext.getCmp('idNumeroChequeEnvioV2PractiGiro').focus(true, true);
    }, null, null);
    comboENTIDAD_FINANCIERA.disable();
    var validado1 = false;
    var items = {
        xtype: 'form',
        title: 'Envios',
        id: 'idHeaderEnvioV2sPractiGiro',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        bodyStyle: 'padding:3px 3px 8px 8px;',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').focus(true, true);
            }
        },
        items: [{
                layout: 'column',
                items: [{
                        columnWidth: .20,
                        layout: 'form',
                        xtype: 'fieldset',
                        title: 'Origen',
                        bodyStyle: 'padding:5px;',
                        items: [{
                                xtype: 'textfield',
                                fieldLabel: 'Cédula',
                                id: 'idCedulaOrigenEnvioV2PractiGiro',
                                name: 'CEDULA_ORIGEN',
                                allowBlank: true,
                                width: '100',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idConfirmarCedulaOrigenEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Cédula',
                                id: 'idConfirmarCedulaOrigenEnvioV2PractiGiro',
                                allowBlank: true,
                                width: '100',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }]
                    }, {
                        columnWidth: .25,
                        layout: 'form',
                        xtype: 'fieldset',
                        title: 'Destino',
                        bodyStyle: ' padding:5px;',
                        items: [{
                                xtype: 'textfield',
                                fieldLabel: 'Cédula',
                                id: 'idCedulaDestinoEnvioV2PractiGiro',
                                name: 'CEDULA_DESTINO',
                                allowBlank: false,
                                width: '100',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarCedulaOrigenEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Origen con la confirmación');
                                            
                                        } else {
                                            validado1 = true;
                                            if (Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue() !== '') {
                                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                                blockUI();
                                                $.ajax({
                                                    type: "POST",
                                                    url: 'ActivarCliente?proc=3&ID_RANDOM=' + randomNumber,
                                                    data: {
                                                        CEDULA: Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue()
                                                    },
                                                    success: function (data) {
                                                        unBlockUI();
                                                        var jsonD = Ext.util.JSON.decode(data);
                                                        console.log(jsonD.status);
                                                        if (jsonD.status !== 0) {
                                                            Ext.Msg.show({
                                                                title: 'Info',
                                                                msg: 'El remitente no completo el formulario de aceptacion.',
                                                                animEl: 'elId',
                                                                icon: Ext.MessageBox.INFO,
                                                                buttons: Ext.Msg.OKCANCEL,
                                                                fn: function (btn) {
                                                                    if (btn === "ok") {
                                                                        Ext.getCmp('idHeaderEnvioV2sPractiGiro').hide();
                                                                        Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').show();
                                                                    } else {
                                                                        Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
//                                                                            Ext.getCmp('idHeaderEnvioV2sPractiGiro').hide();
//                                                                            Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            //Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').focus(true, true);
                                                        }
                                                    },
                                                    error: function () {
                                                        unBlockUI();
                                                        Ext.Msg.show({
                                                            title: 'Estado',
                                                            msg: 'No se pudo determinar si el cliente existe, favor comuniquese a sau@documenta.com.py',
                                                            icon: Ext.MessageBox.ERROR,
                                                            buttons: Ext.Msg.OK,
                                                            fn: function (btn) {
                                                                if (btn === "ok") {
                                                                    cardInicial();
                                                                } else {
                                                                    cardInicial();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }

                                        }

                                    },
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idConfirmarCedulaDestinoEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Cédula',
                                id: 'idConfirmarCedulaDestinoEnvioV2PractiGiro',
                                allowBlank: false,
                                width: '100',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarCedulaOrigenEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Origen con la confirmación');
                                            
                                        } else if (!validado1) {
                                            if (Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue() !== '') {
                                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                                blockUI();
                                                $.ajax({
                                                    type: "POST",
                                                    url: 'ActivarCliente?proc=3&ID_RANDOM=' + randomNumber,
                                                    data: {
                                                        CEDULA: Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue()
                                                    },
                                                    success: function (data) {
                                                        unBlockUI();
                                                        var jsonD = Ext.util.JSON.decode(data);
                                                        console.log(jsonD.status);
                                                        if (jsonD.status !== 0) {
                                                            Ext.Msg.show({
                                                                title: 'Info',
                                                                msg: 'El remitente no completo el formulario de aceptacion.',
                                                                animEl: 'elId',
                                                                icon: Ext.MessageBox.INFO,
                                                                buttons: Ext.Msg.OKCANCEL,
                                                                fn: function (btn) {
                                                                    if (btn === "ok") {
                                                                        Ext.getCmp('idHeaderEnvioV2sPractiGiro').hide();
                                                                        Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').show();
                                                                    } else {
                                                                        Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
//                                                                            Ext.getCmp('idHeaderEnvioV2sPractiGiro').hide();
//                                                                            Ext.getCmp('idHeaderActivarClienteEnvioV2sPractiGiro').show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            //Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').focus(true, true);
                                                        }
                                                    },
                                                    error: function () {
                                                        unBlockUI();
                                                        Ext.Msg.show({
                                                            title: 'Estado',
                                                            msg: 'No se pudo determinar si el cliente existe, favor comuniquese a sau@documenta.com.py',
                                                            icon: Ext.MessageBox.ERROR,
                                                            buttons: Ext.Msg.OK,
                                                            fn: function (btn) {
                                                                if (btn === "ok") {
                                                                    cardInicial();
                                                                } else {
                                                                    cardInicial();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }

                                        }
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Teléfono',
                                id: 'idTelefonoDestinoEnvioV2PractiGiro',
                                name: 'TELEFONO_DESTINO',
                                allowBlank: false,
                                width: '100',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarCedulaDestinoEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Destino con la confirmación');
                                            
                                        }
                                    },
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idConfirmarTelefonoDestinoEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Teléfono',
                                id: 'idConfirmarTelefonoDestinoEnvioV2PractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarCedulaDestinoEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Destino con la confirmación');
                                            
                                        }
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                    	if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                        	Ext.getCmp('idMontoEnvioV2PractiGiro').focus(true, true);
                                    	}
                                    }
                                }
                            }
                        ]
                    }, {
                        columnWidth: .20,
                        layout: 'form',
                        xtype: 'fieldset',
                        title: 'Monto',
                        bodyStyle: ' padding:5px;',
                        items: [{
                                xtype: 'textfield',
                                fieldLabel: 'Monto a enviar',
                                id: 'idMontoEnvioV2PractiGiro',
                                name: 'MONTO_ENVIO',
                                labelStyle: 'font-weight:bold; width: 150px;',
                                allowBlank: false,
                                width: '100',
                                enableKeyEvents: true,
                                listeners: {
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarTelefonoDestinoEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide el Teléfono Destino con la confirmación');
                                            
                                        }
                                    },
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                        esteObjeto.setRawValue(addPuntos(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            Ext.getCmp('idConfirmarMontoEnvioV2PractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Monto',
                                id: 'idConfirmarMontoEnvioV2PractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                        esteObjeto.setRawValue(addPuntos(esteObjeto.getRawValue()));
                                    },
                                    'focus': function (esteObjeto, esteEvento) {
                                        if (Ext.getCmp('idConfirmarTelefonoDestinoEnvioV2PractiGiro').getRawValue() !== Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coincide el Teléfono Destino con la confirmación');
                                            
                                        }
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            if (esteObjeto.getRawValue() !== Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()) {
                                                mostrarError('No coinciden el monto a enviar con la confirmación');
                                                
                                                esteEvento.preventDefault();
                                            } else {
                                                var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue();
                                                var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue();
                                                var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue();
                                                var monto = Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue();
                                                console.log(cedulaOrigen);
                                                console.log(cedulaDestino);
                                                console.log(telefonoDestino);
                                                console.log(monto);

                                                if (cedulaOrigen === "" || cedulaDestino === "" || telefonoDestino === "" || monto === "") {
                                                    mostrarError('Parametros Incorrectos');
                                                } else {
                                                    blockUI();
                                                    var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                                    $.ajax({
                                                        type: "POST",
                                                        url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                                                        data: {
                                                            CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue(),
                                                            CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue(),
                                                            TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue(),
                                                            MONTO: Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()
                                                        },
                                                        success: function (data) {
                                                            unBlockUI();
                                                            var jsonData = Ext.util.JSON.decode(data);
                                                            if (!jsonData.success) {
                                                                mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                            } else {
                                                                var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()));
                                                                var comision = jsonData.comision;
                                                                var montoComision = parseInt(comision) + parseInt(monto);
                                                                Ext.getCmp('idNMontoComisionEnvioV2PractiGiro').setValue(addPuntos(comision.toString()));
                                                                Ext.getCmp('idNMontoCobrarEnvioV2PractiGiro').setValue(addPuntos(montoComision.toString()));
                                                                //Ext.getCmp('idConsultarComisionPG').focus(true, true);
                                                                Ext.getCmp('idBtnAceptarEnvioV2PractiGiro').enable();
                                                            }
                                                        },
                                                        error: function () {
                                                            alert('No se pudo obtener la comision');
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    },
                                    'blur': function (esteObjeto, esteEvento) {
                                        //                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                        if (esteObjeto.getRawValue() !== Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()) {
                                            mostrarError('No coinciden el monto a enviar con la confirmación');
                                            
                                            esteEvento.preventDefault();
                                        } else {
                                            var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue();
                                            var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue();
                                            var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue();
                                            var monto = Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue();
                                            console.log(cedulaOrigen);
                                            console.log(cedulaDestino);
                                            console.log(telefonoDestino);
                                            console.log(monto);

                                            if (cedulaOrigen === "" || cedulaDestino === "" || telefonoDestino === "" || monto === "") {
                                                mostrarError('Parametros Incorrectos');
                                            } else {
                                                blockUI();
                                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                                $.ajax({
                                                    type: "POST",
                                                    url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                                                    data: {
                                                        CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue(),
                                                        CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue(),
                                                        TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue(),
                                                        MONTO: Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()
                                                    },
                                                    success: function (data) {
                                                        unBlockUI();
                                                        var jsonData = Ext.util.JSON.decode(data);
                                                        if (!jsonData.success) {
                                                            mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                        } else {
                                                            var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()));
                                                            var comision = jsonData.comision;
                                                            var montoComision = parseInt(comision) + parseInt(monto);
                                                            Ext.getCmp('idNMontoComisionEnvioV2PractiGiro').setValue(addPuntos(comision.toString()));
                                                            Ext.getCmp('idNMontoCobrarEnvioV2PractiGiro').setValue(addPuntos(montoComision.toString()));
                                                            //Ext.getCmp('idConsultarComisionPG').focus(true, true);
                                                            Ext.getCmp('idBtnAceptarEnvioV2PractiGiro').enable();
                                                        }
                                                    },
                                                    error: function () {
                                                        alert('No se pudo obtener la comision');
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                                //                                }
                            },
                            {
                                xtype: 'button',
                                text: 'Consultar Comisión',
                                id: 'idConsultarComisionPG',
                                iconCls: 'add2',
                                handler: function () {
                                    var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue();
                                    var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue();
                                    var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue();
                                    var monto = Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue();
                                    if (cedulaOrigen === "" || cedulaDestino === "" || telefonoDestino === "" || monto === "") {
                                        mostrarError('Parametros Incorrectos');
                                    } else {
                                        blockUI();
                                        var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                        $.ajax({
                                            type: "POST",
                                            url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                                            data: {
                                                CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').getRawValue(),
                                                CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioV2PractiGiro').getRawValue(),
                                                TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioV2PractiGiro').getRawValue(),
                                                MONTO: Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()
                                            },
                                            success: function (data) {
                                                unBlockUI();
                                                var jsonData = Ext.util.JSON.decode(data);
                                                if (!jsonData.success) {
                                                    mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                } else {
                                                    var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioV2PractiGiro').getRawValue()));
                                                    var comision = jsonData.comision;
                                                    var montoComision = parseInt(comision) + parseInt(monto);
                                                    Ext.getCmp('idNMontoComisionEnvioV2PractiGiro').setValue(addPuntos(comision.toString()));
                                                    Ext.getCmp('idNMontoCobrarEnvioV2PractiGiro').setValue(addPuntos(montoComision.toString()));
                                                    Ext.getCmp('idBtnAceptarEnvioV2PractiGiro').enable();
                                                }
                                            },
                                            error: function () {
                                                unBlockUI();
                                                mostrarError('No se pudo obtener la comision');
                                            }
                                        });
                                    }
                                }
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Comision',
                                id: 'idNMontoComisionEnvioV2PractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                disabled: true
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Monto a Cobrar',
                                id: 'idNMontoCobrarEnvioV2PractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                disabled: true
                            }]
                    },
                    {
                        columnWidth: .30,
                        layout: 'form',
                        xtype: 'fieldset',
                        title: 'Forma de Pago',
                        bodyStyle: ' padding:5px;',
                        defaultType: 'radio',
                        items: [{
                                id: 'idFPEfectivoEnvioV2PractiGiro',
                                checked: true,
                                fieldLabel: 'Forma de pago',
                                boxLabel: 'Efectivo',
                                anchor: '95%',
                                name: 'idtipoPago',
                                inputValue: '1',
                                enableKeyEvents: true,
                                listeners: {
                                    'check': function (esteObjeto, checked) {
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13) {
                                            Ext.Msg.show({
                                                title: 'Confirmación',
                                                msg: '¿Está seguro que desea abonar en efectivo?',
                                                buttons: Ext.Msg.OKCANCEL,
                                                icon: Ext.MessageBox.WARNING,
                                                fn: function (btn, text) {
                                                    if (btn === "ok") {
                                                        Ext.getCmp('idBtnAceptarXC').focus(true, true);
                                                    } else {
                                                        Ext.getCmp('idPagoXCuentaFormPanelEfectivo').focus(true, true);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        ]}

                ]
            }],
        buttonAlign: 'left',
        buttons: [{
                text: 'Aceptar',
                id: 'idBtnAceptarEnvioV2PractiGiro',
                width: '130',
                disabled: true,
                handler: function () {//llamada al autorizador                
                    blockUI();
                    var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                    $.ajax({
                        type: "POST",
                        url: 'COBRO_SERVICIOS?op=REALIZAR_ENVIO_PRACTIGIRO_2&ID_RANDOM=' + randomNumber,
                        data: {
                            FORMA_PAGO: 1
                        },
                        success: function (data) {
                            unBlockUI();
                            var jsonData = Ext.util.JSON.decode(data);
                            console.log(jsonData);
                            if (!jsonData.success) {
                                mostrarError(jsonData.mensaje);
                            } else {
                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                Ext.Msg.wait('Procesando... Por Favor espere...');
                                Ext.Msg.hide();
                                Ext.getCmp('panelTicketCB').load({
                                    url: 'COBRO_SERVICIOS?op=TICKET_RETIRO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                    discardUrl: false,
                                    nocache: true,
                                    text: "Procesando... Por Favor espere...",
                                    scripts: true
                                });
                                Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                            }
                        },
                        error: function () {
                            unBlockUI();
                            alert('No se pudo obtener el monto de Retiro');
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderEnvioV2sPractiGiro').getForm().reset();
                    Ext.getCmp('idCedulaOrigenEnvioV2PractiGiro').focus(true, true);
                }
            }]
    };
    return items;

}
