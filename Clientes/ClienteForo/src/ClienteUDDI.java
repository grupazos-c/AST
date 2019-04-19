import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.DeleteBusiness;
import org.uddi.api_v3.Description;
import org.uddi.api_v3.DiscardAuthToken;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.FindQualifiers;
import org.uddi.api_v3.FindService;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;


/**
 * @deprecated
 * 
 * Esta clase se trata de un cliente basico para un servidor UDDI de registro de servicios. Contiene los metodos necesarios para
 * encontrar cualquiera entidad de negocio o sus respectivos servicios a partir de su nombre o de su identificador, 
 * obtener la localizacion exacta de los mismos y introducir o eliminar cualquier negocio o servicio del registro UDDI.
 * 
 * Para realizar cualquier consulta sera necesario obtener antes un token de autentificacion del propio registro UDDI mediante
 * el metodo <b>obtenerTokenDeAutentificacion()</b>, y sera aconsejable, aunque no necesario, terminar la conexion con el registro
 * invalidando el token obtenido mediante el metodo <b>descartarTokenDeAutentificacion()</b>;
 * 
 * @author VigoCoffeeLovers
 * 
 */
public class ClienteUDDI {
	
	private UDDIClient clienteUDDI;
	private UDDIInquiryPortType inquiryUDDI;
	private UDDISecurityPortType securityUDDI;
	private UDDIPublicationPortType publishUDDI;
	private UDDIClerk clerkUDDI;
	
	private String usuario;
	private String clave;
	
	
	/* EJEMPLOS DE USOS */
	/*
	public static void main(String[] args) throws ConfigurationException, TransportException, DispositionReportFaultMessage, RemoteException {
		
		ClienteUDDI clienteuddi = new ClienteUDDI("usuario","clave");
		
		AuthToken token = clienteuddi.obtenerTokenDeAutentificacion();
		
        BusinessEntity negocio = clienteuddi.registrarNegocio(new Name("nombre_negocio", ""), new Description("descripcion_negocio", ""), token);
        
        BusinessService servicio = clienteuddi.registrarServicio(new Name("nombre_servicio", ""), new Description("descripcion_servicio", ""), "http:\\url_servicio", clienteuddi.obtenerClaveNegocio(new Name("nombre_negocio",""), token), token);
        
        clienteuddi.eliminarNegocio(clienteuddi.obtenerClaveNegocio(new Name("nombre_negocio",""), token), token);
        
        clienteuddi.eliminarServicio(clienteuddi.obtenerClaveServicio(new Name("nombre_servicio",""), new Name("nombre_negocio",""), token), token);
        
		clienteuddi.obtenerDireccionServicio(clienteuddi.obtenerClaveServicio(new Name("nombre_servicio",""), new Name("nombre_negocio",""), token), token);
		
		clienteuddi.descartarTokenDeAutentificacion(token);
		
	}
	*/
	
	
	
	
	
	/**
	 * Crea un nuevo objeto ClienteUDDI e inicializa los parametros necesarios para la realizacion de todas las consultas 
	 * y posibles acciones sobre el registro UDDI:
	 * 	-> clienteUDDI: instancia principal que se corresponde con una conexion directa con el registro para realizar las peticiones.
	 * 	-> inquiryUDDI: utilizado para realizar consultas en el registro.
	 * 	-> securityUDDI: utilizado para obtener permisos de seguridad sobre el registro.
	 * 	-> publishUDDI: utilizado para editar los datos del registro.
	 * 	-> clerkUDDI: utilizado todo tipo de acceso a los datos del registro. Agrupa todas las funcionalidades anteriores.
	 * 
	 * 	@see Habria sido todo mucho mas facil usando solo el Clerk, sin embargo esta informacion llego demasiado tarde para nuestros
	 * valientes heroes, quienes, en vista de las pobres condiciones en las que empezaron esta mision y el duro e infrahumano 
	 * trabajo que tuvieron que pasar para obtener algo tan infimo como un peque�o cliente basico que realizara de malas maneras
	 * las pocas funcionalidades que les habian ordenado implementar, decidieron no reconstruir los avances obtenidos y
	 * simplemente utilizar lo aprendido en una peque�a parte de la totalidad del conjunto para demostrar que, si hubieran
	 * querido, podrian haber llegado a una solucion mejor, pero que en vista de las pobres condiciones en las que empezaron esta 
	 * mision y el duro e infrahumano trabajo que tuvieron que pasar para obtener algo tan infimo como un peque�o cliente basico 
	 * que realizara de malas maneras las pocas funcionalidades que les habian ordenado implementar, decidieron no reconstruir 
	 * los avances obtenidos y simplemente utilizar lo aprendido en una peque�a parte de la totalidad del conjunto para 
	 * demostrar que, si hubieran querido, podrian haber llegado a una solucion mejor.
	 * 
	 * @throws ConfigurationException
	 * @throws TransportException
	 */
	public ClienteUDDI(String usuario, String clave) throws ConfigurationException, TransportException {
		
		this.clienteUDDI = new UDDIClient("uddi.xml"); //donde est� el uddi.xml
		this.inquiryUDDI = clienteUDDI.getTransport().getUDDIInquiryService();
		this.securityUDDI = clienteUDDI.getTransport().getUDDISecurityService();
		this.publishUDDI = clienteUDDI.getTransport().getUDDIPublishService();
		this.clerkUDDI = clienteUDDI.getClerk("default");
		
		this.usuario = usuario;
		this.clave = clave;
	}
	
	
	
	
	
	/**
	 * Realiza una peticion al registro de un token identificador que debera ser usado para poder realizar cualquier peticion.
	 * @return Token que identifica la conexion del cliente con el registro
	 * 
	 * @throws ConfigurationException
	 * @throws TransportException
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public org.uddi.api_v3.AuthToken obtenerTokenDeAutentificacion()  throws ConfigurationException, TransportException, DispositionReportFaultMessage, RemoteException {
		
		org.uddi.api_v3.GetAuthToken getAuthToken = new GetAuthToken(this.usuario, this.clave);

        org.uddi.api_v3.AuthToken tokenAutorizacion = securityUDDI.getAuthToken(getAuthToken);
	        System.out.println("Logeado con exito con las credenciales: ("+this.usuario+","+this.clave+")");
	        System.out.println("El token de autentificacion es: " + tokenAutorizacion.getAuthInfo());
	        
        return tokenAutorizacion;
	}
	
	
	
	
	/**
	 * Informa al registro del fin de la conexion para que descarte el token que identificaba la conexion.
	 * @param tokenAutorizacion Token que sera invalidado por el registro
	 * 
	 * @throws ConfigurationException
	 * @throws TransportException
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public void descartarTokenDeAutentificacion(AuthToken tokenAutorizacion) throws ConfigurationException, TransportException, DispositionReportFaultMessage, RemoteException {
		
		securityUDDI.discardAuthToken(new DiscardAuthToken(tokenAutorizacion.getAuthInfo()));
        	System.out.println("Sesion cerrada y token invalidado");
        
        return;
	}
	
	
	
	
	/**
	 * Introduce un nuevo negocio en el registro UDDI con el nombre indicado.
	 * @param nombreNegocio Nombre deseado para el negocio.
	 * @param descripcionNegocio Descripcion deseada para el negocio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * @return Devuelve un objeto con la entidad de negocio registrada o NULL en el caso de que no se haya podido realizar el registro.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 * @throws ConfigurationException
	 * @throws TransportException
	 */
	public org.uddi.api_v3.BusinessEntity registrarNegocio(Name nombreNegocio, Description descripcionNegocio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException, ConfigurationException, TransportException {
		
		org.uddi.api_v3.BusinessList listaNegocios = obtenerNegocios(tokenAutorizacion, false);

		List<org.uddi.api_v3.BusinessInfo> listaInfoNegocios = listaNegocios.getBusinessInfos().getBusinessInfo();
        for (int i=0; i<listaInfoNegocios.size();i++) {
        	if (listaInfoNegocios.get(i).getName().get(0).getValue().trim().equalsIgnoreCase(nombreNegocio.getValue().trim())) {
        		System.out.println("El servicio con el nombre '" + nombreNegocio.getValue() + "' ya esta registrado");
        		return null;
        	}
        }
        
        org.uddi.api_v3.BusinessEntity negocio = new BusinessEntity();
    
        negocio.getName().add(nombreNegocio);
        negocio.getDescription().add(descripcionNegocio);
    
        org.uddi.api_v3.BusinessEntity negocioRegistrado = clerkUDDI.register(negocio);
	        if (negocioRegistrado==null) {
	            System.out.println("Registro del servicio fallido");
	            return null;
	        }
        
    	System.out.println("Clave del nuevo negocio: " + negocioRegistrado.getBusinessKey());
    	
    	return negocioRegistrado;
	}
	
	
	
	
	/**
	 * Introduce un nuevo servicio en un negocio ya creado y con el nombre indicado.
	 * @param nombreServicio Nombre deseado para el nuevo servicio.
	 * @param descripcionServicio Descripcion deseada para el nuevo servicio.
	 * @param claveNegocio Clave identificativa del negocio.
	 * @param endpoint Direccion URL del servicio que se pretende registrar.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro.
	 * @return Devuelve un objeto con el nuevo servicio registrado o NULL en el caso de que no se haya podido realizar el registro.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 * @throws ConfigurationException
	 * @throws TransportException
	 */
	public org.uddi.api_v3.BusinessService registrarServicio(Name nombreServicio, Description descripcionServicio, String endpoint, String claveNegocio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException, ConfigurationException, TransportException {
		
		org.uddi.api_v3.BusinessService nuevoServicio = new BusinessService();
        nuevoServicio.setBusinessKey(claveNegocio);
        nuevoServicio.getName().add(nombreServicio);
        nuevoServicio.getDescription().add(descripcionServicio);

        org.uddi.api_v3.BindingTemplate bindingTemplate = new BindingTemplate();
        org.uddi.api_v3.AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUseType(AccessPointType.END_POINT.toString());
        accessPoint.setValue(endpoint);
        bindingTemplate.setAccessPoint(accessPoint);
        
        System.out.println(nuevoServicio.getBindingTemplates().getBindingTemplate().get(0).getAccessPoint().getValue());
        
        org.uddi.api_v3.BindingTemplates myBindingTemplates = new BindingTemplates();
        bindingTemplate = UDDIClient.addSOAPtModels(bindingTemplate);
        myBindingTemplates.getBindingTemplate().add(bindingTemplate);
        nuevoServicio.setBindingTemplates(myBindingTemplates);

        org.uddi.api_v3.BusinessService servicioRegistrado = clerkUDDI.register(nuevoServicio);
        if (servicioRegistrado==null) {
                System.out.println("Registro del servicio fallido");
                return null;
        }

        System.out.println("Clave del nuevo servicio:  " + servicioRegistrado.getServiceKey());
		
        return servicioRegistrado;
	}
	
	
	
	
	/**
	 * Elimina el negocio indicado del registro UDDI.
	 * @param claveNegocio Clave identificativa del negocio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public void eliminarNegocio(String claveNegocio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException {
		
		org.uddi.api_v3.DeleteBusiness deleteNegocio = new DeleteBusiness();
		deleteNegocio.setAuthInfo(tokenAutorizacion.getAuthInfo());
		deleteNegocio.getBusinessKey().add(claveNegocio);
		publishUDDI.deleteBusiness(deleteNegocio);
		
		System.out.println("El negocio con clave '" + claveNegocio + "' ha sido eliminado");
		
		return;
	}
	
	
	
	
	/**
	 * Elimina el servicio indicado del registro UDDI.
	 * @param claveServicio Clave identificativa del servicio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public void eliminarServicio(String claveServicio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException {
		
		org.uddi.api_v3.DeleteService deleteServicio = new org.uddi.api_v3.DeleteService();
		deleteServicio.setAuthInfo(tokenAutorizacion.getAuthInfo());
		deleteServicio.getServiceKey().add(claveServicio);
		this.publishUDDI.deleteService(deleteServicio);
		
		System.out.println("El servicio con clave '" + claveServicio + "' ha sido eliminado");
		
		return;
	}
	
	
	
	
	/**
	 * Obtiene todos los negocios guardados en el registro.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * @param P TRUE para sacar todos los datos de los negocios encontrados por pantalla.
	 * @return La lista con todos los negocios del registro.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public org.uddi.api_v3.BusinessList obtenerNegocios(AuthToken tokenAutorizacion, boolean P) throws DispositionReportFaultMessage, RemoteException {
		
		org.uddi.api_v3.FindBusiness findNegocio = new FindBusiness();
		
		findNegocio.setAuthInfo(tokenAutorizacion.getAuthInfo());
		
		org.uddi.api_v3.FindQualifiers fq = new FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);
        findNegocio.setFindQualifiers(fq);
        
        Name nombreParaBusqueda = new Name(UDDIConstants.WILDCARD, ""); //valor + idioma
        findNegocio.getName().add(nombreParaBusqueda); //WILDCARD busqueda general??
        
        org.uddi.api_v3.BusinessList businessList = inquiryUDDI.findBusiness(findNegocio);
        
        if (P) {
        	
        	if (businessList.getBusinessInfos() == null) {
                System.out.println("NO existe ningun negocio en la base de datos");
	        } else {
	        	List<org.uddi.api_v3.BusinessInfo> list = businessList.getBusinessInfos().getBusinessInfo();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("===============================================");
                    System.out.println("Business Key: " + list.get(i).getBusinessKey());
                    System.out.println("Name: " + list.get(i).getName());

                    System.out.println("Description: " + list.get(i).getDescription());
                    System.out.println("Services:");
                    obtenerServicios(list.get(i).getBusinessKey(), tokenAutorizacion, true);
                }
	        }
        	
        }
        
        return businessList;
	}
	
	
	
	
	/**
	 * Obtiene todos los servicios del negocio indicado.
	 * @param claveNegocio Clave identificativa del negocio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * @param P TRUE para sacar todos los datos de los servicios encontrados por pantalla.
	 * @return La lista con todos los servicios del negocio indicado.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public org.uddi.api_v3.ServiceList obtenerServicios(String claveNegocio, AuthToken tokenAutorizacion, boolean P) throws DispositionReportFaultMessage, RemoteException {
		
		org.uddi.api_v3.FindService findServicio = new FindService();
		
		findServicio.setAuthInfo(tokenAutorizacion.getAuthInfo());
		
		org.uddi.api_v3.FindQualifiers fq = new FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);
        findServicio.setFindQualifiers(fq);
        
        Name nombreParaBusqueda = new Name(UDDIConstants.WILDCARD, ""); //valor + idioma
        findServicio.getName().add(nombreParaBusqueda);
        
        org.uddi.api_v3.ServiceList serviceList = inquiryUDDI.findService(findServicio);
        
        if (serviceList == null) 	return null;
        
        if (P) {
        	List<org.uddi.api_v3.ServiceInfo> list = serviceList.getServiceInfos().getServiceInfo();
        	 for (int i = 0; i < list.size(); i++) {
                 System.out.println("-------------------------------------------");
                 System.out.println("Service Key: " + list.get(i).getServiceKey());
                 System.out.println("Owning Business Key: " + list.get(i).getBusinessKey());
                 System.out.println("Name: " + list.get(i).getName());
         }
        }
        
        return serviceList;
	}
	
	
	
	
	/**
	 * Obtiene la clave identificativa de un negocio del registro.
	 * @param nombreNegocio Nombre del negocio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro
	 * @return String con la clave del negocio deseado o NULL en el caso de que no exista ningun negocio con el nombre indicado.
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public String obtenerClaveNegocio(Name nombreNegocio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException {
		
		System.out.println("Buscando la clave del negocio '" + nombreNegocio.getValue() + "'");
		
		org.uddi.api_v3.BusinessList listaNegocios = obtenerNegocios(tokenAutorizacion, false);

		List<org.uddi.api_v3.BusinessInfo> listaInfoNegocios = listaNegocios.getBusinessInfos().getBusinessInfo();
        for (int i=0; i<listaInfoNegocios.size();i++) {
        	if (listaInfoNegocios.get(i).getName().get(0).getValue().trim().equalsIgnoreCase(nombreNegocio.getValue().trim())) {
        		System.out.println("Clave encontrada para el negocio '" + nombreNegocio.getValue() + "'");
        		return listaInfoNegocios.get(i).getBusinessKey();
        	}
        }
        
        System.out.println("NO se ha encontrado el negocio '" + nombreNegocio.getValue() + "'");
        
        return null;
        
	}
	
	
	
	
	
	/**
	 * Obtiene la clave identificativa de un servicio del registro.
	 * @param nombreServicio Nombre del servicio.
	 * @param nombreNegocio Nombre del negocio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro.
	 * @return String con la clave del servicio deseado o NULL en el caso de que no exista ningun servicio con el nombre indicado
	 * en el negocio referido.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public String obtenerClaveServicio(Name nombreServicio, Name nombreNegocio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException {
		
		System.out.println("Buscando la clave del servicio '" + nombreServicio.getValue() + "', dentro del negocio '" + nombreNegocio.getValue() + "'");
		
		org.uddi.api_v3.ServiceList listaServicios = obtenerServicios(obtenerClaveNegocio(nombreNegocio, tokenAutorizacion), tokenAutorizacion, false);

		List<org.uddi.api_v3.ServiceInfo> listaInfoServicios = listaServicios.getServiceInfos().getServiceInfo();
        for (int i=0; i<listaInfoServicios.size();i++) {
        	if (listaInfoServicios.get(i).getName().get(0).getValue().trim().equalsIgnoreCase(nombreServicio.getValue().trim())) {
        		System.out.println("Clave encontrada para el servicio '" + nombreServicio.getValue() + "'");
        		return listaInfoServicios.get(i).getServiceKey();
        	}
        }
        
        System.out.println("NO se ha encontrado el servicio '" + nombreServicio.getValue() + "'");
        
        return null;
	}
	
	
	
	
	/**
	 * Obtiene la direccion de un servicio del registro.
	 * @param claveServicio Clave identificativa del servicio.
	 * @param tokenAutorizacion Token necesario para la conexion con el registro.
	 * @return String con el endpoint del servicio deseado o NULL en el caso de que no exista ningun servicio con el id indicado.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public String obtenerDireccionServicio(String claveServicio, AuthToken tokenAutorizacion) throws DispositionReportFaultMessage, RemoteException {
		
		System.out.println("Buscando el endpoint del servicio...");
		
		org.uddi.api_v3.GetServiceDetail getServiceDetail = new GetServiceDetail();
		
		getServiceDetail.getServiceKey().add(claveServicio);
		
		getServiceDetail.setAuthInfo(tokenAutorizacion.getAuthInfo());
		
		ServiceDetail serviceDetail = inquiryUDDI.getServiceDetail(getServiceDetail);
		
        if (serviceDetail == null) 	return null;
		
        String endpoint = serviceDetail.getBusinessService().get(0).getBindingTemplates().getBindingTemplate().get(0).getAccessPoint().getValue();
        
		System.out.println("Servicio encontrado en la direccion: " + endpoint);
		
		return endpoint;
	}
	
}
