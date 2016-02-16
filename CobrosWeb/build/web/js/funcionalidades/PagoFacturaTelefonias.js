/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.Ajax.timeout=180000;
function panelPagoFacturaPorNumeroCuenta() {
    Ext.DomHelper.useDom = true;
    var panel = {
        id : 'panelPagoFacturaPorNumeroCuenta',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : false,
        items : [cabeceraPagoFacXCuenta(), {
            id:'panelDetallePagoXCuenta',
            border:false,
            autoWidth:true,
            autoHeight:true
        },telefoniasFacPanel()]
    }
    return panel;
}
var workingTel = false;
function cabeceraPagoFacXCuenta(){
    var ds_providers = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'COBRO_SERVICIOS?op=GET_SERVICIOS&PROPIEDAD_SERVICIO=TELEFONIAS'
            
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
            name : 'TAG_REFERENCIA'
        }])
    });
    var comboSERVICIO = new Ext.form.ComboBox({

        fieldLabel : 'SERVICIO',
        id:'idServicioPagoXC',
        hiddenName : 'TAG_REFERENCIA',
        store : ds_providers,
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
        getDetailServicePanelXC(esteCombo.getRawValue(),ds_providers.getAt(index).data.TAG_REFERENCIA);
    });    


    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','Bancos','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','Bancos');
    comboENTIDAD_FINANCIERA.id="idComboEntidadFinancieraXC";
    comboENTIDAD_FINANCIERA.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idNumeroChequeXC').focus(true,true);
    }, null,null ) ;
    comboENTIDAD_FINANCIERA.disable();
    
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Seleccione un Servicio',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [comboSERVICIO]
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
                id:'idPagoXCuentaFormPanelEfectivo',
                checked: true,
                fieldLabel: 'Forma de pago',
                boxLabel: 'Efectivo',
                anchor:'95%',
                name: 'idtipoPago',
                inputValue: '1',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.disable();
                            Ext.getCmp('idNumeroChequeXC').disable();
                            Ext.getCmp('idFechaChequeXCuenta').disable();
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
                                            Ext.getCmp('idBtnAceptarXC').focus(true,true);
                                        }else{
                                            Ext.getCmp('idPagoXCuentaFormPanelEfectivo').focus(true,true);
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
                name: 'idtipoPago',
                anchor:'95%',
                inputValue: '2',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.enable();
                            Ext.getCmp('idNumeroChequeXC').enable();
                            Ext.getCmp('idFechaChequeXCuenta').enable();
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
                id:'idNumeroChequeXC',
                xtype: 'numberfield',
                anchor:'95%',
                name:'nro_cheque',
                fieldLabel:'Número de Cheque',
                allowBlank:false,
                disabled:true,
                enableKeyEvents:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idFechaChequeXCuenta').focus(true,true);
                        }
                    }
                }
            },{
                id:'idFechaChequeXCuenta',
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
                        
                        if(esteObjeto.getValue()!='' && !checkFechaCheque(esteObjeto.getValue())){
                            esteObjeto.reset();
                            esteObjeto.focus(true, true);
                        }
                        
                    }
                }
            }
            ]
        }
    }];

    var pagoFacXCuentaFormPanel = new Ext.FormPanel({
        labelWidth : 130,
        id:'idPagoFacXCuentaFormPanel',
        title:'Pago Factura por Cuenta',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 0px 40px 0px 40px',
        //defaultType:'numberfield',
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
            text : 'Salir',
            handler : function(){
                pagoFacXCuentaFormPanel.getForm().reset();
                cardInicial();
            }
        }]
    });
    return pagoFacXCuentaFormPanel;


}


function telefoniasFacPanel(){

    var serviciosPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkTelefonias.png\')',
        height : 1024
    })
    return serviciosPanel;
}

/**/
function panelCabeceraServicioXC(){
    var panelCabeceraServicio = {
        id : 'panelCabeceraServicioPagoXC',
        xtype : 'panel'   
    }
    return panelCabeceraServicio;
}

function getRemoteServiceHeaderXC(servicio,tagRef){
    var resultado = new Ext.ux.Plugin.RemoteComponent({
        url : 'cabeceraTelefonias.jsp?opcion='+servicio+'&tagRef='+tagRef
    });
    return resultado;
}

function getDetailServicePanelXC (servicio, tagRef){
    if(Ext.getCmp('idPanelHeaderTelefonia')!=undefined){
        Ext.getCmp('idPanelHeaderTelefonia').destroy();
    }
    var mainServicePanel = new Ext.Panel({
        id:'idPanelHeaderTelefonia',
        // title:'Consulta y Pago de '+ servicio,
        enableTabScroll:true,
        autoWidth:true,
        autoHeight:true,
        renderTo: Ext.get('panelDetallePagoXCuenta'),
        items: [{
            plugins:[ getRemoteServiceHeaderXC(servicio, tagRef) ]
        }]
    });
    return mainServicePanel;
}

