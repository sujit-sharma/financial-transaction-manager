package com.sujit.reconciliationwebapp.security;

import com.sujit.reconciliationwebapp.model.HitInformation;
import com.sujit.reconciliationwebapp.model.UserActivity;
import com.sujit.reconciliationwebapp.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserActivityLoggerFilter implements Filter {
    private final UserActivityRepository userActivityRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("User activity logger intercept request ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String username = authentication.getPrincipal().toString();


        log.info("username of requested user is " + username);
        log.info("Client Ip address is " + servletRequest.getRemoteAddr());
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestedUrl = httpServletRequest.getRequestURL().toString();
        String activityMade = requestedUrl.substring(requestedUrl.lastIndexOf("/") + 1);
        log.info("Request url is " + activityMade);

        UserActivity userActivity =  userActivityRepository.findByUsername(username).orElse(new UserActivity());
        List<HitInformation> hitInfoList = userActivity.getHitInformation();
        if(hitInfoList == null) {
            hitInfoList = new ArrayList<>();
        }
        HitInformation hitInformation = new HitInformation();
        userActivity.setUsername(username);
        hitInformation.setRequestIpAddr(servletRequest.getRemoteAddr());
        hitInformation.setActivity(activityMade);
        hitInformation.setDate(LocalDateTime.now());
        hitInfoList.add(hitInformation);
        userActivity.setHitInformation(hitInfoList);
        userActivityRepository.save(userActivity);

        filterChain.doFilter(servletRequest, servletResponse);
    }


}