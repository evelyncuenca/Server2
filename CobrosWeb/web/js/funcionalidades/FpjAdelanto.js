/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function panelFPJAdelanto(){    
    var panelFPJAdelanto = {
        id : 'panelFPJAdelanto',
        xtype : 'panel',
        title:'Adelanto FPJ ',
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
            items:[cabeceraFPJAdelanto()]
        }]
    }    
    return panelFPJAdelanto;    
}

function cabeceraFPJAdelanto(){
    var items = {
        xtype: 'form',
        id: 'idHeaderFPJAdelanto',
        monitorValid: true,
        height: 'auto',
        width: 'auto',        
        frame: true,
        labelAlign: 'top',
        bodyStyle: 'padding:1px 1px 5px 5px;',
        items :[{
            xtype:'numberfield',
            fieldLabel: 'Nro. de Cedula',            
            id :'idNroCIFPJAdelanto',
            allowBlank : false,
            width: '100',
            maxLength : 10,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idUltNrosTarjetaAdelanto').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Ultimos 4 Nros. de Tarjeta',            
            id :'idUltNrosTarjetaAdelanto',
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
                        Ext.getCmp('idFechaVencFPJAdelanto').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Fecha Venc. Tarjeta',            
            id :'idFechaVencFPJAdelanto',
            allowBlank : false,            
            emptyText:'YYMM',
            width: '100', 
            maxLength : 4,
            minLength : 4,
            monitorValid: true,
            enableKeyEvents:true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    var yymm = esteObjeto.getRawValue();
                    if(yymm.length > 4){
                        yymm = yymm.substr(0, 4);
                        esteObjeto.setRawValue(yymm);
                    } 
                    if(yymm.length == 4){
                        var mm = yymm.substr(2, 4);
                        
                        if(parseInt(mm) > 12 || parseInt(mm) == 0){
                            alert("Mes incorrecto, rango entre[1-12]");                            
                            esteObjeto.setRawValue(yymm.substr(0, 2));
                        }
                    }
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idImporteFPJAdelanto').focus(true,true);
                    }
                }
            }
        },{
            xtype:'textfield',
            fieldLabel: 'Importe a retirar',            
            id :'idImporteFPJAdelanto',
            allowBlank : false,
            width: '100',
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {
                'keyup' : function(esteObjeto, esteEvento)   {
                    esteObjeto.setRawValue(replaceAllNoNumbers(esteObjeto.getRawValue()));
                    esteObjeto.setRawValue(addCommas(esteObjeto.getRawValue()));
                        
                },
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idCuotaFPJAdelanto').focus(true,true);
                    }
                }
            }
        },{
            xtype:'numberfield',
            fieldLabel: 'Cuotas',            
            id :'idCuotaFPJAdelanto',
            allowBlank : false,
            width: '20',
            maxLength : 2,
            minLength : 1,
            enableKeyEvents:true,
            monitorValid: true,
            listeners: {                                                
                'specialkey' : function(esteObjeto, esteEvento)   {
                    if(esteEvento.getKey() == 13 || esteEvento.getKey() == 9 ){
                        Ext.getCmp('idBtnAceptarFPJAdelanto').focus(true,true);
                    }
                }
            }
        }],
        buttonAlign:'left',
        buttons:[{
            text: 'Aceptar',
            id :'idBtnAceptarFPJAdelanto',
            width:'130',
            formBind : true,
            handler: function(){//llamada al autorizador                
                var randomNumber = Math.floor((Math.random()*10000000)+1);
                Ext.Msg.wait('Procesando... Por Favor espere...');
                Ext.getCmp('panelConsulta').load({
                    url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO_CONSULTA'+'&ID_RANDOM='+randomNumber,
                    params:{
                        NRO_REFERENCIA:Ext.getCmp("idNroCIFPJAdelanto").getValue()+'-'+Ext.getCmp("idUltNrosTarjetaAdelanto").getValue()+"#"+Ext.getCmp("idFechaVencFPJAdelanto").getValue()+"#"+Ext.getCmp("idCuotaFPJAdelanto").getValue()+"#"+Ext.getCmp("idImporteFPJAdelanto").getValue(),
                        ID_FACTURADOR:40,
                        SERVICIO:141
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
                Ext.getCmp('idHeaderFPJAdelanto').getForm().reset();
                Ext.getCmp('idNroCIFPJAdelanto').focus(true,true);
            }
        }]
    } 
    return items;
}
