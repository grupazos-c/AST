package userguide.loggingmodule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHandler extends AbstractHandler implements Handler{
    private static final Log log = LogFactory.getLog(LogHandler.class);
    private String name;

    public String getName() {
        return name;
    }

    public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
       
    	/*
    	 * LECTURA DEL FICHERO DE USUARIOS Y CONTRASEÑAS
    	 * */
    	
    	ConfigurationContext ctx = msgContext.getConfigurationContext();
    	org.apache.axiom.soap.SOAPBody body = msgContext.getEnvelope().getBody();
    	String usuario = "";
    	String password = "";
    	Iterator it = msgContext.getEnvelope().getBody().getChildElements();
    	while(it.hasNext()) 
    	{
    		OMElement el1 = (OMElement) it.next();
    		Iterator it2 = el1.getChildElements();
    		while (it2.hasNext()) 
    		{
    			OMElement el2 = (OMElement) it2.next();
    			if(el2.toString().contains("usuario")) {
    				usuario = el2.getText();

    			}
    			if(el2.toString().contains("password")) {
    				password = el2.getText();

    			}    			
			}
	    }
    	
    	HashMap<String, String> mapa = userguide.loggingmodule.LoggingModule.mapa;
    	
    	if (mapa.containsKey(usuario)) {
    		String passwordOriginal = mapa.get(usuario);
    		if(!passwordOriginal.equalsIgnoreCase(password))
        		return InvocationResponse.ABORT;
		} else
    		return InvocationResponse.ABORT;
        
        return InvocationResponse.CONTINUE;        
    }

    public void revoke(MessageContext msgContext) {
        log.info(msgContext.getEnvelope().toString());
    }

    public void setName(String name) {
        this.name = name;
    }

}
