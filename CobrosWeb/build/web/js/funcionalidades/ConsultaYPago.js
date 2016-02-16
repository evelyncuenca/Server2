/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.Ajax.timeout=180000;
function panelConsultaYPago() {
    //Ext.DomHelper.useDom = true;
    var panel = {
        id : 'panelConsultaYPago',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        //height:5000,
        autoScroll : false,
        items : [cabeceraConsultaYPago(),{
            id:'panelDetalleCyP',
            border:false,
            autoWidth:true,
            autoHeight:true
        },consultaYPagoPanel()]
    }
    return panel;
}

function cabeceraConsultaYPago(){
    var ds_providers = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'COBRO_SERVICIOS?op=GET_SERVICIOS&PROPIEDAD_SERVICIO=CONSULTABLE'

        }),
        reader : new Ext.data.JsonReader({
            root : 'FACTURADOR',
            id : 'ID_FACTURADOR',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_FACTURADOR'
        }, {
            name : 'DESCRIPCION_FACTURADOR'
        }])
    });
    var ds_providers_serv = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'COBRO_SERVICIOS?op=GET_SERVICIOS&PROPIEDAD_SERVICIO=CONSULTABLE'

        }),
        reader : new Ext.data.JsonReader({
            root : 'SERVICIO',
            id : 'ID_SERVICIO',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_SERVICIO'
        }, {
            name : 'DESCRIPCION_SERVICIO'
        }, {
            name : 'TAG_REF'
        }])
    });
    var comboFACTURADOR = new Ext.form.ComboBox({

        fieldLabel : 'FACTURADOR',
        id:'idFacturadorCyP',
        hiddenName : 'TAG_REFERENCIA',
        store : ds_providers,
        displayField : 'DESCRIPCION_FACTURADOR',
        valueField : 'ID_FACTURADOR',
        triggerAction : 'all',
        emptyText : 'FACTURADOR',
        selectOnFocus : true,
        anchor : '95%',
        pageSize : 10,
        listWidth : 180,
        forceSelection : true,
        loadingText : 'Cargando...',
        typeAhead : true,
        minChars :2,
        allowBlank : false
    });
    comboFACTURADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboSERVICIO.store.baseParams['ID_FACTURADOR'] = record.data.ID_FACTURADOR;
   
        comboSERVICIO.store.baseParams['limit'] = 10;
        comboSERVICIO.store.baseParams['start'] = 0;
        comboSERVICIO.reset();
        comboSERVICIO.store.reload();
        comboSERVICIO.enable();
        if(record.data.ID_FACTURADOR != 56 && record.data.ID_FACTURADOR != 59){
            getDetailServicePanelCyP(record.data.ID_FACTURADOR,"Referencia Busqueda");
        }else{
            if(Ext.getCmp('idPanelHeaderCyP')!=undefined){
                Ext.getCmp('idPanelHeaderCyP').destroy();
            }
            alert("Debe Selecccionar un Servicio");
        }
    //getDetailServicePanelVM(esteCombo.getRawValue(),ds_providers.getAt(index).data.TAG_REFERENCIA);
    });    

    var comboSERVICIO = new Ext.form.ComboBox({
        fieldLabel : 'SERVICIO',
        id:'idServicioCyP',
        hiddenName : 'TAG_REFERENCIA',
        store : ds_providers_serv,
        displayField : 'DESCRIPCION_SERVICIO',
        valueField : 'ID_SERVICIO',
        triggerAction : 'all',
        emptyText : 'SERVICIO',
        selectOnFocus : true,
        anchor : '95%',
        pageSize : 10,
        listWidth : 180,
        forceSelection : true,
        loadingText : 'Cargando...',
        typeAhead : true,
        minChars :2,
        allowBlank : false
    });
    comboSERVICIO.addListener( 'select',function(esteCombo, record,  index  ){
        getDetailServicePanelCyP(record.data.ID_SERVICIO,record.data.TAG_REF);
    });
    comboSERVICIO.disable();

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Seleccione un Facturador',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [comboFACTURADOR,comboSERVICIO]
        }
    }];

    var consultaYPagoFormPanelFac = new Ext.FormPanel({
        labelWidth : 130,
        id:'idconsultaYPagoFormPanelFac',
        title:'Consulta y Pago',
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
            text : 'Reset',
            handler : function(){
                consultaYPagoFormPanelFac.getForm().reset();
                if(Ext.getCmp('idPanelHeaderCyP')!=undefined){
                    Ext.getCmp('idPanelHeaderCyP').destroy();
                }
                comboSERVICIO.disable();
            }

        },{
            text : 'Salir',
            handler : function(){
                consultaYPagoFormPanelFac.getForm().reset();
                comboSERVICIO.disable();
                cardInicial();
            }
        }]
    });
    return consultaYPagoFormPanelFac;
}
function panelCyP(pagina){
    if(pagina==1){
        cardInicial();
    }else if (pagina==2){
        Ext.Msg.show({
            title : "Transacción",
            msg : "Está seguro que desea cancelar la operación?",
            buttons : Ext.Msg.OKCANCEL,
            icon : Ext.MessageBox.QUESTION,
            fn : function(btn, text) {
                if(btn=='ok'){
                    cardInicial();               
                }else if(btn=='cancel'){
                    close();
                }
            }
        });
    }else if(pagina==3){
        if(Ext.getCmp('idPanelHeaderTelefonia')!=undefined){
            Ext.getCmp('idPanelHeaderTelefonia').destroy();
        }
        Ext.getCmp('content-panel').layout.setActiveItem('panelPagoFacturaPorNumeroCuenta');
        Ext.getCmp('idServicioVentaMin').reset();
    }else if(pagina==4){
        Ext.getCmp('content-panel').layout.setActiveItem('panelCobroConCB');
        Ext.getCmp('idFormPanelCodigoDeBarras').getForm().reset();
        Ext.getCmp('idCodigoBarra').focus(true,true);
        
    }else if(pagina==5){
        cardDepositoExtraccion();
    }
}
var workingCyP = false;
function ticketCyP(){
    var randomNumber = Math.floor((Math.random()*10000000)+1);
    var formaDePago = document.getElementById("idNroChequeCyP").disabled?'Efectivo':'Cheque';
    var entidadadFinanciera = document.getElementById("idBancoCyP").value;
    var nroCheque = document.getElementById("idNroChequeCyP").value;
    var fechaCheque = document.getElementById("idFechaChequeCyP").value;
    if(checkValue(false, false, 1)){//monto pago es mayor a cero
        if(getListSelectedValue(document.getElementsByName("NRO_PAGO"))!=""){
            if(formaDePago == "Cheque" && entidadadFinanciera ==""){
                alert("Favor seleccionar un Banco");
            }else if(formaDePago == "Cheque" && nroCheque==""){
                alert("Favor ingresar Nro. de Cheque");
            }else if(formaDePago == "Efectivo" || (formaDePago == "Cheque" && checkFechaCheque(fechaCheque))){
                var monto = parseInt(document.getElementById("idMontoAPagar").value.replace(/,/g,''));
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : '¿Está seguro que desea abonar en '+formaDePago+' el monto de '+addCommas(monto.toString())+'?',
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            if(!workingCyP){
                                workingCyP = true;
                                Ext.Msg.wait('Procesando... Por Favor espere...');
                                Ext.Msg.hide();
                                Ext.getCmp('panelTicketCB').load({
                                    url: 'COBRO_SERVICIOS?op=PROCESAR_CONSULTA'+'&ID_RANDOM='+randomNumber,
                                    params:{
                                        ID_ENTIDAD : document.getElementById("idBancoCyP").value,
                                        TIPO_DE_PAGO : getRadioButtonSelectedValue(document.getElementsByName("TIPO_DE_PAGO")),
                                        NRO_DE_PAGO : getListSelectedValue(document.getElementsByName("NRO_PAGO")),
                                        NRO_DE_CHEQUE : document.getElementById("idNroChequeCyP").value,
                                        SERVICIO : document.getElementById("idServicioCyP").value,
                                        MONTO_CARGAR :document.getElementById("idMontoAPagar").value,
                                        FECHA_CHEQUE: fechaCheque
                                    },
                                    discardUrl: false,
                                    nocache: true,
                                    text: "Procesando... Por Favor espere...",
                                    timeout: TIME_OUT_AJAX,
                                    scripts: true
                                });
                                Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');
                            }
                        }
                    }
                });
            }
        }else{
            Ext.Msg.show({
                title : 'Atención!',
                msg : 'Debe Seleccionar una opcion',
                buttons : Ext.Msg.OK,
                icon : Ext.MessageBox.WARNING,
                fn : function(btn, text) {
                    if (btn == "ok") {
                    }
                }   
            });
        }
    }
}

function checkValue(servExc, calcComision, servicio){    
    var monto = document.getElementById("idMontoAPagar").value;
    if(monto == ''){
        monto = '0'        
    }
    var value = parseInt(monto.replace(/,/g,'')); 
    
    if(value <= 0){
        Ext.Msg.show({
            title : 'Error',
            msg : 'El monto debe ser mayor a cero',
            buttons : Ext.Msg.OK,
            icon : Ext.MessageBox.WARNING,
            fn : function(btn, text) {
                if (btn == "ok") {                   
                }
            }
        });
        return false;
    }else{
        if(servExc && calcComision){
            var comision = 0;
            if(value <= 100000){
                comision = 4400;
            } else if(value > 100000 && value <= 250000){
                comision = 5500;
            } else if(value > 250000 && value <= 500000){
                comision = 6600;
            } else if(value > 500000 && value <= 1000000){
                comision = 7700;  
            } else {                
                comision = value*1.25/100;                
                if(comision > 20000 && servicio == 89){
                    comision = 22000;
                }else{
                    comision = comision*1.1;
                }
            }
            value = Math.round(value + comision);          
            document.getElementById("idMontoAPagar").value = addCommas(String(value));
        }
        return true;
        
    }                
}

function consultaYPagoPanel(){

    var serviciosPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkCYP.png\')',
        height : 440
    })
    return serviciosPanel;
}
function getListSelectedValue(ctrl)
{
    var listSelected ="";
    var moneda;
    for(i=0;i<ctrl.length;i++){
        if(ctrl[i].checked){
            moneda = document.getElementById("idMonedaCyP"+ctrl[i].value).value;
            listSelected =  listSelected + ";;;"+ ctrl[i].value + "#" + moneda  ;
        }
    }
    return listSelected;
}

function setListSelection(ctrl)
{
    var marcado;
    for(i=0;i<ctrl.length;i++)
        if(ctrl[i].checked) marcado=i;

    for(i=0;i<marcado+1;i++){
        ctrl[i].checked=true;
    }
}


function panelConsulta(){
    var panelConfirmacion = {
        id : 'panelConsulta',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true
    }

    return panelConfirmacion;
}
function getRemoteServiceHeaderCyP(servicio,tagRef){
    var resultado = new Ext.ux.Plugin.RemoteComponent({
        url : 'cabeceraServicio.jsp?opcion='+servicio+'&tagRef='+tagRef
    });
    return resultado;
}

function getDetailServicePanelCyP (servicio, tagRef){
    if(Ext.getCmp('idPanelHeaderCyP')!=undefined){
        Ext.getCmp('idPanelHeaderCyP').destroy();
    }
    var mainServicePanel = new Ext.Panel({
        id:'idPanelHeaderCyP',
        enableTabScroll:true,
        autoWidth:true,
        autoHeight:true,
        renderTo: Ext.get('panelDetalleCyP'),
        items: [{
            plugins:[ getRemoteServiceHeaderCyP(servicio, tagRef) ]
        }]
    });
    return mainServicePanel;
}