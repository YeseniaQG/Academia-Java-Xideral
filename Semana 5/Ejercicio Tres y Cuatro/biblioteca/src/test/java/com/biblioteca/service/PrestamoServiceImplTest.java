package com.biblioteca.service;

import com.biblioteca.exception.*;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.enums.EstadoPrestamo;
import com.biblioteca.model.enums.Genero;
import com.biblioteca.model.enums.Rol;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.impl.PrestamoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock private PrestamoRepository prestamoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private LibroRepository libroRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Usuario usuario;
    private Usuario empleado;
    private Libro libro;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setActivo(true);
        usuario.setRol(Rol.ROLE_USER);

        empleado = new Usuario();
        empleado.setId(2L);
        empleado.setActivo(true);
        empleado.setRol(Rol.ROLE_EMPLOYEE);

        libro = new Libro();
        libro.setId(1L);
        libro.setDisponible(true);
        libro.setCantidad(3);
        libro.setGenero(Genero.TECNOLOGIA);

        prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setUsuario(usuario);
        prestamo.setEmpleado(empleado);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaEntregaEstimada(LocalDate.now().plusDays(10));
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
    }

    @Test
    void registrarPrestamo_exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(1L, 1L, EstadoPrestamo.ACTIVO)).thenReturn(false);
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo resultado = prestamoService.registrarPrestamo(1L, 2L, 1L);

        assertNotNull(resultado);
        verify(libroRepository).save(any(Libro.class));
        verify(prestamoRepository).save(any(Prestamo.class));
    }

    @Test
    void registrarPrestamo_usuarioNoExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> prestamoService.registrarPrestamo(99L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_usuarioInactivo_lanzaUsuarioInactivoException() {
        usuario.setActivo(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(UsuarioInactivoException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_usuarioSinRolUser_lanzaRolNoPermitidoException() {
        usuario.setRol(Rol.ROLE_EMPLOYEE);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(RolNoPermitidoException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_limiteActivos_lanzaLimitePrestamosException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(3L);

        assertThrows(LimitePrestamosException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_conRetardo_lanzaPrestamoRetrasadoException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(true);

        assertThrows(PrestamoRetrasadoException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_empleadoNoExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> prestamoService.registrarPrestamo(1L, 99L, 1L));
    }

    @Test
    void registrarPrestamo_empleadoInactivo_lanzaEmpleadoNoValidoException() {
        empleado.setActivo(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));

        assertThrows(EmpleadoNoValidoException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_empleadoEsSolicitante_lanzaEmpleadoNoValidoException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(EmpleadoNoValidoException.class, () -> prestamoService.registrarPrestamo(1L, 1L, 1L));
    }

    @Test
    void registrarPrestamo_libroNoExiste_lanzaLibroNotFoundException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 99L));
    }

    @Test
    void registrarPrestamo_libroNoDisponible_lanzaLibroNoDisponibleException() {
        libro.setDisponible(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        assertThrows(LibroNoDisponibleException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_sinStock_lanzaLibroNoDisponibleException() {
        libro.setCantidad(0);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        assertThrows(LibroNoDisponibleException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarPrestamo_libroYaPrestado_lanzaLibroYaPrestadoException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.countByUsuarioIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(0L);
        when(prestamoRepository.existsByUsuarioIdAndEstado(1L, EstadoPrestamo.RETRASADO)).thenReturn(false);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(empleado));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(1L, 1L, EstadoPrestamo.ACTIVO)).thenReturn(true);

        assertThrows(LibroYaPrestadoException.class, () -> prestamoService.registrarPrestamo(1L, 2L, 1L));
    }

    @Test
    void registrarDevolucion_exitoso() {
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo resultado = prestamoService.registrarDevolucion(1L);

        assertEquals(EstadoPrestamo.DEVUELTO, resultado.getEstado());
        assertNotNull(resultado.getFechaEntregaReal());
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void registrarDevolucion_noExiste_lanzaPrestamoNotFoundException() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.registrarDevolucion(99L));
    }

    @Test
    void obtenerPrestamoPorId_exitoso() {
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.obtenerPrestamoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerPrestamoPorId_noExiste_lanzaPrestamoNotFoundException() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.obtenerPrestamoPorId(99L));
    }

    @Test
    void obtenerTodosLosPrestamos_exitoso() {
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.obtenerTodosLosPrestamos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPrestamosPorUsuario_exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.findByUsuarioId(1L)).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.obtenerPrestamosPorUsuario(1L);

        assertFalse(resultado.isEmpty());
    }

    @Test
    void obtenerPrestamosPorUsuario_usuarioNoExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> prestamoService.obtenerPrestamosPorUsuario(99L));
    }
}
