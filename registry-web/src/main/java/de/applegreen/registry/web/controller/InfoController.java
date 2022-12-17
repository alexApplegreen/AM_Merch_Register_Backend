package de.applegreen.registry.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Tepe | a.tepe@kalverkamp.de
 */
@Controller
public class InfoController {

    @Value("${application-version}")
    private String applicationVersion;

    @GetMapping("/info")
    public ResponseEntity info() {
        Map<String, String> data = new HashMap<>();
        data.put("application-version", this.applicationVersion);
        return ResponseEntity.ok().body(data);
    }
}
