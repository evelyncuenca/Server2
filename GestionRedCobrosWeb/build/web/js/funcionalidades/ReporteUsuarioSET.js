/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function pantallaREPORTE_CIERRE_USUARIO(){

    var panel = {
        id : 'idPanelREPORTE_CIERRE_USUARIO',
        //        title:'REPORTE CIERRE RESUMIDO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteCierreUsuarios()]
    }
    //
    var win = new Ext.Window({
        //id : 'idPanelREPORTE_CIERRE_USUARIOS',
        title:'REPORTE CIERRE USUARIOS',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();


}

function FormPanelReporteCierreUsuarios(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');

    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboRECAUDADOR.disable();
            comboSUCURSAL.disable();

            comboRECAUDADOR.reset();
            comboSUCURSAL.reset();

        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboSUCURSAL.disable();
            comboSUCURSAL.reset();

        }

    }, null,null  ) ;


    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();

        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){

        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();

    }, null,null ) ;

    comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){


        }, null,null ) ;

    comboRECAUDADOR.disable();
    comboSUCURSAL.disable();

    var comboTIPO = new Ext.form.ComboBox({
        fieldLabel: 'Tipo de Reporte',
        name : 'tipoReporteUsuario',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value : 'BBPP',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['BBPP', 'BBPP'],
            ['DDJJ',  'DDJJ']]
        })
    });


    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Ini',
        id:'idFechaIni',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY'
    });

    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFin',
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

        items : [comboRED, comboRECAUDADOR, comboSUCURSAL,fechaIni, fechaFin, comboTIPO, formatoDeDescarga],
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
                        src : 'reportes?op=REPORTES_CIERRE_USUARIOS'+'&fechaIni='+fechaIni.getRawValue() +'&fechaFin='+fechaFin.getRawValue()+'&tipoReporte='+comboTIPO.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&idRed='+comboRED.getValue()+'&idSucursal='+comboSUCURSAL.getValue()
                    });

                } catch (e) {
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                comboRECAUDADOR.disable();
                comboSUCURSAL.disable();
                formPanel.getForm().reset();
            }
        }]
    });

    return formPanel;

}



