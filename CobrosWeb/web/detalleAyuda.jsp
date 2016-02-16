
<%@page import="py.com.konecta.redcobros.ejb.ServicioRcFacade"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="py.com.konecta.redcobros.entities.Facturador"%>
<%@page import="py.com.konecta.redcobros.entities.HelpServicio"%>
<%@page import="py.com.konecta.redcobros.entities.ServicioRc"%>
<%@page import="py.com.konecta.redcobros.ejb.HelpServicioFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

{
        xtype:'panel',
        layout: 'fit',
        width: 625,
        height: 448,
        border: false,
        tbar:[/*{
            text:'Pagar',
            iconCls:'pay',
            handler:function(){
                Ext.getCmp('idWinHelpService').close();
                pagarServicio(moduloDePago);
            }
        },*/{
            text:'Salir (Alt+q)',
            iconCls:'logout',
            handler:function(){
                Ext.getCmp('idWinHelpService').close();      
            }            
        }],
        items: [{
            xtype: 'tabpanel', 
            height: 450,
            bodyStyle: 'padding:10px',
            activeTab: 0,
            enableTabScroll:true,
            plain: false,
            items: [{
                title:'AYUDA',
                html:'<h1>Cómo utilizar la ayuda?</h1></br><p>Seleccione una de las solapas que son los servicios disponibibles del facturador seleccionado previamente, al seleccionar</br> una de las solapas se mostrará en que módulos se puede pagar el servicio seleccionado, una vez seleccionado un módulo</br> usted podrá ver un detalle de que necesita ingresar para poder realizar el pago en ese módulo, así también como la fecha</br> de vencimiento y alguna información particular del servicio.</br></br> El botón "Pagar" que se encuentra en cada módulo de servicio lo llevará al módulo de pago seleccionado.</p>'
            },<%
    Long idFacturador = request.getParameter("idFacturador") != null && !request.getParameter("idFacturador").isEmpty() ? Long.valueOf(request.getParameter("idFacturador")) : null;
    Context context = new InitialContext();
    ServicioRc servicio = new ServicioRc();
    servicio.setIdFacturador(new Facturador(idFacturador));
    servicio.setHabilitado("S");

    ServicioRcFacade servicioRcFacade = (ServicioRcFacade) context.lookup(ServicioRcFacade.class.getName());
    List<ServicioRc> lServicioRc = servicioRcFacade.list(servicio);

    int cant = lServicioRc.size();
    int cont = 0;
    for(ServicioRc it:lServicioRc){
       cont++;
       HelpServicioFacade helpServicioFacade = (HelpServicioFacade) context.lookup(HelpServicioFacade.class.getName()); 
       HelpServicio ejemplo = new HelpServicio();
       ejemplo.setServicio(it);
       List<HelpServicio> lHelpServicio = helpServicioFacade.list(ejemplo);
       int cantSer = lHelpServicio.size();
       int contSer = 0;
       int moduloDePago=-1;                
    %>
            {  
                xtype: 'panel',  
                title: '<%=it.getDescripcion()%>',  
                layout: 'accordion',  
                layoutConfig: {  
                    titleCollapse: true,  
                    animate: false//,  
                    //activeOnTop: true  
                },  defaults: {
                    collapsed: <%=cantSer>1?"true":"false"%>
                },                
                items: [<%                                   
                       for(HelpServicio itt:lHelpServicio){
                            contSer++;
                            moduloDePago = itt.getModulo().equalsIgnoreCase("PAGO_DE_SERVICIOS")?1:itt.getModulo().equalsIgnoreCase("CONSULTA_Y_PAGO")?2:itt.getModulo().equalsIgnoreCase("PAGO_DE_TELEFONIAS")?3:itt.getModulo().equalsIgnoreCase("PAGO_DE_TARJETAS")?4:5;
                            %>
                            {  
                                title: 'Modulo De Pago [<%=itt.getModulo().replace("_"," ")%>]', 
                                bodyStyle:'padding:5px 0px 0px 50px',
                                html: '<h1>Cómo pagar?</h1></br><p><%=itt.getComoPagar()%></p></br><h1>Vencimiento</h1></br><p><%=itt.getVencimiento().replace("\\n","</br>")%></p></br><%=itt.getObservacion()!=null&&!itt.getObservacion().isEmpty()?"<h1>Observacion</h1></br><p>"+itt.getObservacion()+"</p></br>":"</br>"%></br></br><button style="width:150px"  name="btnModuloPago" id="idBtnModuloPago" onclick="pagarServicio(<%=moduloDePago%>)">Pagar</button>'                                
                            }
                            <% if(contSer!=cantSer){
                                    out.println(",");
                                }
                       }%>                      

                ]
            }
<%      if(cont!=cant){
            out.println(",");
        }
    }%>
            ]
    }
    ]
}

