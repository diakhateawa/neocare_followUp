package com.awa.neocare_followUp.service;
import com.awa.neocare_followUp.dto.LoginRequest;
import com.awa.neocare_followUp.dto.RegisterRequest;
import com.awa.neocare_followUp.entity.Role;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import com.awa.neocare_followUp.security.jwt.JwtService;

import com.awa.neocare_followUp.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class AuthServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;


    // ============================
    // TEST REGISTER
    // ============================

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("awa");
        request.setPassword("123456");
        request.setNom("Diakhate");
        request.setPrenom("Awa");
        request.setEmail("awa@test.com");
        request.setRole(Role.ADMIN);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        String result = authService.register(request);

        assertEquals(
                "User created successfully",
                result
        );

        verify(passwordEncoder)
                .encode("123456");

        verify(utilisateurRepository)
                .save(any(Utilisateur.class));

    }

    // ============================
    // TEST LOGIN SUCCESS
    // ============================

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();

        request.setUsername("awa");
        request.setPassword("123456");

        Utilisateur user = Utilisateur.builder()
                .id(1L)
                .username("awa")
                .password("encodedPassword")
                .nom("Diakhate")
                .role(Role.ADMIN)
                .actif(true)
                .build();

        when(utilisateurRepository.findByUsername("awa"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "123456",
                "encodedPassword"
        ))
                .thenReturn(true);

        when(jwtService.generateToken(any()))
                .thenReturn("jwt-token");

        String token = authService.login(request);

        assertEquals(
                "jwt-token",
                token
        );

        verify(jwtService)
                .generateToken(any());

    }

    // ============================
    // USER NOT FOUND
    // ============================

    @Test
    void shouldFailWhenUserNotFound(){

        LoginRequest request = new LoginRequest();

        request.setUsername("test");
        request.setPassword("123");

        when(utilisateurRepository.findByUsername("test"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.login(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

    }

    // ============================
    // INVALID PASSWORD
    // ============================

    @Test
    void shouldFailWhenPasswordInvalid(){

        LoginRequest request = new LoginRequest();

        request.setUsername("awa");
        request.setPassword("wrong");

        Utilisateur user = Utilisateur.builder()
                .username("awa")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .actif(true)
                .build();

        when(utilisateurRepository.findByUsername("awa"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong",
                "encodedPassword"
        ))
                .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.login(request)
                );

        assertEquals(
                "Invalid password",
                exception.getMessage()
        );

    }

    // ============================
    // USER DISABLED
    // ============================

    @Test
    void shouldFailWhenUserDisabled(){

        LoginRequest request = new LoginRequest();

        request.setUsername("awa");
        request.setPassword("123456");

        Utilisateur user = Utilisateur.builder()
                .username("awa")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .actif(false)
                .build();

        when(utilisateurRepository.findByUsername("awa"))
                .thenReturn(Optional.of(user));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authService.login(request)
                );

        assertEquals(
                "User is disabled",
                exception.getMessage()
        );

    }

}