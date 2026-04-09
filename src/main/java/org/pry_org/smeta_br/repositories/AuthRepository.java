package org.pry_org.smeta_br.repositories;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AuthRepository {
    private final JdbcClient jdbcClient;
    public boolean checkCredentials(String login, String password) {
        Boolean result = jdbcClient
                .sql("select crm.check_user_credentials(:login, :password)")
                .param("login", login)
                .param("password", password)
                .query(Boolean.class)
                .single();

        return Boolean.TRUE.equals(result);
    }
}

