package excepcionesPersonalizadas;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message); // Pasa el mensaje al constructor de RuntimeException
    }
}