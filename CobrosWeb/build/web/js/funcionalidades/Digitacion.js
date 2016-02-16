function panelRegistroCargaFormulario() {
    setearVaribles();
    var procesando = false;
    Ext.DomHelper.useDom = true;
    var panel = {
        id : 'panelFormularios',
        title:'Registro-Carga Formularios',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : true,
        tbar : [{
            iconCls : 'gestor',
            text : 'CI. Gestor:'
        },gestor,{
            xtype:'button',
            iconCls:'add2',
            handler: function(){
                pantallaAgregarGESTOR().show().center();
            }
        },{
            iconCls : 'formulario',
            text : 'Formulario:'
        },tipoFormulario],
        items : [
        {
            id:'panelCabeceraFormulario',
            autoWidth:true,
            autoHeight:true
        },
        {
            id:'panelCabeceraFormularioLlenarCampos',
            autoWidth:true,
            autoHeight:true,
            xtype:'panel',
            hidden:true,
            tbar:[{
                text : 'Campo:'

            },campoFormulario,{
                text : 'Descripción:'

            },campoDescripcion,{
                text : 'Valor:'

            },campoValor]
        },
        {
            xtype:'panel',
            frame: false,
            id:'panelDetalleFormulario',
            autoScroll : true
        }
        ],
        buttons : [{
            id:'idValidarFormulario',
            text : 'Validar Formulario',
            disabled:true,
            handler : function validar(){
                fnBotonValidar();
            //                miFuncion('todos');
            //                Ext.getCmp('idGuardarFormulario').enable();
            //                cerrarVentanaReporteError();
            }
        }
        ,
        {
            id: 'idGuardarFormulario',
            text : 'Guardar Formulario',
            timeout :TIME_OUT_AJAX,
            disabled:true,
            handler : function guardarFormulario(var1 , var2){
                if(!procesando){
                    procesando = true;
                    guardar();
                    limpiarPaneles("GUARDAR");
                    Ext.getCmp('idGuardarFormulario').disable();
                    procesando = false;
                }
            }
        } ,
{
            text : 'Cancelar',
            handler : function(){

                limpiarPaneles("CANCELAR");
                Ext.getCmp('idDigitacionGestor').focus(true,true);

            }
        },{
            text : 'Salir',
            handler : function(){
                limpiarPaneles("CANCELAR");
                cardInicial();
            }
        }]
    }

    return panel;

}
function cerrarVentanaReporteError(){  

    if(Ext.getCmp('idVentanaReporteErrores') != undefined){
        Ext.getCmp('idVentanaReporteErrores').close();
    }
    if(Ext.getCmp('idVentanaReporteErrores2') != undefined){
        Ext.getCmp('idMsgBoxErroresDigitacion2').close();
    }

}
function fnBotonValidar(){

    miFuncion('todos');
    Ext.getCmp('idGuardarFormulario').enable();
    cerrarVentanaReporteError();


}
function limpiarPaneles(tipo){
    cerrarVentanaReporteError();
    if(Ext.getCmp('idValidarFormulario') != null){
        Ext.getCmp('idValidarFormulario').disable();
        Ext.getCmp('idGuardarFormulario').disable();

    }

    if(Ext.getCmp('panelCabeceraFormularioLlenarCampos')!= null){

        Ext.getCmp('panelCabeceraFormulario').enable();
        Ext.getCmp('panelCabeceraFormularioLlenarCampos').setVisible(false);
    }

    if(Ext.getCmp('idCampoFormulario') != null ){
        Ext.getCmp('idCampoFormulario').setValue('');
    }
    if(Ext.getCmp('idCampoValor') != null ){
        Ext.getCmp('idCampoValor').setValue('');
    }
    if(Ext.getCmp('idCampoDescripcion') != null ){
        Ext.getCmp('idCampoDescripcion').setValue('');
    }

    if(tipo == "CANCELAR"){

        if(Ext.getCmp('panelCabeceraFormularioLlenarCampos')!=null){
            Ext.getCmp('idTipoFormulario').enable();
            Ext.getCmp('idDigitacionGestor').enable();
        }
        if(Ext.getCmp('idTipoFormulario') != null ){
            Ext.getCmp('idTipoFormulario').setValue('');
        }
        if(Ext.getCmp('idDigitacionGestor') != null ){
            Ext.getCmp('idDigitacionGestor').setValue('');
        }

    }else if (tipo == "GUARDAR" ){
        if(Ext.getCmp('idTipoFormulario') != null ){
            Ext.getCmp('idTipoFormulario').enable();
            Ext.getCmp('idDigitacionGestor').enable();
            Ext.getCmp('idDigitacionGestor').reset();
            Ext.getCmp('idTipoFormulario').setValue('');
        }

    }
    getMainPanel();
    Ext.getCmp('panelDetalleFormulario').load({
        url: "PaginaDigitacion.jsp",
        params: {
            idFormulario:-1
        },
        discardUrl: false,
        nocache: false,
        text: "Loading...",
        timeout: TIME_OUT_AJAX,
        scripts: false
    });


}
function setearVaribles(){ 
    getRemoteComponentPlugin = function(){
        var resultado = new Ext.ux.Plugin.RemoteComponent({
            url : 'cabeceraFormulario.jsp?op=CABECERA',
            params:{
                idFormulario: Ext.getCmp('idTipoFormulario').getRawValue()
            }
        });
        return resultado;
    };
    getMainPanel = function(){

        if(Ext.getCmp('idPanelCabecera')!=undefined){
            Ext.getCmp('idPanelCabecera').destroy();

        }

        mainPanel = new Ext.Panel({
            id:'idPanelCabecera',

            enableTabScroll:true,
            autoWidth:true,
            autoHeight:true,
            renderTo: Ext.get('panelCabeceraFormulario'),
            items: [{
                plugins:[ getRemoteComponentPlugin() ]
            }]
        });

        return mainPanel;
    };
    var btnTipoFormulario = false;
    tipoFormulario = new Ext.form.NumberField({
        id: 'idTipoFormulario',       
        name : 'tipoFormnulario',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                var gestorCI = Ext.getCmp('idDigitacionGestor').getRawValue();
                if(gestorCI == ""){
                    Ext.Msg.show({
                        title : "Atención",
                        msg : "No ingreso CI de Gestor. Campo obligatorio",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn : function(btn, text) {
                            Ext.getCmp('idDigitacionGestor').focus(true, true);
                        }
                    });
                }else{
                    if(gestorCI.length >= 6){
                        if(esteEvento.getKey() == 13  && !btnTipoFormulario){
                            var conn = new Ext.data.Connection();
                            btnTipoFormulario = true;
                            conn.request({
                                waitTitle : 'Conectando',
                                waitMsg : 'Cargando Campos',
                                url : 'formularios?op=BUSCAR_FORMULARIO',
                                params:{
                                    numeroFormulario:Ext.getCmp('idTipoFormulario').getRawValue()
                                },
                                method : 'POST',
                                success : function(respuestaServer) {
                                    var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                    if (!obj.success) {
                                        Ext.Msg.show({
                                            title : 'Atención',
                                            msg : obj.motivo,
                                            buttons : Ext.Msg.OK,
                                            icon : Ext.MessageBox.ERROR,
                                            fn : function (){
                                                //Ext.getCmp('idTipoFormulario').focus(true,true);
                                                btnTipoFormulario = false;
                                            }
                                        });
                                        limpiarPaneles("CANCELAR");
                                        Ext.getCmp('idDigitacionGestor').focus(true,true);                                    
                                        Ext.getCmp('panelCabeceraFormularioLlenarCampos').setVisible(false);

                                    }else{
                                        Ext.getCmp('idTipoFormulario').disable();
                                        Ext.getCmp('idDigitacionGestor').disable();
                                        btnTipoFormulario = false;
                                    }

                                }
                            });

                            getMainPanel();
                            Ext.getCmp('panelDetalleFormulario').load({
                                url: "PaginaDigitacion.jsp",
                                params: {
                                    idFormulario:-1
                                },
                                discardUrl: false,
                                nocache: false,
                                text: "Cargando...",
                                timeout: TIME_OUT_AJAX,
                                scripts: false
                            });
                        }
                    }else{
                        Ext.Msg.show({
                        title : "Atención",
                        msg : "Nro. CI Gestor Invalido",
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.ERROR,
                        fn : function(btn, text) {
                            Ext.getCmp('idDigitacionGestor').reset();
                            Ext.getCmp('idDigitacionGestor').focus(true, true);
                        }
                    });
                    }
                }
            }
        }
    });
    gestor = new Ext.form.NumberField({
        id: 'idDigitacionGestor',
        name : 'ID_GESTOR',
        emptyText:'Cedula de Identidad..',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento){
                if(esteEvento.getKey() == 13 ||esteEvento.getKey() == 9 ){
                    if(esteObjeto.getValue()==""){
                        Ext.Msg.show({
                            title : "Atención",
                            msg : "No ingreso CI de Gestor. Campo obligatorio",
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR,
                            fn : function(btn, text) {
                                esteObjeto.focus(true,true);
                            }
                        });
                    }else{
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
                                    Ext.Msg.show({
                                        title: 'Gestor',
                                        msg: '<p style="font-size:20px;" align="center">'+obj.data.NOMBRE+" "+obj.data.APELLIDO+'</p>',
                                        autoSize: true,
                                        animEl: 'elId',
                                        buttons: Ext.MessageBox.OKCANCEL,
                                        fn: function(btn){
                                            if(btn == 'ok'){
                                                Ext.getCmp('idTipoFormulario').focus(true, true);
                                            }else{
                                                Ext.getCmp('idDigitacionGestor').focus(true, true);
                                            }
                                        }

                                    });
                                }else{
                                    Ext.Msg.show({
                                        title : "Error",
                                        msg : "No existe ci de gestor. Desea Agregar?",
                                        buttons : Ext.Msg.YESNO,
                                        icon : Ext.MessageBox.ERROR,
                                        fn : function(btn, text) {
                                            if(btn=='yes'){
                                                Ext.getCmp('idDigitacionGestor').reset();
                                                pantallaAgregarGESTOR().show().center();
                                            }else if(btn=='no'){
                                                //Ext.getCmp('idDigitacionGestor').reset();
                                                Ext.getCmp('idTipoFormulario').focus(true, true);
                                                close();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        }
    });
    campoFormulario = new Ext.form.NumberField({
        id:'idCampoFormulario',
        title : 'Campo',
        name : 'campoFormulario',
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento){               
                if(esteEvento.getKey() == 13){
                    if(Ext.getCmp('idCampoFormulario').getRawValue() =="00"){                       
                        fnBotonValidar();
                    }
                    else if(document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue())==null){
                        Ext.Msg.show({
                            title : 'Estado',
                            msg : 'El valor del campo es incorrecto',
                            buttons : Ext.Msg.OK,
                            animEl : 'elId',
                            icon : Ext.MessageBox.ERROR,
                            fn:function(){
                                Ext.getCmp('idCampoFormulario').focus(true,true);

                            }
                        });
                        Ext.getCmp('idCampoFormulario').focus(true);
                    }else
                    {
                        Ext.getCmp('idCampoDescripcion').setValue(document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue()).name)
                        Ext.getCmp('idCampoValor').focus(true);
                    }

                }
            }
        }
    });
    campoValor = new Ext.form.TextField({
        id:'idCampoValor',
        name : 'campoFormularioValor',
        enableKeyEvents:true,
        listeners : {
            'keyup' : function(esteObjeto, esteEvento)   {
                esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
            },
            'specialkey' : function(esteObjeto, esteEvento)   {

                if(esteEvento.getKey() == 13){

                    if(document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue())!=null){
                        document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue()).value=Ext.getCmp('idCampoValor').getRawValue();
                        Ext.getCmp('idCampoFormulario').setRawValue("");
                        Ext.getCmp('idCampoValor').setRawValue("");
                        Ext.getCmp('idCampoDescripcion').setRawValue("");
                        Ext.getCmp('idCampoFormulario').focus(true);
                    }
                }
            }
        }
    });
    campoDescripcion = new Ext.form.TextField({
        id:'idCampoDescripcion',
        width:250,
        name : 'campoFormularioDescripcion',
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13){

                    if(document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue())!=null){
                        document.getElementById('C'+Ext.getCmp('idCampoFormulario').getRawValue()).value=Ext.getCmp('idCampoValor').getRawValue();
                        Ext.getCmp('idCampoFormulario').setRawValue("");
                        Ext.getCmp('idCampoValor').setRawValue("");
                        Ext.getCmp('idCampoFormulario').focus(true);
                    }
                }
            }
        }
    });


}