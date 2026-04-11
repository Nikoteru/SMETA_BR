package org.pry_org.smeta_br.security;

import org.pry_org.smeta_br.security.DbAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DbAuthenticationProvider dbAuthenticationProvider
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(dbAuthenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login.html?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                );

        return http.build();
    }
}
//Test save