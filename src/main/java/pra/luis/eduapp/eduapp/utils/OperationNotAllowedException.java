package pra.luis.eduapp.eduapp.utils;

/**
 * @author luE11 on 11/07/23
 */
public class OperationNotAllowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public OperationNotAllowedException(String msg) {
        super(msg);
    }

    public OperationNotAllowedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
