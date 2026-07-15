package com.awa.neocare_followUp.integration;

import com.awa.neocare_followUp.dto.NouveauNeRequest;
import com.awa.neocare_followUp.repository.MereRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.awa.neocare_followUp.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.time.LocalDate;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NouveauNeIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MereRepository mereRepository;


    private Mere mere;



    @BeforeEach
    void setUp() {

        mere = Mere.builder()
                .nom("Diop")
                .prenom("Fatou")
                .dateNaissance(LocalDate.of(1995, 5, 10))
                .telephone("770000000")
                .adresse("Dakar")
                .numeroDossier("DOS-TEST-001")
                .email("fatou.diop@test.com")
                .build();


        mere = mereRepository.save(mere);
    }



    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateNouveauNe() throws Exception {


        NouveauNeRequest request = new NouveauNeRequest();


        request.setNom("Baby");
        request.setPrenom("Test");
        request.setDateNaissance(LocalDate.now());

        request.setSexe("M");

        request.setPoidsNaissance(3.2);
        request.setTailleNaissance(50.0);
        request.setPcNaissance(34.0);

        request.setAgeGestationnel(38);


        // ID réel généré par H2
        request.setMereId(mere.getId());



        mockMvc.perform(post("/nouveau-nes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk());
    }
}