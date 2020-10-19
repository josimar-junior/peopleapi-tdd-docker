package com.jj.peopleapi.service;

import java.util.List;
import java.util.Optional;

import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.service.exception.ExistingCPFException;
import com.jj.peopleapi.service.exception.PersonNotFoundException;
import com.jj.peopleapi.vo.PersonVO;

public interface PersonService {

	PersonVO save(PersonVO personVO) throws ExistingCPFException;
	PersonVO findById(Long id) throws PersonNotFoundException;
	Optional<PersonVO> findByCpf(String cpf);
	void update(PersonVO personVO) throws ExistingCPFException, PersonNotFoundException;
	List<PersonVO> findAll();
	void delete(Long id) throws PersonNotFoundException;
	List<PersonVO> filter(PersonFilter filter);
}
