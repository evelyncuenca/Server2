/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function descargarArchivoCobranza(){

    Ext.Msg.show({
        title : 'Estado',
        msg : '¿Está seguro que desea descargar su Archivo de Cobranza?',
        icon : Ext.MessageBox.INFO,
        buttons : Ext.Msg.OKCANCEL,
        fn:function(btn){
            if(btn =='ok'){
                Ext.destroy(Ext.get('downloadIframe'));
                Ext.DomHelper.append(document.body, {
                    tag : 'iframe',
                    id : 'downloadIframe',
                    frameBorder : 0,
                    width : 0,
                    height : 0,
                    css : 'display:yes;visibility:hidden;height:0px;',
                    src : 'reportes?op=DOWNLOAD_USER_FILE'
                });
            }
        }
    });
}
