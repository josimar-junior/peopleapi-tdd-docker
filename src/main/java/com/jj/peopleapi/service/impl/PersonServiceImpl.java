package com.jj.peopleapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jj.peopleapi.model.BtpPerson;
import com.jj.peopleapi.repository.PersonRepository;
import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.service.PersonService;
import com.jj.peopleapi.service.exception.ExistingCPFException;
import com.jj.peopleapi.service.exception.PersonNotFoundException;
import com.jj.peopleapi.vo.PersonVO;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;

	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public PersonVO save(PersonVO personVO) throws ExistingCPFException {

		try {
			BtpPerson btpPerson = personVO.toBtp();
			
			verifyIfPersonExistsWithCpf(btpPerson);
			
			btpPerson = personRepository.save(btpPerson);
			
			if(btpPerson != null) {
				return btpPerson.toVO();
			}
		} catch(Exception e) {
			//Salvar log
			throw e;
		}
		return null;
	}

	@Override
	public void update(PersonVO personVO) throws ExistingCPFException, PersonNotFoundException {
		findById(personVO.getId());
		BtpPerson btpPerson = personVO.toBtp();
		verifyIfPersonExistsWithCpf(btpPerson);
		personRepository.save(btpPerson);
	}

	private void verifyIfPersonExistsWithCpf(final BtpPerson person) throws ExistingCPFException {
		Optional<PersonVO> personOption = findByCpf(person.getCpf());

		if (personOption.isPresent() && (person.isNew() || isUpdatingToADifferentPerson(person, personOption))) {
			throw new ExistingCPFException("Existing CPF");
		}
	}

	private boolean isUpdatingToADifferentPerson(BtpPerson person, Optional<PersonVO> personOption) {
		return person.alreadyExist() && !personOption.get().equals(person);
	}

	@Override
	public PersonVO findById(Long id) throws PersonNotFoundException {
		return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found")).toVO();
	}

	@Override
	public List<PersonVO> findAll() {
		List<BtpPerson> people = personRepository.findAll();
		List<PersonVO> peopleVO = new ArrayList<>();
		people.forEach(person -> peopleVO.add(person.toVO()));
		return peopleVO;
	}

	@Override
	public Optional<PersonVO> findByCpf(String cpf) {
		Optional<BtpPerson> btpPerson = personRepository.findByCpf(cpf);
		if(btpPerson.isPresent()) {
			return Optional.of(btpPerson.get().toVO());
		}
		return Optional.empty();
	}
	
	@Override
	public void delete(Long id) throws PersonNotFoundException {
		findById(id);
		personRepository.deleteById(id);
	}

	@Override
	public List<PersonVO> filter(PersonFilter filter) {
		return personRepository.filter(filter);
	}
}
