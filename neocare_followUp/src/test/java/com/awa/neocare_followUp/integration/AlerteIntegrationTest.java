package com.awa.neocare_followUp.integration;
import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
import com.awa.neocare_followUp.entity.*;

import com.awa.neocare_followUp.repository.*;


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
import com.awa.neocare_followUp.dto.AlerteRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        TestSecurityConfig.class,
        TestBeanConfig.class
})
@Transactional
public class AlerteIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    NouveauNeRepository nouveauNeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    NouveauNe bebe;

    Utilisateur medecin;

    @BeforeEach
    void setup(){

        bebe = nouveauNeRepository.save(
                NouveauNe.builder()
                        .nom("Baby")
                        .build()
        );

        medecin = utilisateurRepository.save(
                Utilisateur.builder()
                        .nom("Doctor")
                        .role(Role.MEDECIN)
                        .build()
        );

    }

    @Test
    void shouldCreateAlerte() throws Exception {
        AlerteRequest request =
                new AlerteRequest();

        request.setTitre("Poids faible");
        request.setMessage("Surveillance");
        request.setTypeAlerte(TypeAlerte.POIDS_ANORMAL);

        request.setNouveauNeId(bebe.getId());
        request.setMedecinId(medecin.getId());
        mockMvc.perform(post("/alertes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());


    }


}