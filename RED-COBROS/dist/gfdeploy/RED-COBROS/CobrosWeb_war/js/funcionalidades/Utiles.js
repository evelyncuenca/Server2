var TIME_OUT_AJAX = 600000;
var MSG_GESTION_CERRADA = "Debe realizar una apertura"
var MSG_DEBE_ABRIR_GESTION_PARA_REALIZAR_OP = "Debe realizar una Apertura para utilizar esta opción";
var MSG_REFERENCIA_PARA_CAJA = "El cód. de referencia para caja: ";

TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID = 'Atención';
CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID = '¿Está seguro que desea eliminar el registro seleccionado?';

FAIL_TITULO_GENERAL = 'Atención';
FAIL_CUERPO_GENERAL = 'No se pudo realizar la operación.';

TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID = 'Confirmación';
CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID = '¿Está seguro que desea modificar el registro seleccionado?';

TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID = 'Confirmación';
CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID = '¿Está seguro que desea agregar el registro?';

WAIT_TITLE_MODIFICANDO = 'Conectando';
WAIT_MSG_MODIFICANDO = 'Modificando los datos del registro seleccionado...';

WAIT_TITLE_AGREGANDO = 'Conectando';
WAIT_MSG_AGREGANDO = 'Agregando los datos...';

TITULO_NO_EXISTE_REPORTE = 'Estado';
CUERPO_NO_EXISTE_REPORTE = 'No existe resultado para la consulta';

TOOL_TIP_AGREGAR = 'Agregar registro';
TOOL_TIP_QUITAR = 'Quitar registro';

TITULO_ACTUALIZACION_EXITOSA = 'Info';
CUERPO_ACTUALIZACION_EXITOSA = 'Actualización Exitosa';

TITULO_EXITOSO = 'Info';
CUERPO_EXITOSO = 'Operación Exitosa';

function getRadioButtonSelectedValue(ctrl)
{
    for (i = 0; i < ctrl.length; i++)
        if (ctrl[i].checked)
            return ctrl[i].value;
}
function replaceAllNoNumbers(sValue) {
    sValue = sValue.replace(/[^,0-9]/g, "");
    return sValue;
}
function replaceAllNoNumbers2(sValue, elId) {
    //console.log(elId);
    // console.log(sValue);

    //Agrega por deafault el 0
    // console.log(sValue);
    // alert(sValue);
    sValue = sValue.replace(/[^,0-9]/g, "");
    if (sValue.length == 0) {
        sValue = 0;
    }
    //  console.log(document.getElementById(elId))
    document.getElementById(elId).value = sValue;
// return sValue;
}

function ltrim(src, c) {
    i = 0;
    for (; i < src.length; i++) {
        if (src.charAt(i) !== c) {
            break;
        }
    }
    return src.substr(i);

}
function addCommas(sValue) {
    sValue = sValue.replace(/,/g, "");
    var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})');
    while (sRegExp.test(sValue)) {
        sValue = sValue.replace(sRegExp, '$1,$2');
    }
    return sValue;
}

function addPuntos(sValue) {
    sValue = sValue.replace(/,/g, "");
    var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})');
    while (sRegExp.test(sValue)) {
        sValue = sValue.replace(sRegExp, '$1.$2');
    }
    return sValue;
}

function deleteCommas(sValue)
{
    sValue = sValue.replace(/,/g, "");
    return (sValue);

}
function imprimirImpresoraExterna(cadenaImpresion) {

    if (URL_IMPRESORA != "") {
        var dsPrinter = new Ext.data.Store({
            proxy: new Ext.data.ScriptTagProxy({
                url: "" + URL_IMPRESORA + '/?printer=' + cadenaImpresion
            })
        });
        dsPrinter.load({});
    }
}
function getUrlImpresora() {

    var conn = new Ext.data.Connection();
    conn.request({
        url: 'inicial?op=GET_URI_IMPRESORA',
        method: 'POST',
        success: function (action) {
            var obj = Ext.util.JSON.decode(action.responseText);
            if (obj.success) {
                URL_IMPRESORA = "" + obj.uriImpresora;
            }
        },
        failure: function (action) {
            URL_IMPRESORA = "";
        }
    });
}

function logoutDirecto() {
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
                msg: 'No se puede cerrar esta sesión',
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK

            });

        }
    });
}
function logout() {

    Ext.Msg.show({
        title: 'Estado',
        msg: '¿Está seguro que desea cerrar esta Sesión?',
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
                            msg: 'No se puede cerrar esta sesión',
                            animEl: 'elId',
                            icon: Ext.MessageBox.ERROR,
                            buttons: Ext.Msg.OK

                        });

                    }
                });
            }


        }

    });

}

function lanzarReporte(reporte) {

    if (reporte == 'idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN') {
        pantallaREPORTE_DECLARACION_POR_NUMERO_ORDEN().center();


    } else if (reporte == 'idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN') {
        pantallaREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN().center();

    }
    else if (reporte == 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO') {
        pantallaREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO().center();

    }
    else if (reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO') {
        pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO().center();

    }
    else if (reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE') {
        pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE().center();

    }
    else if (reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO') {
        pantallaREPORTE_COBRANZA_DETALLADO_EFECTIVO().center();

    }
    else if (reporte == 'idPanelREPORTE_TAPALOTE') {
        pantallaREPORTE_TAPALOTE().center();

    }
    else if (reporte == 'idPanelREPORTE_TAPALOTE_DETALLADO') {
        pantallaREPORTE_TAPALOTE_DETALLADO().center();

    }
    else if (reporte == 'idPanelREPORTE_CIERRE_RESUMIDO') {
        pantallaREPORTE_CIERRE_RESUMIDO().center();

    }
    else if (reporte == 'idPanelREPORTE_CIERRE_DETALLADO') {
        pantallaREPORTE_CIERRE_DETALLADO().center();

    }
    else if (reporte == 'idPanelREPORTE_CIERRE_CS') {
        pantallaREPORTE_CIERRE_CS().center();
    }
    else if (reporte == 'idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS') {
        pantallaREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS().center();
    }
    else if (reporte == 'idPanelREPORTE_RESUMEN_DE_TRANSACCIONES') {
        pantallaREPORTE_RESUMEN_DE_TRANSACCIONES().center();
    }
    else if (reporte == 'idPanelREPORTE_CIERRE_USUARIO') {
        pantallaREPORTE_CIERRE_USUARIO().center();
    }
    else if (reporte == 'idPanelRESUMEN_COBRANZA_CS') {
        pantallaREPORTE_COBRANZA_CS().center();
    }
    else if (reporte == 'idPanelREPORTE_RESUMEN_FACTURADOR_GESTION') {
        pantallaREPORTE_RESUMEN_FACTURADOR_GESTION().center();
    }
    else if (reporte == 'idPanelREPORTE_DIGITACION') {
        pantallaREPORTE_DIGITACION().center();
    }



}
function autorizarOpcion(reporteAutorizado) {
    var buttonOK = new Ext.Button({
        text: 'OK',
        formBind: true,
        handler: function () {
            win.focus();
            formPanel.getForm().submit({
                url: 'reportes?op=AUTORIZAR_REPORTE',
                method: 'POST',
                timeout: TIME_OUT_AJAX,
                success: function (form, action) {
                    win.close();
                    var obj = Ext.util.JSON.decode(action.response.responseText);
                    if (obj.success) {

                        lanzarReporte(reporteAutorizado);

                    } else {
                        callBackServidor(obj);
                    }
                },
                failure: function (form, action) {
                    var obj = Ext.util.JSON.decode(action.response.responseText);
                    callBackServidor(obj);

                }
            });
        }
    });



    var formPanel = new Ext.FormPanel({
        labelWidth: 100,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [{
                fieldLabel: 'Password',
                name: 'PASSWORD',
                inputType: 'password',
                enableKeyEvents: true,
                allowBlank: false,
                listeners: {
                    'specialkey': function (esteObjeto, esteEvento) {
                        if (esteEvento.getKey() == 13) {
                            buttonOK.focus(true, true);
                        }

                    }
                }
            }],
        buttons: [buttonOK, {
                text: 'Cancelar',
                handler: function () {
                    win.close()
                }

            }]
    });
    var win = new Ext.Window({
        title: 'Autorización',
        autoWidth: true,
        height: 'auto',
        closable: false,
        resizable: false,
        modal: true,
        items: formPanel

    });
    win.show();

}
function getCombo(suUrl, suRootJson, suIdJson, suNameJson, suDescripcion, suLabel, suHidden, suDisplayField, suValueField, suEmptyText) {

    var ds_providers = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'combos?op=' + suUrl + '&combo=' + suEmptyText
        }),
        reader: new Ext.data.JsonReader({
            root: suRootJson,
            id: suIdJson,
            totalProperty: 'TOTAL'
        }, [{
                name: suNameJson
            }, {
                name: suDescripcion
            }])
    });


    var combo = new Ext.form.ComboBox({
        fieldLabel: suLabel,
        hiddenName: suHidden,
        store: ds_providers,
        displayField: suDisplayField,
        valueField: suValueField,
        triggerAction: 'all',
        emptyText: suEmptyText,
        selectOnFocus: true,
        anchor: '95%',
        pageSize: 10,
        listWidth: 250,
        forceSelection: true,
        loadingText: 'Cargando...',
        typeAhead: true,
        minChars: 1
    });
    return combo;
}
function callBackServidor(obj) {
     console.log(obj);
    if (obj.tipo == 1) {
        Ext.MessageBox.show({
            title: 'Estado',
            msg: obj.motivo,
            width: 500,
            wait: true,
            waitConfig: {
                interval: 200
            },
            icon: Ext.MessageBox.ERROR
        });
        setTimeout(function () {
            Ext.MessageBox.hide();
            logoutDirecto();
        }, 5000);

    } else {
        try {
            Ext.Msg.show({
                title: 'Estado',
                msg: obj.motivo,
                buttons: Ext.Msg.OK,
                icon: Ext.MessageBox.ERROR
            });

        }
        catch (er) {
            Ext.Msg.show({
                title: 'Estado',
                msg: 'No se pudo realizar la operación',
                buttons: Ext.Msg.OK,
                icon: Ext.MessageBox.ERROR
            });

        }

    }
}

function checkFechaCheque(date) {
    if (date == "") {
        return false;
    }
    if (date.length != 8) {
        alert("Fecha Inválida. El formato debe ser ddMMyyyy");
        return false;
    }
    var formatDate = date.substr(4, 4) + "-" + date.substr(2, 2) + "-" + date.substr(0, 2);
    var today = new Date();
    var dateParam = new Date(formatDate);

    if (dateParam > today) {
        alert("La fecha del cheque es mayor a la fecha actual");
        return false;
    }

    dateParam.setDate(dateParam.getDate() + 26);

    if (today > dateParam) {
        alert("Fecha De Cheque Vencido");
        return false;
    }
    return true;
}

function blockUI() {
    Ext.Msg.wait('Procesando... Por Favor espere...');
}

function unBlockUI() {
    Ext.Msg.hide();
}

function mostrarError(mensaje) {
    Ext.Msg.show({
        title: 'Estado',
        msg: mensaje,
        icon: Ext.MessageBox.ERROR,
        buttons: Ext.Msg.OK
    });
}