function anularTransaccionCS(){
    var comboTRANSACCIONES_CS = getCombo('TRANSACCION_CS','ID_TRANSACCION','TRANSACCION','ID_TRANSACCION','DESCRIPCION_TRANSACCION','Transacción','ID_TRANSACCION','DESCRIPCION_TRANSACCION','ID_TRANSACCION','ANULACION');
    comboTRANSACCIONES_CS.allowBlank= false;
    comboTRANSACCIONES_CS.anchor = '95%';
    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function() {
            win.focus();
            /*********************************/
            formPanel.getForm().submit({
                url : 'COBRO_SERVICIOS?op=TICKET',
                method : 'POST',
                waitTitle : 'Anulación',
                waitMsg : 'Generando Ticket Anulacion..',
                timeout : TIME_OUT_AJAX,
                success : function(form,action1) {
                    win.close();
                    var obj = Ext.util.JSON .decode(action1.response.responseText);
                    if(obj.success){
                        var win11 = new Ext.Window({
                            title:'A N U L A C I Ó N',
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
                                                Ext.Msg.wait('Procesando... Por Favor espere...');
                                                Ext.getCmp('panelTicketCB').load({
                                                    url: 'COBRO_SERVICIOS?op=ANULAR_TRANSACCION_CS',
                                                    params:{
                                                        passwordSupervisor   :passwordSupervisor.getValue(),
                                                        userSupervisor      :usuarioSupervisor.getValue(),
                                                        ID_TRANSACCION      :comboTRANSACCIONES_CS.getValue(),
                                                        motivoAnulacion     :motivoAnulacion.getValue()
                                                    },
                                                    discardUrl: false,
                                                    nocache: true,
                                                    text: "Cargando...",
                                                    timeout: TIME_OUT_AJAX,
                                                    scripts: true
                                                });
                                                Ext.Msg.hide();
                                                Ext.getCmp('content-panel').layout.setActiveItem('panelTicketCB');

                                            }else{
                                                win11.close();
                                            }

                                        }
                                    });
                                }
                            } ],
                            items : [{
                                html:'<H1>'+obj.ticketPantalla+'</H1>'
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
    var motivoAnulacion = new Ext.form.TextArea({
        fieldLabel:'Motivo Anulación',
        name:'motivoAnulacion',
        anchor :'95%',
        enableKeyEvents:true,
        allowBlank : false
    });
    /*var transaccionCS =new Ext.form.TextField( {
        fieldLabel:'TRANSACCIÓN',
        name:'ID_TRANSACCION',
        xtype:'numberfield',
        allowBlank : false
    });*/

    var formPanel = new Ext.FormPanel({
        labelWidth : 130,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [usuarioSupervisor,passwordSupervisor, motivoAnulacion, comboTRANSACCIONES_CS],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Anular Transacción de Servicios',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();
}