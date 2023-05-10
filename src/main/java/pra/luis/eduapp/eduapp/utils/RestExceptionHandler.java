package pra.luis.eduapp.eduapp.utils;

import javax.naming.AuthenticationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	// HANDLERS

	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return generateHandlerWithMessage(HttpStatus.BAD_REQUEST, "Malformed JSON request");
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return generateHandlerWithErrorsAndMessage(HttpStatus.BAD_REQUEST, "Argument not valid", ex);
    }

	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		return generateHandlerWithMessage(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
		return generateHandlerWithMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	protected ResponseEntity<Object> handleJwtExpired(ExpiredJwtException ex){
		return generateHandlerWithMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}
	
	@ExceptionHandler(InsufficientAuthenticationException.class)
	protected ResponseEntity<Object> handleJwtExpired(InsufficientAuthenticationException ex){
		return generateHandlerWithMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleDefault(Exception ex) {
		System.err.print("UNHANDLED EXCEPTION: "+ex.getClass());
		return generateHandlerWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
	
	@ExceptionHandler(EntityWithExistingFieldException.class)
	protected ResponseEntity<Object> handleEntityWithExistingFieldException(EntityWithExistingFieldException ex) {
		return generateHandlerWithMessage(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	// Methods
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	private ResponseEntity<Object> generateHandlerWithMessage(HttpStatus httpStatus, String message){
		return buildResponseEntity(new ApiError(httpStatus, message));
	}
	
	private ResponseEntity<Object> generateHandlerWithErrorsAndMessage(HttpStatus httpStatus, String message, BindException ex){
		return buildResponseEntity(new ApiError(httpStatus, message, ex));
	}
}