package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.business.util.WooCommerceCommunicatable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Map;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
@Service
public class ProductDBInitializer implements HasLogger, WooCommerceCommunicatable {

    @SuppressWarnings("rawtypes")
    @PostConstruct
    public void init() {
        this.getLogger().info("initializing database");
        String plainauth = this.API_KEY + ":" + this.API_SECRECT;
        byte[] plainAuthBytes = plainauth.getBytes();
        byte[] base64auth = Base64.getEncoder().encode(plainAuthBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64auth));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                this.BASE_URL + "/products",
                HttpMethod.GET,
                entity,
                Map.class
        );
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            this.getLogger().warn("Cannot communicate with WooCommerce Server");
        }
        Map<String, Object> data = responseEntity.getBody();
        System.out.println(data);
    }
}
