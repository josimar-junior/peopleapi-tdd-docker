package com.jj.peopleapi.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class ErrorResponse {

	private int statusCode;
	private List<ApiError> errors;

	public ErrorResponse(int statusCode, List<ApiError> errors) {
		this.statusCode = statusCode;
		this.errors = errors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public List<ApiError> getErrors() {
		return errors;
	}

	static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponse(status.value(), errors);
	}

	static ErrorResponse of(HttpStatus status, ApiError error) {
		return of(status, Collections.singletonList(error));
	}

	@JsonAutoDetect(fieldVisibility = ANY)
	static class ApiError {
		private String code;
		private String message;

		public ApiError(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}
}
