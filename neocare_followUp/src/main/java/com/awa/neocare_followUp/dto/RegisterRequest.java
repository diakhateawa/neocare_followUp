package com.awa.neocare_followUp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
    private String username;
    private String password;
    private String nom;
    private String prenom;
    private String email;
}