package com.proyectoRv.ecommerce.repository;

import com.proyectoRv.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContainingIgnoreCase(String nameProduct);

}
