package com.jj.peopleapi.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jj.peopleapi.model.BtpPerson;
import com.jj.peopleapi.repository.PersonRepository;
import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.vo.PersonVO;

@Sql(value = "/inserts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository personRepository;

	private PersonFilter personFilter;

	@BeforeEach
	public void setUp() {
		personFilter = new PersonFilter();
	}

	@Test
	public void mustSearchPersonByCPF() throws Exception {
		Optional<BtpPerson> personOptional = personRepository.findByCpf("123.456.789-12");

		assertThat(personOptional.isPresent()).isTrue();

		PersonVO person = personOptional.get().toVO();
		assertThat(person.getId()).isEqualTo(1L);
		assertThat(person.getName()).isEqualTo("Josimar");
		assertThat(person.getEmail()).isEqualTo("josimar@gmail.com");
	}

	@Test
	public void mustReturnTwoRecordsInTheFilterByName() throws Exception {
		personFilter.setName("Jo");

		List<PersonVO> persons = personRepository.filter(personFilter);

		assertThat(persons.size()).isEqualTo(2);
	}

	@Test
	public void mustReturnPersonByCPF() throws Exception {
		personFilter.setCpf("123.456.789-12");

		List<PersonVO> persons = personRepository.filter(personFilter);

		assertThat(persons.get(0).getEmail()).isEqualTo("josimar@gmail.com");
	}

	@Test
	public void mustReturnThePersonByNameAndCPF() throws Exception {
		personFilter.setName("Jo");
		personFilter.setCpf("111.222.333-44");

		List<PersonVO> persons = personRepository.filter(personFilter);

		assertThat(persons.get(0).getEmail()).isEqualTo("joao@gmail.com");
	}
}
