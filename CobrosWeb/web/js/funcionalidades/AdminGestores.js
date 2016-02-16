var idGESTORSeleccionadoID_GESTOR ;
function gridGESTOR(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'GESTOR?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'GESTOR',
            totalProperty : 'TOTAL',
            id : 'ID_GESTOR',
            fields : ['ID_GESTOR','NOMBRE','APELLIDO','CLIENTE_RECOMENDO','INDEPENDIENTE','FECHA_NACIMIENTO','SEXO']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    });
    var anchoDefaultColumnas= 10;
    var colModel = new Ext.grid.ColumnModel([{
        header:'CI GESTOR',
        width: anchoDefaultColumnas,
        dataIndex: 'ID_GESTOR'
    },{
        header:'NOMBRE',
        width: anchoDefaultColumnas,
        dataIndex: 'NOMBRE'
    },{
        header:'APELLIDO',
        width: anchoDefaultColumnas,
        dataIndex: 'APELLIDO'
    },{
        header:'CLIENTE RECOMENDO',
        width: anchoDefaultColumnas,
        dataIndex: 'CLIENTE_RECOMENDO'
    },{
        header:'TIPO',
        width: anchoDefaultColumnas,
        dataIndex: 'INDEPENDIENTE'
    },{
        header:'FECHA NACIMIENTO',
        width: anchoDefaultColumnas,
        dataIndex: 'FECHA_NACIMIENTO'
    },{
        header:'SEXO',
        width: anchoDefaultColumnas,
        dataIndex: 'SEXO'
    }]);
    var gridGESTOR = new Ext.grid.GridPanel({
        title:'Administración De Gestores',
        id:'gridGESTOR',
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
                if(Ext.getCmp('agregarGESTOR') == undefined) pantallaAgregarGESTOR().show().center();
            }
        },{
            text:'Quitar',
            tooltip:TOOL_TIP_QUITAR,
            iconCls:'remove',
            handler: function(){
                if(idGESTORSeleccionadoID_GESTOR!=undefined){
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
                                    url : 'GESTOR?op=BORRAR',
                                    method : 'POST',
                                    params : {
                                        ID_GESTOR : idGESTORSeleccionadoID_GESTOR
                                    },
                                    success : function(action) {
                                        var  obj = Ext.util.JSON.decode(action.responseText);
                                        if(obj.success){
                                            Ext.Msg.show({
                                                title :TITULO_ACTUALIZACION_EXITOSA,
                                                msg : CUERPO_ACTUALIZACION_EXITOSA,
                                                buttons : Ext.Msg.OK,
                                                icon : Ext.MessageBox.INFO
                                            });
                                            Ext.getCmp('gridGESTOR').store.reload();
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
            text:'Gestor',
            id:'idBusquedaGestor',
            xtype:'textfield',
            emptyText:'Gestor..',
            listeners : {
                'specialkey' :function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        Ext.getCmp('gridGESTOR').store.reload({
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
            text:'Reporte',
            tooltip:'Reporte',
            iconCls:'reporte',
            handler:function(){                
                pantallaReporteGestores().center();
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
                idGESTORSeleccionadoID_GESTOR = esteObjeto.getStore().getAt(rowIndex).id;   
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {                
                if ( (Ext.getCmp("modificarGESTOR") == undefined)) {
                    idGESTORSeleccionadoID_GESTOR = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarGESTOR().show().center();
                }
            }
        }
    });
    return gridGESTOR;
}
function pantallaModificarGESTOR() {
    var comboSexo = new Ext.form.ComboBox({
        fieldLabel: 'Sexo',
        id:'idSexo',
        name : 'SEXO',
        hiddenName : 'SEXO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth : 180,
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['MASCULINO', 'MASCULINO'],
            ['FEMENINO',  'FEMENINO']]
        }),
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                Ext.getCmp('idEmpresaGestor').focus(true,true);
            }
        }
    });

    var independiente =  new Ext.form.Checkbox ({
        fieldLabel: 'Independiente',
        vertical: true,
        columns: 1,
        name: 'INDEPENDIENTE',
        items: [{
            boxLabel: 'INDEPENDIENTE'
        }],
        listeners : {
            'check' : function(esteObjeto, checked)   {
                if(checked){
                    Ext.getCmp('idEmpresaGestor').disable();
                    Ext.getCmp('idTelefonoEmpresaGestor').disable();
                    Ext.getCmp('idDireccionEmpresaGestor').disable();
                }else{
                    Ext.getCmp('idEmpresaGestor').enable();
                    Ext.getCmp('idTelefonoEmpresaGestor').enable();
                    Ext.getCmp('idDireccionEmpresaGestor').enable();
                }
            }
        }
    });
    var comboLOCALIDAD=getCombo('LOCALIDAD','LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD');
    comboLOCALIDAD.listWidth=200;
    comboLOCALIDAD.allowBlank=false;
    comboLOCALIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idTelefonoGestor').focus(true,true);
    }, null,null ) ;
    var fechaNacimiento = new Ext.form.DateField({
        fieldLabel : 'Fecha Nacimiento',
        id:'idFechaNacimiento',
        name : 'FECHA_NACIMIENTO',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idSexo').focus(true,true);
                }
            }
        }
    });

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Personales',
            //autoHeight: true,
            //autoWidth : true,
            defaultType: 'textfield',
            items: [{
                id:'idNombreGestor',
                xtype:'textfield',
                name: 'NOMBRE',
                anchor:'95%',
                fieldLabel: 'Nombre',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idApellidoGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idApellidoGestor',
                xtype:'textfield',
                name: 'APELLIDO',
                anchor:'95%',
                fieldLabel: 'Apellido',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idCIGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idCIGestor',
                xtype:'textfield',
                name: 'ID_GESTOR',
                anchor:'95%',
                fieldLabel: 'Cedula de Identidad',
                minLength:5,
                allowBlank:false,
                disabled:true,
                vtype:'alphanum',
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDireccionGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDireccionGestor',
                xtype:'textfield',
                name: 'DIRECCION',
                anchor:'95%',
                fieldLabel: 'Dirección',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboLOCALIDAD.focus(true,true);
                        }
                    }
                }
            },comboLOCALIDAD,{
                id:'idTelefonoGestor',
                xtype:'textfield',
                name: 'TEL',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idCelGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idCelGestor',
                xtype:'textfield',
                name: 'CEL',
                anchor:'95%',
                fieldLabel: 'Celular',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idMailGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idMailGestor',
                xtype:'textfield',
                vtype:'email',
                name: 'MAIL',
                anchor:'95%',
                fieldLabel: 'E-mail',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idFechaNacimiento').focus(true,true);
                        }
                    }
                }
            },fechaNacimiento,comboSexo]
        }
    }, {
        bodyStyle: 'padding-left:10px;',
        items: [{
            xtype: 'fieldset',
            title: 'Datos de Empresa',
            //autoHeight: true,
            //autoWidth : true,
            defaultType: 'textfield',
            items: [independiente,{
                id:'idEmpresaGestor',
                xtype:'textfield',
                name: 'EMPRESA',
                anchor:'95%',
                fieldLabel: 'Empresa',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDireccionEmpresaGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDireccionEmpresaGestor',
                xtype:'textfield',
                name: 'EMP_DIRECCION',
                anchor:'95%',
                fieldLabel: 'Direccion',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idTelefonoEmpresaGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idTelefonoEmpresaGestor',
                xtype:'textfield',
                name: 'TEL_EMPRESA',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {

                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idClienteRecomendo').focus(true,true);
                        }
                    }
                }
            }]
        },{
            xtype: 'fieldset',
            title: 'Sugerido por',
            //autoHeight: true,
            //width: 350,contribuyenteModificarFormPanel
            defaultType: 'textfield',
            items: [{
                id:'idClienteRecomendo',
                xtype:'numberfield',
                name: 'CLIENTE_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Cedula de Identidad',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){                            
                            var conn = new Ext.data.Connection();
                            conn.request({
                                waitTitle: 'Conectando',
                                waitMsg: 'Cargando Campos',
                                url: 'GESTOR?op=DETALLE',
                                params: {
                                    ID_GESTOR:this.getRawValue()
                                },
                                method: 'POST',
                                success: function (respuestaServer) {
                                    var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                    if (obj.success) {
                                        Ext.getCmp('idNombreRecomendo').setValue(obj.data.NOMBRE+" "+obj.data.APELLIDO);
                                        Ext.getCmp('idNombreRecomendo').focus(true, true);
                                    }else{
                                        Ext.Msg.show({
                                            title : "Error",
                                            msg : "No existe gestor sugiriente",
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn : function(btn, text) {
                                                if (btn == "ok") {
                                                    Ext.getCmp('idClienteRecomendo').focus(true, true);
                                                    Ext.getCmp('idNombreRecomendo').setValue('');                                        
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            },{
                id:'idNombreRecomendo',
                xtype:'textfield',
                name: 'NOMBRE_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Nombre',
                allowBlank:true,
                disabled:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('btnmodificarGESTOR').focus(true,true);
                        }
                    }
                }
            }
            /*,{
                id:'idTelefonoRecomendo',
                xtype:'textfield',
                name: 'TEL_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                allowBlank:true,
                disabled:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idBtnAgregarGestor').focus(true,true);
                        }
                    }
                }
            }*/]
        }]
    }];

    var gestorModificarFormPanel = new Ext.FormPanel({
        id:'idDatosGestor',
        frame: true,
        labelWidth: 130,
        //autoWidth : true,
        //height : 'auto',
        monitorValid:true,
        items: [{
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        }],
        buttons: [{
            id : 'btnmodificarGESTOR',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idGESTORSeleccionadoID_GESTOR!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                gestorModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'GESTOR?op=MODIFICAR',
                                    params: {
                                        ID_GESTOR:Ext.getCmp('idCIGestor').getRawValue()
                                    },
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarGESTOR').close();
                                        Ext.getCmp('gridGESTOR').store.reload();
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
                Ext.getCmp('modificarGESTOR').close();
            }
        }]
    });

    gestorModificarFormPanel.getForm().load({
        url : 'GESTOR?op=DETALLE',
        method : 'POST',
        params:{
            ID_GESTOR: idGESTORSeleccionadoID_GESTOR
        },
        waitMsg : 'Cargando...'
    });

    var win = new Ext.Window({
        title:'Modificar GESTOR',
        id : 'modificarGESTOR',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [gestorModificarFormPanel]
    });
    return win;

}
function pantallaAgregarGESTOR() {
    var comboSexo = new Ext.form.ComboBox({
        fieldLabel: 'Sexo',
        id:'idSexo',
        name : 'SEXO',
        hiddenName : 'SEXO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        listWidth : 180,
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['MASCULINO', 'MASCULINO'],
            ['FEMENINO',  'FEMENINO']]
        }),
        listeners : {
            'select' : function(esteObjeto, esteEvento)   {
                Ext.getCmp('idEmpresaGestor').focus(true,true);
            }           
        }
    });

    var independiente =  new Ext.form.Checkbox ({
        fieldLabel: 'Independiente',
        vertical: true,
        columns: 1,
        name: 'INDEPENDIENTE',
        items: [{
            boxLabel: 'INDEPENDIENTE'
        }],
        listeners : {
            'check' : function(esteObjeto, checked)   {
                if(checked){
                    Ext.getCmp('idEmpresaGestor').disable();
                    Ext.getCmp('idTelefonoEmpresaGestor').disable();
                    Ext.getCmp('idDireccionEmpresaGestor').disable();
                }else{
                    Ext.getCmp('idEmpresaGestor').enable();
                    Ext.getCmp('idTelefonoEmpresaGestor').enable();
                    Ext.getCmp('idDireccionEmpresaGestor').enable();
                }
            }
        }
    });
    var comboLOCALIDAD=getCombo('LOCALIDAD','LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD','DESCRIPCION_LOCALIDAD','LOCALIDAD','LOCALIDAD');
    comboLOCALIDAD.listWidth=200;
    comboLOCALIDAD.allowBlank=false;
    comboLOCALIDAD.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idTelefonoGestor').focus(true,true);
    }, null,null ) ;
    var fechaNacimiento = new Ext.form.DateField({
        fieldLabel : 'Fecha Nacimiento',
        id:'idFechaNacimiento',
        name : 'FECHA_NACIMIENTO',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idSexo').focus(true,true);
                }
            }
        }
    });

    var individual = [{
        items: {
            xtype: 'fieldset',
            title: 'Datos Personales',
            //autoHeight: true,
            //autoWidth : true,
            defaultType: 'textfield',
            items: [{
                id:'idNombreGestor',
                xtype:'textfield',
                name: 'NOMBRE',
                hiddenName: 'NOMBRE',
                anchor:'95%',
                fieldLabel: 'Nombre',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idApellidoGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idApellidoGestor',
                xtype:'textfield',
                name: 'APELLIDO',
                anchor:'95%',
                fieldLabel: 'Apellido',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idCIGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idCIGestor',
                xtype:'textfield',
                name: 'CI',
                anchor:'95%',
                fieldLabel: 'Cedula de Identidad',
                minLength:5,
                allowBlank:false,
                vtype:'alphanum',
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDireccionGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDireccionGestor',
                xtype:'textfield',
                name: 'DIRECCION',
                anchor:'95%',
                fieldLabel: 'Dirección',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            comboLOCALIDAD.focus(true,true);
                        }
                    }
                }
            },comboLOCALIDAD,{
                id:'idTelefonoGestor',
                xtype:'textfield',
                name: 'TEL',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idCelGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idCelGestor',
                xtype:'textfield',
                name: 'CEL',
                anchor:'95%',
                fieldLabel: 'Celular',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idMailGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idMailGestor',
                xtype:'textfield',
                vtype:'email',
                name: 'MAIL',
                anchor:'95%',
                fieldLabel: 'E-mail',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idFechaNacimiento').focus(true,true);
                        }
                    }
                }
            },fechaNacimiento,comboSexo]
        }
    }, {
        bodyStyle: 'padding-left:10px;',
        items: [{
            xtype: 'fieldset',
            title: 'Datos de Empresa',
            //autoHeight: true,
            //autoWidth : true,
            defaultType: 'textfield',
            items: [independiente,{
                id:'idEmpresaGestor',
                xtype:'textfield',
                name: 'EMPRESA',
                anchor:'95%',
                fieldLabel: 'Empresa',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idDireccionEmpresaGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idDireccionEmpresaGestor',
                xtype:'textfield',
                name: 'EMP_DIRECCION',
                anchor:'95%',
                fieldLabel: 'Direccion',
                allowBlank:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idTelefonoEmpresaGestor').focus(true,true);
                        }
                    }
                }
            },{
                id:'idTelefonoEmpresaGestor',
                xtype:'textfield',
                name: 'TEL_EMPRESA',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {

                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idClienteRecomendo').focus(true,true);
                        }
                    }
                }
            }]
        },{
            xtype: 'fieldset',
            title: 'Sugerido por',
            //autoHeight: true,
            //width: 350,
            defaultType: 'textfield',
            items: [{
                id:'idClienteRecomendo',
                xtype:'numberfield',
                name: 'CLIENTE_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Cedula de Identidad',
                allowBlank:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            var conn = new Ext.data.Connection();
                            conn.request({
                                waitTitle: 'Conectando',
                                waitMsg: 'Cargando Campos',
                                url: 'GESTOR?op=DETALLE',
                                params: {
                                    ID_GESTOR:this.getRawValue()
                                },
                                method: 'POST',
                                success: function (respuestaServer) {
                                    var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                    if (obj.success) {
                                        Ext.getCmp('idNombreRecomendo').setValue(obj.data.NOMBRE+" "+obj.data.APELLIDO);
                                        Ext.getCmp('idNombreRecomendo').focus(true, true);
                                    }else{                                        
                                        Ext.Msg.show({
                                            title : "Error",
                                            msg : "No existe gestor sugiriente",
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn : function(btn, text) {
                                                if (btn == "ok") {
                                                    Ext.getCmp('idClienteRecomendo').focus(true, true);
                                                    Ext.getCmp('idNombreRecomendo').setValue('');
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            },{
                id:'idNombreRecomendo',
                xtype:'textfield',
                name: 'NOMBRE_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Nombre',
                allowsBlank:true,
                disabled:false,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idBtnAgregarGestor').focus(true,true);
                        }
                    }
                }
            }/*,{
                id:'idTelefonoRecomendo',
                xtype:'textfield',
                name: 'TEL_RECOMENDO',
                anchor:'95%',
                fieldLabel: 'Teléfono',
                disabled:true,
                listeners : {
                    'specialkey' : function(esteObjeto, esteEvento)   {
                        if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                            Ext.getCmp('idBtnAgregarGestor').focus(true,true);
                        }
                    }
                }
            }*/]
        }]
    }];

    var gesPanel = new Ext.FormPanel({
        id:'idDatosGestor',
        frame: true,
        labelWidth: 130,
        //autoWidth : true,
        //height : 'auto',
        monitorValid:true,
        items: [{
            layout: 'column',
            border: true,
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        }],
        buttons: [{
            id:'idBtnAgregarGestor',
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
                                url : 'GESTOR?op=AGREGAR',
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
                                        Ext.getCmp('agregarGESTOR').close();
                                        Ext.getCmp('gridGESTOR').store.reload();
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
        title:'Agregar GESTOR',
        id : 'agregarGESTOR',
        autoWidth : true,
        height : 'auto',
        closable : true,
        modal:true,
        resizable : true,
        //        x:'300',
        //        y:'250',
        items : [gesPanel]        
    });
    return win;
}

function pantallaReporteGestores(){

    var panel = {
        //        id : 'idPanelREPORTE_COBRANZA_RESUMIDO_CHEQUE_EFECTIVO',
        //        title:'REPORTE COBRANZA RESUMIDO CHEQUE EFECTIVO',
        xtype : 'panel',
        layout   : 'fit',
        border : false,
        autoScroll : true ,
        items: [FormPanelReporteGestores()]
    }
    //return panel;
    var win = new Ext.Window({
        id : 'idReporteGestores',
        title:'Reporte de Gestores',
        width : 'auto',
        height : 'auto',
        modal:true,
        closable : true,
        resizable : false,
        y:'140',
        items : [panel]
    });
    return win.show();

}

function FormPanelReporteGestores(){
    
    var comboSUCURSAL=getCombo('SUCURSAL','SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL','DESCRIPCION_SUCURSAL','SUCURSAL','SUCURSAL');
    
    var fechaInicio = new Ext.form.DateField({
        fieldLabel : 'Fecha Inicio',
        height : '22',
        anchor : '95%',
        id: 'idFecha',
        allowBlank:false,
        format:'dmY'
    });
    var fechaDeFin = new Ext.form.DateField({
        fieldLabel : 'Fecha Fin',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY'


    });
    comboSUCURSAL.addListener( 'select',function(esteCampo, nuevoValor,  viejoValor  ){
        Ext.getCmp('idReporteGestorCI').focus(true,true);

    }, null,null );

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
        labelWidth : 165,
        frame : true,
        autoHeight:true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [comboSUCURSAL,{
            xtype:'textfield',
            fieldLabel: 'CI',
            name: 'CI',
            id :'idReporteGestorCI',
            allowBlank : true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        fechaInicio.focus(true,true);
                    }
                }
            }
        },fechaInicio,fechaDeFin,formatoDeDescarga],
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
                        src : 'reportes?op=REPORTE-GESTOR'+'&idSucursal='+comboSUCURSAL.getValue()+'&fechaFin='+fechaDeFin.getRawValue()+'&fechaIni='+fechaInicio.getRawValue()+'&formatoDescarga='+formatoDeDescarga.getValue()+'&ci='+Ext.getCmp('idReporteGestorCI').getValue()
                    });
                } catch (e) {
                    alert(e);
                }
            }
        },{
            text : 'Reset',
            handler : function() {
                formPanel.getForm().reset();               
            }
        }]
    });
    return formPanel;
}