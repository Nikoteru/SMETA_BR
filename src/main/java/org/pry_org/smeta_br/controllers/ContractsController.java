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

    @ResponseBody  //Когда нужно вернуть ответ, а не отрендерить страницу, например для api ответа
    @GetMapping("/api/contracts") //маппинг url по которому будет формироваться ответ
    public List<ContractDTO> getContracts( //Лист - формат хранения данных (храниться будет в классе DTO, аналогично как в переменной, но в наборе переменных) результат которого будет передаваться в метод getContacts
            @RequestParam(required = false) Long pId, // Переменная для id в данном случае передаваемое значение по умолчанию не указано
            @RequestParam(required = false, defaultValue = "10") Integer pLmt, // Значение для limit которое будет ограничивать количество строк, по умолчанию 10
            @RequestParam(required = false) Integer pFst // Значение для ofset которое будет ограничивать количество строк
    ) {
        return contractRepository.selectContract(pId, pLmt, pFst); //Вернется значение из метода в репозитории который описывает селект из функции
    }
}