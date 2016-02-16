function pantallaCERRAR_GRUPOS_RED(){
   
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosCerrarGruposRed()]
    };
    var win = new Ext.Window({
        title:'Cerrar Grupos de Red',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosCerrarGruposRed(){
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
     var fecha = new Ext.form.DateField({
        fieldLabel : 'Fecha',
        name : 'FECHA',
        //height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    comboRED.allowBlank = false;    
    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 70,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [fecha,comboRED],
        buttons : [{
            text : 'Cerrar Grupos',
            formBind : true,
            handler : function() {
                if(!trabajando){
                    trabajando = true;
                    formPanel.getForm().submit({
                        method : 'POST',
                        waitTitle : WAIT_TITLE_AGREGANDO,
                        waitMsg : WAIT_MSG_AGREGANDO,
                        url : 'inicial?op=CERRAR_GESTIONES_RED',
                        success : function(form, accion) {
                            Ext.Msg.show({
                                title : TITULO_EXITOSO,
                                msg : CUERPO_EXITOSO,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO
                            });
                           formPanel.getForm().reset();
                            trabajando = false;
                        },
                        failure : function(form, action) {
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : FAIL_CUERPO_GENERAL,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });
                            trabajando = false;
                        }
                    });
           
                    
                }
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