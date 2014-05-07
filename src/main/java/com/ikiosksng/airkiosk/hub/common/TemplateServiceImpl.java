package com.ikiosksng.airkiosk.hub.common;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService, ServletContextAware{

	public static final String TEMPLATE_LOCATION= "/templates/";
	Configuration cfg = new Configuration();
	Template template;
	
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		cfg.setServletContextForTemplateLoading(servletContext, TEMPLATE_LOCATION);
	}

	@Override
	public void setTemplate(String templateName) {
		try {
			template = cfg.getTemplate(templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMockContext(ServletContext servletContext) {
		setServletContext(servletContext);
	}

	@Override
	public String getOutputAsString(DataModel model) {
		StringWriter stringWriter = new StringWriter();
        try {
			template.process(model.getAsMap(), stringWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return stringWriter.toString();
	}
	
	
}
