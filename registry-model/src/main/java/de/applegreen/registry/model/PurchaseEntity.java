package de.applegreen.registry.model;

import java.math.BigDecimal;
import java.util.Set;

@Entity
public class PurchaseEntity {

    private Long id;

    private Datetime date;

    private BigDecimal total_cost;

    private Set<ProductEntity> sold_products;

}