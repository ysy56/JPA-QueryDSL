package com.sparta.greeypeople.common;

import lombok.Getter;

@Getter
public class StatusCommonResponse<T> {
	private Integer httpStatusCode;
	private String message;
	private T data;

	public StatusCommonResponse(int httpStatusCode, String message) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}

	public StatusCommonResponse(int httpStatusCode, String message, T data) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
		this.data = data;
	}
}
