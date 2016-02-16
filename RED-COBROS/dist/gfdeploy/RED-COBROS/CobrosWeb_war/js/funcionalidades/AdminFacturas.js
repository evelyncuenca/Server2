/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var idSERVICIOSeleccionado;
var idFACTURADORSeleccionado;
var idREFERENCIA_PAGOSeleccionado;
function gridADMIN_FACTURAS(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CUENTAS_A_COBRAR?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'FACTURAS',
            totalProperty : 'TOTAL',
            //id : 'REFERENCIA_PAGO',
            fields : ['PAGADO','FACTURADOR','REFERENCIA_PAGO','REFERENCIA_BUSQUEDA','DESCRIPCION_REF_PAGO','MONEDA','MONTO','MONTO_VENCIDO','INT_MORATORIO','VENCIMIENTO','MENSAJE','COBRAR_VENCIDO','TIPO_RECARGO','SOLO_EFECTIVO','PAGAR_MAS_VENCIDA','SERVICIO','ANULABLE']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 100;
    var colModel = new Ext.grid.ColumnModel([{
        header: 'Pagado',
        dataIndex: 'PAGADO',
        width: anchoDefaultColumnas
    },{
        header: 'Servicio',
        dataIndex: 'SERVICIO',
        width: anchoDefaultColumnas
    },{
        header: 'Ref. Pago',
        dataIndex: 'REFERENCIA_PAGO',
        width: anchoDefaultColumnas
    },{
        header: 'Ref. Búsqueda',
        dataIndex: 'REFERENCIA_BUSQUEDA',
        width: anchoDefaultColumnas
    },{
        header: 'Desc. Ref. Pago',
        dataIndex: 'DESCRIPCION_REF_PAGO',
        width: anchoDefaultColumnas
    },{
        header: 'Moneda',
        dataIndex: 'MONEDA',
        width: anchoDefaultColumnas
    },{
        header: 'Monto',
        dataIndex: 'MONTO',
        width: anchoDefaultColumnas
    },{
        header: 'Monto Vencido',
        dataIndex: 'MONTO_VENCIDO',
        width: 150
    },{
        header: 'Interes Moratorio',
        dataIndex: 'INT_MORATORIO',
        width: anchoDefaultColumnas
    },{
        header: 'Fech. Vencimiento',
        dataIndex: 'VENCIMIENTO',
        width: anchoDefaultColumnas//,
    // renderer: Ext.util.Format.dateRenderer('dmY')
    },{
        header: 'Mensaje',
        dataIndex: 'MENSAJE',
        width: anchoDefaultColumnas
    },{
        header: 'Cobrar Vencido',
        dataIndex: 'COBRAR_VENCIDO',
        width: anchoDefaultColumnas
    },{
        header: 'Tipo Recargo',
        dataIndex: 'TIPO_RECARGO',
        width: anchoDefaultColumnas
    },{
        header: 'Solo Efectivo',
        dataIndex: 'SOLO_EFECTIVO',
        width: anchoDefaultColumnas
    },{
        header: 'Pagar más vencida',
        dataIndex: 'PAGAR_MAS_VENCIDA',
        width: anchoDefaultColumnas
    },{
        header: 'Anulable',
        dataIndex: 'ANULABLE',
        width: anchoDefaultColumnas
    }
    ]);
    var gridADMIN_FACTURAS = new Ext.grid.GridPanel({
        title:'Administración De Facturas',
        id:'gridADMIN_FACTURAS',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:false
        },
        tbar:[{
            text:'Agregar',
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarFACTURA') == undefined) pantallaAgregarFACTURAS().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idSERVICIOSeleccionado!=undefined && idFACTURADORSeleccionado!= undefined && idREFERENCIA_PAGOSeleccionado!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();
                                conn.request({
                                    url : 'CUENTAS_A_COBRAR?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        SERVICIO : idSERVICIOSeleccionado,
                                        FACTURADOR :idFACTURADORSeleccionado,
                                        REFERENCIA_PAGO:idREFERENCIA_PAGOSeleccionado
                                    },
                                    success : function(action) {
                                        var  obj = Ext.util.JSON .decode(action.responseText);
                                        if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('gridADMIN_FACTURAS').store.reload();
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
                                            title :FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.ERROR,
                                            animEl : 'elId',
                                            icon : Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        },{
            text:'Facturas',
            id:'idBusquedaFacturas',
            xtype:'textfield',
            emptyText:'Facturas..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridADMIN_FACTURAS').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        },/*{
            text:'Reporte',
            tooltip:'Reporte',
            iconCls:'reporte',
            handler:function(){
                pantallaReporteFacturas().center();
            }
        },*/{
            text:'Salir',
            tooltip:'Salir',
            iconCls:'logout',
            handler:function(){
                cardInicial();
            }
        }],
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : st,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idSERVICIOSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.SERVICIO;
                idREFERENCIA_PAGOSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.REFERENCIA_PAGO;
                idFACTURADORSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.FACTURADOR;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarFACTURAS") == undefined)) {
                    idSERVICIOSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.SERVICIO;
                    idREFERENCIA_PAGOSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.REFERENCIA_PAGO;
                    idFACTURADORSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.FACTURADOR;
                    pantallaModificarFACTURAS().show().center();
                }
            }
        }
    });
    return gridADMIN_FACTURAS;
}
function pantallaModificarFACTURAS() {
    var comboSERVICIO_FACTURADOR =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    comboSERVICIO_FACTURADOR.allowBlank= false;
    comboSERVICIO_FACTURADOR.disable();
    var comboANULABLE=  new Ext.form.ComboBox({
        fieldLabel : 'Anulable',
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
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboMONEDA=  new Ext.form.ComboBox({
        fieldLabel : 'Moneda',
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
            data : [['GS', 'GS'],
            ['USD', 'USD']]

        })
    });
    var comboTIPO_RECARGO =  new Ext.form.ComboBox({
        fieldLabel : 'Tipo Recargo',
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
        fieldLabel : 'Cobrar Vencido',
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
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboPAGAR_MAS_VENCIDO=  new Ext.form.ComboBox({
        fieldLabel : 'Pagar Más Vencido',
        hiddenName : 'PAGAR_MAS_VENCIDA',
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
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboSOLO_EFECTIVO=  new Ext.form.ComboBox({
        fieldLabel : 'Sólo Efectivo',
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
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var fechaVencimiento = new Ext.form.DateField({
        fieldLabel : 'Fecha Vencimiento',
        id:'idFechaVencimientoFac',
        name : 'VENCIMIENTO',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idMensajeFac').focus(true,true);
                }
            }
        }
    });

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Factura',
            defaultType: 'textfield',
            items: [comboSERVICIO_FACTURADOR,{
                id:'idRefBusquedaFac',
                xtype:'textfield',
                name: 'REFERENCIA_BUSQUEDA',
                anchor:'95%',
                fieldLabel: 'Ref. Búsqueda',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idRefPagoFac').focus(true,true);
                        }
                    }
                }
            },{
                id:'idRefPagoFac',
                xtype:'textfield',
                name: 'REFERENCIA_PAGO',
                anchor:'95%',
                fieldLabel: 'Ref. Pago',
                allowBlank:false,
                disabled:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDescripRefPagoFac').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDescripRefPagoFac',
                xtype:'textfield',
                name: 'DESCRIPCION_REF_PAGO',
                anchor:'95%',
                fieldLabel: 'Desc. Ref. Pago',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboMONEDA.focus(true,true);
                        }
                    }
                }
            },comboMONEDA,{
                id:'idMontoFac',
                xtype:'textfield',
                name: 'MONTO',
                anchor:'95%',
                fieldLabel: 'Monto',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idMontoVencFac').focus(true,true);
                        }
                    }
                }
            },{
                id:'idMontoVencFac',
                xtype:'textfield',
                name: 'MONTO_VENCIDO',
                anchor:'95%',
                fieldLabel: 'Monto Vencido',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idIntMoratorioFac').focus(true,true);
                        }
                    }
                }
            },{
                id:'idIntMoratorioFac',
                xtype:'textfield',
                name: 'INT_MORATORIO',
                anchor:'95%',
                fieldLabel: 'Int. Moratorio',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            fechaVencimiento.focus(true,true);
                        }
                    }
                }
            },fechaVencimiento,{
                id:'idMensajeFac',
                xtype:'textfield',
                name: 'MENSAJE',
                anchor:'95%',
                fieldLabel: 'Mensaje',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboCOBRAR_VENCIDO.focus(true,true);
                        }
                    }
                }
            }]
        }
    }, {
        bodyStyle: 'padding-left:10px;',
        items: [{
            xtype: 'fieldset',
            title: 'Configuración de Cobro',
            defaultType: 'textfield',
            items: [comboCOBRAR_VENCIDO, comboTIPO_RECARGO, comboPAGAR_MAS_VENCIDO, comboSOLO_EFECTIVO, comboANULABLE]
        }]
    }];

    var facturasModificarFormPanel = new Ext.FormPanel({
        id:'idDatosFacturas',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [{
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        }],
        buttons: [{
            id : 'btnmodificarFACTURAS',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idSERVICIOSeleccionado!=undefined && idFACTURADORSeleccionado!= undefined && idREFERENCIA_PAGOSeleccionado!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                facturasModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'CUENTAS_A_COBRAR?op=MODIFICAR',
                                    params: {
                                        SERVICIO : idSERVICIOSeleccionado,
                                        FACTURADOR :idFACTURADORSeleccionado,
                                        REFERENCIA_PAGO:idREFERENCIA_PAGOSeleccionado
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarFACTURAS').close();
                                        Ext.getCmp('gridADMIN_FACTURAS').store.reload();
                                    },
                                    failure : function(form, action) {
                                        var obj = Ext.util.JSON .decode(action.response.responseText);

                                        if(obj.motivo != undefined ){
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : obj.motivo,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
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
                    });
                }
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarFACTURAS').close();
            }
        }]
    });

    facturasModificarFormPanel.getForm().load({
        url : 'CUENTAS_A_COBRAR?op=DETALLE',
        method : 'POST',
        params:{
            SERVICIO : idSERVICIOSeleccionado,
            FACTURADOR :idFACTURADORSeleccionado,
            REFERENCIA_PAGO:idREFERENCIA_PAGOSeleccionado
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar FACTURAS',
        id : 'modificarFACTURAS',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [facturasModificarFormPanel]
    });
    return win;

}
function pantallaAgregarFACTURAS() {
    var comboSERVICIO_FACTURADOR =getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIO');
    comboSERVICIO_FACTURADOR.allowBlank= false;
     comboSERVICIO_FACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idRefBusquedaFacAdd').focus(true,true);
    }, null,null ) ;
    var comboANULABLE=  new Ext.form.ComboBox({
        fieldLabel : 'Anulable',
        hiddenName : 'ANULABLE',
        valueField : 'TIPO',
        id  :'idAnulableFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    Ext.getCmp('idAnulableFacturasAdd').focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboMONEDA=  new Ext.form.ComboBox({
        fieldLabel : 'Moneda',
        hiddenName : 'MONEDA',
        valueField : 'TIPO',
        id  :'idMonedaFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    Ext.getCmp('idMontoFacAdd').focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['GS', 'GS'],
            ['USD', 'USD']]

        })
    });
    var comboTIPO_RECARGO =  new Ext.form.ComboBox({
        fieldLabel : 'Tipo Recargo',
        hiddenName : 'TIPO_RECARGO',
        valueField : 'TIPO',
        id  :'idTipoRecargoFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    comboPAGAR_MAS_VENCIDO.focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['NINGUNO', 'NINGUNO'],
            ['MONTO', 'MONTO'],
            ['PORCENTAJE', 'PORCENTAJE']]

        })
    });
    var comboCOBRAR_VENCIDO=  new Ext.form.ComboBox({
        fieldLabel : 'Cobrar Vencido',
        hiddenName : 'COBRAR_VENCIDO',
        valueField : 'TIPO',
        id  :'idCobrarVencidoFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    comboTIPO_RECARGO.focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboPAGAR_MAS_VENCIDO=  new Ext.form.ComboBox({
        fieldLabel : 'Pagar Más Vencido',
        hiddenName : 'PAGAR_MAS_VENCIDA',
        valueField : 'TIPO',
        id  :'idPagarMasVencidaFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : true,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    comboSOLO_EFECTIVO.focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var comboSOLO_EFECTIVO=  new Ext.form.ComboBox({
        fieldLabel : 'Sólo Efectivo',
        hiddenName : 'SOLO_EFECTIVO',
        valueField : 'TIPO',
        id  :'idSoloEfectivoFacturasAdd',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'select' : function(esteObjeto, esteEvento)   {
                    comboANULABLE.focus(true,true);
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['SI', 'SI'],
            ['NO', 'NO']]

        })
    });
    var fechaVencimiento = new Ext.form.DateField({
        fieldLabel : 'Fecha Vencimineto',
        id:'idFechaVencimientoFacAdd',
        name : 'VENCIMIENTO',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                    Ext.getCmp('idMensajeFacAdd').focus(true,true);
            }
        }
    });

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Factura',
            defaultType: 'textfield',
            items: [comboSERVICIO_FACTURADOR,{
                id:'idRefBusquedaFacAdd',
                xtype:'textfield',
                name: 'REFERENCIA_BUSQUEDA',
                anchor:'95%',
                fieldLabel: 'Ref. Búsqueda',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idRefPagoFacAdd').focus(true,true);
                        }
                    }
                }
            },{
                id:'idRefPagoFacAdd',
                xtype:'textfield',
                name: 'REFERENCIA_PAGO',
                anchor:'95%',
                fieldLabel: 'Ref. Pago',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDescripRefPagoFacAdd').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDescripRefPagoFacAdd',
                xtype:'textfield',
                name: 'DESCRIPCION_REF_PAGO',
                anchor:'95%',
                fieldLabel: 'Desc. Ref. Pago',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboMONEDA.focus(true,true);
                        }
                    }
                }
            },comboMONEDA,{
                id:'idMontoFacAdd',
                xtype:'textfield',
                name: 'MONTO',
                anchor:'95%',
                fieldLabel: 'Monto',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idMontoVencFacAdd').focus(true,true);
                        }
                    }
                }
            },{
                id:'idMontoVencFacAdd',
                xtype:'textfield',
                name: 'MONTO_VENCIDO',
                anchor:'95%',
                fieldLabel: 'Monto Vencido',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idIntMoratorioFacAdd').focus(true,true);
                        }
                    }
                }
            },{
                id:'idIntMoratorioFacAdd',
                xtype:'textfield',
                name: 'INT_MORATORIO',
                anchor:'95%',
                fieldLabel: 'Int. Moratorio',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            fechaVencimiento.focus(true,true);
                        }
                    }
                }
            },fechaVencimiento,{
                id:'idMensajeFacAdd',
                xtype:'textfield',
                name: 'MENSAJE',
                anchor:'95%',
                fieldLabel: 'Mensaje',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboCOBRAR_VENCIDO.focus(true,true);
                        }
                    }
                }
            }]
        }
    }, {
        bodyStyle: 'padding-left:10px;',
        items: [{
            xtype: 'fieldset',
            title: 'Configuración de Cobro',
            defaultType: 'textfield',
            items: [comboCOBRAR_VENCIDO, comboTIPO_RECARGO, comboPAGAR_MAS_VENCIDO, comboSOLO_EFECTIVO, comboANULABLE]
        }]
    }];

    var facturasAgregarFormPanel = new Ext.FormPanel({
        id:'idDatosFacturas',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [{
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        }],
        buttons: [{
            id : 'btnagregarFACTURA',
            text : 'Agregar',
            formBind : true,
            handler : function() {
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            facturasAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'CUENTAS_A_COBRAR?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarFACTURA').close();
                                    Ext.getCmp('gridADMIN_FACTURAS').store.reload();
                                },
                                failure : function(form, action) {
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : FAIL_CUERPO_GENERAL,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });
                                }
                            });
                        }
                    }
                });
            }
        },{
            text: 'Reset',
            handler: function(){
                facturasAgregarFormPanel.getForm().reset();
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarFACTURA').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar FACTURA',
        id : 'agregarFACTURA',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [facturasAgregarFormPanel]
    });
    return win;
}

function pantallaReporteFacturas(){

    var panel = {
        //        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        //        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteFacturas()]
    }
    //return panel;
    var win = new Ext.Window({
        id : 'idReporteFacturas',
        title:'Reporte de Facturas',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        y:'140',
        items : [panel]
    });
    return win.show();

}

function FormPanelReporteFacturas(){
    var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name : 'formatoDescarga',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['pdf', 'PDF'],
            ['xls',  'XLS']]
        })
    });
    var formPanel = new Ext.FormPanel({
        labelWidth : 165,
        frame : true,
        autoHeight:true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [formatoDeDescarga],
        buttons : [{
            formBind : true,
            text : 'Reporte',
            handler : function() {

                try {
                    Ext.destroy(Ext.get('downloadIframe'));
                    Ext.DomHelper.append(document.body, {
                        tag : 'iframe',
                        id : 'downloadIframe',
                        frameBorder : 0,
                        width : 0,
                        height : 0,
                        css : 'display:yes;visibility:hidden;height:0px;',
                        src : 'reportes?op=REPORTE-FACTURAS'
                    });
                } catch (e) {
                    alert(e);
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
            }
        }]
    });
    return formPanel;
}
