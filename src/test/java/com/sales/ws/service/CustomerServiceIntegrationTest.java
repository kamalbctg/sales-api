package com.sales.ws.service;

import com.sales.ws.model.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.UUID;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public class CustomerServiceIntegrationTest extends BaseServiceIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        Customer customer = customerService.createCustomer(Customer.builder()
                .code(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());
        assertNotNull(customer);
    }

    @Test
    public void testFindCustomerByCode() {
        String customerCode = UUID.randomUUID().toString();
        customerService.createCustomer(Customer.builder()
                .code(customerCode)
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());

        Customer retrievedCustomer = customerService.getCustomerByCode(customerCode);
        assertNotNull(retrievedCustomer);
        assertNotSame(customerCode, retrievedCustomer.getCode());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateCustomerDuplicateCode() {
        String customerCode = UUID.randomUUID().toString();
        customerService.createCustomer(Customer.builder()
                .code(customerCode)
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());

        customerService.createCustomer(Customer.builder()
                .code(customerCode)
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateCustomerWithoutData() {
        customerService.createCustomer(new Customer());
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateCustomerWithoutCreditLimit() {
        String customerCode = UUID.randomUUID().toString();
        customerService.createCustomer(Customer.builder()
                .code(customerCode)
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());
    }
}