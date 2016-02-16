function pantallaREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS() {

    var panel = {
        //        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        //        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        xtype: 'panel',
        layout: 'fit',
        border: false,
        autoScroll: true,
        items: [FormPanelReporteCobranzas()]
    }
    //return panel;
    var win = new Ext.Window({
        id: 'idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS',
        title: 'REPORTE COBRANZA CHEQUE EFECTIVO COBRO SERVICIOS',
        width: 'auto',
        height: 'auto',
        modal: true,
        closable: true,
        resizable: false,
        items: [panel]
    });
    return win.show();


}

function FormPanelReporteCobranzas() {
    /**
     * Agregamos el filtro por monedas
     * */

    $.ajax({
        type: "POST",
        url: 'Moneda',
        async: false,
        success: function (data) {
            mon = data;
        },
        error: function () {
            alert('No se pudo obtener las monedas');
        }
    });

    var monedaStore = new Ext.data.Store({
        id: 'idMonedaStore',
        reader: new Ext.data.JsonReader({
            root: 'monedas',
            totalProperty: 'total',
            id: 'idMoneda',
            fields: ['idMoneda', 'descripcion', 'abreviatura']
        }),
        listeners: {
            'load': function (store, records, successful, eOpts) {
                if (!successful) {
                    Ext.Msg.show({
                        title: FAIL_TITULO_GENERAL,
                        msg: 'No se han podido obtener las monedas'
                    });
                }
            }
        }
    });

    monedaStore.loadData(Ext.decode(mon));

    var comboMoneda = new Ext.form.ComboBox({
        fieldLabel: 'Moneda',
        id: 'idComboMoneda',
        store: monedaStore,
        emptyText: 'MONEDA',
        anchor: '95%',
        forceSelection: true,
        width: '250',
        listWidth: 250,
        loadingText: 'Cargando...',
        displayField: 'descripcion',
        valueField: 'idMoneda',
        mode: 'local',
        name: 'idMoneda',
        triggerAction: 'all'
    });


    //var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    //var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboFACTURADOR = getCombo('FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR');
    var comboSERVICIO = getCombo('SERVICIO_CS', 'SERVICIO', 'SERVICIO', 'SERVICIO', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'codigoServicio', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'SERVICIO');
    //var comboGESTION  = getCombo("GESTION", "GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION");
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'GESTIÓN', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'REGISTRO_GESTION', 'GESTIONES...');
    // / var comboUSUARIO = getCombo('USUARIO','USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    // var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    // var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    var comboTIPO_REPORTE = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Reporte',
        name: 'tipoReporte',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'DETALLADO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['DETALLADO', 'DETALLADO'],
                ['RESUMIDO', 'RESUMIDO']]
        })
    });
    var comboTIPO_CONSULTA = new Ext.form.ComboBox({
        fieldLabel: 'TIPO CONSULTA',
        hiddenName: 'tipo_consulta',
        valueField: 'TIPO_CONSULTA',
        emptyText: 'TIPO CONSULTA...',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'DESCRIPCION_TIPO_CONSULTA',
        mode: 'local',
        allowBlank: true,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO_CONSULTA', 'DESCRIPCION_TIPO_CONSULTA'],
            data: [['1', 'Todas mis terminales'],
                ['2', 'Sólo esta terminal']]
        })
    });
    var comboZONA = new Ext.form.ComboBox({
        fieldLabel: 'Zona',
        name: 'zona',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['001', 'CAPITAL'],
                ['002', 'INTERIOR']]
        })
    });
    comboTIPO_CONSULTA.addListener('select', function (esteCombo, record, index) {

        comboREGISTRO_GESTION.store.baseParams['tipo_consulta'] = record.data.TIPO_CONSULTA;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);

    var comboTIPO_COBRO = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Cobro',
        name: 'tipoCobro',
        hiddenName: 'TIPO',
        valueField: 'TIPO',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank: false,
        value: 'COB-CS',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data: [['COB-CS', 'CHEQUE Y EFECTIVO'],
                ['COB-CHEQUE-CS', 'CHEQUE'],
                ['COB-EFECT-CS', 'EFECTIVO']]
        })
    });




    var comboEstadoTransaccion = new Ext.form.ComboBox({
        fieldLabel: 'ESTADO TRANSACCIÓN',
        hiddenName: 'estadoTransaccion',
        valueField: 'estadoTransaccion',
        emptyText: 'ESTADO TRANSACCIÓN...',
        anchor: '95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        value: 'null',
        store: new Ext.data.SimpleStore({
            fields: ['estadoTransaccion', 'descripcion'],
            data: [['null', 'Cualquiera'],
                ['S', 'Anulado'],
                ['N', 'No Anulado']]
        })
    });




    comboFACTURADOR.addListener('change', function (esteCombo, newValue, oldValue) {

        if (esteCombo.getRawValue().length == 0) {
            comboSERVICIO.disable();

            comboSERVICIO.reset();


        }

    }, null, null);

    comboFACTURADOR.addListener('select', function (esteCombo, record, index) {
        comboSERVICIO.enable();
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null, null);


    comboTIPO_COBRO.addListener('change', function (esteCombo, newValue, oldValue) {
        if (esteCombo.getRawValue() == 'CHEQUE' || esteCombo.getRawValue() == 'EFECTIVO') {
            comboTIPO_REPORTE.disable();
            comboTIPO_REPORTE.setValue('DETALLADO');
        } else {
            comboTIPO_REPORTE.enable();
        }
    }, null, null);

    comboSERVICIO.disable();

    comboFACTURADOR.allowBlank = true;
    comboSERVICIO.allowBlank = true;
    comboREGISTRO_GESTION.allowBlank = true;

    var fechaInicio = new Ext.form.DateField({
        fieldLabel: 'Fecha Inicio',
        height: '22',
        anchor: '95%',
        id: 'idFecha',
        allowBlank: false,
        format: 'dmY'
    });
    var fechaDeFin = new Ext.form.DateField({
        fieldLabel: 'Fecha Fin',
        height: '22',
        anchor: '95%',
        allowBlank: true,
        format: 'dmY'


    });
    fechaInicio.addListener('change', function (esteCampo, nuevoValor, viejoValor) {

        comboREGISTRO_GESTION.store.baseParams['fechaIni'] = esteCampo.getRawValue();
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);

    fechaDeFin.addListener('change', function (esteCampo, nuevoValor, viejoValor) {

        if (fechaInicio.getRawValue() != "") {
            comboREGISTRO_GESTION.store.baseParams['fechaFin'] = esteCampo.getRawValue();
            comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
            comboREGISTRO_GESTION.store.baseParams['start'] = 0;
            comboREGISTRO_GESTION.store.reload();
        }

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
    var tmu = new Ext.form.Checkbox({
        fieldLabel: 'Imprimir en TMU',
        columns: 1,
        name: 'tmu'
    });
    var formPanel = new Ext.FormPanel({
        labelWidth: 165,
        frame: true,
        autoHeight: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboFACTURADOR, comboSERVICIO, comboZONA, comboEstadoTransaccion, fechaInicio, fechaDeFin, comboTIPO_CONSULTA, comboREGISTRO_GESTION, comboTIPO_REPORTE, comboTIPO_COBRO, comboMoneda, formatoDeDescarga, tmu],
        buttons: [{
                formBind: true,
                text: 'Reporte',
                handler: function () {
                    if (tmu.getValue()) {
                        formPanel.getForm().submit({
                            url: 'reportes?op=COB-CS' + '&codigoServicio=' + comboSERVICIO.getValue() + '&idFacturador=' + comboFACTURADOR.getValue() + '&fechaFin=' + fechaDeFin.getRawValue() + '&fechaIni=' + fechaInicio.getRawValue() + '&tipoPago=' + comboTIPO_COBRO.getValue() + '&tipoConsulta=' + comboTIPO_CONSULTA.getValue() + '&tipoReporte=DETALLADO',
                            method: 'POST',
                            timeout: TIME_OUT_AJAX,
                            success: function (form, action1) {
                                var obj = Ext.util.JSON.decode(action1.response.responseText);
                                if (obj.success) {
                                    var win12 = new Ext.Window({
                                        title: 'Reporte Cobranza Detallado',
                                        autoWidth: true,
                                        autoHeight: false,
                                        autoScroll: true,
                                        height: 300,
                                        closable: false,
                                        resizable: false,
                                        modal: true,
                                        buttons: [{
                                                text: 'Cancelar',
                                                handler: function () {
                                                    win12.close();
                                                }
                                            }, {
                                                text: 'Imprimir',
                                                formBind: true,
                                                handler: function () {
                                                    imprimirImpresoraExterna(obj.reporteCobranzaImpresora);
                                                }
                                            }],
                                        items: [{
                                                html: '<H1>' + obj.reporteCobranzaPantalla + '</H1>'
                                            }]
                                    });
                                    win12.show();
                                } else {
                                    Ext.Msg.show({
                                        title: "Info",
                                        msg: "No existe resultado para la consulta",
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.MessageBox.INFO,
                                        fn: function (btn, text) {
                                            close();
                                        }
                                    });
                                }
                            },
                            failure: function (form, action) {
                                Ext.Msg.show({
                                    title: "Info",
                                    msg: "No existe resultado para la consulta",
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.MessageBox.INFO,
                                    fn: function (btn, text) {
                                        close();
                                    }
                                });
                            }
                        });
                    } else {
                        try {
                            Ext.destroy(Ext.get('downloadIframe'));
                            Ext.DomHelper.append(document.body, {
                                tag: 'iframe',
                                id: 'downloadIframe',
                                frameBorder: 0,
                                width: 0,
                                height: 0,
                                css: 'display:yes;visibility:hidden;height:0px;',
                                src: 'reportes?op=' + comboTIPO_COBRO.getValue() + '&codigoServicio=' + comboSERVICIO.getValue() + '&idFacturador=' + comboFACTURADOR.getValue() + '&idGestion=' + comboREGISTRO_GESTION.getValue() + '&estadoTransaccion=' + comboEstadoTransaccion.getValue() + '&fechaFin=' + fechaDeFin.getRawValue() + '&fechaIni=' + fechaInicio.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue() + '&tipoConsulta=' + comboTIPO_CONSULTA.getValue() + '&tipoReporte=' + comboTIPO_REPORTE.getRawValue() + '&zona=' + comboZONA.getValue() + '&idMoneda=' + comboMoneda.getValue()
                            });
                        } catch (e) {
                            alert(e);
                        }
                    }
                }
            }, {
                text: 'Reset',
                handler: function () {
                    formPanel.getForm().reset();
                    //comboFACTURADOR.disable();
                    //comboRECAUDADOR.disable();
                    comboSERVICIO.disable();
                    comboTIPO_REPORTE.enable();
                    //comboTERMINAL.disable();
                    //comboREGISTRO_GESTION.disable();
                    //comboUSUARIO.disable();
                }
            }]
    });
    return formPanel;
}