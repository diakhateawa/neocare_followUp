package com.awa.neocare_followUp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nom;
    private String prenom;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean actif;
}