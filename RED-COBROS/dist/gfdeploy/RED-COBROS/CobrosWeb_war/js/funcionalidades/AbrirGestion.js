var openGes = false
function abrirGESTION(){

    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function(vari,evt) {
            win.focus();
            if(!openGes){
                openGes = true;                
                formPanel.getForm().submit({
                    url : 'inicial?op=ABRIR_GESTION',
                    method : 'POST',
                    waitTitle : 'Abrir Gestion',
                    waitMsg : 'Abriendo gestion..',
                    timeout : TIME_OUT_AJAX,
                    success : function(form,action1) {
                        win.close();
                        var obj = Ext.util.JSON .decode(action1.response.responseText);
                        if(obj.success){
                            if(obj.tieneRegistrosAbiertosHoy){
                                GESTION_ABIERTA =true;
                                Ext.Msg.show({
                                    title : 'Atención',
                                    msg : 'Ya se realizó una apertura, debe realizar un cierre para abrir una nueva',
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.ERROR
                                });
                            }
                            else if(obj.tieneRegistrosAbiertosAnteriores){
                                GESTION_ABIERTA = false;
                                Ext.Msg.show({
                                    title : 'Info',
                                    msg : 'Tiene gestiones abiertas anteriores. Debe cerrarlas antes de continuar',
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.INFO
                                });
                            }
                            else if(obj.nuevaGestionAbierta){
                                GESTION_ABIERTA = true;
                                Ext.Msg.show({
                                    title : 'Info',
                                    msg : obj.ticket_pantalla,
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.INFO,
                                    minWidth:400,
                                    fn:function(){
                                        imprimirImpresoraExterna(obj.ticket);

                                    }
                                });
                            }
                        }else{
                            callBackServidor(obj);


                        }
                        openGes = false;
                    } ,
                    failure : function(form,action12) { 
                        var obj12 = Ext.util.JSON .decode(action12.response.responseText);
                        callBackServidor(obj12);
                        openGes = false;
                    }
                });
            }
        }

    });
  
    var passwordSupervisor = new Ext.form.TextField({
        fieldLabel:'Password',
        anchor:'90%',
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
        labelWidth : 90,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [  passwordSupervisor ]

       
    });
    var win = new Ext.Window({
        title:'Autenticar',
        width : 250 ,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        buttonAlign:'center',
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }
        }],
        items:formPanel

    });
    win.show();
}