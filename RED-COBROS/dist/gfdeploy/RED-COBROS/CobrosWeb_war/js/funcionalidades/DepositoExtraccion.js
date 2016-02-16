/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function panelDepositoExtraccion(){    
    var panelDepositoExtraccion = {
        id : 'panelDepositoExtraccion',
        xtype : 'panel',
        title:'Deposito/Extraccion',
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
        items : [cabeceraDepositoExtraccion()]
    }    
    return panelDepositoExtraccion;    
}

function cabeceraDepositoExtraccion(){
    
    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','Bancos','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','Bancos');
    comboENTIDAD_FINANCIERA.id="idEntidadDepExt";
    comboENTIDAD_FINANCIERA.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idNumeroChequeDepExt').focus(true,true);
    }, null,null ) ;
    comboENTIDAD_FINANCIERA.allowBlank = false;
    comboENTIDAD_FINANCIERA.disable();

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Cliente',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [{
                xtype:'numberfield',
                fieldLabel: 'Nro. de Cuenta',            
                id :'idNroCuentaDepExt',
                allowBlank : false,
                width: '100',
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idNroCIDepExt').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'numberfield',
                fieldLabel: 'CI Cliente',            
                id :'idNroCIDepExt',
                allowBlank : false,
                width: '100',
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idNroSocioDepExt').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'numberfield',
                fieldLabel: 'Nro. de Socio',            
                id :'idNroSocioDepExt',
                allowBlank : true,
                width: '100',
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idNroCIDepositanteDepExt').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'numberfield',
                fieldLabel: 'CI Depositante',            
                id :'idNroCIDepositanteDepExt',
                allowBlank : false,
                width: '100',
                enableKeyEvents:true,
                monitorValid: true,
                listeners: {                                                
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idMontoDepExt').focus(true,true);
                        }
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Monto',
                id :'idMontoDepExt',
                allowBlank : false,
                enableKeyEvents:true,
                width: '100',
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                        
                    },
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idConfirmMontoDepExt').focus(true,true);
                        }
                    }
                    
                }
            },{
                xtype:'textfield',
                fieldLabel: 'Confirmar monto',
                id :'idConfirmMontoDepExt',
                allowBlank : false,
                enableKeyEvents:true,
                width: '100',
                listeners: {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));                        
                    }
                }
            }/*,{
                id:'idPinDepExt',
                xtype: 'numberfield',
                inputType : 'password',
                fieldLabel:'Pin Extraccion',
                allowBlank:true,
                width: '100',
                enableKeyEvents:true,
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                       // Ext.getCmp('idFechaCheqDepExt').focus(true,true);
                    }
                }
            }*/]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Forma de Pago',
            autoHeight: true,
            width: 350,
            defaultType: 'radio',
            items: [{
                id:'idFormPagoDepExt',
                checked: true,
                fieldLabel: 'Forma de pago',
                boxLabel: 'Efectivo',
                anchor:'95%',
                name: 'idtipoPagoDepExt',
                inputValue: '1',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.disable();
                            Ext.getCmp('idNumeroChequeDepExt').disable();
                            Ext.getCmp('idFechaCheqDepExt').disable();
                        }else{
                            comboENTIDAD_FINANCIERA.focus(true, true);
                        }
                    }
                }
            }, {
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Cheque',
                name: 'idtipoPagoDepExt',
                anchor:'95%',
                inputValue: '2',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.enable();
                            Ext.getCmp('idNumeroChequeDepExt').enable();
                            Ext.getCmp('idFechaCheqDepExt').enable();
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
                id:'idNumeroChequeDepExt',
                xtype: 'numberfield',
                anchor:'95%',
                name:'nro_cheque',
                fieldLabel:'Número de Cheque',
                allowBlank:false,
                disabled:true,
                enableKeyEvents:true,
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idFechaCheqDepExt').focus(true,true);
                    }
                }
            },{
                id:'idFechaCheqDepExt',
                xtype: 'datefield',
                anchor:'95%',
                emptyText:'ddMMyyyy',
                format: 'dmY',
                fieldLabel:'Fecha Cheque',
                allowBlank:false,
                disabled:true,
                enableKeyEvents:true,
                listeners : {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));                        
                    },
                    'blur' :function(esteObjeto, esteEvento){                        
                        if(esteObjeto.getRawValue()!='' && !checkFechaCheque(esteObjeto.getRawValue())){
                            esteObjeto.reset();
                            esteObjeto.focus(true, true);
                        }                        
                    }
                }
            }
            ]
        }
    }];

    var depositoExtraccionFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idDepositoExtraccionFormPanel',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 0px 40px 0px 40px',
        monitorValid : true,
        items: [
        {
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        }
        ],
        buttonAlign:'left',
        buttons: [{
            text: 'Deposito',
            id :'idBtnDeposito',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador
                var monto1 = Ext.getCmp('idMontoDepExt').getValue();
                var monto2 = Ext.getCmp('idConfirmMontoDepExt').getValue();
                if(isEqual(monto1, monto2)){
                    var montoOperacion = parseFloat(Ext.getCmp('idMontoDepExt').getValue().replace(/,/g,""));
                    if(montoOperacion <= 2000000){
                        var referencia = Ext.getCmp('idNroCuentaDepExt').getValue()+";"+Ext.getCmp('idNroCIDepExt').getValue()+";"+" "+";"+Ext.getCmp('idNroCIDepositanteDepExt').getValue();
                        if(Ext.getCmp('idNroSocioDepExt').getValue() != ''){
                            referencia += ";"+ Ext.getCmp('idNroSocioDepExt').getValue();
                        }
                
                        Ext.Msg.show({
                            title : 'Confirmación',
                            msg : '¿Está seguro que desea depositar el monto de '+ Ext.getCmp('idMontoDepExt').getValue() +" Gs. a la Cuenta Nro. " +Ext.getCmp('idNroCuentaDepExt').getValue() + " con CI Nro. " + Ext.getCmp('idNroCIDepExt').getValue()+"?",
                            buttons : Ext.Msg.OKCANCEL,
                            icon : Ext.MessageBox.QUESTION,
                            fn : function(btn, text) {
                                if (btn == "ok") {
                                    confirmFormDepositoExtraccion(122, referencia);
                                }
                            }
                        });
                    }else{
                        Ext.Msg.show({
                            title : 'Atención',
                            msg : 'Monto supera el máximo permitido',
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.WARNING,
                            fn : function(btn, text) {
                                if (btn == "ok") {
                                    Ext.getCmp('idMontoDepExt').reset();
                                    Ext.getCmp('idConfirmMontoDepExt').reset();
                                    Ext.getCmp('idMontoDepExt').focus(true, true);
                                }
                            }
                        });
                    } 
                } else {
                    Ext.Msg.show({
                        title : 'Atención',
                        msg : 'Los montos no coinciden',
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "ok") {
                                Ext.getCmp('idMontoDepExt').reset();
                                Ext.getCmp('idConfirmMontoDepExt').reset();
                                Ext.getCmp('idMontoDepExt').focus(true, true);
                            }
                        }
                    });
                }
            }
        },{
            text: 'Extraccion',
            id :'idBtnExtraccion',
            width:'130',
            hidden: false,
            formBind : true,
            handler: function(){//llamada al autorizador  
                
                if(getRadioButtonSelectedValue(document.getElementsByName("idtipoPagoDepExt")) != 2){
                    
                    Ext.MessageBox.wait("Esperando ingreso de pin...", 'Favor Aguarde'); 
                    callGetUserPin();
                }else{
                    Ext.Msg.show({
                        title : 'Error',
                        msg : "Las extracciones son solo en efectivo",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR
                    });
                    
                }
            }
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idDepositoExtraccionFormPanel').getForm().reset();
                Ext.getCmp('idNroCuentaDepExt').focus(true,true);
            }
        }]
    });
    return depositoExtraccionFormPanel;


}

function sendDeposito(pin){ 
    var referencia = Ext.getCmp('idNroCuentaDepExt').getValue()+";"+Ext.getCmp('idNroCIDepExt').getValue()+";"+ pin +";"+Ext.getCmp('idNroCIDepositanteDepExt').getValue(); 
    if(Ext.getCmp('idNroSocioDepExt').getValue() != ''){
        referencia += ";"+ Ext.getCmp('idNroSocioDepExt').getValue();
    }    
    Ext.Msg.show({
        title : 'Confirmación',
        msg : '¿Está seguro que desea extraer el monto de '+ Ext.getCmp('idMontoDepExt').getValue() +" Gs. de la Cuenta Nro. " +Ext.getCmp('idNroCuentaDepExt').getValue() + " con CI Nro. " + Ext.getCmp('idNroCIDepExt').getValue()+"?",
        buttons : Ext.Msg.OKCANCEL,
        icon : Ext.MessageBox.QUESTION,
        fn : function(btn, text) { 
            if (btn == "ok") {
                confirmFormDepositoExtraccion(123, referencia);
            }
        }
    }); 
}
 
function getPin(cryptedPin){
    var temp = cryptedPin.replace(/:/g,'":"').replace(/{/g,'{"').replace(/}/g,'"}').replace('\"[','[').replace("}]\"","}]");                    
    temp = Ext.util.JSON.decode(temp);  
    sendDeposito(temp.MACS[0].MAC);
}
function callGetUserPin(){
    var pinObtenido = new Ext.data.JsonStore({
        proxy: new Ext.data.ScriptTagProxy({
            url: 'http://localhost:7777/cmd?query=getPin',
            listeners: {
                'exception': function(dataProxy, type, action, options, response, arg) {                                     
                    /*Ext.MessageBox.show({
                        title: 'Error', 
                        msg: 'Hubo un error al intentar obtener el pin', 
                        buttons: Ext.MessageBox.OK, 
                        icon: Ext.MessageBox.ERROR
                    });*/
                }
            }
        })
    });
    pinObtenido.load();
}


function isEqual(monto1, monto2){
    var ok = false;
    if(monto1 == monto2){
        ok = true;
    }
    return ok;
}

function confirmFormDepositoExtraccion(idServicio, refCon){
    var randomNumber = Math.floor((Math.random()*10000000)+1); 
    Ext.Msg.wait('Procesando... Por Favor espere...');
    Ext.getCmp('panelConsulta').load({
        url: 'COBRO_SERVICIOS?op=REALIZAR_COBRANZA'+'&ID_RANDOM='+randomNumber,
        params:{
            NRO_TELEFONO : refCon, 
            SERVICIO : idServicio,
            ID_FACTURADOR : 30,
            MONTO_CARGAR: Ext.getCmp('idMontoDepExt').getValue(),
            TIPO_DE_PAGO: getRadioButtonSelectedValue(document.getElementsByName("idtipoPagoDepExt")),
            ID_ENTIDAD: document.getElementById("idEntidadDepExt").value,
            NRO_DE_CHEQUE:Ext.getCmp("idNumeroChequeDepExt").getValue(),
            FECHA_CHEQUE:Ext.getCmp("idFechaCheqDepExt").getRawValue(),
            DEP_EXT : 0
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