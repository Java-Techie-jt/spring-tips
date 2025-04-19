package com.javatechie.config;

import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.of("JavaTechie"); // Replace with actual logged-in user
//    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty(); // No user logged in
        }

        return Optional.of(authentication.getName()); // Returns logged-in username
    }
}
