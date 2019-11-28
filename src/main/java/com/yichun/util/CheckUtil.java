package com.yichun.util;

import java.util.stream.Stream;

import com.yichun.exceptions.CheckException;

public class CheckUtil {
	
	private static final String[] INVALID_NAMES = {"admin", "yichun"};
	
	/**
	 * 校驗名字, 不成功拋出異常
	 * @param Name
	 */
	public static void checkName(String value) {
		Stream.of(INVALID_NAMES).filter( name -> name.equalsIgnoreCase(value))
				.findAny().ifPresent( name -> { 
					throw new CheckException("name", value);
				});
		
	}

}
