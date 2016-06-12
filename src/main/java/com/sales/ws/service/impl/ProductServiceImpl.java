package com.sales.ws.service.impl;

import com.sales.ws.model.Product;
import com.sales.ws.repository.ProductRepository;
import com.sales.ws.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByCode(String code) {
        return productRepository.findOneByCode(code);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findOne(id);
    }
}
