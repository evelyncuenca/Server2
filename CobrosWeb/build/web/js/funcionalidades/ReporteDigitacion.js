function pantallaREPORTE_DIGITACION(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteDigitacion()]
    }
    //return panel;
    var win = new Ext.Window({
        id : 'idPanelREPORTE_DIGITACION',
        title:'REPORTE DE DIGITACIONES',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}

function FormPanelReporteDigitacion(){    
    var comboCERTIFICADO = new Ext.form.ComboBox({
        fieldLabel: 'Certificado',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        value : '',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO'],
            ['A', 'ANULADO']]
        })
    });
    
    var fechaInicio = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        height : '22',
        anchor : '95%',
        id: 'idFecha',
        allowBlank:false,
        format:'dmY'
    });
    var fechaDeFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        height : '22',
        anchor : '95%',
        allowBlank:true,
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
        labelWidth : 165,
        frame : true,
        autoHeight:true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [fechaInicio,fechaDeFin,comboCERTIFICADO,formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE-DIGITACION&certificado='+comboCERTIFICADO.getValue()+'&fechaFin='+fechaDeFin.getRawValue()+'&fechaIni='+fechaInicio.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
                    });
                } catch (e) {
                    alert(e);
                } 
            }            
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
                //comboFACTURADOR.disable();
                //comboRECAUDADOR.disable();
                comboSERVICIO.disable();
                comboTIPO_REPORTE.enable();
            //comboTERMINAL.disable();
            //comboREGISTRO_GESTION.disable();
            //comboUSUARIO.disable();
            }
        }]
    });
    return formPanel;
}