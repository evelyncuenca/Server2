function pantallaREPORTE_CIERRE_CS(){

    var panel = {
//        id : 'idPanelREPORTE_CIERRE_RESUMIDO',
//        title:'REPORTE CIERRE RESUMIDO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteCierres()]
    }
    //
      var win = new Ext.Window({
        id : 'idPanelREPORTE_CIERRE_CS',
        title:'REPORTE CIERRE COBRO SERVICIOS',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
     return win.show();


}

function FormPanelReporteCierres(){

    var comboGESTIONES = getCombo('GESTION','GESTION','GESTION','GESTION','DESCRIPCION_GESTION','GESTIÓN','GESTION','DESCRIPCION_GESTION','GESTION','GESTIONES...');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboUSUARIO = getCombo('USUARIO','USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO','DESCRIPCION_USUARIO','USUARIO','USUARIO');
    var comboTIPO = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Cierre',
        name : 'tipoCierre',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'CIERRE-DETALLADO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['CIERRE-DETALLADO', 'DETALLADO'],
            ['CIERRE-RESUMIDO',  'RESUMIDO']]
        })
    });

    comboUSUARIO.addListener( 'select',function(esteCombo, record,  index  ){
        comboGESTIONES.enable();
        comboGESTIONES.store.baseParams['ID_USUARIO'] = record.data.USUARIO;
        comboGESTIONES.store.baseParams['limit'] = 10;
        comboGESTIONES.store.baseParams['start'] = 0;
        comboGESTIONES.store.reload();

    }, null,null ) ;
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        comboUSUARIO.enable();
        comboUSUARIO.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboUSUARIO.store.reload();

    }, null,null ) ;

    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboGESTIONES.disable();
            comboUSUARIO.disable();
            comboRECAUDADOR.reset();
            comboUSUARIO.reset();
            comboGESTIONES.reset();
        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboUSUARIO.disable();
            comboUSUARIO.reset();
        }
    }, null,null  ) ;

    comboUSUARIO.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboGESTIONES.disable();
            comboGESTIONES.reset();
        }
    }, null,null  ) ;
    comboGESTIONES.disable();
    comboRECAUDADOR.disable();
    comboUSUARIO.disable();

    comboGESTIONES.allowBlank = true
    comboRED.allowBlank = false
    var fecha = new Ext.form.DateField({
        fieldLabel : 'Fecha',
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

    fecha.addListener('valid', function( esteObjeto){
        comboGESTIONES.store.baseParams['fecha']= fecha.getRawValue();
        comboGESTIONES.store.reload();
    }, null, null);
    var formPanel = new Ext.FormPanel({

        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,

        items : [fecha, comboRED,comboRECAUDADOR,comboUSUARIO,comboGESTIONES,comboTIPO, formatoDeDescarga],
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
                        src : 'reportes?op=CIERRE-CS'+'&idUsuario='+comboUSUARIO.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idRed='+comboRED.getValue()+'&idGestion='+comboGESTIONES.getValue()+'&fechaIni='+fecha.getRawValue() +'&formatoDescarga='+formatoDeDescarga.getValue()+'&tipoCierre='+comboTIPO.getValue()
                    });
                    
                } catch (e) {
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();
                comboGESTIONES.disable();
                comboRECAUDADOR.disable();
                comboUSUARIO.disable();

            }
        }]
    });

    return formPanel;

}

