package com.jj.peopleapi.repository.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import com.jj.peopleapi.model.BtpPerson;
import com.jj.peopleapi.repository.filter.PersonFilter;
import com.jj.peopleapi.vo.PersonVO;

public class PersonRepositoryImpl implements PersonRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonVO> filter(PersonFilter filter) {

		StringBuilder jpql = new StringBuilder("select new com.jj.peopleapi.vo.PersonVO(p.id, p.name, p.cpf, p.email) from BtpPerson p where 1=1 ");
		Map<String, Object> parameters = new HashMap<>();
		
		setParametersIfRequired(filter, jpql, parameters);
		
		Query query = manager.createQuery(jpql.toString(), PersonVO.class);
		
		setParametersQuery(parameters, query);
		
		return query.getResultList();
	}

	private void setParametersQuery(Map<String, Object> parameters, Query query) {
		parameters.forEach((k, v) -> {
			query.setParameter(k, v);
		});
	}

	private void setParametersIfRequired(PersonFilter filter, StringBuilder jpql, Map<String, Object> parameters) {
		if(StringUtils.hasText(filter.getName())) {
			jpql.append("and lower(p.name) like :name ");
			parameters.put("name", "%" + filter.getName().toLowerCase() + "%");
		}
		
		if(StringUtils.hasText(filter.getCpf())) {
			jpql.append("and p.cpf = :cpf");
			parameters.put("cpf", filter.getCpf());
		}
	}
}
