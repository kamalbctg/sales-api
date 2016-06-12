package com.sales.ws.service;

import com.sales.ws.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Product product);

    List<Product> getProducts();

    Product getProductByCode(String code);

    Product getProductById(long id);
}
