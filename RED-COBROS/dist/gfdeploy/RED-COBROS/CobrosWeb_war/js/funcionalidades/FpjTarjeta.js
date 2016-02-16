/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function panelFPJTarjeta(){    
    var panelFPJTarjeta = {
        id : 'panelFPJTarjeta',
        xtype : 'panel',
        title:'Tarjeta FPJ ',
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
            items:[cabeceraFPJTarjeta()]
        }]
    }    
    return panelFPJTarjeta;    
}

function cabeceraFPJTarjeta(){
    var items = {
        xtype: 'form',
        id: 'idHeaderFPJTarjeta',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'numberfield',
            fieldLabel: 'Nro. de Cedula',            
            id :'idNroCIFPJTarjeta',
            allowBlank : false,
            width: '100',
            maxLength : 10,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idUltNrosTarjetaTarjeta').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Ultimos 4 Nros. de Tarjeta',            
            id :'idUltNrosTarjetaTarjeta',
            allowBlank : false,
            width: '100',
            maxLength : 4,
            minLength :4,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: { 
                'keyup' : function(esteObjeto, esteEvento)   {
                   esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    if(esteObjeto.getRawValue().length > 4){                        
                        esteObjeto.setRawValue(esteObjeto.getRawValue().substr(0, 4));
                    }                  
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarFPJTarjeta').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarFPJTarjeta',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:Ext.getCmp("idNroCIFPJTarjeta").getValue()+'-'+Ext.getCmp("idUltNrosTarjetaTarjeta").getValue(),
                        ID_FACTURADOR:40
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
                Ext.getCmp('idHeaderFPJTarjeta').getForm().reset();
                Ext.getCmp('idNroCIFPJTarjeta').focus(true,true);
            }
        }]
    } 
    return items;
}
