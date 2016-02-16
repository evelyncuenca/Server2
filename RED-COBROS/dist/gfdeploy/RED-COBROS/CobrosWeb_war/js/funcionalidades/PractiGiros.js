var minDateY = new Date();
minDateY.setYear(minDateY.getYear() - 18);
function cardPractiGiros() {
    if (GESTION_ABIERTA) {
        opPractiGiros().center();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function opPractiGiros() {
    var win = new Ext.Window({
        title: 'Elija una opcion de PractiGIROS',
        autoWidth: true,
        height: 'auto',
        id: 'idWinopPractiGiros',
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
                html: '<a href=\'#\'  ><img onclick=\'cardPractiEnvio();\' src=\'images/btnPractiEnvio.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                  <a href=\'#\'  ><img onclick=\'cardPractiRetiro();\' src=\'images/btnPractiRetiro.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>'
            }]

    });
    return win.show();
}


function panelPractiEnvio() {
    var panelPractiEnvio = {
        id: 'panelPractiEnvio',
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
                                Ext.getCmp('idHeaderEnviosPractiGiro').getForm().reset();
                                cardInicial();
                            } else {

                            }
                        }
                    });
                }
            }],
        items: [envioPractiGiro(), activarClienteOrigen()]
    };
    return panelPractiEnvio;
}

function panelPractiRetiro() {
    var panelPractiRetiro = {
        id: 'panelPractiRetiro',
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
                                Ext.getCmp('idHeaderRetirosPractiGiro').getForm().reset();
                                cardInicial();
                            } else {

                            }
                        }
                    });
                }
            }],
        items: [retiroPractiGiro(), activarClienteDestino()]
    };
    return panelPractiRetiro;
}



function cardPractiEnvio() {
    Ext.getCmp('idWinopPractiGiros').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelPractiEnvio');
    Ext.getCmp('idHeaderEnviosPractiGiro').show();
    Ext.getCmp('idHeaderEnviosPractiGiro').getForm().reset();
    Ext.getCmp('idHeaderActivarClienteEnviosPractiGiro').hide();
    Ext.getCmp('idHeaderActivarClienteEnviosPractiGiro').getForm().reset();
}

function cardPractiRetiro() {
    Ext.getCmp('idWinopPractiGiros').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelPractiRetiro');
    Ext.getCmp('idHeaderRetirosPractiGiro').show();
//    Ext.getCmp('idHeaderRetirosPractiGiro').hide();
    Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').hide();
//    Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').show();
    Ext.getCmp('idHeaderRetirosPractiGiro').getForm().reset();
    Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').getForm().reset();
}

function retiroPractiGiro() {

    var items = {
        xtype: 'form',
        id: 'idHeaderRetirosPractiGiro',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        title: 'Datos de la Remesa',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items: [{
                xtype: 'textfield',
                fieldLabel: 'Cédula',
                id: 'idCedulaRetiroPractiGiro',
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
                            Ext.getCmp('idConfirmarCedulaRetiroPractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Confirmar Cédula',
                id: 'idConfirmarCedulaRetiroPractiGiro',
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
                            if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaRetiroPractiGiro').getRawValue()) {
                                mostrarError('No coincide la Cédula  con la confirmación');
                                esteObjeto.focus(true, true);
                            } else {
                                Ext.getCmp('idTelefonoRetiroPractiGiro').focus(true, true);
                            }
                        }
                    },
                    'blur': function (esteObjeto, esteEvento) {
//                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                        if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaRetiroPractiGiro').getRawValue()) {
                            mostrarError('No coincide la Cédula  con la confirmación');
                            esteObjeto.focus(true, true);
                        } else {
                            Ext.getCmp('idTelefonoRetiroPractiGiro').focus(true, true);
                        }
//                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Teléfono',
                id: 'idTelefonoRetiroPractiGiro',
                name: 'TELEFONO',
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
                            Ext.getCmp('idConfirmarTelefonoRetiroPractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Confirmar Teléfono',
                id: 'idConfirmarTelefonoRetiroPractiGiro',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100',
                maxLength: 10,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            if (esteObjeto.getRawValue() !== Ext.getCmp('idTelefonoRetiroPractiGiro').getRawValue()) {
                                mostrarError('No coincide el Teléfono con la confirmación');
                                esteObjeto.focus(true, true);
                            } else {
                                Ext.getCmp('idPinRetiroPractiGiro').focus(true, true);
                            }
                        }
                    },
                    'blur': function (esteObjeto, esteEvento) {
//                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                        if (esteObjeto.getRawValue() !== Ext.getCmp('idTelefonoRetiroPractiGiro').getRawValue()) {
                            mostrarError('No coincide el Teléfono con la confirmación');
                            esteObjeto.focus(true, true);
                        } else {
                            Ext.getCmp('idPinRetiroPractiGiro').focus(true, true);
                        }
//                        }
                    }
                }
            }, {
                xtype: 'textfield',
                inputType: 'password',
                fieldLabel: 'Pin de Retiro',
                id: 'idPinRetiroPractiGiro',
                name: 'PIN',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100',
                maxLength: 4,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBtnConsultarRetiroPractiGiro').focus(true, true);

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
                id: 'idBtnConsultarRetiroPractiGiro',
                handler: function () {



                    var randomNumber = Math.floor((Math.random() * 10000000) + 1);

                    $.ajax({
                        type: "POST",
                        url: 'COBRO_SERVICIOS?op=CONSULTAR_RETIRO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                        data: {
                            CEDULA: Ext.getCmp('idCedulaRetiroPractiGiro').getRawValue(),
                            TELEFONO: Ext.getCmp('idTelefonoRetiroPractiGiro').getRawValue(),
                            PIN: Ext.getCmp('idPinRetiroPractiGiro').getRawValue()
                        },
                        success: function (data) {
                            unBlockUI();
                            var jsonData = Ext.util.JSON.decode(data);
                            console.log(jsonData);
                            if (!jsonData.success) {
                                mostrarError("Pin, Telefono, o Cedula Incorrectos")
                            } else {
                                var monto = jsonData.monto;
                                Ext.getCmp('idNMontoRetiroPractiGiro').setValue(addPuntos(monto.toString()));
                                Ext.getCmp('idBtnConfirmarRetiroPractiGiro').focus(true, true);
                                Ext.getCmp('idBtnConfirmarRetiroPractiGiro').enable();
                            }
                        },
                        error: function () {
                            alert('No se pudo obtener el monto de Retiro');
                        }
                    });
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Monto a retirar',
                id: 'idNMontoRetiroPractiGiro',
                allowBlank: false,
                labelStyle: 'font-weight:bold; width: 150px;',
                width: '100', disabled: true
            }
        ],
        buttonAlign: 'left',
        buttons: [{
                text: 'Confirmar',
                id: 'idBtnConfirmarRetiroPractiGiro',
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
                                Ext.getCmp('idBtnConfirmarRetiroPractiGiro').disable();

                                $.ajax({
                                    type: "POST",
                                    url: 'COBRO_SERVICIOS?op=REALIZAR_RETIRO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                    data: {
                                        CEDULA: Ext.getCmp('idCedulaRetiroPractiGiro').getRawValue(),
                                        TELEFONO: Ext.getCmp('idTelefonoRetiroPractiGiro').getRawValue(),
                                        PIN: Ext.getCmp('idPinRetiroPractiGiro').getRawValue(),
                                        MONTO_RETIRO: Ext.getCmp('idNMontoRetiroPractiGiro').getRawValue()
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
                                                            Ext.getCmp('idHeaderRetirosPractiGiro').hide();
                                                            Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').show();
                                                        } else {
                                                            Ext.getCmp('idHeaderRetirosPractiGiro').hide();
                                                            Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').show();
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
                    Ext.getCmp('idHeaderRetirosPractiGiro').getForm().reset();
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreRetiroPractiGiro').disable();
                    Ext.getCmp('idApellidoRetiroPractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoRetiroPractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocRetiroPractiGiro').disable();
                    Ext.getCmp('idDireccionRetiroPractiGiro').disable();
                    Ext.getCmp('idTelefonoRetiroPractiGiro').disable();
                    Ext.getCmp('idProfesionRetiroPractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();
                }
            }]
    };
    return items;
}

function activarClienteOrigen() {

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
        id: 'idSexoClienteEnvioPractiGiro',
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
        id: 'idEstadoCivilClienteEnvioPractiGiro',
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
        id: 'idHeaderActivarClienteEnviosPractiGiro',
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
                id: 'idPrimerNombreClienteEnvioPractiGiro',
                name: 'PRIMER_NOMBRE',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoNombreClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Nombre',
                id: 'idSegundoNombreClienteEnvioPractiGiro',
                name: 'SEGUNDO_NOMBRE',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPrimerApellidoClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Primer Apellido(*)',
                id: 'idPrimerApellidoClienteEnvioPractiGiro',
                name: 'PRIMER_APELLIDO',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoApellidoClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Apellido',
                id: 'idSegundoApellidoClienteEnvioPractiGiro',
                name: 'SEGUNDO_APELLIDO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idNroDocumentoEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Nro. Documento(*)',
                id: 'idNroDocumentoEnvioPractiGiro',
                name: 'NRO_DOC',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idFechaNacimientoEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'datefield',
                fieldLabel: 'Fecha Nacimiento(*)',
                id: 'idFechaNacimientoEnvioPractiGiro',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                maxValue: minDateY,
                name: 'FECHA_NAC',
                format: 'd/m/Y',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idRucClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Ruc',
                id: 'idRucClienteEnvioPractiGiro',
                name: 'RUC',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDireccionClienteEnvioPractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Dirección(*)',
                id: 'idDireccionClienteEnvioPractiGiro',
                name: 'DIRECCION',
                allowBlank: false,
                width: '150',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSexoClienteEnvioPractiGiro').focus(true, true);
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
                id: 'idTelefonoFijoClienteEnvioPractiGiro',
                name: 'TELEFONO_FIJO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoMovilClienteEnvioPractiGiro').enable();
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Telefono Movil(*)',
                id: 'idTelefonoMovilClienteEnvioPractiGiro',
                name: 'TELEFONO_MOVIL',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBarrioClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Barrio',
                id: 'idBarrioClienteEnvioPractiGiro',
                name: 'BARRIO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCiudadClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Ciudad',
                id: 'idCiudadClienteEnvioPractiGiro',
                name: 'CIUDAD',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCodigoPostalClienteEnvioPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Profesion(*)',
                id: 'idCodigoPostalClienteEnvioPractiGiro',
                name: 'CODIGO_POSTAL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDptoClienteEnvioPractiGiro').focus(true, true);
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
                id: 'idDptoClienteEnvioPractiGiro',
                name: 'DEPARTAMENTO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPisoClienteEnvioPractiGiro').focus(true, true);
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
                id: 'idPisoClienteEnvioPractiGiro',
                name: 'PISO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idMailClienteEnvioPractiGiro').focus(true, true);
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
                id: 'idMailClienteEnvioPractiGiro',
                name: 'EMAIL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCedulaFrontalClienteEnvioPractiGiro').focus(true, true);
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
            }
            ],
        buttonAlign: 'left',
        buttons: [{
                text: 'Confirmar',
                id: 'idBtnConfirmarActivarClienteEnvioPractiGiro',
                width: '130',
                formBind: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    Ext.getCmp('idHeaderActivarClienteEnviosPractiGiro').getForm().submit({
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
                    Ext.getCmp('idHeaderEnviosPractiGiro').getForm().reset();
                    Ext.getCmp('idDocumentoEnvioPractiGiro').enable();
                    tipoDoc.focus(true, true);
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreEnvioPractiGiro').disable();
                    Ext.getCmp('idApellidoEnvioPractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoEnvioPractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocEnvioPractiGiro').disable();
                    Ext.getCmp('idDireccionEnvioPractiGiro').disable();
                    Ext.getCmp('idTelefonoEnvioPractiGiro').disable();
                    Ext.getCmp('idProfesionEnvioPractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();


                }
            }]
    };
    return items;

}
function activarClienteDestino() {
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
        id: 'idSexoClienteRetiroPractiGiro',
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
        id: 'idEstadoCivilClienteRetiroPractiGiro',
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
        id: 'idHeaderActivarClienteRetirosPractiGiro',
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
                id: 'idPrimerNombreClienteRetiroPractiGiro',
                name: 'PRIMER_NOMBRE',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoNombreClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Nombre',
                id: 'idSegundoNombreClienteRetiroPractiGiro',
                name: 'SEGUNDO_NOMBRE',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPrimerApellidoClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Primer Apellido(*)',
                id: 'idPrimerApellidoClienteRetiroPractiGiro',
                name: 'PRIMER_APELLIDO',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSegundoApellidoClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Segundo Apellido',
                id: 'idSegundoApellidoClienteRetiroPractiGiro',
                name: 'SEGUNDO_APELLIDO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idNroDocumentoRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Nro. Documento(*)',
                id: 'idNroDocumentoRetiroPractiGiro',
                name: 'NRO_DOC',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idFechaNacimientoRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'datefield',
                fieldLabel: 'Fecha Nacimiento(*)',
                id: 'idFechaNacimientoRetiroPractiGiro',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                maxValue: minDateY,
                name: 'FECHA_NAC',
                format: 'd/m/Y',
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idRucClienteRetiroPractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Ruc',
                id: 'idRucClienteRetiroPractiGiro',
                name: 'RUC',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDireccionClienteRetiroPractiGiro').focus(true, true);
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Dirección(*)',
                id: 'idDireccionClienteRetiroPractiGiro',
                name: 'DIRECCION',
                allowBlank: false,
                width: '150',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idSexoClienteRetiroPractiGiro').focus(true, true);
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
                id: 'idTelefonoFijoClienteRetiroPractiGiro',
                name: 'TELEFONO_FIJO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idTelefonoMovilClienteRetiroPractiGiro').enable();
                        }
                    }
                }
            }, {
                xtype: 'textfield',
                fieldLabel: 'Telefono Movil(*)',
                id: 'idTelefonoMovilClienteRetiroPractiGiro',
                name: 'TELEFONO_MOVIL',
                allowBlank: false,
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idBarrioClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Barrio',
                id: 'idBarrioClienteRetiroPractiGiro',
                name: 'BARRIO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCiudadClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Ciudad',
                id: 'idCiudadClienteRetiroPractiGiro',
                name: 'CIUDAD',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCodigoPostalClienteRetiroPractiGiro').focus(true, true);
                        }
                    }, 'keyup': function (esteObjeto, esteEvento) {
                        esteObjeto.setRawValue(esteObjeto.getRawValue().toString().trim().toUpperCase());
                    }
                }
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Profesion(*)',
                id: 'idCodigoPostalClienteRetiroPractiGiro',
                name: 'CODIGO_POSTAL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idDptoClienteRetiroPractiGiro').focus(true, true);
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
                id: 'idDptoClienteRetiroPractiGiro',
                name: 'DEPARTAMENTO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idPisoClienteRetiroPractiGiro').focus(true, true);
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
                id: 'idPisoClienteRetiroPractiGiro',
                name: 'PISO',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idMailClienteRetiroPractiGiro').focus(true, true);
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
            }, {
                xtype: 'textfield',
                fieldLabel: 'Email',
                id: 'idMailClienteRetiroPractiGiro',
                name: 'EMAIL',
                width: '100',
                enableKeyEvents: true,
                listeners: {
                    'blur': function (esteObjeto, esteEvento) {

                    },
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                            Ext.getCmp('idCedulaFrontalClienteRetiroPractiGiro').focus(true, true);
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
                id: 'idBtnConfirmarActivarClienteRetiroPractiGiro',
                width: '130',
                formBind: true,
                handler: function () {
                    //llamada al autorizador 
                    blockUI();
                    Ext.getCmp('idHeaderActivarClienteRetirosPractiGiro').getForm().submit({
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
                    Ext.getCmp('idHeaderRetirosPractiGiro').getForm().reset();
                    Ext.getCmp('idDocumentoRetiroPractiGiro').enable();
                    tipoDoc.focus(true, true);
                    Ext.getCmp('idAgregarClientePractiGiro').disable();
                    Ext.getCmp('idNombreRetiroPractiGiro').disable();
                    Ext.getCmp('idApellidoRetiroPractiGiro').disable();
                    Ext.getCmp('idFechaNacimientoRetiroPractiGiro').disable();
                    Ext.getCmp('idFechaEmisionDocRetiroPractiGiro').disable();
                    Ext.getCmp('idDireccionRetiroPractiGiro').disable();
                    Ext.getCmp('idTelefonoRetiroPractiGiro').disable();
                    Ext.getCmp('idProfesionRetiroPractiGiro').disable();
                    Ext.getCmp('idCodigoRemesaPractiGiro').disable();


                }
            }]
    };
    return items;

}

function envioPractiGiro() {
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'Bancos', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'Bancos');
    comboENTIDAD_FINANCIERA.id = "idComboEntidadFinancieraEnvioPractiGiro";
    comboENTIDAD_FINANCIERA.addListener('select', function (esteCombo, record, index) {
        Ext.getCmp('idNumeroChequeEnvioPractiGiro').focus(true, true);
    }, null, null);
    comboENTIDAD_FINANCIERA.disable();

    var items = {
        xtype: 'form',
        title: 'Envios',
        id: 'idHeaderEnviosPractiGiro',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        bodyStyle: 'padding:3px 3px 8px 8px;',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idCedulaOrigenEnvioPractiGiro').focus(true, true);
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
                                id: 'idCedulaOrigenEnvioPractiGiro',
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
                                            Ext.getCmp('idConfirmarCedulaOrigenEnvioPractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Cédula',
                                id: 'idConfirmarCedulaOrigenEnvioPractiGiro',
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
                                            if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue()) {
                                                mostrarError('No coincide la Cédula Origen con la confirmación');
                                                esteObjeto.focus(true, true);
                                            } else {
                                                Ext.getCmp('idCedulaDestinoEnvioPractiGiro').focus(true, true);
                                            }
                                        }
                                    },
                                    'blur': function (esteObjeto, esteEvento) {
//                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                        if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Origen con la confirmación');
                                            esteObjeto.focus(true, true);
                                        } else {
                                            if (Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue() !== '') {
                                                var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                                blockUI();
                                                $.ajax({
                                                    type: "POST",
                                                    url: 'ActivarCliente?proc=3&ID_RANDOM=' + randomNumber,
                                                    data: {
                                                        CEDULA: Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue()
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
                                                                        Ext.getCmp('idHeaderEnviosPractiGiro').hide();
                                                                        Ext.getCmp('idHeaderActivarClienteEnviosPractiGiro').show();
                                                                    } else {
                                                                        Ext.getCmp('idHeaderEnviosPractiGiro').hide();
                                                                        Ext.getCmp('idHeaderActivarClienteEnviosPractiGiro').show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            Ext.getCmp('idCedulaDestinoEnvioPractiGiro').focus(true, true);
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
//                                        }
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
                                id: 'idCedulaDestinoEnvioPractiGiro',
                                name: 'CEDULA_DESTINO',
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
                                            Ext.getCmp('idConfirmarCedulaDestinoEnvioPractiGiro').focus(true, true);
                                        }
                                    },
                                    'blur': function (a, b) {
                                        if (Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue() !== Ext.getCmp('idConfirmarCedulaOrigenEnvioPractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Origen con la confirmación');
                                            Ext.getCmp('idConfirmarCedulaOrigenEnvioPractiGiro').focus(true, true);
                                        } else {
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Cédula',
                                id: 'idConfirmarCedulaDestinoEnvioPractiGiro',
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
                                            if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue()) {
                                                mostrarError('No coincide la Cédula Destino con la confirmación');
                                                esteObjeto.focus(true, true);
                                            } else {
                                                Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').focus(true, true);
                                            }
                                        }
                                    },
                                    'blur': function (esteObjeto, esteEvento) {
                                        if (esteObjeto.getRawValue() !== Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue()) {
                                            mostrarError('No coincide la Cédula Destino con la confirmación');
                                            esteObjeto.focus(true, true);
                                        } else {
                                            Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Teléfono',
                                id: 'idTelefonoDestinoEnvioPractiGiro',
                                name: 'TELEFONO_DESTINO',
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
                                            Ext.getCmp('idConfirmarTelefonoDestinoEnvioPractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Teléfono',
                                id: 'idConfirmarTelefonoDestinoEnvioPractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                maxLength: 10,
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            if (esteObjeto.getRawValue() !== Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue()) {
                                                mostrarError('No coincide el Teléfono Destino con la confirmación');
                                                esteObjeto.focus(true, true);
                                            } else {
                                                Ext.getCmp('idMontoEnvioPractiGiro').focus(true, true);
                                            }
                                        }
                                    },
                                    'blur': function (esteObjeto, esteEvento) {
//                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                        if (esteObjeto.getRawValue() !== Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue()) {
                                            mostrarError('No coincide el Teléfono Destino con la confirmación');
                                            esteObjeto.focus(true, true);
                                        } else {
                                            Ext.getCmp('idMontoEnvioPractiGiro').focus(true, true);
                                        }
//                                        }
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
                                id: 'idMontoEnvioPractiGiro',
                                name: 'MONTO_ENVIO',
                                labelStyle: 'font-weight:bold; width: 150px;',
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
                                            Ext.getCmp('idConfirmarMontoEnvioPractiGiro').focus(true, true);
                                        }
                                    }
                                }
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Confirmar Monto',
                                id: 'idConfirmarMontoEnvioPractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                enableKeyEvents: true,
                                listeners: {
                                    'keyup': function (esteObjeto, esteEvento) {
                                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                        esteObjeto.setRawValue(addPuntos(esteObjeto.getRawValue()));
                                    },
                                    'specialkey': function (esteObjeto, esteEvento) {
                                        if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                            if (esteObjeto.getRawValue() !== Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()) {
                                                mostrarError('No coinciden el monto a enviar con la confirmación');
                                                esteObjeto.focus(true, true);
                                                esteEvento.preventDefault();
                                            } else {
                                                var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue();
                                                var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue();
                                                var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue();
                                                var monto = Ext.getCmp('idMontoEnvioPractiGiro').getRawValue();
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
                                                        url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                                        data: {
                                                            CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue(),
                                                            CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue(),
                                                            TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue(),
                                                            MONTO: Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()
                                                        },
                                                        success: function (data) {
                                                            unBlockUI();
                                                            var jsonData = Ext.util.JSON.decode(data);
                                                            if (!jsonData.success) {
                                                                mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                            } else {
                                                                var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()));
                                                                var comision = jsonData.comision;
                                                                var montoComision = parseInt(comision) + parseInt(monto);
                                                                Ext.getCmp('idNMontoComisionEnvioPractiGiro').setValue(addPuntos(comision.toString()));
                                                                Ext.getCmp('idNMontoCobrarEnvioPractiGiro').setValue(addPuntos(montoComision.toString()));
                                                                Ext.getCmp('idConsultarComisionPG').focus(true, true);
                                                                Ext.getCmp('idBtnAceptarEnvioPractiGiro').enable();
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
                                        if (esteObjeto.getRawValue() !== Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()) {
                                            mostrarError('No coinciden el monto a enviar con la confirmación');
                                            esteObjeto.focus(true, true);
                                            esteEvento.preventDefault();
                                        } else {
                                            var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue();
                                            var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue();
                                            var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue();
                                            var monto = Ext.getCmp('idMontoEnvioPractiGiro').getRawValue();
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
                                                    url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                                    data: {
                                                        CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue(),
                                                        CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue(),
                                                        TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue(),
                                                        MONTO: Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()
                                                    },
                                                    success: function (data) {
                                                        unBlockUI();
                                                        var jsonData = Ext.util.JSON.decode(data);
                                                        if (!jsonData.success) {
                                                            mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                        } else {
                                                            var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()));
                                                            var comision = jsonData.comision;
                                                            var montoComision = parseInt(comision) + parseInt(monto);
                                                            Ext.getCmp('idNMontoComisionEnvioPractiGiro').setValue(addPuntos(comision.toString()));
                                                            Ext.getCmp('idNMontoCobrarEnvioPractiGiro').setValue(addPuntos(montoComision.toString()));
                                                            Ext.getCmp('idConsultarComisionPG').focus(true, true);
                                                            Ext.getCmp('idBtnAceptarEnvioPractiGiro').enable();
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
                                    var cedulaOrigen = Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue();
                                    var cedulaDestino = Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue();
                                    var telefonoDestino = Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue();
                                    var monto = Ext.getCmp('idMontoEnvioPractiGiro').getRawValue();
                                    if (cedulaOrigen === "" || cedulaDestino === "" || telefonoDestino === "" || monto === "") {
                                        mostrarError('Parametros Incorrectos');
                                    } else {
                                        blockUI();
                                        var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                                        $.ajax({
                                            type: "POST",
                                            url: 'COBRO_SERVICIOS?op=CONSULTAR_ENVIO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                                            data: {
                                                CEDULA_ORIGEN: Ext.getCmp('idCedulaOrigenEnvioPractiGiro').getRawValue(),
                                                CEDULA_DESTINO: Ext.getCmp('idCedulaDestinoEnvioPractiGiro').getRawValue(),
                                                TELEFONO_DESTINO: Ext.getCmp('idTelefonoDestinoEnvioPractiGiro').getRawValue(),
                                                MONTO: Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()
                                            },
                                            success: function (data) {
                                                unBlockUI();
                                                var jsonData = Ext.util.JSON.decode(data);
                                                if (!jsonData.success) {
                                                    mostrarError("No se pudo procesar la consulta, favor intentelo nuevamente")
                                                } else {
                                                    var monto = parseInt(replaceAllNoNumbers(Ext.getCmp('idMontoEnvioPractiGiro').getRawValue()));
                                                    var comision = jsonData.comision;
                                                    var montoComision = parseInt(comision) + parseInt(monto);
                                                    Ext.getCmp('idNMontoComisionEnvioPractiGiro').setValue(addPuntos(comision.toString()));
                                                    Ext.getCmp('idNMontoCobrarEnvioPractiGiro').setValue(addPuntos(montoComision.toString()));
                                                    Ext.getCmp('idBtnAceptarEnvioPractiGiro').enable();
                                                }
                                            },
                                            error: function () {
                                                mostrarError('No se pudo obtener la comision');
                                            }
                                        });
                                    }
                                }
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Comision',
                                id: 'idNMontoComisionEnvioPractiGiro',
                                allowBlank: false,
                                labelStyle: 'font-weight:bold; width: 150px;',
                                width: '100',
                                disabled: true
                            }, {
                                xtype: 'textfield',
                                fieldLabel: 'Monto a Cobrar',
                                id: 'idNMontoCobrarEnvioPractiGiro',
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
                                id: 'idFPEfectivoEnvioPractiGiro',
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
                id: 'idBtnAceptarEnvioPractiGiro',
                width: '130',
                disabled: true,
                handler: function () {//llamada al autorizador                
//                    blockUI();
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.Msg.hide();
                    var fp = 1;
                    var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                    Ext.getCmp('panelTicketCB').load({
                        url: 'COBRO_SERVICIOS?op=REALIZAR_ENVIO_PRACTIGIRO&ID_RANDOM=' + randomNumber,
                        params: {
                            FORMA_PAGO: fp
                        }
                    });
                    Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderEnviosPractiGiro').getForm().reset();
                    Ext.getCmp('idCedulaOrigenEnvioPractiGiro').focus(true, true);
                }
            }]
    };
    return items;

}
