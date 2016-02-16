/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cardRemesasConti() {
    if (GESTION_ABIERTA) {
        opRemesasConti().center();
    } else {
        Ext.Msg.show({
            title: 'Estado',
            msg: MSG_GESTION_CERRADA,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
        });
    }
}

function opRemesasConti() {
    var win = new Ext.Window({
        title: 'Elija una opcion de PractiGIRO', 
        autoWidth: true,
        height: 'auto',
        id: 'idWinopRemesasConti',
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
            html: '<a href=\'#\'  ><img onclick=\'cardERC();\' src=\'images/btnERC.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>\n\
                  <a href=\'#\'  ><img onclick=\'cardPRC();\' src=\'images/btnPRC.PNG\' style=\'opacity:0.4;filter:alpha(opacity=40);\' onmouseover=\'this.style.opacity=1\' onmouseout=\'this.style.opacity=0.4 \'/></a>'
        }]

    });
    return win.show();
}


function panelERC() {
    var panelERC = {
        id: 'panelERC',
        xtype: 'panel',
        title: 'PractiGIRO',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
            text: 'Salir',
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
                            Ext.getCmp('idHeaderEnviosPG').getForm().reset();
                            Ext.getCmp('idAddClienteRemitentePG').disable();
                            Ext.getCmp('idNombreEnvioPG').disable();
                            Ext.getCmp('idApellidoEnvioPG').disable();
                            Ext.getCmp('idFechaNacimientoPG').disable();
                            Ext.getCmp('idFechaEmisionDocPG').disable();
                            Ext.getCmp('idDireccionEnvioPG').disable();
                            Ext.getCmp('idTelefonoEnvioPG').disable();
                            Ext.getCmp('idProfesionPG').disable();
                            Ext.getCmp('idDatosBeneficiarioPG').disable();
                            Ext.getCmp('idDatosEnvioPG').disable();
                            Ext.getCmp('idDocumentoEnvioPG').enable();
                            cardInicial();   
                        } else {
                            
                        }
                    }                
                });            
            }
        }],
        items: [envioPG()]
    }
    return panelERC;
}

function panelPRC() {
    var panelPRC = {
        id: 'panelRRC',
        xtype: 'panel',
        title: 'PractiGIRO',
        border: false,
        autoHeight: false,
        autoScroll: true,
        tbar: [{
            text: 'Salir',
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
                            Ext.getCmp('idHeaderRetirosPG').getForm().reset();
                            
                            Ext.getCmp('idAgregarClientePG').disable();
                            Ext.getCmp('idNombreRetiroPG').disable();
                            Ext.getCmp('idApellidoRetiroPG').disable();
                            Ext.getCmp('idFechaNacimientoRetiroPG').disable();
                            Ext.getCmp('idFechaEmisionDocRetiroPG').disable();
                            Ext.getCmp('idDireccionRetiroPG').disable();
                            Ext.getCmp('idTelefonoRetiroPG').disable();
                            Ext.getCmp('idProfesionRetiroPG').disable();         
                            Ext.getCmp('idCodigoRemesaPG').disable();
                            Ext.getCmp('idDocumentoRetiroPG').enable();
                            
                            cardInicial();   
                        } else {
                            
                        }
                    }                
                }); 
            }
        }],
        items: [retiroPG()]
    }
    return panelPRC;
}



function cardERC() {
    Ext.getCmp('idWinopRemesasConti').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelERC');
    Ext.getCmp('idDocumentoEnvioPG').enable();
    Ext.getCmp('idHeaderEnviosPG').getForm().reset();
}

function cardPRC() {
    Ext.getCmp('idWinopRemesasConti').close();
    Ext.getCmp('content-panel').layout.setActiveItem('panelRRC');
    Ext.getCmp('idHeaderRetirosPG').getForm().reset();
    Ext.getCmp('idDocumentoRetiroPG').enable();
}

function retiroPG() {
    /*var pais = getComboPractiGiro('PAIS', 'PAIS', 'PAIS', 'PAIS', 'DESCRIPCION_PAIS', 'Pais', 'PAIS', 'DESCRIPCION_PAIS', 'PAIS', 'PAIS');
    var departamento = getComboPractiGiro('DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'Departamento', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO');
    var ciudad = getComboPractiGiro('CIUDAD', 'CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'Ciudad', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD');*/
    var tipoDoc = getComboPractiGiro('TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', 'Tipo Documento', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', 'TIPO_DOC', 'TIPO DOCUMENTO');

   
    tipoDoc.allowBlank = false;
    /*pais.allowBlank = false;
    ciudad.allowBlank = false;
    departamento.disabled = true;
    ciudad.disabled = true;

    pais.addListener('select', function(esteCombo, record, index) {
        departamento.enable();
        departamento.store.baseParams['PAIS'] = record.data.PAIS;
        departamento.store.reload();
    }, null, null);

    departamento.addListener('select', function(esteCombo, record, index) {
        ciudad.enable();
        ciudad.store.baseParams['DEPARTAMENTO'] = record.data.DEPARTAMENTO;
        ciudad.store.reload();
    }, null, null);*/

    tipoDoc.addListener('select', function(esteCombo, record, index) {
        Ext.getCmp('idDocumentoRetiroPG').focus(true, true);
    }, null, null);
    
    var respuestaGetDatos = new Ext.form.TextArea({
        id :'idRespuestaGetDatos',       
        anchor :'100%',
        height:'189px',
        allowBlank: false,
        readOnly: true,        
        style:'font-size:14px;font-weight:bold;'
    });
    
    var items = {
        xtype: 'form',
        id: 'idHeaderRetirosPG',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items: [{
            layout: 'column',
            defaults: {
                columnWidth: '.5',
                border: true
            },
            items: [{
                items: [{
                    xtype: 'fieldset',
                    title: 'Datos Remesa',
                    bodyStyle: 'padding:5px;',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: 'Cód. de Remesa',
                        id: 'idCodigoRemesaPG',
                        allowBlank: false,
                        width: '100',
                        disabled : true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    var idTipoDoc = tipoDoc.getValue();
                                    var idDoc = Ext.getCmp('idDocumentoRetiroPG').getValue();
                                    getDatosEnvio(esteObjeto.getValue(), idTipoDoc, idDoc);
                                //Ext.getCmp('').enable();
                                //aca debemos obtener los datos del beneficiario
                                }
                            }
                        }
                    },{
                        xtype:'hidden',
                        id: 'idMontoBaseRetiroPG'
                    },{
                        xtype: 'hidden',
                        id: 'idComisionRetiroPG'
                    }]
                }, {
                    xtype: 'fieldset',
                    title: 'Datos Transaccion',
                    bodyStyle: 'padding:5px;',
                    id:'idDatosTrxEnvioPG',
                    labelWidth: 1,
                    items: [respuestaGetDatos]
                }]
            }, {
                bodyStyle: 'padding-left:10px;',
                items: [{
                    xtype: 'fieldset',
                    title: 'Datos Beneficiario',
                    id: 'idDatosBeneficiaroRetiroPG',
                    bodyStyle: 'padding:5px;',
                    items: [tipoDoc, {
                        xtype: 'textfield',
                        fieldLabel: 'Nro. de Documento',
                        id: 'idDocumentoRetiroPG',
                        name: 'DOCUMENTO_NRO',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            'blur' : function(esteObjeto, esteEvento)   {
                                verifyClientPago(tipoDoc.getValue(), esteObjeto.getValue());                                
                            },
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idFechaEmisionDocRetiroPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'datefield',
                        fieldLabel: 'Fecha Emision Documento',
                        id: 'idFechaEmisionDocRetiroPG',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        name: 'FECHA_NAC',
                        format: 'd/m/Y',
                        disabled: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNombreRetiroPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Nombre/s',
                        id: 'idNombreRetiroPG',
                        name: 'NOMBRE',
                        allowBlank: false,
                        width: '250',
                        enableKeyEvents: true,
                        disabled: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idApellidoRetiroPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: 'Apellido/s',
                        id: 'idApellidoRetiroPG',
                        allowBlank: false,
                        name: 'APELLIDO',
                        width: '250',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idProfesionRetiroPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: 'Profesión',
                        id: 'idProfesionRetiroPG',
                        allowBlank: false,
                        name: 'PROFESION',
                        width: '250',                        
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idFechaNacimientoRetiroPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'datefield',
                        fieldLabel: 'Fecha Nacimiento',
                        id: 'idFechaNacimientoRetiroPG',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        disabled: true,
                        format: 'd/m/Y',
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idDireccionRetiroPG').focus(true, true);
                                }
                            }
                        }
                    }/*, pais, departamento, ciudad*/, {
                        xtype: 'textfield',
                        fieldLabel: 'Dirección',
                        id: 'idDireccionRetiroPG',
                        allowBlank: false,
                        name: 'DIRECCION',
                        width: '250',
                        enableKeyEvents: true,
                        disabled: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idTelefonoRetiroPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'numberfield',
                        fieldLabel: 'Teléfono',
                        id: 'idTelefonoRetiroPG',
                        allowBlank: false,
                        name: 'TELEFONO',
                        width: '100',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idDocumentoBeneficiarioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'button',
                        text: 'Agregar',
                        id: 'idAgregarClientePG',
                        disabled: true,
                        iconCls: 'add2',
                        handler : function() {
                            var idTipoDoc = tipoDoc.getValue();
                            var idDoc = Ext.getCmp('idDocumentoRetiroPG').getValue();
                            var nombres = Ext.getCmp('idNombreRetiroPG').getValue();
                            var apellidos = Ext.getCmp('idApellidoRetiroPG').getValue();
                            var fechaNac = Ext.getCmp('idFechaNacimientoRetiroPG').getRawValue();
                            var direccion = Ext.getCmp('idDireccionRetiroPG').getValue();
                            var telefono = Ext.getCmp('idTelefonoRetiroPG').getValue();
                            var fechaEmision = Ext.getCmp('idFechaEmisionDocRetiroPG').getRawValue();
                            var profesion = Ext.getCmp('idProfesionRetiroPG').getValue(); 
                            addClient(idTipoDoc, idDoc, fechaEmision, nombres, apellidos, fechaNac, direccion, telefono, profesion, 1); 
                        }
                    }]
                }]
            }]
        }],
        buttonAlign: 'left',
        buttons: [{
            text: 'Confirmar',
            id: 'idBtnConfirmarRetiroPG',
            width: '130',
            formBind: true,
            handler: function() {//llamada al autorizador 
                Ext.Msg.show({
                    title: 'Info',
                    msg: 'Confirma los datos de retiro?',
                    animEl: 'elId',
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OKCANCEL,
                    fn:function(btn){
                        if(btn == "ok"){                            
                            var idTipoDoc = tipoDoc.getValue();
                            var idDoc = Ext.getCmp('idDocumentoRetiroPG').getValue();
                            var codRemesa = Ext.getCmp('idCodigoRemesaPG').getValue();
                            var montoBase = Ext.getCmp('idMontoBaseRetiroPG').getValue();
                            retiroRemesa(idTipoDoc, idDoc, codRemesa, montoBase);                            
                        } else {                            
                        }
                    }                
                }); 
            }
        }, {
            text: 'Cancelar',
            width: '130',
            handler: function() {
                Ext.getCmp('idHeaderRetirosPG').getForm().reset();
                Ext.getCmp('idDocumentoRetiroPG').enable();
                tipoDoc.focus(true, true);
                Ext.getCmp('idAgregarClientePG').disable();
                Ext.getCmp('idNombreRetiroPG').disable();
                Ext.getCmp('idApellidoRetiroPG').disable();
                Ext.getCmp('idFechaNacimientoRetiroPG').disable();
                Ext.getCmp('idFechaEmisionDocRetiroPG').disable();
                Ext.getCmp('idDireccionRetiroPG').disable();
                Ext.getCmp('idTelefonoRetiroPG').disable();
                Ext.getCmp('idProfesionRetiroPG').disable();         
                Ext.getCmp('idCodigoRemesaPG').disable();
                
                
            }
        }]
    }
    return items;
}

function envioPG() {
    var departamento = getComboPractiGiro('DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'Departamento', 'DEPARTAMENTO', 'DESCRIPCION_DEPARTAMENTO', 'DEPARTAMENTO', 'DEPARTAMENTO');
    /*var ciudad = getComboPractiGiro('CIUDAD', 'CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'Ciudad', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD');
     *var pais = getComboPractiGiro('PAIS', 'PAIS', 'PAIS', 'PAIS', 'DESCRIPCION_PAIS', 'Pais', 'PAIS', 'DESCRIPCION_PAIS', 'PAIS', 'PAIS');
    var barrio = getComboPractiGiro('BARRIO', 'BARRIO', 'BARRIO', 'BARRIO', 'DESCRIPCION_BARRIO', 'Barrio', 'BARRIO', 'DESCRIPCION_BARRIO', 'BARRIO', 'BARRIO');*/
    var ciudadPago = getComboPractiGiro('CIUDAD', 'CIUDAD', 'CIUDAD', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'Ciudad de Pago', 'CIUDAD', 'DESCRIPCION_CIUDAD', 'CIUDAD', 'CIUDAD');
    var tipoDocRemitente = getComboPractiGiro('TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', '(*)Tipo Documento', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', 'TIPO_DOC', 'TIPO DOCUMENTO');
    var sucursales = getComboPractiGiro('SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'Sucursal', 'SUCURSAL', 'DESCRIPCION_SUCURSAL', 'SUCURSAL', 'Sucursal mas cercana');
    /*var tipoDocBeneficiario = getComboPractiGiro('TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', 'Tipo Documento', 'TIPO_DOC', 'DESCRIPCION_TIPO_DOC', 'TIPO_DOC', 'TIPO DOCUMENTO');*/
    

    /*pais.allowBlank = false;
    departamento.allowBlank = false;
    ciudad.allowBlank = false;*/
    tipoDocRemitente.allowBlank = false;
    
    var montoConComision =  new Ext.form.Checkbox ({
        fieldLabel: 'Monto con comision',
        vertical: true,
        columns: 1,
        listeners : {
            'check' : function(esteObjeto, checked)   {
                if(checked){
                    Ext.getCmp('idImporteEnvioPG').disable();
                    Ext.getCmp('idImporteCobrarEnvioPG').enable();
                    Ext.getCmp('idImporteEnvioPG').reset();
                    Ext.getCmp('idImporteCobrarEnvioPG').reset();
                    Ext.getCmp('idImporteComisionEnvioPG').reset();
                    Ext.getCmp('idImporteCobrarEnvioPG').focus(true, true);
                    
                }else{
                    Ext.getCmp('idImporteCobrarEnvioPG').disable();
                    Ext.getCmp('idImporteEnvioPG').enable();
                    Ext.getCmp('idImporteEnvioPG').reset();
                    Ext.getCmp('idImporteCobrarEnvioPG').reset();
                    Ext.getCmp('idImporteComisionEnvioPG').reset();
                    Ext.getCmp('idImporteEnvioPG').focus(true, true);                    
                }
            }
        }
    });
    
    
    ciudadPago.disabled = true;
    sucursales.disabled = true;
    /*barrio.disabled = true;*/
    
    tipoDocRemitente.addListener('select', function(esteCombo, record, index) {
        Ext.getCmp('idDocumentoEnvioPG').focus(true, true);
    }, null, null);
    
    /*pais.addListener('select', function(esteCombo, record, index) {
        departamento.enable();
        departamento.focus(true, true);
        departamento.store.baseParams['PAIS'] = record.data.PAIS;
        departamento.store.load({
            callback: function(r, options, success) {                
                if(departamento.getRawValue().length != 0){                    
                    departamento.reset();
                    ciudad.disable();
                    barrio.disable();
                }
            }
        });
        
    }, null, null);
    
    pais.addListener('change', function(esteCombo, newValue, oldValue){
        if(newValue == ''){            
            departamento.disable();
        }
    }, null, null);*/
    
    departamento.addListener('select', function(esteCombo, record, index) {
        ciudadPago.enable();
        ciudadPago.focus(true, true);
        ciudadPago.store.baseParams['DEPARTAMENTO'] = record.data.DEPARTAMENTO;
        ciudadPago.store.baseParams['limit'] = 10;
        ciudadPago.store.baseParams['start'] = 0;
        ciudadPago.store.reload({
            callback: function(r, options, success) {                
                if(departamento.getRawValue().length != 0){                    
                    ciudadPago.reset();
                    sucursales.reset();
                    sucursales.disable();
                }
            }
        });
    }, null, null);
    
    departamento.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(newValue == ''){
            ciudadPago.reset();
            sucursales.reset();
        }
    }, null, null);
    
    ciudadPago.addListener('select', function(esteCombo, record, index) {
        sucursales.enable();
        sucursales.focus(true, true);
        sucursales.store.baseParams['CIUDAD'] = record.data.CIUDAD;
        sucursales.store.baseParams['limit'] = 10;
        sucursales.store.baseParams['start'] = 0;
        sucursales.store.reload();
    }, null, null);
    
    ciudadPago.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(newValue == ''){
            sucursales.reset();
        }
    }, null, null);
    
    sucursales.addListener('select', function(esteCombo, record, index) {
        Ext.getCmp('idPregSegPG').focus(true, true);
    }, null, null);
    
    /*
    barrio.addListener('select', function(esteCombo, record, index) {
        Ext.getCmp('idDireccionEnvioPG').focus(true, true);
    }, null, null);*/
    

    
    var items = {
        xtype: 'form',
        id: 'idHeaderEnviosPG',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        frame: true,
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items: [{
            layout: 'column',
            defaults: {
                columnWidth: '.5',
                border: true
            },
            items: [{
                items: [{
                    xtype: 'fieldset',
                    title: 'Datos Remitente',
                    id:'idDatoRemitentePG',
                    bodyStyle: 'padding:5px;',
                    items: [tipoDocRemitente, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Nro. de Documento',
                        id: 'idDocumentoEnvioPG',
                        name: 'DOCUMENTO_NRO',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            /*'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    if(verifyClient(tipoDocRemitente.getValue(), esteObjeto.getValue())){
                                        Ext.getCmp('idFechaEmisionDocPG').focus(true, true);
                                    }
                                }
                            },*/
                            'blur' : function(esteObjeto, esteEvento)   {
                                if(verifyClient(tipoDocRemitente.getValue(), esteObjeto.getValue())){
                                    Ext.getCmp('idFechaEmisionDocPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'datefield',
                        fieldLabel: '(*)Fecha Emision Documento',
                        id: 'idFechaEmisionDocPG',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        name: 'FECHA_NAC',
                        format: 'd/m/Y',
                        disabled: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNombreEnvioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Nombre/s',
                        id: 'idNombreEnvioPG',
                        name: 'NOMBRE',
                        allowBlank: false,
                        width: '250',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idApellidoEnvioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Apellido/s',
                        id: 'idApellidoEnvioPG',
                        allowBlank: false,
                        name: 'APELLIDO',
                        width: '250',                        
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idProfesionPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: '(*)Profesión',
                        id: 'idProfesionPG',
                        allowBlank: false,
                        name: 'PROFESION',
                        width: '250',                        
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idFechaNacimientoPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'datefield',
                        fieldLabel: '(*)Fecha Nacimiento',
                        id: 'idFechaNacimientoPG',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        name: 'FECHA_NAC',
                        format: 'd/m/Y',
                        disabled: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idDireccionEnvioPG').focus(true, true);
                                }
                            }
                        }
                    },/* pais, departamento, ciudad, barrio,*/{
                        xtype: 'textfield',
                        fieldLabel: '(*)Dirección',
                        id: 'idDireccionEnvioPG',
                        allowBlank: false,
                        name: 'DIRECCION',
                        width: '250',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idTelefonoEnvioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'numberfield',
                        fieldLabel: '(*)Teléfono',
                        id: 'idTelefonoEnvioPG',
                        allowBlank: false,
                        name: 'TELEFONO',
                        width: '100',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idTipoDocBenefEnvioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'button',
                        text: 'Agregar',
                        id: 'idAddClienteRemitentePG',
                        disabled: true,
                        formBind: true,
                        iconCls: 'add2',
                        handler : function() {
                            var idTipoDoc = tipoDocRemitente.getValue();
                            var idDoc = Ext.getCmp('idDocumentoEnvioPG').getValue();
                            var nombres = Ext.getCmp('idNombreEnvioPG').getValue();
                            var apellidos = Ext.getCmp('idApellidoEnvioPG').getValue();
                            var fechaNac = Ext.getCmp('idFechaNacimientoPG').getRawValue();
                            var direccion = Ext.getCmp('idDireccionEnvioPG').getValue();
                            var telefono = Ext.getCmp('idTelefonoEnvioPG').getValue();
                            var fechaEmision = Ext.getCmp('idFechaEmisionDocPG').getRawValue();
                            var profesion = Ext.getCmp('idProfesionPG').getValue();
                            addClient(idTipoDoc, idDoc, fechaEmision, nombres, apellidos, fechaNac, direccion, telefono, profesion, 0);
                        /*    Ext.getCmp('idDatosBeneficiarioPG').enable();
                                Ext.getCmp('idDatosEnvioPG').enable();
                                Ext.getCmp('idCodigoClientePG').focus(true,true);*/
                            
                        }
                    }]
                }, {
                    xtype: 'fieldset',
                    title: 'Datos beneficiario',
                    bodyStyle: 'padding:5px;',
                    id: 'idDatosBeneficiarioPG',
                    disabled: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: 'Código Cliente',
                        id: 'idCodigoClientePG',
                        name: 'CODIGO_CLIENTE',
                        allowBlank: true,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            'keyup': function(esteObjeto, esteEvento) {
                                Ext.getCmp('idImporteEnvioPG').reset();
                                Ext.getCmp('idImporteComisionEnvioPG').reset();
                                Ext.getCmp('idImporteCobrarEnvioPG').reset();
                            },
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idTelefonoBeneficiarioPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'numberfield',
                        fieldLabel: 'Teléfono',
                        id: 'idTelefonoBeneficiarioPG',
                        name: 'TELEFONO',
                        allowBlank: true,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idMovilBeneficiarioPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'numberfield',
                        fieldLabel: 'Móvil',
                        id: 'idMovilBeneficiarioPG',
                        name: 'MOVIL',
                        allowBlank: true,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idNombreBeneficiarioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Nombre/s',
                        id: 'idNombreBeneficiarioPG',
                        name: 'NOMBRE_BENEFICIARIO',
                        allowBlank: false,
                        width: '250',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idApellidoBeneficiarioPG').focus(true, true);
                                }
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Apellido/s',
                        id: 'idApellidoBeneficiarioPG',
                        name: 'APELLIDO_BENEFICIARIO',
                        allowBlank: false,
                        width: '250',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idImporteEnvioPG').focus(true, true);
                                }
                            }
                        }
                    }]
                }]
            }, {
                bodyStyle: 'padding-left:10px;',
                items: [{
                    xtype: 'fieldset',
                    title: 'Datos Envio',
                    bodyStyle: 'padding:5px;',
                    id: 'idDatosEnvioPG',
                    disabled: true,
                    items: [montoConComision,{
                        xtype: 'textfield',
                        fieldLabel: '(*)Importe',
                        id: 'idImporteEnvioPG',
                        allowBlank: false,
                        width: '100',
                        enableKeyEvents: true,
                        listeners: {
                            'keyup': function(esteObjeto, esteEvento) {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));

                            /*var monto = parseInt(esteObjeto.getRawValue().replace(/,/g, ""));
                                if (monto % 100 == 0 && monto >= 1000) {
                                    var comision = monto * 0.04;
                                    Ext.getCmp('idImporteComisionEnvioPG').setValue(addCommas(comision.toString()));
                                    Ext.getCmp('idImporteCobrarEnvioPG').setValue(addCommas((comision + monto).toString()));
                                } else {
                                    Ext.getCmp('idImporteComisionEnvioPG').reset();
                                    Ext.getCmp('idImporteCobrarEnvioPG').reset();
                                }*/
                            },
                            'blur' : function(esteObjeto, esteEvento)   {
                                //if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                if(esteObjeto.getRawValue()!=''){
                                    var monto = parseInt(esteObjeto.getRawValue().replace(/,/g, ""));
                                    if (monto % 50 == 0 && monto >= 1000) {
                                        getParamComision(monto, "N");
                                    }else{
                                        Ext.Msg.show({
                                            title: 'Aviso',
                                            msg: 'Monto incorrecto, debe ser mayor a mil y multiplo de 50',
                                            animEl: 'elId',
                                            icon: Ext.MessageBox.ERROR,
                                            buttons: Ext.Msg.OK,
                                            fn: function(){
                                                esteObjeto.reset();
                                                esteObjeto.focus(true, true);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }, /*{
                                     xtype:'textfield',
                                     fieldLabel: 'Confirmar Monto',           
                                     id :'idConfirmarMontoAEnviar',
                                     allowBlank : false,
                                     width: '100',
                                     enableKeyEvents:true,
                                     listeners: {
                                     'keyup' : function(esteObjeto, esteEvento)   {
                                     esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                     esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                                     },
                                     'specialkey' : function(esteObjeto, esteEvento)   {
                                     if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                     Ext.getCmp('idBtnConfirmarEnvioPG').focus(true,true);
                                     }
                                     }
                                     }
                                     },*/{
                        xtype: 'textfield',
                        fieldLabel: '(*)Comision',
                        id: 'idImporteComisionEnvioPG',
                        allowBlank: false,
                        width: '100',
                        disabled: true
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '(*)Importe a cobrar',
                        id: 'idImporteCobrarEnvioPG',
                        allowBlank: false,
                        width: '100',
                        disabled: true,
                        enableKeyEvents: true,
                        listeners: {
                            'keyup': function(esteObjeto, esteEvento) {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));                            
                            },
                            'blur' : function(esteObjeto, esteEvento)   {
                                //if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                if(esteObjeto.getRawValue()!=''){
                                    var monto = parseInt(esteObjeto.getRawValue().replace(/,/g, ""));
                                    if (monto % 50 == 0 && monto >= 1000) {
                                        getParamComision(monto, "S");
                                    }else{
                                        Ext.Msg.show({
                                            title: 'Aviso',
                                            msg: 'Monto incorrecto, debe ser mayor a mil y multiplo de 50',
                                            animEl: 'elId',
                                            icon: Ext.MessageBox.ERROR,
                                            buttons: Ext.Msg.OK,
                                            fn: function(){
                                                esteObjeto.reset();
                                                esteObjeto.focus(true, true);
                                            }
                                        });
                                    }
                                }
                            },
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    Ext.getCmp('idPregSegPG').focus(true,true);
                                }
                            }
                        }
                    },departamento, ciudadPago,sucursales,{
                        xtype: 'textfield',
                        fieldLabel: 'Pregunta de Seguridad',
                        id: 'idPregSegPG',
                        name: 'PREG_SEG',
                        allowBlank: true,
                        width: '250',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idRespuestaPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'textfield',
                        fieldLabel: 'Respuesta',
                        id: 'idRespuestaPG',
                        name: 'RESP_SEG',
                        allowBlank: true,
                        width: '250',
                        enableKeyEvents: true,
                        listeners: {
                            'specialkey': function(esteObjeto, esteEvento) {
                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idBtnConfirmarEnvioPG').focus(true, true);
                                }
                            }
                        }
                    },{
                        xtype: 'hidden',
                        id: 'idTablaCom'
                    },{
                        xtype: 'hidden',
                        id: 'idNombreTablaCom'
                    }]
                }]
            }]
        }],
        buttonAlign: 'left',
        buttons: [{
            text: 'Confirmar',
            id: 'idBtnConfirmarEnvioPG',
            width: '130',
            formBind: true,
            handler: function() {//llamada al autorizador                 
                Ext.Msg.show({
                    title: 'Info',
                    msg: 'Confirma los datos de envio?',
                    animEl: 'elId',
                    icon: Ext.MessageBox.INFO,
                    buttons: Ext.Msg.OKCANCEL,
                    fn:function(btn){
                        if(btn == "ok"){                            
                            var idTipoDoc = tipoDocRemitente.getValue();
                            var idDoc = Ext.getCmp('idDocumentoEnvioPG').getValue();
                            var nombres = Ext.getCmp('idNombreBeneficiarioPG').getValue();
                            var apellidos = Ext.getCmp('idApellidoBeneficiarioPG').getValue();
                            var ciudad = ciudadPago.getValue();
                            var telefono = Ext.getCmp('idTelefonoBeneficiarioPG').getValue();
                            var movil = Ext.getCmp('idMovilBeneficiarioPG').getValue();
                            var monto = Ext.getCmp('idImporteCobrarEnvioPG').getValue();     
                            var codCliente = Ext.getCmp('idCodigoClientePG').getValue();
                            var comision = Ext.getCmp('idImporteComisionEnvioPG').getValue();
                            var montoBase = Ext.getCmp('idImporteEnvioPG').getValue();
                            Ext.getCmp('idDatosBeneficiarioPG').disable();
                            Ext.getCmp('idDatosEnvioPG').disable();
                            ciudadPago.disable();
                            sucursales.disable();
                            var pregSeg = Ext.getCmp('idPregSegPG').getValue();
                            var respSeg = Ext.getCmp('idRespuestaPG').getValue();
                            var tabla = Ext.getCmp('idNombreTablaCom').getValue();
                            var idTabla = Ext.getCmp('idTablaCom').getValue();
                            
                            envioRemesa(idTipoDoc, idDoc, monto, codCliente, nombres, apellidos, ciudad, telefono, movil, comision, montoBase, tabla, idTabla, pregSeg, respSeg);
                        } else {
                            
                        }
                    }                
                }); 
                
            }
        }, {
            text: 'Cancelar',
            width: '130',
            handler: function() {
                Ext.getCmp('idHeaderEnviosPG').getForm().reset();
                tipoDocRemitente.enable();
                tipoDocRemitente.focus(true, true);
                Ext.getCmp('idAddClienteRemitentePG').disable();
                Ext.getCmp('idNombreEnvioPG').disable();
                Ext.getCmp('idApellidoEnvioPG').disable();
                Ext.getCmp('idFechaNacimientoPG').disable();
                Ext.getCmp('idFechaEmisionDocPG').disable();
                Ext.getCmp('idDireccionEnvioPG').disable();
                Ext.getCmp('idTelefonoEnvioPG').disable();
                Ext.getCmp('idProfesionPG').disable();
                Ext.getCmp('idDatosBeneficiarioPG').disable();
                Ext.getCmp('idDatosEnvioPG').disable();
                ciudadPago.disable();
                sucursales.disable();
                //Ext.getCmp('idDatoRemitentePG').enable();
                
                Ext.getCmp('idDocumentoEnvioPG').enable();
                
                
            }
        }]
    }
    return items;
}


function getComboPractiGiro(suUrl, suRootJson, suIdJson, suNameJson, suDescripcion, suLabel, suHidden, suDisplayField, suValueField, suEmptyText) {

    var ds_providers = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'post',
            url: 'PRACTIGIRO?op=' + suUrl
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
        anchor: '50%',
        pageSize: 10,
        listWidth: 250,
        forceSelection: true,
        loadingText: 'Cargando...',
        typeAhead: true,
        minChars: 1
    });
    return combo;
}


function verifyClient(idTipoDoc, idDoc){    
    
    if(idTipoDoc != '' && idDoc != ''){
        var conn = new Ext.data.Connection();    
        conn.request({
            waitTitle: 'Conectando',
            waitMsg: 'Verificando cliente..',
            url: 'PRACTIGIRO?op=GET_CLIENTE',
            params: {
                TIPO_DOC: idTipoDoc,
                ID_DOCUMENTO: idDoc
            },
            method: 'POST',
            success: function (respuestaServer) {
                var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                if (obj.success) {
                    Ext.getCmp('idNombreEnvioPG').setValue(obj.NOMBRES);
                    Ext.getCmp('idApellidoEnvioPG').setValue(obj.APELLIDOS);
                    Ext.getCmp('idFechaNacimientoPG').setValue(obj.FECHA_NAC);
                    Ext.getCmp('idFechaEmisionDocPG').setValue(obj.FECHA_DOC);
                    Ext.getCmp('idDireccionEnvioPG').setValue(obj.DIRECCION);
                    Ext.getCmp('idTelefonoEnvioPG').setValue(obj.TELEFONO);
                    Ext.getCmp('idProfesionPG').setValue(obj.PROFESION);
                    
                    Ext.getCmp('idAddClienteRemitentePG').disable();
                    Ext.getCmp('idNombreEnvioPG').disable();
                    Ext.getCmp('idApellidoEnvioPG').disable();
                    Ext.getCmp('idFechaNacimientoPG').disable();
                    Ext.getCmp('idFechaEmisionDocPG').disable();
                    Ext.getCmp('idDireccionEnvioPG').disable();
                    Ext.getCmp('idTelefonoEnvioPG').disable();
                    Ext.getCmp('idProfesionPG').disable();
                    
                    Ext.getCmp('idDocumentoEnvioPG').disable();
                    
                    Ext.getCmp('idDatosBeneficiarioPG').enable();
                    Ext.getCmp('idDatosEnvioPG').enable();
                    Ext.getCmp('idCodigoClientePG').focus(true,true);
                } else {
                    
                    Ext.getCmp('idAddClienteRemitentePG').enable();
                    Ext.getCmp('idNombreEnvioPG').enable();
                    Ext.getCmp('idApellidoEnvioPG').enable();
                    Ext.getCmp('idFechaNacimientoPG').enable();
                    Ext.getCmp('idFechaEmisionDocPG').enable();
                    Ext.getCmp('idDireccionEnvioPG').enable();
                    Ext.getCmp('idTelefonoEnvioPG').enable();
                    Ext.getCmp('idProfesionPG').enable();
                    
                    Ext.getCmp('idNombreEnvioPG').reset();
                    Ext.getCmp('idApellidoEnvioPG').reset();
                    Ext.getCmp('idFechaNacimientoPG').reset();
                    Ext.getCmp('idFechaEmisionDocPG').reset();
                    Ext.getCmp('idDireccionEnvioPG').reset();
                    Ext.getCmp('idTelefonoEnvioPG').reset();
                    Ext.getCmp('idProfesionPG').reset(); 
                    
                    Ext.getCmp('idDatosBeneficiarioPG').disable();
                    Ext.getCmp('idDatosEnvioPG').disable();
                    
                    Ext.Msg.show({
                        title: 'Estado',
                        msg: obj.MENSAJE,
                        animEl: 'elId',
                        icon: Ext.MessageBox.ERROR,
                        buttons: Ext.Msg.OK,
                        fn: function(){
                            Ext.getCmp('idFechaEmisionDocPG').focus(true,true);
                        }
                    });
                }
            },
            failure: function (action) {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: 'No se pudo realizar la operación',
                    animEl: 'elId',
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });                
            }
        }); 
    }else{
        alert("Favor completar campo Tipo de Documento y Nro. de Documento");
    }    
}

function getDatosEnvio(codRemesa, idTipoDoc, idDoc){
    if(codRemesa != ''){
        var conn = new Ext.data.Connection();    
        conn.request({
            waitTitle: 'Conectando',
            waitMsg: 'Obteniendo datos de envio..',
            url: 'PRACTIGIRO?op=GET_DATOS_ENVIO',
            params: {
                TIPO_DOC: idTipoDoc,
                ID_DOCUMENTO: idDoc, 
                COD_REMESA: codRemesa
            },
            method: 'POST',
            success: function (respuestaServer) {
                var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                Ext.getCmp('idRespuestaGetDatos').setValue(obj.MENSAJE);
                if (obj.success) {                    
                    Ext.getCmp('idCodigoRemesaPG').disable();                    
                    Ext.getCmp('idBtnConfirmarRetiroPG').focus(true, true);
                    Ext.getCmp('idMontoBaseRetiroPG').setValue(obj.MONTO_BASE)
                    Ext.getCmp('idComisionRetiroPG').setValue(obj.COMISION)
                } else {
                    Ext.getCmp('idCodigoRemesaPG').reset();
                    Ext.getCmp('idCodigoRemesaPG').focus(true, true);
                }
            },
            failure: function (action) {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: 'No se pudo realizar la operación',
                    animEl: 'elId',
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
                Ext.getCmp('idBtnConfirmarRetiroPG').disable();
            }
        }); 
    }else{
        Ext.Msg.show({
            title: 'Atención',
            msg: 'Favor ingresar Código de Remesa',
            animEl: 'elId',
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK,
            fn: function(){
                Ext.getCmp('idCodigoRemesaPG').focus(true,true);
            }
        }); 
    }
    
    
}

function verifyClientPago(idTipoDoc, idDoc){    
    
    if(idTipoDoc != '' && idDoc != ''){
        var conn = new Ext.data.Connection();    
        conn.request({
            waitTitle: 'Conectando',
            waitMsg: 'Verificando cliente..',
            url: 'PRACTIGIRO?op=GET_CLIENTE',
            params: {
                TIPO_DOC: idTipoDoc,
                ID_DOCUMENTO: idDoc
            },
            method: 'POST',
            success: function (respuestaServer) {
                var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                if (obj.success) {
                    Ext.getCmp('idNombreRetiroPG').setValue(obj.NOMBRES);
                    Ext.getCmp('idApellidoRetiroPG').setValue(obj.APELLIDOS);
                    Ext.getCmp('idFechaNacimientoRetiroPG').setValue(obj.FECHA_NAC);
                    Ext.getCmp('idFechaEmisionDocRetiroPG').setValue(obj.FECHA_DOC);
                    Ext.getCmp('idDireccionRetiroPG').setValue(obj.DIRECCION);
                    Ext.getCmp('idTelefonoRetiroPG').setValue(obj.TELEFONO);
                    Ext.getCmp('idProfesionRetiroPG').setValue(obj.PROFESION);
                    
                    Ext.getCmp('idAgregarClientePG').disable();
                    Ext.getCmp('idNombreRetiroPG').disable();
                    Ext.getCmp('idApellidoRetiroPG').disable();
                    Ext.getCmp('idFechaNacimientoRetiroPG').disable();
                    Ext.getCmp('idFechaEmisionDocRetiroPG').disable();
                    Ext.getCmp('idDireccionRetiroPG').disable();
                    Ext.getCmp('idTelefonoRetiroPG').disable();
                    Ext.getCmp('idProfesionRetiroPG').disable();
                    Ext.getCmp('idDocumentoRetiroPG').disable();
                    
                    Ext.getCmp('idCodigoRemesaPG').enable();
                    Ext.getCmp('idCodigoRemesaPG').focus(true,true);
                } else {
                    
                    Ext.getCmp('idAgregarClientePG').enable();
                    Ext.getCmp('idNombreRetiroPG').enable();
                    Ext.getCmp('idApellidoRetiroPG').enable();
                    Ext.getCmp('idFechaNacimientoRetiroPG').enable();
                    Ext.getCmp('idFechaEmisionDocRetiroPG').enable();
                    Ext.getCmp('idDireccionRetiroPG').enable();
                    Ext.getCmp('idTelefonoRetiroPG').enable();
                    Ext.getCmp('idProfesionRetiroPG').enable();
                    
                    Ext.getCmp('idNombreRetiroPG').reset();
                    Ext.getCmp('idApellidoRetiroPG').reset();
                    Ext.getCmp('idFechaNacimientoRetiroPG').reset();
                    Ext.getCmp('idFechaEmisionDocRetiroPG').reset();
                    Ext.getCmp('idDireccionRetiroPG').reset();
                    Ext.getCmp('idTelefonoRetiroPG').reset();
                    Ext.getCmp('idProfesionRetiroPG').reset();
                    
                    Ext.getCmp('idCodigoRemesaPG').disable();
                    
                    Ext.Msg.show({
                        title: 'Estado',
                        msg: obj.MENSAJE,
                        animEl: 'elId',
                        icon: Ext.MessageBox.ERROR,
                        buttons: Ext.Msg.OK,
                        fn: function(){
                            Ext.getCmp('idFechaEmisionDocRetiroPG').focus(true,true);
                        }
                    });
                }
            },
            failure: function (action) {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: 'No se pudo realizar la operación',
                    animEl: 'elId',
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });                
            }
        }); 
    }else{
        alert("Favor completar campo Tipo de Documento y Nro. de Documento");
    }    
}

function addClient(idTipoDoc, idDoc, fechaEmision, nombres, apellidos, fechaNac, direccion, telefono, profesion, modulo){    
    if(idTipoDoc != '' && idDoc!='' &&
        fechaEmision != '' && nombres != '' && 
        apellidos != '' && fechaNac != '' && 
        direccion != '' && telefono!= '' && profesion != '' ){
        var conn = new Ext.data.Connection();    
        conn.request({
            waitTitle: 'Conectando',
            waitMsg: 'Verificando cliente..',
            url: 'PRACTIGIRO?op=ALTA_CLIENTE',
            params: {
                TIPO_DOC : idTipoDoc,
                ID_DOCUMENTO : idDoc,
                NOMBRES : nombres,
                APELLIDOS : apellidos,
                FECHA_NAC : fechaNac,
                DIRECCION : direccion,
                TELEFONO : telefono,
                FECHA_DOCUMENTO : fechaEmision,
                PROFESION : profesion
            },
            method: 'POST',
            success: function (respuestaServer) {
                var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                if (obj.success) {                    
                    Ext.Msg.show({
                        title: 'Estado',
                        msg: 'El cliente fue ingresado exitosamente',
                        animEl: 'elId',
                        icon: Ext.MessageBox.INFO,
                        buttons: Ext.Msg.OK,
                        fn: function(){
                            if(modulo == 0){
                                Ext.getCmp('idDatosBeneficiarioPG').enable();
                                Ext.getCmp('idDatosEnvioPG').enable();
                                Ext.getCmp('idDocumentoEnvioPG').disable();
                                Ext.getCmp('idNombreEnvioPG').disable();
                                Ext.getCmp('idApellidoEnvioPG').disable();
                                Ext.getCmp('idFechaNacimientoPG').disable();
                                Ext.getCmp('idFechaEmisionDocPG').disable();
                                Ext.getCmp('idDireccionEnvioPG').disable();
                                Ext.getCmp('idTelefonoEnvioPG').disable();
                                Ext.getCmp('idProfesionPG').disable();
                                Ext.getCmp('idCodigoClientePG').focus(true,true);
                            }else{
                                Ext.getCmp('idDocumentoRetiroPG').disable();
                                Ext.getCmp('idCodigoRemesaPG').enable();
                                Ext.getCmp('idNombreRetiroPG').disable();
                                Ext.getCmp('idApellidoRetiroPG').disable();
                                Ext.getCmp('idFechaNacimientoRetiroPG').disable();
                                Ext.getCmp('idFechaEmisionDocRetiroPG').disable();
                                Ext.getCmp('idDireccionRetiroPG').disable();
                                Ext.getCmp('idTelefonoRetiroPG').disable();
                                Ext.getCmp('idProfesionRetiroPG').disable(); 
                                Ext.getCmp('idCodigoRemesaPG').focus(true,true);
                            }
                        }
                    });
                    
                } else {
                    Ext.Msg.show({
                        title: 'Estado',
                        msg: obj.MENSAJE,
                        animEl: 'elId',
                        icon: Ext.MessageBox.ERROR,
                        buttons: Ext.Msg.OK,
                        fn: function(){
                        }
                    });
                }
            },
            failure: function (action) {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: 'No se pudo realizar la operación',
                    animEl: 'elId',
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        });     
    } else {
        alert("Favor completar todos los campos"); 
    }
}


function getParamComision(monto, isComIncl){
    var conn = new Ext.data.Connection();    
    conn.request({
        waitTitle: 'Conectando',
        waitMsg: 'Verificando cliente..',
        url: 'PRACTIGIRO?op=PARAM_COMISION',
        params: {            
            MONTO: monto,
            IS_COM_INCL: isComIncl
        },
        method: 'POST',
        success: function (respuestaServer) {
            var obj = Ext.util.JSON.decode(respuestaServer.responseText);
            if (obj.success) {    
                if(isComIncl == "N"){
                    var importe = parseInt(Ext.getCmp('idImporteEnvioPG').getRawValue().replace(/,/g, ""));
                    var comision = importe * parseFloat(obj.PORCENTAJE) / 100;
                    Ext.getCmp('idImporteComisionEnvioPG').setValue(addCommas(comision.toString()));
                    Ext.getCmp('idImporteCobrarEnvioPG').setValue(addCommas((comision + importe).toString()));                   
                }else{
                    Ext.getCmp('idImporteComisionEnvioPG').setValue(addCommas(obj.COMISION));
                    Ext.getCmp('idImporteEnvioPG').setValue(addCommas(obj.IMPORTE));                    
                }
                Ext.getCmp('idNombreTablaCom').setValue(obj.TABLA);
                Ext.getCmp('idTablaCom').setValue(obj.ID_TABLA);
            } else {
                Ext.Msg.show({
                    title: 'Estado',
                    msg: obj.MENSAJE,
                    animEl: 'elId',
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK,
                    fn: function(){
                    }
                });
            }
        },
        failure: function (action) {
            Ext.Msg.show({
                title: 'Estado',
                msg: 'No se pudo realizar la operación',
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    });         
}


/*function envioRemesa(idTipoDoc, idDoc, monto, codCliente, nombres, apellidos, ciudad, telefono, movil){
    var conn = new Ext.data.Connection();    
    conn.request({
        waitTitle: 'Conectando',
        waitMsg: 'Verificando cliente..',
        url: 'PRACTIGIRO?op=ENVIO_REMESA',
        params: {
            TIPO_DOC : idTipoDoc,
            ID_DOCUMENTO : idDoc,
            MONTO: monto,
            COD_CLIENTE: codCliente, 
            NOMBRES: nombres,
            APELLIDOS: apellidos,
            CIUDAD: ciudad,
            TELEFONO: telefono,
            MOVIL: movil
        },
        method: 'POST',
        success: function (respuestaServer) {
            var obj = Ext.util.JSON.decode(respuestaServer.responseText);
            Ext.Msg.show({
                title: 'Estado',
                msg: obj.MENSAJE,
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK,
                fn: function(){
                }
            });
        },
        failure: function (action) {
            Ext.Msg.show({
                title: 'Estado',
                msg: 'No se pudo realizar la operación',
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    });     
}*/


function presentoDDJJ(idTipoDoc, documento, presento, tipoOp, idTrx){
    var conn = new Ext.data.Connection();    
    conn.request({
        waitTitle: 'Guardando',
        waitMsg: 'Favor Aguarde..',
        url: 'PRACTIGIRO?op=SET_BLOQUEO_DDJJ',
        params: {
            PRESENTO : presento,
            TIPO_OP : tipoOp,
            ID_TRX: idTrx,
            TIPO_DOC: idTipoDoc,
            ID_DOCUMENTO: documento           
        },
        method: 'POST',
        success: function (respuestaServer) {
            var obj = Ext.util.JSON.decode(respuestaServer.responseText);
            Ext.Msg.show({
                title: 'Estado',
                msg: obj.MENSAJE,
                animEl: 'elId',
                icon: obj.success ? Ext.MessageBox.INFO : Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK,
                fn: function(){
                    cardInicial();
                }
            });
        },
        failure: function (action) {
            Ext.Msg.show({
                title: 'Estado',
                msg: 'No se pudo realizar la operación',
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    });     
}


function envioRemesa(idTipoDoc, idDoc, monto, codCliente, nombres, apellidos, ciudad, telefono, movil, comision, montoBase, tabla, idTabla, pregSeg, respSeg){
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
            PG : 0,
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
            PG : 0,
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
}