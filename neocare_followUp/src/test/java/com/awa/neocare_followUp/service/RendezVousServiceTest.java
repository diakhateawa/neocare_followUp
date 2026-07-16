package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.RendezVousRequest;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.RendezVousRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.awa.neocare_followUp.dto.RendezVousResponse;

import com.awa.neocare_followUp.entity.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RendezVousServiceTest {

    @Mock
    private RendezVousRepository rendezVousRepository;

    @Mock
    private NouveauNeRepository nouveauNeRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private RendezVousService rendezVousService;

    private RendezVousRequest request;

    private NouveauNe bebe;

    private Utilisateur secretaire;

    private RendezVous rendezVous;

    @BeforeEach
    void setUp() {

        bebe = NouveauNe.builder()
                .id(1L)
                .nom("Baby")
                .prenom("Test")
                .build();

        secretaire = Utilisateur.builder()
                .id(2L)
                .nom("Ndiaye")
                .prenom("Awa")
                .role(Role.SECRETAIRE)
                .build();

        rendezVous = RendezVous.builder()
                .id(1L)
                .dateRdv(LocalDateTime.now().plusDays(2))
                .motif("Consultation")
                .statut(StatutRendezVous.PLANIFIE)
                .nouveauNe(bebe)
                .secretaire(secretaire)
                .build();

        request = new RendezVousRequest();

        request.setDateRdv(LocalDateTime.now().plusDays(2));
        request.setMotif("Consultation");
        request.setNouveauNeId(1L);
        request.setSecretaireId(2L);

    }

    @Test
    void shouldCreateRendezVousSuccessfully(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(secretaire));

        when(rendezVousRepository.save(any(RendezVous.class)))
                .thenReturn(rendezVous);

        RendezVousResponse response =
                rendezVousService.create(request);

        assertNotNull(response);

        assertEquals(
                "Consultation",
                response.getMotif()
        );


        verify(rendezVousRepository)
                .save(any(RendezVous.class));

    }

    @Test
    void shouldRejectPastDate(){


        request.setDateRdv(
                LocalDateTime.now().minusDays(1)
        );

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> rendezVousService.create(request));

        assertEquals(
                "Date de rendez-vous invalide",
                exception.getMessage()
        );


        verifyNoInteractions(
                nouveauNeRepository,
                utilisateurRepository
        );

    }


    @Test
    void shouldThrowWhenBebeNotFound(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> rendezVousService.create(request)
        );

    }

    @Test
    void shouldRejectNonSecretaireUser(){

        Utilisateur user = Utilisateur.builder()
                .id(3L)
                .nom("Admin")
                .role(Role.ADMIN)
                .build();

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(user));

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> rendezVousService.create(request));

        assertEquals(
                "Utilisateur non autorisé",
                exception.getMessage()
        );

    }

    @Test
    void shouldGetRendezVousById(){

        when(rendezVousRepository.findByIdWithFetch(1L))
                .thenReturn(Optional.of(rendezVous));

        RendezVousResponse response =
                rendezVousService.getById(1L);

        assertNotNull(response);
        assertEquals(
                1L,
                response.getId()
        );

    }

    @Test
    void shouldGetRendezVousByBebe(){


        when(rendezVousRepository.findByNouveauNeId(1L))
                .thenReturn(List.of(rendezVous));

        List<RendezVousResponse> result =
                rendezVousService.getByBebe(1L);

        assertEquals(
                1,
                result.size()
        );

    }

    @Test
    void shouldUpdateRendezVous(){

        when(rendezVousRepository.findByIdWithFetch(1L))
                .thenReturn(Optional.of(rendezVous));

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(secretaire));

        when(rendezVousRepository.save(any()))
                .thenReturn(rendezVous);

        RendezVousResponse response =
                rendezVousService.update(1L, request);

        assertNotNull(response);

        verify(rendezVousRepository)
                .save(any());

    }

    @Test
    void shouldDeleteRendezVous(){

        when(rendezVousRepository.findById(1L))
                .thenReturn(Optional.of(rendezVous));

        rendezVousService.delete(1L);

        verify(rendezVousRepository)
                .delete(rendezVous);

    }

}