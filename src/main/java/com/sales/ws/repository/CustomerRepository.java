package com.sales.ws.repository;

import com.sales.ws.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findOneByCode(String code);
}
