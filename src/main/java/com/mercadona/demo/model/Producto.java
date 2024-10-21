package com.mercadona.demo.model;
import javax.persistence.*;
import lombok.Data;
  @Entity
  @Data
  public class Producto{
    @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincremental
      private Long id;  // Clave primaria autoincremental

      @Column(unique = true)  // El EAN sigue siendo único
      private String ean;

      private String proveedor;
      private String codigoProducto;
      private String destino;
  }
  
