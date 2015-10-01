package org.jboss.jdf.example.ticketmonster.monitoring;

import it.polimi.tower4clouds.java_app_dc.Property;
import it.polimi.tower4clouds.java_app_dc.Registry;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.jdf.example.ticketmonster.rest.BaseEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ConfigurationInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Registry.stopMonitoring();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Map<Property, String> applicationProperties = new HashMap<Property, String>();
		applicationProperties.put(Property.ID, loadVariable("MODACLOUDS_TOWER4CLOUDS_INTERNAL_COMPONENT_ID", "_ic1"));
		applicationProperties.put(Property.TYPE, loadVariable("MODACLOUDS_TOWER4CLOUDS_INTERNAL_COMPONENT_TYPE","WebApp"));
		applicationProperties.put(Property.VM_ID, loadVariable("MODACLOUDS_TOWER4CLOUDS_VM_ID", "_vm1"));
		applicationProperties.put(Property.VM_TYPE, loadVariable("MODACLOUDS_TOWER4CLOUDS_VM_TYPE","WebVM"));
		applicationProperties.put(Property.CLOUD_PROVIDER_ID, loadVariable("MODACLOUDS_TOWER4CLOUDS_CLOUD_PROVIDER_ID", "_cp1"));
		applicationProperties.put(Property.CLOUD_PROVIDER_TYPE, "IaaS");
		String mpIP = loadVariable("MODACLOUDS_TOWER4CLOUDS_MANAGER_IP", "localhost");
		String mpPort = loadVariable("MODACLOUDS_TOWER4CLOUDS_MANAGER_PORT", "8170");
		Registry.initialize(mpIP, Integer.parseInt(mpPort), applicationProperties,
				BaseEntityService.class.getPackage().getName(), false, true);
		Registry.startMonitoring();
	}

	public static String loadVariable(String variableName, String defaultValue) {
		if (System.getenv().containsKey(variableName)) {
			return System.getenv(variableName);
		}
		return defaultValue;
	}

}
