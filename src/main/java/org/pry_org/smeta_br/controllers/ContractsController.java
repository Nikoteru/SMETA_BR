package org.pry_org.smeta_br.controllers;

import lombok.AllArgsConstructor;
import org.pry_org.smeta_br.repositories.ContractRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ContractsController {

    private final ContractRepository contractRepository;

    @GetMapping("/contracts")
    public String openContracts(
            @RequestParam(required = false) Long pId,
            @RequestParam(required = false, defaultValue = "10") Integer pLmt,
            Model model
    ) {
        model.addAttribute("contracts", contractRepository.selectContract(pId, pLmt));
        return "fragments/contracts";
    }
}