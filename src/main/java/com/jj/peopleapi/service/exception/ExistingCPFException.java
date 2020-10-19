package com.jj.peopleapi.service.exception;

public class ExistingCPFException extends Exception {

	private static final long serialVersionUID = -7973253592210464475L;

	public ExistingCPFException(String msg) {
		super(msg);
	}
}
