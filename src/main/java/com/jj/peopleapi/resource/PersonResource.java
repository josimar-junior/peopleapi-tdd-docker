package com.jj.peopleapi.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.service.exception.ExistingCPFException;
import com.jj.peopleapi.service.exception.PersonNotFoundException;
import com.jj.peopleapi.service.impl.PersonServiceImpl;
import com.jj.peopleapi.vo.PersonVO;

@RestController
@RequestMapping("/people")
public class PersonResource {

	@Autowired
	private PersonServiceImpl service;
	
	@GetMapping
    public ResponseEntity<List<PersonVO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
	
	@GetMapping("filter")
	public ResponseEntity<List<PersonVO>> filter(PersonFilter filter) {
		return ResponseEntity.ok(service.filter(filter));
	}
	
	@PostMapping
    public ResponseEntity<PersonVO> save(@Valid @RequestBody PersonVO personVO) throws ExistingCPFException {
		personVO = service.save(personVO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(personVO.getId()).toUri();
		return ResponseEntity.created(uri).build();
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonVO> findById(@PathVariable Long id) throws PersonNotFoundException {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody PersonVO personVO, @PathVariable Long id) throws ExistingCPFException, PersonNotFoundException {
		personVO.setId(id);
		service.update(personVO);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws PersonNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
