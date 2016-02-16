/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var idEVENTOSeleccionadoID_EVENTO ;
var idSECTOR_EVENTOSeleccionado;
var idSUSCRIPTORESSeleccionado;

function panelEventos() {
    var panel = {
        id : 'panelEventos',
        title:'Administracion de Eventos',
        xtype : 'panel',
        // layout   : 'border',
        border : false,
        autoScroll : true /*,
        items: [{
            collapsible: false,
            region:'center',
            layout:'fit',
            margins: '5 0 0 0',
            autoScroll : true ,
            items:[gridEVENTO()]
        },{
            collapsible: false,
            layout:'fit',
            region:'south',
            margins: '5 0 0 0',
            autoScroll : true,
            height: 250,
            split:true,
            minSize: 75,
            maxSize: 350,
            items:[gridSECTOR_EVENTO()]
        }]*/,
        items:[{
            layout:'column',
            items:[{
                columnWidth:.5,
                region:'east',
                height: 250,
                layout: 'fit',
                items: [gridEVENTO()]
            },{
                columnWidth:.5,
                region:'west',
                height: 250,
                layout: 'fit',
                items: [gridSECTOR_EVENTO()]
            }]
        },{
            layout: 'fit',
            height: 250,
            items:[gridSUSCRIPTORES()]
        }]
    }
    return panel;

}
function gridEVENTO(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'EVENTO?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'EVENTO',
            totalProperty : 'TOTAL',
            id : 'ID_EVENTO',
            fields : ['ID_EVENTO','ENTIDAD','DESCRIPCION','FECHA_INI','FECHA_FIN','HABILITADO']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'ENTIDAD',
        width: anchoDefaultColumnas,
        dataIndex: 'ENTIDAD'
    },{
        header:'DESCRIPCION',
        width: anchoDefaultColumnas,
        dataIndex: 'DESCRIPCION'
    },{
        header:'FECHA INICIO',
        width: anchoDefaultColumnas,
        dataIndex: 'FECHA_INI'
    },{
        header:'FECHA FIN',
        width: anchoDefaultColumnas,
        dataIndex: 'FECHA_FIN'
    }]);
    var gridEVENTO = new Ext.grid.GridPanel({
        title:'Eventos',
        id:'gridEVENTO',
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
                if(Ext.getCmp('agregarEVENTO') == undefined) pantallaAgregarEVENTO().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idEVENTOSeleccionadoID_EVENTO!=undefined){
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
                                    url : 'EVENTO?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_EVENTO : idEVENTOSeleccionadoID_EVENTO
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
                                            Ext.getCmp('gridEVENTO').store.reload();
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
            text:'Evento',
            id:'idBusquedaEvento',
            xtype:'textfield',
            emptyText:'Evento..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridEVENTO').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA:esteObjeto.getValue()
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
                idEVENTOSeleccionadoID_EVENTO = esteObjeto.getStore().getAt(rowIndex).id;
                Ext.getCmp('gridSECTOR').store.load({
                    params : {
                        ID_EVENTO :idEVENTOSeleccionadoID_EVENTO,
                        start : 0,
                        limit : 20
                    }
                });
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {                
                if ( (Ext.getCmp("modificarEVENTO") == undefined)) {
                    idEVENTOSeleccionadoID_EVENTO = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarEVENTO().show().center();
                }
            }
        }
    });
    return gridEVENTO;
}
function gridSECTOR_EVENTO(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'SECTOR?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'SECTOR',
            totalProperty : 'TOTAL',
            id : 'ID_SECTOR',
            fields : ['ID_SECTOR','EVENTO','DESCRIPCION','MONTO']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'EVENTO',
        width: anchoDefaultColumnas,
        dataIndex: 'EVENTO'
    },{
        header:'SECTOR',
        width: anchoDefaultColumnas,
        dataIndex: 'DESCRIPCION'
    },{
        header:'MONTO',
        width: anchoDefaultColumnas,
        dataIndex: 'MONTO'
    }]);
    var gridSECTOR = new Ext.grid.GridPanel({
        title:'Sector',
        id:'gridSECTOR',
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
                if(Ext.getCmp('agregarSECTOR') == undefined) pantallaAgregarSECTOR().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idEVENTOSeleccionadoID_EVENTO!=undefined){
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
                                    url : 'SECTOR?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_SECTOR : idSECTOR_EVENTOSeleccionado
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
                                            Ext.getCmp('gridSECTOR').store.reload();
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
                idSECTOR_EVENTOSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;   
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {                
                if ( (Ext.getCmp("modificarSECTOR") == undefined)) {
                    idSECTOR_EVENTOSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarSECTOR().show().center();
                }
            }
        }
    });
    return gridSECTOR;
}
function pantallaModificarEVENTO() {    
    var comboENTIDAD = getCombo('ENTIDAD','ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD');
    comboENTIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDescripcionModEvento').focus(true, true);
    }, null,null ) ;

    var descripcion = new Ext.form.TextField({
        fieldLabel:'Descripcion',
        id:'idDescripcionModEvento',
        name:'DESCRIPCION',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idModEventoFechaInicio').focus(true,true);
                }
            }
        }
    });
    
    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        id:'idModEventoFechaInicio',
        name : 'FECHA_INI',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idModEventoFechaFin').focus(true,true);
                }
            }
        }
    });
    
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idModEventoFechaFin',
        name : 'FECHA_FIN',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('btnmodificarEVENTO').focus(true,true);
                }
            }
        }
    });
    
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Evento',
            defaultType: 'textfield',
            items: [comboENTIDAD, descripcion, fechaIni, fechaFin]
        }
    }];

    var eventoModificarFormPanel = new Ext.FormPanel({
        id:'idDatosEvento',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id : 'btnmodificarEVENTO',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idEVENTOSeleccionadoID_EVENTO!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                eventoModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'EVENTO?op=MODIFICAR',
                                    params: {
                                        ID_EVENTO:idEVENTOSeleccionadoID_EVENTO
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarEVENTO').close();
                                        Ext.getCmp('gridEVENTO').store.reload();
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
                Ext.getCmp('modificarEVENTO').close();
            }
        }]
    });

    eventoModificarFormPanel.getForm().load({
        url : 'EVENTO?op=DETALLE',
        method : 'POST',
        params:{
            ID_EVENTO: idEVENTOSeleccionadoID_EVENTO
        },
        waitMsg : 'Cargando...',
        success : function(form, accion) {  
            var obj = Ext.util.JSON.decode(accion.response.responseText);
            comboENTIDAD.setValue(obj.data.ENTIDAD.ENTIDAD);
            comboENTIDAD.setRawValue(obj.data.ENTIDAD.DESCRIPCION_ENTIDAD);            
        }
    });

    var win = new Ext.Window({
        title:'Modificar EVENTO',
        id : 'modificarEVENTO',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [eventoModificarFormPanel]
    });
    return win;

}
function pantallaAgregarEVENTO() {
    var comboENTIDAD = getCombo('ENTIDAD','ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD','DESCRIPCION_ENTIDAD','ENTIDAD','ENTIDAD');
    comboENTIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDescripcionEvento').focus(true, true);
    }, null,null ) ;

    var descripcion = new Ext.form.TextField({
        fieldLabel:'Descripcion',
        id:'idDescripcionEvento',
        name:'DESCRIPCION',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ){
                    Ext.getCmp('idFechaInicioEvento').focus(true, true);

                }
            }
        }
    });
    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        id:'idFechaInicioEvento',
        name : 'FECHA_INI',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idFechaFinEvento').focus(true,true);
                }
            }
        }
    });
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFinEvento',
        name : 'FECHA_FIN',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idBtnAgregarEvento').focus(true,true);
                }
            }
        }
    });
    
    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Evento',
            defaultType: 'textfield',
            items: [comboENTIDAD, descripcion, fechaIni, fechaFin]
        }
    }];   
    
    var gesPanel = new Ext.FormPanel({
        id:'idDatosEvento',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id:'idBtnAgregarEvento',
            text: 'Aceptar',
            formBind:true,
            handler: function(esteObjeto){
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            gesPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'EVENTO?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                                    if (obj2.success) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarEVENTO').close();
                                        Ext.getCmp('gridEVENTO').store.reload();
                                    }else{
                                        Ext.Msg.show({
                                            title   : "Estado",
                                            msg     : obj2.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon    : Ext.MessageBox.ERROR
                                        });
                                    }
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
            text: 'Reset',
            handler: function(){
                gesPanel.getForm().reset();
            }
        },{
            text : 'Salir',
            handler : function(){
                gesPanel.getForm().reset();
                win.close();
            }

        }]
    });
    var win = new Ext.Window({
        title:'Agregar EVENTO',
        id : 'agregarEVENTO',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        items : [gesPanel]        
    });
    return win;
}
function pantallaAgregarSECTOR(){
    var comboEVENTO = getCombo('EVENTO','EVENTO','EVENTO','EVENTO','DESCRIPCION_EVENTO','EVENTO','EVENTO','DESCRIPCION_EVENTO','EVENTO','EVENTO');
    comboEVENTO.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idDescripcionSectorEvento').focus(true, true);
    }, null,null ) ;
    var descripcion = new Ext.form.TextField({
        fieldLabel:'Descripcion',
        id:'idDescripcionSectorEvento',
        name:'DESCRIPCION',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey()==9 || esteEvento.getKey() == 13 ){
                    Ext.getCmp('idMontoSectorEvento').focus(true, true);
                }
            }
        }
    });
    var monto = new Ext.form.TextField({
        fieldLabel:'Monto',
        id:'idMontoSectorEvento',
        name:'MONTO',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'keyup' : function(esteObjeto, esteEvento)   {
                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
            },
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ){
                    Ext.getCmp('idBtnAgregarSectorEvento').focus(true, true);

                }
            }
        }
    });    
    
    var individual = [comboEVENTO, descripcion, monto];   
    
    var agregarSectorPanel = new Ext.FormPanel({
        id:'idDatosSectorEvento',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id:'idBtnAgregarSectorEvento',
            text: 'Aceptar',
            formBind:true,
            handler: function(esteObjeto){
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            agregarSectorPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'SECTOR?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                params : {
                                    ID_EVENTO : idEVENTOSeleccionadoID_EVENTO
                                },
                                success : function(form, accion) {
                                    var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                                    if (obj2.success) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarSECTOR').close();                                         
                                        Ext.getCmp('gridSECTOR').store.load({
                                            params : {
                                                ID_EVENTO :idEVENTOSeleccionadoID_EVENTO,
                                                start : 0,
                                                limit : 20
                                            }
                                        });
                                    }else{
                                        Ext.Msg.show({
                                            title   : "Estado",
                                            msg     : obj2.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon    : Ext.MessageBox.ERROR
                                        });
                                    }
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
            text: 'Reset',
            handler: function(){
                agregarSectorPanel.getForm().reset();
            }
        },{
            text : 'Salir',
            handler : function(){
                agregarSectorPanel.getForm().reset();
                win.close();
            }

        }]
    });
    var win = new Ext.Window({
        title:'Agregar Sector',
        id : 'agregarSECTOR',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        items : [agregarSectorPanel]        
    });
    return win;
}
function pantallaModificarSECTOR(){
    var descripcion = new Ext.form.TextField({
        fieldLabel:'Descripcion',
        id:'idDescripcionModSectorEvento',
        name:'DESCRIPCION',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ){
                    Ext.getCmp('idMontoSectorModEvento').focus(true, true);

                }
            }
        }
    });
    var monto = new Ext.form.TextField({
        fieldLabel:'Monto',
        id:'idMontoSectorModEvento',
        name:'MONTO',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'keyup' : function(esteObjeto, esteEvento)   {
                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
            },
            'specialkey' :function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ){
                    Ext.getCmp('idBtnModSectorEvento').focus(true, true);

                }
            }
        }
    });    
    
    var individual = [descripcion, monto];   
    
    var sectorModificarFormPanel = new Ext.FormPanel({
        id:'idDatosSectorEvento',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id:'idBtnModSectorEvento',
            text: 'Aceptar',
            formBind:true,
            handler: function(esteObjeto){
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            sectorModificarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'SECTOR?op=MODIFICAR',
                                timeout : TIME_OUT_AJAX,
                                params : {
                                    ID_SECTOR: idSECTOR_EVENTOSeleccionado
                                },
                                success : function(form, accion) {
                                    var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                                    if (obj2.success) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarSECTOR').close();                                         
                                        Ext.getCmp('gridSECTOR').store.load({
                                            params : {
                                                ID_EVENTO :idEVENTOSeleccionadoID_EVENTO,
                                                start : 0,
                                                limit : 20
                                            }
                                        });
                                    }else{
                                        Ext.Msg.show({
                                            title   : "Estado",
                                            msg     : obj2.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon    : Ext.MessageBox.ERROR
                                        });
                                    }
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
            text: 'Reset',
            handler: function(){
                sectorModificarFormPanel.getForm().reset();
            }
        },{
            text : 'Salir',
            handler : function(){
                sectorModificarFormPanel.getForm().reset();
                win.close();
            }

        }]
    });
    
    sectorModificarFormPanel.getForm().load({
        url : 'SECTOR?op=DETALLE',
        method : 'POST',
        params:{
            ID_SECTOR: idSECTOR_EVENTOSeleccionado
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar Sector',
        id : 'modificarSECTOR',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        items : [sectorModificarFormPanel]        
    });
    return win;
}
function gridSUSCRIPTORES(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'SUSCRIPTORES?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'SUSCRIPTORES',
            totalProperty : 'TOTAL',
            id : 'CI',
            fields : ['CI','NOMBRE','APELLIDO', 'TELEFONO', 'EMAIL']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'CI',
        width: anchoDefaultColumnas,
        dataIndex: 'CI'
    },{
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    },{
        header:'APELLIDO',
        width: anchoDefaultColumnas,
        dataIndex: 'APELLIDO'
    },{
        header:'TELEFONO',
        width: anchoDefaultColumnas,
        dataIndex: 'TELEFONO'
    },{
        header:'EMAIL',
        width: anchoDefaultColumnas,
        dataIndex: 'EMAIL'
    }]);
    var gridSUSCRIPTORES = new Ext.grid.GridPanel({
        title:'Suscriptores',
        id:'gridSUSCRIPTORES',
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
                if(Ext.getCmp('agregarSUSCRIPTORES') == undefined) pantallaAgregarSUSCRIPTORES().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){                
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
                                url : 'SUSCRIPTORES?op=BORRAR',
                                method : 'POST',
                                params : {
                                    CI : idSUSCRIPTORESSeleccionado
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
                                        Ext.getCmp('gridSUSCRIPTORES').store.reload();
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
        },{
            text:'Asignar Eventos',
            tooltip:TOOL_TIP_AGREGAR,
            id :'idAsignarSuscriptorAEventos',
            iconCls:'add',
            disabled:true,
            handler:function(){
                if(Ext.getCmp('idAsignacionEventos') == undefined) asignacionSuscriptorAEvento().show().center();
            }
        },{
            text:'Suscriptor',
            id:'idSucriptorEvento',
            xtype:'textfield',
            emptyText:'Nombre..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridSUSCRIPTORES').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA:esteObjeto.getValue()
                            }
                        });

                    }
                }
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
                idSUSCRIPTORESSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;
                Ext.getCmp('idAsignarSuscriptorAEventos').enable();
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {                
                if ( (Ext.getCmp("modificarSUSCRIPTORES") == undefined)) {
                    idSUSCRIPTORESSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarSUSCRIPTORES().show().center();
                }
            }
        }
    });
    return gridSUSCRIPTORES;

}
function pantallaAgregarSUSCRIPTORES(){
    var ci = new Ext.form.NumberField({
        fieldLabel:'Ced. de Identidad',
        id:'idCISUSCRIPTORES',
        name:'CI',
        enableKeyEvents:true,
        allowBlank : false,
        listeners : {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idTelefonoSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var nombre = new Ext.form.TextField({
        fieldLabel:'Nombre',
        id:'idNombreSUSCRIPTORES',
        name:'NOMBRE',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idApellidoSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });    
    var apellido = new Ext.form.TextField({
        fieldLabel:'Apellido',
        id:'idApellidoSUSCRIPTORES',
        name:'APELLIDO',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idCISUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var telefono = new Ext.form.NumberField({
        fieldLabel:'Telefono',
        id:'idTelefonoSUSCRIPTORES',
        name:'TELEFONO',
        enableKeyEvents:true,
        allowBlank : true,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idEmailSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var email = new Ext.form.TextField({
        fieldLabel:'Email',
        id:'idEmailSUSCRIPTORES',
        name:'EMAIL',
        vtype:'email',
        enableKeyEvents:true,
        allowBlank : true,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idBtnAgregarSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    
    var individual = [nombre, apellido, ci, telefono, email];   
    
    var agregarSUSCRIPTORESPanel = new Ext.FormPanel({
        id:'idDatosSUSCRIPTORES',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id:'idBtnAgregarSUSCRIPTORES',
            text: 'Aceptar',
            formBind:true,
            handler: function(esteObjeto){
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            agregarSUSCRIPTORESPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'SUSCRIPTORES?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                params : {
                                    ID_EVENTO : idEVENTOSeleccionadoID_EVENTO
                                },
                                success : function(form, accion) {
                                    var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                                    if (obj2.success) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('agregarSUSCRIPTORES').close();                                         
                                        Ext.getCmp('gridSUSCRIPTORES').store.load({
                                            params : {
                                                ID_EVENTO :idEVENTOSeleccionadoID_EVENTO,
                                                start : 0,
                                                limit : 20
                                            }
                                        });
                                    }else{
                                        Ext.Msg.show({
                                            title   : "Estado",
                                            msg     : obj2.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon    : Ext.MessageBox.ERROR
                                        });
                                    }
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
            text: 'Reset',
            handler: function(){
                agregarSUSCRIPTORESPanel.getForm().reset();
            }
        },{
            text : 'Salir',
            handler : function(){
                agregarSUSCRIPTORESPanel.getForm().reset();
                win.close();
            }

        }]
    });
    var win = new Ext.Window({
        title:'Agregar Suscriptores',
        id : 'agregarSUSCRIPTORES',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        items : [agregarSUSCRIPTORESPanel]        
    });
    return win;    
}
function pantallaModificarSUSCRIPTORES(){
    var ci = new Ext.form.TextField({
        fieldLabel:'Ced. de Identidad',
        id:'idCIModSUSCRIPTORES',
        name:'CI',
        enableKeyEvents:true,
        disabled:true,
        allowBlank : false,
        listeners : {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idTelefonoModSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var nombre = new Ext.form.TextField({
        fieldLabel:'Nombre',
        id:'idNombreModSUSCRIPTORES',
        name:'NOMBRE',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idApellidoModSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });    
    var apellido = new Ext.form.TextField({
        fieldLabel:'Apellido',
        id:'idApellidoModSUSCRIPTORES',
        name:'APELLIDO',
        enableKeyEvents:true,
        allowBlank : false,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idCIModSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var telefono = new Ext.form.NumberField({
        fieldLabel:'Telefono',
        id:'idTelefonoModSUSCRIPTORES',
        name:'TELEFONO',
        enableKeyEvents:true,
        allowBlank : true,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idEmailModSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    var email = new Ext.form.TextField({
        fieldLabel:'Email',
        id:'idEmailModSUSCRIPTORES',
        name:'EMAIL',
        vtype:'email',
        enableKeyEvents:true,
        allowBlank : true,
        listeners: {
            'specialkey' :function(esteObjeto, este)   {
                if(este.getKey()==9 || este.getKey() == 13 ){
                    Ext.getCmp('idBtnModificarSUSCRIPTORES').focus(true, true);
                }
            }
        }
    });
    
    var individual = [nombre,apellido, ci,telefono, email ];   
    
    var modificarSUSCRIPTORESPanel = new Ext.FormPanel({
        id:'idDatosModSUSCRIPTORES',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [individual],
        buttons: [{
            id:'idBtnModificarSUSCRIPTORES',
            text: 'Modificar',
            formBind:true,
            handler: function(esteObjeto){
                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "ok") {
                            modificarSUSCRIPTORESPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'SUSCRIPTORES?op=MODIFICAR',
                                timeout : TIME_OUT_AJAX,
                                params : {
                                    CI : idSUSCRIPTORESSeleccionado
                                },
                                success : function(form, accion) {
                                    var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                                    if (obj2.success) {
                                        Ext.Msg.show({
                                            title :TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('ModificarSUSCRIPTORES').close();                                         
                                        Ext.getCmp('gridSUSCRIPTORES').store.reload();
                                    }else{
                                        Ext.Msg.show({
                                            title   : "Estado",
                                            msg     : obj2.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon    : Ext.MessageBox.ERROR
                                        });
                                    }
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
            text: 'Reset',
            handler: function(){
                modificarSUSCRIPTORESPanel.getForm().reset();
            }
        },{
            text : 'Salir',
            handler : function(){
                modificarSUSCRIPTORESPanel.getForm().reset();
                win.close();
            }

        }]
    });
    modificarSUSCRIPTORESPanel.getForm().load({
        url : 'SUSCRIPTORES?op=DETALLE',
        method : 'POST',
        params:{
            CI: idSUSCRIPTORESSeleccionado
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar Suscriptores',
        id : 'ModificarSUSCRIPTORES',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        items : [modificarSUSCRIPTORESPanel]        
    });
    return win;    

}
function asignacionSuscriptorAEvento(){ 

    var ds_providersDisponible = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'SUSCRIPTORES?op=LISTAR_EVENTOS'
        }),
        reader : new Ext.data.JsonReader({
            root : 'EVENTOS',
            id : 'ID_EVENTO',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_EVENTO'
        }, {
            name : 'DESCRIPCION'
        }])
    });

    ds_providersDisponible.load({
        params:{
            CI: idSUSCRIPTORESSeleccionado,
            FLAG_ASIGNACION: 'NO'
        }
    });
    var ds_providersSeleccionado = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'SUSCRIPTORES?op=LISTAR_EVENTOS'
        }),
        reader : new Ext.data.JsonReader({
            root : 'EVENTOS',
            id : 'ID_EVENTO',
            totalProperty : 'TOTAL'
        }, [{
            name : 'ID_EVENTO'
        }, {
            name : 'DESCRIPCION'
        }])
    });

    ds_providersSeleccionado.load({
        params:{
            CI: idSUSCRIPTORESSeleccionado,
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
                displayField: 'DESCRIPCION',
                valueField: 'ID_EVENTO'
            },{
                width: 250,
                height: 200,
                store: ds_providersSeleccionado,
                displayField: 'DESCRIPCION',
                valueField: 'ID_EVENTO'

            }]
        }],

        buttons: [{
            text: 'Guardar',
            handler: function(){
                if(formulario.getForm().isValid()){

                    formulario.getForm().submit({
                        method : 'POST',
                        waitTitle : 'Conectando',
                        waitMsg : 'Asignando los Eventos...',
                        url : 'SUSCRIPTORES?op=ASIGNAR_EVENTOS',
                        timeout : TIME_OUT_AJAX,
                        params:{
                            CI:idSUSCRIPTORESSeleccionado
                        },
                        success : function(form, accion) {
                            ds_providersSeleccionado.load({
                                params:{
                                    CI: idSUSCRIPTORESSeleccionado,
                                    FLAG_ASIGNACION: 'SI'
                                }
                            });
                            ds_providersDisponible.load({
                                params:{
                                    CI: idSUSCRIPTORESSeleccionado,
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
        title:'Asignacion de Eventos',
        id : 'idAsignacionEventos',
        autoWidth : true,
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        items : [formulario]
    });
    return win;


}