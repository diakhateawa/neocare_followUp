package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.PrescriptionRequest;
import com.awa.neocare_followUp.dto.PrescriptionResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.ConsultationRepository;
import com.awa.neocare_followUp.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.awa.neocare_followUp.entity.Consultation;
import com.awa.neocare_followUp.entity.Prescription;
import org.mockito.*;
@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Test
    void shouldCreatePrescription() {

        // =====================
        // GIVEN
        // =====================
        Long consultationId = 1L;

        Consultation consultation = new Consultation();
        consultation.setId(consultationId);

        // ⚠️ IMPORTANT : éviter NullPointerException dans mapToResponse
        NouveauNe nn = new NouveauNe();
        nn.setId(10L);
        nn.setNom("Bébé");

        Utilisateur medecin = new Utilisateur();
        medecin.setId(20L);
        medecin.setNom("Dr Awa");

        consultation.setNouveauNe(nn);
        consultation.setMedecin(medecin);

        PrescriptionRequest request = new PrescriptionRequest();
        request.setConsultationId(consultationId);
        request.setMedicaments("Paracétamol");
        request.setPosologie("1 cp");
        request.setDuree("3 jours");

        Prescription saved = new Prescription();
        saved.setId(100L);

        Prescription full = new Prescription();
        full.setId(100L);
        full.setMedicaments("Paracétamol");
        full.setPosologie("1 cp");
        full.setDuree("3 jours");
        full.setConsultation(consultation);

        // =====================
        // MOCKS
        // =====================
        when(consultationRepository.findById(consultationId))
                .thenReturn(Optional.of(consultation));

        when(prescriptionRepository.save(any(Prescription.class)))
                .thenReturn(saved);

        when(prescriptionRepository.findByIdWithFetch(100L))
                .thenReturn(Optional.of(full));

        // =====================
        // WHEN
        // =====================
        PrescriptionResponse response = prescriptionService.create(request);

        // =====================
        // THEN
        // =====================
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Paracétamol", response.getMedicaments());
    }
}