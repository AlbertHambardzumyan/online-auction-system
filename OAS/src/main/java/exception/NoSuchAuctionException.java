package exception;

/**
 ** Created by Alik on 12/3/2015.
 */
public class NoSuchAuctionException extends RuntimeException {
    public NoSuchAuctionException(String string) {
        super(string);
    }
    public NoSuchAuctionException() {
        super();
    }
}

