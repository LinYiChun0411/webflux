package com.yichun.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.yichun.exceptions.CheckException;

@ControllerAdvice
public class CheckAdvice {

	
	
	
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity handleBindException(WebExchangeBindException e) {
		return new ResponseEntity<String> (toStr(e), HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(CheckException.class)
	public ResponseEntity handleCheckException(CheckException e) {
		return new ResponseEntity<String> (toStr(e), HttpStatus.BAD_REQUEST);
		
	}

	private String toStr(CheckException e) {
		return e.getFieldName() + ":錯誤的值" + e.getFieldValue();
		
	}
	
	/**
	 * 把異常轉換為字串
	 * @param ex
	 * @return
	 */
	private String toStr(WebExchangeBindException ex) {
		return ex.getFieldErrors().stream().map(e-> e.getField() + ":" + e.getDefaultMessage())
		.reduce("", (s1,s2) -> s1 + "\n" + s2);
	}

}
