package org.common.services.exceptions;

public class UnconcatenableException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String msg;
	
	public UnconcatenableException(String msg) {
		this.msg = msg;
	}
	
	public void showMsg() {
		System.out.println(msg);
	}

}
