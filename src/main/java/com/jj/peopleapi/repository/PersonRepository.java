package com.jj.peopleapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jj.peopleapi.model.BtpPerson;
import com.jj.peopleapi.repository.person.PersonRepositoryQuery;

public interface PersonRepository extends JpaRepository<BtpPerson, Long>, PersonRepositoryQuery {

	Optional<BtpPerson> findByCpf(String cpf);
}
