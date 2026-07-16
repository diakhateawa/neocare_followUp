package com.awa.neocare_followUp.security;
import com.awa.neocare_followUp.conf.JwtTestSecurityConfig;
import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestJwtSecurityConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
import com.awa.neocare_followUp.entity.Role;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import com.awa.neocare_followUp.security.jwt.JwtService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.security.core.userdetails.UserDetails;




@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        JwtTestSecurityConfig.class,
        TestBeanConfig.class
})
public class JwtSecurityIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Autowired
    private JwtService jwtService;


    private String token;


    @BeforeEach
    void setup() {


        utilisateurRepository.deleteAll();


        Utilisateur user = Utilisateur.builder()
                .username("jwt_test")
                .password("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy")
                .nom("Test")
                .prenom("JWT")
                .email("jwt@test.com")
                .role(Role.ADMIN)
                .actif(true)
                .build();


        utilisateurRepository.save(user);


        UserDetails userDetails =
                User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build();


        token = jwtService.generateToken(userDetails);

    }



    // ======================================
    // JWT VALIDE -> ACCES AUTORISE
    // ======================================

    @Test
    void shouldAccessProtectedEndpointWithValidJwt() throws Exception {


        mockMvc.perform(
                        get("/meres")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk());

    }



    // ======================================
    // SANS JWT -> ACCES REFUSE
    // ======================================

    @Test
    void shouldRejectRequestWithoutJwt() throws Exception {


        mockMvc.perform(
                        get("/meres")
                )

                .andExpect(status().isUnauthorized());

    }



    // ======================================
    // JWT INVALIDE
    // ======================================

    @Test
    void shouldRejectInvalidJwt() throws Exception {


        mockMvc.perform(
                        get("/meres")
                                .header(
                                        "Authorization",
                                        "Bearer faux-token"
                                )
                )

                .andExpect(status().isUnauthorized());

    }

}