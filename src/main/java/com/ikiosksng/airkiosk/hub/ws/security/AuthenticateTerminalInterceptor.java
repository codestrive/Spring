package com.ikiosksng.airkiosk.hub.ws.security;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.support.PayloadRootUtils;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.transform.TransformerHelper;
import org.springframework.xml.xpath.AbstractXPathTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;

import com.ikiosksng.airkiosk.hub.terminal.TerminalService;
import com.ikiosksng.airkiosk.hub.ws.ServiceConstants;

public class AuthenticateTerminalInterceptor implements EndpointInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateTerminalInterceptor.class);
	
	private TerminalService terminalService;
	private AbstractXPathTemplate xpathTemplate = new Jaxp13XPathTemplate();
    private TransformerHelper transformerHelper = new TransformerHelper();
    private boolean enabled = false;
    
	@Autowired
    public AuthenticateTerminalInterceptor(TerminalService terminalService) {
    	this.terminalService = terminalService;
    	Map<String,String> namespaces = new HashedMap();
    	namespaces.put("hub",ServiceConstants.NAME_SPACE_URI);
    	xpathTemplate.setNamespaces(namespaces);
    	
    }
    
	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint)
			throws Exception {

		if (!enabled) {
			return true;
		}
		
		WebServiceMessage request = messageContext.getRequest();
		Source payload = request.getPayloadSource();
		
		String deviceId = xpathTemplate.evaluateAsString("//hub:deviceId", payload);
		String deviceKey = xpathTemplate.evaluateAsString("//hub:deviceKey", payload);

		LOGGER.info("Authenticating device - " + deviceId);

		if (terminalService.isAuthorized(deviceId, deviceKey)) {
			LOGGER.info("Device authentication successful");
			return true;
		} else {
			LOGGER.info("Device authentication failed");

            QName payloadRootName = PayloadRootUtils.getPayloadRootQName(request.getPayloadSource(), transformerHelper);
            QName responsePayloadRoot = new QName(convertToResponse(payloadRootName.getLocalPart()),payloadRootName.getNamespaceURI());

            String repsonse = "<" + convertToResponse(payloadRootName.getLocalPart()) + " xmlns=\"" + payloadRootName.getNamespaceURI() + "\">"
            						+ "<error>" 
            							+ "<code>INVALID_DEVICE_ID_OR_KEY</code>"
            							+ "<message>Invalid deviceId of Key</message>"
            						+ "</error>"
            		+ "</" + convertToResponse(payloadRootName.getLocalPart()) + ">";
            Source source = new StringSource(repsonse);
            
            transformerHelper.transform(source, messageContext.getResponse().getPayloadResult());
            
            return false;
		}
	}

	private String convertToResponse(String requestRoot) {
		int endOfRequestName = requestRoot.indexOf("-request");
		
		return requestRoot.substring(0,endOfRequestName) + "-reponse";
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint)
			throws Exception {
		return false;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint,
			Exception ex) {
		
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
