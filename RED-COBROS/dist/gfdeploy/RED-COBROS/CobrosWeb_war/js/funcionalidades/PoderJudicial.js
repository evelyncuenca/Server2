/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function panelPoderJudicial(){    
    var panelPoderJudicial = {
        id : 'panelPoderJudicial',
        xtype : 'panel',
        title:'Poder Judicial',
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
            items:[cabeceraLiquidacion()]
        },{
            xtype : 'panel',                
            items: [cabeceraAntecedente()]
        },poderJudicialBackground()]
    }    
    return panelPoderJudicial;    
}
function poderJudicialBackground(){
    var backgroundPanel = new Ext.form.FormPanel({
        bodyStyle:'background-image: url(\'images/waterMarkPoderJudicial.png\')',
        height : 300
    })
    return backgroundPanel;
}
function cabeceraLiquidacion(){
    var items = {
        xtype: 'form',
        title :'Liquidacion',
        id: 'idHeaderLiquidacionPoderJudicial',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'textfield',
            fieldLabel: 'Nro. de Liquidacion',            
            id :'idNroLiquidacion',
            allowBlank : false,
            width: '100',
            maxLength : 10,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarLiquidacion').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarLiquidacion',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                confirmFormPoderJudicial(78, Ext.getCmp('idNroLiquidacion').getValue());
            }
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idHeaderLiquidacionPoderJudicial').getForm().reset();
                Ext.getCmp('idNroLiquidacion').focus(true,true);
            }
        }]
    } 
    return items;
}

function cabeceraAntecedente(){
    var items = {
        xtype: 'form',
        title :'Antecedentes',
        id: 'idHeaderAntecedentePoderJudicial',
        monitorValid: true,
        height: 'auto',
        width: 'auto',
        labelAlign: 'top',
        frame: true,
        bodyStyle: ' padding:1px 1px 5px 5px',
        items :[{            
            fieldLabel: 'Nro. de CI',            
            id :'idCiAntecedente',
            allowBlank : false,
            xtype:'textfield',
            width: '100',
            listeners: {
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13  ){
                        Ext.getCmp('idBtnAceptarAntecedente').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarAntecedente',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                confirmFormPoderJudicial(79, Ext.getCmp('idCiAntecedente').getValue());
            }
        },{
            text: 'Cancelar',
            width:'130',
            handler: function(){
                Ext.getCmp('idHeaderAntecedentePoderJudicial').getForm().reset();
                Ext.getCmp('idCiAntecedente').focus(true,true);
            }
        }]
    }
    return items;
}


function confirmFormPoderJudicial(idServicio, refCon){
    var randomNumber = Math.floor((Math.random()*10000000)+1);
    Ext.Msg.wait('Procesando... Por Favor espere...');
    Ext.getCmp('panelConsulta').load({
        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
        params:{
            NRO_REFERENCIA : refCon,
            SERVICIO : idServicio,
            ID_FACTURADOR : 55
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