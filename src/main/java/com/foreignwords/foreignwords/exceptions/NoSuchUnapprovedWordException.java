package com.foreignwords.foreignwords.exceptions;

public class NoSuchUnapprovedWordException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NoSuchUnapprovedWordException() {}
	public NoSuchUnapprovedWordException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
}
