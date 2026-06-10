package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.Role;
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
    private Role role;
}