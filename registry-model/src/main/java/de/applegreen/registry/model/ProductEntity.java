package de.applegreen.registry.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ProductEntity {

    @Id
    @Basic
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "product_description")
    private String product_description;

    @Basic
    @Column(name = "amount")
    private Long amount;

    @Basic
    @Column(name = "cost")
    private BigDecimal cost;
}