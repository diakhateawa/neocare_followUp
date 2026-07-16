package com.awa.neocare_followUp.integration;

import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
import com.awa.neocare_followUp.dto.LoginRequest;
import com.awa.neocare_followUp.dto.RegisterRequest;
import com.awa.neocare_followUp.entity.Role;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.UtilisateurRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        TestSecurityConfig.class,
        TestBeanConfig.class
})
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // =====================================
    // REGISTER
    // =====================================

    @Test
    void shouldRegisterUser() throws Exception {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("awa_test");
        request.setPassword("123456");
        request.setNom("Diakhate");
        request.setPrenom("Awa");
        request.setEmail("awa_test@test.com");
        request.setRole(Role.ADMIN);

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string("User created successfully")
                );

    }

    // =====================================
    // LOGIN SUCCESS
    // =====================================

    @Test
    void shouldLoginSuccessfully() throws Exception {

        Utilisateur user = Utilisateur.builder()
                .username("login_test")
                .password(
                        passwordEncoder.encode("123456")
                )
                .nom("Test")
                .prenom("User")
                .email("login@test.com")
                .role(Role.ADMIN)
                .actif(true)
                .build();

        utilisateurRepository.save(user);

        LoginRequest request = new LoginRequest();

        request.setUsername("login_test");
        request.setPassword("123456");

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertTrue(
                                result.getResponse()
                                        .getContentAsString()
                                        .startsWith("ey")
                        )
                );

    }


    // =====================================
    // LOGIN WRONG PASSWORD
    // =====================================

    @Test
    void shouldFailLoginWithWrongPassword() {

        Utilisateur user = Utilisateur.builder()
                .username("wrong_password")
                .password(
                        passwordEncoder.encode("123456")
                )
                .nom("Test")
                .prenom("User")
                .email("wrong@test.com")
                .role(Role.ADMIN)
                .actif(true)
                .build();



        utilisateurRepository.save(user);

        LoginRequest request = new LoginRequest();

        request.setUsername("wrong_password");
        request.setPassword("999999");

        Exception exception = assertThrows(
                Exception.class,
                () -> {

                    mockMvc.perform(
                                    post("/auth/login")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(
                                                    objectMapper.writeValueAsString(request)
                                            )
                            )
                            .andReturn();

                }
        );


        assertTrue(
                exception.getCause()
                        .getMessage()
                        .contains("Invalid password")
        );

    }


    // =====================================
    // LOGIN USER NOT FOUND
    // =====================================

    @Test
    void shouldFailLoginWhenUserNotFound() {

        LoginRequest request = new LoginRequest();

        request.setUsername("unknown");
        request.setPassword("123456");

        Exception exception = assertThrows(
                Exception.class,
                () -> {

                    mockMvc.perform(
                                    post("/auth/login")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(
                                                    objectMapper.writeValueAsString(request)
                                            )
                            )
                            .andReturn();

                }
        );


        assertTrue(
                exception.getCause()
                        .getMessage()
                        .contains("User not found")
        );

    }

    // =====================================
    // LOGIN USER DISABLED
    // =====================================

    @Test
    void shouldFailLoginWhenUserDisabled() {

        Utilisateur user = Utilisateur.builder()
                .username("disabled_user")
                .password(
                        passwordEncoder.encode("123456")
                )
                .nom("Test")
                .prenom("Disabled")
                .email("disabled@test.com")
                .role(Role.ADMIN)
                .actif(false)
                .build();

        utilisateurRepository.save(user);

        LoginRequest request = new LoginRequest();

        request.setUsername("disabled_user");
        request.setPassword("123456");

        Exception exception = assertThrows(
                Exception.class,
                () -> {

                    mockMvc.perform(
                                    post("/auth/login")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(
                                                    objectMapper.writeValueAsString(request)
                                            )
                            )
                            .andReturn();

                }
        );


        assertTrue(
                exception.getCause()
                        .getMessage()
                        .contains("User is disabled")
        );

    }


}