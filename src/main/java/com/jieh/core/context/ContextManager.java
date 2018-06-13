package com.jieh.core.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jieh.core.bean.ComponentConfig;
import com.jieh.core.bean.ComponentsConfig;
import com.jieh.core.bean.PropertyConfig;

/**
 * create bean and put them into instatnceMap
 * @author a620824
 *
 */
public class ContextManager {
	
	private static Map<String, Object> instanceMap = new HashMap<String, Object>();
	private static Map<String,ComponentConfig> componentConfigListMap = new HashMap<String,ComponentConfig>();
	
	public static void createComponent(ComponentsConfig config) {
		for(ComponentConfig componentConfig : config.getComponent()) {
			componentConfigListMap.put(componentConfig.getName(), componentConfig);
		}
		for(String key :  componentConfigListMap.keySet()) {
			createBean(componentConfigListMap.get(key));
		}
	}
	
	public static Object createBean(ComponentConfig componentConfig) {
		String componentName = componentConfig.getName();
		String componentClazz = componentConfig.getClazz();
		List<PropertyConfig> propertyConfigs = componentConfig.getProperty();
		Object bean = instanceMap.get(componentName);
		try {
			if(bean == null) {
				Class<?> clazz = Class.forName(componentClazz);
				bean = clazz.newInstance();
				instanceMap.put(componentName, bean);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (propertyConfigs != null) {
			injectBean(bean, propertyConfigs);
		}
		return bean;
	}
	
	private static void injectBean(Object bean, List<PropertyConfig> propertyConfigs) {
		for(PropertyConfig propertyConfig : propertyConfigs) {
			String content = propertyConfig.getContent();
			String name = propertyConfig.getName().getLocalPart();
			try {
				Field field = bean.getClass().getDeclaredField(name);
				field.setAccessible(true);
				Object refBean = instanceMap.get(content);
				if(null == refBean) {
					refBean = createBean(componentConfigListMap.get(content));
				}
				field.set(bean, refBean);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Object getObject(String name) {
		return instanceMap.get(name);
	}
	
}
