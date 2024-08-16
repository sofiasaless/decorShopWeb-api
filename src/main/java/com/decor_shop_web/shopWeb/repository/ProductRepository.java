package com.decor_shop_web.shopWeb.repository;

import com.decor_shop_web.shopWeb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}
