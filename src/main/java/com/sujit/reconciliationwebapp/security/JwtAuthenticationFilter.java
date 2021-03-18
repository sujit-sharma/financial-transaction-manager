package com.sujit.petservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter implements Filter {
    private static final String HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String JWT_SECRET_KEY = "MySecret@#$%K%y";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.doFilterInternal((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("Authorizing JWT token");
        String header = httpServletRequest.getHeader(HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            log.info("received request without token");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.substring(header.lastIndexOf(' ') + 1);

        log.info("Received jwt token is " + token);

        //validating token
        try {
            log.info("Retrieving claims");
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            log.info("Username from JWT token is " + username);

            if (username != null) {
                log.info("User exist and retrieved ");
                List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");
                log.info("Authorities from jwt are" + authorities);

                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(this::getRole)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

                log.info("authorities retrieved");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Authenticate user");
            }
        } catch (Exception e) {
            log.error("Token error", e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    public Optional<String> getRole(Map<String, String> object) {
        return Optional.ofNullable(object).map(v -> v.get("authority"));
    }

}
