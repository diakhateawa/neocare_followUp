package com.awa.neocare_followUp.integration;
import com.awa.neocare_followUp.conf.TestBeanConfig;
import com.awa.neocare_followUp.conf.TestSecurityConfig;
import com.awa.neocare_followUp.dto.MereRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({
        TestSecurityConfig.class,
        TestBeanConfig.class
})
public class MereIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateMere() throws Exception {

        MereRequest request = new MereRequest();

        request.setNom("Diop");
        request.setPrenom("Awa");
        request.setDateNaissance(LocalDate.of(1995,1,10));
        request.setTelephone("770000000");
        request.setAdresse("Dakar");
        request.setNumeroDossier("DOS001");
        request.setEmail("awa@test.com");

        mockMvc.perform(post("/meres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom")
                        .value("Diop"));

    }

    @Test
    void shouldGetAllMeres() throws Exception {

        mockMvc.perform(get("/meres"))
                .andExpect(status().isOk());

    }

}