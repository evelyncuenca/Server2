var idEntidadDir;
var idEntidadSeleccionado;
var idGrupoSeleccionado;
var emailSeleccionado;
function gridADMIN_MAIL(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'Entidad_Dir?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'Entidad_Dir',
            totalProperty : 'TOTAL',            
            fields : ['ID_ENTIDAD_DIR','ID_ENTIDAD','ID_GRUPO','ENTIDAD','GRUPO','EMAIL']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 200;
    var colModel = new Ext.grid.ColumnModel([{
        header: 'Entidad',
        dataIndex: 'ENTIDAD',
        width: anchoDefaultColumnas
    },{
        header: 'Grupo',
        dataIndex: 'GRUPO',
        width: anchoDefaultColumnas
    },{
        header: 'E-Mail',
        dataIndex: 'EMAIL',
        width: anchoDefaultColumnas
    }]);
    var gridADMIN_MAIL = new Ext.grid.GridPanel({
        title:'Administración De Grupo de E-Mail',
        id:'gridADMIN_MAIL',
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
                if(Ext.getCmp('agregarEntidad_Dir') == undefined) pantallaAgregarEntidad_Dir().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idEntidadDir!=undefined){
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
                                    url : 'Entidad_Dir?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_ENTIDAD_DIR:idEntidadDir     
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
                                            Ext.getCmp('gridADMIN_MAIL').store.reload();
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
            text:'Entidad',
            id:'idBusquedaENTIDADRR',
            xtype:'textfield',
            emptyText:'ENTIDAD..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridADMIN_MAIL').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA_ENTIDAD:esteObjeto.getValue()
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
                idEntidadDir=esteObjeto.getStore().getAt(rowIndex).data.ID_ENTIDAD_DIR;
                idEntidadSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_ENTIDAD;
                idGrupoSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_GRUPO;
                emailSeleccionado=esteObjeto.getStore().getAt(rowIndex).data.EMAIL;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarEntidad_Dir") == undefined)) {
                    idEntidadDir=esteObjeto.getStore().getAt(rowIndex).data.ID_ENTIDAD_DIR;
                    idEntidadSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_ENTIDAD;
                    idGrupoSeleccionado = esteObjeto.getStore().getAt(rowIndex).data.ID_GRUPO;
                    emailSeleccionado=esteObjeto.getStore().getAt(rowIndex).data.EMAIL;
                    pantallaModificarEntidad_Dir().show().center();
                }
            }
        }
    });
    return gridADMIN_MAIL;
}
function pantallaModificarEntidad_Dir() {
    var comboGRUPO=getCombo('GRUPO','GRUPO','GRUPO','GRUPO','EMAIL_GRUPO','GRUPO','GRUPO','EMAIL_GRUPO','GRUPO','GRUPO');
    var comboENTIDAD =getCombo('ENTIDAD','ENTIDAD','ENTIDAD','ENTIDAD','EMAIL_ENTIDAD','ENTIDAD','ENTIDAD','EMAIL_ENTIDAD','ENTIDAD','ENTIDAD');
    
    comboENTIDAD.allowBlank= false;
    comboGRUPO.allowBlank= false;
    comboENTIDAD.disable();
    comboGRUPO.disable();
    
    /*comboENTIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        comboGRUPO.enable();        
        comboGRUPO.store.baseParams['ID_ENTIDAD'] = record.data.ENTIDAD;
        comboGRUPO.store.reload();        
    }, null, null) ;
    
    comboENTIDAD.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboGRUPO.disable();
            comboGRUPO.reset();            
        }
    }, null, null) ;*/    

    var ENTIDADGRUPOModificarFormPanel = new Ext.FormPanel({
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboENTIDAD, comboGRUPO,{
            id:'idEMAILENTIDADRec',
            xtype:'textfield',
            name: 'EMAIL',
            anchor:'95%',
            fieldLabel: 'EMAIL',
            emptyText: 'ingrese e-mail...',
            regex: /^([\w\-\'\-]+)(\.[\w-\'\-]+)*@([\w\-]+\.){1,5}([A-Za-z]){2,4}$/,
            allowBlank:false,
            listeners : {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                        Ext.getCmp('idRefPagoFac').focus(true,true);
                    }
                }
            }
        }],
        buttons: [{
            id : 'btnmodificarEntidad_Dir',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idEntidadDir!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                ENTIDADGRUPOModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'Entidad_Dir?op=MODIFICAR',
                                    params: {                                        
                                        ID_ENTIDAD_DIR:idEntidadDir                                     
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarEntidad_Dir').close();
                                        Ext.getCmp('gridADMIN_MAIL').store.reload();
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
                }else{
                    Ext.Msg.show({ title : "Error",
                                   msg : "Id Entidad Dir vacio",
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.ERROR
                                            });
                }
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarEntidad_Dir').close();
            }
        }]
    });

    ENTIDADGRUPOModificarFormPanel.getForm().load({
        url : 'Entidad_Dir?op=DETALLE',
        method : 'POST',
        params:{
            ID_ENTIDAD_DIR:idEntidadDir, 
            ID_ENTIDAD:idEntidadSeleccionado,
            ID_GRUPO :idGrupoSeleccionado,
            EMAIL:emailSeleccionado
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar E-Mail',
        id : 'modificarEntidad_Dir',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [ENTIDADGRUPOModificarFormPanel]
    });
    return win;

}
function pantallaAgregarEntidad_Dir() {
    var comboGRUPO=getCombo('GRUPO_REPORTE','GRUPO_REPORTE','GRUPO_REPORTE','GRUPO_REPORTE','DESCRIPCION_GRUPO_REPORTE'/*desc*/,'GRUPO REPORTE','GRUPO_REPORTE','DESCRIPCION_GRUPO_REPORTE','GRUPO_REPORTE','GRUPO REPORTE');
    var comboENTIDAD = getCombo("ENTIDAD", "ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD", "DESCRIPCION_ENTIDAD", "ENTIDAD", "ENTIDAD");
    
    comboENTIDAD.allowBlank= false;
    comboGRUPO.allowBlank= false;
    
    /*comboENTIDAD.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboGRUPO.disable();
            comboGRUPO.reset();            
        }
    }, null, null) ;   */ 

    var ENTIDADGRUPOAgregarFormPanel = new Ext.FormPanel({
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboENTIDAD, comboGRUPO,{
            id:'idEMAILENTIDADRec',
            xtype:'textfield',
            name: 'EMAIL',
            anchor:'95%',
            fieldLabel: 'EMAIL',
            emptyText: 'ingrese e-mail...',
            regex: /^([\w\-\'\-]+)(\.[\w-\'\-]+)*@([\w\-]+\.){1,5}([A-Za-z]){2,4}$/,
            allowBlank:false,
            listeners : {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                        Ext.getCmp('idRefPagoFac').focus(true,true);
                    }
                }
            }
        }],
        buttons: [{
            id : 'btnagregarEntidad_Dir',
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
                            ENTIDADGRUPOAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'Entidad_Dir?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                params:{
                                    ID_ENTIDAD:comboENTIDAD.getValue(),
                                    ID_GRUPO :comboGRUPO.getValue()
                                },
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarEntidad_Dir').close();
                                    Ext.getCmp('gridADMIN_MAIL').store.reload();
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
                Ext.getCmp('agregarEntidad_Dir').close();
            }
        }]
    });
    

    var win = new Ext.Window({
        title:'Agregar E-Mail',
        id : 'agregarEntidad_Dir',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [ENTIDADGRUPOAgregarFormPanel]
    });
    return win;
}

