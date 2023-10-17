package kr.joberchip.server.v1.global.config;

import kr.joberchip.server.v1.global.security.JwtAuthenticationFilter;
import kr.joberchip.server.v1.global.errors.ErrorMessage;
import kr.joberchip.server.v1.global.errors.exceptions.filter.Exception401;
import kr.joberchip.server.v1.global.errors.exceptions.filter.Exception403;
import kr.joberchip.server.v1.global.utils.FilterResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ServerSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .apply(new SecurityFilterManager())
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            (request, response, authException) ->
                FilterResponse.unAuthorized(response, new Exception401(ErrorMessage.UN_AUTHORIZED)))
        .and()
        .exceptionHandling()
        .accessDeniedHandler(
            (request, response, accessDeniedException) ->
                FilterResponse.forbidden(response, new Exception403(ErrorMessage.FORBIDDEN)))
        .and()
        .authorizeRequests(
            expressionInterceptUrlRegistry ->
                expressionInterceptUrlRegistry
                    .antMatchers(HttpMethod.GET, "/v1/page/**")
                    .permitAll()
                    .antMatchers("/v1/user/testTokens", "/v1/user/join", "/v1/user/login")
                    .permitAll()
                    .antMatchers(HttpMethod.POST)
                    .authenticated()
                    .antMatchers(HttpMethod.PUT)
                    .authenticated()
                    .antMatchers(HttpMethod.DELETE)
                    .authenticated()
                    .anyRequest()
                    .authenticated());

    return http.build();
  }

  private CorsConfigurationSource configurationSource() {
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

  public static class SecurityFilterManager
      extends AbstractHttpConfigurer<SecurityFilterManager, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager =
          builder.getSharedObject(AuthenticationManager.class);
      builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
      super.configure(builder);
    }
  }
}
