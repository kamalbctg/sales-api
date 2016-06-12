package com.sales.ws.repository;

import com.sales.ws.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findOneByCode(String code);
}
