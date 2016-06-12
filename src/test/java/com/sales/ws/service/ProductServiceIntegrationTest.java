package com.sales.ws.service;

import com.sales.ws.model.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.UUID;

import static junit.framework.Assert.assertNotNull;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public class ProductServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 1")
                .build());
        assertNotNull(product);
    }

    @Test
    public void testGetProductByCode() throws Exception {
        String productCode = UUID.randomUUID().toString();
        productService.createProduct(Product.builder()
                .code(productCode)
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 2")
                .build());

        Product retrievedProduct = productService.getProductByCode(productCode);
        assertNotNull(retrievedProduct);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateProductWithDuplicateCode() throws Exception {
        String productCode = UUID.randomUUID().toString();
        productService.createProduct(Product.builder()
                .code(productCode)
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 2")
                .build());

        productService.createProduct(Product.builder()
                .code(productCode)
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 2")
                .build());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateProductWithWithoutData() throws Exception {
        productService.createProduct(new Product());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateProductWithWithoutQuantity() throws Exception {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .description("product 1")
                .build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateProductWithWithoutPrice() throws Exception {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .description("product 1")
                .build());
    }
}