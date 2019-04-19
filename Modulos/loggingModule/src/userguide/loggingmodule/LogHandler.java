package userguide.loggingmodule;

import java.sql.*;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.OMElementEx;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisEngine;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.util.MessageContextBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.tools.ws.wsdl.document.soap.SOAPBody;

public class LogHandler extends AbstractHandler implements Handler {

	private static final Log log = LogFactory.getLog(LogHandler.class);
	private String name;
	/**
	 * Macros de MySQL
	 */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Foro?useSSL=false";
	private static final String USER = "cliente";
	private static final String PASS = "fBys{iB198Ha";

	/**
	 * getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * InvocationResponse
	 */
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {

		/*
		 * LECTURA DEL FICHERO DE USUARIOS Y CONTRASEÑAS
		 */

		String usuario = "";
		String password = "";
		String bdPwd = "";

		Iterator it = msgContext.getEnvelope().getBody().getChildElements();

		while (it.hasNext()) {
			OMElement el1 = (OMElement) it.next();

			String nombre = el1.getLocalName();
			System.out.println("Nombre elementos: " + nombre);
			if (nombre.equals("subirPost")) {
				Iterator it2 = el1.getChildElements();
				while (it2.hasNext()) {
					OMElement el2 = (OMElement) it2.next();
					if (el2.toString().contains("username")) {
						usuario = el2.getText();
					}
					if (el2.toString().contains("password")) {
						password = el2.getText();
					}
				}
			} else {
				System.out.println("Aquí saltaría el else");
				return InvocationResponse.CONTINUE;
			}

		}
		/*
		 * if(usuario.equals("")) { return InvocationResponse.CONTINUE; }
		 */

		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();

			String comando; // comando a ejecutar en mySQL
			comando = ("select Pwd from Autores where Autor = '" + usuario + "';");

			ResultSet rs = stmt.executeQuery(comando);

			rs.next();
			bdPwd = rs.getString(1);

			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Logging:] Error en el comando SQL");
			return InvocationResponse.ABORT;
		}

		if (bdPwd.equals(password)) {
			System.out.println("[Logging:] Contraseña correcta");
			return InvocationResponse.CONTINUE;
		}
		System.out.println("[Logging:] Contraseña incorrecta");
		return InvocationResponse.ABORT;

	}

	/**
	 * revoke
	 * 
	 * @param msgContext
	 */
	public void revoke(MessageContext msgContext) {
		log.info(msgContext.getEnvelope().toString());
	}

	/**
	 * setName
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
