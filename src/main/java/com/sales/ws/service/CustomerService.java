package com.sales.ws.service;

import com.sales.ws.model.Customer;

import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public interface CustomerService {

    Customer getById(long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    List<Customer> getCustomers();

    Customer getCustomerByCode(String code);

}
