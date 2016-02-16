function panelFastBox(){    
    var panelFast = {
        id : 'panelFast',
        xtype : 'panel',
        title:'Fast Box',
        //layout : 'fit',
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
        items : [{
            xtype : 'panel',            
            items:[cabeceraFlete()]
        },
        {
            xtype : 'panel',                
            items: [cabeceraCompra()]
        },
        FastBoxBackground()]
    }    
    return panelFast;    
}

function cabeceraFlete(){
    var items = {
        title:'Fletes',
        xtype: 'form',
        id: 'idHeaderFlete',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'textfield',
            fieldLabel: 'Nro. Casilla',            
            id :'idCasillaFast',
            allowBlank : false,
            width: '100',
            maxLength : 8,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('nombreClienteFast').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Nombre y Apellido',            
            id :'nombreClienteFast',
            allowBlank : false,
            width: '300',
            maxLength : 40,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('nroGuiaFast').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'Nro. de Guia',            
            id :'nroGuiaFast',
            allowBlank : false,            
            width: '100', 
            maxLength : 10,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('descPaqueteFast').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Descripción del Paquete',            
            id :'descPaqueteFast',
            allowBlank : false,            
            width: '300', 
            maxLength : 90,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idMontoFlete').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Monto a pagar',            
            id :'idMontoFlete',
            name: 'MONTO_CARGAR',
            allowBlank : false,
            width: '100',
            maxLength : 8,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },                                           
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarFleteFast').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarFleteFast',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=FAST_BOX'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:"0"+Ext.getCmp("idCasillaFast").getValue(),
                        NOMBRE:Ext.getCmp("nombreClienteFast").getValue(),
                        NRO_DE_PAGO:Ext.getCmp("nroGuiaFast").getValue(),//Nro de Guia
                        DESCRIPCION:Ext.getCmp("descPaqueteFast").getValue(),
                        MONTO_CARGAR: Ext.getCmp("idMontoFlete").getValue()
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
                Ext.getCmp('idHeaderFlete').getForm().reset();
                Ext.getCmp('idCasillaFast').focus(true,true);
            }
        }]
    } 
    return items;

}

function cabeceraCompra(){
    var items = {
        title:'Compras Online',
        xtype: 'form',
        id: 'idHeaderCompra',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'textfield',
            fieldLabel: 'Nro. Casilla',            
            id :'idCasillaCompra',
            allowBlank : false,
            width: '100',
            maxLength : 8,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('nombreClienteCompra').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Nombre y Apellido',            
            id :'nombreClienteCompra',
            allowBlank : false,
            width: '300',
            maxLength : 40,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('descTiendaCompra').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Tienda de compra',            
            id :'descTiendaCompra',
            allowBlank : false,            
            width: '300', 
            maxLength : 40,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('descProdCompra').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Descripción del Producto',            
            id :'descProdCompra',
            allowBlank : false,            
            width: '300', 
            maxLength : 90,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idMontoCompra').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            name: 'MONTO_CARGAR',
            fieldLabel: 'Monto a pagar',            
            id :'idMontoCompra',
            allowBlank : false,
            width: '100',
            maxLength : 8,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },                                          
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarCompraFast').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCompraFast',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=FAST_BOX'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:"1"+Ext.getCmp("idCasillaCompra").getValue(),
                        NOMBRE:Ext.getCmp("nombreClienteCompra").getValue(),
                        NRO_DE_PAGO:Ext.getCmp("descTiendaCompra").getValue(),//Nro de Guia
                        DESCRIPCION:Ext.getCmp("descProdCompra").getValue(),
                        MONTO_CARGAR: Ext.getCmp("idMontoCompra").getValue()
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
                Ext.getCmp('idHeaderCompra').getForm().reset();
                Ext.getCmp('idCasillaCompra').focus(true,true);
            }
        }]
    } 
    return items;


}

function FastBoxBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkFastBox.png\')',
        height : 170
    })
    return backgroundPanel;
}

