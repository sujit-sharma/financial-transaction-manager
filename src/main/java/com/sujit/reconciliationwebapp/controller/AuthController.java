package com.sujit.reconciliationwebapp.controller;

import com.sujit.reconciliationwebapp.dto.LoginDto;
import com.sujit.reconciliationwebapp.dto.LoginResponse;
import com.sujit.reconciliationwebapp.repository.UserRepository;
import com.sujit.reconciliationwebapp.security.TokenProvider;
import com.sujit.reconciliationwebapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManager;
    private final TokenProvider tokenProvider;

    @GetMapping("user/login")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(password);
        log.info("Signing  in user {} ", loginDto.getUsername());

        final LoginResponse response = new LoginResponse();

        try {
            Authentication authentication = authenticationManager.getObject().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.getToken();
            response.setToken(token);
            response.setMessage("Login successful");
        } catch (AuthenticationException e) {
            log.info("Invalid user/ password . Sign in Unsuccessful");
            response.setMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        log.info("User login success");
        return ResponseEntity.ok(response);
    }

}
