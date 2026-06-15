package com.awa.neocare_followUp.service;


import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import com.awa.neocare_followUp.dto.ConsultationRequest;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.ConsultationRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private NouveauNeRepository nouveauNeRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private ConsultationService consultationService;

    @Test
    void shouldCreateConsultation() {
        ConsultationRequest request = new ConsultationRequest();
        request.setNouveauNeId(1L);
        request.setMedecinId(2L);
        request.setTemperature(37.5);
        request.setPoids(3.2);

        NouveauNe bebe = NouveauNe.builder().id(1L).nom("Bebe").build();
        Utilisateur medecin = Utilisateur.builder()
                .id(2L)
                .nom("Doc")
                .role(Role.MEDECIN)
                .build();

        Consultation consultationSaved = Consultation.builder()
                .id(1L)
                .nouveauNe(bebe)
                .medecin(medecin)
                .build();

        when(nouveauNeRepository.findById(1L)).thenReturn(Optional.of(bebe));
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.of(medecin));
        when(consultationRepository.save(any())).thenReturn(consultationSaved);

        var result = consultationService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getNouveauNeId());
    }

    @Test
    void shouldThrowExceptionWhenBebeNotFound() {
        ConsultationRequest request = new ConsultationRequest();
        request.setNouveauNeId(1L);

        when(nouveauNeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> consultationService.create(request));
    }

    @Test
    void shouldThrowExceptionWhenMedecinNotDoctor() {
        ConsultationRequest request = new ConsultationRequest();
        request.setNouveauNeId(1L);
        request.setMedecinId(2L);

        NouveauNe bebe = NouveauNe.builder().id(1L).build();
        Utilisateur user = Utilisateur.builder()
                .id(2L)
                .role(Role.INFIRMIER)
                .build();

        when(nouveauNeRepository.findById(1L)).thenReturn(Optional.of(bebe));
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> consultationService.create(request));
    }

    @Test
    void shouldGetConsultationById() {
        Consultation consultation = Consultation.builder()
                .id(1L)
                .nouveauNe(NouveauNe.builder().id(1L).nom("Bebe").build())
                .medecin(Utilisateur.builder().id(2L).nom("Doc").build())
                .build();

        when(consultationRepository.findByIdWithFetch(1L))
                .thenReturn(Optional.of(consultation));

        var result = consultationService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}