package excepcionesPersonalizadas;

public class InsufficientBalanceException extends Exception {
    private final double deficit;

    public InsufficientBalanceException(String message, double deficit) {
        super(message); // Pasa el mensaje al constructor de Exception (Checked)
        this.deficit = deficit;
    }

    public double getDeficit() { 
        return deficit; 
    }
}

