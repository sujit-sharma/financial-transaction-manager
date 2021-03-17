package com.sujit.reconciliationwebapp.service;

import com.sujit.reconciliationwebapp.constraint.Authorities;
import com.sujit.reconciliationwebapp.model.UserEntity;
import com.sujit.reconciliationwebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createUser(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Creating Database User");
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        Set<String> authoritiesSet = new HashSet<>();
        authoritiesSet.add(Authorities.ADMIN.toString());
        authoritiesSet.add(Authorities.USER.toString());
        user.setAuthorities(authoritiesSet);
        userRepository.save(user);
        log.info("Database User create Success " +user.getUsername());

    }

}
