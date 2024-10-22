package com.mercadona.demo.service;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Optional<Producto> obtenerProductoPorEan(String ean) {
        // Validar EAN numérico y longitud
        validarEan(ean);

        // Determinar destino del producto
        String destino = determinarDestino(ean);

        // Obtener la segunda parte del EAN para código de producto y proveedor
        String segundaParte = ean.substring(7, 12);
        String primeraParte = ean.substring(0, 7);

        // Buscar el producto en la base de datos
        Optional<Producto> producto = productoRepository.findByEan(ean);

        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setDestino(destino);
            p.setCodigoProducto(segundaParte);
            p.setProveedor(determinarProveedor(primeraParte));
        }

        return producto;
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

    @Override
    public Producto guardarProducto(Producto producto) {
        // Validar EAN
        validarEan(producto.getEan());

        // Derivar proveedor, código de producto y destino antes de guardar
        String destino = determinarDestino(producto.getEan());
        String codigoProducto = producto.getEan().substring(7, 12);
        String proveedor = determinarProveedor(producto.getEan().substring(0, 7));

        producto.setCodigoProducto(codigoProducto);
        producto.setDestino(destino);
        producto.setProveedor(proveedor);

        return productoRepository.save(producto); // Guardar producto con datos completos
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        // Verificar que el producto existe antes de actualizar
        Optional<Producto> productoExistente = productoRepository.findById(producto.getId());
        if (productoExistente.isPresent()) {
            // Recalcular proveedor, código de producto y destino si se actualiza el EAN
            validarEan(producto.getEan());

            producto.setCodigoProducto(producto.getEan().substring(7, 12));
            producto.setDestino(determinarDestino(producto.getEan()));
            producto.setProveedor(determinarProveedor(producto.getEan().substring(0, 7)));

            return productoRepository.save(producto);  // Guardar la actualización
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

    @Override
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }
}
