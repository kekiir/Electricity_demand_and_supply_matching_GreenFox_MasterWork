package com.gfa.powertrade.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  private JwtFilter jwtFilter;
  private static final Long MAX_AGE = 3600L;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .httpBasic().disable()
      .cors(cors -> cors // Configure CORS support
        .configurationSource(request -> setCorsConfig()))
        .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .antMatchers("/supplier", "/supplier/**").authenticated()
        .antMatchers("/consumer", "/consumer/**").authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationExceptionHandler())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return httpSecurity.build();
  }

  private CorsConfiguration setCorsConfig () {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:5174");
    config.setAllowedHeaders(Arrays.asList(
      HttpHeaders.AUTHORIZATION,
      HttpHeaders.CONTENT_TYPE,
      HttpHeaders.ACCEPT));
    config.setAllowedMethods(Arrays.asList(
      HttpMethod.GET.name(),
      HttpMethod.POST.name(),
      HttpMethod.PUT.name(),
      HttpMethod.DELETE.name()));
    config.setMaxAge(MAX_AGE);
    return config;

  }

}
