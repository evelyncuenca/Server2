function gridSelectTransacc(){
    var transaccionText = new Ext.form.TextField({
        fieldLabel:'Transacción',
        id :'transaccion',
        name:'transaccion',
        inputType:'transaccion',
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
      
    var botonOK = new Ext.Button({
        text : 'OK',
        formBind : true,
        handler : function(){
            try {
                Ext.destroy(Ext.get('downloadIframe'));
                Ext.DomHelper.append(document.body, {
                tag : 'iframe',
                id : 'downloadIframe',
                frameBorder : 0,
                width : 0,
                height : 0,
                css : 'display:yes;visibility:hidden;height:0px;',
                src : 'reportes?op=SOLICITUD_TRANSACCION'+'&transaccion='+Ext.getCmp('transaccion').getValue()
                    });
                win.close();
                Ext.MessageBox.alert('Mensaje', 'La operación ha finalizado');
                    
                } catch (e) {
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
        items : [transaccionText],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });
    var win = new Ext.Window({
        title:'Generar Ticket Transacciones',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        items:formPanel

    });
    win.show();
}