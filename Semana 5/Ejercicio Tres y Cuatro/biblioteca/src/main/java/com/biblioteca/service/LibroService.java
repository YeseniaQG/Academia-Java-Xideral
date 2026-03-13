package com.biblioteca.service;

import com.biblioteca.model.Libro;
import java.util.List;

public interface LibroService {
    Libro registrarLibro(Libro libro, Long idEmpleado);
    Libro obtenerLibroPorId(Long id);
    List<Libro> obtenerTodosLosLibros();
    Libro actualizarLibro(Long id, Libro libro);
    void desactivarLibro(Long id);
}
