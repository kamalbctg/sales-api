package com.sales.ws.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User : Kamal Hossain
 * Date : 6/10/16.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "code"})
@Builder(builderClassName = "Builder", toBuilder = true)
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = -4849902933295899427L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    protected Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone1", nullable = false)
    private String phone1;

    @Column(name = "phone2", nullable = false)
    private String phone2;

    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;

    @Column(name = "current_credit", nullable = false)
    private BigDecimal currentCredit;
}
