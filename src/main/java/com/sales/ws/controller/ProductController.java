package com.sales.ws.controller;

import com.google.common.base.Preconditions;
import com.sales.ws.model.Product;
import com.sales.ws.service.ProductService;
import com.sales.ws.util.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@RestController
@RequestMapping(value = "/products",
        consumes = "application/json",
        produces = "application/json")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Product createProduct(@RequestBody Product product) {
        Preconditions.checkNotNull(product);
        return productService.createProduct(product);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product getProductByCode(@PathVariable String code) {
        return RestPreconditions.checkFound(productService.getProductByCode(code));
    }

    @RequestMapping(value = "{code}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable String code) {
        productService.deleteProduct(RestPreconditions.checkFound(productService.getProductByCode(code)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        Preconditions.checkNotNull(product);
        RestPreconditions.checkFound(productService.getProductById(id));
        productService.updateProduct(product);
        return product;
    }

}
