function panelCambiarImagenes() {
    var panel = new Ext.Panel({
        title: 'Cambiar Imagen',
        id: 'panelCambiarImagen',
        // activeTab: 0,
        autoScroll: true,
        frame: true,
        defaults: {
            autoScroll: true
        },
        items: [cabeceraCambiarImagen()]
    });
    return panel;
}

function cabeceraCambiarImagen() {
    var facturadores = localStorage.getItem("facturadores");
    var jsonCompleto = JSON.parse(facturadores);
    var jsonFacturadores = [];
    var jsonServicio = [];
    var idServicio;
    Ext.each(jsonCompleto, function (value) {
        jsonFacturadores.push(value.facturador);
    });
    var comboServicio = new Ext.form.ComboBox({
        fieldLabel: 'Servicio',
        id: 'idComboServicio',
        store: [],
        emptyText: 'Seleccione un servicio...',
        forceSelection: true,
        displayField: 'nombre',
        valueField: 'id',
        width: '250',
        listWidth: 250,
        loadingText: 'Cargando...'
    });
    comboServicio.disable();

    var comboFacturadores = new Ext.form.ComboBox({
        fieldLabel: 'Facturador',
        id: 'idComboFacturador',
        store: jsonFacturadores,
        emptyText: 'Seleccione un facturador...',
        forceSelection: true,
        displayField: 'nombre',
        valueField: 'nombre',
        queryMode: 'remote',
        listWidth: 250,
        loadingText: 'Cargando...',
        listeners: {
            select: function () {
                comboServicio.bindStore([]);
                comboServicio.setValue('');
                Ext.each(jsonCompleto, function (value) {
                    if (value.facturador === comboFacturadores.getValue()) {
                        jsonServicio = [];
                        Ext.each(value.servicios, function (valueC) {
                            jsonServicio.push(valueC.nombre);
                        });
                    }
                });
                comboServicio.bindStore(jsonServicio);
                comboServicio.enable();
            }
        }
    });




    var items = {
        title: 'Complete el formulario',
        xtype: 'form',
        id: 'idHeaderCargarImagen',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        labelAlign: 'top',
        fileUpload: true,
        url: "ImagenesUpload",
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items: [comboFacturadores, comboServicio,
            {
                xtype: 'textfield',
                inputType: 'file',
                name: 'archivoIMG',
                fieldLabel: 'Imagen',
                id: 'idFieldCargaImagen',
                allowBlank: false,
                width: '250',
                enableKeyEvents: true,
                monitorValid: true
            }],
        buttonAlign: 'left',
        buttons: [{
                text: 'Aceptar',
                id: 'idBtnAceptarCargaImagen',
                width: '130',
                formBind: true,
                handler: function () {
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('idHeaderCargarImagen').getForm().submit({
                        success: function (form, result) {
                            Ext.Msg.hide();
                            Ext.Msg.show({
                                title: 'OPERACION EXITOSA',
                                msg: 'Archivo Cargado y Procesado con &eacute;xito',
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.INFO,
                                width: 400,
                                fn: function (btn) {
                                    if (btn === 'ok') {
                                        Ext.getCmp('idHeaderCargarImagen').getForm().reset();
                                    }
                                }
                            });
                        },
                        failure: function () {
                            Ext.Msg.hide();
                            Ext.Msg.show({
                                title: FAIL_TITULO_GENERAL,
                                msg: 'La imagen no se proceso correctamente, intentelo nuevamente',
                                buttons: Ext.Msg.OK,
                                icon: Ext.MessageBox.ERROR,
                                fn: function (btn) {
                                    if (btn === 'ok') {
                                        Ext.getCmp('idHeaderCargarImagen').getForm().reset();
                                    }
                                }
                            });
                        }
                    });
                }
            }, {
                text: 'Cancelar',
                width: '130',
                handler: function () {
                    Ext.getCmp('idHeaderCargarImagen').getForm().reset();
                }
            }]
    };
    return items;
}