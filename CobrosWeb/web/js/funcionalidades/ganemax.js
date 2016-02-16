function panelGanemax(){
    
// /*    $(document).ready(function(){
//
//        var destino = $('#pronet');
//
//        $('#elegidos').on('click', function(){
//
//            $.ajax({
//                type: "POST",
//                url: "http://egmax.net:8080/apiegsa/jugar/1|70000001/elegidos",
//                data: {
//                    key: 'QCNodHRwOi8vYXBpZWdtYXguY2FidS5jbyZ8'
//                },
//                //dataType: 'html',
//                async: false,
//                complete: function(jqXHR, textStatus) {
//    // console.log('complete: '/*+JSON.stringify(jqXHR)+" --- "*/ +textStatus);
//              }
//            })
//            .done(function( data ) { });
//
//            setTimeout(function(){
//                var elegidos = new classApiEgsa(destino);
//                elegidos.buildGame();
//                $('#btn_enviar').on('click', function(){
//
//                    elegidos.enviar_apuesta(function(){
//
//                        var	paquetered = {
//                            terminal : 70000001,
//                            lote: 700015,
//                            transaccion: 15879,
//                            usuario: 'cvallejos',
//                            token: elegidos.getToken()
//                        };
//                        $.ajax({
//                            type: "POST",
//                            url: "http://egmax.net:8080/apiegsa/apostar/1|70000001/elegidos",
//                            data: {
//                                key: 'QCNodHRwOi8vYXBpZWdtYXguY2FidS5jbyZ8', 
//                                apuesta: paquetered
//                            },
//                            async: false,
//                            dataType: 'json',
//                            success: function(data){
//                                prueba=data;
//                            },
//                            error: function(e){
//                                alert("error : "+ e);
//                            },
//                            complete: function(jqXHR, textStatus) {
//                                console.log('complete: '+jqXHR+" --- " +textStatus);
//                            }
//                        }).done(function(data){
//                            console.log('done: OK');
//                        });
//
//                        setTimeout(function(){ 
//                            alert('Respuesta: '+prueba.status+'\nTicket: '+prueba.ticket);
//                            console.log('envio: '+JSON.stringify(prueba));
//                            elegidos.clean();
//                            $('#tablaApuestas tbody').empty();
//                            tipoJuegoActual="";
//                            elegidos.updateTotal();
//                        },300);
//
//                    });
//	
//                });
//            },300);
//
//        });
//
//        $('#superlotto').on('click', function(){
//
//            $.ajax({
//                type: "POST",
//                url: "http://egmax.net:8080/apiegsa/jugar/1|70000001/superlotto",
//                data: {
//                    key: 'QCNodHRwOi8vYXBpZWdtYXguY2FidS5jbyZ8'
//                },
//                async: false,
//                complete: function(jqXHR, textStatus) {
//    //  console.log('complete: '/*+JSON.stringify(jqXHR)+" --- "*/ +textStatus);
//            }
//            })
//            .done(function( data ) { });
//
//            setTimeout(function(){
//                var superlotto = new classApiEgsa(destino);
//                superlotto.buildGame();
//                $('#btn_enviar').on('click', function(){
//
//                    superlotto.enviar_apuesta(function(){
//
//                        var	paquetered = {
//                            terminal : 70000001,
//                            lote: 700015,
//                            transaccion: 15879,
//                            usuario: 'cvallejos',
//                            token: superlotto.getToken()
//                        };
//
//
//
//                        $.ajax({
//                            type: "POST",
//                            url: "http://egmax.net:8080/apiegsa/apostar/1|70000001/superlotto",
//                            data: {
//                                key: 'QCNodHRwOi8vYXBpZWdtYXguY2FidS5jbyZ8', 
//                                apuesta: paquetered
//                            },
//                            async: false,
//                            dataType: 'json',
//                            success: function(data){
//                                prueba=data;
//                            },
//                            error: function(e){
//                                alert("error : "+ e);
//                            },
//                            complete: function(jqXHR, textStatus) {
//                                console.log('complete: '+jqXHR+" --- " +textStatus);
//                            }
//                        }).done(function(data){
//                            console.log('done: OK');
//                        });
//
//                        setTimeout(function(){ 
//                            alert('Respuesta: '+prueba.status+'\nTicket: '+prueba.ticket);
//                            console.log('envio: '+JSON.stringify(prueba));
//                            superlotto.clean();
//                            $('#tablaApuestas tbody').empty();
//                            tipoJuegoActual="";
//                            superlotto.updateTotal();
//                        },300);
//
//                    });
//	
//                });
//            },300);
//	
//        });
//
//        $('#inquire').on('click', function(){
//
//            $.ajax({
//                type: "POST",
//                url: "http://egmax.net:8080/apiegsa/jugar/1|70000001/inquire",
//                data: {
//                    key: 'QCNodHRwOi8vYXBpZWdtYXguY2FidS5jbyZ8'
//                },
//                async: false,
//                complete: function(jqXHR, textStatus) {
//                    console.log('complete: '+textStatus);
//                }
//            })
//            .done(function( data ) { });
//
//            setTimeout(function(){
//                var inquire = new classApiEgsa(destino);
//                inquire.buildGame();
//                
//                $('#btn_validate').on('click', function(){
//
//                    inquire.enviar_validacion(function(){
//
//                        alert( JSON.stringify( inquire.getToken() ) + '<br />Monto: ' + inquire.getTotal() );
//
//                    });
//
//                });
//
//            },300);
//
//        });
//
//    });
//    */
    var destino = $('#documenta');
    var panelGmx = {
        id : 'panelGmx',
        xtype : 'panel',
        title:'GaneMax',
        //layout : 'fit',
        border : false,
        autoHeight:false,
        autoScroll : true,
        tbar : [{
            text : 'Salir (Alt+q)',
            iconCls:'logout',
            handler : function(){
                cardInicial();
            }
        },{
            text : 'Elegidos',
            //iconCls:'logout',
            handler : function(){
  


        }
    }], 
    items : []
}    
return panelGmx;  
}

