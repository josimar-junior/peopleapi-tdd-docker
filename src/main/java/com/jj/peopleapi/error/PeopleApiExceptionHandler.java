package com.jj.peopleapi.error;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jj.peopleapi.error.ErrorResponse.ApiError;
import com.jj.peopleapi.service.exception.ExistingCPFException;
import com.jj.peopleapi.service.exception.PersonNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class PeopleApiExceptionHandler {
	
    private static final Logger LOG = LoggerFactory.getLogger(PeopleApiExceptionHandler.class);

    @Autowired
    private MessageSource apiErrorMessageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception
            , Locale locale) {
        Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

        List<ApiError> apiErrors = errors
                .map(ObjectError::getDefaultMessage)
                .map(code -> toApiError(code, locale, exception.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
        return ResponseEntity.badRequest().body(errorResponse);
    }
	
	@ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException exception, Locale locale) {
        final String errorCode = "person-not-found";
        final HttpStatus status = HttpStatus.NOT_FOUND;

        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
	
	@ExceptionHandler(ExistingCPFException.class)
    public ResponseEntity<ErrorResponse> handleExistingCPFException(ExistingCPFException exception, Locale locale) {
        final String errorCode = "existing-cpf";
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getMessage()));
        return ResponseEntity.badRequest().body(errorResponse);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
		LOG.error("Error not expected", exception);
		final String errorCode = "error-1";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getMessage()));
		return ResponseEntity.status(status).body(errorResponse);
	}
	
	private ApiError toApiError(String code, Locale locale, Object... args) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            LOG.error("Could not find any message for {} code under {} locale", code, locale);
            message = args[0].toString();
        }

        return new ApiError(code, message);
    }
}
