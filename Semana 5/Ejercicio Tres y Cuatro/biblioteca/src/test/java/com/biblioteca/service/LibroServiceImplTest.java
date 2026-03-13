package com.biblioteca.service;

import com.biblioteca.exception.*;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.enums.Genero;
import com.biblioteca.model.enums.Rol;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;
    private Usuario empleado;

    @BeforeEach
    void setUp() {
        empleado = new Usuario();
        empleado.setId(1L);
        empleado.setActivo(true);
        empleado.setRol(Rol.ROLE_EMPLOYEE);

        libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Clean Code");
        libro.setIsbn("978-0132350884");
        libro.setAutor("Robert C. Martin");
        libro.setGenero(Genero.TECNOLOGIA);
        libro.setCantidad(5);
        libro.setDisponible(true);
        libro.setRegistradoPor(empleado);
    }

    @Test
    void registrarLibro_exitoso() {
        when(libroRepository.existsByIsbn(libro.getIsbn())).thenReturn(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        Libro resultado = libroService.registrarLibro(libro, 1L);

        assertNotNull(resultado);
        assertEquals("Clean Code", resultado.getTitulo());
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void registrarLibro_isbnDuplicado_lanzaIsbnDuplicadoException() {
        when(libroRepository.existsByIsbn(libro.getIsbn())).thenReturn(true);

        assertThrows(IsbnDuplicadoException.class, () -> libroService.registrarLibro(libro, 1L));
        verify(libroRepository, never()).save(any());
    }

    @Test
    void registrarLibro_empleadoInactivo_lanzaEmpleadoNoValidoException() {
        empleado.setActivo(false);
        when(libroRepository.existsByIsbn(libro.getIsbn())).thenReturn(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(empleado));

        assertThrows(EmpleadoNoValidoException.class, () -> libroService.registrarLibro(libro, 1L));
    }

    @Test
    void registrarLibro_empleadoSinRol_lanzaEmpleadoNoValidoException() {
        empleado.setRol(Rol.ROLE_USER);
        when(libroRepository.existsByIsbn(libro.getIsbn())).thenReturn(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(empleado));

        assertThrows(EmpleadoNoValidoException.class, () -> libroService.registrarLibro(libro, 1L));
    }

    @Test
    void obtenerLibroPorId_exitoso() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.obtenerLibroPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerLibroPorId_noExiste_lanzaLibroNotFoundException() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.obtenerLibroPorId(99L));
    }

    @Test
    void obtenerTodosLosLibros_exitoso() {
        when(libroRepository.findAll()).thenReturn(List.of(libro));

        List<Libro> resultado = libroService.obtenerTodosLosLibros();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void actualizarLibro_exitoso() {
        Libro actualizado = new Libro();
        actualizado.setTitulo("Clean Code 2nd Edition");
        actualizado.setIsbn("978-0132350884");
        actualizado.setAutor("Robert C. Martin");
        actualizado.setGenero(Genero.TECNOLOGIA);
        actualizado.setCantidad(3);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(actualizado);

        Libro resultado = libroService.actualizarLibro(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Clean Code 2nd Edition", resultado.getTitulo());
    }

    @Test
    void actualizarLibro_noExiste_lanzaLibroNotFoundException() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.actualizarLibro(99L, libro));
    }

    @Test
    void desactivarLibro_exitoso() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        libroService.desactivarLibro(1L);

        assertFalse(libro.isDisponible());
        verify(libroRepository).save(libro);
    }

    @Test
    void desactivarLibro_noExiste_lanzaLibroNotFoundException() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.desactivarLibro(99L));
    }
}
