package com.atguigu.exception;

public class OverSaleException extends Exception {

	private static final long serialVersionUID = 1L;

	public OverSaleException(String message) {
		super(message);
	}
}
