/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var idREDSeleccionado;
var idRECAUDADORSeleccionado;
function gridADMIN_RED_RECAUDADOR(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'RED_RECAUDADOR?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'RED_RECAUDADOR',
            totalProperty : 'TOTAL',            
            fields : ['ID_RED','ID_RECAUDADOR','RED','RECAUDADOR','DESCRIPCION','RED_TICKET']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 100;
    var colModel = new Ext.grid.ColumnModel([{
        header: 'Red',
        dataIndex: 'RED',
        width: anchoDefaultColumnas
    },{
        header: 'Recaudador',
        dataIndex: 'RECAUDADOR',
        width: anchoDefaultColumnas
    },{
        header: 'Descripcion',
        dataIndex: 'DESCRIPCION',
        width: anchoDefaultColumnas
    },{        
        header: 'Red Ticket',
        dataIndex: 'RED_TICKET',
        width: anchoDefaultColumnas    
    }]);
    var gridADMIN_RED_RECAUDADOR = new Ext.grid.GridPanel({
        title:'Administración De Red Recaudador',
        id:'gridADMIN_RED_RECAUDADOR',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:false
        },
        tbar:[{
            text:'Agregar',
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarRED_RECAUDADOR') == undefined) pantallaAgregarRED_RECAUDADOR().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idREDSeleccionado!=undefined && idRECAUDADORSeleccionado!= undefined ){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();
                                conn.request({
                                    url : 'RED_RECAUDADOR?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_RED : idREDSeleccionado,
                                        ID_RECAUDADOR :idRECAUDADORSeleccionado
                                    },
                                    success : function(action) {
                                        var  obj = Ext.util.JSON .decode(action.responseText);
                                        if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.reload();
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
                                            title :FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.ERROR,
                                            animEl : 'elId',
                                            icon : Ext.MessageBox.ERROR
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        },{
            text:'Red',
            id:'idBusquedaRedRR',
            xtype:'textfield',
            emptyText:'Red..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA_RED:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        },{
            text:'Recaudador',
            id:'idBusquedaRecaudadorRR',
            xtype:'textfield',
            emptyText:'Recaudador..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA_RECAUDADOR:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        },{
            text:'Salir',
            tooltip:'Salir',
            iconCls:'logout',
            handler:function(){
                cardInicial();
            }
        }],
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : st,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idREDSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_RED;
                idRECAUDADORSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_RECAUDADOR;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarRED_RECAUDADOR") == undefined)) {
                    idREDSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_RED;
                    idRECAUDADORSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_RECAUDADOR;
                    pantallaModificarRED_RECAUDADOR().show().center();
                }
            }
        }
    });
    return gridADMIN_RED_RECAUDADOR;
}
function pantallaModificarRED_RECAUDADOR() {
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    
    comboRED.allowBlank= false;
    comboRECAUDADOR.allowBlank= false;
    comboRED.disable();
    comboRECAUDADOR.disable();
    var comboREDTICKET = new Ext.form.ComboBox({
        fieldLabel: 'TICKET',
        name : 'RED_TICKET',
        hiddenName : 'RED_TICKET',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['PRACTIPAGO', 'PRACTIPAGO'],
            ['RED COOP',  'RED COOP'],
            ['RED DE PAGOS', 'RED DE PAGOS'],]
        })
    });
    comboREDTICKET.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('btnmodificarRED_RECAUDADOR').focus(true, true);
    }, null, null) ;
    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDescripcionRedRec').focus(true, true);
    }, null, null) ;
    
    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();        
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();
        comboRECAUDADOR.focus(true,true);
    }, null, null) ;
    
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();            
        }
    }, null, null) ;    

    var redRecaudadorModificarFormPanel = new Ext.FormPanel({
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboRED, comboRECAUDADOR,{
            id:'idDescripcionRedRec',
            xtype:'textfield',
            name: 'DESCRIPCION',
            anchor:'95%',
            fieldLabel: 'Descripcion',
            allowBlank:true,
            listeners : {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                        comboREDTICKET.focus(true,true);
                    }
                }
            }
        },comboREDTICKET],
        buttons: [{
            id : 'btnmodificarRED_RECAUDADOR',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idREDSeleccionado!=undefined && idRECAUDADORSeleccionado!= undefined ){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                redRecaudadorModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'RED_RECAUDADOR?op=MODIFICAR',
                                    params: {                                        
                                        ID_RED:idREDSeleccionado,
                                        ID_RECAUDADOR :idRECAUDADORSeleccionado
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarRED_RECAUDADOR').close();
                                        Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.reload();
                                    },
                                    failure : function(form, action) {
                                        var obj = Ext.util.JSON .decode(action.response.responseText);

                                        if(obj.motivo != undefined ){
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : obj.motivo,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                                        }else{
                                            Ext.Msg.show({
                                                title : FAIL_TITULO_GENERAL,
                                                msg : FAIL_CUERPO_GENERAL,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarRED_RECAUDADOR').close();
            }
        }]
    });

    redRecaudadorModificarFormPanel.getForm().load({
        url : 'RED_RECAUDADOR?op=DETALLE',
        method : 'POST',
        params:{
            ID_RED:idREDSeleccionado,
            ID_RECAUDADOR :idRECAUDADORSeleccionado
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar RED_RECAUDADOR',
        id : 'modificarRED_RECAUDADOR',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [redRecaudadorModificarFormPanel]
    });
    return win;

}
function pantallaAgregarRED_RECAUDADOR() {
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    
    comboRED.allowBlank= false;
    comboRECAUDADOR.allowBlank= false;
    var comboREDTICKET = new Ext.form.ComboBox({
        fieldLabel: 'TICKET',
        name : 'RED_TICKET',
        hiddenName : 'RED_TICKET',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['PRACTIPAGO', 'PRACTIPAGO'],
            ['RED COOP',  'RED COOP'],
            ['MULTICOOP',  'MULTICOOP'],
            ['RED DE PAGOS', 'RED DE PAGOS'],]
        })
    });
    comboREDTICKET.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('btnagregarRED_RECAUDADOR').focus(true, true);
    }, null, null) ;
    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDescripcionRedRec').focus(true, true);
    }, null, null) ;
     comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.focus(true, true);
    }, null, null) ;
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboRECAUDADOR.reset();            
        }
    }, null, null) ;    

    var redRecaudadorAgregarFormPanel = new Ext.FormPanel({
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboRED, comboRECAUDADOR,{
            id:'idDescripcionRedRec',
            xtype:'textfield',
            name: 'DESCRIPCION',
            anchor:'95%',
            fieldLabel: 'Descripcion',
            allowBlank:true,
            listeners : {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                        comboREDTICKET.focus(true,true);
                    }
                }
            }
        },comboREDTICKET],
        buttons: [{
            id : 'btnagregarRED_RECAUDADOR',
            text : 'Agregar',
            formBind : true,
            handler : function() {
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            redRecaudadorAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'RED_RECAUDADOR?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                params:{
                                    ID_RED:comboRED.getValue(),
                                    ID_RECAUDADOR :comboRECAUDADOR.getValue()
                                },
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarRED_RECAUDADOR').close();
                                    Ext.getCmp('gridADMIN_RED_RECAUDADOR').store.reload();
                                },
                                failure : function(form, action) {
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : FAIL_CUERPO_GENERAL,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });
                                }
                            });
                        }
                    }
                });
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarRED_RECAUDADOR').close();
            }
        }]
    });
    

    var win = new Ext.Window({
        title:'Agregar RED RECAUDADOR',
        id : 'agregarRED_RECAUDADOR',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [redRecaudadorAgregarFormPanel]
    });
    return win;
}

