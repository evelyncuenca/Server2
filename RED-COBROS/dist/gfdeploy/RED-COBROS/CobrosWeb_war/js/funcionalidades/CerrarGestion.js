var closeGes = false;

function cerrarGESTION(){
    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function(var1,var2) {
            win.focus();
            if(!closeGes){
                closeGes = true;
                formPanel.getForm().submit({
                    url : 'inicial?op=CERRAR_GESTION',
                    method : 'POST',
                    waitTitle : 'Cerrar Gestion',
                    waitMsg : 'Cerrando gestion..',
                    timeout : TIME_OUT_AJAX,
                    success : function(form,action) {
                        win.close();
                        var obj = Ext.util.JSON .decode(action.response.responseText);
                        if(obj.success){
                            GESTION_ABIERTA = false;
                            Ext.getCmp('content-panel').layout.setActiveItem('start-panel');
                            Ext.Msg.show({
                                title : 'Info',
                                msg : obj.ticket_pantalla,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO,
                                minWidth:400,
                                fn:function(){
                                    imprimirImpresoraExterna(obj.ticket)
                                }
                            });

                        }else{
                            callBackServidor(obj);
                   
                        }
                        closeGes = false;
                    },
                    failure : function(form,action) {
                        var obj = Ext.util.JSON .decode(action.response.responseText);
                        callBackServidor(obj);
                        closeGes = false;
                    }
                });
            }

        }
    });
    var passwordSupervisor = new Ext.form.TextField({
        fieldLabel:'Password',
        name:'PASSWORD',
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

    var formPanel = new Ext.FormPanel({
        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ passwordSupervisor ],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Autenticar',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel
    });
    win.show();
}