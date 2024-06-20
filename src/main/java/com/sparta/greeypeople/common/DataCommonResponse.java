package com.sparta.greeypeople.common;

import lombok.Getter;

@Getter
public class DataCommonResponse<T> {
	private Integer httpStatusCode;
	private String message;
	private T data;

	public DataCommonResponse(int httpStatusCode, String message, T data) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
		this.data = data;
	}
}