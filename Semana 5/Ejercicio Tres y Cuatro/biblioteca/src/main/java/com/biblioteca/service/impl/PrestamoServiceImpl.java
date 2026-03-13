package com.biblioteca.service.impl;

import com.biblioteca.exception.*;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.enums.EstadoPrestamo;
import com.biblioteca.model.enums.Rol;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                                UsuarioRepository usuarioRepository,
                                LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public Prestamo registrarPrestamo(Long idUsuario, Long idEmpleado, Long idLibro) {
        // Validar usuario solicitante
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id " + idUsuario + " no encontrado."));
        if (!usuario.isActivo()) {
            throw new UsuarioInactivoException("El usuario con id " + idUsuario + " no está activo.");
        }
        if (usuario.getRol() != Rol.ROLE_USER) {
            throw new RolNoPermitidoException("Solo usuarios con ROLE_USER pueden solicitar préstamos.");
        }
        if (prestamoRepository.countByUsuarioIdAndEstado(idUsuario, EstadoPrestamo.ACTIVO) >= 3) {
            throw new LimitePrestamosException("El usuario ya tiene 3 préstamos activos.");
        }
        if (prestamoRepository.existsByUsuarioIdAndEstado(idUsuario, EstadoPrestamo.RETRASADO)) {
            throw new PrestamoRetrasadoException("El usuario tiene préstamos retrasados pendientes de devolución.");
        }

        // Validar empleado autorizante
        if (idUsuario.equals(idEmpleado)) {
            throw new EmpleadoNoValidoException("El empleado autorizante no puede ser el mismo que el solicitante.");
        }
        Usuario empleado = usuarioRepository.findById(idEmpleado)
                .orElseThrow(() -> new UsuarioNotFoundException("Empleado con id " + idEmpleado + " no encontrado."));
        if (!empleado.isActivo()) {
            throw new EmpleadoNoValidoException("El empleado con id " + idEmpleado + " no está activo.");
        }

        // Validar libro
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new LibroNotFoundException("Libro con id " + idLibro + " no encontrado."));
        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException("El libro con id " + idLibro + " no está disponible.");
        }
        if (libro.getCantidad() <= 0) {
            throw new LibroNoDisponibleException("El libro con id " + idLibro + " no tiene stock disponible.");
        }
        if (prestamoRepository.existsByUsuarioIdAndLibroIdAndEstado(idUsuario, idLibro, EstadoPrestamo.ACTIVO)) {
            throw new LibroYaPrestadoException("El usuario ya tiene este libro en préstamo activo.");
        }

        // Registrar préstamo
        libro.setCantidad(libro.getCantidad() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEmpleado(empleado);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaEntregaEstimada(LocalDate.now().plusDays(10));
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo registrarDevolucion(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new PrestamoNotFoundException("Préstamo con id " + idPrestamo + " no encontrado."));

        Libro libro = prestamo.getLibro();
        libro.setCantidad(libro.getCantidad() + 1);
        libroRepository.save(libro);

        prestamo.setFechaEntregaReal(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Préstamo con id " + id + " no encontrado."));
    }

    @Override
    public List<Prestamo> obtenerTodosLosPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorUsuario(Long idUsuario) {
        usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id " + idUsuario + " no encontrado."));
        return prestamoRepository.findByUsuarioId(idUsuario);
    }
}
