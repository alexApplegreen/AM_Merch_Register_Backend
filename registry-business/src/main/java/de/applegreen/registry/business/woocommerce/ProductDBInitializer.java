package de.applegreen.registry.business.woocommerce;

import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.business.util.WooCommerceCommunicatable;
import de.applegreen.registry.model.WCProductType;
import de.applegreen.registry.persistence.repository.WCProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;
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

    private final WCProductTypeRepository wcProductTypeRepository;

    @Autowired
    public ProductDBInitializer(WCProductTypeRepository wcProductTypeRepository) {
        this.wcProductTypeRepository = wcProductTypeRepository;
    }

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
            try {
                WCProductType productType = new WCProductType();
                productType.setId((long) product.get("id"));
                productType.setDescription(product.get("name").toString());
                this.wcProductTypeRepository.save(productType);
                this.getLogger().info("added producttemplate: " + product.get("name").toString());
            }
            catch (JpaSystemException e) {
                this.getLogger().warn("Could not add Product " + product.get("name") + " to database");
            }
            catch (NullPointerException e) {
                this.getLogger().error(e.getLocalizedMessage());
            }
        });
    }
}
