package com.mercadona.demo.controller;

import com.mercadona.demo.model.Producto;
import com.mercadona.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
  public class ProductoController{
  @Autowired
  private ProductoService productoService;

  @GetMapping("/{ean}")
  public ResponseEntity<Producto> obtenerProductoPorEan(@PathVariable("ean") String ean) {
    Optional<Producto> producto = productoService.obtenerProductoPorEan(ean);
    if (producto.isPresent()) {
      return ResponseEntity.ok(producto.get());
    } else {
      return ResponseEntity.notFound().build();  // Si no se encuentra el producto
    }
  }

  @PostMapping
  public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody Producto producto) {
    Producto productoGuardado = productoService.guardarProducto(producto);
    return ResponseEntity.ok(productoGuardado);
  }

  @PutMapping
  public ResponseEntity<Producto> actualizarProducto(@Valid @RequestBody Producto producto) {
    Producto productoActualizado = productoService.actualizarProducto(producto);
    return ResponseEntity.ok(productoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Producto> eliminarProducto(@PathVariable("id") Long id) {
    productoService.eliminarProducto(id);
    return ResponseEntity.ok().build();
  }

  }