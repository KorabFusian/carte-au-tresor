package me.jeremi.carte;

/**
 * Exception spécifique à un mouvement d'aventurier invalide.
 * @author Jeremi Friggit(@KorabFusian)
 */
class InvalidMoveException extends Exception {
    public InvalidMoveException(String errorMessage) {
        super(errorMessage);
    }
}
