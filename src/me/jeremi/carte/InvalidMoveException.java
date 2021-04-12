package me.jeremi.carte;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String errorMessage) {
        super(errorMessage);
    }
}
