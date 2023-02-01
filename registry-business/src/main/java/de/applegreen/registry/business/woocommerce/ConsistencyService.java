package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */

@Aspect
@Service
public class ConsistencyService implements HasLogger {

    @AfterReturning(value = "@annotation(de.applegreen.registry.business.util.AdviceAnnotations.PurchaseCommit)")
    public void updateShopRegistry() {
        this.getLogger().info("Updating Registry of webshop");
    }
}
