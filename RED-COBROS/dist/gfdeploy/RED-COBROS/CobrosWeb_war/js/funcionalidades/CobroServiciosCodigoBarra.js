/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.Ajax.timeout=180000;
function panelCobroConCB() {
    var panel = {
        id : 'panelCobroConCB',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : false,
        items : [cabeceraPagoConCB(),serviciosPanel()]
    }
    return panel;
}
function cabeceraPagoConCB(){
    var moreCB =  new Ext.form.Checkbox ({
        id:'idChecKMoreCB',
        fieldLabel: '2do. Codigo de Barras',
        vertical: true,
        columns: 1,
        name: 'MORE_CB',
        listeners : {
            'check' : function(esteObjeto, checked)   {
                Ext.getCmp('idCodigoBarra').focus(true,true);
                if(checked){
                    Ext.getCmp('idCodigoBarra2').enable();
                }else{
                    Ext.getCmp('idCodigoBarra2').disable();
                }
            }
        }
    });
    var codigoBarraFormPanel = new Ext.form.FormPanel({
        labelWidth : 160,
        id:'idFormPanelCodigoDeBarras',
        title:'Pago de servicios',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        //defaultType:'numberfield',
        monitorValid : true,
        items :[moreCB,{
            xtype:'textfield',
            fieldLabel: 'Ingrese Cód. de Barra',
            name: 'CODIGO_DE_BARRA',
            id :'idCodigoBarra',
            allowBlank : false,
            width: '400',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        if(Ext.getCmp('idChecKMoreCB').checked){
                            Ext.getCmp('idCodigoBarra2').focus(true,true);
                        }else{
                            Ext.getCmp('idBtnAceptarCB').focus(true,true);
                        }
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Ingrese 2do. Cód. de Barra',
            name: 'CODIGO_DE_BARRA_2',
            id :'idCodigoBarra2',
            allowBlank : false,
            disabled:true,
            width: '400',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarCB').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCB',
            width:'290',
            style :'padding:0px 0px 0px 163px',
            formBind : true,
            handler: function(){//llamada al autorizador
                var codigoDeBarras=Ext.getCmp("idCodigoBarra").getValue()+Ext.getCmp("idCodigoBarra2").getValue();
                //var codeReturned = checkBarCode(codigoDeBarras);
                /* if(codeReturned!=-99){
                    formDatosAdicionales(codigoDeBarras,codeReturned).show();
                }else{*/
                
                sendToServer(cleanBarCode(codigoDeBarras));
            //  }
            }
        },{
            text: 'Cancelar',
            width:'135',
            handler: function(){
                codigoBarraFormPanel.getForm().reset();
                Ext.getCmp('idCodigoBarra').focus(true,true);
            }
        },{
            text:'Salir',
            width:'135',
            handler: function(){
                cardInicial();
            }
        }]
    });
    return codigoBarraFormPanel;

}

function cleanBarCode(cod){ 
    if(cod.length > 55){
        if(cod.substr(1, 4) == '1188'){
            cod = cod.substr(1, 55);
        }        
    }
    /*if(cod.length == 36 &&  cod.substr(0,3) =="255"){         
        cod = "256" + cod.substr(3,32);
        cod = cod + addDV(cod);       
    }*/
    return cod;
}

function addDV(cod){
    var BASE = new Array(1, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5, 7, 9, 3, 5);
    var result=0;
    for (i = 0; i < 35; i++) {                
        result += (parseInt(BASE[i]) * parseInt(cod[i]));
    }
    return (result / 2) % 10; 
}
function serviciosPanel(){

    var serviciosPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkAll.png\')',
        height : 1024
    })
    return serviciosPanel;
}

function panelCB(pagina){
    if(pagina==1){
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroConCB');
        Ext.getCmp('idCodigoBarra').reset();
        Ext.getCmp('idCodigoBarra').focus(true,true);
    }else if (pagina==2){
        Ext.Msg.show({
            title : "Transacción",
            msg : "Está seguro que desea cancelar la operación?",
            buttons : Ext.Msg.OKCANCEL,
            icon : Ext.MessageBox.WARNING,
            fn : function(btn, text) {
                if(btn=='ok'){
                    Ext.getCmp('idCodigoBarra').reset();
                    Ext.getCmp('idCodigoBarra').focus(true,true);
                    Ext.getCmp('content-panel').layout.setActiveItem('panelCobroConCB');
                }else if(btn=='cancel'){
                    close();
                }
            }
        });
    }
}

function panelConfirmacionCB(){ 
    var panelConfirmacion = {
        id : 'panelConfirmacionDePago',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true
    }

    return panelConfirmacion;

}
function panelTicketCB(){
    var panelTicket = {
        id : 'panelTicketCB',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : true,
        modal : true
    }
    return panelTicket;
}



function checkBarCode(barCode){
    /*if(barCode.length == 24){//ANDE
        return 1;
    }else */
    var codEmp = barCode.substring(0, 3);
    var isCodEmp = new Boolean(false)
    switch (codEmp){
        case '541':case '038': case '990':case '573':
            isCodEmp = true;

    }
    if(barCode.length == 27 && isCodEmp){
        return 2;
    }else if(barCode.length == 28 &&  barCode.substring(0, 4)=='0509'){
        return 2;
    }
    return -99;

}

function formDatosAdicionales(codigoDeBarras, serviceId){
    var itemsFormAdicional;
    var focusWidget;
    var aditionalData = false;
        
    switch(serviceId){
        case 1:
            focusWidget = 'idTipoFactura';
            itemsFormAdicional=new Ext.form.ComboBox({
                fieldLabel: 'Tipo Factura',
                id:'idTipoFactura',
                name : 'TIPO_FACTURA',
                valueField : 'TIPO',
                anchor:'95%',
                triggerAction: 'all',
                displayField: 'descripcion',
                mode: 'local',
                listWidth : 180,
                allowBlank : false,
                store: new Ext.data.SimpleStore({
                    fields: ['TIPO', 'descripcion'],
                    data : [['1', 'PRIMER AVISO'],
                    ['2',  'SEGUNDO AVISO'],
                    ['3',  'DESCONEXION']]
                }),
                listeners : {
                    'select' : function(esteObjeto, esteEvento)   {
                        Ext.getCmp('btnAceptarDatoAdicional').focus(true,true);
                    }
                }
            });
            break;
        case 2:
            aditionalData=true;
            focusWidget='idAdicionalMontoPagar';
            itemsFormAdicional={
                id:'idAdicionalMontoPagar',
                xtype:'numberfield',
                name: 'MONTO',
                anchor:'95%',
                fieldLabel: 'Monto a Pagar',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('btnAceptarDatoAdicional').focus(true,true);
                        }
                    }
                }
            };          
            break;           
    }


    var datosAdicionalesFormPanel = new Ext.FormPanel({
        id:'idDatosAdicionales',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [itemsFormAdicional],
        buttons: [{
            id : 'btnAceptarDatoAdicional',
            text : 'Aceptar',
            formBind : true,
            handler : function() {
                if(aditionalData){
                    sendToServer(codigoDeBarras+pad(Ext.getCmp('idAdicionalMontoPagar').getValue(),10));
                }else{
                    sendToServer(codigoDeBarras);
                }
                Ext.getCmp('idDatosAdicionales').close();
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('idCodigoBarra').focus(true,true);
                Ext.getCmp('idDatosAdicionales').close();
            }
        }]
    });

    var win = new Ext.Window({
        title:'Datos Adicionales',
        id : 'idDatosAdicionales',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [datosAdicionalesFormPanel],
        listeners:{
            'render' : function(esteObjeto)   {
                Ext.getCmp(focusWidget).focus(true,true);
            }
        }
    });
    return win;
}

function sendToServer(codigoDeBarras){

    Ext.Msg.wait('Procesando... Por Favor espere...');
    Ext.getCmp('panelConfirmacionDePago').load({
        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO',
        params:{
            CODIGO_DE_BARRA:codigoDeBarras
        },
        discardUrl: false,
        nocache: true,
        text: "Procesando... Por Favor espere...",
        timeout: TIME_OUT_AJAX,
        scripts: true
    });
    Ext.Msg.hide();
    Ext.getCmp('content-panel').layout.setActiveItem('panelConfirmacionDePago');
}

function pad(number, length) {

    var str = '' + number;
    while (str.length < length) {
        str = '0' + str;
    }

    return str;

}


function DiferenciaFechas (fechaFac, tipoFac) {
    if(tipoFac!=""){
        var diasGracia = 10;
        var today = new Date();
        var formDay = new fecha(fechaFac) ;
        var formDayToDate = new Date( formDay.anio, formDay.mes-1, formDay.dia )
 
        var diferencia = today.getTime() - (formDayToDate.getTime()+diasGracia*1000*60*60*24);
        var dias = Math.floor(diferencia/(1000 * 60 * 60 * 24));
        if(dias > 0){            
            document.getElementById("idBtnEnviar").disabled = true;
            alert("Factura Vencida");
        }else{
            document.getElementById("idBtnEnviar").disabled = false;
        }
    }else{
        alert("No se eligio Tipo de Factura");
    }
}
function fecha(cadena) {
    var separador = "/"

    if ( cadena.indexOf( separador ) != -1 ) {
        var posi1 = 0
        var posi2 = cadena.indexOf( separador, posi1 + 1 )
        var posi3 = cadena.indexOf( separador, posi2 + 1 )
        this.dia = cadena.substring( posi1, posi2 )
        this.mes = cadena.substring( posi2 + 1, posi3 )
        this.anio = cadena.substring( posi3 + 1, cadena.length )
    } else {
        this.dia = 0
        this.mes = 0
        this.anio = 0
    }
}