package me.jeremi.carte;

public class WrongCarteInputException extends Exception {
    public WrongCarteInputException(String errorMessage) {
        super(errorMessage);
    }
}
