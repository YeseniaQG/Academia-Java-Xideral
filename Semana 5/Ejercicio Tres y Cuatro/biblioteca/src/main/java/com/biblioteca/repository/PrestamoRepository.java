package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import com.biblioteca.model.enums.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuarioId(Long usuarioId);
    long countByUsuarioIdAndEstado(Long usuarioId, EstadoPrestamo estado);
    boolean existsByUsuarioIdAndLibroIdAndEstado(Long usuarioId, Long libroId, EstadoPrestamo estado);
    boolean existsByUsuarioIdAndEstado(Long usuarioId, EstadoPrestamo estado);
}
