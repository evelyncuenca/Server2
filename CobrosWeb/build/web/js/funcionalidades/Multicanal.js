function panelCobranzaMuticanal(){
    var panel = {
        id : 'panelCobranzasMulticanal',
        title:'Cobranzas Multicanal',
        xtype : 'panel',
        // layout : 'fit',
        border : false,
        height:500,
        autoScroll : true ,
        items : [cobranzasMulticanal()]
    }

    return panel;

}

function cobranzasMulticanal(){

    var st = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            method : 'post',
            url : 'COBRANZAS_MULTICANAL?op=LISTAR'
        }),
        reader : new Ext.data.JsonReader({
            root : 'COBRANZAS_MULTICANAL',
            totalProperty : 'TOTAL',
            id : 'ID_USUARIO_TERMINAL',
            fields : [ 'ID_USUARIO_TERMINAL','FRANJA_HORARIA_CAB','USUARIO','TERMINAL','USUARIO','LOGUEADO','RECAUDADOR','SUCURSAL']
        }),
        listeners : {
            'beforeload' : function(store, options) { }
        }
    }); var anchoDefaultColumnas= 10; var colModel = new Ext.grid.ColumnModel([{
        header:'USUARIO',
        width: anchoDefaultColumnas,
        dataIndex: 'USUARIO'
    }, {
        header:'TERMINAL',
        width: anchoDefaultColumnas,
        dataIndex: 'TERMINAL'
    },{
        header:'RECAUDADOR',
        width: anchoDefaultColumnas,
        dataIndex: 'RECAUDADOR'
    },{
        header:'SUCURSAL',
        width: anchoDefaultColumnas,
        dataIndex: 'SUCURSAL'
    },{
        header:'FRANJA HORARIA',
        width: anchoDefaultColumnas,
        dataIndex: 'FRANJA_HORARIA_CAB'
    },{
        header:'LOGUEADO',
        width: anchoDefaultColumnas,
        dataIndex: 'LOGUEADO'
    } ]);
    var gridCobranzasMulticanal = new Ext.grid.GridPanel({
        title:'Cobranzas Multicanal',
        id:'gridCobranzasMulticanal',
        store: st,
        cm: colModel,
        viewConfig: {
            forceFit:true
        },
        buttons: [{
            text : 'Pagar',
            handler : function(){
               
            }
        },{
            text : 'Imprimir',
            handler : function(){
               
            }
        },{
            text : 'Salir',
            handler : function(){               
                cardInicial();
            }
        }],
        tbar:['Buscar: ', ' ',new Ext.form.TextField({
            id:'idBotonBuscarCobrosMulticanal',
            fieldLabel : 'Numero Orden',
            name : 'numeroOrden'

        })

        ],
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : st,
            displayInfo : true,
            displayMsg : 'Mostrando {0} - {1} de {2}',
            emptyMsg : "No exiten datos que mostrar",
            items : ['-']
        }),
        frame:true,
        iconCls:'icon-grid',
        listeners : {
            'cellclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id;
            },
            'celldblclick' : function(esteObjeto, rowIndex, columnIndex, e) {
                if ( (Ext.getCmp("modificarUSUARIO_TERMINAL") == undefined)) {
                    idUSUARIO_TERMINALSeleccionadoID_USUARIO_TERMINAL = esteObjeto.getStore().getAt(rowIndex).id; pantallaModificarUSUARIO_TERMINAL().show();
                }
            }
        }
    });return gridCobranzasMulticanal;


}