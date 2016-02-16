FAIL_TITULO_GENERAL='Atención';
FAIL_CUERPO_GENERAL='No se pudo realizar la operación.';

TITULO_CONFIRMACION_AGREGAR_REGISTRO_GRID='Confirmación';
CUERPO_CONFIRMACION_AGREGAR_REGISTRO_GRID='¿Está seguro que desea agregar el registro?';

WAIT_TITLE_MODIFICANDO='Conectando';
WAIT_MSG_MODIFICANDO='Modificando los datos del registro seleccionado...';

TITULO_ACTUALIZACION_EXITOSA = 'Info';
CUERPO_ACTUALIZACION_EXITOSA = 'Actualización Exitosa';
var idCONTRIBUYENTESeleccionadoID_CONTRIBUYENTE;

function panelConsultaContribuyentes() {
    var panel = {
        id : 'panelConsultaContribuyentes',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : true,
        items : [cabeceraConsultaContribuyentes(),gridCONTRIBUYENTE()]
    }

    return panel;

}

function cabeceraConsultaContribuyentes(){
    var cedulaIdentidad = new Ext.form.TextField({
        fieldLabel: 'Criterio de búsqueda',
        name   :'CI',
        id     : 'idCI',
        height : '20',
        anchor : '39%',
        emptyText  :  'CI o RAZON SOCIAL',
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {

                if(esteEvento.getKey() == 13  ){

                    Ext.getCmp('idBtnConsulta').focus(true,true);
                }
            }
        }
    })

    var formPanel = new Ext.form.FormPanel({
        id: 'cabeceraContribuyentes',
        labelWidth : 120,
        title:'Consulta Contribuyentes',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },

        items :[{
            columnWidth:.5,
            layout: 'form',
            items: [cedulaIdentidad]
        },{
            layout:'column',
            bodyStyle: 'left:125px;',
            items:[{
                xtype: 'button',
                text:  'Consultar',
                id   : 'idBtnConsulta',
                width:'93',
                formBind : true,
                handler : function() {
                    Ext.getCmp('gridCONTRIBUYENTE').store.baseParams['start'] = 0;
                    Ext.getCmp('gridCONTRIBUYENTE').store.baseParams['limit'] = 20;
                    Ext.getCmp('gridCONTRIBUYENTE').store.baseParams['CONSULTA'] = Ext.getCmp('idCI').getValue();
                    Ext.getCmp('gridCONTRIBUYENTE').store.load();
                    Ext.getCmp('gridCONTRIBUYENTE').setHeight(
                        Ext.getCmp('panelConsultaContribuyentes').getHeight() - 
                        Ext.getCmp('cabeceraContribuyentes').getHeight());
                }
            },{
                xtype   : 'button',
                text    : 'Agregar',
                width   : '93',
                id      : 'idBtnAgregar',
                formBind: true,
                handler : function(){
                    if(Ext.getCmp('agregarCONTRIBUYENTE') == undefined) pantallaAgregarContribuyente().show();
                    Ext.getCmp('idModalidad').setValue(2);
                    Ext.getCmp('idMesCierre').setValue(12);
                                       
                }
            }]
        }]
    });
    return formPanel;

}

function pantallaAgregarContribuyente(){

    var comboTipoContribuyente=  new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName : 'TIPO_CONTRIBUYENTE',
        valueField : 'TIPO',
        id  :'idTipoContrib',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRucNuevo').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'Físico'],
            ['2', 'Jurídico']]

        })
    });
    var comboMesDeCierre=  new Ext.form.ComboBox({
        fieldLabel: 'MES DE CIERRE',
        hiddenName : 'MES_CIERRE',
        valueField : 'MES_CIERRE',
        id  :'idMesCierre',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRazonSocial').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['MES_CIERRE', 'descripcion'],
            data : [['4', 'Abril'],
            ['6', 'Junio'],
            ['12', 'Diciembre']]

        })
    });

    var estado =  new Ext.form.ComboBox({
        fieldLabel: 'ESTADO',
        hiddenName : 'ESTADO',
        valueField : 'ESTADO',
        id  :'idEstadoContrib',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value :'ACTIVO',
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRazonSocial').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['ESTADO', 'descripcion'],
            data : [['ACTIVO', 'ACTIVO'],
            ['SUSPENSION TEMPORAL ', 'SUSPENSION TEMPORAL'],
            ['BLOQUEADO', 'BLOQUEADO']]
        })
    });

    var comboModalidadContribuyente=  new Ext.form.ComboBox({
        fieldLabel: 'MODALIDAD',
        hiddenName : 'MODALIDAD_CONTRIBUYENTE',
        valueField : 'MODALIDAD',
        id  :'idModalidad',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idMesCierre').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['MODALIDAD', 'descripcion'],
            data : [['1', 'Grande'],
            ['2', 'Pequeño'],
            ['3', 'Mediano']]

        })
    }); 
    
    var contribuyenteAgregarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelAgregarCONTRIBUYENTE',
        labelWidth : 100,
        labelAlign: 'left',      
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID CONTRIBUYENTE',
            name:'ID_CONTRIBUYENTE',
            xtype:'textfield',
            inputType:'hidden'
        },{
            layout:'form',
            items:[comboTipoContribuyente]
        },{
            fieldLabel:'RUC NUEVO',
            name:'RUC_NUEVO',
            id  :'idRucNuevo',
            xtype:'numberfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idDV').focus(true,true);
                        Ext.getCmp('idDV').setValue(MODULO11(Ext.getCmp('idRucNuevo').getRawValue()));
                    }
                }
            }
        },{
            fieldLabel:'DV',
            name:'DIGITO_VERIFICADOR',
            id  :'idDV',
            xtype:'numberfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idRucViejo').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'RUC ANTERIOR',
            id  :'idRucViejo',
            name:'RUC_ANTERIOR',
            xtype:'textfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idModalidad').focus(true,true);
                    }
                }
            }
        },{
            layout:'form',
            items:[comboModalidadContribuyente]
        },{
            layout:'form',            
            items:[comboMesDeCierre]
        },{
            fieldLabel:'RAZON SOCIAL',
            id  :'idRazonSocial',
            name:'NOMBRE_CONTRIBUYENTE',
            xtype:'textfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        estado.focus(true,true);
                    }
                }
            }
        },estado],
        buttons : [{
            id : 'btnagregarCONTRIBUYENTE',
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
                            contribuyenteAgregarFormPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : 'Conectando',
                                waitMsg : 'Asignando...',
                                url : 'CONTRIBUYENTE?op=AGREGAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {
                                    Ext.Msg.show({
                                        title :TITULO_ACTUALIZACION_EXITOSA,
                                        msg : CUERPO_ACTUALIZACION_EXITOSA,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.INFO
                                    });
                                    Ext.getCmp('agregarCONTRIBUYENTE').close();
                                    Ext.getCmp('gridCONTRIBUYENTE').store.reload();
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
                Ext.getCmp('agregarCONTRIBUYENTE').close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar Contribuyente',
        id : 'agregarCONTRIBUYENTE',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        resizable : false,
        items : [contribuyenteAgregarFormPanel]
    });
    return win;
}

function gridCONTRIBUYENTE(){
    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'CONTRIBUYENTE?op=CONSULTAR_CONTRIBUYENTE'
        }),
        reader : new Ext.data.JsonReader({
            root : 'CONTRIBUYENTE',
            totalProperty : 'TOTAL',
            id : 'ID_CONTRIBUYENTE',
            fields : ['ID_CONTRIBUYENTE','RUC_NUEVO','DIGITO_VERIFICADOR','RUC_ANTERIOR','NOMBRE_CONTRIBUYENTE','MODALIDAD_CONTRIBUYENTE','TIPO_CONTRIBUYENTE','MES_CIERRE', 'ESTADO']
        }),
        listeners : {
            'beforeload' : function(store, options) {
                st.baseParams['CONSULTA'] = Ext.getCmp('idCI').getValue();
            }
        }
    });

    var anchoDefaultColumnas= 150;
    var colModel = new Ext.grid.ColumnModel([{
        header:'RUC NUEVO',
        width: anchoDefaultColumnas,
        dataIndex: 'RUC_NUEVO'
    },{
        header:'DV',
        width: anchoDefaultColumnas,
        dataIndex: 'DIGITO_VERIFICADOR'
    },{
        header:'RUC VIEJO',
        width: anchoDefaultColumnas,
        dataIndex: 'RUC_ANTERIOR'
    },{
        header:'RAZON SOCIAL',
        width: 300,
        dataIndex: 'NOMBRE_CONTRIBUYENTE'
    },{
        header:'MODALIDAD',
        width: anchoDefaultColumnas,
        dataIndex: 'MODALIDAD_CONTRIBUYENTE'
    },{
        header:'TIPO',
        width: anchoDefaultColumnas,
        dataIndex: 'TIPO_CONTRIBUYENTE'
    },{
        header:'MES DE CIERRE',
        width: anchoDefaultColumnas,
        dataIndex: 'MES_CIERRE'
    },{
        header:'ESTADO',
        width: anchoDefaultColumnas,
        dataIndex: 'ESTADO'
    }]);
    var gridCONTRIBUYENTE = new Ext.grid.GridPanel({
        id:'gridCONTRIBUYENTE',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
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
                idCONTRIBUYENTESeleccionadoID_CONTRIBUYENTE = esteObjeto.getStore().getAt(rowIndex).id;
                pantallaModificarCONTRIBUYENTE().show();
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                
                if ( (Ext.getCmp("modificarUSUARIO") == undefined)) {
                    idCONTRIBUYENTESeleccionadoID_CONTRIBUYENTE = esteObjeto.getStore().getAt(rowIndex).id;
                    pantallaModificarCONTRIBUYENTE().show();
                }
            }
        }
    });
    return gridCONTRIBUYENTE;
}

function MODULO11(p_numero) {
    p_basemax = 11;
    v_numero_al = "";
      
    for (i = 0; i < p_numero.length; i++) {
        v_caracter = p_numero.charAt(i);
        if (!(48 <= v_caracter && v_caracter <= 57)) {
            v_numero_al += v_caracter;
        } else {
            v_numero_al += (v_caracter + "");
        }
    }

    k = 2;
    v_total = 0;

    for (i = v_numero_al.length - 1; i >= 0; i--) {
        if (k > p_basemax) {
            k = 2;
        }
        v_numero_aux = v_numero_al.substring(i, i + 1);
        v_total = v_total + (v_numero_aux * k);
        k = k + 1;
    }

    v_resto = v_total % 11;
    if (v_resto > 1) {
        v_digit = 11 - v_resto;
    } else {
        v_digit = 0;
    }
    return v_digit;
}

function pantallaModificarCONTRIBUYENTE() {
    var comboTipoContribuyente=  new Ext.form.ComboBox({
        fieldLabel: 'TIPO',
        hiddenName : 'TIPO_CONTRIBUYENTE',
        valueField : 'TIPO',
        id  :'idTipoContrib',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRucNuevo').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['1', 'Físico'],
            ['2', 'Jurídico']]

        })
    });
    var comboMesDeCierre=  new Ext.form.ComboBox({
        fieldLabel: 'MES DE CIERRE',
        hiddenName : 'MES_CIERRE',
        valueField : 'MES_CIERRE',
        id  :'idMesCierre',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRazonSocial').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['MES_CIERRE', 'descripcion'],
            data : [['4', 'Abril'],
            ['6', 'Junio'],
            ['12', 'Diciembre']]

        })
    });

    var estado =  new Ext.form.ComboBox({
        fieldLabel: 'ESTADO',
        hiddenName : 'ESTADO',
        valueField : 'ESTADO',
        id  :'idEstadoContrib',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        value :'ACTIVO',
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('idRazonSocial').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['ESTADO', 'descripcion'],
            data : [['ACTIVO', 'ACTIVO'],
            ['SUSPENSION TEMPORAL ', 'SUSPENSION TEMPORAL'],
            ['BLOQUEADO', 'BLOQUEADO']]
        })
    });

    var comboModalidadContribuyente =  new Ext.form.ComboBox({
        fieldLabel: 'MODALIDAD',
        hiddenName : 'MODALIDAD_CONTRIBUYENTE',
        valueField : 'MODALIDAD',
        id  :'idModalidad',
        anchor:'93%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        listeners: {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13  ){
                    Ext.getCmp('btnmodificarUSUARIO').focus(true,true);
                }
            }
        },
        store: new Ext.data.SimpleStore({
            fields: ['MODALIDAD', 'descripcion'],
            data : [['1', 'Grande'],
            ['2', 'Pequeño'],
            ['3', 'Mediano']]

        })
    });

    var contribuyenteModificarFormPanel = new Ext.FormPanel({
        id : 'idFormPanelModificarCONTRIBUYENTE',
        labelWidth : 120,
        labelAlign: 'left',
        width : 'auto',
        monitorValid : true,
        frame:true,
        items: [{
            fieldLabel:'ID CONTRIBUYENTE',
            name:'ID_CONTRIBUYENTE',
            xtype:'textfield',
            inputType:'hidden'
        },{
            layout:'form',
            items:[comboTipoContribuyente]
        },{
            fieldLabel:'RUC NUEVO',
            name:'RUC_NUEVO',
            id  :'idRucNuevo',
            xtype:'numberfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9){
                        Ext.getCmp('idDV').focus(true,true);
                        Ext.getCmp('idDV').setValue(MODULO11(Ext.getCmp('idRucNuevo').getRawValue()));
                    }
                }
            }
        },{
            fieldLabel:'DV',
            name:'DIGITO_VERIFICADOR',
            id  :'idDV',
            xtype:'numberfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idRucViejo').focus(true,true);
                    }
                }
            }
        },{
            fieldLabel:'RUC ANTERIOR',
            id  :'idRucViejo',
            name:'RUC_ANTERIOR',
            xtype:'textfield',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idModalidad').focus(true,true);
                    }
                }
            }
        },{
            layout:'form',
            items:[comboModalidadContribuyente]
        },{
            layout:'form',
            items:[comboMesDeCierre]
        },{
            fieldLabel:'RAZON SOCIAL',
            id  :'idRazonSocial',
            name:'NOMBRE_CONTRIBUYENTE',
            xtype:'textfield',
            allowBlank : false,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 ){
                        estado.focus(true,true);
                    }
                }
            }
        },estado],

        buttons : [{
            id : 'btnmodificarUSUARIO',
            text : 'Modificar',
            formBind : true,
            handler : function() {
                if(idCONTRIBUYENTESeleccionadoID_CONTRIBUYENTE!=undefined){
                    Ext.Msg.show({
                        title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                        buttons : Ext.Msg.YESNO,
                        icon : Ext.MessageBox.WARNING,
                        fn : function(btn, text) {
                            if (btn == "yes") {
                                contribuyenteModificarFormPanel.getForm().submit({
                                    method : 'POST',
                                    waitTitle : WAIT_TITLE_MODIFICANDO,
                                    waitMsg : WAIT_MSG_MODIFICANDO,
                                    url : 'CONTRIBUYENTE?op=MODIFICAR',
                                    timeout : TIME_OUT_AJAX,
                                    success : function(form, accion) {
                                        Ext.Msg.show({
                                            title : TITULO_ACTUALIZACION_EXITOSA,
                                            msg : CUERPO_ACTUALIZACION_EXITOSA,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.INFO
                                        });
                                        Ext.getCmp('modificarCONTRIBUYENTE').close();
                                        Ext.getCmp('gridCONTRIBUYENTE').store.reload();
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
                Ext.getCmp('modificarCONTRIBUYENTE').close();
            }
        }]
    });
    contribuyenteModificarFormPanel.getForm().load({
        url : 'CONTRIBUYENTE?op=LISTAR',
        method : 'POST',
        params:{
            ID_CONTRIBUYENTE: idCONTRIBUYENTESeleccionadoID_CONTRIBUYENTE
        },
        waitMsg : 'Cargando...'
    });
    var win = new Ext.Window({
        title:'Modificar Contribuyente',
        id : 'modificarCONTRIBUYENTE',
        autoWidth : true,
        modal:true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [contribuyenteModificarFormPanel]
    });
    return win;
}