package com.example.userservice.service.impl.security.impl;

import com.example.userservice.model.entity.Token;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.security.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final SecurityHelper securityHelper;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        if (securityHelper.authHeaderIsValid(authHeader)) {
            String jwt = authHeader.substring(7);

            Optional<Token> tokenOptional = tokenRepository.findTokenByToken(jwt);

            if (tokenOptional.isPresent()) {
                Token token = tokenOptional.get();
                token.setExpired(true);
                token.setRevoked(true);

                tokenRepository.save(token);

                SecurityContextHolder.clearContext();
            }
        }else {
            throw new RuntimeException("Authorization header is invalid");
        }
    }
}

