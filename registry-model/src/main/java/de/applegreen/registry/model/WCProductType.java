package de.applegreen.registry.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Entity
@Getter
@Setter
public class WCProductType {

    @Id
    @Column(name = "id")
    @Basic
    private Long id;

    @Column(name = "description")
    @Basic
    private String description;

    @Column(name = "cost")
    @Basic
    private BigDecimal cost;
}
