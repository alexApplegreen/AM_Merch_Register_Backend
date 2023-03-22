package de.applegreen.registry.business.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
public interface WooCommerceCommunicatable {

    @Value("${woocommerce_api_key}")
    String API_KEY = "";

    @Value("${woocommerce_api_secret}")
    String API_SECRECT = "";

    String BASE_URL = "https://aboutmonsters.de/wp-json/wc/v3";
}
