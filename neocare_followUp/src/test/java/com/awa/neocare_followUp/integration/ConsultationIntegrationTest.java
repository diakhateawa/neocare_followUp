package com.awa.neocare_followUp.integration;
import com.awa.neocare_followUp.dto.ConsultationRequest;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.MereRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ConsultationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MereRepository mereRepository;


    @Autowired
    private NouveauNeRepository nouveauNeRepository;


    @Autowired
    private UtilisateurRepository utilisateurRepository;



    private NouveauNe bebe;

    private Utilisateur medecin;



    @BeforeEach
    void setUp() {


        // Création de la mère
        Mere mere = Mere.builder()
                .nom("Diop")
                .prenom("Fatou")
                .dateNaissance(LocalDate.of(1995, 5, 10))
                .telephone("770000000")
                .adresse("Dakar")
                .numeroDossier("DOS-CONSULT-001")
                .email("fatou@test.com")
                .build();


        mere = mereRepository.save(mere);



        // Création du nouveau-né
        bebe = NouveauNe.builder()
                .nom("Baby")
                .prenom("Test")
                .dateNaissance(LocalDate.now())
                .sexe("M")
                .poidsNaissance(3.2)
                .tailleNaissance(50.0)
                .pcNaissance(34.0)
                .ageGestationnel(38)
                .mere(mere)
                .build();


        bebe = nouveauNeRepository.save(bebe);



        // Création du médecin
        medecin = Utilisateur.builder()
                .nom("Fall")
                .prenom("Moussa")
                .email("medecin@test.com")
                .role(Role.MEDECIN)
                .password("password")
                .build();


        medecin = utilisateurRepository.save(medecin);

    }



    @Test
    @WithMockUser(username = "medecin", roles = {"MEDECIN"})
    void shouldCreateConsultation() throws Exception {


        ConsultationRequest request = new ConsultationRequest();


        request.setNouveauNeId(bebe.getId());

        request.setMedecinId(medecin.getId());


        request.setTemperature(37.5);
        request.setPoids(3.2);
        request.setTaille(50.0);
        request.setPc(35.0);

        request.setDiagnostic("Bébé en bon état");
        request.setObservations("RAS");
        request.setModeAlimentation("Allaitement");



        mockMvc.perform(post("/consultations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk());
    }
}