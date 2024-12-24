package com.isamm.tasks.config;



import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html#/",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/h2-console/**" 
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	  MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

     
        http
               
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()  
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/auth/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui.html")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/tasks/**")).authenticated()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/projects/**")).authenticated()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/labels/**")).authenticated()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/members/**")).authenticated()
                                .anyRequest().authenticated()
                )
           
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http
        .csrf().disable()  
        .headers().frameOptions().disable();   


        return http.build();
    }
}
