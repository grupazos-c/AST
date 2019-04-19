import java.io.IOException;

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

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisEngine;

public class ClienteSOAP {

	public static void main(String[] args) throws UnsupportedOperationException, SOAPException, AxisFault {
		String soapEndpointUrl = "http://localhost:7162/axis2/services/Noticia";
		String soapAction = "http://localhost:7162/axis2/services/Noticia/setTitular";
		
		 // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        //Create SOAP Message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        //Build SOAP Message
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "myNamespace";
        String myNamespaceURI = "http://ws.apache.org/axis2";

	        // SOAP Envelope
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
	        
	        //SOAP Body
	        SOAPBody soapBody = envelope.getBody();
	        
	        SOAPElement soapBodyElem = soapBody.addChildElement("getTitular", myNamespace);
            //SOAPElement soapBodyPostID = soapBodyElem.addChildElement("titular", myNamespace);
            //soapBodyPostID.addTextNode("Titulo SOAP no");

            //soapBodyPostID = soapBodyElem.addChildElement("usuario", myNamespace);
           // soapBodyPostID.addTextNode("Admins");

            //soapBodyPostID = soapBodyElem.addChildElement("password", myNamespace);
           // soapBodyPostID.addTextNode("A8E84C61-2AFA");
      

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();
        
        // Send SOAP Message to SOAP Server
        MessageContext hola = new MessageContext();
        AxisEngine.receive(hola);
        //SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
        System.out.println("Respuesta recibida: ");
        System.out.println(hola);
		/*
		 * try { soapResponse.writeTo(System.out); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
        System.out.println();
	}
}
