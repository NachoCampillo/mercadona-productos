package com.mercadona.demo.model;

import javax.persistence.*;
import lombok.Data;
import javax.validation.constraints.*;

@Entity
@Data
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Clave primaria autoincremental

  @NotBlank(message = "El código EAN no puede estar en blanco.")
  @Size(min = 13, max = 13, message = "El código EAN debe tener exactamente 13 caracteres.")
  @Column(unique = true) // El EAN sigue siendo único
  private String ean;

  @NotBlank(message = "El nombre del proveedor no puede estar en blanco.")
  @Column(nullable = false)
  private String proveedor;

  @NotBlank(message = "El código de producto no puede estar en blanco.")
  @Column(nullable = false)
  private String codigoProducto;

  @NotBlank(message = "El destino no puede estar en blanco.")
  @Column(nullable = false)
  private String destino;
}
