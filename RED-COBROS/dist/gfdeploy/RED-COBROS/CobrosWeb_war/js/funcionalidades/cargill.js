function panelCargill(){    
    var panelCargill = {
        id : 'panelCargill',
        xtype : 'panel',
        title:'CARGILL',
        layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true,
        tbar : [ {
            text : 'Salir (Alt+q)',
            iconCls:'logout',
            handler : function(){
                cardInicial();
            }
        }],
        items : [cabeceraRetencionCargill(),
        CargillBoxBackground()]
    }    
    return panelCargill;    
}

function cabeceraRetencionCargill(){
    
    var comboMoneda = new Ext.form.ComboBox({
        fieldLabel: 'Moneda',
        name : 'tipoMoneda',
        hiddenName : 'tipoMoneda',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : '',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['600', 'GUARANIES'],
            ['700',  'DÓLARES']]
        })
    });
    
    var fecha = new Ext.form.DateField({
        fieldLabel : 'Fecha Boleta',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y'
    });
    
    
    comboMoneda.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('timbradoCargill').focus(true, true);
    }, null,null ) ;

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Retención Cargill',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [{
                xtype:'textfield',
                fieldLabel: 'Nro. Cliente',            
                id :'idNroClienteCargill',
                allowBlank : false,
                width: '100',
                maxLength : 8,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('rucCargill').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'RUC/CI',            
                id :'rucCargill',
                allowBlank : false,
                width: '100',
                maxLength : 40,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('razonSocialCargil').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Razon Social',            
                id :'razonSocialCargil',
                allowBlank : false,
                width: '150',
                maxLength : 40,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('nroBoletaCargill').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Nro. Boleta de Pago',            
                id :'nroBoletaCargill',
                allowBlank : false,
                width: '100',
                maxLength : 8,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('nroFacturaCargill').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Nro. de Factura',            
                id :'nroFacturaCargill',
                allowBlank : false,
                width: '100',
                maxLength : 8,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('montoCargill').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Importe',            
                id :'montoCargill',
                allowBlank : false,            
                width: '100', 
                maxLength : 40,
                monitorValid: true,
                enableKeyEvents:true,
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));                            
                    
                    },                 
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            comboMoneda.focus(true,true);
                        }
                    }
                }
            },comboMoneda,{
                xtype:'textfield',
                fieldLabel: 'Timbrado',            
                id :'timbradoCargill',
                allowBlank : false,
                width: '100',
                maxLength : 8,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            fecha.focus(true,true);
                        }
                    }
                }
            },fecha]
        }
     
    }];

    var cargillRetencionFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idCargillRetencionFormPanel',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 0px 40px 0px 40px',
        monitorValid : true,
        items: [
        {
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.8',
                border: false
            },
            items: individual
        }
        ],
        buttonAlign:'left',
        buttons: [{
            text: 'Aceptar',
            id :'idBtnAceptarRetCargill',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        CARGILL:0,
                        SERVICIO: 153,
                        TIPO_DE_PAGO: 1,
                        NRO_TELEFONO:Ext.getCmp("nroFacturaCargill").getValue(),
                        NRO_CLIENTE:Ext.getCmp("idNroClienteCargill").getValue(),
                        RAZON_SOCIAL:Ext.getCmp("razonSocialCargil").getValue(),
                        RUC: Ext.getCmp("rucCargill").getValue(),
                        NRO_BOLETA:Ext.getCmp("nroBoletaCargill").getValue(),                        
                        MONTO_CARGAR:Ext.getCmp("montoCargill").getValue(),
                        MONEDA: comboMoneda.getValue(),
                        TIMBRADO:Ext.getCmp("timbradoCargill").getValue(),
                        FECHA_BOLETA:fecha.getRawValue()                        
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
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idHeaderRetenionesCargill').getForm().reset();
                Ext.getCmp('idNroCliente').focus(true,true);
            }
        }]
    });
    return cargillRetencionFormPanel;
}

function CargillBoxBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkCargill.png\')',
        height : 260
    })
    return backgroundPanel;
}