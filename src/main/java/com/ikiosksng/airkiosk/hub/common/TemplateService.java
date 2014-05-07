package com.ikiosksng.airkiosk.hub.common;

import javax.servlet.ServletContext;

public interface TemplateService {
	void setMockContext(ServletContext servletContext);
	void setTemplate(String templateName);
	String getOutputAsString(DataModel dataModel);
}
