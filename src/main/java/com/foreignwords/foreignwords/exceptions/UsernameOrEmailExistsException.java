package com.foreignwords.foreignwords.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UsernameOrEmailExistsException extends DataIntegrityViolationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameOrEmailExistsException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
