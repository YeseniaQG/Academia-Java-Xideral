package excepcionesPersonalizadas;

public class BankAccount {
    private double balance;
    private boolean locked;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.locked = false;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Monto invalido: " + amount);
        }
        this.balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Monto invalido: " + amount);
        }
        if (amount > balance) {
            // Calcula el déficit: cantidad solicitada menos el saldo actual
            throw new InsufficientBalanceException("Fondos insuficientes para retirar $" + amount, amount - balance);
        }
        this.balance -= amount;
    }

    public void transfer(BankAccount target, double amount) throws InsufficientBalanceException {
        // Uso de try-with-resources: log se cerrará automáticamente al salir del bloque
        try (TransactionLog log = new TransactionLog()) {
            this.withdraw(amount);
            log.log(String.format("Retiro de $%.2f de cuenta origen. Saldo: $%.2f", amount, this.balance));
            
            target.deposit(amount);
            log.log(String.format("Deposito de $%.2f en cuenta destino. Saldo: $%.2f", amount, target.balance));
        } 
        // No hace falta catch aquí si queremos que la excepción suba al main
    }

    public void lock() { this.locked = true; }
    public double getBalance() { return balance; }

    public static void main(String[] args) {
        BankAccount cuenta1 = new BankAccount(1000.00);
        BankAccount cuenta2 = new BankAccount(500.00);

        // Operaciones válidas
        try {
            cuenta1.deposit(500);
            System.out.printf("Deposito exitoso. Saldo: $%.2f%n", cuenta1.getBalance());

            cuenta1.withdraw(200);
            System.out.printf("Retiro exitoso. Saldo: $%.2f%n", cuenta1.getBalance());

            cuenta1.transfer(cuenta2, 300);
            System.out.printf("Transferencia exitosa. Saldo cuenta1: $%.2f, cuenta2: $%.2f%n",
                cuenta1.getBalance(), cuenta2.getBalance());
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Manejo de Errores ===");

        // Prueba de monto inválido
        try {
            cuenta1.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Prueba de fondos insuficientes con detalle de déficit
        try {
            cuenta1.withdraw(999999);
        } catch (InsufficientBalanceException e) {
            System.out.printf("Error: %s (deficit: $%.2f)%n",
                e.getMessage(), e.getDeficit());
        }
    }
}
