package com.awa.neocare_followUp.integration;

import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
import com.awa.neocare_followUp.dto.RendezVousRequest;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;


import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        TestSecurityConfig.class,
        TestBeanConfig.class
})
public class RendezVousIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    NouveauNeRepository nouveauNeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    private NouveauNe bebe;

    private Utilisateur secretaire;

    @BeforeEach
    void setup(){

        bebe = nouveauNeRepository.save(
                NouveauNe.builder()
                        .nom("Baby")
                        .build()
        );

        secretaire = utilisateurRepository.save(
                Utilisateur.builder()
                        .nom("Secretaire")
                        .role(Role.SECRETAIRE)
                        .build()
        );

    }

    @Test
    void shouldCreateRendezVous() throws Exception {

        RendezVousRequest request = new RendezVousRequest();

        request.setDateRdv(
                LocalDateTime.now().plusDays(2)
        );

        request.setMotif("Contrôle");

        request.setNouveauNeId(bebe.getId());

        request.setSecretaireId(secretaire.getId());


        mockMvc.perform(post("/rendez-vous")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }


}