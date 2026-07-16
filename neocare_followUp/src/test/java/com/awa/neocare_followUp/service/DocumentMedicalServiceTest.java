package com.awa.neocare_followUp.service;
import com.awa.neocare_followUp.dto.DocumentMedicalRequest;
import com.awa.neocare_followUp.dto.DocumentMedicalResponse;

import com.awa.neocare_followUp.entity.*;

import com.awa.neocare_followUp.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentMedicalServiceTest {

    @Mock
    private DocumentMedicalRepository repository;

    @Mock
    private NouveauNeRepository nouveauNeRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private DocumentMedicalService service;

    private DocumentMedicalRequest request;

    private NouveauNe bebe;

    private Consultation consultation;

    private Utilisateur uploader;

    private DocumentMedical document;

    @BeforeEach
    void setUp() {

        bebe = NouveauNe.builder()
                .id(1L)
                .nom("Baby")
                .build();

        uploader = Utilisateur.builder()
                .id(2L)
                .nom("Docteur")
                .build();

        consultation = Consultation.builder()
                .id(3L)
                .build();

        document = DocumentMedical.builder()
                .id(10L)
                .nomFichier("radio.pdf")
                .typeDocument(TypeDocument.RADIOGRAPHIE)
                .cheminFichier("/files/radio.pdf")
                .nouveauNe(bebe)
                .consultation(consultation)
                .uploader(uploader)
                .build();

        request = new DocumentMedicalRequest();

        request.setNomFichier("radio.pdf");
        request.setTypeDocument(TypeDocument.RADIOGRAPHIE);
        request.setCheminFichier("/files/radio.pdf");

        request.setNouveauNeId(1L);
        request.setConsultationId(3L);
        request.setUploaderId(2L);

    }

    @Test
    void shouldCreateDocumentSuccessfully(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(consultationRepository.findById(3L))
                .thenReturn(Optional.of(consultation));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(uploader));

        when(repository.save(any(DocumentMedical.class)))
                .thenReturn(document);

        DocumentMedicalResponse response =
                service.create(request);

        assertNotNull(response);

        assertEquals(
                "radio.pdf",
                response.getNomFichier()
        );

        verify(repository)
                .save(any(DocumentMedical.class));

    }


    @Test
    void shouldThrowWhenBebeNotFound(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.create(request));
        assertEquals(
                "Bébé introuvable",
                exception.getMessage()
        );

    }

    @Test
    void shouldThrowWhenConsultationNotFound(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(consultationRepository.findById(3L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.create(request));

        assertEquals(
                "Consultation introuvable",
                exception.getMessage()
        );

    }

    @Test
    void shouldThrowWhenUploaderNotFound(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(consultationRepository.findById(3L))
                .thenReturn(Optional.of(consultation));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.create(request));

        assertEquals(
                "Utilisateur introuvable",
                exception.getMessage()
        );

    }

    @Test
    void shouldGetDocumentById(){

        when(repository.findById(10L))
                .thenReturn(Optional.of(document));

        DocumentMedicalResponse response =
                service.getById(10L);

        assertNotNull(response);

        assertEquals(
                10L,
                response.getId()
        );

    }

    @Test
    void shouldGetDocumentsByBebe(){

        when(repository.findByNouveauNeId(1L))
                .thenReturn(List.of(document));

        List<DocumentMedicalResponse> result =
                service.getByBebe(1L);

        assertEquals(
                1,
                result.size()
        );

    }

    @Test
    void shouldGetDocumentsByConsultation(){

        when(repository.findByConsultationId(3L))
                .thenReturn(List.of(document));

        List<DocumentMedicalResponse> result =
                service.getByConsultation(3L);

        assertEquals(
                1,
                result.size()
        );

    }

    @Test
    void shouldUpdateDocumentSuccessfully(){

        when(repository.findById(10L))
                .thenReturn(Optional.of(document));

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(consultationRepository.findById(3L))
                .thenReturn(Optional.of(consultation));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(uploader));

        when(repository.save(any(DocumentMedical.class)))
                .thenReturn(document);

        DocumentMedicalResponse response =
                service.update(10L, request);

        assertNotNull(response);

        verify(repository)
                .save(any(DocumentMedical.class));

    }

    @Test
    void shouldDeleteDocument(){

        service.delete(10L);

        verify(repository)
                .deleteById(10L);

    }

}