package com.sales.ws.repository;

import com.sales.ws.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    SalesOrder findOneByOrderNumber(String orderNumber);
}
