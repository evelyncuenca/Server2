function anularTransaccion(){
    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function() {
            win.focus();
            /*********************************/
            formPanel.getForm().submit({
                url : 'cobranzas?op=DETALLE_TRANSA_A_ANULAR',
                method : 'POST',
                timeout : TIME_OUT_AJAX,
                success : function(form,action1) {
                    win.close();
                    var obj = Ext.util.JSON .decode(action1.response.responseText);
                    if(obj.success){
                        var win11 = new Ext.Window({
                            title:'Transacción seleccionada para ANULAR',
                            autoWidth : true,
                            height : 'auto',
                            closable : false,
                            resizable : false,
                            modal:true,
                            buttons : [{
                                text : 'Cancelar',
                                handler : function() {
                                    win11.close();
                                }
                            },{
                                text : 'Anular',
                                formBind : true,
                                handler : function() {
                                    Ext.Msg.show({
                                        title : 'Estado',
                                        msg : "¿Está seguro que desea Anular esta Transacción?",
                                        buttons : Ext.Msg.OKCANCEL,
                                        animEl : 'elId',
                                        icon : Ext.MessageBox.WARNING,
                                        fn:function(btn){ 
                                            if(btn =="ok"){
                                                win11.close();
                                                formPanel.getForm().submit({
                                                    url : 'cobranzas?op=ANULAR_TRANSACCION',
                                                    method : 'POST',
                                                    timeout : TIME_OUT_AJAX,
                                                    params:{
                                                        passwordSupervisor	:passwordSupervisor.getValue(),
                                                        userSupervisor:usuarioSupervisor.getValue(),
                                                        TRANSACCION:transaccion.getValue(),
                                                        motivoAnulacion     :motivoAnulacion.getValue()
                                                    },
                                                    success : function(form,action1) {
                                                        win.close();
                                                        var obj = Ext.util.JSON .decode(action1.response.responseText);
                                                        if(obj.success){
                                                            var win1 = new Ext.Window({
                                                                title:'Info',
                                                                autoWidth : true,
                                                                height : 'auto',
                                                                closable : false,
                                                                resizable : false,
                                                                buttons : [{
                                                                    text : 'ok',
                                                                    handler : function() {
                                                                        win1.close();
                                                                    }
                                                                },{
                                                                    text : 'Ticket',
                                                                    formBind : true,
                                                                    handler : function() {
                                                                        imprimirImpresoraExterna(obj.ticket);
                                                                    }
                                                                } ],
                                                                items : [{
                                                                    html:'<H1>'+obj.ticket_pantalla+'</H1>'
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


                                            }else{
                                                win11.close();
                                            }

                                        }
                                    });
                                }
                            } ],
                            items : [{
                                html:'<H1>'+obj.ticket_pantalla+'</H1>'
                            }]
                        });
                        win11.show();

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
    var motivoAnulacion = new Ext.form.TextArea({
        fieldLabel:'Motivo Anulación',
        name:'motivoAnulacionTrx',
        anchor :'95%',
        enableKeyEvents:true,
        allowBlank : false
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
                    botonOK.focus(true,true);
                }

            }
        }
    });
    var usuarioSupervisor =   new Ext.form.TextField({
        fieldLabel:'User Supervisor',
        name:'userSupervisor',
        enableKeyEvents:true,
        allowBlank : false
    });
    var transaccion =new Ext.form.TextField( {
        fieldLabel:'TRANSACCIÓN',
        name:'TRANSACCION',
        xtype:'numberfield',
        allowBlank : false
    });

    var formPanel = new Ext.FormPanel({
        labelWidth : 130,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [usuarioSupervisor,passwordSupervisor,motivoAnulacion, transaccion],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Anular Transacción',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();
}