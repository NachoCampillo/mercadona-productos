package com.mercadona.demo.service;

import com.mercadona.demo.model.Producto;
import java.util.Optional;
import java.util.List;
public interface ProductoService {

    // Obtener un producto por su EAN
    Optional<Producto> obtenerProductoPorEan(String ean);

    // Guardar un producto en la base de datos
    Producto guardarProducto(Producto producto);

    // Actualizar un producto existente
    Producto actualizarProducto(Producto producto);

    // Eliminar un producto por su ID
    void eliminarProducto(Long id);

    List<Producto> obtenerProductos();
}
