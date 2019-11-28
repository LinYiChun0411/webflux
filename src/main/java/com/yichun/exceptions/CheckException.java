package com.yichun.exceptions;

import lombok.Data;

@Data
public class CheckException extends RuntimeException{
	
	public static final long serialVersionUID = 1L;

	/**
	 * 出錯字串的名字
	 */
	private String fieldName; 
	
	/**
	 * 出錯字串的值
	 */
	private String fieldValue;
	
	public CheckException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CheckException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CheckException(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	

}
