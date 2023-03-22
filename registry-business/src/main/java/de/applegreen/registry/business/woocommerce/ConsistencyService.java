package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.datatransfer.PurchaseDTO;
import de.applegreen.registry.business.util.HasLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
@Aspect
@Service
public class ConsistencyService implements HasLogger {

    @Value("${woocommerce_api_key}")
    private String KEY;

    @Value("${woocommerce_api_secret}")
    private String SECRET;

    private final static String BASE_URL = "https://aboutmonsters.de/wp-json/wc/v3";
    private final static String PRODUCTS = "/products";
    private final static String STOCK_KEY = "stock_quantity";

    /**
     * Pointcut definition and advice for when a purchase has been registered
     */
    @AfterReturning(value = "@annotation(de.applegreen.registry.business.util.AdviceAnnotations.PurchaseCommit)")
    public void updateShopRegistry(JoinPoint joinPoint) {
        Optional<PurchaseDTO> purchaseDtoOptional = Arrays.stream(
                (PurchaseDTO[]) joinPoint.getArgs()
        ).findFirst();
        if (purchaseDtoOptional.isEmpty()) {
            this.getLogger().warn("No Argument present in Advice");
            return;
        }
        PurchaseDTO purchaseDTO = purchaseDtoOptional.get();
        purchaseDTO.getSold_products().forEach((productDTO -> {
            this.handleStockQuantityUpdate(productDTO.getProduct_id());
        }));
    }

    @SuppressWarnings("rawtypes")
    private void handleStockQuantityUpdate(Long productId) {
        RestTemplate restTemplate = new RestTemplate();
        String plainauth = this.KEY + ":" + this.SECRET;
        byte[] plainAuthBytes = plainauth.getBytes();
        byte[] base64auth = Base64.getEncoder().encode(plainAuthBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64auth));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        this.getLogger().info("Updating Stock of Product " + productId);
        ResponseEntity<Map> responsedata = restTemplate.exchange(
                ConsistencyService.BASE_URL + ConsistencyService.PRODUCTS + productId,
                HttpMethod.GET,
                httpEntity,
                Map.class
        );
        if (!responsedata.getStatusCode().is2xxSuccessful()) {
            this.getLogger().warn("Cannot get Data from WooCommerce Server");
            // TODO save in database and retry exponentially
            return;
        }
        try {
            Long quantity = (Long) responsedata.getBody().get(ConsistencyService.STOCK_KEY);
            quantity = quantity - 1;
            Map<String, Long> data = new HashMap<>();
            data.put(ConsistencyService.STOCK_KEY, quantity);
            restTemplate.exchange(
                    ConsistencyService.BASE_URL + ConsistencyService.PRODUCTS + productId,
                    HttpMethod.PUT,
                    httpEntity,
                    Map.class,
                    data
            );
            this.getLogger().info("Updated stock quantity in database");
        }
        catch (NullPointerException e) {
            this.getLogger().warn("Received faulty data from Woocommerce API");
        }
    }
}
