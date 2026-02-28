package com.academia.batch.processor;

import com.academia.batch.model.Producto;
import com.academia.batch.model.ProductoReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Step 2 - Processor: convierte Empleado a EmpleadoReporte calculando el salario total
// Nota: el tipo de entrada (Empleado) es diferente al tipo de salida (EmpleadoReporte)
   public class ReporteProcessor implements ItemProcessor<Producto, ProductoReporte> {

    private static final Logger log = LoggerFactory.getLogger(ReporteProcessor.class);

    @Override
    public ProductoReporte process(Producto producto) {
        ProductoReporte reporte = new ProductoReporte();
        reporte.setNombreProducto(producto.getNombreProducto());
        reporte.setMarca(producto.getMarca());
        reporte.setPrecio(producto.getPrecio());
        reporte.setQuantity(producto.getQuantity());
        // Código corregido usando el método subtract
        reporte.setPrecioReal(producto.getPrecio().subtract(producto.getDescuento()));

        log.info("Step 2 - Reporte: {}", reporte);
        return reporte;
    }
}
