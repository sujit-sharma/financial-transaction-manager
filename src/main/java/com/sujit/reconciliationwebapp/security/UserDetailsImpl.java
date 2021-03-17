package com.sujit.reconciliationwebapp.security;

import com.sujit.reconciliationwebapp.model.UserEntity;
import com.sujit.reconciliationwebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(s).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist {} " + s);
        }
        Set<SimpleGrantedAuthority> authorities = Optional.of(user.getAuthorities()).orElseGet(HashSet::new).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
