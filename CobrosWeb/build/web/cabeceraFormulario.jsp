<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.util.Date.*,javax.ejb.EJB,javax.naming.*,java.text.*,javax.naming.*,py.com.konecta.redcobros.ejb.*,py.com.konecta.redcobros.entities.* , org.json.simple.JSONArray,org.json.simple.JSONObject; " %>



<%
        JSONObject json = new JSONObject();
        JSONObject jsonFinal = new JSONObject();
        JSONObject configuracion = new JSONObject();
        JSONArray arrayJson = new JSONArray();
       // SchemaQuery schemaQ = (SchemaQuery) request.getSession().getAttribute("schemaQ");
        String op = request.getParameter("op");
            Context context = new InitialContext();

           


         // System.out.println("ejb/"+FormularioFacade.class.getSimpleName());
            FormularioFacade formularioFacade = (FormularioFacade)context.lookup(FormularioFacade.class.getName());
            
            Formulario formularioExample = new Formulario();          
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

%>


<%
        if (op.equalsIgnoreCase("CABECERA")) {
            String jsonFinalString = ""; 
            if ((request.getParameter("idFormulario") != null) && (request.getParameter("idFormulario").matches("[0-9]+"))) {
                Integer idFormulario = new Integer(request.getParameter("idFormulario"));
                formularioExample.setNumeroFormulario(idFormulario);
                //formularioExample.setVersion(new Integer("1"));
               
                List<Formulario> lcabecera = formularioFacade.list(formularioExample);
                // List<Formulario> lcabecera = formularioFacade.(formularioExample);
                Date fecha = new Date();
                SimpleDateFormat spdf = new SimpleDateFormat("yyyy");
                int anho = Integer.parseInt(spdf.format(fecha));                 
                int mes = Integer.parseInt(new SimpleDateFormat("MM").format(fecha));
                boolean showRec = anho > 2013 || mes > 3  ? false : true;
                
                String arrayJS = "";
                if(showRec){
                    if(mes == 1){
                        arrayJS = "var termInvalid = [0, 1, 2];";
                    }else if(mes == 2){
                        arrayJS = "var termInvalid = [0, 1, 2, 3, 4, 5];";
                    }else if(mes == 3){
                        arrayJS = "var termInvalid = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];";
                    }
                }
               // List<Formulario> lcabecera = schemaQ.getLimitLogFormulario(10, 0, " where e.numeroFormulario = " + request.getParameter("idFormulario") + " and e.versionDefault = 1", "e.numeroFormulario", "asc", "e");

                if (lcabecera.size() > 0) {
                    //if (lcabecera.get(0).getIdCabeceraFormulario().getIdCabeceraFormulario().intValue() == 1 || lcabecera.get(0).getIdCabeceraFormulario().getIdCabeceraFormulario().intValue() == 2) {
                    if (idFormulario == 90){


%>

 {
        xtype: 'form',
        id: 'formulario',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('CC4').focus(true,true);
            }
        },
        items: [{
            layout: 'column',
            items: [{
                columnWidth: 0.2,
                layout: 'form',
                bodyStyle: 'padding-right:5px;',

                items: [{

                    xtype: 'numberfield',
                    value: '2',
                    width:25,
                    fieldLabel: 'Versión',
                    id: 'CC1',
                    name: 'CC1'
                },   {
                    listeners: {
                        'check': function (esteObjeto, checked) {
                            <%if(showRec){%>
                            if (checked) {
                                Ext.getCmp('CC5').disable();

                               Ext.getCmp('idCC3').setValue(0);
                            }
                            <%}%>
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){

                                //Ext.getCmp('CC9').focus(true,true);
                            }
                        }
                    }
                    
                },
                {
                    listeners: {
                        'check': function (esteObjeto, checked) {
                            if (checked) {
                                Ext.getCmp('CC5').enable();

                                Ext.getCmp('idCC2').setValue(0);
                            }else{
                                Ext.getCmp('CC5').disable();

                            }
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){

                                Ext.getCmp('CC5').focus(true,true);
                            }
                        }
                    }
                } ,
                {
                    listeners: {
                        'check': function (esteObjeto, checked) {
                            if (checked) {
                                if(!(Ext.getCmp('idCC3').getValue())){
                                    Ext.getCmp('CC5').disable();

                                }else{
                                    Ext.getCmp('CC5').focus(true,true);

                                }
                            }
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){
                                if(Ext.getCmp('idCC3').getValue()){
                                    Ext.getCmp('CC5').focus(true,true);
                                }else{
                                    //Ext.getCmp('CC9').focus(true,true);
                                }

                            }
                        }

                    }
                }
                ]
            },
            {
                columnWidth: 0.12,
                layout: 'form',
                items: [{
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {
                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                Ext.getCmp('CC6').focus(true,true);
                            }

                        }
                    },
                    xtype: 'textfield',
                    fieldLabel: 'RUC',
                    anchor:'90%',
                    id: 'CC4',
                    width:100,
                    name: 'CC4'
                },{
                    xtype:'textfield',
                    hidden:true
                },{
                    xtype:'textfield',
                    hidden:true
                },
                {
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {

                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                //Ext.getCmp('CC9').focus(true,true);
                            }
                        }
                    }
                }]
            },
            {
                columnWidth: 0.16,
                layout: 'form',
                border:true,
                items: [{
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {
                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                if (true) {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        waitTitle: 'Conectando',
                                        waitMsg: 'Cargando Campos',
                                        url: 'formularios?op=RUC-LLENAR_CAMPOS',
                                        params: {
                                            CC4: Ext.getCmp('CC4').getRawValue(),
                                            CC6: Ext.getCmp(esteObjeto.id).getRawValue(),
                                            idFormulario: Ext.getCmp('idTipoFormulario').getRawValue(),
                                        },
                                        method: 'POST',
                                        success: function (respuestaServer) {
                                            var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                            if (obj.success) {
                                                for (var ii = 0; ii < obj.total; ii++) {

                                                    Ext.Msg.show({
                                                            title: 'Nombre/Razón Social:',
                                                            msg: '<p style="font-size:20px;" align="center">'+obj.items[ii].valor+'</p>',
                                                            autoSize: true,
                                                            animEl: 'elId',
                                                            buttons: Ext.MessageBox.OKCANCEL,
                                                            fn: function(btn){
                                                                if(btn == 'ok'){

                                                                    Ext.getCmp('idRadio').setValue(10);
                                                                    Ext.getCmp('idRadio').focus(true,true);

                                                                }else{

                                                                    Ext.getCmp('CC4').focus(true,true);
                                                                }
                                                            }

                                                    });
                                                    Ext.getCmp(obj.items[ii].campo).setValue(obj.items[ii].valor);
                                                }
                                                if (obj.total == 0) {
                                                    //Ext.getCmp('radio').setValue('');
                                                }
                                                
                                                    //Ext.getCmp('idBotonValidarCabecera').focus(true,true);
                                                    


                                                    
                                               
                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    animEl: 'elId',
                                                    icon: Ext.MessageBox.ERROR,
                                                    buttons: Ext.Msg.OK,
                                                    fn: function(){
                                                        Ext.getCmp('CC4').focus(true,true);
                                                    }
                                                });

                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: 'Estado',
                                                msg: 'No se pudo realizar la operación',
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR,
                                                buttons: Ext.Msg.OK
                                            });
                                        }
                                    });;
                                }
                            }
                        }
                    },
                    xtype: 'numberfield',
                    fieldLabel: 'DV',
                    id: 'CC6',
                    name: 'CC6',
                    width:25
                },{
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {

                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                Ext.getCmp('radio').focus(true,true);
                            }
                        }
                    }
                },{
                    xtype:'textfield',
                    hidden:true
                },{
                    xtype:'textfield',
                    hidden:true
                }
                ]
            },
            {
                columnWidth: 0.2,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'Nombre/Razón Social',
                    anchor:'95%',
                    id: 'CC8',
                    name: 'CC8',
                    disabled:true

                },{
                    disabled: true,
                    xtype: 'datefield',
                    format: 'd/m/Y',
                    value: '<%out.print(formatter.format(new Date()));%>',
                    fieldLabel: 'Fecha Presentación',
                    id: 'CC7',
                    name: 'CC7'
                }]
            }]
        }, {
            xtype: 'fieldset',
            title: 'MOTIVO DE LA LIQUIDACION',
            autoHeight: true,
            items:
                [
                    {
                        xtype: 'radiogroup',
                        id: 'radio',
                        columns: [650, 200],
                        vertical: true,
                        items: [
                            {boxLabel: '10. Ingresos ocasionales obtenidos por contribuyentes del IMAGRO,gravados por el IRACIS.', id: 'idRadio', name: 'radioFormulario90', inputValue: '10'},
                            {boxLabel: '11. Ingresos ocasionales obtenidos por Personas Físicas sin representantes en el pais, gravadas por el IRACIS.', name: 'radioFormulario90', inputValue: '11'},
                            {boxLabel: '12. Distribución de Utilidades o Pagos de Dividendos.', name: 'radioFormulario90', inputValue: '12'},
                            {boxLabel: '13. Patente de Autovehículos.', name: 'radioFormulario90', inputValue: '13'},
                            {boxLabel: '14. Otros pagos a Solicitar.', name: 'radioFormulario90', inputValue: '14'}                            
                        ],
                        listeners: {
                            'specialkey': function (esteObjeto, esteEvento) {

                                if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                    Ext.getCmp('idBotonValidarCabecera').focus(true,true);
                                }
                            }
                        }
                    }                
                ]

        }],
        bbar: [{
            handler: function (var1, var2) {
               // Ext.getCmp('idBotonValidarCabecera').disable();
                Ext.getCmp('formulario').getForm().submit({
                    params: {
                        CC7: Ext.getCmp('CC7').getRawValue(),
                        idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
                    },
                    method: 'POST',
                    waitTitle: 'Conectando',
                    waitMsg: 'Validando la cabecera...',
                    url: 'formularios?op=VALIDAR_CABECERA',
                    success: function (form, accion) {
                                Ext.getCmp('panelDetalleFormulario').load(
                                {
                                    url: 'PaginaDigitacion.jsp',
                                    params: {
                                        idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
                                    },
                                    discardUrl: false,
                                    nocache: false,
                                    text: 'Loading...',
                                    timeout: 30,
                                    scripts: false
                                });
                                Ext.getCmp('panelCabeceraFormularioLlenarCampos').setVisible(true);
                                Ext.getCmp('panelCabeceraFormulario').disable();
                                Ext.getCmp('idTipoFormulario').disable();
                                Ext.getCmp('idCampoFormulario').focus(true);
                                Ext.getCmp('idValidarFormulario').enable();
                    },
                    failure: function (form, action) {

                        var obj = Ext.util.JSON.decode(action.response.responseText);
                        var errores = '';
                        for (var ii = 0; ii < obj.total; ii++) {
                            errores += obj.items[ii].Error;
                        }
                        Ext.Msg.show({
                            title: 'Atención',
                            msg: obj.motivo + ' ' + errores,
                            buttons: Ext.Msg.OK,
                            icon: Ext.MessageBox.ERROR,
                            fn:function(){
                                //Ext.getCmp('CC9').focus(true,true);
                            }
                        });

                    }
                });
            },
            xtype: 'button',
            text: 'Validar Cabecera',
            formBind: true,
            iconCls : 'validar',
            iconAlign: 'left',
            id:'idBotonValidarCabecera'
        }]
    }

<%
            } else {

%>

 {
        xtype: 'form',
        id: 'formulario',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        listeners: {
            'render': function (esteObjeto) {
                Ext.getCmp('CC4').focus(true,true);
            }
        },
        items: [{
            layout: 'column',
            items: [{
                columnWidth: 0.2,
                layout: 'form',
                bodyStyle: 'padding-right:5px;',

                items: [{

                    xtype: 'numberfield',
<%            if (lcabecera.size() == 1){ %>
                    value: '1',
<%            }else{
                    if (lcabecera.size() > 1){%>
                        disabled:true,
<%                  }
              }%>
                    width:25,
                    fieldLabel: 'Versión',
                    id: 'CC1',
                    name: 'CC1'
                },   {
                    listeners: {
                    <%if(showRec){%>
                        'check': function (esteObjeto, checked) {
                            if (checked) {
                                Ext.getCmp('CC5').disable();

                                Ext.getCmp('idCC3').setValue(0);
                            }
                        },
                        <%}%>
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){

                                Ext.getCmp('CC9').focus(true,true);
                            }
                        }
                    },
                    id:'idCC2',
                    xtype: 'checkbox',
                    checked: true,
                    boxLabel: 'Declaración Jurada Original',
                    name: 'CC2',
                    inputValue: 'CC2',
                    disabled: false
                },
                {
                    listeners: {
                        'check': function (esteObjeto, checked) {
                            <%if(showRec){%>
                            if (checked) {
                                Ext.getCmp('CC5').enable();

                                Ext.getCmp('idCC2').setValue(0);
                            }else{
                                Ext.getCmp('CC5').disable();

                            }
                            <%}%>
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){

                                //Ext.getCmp('CC5').focus(true,true);
                            }
                        }
                    }<%if(showRec){%>,
                    xtype: 'checkbox',
                    boxLabel: 'Declaración Jurada Rectificada',
                    name: 'CC3',
                    inputValue: 'CC3',
                    id:'idCC3',
                    disabled: false
                } ,
                {
                    listeners: {
                        'check': function (esteObjeto, checked) {
                            if (checked) {
                                if(!(Ext.getCmp('idCC3').getValue())){
                                    Ext.getCmp('CC5').disable();

                                }else{
                                    Ext.getCmp('CC5').focus(true,true);

                                }
                            }
                        },
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13  ){
                                if(Ext.getCmp('idCC3').getValue()){
                                    Ext.getCmp('CC5').focus(true,true);
                                }else{
                                    Ext.getCmp('CC9').focus(true,true);
                                }

                            }
                        }

                    }
                    <%}%>
                    ,
                    xtype: 'checkbox',
                    boxLabel: 'Clausura o Cese',
                    name: 'CC10',
                    inputValue: 'CC10',
                    disabled: false
                }
                ]
            },
            {
                columnWidth: 0.12,
                layout: 'form',
                items: [{
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {
                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                Ext.getCmp('CC6').focus(true,true);
                            }
                        },
                        'keyup' : function(esteObjeto, esteEvento)   {
                            <%if(!arrayJS.isEmpty()){
                                out.println(arrayJS);
                            %>                                
                                var fecha = new Date();
                                var mes = fecha.getMonth();
                                var ruc = this.getValue();
                                var terminacion  = ruc.substr(ruc.length-1,1);
                                for(i = 0; i < termInvalid.length; i++ ){
                                    if(termInvalid[i] == terminacion){
                                        Ext.getCmp('idCC3').disable();
                                        Ext.getCmp('CC5').disable();
                                        break;
                                    }else{
                                        Ext.getCmp('idCC3').enable();
                                        Ext.getCmp('CC5').enable();
                                    }
                                }
                            
                            <%}%>    
                            
                        }
                    },
                    xtype: 'textfield',
                    fieldLabel: 'RUC',
                    anchor:'90%',
                    id: 'CC4',
                    width:100,
                    name: 'CC4',
                    enableKeyEvents:true
                },{
                    xtype:'textfield',
                    hidden:true
                },{
                    xtype:'textfield',
                    hidden:true
                },
                {
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {

                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                Ext.getCmp('CC9').focus(true,true);
                            }
                        }
                    }<%if(showRec){%>,
                    disabled: true,
                    xtype: 'numberfield',
                    width:120,
                    fieldLabel: 'Dec. que rectifica',
                    id: 'CC5',
                    anchor:'95%',
                    name: 'CC5'
                    <%}%>
                }]
            },
            {
                columnWidth: 0.16,
                layout: 'form',
                border:true,
                items: [{
                    listeners: {
                        'specialkey': function (esteObjeto, esteEvento) {
                            if (esteEvento.getKey() == 13 || esteEvento.getKey() == 9) {
                                if (true) {
                                    var conn = new Ext.data.Connection();
                                    conn.request({
                                        waitTitle: 'Conectando',
                                        waitMsg: 'Cargando Campos',
                                        url: 'formularios?op=RUC-LLENAR_CAMPOS',
                                        params: {
                                            
                                            CC4: Ext.getCmp('CC4').getRawValue(),
                                            CC6: Ext.getCmp(esteObjeto.id).getRawValue(),
                                            idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()

                                        },
                                        method: 'POST',
                                        success: function (respuestaServer) {
                                            var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                            if (obj.success) {
                                                for (var ii = 0; ii < obj.total; ii++) {

                                                    Ext.Msg.show({
                                                            title: 'Nombre/Razón Social:',
                                                            msg: '<p style="font-size:20px;" align="center">'+obj.items[ii].valor+'</p>',
                                                            autoSize: true,
                                                            animEl: 'elId',
                                                            buttons: Ext.MessageBox.OKCANCEL,
                                                            fn: function(btn){
                                                                if(btn == 'ok'){
                                                                
                                                                    Ext.getCmp('idCC2').focus(true,true);

                                                                }else{
                                                                    
                                                                    Ext.getCmp('CC4').focus(true,true);
                                                                }
                                                            }
                                                            
                                                    });
                                                    Ext.getCmp(obj.items[ii].campo).setValue(obj.items[ii].valor);
                                                }
                                                if (obj.total == 0) {
                                                    Ext.getCmp('CC8').setValue('');
                                                }
                                                

                                                //<% if (idFormulario == 90) { %>
                                                //    Ext.getCmp('idBotonValidarCabecera').focus(true,true);
                                                //<% } else { %>
                                                //    Ext.getCmp('idCC2').focus(true,true);
                                                //<% } %>

                                            } else {
                                                Ext.Msg.show({
                                                    title: 'Estado',
                                                    msg: obj.motivo,
                                                    animEl: 'elId',
                                                    icon: Ext.MessageBox.ERROR,
                                                    buttons: Ext.Msg.OK,
                                                    fn: function(){
                                                        Ext.getCmp('CC4').focus(true,true);
                                                    }
                                                });

                                            }
                                        },
                                        failure: function (action) {
                                            Ext.Msg.show({
                                                title: 'Estado',
                                                msg: 'No se pudo realizar la operación',
                                                animEl: 'elId',
                                                icon: Ext.MessageBox.ERROR,
                                                buttons: Ext.Msg.OK
                                            });
                                        }
                                    });;
                                }
                            }
                        }
                    },
                    xtype: 'numberfield',
                    fieldLabel: 'DV',
                    id: 'CC6',
                    name: 'CC6',
                    width:25,

                },{
                    xtype:'textfield',
                    hidden:true
                },{
                    xtype:'textfield',
                    hidden:true
                }
                ]
            },
            {
                columnWidth: 0.2,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'Nombre/Razón Social',
                    anchor:'95%',
                    id: 'CC8',
                    name: 'CC8',
                    disabled:true

                },{
                    disabled: true,
                    xtype: 'datefield',
                    format: 'd/m/Y',
                    value: '<%out.print(formatter.format(new Date()));%>',
                    fieldLabel: 'Fecha Presentación',
                    id: 'CC7',
                    name: 'CC7'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'PERIODO FISCAL',
                    id: 'CC9',
                    anchor:'95%',
                    name: 'CC9',
                    disabled: false,
                    listeners : {
                        'specialkey' : function(esteObjeto, esteEvento)   {

                            if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){

                                Ext.getCmp('idBotonValidarCabecera').focus(true,true);
                            }
                        }
                    }
                }]
            }]
        }],
        bbar: [{
            handler: function (var1, var2) {
               // Ext.getCmp('idBotonValidarCabecera').disable();
                Ext.getCmp('formulario').getForm().submit({
                    params: {
                        CC7: Ext.getCmp('CC7').getRawValue(),
                        idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
                    },
                    method: 'POST',
                    waitTitle: 'Conectando',
                    waitMsg: 'Validando la cabecera...',
                    url: 'formularios?op=VALIDAR_CABECERA',
                    timeout : TIME_OUT_AJAX,
                    success: function (form, accion) {
                        var obj2 = Ext.util.JSON.decode(accion.response.responseText);
                        
<%            if (lcabecera.size() > 1){ %>
                        Ext.Msg.show({
                        title : 'Confirmacion',
                        msg : 'Desea confirmar version '+obj2.version+' del formulario '+obj2.formulario,
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {
                            if (btn == "ok") {
                                Ext.getCmp('panelDetalleFormulario').load(
                                {
                                    url: 'PaginaDigitacion.jsp',
                                    params: {
                                        idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
                                    },
                                    discardUrl: false,
                                    nocache: false,
                                    text: 'Loading...',
                                    timeout: 30,
                                    scripts: false
                                });
                                Ext.getCmp('panelCabeceraFormularioLlenarCampos').setVisible(true);
                                Ext.getCmp('panelCabeceraFormulario').disable();
                                Ext.getCmp('idTipoFormulario').disable();
                                Ext.getCmp('idCampoFormulario').focus(true);
                                Ext.getCmp('idValidarFormulario').enable();
                                
                          }else{
                                limpiarPaneles("CANCELAR");
                                Ext.getCmp('idDigitacionGestor').focus(true,true);
                          }
                         }
                     });
                    
<%              }else{
                    if (lcabecera.size() == 1){%>
                    Ext.getCmp('panelDetalleFormulario').load(
                                {
                                    url: 'PaginaDigitacion.jsp',
                                    params: {
                                        idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
                                    },
                                    discardUrl: false,
                                    nocache: false,
                                    text: 'Loading...',
                                    timeout: 30,
                                    scripts: false
                                });
                                Ext.getCmp('panelCabeceraFormularioLlenarCampos').setVisible(true);
                                Ext.getCmp('panelCabeceraFormulario').disable();
                                Ext.getCmp('idTipoFormulario').disable();
                                Ext.getCmp('idCampoFormulario').focus(true);
                                Ext.getCmp('idValidarFormulario').enable();
                    
                    <%                  }
              }%>
                    },
                    failure: function (form, action) {

                        var obj = Ext.util.JSON.decode(action.response.responseText);
                        var errores = '';
                        for (var ii = 0; ii < obj.total; ii++) {
                            errores += obj.items[ii].Error;
                        }
                        Ext.Msg.show({
                            title: 'Atención',
                            msg: obj.motivo + ' ' + errores,
                            buttons: Ext.Msg.OK,
                            icon: Ext.MessageBox.ERROR,
                            fn:function(){
                                Ext.getCmp('CC9').focus(true,true);
                            }
                        });

                    }
                });
            },
            xtype: 'button',
            text: 'Validar Cabecera',
            formBind: true,
            iconCls : 'validar',
            iconAlign: 'left',
            id:'idBotonValidarCabecera'
        }]
    }

<%
            }
//}
                } else {
                    jsonFinal.put("success", false);
                    jsonFinal.put("motivo", "No existe el formulario");
                    jsonFinalString = jsonFinal.toString();
                   out.print(jsonFinalString);
                }
            /******************************/
             
            } else {
                configuracion.put("layout", "form");
                configuracion.put("bodyStyle", "padding:5px 5px 0");
                out.print(configuracion);
            }

        } 

%>


