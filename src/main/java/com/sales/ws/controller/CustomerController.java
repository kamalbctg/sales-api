package com.sales.ws.controller;

import com.google.common.base.Preconditions;
import com.sales.ws.model.Customer;
import com.sales.ws.service.CustomerService;
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
@RequestMapping(value = "/customers",
        consumes = "application/json",
        produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Customer createCustomer(@RequestBody Customer customer) {
        Preconditions.checkNotNull(customer);
        return customerService.createCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Customer getCustomerByCode(@PathVariable String code) {
        return RestPreconditions.checkFound(customerService.getCustomerByCode(code));
    }

    @RequestMapping(value = "{code}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String code) {
        Customer customer = RestPreconditions.checkFound(customerService.getCustomerByCode(code));
        customerService.deleteCustomer(customer);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        Preconditions.checkNotNull(customer);
        RestPreconditions.checkFound(customerService.getById(id));
        return customerService.updateCustomer(customer);
    }
}
