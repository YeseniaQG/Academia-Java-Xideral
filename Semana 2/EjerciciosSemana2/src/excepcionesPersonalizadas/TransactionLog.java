package excepcionesPersonalizadas;

//--- AutoCloseable ---
public class TransactionLog implements AutoCloseable {
 private boolean open = true;

 public void log(String message) {
     if (!open) throw new IllegalStateException("Log cerrado");
     System.out.println("[LOG] " + message);
 }

 @Override
 public void close() {
     this.open = false; // Marca como cerrado
     System.out.println("[LOG] TransactionLog cerrado.");
 }
}
