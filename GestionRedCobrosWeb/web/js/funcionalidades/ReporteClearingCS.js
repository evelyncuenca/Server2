/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function pantallaREPORTE_CLEARING_CS(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelClearingCS()]
    }
    //
      var win = new Ext.Window({
        id : 'idPanelREPORTE_CLEARING_CS',
        title:'REPORTE CLEARING COBRO SERVICIO',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();
}


function FormPanelClearingCS(){

    var comboENTIDAD =getCombo('ENTIDAD','ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD');
    comboENTIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        comboTIPO_REPORTE.focus(true,true);
    }, null,null );
   

     var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Desde',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Hasta',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });
  
   var formatoDeDescarga = new Ext.form.ComboBox({
        fieldLabel: 'Formato de Descarga',
        name : 'formatoDescarga',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'pdf',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['pdf', 'PDF'],
            ['xls',  'XLS']]
        })
    });

   
     
    var formPanel = new Ext.FormPanel({

        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [fechaIni, fechaFin, comboENTIDAD,formatoDeDescarga],
        buttons : [{
            formBind : true,
            text : 'Reporte',
            handler : function() {
               
                try {
                    Ext.destroy(Ext.get('downloadIframe'));
                    Ext.DomHelper.append(document.body, {
                        tag : 'iframe',
                        id : 'downloadIframe',
                        frameBorder : 0,
                        width : 0,
                        height : 0,
                        css : 'display:yes;visibility:hidden;height:0px;',
                        src : 'reportes?op=CLEARING-CS'+'&fechaIni='+fechaIni.getRawValue()+'&fechaFin='+fechaFin.getRawValue()+'&idEntidad='+comboENTIDAD.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
                    });
                    
                } catch (e) {
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
            }
        }]
    });

    return formPanel;

}

