package pra.luis.eduapp.eduapp.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;

class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private Map<String, List<String>> subErrors;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
		this.message = "Unexpected error";
	}

	ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	ApiError(HttpStatus status, String message, BindException ex) {
		this();
		this.status = status;
		this.message = message;
		this.subErrors = getErrorsFromException(ex);
	}
	
	private Map<String, List<String>> getErrorsFromException(BindException ex){
		List<String> subErrors = ex.getBindingResult().getFieldErrors()
                .stream().map((err) -> this.getSubErrorAsString(err)).collect(Collectors.toList());
		return getErrorsMap(subErrors);
    }
	
	private String getSubErrorAsString(FieldError error) {
		return error.getField()+": "+error.getDefaultMessage();
	}
	
	private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSubErrors(Map<String, List<String>> map) {
		this.subErrors = map;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, List<String>> getSubErrors() {
		return subErrors;
	}

}