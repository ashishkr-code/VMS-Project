package com.example.vms.Repository;

import com.example.vms.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(String productId);

    Optional<Product> findByModelno(String modelno);

    Optional<Product> findByNameIgnoreCase(String name);
}
