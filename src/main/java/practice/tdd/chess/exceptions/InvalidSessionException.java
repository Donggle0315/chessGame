package practice.tdd.chess.exceptions;

public class InvalidSessionException extends Exception {
    public InvalidSessionException(String message) {
        super(message);
    }
}
