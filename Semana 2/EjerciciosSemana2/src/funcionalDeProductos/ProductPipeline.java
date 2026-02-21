package funcionalDeProductos;
import java.util.*;
import java.util.function.*;

class ProductPipeline {
    private Predicate<Product> filter = p -> true; // Empieza aceptando todo
    private Function<Product, String> transform = Product::toDisplayString;

    public ProductPipeline where(Predicate<Product> predicate) {
        // Encadenamos el filtro actual con el nuevo usando AND
        this.filter = this.filter.and(predicate);
        return this; // Retornamos 'this' para poder encadenar métodos (Fluent API)
    }

    public ProductPipeline transform(Function<Product, String> fn) {
        this.transform = fn;
        return this;
    }

    public void forEach(List<Product> products, Consumer<String> action) {
        for (Product p : products) {
            // Si el producto cumple todos los filtros acumulados
            if (filter.test(p)) {
                // Aplicamos la transformación y luego la acción (imprimir)
                action.accept(transform.apply(p));
            }
        }
    }

    public long count(List<Product> products) {
        long total = 0;
        for (Product p : products) {
            if (filter.test(p)) total++;
        }
        return total;
    }
}
