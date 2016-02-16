function reImprimirTicketCS(){
    var comboTRANSACCIONES_CS = getCombo('TRANSACCION_CS','ID_TRANSACCION','TRANSACCION','ID_TRANSACCION','DESCRIPCION_TRANSACCION','Transacción','ID_TRANSACCION','DESCRIPCION_TRANSACCION','ID_TRANSACCION','TRANSACCION');
    comboTRANSACCIONES_CS.allowBlank= false;
    comboTRANSACCIONES_CS.anchor = '87%';
    /*var transaccionCS = new Ext.form.NumberField({
        name : 'ID_TRANSACCION',
        id :'idTransaccionCS',
        fieldLabel : 'Transacción',
        allowBlank : false,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13){
                    botonOK.focus(true,true);
                }
            }
        }
    });*/
    comboTRANSACCIONES_CS.addListener('specialkey',
        function(esteObjeto, esteEvento)   {
            if(esteEvento.getKey() == 13){
               Ext.getCmp('idBtnReimprimir').focus(true,true);
            }
        }, true);
    var botonOK = new Ext.Button({
        text : 'OK',
        id:'idBtnReimprimir',
        formBind : true,
        handler : function() {
            win.focus();
            formPanel.getForm().submit({
                url : 'COBRO_SERVICIOS?op=REIMPRIMIR_CS',
                method : 'POST',
                waitTitle: 'Conectando',
                waitMsg :'Procesando..',
                timeout : TIME_OUT_AJAX,
                success : function(form,action1) {
                    win.close();
                    var obj = Ext.util.JSON .decode(action1.response.responseText);
                    if(obj.success){
                        var win1 = new Ext.Window({
                            title:'R E M I M P R E S I Ó N',
                            autoWidth : true,
                            height : 'auto',
                            closable : false,
                            resizable : false,
                            defaultButton:0,
                            buttonAlign:'center',
                            buttons : [{
                                id:'idBtnTicketReimpresion',
                                text : 'Imprimir Ticket',
                                formBind : true,
                                handler : function() {
                                    imprimirImpresoraExterna(obj.ticketImpresora);
                                }
                            },{
                                text : 'ok',
                                handler : function() {
                                    win1.close();
                                }
                            }],
                            items : [{
                                html:'<H1>'+obj.ticketPantalla+'</H1>'
                            }]
                        });
                        win1.show();
                    }else{
                        callBackServidor(obj);

                    }
                },
                failure : function(form,action) {
                    var obj = Ext.util.JSON .decode(action.response.responseText);
                    callBackServidor(obj);
                }
            });
        }

    }); 
    var passwordSupervisor = new Ext.form.TextField({
        fieldLabel:'Password Supervisor',
        name:'passwordSupervisor',
        inputType:'password',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13){
                    comboTRANSACCIONES_CS.focus(true,true);
                }

            }
        }
    });
    var usuarioSupervisor =   new Ext.form.TextField({
        fieldLabel:'User Supervisor',
        name:'userSupervisor',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13){
                    passwordSupervisor.focus(true,true);
                }
            }
        }
    });
    var formPanel = new Ext.FormPanel({
        labelWidth : 130,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        //activeItem:0,
        items : [usuarioSupervisor,passwordSupervisor,comboTRANSACCIONES_CS],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Reimpresión de Tickets de Servicios',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();
}