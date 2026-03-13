package com.biblioteca.service;

import com.biblioteca.model.Prestamo;
import java.util.List;

public interface PrestamoService {
    Prestamo registrarPrestamo(Long idUsuario, Long idEmpleado, Long idLibro);
    Prestamo registrarDevolucion(Long idPrestamo);
    Prestamo obtenerPrestamoPorId(Long id);
    List<Prestamo> obtenerTodosLosPrestamos();
    List<Prestamo> obtenerPrestamosPorUsuario(Long idUsuario);
}
