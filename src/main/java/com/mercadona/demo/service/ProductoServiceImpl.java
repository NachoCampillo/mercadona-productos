package com.mercadona.demo.service;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

   //@Override
    public Optional<Producto> obtenerProductoPorEan(String ean) {
        return productoRepository.findByEan(ean); // Buscar producto por EAN
    }

    // @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto); // Guardar producto
    }

    // @Override
    public Producto actualizarProducto(Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(producto.getId());
        if (productoExistente.isPresent()) {
            return productoRepository.save(producto); // Actualizar producto
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }

    // @Override
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id); // Eliminar producto
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }
}
