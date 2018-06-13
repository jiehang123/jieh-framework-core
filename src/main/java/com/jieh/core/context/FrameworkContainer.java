package com.jieh.core.context;

import com.jieh.core.bean.ComponentsConfig;
import com.jieh.core.config.ConfigurationManager;

/**
 * init all bean
 * @author a620824
 *
 */
public class FrameworkContainer {
	
	private ComponentsConfig config;
	
	private FrameworkContainer(String xmlFileStr) {
		ConfigurationManager configurationManager = ConfigurationManager.getConfigurationManagerInstance();
		config = configurationManager.loadConfigurationForXml(xmlFileStr);
	}
	
	public static void init(String configStr) {
		FrameworkContainer frameworkContainer = new FrameworkContainer(configStr);
		frameworkContainer.create();
	}
	
	public void create() {
		ContextManager.createComponent(config);
	}
	
}
