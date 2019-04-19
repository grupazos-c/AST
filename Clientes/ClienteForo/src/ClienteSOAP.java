import java.io.IOException;
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

import org.w3c.dom.NodeList;

public class ClienteSOAP {

	public static void main(String[] args) {
		try {
//			ArrayList<String> tags = new ArrayList<String>();
			String[] tags = { "Cliente", "SOAP" };
//			tags.add("Cliente");
//			tags.add("SOAP");
//			tags.add("vivaa");

//			System.out.println(leerPost("1"));
			System.out.println(subirPost("Post generado por el cliente SOAP", tags, "Cliente", "1234"));
//			System.out.println(contarPost());

		} catch (UnsupportedOperationException | SOAPException e) {
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
		} catch (IOException e) {
			e.printStackTrace();
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

	//TODO no funciona correctamente
	private static ArrayList<Integer> response2arrint(SOAPMessage soapResponse) throws SOAPException {
		ArrayList<Integer> respuesta = new ArrayList<Integer>();
		Iterator<?> it = soapResponse.getSOAPBody().getChildElements();
		while (it.hasNext()) {
			SOAPElement el1 = (SOAPElement) it.next();
			Iterator<?> it2 = el1.getChildElements();
			while (it.hasNext()) {
				SOAPElement el2 = (SOAPElement) it2.next();
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

}
