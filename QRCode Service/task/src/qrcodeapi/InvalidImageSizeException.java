package qrcodeapi;

public class InvalidImageSizeException extends RuntimeException {
    public InvalidImageSizeException(String cause) {
        super(cause);
    }
}
