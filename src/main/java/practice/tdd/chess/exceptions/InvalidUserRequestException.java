package practice.tdd.chess.exceptions;

public class InvalidUserRequestException extends Exception {
    public InvalidUserRequestException() {
    }

    public InvalidUserRequestException(String message) {
        super(message);
    }
}
