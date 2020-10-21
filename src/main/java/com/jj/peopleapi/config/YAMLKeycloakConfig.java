package com.jj.peopleapi.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class YAMLKeycloakConfig {

	private Map<String, String> keycloak;

	public Map<String, String> getKeycloak() {
		return keycloak;
	}

	public void setKeycloak(Map<String, String> keycloak) {
		this.keycloak = keycloak;
	}
	
}
