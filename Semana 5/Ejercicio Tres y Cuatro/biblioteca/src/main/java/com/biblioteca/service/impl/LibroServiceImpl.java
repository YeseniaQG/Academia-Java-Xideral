package com.biblioteca.service.impl;

import com.biblioteca.exception.EmpleadoNoValidoException;
import com.biblioteca.exception.IsbnDuplicadoException;
import com.biblioteca.exception.LibroNotFoundException;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.enums.Rol;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.LibroService;
import com.biblioteca.exception.UsuarioNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public LibroServiceImpl(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Libro registrarLibro(Libro libro, Long idEmpleado) {
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new IsbnDuplicadoException("El ISBN " + libro.getIsbn() + " ya está registrado.");
        }
        Usuario empleado = usuarioRepository.findById(idEmpleado)
                .orElseThrow(() -> new UsuarioNotFoundException("Empleado con id " + idEmpleado + " no encontrado."));

        if (!empleado.isActivo()) {
            throw new EmpleadoNoValidoException("El empleado con id " + idEmpleado + " no está activo.");
        }
        if (empleado.getRol() == Rol.ROLE_USER) {
            throw new EmpleadoNoValidoException("El usuario con id " + idEmpleado + " no tiene permisos para registrar libros.");
        }

        libro.setRegistradoPor(empleado);
        return libroRepository.save(libro);
    }

    @Override
    public Libro obtenerLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new LibroNotFoundException("Libro con id " + id + " no encontrado."));
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro actualizarLibro(Long id, Libro datos) {
        Libro libro = obtenerLibroPorId(id);
        libro.setTitulo(datos.getTitulo());
        libro.setIsbn(datos.getIsbn());
        libro.setAutor(datos.getAutor());
        libro.setGenero(datos.getGenero());
        libro.setCantidad(datos.getCantidad());
        return libroRepository.save(libro);
    }

    @Override
    public void desactivarLibro(Long id) {
        Libro libro = obtenerLibroPorId(id);
        libro.setDisponible(false);
        libroRepository.save(libro);
    }
}
