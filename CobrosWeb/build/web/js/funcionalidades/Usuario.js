
Ext.onReady(function() {
    Ext.QuickTips.init();
//tabOperaciones();
});

var idUSUARIOSeleccionadoID_USUARIO ;
function gridUSUARIO(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'USUARIO?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'USUARIO',
            totalProperty : 'TOTAL',
            id : 'ID_USUARIO',
            fields : [ 'ES_SUPERVISOR','CODIGO','CODIGO_EXTERNO','ID_USUARIO','PERSONA','NOMBRE_USUARIO','PERSONA','TELEFONO_PERSONA','RECAUDADOR' ]
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'CÓDIGO',
        width: anchoDefaultColumnas,
        dataIndex: 'CODIGO'
    },{
        header:'CÓD. EXTERNO',
        width: anchoDefaultColumnas,
        dataIndex: 'CODIGO_EXTERNO'
    },{
        header:'NOMBRE USUARIO',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE_USUARIO'
    },{
        header:'RECAUDADOR',
        width: anchoDefaultColumnas,
        dataIndex: 'RECAUDADOR'
    },{
        header:'PERSONA',
        width: anchoDefaultColumnas,
        dataIndex: 'PERSONA'
    },{
        header:'TELÉFONO PERSONA',
        width: anchoDefaultColumnas,
        dataIndex: 'TELEFONO_PERSONA'
    },{
        header:'ES SUPERVISOR',
        width: anchoDefaultColumnas,
        dataIndex: 'ES_SUPERVISOR'
    } ]);
    var btnAsignarSupervisor =  new Ext.Button({
        text:'Asignar Supervisor',
        id:'iBtnAsignarSupervisor',
        tooltip:'Asigna Supervisor al usuario',
        iconCls:'add',
        disabled:true,
        handler:function(){
            if(Ext.getCmp('idAsignacionSupervisores') == undefined){

                itemSelectorSupervisores(idUSUARIOSeleccionadoID_USUARIO).show();

            }
        }
    });
    var gridUSUARIO = new Ext.grid.GridPanel({
        title:'USUARIO',
        id:'gridUSUARIO',
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
                if(Ext.getCmp('agregarUSUARIO') == undefined) pantallaAgregarUSUARIO().show();
            }
        },btnAsignarSupervisor,{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idUSUARIOSeleccionadoID_USUARIO!=undefined){
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
                                    url : 'USUARIO?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_USUARIO : idUSUARIOSeleccionadoID_USUARIO
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
                                            Ext.getCmp('gridUSUARIO').store.reload();
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
            text:'Usuario',
            id:'idBusquedaUsario',
            xtype:'textfield',
            emptyText:'Usuario..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridUSUARIO').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                NOMBRE_USUARIO:esteObjeto.getValue()
                                }
                            });

                }
            }
        }
    },{
        text:'Persona',
        id:'idBusquedaPersona',
        xtype:'textfield',
        emptyText:'Persona..',
        listeners : {
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ){
                    Ext.getCmp('gridUSUARIO').store.reload({
                        params: {
                            start:0,
                            limit:20,
                            PERSONA:esteObjeto.getValue()
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
            idUSUARIOSeleccionadoID_USUARIO = esteObjeto.getStore().getAt(rowIndex).id;

            if(esteObjeto.getStore().getAt(rowIndex).data.ES_SUPERVISOR=="N"){
                btnAsignarSupervisor.enable();

            }else{
                btnAsignarSupervisor.disable();
            }


        },
        'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
            if(esteObjeto.getStore().getAt(rowIndex).data.ES_SUPERVISOR=="N"){
                btnAsignarSupervisor.enable();

            }else{
                btnAsignarSupervisor.disable();
            }
            if ( (Ext.getCmp("modificarUSUARIO") == undefined)) {
                idUSUARIOSeleccionadoID_USUARIO = esteObjeto.getStore().getAt(rowIndex).id;
                pantallaModificarUSUARIO().show();
            }
        }
    }
});
return gridUSUARIO;
}
function pantallaModificarUSUARIO() {
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboPERSONA =getCombo('PERSONA','PERSONA','PERSONA','PERSONA','DESCRIPCION_PERSONA','PERSONA','PERSONA','DESCRIPCION_PERSONA','PERSONA','PERSONA');
    comboPERSONA.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    comboRECAUDADOR.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var esSupervisor =  new Ext.form.Checkbox ({
        fieldLabel: 'ES SUPERVISOR',
        vertical: true,
        columns: 1,
        name: 'ES_SUPERVISOR',
        items: [
        {
            boxLabel: 'ES SUPERVISOR',
            inputValue: 'S'
        }

        ]
    });
    var usuarioModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarUSUARIO',
        labelWidth : 120,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID USUARIO',
            name:'ID_USUARIO',
            xtype:'textfield',
            inputType:'hidden'
        },{
            xtype:'numberfield',
            fieldLabel:'CÓDIGO',
            name:'CODIGO',
            allowBlank : false,
            anchor:'95%'
        },{
            xtype:'numberfield',
            fieldLabel:'CÓD. EXTERNO',
            name:'CODIGO_EXTERNO',
            allowBlank : true,
            anchor:'95%'
        },{
            fieldLabel:'NOMBRE USUARIO',
            name:'NOMBRE_USUARIO',
            xtype:'textfield',
            allowBlank : false
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboRECAUDADOR]
            }]
        },{
            fieldLabel:'CONTRASENHA',
            name:'CONTRASENHA',
            xtype:'textfield',
            allowBlank : true,
            inputType:'password'
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboPERSONA]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',
                    bodyStyle:'padding:4px 0 0',
                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarPERSONA().show();

                        }
                    }]
                }]
            }]
        },esSupervisor],

        buttons : [{
            id : 'btnmodificarUSUARIO',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idUSUARIOSeleccionadoID_USUARIO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                usuarioModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'USUARIO?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarUSUARIO').close();
                                        Ext.getCmp('gridUSUARIO').store.reload();
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
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarUSUARIO').close();
            }
        }]
    });
    usuarioModificarFormPanel.getForm().load({
        url : 'USUARIO?op=DETALLE',
        method : 'POST',
        params:{
            ID_USUARIO: idUSUARIOSeleccionadoID_USUARIO
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar USUARIO',
        id : 'modificarUSUARIO',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [usuarioModificarFormPanel]
    });
    return win;
}
function pantallaAgregarUSUARIO() {
    var esSupervisor =  new Ext.form.Checkbox ({
        fieldLabel: 'ES SUPERVISOR',
        vertical: true,
        columns: 1,
        name: 'ES_SUPERVISOR',
        items: [
        {
            boxLabel: 'ES SUPERVISOR',
            inputValue: 'S'
        }

        ]
    });

    var comboPERSONA =getCombo('PERSONA','PERSONA','PERSONA','PERSONA','DESCRIPCION_PERSONA','PERSONA','PERSONA','DESCRIPCION_PERSONA','PERSONA','PERSONA');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    comboPERSONA.allowBlank= false;
    comboRECAUDADOR.allowBlank= false;
    var usuarioAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarUSUARIO',
        labelWidth : 120,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID USUARIO',
            name:'ID_USUARIO',
            xtype:'textfield',
            inputType:'hidden'
        },{
            xtype:'numberfield',
            fieldLabel:'CÓDIGO',
            name:'CODIGO',
            allowBlank : false,
            anchor:'95%'
        },{
            xtype:'numberfield',
            fieldLabel:'CÓD. EXTERNO',
            name:'CODIGO_EXTERNO',
            allowBlank : true,
            anchor:'95%'
        },{
            fieldLabel:'NOMBRE USUARIO',
            name:'NOMBRE_USUARIO',
            xtype:'textfield',
            allowBlank : false
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboRECAUDADOR]
            }]
        },{
            fieldLabel:'CONTRASENHA',
            name:'CONTRASENHA',
            xtype:'textfield',
            allowBlank : false,
            inputType:'password'
        },{
            layout:'column',
            items:[{
                columnWidth:.9,
                layout: 'form',
                items: [comboPERSONA]
            },{
                columnWidth:.1,
                layout: 'form',
                items: [{
                    xtype:'panel',
                    bodyStyle:'padding:4px 0 0',
                    items:[{
                        xtype:'button',
                        iconCls:'add2',
                        handler: function(){
                            pantallaAgregarPERSONA().show();

                        }
                    }]
                }]
            }]
        },esSupervisor],
        buttons : [{
            id : 'btnAgregarUSUARIO',
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
                            usuarioAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'USUARIO?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarUSUARIO').close();
                                    Ext.getCmp('gridUSUARIO').store.reload();
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
        }, {
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarUSUARIO').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar USUARIO',
        id : 'agregarUSUARIO',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [usuarioAgregarFormPanel]
    });
    return win;
}

function itemSelectorSupervisores(elUsuario){ 

    var ds_providersDisponible = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'USUARIO?op=LISTAR_SUPERVISORES'

        }),
        reader : new Ext.data.JsonReader({
            root : 'SUPERVISORES',
            id : 'ID_USUARIO',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_USUARIO'
        }, {
            name : 'PERSONA'
        }])
    });

    ds_providersDisponible.load({
        params:{
            ID_USUARIO: elUsuario,
            FLAG_ASIGNACION: 'NO'
        }
    });
    var ds_providersSeleccionado = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'USUARIO?op=LISTAR_SUPERVISORES'

        }),
        reader : new Ext.data.JsonReader({
            root : 'SUPERVISORES',
            id : 'ID_USUARIO',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_USUARIO'
        }, {
            name : 'PERSONA'
        }])
    });

    ds_providersSeleccionado.load({
        params:{
            ID_USUARIO: elUsuario,
            FLAG_ASIGNACION: 'SI'
        }
    });

    var formulario = new Ext.form.FormPanel({

        bodyStyle: 'padding:10px;',
        items:[{
            xtype: 'itemselector',
            name: 'A_ASIGNAR',
            hideLabel:true,
            imagePath: 'images/',
            multiselects: [{
                width: 250,
                height: 200,
                store:   ds_providersDisponible,
                displayField: 'PERSONA',
                valueField: 'ID_USUARIO'
            },{
                width: 250,
                height: 200,
                store: ds_providersSeleccionado,
                displayField: 'PERSONA',
                valueField: 'ID_USUARIO'

            }]
        }],

        buttons: [{
            text: 'Guardar',
            handler: function(){
                if(formulario.getForm().isValid()){

                    formulario.getForm().submit({
                        method : 'POST',
                        waitTitle : 'Conectando',
                        waitMsg : 'Asignando los Supervisores...',
                        url : 'USUARIO?op=GUARDAR_SUPERVISORES',
                        timeout : TIME_OUT_AJAX,
                        params:{
                            ID_USUARIO:idUSUARIOSeleccionadoID_USUARIO
                        },
                        success : function(form, accion) {
                            ds_providersSeleccionado.load({
                                params:{
                                    ID_USUARIO: elUsuario,
                                    FLAG_ASIGNACION: 'SI'
                                }
                            });
                            ds_providersDisponible.load({
                                params:{
                                    ID_USUARIO: elUsuario,
                                    FLAG_ASIGNACION: 'NO'
                                }
                            });
                        },
                        failure : function(form, action) {
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : FAIL_CUERPO_GENERAL,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });
                        }
                    })
                }
            }
        },{
            text: 'Cerrar',
            handler: function(){
                win.close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Asignacion de Supervisores',
        id : 'idAsignacionSupervisores',
        autoWidth : true,
        height : 'auto',
        closable : true,
        resizable : false,
        items : [formulario]
    });
    return win;


}