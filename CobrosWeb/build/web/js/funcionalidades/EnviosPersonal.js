/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function panelEnviosPersonal(){    
    var panelEnvios = {
        id : 'panelEnvios',
        xtype : 'panel',
        title:'Envios Personal',
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
            items:[cabeceraEnvio()]
        },{
            xtype : 'panel',                
            items: [cabeceraRetiro()]
        },enviosPersonalBackground()]
    }    
    return panelEnvios;    
}

function enviosPersonalBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkEnviosPersonal.png\')',
        height : 170
    })
    return backgroundPanel;
}
function cabeceraEnvio(){
    var items = {
        xtype: 'form',
        title :'Envios',
        id: 'idHeaderEnviosPersonal',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        bodyStyle: 'padding:1px 1px 5px 5px;',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroOrigen').focus(true,true);
            }
        },
        items :[{
            layout:'column',
            items:[{
                columnWidth:.25,
                layout: 'form',
                xtype: 'fieldset',
                title: 'Origen',
                bodyStyle: 'padding:5px;',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Nro. Origen',            
                    id :'idNroOrigen',
                    allowBlank : true,
                    width: '100',
                    maxLength : 10,
                    enableKeyEvents:true,
                    listeners: {                        
                        'keyup' : function(esteObjeto, esteEvento)   {
                            esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idConfirmarNroOrigen').focus(true,true);
                            }
                        }
                    }
                },{
                    xtype:'textfield',
                    fieldLabel: 'Confirmar Origen',            
                    id :'idConfirmarNroOrigen',
                    allowBlank : true,
                    width: '100',
                    maxLength : 10,
                    enableKeyEvents:true,
                    listeners: {
                        'keyup' : function(esteObjeto, esteEvento)   {
                            esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idCIOrigen').focus(true,true);
                            }
                        }
                    }
                },{
                    xtype:'numberfield',
                    fieldLabel: 'CI. Origen',
                    id :'idCIOrigen',
                    allowBlank : false,
                    width: '100',
                    enableKeyEvents:true,
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idNroDestino').focus(true,true);
                            }
                        }
                    }
                }]
            },{
                columnWidth:.25,
                layout: 'form',     
                xtype: 'fieldset',
                title: 'Destino',
                bodyStyle: ' padding:5px;',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Nro. Destino',            
                    id :'idNroDestino',
                    allowBlank : false,
                    width: '100',
                    maxLength : 10,
                    enableKeyEvents:true,
                    listeners: {
                        'keyup' : function(esteObjeto, esteEvento)   {
                            esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idConfirmarNroDestino').focus(true,true);
                            }
                        }
                    }
                },{
                    xtype:'textfield',
                    fieldLabel: 'Confirmar Destino',            
                    id :'idConfirmarNroDestino',
                    allowBlank : false,
                    width: '100',
                    maxLength : 10,
                    enableKeyEvents:true,
                    listeners: {
                        'keyup' : function(esteObjeto, esteEvento)   {
                            esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idMontoAEnviar').focus(true,true);
                            }
                        }
                    }
                }/*,{
                    xtype:'numberfield',
                    fieldLabel: 'CI. Destino',
                    id :'idCIDestino',
                    allowBlank : false,
                    width: '100',
                    enableKeyEvents:true,
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idMontoAEnviar').focus(true,true);
                            }
                        }
                    }
                }*/]  
            },{
                columnWidth:.25,
                layout: 'form',
                xtype: 'fieldset',
                title: 'Monto',
                bodyStyle: ' padding:5px;',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Monto a enviar',           
                    id :'idMontoAEnviar',
                    allowBlank : false,
                    width: '100',
                    enableKeyEvents:true,
                    listeners: {
                        'keyup' : function(esteObjeto, esteEvento)   {
                            esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                            esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                            
                            var monto = parseInt(esteObjeto.getRawValue().replace( /,/g, "" )); 
                            if(monto%100 == 0 && monto >= 1000){                                
                                var comision = monto * (localStorage.getItem("COMISION_EP") / 100);
                                Ext.getCmp('idComisionEnvio').setValue(addCommas(comision.toString()));
                                Ext.getCmp('idMontoCobrarEnvio').setValue(addCommas((comision+monto).toString()));
                            }else{
                                Ext.getCmp('idComisionEnvio').reset();
                                Ext.getCmp('idMontoCobrarEnvio').reset();
                            }
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                Ext.getCmp('idConfirmarMontoAEnviar').focus(true,true);
                            }
                        }
                    }
                },{
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
                                Ext.getCmp('idBtnAceptarEnvio').focus(true,true);
                            }
                        }
                    }
                },{
                    xtype:'textfield',
                    fieldLabel: 'Comision',                    
                    id :'idComisionEnvio',
                    allowBlank : false,
                    width: '100',
                    disabled:true
                },{
                    xtype:'textfield',
                    fieldLabel: 'Monto a Cobrar',                    
                    id :'idMontoCobrarEnvio',
                    allowBlank : false,
                    width: '100',
                    disabled:true
                }]
            }]
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarEnvio',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                confirmFormEnvio();
            }
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idHeaderEnviosPersonal').getForm().reset();
                Ext.getCmp('idNroOrigen').focus(true,true);
            }
        }]
    } 
    return items;
}
function confirmFormEnvio(){
    var monto = Ext.getCmp('idMontoAEnviar').getValue();
    var montoConfirm = Ext.getCmp('idConfirmarMontoAEnviar').getValue();
    var origen= Ext.getCmp('idNroOrigen').getValue();
    var origenConfirm= Ext.getCmp('idConfirmarNroOrigen').getValue();
    var destino = Ext.getCmp('idNroDestino').getValue();
    var destinoConfirm = Ext.getCmp('idConfirmarNroDestino').getValue();
    var info = 'No coinciden confirmacion de ';
    errorConfirm = false;
    if(origen!= "" || origenConfirm!=""){
        if(origen != origenConfirm){
            Ext.getCmp('idNroOrigen').reset(); 
            Ext.getCmp('idConfirmarNroOrigen').reset(); 
            errorConfirm = true;
            info +='origen';  
        }        
    }else{
        Ext.getCmp('idNroOrigen').setValue("0");        
    }
    if(destino != destinoConfirm){
        Ext.getCmp('idNroDestino').reset(); 
        Ext.getCmp('idConfirmarNroDestino').reset(); 
        if(errorConfirm){
            info += ', ';
        }
        errorConfirm = true;
        info +='destino';           
    }
    if((monto != montoConfirm)){
        Ext.getCmp('idMontoAEnviar').reset(); 
        Ext.getCmp('idConfirmarMontoAEnviar').reset();         
        if(errorConfirm){
            info += ', ';
        }
        errorConfirm = true;
        info +='monto';          
        
    }else if(!(parseInt(monto.replace(",",""))%100==0) || parseInt(monto.replace(",","")) < 1000){
        errorConfirm = true;
        info = 'El monto debe ser multiplo de 100 y mayor a 1000 guaranies';
    }
    if(errorConfirm){
        Ext.Msg.show({
            title : FAIL_TITULO_GENERAL,
            msg : info,
            buttons : Ext.Msg.OK,
            icon : Ext.MessageBox.ERROR,
            fn: function(btn){
                if(btn == 'ok'){
                    Ext.getCmp('idNroOrigen').show();               
                    Ext.getCmp('idNroOrigen').focus(true,true);
                }
            }
        });
    }else{
        var randomNumber = Math.floor((Math.random()*10000000)+1);
        Ext.Msg.wait('Procesando... Por Favor espere...');
        Ext.getCmp('panelConsulta').load({
            url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
            params:{
                NRO_REFERENCIA:Ext.getCmp("idNroOrigen").getValue()+';'+Ext.getCmp("idCIOrigen").getValue()+';'+Ext.getCmp("idNroDestino").getValue()+';'+"0"+';'+Ext.getCmp("idMontoAEnviar").getValue(),
                SERVICIO:83,
                ID_FACTURADOR:59
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

}
function cabeceraRetiro(){
    var items = {
        xtype: 'form',
        title :'Retiros',
        id: 'idHeaderRetirosPersonal',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroDestionV2').focus(true,true);
            }
        },
        items :[{            
            fieldLabel: 'Nro. Destino',            
            id :'idNroDestionV2',
            allowBlank : false,
            xtype:'numberfield',
            width: '100',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idMontoRetEnvV2').focus(true,true);
                    }
                }
            }
        },{           
            fieldLabel: 'Monto',
            id :'idMontoRetEnvV2',
            allowBlank : false,
            width: '100',
            xtype:'textfield', 
            enableKeyEvents:true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarRetiro').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarRetiro',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador          
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:Ext.getCmp("idNroDestionV2").getValue()+';'+Ext.getCmp("idMontoRetEnvV2").getValue(),
                        SERVICIO:84,
                        ID_FACTURADOR:59
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
                Ext.getCmp('idHeaderRetirosPersonal').getForm().reset();
                Ext.getCmp('idNroDestionV2').focus(true,true);
            }
        }]
    }
    return items;
}