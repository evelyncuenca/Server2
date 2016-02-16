function panelIced(){    
    var panelIced = {
        id : 'panelIced',
        xtype : 'panel',
        title:'ICED',
        //layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true,
        tbar : [ {
            text : 'Salir (Alt+q)',
            iconCls:'logout',
            handler : function(){
                cardInicial();
            }
        }],
        items : [{
            xtype : 'panel',            
            items:[cabeceraCurso()]
        },/*
        {
            xtype : 'panel',                
            items: [cabeceraCompra()]
        },*/
        IcedBoxBackground()]
    }    
    return panelIced;    
}

function cabeceraCurso(){
    var combocurso = getCombo('ICED','ICED','ICED','ICED','DESCRIPCION','DESCRIPCION','DESCRIPCION','DESCRIPCION','DESCRIPCION','DESCRIPCION');
    /* (suUrl,suRootJson,suIdJson,suNameJson,suDescripcion,suLabel,suHidden,suDisplayField,suValueField,suEmptyText)
*/
    combocurso.addListener( 'select',function(esteCombo, record,  index  ){
        var asdf=record.json;
        
        Ext.getCmp('idMontoIced').setValue(asdf.IMPORTE)        
        //Ext.getCmp('idMontoIced').focus(true, true);
    }, null,null ) ;
 
    /*  combocurso.addListener('change', function (esteCombo, record, index){
        console.log("valor record "+record);
        console.log("valor oldValue "+index);
    //Ext.getCmp('idMontoIced').setValue(record)
    }, null,null );*/
 
 
    var items = {
        title:'Cursos ICED',
        xtype: 'form',
        id: 'idHeaderCurso',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'textfield',
            fieldLabel: 'Nombre y Apellido',            
            id :'nombreClienteIced',
            allowBlank : false,
            width: '300',
            maxLength : 40,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('nroCedulaIced').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'Nro. de Cedula',            
            id :'nroCedulaIced',
            allowBlank : false,            
            width: '100', 
            maxLength : 10,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idMontoIced').focus(true,true);
                    }
                }
            }
        },combocurso,{
            xtype:'textfield',
            fieldLabel: 'Monto a pagar',            
            id :'idMontoIced',
            name: 'MONTO_CARGAR',
            allowBlank : false,
            width: '100',
            maxLength : 8,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                },                                           
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarCursoIced').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarCursoIced',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=ICED'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:Ext.getCmp("nroCedulaIced").getValue(),
                        NOMBRE:Ext.getCmp("nombreClienteIced").getValue(),
                        DESCRIPCION:combocurso.getValue(),
                        MONTO_CARGAR: Ext.getCmp("idMontoIced").getValue()
                    },
                    discardUrl: false,
                    nocache: true, 
                    text: "Cargando...",
                    timeout: TIME_OUT_AJAX,
                    scripts: true
                });
                        
                
                Ext.Msg.hide();
                Ext.getCmp('content-panel').layout.setActiveItem('panelConsulta');
            }
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idHeaderCurso').getForm().reset();
                Ext.getCmp('nombreClienteIced').focus(true,true);
            }
        }]
    } 
    return items;

}


function IcedBoxBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkIced.png\')',
        height : 260
    })
    return backgroundPanel;
}




