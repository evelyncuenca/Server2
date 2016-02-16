/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function panelGrillaAyuda(){
    var service = new Ext.form.TextField({
        id: 'idServicioAyuda',       
        name : 'SERVICIO_AYUDA',
        enableKeyEvents:true,
        listeners : {
            'specialkey' : function(esteObjeto, esteEvento)   {
                if(esteEvento.getKey() == 13){
                    getServicesHelp();
                }
            }
        }
    });
    var panelGrillaAyuda = {
        id : 'panelAyuda',
        xtype : 'panel',
        title:'Ayuda',
        layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true,
        tbar : [ {
            iconCls : 'help',
            text : 'Ingrese el servicio a consultar:'
        },service,{
            text : 'Salir',
            iconCls:'logout',
            handler : function(){
                cardInicial();
            }
        }],
        items : [{
            xtype:'panel',
            frame: false,
            id:'panelGrillaAyuda',
            autoScroll : true            
        }]
    }
    return panelGrillaAyuda;
}
function getServicesHelp (){
    Ext.getCmp('panelGrillaAyuda').load(
    {
        url: 'ayuda.jsp',
        params: {
            SERVICIO_AYUDA: Ext.getCmp('idServicioAyuda').getRawValue()
        },
        discardUrl: false,
        nocache: false,
        text: 'Loading...',
        timeout: 30,
        scripts: false
    });
}


function getRemoteServiceHelp(idFacturador){
    var resultado = new Ext.ux.Plugin.RemoteComponent({
        url : 'detalleAyuda.jsp',
        params:{
            idFacturador:idFacturador
        }
    });
    return resultado;
}


function tabServices(idFacturador){    
//    var backendViewport = {
//        xtype:'panel',
//        layout: 'fit',
//        width: 625,
//        height: 448,
//        border: false,
//        tbar:[{
//            text:'Pagar',
//            iconCls:'pay',
//            handler:function(){
//                Ext.getCmp('idWinHelpService').close();
//                pagarServicio(3);
//            }
//        },{
//            text:'Salir',
//            iconCls:'logout',
//            handler:function(){
//                Ext.getCmp('idWinHelpService').close();      
//            }            
//        }],
//        items: [{
//            xtype: 'tabpanel', 
//            height: 450,
//            bodyStyle: 'padding:10px',
//            activeTab: 0,
//            enableTabScroll:true,
//            plain: false,
//            html:'<h1>Uste puede pagar este servicio de n maneras</h1>',
//            items: [{  
//                xtype: 'panel',  
//                title: 'Tab #3',  
//                layout: 'accordion',  
//                layoutConfig: {  
//                    //titleCollapse: false,  
//                    animate: false//,  
//                //activeOnTop: false  
//                },                
//                items: [{
//                        xtype: 'panel',  
//                        title: 'Usted'
//                },{  
//                    title: 'Subpanel 1',  
//                    html: 'Contenido subpanel 1'  
//                },{  
//                    title: 'Subpanel 2',  
//                    html: 'Contenido subpanel 2'  
//                },{  
//                    title: 'Subpanel 3',  
//                    html: 'Contenido subpanel 3'  
//                }]
//               
//                
//            },{  
//                xtype: 'panel',  
//                title: 'Tab #2',  
//                layout: 'accordion',  
//                layoutConfig: {  
//                    //titleCollapse: false,  
//                    animate: false//,  
//                //activeOnTop: false  
//                },                
//                items: [{  
//                    title: 'Subpanel 1',  
//                    html: 'Contenido subpanel 1'  
//                },{  
//                    title: 'Subpanel 2',  
//                    html: 'Contenido subpanel 2'  
//                },{  
//                    title: 'Subpanel 3',  
//                    html: 'Contenido subpanel 3'  
//                }]
//               
//                
//            }]
//        }]
//    }
    var win = new Ext.Window({
        closable: false,
        resizable: true,
        modal: false,
        border: true,
        plain: true,
        id:'idWinHelpService',
        closeAction: 'hide',
        title: 'Ayuda Servicios',
        width: 640,
        height: 480,	    
        items: [{
            plugins:[ getRemoteServiceHelp(idFacturador) ]
        }]
    });

    win.show();
}

function pagarServicio(modulo){
    Ext.getCmp('idWinHelpService').close();
    switch(modulo){
        case 1:
            cardCobroServiciosCodigoBarra();
            break;
        case 2:
            cardConsultaPago();
            break;
        case 3:
            cardVentaMinutos();
            break;
        case 4:
            cardCobroTarjetas();
            break;        
        case 5:
            cardDepositoExtraccion();
            break;        
    }
}
