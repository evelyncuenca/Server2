function panelCobranzaPagoConBoletaDePago(){
    var panel = {
        id : 'panelCobranzasPagoConBoletaDePago',
        title:'Pago Con Boleta de Pago',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : true ,
        items : [cobranzasFormularioBoletaDePago()]
    }

    return panel;

}

function cobranzasFormularioBoletaDePago(){

    var comboIMPUESTO =getCombo('CODIGOS_IMPUESTOS','IMPUESTO','IMPUESTO','IMPUESTO','DESCRIPCION_IMPUESTO','Código Obligación','IMPUESTO','DESCRIPCION_IMPUESTO','IMPUESTO','Código Obligación...');

    var fechaCheque = new Ext.form.DateField({
        fieldLabel : 'Fecha Cheque',
        id:'idFechaCheque',
        name : 'FECHA_CHEQUE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        disabled:true,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {

                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('btnRealizarOperacion').focus(true,true);
                }
            }
        }
    });

    comboIMPUESTO.allowBlank=false;
    comboIMPUESTO.addListener( 'select',function(esteCombo, record,  index  ){
        var conn = new Ext.data.Connection();
        conn.request({
            waitTitle : 'Conectando',
            waitMsg : 'Cargando Campos',
            url : 'cobranzas?op=CODIGO_REFERNCIA-LLENAR_CAMPOS',
            params:{
                numeroImpuesto:comboIMPUESTO.getRawValue()

            },
            method : 'POST',
            success : function(respuestaServer) {
                var obj = Ext.util.JSON.decode(respuestaServer.responseText);

                if (obj.success) {
                    if(obj.resolutivo){

                        Ext.getCmp('idNumeroResolucion').enable();
                        Ext.getCmp('idNumeroResolucion').maxLength = 11;
                        Ext.getCmp('idNumeroResolucion').maxLengthText = 'La cantidad máxima de dígitos para este campo es 11';
                        Ext.getCmp('idNumeroResolucion').allowBlank = false;

                    }else{
                        Ext.getCmp('idNumeroResolucion').disable();
                        Ext.getCmp('idNumeroResolucion').allowBlank = true;
                    }
                    for(var ii= 0; ii<obj.total;ii++){
                        Ext.getCmp(obj.items[ii].campo).setValue(obj.items[ii].valor);
                    }
                    if(obj.total==0){
                        Ext.getCmp('idDescripcionObligacion').setValue('');
                    }
                }
            },
            failure : function(action) {

            }
        });
    }, null,null ) ;

    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','Bancos','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','Bancos');
    comboENTIDAD_FINANCIERA.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idNumeroChequeBoletaPago').focus(true,true);
    }, null,null ) ;
    comboIMPUESTO.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idPeriodoFiscalFormPagoBoleta').focus(true,true);
    }, null,null ) ;
    comboENTIDAD_FINANCIERA.disable();
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Búsqueda',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [{
                id:'idRucBoletaPago',
                name: 'ruc',
                fieldLabel: 'RUC',
                anchor:'95%',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {

                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){

                            Ext.getCmp('idDvBoletaPago').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDvBoletaPago',
                name: 'dv',
                anchor:'95%',
                fieldLabel: 'DV',
                allowBlank:false,
                listeners : {
                    'specialkey' :function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            if(true){
                                var conn = new Ext.data.Connection();
                                conn.request({
                                    waitTitle : 'Conectando',
                                    waitMsg : 'Cargando Campos',
                                    url : 'cobranzas?op=RUC-LLENAR_CAMPOS',
                                    params:{
                                        CC4:Ext.getCmp('idRucBoletaPago').getRawValue(),
                                        CC6: Ext.getCmp(esteObjeto.id).getRawValue()

                                    },
                                    method : 'POST',
                                    success : function(respuestaServer) {
                                        var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                        if (obj.success) {
                                            for(var ii= 0; ii<obj.total;ii++){
                                                Ext.Msg.show({
                                                    title: 'Nombre/Razón Social:',
                                                    msg: '<p style="font-size:20px;" align="center">'+obj.items[ii].valor+'</p>',
                                                    autoSize: true,
                                                    animEl: 'elId',
                                                    buttons: Ext.MessageBox.OKCANCEL,
                                                    fn: function(btn){
                                                        if(btn=="ok"){
                                                            comboIMPUESTO.focus(true,true);
                                                        }else{
                                                            Ext.getCmp('idRucBoletaPago').focus(true,true);
                                                        }
                                                    }

                                                });
                                                Ext.getCmp(obj.items[ii].campo).setValue(obj.items[ii].valor);
                                            }
                                            if(obj.total==0){
                                                Ext.getCmp('idNombreRazonSocialBoletaPago').setValue('');
                                            }
                                        //comboIMPUESTO.focus(true,true);
                                        }else {
                                            Ext.getCmp('idRucBoletaPago').reset(),
                                            Ext.getCmp(esteObjeto.id).reset()
                                            Ext.Msg.show({
                                                title : 'Estado',
                                                msg : obj.motivo,
                                                animEl : 'elId',
                                                icon : Ext.MessageBox.ERROR,
                                                buttons : Ext.Msg.OK,
                                                fn:function(){
                                                    Ext.getCmp('idRucBoletaPago').focus(true,true);
                                                }
                                            });
                                        }
                                    },
                                    failure : function(action) {
                                        Ext.Msg.show({
                                            title : 'Estado',
                                            msg : 'No se pudo realizar la operación',
                                            animEl : 'elId',
                                            icon : Ext.MessageBox.ERROR,
                                            buttons : Ext.Msg.OK
                                        });
                                    }
                                });
                            }
                        }
                    }
                }
            }, {
                id:'idNombreRazonSocialBoletaPago',
                name: 'formulario',
                anchor:'95%',
                fieldLabel: 'Nombre/Razón Social',
                disabled:true

            }, comboIMPUESTO,
            {
                id:'idDescripcionObligacion',
                name: 'descripcion_obligacion',
                anchor:'95%',
                fieldLabel: 'Descripción Obligación',
                disabled:true

            }
            ,{
                id:'idPeriodoFiscalFormPagoBoleta',
                xtype:'textfield',
                anchor:'95%',
                name: 'periodo_fiscal',
                fieldLabel: 'Periodo',
                allowBlank: true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {

                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            if (!Ext.getCmp('idNumeroResolucion').disabled){
                                Ext.getCmp('idNumeroResolucion').focus(true,true);
                            }
                            else{
                                Ext.getCmp('idMonto').focus(true,true);
                            }
                        }
                    }
                }

            },{
                id:'idNumeroResolucion',
                xtype:'numberfield',
                anchor:'95%',
                name: 'nro_resolucion',
                fieldLabel: 'Nro. Resolución',
                blankText: 'Número de Resolución Obligatorio',
                disabled:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {

                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){

                            Ext.getCmp('idMonto').focus(true,true);
                        }
                    }
                }
            },{
                id: 'idMonto',
                name: 'monto',
                fieldLabel: 'Total a pagar',
                xtype:'textfield',
                anchor:'95%',
                allowBlank:false,
                enableKeyEvents:true,
                listeners : {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                        montoVERIFICADO= false;
                    },
                    'specialkey' :function(esteObjeto, esteEvento){
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            Ext.getCmp('idConfirmarMontoAPagar').focus(true,true);
                            Ext.getCmp('idMonto').hide();
                        }
                    }
                }

            },{
                id: 'idConfirmarMontoAPagar',
                name: 'monto',
                fieldLabel: 'Confirmar Monto',
                xtype:'textfield',
                anchor:'95%',
                allowBlank:false,
                enableKeyEvents:true,
                listeners : {
                    'keyup' : function(esteObjeto, esteEvento)   {
                        esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                        esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                    },
                    'specialkey' :function(esteObjeto, esteEvento){
                        
                        if(Ext.getCmp('idMonto').getValue()!=Ext.getCmp('idConfirmarMontoAPagar').getValue()){
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : "Los montos no coinciden.",
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR,
                                fn: function(btn){
                                    if(btn == 'ok'){
                                        Ext.getCmp('idMonto').show();
                                        Ext.getCmp('idConfirmarMontoAPagar').reset();
                                        Ext.getCmp('idMonto').focus(true,true);
                                    }
                                }
                            });
                        }else{
                            Ext.getCmp('idMonto').show();
                            Ext.getCmp('idFormaPagoEfectivo').focus(true,true);
                        }
                        
                    }
                }

            }]
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
                id:'idFormaPagoEfectivo',
                checked: true,
                fieldLabel: 'Forma de pago',
                boxLabel: 'Efectivo',
                anchor:'95%',
                name: 'idTipoPago',
                inputValue: '1',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.disable();
                            Ext.getCmp('idNumeroChequeBoletaPago').disable();
                            fechaCheque.disable();

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
                                            Ext.getCmp('btnRealizarOperacion').focus(true,true);
                                        }else{
                                            Ext.getCmp('idFormaPagoEfectivo').focus(true,true);
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
                name: 'idTipoPago',
                anchor:'95%',
                inputValue: '2',
                enableKeyEvents:true,
                listeners : {
                    'check' : function(esteObjeto, checked)   {
                        if(checked){
                            comboENTIDAD_FINANCIERA.enable();
                            Ext.getCmp('idNumeroChequeBoletaPago').enable();
                            fechaCheque.enable();
                        }
                    },
                    'specialkey' :function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                            comboENTIDAD_FINANCIERA.focus(true,true);
                        }
                    }
                }
            },
            comboENTIDAD_FINANCIERA,
            {
                id:'idNumeroChequeBoletaPago',
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
                            Ext.getCmp('idFechaCheque').focus(true,true);
                        // Ext.getCmp('btnRealizarOperacion').focus(true,true);
                        }
                    }
                }
            },fechaCheque
            ]
        }
    }];

    var aceptarTrabajando = false;
    var fp = new Ext.FormPanel({
        id:'formualrio',
        frame: true,
        labelWidth: 130,
        width: 300,
        monitorValid:true,
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
        buttons: [{
            id:'btnRealizarOperacion',
            text: 'Aceptar',
            formBind:true,
            handler: function(esteObjeto){
                // if(fp.getForm().isValid()){
                var contador = 0;                   
                if(!aceptarTrabajando){
                    aceptarTrabajando = true;
                    fp.getForm().submit({
                        method : 'POST',
                        waitTitle : 'Conectando',
                        waitMsg : 'Enviando Información...',
                        url : 'cobranzas?op=COBRO_CON_BOLETA_DE_PAGO',
                        timeout : TIME_OUT_AJAX,
                        success : function(form, accion) {
                            var obj = Ext.util.JSON.decode(accion.response.responseText);
                            if(obj.success){
                                fp.getForm().reset();
                                var win = new Ext.Window({
                                    title:'Info',
                                    autoWidth : true,
                                    height : 'auto',
                                    closable : false,
                                    resizable : false,
                                    modal:true,
                                    defaultButton : 'idBtnCertificarCobroBoleta',
                                    buttons : [{
                                        id: 'idBtnCertificarCobroBoleta',
                                        text : 'Certificar',
                                        formBind : true,
                                        handler : function() {
                                            imprimirImpresoraExterna(obj.certificacion);
                                            contador = contador + 1;

                                            if (contador == 1){
                                                Ext.getCmp('botonOk').setDisabled(false);
                                            }


                                        }
                                    },{
                                        id: 'botonOk',
                                        text : 'Ok',
                                        disabled : true,
                                        handler : function() {
                                            win.close();
                                            Ext.getCmp('idRucBoletaPago').focus(true);

                                        }
                                    },{
                                        id: 'idBotonCancel',
                                        text : 'Cancelar',
                                        disabled : false,
                                        handler : function() {
                                            Ext.Msg.show({
                                                title: 'Atención',
                                                msg:'Esta seguro que desea cancelar la acción?',
                                                buttons: Ext.MessageBox.OKCANCEL,
                                                icon: Ext.MessageBox.WARNING,
                                                fn: function(btn){
                                                    if (btn == 'ok'){
                                                        win.close();
                                                        Ext.getCmp('idRucBoletaPago').focus(true);
                                                    }
                                                }
                                            });
                                        }
                                    }],
                                    items : [{
                                        html:'<H1>'+obj.ticket_pantalla+'</H1>'
                                    }]
                                });
                                win.show();
                                imprimirImpresoraExterna(obj.ticket);
                                aceptarTrabajando = false;
                            }else{
                                Ext.MessageBox.show({
                                    title : 'Estado',
                                    msg: obj.motivo,
                                    width:500,
                                    wait:true,
                                    waitConfig: {
                                        interval:200
                                    },
                                    icon:Ext.MessageBox.ERROR
                                });
                            //callBackServidor(obj);
                            }
                        },
                        failure : function(form, action) {
                            aceptarTrabajando = false;
                            var obj = Ext.util.JSON.decode(action.response.responseText);
                            callBackServidor(obj);

                        }
                    });
                }else{
                    esteObjeto.enable();


                }
                
            }
        },{
            text: 'Reset',
            handler: function(){
                fp.getForm().reset(); 
            }
        },{
            text : 'Salir',
            handler : function(){
                fp.getForm().reset();
                cardInicial();
            }
        }]
    });
    return fp;


}