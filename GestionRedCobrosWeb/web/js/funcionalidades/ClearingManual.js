//Variables globales
var editor = new Ext.ux.grid.RowEditor({
    saveText: 'Actualizar'
});
var editor2 = new Ext.ux.grid.RowEditor({
    saveText: 'Actualizar'
});
var editorActivo = null;

function panelClearingManual() {
    var panel = new Ext.Panel({
        title:'Clearing Manual',
        id : 'panelClearingManual',
        // activeTab: 0,
        autoScroll:true,     
        frame:true,
        defaults:{
            autoScroll: true
        },
        items:[cabeceraClearingManual(),gridClearingManual()]
    });
    return panel;
}

function cabeceraClearingManual(){    
    var fecha = new Ext.form.DateField({
        id:'idFechaCabeceraClearingManual',
        fieldLabel : 'FECHA',
        name : 'FECHA',
        height : '22',
        anchor : '95%',
        format:'dmY',
        enableKeyEvents:true,
        listeners : {           
            'select' : function(esteObjeto, date){               
                formPanel.getForm().load({
                    url : 'CLEARING_MANUAL?op=OBTENER_CABECERA',
                    method : 'POST',
                    params:{
                        FECHA: formPanel.findById('idFechaCabeceraClearingManual').getRawValue()
                    },
                    waitMsg : 'Cargando...'
                });
                Ext.getCmp('gridClearingManual').store.baseParams['FECHA'] =formPanel.findById('idFechaCabeceraClearingManual').getRawValue();
                Ext.getCmp('gridClearingManual').store.reload();
            }
        }
    });      
    var fechaAcreditacion = new Ext.form.DateField({
        id:'idFechaAcreditacionClearingManual',
        format:'dmY',
        fieldLabel : 'FECHA ACREDITACIÓN',
        name : 'FECHA_ACREDITACION',
        height : '22',
        anchor : '95%'
       
    });
    
    var cuenta= new Ext.form.TextField({
        id:'idCuentaClearingManual',
        fieldLabel:'CUENTA',
        name:'CUENTA_CABECERA',
        xtype:'textfield',
        anchor:'95%'
    });
    var monto= new Ext.form.TextField({
        id:'idMontoClearingManual',
        fieldLabel:'MONTO',
        name:'MONTO_CABECERA',
        xtype:'textfield',
        anchor:'95%',
        enableKeyEvents:true,
        listeners : {
            'keyup' : function(esteObjeto, esteEvento)   {               
                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
            }
        }
    });

    var descripcion= new Ext.form.TextField({
        id:'idDescripcionClearingManual',
        fieldLabel:'DESCRIPCIÓN',
        name:'DESCRIPCION_CABECERA',
        xtype:'textfield',
        anchor:'95%',
        enableKeyEvents:true
    });
    var comboCreditoDebito =  new Ext.form.ComboBox({
        id:'idCreditoDebitoClearingManual',
        fieldLabel: 'TIPO',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        emptyText: 'Tipo...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['CREDITO', 'CREDITO'],
            ['DEBITO',  'DEBITO']]
        })
    });   

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Cabecera',
            autoHeight: true,
            width: 350,
            defaultType: 'textfield',
            items: [fecha,fechaAcreditacion,cuenta,comboCreditoDebito,monto,descripcion]
        }
    }];

    var formPanel = new Ext.form.FormPanel({
        id:'idFormClearingManual',
        autoHeight: true,
        labelWidth: 150,
        frame:true,
        bodyStyle: 'padding: 5px',
        items : individual
    });
    return formPanel;
}

function gridClearingManual(){

    var fm = Ext.form;  
    var cm = new Ext.grid.ColumnModel([{
        id: 'common',
        header: 'Descripción',
        dataIndex: 'DESCRIPCION_DETALLE',
        width: 220,        
        editor: new fm.TextField({
            allowBlank: false
        })
    },{
        id: 'common2',
        header: 'Número de Cuenta',
        dataIndex: 'NUMERO_CUENTA_DETALLE',
        width: 220,      
        editor: new fm.TextField({
            allowBlank: false
        })
    },{
        id: 'common3',
        header: 'Monto',
        dataIndex: 'MONTO_DETALLE',
        width: 220,       
        editor: new fm.TextField({
            allowBlank: false
        })
    }        
    ]);
  
    cm.defaultSortable = true;
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CLEARING_MANUAL?op=OBTENER_DETALLE'
        }),
        reader : new Ext.data.JsonReader({
            root : 'DETALLE',
            totalProperty : 'TOTAL',
            id : 'SERVICIO',
            fields : [ 'DESCRIPCION_DETALLE','NUMERO_CUENTA_DETALLE','MONTO_DETALLE' ]
        })
    });
   
    
    var grid = new Ext.grid.EditorGridPanel({
        id:'gridClearingManual',
        store: st,
        autoScroll:true,
        cm: cm,
        width: 'auto',
        height: 200,
        autoExpandColumn: 'common',
        frame: true,      
        clicksToEdit: 1,
        tbar: [{
            text: 'Agregar', 
            iconCls:'add',
            handler : function(){
                var Plant = grid.getStore().recordType;
                var p = new Plant({
                    common: 'New Plant 1',
                    light: 'Mostly Shade',
                    price: 0,
                    availDate: (new Date()).clearTime(),
                    indoor: false
                });
                grid.stopEditing();
                st.insert(0, p);
                grid.startEditing(0, 0);
            }
        },{
            text: 'Borrar',
            iconCls:'remove',
            handler : function(){
                var index = grid.getSelectionModel().getSelectedCell();
                if (!index) {
                    return false;
                }
                var rec = grid.store.getAt(index[0]);
                grid.store.remove(rec);
            }
        },{
            text: 'Guardar',
            iconCls:'guardar',
            handler : function(){
                var arrayDescripcion= "";
                var arrayCuenta="";
                var arrayMonto="";
                Ext.each(
                    grid.getStore().data.items,
                    function(row) {                    
                        arrayDescripcion += ";"+row.data.DESCRIPCION_DETALLE;
                        arrayCuenta +=";"+row.data.NUMERO_CUENTA_DETALLE;
                        arrayMonto+=";"+row.data.MONTO_DETALLE;
                    });
                var conn = new Ext.data.Connection(); conn.request({
                    url : 'CLEARING_MANUAL?op=GUARDAR_CLEARING_MANUAL',
                    method : 'POST',
                    params : {
                        FECHA:Ext.getCmp('idFormClearingManual').findById('idFechaCabeceraClearingManual').getRawValue(),
                        FECHA_ACREDITACION:Ext.getCmp('idFormClearingManual').findById('idFechaAcreditacionClearingManual').getRawValue(),
                        DEBITO_CREDITO:Ext.getCmp('idFormClearingManual').findById('idCreditoDebitoClearingManual').getRawValue(),
                        DESCRIPCION_CABECERA:Ext.getCmp('idFormClearingManual').findById('idDescripcionClearingManual').getRawValue(),
                        CUENTA_CABECERA:Ext.getCmp('idFormClearingManual').findById('idCuentaClearingManual').getRawValue(),
                        MONTO_CABECERA:Ext.getCmp('idFormClearingManual').findById('idMontoClearingManual').getRawValue(),
                        DESCRIPCION_DETALLE:arrayDescripcion,
                        NUMERO_CUENTA_DETALLE:arrayCuenta,
                        MONTO_DETALLE:arrayMonto
                    },
                    success : function(action) {
                        var obj = Ext.util.JSON .decode(action.responseText);
                        if(obj.success){
                            Ext.Msg.show({
                                title :TITULO_ACTUALIZACION_EXITOSA,
                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.INFO
                            });
                            Ext.getCmp('gridUSUARIO_TERMINAL').store.reload();
                        }else{
                            Ext.Msg.show({
                                title : 'Estado',
                                msg : obj.motivo,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });
                        }
                    },
                    failure : function(action) {
                        Ext.Msg.show({
                            title : 'Estado',
                            msg : 'No se pudo realizar la operación.',
                            buttons : Ext.Msg.OK,
                            animEl : 'elId',
                            icon : Ext.MessageBox.ERROR
                        });
                    }
                });
            }
        },{
            text: 'Reset',
            iconCls:'resetBtn',
            handler : function(){
                Ext.getCmp('idFormClearingManual').getForm().reset();
                grid.store.removeAll();
                grid.getView().refresh();
            }
        }]
    });    
    return grid;


}

