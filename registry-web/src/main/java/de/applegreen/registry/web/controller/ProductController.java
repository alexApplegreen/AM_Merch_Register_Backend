package de.applegreen.registry.web.controller;

import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.persistence.repository.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
@Controller
@RequestMapping("/products")
public class ProductController implements HasLogger {

    private final ProductEntityRepository productEntityRepository;

    @Autowired
    public ProductController(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }
}
