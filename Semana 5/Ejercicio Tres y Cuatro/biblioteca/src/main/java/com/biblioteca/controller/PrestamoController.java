package com.biblioteca.controller;

import com.biblioteca.model.Prestamo;
import com.biblioteca.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> registrar(
            @RequestParam Long idUsuario,
            @RequestParam Long idEmpleado,
            @RequestParam Long idLibro) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestamoService.registrarPrestamo(idUsuario, idEmpleado, idLibro));
    }

    @PutMapping("/{id}/devolucion")
    public ResponseEntity<Prestamo> devolucion(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPrestamoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerTodos() {
        return ResponseEntity.ok(prestamoService.obtenerTodosLosPrestamos());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Prestamo>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorUsuario(idUsuario));
    }
}
