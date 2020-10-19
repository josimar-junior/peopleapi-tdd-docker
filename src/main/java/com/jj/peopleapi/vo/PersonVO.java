package com.jj.peopleapi.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.jj.peopleapi.model.BtpPerson;

public class PersonVO {

	private Long id;
	
	@NotBlank(message = "person-1")
	private String name;
	
	@CPF(message = "person-2")
	@NotBlank(message = "person-3")
	private String cpf;
	
	@Email(message = "person-4")
	private String email;
	
	public PersonVO() {}
	
	public PersonVO(String name, String cpf, String email) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
	}
	
	public PersonVO(Long id, String name, String cpf, String email) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BtpPerson toBtp() {
		BtpPerson btp = new BtpPerson();
		
		btp.setId(id);
		btp.setName(name);
		btp.setCpf(cpf);
		btp.setEmail(email);
		
		return btp;
	}
}
