package org.pry_org.smeta_br.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContractDTO(
        Long id,
        String contractNum,
        LocalDate contractDate,
        Long contractorId,
        LocalDateTime createDttm,
        LocalDateTime updateDttm,
        Long createUserId,
        Long updateUserId
) {
}