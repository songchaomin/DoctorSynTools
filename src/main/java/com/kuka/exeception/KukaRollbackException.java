package com.kuka.exeception;

public class KukaRollbackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public String code;

	public String msg;

	public KukaRollbackException(String message) {
		super(message);
		msg = message;
	}

	public KukaRollbackException(String exCode, String message) {
		super(message);
		code = exCode;
		msg = message;
	}

	@Override
	public String getLocalizedMessage() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
