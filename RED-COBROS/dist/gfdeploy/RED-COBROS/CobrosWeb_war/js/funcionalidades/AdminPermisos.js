var treeSeleccionado = 'nada';

function administrarPermisos(){

    var tree1 =  getTreePanelAdminUsuarios('tree1','Usuarios - Roles','USUARIOS_ROLES?op=LISTAR', 'Usuarios - Roles', 'USUARIOS_ROLES');
    var tree2 =  getTreePanelAdminUsuarios('tree2','Permisos - Roles','PERMISOS_ROLES?op=LISTAR', 'Permisos - Roles', 'PERMISOS_ROLES');
    var tree3 =  getTreePanelAdminUsuarios('tree3','Roles','ROLES?op=LISTAR', 'Roles', 'ROLES');
    var tree4 =  getTreePanelAdminUsuarios('tree4','Permisos','PERMISOS?op=LISTAR', 'Permisos', 'PERMISOS');
    var win = new Ext.Window({
        title:' Administración de Permisos',
        id: 'idAdminPermisos',
        resizable : true,
        width : 400,
        height : 400,
        layout : 'accordion',
        border : false,
        layoutConfig : {
            animate : false
        },
        modal:true,
        tbar : [{
            text:'Agregar',
            tooltip : TOOL_TIP_AGREGAR,
            iconCls : 'add2',
            scope : this,
            handler : function() {

                if (treeSeleccionado == 'nada') {
                    treeSeleccionado = tree1;
                }
                if (treeSeleccionado.id == tree1.id) {

                    pantallaAgregarRolesUsuario(tree1).show();

                } else if (treeSeleccionado.id == tree2.id) {
                    pantallaPermisosRoles(tree2).show();
                } else if (treeSeleccionado.id == tree3.id) {
                    pantallaAgregarRoles(tree3).show();
                }
            }
        }, ' ', {
            text:'Quitar',
            tooltip : TOOL_TIP_QUITAR,
            iconCls : 'remove',
            handler : function() {
                if (treeSeleccionado == 'nada') {
                    treeSeleccionado = tree1;
                }
                var elementoBorrar = treeSeleccionado.selModel.selNode;
                if (treeSeleccionado.id == tree1.id && elementoBorrar != null) {
                    Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        function(obj) {
                            if (obj == 'yes') {
                                borrarElementoAccordionAdminSys('USUARIOS_ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                            }
                        });
                } else if (treeSeleccionado.id == tree2.id  && elementoBorrar != null) {

                    Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        function(obj) {
                            if (obj == 'yes') {
                                borrarElementoAccordionAdminSys('PERMISOS_ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                            }
                        }

                        )

                } else if (treeSeleccionado.id == tree3.id && elementoBorrar != null) {

                    Ext.MessageBox.confirm(TITULO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,CUERPO_CONFIRMACION_ELIMINACION_REGISTRO_GRID,
                        function(obj) {
                            if (obj == 'yes') {
                                borrarElementoAccordionAdminSys('ROLES?OP=BORRAR', elementoBorrar, treeSeleccionado);
                            }
                        })
                }
            }
        }],
        items : [tree1,tree2, tree3, tree4]
    });
    tree3.on('dblclick', function(esteNodo, esteEvento) {
        pantallaModificarRoles(tree3,esteNodo.id).show();
    });

    tree1.on('beforeexpand', function() {
        treeSeleccionado = tree1;
        tree1.root.reload();
    });

    tree2.on('beforeexpand', function() {
        treeSeleccionado = tree2;
        tree2.root.reload();
    });
    tree3.on('beforeexpand', function() {
        treeSeleccionado = tree3;
        tree3.root.reload();
    });
    tree4.on('beforeexpand', function() {

        treeSeleccionado = tree4;
        tree4.root.reload();
    });
    win.show();

}
function pantallaPermisosRoles(tree){
    var comboPERMISOS =getCombo('PERMISOS','PERMISOS','PERMISOS','PERMISOS','DESCRIPCION_PERMISOS','PERMISOS','PERMISOS','DESCRIPCION_PERMISOS','PERMISOS','PERMISOS...');
    var comboROLES =getCombo('ROLES','ROLES','ROLES','ROLES','DESCRIPCION_ROLES','ROLES','ROLES','DESCRIPCION_ROLES','ROLES','ROLES...');
    var comboAPLICACIONES =getCombo('APLICACIONES','APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES...');

    comboAPLICACIONES.addListener( 'select',function(esteCombo, record,  index  ){

        comboROLES.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboROLES.store.reload();
        comboPERMISOS.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboPERMISOS.store.reload();
    }, null,null ) ;
    var formPanel = new Ext.FormPanel({

        labelWidth : 110,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ comboAPLICACIONES,comboROLES, comboPERMISOS],
        buttons : [{

            text : 'Agregar',
            formBind : true,
            handler : function() {
                formPanel.getForm().submit({
                    method : 'POST',
                    waitTitle : WAIT_TITLE_AGREGANDO,
                    waitMsg : WAIT_MSG_AGREGANDO,
                    url : 'PERMISOS_ROLES?op=AGREGAR',
                    timeout : TIME_OUT_AJAX,
                    success : function(form, accion) {
                        win.close();
                        tree.root.reload();
                    },
                    failure : function(form, action) {
                        var obj = Ext.util.JSON.decode(action.response.responseText);
                        try{
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : obj.motivo,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });
                        }
                        catch(er){
                            Ext.Msg.show({
                                title : FAIL_TITULO_GENERAL,
                                msg : FAIL_CUERPO_GENERAL,
                                buttons : Ext.Msg.OK,
                                icon : Ext.MessageBox.ERROR
                            });

                        }

                    }
                });
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                win.close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar Permisos a Roles',
        id : 'idAgregarPermisosRoles',
        width : 300,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [formPanel]
    });return win;

}
function pantallaAgregarRolesUsuario(tree){
    var comboUSUARIO =getCombo('CONF_USUARIO','CONF_USUARIO','CONF_USUARIO','CONF_USUARIO','DESCRIPCION_USUARIO','USUARIO','CONF_USUARIO','DESCRIPCION_USUARIO','CONF_USUARIO','USUARIOS...');
    var comboROLES =getCombo('ROLES','ROLES','ROLES','ROLES','DESCRIPCION_ROLES','ROLES','ROLES','DESCRIPCION_ROLES','ROLES','ROLES...');
    var comboAPLICACIONES =getCombo('APLICACIONES','APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES...');

    comboAPLICACIONES.addListener( 'select',function(esteCombo, record,  index  ){

        comboROLES.store.baseParams['APLICACION'] = record.data.APLICACIONES;
        comboROLES.store.reload();

    }, null,null ) ;
    var formPanel = new Ext.FormPanel({

        labelWidth : 110,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ comboAPLICACIONES,comboROLES, comboUSUARIO],
        buttons : [{

            text : 'Agregar',
            formBind : true,
            handler : function() {
                formPanel.getForm().submit({
                    method : 'POST',
                    waitTitle : WAIT_TITLE_AGREGANDO,
                    waitMsg : WAIT_MSG_AGREGANDO,
                    url : 'USUARIOS_ROLES?op=AGREGAR',
                    timeout : TIME_OUT_AJAX,
                    success : function(form, accion) {
                        win.close();
                        tree.root.reload();
                    },
                    failure : function(form, action) {
                        Ext.Msg.show({
                            title : FAIL_TITULO_GENERAL,
                            msg : FAIL_CUERPO_GENERAL,
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });
                    }
                });
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                win.close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar Usuarios a Roles',
        id : 'idAgregarUsuariosRoles',
        width : 300,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [formPanel]
    });return win;

}
function pantallaAgregarRoles(tree){
    var comboAPLICACIONES =getCombo('APLICACIONES','APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES...');

    var formPanel = new Ext.FormPanel({

        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [comboAPLICACIONES,{
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false
        } ],
        buttons : [{

            text : 'Agregar',
            formBind : true,
            handler : function() {
                formPanel.getForm().submit({
                    method : 'POST',
                    waitTitle : WAIT_TITLE_AGREGANDO,
                    waitMsg : WAIT_MSG_AGREGANDO,
                    url : 'ROLES?op=AGREGAR',
                    timeout : TIME_OUT_AJAX,
                    success : function(form, accion) {
                        win.close();
                        tree.root.reload();
                    },
                    failure : function(form, action) {
                        Ext.Msg.show({
                            title : FAIL_TITULO_GENERAL,
                            msg : FAIL_CUERPO_GENERAL,
                            buttons : Ext.Msg.OK,
                            icon : Ext.MessageBox.ERROR
                        });
                    }
                });
            }
        }, {
            text : 'Cancelar',
            handler : function() {
                win.close();
            }
        }]
    });
    var win = new Ext.Window({
        title:'Agregar ROL',
        id : 'idAgregarRol',
        width : 300,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [formPanel]
    });return win;

}
function pantallaModificarRoles(tree,elIdRol){
    var comboAPLICACIONES =getCombo('APLICACIONES','APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES','DESCRIPCION_APLICACIONES','APLICACIONES','APLICACIONES...');
    comboAPLICACIONES.store.load({
        params : {
            start : 0,
            limit : 25
        }
    });
    var formPanel = new Ext.FormPanel({

        labelWidth : 100,
        frame : true,
        autoWidth : true,
        defaultType : 'textfield',
        monitorValid : true,
        items : [ comboAPLICACIONES,{

            name:'ID_ROL',
            inputType: 'hidden'
        },{
            fieldLabel:'DESCRIPCIÓN',
            name:'DESCRIPCION',
            allowBlank : false
        } ],
        buttons : [{

            text : 'Modificar',
            formBind : true,
            handler : function() {

                Ext.Msg.show({
                    title : TITULO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                    msg : CUERPO_CONFIRMACION_MODIFICACION_REGISTRO_GRID,
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.MessageBox.WARNING,
                    fn : function(btn, text) {
                        if (btn == "yes") {
                            formPanel.getForm().submit({
                                method : 'POST',
                                waitTitle : WAIT_TITLE_MODIFICANDO,
                                waitMsg : WAIT_MSG_MODIFICANDO,
                                url : 'ROLES?op=MODIFICAR',
                                timeout : TIME_OUT_AJAX,
                                success : function(form, accion) {


                                    tree.root.reload();
                                    win.close();

                                },
                                failure : function(form, action) {
                                    Ext.Msg.show({
                                        title : FAIL_TITULO_GENERAL,
                                        msg : FAIL_CUERPO_GENERAL,
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.MessageBox.ERROR
                                    });
                                }
                            });
                        }
                    }
                });

            }
        }, {
            text : 'Cancelar',
            handler : function() {
                win.close();
            }
        }]
    });formPanel.getForm().load({
        url : 'ROLES?op=DETALLE',
        method : 'POST',
        params:{
            ID_ROL: elIdRol
        },
        waitMsg : 'Cargando...'
    });var win = new Ext.Window({
        title:'Modificar Rol',
        id : 'modificarROL',
        autoWidth : true,
        height : 'auto',
        closable : false,
        resizable : false,
        items : [formPanel]
    });return win;

}

function borrarElementoAccordionAdminSys(suUrl, suElementoABorrar, treeSeleccionado){
    var conn = new Ext.data.Connection();
    conn.request({

        url : suUrl,
        params:{

            op:'BORRAR',
            elementoABorrar: suElementoABorrar.id
        },
        method : 'POST',
        success : function(respuesta) {
            var obj = Ext.util.JSON.decode(respuesta.responseText);
            if (obj.success) {

                treeSeleccionado.root.reload();
            } else {
                Ext.Msg.show({
                    title : FAIL_TITULO_GENERAL,
                    msg : FAIL_CUERPO_GENERAL,
                    buttons : Ext.Msg.OK,
                    icon : Ext.MessageBox.ERROR
                });

            }
        },
        failure : function(respuesta) {

            var obj = Ext.util.JSON.decode(respuesta.responseText);

            Ext.Msg.show({
                title : FAIL_TITULO_GENERAL,
                msg : FAIL_CUERPO_GENERAL,
                buttons : Ext.Msg.OK,
                icon : Ext.MessageBox.ERROR
            });
        }
    });

}

function getWindows(elPanel){

    var  win = new Ext.createWindow({
        id : 'formCrearRoles-win',
        title : 'Crear Roles',
        width : 350,
        height : 350,
        iconCls : 'icon-grid',
        shim : false,
        animCollapse : false,
        constrainHeader : true,
        layout : 'fit',
        items : elPanel


    });

    return win;

}

function getTreePanelAdminUsuarios(suId, suTitle, suDataUrl, suTextAsyncTreeNode, suIdAsyncTreeNode){

    var tree = new Ext.tree.TreePanel({

        title : suTitle,
        loader : new Ext.tree.TreeLoader({

            dataUrl : suDataUrl
        }),
        rootVisible : false,
        lines : false,
        autoScroll : true,
        tools : [{
            id : 'refresh',
            on : {
                click : function() {

                    tree.body.mask('Cargando...', 'x-mask-loading');
                    tree.root.reload();
                    tree.root.collapse(true, false);
                    setTimeout(function() {
                        tree.body.unmask();
                        tree.root.expand(true, true);
                    }, 1000);
                }
            }
        }],
        root : new Ext.tree.AsyncTreeNode({
            text : suTextAsyncTreeNode,
            draggable : false,
            id : suIdAsyncTreeNode
        })
    });

    return tree;


}