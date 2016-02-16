function pantallaCONSULTAR_PASE(){
   
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosConsultarPase()]
    };
    var win = new Ext.Window({
        title:'Consultar Pase',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosConsultarPase(){
    
    var formPanel = new Ext.FormPanel({
        labelWidth : 70,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [{
            xtype:'textfield',
            name :'PASE',
            fieldLabel: 'Ingrese Nro. de Pase'
        }],
        buttons : [{
            text : 'Consultar',
            formBind : true,
            handler : function() {

                formPanel.getForm().submit({
                    method : 'POST',
                    waitTitle : WAIT_TITLE_AGREGANDO,
                    waitMsg : WAIT_MSG_AGREGANDO,
                    url : 'USUARIO_TERMINAL?op=OBTENER_PASE',
                    success : function(form, accion) {
                        var obj = Ext.util.JSON.decode(accion.response.responseText);
                        
                        var win1 = new Ext.Window({
                            title:'DATOS DEL PASE',
                            autoWidth : true,
                            height : 'auto',
                            closable : false,
                            resizable : false,
                            defaultButton:0,
                            buttonAlign:'center',
                            buttons : [{
                                text : 'ok',
                                handler : function() {
                                    win1.close();
                                }
                            }],
                            items : [{
                                html:'<h1>'+obj.msg+'</h1>'
                            }]
                        });
                        win1.show();
                            
                    },
                    failure : function(form, action) {
                        var obj = Ext.util.JSON.decode(action.response.responseText);
                        Ext.Msg.show({
                            title : FAIL_TITULO_GENERAL,
                            msg : obj.msg,
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });
                            
                    }
                });          
                    
                
            }
        }, {
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
            }
        }]
    });
    return formPanel;
}