<%-- 
    Document   : validadorDetalleFormularios
    Created on : 09-feb-2010, 14:51:48
    Author     : konecta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import ="java.util.*, py.com.konecta.redcobros.cobrosweb.utiles.Utiles"%>


<%HashMap<String, HashMap<String, String>> task = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("TASKS");%>
<script type="text/javascript">
   <%if (task != null && !task.isEmpty()) {%>
    function validar(cadena) {

        cadena = replaceAllNoNumbers(cadena);
        cadena = addCommas( cadena );
        return cadena;
    }

    function miFuncion(val){
        if (val!='todos') {
            var campoIntroducido=document.getElementById(val);
            var esNumerico=validar(campoIntroducido.value);
            campoIntroducido.value=validar(campoIntroducido.value);

        }

        var numeroFormulario=document.getElementById('numeroFormulario');
        var numeroImpuesto=document.getElementById('numeroImpuesto');

        if(numeroFormulario.value != 90){
            var original=document.getElementById('decJuradaOrig');
            var rectificativa=document.getElementById('decJuradaRectif');
            var clausuracese=document.getElementById('decJuradaClausuraCese');
            var periodoFiscal = document.getElementById('periodoFiscal');
            var diasatrasos = document.getElementById('numeroDiasAtraso');
            var mesesatrasos = document.getElementById('numeroMesesAtraso');

        }

        //var diasatrasos = document.getElementById('numeroDiasAtraso');
        var version = document.getElementById('version');
        //var mesesatrasos = document.getElementById('numeroMesesAtraso');
        var minimo = document.getElementById('minimoCampo');
        var maximo = document.getElementById('maximoCampo');
       
        var parametros='formulario='+numeroFormulario.value+
            '&validar='+val+
            '&impuesto='+numeroImpuesto.value+
            '&version='+version.value+
            '&minimo='+minimo.value+
            '&maximo='+maximo.value;
        //'&mesesatrasos='+mesesatrasos.value+
        //'&diasatrasos='+diasatrasos.value+
        //'&decjuradaorig='+original.value+
        //'&decjuradarectif='+rectificativa.value+
        //'&decjuradaclausuracese='+clausuracese.value+
        //'&periodoFiscal='+periodoFiscal.value+
            

        if(numeroFormulario.value == 90){

            var numeroRuc=document.getElementById('ruc');
            var campoActual=document.getElementById('C4');

            parametros=parametros+'&ruc='+numeroRuc.value;

            
            if (campoActual!=null && campoActual.value!='') {

                valorCampo=deleteCommas(campoActual.value);
                parametros=parametros+'&C4='+valorCampo;
            }
            for (var i=10; i<=14; i++){
                campoActual=document.getElementById('C'+i);
                if (campoActual!=null && campoActual.value!='') {

                    valorCampo=deleteCommas(campoActual.value);
                    parametros=parametros+'&C'+i+'='+valorCampo;
                }
            }
        }
        else{

            parametros=parametros+'&mesesatrasos='+mesesatrasos.value+
                '&diasatrasos='+diasatrasos.value+
                '&decjuradaorig='+original.value+
                '&decjuradarectif='+rectificativa.value+
                '&decjuradaclausuracese='+clausuracese.value+
                '&periodoFiscal='+periodoFiscal.value;

            var fecha_actual = new Date();
            dia_mes = fecha_actual.getDate();

            mes = fecha_actual.getMonth() + 1;
            anio = fecha_actual.getFullYear();

            hora = fecha_actual.getHours();

            minuto = fecha_actual.getMinutes();
            segundo = fecha_actual.getSeconds();

            var cadenafecha=''+anio+mes+ dia_mes+hora+minuto+segundo;
            parametros=parametros+'&fechahora='+cadenafecha;

        }
        

        
        for (var i=parseInt(minimo.value);i<=parseInt(maximo.value);i++) {
            var campoActual=document.getElementById('C'+i);
            if (campoActual!=null && campoActual.value!='') {

                valorCampo=deleteCommas(campoActual.value);
                parametros=parametros+'&C'+i+'='+valorCampo;
            }
        }

        //var fecha_actual = new Date();
        //dia_mes = fecha_actual.getDate();

        //mes = fecha_actual.getMonth() + 1;
        //anio = fecha_actual.getFullYear();

        //hora = fecha_actual.getHours();

        //minuto = fecha_actual.getMinutes();
        //segundo = fecha_actual.getSeconds();

        //var cadenafecha=''+anio+mes+ dia_mes+hora+minuto+segundo;
        //parametros=parametros+'&fechahora='+cadenafecha;

        var conn = new Ext.data.Connection();
        conn.request({
            waitTitle : 'Conectando',
            waitMsg : 'Generando el informe...',
            url : 'ValidacionCampos?'+parametros,
            timeout : TIME_OUT_AJAX,
            method  : 'POST',
            success : function(respuestaServer) {

                var obj = Ext.util.JSON.decode(respuestaServer.responseText);
                var validadoFormulario=document.getElementById('validado');
                validadoFormulario.value=1;
                for (var x=0; x<obj.campos.length; x++) {
                    var identificador=obj.campos[x].identificador;
                    var mensaje= obj.campos[x].mensaje;
                    var posiblevalor= obj.campos[x].posiblevalor;
                    var tipofallo= obj.campos[x].tipofallo;

                    var campito = document.getElementById(identificador);
                    if (tipofallo=='1') {
                        campito.style.backgroundColor='red';
                        validadoFormulario.value=2;
                    } else if (tipofallo=='2') {
                        campito.style.backgroundColor='yellow';
                        validadoFormulario.value=2;
                    } else if (tipofallo=='0') {
                        campito.style.backgroundColor='lightblue';

                    } else if (tipofallo=='3') {
                        campito.style.backgroundColor='blue';
                        validadoFormulario.value=2;
                    }
                }
                var campoErrores = document.getElementById('validado');

                if (val=='todos'){
                    if (campoErrores.value==2) {
                        Ext.Msg.show({
                            id:'idVentanaReporteErrores2',
                            title : 'Estado',
                            msg : 'Existen errores',
                            animEl : 'elId',
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK,
                            fn:function(){
                                verReporteError(val);
                                Ext.getCmp('idGuardarFormulario').enable();
                            }

                        });
                    } else {
                        //                        Ext.getCmp('idGuardarFormulario').enable();
                        Ext.Msg.show({
                            title : 'Estado',
                            msg : 'Formulario correcto. ¿Desea guardar el formulario?',
                            animEl : 'elId',
                            // minWidth: 500,
                            icon : Ext.MessageBox.INFO,
                            buttons : Ext.Msg.OKCANCEL,
                            fn: function(btn){
                                if(btn == 'ok'){

    <%if (task.get("DIGITACION") != null && task.get("DIGITACION").get("GUARDAR_FORMULARIO") != null) {%>

                                                                                 guardar();
                                                                                 limpiarPaneles("GUARDAR");
                                                                                 Ext.getCmp('idGuardarFormulario').disable();



    <%} else {%>

                                                                                Ext.Msg.show({
                                                                                    title : 'Estado',
                                                                                    msg : 'No tiene permiso para realizar esta Operación. Consulte al administrador',
                                                                                    animEl : 'elId',
                                                                                    icon : Ext.MessageBox.ERROR,
                                                                                    buttons : Ext.Msg.OK});
    <%}%>
                                                                             }

                                                                         }

                                                                     });
                                                                 }
                                                             }


                                                         },
                                                         failure : function(action) {
                                                             Ext.Msg.show({
                                                                 title : 'Estado',
                                                                 msg : 'No se pudo realizar la operación',
                                                                 animEl : 'elId',
                                                                 icon : Ext.MessageBox.ERROR,
                                                                 buttons : Ext.Msg.OK

                                                             });
                                                         }
                                                     });
                                                 }

                                                 function verReporteError(val){

                                                     var numeroFormulario=document.getElementById('numeroFormulario');
                                                     var numeroImpuesto=document.getElementById('numeroImpuesto');

                                                     if(numeroFormulario.value != 90){
                                                         var original=document.getElementById('decJuradaOrig');
                                                         var rectificativa=document.getElementById('decJuradaRectif');
                                                         var clausuracese=document.getElementById('decJuradaClausuraCese');
                                                         var periodoFiscal = document.getElementById('periodoFiscal');
                                                         var diasatrasos = document.getElementById('numeroDiasAtraso');
                                                         var mesesatrasos = document.getElementById('numeroMesesAtraso');
                                                     }
                                                    
                                                     //var diasatrasos = document.getElementById('numeroDiasAtraso');
                                                     var version = document.getElementById('version');
                                                     //var mesesatrasos = document.getElementById('numeroMesesAtraso');
                                                     var minimo = document.getElementById('minimoCampo');
                                                     var maximo = document.getElementById('maximoCampo');
                                                     var periodoFiscal = document.getElementById('periodoFiscal');
                                                     var parametros='formulario='+numeroFormulario.value+
                                                         '&validar='+val+
                                                         '&impuesto='+numeroImpuesto.value+
                                                         '&version='+version.value+
                                                         '&minimo='+minimo.value+
                                                         '&maximo='+maximo.value;
                                                     //'&mesesatrasos='+mesesatrasos.value+
                                                     //'&diasatrasos='+diasatrasos.value+
                                                     //'&decjuradaorig='+original.value+
                                                     //'&decjuradarectif='+rectificativa.value+
                                                     //'&decjuradaclausuracese='+clausuracese.value+
                                                     //'&periodoFiscal='+periodoFiscal.value+
                                                         

                                                     if(numeroFormulario.value == 90){
                                                         var numeroRuc=document.getElementById('ruc');
                                                         var campoActual=document.getElementById('C4');

                                                         parametros=parametros+'&ruc='+numeroRuc.value;

                                                         if (campoActual!=null && campoActual.value!='') {
                                                             valorCampo=deleteCommas(campoActual.value);
                                                             parametros=parametros+'&C4='+valorCampo;
                                                         }

                                                         for (var i=10; i<=14; i++){
                                                             var campoActual=document.getElementById('C'+i);
                                                             if (campoActual!=null && campoActual.value!='') {

                                                                 valorCampo=deleteCommas(campoActual.value);
                                                                 parametros=parametros+'&C'+i+'='+valorCampo;
                                                             }
                                                         }
                                                     }
                                                     else{

                                                         parametros=parametros+'&mesesatrasos='+mesesatrasos.value+
                                                             '&diasatrasos='+diasatrasos.value+
                                                             '&decjuradaorig='+original.value+
                                                             '&decjuradarectif='+rectificativa.value+
                                                             '&decjuradaclausuracese='+clausuracese.value+
                                                             '&periodoFiscal='+periodoFiscal.value;

                                                         var fecha_actual = new Date();
                                                         dia_mes = fecha_actual.getDate();

                                                         mes = fecha_actual.getMonth() + 1;
                                                         anio = fecha_actual.getFullYear();

                                                         hora = fecha_actual.getHours();

                                                         minuto = fecha_actual.getMinutes();
                                                         segundo = fecha_actual.getSeconds();

                                                         var cadenafecha=''+anio+mes+ dia_mes+hora+minuto+segundo;
                                                         parametros=parametros+'&fechahora='+cadenafecha;

                                                     }
        

                                                     for (var i=parseInt(minimo.value);i<=parseInt(maximo.value);i++) {
                                                         var campoActual=document.getElementById('C'+i);
                                                         if (campoActual!=null && campoActual.value!='') {
                                                             valorCampo=campoActual.value;
                                                             valorCampo = deleteCommas(valorCampo);
                                                             parametros=parametros+'&C'+i+'='+valorCampo;
                                                         }
                                                     }

                                                     //var fecha_actual = new Date();
                                                     //dia_mes = fecha_actual.getDate();

                                                     //mes = fecha_actual.getMonth() + 1;
                                                     //anio = fecha_actual.getFullYear();

                                                     //hora = fecha_actual.getHours();

                                                     //minuto = fecha_actual.getMinutes();
                                                     //segundo = fecha_actual.getSeconds();

                                                     //var cadenafecha=''+anio+mes+ dia_mes+hora+minuto+segundo;
                                                     //parametros=parametros+'&fechahora='+cadenafecha;

                                                     /***************************************/
                                                     var conn = new Ext.data.Connection();
                                                     conn.request({
                                                         waitTitle : 'Conectando',
                                                         waitMsg : 'Generando el informe...',
                                                         url : 'ValidacionCampos?'+parametros,
                                                         timeout : TIME_OUT_AJAX,
                                                         method : 'POST',
                                                         success : function(respuestaServer) {

                                                             var obj = Ext.util.JSON.decode(respuestaServer.responseText);

                                                             var validadoFormulario=document.getElementById('validado');
                                                             validadoFormulario.value=1;
                                                             var mensajeError ="";
                                                             var mensajeErrorToServlet ="";
                                                             for (var x=0; x<obj.campos.length; x++) {


                                                                 var identificador=obj.campos[x].identificador;
                                                                 var mensaje= obj.campos[x].mensaje;
                                                                 var posiblevalor= obj.campos[x].posiblevalor;
                                                                 var tipofallo= obj.campos[x].tipofallo;

                                                                 var campito = document.getElementById(identificador);

                                                                 if(posiblevalor!="--")
                                                                     if (tipofallo=='1') {

                                                                         mensajeError +="<br>"+identificador+" Valor esperado: "+addCommas( posiblevalor )+"</br>";
                                                                         mensajeErrorToServlet += identificador +" Valor esperado: "+addCommas( posiblevalor ) +"\n\n";
                                                                         validadoFormulario.value=2;
                                                                     } else if (tipofallo=='2') {
                                                                     mensajeError +="<br>"+identificador+" Valor esperado: "+addCommas( posiblevalor )+"</br>";
                                                                     mensajeErrorToServlet += identificador +" Valor esperado: "+addCommas( posiblevalor ) +"\n\n";
                                                                     validadoFormulario.value=2;
                                                                 } else if (tipofallo=='3') {
                                                                     mensajeError +="<br>"+identificador+" Valor esperado: "+addCommas( posiblevalor )+"</br>";
                                                                     mensajeErrorToServlet += identificador +" Valor esperado: "+addCommas( posiblevalor ) +"\n\n";
                                                                     validadoFormulario.value=2;
                                                                 }
                                                             }
                                                             var campoErrores = document.getElementById('validado');

                                                             if (campoErrores.value==2) {
                                                                 if(Ext.getCmp('idVentanaReporteErrores')==null){


                                                                     var obj=null ;
                                                                     var conn = new Ext.data.Connection();
                                                                     conn.request({
                                                                         waitTitle: 'Conectando',
                                                                         waitMsg: 'Cargando Campos',
                                                                         url: 'reportes?op=REPORTE_ERROR_DIGITACION',
                                                                         params: {
                                                                             idFormulario: Ext.getCmp('idTipoFormulario').getRawValue(),
                                                                             mensajeErrorServlet:mensajeErrorToServlet

                                                                         },
                                                                         timeout : TIME_OUT_AJAX,
                                                                         method: 'POST',
                                                                         success: function (respuestaServer) {

                                                                             obj = Ext.util.JSON.decode(respuestaServer.responseText);
                                                                             if (obj.success) {
                                                                                 ;

                                                                             } else {
                                                                                 Ext.Msg.show({
                                                                                     title: 'Estado',
                                                                                     msg: obj.motivo,
                                                                                     animEl: 'elId',
                                                                                     icon: Ext.MessageBox.ERROR,
                                                                                     buttons: Ext.Msg.OK,
                                                                                     fn: function(){
                                                                                         Ext.getCmp('CC4').focus(true,true);
                                                                                     }
                                                                                 });

                                                                             }
                                                                         },
                                                                         failure: function (action) {
                                                                             Ext.Msg.show({
                                                                                 title: 'Estado',
                                                                                 msg: 'No se pudo realizar la operación',
                                                                                 animEl: 'elId',
                                                                                 icon: Ext.MessageBox.ERROR,
                                                                                 buttons: Ext.Msg.OK
                                                                             });
                                                                         }
                                                                     });

                                                                     var win = new Ext.Window({
                                                                         title : 'Reporte de Errores',
                                                                         id : 'idVentanaReporteErrores',
                                                                         width : 'auto',
                                                                         height : 'auto',
                                                                         closable : true,
                                                                         autoScroll:true,
                                                                         resizable : false,
                                                                         defaultButton:'idBtnOkReporteErrores',
                                                                         items : [{
                                                                                 html : mensajeError+"<br></br>"
                                                                             }],
                                                                         buttons : [{
                                                                                 id:'idBtnOkReporteErrores',
                                                                                 text : 'Ok',
                                                                                 handler: function(){
                                                                                     win.close();
                                                                                 }
                                                                             },{
                                                                                 id:'idBtnImprimirReporteErrores',
                                                                                 text : 'Imprimir',
                                                                                 handler: function(){

                                                                                     if(obj !=null){
                                                                                         imprimirImpresoraExterna(obj.ticketError);
                                                                                     }
                                                                                 }
                                                                             }]
                                                                     });
                                                                     win.show();
                                                                 }

                                                             }
                                                         }, 
                                                         failure : function(action) {
                                                             Ext.Msg.show({
                                                                 title : 'Estado',
                                                                 msg : 'No se pudo realizar la operación',
                                                                 animEl : 'elId',
                                                                 minWidth: 300,
                                                                 maxWidth: 300,
                                                                 minHeight :300,
                                                                 maxHeight :300,
                                                                 icon : Ext.MessageBox.ERROR,
                                                                 buttons : Ext.Msg.OK

                                                             });
                                                         }
                                                     });
                                                 }

                                                 function guardar(){
                                                     var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Guardando..."});
                                                     myMask.show();

                                                     var gestor=document.getElementById('idDigitacionGestor');
                                                     var numeroFormulario=document.getElementById('numeroFormulario');
                                                     var numeroRuc=document.getElementById('ruc');
                                                     var numeroDV=document.getElementById('dv');

                                                     if(numeroFormulario.value != 90){
                                                         var original=document.getElementById('decJuradaOrig');
                                                         var rectificativa=document.getElementById('decJuradaRectif');
                                                         var clausuracese=document.getElementById('decJuradaClausuraCese');
                                                         var periodoFiscal = document.getElementById('periodoFiscal');
                                                         var decNumeroRectif = document.getElementById('decNumeroRectif');
                                                     }
                                                     else{

                                                     }
                                                     
                                                     var version = document.getElementById('version');
                                                     var validado = document.getElementById('validado');
                                                     var minimo = document.getElementById('minimoCampo');
                                                     var maximo = document.getElementById('maximoCampo');
                                                     var fechaPresentacion = document.getElementById('fechaPresentacion');
                                                     

                                                     if (validado.value==2) {
                                                         Ext.Msg.show({
                                                             title : 'Error',
                                                             msg : 'Existen campos invalidos',
                                                             animEl : 'elId',
                                                             minWidth: 220,
                                                             icon : Ext.MessageBox.ERROR,
                                                             buttons : Ext.Msg.OK,
                                                             fn:function(){
                                                                 Ext.getCmp('idTipoFormulario').focus(true,true);
                                                             }

                                                         });

                                                     }


                                                     //var diasatrasos = document.getElementById('numeroDiasAtraso');
                                                     var version = document.getElementById('version');
                                                     //var mesesatrasos = document.getElementById('numeroMesesAtraso');
                                                     var minimo = document.getElementById('minimoCampo');
                                                     var maximo = document.getElementById('maximoCampo');
                                                     var periodoFiscal = document.getElementById('periodoFiscal');
                                                     var parametros='formulario='+numeroFormulario.value+
                                                         '&ruc='+numeroRuc.value+
                                                         '&dv='+numeroDV.value+
                                                         '&fechaPresentacion='+fechaPresentacion.value+
                                                         '&version='+version.value+
                                                         '&validado='+validado.value+
                                                         '&minimoCampo='+minimo.value+
                                                         '&maximoCampo='+maximo.value+
                                                         '&gestorCI='+gestor.value;

                                                     if(numeroFormulario.value != 90){
                                                         parametros=parametros+'&decJuradaOrig='+original.value+
                                                             '&periodoFiscal='+periodoFiscal.value+
                                                             '&decJuradaRectif='+rectificativa.value+
                                                             '&decJuradaClausuraCese='+clausuracese.value+
                                                             '&decNumeroRectif='+decNumeroRectif.value;
                                                     }
                                                     if(numeroFormulario.value == 90){

                                                         var numeroRuc=document.getElementById('ruc');
                                                         var campoActual=document.getElementById('C4');

                                                         parametros=parametros+'&ruc='+numeroRuc.value;


                                                         if (campoActual!=null && campoActual.value!='') {

                                                             valorCampo=deleteCommas(campoActual.value);
                                                             parametros=parametros+'&C4='+valorCampo;
                                                         }
                                                         for (var i=10; i<=14; i++){
                                                             campoActual=document.getElementById('C'+i);
                                                             if (campoActual!=null && campoActual.value!='') {
                                                                 var campo;

                                                                 valorCampo=deleteCommas(campoActual.value);
                                                                 parametros=parametros+'&C'+i+'='+valorCampo;

                                                                 if (i==10 && campoActual.value!='0'){
                                                                     campo=i;
                                                                 }else{
                                                                     if(i==11 && campoActual.value!='0')
                                                                         campo=i;
                                                                     else{
                                                                         if(i==12 && campoActual.value!='0')
                                                                             campo=i;
                                                                         else{
                                                                             if(i==13 && campoActual.value!='0'){
                                                                                 campo=i;
                                                                             }else{
                                                                                 if(i==14 && campoActual.value!='0'){
                                                                                     campo=i;
                                                                                 }
                                                                             }
                                                                         }
                                                                     }
                                                                 }
                                                             }
                                                         }
                                                     }


                                                         

                                                     for (var i=parseInt(minimo.value);i<=parseInt(maximo.value);i++) {
                                                         var campoActual=document.getElementById('C'+i);
                                                         if (campoActual!=null && campoActual.value!='') {
                                                             valorCampo=campoActual.value;
                                                             valorCampo = deleteCommas(valorCampo);
                                                             parametros=parametros+'&C'+i+'='+valorCampo;

                                                             if(numeroFormulario.value == 90){
                                                                 if(campo=='10'&& i==19){
                                                                     parametros=parametros+'&periodoFiscal='+campoActual.value;
                                                                 }else{
                                                                     if(campo=='11'&& i==21){
                                                                         parametros=parametros+'&periodoFiscal='+campoActual.value;
                                                                     }else{
                                                                         if(campo=='12'&& i==19){
                                                                             parametros=parametros+'&periodoFiscal='+campoActual.value;
                                                                         }else{
                                                                             if(campo=='13'&& i==31){
                                                                                 parametros=parametros+'&periodoFiscal='+campoActual.value;
                                                                             }else{
                                                                                 if(campo=='14'&& i==33){
                                                                                     parametros=parametros+'&periodoFiscal='+campoActual.value;
                                                                                 }
                                                                             }
                                                                         }
                                                                     }
                                                                 }
                                                             }
                                                         }


                                                     }

                                                     var fecha_actual = new Date();
                                                     dia_mes = fecha_actual.getDate();

                                                     mes = fecha_actual.getMonth() + 1;
                                                     anio = fecha_actual.getFullYear();

                                                     hora = fecha_actual.getHours();

                                                     minuto = fecha_actual.getMinutes();
                                                     segundo = fecha_actual.getSeconds();

                                                     var cadenafecha=''+anio+mes+ dia_mes+hora+minuto+segundo;
                                                     parametros=parametros+'&fechahora='+cadenafecha;

                                                     var conn = new Ext.data.Connection();
                                                     conn.request({
                                                         waitTitle : 'Conectando',
                                                         waitMsg : 'Guardando Formulario...',
                                                         url : 'GuardarFormulario?op=GUARDAR_FORMULARIO&'+parametros,
                                                         method : 'POST',
                                                         timeout : TIME_OUT_AJAX,
                                                         success : function(respuestaServer) {
                                                             myMask.hide();
                                                             var respuesta = Ext.util.JSON.decode(respuestaServer.responseText);
                                                             if (validado.value==2) {
                                                                 return;
                                                             }
                                                             if(respuesta.success){
                                                                 var botones = null;

    <%if (task.get("DIGITACION") != null && task.get("DIGITACION").get("GUARDAR_CERTIFICAR") != null) {%>
                                                
                                                var contador = 0;

                                                var botonOk = {
                                                    id: 'botonOk',
                                                    text : 'Ok',
                                                    disabled : true,
                                                    handler : function() {


                                                        win.close();
                                                        Ext.getCmp('idDigitacionGestor').focus(true);
                                                    }
                                                };

                                                var botonCancel = {
                                                    id: 'idBotonCancel',
                                                    text : 'Cancelar',
                                                    disabled : false,
                                                    handler : function() {
                                                        Ext.Msg.show({
                                                            title: 'Atención',
                                                            msg:'Esta seguro que desea cancelar la acción?',
                                                            buttons: Ext.MessageBox.OKCANCEL,
                                                            icon: Ext.MessageBox.WARNING,
                                                            fn: function(btn){
                                                                if (btn == 'ok'){
                                                                    win.close();
                                                                    Ext.getCmp('idDigitacionGestor').focus(true);
                                                                }
                                                            }
                                                        });
                                                    }
                                                };

                                                botones = [{
                                                        text : 'Certificar',
                                                        id: 'idBtnCERTIFICAR',
                                                        formBind : true,
                                                        handler : function() {
                                                            var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Certificando..."});
                                                            myMask.show();
                                                            var conn = new Ext.data.Connection();           


                                                            conn.request({
                                                                waitTitle : 'Conectando',
                                                                waitMsg : 'Generando el informe...',
                                                                url : 'CertificarFormulario',
                                                                params:{
                                                                    idFormContrib:respuesta.codigo
                                                                },
                                                                timeout : TIME_OUT_AJAX,
                                                                method : 'POST',
                                                                success : function(respuestaServer) {
                                                                    myMask.hide();
                                                                    var obj = Ext.util.JSON.decode(respuestaServer.responseText);

                                                                    contador = contador + 1;

                                                                    if (contador == 1){
                                                                        Ext.getCmp('botonOk').setDisabled(false);
                                                                        Ext.getCmp('idBtnCERTIFICAR').setDisabled(true);
                                                                    }

                                                                    Ext.Msg.show({
                                                                        title : 'Info',
                                                                        msg : obj.ticket_pantalla,
                                                                        buttons : Ext.Msg.OK,
                                                                        icon : Ext.MessageBox.INFO,
                                                                        minWidth: 500,
                                                                        fn:function(){
                                                                            Ext.getCmp('idBtnCERTIFICAR').focus(true,true);
                                                                            imprimirImpresoraExterna(obj.ticket);
                                                                        }
                                                                    });
                                                                },
                                                                failure : function(action) {
                                                                    myMask.hide();
                                                                    Ext.Msg.show({
                                                                        title : 'Estado',
                                                                        msg : 'No se pudo realizar la operación',
                                                                        animEl : 'elId',
                                                                        icon : Ext.MessageBox.ERROR,
                                                                        buttons : Ext.Msg.OK

                                                                    });
                                                                }
                                                            });
                                                        }
                                                    },botonOk,botonCancel];

    <%} else {%>
                                                botones = [{
                                                        text : 'OK',
                                                        handler : function() {
                                                            win.close();
                                                        }
                                                    }];

    <%}%>
                                                   var win = new Ext.Window({
                                                       title:'Atención',
                                                       autoWidth : true,
                                                       height : 'auto',
                                                       closable : false,
                                                       resizable : false,
                                                       buttons : botones,
                                                       defaultButton:0,
                                                       items : [{
                                                               html:'<H1>'+MSG_REFERENCIA_PARA_CAJA+respuesta.codigo+'</H1>'
                                                           }]
                                                   });
                                                   win.show();
                                               }
                                               else{
                                                   callBackServidor(respuesta);
                                               }
                                           },
                                           failure : function(action) {
                                               myMask.hide();
                                               var obj = Ext.util.JSON.decode(action.responseText);
                                               callBackServidor(obj);
                                           }
                                       });


                                   }


<%}%>
</script>

