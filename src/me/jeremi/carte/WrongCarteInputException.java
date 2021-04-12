package me.jeremi.carte;

/**
 * Exception spécifique à un mauvais input lors de la création de la carte au trésor.
 * @author Jeremi Friggit(@KorabFusian)
 */
public class WrongCarteInputException extends Exception {
    public WrongCarteInputException(String errorMessage) {
        super(errorMessage);
    }
}
