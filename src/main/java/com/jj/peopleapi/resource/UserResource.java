package com.jj.peopleapi.resource;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

	@GetMapping("/user-name")
	public ResponseEntity<String> getUsername(Principal principal) {
	   return ResponseEntity.ok(principal.getName());
	}
}
