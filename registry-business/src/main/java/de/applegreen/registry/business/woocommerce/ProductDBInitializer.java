package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Service
public class ProductDBInitializer implements HasLogger {

    @PostConstruct
    public void init() {
        this.getLogger().info("initializing database");
        // TODO
    }
}
