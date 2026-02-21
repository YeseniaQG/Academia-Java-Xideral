package com.academia.batch.processor;
import java.math.BigDecimal;

import com.academia.batch.model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// El Processor es el componente que transforma cada registro.
// Recibe un Empleado del Reader y devuelve un Empleado transformado al Writer.
public class ProductoProcessor implements ItemProcessor<Producto, Producto> {

    private static final Logger log = LoggerFactory.getLogger(ProductoProcessor.class);

    @Override
    public Producto process(Producto producto) {
        // Regla de negocio: nombre en mayusculas y bono del 10%
        producto.setNombreProducto(producto.getNombreProducto().toUpperCase());
        producto.setDescuento(producto.getPrecio().multiply(BigDecimal.valueOf(0.05)));
        
        log.info("Procesando: {}", producto);
        return producto;
    }
}
