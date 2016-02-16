//Variables globales
var editor = new Ext.ux.grid.RowEditor({
    saveText: 'Actualizar'
});
var editor2 = new Ext.ux.grid.RowEditor({
    saveText: 'Actualizar'
});
var editorActivo = null;

function panelControlFormularios() {
    var panel = new Ext.TabPanel({
        id : 'panelControlFormularios',
        activeTab: 0,
        autoScroll:true,     
        frame:true,
        defaults:{
            autoScroll: true
        },
        buttons:[{
            text:'Salir',
            tooltip:'Salir',
            iconCls:'logout',
            handler:function(){
                limpiarPaneles("CANCELAR");
                cardInicial();
            }
        }],
        items:[{
            title: 'Formularios',          
            xtype:'panel',
            frame:true,            
            items:[cabeceraControlFormularios(),gridControlFormularios(),{
                id: 'detailPanel',
               
                bodyStyle: {
                    background: '#ffffff',
                    padding: '7px'
                },
                html: 'Seleccionar un registro para ver detalles ...'
            }]

        },{
            title: 'Boletas',
           
            xtype:'panel',
            frame:true,
            // autoScroll:true,
            items:[cabeceraControlFormulariosBoletas(),gridControlFormulariosBoletas(),{
                id: 'detailPanelBoletas',
              
                bodyStyle: {
                    background: '#ffffff',
                    padding: '7px'
                },
                html: 'Seleccionar un registro para ver detalles ...'
            }]

        }
        ]
        
    });
    return panel;
}
function cabeceraControlFormulariosBoletas(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    var comboTERMINAL = getCombo("TERMINAL", "TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL");
    // var comboGESTION = getCombo("GESTION", "GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION");
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','GESTIÓN','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','REGISTRO_GESTION','GESTIONES...');
    
    var comboLoteEnviado=  new Ext.form.ComboBox({
        fieldLabel: 'Lote Enviado',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N',  'NO'],
            ['-','-']]
        })
    });
    var comboLoteRechazado=  new Ext.form.ComboBox({
        fieldLabel: 'Lote Rechazado',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'opcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'opcion'],
            data : [['S', 'SI'],
            ['N',  'NO']]
        })
    });
    var fechaDesde = new Ext.form.DateField({
        fieldLabel : 'Desde',
        name : 'FECHA_DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        disabled:true
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel : 'Hasta',
        name : 'FECHA_HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        disabled:true
    });
    var dt = new Date();
    fechaDesde.setValue(dt);
    fechaHasta.setValue(dt);
    /*******************/
   

    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboRECAUDADOR.disable();
            comboSUCURSAL.disable();
            comboTERMINAL.disable();
            comboREGISTRO_GESTION.disable();

            comboRECAUDADOR.reset();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();
            comboREGISTRO_GESTION.reset();

        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){
   
        if(esteCombo.getRawValue().length==0){

            comboTERMINAL.disable();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();

        }

    }, null,null  ) ;

    comboSUCURSAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
            comboTERMINAL.disable();
            comboTERMINAL.reset();
        }

        comboTERMINAL.store.baseParams['sUCURSAL'] = newValue;
        comboTERMINAL.store.baseParams['limit'] = 10;
        comboTERMINAL.store.baseParams['start'] = 0;
        comboTERMINAL.store.reload();

    }, null,null  ) ;
    comboTERMINAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null,null  ) ;

    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();

        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){

        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
    }, null,null ) ;

    comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_SUCURSAL'] = record.data.SUCURSAL;
        comboTERMINAL.store.reload();


    }, null,null ) ;



    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;

    comboRECAUDADOR.disable();

    comboTERMINAL.disable();
    comboREGISTRO_GESTION.disable();
    comboSUCURSAL.disable();

    comboRED.allowBlank= false;
    comboTERMINAL.allowBlank= true;
    comboRECAUDADOR.allowBlank= true;
    comboREGISTRO_GESTION.allowBlank=true;
    comboSUCURSAL.allowBlank = false;
    
    /*********************/


    var formPanel = new Ext.form.FormPanel({
        title   : 'Control de Formularios',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        buttons : [{           
            text : 'Filtrar',
            formBind : true,
            handler : function() {
               
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FECHA_DESDE'] = fechaDesde.getRawValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FECHA_HASTA'] = fechaHasta.getRawValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_RECAUDADOR'] = comboRECAUDADOR.getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_RED'] = comboRED.getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_GESTION'] = comboREGISTRO_GESTION.getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_SUCURSAL'] = comboSUCURSAL.getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_TERMINAL'] = comboTERMINAL.getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['RECIBIDOS'] = comboTERMINAL.getValue();

                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_ACEPTADOS'] =Ext.getCmp('idFiltroAceptadosBoletas').getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_RECHAZADOS'] =Ext.getCmp('idFiltroRechazadosBoletas').getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_TODOS_ACEPTADOS_RECHAZADOS'] = Ext.getCmp('idFiltroTodosAceptadosRechazadosBoletas').getValue();

                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_EFECTIVO'] = Ext.getCmp('idFiltroEfectivoBoletas').getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_CHEQUE'] =Ext.getCmp('idFiltroChequeBoletas').getValue();
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_TODOS_FORMA_PAGO'] = Ext.getCmp('idFiltroTodosBoletas').getValue();


                Ext.getCmp('gridControlFormulariosBoletas').store.reload();
            }
        },{          
            text : 'Generar Reporte',
            formBind : true,
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
                        src : 'reportes?op=CntForm-BolRechazadasNoRechazadas'+'&todosFormaPago='+Ext.getCmp('idFiltroTodosBoletas').getValue()+'&todosAceptadosRechazados='+Ext.getCmp('idFiltroTodosAceptadosRechazadosBoletas').getValue()+'&filtroRechazados='+Ext.getCmp('idFiltroRechazadosBoletas').getValue()+'&filtroAceptados='+Ext.getCmp('idFiltroAceptadosBoletas').getValue()+'&filtroCheque='+Ext.getCmp('idFiltroChequeBoletas').getValue()+'&filtroEfectivo='+Ext.getCmp('idFiltroEfectivoBoletas').getValue()+'&idGestion='+comboREGISTRO_GESTION.getValue()+'&idTerminal='+comboTERMINAL.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&idRed='+comboRED.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&fechaDesde='+fechaDesde.getRawValue()+'&fechaHasta='+fechaHasta.getRawValue()
                    });
                } catch (e) {

                }
            }
        }, {
            text : 'Eliminar Filtros',
            handler : function() {
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FECHA_DESDE'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FECHA_HASTA'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_RECAUDADOR'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_GESTION'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_SUCURSAL'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_TERMINAL'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_EFECTIVO'] = "";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_CHEQUE'] ="";                
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_TODOS'] = "true";

                //Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_ACEPTADOS'] ="";
                //Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_RECHAZADOS'] ="";
                //Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_ACEPTADOS_RECHAZADOS_TODOS'] = "true";

                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_ACEPTADOS'] ="";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_RECHAZADOS'] ="";
                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['FILTRO_TODOS_ACEPTADOS_RECHAZADOS'] = "true";

                Ext.getCmp('gridControlFormulariosBoletas').store.baseParams['ID_RED']  ="";

                Ext.getCmp('gridControlFormulariosBoletas').store.reload();
             
                fechaDesde.reset();
                fechaHasta.reset();
                comboREGISTRO_GESTION.reset();
                comboRECAUDADOR.reset();
                comboSUCURSAL.reset();
                comboTERMINAL.reset();
                comboRED.reset();           
              
            }
        }], 
        items   : [
        {
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [fechaDesde,comboRED,comboRECAUDADOR, comboSUCURSAL]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [fechaHasta,comboTERMINAL,comboREGISTRO_GESTION]
            }]
        },{
            xtype: 'radiogroup',
            id:'idGroupFiltrosBoletas',
            itemCls: 'x-check-group-alt',          
            hideLabel:true,
            allowBlank: false,
            anchor: '95%',
            items: [{               
                columnWidth: '.15',
                items: [                
                {
                    id:'idFiltroEfectivoBoletas',
                    boxLabel: 'Efectivo',
                    name: 'FILTRO_CHECK',
                    inputValue: 1
                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    id:'idFiltroChequeBoletas',
                    boxLabel: 'Cheque',
                    name: 'FILTRO_CHECK',
                    inputValue: 3,
                    checked:true
                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    id:'idFiltroTodosBoletas',
                    boxLabel: 'TODOS',
                    name: 'FILTRO_CHECK',
                    inputValue: 3,
                    checked:true
                }
                ]
            }]
        },
        {
            xtype: 'radiogroup',
            id:'idGroupFiltrosBoletas2',
            itemCls: 'x-check-group-alt',
            hideLabel:true,
            allowBlank: false,
            anchor: '95%',
            items: [{
                columnWidth: '.15',
                items: [
                {
                    id:'idFiltroAceptadosBoletas',
                    boxLabel: 'Aceptados',
                    name: 'FILTRO_CHECK2',
                    inputValue: 1
                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    id:'idFiltroRechazadosBoletas',
                    boxLabel: 'Rechazados',
                    name: 'FILTRO_CHECK2',
                    inputValue: 3,
                    checked:true
                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    id:'idFiltroTodosAceptadosRechazadosBoletas',
                    boxLabel: 'TODOS',
                    name: 'FILTRO_CHECK2',
                    inputValue: 3,
                    checked:true
                }
                ]
            }]
        },{
            layout:'column',
            items:[{
                width: '325',
                layout:'form',
                items: [comboLoteEnviado]
            }, {
                xtype: 'button',
                text:  'Enviado',
                width: '75',
                id   : 'idBtnEnviado',
                formBind : true,
                handler : function() {
                  arrayFormEnviado = Ext.getCmp('gridControlFormulariosBoletas').getSelectionModel().getSelections();
                  for (i = 0; i < arrayFormEnviado.length; i ++)  {
                      reload = (i == (arrayFormEnviado.length -1));
                      arrayFormEnviado[i].data.ENVIADO = comboLoteEnviado.getValue();
                      actualizarFormulariosBoletas(null, arrayFormEnviado[i], null, reload);
                  }
                }
            }]
          },{
              layout: 'column',
              items:[{
                      width: '325',
                      layout:'form',
                      items: [comboLoteRechazado]
              },{
                xtype: 'button',
                text : 'Rechazado',
                width: '75',
                id   : 'idBtnRechazado',
                formBind : true,
                handler : function() {
                      arrayFormRechazado = Ext.getCmp('gridControlFormulariosBoletas').getSelectionModel().getSelections();
                      for (i = 0; i < arrayFormRechazado.length; i ++)  {
                          reload = (i == (arrayFormRechazado.length -1));
                          arrayFormRechazado[i].data.RECHAZADO = comboLoteRechazado.getValue();
                          actualizarFormulariosBoletas(null, arrayFormRechazado[i], null, reload);
                      }

                }
              }]
          }
        ]
    });
    

    return formPanel;
    
}
function cabeceraControlFormularios(){
    var comboRED =getCombo('RED','RED','RED','RED','DESCRIPCION_RED','RED','RED','DESCRIPCION_RED','RED','RED');
    var comboRECAUDADOR = getCombo("RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR", "DESCRIPCION_RECAUDADOR", "RECAUDADOR", "RECAUDADOR");
    var comboSUCURSAL = getCombo("SUCURSAL", "SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL", "DESCRIPCION_SUCURSAL", "SUCURSAL", "SUCURSAL");
    var comboTERMINAL = getCombo("TERMINAL", "TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL", "DESCRIPCION_TERMINAL", "TERMINAL", "TERMINAL");
    // var comboGESTION = getCombo("GESTION", "GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION", "DESCRIPCION_GESTION", "GESTION", "GESTION");
    var comboREGISTRO_GESTION = getCombo('REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','GESTIÓN','REGISTRO_GESTION','DESCRIPCION_REGISTRO_GESTION','REGISTRO_GESTION','GESTIONES...');


   var comboLoteEnviado=  new Ext.form.ComboBox({
        fieldLabel: 'Lote Enviado',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N', 'NO'],
            ['-', '-']]

        })
    });
    var comboLoteValidado=  new Ext.form.ComboBox({
        fieldLabel: 'Lote Valido',
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'opcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'opcion'],
            data : [['1', 'SI'],
            ['2',  'NO']]
        })
    });
   var loteFecha = new Ext.form.DateField({
        fieldLabel : 'Fecha Lote',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y'
    });
    var fechaDesde = new Ext.form.DateField({
        fieldLabel : 'Desde',
        name : 'FECHA_DESDE',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        disabled:true
    });
    var fechaHasta = new Ext.form.DateField({
        fieldLabel : 'Hasta',
        name : 'FECHA_HASTA',
        height : '22',
        anchor : '95%',
        allowBlank:false,
        format:'d/m/Y',
        disabled:true
    });
    var dt = new Date();
    fechaDesde.setValue(dt);
    fechaHasta.setValue(dt);
    /*******************/


    comboRED.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboRECAUDADOR.disable();
            comboSUCURSAL.disable();
            comboTERMINAL.disable();
            comboREGISTRO_GESTION.disable();

            comboRECAUDADOR.reset();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();
            comboREGISTRO_GESTION.reset();

        }

    }, null,null  ) ;

    comboRECAUDADOR.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0){

            comboTERMINAL.disable();
            comboSUCURSAL.disable();
            comboSUCURSAL.reset();
            comboTERMINAL.reset();

        }

    }, null,null  ) ;

    comboSUCURSAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
            comboTERMINAL.disable();
            comboTERMINAL.reset();
        }

        comboTERMINAL.store.baseParams['sUCURSAL'] = newValue;
        comboTERMINAL.store.baseParams['limit'] = 10;
        comboTERMINAL.store.baseParams['start'] = 0;
        comboTERMINAL.store.reload();

    }, null,null  ) ;
    comboTERMINAL.addListener( 'change',function(esteCombo,newValue, oldValue ){

        if(esteCombo.getRawValue().length==0 ){
            comboREGISTRO_GESTION.disable();
            comboREGISTRO_GESTION.reset();


        }
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = newValue;
        comboREGISTRO_GESTION.store.baseParams['limit'] = 10;
        comboREGISTRO_GESTION.store.baseParams['start'] = 0;
        comboREGISTRO_GESTION.store.reload();

    }, null,null  ) ;

    comboRED.addListener( 'select',function(esteCombo, record,  index  ){
        comboRECAUDADOR.enable();

        comboRECAUDADOR.store.baseParams['ID_RED'] = record.data.RED;
        comboRECAUDADOR.store.reload();

    }, null,null ) ;

    comboRECAUDADOR.addListener( 'select',function(esteCombo, record,  index  ){

        comboSUCURSAL.enable();
        comboSUCURSAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboSUCURSAL.store.reload();

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_RECAUDADOR'] = record.data.RECAUDADOR;
        comboTERMINAL.store.reload();
    }, null,null ) ;

    comboSUCURSAL.addListener( 'select',function(esteCombo, record,  index  ){

        comboTERMINAL.enable();
        comboTERMINAL.store.baseParams['ID_SUCURSAL'] = record.data.SUCURSAL;
        comboTERMINAL.store.reload();


    }, null,null ) ;



    comboTERMINAL.addListener( 'select',function(esteCombo, record,  index  ){
        comboREGISTRO_GESTION.enable();
        comboREGISTRO_GESTION.store.baseParams['TERMINAL'] = record.data.TERMINAL;
        comboREGISTRO_GESTION.store.reload();
    }, null,null ) ;

    comboRECAUDADOR.disable();

    comboTERMINAL.disable();
    comboREGISTRO_GESTION.disable();
    comboSUCURSAL.disable();

    comboRED.allowBlank= false;
    comboTERMINAL.allowBlank= true;
    comboRECAUDADOR.allowBlank= true;
    comboREGISTRO_GESTION.allowBlank=true;
    comboSUCURSAL.allowBlank = false;

    /*********************/


    var formPanel = new Ext.form.FormPanel({
        title   : 'Control de Formularios',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        buttons : [{          
            text : 'Filtrar',
            formBind : true,
            handler : function() {
               
                Ext.getCmp('gridControlFormularios').store.baseParams['FECHA_DESDE'] = fechaDesde.getRawValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['FECHA_HASTA'] = fechaHasta.getRawValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_RECAUDADOR'] = comboRECAUDADOR.getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_RED'] = comboRED.getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_GESTION'] = comboREGISTRO_GESTION.getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_SUCURSAL'] = comboSUCURSAL.getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_TERMINAL'] = comboTERMINAL.getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['RECIBIDOS'] = comboTERMINAL.getValue();
         
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_RECEPCIONADOS'] = Ext.getCmp('idFiltroRecepcionados').getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_CONTROLADOS'] =Ext.getCmp('idFiltroControlados').getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_TODOS_RECEPCIONADOS'] = Ext.getCmp('idFiltroTodosRecepcionados').getValue();
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_TODOS_CONTROLADOS'] = Ext.getCmp('idFiltroTodosControlados').getValue();

                Ext.getCmp('gridControlFormularios').store.reload();
            }
        },{          
            text : 'Generar Reporte',
            formBind : true,
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
                        src : 'reportes?op=CntForm-RecCntEnv'+'&todosRecepcionados='+Ext.getCmp('idFiltroTodosRecepcionados').getValue()+'&todosControlados='+Ext.getCmp('idFiltroTodosControlados').getValue()+'&controlados='+Ext.getCmp('idFiltroControlados').getValue()+'&recepcionados='+Ext.getCmp('idFiltroRecepcionados').getValue()+'&idGestion='+comboREGISTRO_GESTION.getValue()+'&idTerminal='+comboTERMINAL.getValue()+'&idSucursal='+comboSUCURSAL.getValue()+'&idRed='+comboRED.getValue()+'&idRecaudador='+comboRECAUDADOR.getValue()+'&fechaDesde='+fechaDesde.getRawValue()+'&fechaHasta='+fechaHasta.getRawValue()
                    });
                } catch (e) {

                }
            }
        }, {
            text : 'Eliminar Filtros',
            handler : function() {
                Ext.getCmp('gridControlFormularios').store.baseParams['FECHA_DESDE'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['FECHA_HASTA'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_RECAUDADOR'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_GESTION'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_SUCURSAL'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_TERMINAL'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_RECEPCIONADOS'] = "";
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_CONTROLADOS'] ="";
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_TODOS_RECEPCIONADOS'] = "true";
                Ext.getCmp('gridControlFormularios').store.baseParams['FILTRO_TODOS_CONTROLADOS'] = "true";
                Ext.getCmp('gridControlFormularios').store.baseParams['ID_RED']  ="";
                
                Ext.getCmp('gridControlFormularios').store.reload();
                Ext.getCmp('idFiltroTodosControlados').checked= true;
                Ext.getCmp('idFiltroTodosRecepcionados').checked= true;

                
                fechaDesde.reset();
                fechaHasta.reset();
                comboREGISTRO_GESTION.reset();
                comboRECAUDADOR.reset();
                comboSUCURSAL.reset();
                comboTERMINAL.reset();
                comboRED.reset();

            }
        }],
        items   : [
        {
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [fechaDesde,comboRED,comboRECAUDADOR, comboSUCURSAL]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [fechaHasta,comboTERMINAL,comboREGISTRO_GESTION]
            }]
        },{
            xtype: 'radiogroup',
            id:'idGroupFiltros',
            itemCls: 'x-check-group-alt',
            hideLabel:true,
            allowBlank: false,
            anchor: '95%',
            items: [{
                columnWidth: '.15',
                items: [
                {
                    xtype: 'label',
                    text: 'Grupo 1',
                    cls:'x-form-check-group-label',
                    anchor:'-15'
                },
                {
                    id:'idFiltroRecepcionados',
                    boxLabel: 'Recepcionados',
                    name: 'FILTRO_RECEPCIONADOS',
                    inputValue: 1
                },

                {
                    id:'idFiltroControlados',
                    boxLabel: 'Controlado',
                    name: 'FILTRO_CONTROLADOS',
                    inputValue: 1

                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    xtype: 'label',
                    text: 'Grupo 2',
                    cls:'x-form-check-group-label',
                    anchor:'-15'
                },
                {
                    id:'idFiltroNoRecibidos',
                    boxLabel: 'No Recibidos',
                    name: 'FILTRO_RECEPCIONADOS',
                    inputValu:2
                },
                {
                    id:'idFiltroNoControlados',
                    boxLabel: 'No Controlado',
                    name: 'FILTRO_CONTROLADOS',
                    inputValue: 2
                }
                ]
            },{
                columnWidth: '.15',
                items: [
                {
                    xtype: 'label',
                    text: 'Grupo 3',
                    cls:'x-form-check-group-label',
                    anchor:'-15'
                },{
                    id:'idFiltroTodosRecepcionados',
                    boxLabel: 'TODOS',
                    name: 'FILTRO_RECEPCIONADOS',
                    inputValue: 3,
                    checked:true
                },{
                    id:'idFiltroTodosControlados',
                    boxLabel: 'TODOS',
                    name: 'FILTRO_CONTROLADOS',
                    inputValue: 3,
                    checked:true

                }
                ]
            }]
        },{
            layout:'column',
            items:[{
                width:'325',
                layout: 'form',
                items: [loteFecha]
            }, {
                xtype: 'button',
                text:  'Fec. Recibido',
                id   : 'idBtnFechaRecibido',
                formBind : true,
                handler : function() {
                  arrayFormFechaRecibido = Ext.getCmp('gridControlFormularios').getSelectionModel().getSelections();
                  for (i = 0; i < arrayFormFechaRecibido.length; i ++)  {
                      reload = (i == (arrayFormFechaRecibido.length -1));
                      arrayFormFechaRecibido[i].data.FECHA_RECIBIDO = loteFecha.getValue();
                      actualizarFormulario(null, arrayFormFechaRecibido[i], null, reload,3);
                  }

                }
            },{
                xtype: 'button',
                text : 'Fec. Controlado',
                id   : 'idBtnFechaControlado',
                formBind : true,
                handler : function() {
                      arrayFormFechaControlado = Ext.getCmp('gridControlFormularios').getSelectionModel().getSelections();
                      for (i = 0; i < arrayFormFechaControlado.length; i ++)  {
                          reload = (i == (arrayFormFechaControlado.length -1));
                          arrayFormFechaControlado[i].data.FECHA_CONTROLADO = loteFecha.getValue();
                          actualizarFormulario(null, arrayFormFechaControlado[i], null, reload,2);
                      }

                }
            }]
          },{
            layout:'column',
            items:[{
                width:'325',
                layout: 'form',
                items: [comboLoteEnviado]
            }, {
                xtype: 'button',
                text:  'Enviado',
                width: '75',
                id   : 'idBtnEnviado',
                formBind : true,
                handler : function() {
                  arrayFormEnviado = Ext.getCmp('gridControlFormularios').getSelectionModel().getSelections();
                  for (i = 0; i < arrayFormEnviado.length; i ++)  {
                      reload = (i == (arrayFormEnviado.length -1));
                      arrayFormEnviado[i].data.ENVIADO = comboLoteEnviado.getValue();
                      actualizarFormulario(null, arrayFormEnviado[i], null, reload,4);
                  }

                }
            }]
          },{
              layout: 'column',
              items:[{
                      width:'325',
                      layout:     'form',
                      items:      [comboLoteValidado]
              },{                 
                xtype: 'button',                
                text : 'Valido',
                width: '75',
                id   : 'idBtnValido',
                formBind : true,
                handler : function() {
                      arrayFormValido = Ext.getCmp('gridControlFormularios').getSelectionModel().getSelections();
                      for (i = 0; i < arrayFormValido.length; i ++)  {
                          reload = (i == (arrayFormValido.length -1));
                          arrayFormValido[i].data.VALIDO = comboLoteValidado.getValue();
                          actualizarFormulario(null, arrayFormValido[i], null, reload, 4);
                      }

                }

              }]
          }
        ]
    });


    return formPanel;

}

function gridControlFormularios(){
    var proxy = new Ext.data.HttpProxy({
        url: 'CONTROL_FORMULARIOS?op=LISTAR'
    });
    var reader = new Ext.data.JsonReader({
        root : 'FORMULARIOS',
        totalProperty : 'TOTAL',
        id : 'ID_FORM_CONTRIB',
        fields : ["CAMPO_VALOR","ID_TERMINAL","FECHA_CERTIFICACION","ID_GESTION","ID_SUCURSAL","ID_RECAUDADOR","ID_FORM_CONTRIB","CODIGO_TERMINAL","DESCRIPCION_GESTION","DESCRIPCION_SUCURSAL","DESCRIPCION_RECAUDADOR","CONSECUTIVO","FECHA_COBRO","NUMERO_ORDEN","RUC","MONTO_TOTAL","MONTO_PAGADO","FORMA_PAGO","NUMERO_CHEQUE","RECHAZADO","FECHA_RECIBIDO","FECHA_CONTROLADO","NUMERO_FORMULARIO","ENVIADO","VALIDO"]
    });
    var writer = new Ext.data.JsonWriter();  
    var store = new Ext.data.Store({
        id: 'user',       
        proxy: proxy,
        reader: reader,
        writer: writer,  
        listeners: {
            'update'  : function ( esteObjeto,  record, operation ) {
                actualizarFormulario( esteObjeto,  record, operation, true, 1);
            }
        } 
    });

    var comboEnviado=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N',  'NO'],
            ['-','-']]

        })
    });
    var comboValidado=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'opcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'opcion'],
            data : [['1', 'SI'],
            ['2',  'NO']]
        })
    });

    var anchoDefault= 20;
    var smFormGrid = new Ext.grid.CheckboxSelectionModel({singleSelect: false});
    var userColumns =  [
    smFormGrid.valueOf(),
    {
        header : "Terminal",
        width : 15,
        dataIndex : 'CODIGO_TERMINAL'

    },{
        header : "Gestion",
        width : 15,
        dataIndex : 'DESCRIPCION_GESTION'

    }, {
        header : "Consecutivo",
        width : 15,
        dataIndex : 'CONSECUTIVO'

    },{
        header : "Fec.Certificado",
        width : anchoDefault,
        dataIndex : 'FECHA_CERTIFICACION'

    },{
        header : "Formulario",
        width : 15,
        dataIndex : 'NUMERO_FORMULARIO'

    },{
        header : "Nro.Orden",
        width : anchoDefault,
        dataIndex : 'NUMERO_ORDEN'
    },{
        header : "RUC",
        width : 15,
        dataIndex : 'RUC'
    },{
        header : "F. Recibido",
      
        width : anchoDefault,
        dataIndex : 'FECHA_RECIBIDO',       
        editor: {
            xtype: 'datefield',           
            format:'d/m/Y'
        }
    },{
        header : "F. Controlado",    
        width : 15,
        dataIndex : 'FECHA_CONTROLADO',
        editor: {
            xtype: 'datefield',           
            format:'d/m/Y'
        }
    },{
        header : "Enviado",
        width : 15,
        dataIndex : 'ENVIADO',
        editor: comboEnviado
    },
    {
        header : "Valido",
        width : 15,
        dataIndex : 'VALIDO',
        editor: comboValidado
    }];
    store.load();       
    editor.id = "idEditor";
    var bookTplMarkup = [
    '<div >',
    '<table>',

    '<tr>',
    '<td>',
    'MONTO TOTAL: {MONTO_TOTAL}',
    '</td>',
    '</tr>',
    '<tr>',
    '<td>',
    '{CAMPO_VALOR}',
    '</td>',
    '</tr>',
   
    '</table>',
    '</div>'
    ];
    var bookTpl = new Ext.Template(bookTplMarkup);

    var gridControlFormulario = new Ext.grid.GridPanel({
        id:'gridControlFormularios',     
        height:200,
        sm:smFormGrid,
        // margins: '0 5 5 5',
        plugins: [editor],
        store: store,
        // autoScroll:true,
        columns:userColumns,      
        viewConfig: {
            forceFit:true
        },
        tbar:[],
        //split: true,
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : store,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        // frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                editorActivo = editor.id;
            }
        }
    });
    
    gridControlFormulario.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
        var detailPanel = Ext.getCmp('detailPanel');
        bookTpl.overwrite(detailPanel.body, r.data);
    });

    return gridControlFormulario;

}
function detalleControlFormularios(elIdFormContrib,laOpcion){  

    var individual = [{
        anchor:'95%',
        bodyStyle: 'padding-right:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Cabecera Declaración Jurada',
            autoHeight: true,
            defaultType: 'checkbox', 
            items: [{
                xtype: 'textfield',
                name: 'NUMERO_FORMULARIO',
                fieldLabel: 'Formulario'
            }, {
                xtype: 'textfield',
                name: 'FECHA_COBRO',
                fieldLabel: 'Fecha Cobro'
            },{
                xtype: 'textfield',
                name: 'NUMERO_ORDEN',
                fieldLabel: 'Nro. Orden'
            }, {
                xtype: 'textfield',
                name: 'RUC',
                fieldLabel: 'RUC'
            }, {
                xtype: 'textfield',
                name: 'CONSECUTIVO',
                fieldLabel: 'Consecutivo'
            }]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        items: {
            anchor:'95%',
            xtype: 'fieldset',
            title: 'Lugar de Cobro',
            autoHeight: true,
            defaultType: 'radio', 
            items: [{
                xtype: 'textfield',
                name: 'CODIGO_TERMINAL',
                fieldLabel: 'Terminal'
            }, {
                xtype: 'textfield',
                name: 'CODIGO_RECAUDADOR',
                fieldLabel: 'Recaudador'
            },{
                xtype: 'textfield',
                name: 'DESCRIPCION_RECAUDADOR',
                fieldLabel: 'Descripcion'
            },{
                xtype: 'textfield',
                name: 'CODIGO_CAJA_SET',
                fieldLabel: 'Caja Nro.'
            }]
        }
    }];

    var individual2 = [{
        bodyStyle: 'padding-right:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Importe y Forma de Pago',
            autoHeight: true,
            defaultType: 'checkbox', 
            items: [{
                xtype: 'textfield',
                name: 'MONTO_TOTAL',
                fieldLabel: 'Monto Total'
            }, {
                xtype: 'textfield',
                name: 'MONTO_PAGADO',
                fieldLabel: 'Monto Pagado'
            },{
                xtype: 'textfield',
                name: 'FECHA_COBRO',
                fieldLabel: 'Fecha Cobro'
            },{
                xtype: 'textfield',
                name: 'FORMA_PAGO',
                fieldLabel: 'Forma de Pago'
            },{
                xtype: 'textfield',
                name: 'NUMERO_CHEQUE',
                fieldLabel: 'Nro. de Cheque'
            },{
                xtype: 'textfield',
                name: 'ID_BANCO',
                fieldLabel: 'Banco'
            },{
                xtype: 'textfield',
                name: 'DESCRIPCION_BANCO',
                fieldLabel: 'Descripción'
            },{
                xtype: 'textfield',
                name: 'RECHAZADO',
                fieldLabel: 'Estado Cheque'
            }]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Orden de Transaferencia',
            autoHeight: true,
            defaultType: 'radio', 
            items: [{
                xtype: 'textfield',
                name: 'NUMERO_OT',
                fieldLabel: 'O.T Nro.'
            },{
                xtype: 'textfield',
                name: 'FECHA_RECIBIDO',
                fieldLabel: 'Fecha Recepción'
            },{
                xtype: 'textfield',
                name: 'FECHA_CONTROLADO',
                fieldLabel: 'Fecha Controlado'
            },{
                xtype: 'textfield',
                name: 'ENVIADO',
                fieldLabel: 'Enviado'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Valor BCO'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Valor BCP'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Venc. Doc.'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Entrega'
            }]
        }
    }];
    var fp = new Ext.FormPanel({
     
        frame: true,
        labelWidth: 110,
        width: 600,       
        bodyStyle: 'padding:0 10px 0;',
        items: [
        {
            layout: 'column',
            border: false,
         
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        },
        {
            layout: 'column',
            border: false,
            
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual2
        }
        ],
        buttons: [{
            text: 'Salir',
            handler: function(){
                win.close();
            }
        }]
    });

    fp.getForm().load({
        url : 'CONTROL_FORMULARIOS?op='+laOpcion,
        method : 'POST',
        params:{
            ID_FORM_CONTRIB: elIdFormContrib
        },
        waitMsg : 'Cargando...'
    });
   

    var win = new Ext.Window({
        title:'Detalle Control de Formularios',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        y:100,
        resizable : false,
        items : [fp]
    });win.show();



}

function gridControlFormulariosBoletas(){
    var proxy = new Ext.data.HttpProxy({
        url: 'CONTROL_FORMULARIOS?op=LISTAR_BOLETAS'
    });
    var reader = new Ext.data.JsonReader({
        root : 'FORMULARIOS',
        totalProperty : 'TOTAL',
        id : 'ID_TRANSACCION',
        fields : ["ID_TERMINAL","ID_GESTION","ID_SUCURSAL","ID_RECAUDADOR","ID_BANCO","ID_TRANSACCION","CODIGO_TERMINAL","DESCRIPCION_GESTION","DESCRIPCION_SUCURSAL","DESCRIPCION_RECAUDADOR","CONSECUTIVO","FECHA_COBRO","NUMERO_ORDEN","RUC","MONTO_TOTAL","MONTO_PAGADO","FORMA_PAGO","NUMERO_CHEQUE","DESCRIPCION_BANCO","RECHAZADO","FECHA_RECIBIDO","FECHA_CONTROLADO","NUMERO_IMPUESTO","ENVIADO" ]
    });
    var writer = new Ext.data.JsonWriter();
    var store = new Ext.data.Store({
        id: 'storeBoletas',
        proxy: proxy,
        reader: reader,
        writer: writer,
        listeners: {
            'update'  : function ( esteObjeto,  record, operation ) {
                actualizarFormulariosBoletas( esteObjeto,  record, operation, true, 1);
            }
        }
    });

    var comboEnviado=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        emptyText: 'Tipo...',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        allowBlank : false,
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['S', 'SI'],
            ['N',  'NO'],
            ['-','-']]
        })
    });
    var comboRechazado=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'opcion',
        mode: 'local',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'opcion'],
            data : [['S', 'SI'],
            ['N',  'NO']]
        })
    });
    var anchoDefault= 20;
    var smFormBoletasGrid = new Ext.grid.CheckboxSelectionModel({singleSelect: false});
    var userColumns =  [
    smFormBoletasGrid.valueOf(),
    {
        header : "Terminal",
        width : anchoDefault,
        dataIndex : 'CODIGO_TERMINAL'

    },{
        header : "Gestion",
        width : anchoDefault,
        dataIndex : 'DESCRIPCION_GESTION'

    }, {
        header : "Consec.",
        width : anchoDefault,
        dataIndex : 'CONSECUTIVO'

    },{
        header : "Fecha Cobro",
        width : anchoDefault,
        dataIndex : 'FECHA_COBRO'

    },{
        header : "Nro. Impuesto",
        width : anchoDefault,
        dataIndex : 'NUMERO_IMPUESTO'

    },{
        header : "Nro.Orden",
        width : anchoDefault,
        dataIndex : 'NUMERO_ORDEN'
    },{
        header : "RUC",
        width : anchoDefault,
        dataIndex : 'RUC'
    },{
        header : "F. Pago",
        width : anchoDefault,
        dataIndex : 'FORMA_PAGO'
    },{
        header : "Rechazado",
        width : anchoDefault,
        dataIndex : 'RECHAZADO',
        editor: comboRechazado
    },{
        header : "Enviado",
        width : 15,
        dataIndex : 'ENVIADO',
        editor: comboEnviado
    }];
    store.load();
    editor2.id = "idEditor2";
    var bookTplMarkup = [
    '<div >',
    '<table>',

    '<tr>',
    '<td>',
    'MONTO TOTAL: {MONTO_TOTAL}',
    '</td>',
    '</tr>',

    '<tr>',
    '<td>',
    'MONTO PAGADO: {MONTO_PAGADO}',
    '</td>',
    '</tr>',    

    '<tr>',
    '<td>',
    'NÚMERO CHEQUE: {NUMERO_CHEQUE}',
    '</td>',
    '</tr>',
    '<tr>',
    '<td>',
    'BANCO: {DESCRIPCION_BANCO}',
    '</td>',
    '</tr>',
    '</table>',
    '</div>'
    ];
    var bookTpl = new Ext.Template(bookTplMarkup);

    var gridControlFormulario = new Ext.grid.GridPanel({
        id:'gridControlFormulariosBoletas',
        height:200,
        sm:smFormBoletasGrid,
        //margins: '5 5 5 5',
        plugins: [editor2],
        store: store,
        // autoScroll:true,
        columns:userColumns,
        viewConfig: {
            forceFit:true
        },
        tbar:[],
        // split: true,
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                editorActivo = editor2.id;
            }
        },
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : store,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten Datos que mostrar",
            items : ['-']
        }),
        // frame:true,
        iconCls:'icon-grid'
    });
    gridControlFormulario.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
        var detailPanel = Ext.getCmp('detailPanelBoletas');
        bookTpl.overwrite(detailPanel.body, r.data);
    });
  
    return gridControlFormulario;

}
function detalleControlFormulariosBoletas(elIdFormContrib){

    var individual = [{
        anchor:'95%',
        bodyStyle: 'padding-right:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Cabecera Declaración Jurada',
            autoHeight: true,
            defaultType: 'checkbox', // each item will be a checkbox
            items: [{
                xtype: 'textfield',
                name: 'NUMERO_FORMULARIO',
                fieldLabel: 'Formulario'
            }, {
                xtype: 'textfield',
                name: 'FECHA_COBRO',
                fieldLabel: 'Fecha Cobro'
            },{
                xtype: 'textfield',
                name: 'NUMERO_ORDEN',
                fieldLabel: 'Nro. Orden'
            }, {
                xtype: 'textfield',
                name: 'RUC',
                fieldLabel: 'RUC'
            }, {
                xtype: 'textfield',
                name: 'CONSECUTIVO',
                fieldLabel: 'Consecutivo'
            }]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        anchor:'95%',
        items: {
            anchor:'95%',
            xtype: 'fieldset',
            title: 'Lugar de Cobro',
            autoHeight: true,
            defaultType: 'radio', // each item will be a radio button
            items: [{
                xtype: 'textfield',
                name: 'CODIGO_TERMINAL',
                fieldLabel: 'Terminal'
            }, {
                xtype: 'textfield',
                name: 'CODIGO_RECAUDADOR',
                fieldLabel: 'Recaudador'
            },{
                xtype: 'textfield',
                name: 'DESCRIPCION_RECAUDADOR',
                fieldLabel: 'Descripcion'
            },{
                xtype: 'textfield',
                name: 'CODIGO_CAJA_SET',
                fieldLabel: 'Caja Nro.'
            }]
        }
    }];

    var individual2 = [{
        bodyStyle: 'padding-right:5px;',
        width: 300,
        items: {
            xtype: 'fieldset',
            title: 'Importe y Forma de Pago',
            autoHeight: true,
            defaultType: 'checkbox', // each item will be a checkbox
            items: [{
                xtype: 'textfield',
                name: 'MONTO_TOTAL',
                fieldLabel: 'Monto Total'
            }, {
                xtype: 'textfield',
                name: 'MONTO_PAGADO',
                fieldLabel: 'Monto Pagado'
            },{
                xtype: 'textfield',
                name: 'FECHA_COBRO',
                fieldLabel: 'Fecha Cobro'
            },{
                xtype: 'textfield',
                name: 'FORMA_PAGO',
                fieldLabel: 'Forma de Pago'
            },{
                xtype: 'textfield',
                name: 'NUMERO_CHEQUE',
                fieldLabel: 'Nro. de Cheque'
            },{
                xtype: 'textfield',
                name: 'ID_BANCO',
                fieldLabel: 'Banco'
            },{
                xtype: 'textfield',
                name: 'DESCRIPCION_BANCO',
                fieldLabel: 'Descripción'
            },{
                xtype: 'textfield',
                name: 'RECHAZADO',
                fieldLabel: 'Estado Cheque'
            }]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        width: 300,
        items: {
            xtype: 'fieldset',
            title: 'Orden de Transaferencia',
            autoHeight: true,
            defaultType: 'radio', // each item will be a radio button
            items: [{
                xtype: 'textfield',
                name: 'NUMERO_OT',
                fieldLabel: 'O.T Nro.'
            },{
                xtype: 'textfield',
                name: 'FECHA_RECIBIDO',
                fieldLabel: 'Fecha Recepción'
            },{
                xtype: 'textfield',
                name: 'FECHA_CONTROLADO',
                fieldLabel: 'Fecha Controlado'
            },{
                xtype: 'textfield',
                name: 'ENVIADO',
                fieldLabel: 'Enviado'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Valor BCO'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Valor BCP'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Venc. Doc.'
            },{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Fecha Entrega'
            }]
        }
    }];
    var fp = new Ext.FormPanel({

        frame: true,
        labelWidth: 110,
        width: 650,
        //  bodyStyle: 'padding:0 10px 0;',
        items: [
        {
            layout: 'column',
            border: false,
            // defaults are applied to all child items unless otherwise specified by child item
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual
        },
        {
            layout: 'column',
            border: false,
            // defaults are applied to all child items unless otherwise specified by child item
            defaults: {
                columnWidth: '.5',
                border: false
            },
            items: individual2
        }
        ],
        buttons: [{
            text: 'Salir',
            handler: function(){
                win.close();
            }
        }]
    });

    fp.getForm().load({
        url : 'CONTROL_FORMULARIOS?op=DETALLE',
        method : 'POST',
        params:{
            ID_FORM_CONTRIB: elIdFormContrib
        },
        waitMsg : 'Cargando...'
    });


    var win = new Ext.Window({
        title:'Detalle Control de Formularios',
        autoWidth : true,
        height : 'auto',
        closable : false,
        modal:true,
        y:100,
        resizable : false,
        items : [fp]
    });win.show();



}

function callExternalFunction(){
    //Poner el hook correspondiente a la funcion que se quiere llamar
    var idFormContrib = null;  
    if(editorActivo == 'idEditor'){
        //  var rec = Ext.getCmp('gridControlFormularios').getSelectionModel().getSelected();
        editor.editing = false;
        editor.hide();
        // idFormContrib =rec.data.ID_FORM_CONTRIB;
        detalleControlFormularios(Ext.getCmp('gridControlFormularios').getSelectionModel().getSelected().data.ID_FORM_CONTRIB,'DETALLE');
    }else if(editorActivo == 'idEditor2'){
        var rec = Ext.getCmp('gridControlFormulariosBoletas').getSelectionModel().getSelected();
        editor2.editing = false;
        editor2.hide();
        // idFormContrib =rec.data.ID_TRANSACCION;
        detalleControlFormularios(Ext.getCmp('gridControlFormulariosBoletas').getSelectionModel().getSelected().data.ID_TRANSACCION,'DETALLE_BOLETAS');
    }

}

function actualizarFormulario( esteObjeto,  record, operation, reload, isFecha){
                var fechaRecibido = record.data.FECHA_RECIBIDO;
                var fechaControlado = record.data.FECHA_CONTROLADO;
                if(isFecha==1){
                    if(fechaControlado !=""){
                        fechaControlado = fechaControlado.format('d/m/Y');
                    }
                    if(fechaRecibido !=""){
                        fechaRecibido = fechaRecibido.format('d/m/Y');
                    }
                }else if(isFecha!=4){

                    if(fechaControlado !="" && isFecha==2){
                        fechaControlado = fechaControlado.format('d/m/Y');
                    }
                    if(fechaRecibido !="" && isFecha==3){
                        fechaRecibido = fechaRecibido.format('d/m/Y');
                    }
                }
                var conn = new Ext.data.Connection();conn.request({
                    url : 'CONTROL_FORMULARIOS?op=MODIFICAR',
                    method : 'POST',
                    params : {

                        ID_FORM_CONTRIB : record.data.ID_FORM_CONTRIB,
                        RECHAZADO : record.data.RECHAZADO,
                        FECHA_RECIBIDO:fechaRecibido,
                        FECHA_CONTROLADO:fechaControlado,
                        ENVIADO:record.data.ENVIADO,
                        VALIDO:record.data.VALIDO
                    },
                    success : function(action) {
                        var  obj = Ext.util.JSON .decode(action.responseText);
                        if(obj.success){
                            if (reload) {
                                Ext.getCmp('gridControlFormularios').store.reload();
                            }
                        }else{
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg :FAIL_CUERPO_GENERAL,
                                icon : Ext.MessageBox.ERROR,
                                buttons : Ext.Msg.OK

                            });
                            Ext.getCmp('gridControlFormularios').store.reload();
                        }
                    },
                    failure : function(action) {
                        Ext.Msg.show({
                            title : FAIL_TITULO_GENERAL,
                            msg :FAIL_CUERPO_GENERAL,
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK

                        });
                        Ext.getCmp('gridControlFormularios').store.reload();
                    }
                })
            }
function actualizarFormulariosBoletas( esteObjeto,  record, operation, reload){
        var fechaRecibido = record.data.FECHA_RECIBIDO;
        var fechaControlado = record.data.FECHA_CONTROLADO;
        if(fechaControlado !=""){
            fechaControlado =fechaControlado.format('d/m/Y');
        }
        if(fechaRecibido!=""){
            fechaRecibido =fechaRecibido.format('d/m/Y');
        }

        var conn = new Ext.data.Connection();conn.request({
            url : 'CONTROL_FORMULARIOS?op=MODIFICAR_BOLETAS',
            method : 'POST',
            params : {

                //ID_FORM_CONTRIB : record.data.ID_FORM_CONTRIB,
                ID_TRANSACCION:record.data.ID_TRANSACCION,
                RECHAZADO : record.data.RECHAZADO,
                FECHA_RECIBIDO:fechaRecibido,
                FECHA_CONTROLADO:fechaControlado,
                ENVIADO:record.data.ENVIADO
            },
            success : function(action) {
                var  obj = Ext.util.JSON .decode(action.responseText);
                if(obj.success){
                    if (reload) {
                        Ext.getCmp('gridControlFormulariosBoletas').store.reload();
                    }
                }else{
                    Ext.Msg.show({
                        title : FAIL_TITULO_GENERAL,
                        msg :FAIL_CUERPO_GENERAL,
                        icon : Ext.MessageBox.ERROR,
                        buttons : Ext.Msg.OK

                    });
                    Ext.getCmp('gridControlFormulariosBoletas').store.reload();
                }
            },
            failure : function(action) {
                Ext.Msg.show({
                    title : FAIL_TITULO_GENERAL,
                    msg :FAIL_CUERPO_GENERAL,
                    icon : Ext.MessageBox.ERROR,
                    buttons : Ext.Msg.OK

                });
                Ext.getCmp('gridControlFormulariosBoletas').store.reload();
            }
        })
    }