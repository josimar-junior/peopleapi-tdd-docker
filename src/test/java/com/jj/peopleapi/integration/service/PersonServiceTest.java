package com.jj.peopleapi.integration.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jj.peopleapi.repository.PersonRepository;
import com.jj.peopleapi.service.PersonService;
import com.jj.peopleapi.service.exception.ExistingCPFException;
import com.jj.peopleapi.service.exception.PersonNotFoundException;
import com.jj.peopleapi.service.impl.PersonServiceImpl;
import com.jj.peopleapi.vo.PersonVO;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

	private static final String NAME = "Josimar Junior";
	private static final String CPF = "111.222.333-44";
	private static final String EMAIL = "josimar@gmail.com";

	@MockBean
	private PersonRepository personRepository;
	
	private PersonService personService;
	
	private PersonVO personVO;
	
	@BeforeEach
	public void setUp() {
		personService = new PersonServiceImpl(personRepository);
		personVO = new PersonVO(NAME, CPF, EMAIL);
		when(personRepository.findByCpf(CPF)).thenReturn(Optional.empty());
	}
	
	@Test
	public void shouldSavePerson() throws Exception {
		personService.save(personVO);
		
		verify(personRepository).save(personVO.toBtp());
	}
	
	@Test
	public void mustThrowExceptionWhenSavingPeopleWithTheSameCPF() throws ExistingCPFException {
		
		when(personRepository.findByCpf(CPF)).thenReturn(Optional.of(personVO.toBtp()));
		
		ExistingCPFException exception = assertThrows(ExistingCPFException.class, () -> personService.save(personVO));
		
		assertThat(exception.getMessage(), is("Existing CPF"));
		
	}
	
	@Test
	public void mustThrowPersonNotFoundException() {
		PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> personService.findById(1L));
		assertThat(exception.getMessage(), is("Person not found"));
	}
}
