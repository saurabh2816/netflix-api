package com.javatechie.crud.netflix.repository;

import com.javatechie.crud.netflix.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);
}

