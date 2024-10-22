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

    @Override
    public Optional<Producto> obtenerProductoPorEan(String ean) {
        // Verificar que el EAN sea numérico
        if (!ean.matches("\\d+")) {
            throw new IllegalArgumentException("El EAN debe contener solo números.");
        }

        // Verificar que el EAN tenga 13 dígitos
        if (ean.length() != 13) {
            throw new IllegalArgumentException("El EAN debe tener 13 dígitos.");
        }

        // Extraer el último dígito para determinar el destino
        char lastDigitChar = ean.charAt(ean.length() - 1);
        int lastDigit = Character.getNumericValue(lastDigitChar);

        String destino;
        switch (lastDigit) {
            case 0:
                destino = "Colmenas";
                break;
            case 1: case 2: case 3: case 4: case 5:
                destino = "Tiendas Mercadona España";
                break;
            case 6:
                destino = "Tiendas Mercadona Portugal";
                break;
            case 8:
                destino = "Almacenes";
                break;
            case 9:
                destino = "Oficinas Mercadona";
                break;
            default:
                throw new IllegalArgumentException("El EAN tiene un código de destino inválido.");
        }

        // Buscar el producto en la base de datos
        Optional<Producto> producto = productoRepository.findByEan(ean);

        if (producto.isPresent()) {
            // Establecer el destino antes de devolver el producto
            producto.get().setDestino(destino);
        }

        return producto;
    }


    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto); // Guardar producto
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(producto.getId());
        if (productoExistente.isPresent()) {
            return productoRepository.save(producto); // Actualizar producto
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }

    @Override
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id); // Eliminar producto
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }
}
