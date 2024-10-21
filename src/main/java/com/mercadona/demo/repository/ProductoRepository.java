package com.mercadona.demo.repository;

import com.mercadona.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
  Optional <Producto> findByEan(String ean);

}
