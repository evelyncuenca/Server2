<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<% String op = request.getParameter("opcion");
   String prefix = "";
   String min = "";
   String max = "";
   if(op.equalsIgnoreCase("Tigo Recarga")){
       prefix="09";
       min = "2000";
       max = "99000";
   }
   if(op.equalsIgnoreCase("Personal Recarga")){
       prefix="09";
       min = "1000";
       max = "100000";
   }
   if(op.equalsIgnoreCase("Claro Recarga")){
       prefix="09";
       min = "1000";
       max = "300000";
   }
   if(op.equalsIgnoreCase("Vox Recarga")){
       prefix="09";
       min = "1000";
       max = "500000";
   }
   if(op.equalsIgnoreCase("Vox Michimi")){
       prefix="096";
   }

   if(op.equalsIgnoreCase("Tigo Recarga")
   || op.equalsIgnoreCase("Personal Recarga")
   || op.equalsIgnoreCase("Claro Recarga")
   || op.equalsIgnoreCase("Vox Recarga")
   || op.equalsIgnoreCase("Vox Michimi")){
%>
 {
        xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 15px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroCelular').focus(true,true);
            }
        },
        items :[{
            layout:'column',
            items:[{
                columnWidth:.15,
                layout: 'form',
                items: [{
                xtype:'textfield',
                fieldLabel: 'Número de Teléfono',
                name: 'NRO_CELULAR',
                id :'idNroCelular',
                allowBlank : false,
                value:'<%=prefix%>',
                width: '120',
                listeners: {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            esteObjeto.hide();
                            Ext.getCmp('idNroConfirmacion').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Confirmar Número',
                name: 'NRO_CONFIRMARCION',
                id :'idNroConfirmacion',
                allowBlank : false,
                width: '120',
                value:'<%=prefix%>',
                listeners: {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                                if(Ext.getCmp('idNroCelular').getValue()!=esteObjeto.getValue()){
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : "Los números no coinciden.",
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR,
                                        fn: function(btn){
                                            if(btn == 'ok'){
                                                Ext.getCmp('idNroCelular').show();
                                                esteObjeto.reset();
                                                Ext.getCmp('idNroCelular').focus(true,true);
                                            }
                                        }
                                    });
                               }else{
                                Ext.getCmp('idNroCelular').show();
                                Ext.getCmp('<%=op.equalsIgnoreCase("Vox Michimi")?"idMontoMichimi":"idMontoCargar"%>').focus(true,true);
                               
                                }
                        }
                    }
                }
            },<%if(op.equalsIgnoreCase("Vox Michimi")){%>
            
                getMichimiMonto()
            
            <%}else{%>
            {
                xtype:'textfield',
                fieldLabel: 'Monto a Cargar',
                name: 'MONTO_CARGAR',
                id :'idMontoCargar',
                allowBlank : false,
                width: '120',
                enableKeyEvents:true,
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                    },
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(!Ext.getCmp('idMontoCargar').getValue()==""){
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                if(!(Ext.getCmp('idMontoCargar').getValue().replace(",","")%1000==0 && Ext.getCmp('idMontoCargar').getValue().replace(",","")>=<%=min%>
                                    && Ext.getCmp('idMontoCargar').getValue().replace(",","")<=<%=max%>)){
                                    Ext.getCmp('idMontoCargar').reset();
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : "Monto no permitido",
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR,
                                        fn : function(btn, text) {
                                            if (btn == "ok") {
                                                Ext.getCmp('idMontoCargar').focus(true,true);
                                            }
                                        }
                                    });

                                }else{
                                    Ext.getCmp('idMontoRecibido').focus(true,true);
                                }
                            }
                        }
                    },
                    'blur' : function(esteObjeto, esteEvento)   {
                        if(!Ext.getCmp('idMontoCargar').getValue()==""){
                            if(!(Ext.getCmp('idMontoCargar').getValue().replace(",","")%1000==0 && Ext.getCmp('idMontoCargar').getValue().replace(",","")>=<%=min%>
                                && Ext.getCmp('idMontoCargar').getValue().replace(",","")<=<%=max%>)){
                                Ext.getCmp('idMontoCargar').reset();
                                Ext.Msg.show({
                                    title : FAIL_TITULO_GENERAL,
                                    msg : "Monto no permitido",
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.ERROR,
                                    fn : function(btn, text) {
                                        if (btn == "ok") {
                                            Ext.getCmp('idMontoCargar').focus(true,true);
                                        }
                                    }
                                });

                            }else{
                                Ext.getCmp('idMontoRecibido').focus(true,true);
                            }
                       }
                    }
                }
            }<%}%>]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                xtype:'textfield',
                fieldLabel: 'Monto Recibido',
                name: 'MONTO_RECIBIDO',
                id :'idMontoRecibido',
                allowBlank : true,
                enableKeyEvents:true,
                width: '120',
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                        Ext.getCmp('idVuelto').setValue(Ext.getCmp('idMontoRecibido').getValue().replace(",","")-Ext.getCmp('<%=op.equalsIgnoreCase("Vox Michimi")?"idMontoMichimi":"idMontoCargar"%>').getValue().replace(",",""));
                    },
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idBtnAceptarVM').focus(true,true);
                            Ext.getCmp('idVuelto').setValue(Ext.getCmp('idMontoRecibido').getValue().replace(",","")-Ext.getCmp('<%=op.equalsIgnoreCase("Vox Michimi")?"idMontoMichimi":"idMontoCargar"%>').getValue().replace(",",""));
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Vuelto',
                name: 'VUELTO',
                id :'idVuelto',
                allowBlank : false,
                width: '120',
                disabled:true
            }]
            }]
        }],
        buttonAlign:'left',
        buttons: [{
            text: 'Aceptar',
            id :'idBtnAceptarVM',
            //width:'110',
            //style :'padding:0px 0px 0px 5px',
            formBind : true,
            handler: function(){//llamada al autorizador
            if(Ext.getCmp('idNroCelular').getValue()!=Ext.getCmp('idNroConfirmacion').getValue()){
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg : "Los números no coinciden.",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn: function(btn){
                            if(btn == 'ok'){
                                Ext.getCmp('idNroCelular').show();
                                Ext.getCmp('idNroConfirmacion').reset();
                                Ext.getCmp('idNroCelular').focus(true,true);
                            }
                        } 
                    });                
            }else{      
                        
                        Ext.Msg.show({
                        title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg : '¿Confirma abonar el monto de '+Ext.getCmp('<%=op.equalsIgnoreCase("Vox Michimi")?"idMontoMichimi":"idMontoCargar"%>').getValue()+' Gs.?',
                        buttons : Ext.Msg.OKCANCEL, 
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {                            
                            if (btn == "ok") {
                                if(!workingTel){
                                    workingTel=true;
                                    Ext.Msg.wait('Procesando... Por Favor espere...');
                                    var randomNumber = Math.floor((Math.random()*1000000)+1);
                                    Ext.getCmp('panelConsulta').load({
                                        url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                        params:{
                                            NRO_TELEFONO:Ext.getCmp("idNroCelular").getValue(),
                                            SERVICIO:Ext.getCmp("idServicioVentaMin").getValue(),
                                            MONTO_CARGAR:Ext.getCmp("<%=op.equalsIgnoreCase("Vox Michimi")?"idMontoMichimi":"idMontoCargar"%>").getValue(),
                                            TIPO_DE_PAGO: getRadioButtonSelectedValue(document.getElementsByName("idtipoPago")),
                                            ID_ENTIDAD: document.getElementById("idComboEntidadFinancieraXC").value,
                                            NRO_DE_CHEQUE:Ext.getCmp("idNumeroChequeVM").getValue()
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
                        }
                    });
            }

            }
        },{
            text: 'Reset',
            handler: function(){
                Ext.getCmp('idHeader').getForm().reset();
                Ext.getCmp('idNroCelular').focus(true,true);
            }
        }]
    }
<%} else {%>
{
        xtype: 'form',
        id: 'idHeader',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 15px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('idNroCelular').focus(true,true);
            }
        },
        items :[{
            layout:'column',
            items:[{
                columnWidth:.15,
                layout: 'form',
                items: [{
                xtype:'textfield',
                fieldLabel: '<%=request.getParameter("tagRef")%>',
                name: 'NRO_CELULAR',
                id :'idNroCelular',
                allowBlank : false,
                <%if(request.getParameter("tagRef").equalsIgnoreCase("Nro telefono"))out.print("value:'09',");%>
                width: '120',
                listeners: {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            esteObjeto.hide();
                            Ext.getCmp('idNroConfirmacion').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Confirmar <%=request.getParameter("tagRef")%>',
                name: 'NRO_CONFIRMARCION',
                id :'idNroConfirmacion',
                allowBlank : false,
                width: '120',
                //value:'09',
                listeners: {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                                if(Ext.getCmp('idNroCelular').getValue()!=esteObjeto.getValue()){
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : "Los números no coinciden.",
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR,
                                        fn: function(btn){
                                            if(btn == 'ok'){
                                                Ext.getCmp('idNroCelular').show();
                                                esteObjeto.reset();
                                                Ext.getCmp('idNroCelular').focus(true,true);
                                            }
                                        }
                                    });
                               }else{
                                Ext.getCmp('idNroCelular').show();
                                Ext.getCmp('idMontoCargar').focus(true,true);

                                }
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Monto a Pagar',
                name: 'MONTO_CARGAR',
                id :'idMontoCargar',
                allowBlank : false,
                width: '120',
                enableKeyEvents:true,
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                    },
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(!Ext.getCmp('idMontoCargar').getValue()==""){
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    Ext.getCmp('idMontoRecibido').focus(true,true);
                            }
                        }
                    },
                    'blur' : function(esteObjeto, esteEvento)   {
                        if(!Ext.getCmp('idMontoCargar').getValue()==""){
                           Ext.getCmp('idMontoRecibido').focus(true,true);
                       }
                    }
                }
            }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                xtype:'textfield',
                fieldLabel: 'Monto Recibido',
                name: 'MONTO_RECIBIDO',
                id :'idMontoRecibido',
                allowBlank : true,
                enableKeyEvents:true,
                width: '120',
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                        Ext.getCmp('idVuelto').setValue(Ext.getCmp('idMontoRecibido').getValue().replace(",","")-Ext.getCmp('idMontoCargar').getValue().replace(",",""));
                    },
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idBtnAceptarVM').focus(true,true);
                            Ext.getCmp('idVuelto').setValue(Ext.getCmp('idMontoRecibido').getValue().replace(",","")-Ext.getCmp('idMontoCargar').getValue().replace(",",""));
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Vuelto',
                name: 'VUELTO',
                id :'idVuelto',
                allowBlank : false,
                width: '120',
                disabled:true
            }]
            }]
        }],
        buttonAlign:'left',
        buttons: [{
            text: 'Aceptar',
            id :'idBtnAceptarVM',
    //        width:'110',
 //           style :'padding:0px 0px 0px 5px',
            formBind : true,
            handler: function(){//llamada al autorizador
                if(Ext.getCmp('idNroCelular').getValue()!=Ext.getCmp('idNroConfirmacion').getValue()){
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg : "Los números no coinciden.",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn: function(btn){
                            if(btn == 'ok'){
                                Ext.getCmp('idNroCelular').show();
                                Ext.getCmp('idNroConfirmacion').reset();
                                Ext.getCmp('idNroCelular').focus(true,true);
                            }
                        } 
                    });                
                }else{
                    if(ltrim(Ext.getCmp('idMontoCargar').getValue().replace(/,/g, "" ),'0')==''){
                        Ext.Msg.show({
                            title : 'Atencion!',
                            msg : 'Favor ingrese un monto mayor a cero',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR,
                            fn : function(btn, text) {
                                Ext.getCmp('idMontoCargar').reset();
                                Ext.getCmp('idMontoCargar').focus(true,true);
                            }
                        });
                    }else{
                        Ext.Msg.show({
                        title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                        msg : '¿Confirma abonar el monto de '+Ext.getCmp('idMontoCargar').getValue()+' Gs.?',
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {                           
                            if (btn == "ok") {
                                if(!workingTel){
                                    workingTel=true;
                                    Ext.Msg.wait('Procesando... Por Favor espere...');
                                    var randomNumber = Math.floor((Math.random()*1000000)+1);
                                    Ext.getCmp('panelConsulta').load({
                                        url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                        params:{
                                            NRO_TELEFONO:Ext.getCmp("idNroCelular").getValue(),
                                            SERVICIO:Ext.getCmp("idServicioPagoXC").getValue(),
                                            MONTO_CARGAR:ltrim(Ext.getCmp("idMontoCargar").getValue().replace(/,/g,''),'0'),
                                            TIPO_DE_PAGO: getRadioButtonSelectedValue(document.getElementsByName("idtipoPago")),
                                            ID_ENTIDAD: document.getElementById("idComboEntidadFinancieraXC").value,
                                            NRO_DE_CHEQUE:Ext.getCmp("idNumeroChequeXC").getValue(),
                                            FECHA_CHEQUE:Ext.getCmp("idFechaChequeXCuenta").getValue()
                                            
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
                            }
                        });
                    }
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                Ext.getCmp('idHeader').getForm().reset();
                Ext.getCmp('idNroCelular').focus(true,true);
            }
        }]
    }
<%}%>