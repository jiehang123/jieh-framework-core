package com.jieh.sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestSax {

	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	public static void main(String[] args) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
//			builderFactory.setValidating(true);
//			builderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
//			builderFactory.setNamespaceAware(true);
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			builder.setErrorHandler(new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					System.out.println("warning");
				}
				
				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					System.out.println("fatelError");				}
				
				@Override
				public void error(SAXParseException exception) throws SAXException {
					System.out.println("saxParseException");
				}
			});
			Document doc = builder.parse(
					new File("D:\\eclipse_workspace\\jieh-framework-core\\src\\test\\resources\\jiehContext.xml"));
			Element element = doc.getDocumentElement();
			System.out.println(element);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
