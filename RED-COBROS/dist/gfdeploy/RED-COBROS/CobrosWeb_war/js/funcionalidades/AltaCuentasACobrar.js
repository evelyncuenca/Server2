/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function panelAltaCuentasACobrar() {
    var panel = new Ext.Panel({
        id : 'panelAltaCuentasACobrar',
        frame:true,
        defaults:{
            autoScroll: true
        },
        items : [cabeceraUploadFile(),gridAltaCuentas()]
    });

    return panel;

}
function cabeceraUploadFile(){
    var separador =   new Ext.form.ComboBox({
        fieldLabel: 'Separador Campos',
        hiddenName : 'SEPARADOR',
        valueField : 'value',
        emptyText: 'Seleccionar uno...',
        anchor:'20%',
        width:100,
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['value', 'descripcion'],
            data : [['PIPE', 'PIPE'],
            ['PUNTO_COMA',  'PUNTO Y COMA']]
        })
    });
    var fp = new Ext.form.FormPanel({
        // width: 500,
        frame: true,
        title: 'Alta de cuentas a cobrar',
        autoHeight: true,
        labelAlign:'top',
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        buttonAlign:'left',
        monitorValid:true,
        method : 'post',
        fileUpload : true,
        defaults: {
            allowBlank: false
        //msgTarget: 'side'
        },
        items: [{
            fieldLabel : 'Archivo de cuentas',
            id : 'idUploadCuentasACobrar',
            anchor : '100%',
            xtype : 'textfield',
            inputType : 'file',
            allowBlank: false
        },separador],
        buttons: [{
            text: 'Upload',
            formBind : true,
            handler: function(){
                if(fp.getForm().isValid()){
                    fp.getForm().submit({
                        url: 'CUENTAS_A_COBRAR?op=SAVE_FILE'+'&SEPARADOR='+separador.getValue(),
                        waitMsg: 'Enviando archivo..',
                        success : function(result, accion) {
                            var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                            if (obj2.success) {
                                Ext.Msg.show({
                                    title : "Info",
                                    msg : "El archivo fue procesado con éxito",
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.INFO
                                });
                            }
                        },
                        failure : function(form, action) {
                            var obj = Ext.util.JSON.decode(action.response.responseText);
                            if(obj.motivo != undefined ){
                                var win = new Ext.Window({
                                    title : 'Reporte de Errores',
                                    id : 'idWinErrorFile',
                                    width : 250,
                                    height : 250,
                                    closable : true,
                                    autoScroll:true,
                                    resizable : false,
                                    items : [{
                                        html : obj.motivo+"<br></br>"
                                    }],
                                    buttons : [{
                                        id:'idBtnOkWinFile',
                                        text : 'Ok',
                                        handler: function(){
                                            win.close();
                                        }
                                    }]
                                });
                                win.show();
                            }else{
                                Ext.Msg.show({
                                    title : FAIL_TITULO_GENERAL,
                                    msg : FAIL_CUERPO_GENERAL,
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.ERROR
                                });
                            }
                        }
                    });
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                fp.getForm().reset();
            }
        }]
    });
    return fp;
}
function gridAltaCuentas(){
    var comboSERVICIO_FACTURADOR =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    //    comboTIPO_IDENTIFICADOR.listWidth = 100;
    comboSERVICIO_FACTURADOR.allowBlank= false;
    var comboANULABLE=  new Ext.form.ComboBox({
        hiddenName : 'ANULABLE',
        valueField : 'TIPO',
        id  :'idAnulableFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,        
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    var comboMONEDA=  new Ext.form.ComboBox({
        hiddenName : 'MONEDA',
        valueField : 'TIPO',
        id  :'idMonedaFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['GS', 'GUARANIES'],
            ['USD', 'DOLARES']]

        })
    });
    var comboTIPO_RECARGO =  new Ext.form.ComboBox({
        hiddenName : 'TIPO_RECARGO',
        valueField : 'TIPO',
        id  :'idTipoRecargoFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['NINGUNO', 'NINGUNO'],
            ['MONTO', 'MONTO'],
            ['PORCENTAJE', 'PORCENTAJE']]

        })
    });
    var comboCOBRAR_VENCIDO=  new Ext.form.ComboBox({
        hiddenName : 'COBRAR_VENCIDO',
        valueField : 'TIPO',
        id  :'idCobrarVencidoFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idSoloEfectivoFacturas').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    var comboPAGAR_MAS_VENCIDO=  new Ext.form.ComboBox({
        hiddenName : 'PAGAR_MAS_VENCIDO',
        valueField : 'TIPO',
        id  :'idPagarMasVencidaFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : true,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idAnulableFacturas').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    var comboSOLO_EFECTIVO=  new Ext.form.ComboBox({
        hiddenName : 'SOLO_EFECTIVO',
        valueField : 'TIPO',
        id  :'idSoloEfectivoFacturas',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idPagarMasVencidaFacturas').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    var fm = Ext.form;
    var cm = new Ext.grid.ColumnModel([{
        header: 'Servicio',
        dataIndex: 'SERVICIO',
        width: 100,
        editor: comboSERVICIO_FACTURADOR
    },{
        header: 'Ref. Pago',
        dataIndex: 'REFERENCIA_PAGO',
        width: 100,
        editor: new fm.TextField({
            allowBlank: false
        })
    },{
        header: 'Ref. Búsqueda',
        dataIndex: 'REFERENCIA_BUSQUEDA',
        width: 100,
        editor: new fm.TextField({
            allowBlank: false
        })
    },{
        header: 'Desc. Ref. Pago',
        dataIndex: 'DESCRIPCION_REF_PAGO',
        width: 100,
        editor: new fm.TextField({
            allowBlank: true
        })
    },{
        header: 'Moneda',
        dataIndex: 'MONEDA',
        width: 100,
        editor: comboMONEDA
    },{
        header: 'Monto',
        dataIndex: 'MONTO',
        width: 100,
        editor: new fm.TextField({
            allowBlank: false
        })
    },{
        header: 'Monto Vencido',
        dataIndex: 'MONTO_VENCIDO',
        width: 150,
        editor: new fm.TextField({
            allowBlank: true
        })
    },{
        header: 'Interes Moratorio',
        dataIndex: 'INT_MORATORIO',
        width: 100,
        editor: new fm.TextField({
            allowBlank: true
        })
    },{
        header: 'Fech. Vencimiento',
        dataIndex: 'VENCIMIENTO',
        width: 100,
        renderer: Ext.util.Format.dateRenderer('d/m/Y'),
        editor: new fm.DateField({
            format:'d/m/Y',
            allowBlank: true
        })
    },{
        header: 'Mensaje',
        dataIndex: 'MENSAJE',
        width: 100,
        editor: new fm.TextField({
            allowBlank: true
        })
    },{
        header: 'Tipo Recargo',
        dataIndex: 'TIPO_RECARGO',
        width: 100,
        editor: comboTIPO_RECARGO
    },{
        header: 'Cobrar Vencido',
        dataIndex: 'COBRAR_VENCIDO',
        width: 100,
        editor: comboCOBRAR_VENCIDO
    },{
        header: 'Solo Efectivo',
        dataIndex: 'SOLO_EFECTIVO',
        width: 100,
        editor: comboSOLO_EFECTIVO
    },{
        header: 'Pagar más vencida',
        dataIndex: 'PAGAR_MAS_VENCIDA',
        width: 100,
        editor:comboPAGAR_MAS_VENCIDO
    },{
        header: 'Anulable',
        dataIndex: 'ANULABLE',
        width: 100,
        editor:comboANULABLE
    }
    ]);

    cm.defaultSortable = true;
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CUENTAS_A_COBRAR?op='
        }),
        reader : new Ext.data.JsonReader({
            root : 'DETALLE',
            totalProperty : 'TOTAL',
            id : 'SERVICIO',
            fields : ['REFERENCIA_PAGO','REFERENCIA_BUSQUEDA','DESCRIPCION_REF_PAGO','MONEDA','MONTO','MONTO_VENCIDO','INT_MORATORIO','VENCIMIENTO','MENSAJE','COBRAR_VENCIDO','TIPO_RECARGO','SOLO_EFECTIVO','PAGAR_MAS_VENCIDA','ANULABLE','SERVICIO']
        })
    });


    var grid = new Ext.grid.EditorGridPanel({
        id:'gridAltaCuentasACobrar',
        store: st,
        autoScroll:true,
        cm: cm,
        width: 'auto',
        height: 400,
        // autoExpandColumn: 'common',
        frame: true,
        clicksToEdit: 1,
        tbar: [{
            text: 'Agregar',
            iconCls:'add',
            handler : function(){
                var Plant = grid.getStore().recordType;
                var p = new Plant({
                    common: 'New Plant 1',
                    light: 'Mostly Shade',
                    price: 0,
                    availDate: (new Date()).clearTime(),
                    indoor: false
                });
                grid.stopEditing();
                st.insert(0, p);
                grid.startEditing(0, 0);
            }
        },{
            text: 'Borrar',
            iconCls:'remove',
            handler : function(){
                var index = grid.getSelectionModel().getSelectedCell();
                if (!index) {
                    return false;
                }
                var rec = grid.store.getAt(index[0]);
                grid.store.remove(rec);
            }
        },{
            text: 'Guardar',
            iconCls:'guardar',
            handler : function(){
                var arrayRefPago= "";
                var arrayRefBusqueda= "";
                var arrayDescRefPago="";
                var arrayMoneda="";
                var arrayMonto="";
                var arrayMontoVencido="";
                var arrayIntMoratorio="";
                var arrayVencimiento="";
                var arrayMensaje="";
                var arrayCobrarVencido="";
                var arrayTipoRecargo="";
                var arraySoloEfectivo="";
                var arrayAnulable="";
                var arrayPagarMasVencida="";
                var arrayServicio="";

                Ext.each(
                    grid.getStore().data.items,
                    function(row) {
                        arrayRefPago += ";"+row.data.REFERENCIA_PAGO;
                        arrayRefBusqueda += ";"+row.data.REFERENCIA_BUSQUEDA;
                        arrayDescRefPago +=";"+row.data.DESCRIPCION_REF_PAGO;
                        arrayMensaje +=";"+row.data.MENSAJE;
                        arrayMoneda+=";"+row.data.MONEDA;
                        arrayMonto+=";"+row.data.MONTO;
                        arrayMontoVencido+=";"+row.data.MONTO_VENCIDO;
                        arrayVencimiento+=";"+new Date(row.data.VENCIMIENTO).format('d/m/Y');
                        arrayIntMoratorio+=";"+row.data.INT_MORATORIO;
                        arrayCobrarVencido+=";"+row.data.COBRAR_VENCIDO;
                        arrayTipoRecargo+=";"+row.data.TIPO_RECARGO;
                        arraySoloEfectivo+=";"+row.data.SOLO_EFECTIVO;
                        arrayAnulable+=";"+row.data.ANULABLE;
                        arrayPagarMasVencida+=";"+row.data.PAGAR_MAS_VENCIDA;
                        arrayServicio+=";"+row.data.SERVICIO;

                    });
                var conn = new Ext.data.Connection();
                conn.request({
                    url : 'CUENTAS_A_COBRAR?op=GUARDAR',
                    waitMsg: 'Guardando..',
                    method : 'POST',
                    timeout : TIME_OUT_AJAX,
                    params : {
                        REFERENCIA_PAGO: arrayRefPago,
                        REFERENCIA_BUSQUEDA: arrayRefBusqueda,
                        DESCRIPCION_REF_PAGO: arrayDescRefPago,
                        MONEDA:arrayMoneda,
                        MONTO:arrayMonto,
                        MONTO_VENCIDO:arrayMontoVencido,
                        INT_MORATORIO:arrayIntMoratorio,
                        VENCIMIENTO:arrayVencimiento,
                        COBRAR_VENCIDO:arrayCobrarVencido,
                        TIPO_RECARGO:arrayTipoRecargo,
                        SOLO_EFECTIVO:arraySoloEfectivo,
                        ANULABLE:arrayAnulable,
                        PAGAR_MAS_VENCIDA:arrayPagarMasVencida,
                        SERVICIO:arrayServicio,
                        MENSAJE:arrayMensaje

                    },
                    success : function(action) {
                        var obj = Ext.util.JSON .decode(action.responseText);
                        if(obj.success){
                            Ext.Msg.show({
                                title :TITULO_ACTUALIZACION_EXITOSA,
                                msg : 'El registro se guardo correctamente.',
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO
                            });
                        //Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                        }else{
                            Ext.Msg.show({
                                title : 'Estado',
                                msg : obj.motivo,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });
                        }
                    },
                    failure : function(action) {
                        Ext.Msg.show({
                            title : 'Estado',
                            msg : 'No se pudo realizar la operación.',
                            buttons : Ext.Msg.OK,
                            animEl : 'elId',
                            icon : Ext.MessageBox.ERROR
                        });
                    }
                });
            }
        },{
            text: 'Reset',
            iconCls:'resetBtn',
            handler : function(){              
                grid.store.removeAll();
                grid.getView().refresh();
            }
        }]
    });
    return grid;
}