package com.sales.ws.rest;

import com.sales.ws.util.TestUtil;
import com.sales.ws.model.Customer;
import com.sales.ws.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */

public class CustomerRestServiceTest extends BaseRestServiceTest {
    private static String urlTemplate = "/customers";

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetCustomers() throws Exception {
        customerService.createCustomer(Customer.builder()
                .code(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());

        getMockMvc().perform(get(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerByCode() throws Exception {
        Customer customer = customerService.createCustomer(Customer.builder()
                .code(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());

        getMockMvc().perform(get(urlTemplate + "/{code}", customer.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        getMockMvc().perform(post(urlTemplate)
                .content(TestUtil.convertObjectToJsonBytes(Customer.builder()
                        .code(UUID.randomUUID().toString())
                        .name(RandomStringUtils.randomAlphabetic(10))
                        .address(RandomStringUtils.randomAlphabetic(20))
                        .phone1(RandomStringUtils.randomNumeric(10))
                        .phone2(RandomStringUtils.randomNumeric(10))
                        .creditLimit(new BigDecimal(Math.random() * 100))
                        .currentCredit(new BigDecimal(Math.random() * 10))
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = customerService.createCustomer(Customer.builder()
                .code(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(Math.random() * 100))
                .currentCredit(new BigDecimal(Math.random() * 10))
                .build());

        customer.setName("Changed");
        getMockMvc().perform(put(urlTemplate + "/{id}", customer.getId())
                .content(TestUtil.convertObjectToJsonBytes(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
