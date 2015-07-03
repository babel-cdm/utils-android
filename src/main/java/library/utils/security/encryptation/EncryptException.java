package library.utils.security.encryptation;

public class EncryptException extends Exception {
    //Parameterless Constructor
    public EncryptException() {
    }

    //Constructor that accepts a message
    public EncryptException(String message) {
        super(message);
    }
}
