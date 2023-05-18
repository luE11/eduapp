package pra.luis.eduapp.eduapp.utils;

public class TokenRefreshException extends Exception {

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String msg) {
        super(msg);
    }

    public TokenRefreshException(String msg, Throwable cause) {
        super(msg, cause);
    }

}