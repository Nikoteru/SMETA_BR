package org.pry_org.smeta_br.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContractDTO(
        Long id,
        String contractNum,
        String contractDate,
        Long contractorId,
        String createDttm,
        String updateDttm,
        Long createUserId,
        Long updateUserId,
        Integer rCnt,
        Integer pCnt
) {
}