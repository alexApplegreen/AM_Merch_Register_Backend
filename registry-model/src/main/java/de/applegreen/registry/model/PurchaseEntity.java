package de.applegreen.registry.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
public class PurchaseEntity {

    @Id
    @Basic
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Basic
    @Column(name = "total_cost")
    private BigDecimal total_cost;

    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "sold_products")
    private Set<WCProductType> sold_products;
}