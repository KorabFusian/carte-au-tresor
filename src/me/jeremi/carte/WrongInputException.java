package me.jeremi.carte;

public class WrongInputException extends Exception {
    public WrongInputException(String errorMessage) {
        super(errorMessage);
    }
}
