/*
package com.mercadona.demo.service;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.repository.ProductoRepository;
import com.mercadona.demo.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest{
  @Mock
  private ProductoRepository productoRepository;
  
  @InjectMocks
  private ProductoServiceImpl productoService;

   @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks antes de cada prueba
    }

  @Test
  void obtenerProductoPorEan_productoExiste(){
    String ean ="8437008459059";
    Producto producto = new Producto();
    producto.setEan(ean);
    producto.setCodigoProducto("84590");
    producto.setProveedor("Hacendado");

   when(productoRepository.findByEan(ean)).thenReturn(Optional.of(producto));

    Optional<Producto> productoObtenido = productoService.obtenerProductoPorEan(ean);

    assertTrue(productoObtenido.isPresent());
    assertEquals(ean, productoObtenido.get().getEan());
    assertEquals("Hacendado", productoObtenido.get().getProveedor());
    
  verify(productoRepository, times(1)).findByEan(ean);
  }
  @Test
    void obtenerProductoPorEan_productoNoExiste() {
        String ean = "8437008459059";

        // Simular el comportamiento del repositorio cuando no se encuentra el producto
        when(productoRepository.findByEan(ean)).thenReturn(Optional.empty());

        // Ejecutar y esperar una excepción
        assertThrows(EntityNotFoundException.class, () -> {
            productoService.obtenerProductoPorEan(ean);
        });

        // Verificar que el repositorio fue llamado una vez
        verify(productoRepository, times(1)).findByEan(ean);
    }

    @Test
    void guardarProducto() {
        Producto producto = new Producto();
        producto.setEan("8437008459059");
        producto.setCodigoProducto("84590");
        producto.setProveedor("Hacendado");

        // Simular el guardado del producto
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto productoGuardado = productoService.guardarProducto(producto);

        assertNotNull(productoGuardado);
        assertEquals("8437008459059", productoGuardado.getEan());
        verify(productoRepository, times(1)).save(producto);
    }
}
*/