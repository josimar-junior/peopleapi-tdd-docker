package com.jj.peopleapi.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring")
public class YAMLSpringConfig {

	private Map<String, String> profiles;
	private Map<String, String> application;
	private Map<String, String> jpa;

	public Map<String, String> getProfiles() {
		return profiles;
	}

	public void setProfiles(Map<String, String> profiles) {
		this.profiles = profiles;
	}

	public Map<String, String> getApplication() {
		return application;
	}

	public void setApplication(Map<String, String> application) {
		this.application = application;
	}

	public Map<String, String> getJpa() {
		return jpa;
	}

	public void setJpa(Map<String, String> jpa) {
		this.jpa = jpa;
	}

}
