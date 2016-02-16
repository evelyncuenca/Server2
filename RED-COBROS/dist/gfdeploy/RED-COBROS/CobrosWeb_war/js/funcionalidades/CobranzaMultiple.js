/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var itemSeleccionadoCobranzaMultiple;
function panelCobroMultipleConCB() {
    var panel = {
        id : 'panelCobroMulipleConCB',
        xtype : 'panel',
        layout : 'fit',
        border : false,
        height:500,
        autoScroll : false,
        items : [cabeceraPagoMultipleConCB(),gridPagosMultiples(),gridTicketsMultiples()]
    }
    return panel;
}
function cabeceraPagoMultipleConCB(){
    var moreCB =  new Ext.form.Checkbox ({
        id:'idChecKMoreCBMul',
        fieldLabel: '2do. Codigo de Barras',
        vertical: true,
        columns: 1,
        name: 'MORE_CB',
        listeners : {
            'check' : function(esteObjeto, checked)   {
                Ext.getCmp('idCodigoBarraMul').focus(true,true);
                if(checked){
                    Ext.getCmp('idCodigoBarra2Mul').enable();
                }else{
                    Ext.getCmp('idCodigoBarra2Mul').disable();
                }
            }
        }
    });
    var codigosIngresados = new Ext.form.TextArea({
        fieldLabel:'Códigos de Barras Ingresados',        
        anchor :'95%',
        enableKeyEvents:true
    });
    var codigoBarraFormPanel = new Ext.form.FormPanel({
        labelWidth : 160,
        id:'idFormPanelCodigoDeBarrasMul',
        title:'Pago de Multiples Servicios',
        autoHeight: true,
        frame:true,
        bodyStyle: 'padding: 5px',
        monitorValid : true,
        items :[{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [moreCB,{
                    xtype:'textfield',
                    fieldLabel: 'Ingrese Cód. de Barra',
                    name: 'CODIGO_DE_BARRA',
                    id :'idCodigoBarraMul',
                    allowBlank : false,
                    width: '350',
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() ==9 ){
                                var barCode = clearAllNoNumbers(this.getValue());
                                Ext.getCmp('idAceptarBtnPagoMultiple').enable();
                                if(isDoubleBarcode(barCode)){
                                    Ext.getCmp('idChecKMoreCBMul').setValue(true);
                                    Ext.getCmp('idCodigoBarra2Mul').enable();
                                    Ext.getCmp('idCodigoBarra2Mul').focus(true,true);
                                }else{                                    
                                    codigosDeBarrasMultiples = codigosDeBarrasMultiples+barCode+"#";
                                    codigosIngresados.setValue(codigosDeBarrasMultiples.replace('#','').replace(/#/g,'\n'));
                                    this.reset();
                                    this.focus(true,true);
                                }
                            }
                        }
                    }
                },{
                    xtype:'textfield',
                    fieldLabel: 'Ingrese 2do. Cód. de Barra',
                    name: 'CODIGO_DE_BARRA_2',
                    id :'idCodigoBarra2Mul',
                    allowBlank : false,
                    disabled:true,
                    width: '350',
                    listeners: {
                        'specialkey' : function(esteObjeto, esteEvento)   {
                            if(esteEvento.getKey() == 13 || esteEvento.getKey() ==9){
                                codigosDeBarrasMultiples = codigosDeBarrasMultiples+clearAllNoNumbers(Ext.getCmp('idCodigoBarraMul').getValue())+clearAllNoNumbers(this.getValue())+"#";
                                codigosIngresados.setValue(codigosDeBarrasMultiples.replace('#','').replace(/#/g,'\n'));
                                Ext.getCmp('idCodigoBarraMul').reset();                                
                                this.reset();
                                this.disable();
                                Ext.getCmp('idChecKMoreCBMul').setValue(false);
                                Ext.getCmp('idCodigoBarraMul').focus(true,true);
                        
                            }
                        }
                    }
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                labelAlign:'top',
                items: [codigosIngresados]
            }]
        }]
    });
    return codigoBarraFormPanel;

}



function sendToServerMultiple(codigoDeBarras){   
    Ext.getCmp('idGridCobranzaMultiple').getGridEl().getStore().load({
        params: {
            CODIGO_DE_BARRA:codigoDeBarras,
            MULTIPLE: true
        }
    });
}
var codigosDeBarrasMultiples="#";
function gridTicketsMultiples(){
    var randomNumber = Math.floor((Math.random()*1000000)+1);
    var proxy = new Ext.data.HttpProxy({
        url: 'COBRO_SERVICIOS?op=PROCESAR_SERVICIO'+'&ID_RANDOM='+randomNumber
    });
    var reader = new Ext.data.JsonReader({
        root : 'FACTURAS',
        totalProperty : 'TOTAL',
        id : 'ID_TRANSACCION',
        fields : ['ID_TRANSACCION','DESCRIPCION','CORRECTO','TICKET_PRINTER','TICKET_SCREEN']
    });
    var writer = new Ext.data.JsonWriter();
    var store = new Ext.data.Store({
        id: 'idStoreFacturas',
        proxy: proxy,
        reader: reader,
        writer: writer,
        pruneModifiedRecords : false,
        listeners: {
            'update'  : function ( esteObjeto,  record, operation ) {
                return false;
            }
        }
    });
    var anchoDefault= 120;    
    var smFacturasGrid = new Ext.grid.CheckboxSelectionModel({
        singleSelect: true
    }); 
    var userColumns =  [smFacturasGrid,{
        header : "Id Transacción",
        width : anchoDefault,
        dataIndex : 'ID_TRANSACCION',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header : "Descripcion",
        width : 180,
        dataIndex : 'DESCRIPCION',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    }];
    var gridTicketCobranzaMultiple = new Ext.grid.EditorGridPanel({
        id:'idGridTicketMultiple',
        autoHeight: true,
        sm:smFacturasGrid,
        store: store,
        columns:userColumns,
        pruneModifiedRecords:true,
        viewConfig: {
            forceFit:false
        },        
        split: true,
        stripeRows: true,
        tbar:[{
            text : 'Ticket',
            iconCls:'ticket',
            id:'idBtnTicketMultiples',
            disabled: true,
            handler : function(){
                var win1 = new Ext.Window({
                    title:'Imprimir Tickets',
                    autoWidth : true,
                    height : 'auto',
                    closable : false,
                    resizable : false,
                    modal:true,
                    defaultButton:0,
                    buttonAlign:'center',
                    buttons : [{
                        id:'idBtnPrintTicketMul',
                        text : 'Imprimir Todos',
                        formBind : true,
                        handler : function() {
                            imprimirImpresoraExterna(getTicket());
                            Ext.getCmp('idGridCobranzaMultiple').getStore().removeAll();
                            store.removeAll();
                        }
                    },{
                        text : 'Cancelar',
                        handler : function() {
                            win1.close();
                        }
                    }]                        
                });
                win1.show();
            }        
        }],
        listeners: {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                var record =  esteObjeto.getStore().getAt(rowIndex);   
                if(record.data.CORRECTO=="S"){
                    var win1 = new Ext.Window({
                        title:'    T  I  C  K  E  T    ',
                        autoWidth : true,
                        height : 'auto',
                        closable : false,
                        resizable : false,
                        modal:true,
                        defaultButton:0,
                        buttonAlign:'center',
                        buttons : [{
                            id:'idBtnPrintTicketMul',
                            text : 'Imprimir Ticket',
                            formBind : true,
                            handler : function() {
                                imprimirImpresoraExterna(record.data.TICKET_PRINTER);
//                                esteObjeto.getStore().getAt(rowIndex).remove();
//                                Ext.getCmp('idGridCobranzaMultiple').getStore().getAt(rowIndex).remove();
                            }
                        },{
                            text : 'ok',
                            handler : function() {
                                win1.close();
                            }
                        }],
                        items : [{
                            html:'<H1>'+record.data.TICKET_SCREEN+'</H1>'
                        }]
                    });
                    win1.show();
                    return true;
                }else{
                    return false;
                }
            }
        }
    });
    return gridTicketCobranzaMultiple;    
}
function gridPagosMultiples(){
    var proxy = new Ext.data.HttpProxy({
        url: 'COBRO_SERVICIOS?op=RESOLVER_SERVICIO'
    });
    var reader = new Ext.data.JsonReader({
        root : 'FACTURAS',
        totalProperty : 'TOTAL',
        id : 'ID_TRANSACCION',
        fields : ['ID_TRANSACCION','SERVICIO','NRO_REFERENCIA','MONTO','DESCRIPCION','CODIGO_DE_BARRA','CORRECTO','FORMA_PAGO','ENTIDAD_FINANCIERA','NRO_CHEQUE','ID_SERVICIO']
    });
    var writer = new Ext.data.JsonWriter();
    var store = new Ext.data.Store({
        id: 'idStoreFacturas',
        proxy: proxy,
        reader: reader,
        writer: writer,
        pruneModifiedRecords : false,
        listeners: {
            'update'  : function ( esteObjeto,  record, operation ) {
                return false;
            }
        }
    });

    var anchoDefault= 120;
    var smFacturasGrid = new Ext.grid.CheckboxSelectionModel({
        singleSelect: false
    });    
    smFacturasGrid.addListener( 'rowselect',function(selModel, rowIndex, record){ 
        var sum = parseFloat(Ext.getCmp('idMontoPagarMultiples').getValue().replace(/,/g,''))+ parseFloat(record.data.MONTO);
        Ext.getCmp('idMontoPagarMultiples').setValue(addCommas(String(sum)));
    }, null,null ) ; 
    
    var comboFormaDePago=  new Ext.form.ComboBox({
        hiddenName : 'TIPO',
        valueField : 'TIPO',
        anchor:'95%',
        triggerAction: 'all',
        displayField: 'descripcion',
        mode: 'local',
        value:'EFECTIVO',
        store: new Ext.data.SimpleStore({
            fields: ['TIPO', 'descripcion'],
            data : [['EFECTIVO', 'EFECTIVO'],
            ['CHEQUE_CONTINENTAL', 'CHEQUE CONTINENTAL'],
            ['CHEQUE_OTROS','CHEQUE OTROS']]
        })
    });

    var comboENTIDAD_FINANCIERA = getCombo('ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','Bancos','ENTIDAD_FINANCIERA','DESCRIPCION_ENTIDAD_FINANCIERA','ENTIDAD_FINANCIERA','Bancos');
    //    comboENTIDAD_FINANCIERA.addListener( 'select',function(esteCombo, record,  index  ){
    //        Ext.getCmp('idNumeroChequeBoletaPago').focus(true,true);
    //    }, null,null ) ;    
    var userColumns =  [
    smFacturasGrid.valueOf(),
    {
        header : "Id",
        width : anchoDefault,
        dataIndex : 'ID_TRANSACCION',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header : "Codigo de Barra",
        width : anchoDefault,
        dataIndex : 'CODIGO_DE_BARRA',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }

    },{
        header : "Servicio",
        width : anchoDefault,
        dataIndex : 'SERVICIO',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header : "Nro. Referencia",
        width : anchoDefault,
        dataIndex : 'NRO_REFERENCIA',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header : "Monto",
        width : anchoDefault,
        dataIndex : 'MONTO',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return addCommas(new String(value));
        }
    },{
        header : "Descripcion",
        width : anchoDefault,
        dataIndex : 'DESCRIPCION',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header : "Forma de Pago",
        width : anchoDefault,
        dataIndex : 'FORMA_PAGO',
        editor: comboFormaDePago,
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }      
    },{
        header: "Banco",
        width : anchoDefault,
        dataIndex : 'ENTIDAD_FINANCIERA',
        editor:comboENTIDAD_FINANCIERA,
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        header: "NroCheque",
        width : anchoDefault,
        dataIndex : 'NRO_CHEQUE',
        editor: {
            xtype:'textfield'
        },
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    },{
        hidden: true,
        dataIndex : 'ID_SERVICIO',
        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
            if (record.data.CORRECTO=='S') {
                metaData.css = 'change-row-correct';
            }else{
                metaData.css = 'change-row-fail';
            }
            return value;
        }
    }];
    var gridControlCobranzaMultiple = new Ext.grid.EditorGridPanel({
        id:'idGridCobranzaMultiple',
        autoHeight: true,
        sm:smFacturasGrid,
        store: store,
        columns:userColumns,
        pruneModifiedRecords:true,
        viewConfig: {
            forceFit:false
        },        
        split: true,
        stripeRows: true,
        clicksToEdit: 1,
        tbar:[{
            disabled:true,
            text:'Aceptar',
            iconCls:'accept',            
            id:'idAceptarBtnPagoMultiple',
            handler : function(){  
                this.disable();
                var myMask = new Ext.LoadMask(Ext.getBody(), {
                    msg:"Procesando Codigos de Barra..Favor aguarde un momento.."
                });
                myMask.show();
                store.load({
                    params: {
                        CODIGO_DE_BARRA:codigosDeBarrasMultiples,
                        MULTIPLE: true
                    },
                    callback : function(action) {
                        myMask.hide();
                        codigosDeBarrasMultiples="#";
                        Ext.getCmp('idBtnPagarMultiples').enable();
                    //store.suspendEvents(true);
                    }
                });
                Ext.getCmp('idCodigoBarraMul').reset();
                Ext.getCmp('idCodigoBarra2Mul').reset();
                Ext.getCmp('idCodigoBarraMul').focus(true,true);
            }                
        },{ 
            id:'idCancelBtnPagoMultiple',
            text:'Cancelar',
            iconCls:'cancel',
            handler:function(){
                Ext.Msg.show({
                    title : "Aviso",
                    msg : "Está seguro que desea cancelar?",
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn, text) {
                        if(btn=='ok'){
                            //store.suspendEvents(false);
                            Ext.getCmp('idGridTicketMultiple').getStore().removeAll();
                            store.removeAll();                            
                            codigosDeBarrasMultiples ="#";
                            Ext.getCmp('idFormPanelCodigoDeBarrasMul').getForm().reset();
                            Ext.getCmp('idCodigoBarraMul').focus(true,true);
                            Ext.getCmp('idAceptarBtnPagoMultiple').disable();    
                            Ext.getCmp('idBtnPagarMultiples').disable();
                            Ext.getCmp('idMontoPagarMultiples').reset();
                        }else if(btn=='cancel'){
                            close();
                        }
                    }
                });                
            }
        },{
            text : 'Salir (Alt+q)',
            iconCls:'logout',
            id:'idBtnSalirMultiple',
            disabled:false,
            handler : function(){
                if(codigosDeBarrasMultiples!='#'){
                    Ext.Msg.show({
                        title : "Aviso",
                        msg : "Está seguro que desea salir?",
                        buttons : Ext.Msg.OKCANCEL,
                        icon : Ext.MessageBox.QUESTION,
                        fn : function(btn, text) {
                            if(btn=='ok'){
                                Ext.getCmp('idGridTicketMultiple').getStore().removeAll();
                                store.removeAll();
                                codigosDeBarrasMultiples ="#";
                                Ext.getCmp('idAceptarBtnPagoMultiple').disable();
                                Ext.getCmp('idBtnPagarMultiples').disable();
                                Ext.getCmp('idBtnTicketMultiples').disable();
                                cardInicial();
                            }else if(btn=='cancel'){
                                close();
                            }
                        }
                    });
                }else{
                    cardInicial();
                }
            }
        },{
            text : 'Pagar',
            iconCls:'pay',
            id:'idBtnPagarMultiples',
            disabled: true,
            handler : function(){
                if(checkBeforeSend()){
                    if(isRegisterToPay()){
                        Ext.Msg.show({
                            title : "Aviso",
                            msg : "Está seguro de pagar los siguientes registros?</br>"+getRegisterToPay()+"Con un monto total de: "+Ext.getCmp('idMontoPagarMultiples').getValue(),
                            buttons : Ext.Msg.OKCANCEL,
                            icon : Ext.MessageBox.QUESTION,
                            fn : function(btn, text) {
                                if(btn=='ok'){
                                    Ext.getCmp('idMontoPagarMultiples').reset();
                                    Ext.getCmp('idBtnPagarMultiples').disable();
                                    var myMask = new Ext.LoadMask(Ext.getBody(), {
                                        msg:"Procesando Pago..Favor aguarde un momento.."
                                    });
                                    myMask.show();                                                                                                            
                                    Ext.getCmp('idGridTicketMultiple').getStore().load({
                                        params: {
                                            ID_TRANSACCION:sendRegisterToPay(0),
                                            TIPO_DE_PAGO:sendRegisterToPay(1),
                                            ID_ENTIDAD:sendRegisterToPay(2),
                                            NRO_DE_CHEQUE:sendRegisterToPay(3),
                                            SERVICIO: sendRegisterToPay(4),
                                            MONTO_CARGAR: sendRegisterToPay(5),
                                            MULTIPLE: true
                                        },
                                        callback : function(action) {
                                            myMask.hide();
                                            Ext.getCmp('idBtnTicketMultiples').enable();                                            
                                        }
                                    });
                                    //this.disable();
                                }else if(btn=='cancel'){
                                    close();
                                }
                            }
                        });
                    }else{
                        alert("No hay registros seleccionados para pagar");
                    }
                }
            }
        },{
            text : 'Total a Pagar:'
        },{
            xtype:'textfield',
            id:'idMontoPagarMultiples',
            disabled:true
        }],
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {               
                calcMontoPagar();
            },
            'afteredit':{
                fn : function(e){
                    return false;
                }
            }
        }
    });
    gridControlCobranzaMultiple.getSelectionModel().addListener(  'beforerowselect',
        function(  selModel, rowIndex, keepExisting, record)	{
            calcMontoPagar();
            if(record.data.CORRECTO=="N"){
                return false;
            }else{
                return true;
            }
        }, this);
    return gridControlCobranzaMultiple;    
}

function clearAllNoNumbers(sValue){
    sValue=  sValue.replace( /[^,0-9]/g, "");
    return sValue;

}
function isDoubleBarcode(cb){
    if((cb.substring(0,5)=='20065' ||cb.substring(0, 5)=='20064' || cb.substring(0,5)=='10041') && cb.length ==26){
        return true;
    }else{
        return false;
    }    
}

function isRegisterToPay(){
    var selectionsGrid = Ext.getCmp('idGridCobranzaMultiple').getSelectionModel().getSelections();
    for (i = 0; i < selectionsGrid.length; i ++)  {
        if(selectionsGrid[i].data.CORRECTO=='S'){
            return true;
        }
    }
    return false;    
}
function getTicket(){
    var storeGrid = Ext.getCmp('idGridTicketMultiple').getStore();
    var ticket='';
    for (i = 0; i < storeGrid.getCount(); i ++)  {        
        if(storeGrid.getAt(i).data.CORRECTO=='S'){            
            ticket += storeGrid.getAt(i).data.TICKET_PRINTER;
        }
    }
    return ticket;    
}
function calcMontoPagar(){
    var selectionsGrid = Ext.getCmp('idGridCobranzaMultiple').getSelectionModel().getSelections();
    var sum=0;
    for (i = 0; i < selectionsGrid.length; i ++)  {
        if(selectionsGrid[i].data.CORRECTO=='S'){
            sum += parseFloat(selectionsGrid[i].data.MONTO);
        }
    }
    Ext.getCmp('idMontoPagarMultiples').setValue(addCommas(String(sum)));    
}
function getRegisterToPay(){
    var selectionsGrid = Ext.getCmp('idGridCobranzaMultiple').getSelectionModel().getSelections();
    var register="";
    for (i = 0; i < selectionsGrid.length; i ++)  {
        if(selectionsGrid[i].data.CORRECTO=='S'){
            register += selectionsGrid[i].data.ID_TRANSACCION+'</br>'
        }
    }
    return register;
}
function checkBeforeSend(){
    var selectionsGrid = Ext.getCmp('idGridCobranzaMultiple').getSelectionModel().getSelections();
    var isCorrect=true;
    var mensaje = "Favor ingresar: \n";
    for (i = 0; i < selectionsGrid.length; i ++)  {
        if(selectionsGrid[i].data.CORRECTO=='S'){
            if(selectionsGrid[i].data.FORMA_PAGO=="CHEQUE_OTROS" ) {
                if(selectionsGrid[i].data.ENTIDAD_FINANCIERA==""){
                    mensaje+="Entidad Financiera\n";
                    isCorrect = false;                
                }
                if(selectionsGrid[i].data.NRO_CHEQUE==""){
                    mensaje+="Nro. de Cheque\n";                    
                    isCorrect = false;
                }
            }else if(selectionsGrid[i].data.FORMA_PAGO=="CHEQUE_CONTINENTAL"){
                if(selectionsGrid[i].data.NRO_CHEQUE==""){
                    mensaje+="Nro. de Cheque\n";    
                    isCorrect = false;                    
                }                
            }
            if(!isCorrect){
                alert(mensaje+" Del siguiente registro ["+selectionsGrid[i].data.ID_TRANSACCION+"]\n");
            }
            mensaje = "Favor ingresar: ";
        }
    }
    return isCorrect;
}
function sendRegisterToPay(index){
    var selectionsGrid = Ext.getCmp('idGridCobranzaMultiple').getSelectionModel().getSelections();
    var ids="#";    
    for (i = 0; i < selectionsGrid.length; i ++)  {
        if(selectionsGrid[i].data.CORRECTO=='S'){
            if(index==0){
                ids+=selectionsGrid[i].data.ID_TRANSACCION+"#";
            }else if(index==1){
                ids+=selectionsGrid[i].data.FORMA_PAGO+"#";
            }else if(index==2){
                ids+=(selectionsGrid[i].data.ENTIDAD_FINANCIERA!=""?selectionsGrid[i].data.ENTIDAD_FINANCIERA:"-1")+"#";
            }else if(index==3){
                ids+=(selectionsGrid[i].data.NRO_CHEQUE!=""?selectionsGrid[i].data.NRO_CHEQUE:"-1")+"#";
            }else if(index==4){
                ids+=selectionsGrid[i].data.ID_SERVICIO+"#";
            }else if(index==5){
                ids+=selectionsGrid[i].data.MONTO+"#";
            }
        }
    }
    return ids;
}