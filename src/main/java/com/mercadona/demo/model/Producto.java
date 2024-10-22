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
    @Pattern(regexp = "\\d+", message = "El código EAN solo puede contener números.")
    @Column(unique = true)
    private String ean;

    // Estas propiedades ya no necesitan ser obligatorias, se derivarán del EAN
  @Column(nullable = false)
    private String proveedor;
  
  @Column(nullable = false)
    private String codigoProducto;
  
  @Column(nullable = false)
    private String destino;
}
