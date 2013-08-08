package org.bigNumber.common.services.exceptions;

/**
 * 
 * @author Alok Shukla
 * @since v0.1.0
 */
public class IncompatibleCharacterException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;
	
	public IncompatibleCharacterException() {}
	
	public IncompatibleCharacterException(String msg) {
		this.msg = msg;
	}
	public void showMsg() {
		System.out.println(msg);
	}

}