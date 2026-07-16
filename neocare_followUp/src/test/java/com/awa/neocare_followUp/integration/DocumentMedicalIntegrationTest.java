package com.awa.neocare_followUp.integration;
import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
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

import com.awa.neocare_followUp.dto.DocumentMedicalRequest;
import com.awa.neocare_followUp.entity.*;

import com.awa.neocare_followUp.repository.*;


import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        TestSecurityConfig.class,
        TestBeanConfig.class
})
public class DocumentMedicalIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    NouveauNeRepository nouveauNeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    NouveauNe bebe;

    Utilisateur user;

    @BeforeEach
    void setup(){

        bebe = nouveauNeRepository.save(
                NouveauNe.builder()
                        .nom("Baby")
                        .build()
        );

        user = utilisateurRepository.save(
                Utilisateur.builder()
                        .nom("Admin")
                        .role(Role.ADMIN)
                        .build()
        );

    }

    @Test
    void shouldCreateDocument() throws Exception {

        DocumentMedicalRequest request =
                new DocumentMedicalRequest();

        request.setNomFichier("radio.jpg");
        request.setCheminFichier("/files/radio.jpg");
        request.setTypeDocument(TypeDocument.RADIOGRAPHIE);
        request.setNouveauNeId(bebe.getId());
        request.setUploaderId(user.getId());

        mockMvc.perform(post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }


}