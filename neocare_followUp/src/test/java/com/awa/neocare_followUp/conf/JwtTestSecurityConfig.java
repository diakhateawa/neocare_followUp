package com.awa.neocare_followUp.conf;
import com.awa.neocare_followUp.security.jwt.JwtFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.awa.neocare_followUp.security.jwt.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;




@TestConfiguration
public class JwtTestSecurityConfig {



    private final JwtFilter jwtFilter;


    public JwtTestSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }



    @Bean
    public SecurityFilterChain jwtTestFilterChain(HttpSecurity http)
            throws Exception {


        http

                .csrf(csrf -> csrf.disable())


                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                (request, response, authException) -> {

                                    response.sendError(
                                            HttpServletResponse.SC_UNAUTHORIZED
                                    );

                                }
                        )

                )


                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**").permitAll()

                        .anyRequest().authenticated()

                )


                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();

    }

}