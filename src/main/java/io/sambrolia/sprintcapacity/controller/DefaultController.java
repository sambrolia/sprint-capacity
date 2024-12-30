package io.sambrolia.sprintcapacity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String home() {
        // This will render the template: src/main/resources/templates/index.html
        return "index";
    }
}
