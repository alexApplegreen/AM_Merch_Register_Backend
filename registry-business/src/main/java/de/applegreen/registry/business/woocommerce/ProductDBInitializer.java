package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.business.util.WooCommerceCommunicatable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
@Service
public class ProductDBInitializer implements HasLogger, WooCommerceCommunicatable {

    @Value("${woocommerce_api_key}")
    private String API_KEY;

    @Value("${woocommerce_api_secret}")
    private String API_SECRET;

    @SuppressWarnings("rawtypes")
    @PostConstruct
    public void init() {
        this.getLogger().info("initializing database");
        String plainauth = this.API_KEY + ":" + this.API_SECRET;
        byte[] plainAuthBytes = plainauth.getBytes();
        byte[] base64auth = Base64.getEncoder().encode(plainAuthBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64auth));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                this.BASE_URL + "/products",
                HttpMethod.GET,
                entity,
                List.class
        );
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            this.getLogger().warn("Cannot communicate with WooCommerce Server");
        }
        List<Map<String, Object>> data = responseEntity.getBody();
        if (data.isEmpty()) {
            this.getLogger().warn("List of products from WooCommerce API is empty");
        }
        data.forEach((product) -> {
            // TODO create a productType Entity and add to Database
        });
    }
}
