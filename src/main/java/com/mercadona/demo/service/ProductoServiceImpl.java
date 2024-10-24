package com.mercadona.demo.service;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.repository.ProductoRepository;
import com.mercadona.demo.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Cacheable(value="productos", key="#ean")
    public Optional<Producto> obtenerProductoPorEan(String ean) {
        validarEan(ean);
        String destino = determinarDestino(ean);
        String segundaParte = ean.substring(7, 12);
        String primeraParte = ean.substring(0, 7);
        Optional<Producto> producto = productoRepository.findByEan(ean);

        if (!producto.isPresent()) {
            throw new EntityNotFoundException("Producto con EAN " + ean + " no encontrado.");
        }

        Producto p = producto.get();
        p.setDestino(destino);
        p.setCodigoProducto(segundaParte);
        p.setProveedor(determinarProveedor(primeraParte));

        return producto;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        validarEan(producto.getEan());
        String destino = determinarDestino(producto.getEan());
        String codigoProducto = producto.getEan().substring(7, 12);
        String proveedor = determinarProveedor(producto.getEan().substring(0, 7));
        producto.setCodigoProducto(codigoProducto);
        producto.setDestino(destino);
        producto.setProveedor(proveedor);

        try {
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto: " + e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value = "productos",  allEntries = true)
    public Producto actualizarProducto(Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(producto.getId());
        if (productoExistente.isPresent()) {
            validarEan(producto.getEan());
            producto.setCodigoProducto(producto.getEan().substring(7, 12));
            producto.setDestino(determinarDestino(producto.getEan()));
            producto.setProveedor(determinarProveedor(producto.getEan().substring(0, 7)));

            try {
                return productoRepository.save(producto);
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar el producto: " + e.getMessage(), e);
            }
        } else {
            throw new EntityNotFoundException("Producto con ID " + producto.getId() + " no encontrado.");
        }
    }

    @Override
    @CacheEvict(value = "productos",  allEntries = true)  
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            try {
                productoRepository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar el producto con ID " + id + ": " + e.getMessage(), e);
            }
        } else {
            throw new EntityNotFoundException("Producto con ID " + id + " no encontrado.");
        }
    }

    @Override
    @Cacheable(value = "productos")  
    public List<Producto> obtenerProductos() {
        try {
            return productoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de productos: " + e.getMessage(), e);
        }
    }

    private void validarEan(String ean) {
        if (!ean.matches("\\d+")) {
            throw new IllegalArgumentException("El EAN debe contener solo números.");
        }
        if (ean.length() != 13) {
            throw new IllegalArgumentException("El EAN debe tener 13 dígitos.");
        }
    }

    private String determinarDestino(String ean) {
        char lastDigitChar = ean.charAt(ean.length() - 1);
        int lastDigit = Character.getNumericValue(lastDigitChar);

        switch (lastDigit) {
            case 0: return "Colmenas";
            case 1: case 2: case 3: case 4: case 5: return "Tiendas Mercadona España";
            case 6: return "Tiendas Mercadona Portugal";
            case 8: return "Almacenes";
            case 9: return "Oficinas Mercadona";
            default: throw new IllegalArgumentException("El EAN tiene un código de destino inválido.");
        }
    }

    private String determinarProveedor(String primeraParte) {
        if ("8437008".equals(primeraParte)) {
            return "Hacendado";
        }
        return "Proveedor Desconocido";
    }
}
