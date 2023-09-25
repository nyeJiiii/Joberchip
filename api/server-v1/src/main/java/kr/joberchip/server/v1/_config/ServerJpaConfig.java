package kr.joberchip.server.v1._config;

import java.util.Optional;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EntityScan(basePackages = {"kr.joberchip.core"})
@EnableJpaRepositories(basePackages = {"kr.joberchip.server.v1"})
@EnableJpaAuditing
public class ServerJpaConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return () ->
        Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(CustomUserDetails.class::cast)
            .map(CustomUserDetails::getUsername);
  }
}
