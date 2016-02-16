var idTERMINALSeleccionadoID_TERMINAL ;

function getMac()
            {
                try
                {
                    document.appletAutorizadorTerminal.getMac(1);
                    //document.a.macCalculado.value =  document.appletAutorizadorTerminal.getMac();
                    return true;
                }
                catch(err)
                {

                    return false;
                }

            }

function gridTERMINAL(){


    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'TERMINAL?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'TERMINAL',
            totalProperty : 'TOTAL',
            id : 'ID_TERMINAL',
            fields : [ 'ID_TERMINAL','SUCURSAL','DESCRIPCION','URL_IMPRESORA', 'CODIGO_TERMINAL','CODIGO_CAJA_SET' ,'RECAUDADOR']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });var anchoDefaultColumnas= 10;var colModel = new Ext.grid.ColumnModel([ {
        header:'CÓD. TERMINAL',
        width: anchoDefaultColumnas,
        dataIndex: 'CODIGO_TERMINAL'
    } ,{
        header:'CÓD. CAJA SET',
        width: anchoDefaultColumnas,
        dataIndex: 'CODIGO_CAJA_SET'
    },{
        header:'DESCRIPCIÓN',
        width: anchoDefaultColumnas,
        dataIndex: 'DESCRIPCION'
    },{
        header:'RECAUDADOR',
        width: anchoDefaultColumnas,
        dataIndex: 'RECAUDADOR'
    },{
        header:'SUCURSAL',
        width: anchoDefaultColumnas,
        dataIndex: 'SUCURSAL'
    },{
        header:'URL IMPRESORA',
        width: anchoDefaultColumnas,
        dataIndex: 'URL_IMPRESORA'
    }]);var gridTERMINAL = new Ext.grid.GridPanel({
        title:'TERMINAL',
        id:'gridTERMINAL',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
        tbar:[{
            text:'Agregar',
            tooltip:TOOL_TIP_AGREGAR,
            iconCls:'add',
            handler:function(){
                if(Ext.getCmp('agregarTERMINAL') == undefined) pantallaAgregarTERMINAL().show();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idTERMINALSeleccionadoID_TERMINAL!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        buttons : Ext.Msg.OKCANCEL,
                        animEl : 'elId',
                        icon : Ext.MessageBox.WARNING,
                        fn:function(btn){
                            if(btn=="ok"){
                                var conn = new Ext.data.Connection();conn.request({
                                    url : 'TERMINAL?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_TERMINAL : idTERMINALSeleccionadoID_TERMINAL
                                    },
                                    success : function(action) {
                                        obj = Ext.util.JSON .decode(action.responseText);if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });Ext.getCmp('gridTERMINAL').store.reload();
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
                                            title : FAIL_TITULO_GENERAL,
                                            msg : FAIL_CUERPO_GENERAL,
                                            buttons : Ext.Msg.OK,
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
            text:'Terminal',
            id:'idBusquedaTerminal',
            xtype:'textfield',
            emptyText:'Terminal..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridTERMINAL').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                ID_TERMINAL:esteObjeto.getValue()
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
                limpiarPaneles("CANCELAR");
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
                idTERMINALSeleccionadoID_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarTERMINAL") == undefined)) {
                    idTERMINALSeleccionadoID_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;pantallaModificarTERMINAL().show();
                }
            }
        }
    });return gridTERMINAL;
}
function pantallaModificarTERMINAL() {

    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    comboSUCURSAL.allowBlank = false;
    comboSUCURSAL.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var terminalModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarTERMINAL',
        labelWidth : 120,
        frame : true,
        autoWidth : true,

        monitorValid : true,
        items: [{
            fieldLabel:'ID TERMINAL',
            name:'ID_TERMINAL',
            inputType:'hidden',
            xtype:'textfield'
        },{

            fieldLabel:'CÓD. TERMINAL',
            allowBlank : false,
            xtype:'numberfield',
            name: 'CODIGO_TERMINAL'

        },{

            fieldLabel:'CÓD. CAJA SET ',
            allowBlank : false,
            xtype:'numberfield',
            name: 'CODIGO_CAJA_SET'

        },{
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false,
            xtype:'textfield'
        },{
            fieldLabel:'URL IMPRESORA',
            name:'URL_IMPRESORA',
            xtype:'textfield'

        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboSUCURSAL]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarSUCURSAL().show();
                        }
                    }]
                }]
            }]
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [{
                    id:'idTextFieldHashTerminal',
                    fieldLabel:'HASH',
                    name:'HASH',
                    allowBlank : true,
                    inputType:'password',
                    xtype:'textfield'
                }]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            if(getMac(1)){
                                //Ext.getCmp('idTextFieldHashTerminal').setValue(document.getElementById('macCalculadoId').value);
                                Ext.getCmp('idTextFieldHashTerminal').setValue(document.appletAutorizadorTerminal.getMac(1));
                            }else{
                                Ext.Msg.show({
                                    title : 'Atención',
                                    msg : 'No se puede obtner la MAC de esta Terminal',
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.ERROR
                                });

                            }


                        }
                    }]
                }]
            }]
        }]
        ,
        buttons : [{
            id : 'btnmodificarTERMINAL',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idTERMINALSeleccionadoID_TERMINAL!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                terminalModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'TERMINAL?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });Ext.getCmp('modificarTERMINAL').close();Ext.getCmp('gridTERMINAL').store.reload();
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
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarTERMINAL').close();
            }
        }]
    });terminalModificarFormPanel.getForm().load({
        url : 'TERMINAL?op=DETALLE',
        method : 'POST',
        params:{
            ID_TERMINAL: idTERMINALSeleccionadoID_TERMINAL
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar TERMINAL',
        id : 'modificarTERMINAL',
        autoWidth : true,
        height : 'auto',
        modal:true,
        closable : false,
        resizable : false,
        items : [terminalModificarFormPanel]
    });return win;
}
function pantallaAgregarTERMINAL() {  

    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    comboSUCURSAL.allowBlank = false;
    var terminalAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarTERMINAL',
        labelWidth : 120,
        frame : true,
        autoWidth : true,
        monitorValid : true,
        items: [{
            fieldLabel:'ID TERMINAL',
            name:'ID_TERMINAL',
            inputType:'hidden',
            xtype:'textfield'
        },{

            fieldLabel:'CÓD. TERMINAL',
            allowBlank : false,
            xtype:'numberfield',
            name: 'CODIGO_TERMINAL'

        },{

            fieldLabel:'CÓD. CAJA SET ',
            allowBlank : false,
            xtype:'numberfield',
            name: 'CODIGO_CAJA_SET'

        },{
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false,
            xtype:'textfield'
        },{
            fieldLabel:'URL IMPRESORA',
            name:'URL_IMPRESORA',
            xtype:'textfield'

        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboSUCURSAL]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarSUCURSAL().show();
                        }
                    }]
                }]
            }]
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [{
                    id:'idTextFieldHashTerminal',
                    fieldLabel:'HASH',
                    name:'HASH',
                    allowBlank : false,
                    inputType:'password',
                    xtype:'textfield'
                }]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',

                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            if(getMac(1)){
                                Ext.getCmp('idTextFieldHashTerminal').setValue(document.appletAutorizadorTerminal.getMac(1));

                            }else{
                                Ext.Msg.show({
                                    title : 'Atención',
                                    msg : 'No se puede obtner la MAC de esta Terminal',
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.ERROR
                                });

                            }


                        }
                    }]
                }]
            }]
        }],
        buttons : [{
            id : 'btnAgregarTERMINAL',
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
                            terminalAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'TERMINAL?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });Ext.getCmp('agregarTERMINAL').close();Ext.getCmp('gridTERMINAL').store.reload();
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
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarTERMINAL').close();
            }
        }]
    });var win = new Ext.Window({
        title:'Agregar TERMINAL',
        id : 'agregarTERMINAL',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [terminalAgregarFormPanel]
    });return win;
}

