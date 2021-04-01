package com.sujit.reconciliationwebapp.controller;

import com.sujit.reconciliationwebapp.dto.LoginDto;
import com.sujit.reconciliationwebapp.dto.LoginResponse;
import com.sujit.reconciliationwebapp.model.UserActivity;
import com.sujit.reconciliationwebapp.model.UserEntity;
import com.sujit.reconciliationwebapp.repository.UserActivityRepository;
import com.sujit.reconciliationwebapp.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserActivityRepository userActivityRepository;

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
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
            //setting user Activity
            UserActivity userActivity = new UserActivity();
            User user  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userActivity.setUsername(user.getUsername());
            userActivity.setActivity("login");
            userActivity.setDate(LocalDateTime.now());
            userActivity.setRequestIpAddr(request.getRemoteAddr());
            userActivityRepository.save(userActivity);
        } catch (AuthenticationException e) {
            log.info("Invalid user/ password . Sign in Unsuccessful");
            response.setMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        log.info("User login success");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/activity/{username}")
    public ResponseEntity<Object> publishUserActivity(@PathVariable String username){
        if(username == null){
            return ResponseEntity.badRequest().build();
        }
        List<UserActivity> userActivity = userActivityRepository.findAllByUsername(username);
        return ResponseEntity.ok().body(userActivity);

    }

}
