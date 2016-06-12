package com.sales.ws.service;

import com.sales.ws.dto.OrderItemInfo;
import com.sales.ws.dto.SalesOrderInfo;
import com.sales.ws.exception.ValidationException;
import com.sales.ws.model.Customer;
import com.sales.ws.model.Product;
import com.sales.ws.model.SalesOrder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertNotNull;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
public class SalesOrderServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesOrderService salesOrderService;

    @Test
    public void testCreateSalesOrder() {
        Product product = createProduct(200, new BigDecimal(10));
        Product product2 = createProduct(200, new BigDecimal(10));
        Customer customer = createCustomer();
        List<OrderItemInfo> orderItems = prepareOrderItems(2, product, product2);

//        BigDecimal totalPrice = orderItems.stream()
//                .map(it -> it.getTotalPrice())
//                .reduce(BigDecimal::add).get();

        BigDecimal totalPrice = calculateTotalOrderPrice(orderItems);

        SalesOrder salesOrder = salesOrderService.createSalesOrder(SalesOrderInfo.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerCode(customer.getCode())
                .totalPrice(totalPrice)
                .orderItemInfo(orderItems)
                .build()
        );

        assertNotNull(salesOrder);
    }

    @Test(expected = ValidationException.class)
    public void testCreateSalesOrderWithCreditLimitExceeded() {
        Product product = createProduct(2000, new BigDecimal(200));

        Customer customer = createCustomer();
        List<OrderItemInfo> orderItems = prepareOrderItems(10, product);

        BigDecimal totalPrice = calculateTotalOrderPrice(orderItems);

        SalesOrder salesOrder = salesOrderService.createSalesOrder(SalesOrderInfo.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerCode(customer.getCode())
                .totalPrice(totalPrice)
                .orderItemInfo(orderItems)
                .build()
        );

        assertNotNull(salesOrder);
    }

    @Test(expected = ValidationException.class)
    public void testCreateSalesOrderWithInventoryExceeded() {
        Product product = createProduct(1, new BigDecimal(2));

        Customer customer = createCustomer();
        List<OrderItemInfo> orderItems = prepareOrderItems(10, product);

        BigDecimal totalPrice = calculateTotalOrderPrice(orderItems);

        SalesOrder salesOrder = salesOrderService.createSalesOrder(SalesOrderInfo.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerCode(customer.getCode())
                .totalPrice(totalPrice)
                .orderItemInfo(orderItems)
                .build()
        );

        assertNotNull(salesOrder);
    }

    @Test
    public void testUpdateSalesOrder() {
        Product product = createProduct(200, new BigDecimal(10));
        Product product2 = createProduct(100, new BigDecimal(10));
        Product product3 = createProduct(200, new BigDecimal(10));
        Customer customer = createCustomer();

        List<OrderItemInfo> orderItems = prepareOrderItems(3, product, product2);

        BigDecimal totalPrice = calculateTotalOrderPrice(orderItems);

        SalesOrder salesOrder = salesOrderService.createSalesOrder(SalesOrderInfo.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerCode(customer.getCode())
                .totalPrice(totalPrice)
                .orderItemInfo(orderItems)
                .build()
        );


        orderItems = prepareOrderItems(2, product, product2, product3);
        totalPrice = calculateTotalOrderPrice(orderItems);
        salesOrderService.updateSalesOrder(SalesOrderInfo.builder()
                .orderNumber(salesOrder.getOrderNumber())
                .customerCode(customer.getCode())
                .totalPrice(totalPrice)
                .orderItemInfo(orderItems)
                .build());
        assertNotNull(salesOrder);
    }

    private Product createProduct(Integer qty, BigDecimal price) {
        Product product = productService.createProduct(Product.builder()
                .code(UUID.randomUUID().toString())
                .price(price)
                .quantity(qty)
                .description("PO1")
                .build());
        productService.createProduct(product);
        return product;
    }

    private BigDecimal calculateTotalOrderPrice(List<OrderItemInfo> orderItems) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItemInfo item : orderItems) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        return totalPrice;
    }

    private Customer createCustomer() {
        Customer customer = customerService.createCustomer(Customer.builder()
                .code(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(20))
                .phone1(RandomStringUtils.randomNumeric(10))
                .phone2(RandomStringUtils.randomNumeric(10))
                .creditLimit(new BigDecimal(200))
                .currentCredit(new BigDecimal(0))
                .build());

        customerService.createCustomer(customer);
        return customer;
    }

    private List<OrderItemInfo> prepareOrderItems(Integer qty, Product... products) {
        List<OrderItemInfo> orderItems = new ArrayList<>();
        for (Product product : products) {
            orderItems.add(OrderItemInfo.builder()
                    .productCode(product.getCode())
                    .quantity(qty)
                    .unitPrice(product.getPrice())
                    .totalPrice(product.getPrice().multiply(new BigDecimal(qty)))
                    .build());
        }
        return orderItems;
    }
}