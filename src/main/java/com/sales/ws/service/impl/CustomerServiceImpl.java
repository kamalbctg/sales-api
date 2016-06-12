package com.sales.ws.service.impl;

import com.sales.ws.model.Customer;
import com.sales.ws.repository.CustomerRepository;
import com.sales.ws.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getById(long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public Customer getCustomerByCode(String code) {
        return customerRepository.findOneByCode(code);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
