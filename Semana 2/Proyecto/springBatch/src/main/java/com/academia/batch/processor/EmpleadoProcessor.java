package com.academia.batch.processor;

import com.academia.batch.model.Empleado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// El Processor es el componente que transforma cada registro.
// Recibe un Empleado del Reader y devuelve un Empleado transformado al Writer.
public class EmpleadoProcessor implements ItemProcessor<Empleado, Empleado> {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoProcessor.class);

    @Override
    public Empleado process(Empleado empleado) {
        // Regla de negocio: nombre en mayusculas y bono del 10%
        empleado.setNombre(empleado.getNombre().toUpperCase());
        empleado.setBono(empleado.getSalario() * 0.10);

        log.info("Procesando: {}", empleado);
        return empleado;
    }
}
