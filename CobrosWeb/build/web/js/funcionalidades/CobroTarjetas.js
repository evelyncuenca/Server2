/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function panelCobroTarjetas() {
    var panel = {
        id : 'panelCobroTarjetas',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : false,
        items : [cabeceraCobroTarjetas(),serviciosTarjetasPanel()]
    }
    return panel;
}
function cabeceraCobroTarjetas(){
    var conTarjeta =  new Ext.form.Checkbox ({
        id:'idConTarjeta',
        fieldLabel: 'Por Nro. de Tarjeta',
        vertical: true,
        columns: 1,
        name: 'CON_TARJETA',
        listeners : {
            'check' : function(esteObjeto, checked)   {
                Ext.getCmp('idCodigoBarraTarjeta').focus(true,true);
            }
        }
    });
    var tarjetasFormPanel = new Ext.form.FormPanel({
        labelWidth : 160,
        title:'Cobro de tarjetas',
        id:'idFomTarjeta',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        //defaultType:'numberfield',
        monitorValid : true,
        items :[conTarjeta,{
            xtype:'textfield',
            fieldLabel: 'Ingrese Cód. de Barra',
            name: 'CODIGO_DE_BARRA',
            id :'idCodigoBarraTarjeta',
            allowBlank : false,
            width: '400',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  || esteEvento.getKey() == 9){
                        Ext.getCmp('idMontoPagarTarjeta').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Monto a Pagar',
            name: 'MONTO_CARGAR',
            id :'idMontoPagarTarjeta',
            allowBlank : false,
            width: '150',
            enableKeyEvents:true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  || esteEvento.getKey() == 9){
                        esteObjeto.hide();
                        Ext.getCmp('idConfirmarMontoPagarTarjeta').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Confirmar Monto',
            name: 'CONFIRMAR_MONTO',
            id :'idConfirmarMontoPagarTarjeta',
            allowBlank : false,
            width: '150',
            enableKeyEvents:true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  || esteEvento.getKey() == 9){
                        if(Ext.getCmp('idMontoPagarTarjeta').getValue()!=esteObjeto.getValue()){
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : "Los montos no coinciden.",
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR,
                                fn: function(btn){
                                    if(btn == 'ok'){
                                        Ext.getCmp('idMontoPagarTarjeta').show();
                                        esteObjeto.reset();
                                        Ext.getCmp('idMontoPagarTarjeta').focus(true,true);
                                    }
                                }
                            });
                        }else{
                            Ext.getCmp('idMontoPagarTarjeta').show();
                            Ext.getCmp('idBtnAceptarTarjeta').focus(true,true);
                        }
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarTarjeta',
            width:'290',
            style :'padding:0px 0px 0px 163px',
            formBind : true,
            handler: function(){//llamada al autorizador
                if(Ext.getCmp('idMontoPagarTarjeta').getValue()!=Ext.getCmp('idMontoPagarTarjeta').getValue()){
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg : "Los montos no coinciden.",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn: function(btn){
                            if(btn == 'ok'){
                                Ext.getCmp('idMontoPagarTarjeta').show();
                                Ext.getCmp('idConfirmarMontoPagarTarjeta').reset();
                                Ext.getCmp('idMontoPagarTarjeta').focus(true,true);
                            }
                        }
                    });
                }else{
                    var randomNumber = Math.floor((Math.random()*1000000)+1);
                    Ext.Msg.wait('Procesando... Por Favor espere...');
                    Ext.getCmp('panelConfirmacionDePagoTarjetas').load({
                        url : 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO&ct=TRUE'+'&ID_RANDOM='+randomNumber,
                        params:{
                            CODIGO_DE_BARRA:Ext.getCmp("idCodigoBarraTarjeta").getValue(),
                            MONTO_CARGAR:Ext.getCmp("idMontoPagarTarjeta").getValue(),
                            CON_TARJETA:Ext.getCmp("idConTarjeta").getValue()
                        },
                        discardUrl: false,
                        nocache: true,
                        text: "Cargando...",
                        timeout: TIME_OUT_AJAX,
                        scripts: true
                    });
                    Ext.Msg.hide();
                    Ext.getCmp('content-panel').layout.setActiveItem('panelConfirmacionDePagoTarjetas');
                }
            }
        },{
            text: 'Cancelar',
            width:'135',
            handler: function(){
                tarjetasFormPanel.getForm().reset();
                Ext.getCmp('idCodigoBarraTarjeta').focus(true,true);
                Ext.getCmp('idMontoPagarTarjeta').show();
            }
        },{
            text:'Salir',
            width:'135',
            handler: function(){
                cardInicial();
            }
        }]
    });
    return tarjetasFormPanel;

}
function serviciosTarjetasPanel(){

    var serviciosPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkTarjetas.png\')',
        height : 1024
    })
    return serviciosPanel;
}

function panelTarjeta(pagina){
    if(pagina==1){
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroTarjetas');
        Ext.getCmp('idFomTarjeta').getForm().reset();
        Ext.getCmp('idCodigoBarraTarjeta').focus(true,true);
    }else if (pagina==2){
        Ext.Msg.show({
            title : "Transacción",
            msg : "Está seguro que desea cancelar la operación?",
            buttons : Ext.Msg.OKCANCEL,
            icon : Ext.MessageBox.WARNING,
            fn : function(btn, text) {
                if(btn=='ok'){
                    Ext.getCmp('content-panel').layout.setActiveItem('panelCobroTarjetas');
                    Ext.getCmp('idFomTarjeta').getForm().reset();
                    Ext.getCmp('idCodigoBarraTarjeta').focus(true,true);
                }else if(btn=='cancel'){
                    close();
                }
            }
        });
    }
}
function panelConfirmacionTarjetas(){
    var panelConfirmacion = {
        id : 'panelConfirmacionDePagoTarjetas',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true
    }

    return panelConfirmacion;

}
function panelTicketTarjetas(){
    var panelTicket = {
        id : 'panelTicketTarjetas',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : true,
        modal : true
    }
    return panelTicket;
}
