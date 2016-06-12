package com.sales.ws.controller;

import com.google.common.base.Preconditions;
import com.sales.ws.dto.SalesOrderInfo;
import com.sales.ws.model.SalesOrder;
import com.sales.ws.service.SalesOrderService;
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
@RequestMapping(value = "/orders",
        consumes = "application/json",
        produces = "application/json")
public class SalesOrderController {
    @Autowired
    private SalesOrderService salesOrderService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SalesOrder createSalesOrder(@RequestBody SalesOrderInfo salesOrderInfo) {
        Preconditions.checkNotNull(salesOrderInfo);
        return salesOrderService.createSalesOrder(salesOrderInfo);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SalesOrder> getSalesOrders() {
        return salesOrderService.getSalesOrders();
    }

    @RequestMapping(value = "{orderNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SalesOrder getSalesOrderByOrderNumber(@PathVariable String orderNumber) {
        return RestPreconditions.checkFound(salesOrderService.getSalesOrderByOrderNumber(orderNumber));
    }

    @RequestMapping(value = "{orderNumber}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteSalesOrder(@PathVariable String orderNumber) {
        salesOrderService.deleteSalesOrder(RestPreconditions.checkFound(salesOrderService.getSalesOrderByOrderNumber(orderNumber)));

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public SalesOrder updateSalesOrder(@PathVariable(value = "id") long id, @RequestBody SalesOrderInfo salesOrderInfo) {
        Preconditions.checkNotNull(salesOrderInfo);
        RestPreconditions.checkFound(salesOrderService.getSalesOrderById(id));
        return Preconditions.checkNotNull(salesOrderService.updateSalesOrder(salesOrderInfo));
    }
}
