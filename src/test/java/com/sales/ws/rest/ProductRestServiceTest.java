package com.sales.ws.rest;

import com.sales.ws.util.TestUtil;
import com.sales.ws.model.Product;
import com.sales.ws.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public class ProductRestServiceTest extends BaseRestServiceTest {
    private static String urlTemplate = "/products";

    @Autowired
    private ProductService productService;


    @Test
    public void testCreateProduct() throws Exception {
        getMockMvc().perform(post(urlTemplate)
                .content(TestUtil.convertObjectToJsonBytes(Product.builder()
                        .code(UUID.randomUUID().toString())
                        .price(new BigDecimal(120))
                        .quantity(12)
                        .description("product 1")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProductByCode() throws Exception {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 1")
                .build());


        product.setDescription("Changed");
        product.setPrice(new BigDecimal(500));

        getMockMvc().perform(put(urlTemplate + "/{id}", product.getId())
                .content(TestUtil.convertObjectToJsonBytes(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }


    @Test
    public void testGetProductByCode() throws Exception {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 1")
                .build());

        getMockMvc().perform(get(urlTemplate + "/{code}", product.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testGetProducts() throws Exception {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(new BigDecimal(120))
                .quantity(12)
                .description("product 1")
                .build());

        getMockMvc().perform(get(urlTemplate, product.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}