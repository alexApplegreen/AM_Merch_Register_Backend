package de.applegreen.registry.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Entity
@Getter
@Setter
public class WCProductType {

    @Id
    @Basic
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wc_id")
    @Basic
    private Long wc_id;

    @Column(name = "description")
    @Basic
    private String description;

    @Column(name = "cost")
    @Basic
    private BigDecimal cost;
}
