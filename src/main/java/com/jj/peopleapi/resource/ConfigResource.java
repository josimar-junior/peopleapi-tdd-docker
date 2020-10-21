package com.jj.peopleapi.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jj.peopleapi.config.YAMLKeycloakConfig;
import com.jj.peopleapi.config.YAMLSpringConfig;

@RestController
@RequestMapping("/config")
public class ConfigResource {

	@Autowired
	private YAMLSpringConfig yaml;
	
	@Autowired
	private YAMLKeycloakConfig keycloakConfig;
	
	@GetMapping("environment")
	public ResponseEntity<Map<String, String>> environment() {
		return ResponseEntity.ok(yaml.getProfiles());
	}
	
	@GetMapping("keycloak")
	public ResponseEntity<Map<String, String>> keycloak() {
		return ResponseEntity.ok(keycloakConfig.getKeycloak());
	}
}
