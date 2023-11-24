package qrcodeapi;

public class InvalidImageTypeException extends RuntimeException {
    public InvalidImageTypeException(String cause) {
        super(cause);
    }
}
