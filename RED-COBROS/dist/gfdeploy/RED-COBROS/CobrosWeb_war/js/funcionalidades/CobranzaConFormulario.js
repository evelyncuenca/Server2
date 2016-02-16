function panelCobranzaPagoConFormulario() {
    var panel = {
        id: 'panelCobranzasPagoConFormulario',
        title: 'Pago De Formulario',
        xtype: 'panel',
        //layout : 'fit',  
        border: false,
        //height:500,
        autoScroll: true,
        tbar: [{
                text: 'Salir (Alt+q)',
                iconCls: 'logout',
                handler: function () {
                    Ext.getCmp('idPagoDeFormularioSet').getForm().reset();
                    cardInicial();
                }
            }],
        items: [{
                xtype: 'panel',
                items: [cobranzasFormularioFormaPago()]
            }]
    }

    return panel;

}


function cobranzasFormularioFormaPago() {
    var comboREFERENCIAS = getCombo('REFERENCIAS', 'referencias', 'idFormContrib', 'idFormContrib', 'idFormContrib', 'Referencias', 'idFormContrib', 'idFormContrib', 'idFormContrib', 'Referencias...');
    var fechaChequeForm = new Ext.form.DateField({
        id: 'idFechaChequeBP',
        fieldLabel: 'Fecha Cheque',
        name: 'FECHA_CHEQUE',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        disabled: true,
        format: 'dmY',
        enableKeyEvents: true,
        listeners: {
            'specialkey': function (esteObjeto, esteEvento) {
                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                    Ext.getCmp('btnRealizarOperacionPagoFormulario').focus(true, true);
                }
            }
        }
    });
    comboREFERENCIAS.addListener('select', function (esteCombo, record, index) {
        fp.getForm().load({
            url: 'cobranzas?op=DETALLE_COBRO_CON_FORMULARIO',
            method: 'POST',
            params: {
                idFormContrib: esteCombo.getRawValue()
            },
            waitMsg: 'Cargando...',
            success: function (action) {
                //Ext.getCmp('idMontoPagoConFormulario').focus(true);
            },
            failure: function (action) {

            }

        });

    }, null, null);
    var primeraVez = true;//Permite que no se cargue en el onLoad de su store.
    comboREFERENCIAS.addListener('focus', function (esteCombo) {
        if (!primeraVez) {
            esteCombo.store.load({
                params: {
                    start: 0,
                    limit: 20
                },
                waitMsg: 'Cargando...'
            });
        }
        primeraVez = false;
    }, null, null);
    comboREFERENCIAS.addListener('specialkey', function (esteCombo, esteEvento) {
        if (esteEvento.getKey() == 40) {
            esteCombo.store.load({
                params: {
                    start: 0,
                    limit: 20
                },
                waitMsg: 'Cargando...'
            });
        }
    }, null, null);
    comboREFERENCIAS.allowBlank = false;
    comboREFERENCIAS.enableKeyEvents = true;
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'Bancos', 'ENTIDAD_FINANCIERA', 'DESCRIPCION_ENTIDAD_FINANCIERA', 'ENTIDAD_FINANCIERA', 'Bancos');
    comboENTIDAD_FINANCIERA.addListener('select', function (esteCombo, record, index) {
        Ext.getCmp('idNumeroChequePagoFormulario').focus(true, true);

    }, null, null);
    comboENTIDAD_FINANCIERA.disable();

    comboREFERENCIAS.id = 'idComboReferenciasPagoFormulario';
    var individual = [{
            items: {
                xtype: 'fieldset',
                title: 'Búsqueda',
                autoHeight: true,
                width: 350,
                defaultType: 'textfield',
                items: [comboREFERENCIAS, {
                        name: 'ruc',
                        fieldLabel: 'RUC',
                        disabled: true,
                        anchor: '95%'
                    }, {
                        name: 'dv',
                        fieldLabel: 'DV',
                        anchor: '95%',
                        disabled: true
                    }, {
                        xtype: 'numberfield',
                        name: 'numero_formulario',
                        fieldLabel: 'Formulario',
                        anchor: '95%',
                        disabled: true
                    }, {
                        name: 'periodoFiscal',
                        fieldLabel: 'Periodo Fiscal',
                        anchor: '95%',
                        disabled: true
                    }, /*{
                     name: 'montoDeclarado',
                     fieldLabel: 'Monto Declarado',
                     anchor:'95%',
                     disabled:true
                     },*/{
                        id: 'idMontoPagoConFormulario',
                        xtype: 'textfield',
                        anchor: '95%',
                        name: 'montoFormulario',
                        fieldLabel: 'Importe de Formulario',
                        disabled: true
                                /*enableKeyEvents:true,
                                 
                                 listeners : {
                                 'keyup' : function(esteObjeto, esteEvento)   {
                                 esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                 esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                                 },
                                 'specialkey' :function(esteObjeto, esteEvento){
                                 if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                 Ext.getCmp('idMontoPagoConFormulario').hide();
                                 Ext.getCmp('idConfirmarMonto').focus(true,true);
                                 }
                                 }
                                 }*/

                    }/*,{
                     id: 'idConfirmarMonto',
                     name: 'monto',
                     fieldLabel: 'Confirmar Monto',
                     xtype:'textfield',
                     anchor:'95%',
                     allowBlank:false,
                     enableKeyEvents:true,
                     listeners : {
                     'keyup' : function(esteObjeto, esteEvento)   {
                     esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                     esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                     },
                     'specialkey' :function(esteObjeto, esteEvento){
                     if(Ext.getCmp('idMontoPagoConFormulario').getValue()!=Ext.getCmp('idConfirmarMonto').getValue()){
                     Ext.Msg.show({
                     title : FAIL_TITULO_GENERAL,
                     msg : "Los montos no coinciden.",
                     buttons : Ext.Msg.OK,
                     icon : Ext.MessageBox.ERROR,
                     fn: function(btn){
                     if(btn == 'ok'){
                     Ext.getCmp('idMontoPagoConFormulario').show();
                     Ext.getCmp('idConfirmarMonto').reset();
                     Ext.getCmp('idMontoPagoConFormulario').focus(true,true);
                     }
                     }
                     });
                     }else{
                     Ext.getCmp('idMontoPagoConFormulario').show();
                     Ext.getCmp('idFormaPagoPagoFormulario').focus(true,true);
                     }
                     }
                     }
                     
                     }*/]
            }
        }, {
            bodyStyle: 'padding-left:5px;',
            items: {
                xtype: 'fieldset',
                title: 'Forma de Pago',
                autoHeight: true,
                width: 350,
                defaultType: 'radio', // each item will be a radio button
                items: [{
                        id: 'idFormaPagoPagoFormulario',
                        checked: true,
                        fieldLabel: 'Forma de pago',
                        boxLabel: 'Efectivo',
                        anchor: '95%',
                        name: 'idTipoPago',
                        inputValue: '1',
                        listeners: {
                            'check': function (esteObjeto, checked) {
                                if (checked) {
                                    comboENTIDAD_FINANCIERA.disable();
                                    Ext.getCmp('idNumeroChequePagoFormulario').disable();
                                    fechaChequeForm.disable();

                                }
                            },
                            'specialkey': function (esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    if (esteEvento.getKey() == 13) {
                                        Ext.Msg.show({
                                            title: 'Confirmación',
                                            msg: '¿Está seguro que desea abonar en efectivo?',
                                            buttons: Ext.Msg.OKCANCEL,
                                            icon: Ext.MessageBox.WARNING,
                                            fn: function (btn, text) {
                                                if (btn == "ok") {
                                                    Ext.getCmp('btnRealizarOperacionPagoFormulario').focus(true, true);
                                                } else {
                                                    Ext.getCmp('idFormaPagoPagoFormulario').focus(true, true);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }

                    }/*, {
                     fieldLabel: '',
                     labelSeparator: '',
                     boxLabel: 'Cheque',
                     name: 'idTipoPago',
                     anchor:'95%',
                     inputValue: '2',
                     listeners : {
                     'check' : function(esteObjeto, checked)   {
                     if(checked){
                     comboENTIDAD_FINANCIERA.enable();
                     Ext.getCmp('idNumeroChequePagoFormulario').enable();
                     fechaChequeForm.enable();
                     
                     }
                     },
                     'specialkey' :function(esteObjeto, esteEvento)   {
                     if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                     comboENTIDAD_FINANCIERA.focus(true,true);
                     }
                     }
                     }
                     },
                     comboENTIDAD_FINANCIERA,
                     {
                     id:'idNumeroChequePagoFormulario',
                     xtype: 'numberfield',
                     name:'nro_cheque',
                     anchor:'95%',
                     fieldLabel:'Número de Cheque',
                     allowBlank:false,
                     disabled:true,
                     listeners : {
                     'specialkey' :function(esteObjeto, esteEvento)   {
                     if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                     Ext.getCmp('idFechaChequeBP').focus(true,true);
                     }
                     }
                     }
                     
                     },fechaChequeForm
                     */]
            }
        }];
    var aceptarTrabajando = false;
    var fp = new Ext.FormPanel({
        id: 'idPagoDeFormularioSet',
        frame: true,
        labelWidth: 130,
        buttonAlign: 'left',
        //width: 300,
        monitorValid: true,
        items: [
            {
                layout: 'column',
                border: true,
                defaults: {
                    columnWidth: '.5',
                    border: false
                },
                items: individual
            }
        ],
        buttons: [{
                id: 'btnRealizarOperacionPagoFormulario',
                text: 'Aceptar',
                formBind: true,
                handler: function () {
                    // if(fp.getForm().isValid()){
                    if (fp.getForm().isValid()) {
                        Ext.Msg.wait('Procesando... Por Favor espere...');
                        fp.getForm().submit({
                            method: 'POST',
//                            waitTitle: 'Conectando',
//                            waitMsg: 'Enviando Información...',
                            url: 'cobranzas?op=COBRO_CON_FORMULARIO',
                            timeout: TIME_OUT_AJAX,
                            success: function (form, accion) {
                                console.log(accion);
                                Ext.Msg.hide();
                                Ext.MessageBox.hide();
                                var obj = Ext.util.JSON.decode(accion.response.responseText);
                                if (obj.success) {
                                    fp.getForm().reset();
                                    var win = new Ext.Window({
                                        title: 'Info',
                                        autoWidth: true,
                                        height: 'auto',
                                        closable: false,
                                        resizable: false,
                                        modal: true,
                                        // defaultButton : 'idBtnOkCobroFormulario',
                                        buttons: [{
                                                id: 'idBtnOkCobroFormulario',
                                                text: 'ok',
                                                focus: true,
                                                disabled: true,
                                                handler: function () {
                                                    win.close();
                                                    comboREFERENCIAS.focus(true);
                                                }
                                            }, {
                                                text: 'Certificar Formulario',
                                                formBind: true,
                                                handler: function () {
                                                    imprimirImpresoraExterna(obj.ticket);
                                                    imprimirImpresoraExterna(obj.ticketTMU);
                                                    Ext.getCmp('idBtnOkCobroFormulario').setDisabled(false);
                                                    Ext.getCmp('idBtnImprTicketCertTMU').setDisabled(false);

                                                }
                                            }, {
                                                text: 'Imprimir en Ticket',
                                                id: 'idBtnImprTicketCertTMU',
                                                formBind: true,
                                                disabled: true,
                                                handler: function () {
                                                    imprimirImpresoraExterna(obj.ticketTMU);
                                                }
                                            }, {
                                                id: 'idBotonCancel',
                                                text: 'Cancelar',
                                                disabled: false,
                                                handler: function () {
                                                    Ext.Msg.show({
                                                        title: 'Atención',
                                                        msg: 'Esta seguro que desea cancelar la acción?',
                                                        buttons: Ext.MessageBox.OKCANCEL,
                                                        icon: Ext.MessageBox.WARNING,
                                                        fn: function (btn) {
                                                            if (btn == 'ok') {
                                                                win.close();
                                                                comboREFERENCIAS.focus(true);
                                                            }
                                                        }
                                                    });
                                                }
                                            }],
                                        items: [{
                                                html: '<H1>' + obj.ticket_pantalla + '</H1>'
                                            }]
                                    });
                                    aceptarTrabajando = false;
                                    win.show();
                                    if (obj.certificacion_formulario != undefined) {
                                        imprimirImpresoraExterna(obj.ticket);
                                    }

                                } else {
                                    callBackServidor(obj);
                                }
                            },
                            failure: function (form, action) {
                                Ext.Msg.hide();
                                aceptarTrabajando = false;
                                var obj = Ext.util.JSON.decode(action.response.responseText);
                                callBackServidor(obj);

                            }
                        });
                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    fp.getForm().reset();
                }
            }]
    });

    return fp;


}