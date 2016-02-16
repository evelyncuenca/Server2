function reImprimirFormulario(){
    var comboTRANSACCIONES = getCombo('FORMULARIO','FORMULARIO','FORMULARIO','FORMULARIO','DESCRIPCION_FORMULARIO','FORMULARIO','FORMULARIO','DESCRIPCION_FORMULARIO','FORMULARIO','FORMULARIO');
    comboTRANSACCIONES.allowBlank= false;
    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function() {
            win.focus();
            formPanel.getForm().submit({
                url : 'cobranzas?op=REIMPRIMIR_FORMULARIO',
                method : 'POST',
                timeout : TIME_OUT_AJAX,
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
                            defaultButton:0,
                            buttons : [
                                {
                                    text : 'ok',
                                    handler : function() {
                                        win1.close();
                                    }
                                },{
                                    text : 'Certificar Boleta',
                                    formBind : true,
                                    handler : function() {
                                        imprimirImpresoraExterna(obj.certificacion);
                                    }
                                }
                            ],
                            items : [
                                {
                                    html:'<H1>'+obj.pantalla+'</H1>'
                                }
                            ]
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
                    botonOK.focus(true,true);
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
        items : [usuarioSupervisor,passwordSupervisor,comboTRANSACCIONES ],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Re Impresión de Formulario',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();
}