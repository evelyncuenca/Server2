/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function gridSORTEADOR_GESTOR(){
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
        title:'Gestores disponibles para Sorteo',
        id:'gridSORTEADOR_GESTOR',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
        tbar:[{
            text:'Sortear',
            tooltip:'Sortear',
            iconCls:'sortear',
            handler:function(){
                if(Ext.getCmp('winConfiguracionSORTEO') == undefined) pantallaConfiguracionSorteo().show().center();
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
        iconCls:'icon-grid'
    });
    return gridGESTOR;

}
function pantallaConfiguracionSorteo(){
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
     comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){
        Ext.getCmp('idMin').focus(true,true);
    }, null,null ) ;
    comboSUCURSAL.allowBlank=false;
    var fechaDesde = new Ext.form.DateField({
        fieldLabel : 'Fecha Desde',
        id:'idFechaDesdeSorteo',
        name : 'FECHA_DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idMin').focus(true,true);
                }
            }
        }
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel : 'Fecha Hasta',
        id:'idFechaHastaSorteo',
        name : 'FECHA_HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:true,
        format:'dmY',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    Ext.getCmp('idSortearGestor').focus(true,true);
                }
            }
        }
    });
    var gestorConfiguracionFormPanel = new Ext.FormPanel({
        id:'idConfiguracionSorteo',
        frame: true,
        labelWidth: 130,
        monitorValid:true,
        items: [comboSUCURSAL,{
            xtype:'textfield',
            fieldLabel: 'Minimo de Formularios',
            name: 'MIN',
            id :'idMin',
            allowBlank : false,
            width: '50',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idFechaDesdeSorteo').focus(true,true);
                    }
                }
            }
        },fechaDesde,fechaHasta],
        buttons: [{
            text : 'Sortear',
            id:'idSortearGestor',
            formBind : true,
            handler : function() {
                gestorConfiguracionFormPanel.getForm().submit({
                    method : 'POST',
                    waitTitle : 'Sorteo',
                    waitMsg : 'Calculando Ganador...',
                    url : 'GESTOR?op=SORTEAR',
                    timeout : TIME_OUT_AJAX,
                    success : function(form, accion) {
                        var obj = Ext.util.JSON .decode(accion.response.responseText);
                        if(Ext.getCmp('ganadorGESTOR') == undefined) {
                            if(obj.GANADOR!=null){
                                pantallaGanadorGESTOR(obj.GANADOR).show().center();
                            }else{
                                Ext.Msg.show({
                                    title : FAIL_TITULO_GENERAL,
                                    msg : obj.motivo,
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.MessageBox.INFO
                                });
                            }
                        }
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
        },{
            text : 'Salir',
            handler : function() {
                Ext.getCmp('winConfiguracionSORTEO').close();
            }
        }]
    });

    var win = new Ext.Window({
        title:'Configuración de Sorteo',
        id : 'winConfiguracionSORTEO',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [gestorConfiguracionFormPanel]
    });
    return win;

}
function pantallaGanadorGESTOR(idGestor) {
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
                                timeout : 300,
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
                            Ext.getCmp('btnganadorGESTOR').focus(true,true);
                        }
                    }
                }
            }]
        }]
    }];

    var gestorGanadorFormPanel = new Ext.FormPanel({
        id:'idDatosGestorGanador',
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
            text : 'Cancelar',
            handler : function() {
                Ext.getCmp('ganadorGESTOR').close();
            }
        }]
    });

    gestorGanadorFormPanel.getForm().load({
        url : 'GESTOR?op=DETALLE',
        method : 'POST',
        timeout : TIME_OUT_AJAX,
        waitMsg : 'Obteniendo Ganador...',
        params:{
            ID_GESTOR:idGestor
        }
    });

    var win = new Ext.Window({
        title:'Gestor GANADOR!!!',
        id : 'ganadorGESTOR',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [gestorGanadorFormPanel]
    });
    return win;

}