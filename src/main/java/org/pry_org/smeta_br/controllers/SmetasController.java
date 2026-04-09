package org.pry_org.smeta_br.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SmetasController {
    @GetMapping("/smetas-modal")
    public String openSmetasModal() {
        return "fragments/smetas.html";
    }
}
