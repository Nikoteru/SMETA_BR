package org.pry_org.smeta_br.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SmetaController {
    @GetMapping({"/", "/index"})
    public String openIndexPage() {
        return "forward:/index.html";
    }
}
