package de.applegreen.registry.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Alexander Tepe | alexander.tepe@hotmail.de
 */
@Controller
public class NavigationController {

    @GetMapping("/")
    public String main() {
        return "index.html";
    }
}
