var TIME_OUT_AJAX= 600000;
TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID='Atención';
CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID='¿Está seguro que desea eliminar el registro seleccionado?';

FAIL_TITULO_GENERAL='Atención';
FAIL_CUERPO_GENERAL='No se pudo realizar la operación.';

TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID='Confirmación';
CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID='¿Está seguro que desea modificar el registro seleccionado?';

TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID='Confirmación';
CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID='¿Está seguro que desea agregar el registro?';

WAIT_TITLE_MODIFICANDO='Conectando';
WAIT_MSG_MODIFICANDO='Modificando los datos del registro seleccionado...';

WAIT_TITLE_AGREGANDO='Conectando';
WAIT_MSG_AGREGANDO='Agregando los datos...';

TITULO_NO_EXISTE_REPORTE ='Estado';
CUERPO_NO_EXISTE_REPORTE ='No existe resultado para la consulta';

TOOL_TIP_AGREGAR = 'Agregar registro';
TOOL_TIP_QUITAR = 'Quitar registro';

TITULO_ACTUALIZACION_EXITOSA = 'Info';
CUERPO_ACTUALIZACION_EXITOSA = 'Actualización Exitosa';

TITULO_EXITOSO = 'Info';
CUERPO_EXITOSO = 'Operación Exitosa';

function pantallaCapturadorArchivos() {
    var combo1 =   new Ext.form.ComboBox({
        fieldLabel: 'Param 1',
        hiddenName : 'combo1',
        valueField : 'value',
        emptyText: 'Seleccionar uno...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data : [['FORMULARIO', 'FORMULARIO'],
            ['CAMPO',  'CAMPO'],
            ['FORM_IMP', 'FORMULARIO IMPUESTO'],
            ['TASAS_FORM', 'TASAS FORMULARIO'],
            ['IMPUESTO', 'IMPUESTO'],
            ['VENCIMIENTOS', 'VENCIMIENTOS'],
             ['CONTRIBUYENTE', 'CONTRIBUYENTE'],
            ['PARAMETROS', 'PARAMETROS']]
        })
    });
    var combo2 =   new Ext.form.ComboBox({
        fieldLabel: 'Formato Fecha',
        hiddenName : 'combo2',
        valueField : 'value',
        emptyText: 'Seleccionar uno..',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data : [['DD/MM/YYYY', 'DD/MM/YYYY'],
            ['MM/DD/YYYY',  'MM/DD/YYYY']]
        })
    });
    var combo3 =   new Ext.form.ComboBox({
        fieldLabel: 'Separador Campos',
        hiddenName : 'combo3',
        valueField : 'value',
        emptyText: 'Seleccionar uno...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data : [['TAB', 'TABULADOR'],
            ['PUNTO_COMA',  'PUNTO Y COMA'],['COMA',  'COMA']]
        })
    });
    var win = new Ext.Window({
        width : 450,
        id : 'idWinImportar',
        height : 220,
        layout : 'fit',
        border : false,
        modal:true,
        resizable : false,
        frame : true,
        title : 'Procesar Formularios',
        iconCls : 'add',
        closable : true,
        items : [{
            labelWidth : 120,
            monitorValid : true,
            xtype : 'form',
            id : 'formid',
            defaultType : 'textfield',
            frame : true,
            method : 'post',
            fileUpload : true,
            items : [{
                fieldLabel : 'Upload',
                id : 'uploadedfile',
                anchor : '95%',
                xtype : 'textfield',
                inputType : 'file',
                allowBlank: false
            },combo1,combo2,combo3],
            buttons : [{
                formBind : true,
                text : 'Procesar Formularios',
                handler : function(btn) {
                    Ext.getCmp('formid').getForm().submit({
                        url : 'LECTOR_ARCHIVOS?combo1='+combo1.getValue()+"&combo2="+combo2.getValue()+"&combo3="+combo3.getValue(),
                        success : function(form, result) {
                            // console.info(Ext.decode(result.response.responseText));
                            //console.log(Ext.decode(result.response.responseText));
                            var obj = Ext.util.JSON.decode(result.response.responseText);
                            Ext.Msg.show({
                                title : 'Info',
                                msg : 'El archivo ha sido procesado correctamente!',
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO
                            });
                        },
                        failure : function() {
                            Ext.Msg.alert('Atención!!',
                                'No se puede levantar el archivo.');
                        }
                    });
                }
            }]
        }]
    });
    win.show();
}
function pantallaMulticanalCargarArchivoFacturacion() {

    var win = new Ext.Window({
        width : 450,
        //id : 'idWinImportarArchivoFacturacion',
        height : 110,
        layout : 'fit',
        border : false,
        modal:true,
        resizable : false,
        frame : true,
        title : 'Multicanal - Levantar Archivo Facturación ',
        iconCls : 'add',
        closable : true,
        items : [{
            labelWidth : 120,
            monitorValid : true,
            xtype : 'form',
          //  id : 'formid',
            defaultType : 'textfield',
            frame : true,
            method : 'post',
            fileUpload : true,
            items : [{
                fieldLabel : 'Upload',
                id : 'uploadedfile',
                anchor : '95%',
                xtype : 'textfield',
                inputType : 'file',
                allowBlank: false
            }],
            buttons : [{
                formBind : true,
                text : 'Levantar',
                handler : function(btn) {
                    Ext.getCmp('formid').getForm().submit({
                        url : 'MULTICANAL?OP=LEVANTAR_ARCHIVO_FACTURACION',
                        success : function(form, result) {                           
                            var obj = Ext.util.JSON.decode(result.response.responseText);
                            Ext.Msg.show({
                                title : 'Info',
                                msg : 'El archivo ha sido procesado correctamente!',
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO
                            });
                        },
                        failure : function() {
                            Ext.Msg.alert('Atención!!',
                                'No se puede levantar el archivo.');
                        }
                    });
                }
            }]
        }] 
    });
    win.show();
}
function lanzarReporte(reporte){
 
    if(reporte == 'idPanelREPORTE_DECLARACION_POR_NUMERO_ORDEN'){
        pantallaREPORTE_DECLARACION_POR_NUMERO_ORDEN();
    }else if(reporte == 'idPanelREPORTE_TAPA_CAJA'){
        pantallaREPORTE_COBRANZA_TAPA_CAJA();
    }else if(reporte == 'idPanelREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN'){
        pantallaREPORTE_BOLETA_PAGO_POR_NUMERO_ORDEN();
    }
    else if(reporte == 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO'){
        pantallaREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO();
    }
    else if(reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO'){
        pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE_EFECTIVO();
    }
    else if(reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_CHEQUE'){
        pantallaREPORTE_COBRANZA_DETALLADO_CHEQUE();
    }
    else if(reporte == 'idPanelREPORTE_COBRANZA_DETALLADO_EFECTIVO'){
        pantallaREPORTE_COBRANZA_DETALLADO_EFECTIVO();
    }else if(reporte == 'idPanelREPORTE_FORMULARIO_OT'){
        pantallaREPORTE_FORMULARIO_OT();
    }else if(reporte == 'idPanelRESUMEN_CLEARING_COMISION'){
        pantallaRESUMEN_CLEARING_COMISION();
    }
    else if(reporte == 'idPanelREPORTE_TAPALOTE'){
        pantallaREPORTE_TAPALOTE();
    }
    else if(reporte == 'idPanelREPORTE_TAPALOTE_DETALLADO'){
        pantallaREPORTE_TAPALOTE_DETALLADO();
    }
    else if(reporte == 'idPanelREPORTE_CIERRE_RESUMIDO'){
        pantallaREPORTE_CIERRE_RESUMIDO();
    }
    else if(reporte == 'idPanelREPORTE_CIERRE_DETALLADO'){
        pantallaREPORTE_CIERRE_DETALLADO();
    }
    else if(reporte == 'idPanelREPORTE_RESUMEN_DE_TRANSACCIONES'){
        pantallaREPORTE_RESUMEN_DE_TRANSACCIONES().center();
    }
    else if(reporte == 'idPanelREPORTE_COMISION_CS'){
        pantallaREPORTE_COMISION_CS().center();
    }
    else if(reporte == 'idPanelREPORTE_MOVIMIENTO_FACTURADORES'){
        pantallaREPORTE_MOVIMIENTO_FACTURADORES().center();
    }
    else if(reporte == 'idPanelREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS'){
        pantallaREPORTE_COBRANZA_CHEQUE_EFECTIVO_CS().center();
    }
    else if(reporte == 'idPanelREPORTE_CIERRE_CS'){
        pantallaREPORTE_CIERRE_CS().center();
    }
    else if(reporte == 'idPanelREPORTE_CIERRE_USUARIO'){
        pantallaREPORTE_CIERRE_USUARIO();
    }
    else if(reporte == 'idPanelREPORTE_TERMINALES_ABIERTAS'){
        pantallaREPORTE_TERMINALES_ABIERTAS();
    }
    else if(reporte == 'idPanelREPORTE_RESUMEN_FACTURADOR'){
        pantallaREPORTE_RESUMEN_FACTURADOR().center();
    }
    else if(reporte == 'idPanelREPORTE_CLEARING_CS'){
        pantallaREPORTE_CLEARING_CS().center();
    }else if(reporte == 'idPanelREPORTE_SAN_CRISTOBAL'){
        pantallaREPORTE_SAN_CRISTOBAL().center();
        
    }else if(reporte == 'idPanelREPORTE_FACTURACION_ADMINISTRACION'){
        pantallaREPORTE_FACTURACION_ADMINISTRACION().center();
        
    }else if(reporte == 'idPanelREPORTE_COMISION_DET_REC'){
        pantallaREPORTE_COMISION('REC').center();
        
    }else if(reporte == 'idPanelREPORTE_COMISION_DET_FAC'){
        pantallaREPORTE_COMISION('FAC').center();
        
    }else if(reporte == 'idPanelREPORTE_COMISION_RES_GRAL_REC'){
        pantallaREPORTE_COMISION_RESUMIDO_REC().center();
        
    }else if(reporte == 'idPanelREPORTE_COMISION_RES_FAC'){
        pantallaREPORTE_FACTURACION_ADMINISTRACION().center();
        
    }else if(reporte == 'SOLICITUD_TRANSACCION'){
        gridSelectTransacc().center();
        
    }else if(reporte == 'REPORTE_DIGITACION'){
        pantallaREPORTE_DIGITACION().center();
        
    }else if(reporte == 'REPORTE_DESEMBOLSO'){
        pantallaREPORTE_DESEMBOLSO().center();
    }

}
function autorizarOpcion(reporteAutorizado){
    var buttonOK = new Ext.Button({

        text : 'OK',
        formBind : true,
        handler : function() {
            formPanel.getForm().submit({
                url : 'reportes?op=AUTORIZAR_REPORTE',
                method : 'POST',
                success : function(form,action) {
                    win.close();
                    var obj = Ext.util.JSON .decode(action.response.responseText);
                    if(obj.success){
                        
                        lanzarReporte(reporteAutorizado);
                     
                    }else{
                        Ext.Msg.show({
                            title : 'Atención',
                            msg : 'No esta autorizado a realizar la operación.',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });
                    }
                },
                failure : function(form,action) {
                    var obj = Ext.util.JSON .decode(action.response.responseText);
                    if(obj.motivo!= null || obj.motivo!= undefined ){

                        Ext.Msg.show({
                            title : 'Atención',
                            msg : obj.motivo,
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });

                    }
                    else{
                        Ext.Msg.show({
                            title : 'Atención',
                            msg : 'No esta autorizado para realizar la operación.',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });

                    }
                }
            });
        }
    });



    var formPanel = new Ext.FormPanel({
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ {
            fieldLabel:'PASSWORD',
            name:'PASSWORD',
            inputType:'password',
            enableKeyEvents:true,
            allowBlank : false,
            listeners : {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13){
                        buttonOK.focus(true,true);
                    }

                }
            }
        } ],


        buttons : [buttonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Autorización',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();

}
function replaceAllNoNumbers3(sValue){
//Se permite  , y . entre los numeros
    sValue=  sValue.replace( /[^,.0-9]/g, "");
    return sValue;
}
function replaceAllNoNumbers(sValue){
//Se permite la coma entre los numeros
    sValue=  sValue.replace( /[^,0-9]/g, "");
    return sValue;
}
function replaceAllNoNumbers2(sValue){
    //No se permite . entre los numeros
    sValue=  sValue.replace( /[^.0-9]/g, "");
    return sValue;
}
function addCommas( sValue ){
    
    sValue=  sValue.replace( /,/g, "" );
    var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})');
    while(sRegExp.test(sValue)) {
        sValue = sValue.replace(sRegExp, '$1,$2');
    }
    return sValue;
}
function getCombo(suUrl,suRootJson,suIdJson,suNameJson,suDescripcion,suLabel,suHidden,suDisplayField,suValueField,suEmptyText){

    var ds_providers = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'combos?op='+suUrl
        }),
        //        callback: function(records, options, success){
        //            if(!success){
        //                alert("error")
        //            }else{
        //                alert("TODO BIEN")
        //
        //            }
        //        }
        //        ,
        reader : new Ext.data.JsonReader({
            root : suRootJson,
            id : suIdJson,
            totalProperty : 'TOTAL'
        }, [{
            name : suNameJson
        }, {
            name : suDescripcion
        }])

    });

    //    ds_providers.load({
    //        params : {
    //            start : 0,
    //            limit : 25
    //        }
    //    });
    var combo = new Ext.form.ComboBox({

        fieldLabel : suLabel,
        hiddenName : suHidden,
        store : ds_providers,
        displayField : suDisplayField,
        valueField : suValueField,
        triggerAction : 'all',
        emptyText : suEmptyText,
        selectOnFocus : true,
        anchor : '95%',
        pageSize : 10,
        listWidth : 250,
        forceSelection : true,
        loadingText : 'Cargando...',
        typeAhead : true,
        minChars :2
    });
    return combo;
}

function imprimirImpresoraExterna(cadenaImpresion){

    var URL_IMPRESORA = "http://localhost:12345";


    if(URL_IMPRESORA !=""){
        var dsPrinter = new Ext.data.Store({
            proxy: new Ext.data.ScriptTagProxy({

                url : ""+URL_IMPRESORA+'/?printer='+cadenaImpresion
            })
        });
        dsPrinter.load({});
    }
}
