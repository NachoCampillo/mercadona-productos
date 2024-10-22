/*
package com.mercadona.demo.controller;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerProductoPorEan_productoExiste() {
        String ean = "8437008459059";
        Producto producto = new Producto();
        producto.setEan(ean);

        when(productoService.obtenerProductoPorEan(ean)).thenReturn(Optional.of(producto));

        ResponseEntity<Producto> response = productoController.obtenerProductoPorEan(ean);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ean, response.getBody().getEan());
        verify(productoService, times(1)).obtenerProductoPorEan(ean);
    }

    @Test
    void obtenerProductoPorEan_productoNoExiste() {
        String ean = "8437008459059";

        when(productoService.obtenerProductoPorEan(ean)).thenReturn(Optional.empty());

        ResponseEntity<Producto> response = productoController.obtenerProductoPorEan(ean);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productoService, times(1)).obtenerProductoPorEan(ean);
    }
}
*/