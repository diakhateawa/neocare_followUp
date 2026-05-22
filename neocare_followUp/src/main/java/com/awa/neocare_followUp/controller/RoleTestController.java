package com.awa.neocare_followUp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class RoleTestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Bienvenue ADMIN";
    }

    @GetMapping("/medecin")
    @PreAuthorize("hasRole('MEDECIN')")
    public String medecin() {
        return "Bienvenue MEDECIN";
    }

    @GetMapping("/infirmier")
    @PreAuthorize("hasRole('INFIRMIER')")
    public String infirmier() {
        return "Bienvenue INFIRMIER";
    }
}