package exceptions;

/**
 * Classe ShelterException
 * 
 * Exception personnalisée pour le système de refuge d'animaux.
 * Levée quand une opération invalide est tentée (ex: adoption d'un animal déjà
 * adopté).
 */
public class ShelterException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut
     */
    public ShelterException() {
        super("Erreur dans l'opération du refuge.");
    }

    /**
     * Constructeur avec message personnalisé
     * 
     * @param message Message d'erreur
     */
    public ShelterException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et cause
     * 
     * @param message Message d'erreur
     * @param cause   Cause de l'exception
     */
    public ShelterException(String message, Throwable cause) {
        super(message, cause);
    }
}
