
Ext.onReady(function () {
    overrideExtDate();
    Ext.QuickTips.init();
    tabOperaciones();
    /*verificarGESTIONAbierta();
     getInformation();
     getUrlImpresora();  */
    inactivityTime();
    setTimeout("valIniciales()", 1000);
    //setInterval("hasSession()", 180000);
});
var mainPanel = false;
var GESTION_ABIERTA = false;
var getRemoteComponentPlugin;
var getMainPanel;
var tipoFormulario;
var campoFormulario;
var campoValor;
var campoDescripcion;
var helpTable;
var MENSAJES = false;
var URL_IMPRESORA = "";


var inactivityTime = function () {
    var t;
    window.onload = resetTimer;
    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;

    function logOutAuto() {
        var conn = new Ext.data.Connection();
        conn.request({
            url: 'inicial?op=LOGOUT',
            method: 'POST',
            success: function (action) {
                var obj = Ext.util.JSON.decode(action.responseText);
                if (obj.success) {

                    Ext.Msg.show({
                        title: 'Estado',
                        msg: 'Su sesión ha expirado, favor vuelva a iniciar sesión.',
                        buttons: Ext.Msg.OK,
                        //modal: true,
                        icon: Ext.MessageBox.ERROR,
                        fn: function (btn) {
                            var redirect = 'login.jsp?lo=1';
                            window.location = redirect;
                        }
                    });

                } else {
                }
            },
            failure: function (action) {

            }
        });
    }
    function resetTimer() {
        clearTimeout(t);
        t = setTimeout(logOutAuto, 10800000);//3 horas  
    }
};

function cardPurpura() {

    if (GESTION_ABIERTA) {
        //workingCyP = false;
        Ext.getCmp('idCargillRetencionFormPanel').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelPurpura');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function valIniciales() {
    verificarGESTIONAbierta();
    getInformation();
    getUrlImpresora();
}
function overrideExtDate() {
    Ext.DatePicker.override({
        update: Ext.DatePicker.prototype.update.createSequence(function () {
            var w = 190;
            this.el.setWidth(w + this.el.getBorderWidth("lr"));
            Ext.fly(this.el.dom.firstChild).setWidth(w);
        })
    });
}

function generarPase() {
    var conn = new Ext.data.Connection();
    conn.request({
        url: 'USUARIO_TERMINAL?op=GENERAR_PASE',
        method: 'POST',
        success: function (action) {
            var obj1 = Ext.util.JSON.decode(action.responseText);
            var titulo = "Pase obtenido";
            var msg = "";
            var icon = Ext.MessageBox.INFO;
            if (obj1.success) {
                msg = obj1.pase;
            } else {
                titulo = "Error";
                msg = "No se pudo generar pase";
                icon = Ext.MessageBox.ERROR;
            }
            Ext.Msg.show({
                title: titulo,
                msg: msg,
                buttons: Ext.Msg.OK,
                icon: icon
            });
        },
        failure: function (action) {
            Ext.Msg.show({
                title: 'Estado',
                msg: "No se pudo generar pase",
                buttons: Ext.Msg.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    });
}

function calcMacAddr() {
    var macaddres = new Ext.data.JsonStore({
        root: 'MACS',
        fields: ['MAC'],
        proxy: new Ext.data.ScriptTagProxy({
            url: 'http://localhost:54321?getMac',
            callbackPrefix: 'getMac'
        })
    });
    macaddres.load();
}
var macAddr;
function getMac(data)
{
    try {
        var temp = data.replace(/:/g, '":"').replace(/{/g, '{"').replace(/}/g, '"}').replace('\"[', '[').replace("}]\"", "}]");
        macAddr = Ext.util.JSON.decode(temp);
    } catch (e) {
        alert("No se pudo obtener MAC");
    }
}


function getInformation() {
    var conn = new Ext.data.Connection();
    conn.request({
        url: 'INFORMACION?op=SYS_INF',
        method: 'POST',
        success: function (action) {
            var obj1 = Ext.util.JSON.decode(action.responseText);
            if (obj1.success) {
                MENSAJES = true;
                for (i = 0; i < obj1.TOTAL; i++) {
                    jQuery.noticeAdd({
                        text: obj1.INFORMACION[i].MENSAJE,
                        stay: true
                    });
                }

            }
        },
        failure: function (action) {

        }
    });
}
function newPopup(url) {
    window.open(url, 'popUpWindow', 'height=700,width=800,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
}

function hasSession() {
    try {
        var conn = new Ext.data.Connection();
        conn.request({
            url: 'inicial?op=HAS_SESSION',
            method: 'POST',
            success: function (action) {
                var obj = Ext.util.JSON.decode(action.responseText);
                if (!obj.success) {
                    var redirect = 'login.jsp';
                    window.location = redirect;
                }
            },
            failure: function (action) {
            }
        });
    } catch (err) {
    }

}
function verificarGESTIONAbierta() {
    var conn = new Ext.data.Connection();
    conn.request({
        url: 'inicial?op=VERIFICAR_GESTION_ABIERTA',
        method: 'POST',
        success: function (action) {
            var obj1 = Ext.util.JSON.decode(action.responseText);
            if (obj1.success) {
                GESTION_ABIERTA = true;

            } else {
                GESTION_ABIERTA = false;
            }
        },
        failure: function (action) {
            GESTION_ABIERTA = false;
        }
    });
}



function cardDigitacionFormulario() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelFormularios');
        Ext.getCmp('idDigitacionGestor').focus(true);

    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardEvento() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelFormularios');
        Ext.getCmp('idDigitacionGestor').focus(true);

    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardEnviosPersonal() {

    if (GESTION_ABIERTA) {
        var randomNumber = Math.floor((Math.random() * 10000000) + 1);
        $.ajax({
            type: "POST",
            url: 'ComisionServicio?' + '&ID_RANDOM=' + randomNumber,
            data: {
                ID_SERVICIO: 83
            },
            async: false,
            success: function (data) {
                var jsonData = JSON.parse(data);
                localStorage.setItem("COMISION_EP", jsonData.comision);
                workingCyP = false;
                Ext.getCmp('idHeaderEnviosPersonal').getForm().reset();
                Ext.getCmp('idHeaderRetirosPersonal').getForm().reset();
                Ext.getCmp('content-panel').layout.setActiveItem('panelEnvios');
            },
            error: function () {
                alert('No se pudo obtener la comision');
            }
        });
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardFastBox() {

    if (GESTION_ABIERTA) {
        //workingCyP = false;
        Ext.getCmp('idHeaderFlete').getForm().reset();
        Ext.getCmp('idHeaderCompra').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelFast');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardIced() {

    if (GESTION_ABIERTA) {
        //workingCyP = false;
        Ext.getCmp('idHeaderCurso').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelIced');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardCargill() {

    if (GESTION_ABIERTA) {
        //workingCyP = false;
        Ext.getCmp('idCargillRetencionFormPanel').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelCargill');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardGanemax() {

    if (GESTION_ABIERTA) {
        //workingCyP = false;
        // Ext.getCmp('idHeaderFlete').getForm().reset();
        //Ext.getCmp('idHeaderCompra').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelGmx');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardFPJTarjeta() {

    if (GESTION_ABIERTA) {
        workingCyP = false;
        Ext.getCmp('idHeaderFPJTarjeta').getForm().reset();
        Ext.getCmp('idNroCIFPJTarjeta').focus(true, true);
        Ext.getCmp('content-panel').layout.setActiveItem('panelFPJTarjeta');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardFPJAdelanto() {

    if (GESTION_ABIERTA) {
        workingCyP = false;
        Ext.getCmp('idHeaderFPJAdelanto').getForm().reset();
        Ext.getCmp('idNroCIFPJAdelanto').focus(true, true);
        Ext.getCmp('content-panel').layout.setActiveItem('panelFPJAdelanto');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardPoderJudicial() {

    if (GESTION_ABIERTA) {
        workingCyP = false;
        Ext.getCmp('idHeaderLiquidacionPoderJudicial').getForm().reset();
        Ext.getCmp('idHeaderAntecedentePoderJudicial').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelPoderJudicial');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardDepositoExtraccion() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('idDepositoExtraccionFormPanel').getForm().reset();
        Ext.getCmp('content-panel').layout.setActiveItem('panelDepositoExtraccion');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}


function confAdminGestores() {

    Ext.getCmp('content-panel').layout.setActiveItem('gridGESTOR');
    Ext.getCmp('gridGESTOR').store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
}
function confAdminEventos() {

    Ext.getCmp('content-panel').layout.setActiveItem('panelEventos');
    Ext.getCmp('gridEVENTO').store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    Ext.getCmp('gridSUSCRIPTORES').store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
}
function cardAyuda() {
    if (GESTION_ABIERTA) {
        //        Ext.getCmp('panelGrillaAyuda').load({
        //            url: 'ayuda.jsp',
        //            params:{
        //            },
        //            discardUrl: false,
        //            nocache: false,
        //            text: "Cargando...",
        //            timeout: TIME_OUT_AJAX,
        //            scripts: true
        //        });
        Ext.getCmp('content-panel').layout.setActiveItem('panelAyuda');
        Ext.getCmp('idServicioAyuda').reset();
        Ext.getCmp('idServicioAyuda').focus(true, true);
        getServicesHelp();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }

}
function confAdminFacturas() {

    Ext.getCmp('content-panel').layout.setActiveItem('gridADMIN_FACTURAS');
    Ext.getCmp('gridADMIN_FACTURAS').store.load({
        params: {
            start: 0,
            limit: 20
        }
    });

}
function ConfUsuarios() {

    Ext.getCmp('content-panel').layout.setActiveItem('gridUSUARIO');
    Ext.getCmp('gridUSUARIO').store.baseParams['start'] = 0;
    Ext.getCmp('gridUSUARIO').store.baseParams['limit'] = 20;
    Ext.getCmp('gridUSUARIO').store.reload();
}
function ConfTerminal() {

    Ext.getCmp('content-panel').layout.setActiveItem('gridTERMINAL');
    Ext.getCmp('gridTERMINAL').store.baseParams['start'] = 0;
    Ext.getCmp('gridTERMINAL').store.baseParams['limit'] = 20;
    Ext.getCmp('gridTERMINAL').store.reload();

}
function ConfUsuarioTerminal() {

    Ext.getCmp('content-panel').layout.setActiveItem('gridUSUARIO_TERMINAL');
    Ext.getCmp('gridUSUARIO_TERMINAL').store.baseParams['start'] = 0;
    Ext.getCmp('gridUSUARIO_TERMINAL').store.baseParams['limit'] = 20;
    Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();

}
function ConfFranjaHoraria() {

    Ext.getCmp('content-panel').layout.setActiveItem('idPanelFranjaHoraria');
    Ext.getCmp('gridFRANJA_HORARIA_CAB').store.baseParams['start'] = 0;
    Ext.getCmp('gridFRANJA_HORARIA_CAB').store.baseParams['limit'] = 20;
    Ext.getCmp('gridFRANJA_HORARIA_CAB').store.reload();

}
function ConfAdminPermisos() {

    administrarPermisos();

}

function ControlFormulariosBoletas() {




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
}

function cardAltaCuentasACobrar() {
    Ext.getCmp('content-panel').layout.setActiveItem('panelAltaCuentasACobrar');
}

function cardConsultaContribuyentes() {


    Ext.getCmp('content-panel').layout.setActiveItem('panelConsultaContribuyentes');
    Ext.getCmp('idCI').reset();
    Ext.getCmp('idCI').focus(true, true);

    var conn = new Ext.data.Connection();
    conn.request({
        url: 'filtro?op=AGREGAR_CONTRIBUYENTE',
        method: 'POST',
        success: function (accion) {
            var respuesta = Ext.util.JSON.decode(accion.responseText);
            if (!respuesta.success) {
                Ext.getCmp('idBtnAgregar').disable();
            }
        },
        failure: function (accion) {

        }
    })

}

function cardCobroTarjetas() {
    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroTarjetas');
        Ext.getCmp('idFomTarjeta').getForm().reset();
        Ext.getCmp('idCodigoBarraTarjeta').focus(true, true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function ConfSorteadorGestores() {
    Ext.getCmp('content-panel').layout.setActiveItem('gridSORTEADOR_GESTOR');
    Ext.getCmp('gridSORTEADOR_GESTOR').store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
}

shortcut.add("Alt+1", function () {
    cardDigitacionFormulario();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+2", function () {
    cardPagoFormualario();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+3", function () {
    cardPagoBoleta();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+4", function () {
    cardCobroTarjetas();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+5", function () {
    cardCobroServiciosCodigoBarra();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+6", function () {
    cardCobroDesdeArchivo();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+7", function () {
    cardConsultaPago();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+8", function () {
    opTelefonias();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+9", function () {
    cardCobrosMultiples();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+q", function () {
    cardInicial();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+s", function () {
    cardInicial();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});

shortcut.add("Alt+a", function () {
    cardFPJAdelanto()();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
shortcut.add("Alt+t", function () {
    cardFPJTarjeta()();
}, {
    'type': 'keydown',
    'propagate': true,
    'target': document
});
function cardCobrosMultiples() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroMulipleConCB');
        Ext.getCmp('idFormPanelCodigoDeBarrasMul').getForm().reset();
        Ext.getCmp('idGridCobranzaMultiple').getStore().removeAll();
        Ext.getCmp('idMontoPagarMultiples').reset();
        Ext.getCmp('idCodigoBarraMul').focus(true, true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }

}

function cardCobroServiciosCodigoBarra() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroConCB');
        Ext.getCmp('idFormPanelCodigoDeBarras').getForm().reset();
        Ext.getCmp('idCodigoBarra').focus(true, true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardCobroDesdeArchivo() {

    if (GESTION_ABIERTA) {
        cobrarDesdeArchivo();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardConsultaPago() {
    if (GESTION_ABIERTA) {
        workingCyP = false;
        //        Ext.getCmp('panelGrillaServicios').load({
        //            url: 'COBRO_SERVICIOS?op=GET_SERVICIOS&PROPIEDAD_SERVICIO=CONSULTABLE',
        //            params:{
        //
        //            },
        //            discardUrl: false,
        //            nocache: false,
        //            text: "Cargando...",
        //            timeout: TIME_OUT_AJAX,
        //            scripts: true
        //        });
        if (Ext.getCmp('idPanelHeaderCyP') != undefined) {
            Ext.getCmp('idPanelHeaderCyP').destroy();
        }
        Ext.getCmp('idconsultaYPagoFormPanelFac').getForm().reset();
        Ext.getCmp('panelConsultaYPago').findById('idFacturadorCyP').focus(true, true);

        //Ext.getCmp("idServicioCyP").disable();
        Ext.getCmp('content-panel').layout.setActiveItem('panelConsultaYPago');
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
//    if( GESTION_ABIERTA){
//        Ext.getCmp('content-panel').layout.setActiveItem('panelConsultaYPago');
//        Ext.getCmp('idNroReferencia').reset();
//        Ext.getCmp('idServicioCyP').reset();
//        Ext.getCmp('idServicioCyP').focus(true,true);
//
//    }else{
//        Ext.Msg.show({
//            title : 'Estado',
//            msg : MSG_GESTION_CERRADA,
//            buttons : Ext.Msg.OK,
//            icon : Ext.MessageBox.ERROR
//        });
//    }
    /*    var resultado = new Ext.ux.Plugin.RemoteComponent({
     url : 'servicios.jsp',
     params:{
     
     }
     });
     return resultado;*/
}
function opTelefonias() {
    var win = new Ext.Window({
        title: 'Elija una opcion',
        autoWidth: true,
        height: 'auto',
        id: 'idWinOpTelefonias',
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
                html: '<a href=\'#\'  ><img onclick=\'cardRecarga();\' src=\'images/btnRecarga.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                  <a href=\'#\'  ><img onclick=\'cardFacturaXNroCuenta();\' src=\'images/btnFacturaXFac.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                  <a href=\'#\'  ><img onclick=\'cardFacturaXNroTel();\' src=\'images/btnFacturaXTel.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>'
            }]

    });
    return win.show();
}

function cardRecarga() {
    workingTelRC = false;
    Ext.getCmp('idWinOpTelefonias').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelVentaDeMinutos');
    Ext.getCmp('idVentaMinutosFormPanel').getForm().reset();
    Ext.getCmp('idNroTelefonoVM').focus(true, true);

}
function cardFacturaXNroCuenta() {
    workingTel = false;
    Ext.getCmp('idWinOpTelefonias').close();
    if (Ext.getCmp('idPanelHeaderTelefonia') != undefined) {
        Ext.getCmp('idPanelHeaderTelefonia').destroy();
    }
    Ext.getCmp('content-panel').layout.setActiveItem('panelPagoFacturaPorNumeroCuenta');
    Ext.getCmp('panelPagoFacturaPorNumeroCuenta').findById('idServicioPagoXC').focus(true, true);
    Ext.getCmp('idPagoFacXCuentaFormPanel').getForm().reset();
//Ext.getCmp("idServicioPagoXC").focus(true,true);
}
function cardFacturaXNroTel() {
    workingTelXNRO = false;
    Ext.getCmp('idWinOpTelefonias').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelPagoFacXNroTel');
    Ext.getCmp('idPagoFactXNroFormPanel').getForm().reset();
    Ext.getCmp('idNroTelefonoXNro').focus(true, true);

}

function cardTelefonias() {

    if (GESTION_ABIERTA) {

        opTelefonias().center();
        /*if(Ext.getCmp('idPanelHeaderTelefonia')!=undefined){
         Ext.getCmp('idPanelHeaderTelefonia').destroy();
         }
         Ext.getCmp('content-panel').layout.setActiveItem('panelVentaDeMinutos');
         Ext.getCmp('idVentaMinutosFormPanel').getForm().reset();
         Ext.getCmp("idServicioVentaMin").focus(true,true);*/
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardPagoFormualario() {

    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobranzasPagoConFormulario');
        Ext.getCmp('idPagoDeFormularioSet').getForm().reset();
        Ext.getCmp('panelCobranzasPagoConFormulario').findById('idComboReferenciasPagoFormulario').focus(true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}
function cardPagoBoleta() {
    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobranzasPagoConBoletaDePago');
        Ext.getCmp('panelCobranzasPagoConBoletaDePago').findById('idRucBoletaPago').focus(true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardPagoBoletaCyp() {
    if (GESTION_ABIERTA) {
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobranzasPagoConBoletaDePagoCyp');
        Ext.getCmp('panelCobranzasPagoConBoletaDePagoCyp').findById('idRucBoletaPagoCyp').focus(true);
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function cardMulticanal() {
    Ext.getCmp('content-panel').layout.setActiveItem('gridCobranzasMulticanal');
    Ext.getCmp('idBotonBuscarCobrosMulticanal').focus(true);
}
function cardInicial() {
    Ext.getCmp('content-panel').layout.setActiveItem('start-panel');

}

/**********************REPORTES-FUNCIONES CABECERAS****************************/
function getFormPanelReporteSimple(opDelServlet) {
    var numeroOrden = new Ext.form.TextField({
        fieldLabel: 'Número Orden',
        name: 'numeroOrden',
        allowBlank: false
    })
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
    var formPanel = new Ext.FormPanel({
        id: opDelServlet,
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [numeroOrden],
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
                            src: 'reportes?op=' + opDelServlet + '&numeroOrden=' + numeroOrden.getValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
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
        title: 'Estado',
        msg: 'No existe resultado para la consulta.',
        buttons: Ext.Msg.OK,
        icon: Ext.MessageBox.ERROR
    });

}
function getFormPanelReporteCompuestoCobranzas(opDelServlet) {
    var comboFACTURADOR = getCombo('FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR', 'DESCRIPCION_FACTURADOR', 'FACTURADOR', 'FACTURADOR');
    var comboSERVICIO = getCombo('SERVICIO', 'SERVICIO', 'SERVICIO', 'SERVICIO', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'SERVICIO', 'DESCRIPCION_SERVICIO', 'SERVICIO', 'SERVICIO');
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'GESTION', 'REGISTRO_GESTION', 'DESCRIPCION_REGISTRO_GESTION', 'REGISTRO_GESTION', 'GESTIONES...');

    comboFACTURADOR.addListener('select', function (esteCombo, record, index) {
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.FACTURADOR;
        comboSERVICIO.store.reload();
    }, null, null);
    var comboEstadoTransaccion = new Ext.form.ComboBox({
        fieldLabel: 'ESTADO TRANSACCION',
        hiddenName: 'estadoTransaccion',
        valueField: 'estadoTransaccion',
        emptyText: 'ESTADO TRANSACCION...',
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
    comboFACTURADOR.allowBlank = true;
    comboSERVICIO.allowBlank = true;
    comboREGISTRO_GESTION.allowBlank = true;
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

    comboTIPO_CONSULTA.addListener('select', function (esteCombo, record, index) {

        comboREGISTRO_GESTION.store.baseParams['tipo_consulta'] = record.data.TIPO_CONSULTA;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);


    var fechaIni = new Ext.form.DateField({
        fieldLabel: 'FECHA INICIO',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        labelSeparator: ':<font size = 4 color=red> * </font>',
        format: 'dmY'

    });
    var fechaFin = new Ext.form.DateField({
        fieldLabel: 'FECHA FIN',
        height: '22',
        anchor: '95%',
        allowBlank: true,
        format: 'dmY'

    });

    fechaIni.addListener('change', function (esteCampo, nuevoValor, viejoValor) {

        comboREGISTRO_GESTION.store.baseParams['fechaIni'] = esteCampo.getRawValue();
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();
    }, null, null);
    fechaFin.addListener('change', function (esteCampo, nuevoValor, viejoValor) {

        if (fechaIni.getRawValue() != "") {
            comboREGISTRO_GESTION.store.baseParams['fechaFin'] = esteCampo.getRawValue();
            comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
            comboREGISTRO_GESTION.store.baseParams['start'] = 0;
            comboREGISTRO_GESTION.store.reload();
        }

    }, null, null);

    var formPanel = new Ext.FormPanel({
        id: opDelServlet,
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [comboFACTURADOR, comboSERVICIO, comboEstadoTransaccion, fechaIni, fechaFin, comboTIPO_CONSULTA, comboREGISTRO_GESTION, formatoDeDescarga],
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
                            src: 'reportes?op=' + opDelServlet + '&tipoConsulta=' + comboTIPO_CONSULTA.getValue() + '&codigoServicio=' + comboSERVICIO.getValue() + '&idFacturador=' + comboFACTURADOR.getValue() + '&idGestion=' + comboREGISTRO_GESTION.getValue() + '&estadoTransaccion=' + comboEstadoTransaccion.getValue() + '&fechaFin=' + fechaFin.getRawValue() + '&fechaIni=' + fechaIni.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
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
function getFormPanelReporteCierres(opDelServlet) {
    var comboGESTIONES = getCombo('GESTION', 'GESTION', 'GESTION', 'GESTION', 'DESCRIPCION_GESTION', 'GESTIÓN', 'GESTION', 'DESCRIPCION_GESTION', 'GESTION', 'GESTIONES...');
    comboGESTIONES.allowBlank = true
    var fecha = new Ext.form.DateField({
        fieldLabel: 'Fecha',
        height: '22',
        anchor: '95%',
        allowBlank: true,
        format: 'dmY'
    });
    fecha.addListener('valid', function (esteObjeto) {
        comboGESTIONES.store.baseParams['fecha'] = fecha.getRawValue();
    }, null, null);
    var primeraVez = true;
    comboGESTIONES.addListener('focus', function (esteCombo) {
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
    }, null, null)
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
    var formPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fecha, comboGESTIONES, formatoDeDescarga],
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
                            src: 'reportes?op=' + opDelServlet + '&GESTION=' + comboGESTIONES.getValue() + '&fechaIni=' + fecha.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
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
function getFormPanelReporteTapalotes(opDelServlet) {
    var comboGRUPOS_USUARIO_FECHA = getCombo('GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'DESCRIPCION_GRUPOS_USUARIO_FECHA', 'GRUPO', 'GRUPOS_USUARIO_FECHA', 'DESCRIPCION_GRUPOS_USUARIO_FECHA', 'GRUPOS_USUARIO_FECHA', 'GRUPOS...');
    comboGRUPOS_USUARIO_FECHA.allowBlank = true
    var fecha = new Ext.form.DateField({
        fieldLabel: 'Fecha',
        height: '22',
        anchor: '95%',
        allowBlank: false,
        format: 'dmY'
    });
    fecha.addListener('valid', function (esteObjeto) {
        comboGRUPOS_USUARIO_FECHA.store.baseParams['fecha'] = fecha.getRawValue();


    }, null, null);
    var primeraVez = true;
    comboGRUPOS_USUARIO_FECHA.addListener('focus', function (esteCombo) {
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
    }, null, null)
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
    var formPanel = new Ext.FormPanel({
        labelWidth: 150,
        frame: true,
        autoWidth: true,
        defaultType: 'textfield',
        monitorValid: true,
        items: [fecha, comboGRUPOS_USUARIO_FECHA, formatoDeDescarga],
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
                            src: 'reportes?op=' + opDelServlet + '&GRUPO=' + comboGRUPOS_USUARIO_FECHA.getValue() + '&fechaIni=' + fecha.getRawValue() + '&formatoDescarga=' + formatoDeDescarga.getValue()
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