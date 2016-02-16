/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var idINFOSeleccionado;
function gridADMIN_INFO_SYS(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'INFORMACION?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'INFORMACIONES',
            totalProperty : 'TOTAL',
            id : 'ID_INFORMACION',
            fields : ['RED','RECAUDADOR','SUCURSAL','TERMINAL','MENSAJE','FECHA_INI','FECHA_FIN','ACTIVO']
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
        header: 'Sucursal',
        dataIndex: 'SUCURSAL',
        width: anchoDefaultColumnas
    },{
        header: 'Terminal',
        dataIndex: 'TERMINAL',
        width: anchoDefaultColumnas
    },{
        header: 'Mensaje',
        dataIndex: 'MENSAJE',
        width: anchoDefaultColumnas
    },{
        header: 'Fecha Ini',
        dataIndex: 'FECHA_INI',
        width: anchoDefaultColumnas
    },{
        header: 'Fecha Fin',
        dataIndex: 'FECHA_FIN',
        width: anchoDefaultColumnas
    },{
        header: 'Activo',
        dataIndex: 'ACTIVO',
        width: anchoDefaultColumnas
    }]);
    var gridADMIN_INFO_SYS = new Ext.grid.GridPanel({
        title:'Administración De Informacion',
        id:'gridADMIN_INFO_SYS',
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
                if(Ext.getCmp('agregarINFORMACION') == undefined) pantallaAgregarINFORMACIONES().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idINFOSeleccionado!=undefined){
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
                                    url : 'INFORMACION?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_INFORMACION : idINFOSeleccionado
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
                                            Ext.getCmp('gridADMIN_INFO_SYS').store.reload();
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
        }/*,{
            text:'Informaciones',
            id:'idBusquedaInformaciones',
            xtype:'textfield',
            emptyText:'Infos..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridADMIN_INFO_SYS').store.reload({
                            params: {
                                start:0,
                                limit:20,
                                CONSULTA:esteObjeto.getValue()
                            }
                        });

                    }
                }
            }
        }*/,{
            text:'Salir',
            tooltip:'Salir',
            iconCls:'logout',
            handler:function(){

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
                idINFOSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;                
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarINFORMACIONES") == undefined)) {
                    idINFOSeleccionado = esteObjeto.getStore().getAt(rowIndex).id;                    
                    pantallaModificarINFORMACIONES().show().center();
                }
            }
        }
    });
    return gridADMIN_INFO_SYS;
}
function pantallaModificarINFORMACIONES() {
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');    
    var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    
    comboRECAUDADOR.disable();
    comboSUCURSAL.disable();
    comboTERMINAL.disable();
    
    comboRED.addListener('select', function(esteCombo, record,  index  ){ 
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();       
    }, null,null );
    
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboTERMINAL.disable();
            comboRECAUDADOR.reset();
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;
    
    comboRECAUDADOR.addListener('select', function(esteCombo, record,  index  ){
        comboTERMINAL.enable();
        comboSUCURSAL.enable();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null );
    
    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboSUCURSAL.disable();
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;
    comboSUCURSAL.addListener('select', function(esteCombo, record,  index  ){
        comboTERMINAL.store.baseParams['ID_SUCURSAL'] = record.data.SUCURSAL;
        comboTERMINAL.store.reload();
    }, null,null );
    comboSUCURSAL.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;   
    
    
    var comboACTIVO=  new Ext.form.ComboBox({
        fieldLabel : 'Activo',
        hiddenName : 'ACTIVO',
        valueField : 'TIPO',
        id  :'idInfActivo',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    
    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        id:'idFechaInicioInfo',
        name : 'FECHA_INI',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    
            }
            }
        }
    });
    
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFinInfo',
        name : 'FECHA_FIN',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    
            }
            }
        }
    });
    
    var mensaje = new Ext.form.TextArea({
        fieldLabel:'Mensaje',
        name:'MENSAJE',
        anchor :'95%',
        enableKeyEvents:true,
        allowBlank : false
    });   

    var sysInfoModificarFormPanel = new Ext.FormPanel({
        id:'idDatosFacturas',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboRED, comboRECAUDADOR, comboSUCURSAL, comboTERMINAL, mensaje, fechaIni, fechaFin, comboACTIVO],
        buttons: [{
            id : 'btnPreviewMod',
            text : 'Preview',
            handler : function() {
                jQuery.noticeAdd({
                    text: mensaje.getValue(),
                    stay: true
                });
            }  
        },{
            id : 'btnmodificarINFORMACIONES',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idINFOSeleccionado!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                sysInfoModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'INFORMACION?op=MODIFICAR',
                                    params: {
                                        ID_INFORMACION : idINFOSeleccionado
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarINFORMACIONES').close();
                                        Ext.getCmp('gridADMIN_INFO_SYS').store.reload();
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
            text: 'Reset',
            handler: function(){
                comboRECAUDADOR.disable();
                comboSUCURSAL.disable();
                comboTERMINAL.disable();
                sysInfoModificarFormPanel.getForm().reset();
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('modificarINFORMACIONES').close();
            }
        }]
    });

    sysInfoModificarFormPanel.getForm().load({
        url : 'INFORMACION?op=DETALLE',
        method : 'POST',
        params:{            
            ID_INFORMACION:idINFOSeleccionado
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar INFORMACIONES',
        id : 'modificarINFORMACIONES',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [sysInfoModificarFormPanel]
    });
    return win;

}
function pantallaAgregarINFORMACIONES() {
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR=getCombo('RECAUDADOR','RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR','DESCRIPCION_RECAUDADOR','RECAUDADOR','RECAUDADOR');
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');    
    var comboTERMINAL = getCombo('TERMINAL','TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL','DESCRIPCION_TERMINAL','TERMINAL','TERMINAL');
    comboRECAUDADOR.disable();
    comboSUCURSAL.disable();
    comboTERMINAL.disable();
    
    comboRED.addListener('select', function(esteCombo, record,  index  ){ 
        comboRECAUDADOR.enable();
        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();       
    }, null,null );
    
    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboRECAUDADOR.disable();
            comboTERMINAL.disable();
            comboRECAUDADOR.reset();
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;
    
    comboRECAUDADOR.addListener('select', function(esteCombo, record,  index  ){
        comboTERMINAL.enable();
        comboSUCURSAL.enable();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();
    }, null,null );
    
    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboSUCURSAL.disable();
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;
    comboSUCURSAL.addListener('select', function(esteCombo, record,  index  ){
        comboTERMINAL.store.baseParams['ID_SUCURSAL'] = record.data.SUCURSAL;
        comboTERMINAL.store.reload();
    }, null,null );
    comboSUCURSAL.addListener( 'change',function(esteCombo,newValue, oldValue ){
        if(esteCombo.getRawValue().length==0){
            comboTERMINAL.reset();
        }        
    }, null,null  ) ;
    
    var comboACTIVO=  new Ext.form.ComboBox({
        fieldLabel : 'Activo',
        hiddenName : 'ACTIVO',
        valueField : 'TIPO',
        id  :'idInfActivo',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value :'S',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO']]

        })
    });
    
    var fechaIni = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        id:'idFechaInicioInfo',
        name : 'FECHA_INI',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    
            }
            }
        }
    });
    
    var fechaFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        id:'idFechaFinInfo',
        name : 'FECHA_FIN',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'d/m/Y',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    
            }
            }
        }
    });
    
    var mensaje = new Ext.form.TextArea({
        fieldLabel:'Mensaje',
        name:'MENSAJE',
        anchor :'95%',
        enableKeyEvents:true,
        allowBlank : false
    });      

    var sysInfoAgregarFormPanel = new Ext.FormPanel({
        id:'idDatosFacturas',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboRED, comboRECAUDADOR, comboSUCURSAL, comboTERMINAL, mensaje, fechaIni, fechaFin, comboACTIVO],
        buttons: [{
            id : 'btnPreviewINFORMACION',
            text : 'Preview',
            handler : function() {
                jQuery.noticeAdd({
                    text: mensaje.getValue(),
                    stay: true
                });
            }  
        },{
            id : 'btnagregarINFORMACION',
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
                            sysInfoAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Agregando...',
                                url : 'INFORMACION?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarINFORMACION').close();
                                    Ext.getCmp('gridADMIN_INFO_SYS').store.reload();
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
                comboRECAUDADOR.disable();
                comboSUCURSAL.disable();
                comboTERMINAL.disable();
                sysInfoAgregarFormPanel.getForm().reset();
            }
        },{
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('agregarINFORMACION').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar INFORMACION',
        id : 'agregarINFORMACION',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [sysInfoAgregarFormPanel]
    });
    return win;
}


