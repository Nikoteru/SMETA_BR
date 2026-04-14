package org.pry_org.smeta_br.repositories;

import lombok.AllArgsConstructor;
import org.pry_org.smeta_br.DTOs.ContractDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class ContractRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ContractDTO> selectContract(
            Long pId,
            Integer pLmt,
            Integer pFst,
            String[] pOrderCols,
            String[] pOrderDirs,
            String pContractNum,
            LocalDate pContractDate,
            Long pContractorId,
            LocalDateTime pCreateDttm,
            LocalDateTime pUpdateDttm,
            Long pCreateUserId,
            Long pUpdateUserId
    ) {
        String sql = """
                select
                    id,
                    contract_num   as contractNum,
                    contract_date  as contractDate,
                    contractor_id  as contractorId,
                    create_dttm    as createDttm,
                    update_dttm    as updateDttm,
                    create_user_id as createUserId,
                    update_user_id as updateUserId,
                    r_cnt          as rCnt,
                    p_cnt          as pCnt
                from crm.fn_select_contract(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.query(
                sql,
                ps -> {
                    if (pId != null) ps.setLong(1, pId); else ps.setNull(1, Types.BIGINT);
                    if (pLmt != null) ps.setInt(2, pLmt); else ps.setNull(2, Types.INTEGER);
                    if (pFst != null) ps.setInt(3, pFst); else ps.setNull(3, Types.INTEGER);

                    if (pOrderCols != null && pOrderCols.length > 0) {
                        ps.setArray(4, ps.getConnection().createArrayOf("text", pOrderCols));
                    } else {
                        ps.setNull(4, Types.ARRAY);
                    }

                    if (pOrderDirs != null && pOrderDirs.length > 0) {
                        ps.setArray(5, ps.getConnection().createArrayOf("text", pOrderDirs));
                    } else {
                        ps.setNull(5, Types.ARRAY);
                    }

                    if (pContractNum != null && !pContractNum.isBlank()) {
                        ps.setString(6, pContractNum);
                    } else {
                        ps.setNull(6, Types.VARCHAR);
                    }

                    if (pContractDate != null) {
                        ps.setObject(7, pContractDate);
                    } else {
                        ps.setNull(7, Types.DATE);
                    }

                    if (pContractorId != null) ps.setLong(8, pContractorId); else ps.setNull(8, Types.BIGINT);

                    if (pCreateDttm != null) {
                        ps.setTimestamp(9, Timestamp.valueOf(pCreateDttm));
                    } else {
                        ps.setNull(9, Types.TIMESTAMP);
                    }

                    if (pUpdateDttm != null) {
                        ps.setTimestamp(10, Timestamp.valueOf(pUpdateDttm));
                    } else {
                        ps.setNull(10, Types.TIMESTAMP);
                    }

                    if (pCreateUserId != null) ps.setLong(11, pCreateUserId); else ps.setNull(11, Types.BIGINT);
                    if (pUpdateUserId != null) ps.setLong(12, pUpdateUserId); else ps.setNull(12, Types.BIGINT);
                },
                (rs, rowNum) -> new ContractDTO(
                        rs.getObject("id", Long.class),
                        rs.getString("contractNum"),
                        rs.getString("contractDate"),
                        rs.getObject("contractorId", Long.class),
                        rs.getString("createDttm"),
                        rs.getString("updateDttm"),
                        rs.getObject("createUserId", Long.class),
                        rs.getObject("updateUserId", Long.class),
                        rs.getObject("rCnt", Integer.class),
                        rs.getObject("pCnt", Integer.class)
                )
        );
    }
}