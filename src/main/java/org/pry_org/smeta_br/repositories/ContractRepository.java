package org.pry_org.smeta_br.repositories;

import lombok.AllArgsConstructor;
import org.pry_org.smeta_br.DTOs.ContractDTO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ContractRepository {

    private final JdbcClient jdbcClient;

    public List<ContractDTO> selectContract(Long pId, Integer pLmt, Integer pFst) {
        return jdbcClient
                .sql("""
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
                        from crm.fn_select_contract(:pId, :pLmt, :pFst)
                        """)
                .param("pId", pId)
                .param("pLmt", pLmt)
                .param("pFst", pFst)
                .query((rs, rowNum) -> new ContractDTO(
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
                ))
                .list();
    }
}