/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.Ajax.timeout=180000;
function panelVentaDeMinutos() {
    Ext.DomHelper.useDom = true;
    var panel = {
        id : 'panelVentaDeMinutos',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : false,
        items : [cabeceraVentaDeMinutos(),telefoniasPanel()]
    }
    return panel;
}
var workingTelRC = false;
var workingTelXNRO = false;
function panelPagoFacXNroTel() {
    Ext.DomHelper.useDom = true;
    var panel = {
        id : 'panelPagoFacXNroTel',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : false,
        items : [cabeceraPagoFacturaXNroTel(),telefoniasPanel()]
    }
    return panel;
}
function cabeceraVentaDeMinutos(){
    var michimi =  new Ext.form.Checkbox ({
        id:'idIsMichimiVox',
        fieldLabel: 'Michimi Vox',
        vertical: true,
        columns: 1,
        listeners : {
            'check' : function(esteObjeto, checked)   {
                Ext.getCmp('idNroTelefonoVM').focus(true,true);
                if(checked){
                    Ext.getCmp('idMontoCargarVM').disable();
                    Ext.getCmp('idMontoMichimi').enable();
                }else{
                    Ext.getCmp('idMontoCargarVM').enable();
                    Ext.getCmp('idMontoMichimi').disable();
                }
            }
        }
    });
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Recarga',
            autoHeight: true,
            width: 600,
            // defaultType: 'textfield',
            items: [{
                layout:'column',
                items:[{
                    //columnWidth:.15,
                    layout: 'form',
                    items: [michimi,{
                        xtype:'numberfield',
                        fieldLabel: 'Número de Teléfono',
                        name: 'NRO_CELULAR',
                        id :'idNroTelefonoVM',
                        allowBlank : false,
                        width: '120',
                        maxLength:'9',
                        listeners: {                            
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    esteObjeto.hide();
                                    Ext.getCmp('idNroTelfConfirmacionVM').focus(true,true);
                                }
                            }
                        }
                    },{
                        xtype:'numberfield',
                        fieldLabel: 'Confirmar Número',
                        name: 'NRO_CONFIRMARCION',
                        id :'idNroTelfConfirmacionVM',
                        allowBlank : false,
                        width: '120',
                        maxLength:'9',
                        listeners: {
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                                    if(Ext.getCmp('idNroTelefonoVM').getValue()!=esteObjeto.getValue()){
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : "Los números no coinciden.",
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn: function(btn){
                                                if(btn == 'ok'){
                                                    Ext.getCmp('idNroTelefonoVM').show();
                                                    esteObjeto.reset();
                                                    Ext.getCmp('idNroTelefonoVM').focus(true,true);
                                                }
                                            }
                                        });
                                    }else{
                                        Ext.getCmp('idNroTelefonoVM').show();
                                        Ext.getCmp('idMontoCargarVM').focus(true,true);                               
                                    }
                                }
                            }
                        }
                    },
                    {
                        xtype:'textfield',
                        fieldLabel: 'Monto a Cargar',
                        name: 'MONTO_CARGAR',
                        id :'idMontoCargarVM',
                        allowBlank : false,
                        width: '120',
                        enableKeyEvents:true,
                        listeners: {
                            'keyup' : function(esteObjeto, esteEvento)   {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                            },
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(!Ext.getCmp('idMontoCargarVM').getValue()==""){
                                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                        
                                        if(!(Ext.getCmp('idMontoCargarVM').getValue().replace(/,/g,"")%1000==0)){
                                            Ext.getCmp('idMontoCargarVM').reset();
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : "Monto no permitido",
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR,
                                                fn : function(btn, text) {
                                                    if (btn == "ok") {
                                                        Ext.getCmp('idMontoCargarVM').focus(true,true);
                                                    }
                                                }
                                            });

                                        }else{
                                            Ext.getCmp('idMontoRecibidoVM').focus(true,true);
                                        }
                                    }
                                }
                            },
                            'blur' : function(esteObjeto, esteEvento)   {
                                if(!Ext.getCmp('idMontoCargarVM').getValue()==""){
                                    if(!(Ext.getCmp('idMontoCargarVM').getValue().replace(/,/g,"")%1000==0)){
                                        Ext.getCmp('idMontoCargarVM').reset();
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : "Monto no permitido",
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn : function(btn, text) {
                                                if (btn == "ok") {
                                                    Ext.getCmp('idMontoCargarVM').focus(true,true);
                                                }
                                            }
                                        });

                                    }else{
                                        Ext.getCmp('idMontoRecibidoVM').focus(true,true);
                                    }
                                }
                            }
                        }
                    }]
                },{
                    //columnWidth:.3,
                    layout: 'form',                    
                    bodyStyle: 'padding-left:10px;',
                    items: [getMichimiMonto(),{
                        xtype:'textfield',
                        fieldLabel: 'Monto Recibido',
                        name: 'MONTO_RECIBIDO',
                        id :'idMontoRecibidoVM',
                        allowBlank : true,
                        enableKeyEvents:true,
                        width: '120',
                        listeners: {
                            'keyup' : function(esteObjeto, esteEvento)   {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                                Ext.getCmp('idVueltoVM').setValue(Ext.getCmp('idMontoRecibidoVM').getValue().replace(/,/g,"")-Ext.getCmp('idMontoCargarVM').getValue().replace(/,/g,""));
                            },
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    Ext.getCmp('idBtnConfirmarRecarga').focus(true,true);
                                    Ext.getCmp('idVueltoVM').setValue(Ext.getCmp('idMontoRecibidoVM').getValue().replace(/,/g,"")-Ext.getCmp('idMontoCargarVM').getValue().replace(/,/g,""));
                                }
                            }
                        }
                    },{
                        xtype:'textfield',
                        fieldLabel: 'Vuelto',
                        name: 'VUELTO',
                        id :'idVueltoVM',
                        allowBlank : false,
                        width: '120',
                        disabled:true
                    }]
                }]
            }]
        }
    }];

    var ventaDeMinutosFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idVentaMinutosFormPanel',
        title:'Recarga de Telefonias',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 0px 40px 0px 40px',
        monitorValid : true,
        items: [
        {
            layout: 'column',
            border: true,
            
            items: individual
        }
        ],
        buttonAlign:'left',
        buttons: [{
            formBind : true,
            text :'Confirmar',
            id:'idBtnConfirmarRecarga',
            handler : function(){    
                Ext.getCmp('idNroTelefonoVM').show();
                if(Ext.getCmp('idNroTelefonoVM').getValue()!=Ext.getCmp('idNroTelfConfirmacionVM').getValue()){
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg : "Los números no coinciden.",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn: function(btn){
                            if(btn == 'ok'){
                                Ext.getCmp('idNroTelefonoVM').show();
                                Ext.getCmp('idNroTelfConfirmacionVM').reset();
                                Ext.getCmp('idNroTelefonoVM').reset();                                
                                Ext.getCmp('idNroTelefonoVM').focus(true,true);
                            }
                        } 
                    });                
                }else{
                    var isMichimi = Ext.getCmp('idIsMichimiVox').checked;                    
                    var monto = isMichimi ? Ext.getCmp('idMontoMichimi').getValue() : Ext.getCmp('idMontoCargarVM').getValue();
                    if(ltrim(monto.replace(/,/g, "" ),'0')==''){
                        Ext.Msg.show({
                            title : 'Atencion!',
                            msg : 'Favor ingrese un monto mayor a cero',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR,
                            fn : function(btn, text) {
                                Ext.getCmp('idMontoCargarVM').reset();
                                Ext.getCmp('idMontoCargarVM').focus(true,true);
                            }
                        });
                    }else{
                        if(!isMichimi){
                            var myMask = new Ext.LoadMask(Ext.getBody(), {
                                msg:"Procesando.. Favor aguarde un momento.."
                            });
                            myMask.show();
                            var conn = new Ext.data.Connection();
                            var randomNumber = Math.floor((Math.random()*10000000)+1);
                            conn.request({
                                url : 'COBRO_SERVICIOS?op=SERVICIO_TELEFONIA'+'&ID_RANDOM='+randomNumber,
                                method : 'POST',
                                params : {
                                    NRO_TELEFONO:Ext.getCmp("idNroTelefonoVM").getValue(),
                                    TIPO_SERVICIO: "0"
                                },
                                success : function(action) {
                                    myMask.hide();
                                    var  obj = Ext.util.JSON.decode(action.responseText);
                                    if(obj.success){                                         
                                        if(!(monto.replace(/,/g,"")%1000==0 && monto.replace(/,/g,"") >= obj.montoMin
                                            && monto.replace(/,/g,"") <= obj.montoMax) && !isMichimi){
                                            Ext.getCmp('idMontoCargarVM').reset();
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : "Monto no permitido, rango valido de "+obj.montoMin+" a "+obj.montoMax,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR,
                                                fn : function(btn, text) {
                                                    if (btn == "ok") {
                                                        Ext.getCmp('idMontoCargarVM').focus(true,true);
                                                    }
                                                }
                                            });
                                        }else{
                                            Ext.Msg.show({
                                                title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                                                msg : '¿Confirma la recarga a ' + obj.descripcion + " ["+Ext.getCmp("idNroTelefonoVM").getValue()+"] "+ "por el monto de " + monto +' Gs.?',
                                                buttons : Ext.Msg.OKCANCEL,
                                                icon : Ext.MessageBox.QUESTION,
                                                fn : function(btn, text) {                           
                                                    if (btn == "ok") {
                                                        if(!workingTelRC){
                                                            workingTelRC=true;
                                                            Ext.Msg.wait('Procesando... Por Favor espere...');
                                                            var randomNumber = Math.floor((Math.random()*10000000)+1);
                                                            Ext.getCmp('panelConsulta').load({
                                                                url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                                                params:{
                                                                    NRO_TELEFONO: "0"+Ext.getCmp("idNroTelefonoVM").getValue(),                                                
                                                                    MONTO_CARGAR: ltrim(Ext.getCmp("idMontoCargarVM").getValue().replace(/,/g,''),'0'),
                                                                    TIPO_DE_PAGO: "1",                                                            
                                                                    SERVICIO: obj.servicio
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
                                    myMask.hide();
                                    Ext.Msg.show({
                                        title :FAIL_TITULO_GENERAL,
                                        msg : FAIL_CUERPO_GENERAL,
                                        buttons : Ext.Msg.ERROR,
                                        animEl : 'elId',
                                        icon : Ext.MessageBox.ERROR
                                    });
                                }
                            });   
                        }else{
                            Ext.Msg.show({
                                title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                                msg : '¿Confirma la recarga a Vox Michimi' +'['+Ext.getCmp("idNroTelefonoVM").getValue()+']' + 'por el monto de ' + monto +' Gs.?',
                                buttons : Ext.Msg.OKCANCEL,
                                icon : Ext.MessageBox.QUESTION,
                                fn : function(btn, text) {                           
                                    if (btn == "ok") {
                                        if(!workingTelRC){
                                            workingTelRC=true;
                                            Ext.Msg.wait('Procesando... Por Favor espere...');
                                            var randomNumber = Math.floor((Math.random()*10000000)+1);
                                            Ext.getCmp('panelConsulta').load({
                                                url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                                params:{
                                                    NRO_TELEFONO: "0" + Ext.getCmp("idNroTelefonoVM").getValue(),                                                
                                                    MONTO_CARGAR: ltrim(monto.replace(/,/g,''),'0'),
                                                    TIPO_DE_PAGO: "1",
                                                    SERVICIO: 43
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
            }            
        },{
            text: 'Reset',
            handler: function(){
                Ext.getCmp('idVentaMinutosFormPanel').getForm().reset();
                Ext.getCmp('idNroTelefonoVM').focus(true,true);
            }
        },{
            text : 'Salir',
            handler : function(){
                ventaDeMinutosFormPanel.getForm().reset();
                cardInicial();
            }
        }]
    });
    return ventaDeMinutosFormPanel;
}

function cabeceraPagoFacturaXNroTel(){

    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','Bancos','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','Bancos');
    comboENTIDAD_FINANCIERA.id="idComboEntidadFinancieraXNro";
    comboENTIDAD_FINANCIERA.addListener( 'select',function(esteCombo, record,  index){
        Ext.getCmp('idNumeroChequeNroTelFac').focus(true,true);
    }, null,null ) ;
    comboENTIDAD_FINANCIERA.disable();

    
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Factura',
            autoHeight: true,
            width: 600,
            // defaultType: 'textfield',
            items: [{
                layout:'column',
                items:[{
                    //columnWidth:.15,
                    layout: 'form',
                    items: [{
                        xtype:'numberfield',
                        fieldLabel: 'Número de Teléfono',
                        name: 'NRO_CELULAR',
                        id :'idNroTelefonoXNro',
                        allowBlank : false,
                        width: '120',
                        maxLength:'9',
                        listeners: {
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    esteObjeto.hide();
                                    Ext.getCmp('idNroTelfConfirmacionXNro').focus(true,true);
                                }
                            }
                        }
                    },{
                        xtype:'numberfield',
                        fieldLabel: 'Confirmar Número',
                        name: 'NRO_CONFIRMARCION',
                        id :'idNroTelfConfirmacionXNro',
                        allowBlank : false,
                        maxLength:'9',
                        width: '120',
                        listeners: {
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                                    if(Ext.getCmp('idNroTelefonoXNro').getValue()!=esteObjeto.getValue()){
                                        Ext.Msg.show({
                                            title : FAIL_TITULO_GENERAL,
                                            msg : "Los números no coinciden.",
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn: function(btn){
                                                if(btn == 'ok'){
                                                    Ext.getCmp('idNroTelefonoXNro').show();
                                                    esteObjeto.reset();
                                                    Ext.getCmp('idNroTelefonoXNro').focus(true,true);
                                                }
                                            }
                                        });
                                    }else{
                                        Ext.getCmp('idNroTelefonoXNro').show();
                                        Ext.getCmp('idMontoCargarXNro').focus(true,true);                               
                                    }
                                }
                            }
                        }
                    },
                    {
                        xtype:'textfield',
                        fieldLabel: 'Monto a Pagar',
                        name: 'MONTO_CARGAR',
                        id :'idMontoCargarXNro',
                        allowBlank : false,
                        width: '120',
                        enableKeyEvents:true,
                        listeners: {
                            'keyup' : function(esteObjeto, esteEvento)   {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                            },
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(!Ext.getCmp('idMontoCargarXNro').getValue()==""){
                                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){                                        
                                        Ext.getCmp('idMontoRecibidoXNro').focus(true,true);                                        
                                    }
                                }
                            },
                            'blur' : function(esteObjeto, esteEvento)   {
                                if(!Ext.getCmp('idMontoCargarXNro').getValue()==""){                                    
                                    Ext.getCmp('idMontoRecibidoXNro').focus(true,true);                                    
                                }
                            }
                        }
                    }]
                },{
                    //columnWidth:.3,
                    bodyStyle: 'padding-left:10px;',
                    layout: 'form',
                    items: [{
                        xtype:'textfield',
                        fieldLabel: 'Monto Recibido',
                        name: 'MONTO_RECIBIDO',
                        id :'idMontoRecibidoXNro',
                        allowBlank : true,
                        enableKeyEvents:true,
                        width: '120',
                        listeners: {
                            'keyup' : function(esteObjeto, esteEvento)   {
                                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                                Ext.getCmp('idVueltoXNro').setValue(Ext.getCmp('idMontoRecibidoXNro').getValue().replace(/,/g,"")-Ext.getCmp('idMontoCargarXNro').getValue().replace(/,/g,""));
                            },
                            'specialkey' : function(esteObjeto, esteEvento)   {
                                if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                                    Ext.getCmp('idBtnConfirmarXNroTel').focus(true,true);
                                    Ext.getCmp('idVueltoXNro').setValue(Ext.getCmp('idMontoRecibidoXNro').getValue().replace(/,/g,"")-Ext.getCmp('idMontoCargarXNro').getValue().replace(/,/g,""));
                                }
                            }
                        }
                    },{
                        xtype:'textfield',
                        fieldLabel: 'Vuelto',
                        name: 'VUELTO',
                        id :'idVueltoXNro',
                        allowBlank : false,
                        width: '120',
                        disabled:true
                    }]
                }]
            }]
        }
    },{
        bodyStyle: 'padding-left:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Forma de Pago',
            autoHeight: true,
            width: 350,
            defaultType: 'radio',
            items: [{
                id:'idPagoXNroTelFormPanelEfectivo',
                checked: true,
                fieldLabel: 'Forma de pago',
                boxLabel: 'Efectivo',
                anchor:'95%',
                name: 'idtipoPagoNroTelFac',
                inputValue: '1',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.disable();
                            Ext.getCmp('idNumeroChequeNroTelFac').disable();
                            Ext.getCmp('idFechaChequeNroTelFac').disable();

                        }
                    },
                    'specialkey' :function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            if(esteEvento.getKey() == 13){
                                Ext.Msg.show({
                                    title : 'Confirmación',
                                    msg : '¿Está seguro que desea abonar en efectivo?',
                                    buttons : Ext.Msg.OKCANCEL,
                                    icon : Ext.MessageBox.WARNING,
                                    fn : function(btn, text) {
                                        if (btn == "ok") {
                                            Ext.getCmp('idBtnAceptarNroTel').focus(true,true);
                                        }else{
                                            Ext.getCmp('idPagoXNroTelFormPanelEfectivo').focus(true,true);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }, {
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Cheque',
                name: 'idtipoPagoNroTelFac',
                id:'idTipoPagoChequeXNro',
                anchor:'95%',
                inputValue: '2',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.enable();
                            Ext.getCmp('idNumeroChequeNroTelFac').enable();
                            Ext.getCmp('idFechaChequeNroTelFac').enable();
                        }
                    },
                    'specialkey' :function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            comboENTIDAD_FINANCIERA.focus(true,true);
                        }
                    }
                }
            },comboENTIDAD_FINANCIERA,
            {
                id:'idNumeroChequeNroTelFac',
                xtype: 'numberfield',
                anchor:'95%',
                name:'nro_cheque',
                fieldLabel:'Número de Cheque',
                allowBlank:false,
                disabled:true,
                enableKeyEvents:true,
                listeners : {
                    'specialkey' :function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idFechaChequeNroTelFac').focus(true,true);
                        }
                    }                   
                }
            },{
                id:'idFechaChequeNroTelFac',
                xtype: 'textfield',
                anchor:'95%',
                emptyText:'ddMMyyyy',
                fieldLabel:'Fecha Cheque',
                allowBlank:false,
                disabled:true,
                enableKeyEvents:true,
                listeners : {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));                        
                    },
                    'blur' :function(esteObjeto, esteEvento){
                        if(esteObjeto.getValue()!='' &&!checkFechaCheque(esteObjeto.getValue())){
                            esteObjeto.reset();
                            esteObjeto.focus(true, true);
                        }                        
                    }            
                }
            }
            ]
        }
    }];

    var ventaDeMinutosFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idPagoFactXNroFormPanel',
        title:'Pago de Facturas',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 0px 40px 0px 40px',
        monitorValid : true,
        items: [
        {
            layout: 'column',
            border: true,            
            items: individual
        }
        ],
        buttonAlign:'left',
        buttons: [{
            formBind : true,
            text :'Confirmar',
            id:'idBtnConfirmarXNroTel',
            handler : function(){
                Ext.getCmp('idNroTelefonoXNro').show();
                if(Ext.getCmp('idNroTelefonoXNro').getValue()!=Ext.getCmp('idNroTelfConfirmacionXNro').getValue()){
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg : "Los números no coinciden.",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn: function(btn){
                            if(btn == 'ok'){
                                Ext.getCmp('idNroTelefonoXNro').show();
                                Ext.getCmp('idNroTelfConfirmacionXNro').reset();
                                Ext.getCmp('idNroTelefonoXNro').reset();                                
                                Ext.getCmp('idNroTelefonoXNro').focus(true,true);
                            }
                        } 
                    });                
                }else{
                    if(ltrim(Ext.getCmp('idMontoCargarXNro').getValue().replace(/,/g, "" ),'0')==''){
                        Ext.Msg.show({
                            title : 'Atencion!',
                            msg : 'Favor ingrese un monto mayor a cero',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR,
                            fn : function(btn, text) {
                                Ext.getCmp('idMontoCargarXNro').reset();
                                Ext.getCmp('idMontoCargarXNro').focus(true,true);
                            }
                        });
                    }else{
                        var myMask = new Ext.LoadMask(Ext.getBody(), {
                            msg:"Procesando.. Favor aguarde un momento.."
                        });
                        myMask.show();
                        var conn = new Ext.data.Connection();
                        var randomNumber = Math.floor((Math.random()*10000000)+1);
                        conn.request({
                            url : 'COBRO_SERVICIOS?op=SERVICIO_TELEFONIA'+'&ID_RANDOM='+randomNumber,
                            method : 'POST',
                            params : {
                                NRO_TELEFONO:Ext.getCmp("idNroTelefonoXNro").getValue(),
                                TIPO_SERVICIO: "1"
                            },
                            success : function(action) {
                                myMask.hide();
                                var  obj = Ext.util.JSON.decode(action.responseText);
                                if(obj.success){
                                    if(obj.servicio != 41 ){
                                        Ext.Msg.show({
                                            title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                                            msg : '¿Confirma abonar a ' + obj.descripcion + " ["+Ext.getCmp("idNroTelefonoXNro").getValue()+"] "+ " el monto de " + Ext.getCmp('idMontoCargarXNro').getValue()+' Gs.?',
                                            buttons : Ext.Msg.OKCANCEL,
                                            icon : Ext.MessageBox.QUESTION,
                                            fn : function(btn, text) {                           
                                                if (btn == "ok") {
                                                    if(!workingTelXNRO){
                                                        workingTelXNRO=true;
                                                        Ext.Msg.wait('Procesando... Por Favor espere...');
                                                        var randomNumber = Math.floor((Math.random()*10000000)+1);
                                                        Ext.getCmp('panelConsulta').load({
                                                            url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
                                                            params:{
                                                                NRO_TELEFONO:"0" + Ext.getCmp("idNroTelefonoXNro").getValue(),                                                
                                                                MONTO_CARGAR:ltrim(Ext.getCmp("idMontoCargarXNro").getValue().replace(/,/g,''),'0'),
                                                                TIPO_DE_PAGO: getRadioButtonSelectedValue(document.getElementsByName("idtipoPagoNroTelFac")),
                                                                SERVICIO: obj.servicio,
                                                                ID_ENTIDAD: document.getElementById("idComboEntidadFinancieraXNro").value,
                                                                NRO_DE_CHEQUE: Ext.getCmp("idNumeroChequeNroTelFac").getValue(),
                                                                FECHA_CHEQUE: Ext.getCmp("idFechaChequeNroTelFac").getValue()
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
                                    }else{
                                        Ext.Msg.show({
                                            title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                                            msg : 'Factura de Vox se paga por el modulo de consulta y pago, OK para ir.',
                                            buttons : Ext.Msg.OKCANCEL,
                                            icon : Ext.MessageBox.QUESTION,
                                            fn : function(btn, text) {                           
                                                if (btn == "ok") {
                                                    cardConsultaPago();
                                                }
                                            }
                                        }); 
                                    }
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
                                myMask.hide();
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
            }            
        },{
            text: 'Reset',
            handler: function(){
                Ext.getCmp('idPagoFactXNroFormPanel').getForm().reset();
                Ext.getCmp('idNroTelefonoXNro').focus(true,true);
            }
        },{
            text : 'Salir',
            handler : function(){
                ventaDeMinutosFormPanel.getForm().reset();
                cardInicial();
            }
        }]
    });
    return ventaDeMinutosFormPanel;
}

function telefoniasPanel(){

    var serviciosPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkTelefonias.png\')',
        height : 1024
    })
    return serviciosPanel;
}


function getMichimiMonto(){

    var monto = new Ext.form.ComboBox({
        fieldLabel: 'Monto',
        id:'idMontoMichimi',
        valueField : 'TIPO',
        anchor:'90%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth : 120,
        allowBlank : false,
        disabled:true,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['2000', '2000'],
            ['5000', '5000'],
            ['10000', '10000']]
        }),
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                Ext.getCmp('idMontoRecibidoVM').focus(true,true);
            }
        }
    });
    return monto;

}