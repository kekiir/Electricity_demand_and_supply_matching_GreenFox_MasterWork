package com.gfa.powertrade.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  private JwtFilter jwtFilter;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .httpBasic().disable()
        .cors()
        .and()
        .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .antMatchers("/supplier", "/supplier/**" ).authenticated()
        .antMatchers( "/consumer", "/consumer/**").authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationExceptionHandler())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return httpSecurity.build();
  }

}
