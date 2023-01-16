package de.applegreen.registry.model;

import java.math.BigDecimal;

@Entity
public class ProductEntity {

    private Long id;

    private String product_description;

    private Long amount;

    private BigDecimal cost;
}