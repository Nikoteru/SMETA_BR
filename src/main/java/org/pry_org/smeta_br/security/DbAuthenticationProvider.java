package org.pry_org.smeta_br.security;

import org.pry_org.smeta_br.repositories.AuthRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbAuthenticationProvider implements AuthenticationProvider {

    private final AuthRepository authRepository;

    public DbAuthenticationProvider(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials() == null
                ? ""
                : authentication.getCredentials().toString();

        boolean isValid = authRepository.checkCredentials(login, password);

        if (!isValid) {
            throw new BadCredentialsException("Неверный логин или пароль");
        }

        return new UsernamePasswordAuthenticationToken(
                login,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}