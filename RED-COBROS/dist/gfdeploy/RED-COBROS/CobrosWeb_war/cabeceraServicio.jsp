<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%
String opcion = request.getParameter("opcion");
if(opcion.equals("4")){//Copaco
%>
 {
        xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idCodArea').focus(true,true);
            }
        },
        bodyStyle: ' padding:1px 1px 5px 5px',
        items :[{
            layout:'column',
            items:[{
                columnWidth:.08,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Cód. Area',
                    name: 'COD_AREA',
                    id :'idCodArea',
                    allowBlank : false,
                    width: '50',
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13  ){
                                Ext.getCmp('idNroReferencia').focus(true,true);
                            }
                        }
                    }
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Nro Teléfono',
                    name: 'NRO_REFERENCIA',
                    id :'idNroReferencia',
                    allowBlank : false,
                    width: '180',
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13  ){
                                Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                            }
                        }
                    }
                }]
            }]
        },{
            xtype:'label',
            text:'Ejemplo: 21 XXXXX'
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:Ext.getCmp("idCodArea").getValue()+' '+Ext.getCmp("idNroReferencia").getValue(),
                        SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                        ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idNroReferencia').reset();
                Ext.getCmp('idCodArea').reset();
                Ext.getCmp('idCodArea').focus(true,true);
            }
        }/*,{
            text:'Salir',
            width:'130',
            handler: function(){
                cardConsultaPago();
            }
        }*/]
    }
    <%}else if(opcion.equals("46")){//Heraldos%>
    {
       xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        },
        items :[{
            xtype:'textfield',
            fieldLabel: 'C.I.',
            name: 'NRO_REFERENCIA',
            id :'idNroReferencia',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idNombreHeraldo').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Nombre y Apellido',
            name: 'NOMBRE',
            id :'idNombreHeraldo',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idMontoPagarHeraldo').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Monto',
            name: 'MONTO',
            id :'idMontoPagarHeraldo',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador
                var nroReferencia = Ext.getCmp("idNroReferencia").getValue();
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                        params:{
                            NRO_REFERENCIA:';'+nroReferencia+';'+Ext.getCmp("idNombreHeraldo").getValue()+';'+Ext.getCmp("idMontoPagarHeraldo").getValue(),
                            SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                            ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idNroReferencia').reset();
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        }]
    }
    <%}else if(opcion.equals("80")){//Funiber Matricula%>
    {
       xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        },
        items :[{
            xtype:'textfield',
            fieldLabel: 'C.I.',
            name: 'NRO_REFERENCIA',
            id :'idNroReferencia',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idNombreAdicional').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Nombre y Apellido',
            name: 'NOMBRE',
            id :'idNombreAdicional',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idMontoPagarAdicional').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Monto',
            name: 'MONTO',
            id :'idMontoPagarAdicional',
            allowBlank : false,
            width: '260',
            enableKeyEvents:true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                    },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador
                var nroReferencia = Ext.getCmp("idNroReferencia").getValue();
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                        params:{
                            NRO_REFERENCIA:';'+nroReferencia+';'+Ext.getCmp("idNombreAdicional").getValue()+';'+Ext.getCmp("idMontoPagarAdicional").getValue(),
                            SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                            ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idHeader').getForm().reset();
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        }]
    }    
 <%}else if(opcion.equals("83")){//Envios Personal%>
       {
        xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'left',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroOrigen').focus(true,true);
            }
        },
        items :[{
            xtype:'numberfield',
            fieldLabel: 'Nro. Origen',            
            id :'idNroOrigen',
            allowBlank : false,
            width: '100',
            maxLength : 10,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
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
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idNroDestino').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'Nro. Destino',            
            id :'idNroDestino',
            allowBlank : false,
            width: '100',
            maxLength : 10,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idCIDestino').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'CI. Destino',
            id :'idCIDestino',
            allowBlank : false,
            width: '100',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idMontoAEnviar').focus(true,true);
                    }
                }
            }
        },{
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
                    },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador  
                    var randomNumber = Math.floor((Math.random()*10000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                        params:{
                            NRO_REFERENCIA:Ext.getCmp("idNroOrigen").getValue()+';'+Ext.getCmp("idCIOrigen").getValue()+';'+Ext.getCmp("idNroDestino").getValue()+';'+Ext.getCmp("idCIDestino").getValue()+';'+Ext.getCmp("idMontoAEnviar").getValue(),
                            SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                            ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idHeader').getForm().reset();
                Ext.getCmp('idNroOrigen').focus(true,true);
            }
        }]
    } 
<%}else if(opcion.equals("84")){//Retiros Personal%>
       {
       xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idCiDestino').focus(true,true);
            }
        },
        items :[{
            xtype:'numberfield',
            fieldLabel: 'CI. Recibe',            
            id :'idCiDestino',
            allowBlank : false,
            width: '100',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idCodRemesa').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'Codigo de remesa',
            id :'idCodRemesa',
            allowBlank : false,
            width: '100',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador 
                    var randomNumber = Math.floor((Math.random()*10000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                        params:{
                            NRO_REFERENCIA:Ext.getCmp("idCiDestino").getValue()+';'+Ext.getCmp("idCodRemesa").getValue(),
                            SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                            ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idHeader').getForm().reset();
                Ext.getCmp('idCiDestino').focus(true,true);
            }
        }]
    }
<%}else if(opcion.equals("152")){//Cargill%>
{		
        xtype: 'form',		
        id: 'idHeader',		
        monitorValid: true,		
        height: 'auto',		
        width: 'auto',		
        labelAlign: 'top',		
        frame: true,		
        bodyStyle: ' padding:1px 1px 5px 5px',		
        items :[{		
                layout:'column',		
                items:[{		
                        columnWidth:.2,		
                        layout: 'form',		
                        items: [{		
                                xtype:'combo',		
                                fieldLabel:'Consultar Por',		
                                id:'idConsultarPor',		
                                valueField:'metodoConsulta',		
                                queryMode:'local',		
                                store:[		
                                    'Cédula o Ruc',		
                                    'Código de Cliente'],		
                                displayField:'metodoConsulta',		
                                autoSelect:true,		
                                forceSelection:true,		
                                allowBlank:false,
                                triggerAction: 'all'
                        }]		
                },{		
                        columnWidth:.5,		
                        layout:'form',		
                        items:[{		
                                xtype:'textfield',		
                                fieldLabel:'Referencia',		
                                name:'NRO_REFERENCIA',		
                                id:'idNroReferencia',		
                                allowBlank:false,		
                                width:180		
                        }]		
                }]		
        }],		
        buttonAlign:'left',		
        buttons:[{		
            text: 'Aceptar',		
            id :'idBtnAceptarCYP',		
            width:'130',		
            formBind : true,		
            handler: function(){//llamada al autorizador		
                var metCons = Ext.getCmp('idConsultarPor').getValue();		
                //console.log(metCons);		
                var prefijo='';		
                if(metCons==='Cédula o Ruc'){		
                        prefijo='CI';		
                }else if(metCons==='Código de Cliente'){		
                        prefijo='CC';		
                }		
                var randomNumber = Math.floor((Math.random()*10000000)+1);		
                Ext.Msg.wait('Procesando... Por Favor espere...');		
                Ext.getCmp('panelConsulta').load({		
                    url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,		
                    params:{		
                        NRO_REFERENCIA:prefijo + Ext.getCmp("idNroReferencia").getValue(),		
                        SERVICIO:Ext.getCmp("idServicioCyP").getValue(),		
                        ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()		
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
                Ext.getCmp('idNroReferencia').reset();		
                Ext.getCmp('idConsultarPor').reset();		
                Ext.getCmp('idConsultarPor').focus(true,true);		
            }		
        }]		
    }
<%}else{%>
     {
       xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        },
        items :[{
            xtype:'textfield',
            fieldLabel: '<%out.print(request.getParameter("tagRef"));%>',
            name: 'NRO_REFERENCIA',
            id :'idNroReferencia',
            allowBlank : false,
            width: '260',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCYP').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCYP',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador
                var nroReferencia = Ext.getCmp("idNroReferencia").getValue();
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConsulta').load({
                        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                        params:{
                            NRO_REFERENCIA:nroReferencia,
                            SERVICIO:Ext.getCmp("idServicioCyP").getValue(),
                            ID_FACTURADOR:Ext.getCmp("idFacturadorCyP").getValue()
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
                Ext.getCmp('idNroReferencia').reset();
                Ext.getCmp('idNroReferencia').focus(true,true);
            }
        }]
    }
<%}%>