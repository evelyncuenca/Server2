/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function cobroServiciosManual(){
    var comboSERVICIOS_DISPONIBLES = getCombo('SERVICIO_CS','SERVICIO','SERVICIO','SERVICIO','DESCRIPCION_SERVICIO','Servicios Disponibles','SERVICIO','DESCRIPCION_SERVICIO','SERVICIO','SERVICIOS');
    var botonOK = new Ext.Button({
        text : 'Aceptar',
        formBind : true,
        handler : function() {
            win.focus();
        }
    });
     var codigoDeServicio =   new Ext.form.TextField({
        fieldLabel:'Codigo de Servicio',
        name:'codigoServicio',
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
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        //activeItem:0,
        items : [comboSERVICIOS_DISPONIBLES,codigoDeServicio],
        buttons : [botonOK,{
            text : 'Cancelar',
            handler : function() {
                win.close()
            }

        }]
    });

   var win = new Ext.Window({
        title:'Cobro de Servicios',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        modal:true,
        x:  500,
        items:formPanel

    });
    win.show();
}

