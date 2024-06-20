package com.sparta.greeypeople.common;

import lombok.Getter;

@Getter
public class StatusCommonResponse {
	private Integer httpStatusCode;
	private String message;

	public StatusCommonResponse(int httpStatusCode, String message) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}
}