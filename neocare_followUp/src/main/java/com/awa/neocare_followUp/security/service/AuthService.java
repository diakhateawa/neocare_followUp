package com.awa.neocare_followUp.security.service;

import com.awa.neocare_followUp.dto.LoginRequest;
import com.awa.neocare_followUp.dto.RegisterRequest;
import com.awa.neocare_followUp.entity.Role;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import com.awa.neocare_followUp.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String register(RegisterRequest request) {

        Utilisateur user = Utilisateur.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .role(Role.INFIRMIER)
                .actif(true)
                .build();

        utilisateurRepository.save(user);

        return "User created successfully";
    }

    public String login(LoginRequest request) {

        Utilisateur user = utilisateurRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );

        return jwtService.generateToken(userDetails);
    }
}