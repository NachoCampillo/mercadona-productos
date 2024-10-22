package com.mercadona.demo.controller;

import org.springframework.http.HttpStatus;
import com.mercadona.demo.model.Producto;
import com.mercadona.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado); 
  }

    @PutMapping("/{id}")
      public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
          producto.setId(id);  // Asegura que el ID se establece correctamente
          Producto productoActualizado = productoService.actualizarProducto(producto);
          return ResponseEntity.ok(productoActualizado);  // Devuelve 200 OK
      }

  @DeleteMapping("/{id}")
  public ResponseEntity<Producto> eliminarProducto(@PathVariable("id") Long id) {
    productoService.eliminarProducto(id);
    return ResponseEntity.ok().build();
  }

    @GetMapping("/all")
  public ResponseEntity<List<Producto>> obtenerProductos(){
    List<Producto> productos = productoService.obtenerProductos();
    return ResponseEntity.ok(productos);
  }
  }