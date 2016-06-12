package com.sales.ws.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "orderNumber", "customerCode"})
@Builder(builderClassName = "Builder", toBuilder = true)
public class SalesOrderInfo {
    private Long id;
    private String orderNumber;
    private String customerCode;
    private BigDecimal totalPrice;
    private List<OrderItemInfo> orderItemInfo;
}
