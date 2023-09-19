package kr.joberchip.server.v1._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ServerSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .headers()
        .frameOptions()
        .sameOrigin()
        .and()
        .cors()
        .configurationSource(configurationSource())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.httpBasic().disable().formLogin().disable();
    http.authorizeRequests().anyRequest().permitAll();

    return http.build();
  }

  public CorsConfigurationSource configurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOriginPattern("*");
    configuration.setAllowCredentials(true);
    configuration.addExposedHeader("Authorization");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
