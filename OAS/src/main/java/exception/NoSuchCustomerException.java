package exception;

/**
 ** Created by Alik on 12/6/2015.
 */
public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String string) {
        super(string);
    }
    public NoSuchCustomerException() {
        super();
    }
}
