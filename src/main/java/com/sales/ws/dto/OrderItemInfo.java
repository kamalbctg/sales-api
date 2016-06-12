package com.sales.ws.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"productCode", "quantity", "unitPrice"})
@Builder(builderClassName = "Builder", toBuilder = true)
public class OrderItemInfo {
    private String productCode;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
