
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.Name;
import org.uddi.v3_service.DispositionReportFaultMessage;

public class ClienteSOAP {
	
	public static void main(String[] args) {
		try {
			init();
		} catch (ConfigurationException | RemoteException | TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String soapEndpointUrl = "http://localhost:7162/axis2/services/Foro";
	static String soapActionSubirPost = "http://localhost:7162/axis2/services/Foro/subirPost";
	static String soapActionLeerPost = "http://localhost:7162/axis2/services/Foro/leerPost";
	static String soapActionBuscarxTag = "http://localhost:7162/axis2/services/Foro/buscarXTag";
	static String soapActionBuscar = "http://localhost:7162/axis2/services/Foro/buscar";
	static String soapActionresgistrar = "http://localhost:7162/axis2/services/Foro/registrar";
	static String soapActionContar = "http://localhost:7162/axis2/services/Foro/contarPost";
	
	@SuppressWarnings("deprecation")
	public static void init() throws ConfigurationException, TransportException, DispositionReportFaultMessage, RemoteException {
		ClienteUDDI clienteuddi = new ClienteUDDI("usuario","clave");
		
		AuthToken token = clienteuddi.obtenerTokenDeAutentificacion();
        
		String ruta = clienteuddi.obtenerDireccionServicio(clienteuddi.obtenerClaveServicio(new Name("Foro",""), new Name("VigoCoffeeLovers",""), token), token);

		soapEndpointUrl = ruta;                
		soapActionSubirPost = ruta + "/subirPost";  
		soapActionLeerPost = ruta + "/leerPost";    
		soapActionBuscarxTag = ruta + "/buscarXTag";
		soapActionBuscar = ruta + "/buscar";        
		soapActionresgistrar = ruta + "/registrar"; 
		soapActionContar = ruta + "/contarPost";    
	}

	public static int subirPost(String post, String[] tags, String username, String password)
			throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement soapBodyElem = soapBody.addChildElement("subirPost", myNamespace);
		SOAPElement soapBodyPost = soapBodyElem.addChildElement("post", myNamespace);
		soapBodyPost.addTextNode(post);

		SOAPElement soapBodyusername = soapBodyElem.addChildElement("username", myNamespace);
		soapBodyusername.addTextNode(username);

		SOAPElement soapBodyPassword = soapBodyElem.addChildElement("password", myNamespace);
		soapBodyPassword.addTextNode(password);

		for (String string : tags) {
			System.out.println("Tag añadido: " + string);
			SOAPElement soapBodytags = soapBodyElem.addChildElement("tags", myNamespace);
			soapBodytags.addTextNode(string);
		}

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionSubirPost);

		soapMessage.saveChanges();
		System.out.println("Solicitud enviada: ");
		try {
			soapMessage.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (Exception e) {
			System.out.println("Solicitud cancelada por el servicio: Username no reconocido o contrtaseña incorrecta");
			e.printStackTrace();
			return -6;
		}
		System.out.println();

		return response2int(soapResponse);
	}

	public static Post leerPost(String PostID) throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement soapBodyElem = soapBody.addChildElement("leerPost", myNamespace);
		SOAPElement soapBodyPostID = soapBodyElem.addChildElement("postID", myNamespace);
		soapBodyPostID.addTextNode(PostID);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionLeerPost);

		soapMessage.saveChanges();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		return response2Post(soapResponse);
	}

	public static ArrayList<Integer> buscar(String busqueda) throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement soapBodyElem = soapBody.addChildElement("buscar", myNamespace);
		SOAPElement soapBodyPostID = soapBodyElem.addChildElement("busqueda", myNamespace);
		soapBodyPostID.addTextNode(busqueda);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionBuscar);

		soapMessage.saveChanges();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		return response2arrint(soapResponse);
	}

	public static ArrayList<Integer> buscarXtag(String tag) throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement soapBodyElem = soapBody.addChildElement("buscarXTag", myNamespace);
		SOAPElement soapBodyPostID = soapBodyElem.addChildElement("busqueda", myNamespace);
		soapBodyPostID.addTextNode(tag);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionBuscarxTag);

		soapMessage.saveChanges();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		return response2arrint(soapResponse);
	}

	public static int registrar(String username, String password) throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement soapBodyElem = soapBody.addChildElement("registrar", myNamespace);
		SOAPElement soapBodyPostID = soapBodyElem.addChildElement("username");
		soapBodyPostID.addTextNode(username);

		soapBodyPostID = soapBodyElem.addChildElement("password", myNamespace);
		soapBodyPostID.addTextNode(password);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionresgistrar);

		soapMessage.saveChanges();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		return response2int(soapResponse);
	}

	public static int contarPost() throws UnsupportedOperationException, SOAPException {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Create SOAP Message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		// Build SOAP Message
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "myNamespace";
		String myNamespaceURI = "http://ws.apache.org/axis2";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		soapBody.addChildElement("contarPost", myNamespace);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionresgistrar);

		soapMessage.saveChanges();

		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
		System.out.println("Respuesta recibida: ");
		try {
			soapResponse.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

		return response2int(soapResponse);
	}

	private static Post response2Post(SOAPMessage soapResponse) throws SOAPException {
		Post Post;
		String post = null, autor = null;
		ArrayList<String> tags = new ArrayList<String>();
		Iterator<?> it = soapResponse.getSOAPBody().getChildElements();
		while (it.hasNext()) {
			SOAPElement el1 = (SOAPElement) it.next();
			SOAPElement el2 = (SOAPElement) el1.getFirstChild();
			if (el2.getNodeName().equals("return")) {
				Iterator<?> it2 = el2.getChildElements();
				while (it2.hasNext()) {
					SOAPElement object = (SOAPElement) it2.next();
					String nombre = object.getNodeName();
					switch (nombre) {
					case "post":
						post = object.getTextContent();
						break;
					case "autor":
						autor = object.getTextContent();
						break;
					case "tags":
						tags.add(object.getTextContent());
						break;
					default:
						break;
					}
				}
				Post = new Post(post, tags, autor);
				return Post;
			}
		}

		return null;
	}

	private static int response2int(SOAPMessage soapResponse) throws SOAPException {
		Iterator<?> it = soapResponse.getSOAPBody().getChildElements();
		while (it.hasNext()) {
			SOAPElement el1 = (SOAPElement) it.next();
			SOAPElement el2 = (SOAPElement) el1.getFirstChild();
			if (el2.getNodeName().equals("return")) {
				return Integer.parseInt(el2.getTextContent());
			}
		}
		return -10;
	}

	private static ArrayList<Integer> response2arrint(SOAPMessage soapResponse) throws SOAPException {
		ArrayList<Integer> respuesta = new ArrayList<Integer>();
		Iterator<?> it = soapResponse.getSOAPBody().getChildElements();
		while (it.hasNext()) {
			SOAPElement el1 = (SOAPElement) it.next();
			Iterator<?> it2 = el1.getChildElements();
			while (it2.hasNext()) {
				SOAPElement el2 = (SOAPElement) it2.next();
				System.out.println(el2.getTextContent());
				try {
					if (el2.getNodeName().equals("return")) {
						respuesta.add(Integer.parseInt(el2.getTextContent()));
					}
				} catch (NullPointerException e) {
				}
			}
		}
		return respuesta;
	}

//	public static Post leerPostWSAddressing(String PostID, String destino) throws SOAPException, UnknownHostException, IOException {
//		// Create SOAP Connection
//		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//		// Create SOAP Message
//		MessageFactory messageFactory = MessageFactory.newInstance();
//		SOAPMessage soapMessage = messageFactory.createMessage();
//
//		// Build SOAP Message
//		SOAPPart soapPart = soapMessage.getSOAPPart();
//
//		String myNamespace = "myNamespace";
//		String myNamespaceURI = "http://ws.apache.org/axis2";
//		String wsaNamespace = "wsa";
//		String wsaNamespaceURI = "http://www.w3.org/2005/08/addressing";
//		
//		
//
//		// SOAP Envelope
//		SOAPEnvelope envelope = soapPart.getEnvelope();
//		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
//
//		// SOAP Body
//		SOAPBody soapBody = envelope.getBody();
//
//		SOAPElement soapBodyElem = soapBody.addChildElement("leerPost", myNamespace);
//		SOAPElement soapBodyPostID = soapBodyElem.addChildElement("postID", myNamespace);
//		soapBodyPostID.addTextNode(PostID);
//
//		MimeHeaders headers = soapMessage.getMimeHeaders();
//		headers.addHeader("SOAPAction", soapActionLeerPost);
//		headers.addHeader("ReplyTo", "localhost:7163");
//
//		soapMessage.saveChanges();
//		
//
//		// Send SOAP Message to SOAP Server
//		SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
//		System.out.println("Respuesta recibida: ");
//		try {
//			soapResponse.writeTo(System.out);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println();
//
//		return response2Post(soapResponse);
//	}

}
