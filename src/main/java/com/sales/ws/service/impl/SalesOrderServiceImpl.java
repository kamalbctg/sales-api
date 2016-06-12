package com.sales.ws.service.impl;

import com.sales.ws.dto.OrderItemInfo;
import com.sales.ws.dto.SalesOrderInfo;
import com.sales.ws.exception.ResourceNotFoundException;
import com.sales.ws.exception.ValidationException;
import com.sales.ws.model.Customer;
import com.sales.ws.model.Product;
import com.sales.ws.model.SalesOrder;
import com.sales.ws.model.SalesOrderItem;
import com.sales.ws.repository.CustomerRepository;
import com.sales.ws.repository.ProductRepository;
import com.sales.ws.repository.SalesOrderRepository;
import com.sales.ws.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/11/16.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public SalesOrder createSalesOrder(SalesOrderInfo salesOrderInfo) {
        Customer customer = customerRepository.findOneByCode(salesOrderInfo.getCustomerCode());

        if (customer == null) {
            throw new ResourceNotFoundException("customer not found");
        }

        BigDecimal currentCredit = customer.getCurrentCredit().add(salesOrderInfo.getTotalPrice());

        if (currentCredit.compareTo(customer.getCreditLimit()) > 0) {
            throw new ValidationException("credit limit exceeded");
        }

        SalesOrder order = SalesOrder.builder()
                .orderNumber(salesOrderInfo.getOrderNumber())
                .customer(customer)
                .build();


        customer.setCurrentCredit(customer.getCurrentCredit().add(salesOrderInfo.getTotalPrice()));
        order.setTotalPrice(salesOrderInfo.getTotalPrice());


        order.setOrderItems(new ArrayList<>());
        for (OrderItemInfo orderItemInfo : salesOrderInfo.getOrderItemInfo()) {
            Product product = productRepository.findOneByCode(orderItemInfo.getProductCode());

            if (product == null) {
                throw new ResourceNotFoundException("product not found");
            }

            if (orderItemInfo.getQuantity() > product.getQuantity()) {
                throw new ValidationException("product not available");
            }

            product.setQuantity(product.getQuantity() - orderItemInfo.getQuantity());

            SalesOrderItem orderItem = new SalesOrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemInfo.getQuantity());
            orderItem.setUnitPrice(orderItemInfo.getUnitPrice());
            orderItem.setTotalPrice(orderItemInfo.getTotalPrice());
            order.getOrderItems().add(orderItem);
        }
        return salesOrderRepository.save(order);
    }


    @Override
    public SalesOrder updateSalesOrder(SalesOrderInfo orderInfo) {
        Customer customer = customerRepository.findOneByCode(orderInfo.getCustomerCode());

        if (customer == null) {
            throw new ResourceNotFoundException("customer not found");
        }

        SalesOrder order = salesOrderRepository.findOneByOrderNumber(orderInfo.getOrderNumber());

        if (order == null) {
            throw new ResourceNotFoundException("order not found");
        } else {
            //update customer credit
            customer.setCurrentCredit(customer.getCurrentCredit().subtract(order.getTotalPrice()));

            //update inventory
            for (SalesOrderItem orderItem : order.getOrderItems()) {
                orderItem.getProduct().setQuantity(orderItem.getProduct().getQuantity() + orderItem.getQuantity());
            }
            salesOrderRepository.delete(order);
        }
        return createSalesOrder(orderInfo);
    }

    @Override
    public List<SalesOrder> getSalesOrders() {
        return salesOrderRepository.findAll();
    }

    @Override
    public SalesOrder getSalesOrderByOrderNumber(String orderNumber) {
        return salesOrderRepository.findOneByOrderNumber(orderNumber);
    }

    @Override
    public SalesOrder getSalesOrderById(long id) {
        return salesOrderRepository.findOne(id);
    }

    @Override
    public void deleteSalesOrder(SalesOrder salesOrder) {
        //update customer credit
        salesOrder.getCustomer().setCurrentCredit(salesOrder.getCustomer().getCurrentCredit().subtract(salesOrder.getTotalPrice()));

        //update inventory
        for (SalesOrderItem orderItem : salesOrder.getOrderItems()) {
            orderItem.getProduct().setQuantity(orderItem.getProduct().getQuantity() + orderItem.getQuantity());
        }
        salesOrderRepository.delete(salesOrder);
    }
}
