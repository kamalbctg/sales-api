package com.sales.ws.service;

import com.sales.ws.dto.SalesOrderInfo;
import com.sales.ws.model.SalesOrder;

import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public interface SalesOrderService {
    SalesOrder createSalesOrder(SalesOrderInfo salesOrderInfo);

    SalesOrder updateSalesOrder(SalesOrderInfo orderInfo);

    void deleteSalesOrder(SalesOrder salesOrder);

    List<SalesOrder> getSalesOrders();

    SalesOrder getSalesOrderByOrderNumber(String orderNumber);

    SalesOrder getSalesOrderById(long id);
}
