function panelPurpura(){    
    var panelPurpura = {
        id : 'panelPurpura',
        xtype : 'panel',
        title:'COBROS PURPURA',
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
        items : [cabeceraRetencionPurpura(),
        PurpuraBoxBackground()]
    }    
    return panelPurpura;    
}

function cabeceraRetencionPurpura(){
    
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
            title: 'Datos de Cobro Purpura',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [{
                xtype:'textfield',
                fieldLabel: 'Cedula',            
                id :'rucPurpura',
                allowBlank : false,
                width: '100',
                maxLength : 8,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('nroTelefonoPurpura').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Nº Tel',            
                id :'nroTelefonoPurpura',
                allowBlank : false,
                width: '100',
                maxLength : 40,
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('montoPurpura').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Importe',            
                id :'montoPurpura',
                allowBlank : false,            
                width: '100', 
                maxLength : 40,
                monitorValid: true,
                enableKeyEvents:true,
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        //esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));                            
                    
                    },                 
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            comboMoneda.focus(true,true);
                        }
                    }
                }
            }]
        }
     
    }];

    var purpuraRetencionFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idPurpuraRetencionFormPanel',
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
            id :'idBtnAceptarRetPurpura',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=PURPURA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        PURPURA:0,
                        SERVICIO: 195,
                        TIPO_DE_PAGO: 1,
                        NRO_TELEFONO:Ext.getCmp("nroTelefonoPurpura").getValue(),
                        RUC: Ext.getCmp("rucPurpura").getValue(),
                        MONTO_CARGAR:Ext.getCmp("montoPurpura").getValue()
                        //RUC: Ext.getCmp("rucCargill").getValue(),
                        //NRO_BOLETA:Ext.getCmp("nroBoletaCargill").getValue(),                        
                        //MONTO_CARGAR:Ext.getCmp("montoCargill").getValue(),
                        //MONEDA: comboMoneda.getValue(),
                        //TIMBRADO:Ext.getCmp("timbradoCargill").getValue(),
                        //FECHA_BOLETA:fecha.getRawValue()                        
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
    return purpuraRetencionFormPanel;
}

function PurpuraBoxBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/purpuraAgua3.png\')',
        height : 260
    })
    return backgroundPanel;
}