package com.jieh.core.config;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.jieh.core.bean.ComponentsConfig;
import com.jieh.core.constants.FrameworkConstants;;

/**
 * based on xsd to load component config from xml
 * @author a620824
 *
 */
public class ConfigurationManager {
	
	private static ConfigurationManager configurationManager;
	
	private ConfigurationManager() {}
	
	public static ConfigurationManager getConfigurationManagerInstance() {
		if(configurationManager == null) {
			configurationManager = new ConfigurationManager();
		}
		return configurationManager;
	}
	
	public ComponentsConfig loadConfigurationForXml(String configfile) {
		InputStream inputStream = null;
		ComponentsConfig componentsConfig = null;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(FrameworkConstants.XSD_FILE);
			StreamSource streamSource = new StreamSource(inputStream);
			Schema schema = schemaFactory.newSchema(streamSource);
			JAXBContext jaxbContext = JAXBContext.newInstance(ComponentsConfig.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			
			inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(configfile);
			streamSource = new StreamSource(inputStream);
			componentsConfig = (ComponentsConfig)unmarshaller.unmarshal(streamSource);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return componentsConfig;
		
	}
}
