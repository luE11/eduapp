package pra.luis.eduapp.eduapp.utils;

public class EntityWithExistingFieldException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EntityWithExistingFieldException(String msg) {
		super(msg);
	}

	public EntityWithExistingFieldException(String msg, Throwable cause) {
		super(msg, cause);
	}

}