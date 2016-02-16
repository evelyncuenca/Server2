function pantallaREPORTE_DIGITACION(){

    var panel = {
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelREPORTE_DIGITACION()]
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

function FormPanelREPORTE_DIGITACION(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    var comboUSUARIO = getCombo('USUARIO','USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    
    comboRECAUDADOR.disable();
    comboSUCURSAL.disable();
    comboUSUARIO.disable();
    
    comboRECAUDADOR.allowBlank = true;    
    comboSUCURSAL.allowBlank =true;
    comboUSUARIO.allowBlank = true;
    
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){           
            comboRECAUDADOR.disable();  
            comboUSUARIO.disable();
            comboRECAUDADOR.reset();
            comboUSUARIO.reset();
        }        
    }, null,null  ) ;
    
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();        
    }, null,null ) ;
    
    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboUSUARIO.disable();
            comboSUCURSAL.disable();
            comboUSUARIO.reset();
            comboSUCURSAL.reset();
        }
    }, null,null  ) ;
    
    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboUSUARIO.enable();
        comboSUCURSAL.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null ) ;

   
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
        items : [comboRED,comboRECAUDADOR,comboSUCURSAL,comboUSUARIO,comboCERTIFICADO,fechaInicio,fechaDeFin,formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE-DIGITACION&certificado='+comboCERTIFICADO.getValue()+'&fechaFin='+fechaDeFin.getRawValue()+'&fechaIni='+fechaInicio.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&idRed='+comboRED.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&idUsuario='+comboUSUARIO.getValue()
                    });
                } catch (e) {
                    alert(e);
                }                
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
                comboRECAUDADOR.disable();                
                comboSUCURSAL.disable();
                comboUSUARIO.disable();
            }
        }]
    });
    return formPanel;
}