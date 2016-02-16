function panelCobranzaPagoConBoletaDePagoCyp() {
    var panel = {
        id: 'panelCobranzasPagoConBoletaDePagoCyp',
        title: 'Pago Con Consulta de Boletas de Pago',
        xtype: 'panel',
        layout: 'fit',
        border: false,
        height: 500,
        autoScroll: true,
        items: [cobranzasFormularioBoletaDePagoCyp()]
    };

    return panel;

}

function cobranzasFormularioBoletaDePagoCyp() {

    var individual = [{
            items: {
                xtype: 'fieldset',
                title: 'Búsqueda',
                autoHeight: true,
                width: 350,
                defaultType: 'textfield',
                items: [{
                        id: 'idRucBoletaPagoCyp',
                        name: 'ruc',
                        fieldLabel: 'RUC',
                        anchor: '95%',
                        allowBlank: false,
                        listeners: {
                            'specialkey': function (esteObjeto, esteEvento) {
                                if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                    Ext.getCmp('idFechaBoletaPagoCyp').focus(true, true);
                                }
                            }
                        }
                    }, {
                        id: 'idFechaBoletaPagoCyp',
                        name: 'fecha',
                        anchor: '95%',
                        emptyText: 'dd/mm/yyyy',
                        fieldLabel: 'Fecha',
                        allowBlank: false,
                        listeners: {
                            'specialkey': function (esteObjeto, esteEvento) {
                                if (esteEvento.getKey() === 13 || esteEvento.getKey() === 9) {
                                    Ext.getCmp('btnRealizarOperacionSetCyp').focus(true, true);
                                }
                            }
                        }
                    }]
            }
        }];

    var fp = new Ext.FormPanel({
        id: 'formualrioSetCyp',
        frame: true,
        labelWidth: 130,
        width: 300,
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
                id: 'btnRealizarOperacionSetCyp',
                text: 'Aceptar',
                formBind: true,
                handler: function (esteObjeto) {
                    var randomNumber = Math.floor((Math.random() * 10000000) + 1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA' + '&ID_RANDOM=' + randomNumber,
                        params: {
                            NRO_REFERENCIA: Ext.getCmp("idRucBoletaPagoCyp").getValue() + ';' + Ext.getCmp("idFechaBoletaPagoCyp").getValue(),
                            SERVICIO: 1,
                            ID_FACTURADOR: 1
                        },
                        discardUrl: false,
                        nocache: true,
                        text: "Cargando...",
                        timeout: TIME_OUT_AJAX,
                        scripts: true
                    });
                    Ext.Msg.hide();
                    Ext.getCmp('content-panel').layout.setActiveItem('panelConsulta');
                }
            }, {
                text: 'Reset',
                handler: function () {
                    fp.getForm().reset();
                }
            }, {
                text: 'Salir',
                handler: function () {
                    fp.getForm().reset();
                    cardInicial();
                }
            }]
    });
    return fp;


}