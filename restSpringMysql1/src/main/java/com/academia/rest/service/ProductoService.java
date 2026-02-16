package com.academia.rest.service;

import com.academia.rest.entity.Producto;
import com.academia.rest.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Producto> buscarPorId(Integer id) {
        return repository.findById(id);
    }
 
    public Producto crear(Producto producto) {
        if (producto.getFechaRegistro() == null) {
            producto.setFechaRegistro(LocalDate.now());
        }
        return repository.save(producto);
    }

    public Optional<Producto> actualizar(Integer id, Producto datos) {
        return repository.findById(id).map(producto -> {
            producto.setNombreProducto(datos.getNombreProducto());
            producto.setPrecio(datos.getPrecio());
            producto.setQuantity(datos.getQuantity());
            producto.setSku(datos.getSku());
            return repository.save(producto);
        });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
