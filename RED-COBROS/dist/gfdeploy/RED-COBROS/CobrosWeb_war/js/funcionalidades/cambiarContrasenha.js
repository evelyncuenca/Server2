function pantallaModificarContrasenhaDeLogeo() {
    var counterTeclasPresionadas = "";
    var confirmarPassword =    new Ext.form.TextField({
       
        id:"confirma_new_pass_id",
        fieldLabel : 'Confirmar Password Nuevo',
        name : 'confirmar_new_pass',
        height : '22',
        anchor : '95%',
        inputType : 'password',
        allowBlank:false,              
        listeners: {
            'render': function(c) {
                c.getEl().on('keyup', function(esteObjeto, esteEvento){
                   
                    if(!(esteObjeto.getKey()==9||esteObjeto.getKey()==17 || esteObjeto.getKey()==13  || esteObjeto.getKey()==16 || esteObjeto.getKey()==20 || esteObjeto.getKey()==8 || esteObjeto.getKey()==46 || esteObjeto.getKey()==37 || esteObjeto.getKey()==38 || esteObjeto.getKey()==39 || esteObjeto.getKey()==40 || esteObjeto.getKey()==144 || esteObjeto.getKey()==0  )){
                        counterTeclasPresionadas++;                         
                    }
                    
                    if(Ext.getCmp('confirma_new_pass_id').getRawValue() ==""){                        
                        counterTeclasPresionadas=0;
                    }else if(esteObjeto.getKey() == 8 || esteObjeto.getKey() == 46){
                        counterTeclasPresionadas = Ext.getCmp('confirma_new_pass_id').getValue().split("").length;
                        
                    }   
                   
                   
                }
                );
            }
        }
    
    }) ; 
    
    if ((Ext.getCmp('modificarContrasenhaLogin'))){
            
        return null;
            
    }else{
        var formPanel = new Ext.FormPanel({

            labelWidth : 170,
            frame : true,
            autoWidth : true,
            defaultType : 'textfield',
            monitorValid : true,
            items : [ {
                xtype : 'textfield',
                fieldLabel : 'Password Actual',
                name : 'old_pass',
                height : '22',
                anchor : '95%',
                inputType : 'password',
                allowBlank:false

            },{
                xtype : 'textfield',
                fieldLabel : 'Password Nuevo',
                name : 'new_pass',
                height : '22',
                anchor : '95%',
                inputType : 'password',
                allowBlank:false


            },confirmarPassword],
            buttons : [{
                id : 'btnmodificarContrasenhaLogin',
                text : 'Modificar',
                formBind : true,

                handler : function() {
                    win.focus();
                    Ext.Msg.show({

                        title : 'Atención',
                        msg : '¿Desea modificar los datos del registro?',
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {

                            if (btn == "ok") {

                                var counterConfirmarContrasenha = Ext.getCmp('confirma_new_pass_id').getValue().split("");

                                if(counterTeclasPresionadas == counterConfirmarContrasenha.length){

                                    formPanel.getForm().submit({
                                        method : 'POST',
                                        waitTitle : 'Conectando',
                                        waitMsg : 'Modificando los datos del registro...',
                                        url : 'inicial?op=CAMBIAR_PASSWORD',
                                        timeout : TIME_OUT_AJAX,

                                        success : function(form, accion) {
                                            Ext.Msg.show({
                                                title : 'Info',
                                                msg : 'Actualización Exitosa',
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            win.close();

                                        },
                                        failure : function(form, action) {
                                            var obj = Ext.util.JSON
                                            .decode(action.response.responseText);

                                            Ext.Msg.show({
                                                title : 'Atención',
                                                msg : obj.motivo,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });


                                        }
                                    });

                                }
                                else{
                                    Ext.Msg.show({
                                        title : 'Atención',
                                        msg : "El campo 'Confirmar Nuevo Password' debe ser cargado solo desde el teclado, o No coinciden el campo 'Password Nuevo' y el campo 'Confirmar Password Nuevo'",
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });


                                }



                            }

                        }

                    });


                }
            }, {
                text : 'Cancelar',
                handler : function() {

                    win.close();
                }

            }]
        });
        var win = new Ext.Window({
            title:'Cambiar Password',
            id : 'formModificarTablaContrasenhaLogin',
            width : 450,
            height : 'auto',
            modal:true,
            closable : false,
            resizable : false,
            items : [formPanel]
        });


        
        return win.show();        
        
    }    
}
