package com.jj.peopleapi.repository.person;

import java.util.List;

import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.vo.PersonVO;

public interface PersonRepositoryQuery {

	List<PersonVO> filter(PersonFilter filter);
}
