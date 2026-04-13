package org.pry_org.smeta_br.controllers;

import lombok.AllArgsConstructor;
import org.pry_org.smeta_br.repositories.ContractRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ObjectController {

    private final ContractRepository contractRepository;

    @GetMapping("/objects")
    public String openObjectsPage(
    ) {
        return "objects.html";
    }
}