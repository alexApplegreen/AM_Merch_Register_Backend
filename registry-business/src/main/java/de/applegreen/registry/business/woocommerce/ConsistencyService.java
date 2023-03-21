package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Aspect
@Service
public class ConsistencyService implements HasLogger {

    @Value("${woocommerce_api_key}")
    private String key;

    private final String baseUrl = "https://aboutmonsters.de/wp-json/wc/v3";

    /**
     * Pointcut definition and advice for when a purchase has been registered
     */
    @AfterReturning(value = "@annotation(de.applegreen.registry.business.util.AdviceAnnotations.PurchaseCommit)")
    public void updateShopRegistry() {
        // TODO actually send API Request to woocommerce instance
        this.getLogger().info("Updating Registry of webshop");
    }
}
