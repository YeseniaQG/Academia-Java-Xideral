package funcionalDeProductos;



// Un 'record' es una clase inmutable rápida de escribir.
record Product(String name, double price, String category, boolean inStock) {
    boolean isAvailable() { return inStock; }

    String toDisplayString() {
        return String.format("%-15s $%7.2f  %-12s [%s]",
            name, price, category, inStock ? "En stock" : "Agotado");
    }
}
