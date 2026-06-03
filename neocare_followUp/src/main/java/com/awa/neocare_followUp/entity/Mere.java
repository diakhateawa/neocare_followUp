package com.awa.neocare_followUp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String adresse;

    @Column(unique = true)
    private String numeroDossier;

    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "mere")
    private List<NouveauNe> nouveauNes;
}