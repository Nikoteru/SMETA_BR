package org.pry_org.smeta_br.controllers;

import lombok.AllArgsConstructor;
import org.pry_org.smeta_br.DTOs.ContractDTO;
import org.pry_org.smeta_br.repositories.ContractRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class ContractsController {

    private final ContractRepository contractRepository;

    @GetMapping("/contracts") // Маппинг url страницы для упрощения обращения
    public String openContractsPage() {
        return "redirect:/fragments/contracts.html";
    } // Редирект на страницу при вызове по упрощенному варианту в getmapping

    @ResponseBody
    @GetMapping("/api/contracts")
    public List<ContractDTO> getContracts(
            @RequestParam(required = false) Long pId,
            @RequestParam(required = false, defaultValue = "10") Integer pLmt,
            @RequestParam(required = false) Integer pFst,
            @RequestParam(required = false) String[] pOrderCols,
            @RequestParam(required = false) String[] pOrderDirs,

            @RequestParam(required = false) String pContractNum,

            @RequestParam(required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            java.time.LocalDate pContractDate,

            @RequestParam(required = false) Long pContractorId,

            @RequestParam(required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
            java.time.LocalDateTime pCreateDttm,

            @RequestParam(required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
            java.time.LocalDateTime pUpdateDttm,

            @RequestParam(required = false) Long pCreateUserId,
            @RequestParam(required = false) Long pUpdateUserId
    ) {
        return contractRepository.selectContract(
                pId,
                pLmt,
                pFst,
                pOrderCols,
                pOrderDirs,
                pContractNum,
                pContractDate,
                pContractorId,
                pCreateDttm,
                pUpdateDttm,
                pCreateUserId,
                pUpdateUserId
        );
    }
}