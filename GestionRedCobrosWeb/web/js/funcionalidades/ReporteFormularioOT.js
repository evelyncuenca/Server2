
function pantallaREPORTE_FORMULARIO_OT(){
    var panel = {
        xtype : 'panel',
        layout : 'fit',
        border : false,
        autoScroll : true ,
        items: [parametrosREPORTE_FORMULARIO_OT()]
    };
    var win = new Ext.Window({
         id : 'idPanelREPORTE_FORMULARIO_OT',
        title:'Generar Reporte Formulario OT',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [panel]
    });
    return win.show();
}
function parametrosREPORTE_FORMULARIO_OT(){
    var comboRED = getCombo("RED", "RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED", "DESCRIPCION_RED", "RED", "RED");
    var fecha = new Ext.form.DateField({
        fieldLabel : 'FECHA',
        name : 'FECHA',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true
    });
    var comboEfectivoCheques =  new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        emptyText: 'Tipo...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'EFECTIVO'],
            ['2',  'CHEQUES'],
            ['3',  'TRANSFERENCIA_DGR'],
            ['4',  'TRANS_DGR_DETALLADO']]
        })
    });
    /*var comboOT=getCombo("NRO_OT", "NRO_OT", "NRO_OT", "NRO_OT", "DESCRIPCION_NRO_OT", "NRO OT", "NRO_OT", "DESCRIPCION_NRO_OT", "NRO_OT", "NRO OT");
    comboEfectivoCheques.addListener( 'change',function(esteCombo, record,  index  ){
        comboOT.store.baseParams['FECHA'] = fecha.getRawValue();
        comboOT.store.baseParams['TIPO'] = comboEfectivoCheques.getValue();
        comboOT.store.reload();
    }, null,null ) ;*/
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
    var trabajando = false;
    var formPanel = new Ext.FormPanel({
        labelWidth : 150,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [fecha,comboRED,comboEfectivoCheques,/*comboOT,*/formatoDeDescarga],
        buttons : [{
            text : 'Generar Archivo',
            formBind : true,
            handler : function() {
                if(!trabajando){
                    trabajando = true;
                    try {
                        Ext.destroy(Ext.get('downloadIframe'));
                        Ext.DomHelper.append(document.body, {
                            tag : 'iframe',
                            id : 'downloadIframe',
                            frameBorder : 0,
                            width : 0,
                            height : 0,
                            css : 'display:yes;visibility:hidden;height:0px;',
                            src : 'reportes?op=REPORTE-FORMULARIO-OT&fechaIni='+fecha.getRawValue()+'&idRed='+comboRED.getValue()/*+'&nroOt='+comboOT.getValue()*/+'&tipoPago='+comboEfectivoCheques.getValue()+'&formatoDescarga='+formatoDeDescarga.getValue()
                        });
                        trabajando = false;
                    } catch (e) {

                    }
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