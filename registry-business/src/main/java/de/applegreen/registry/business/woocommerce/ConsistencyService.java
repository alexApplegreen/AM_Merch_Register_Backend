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

    /**
     * Pointcut definition and advice for when a purchase has been registered
     */
    @AfterReturning(value = "@annotation(de.applegreen.registry.business.util.AdviceAnnotations.PurchaseCommit)")
    public void updateShopRegistry() {
        // TODO actually send API Request to woocommerce instance
        this.getLogger().info("Updating Registry of webshop");
    }
}
