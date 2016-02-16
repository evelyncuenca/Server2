/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var idRemesaMoreSeleccionada;
function cardRemesasMoreMT() {
    if (GESTION_ABIERTA) {
        opRemesasMoreMT().center();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function opRemesasMoreMT() {
    var win = new Ext.Window({
        title: 'Elija una opcion de MORE MT',
        autoWidth: true,
        height: 'auto',
        id: 'idWinopRemesasMoreMT',
        closable: false,
        resizable: false,
        modal: true,
        defaultButton: 0,
        buttonAlign: 'right',
        buttons: [{
            text: 'Cancelar',
            handler: function() {
                win.close();
            }
        }],
        items: [{
            html: //'<a href=\'#\'  ><img onclick=\'cardEnvioMoreMT();\' src=\'images/btnERC.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                  '<a href=\'#\'  ><center><img onclick=\'cardRetiroMoreMT();\' src=\'images/btnPRC.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></center></a>'
        }]

    });
    return win.show();
}


function panelEnvioMoreMT() {
    var panelEnvioMoreMT = {
        id: 'panelEnvioMoreMT',
        xtype: 'panel',
        title: 'More Money Transfers',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
            text: 'Salir (Alt+q)',
            iconCls: 'logout',
            handler: function() { 
                Ext.Msg.show({
                    title: 'Info',
                    msg: 'Seguro que desea salir?',
                    animEl: 'elId',
                    icon: Ext.MessageBox.WARNING,
                    buttons: Ext.Msg.OKCANCEL,
                    fn:function(btn){
                        if(btn == "ok"){                            
                            Ext.getCmp('idHeaderEnviosMoreMT').getForm().reset();
                            Ext.getCmp('idAddClienteRemitenteMoreMT').disable();
                            Ext.getCmp('idNombreEnvioMoreMT').disable();
                            Ext.getCmp('idApellidoEnvioMoreMT').disable();
                            Ext.getCmp('idFechaNacimientoMoreMT').disable();
                            Ext.getCmp('idFechaEmisionDocMoreMT').disable();
                            Ext.getCmp('idDireccionEnvioMoreMT').disable();
                            Ext.getCmp('idTelefonoEnvioMoreMT').disable();
                            Ext.getCmp('idProfesionMoreMT').disable();
                            Ext.getCmp('idDatosBeneficiarioMoreMT').disable();
                            Ext.getCmp('idDatosEnvioMoreMT').disable();
                            Ext.getCmp('idDocumentoEnvioMoreMT').enable();
                            cardInicial();   
                        } else {
                            
                        }
                    }                
                });            
            }
        }],
        items: [envioMoreMT()]
    }
    return panelEnvioMoreMT;
}

function panelRetiroMoreMT() {
    var panelRetiroMoreMT = {
        id: 'panelMoreMT',
        xtype: 'panel',
        // title: 'Retiro - More Money Transfers',
        layout : 'fit',
        border : false,
        //height:500,
        autoScroll : true,/*
        tbar: [{
            text: 'Salir (Alt+q)',
            iconCls: 'logout',
            handler: function() {                
                Ext.Msg.show({
                    title: 'Info',
                    msg: 'Seguro que desea salir?',
                    animEl: 'elId',
                    icon: Ext.MessageBox.WARNING,
                    buttons: Ext.Msg.OKCANCEL,
                    fn:function(btn){
                        if(btn == "ok"){                            
                            Ext.getCmp('idHeaderRetirosMoreMT').getForm().reset();
                            Ext.getCmp('gridMORE_ORDEN_PAGO').getStore().removeAll();    
                            cardInicial();   
                        } else {
                            
                        }
                    }                
                }); 
            }
        }],*/
        items: [retiroMoreMT(),gridMoreMT()]
    }
    return panelRetiroMoreMT;
}



function cardEnvioMoreMT() {
    Ext.getCmp('idWinopRemesasMoreMT').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelEnvioMoreMT');
    Ext.getCmp('idDocumentoEnvioMoreMT').enable();
    Ext.getCmp('idHeaderEnviosMoreMT').getForm().reset();
}

function cardRetiroMoreMT() {
    Ext.getCmp('idWinopRemesasMoreMT').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelMoreMT');
    Ext.getCmp('idHeaderRetirosMoreMT').getForm().reset();    
    Ext.getCmp('gridMORE_ORDEN_PAGO').getStore().removeAll();         
    Ext.getCmp('idOrdenPagoRetMoreMT').focus(true, true);
}

function retiroMoreMT() {
    var tipoDoc =  new Ext.form.ComboBox({
        fieldLabel: 'Tipo Documento Beneficiario',
        valueField : 'TIPO',
        id  :'idTipoDocMoreRet',
        width: '200',
        anchor:'50%',
        triggerAction: 'all', 
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRucNuevo').focus(true,true);
                }
            }
        },  
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'Nro. de Cedula'], 
            ['2', 'Numero de Seguridad Social'], 
            ['3', 'Libreta de Conducir'],
            ['541', 'DNI - Argentina'], 
            ['523', 'Pasaporte']]

        })
    });
    tipoDoc.allowBlank = true;
    
    var items = {
        xtype: 'form',
        title: 'Retiro - More Money Transfers',
        id: 'idHeaderRetirosMoreMT',
        monitorValid: true,
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        items: [{
            layout: 'column',
            defaults: {
                columnWidth: '.5',
                border: true
            },
            items: [{
                items: [{
                    xtype: 'fieldset',
                    title: 'Datos Consulta',
                    id:'idDatoRemitenteMoreMT',
                    bodyStyle: 'padding:5px;',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '(*)Nro. de Orden de Pago',
                        id: 'idOrdenPagoRetMoreMT',
                        allowBlank: false,
                        width: '150',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNombreOrdenanteMoreMT').focus(true, true);                                    
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: 'Nombre Ordenante',
                        id: 'idNombreOrdenanteMoreMT',
                        allowBlank: true,
                        width: '250',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idApellidoOrdenanteMoreMT').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Apellido Ordenante',
                        id: 'idApellidoOrdenanteMoreMT',
                        allowBlank: true,
                        width: '250',
                        disabled: false,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNombreBenefMoreMT').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Nombre Beneficiario',
                        id: 'idNombreBenefMoreMT',
                        allowBlank: true,
                        width: '250',                        
                        disabled: false,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idApellidoBenefMoreMT').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: 'Apellido Beneficiario',
                        id: 'idApellidoBenefMoreMT',
                        allowBlank: true,
                        width: '250',                        
                        disabled: false,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNroDocBenefMoreMTRetiro').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: 'Nro. Doc. Beneficiario',
                        id: 'idNroDocBenefMoreMTRetiro',
                        allowBlank: true,
                        width: '250',                        
                        disabled: false,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    tipoDoc.focus(true, true);
                                }
                            }
                        }
                    },tipoDoc,{
                        xtype:'hidden',
                        id: 'idImportPagoMoreMT'
                    }]
                }]
            }]
        }],
        buttonAlign: 'left',
        buttons: [{
            text: 'Consultar',
            id: 'idBtnConfirmarEnvioMoreMT',
            width: '130',
            formBind: true,
            handler: function() {//llamada al autorizador    
                var refConsulta = "OP#"+Ext.getCmp('idOrdenPagoRetMoreMT').getValue()+";"
                +"RN#"+Ext.getCmp('idNombreOrdenanteMoreMT').getValue()+";"
                +"RA#"+Ext.getCmp('idApellidoOrdenanteMoreMT').getValue()+";"
                +"BN#"+Ext.getCmp('idNombreBenefMoreMT').getValue()+";"
                +"BA#"+Ext.getCmp('idApellidoBenefMoreMT').getValue()+";"
                +"ND#"+Ext.getCmp('idNroDocBenefMoreMTRetiro').getValue()+";"
                +"TD#"+tipoDoc.getValue();
                
                Ext.getCmp('gridMORE_ORDEN_PAGO').store.baseParams['NRO_REFERENCIA'] = refConsulta;    
                Ext.getCmp('gridMORE_ORDEN_PAGO').store.baseParams['SERVICIO'] = 139;     
                Ext.getCmp('gridMORE_ORDEN_PAGO').store.baseParams['ID_FACTURADOR'] = 109;                   
                
                Ext.getCmp('gridMORE_ORDEN_PAGO').store.load({
                    callback: function(records, operation, success) {
                        var mensaje;    
                        var icon; 
                        if(success){ 
                            mensaje = 'Favor seleccione un item';
                            icon = Ext.MessageBox.INFO ;
                        }else{
                            Ext.getCmp('idOrdenPagoRetMoreMT').reset();
                            icon = Ext.MessageBox.ERROR;
                            mensaje ='No se registran remesas con los datos de la consulta';                
                        }
                        Ext.Msg.show({
                            title : 'Info',
                            msg : mensaje,
                            buttons : Ext.Msg.OK,
                            icon : icon
                        });  
                    }
                });      
                Ext.getCmp('gridMORE_ORDEN_PAGO').setHeight(
                    Ext.getCmp('panelMoreMT').getHeight() - 
                    Ext.getCmp('idHeaderRetirosMoreMT').getHeight());
            }
        }, {
            text: 'Cancelar',
            width: '130',
            handler: function() {
                Ext.getCmp('gridMORE_ORDEN_PAGO').getStore().removeAll();       
                Ext.getCmp('idHeaderRetirosMoreMT').getForm().reset();
                Ext.getCmp('idOrdenPagoRetMoreMT').focus(true, true);
                         
            }
        },{
            text: 'Salir',
            width: '130',
            handler: function() {
                Ext.Msg.show({
                    title: 'Info',
                    msg: 'Seguro que desea salir?',
                    animEl: 'elId',
                    icon: Ext.MessageBox.WARNING,
                    buttons: Ext.Msg.OKCANCEL,
                    fn:function(btn){
                        if(btn == "ok"){                            
                            Ext.getCmp('idHeaderRetirosMoreMT').getForm().reset();
                            Ext.getCmp('gridMORE_ORDEN_PAGO').getStore().removeAll();    
                            cardInicial();   
                        } else {
                            
                        }
                    }                
                });
            }            
        }]
    }
    return items;
}

function envioMoreMT() {
    return false;
}


function gridMoreMT(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'COBRO_SERVICIOS?op=CONSULTA_MORE_MT'            
        }),
        reader : new Ext.data.JsonReader({
            root : 'ORDEN_PAGO',
            totalProperty : 'TOTAL',
            id : 'ID_ORDEN_PAGO',
            fields : ['ID_ORDEN_PAGO','IMPORTE','MONEDA','BENEFACTOR','PAIS_ORIGEN','PAIS_DESTINO','BENEFICIARIO', 'MENSAJE']
        }),        
        listeners : {
            'beforeload' : function(store, options) {   
            // st.baseParams['CONSULTA'] = Ext.getCmp('idCI').getValue();
            }
        }
    });

    var anchoDefaultColumnas= 150;
    var colModel = new Ext.grid.ColumnModel([{
        header:'ORDEN DE PAGO',
        width: anchoDefaultColumnas,
        dataIndex: 'ID_ORDEN_PAGO'
    },{
        header:'IMPORTE',
        width: 100,
        dataIndex: 'IMPORTE',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            Ext.getCmp('idImportPagoMoreMT').setValue(value);
            return addCommas(String(value));
        }
    },{
        header:'MONEDA',
        width: 100,
        dataIndex: 'MONEDA'
    },{
        header:'BENEFACTOR',
        width: anchoDefaultColumnas,
        dataIndex: 'BENEFACTOR'
    },{
        header:'PAIS ORIGEN',
        width: 100,
        dataIndex: 'PAIS_ORIGEN'
    },{
        header:'PAIS DESTINO',
        width: 100,
        dataIndex: 'PAIS_DESTINO'
    },{
        header:'BENEFICIARIO',
        width: anchoDefaultColumnas,
        dataIndex: 'BENEFICIARIO'
    },{
        header:'MENSAJE',
        width: anchoDefaultColumnas,
        dataIndex: 'MENSAJE'
    }]);
    var gridMORE_ORDEN_PAGO = new Ext.grid.GridPanel({
        id:'gridMORE_ORDEN_PAGO',
        store: st,
        cm: colModel/*,
        viewConfig: {
            forceFit:true
        }*/,
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
                idRemesaMoreSeleccionada = esteObjeto.getStore().getAt(rowIndex).id;
                confirmarRetiroMoreMT().show();
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) { 
                idRemesaMoreSeleccionada = esteObjeto.getStore().getAt(rowIndex).id;
                confirmarRetiroMoreMT().show();
            }
        }
    });
    return gridMORE_ORDEN_PAGO;
}

function confirmarRetiroMoreMT(){
    var tipoDoc =  new Ext.form.ComboBox({
        fieldLabel: 'Tipo Documento',
        valueField : 'TIPO',
        id  :'idTipoDocMoreConfirm',
        width: '200',
        anchor:'100%',
        triggerAction: 'all', 
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRucNuevo').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'Nro. de Cedula'], 
            ['2', 'Numero de Seguridad Social'], 
            ['3', 'Libreta de Conducir'],
            ['541', 'DNI - Argentina'], 
            ['523', 'Pasaporte']]

        })
    });
    tipoDoc.allowBlank = true;
    var fechaNacimiento = new Ext.form.DateField({
        fieldLabel : 'Fecha de Nacimiento',
        id:'idFechaNacRetMore',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    
    var fechaEmisionDoc = new Ext.form.DateField({
        fieldLabel : 'Fecha de Emision',
        id:'idFechaExpRetMore',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    

    var confirmaMoreMTFormPanel = new Ext.FormPanel({
        id : 'idConfirmaMoreMTFormPanel',
        labelWidth : 120,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'Orden de Pago',
            id:'idOrdenPagoMoreMT',
            name:'ID NRO_ORDEN',
            xtype:'textfield',
            value :Ext.getCmp('idOrdenPagoRetMoreMT').getValue(),
            disabled: true
        },{
            fieldLabel:'Monto a pagar',
            id:'idMontoPagarMoreMT',
            xtype:'textfield',
            value:addCommas(String(Ext.getCmp('idImportPagoMoreMT').getValue())),
            disabled: true
        },tipoDoc,{
            fieldLabel:'Nro. Doc. Beneficiario',
            name:'NRO_DOC_BENF',
            id  :'idNroDocBenefMoreMT',
            xtype:'numberfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('btnConfirmarPagoMoreMT').focus(true,true);
                    }
                }
            }
        },fechaEmisionDoc,fechaNacimiento],

        buttons : [{
            id : 'btnConfirmarPagoMoreMT',
            text : 'Confirmar',
            formBind : true,
            handler : function() {
                if(idRemesaMoreSeleccionada != undefined){
                    Ext.Msg.show({
                        title : "Confirmacion",
                        msg : "¿Está seguro que confirmar el pago?",
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {
                            if (btn == "yes") {                                
                                var randomNumber = Math.floor((Math.random()*10000000)+1); 
                                Ext.Msg.wait('Procesando... Por Favor espere...');
                                Ext.getCmp('panelConsulta').load({
                                    url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                    params:{
                                        NRO_TELEFONO : Ext.getCmp('idOrdenPagoRetMoreMT').getValue(), 
                                        SERVICIO : 139,
                                        ID_FACTURADOR : 109,
                                        MONTO_CARGAR:  Ext.getCmp('idImportPagoMoreMT').getValue(),
                                        TIPO_DE_PAGO: 1,
                                        MORE : 0,
                                        FECHA_NAC: Ext.getCmp('idFechaNacRetMore').getRawValue(),
                                        TIPO_DOC:Ext.getCmp('idTipoDocMoreConfirm').getValue(),
                                        ID_DOCUMENTO:Ext.getCmp('idNroDocBenefMoreMT').getValue(),
                                        FECHA_EXP: Ext.getCmp('idFechaExpRetMore').getRawValue()
                                    }, 
                                    discardUrl: false,
                                    nocache: true,
                                    text: "Cargando...",
                                    timeout: TIME_OUT_AJAX,
                                    scripts: true
                                });
                                Ext.getCmp('winConfirmarMoreMT').close();
                                Ext.Msg.hide();
                                Ext.getCmp('content-panel').layout.setActiveItem('panelConsulta');     
                            }
                        }
                    });
                }
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('winConfirmarMoreMT').close();
            }
        }]
    });    
    var win = new Ext.Window({
        title:'Confirmar Solicitud de Pago',
        id : 'winConfirmarMoreMT',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [confirmaMoreMTFormPanel]
    });
    return win;
}

/*function envioRemesa(idTipoDoc, idDoc, monto, codCliente, nombres, apellidos, ciudad, telefono, movil, comision, montoBase, tabla, idTabla, pregSeg, respSeg){
    var randomNumber = Math.floor((Math.random()*10000000)+1);
    Ext.Msg.wait('Procesando... Por Favor espere...');
    Ext.getCmp('panelConsulta').load({
        url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
        params:{
            NRO_TELEFONO : idDoc, 
            SERVICIO : 131,
            ID_FACTURADOR : 104,
            MONTO_CARGAR: monto,
            TIPO_DE_PAGO: 1,
            MoreMT : 0,
            TIPO_DOC : idTipoDoc,
            ID_DOCUMENTO : idDoc,
            COD_CLIENTE: codCliente, 
            NOMBRES: nombres,
            APELLIDOS: apellidos,
            CIUDAD: ciudad,
            TELEFONO: telefono,
            MOVIL: movil,
            COMISION: comision, 
            MONTO_BASE: montoBase,
            MONTO: monto, 
            PREG_SEG: pregSeg,
            RESP_SEG: respSeg,
            TABLA: tabla,
            ID_TABLA: idTabla
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

function retiroRemesa(idTipoDoc, idDoc, codRemesa, montoBase){
    var randomNumber = Math.floor((Math.random()*10000000)+1);
    Ext.Msg.wait('Procesando... Por Favor espere...');
    Ext.getCmp('panelConsulta').load({
        url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
        params:{
            NRO_TELEFONO : idDoc, 
            SERVICIO : 132,
            ID_FACTURADOR : 104,
            TIPO_DE_PAGO: 1,
            MONTO_CARGAR: montoBase,
            MoreMT : 0,
            TIPO_DOC : idTipoDoc,
            ID_DOCUMENTO : idDoc,
            COD_REMESA: codRemesa
        }, 
        discardUrl: false,
        nocache: true,
        text: "Cargando...",
        timeout: TIME_OUT_AJAX,
        scripts: true
    });
    Ext.Msg.hide();
    Ext.getCmp('content-panel').layout.setActiveItem('panelConsulta');       
}*/