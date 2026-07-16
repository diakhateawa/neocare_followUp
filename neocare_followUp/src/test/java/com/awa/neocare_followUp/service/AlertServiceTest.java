package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.AlerteRequest;
import com.awa.neocare_followUp.dto.AlerteResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.AlerteRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;


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
public class AlertServiceTest {

    @Mock
    private AlerteRepository alerteRepository;

    @Mock
    private NouveauNeRepository nouveauNeRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private AlerteService service;

    private NouveauNe bebe;

    private Utilisateur medecin;

    private Alerte alerte;

    private AlerteRequest request;

    @BeforeEach
    void setUp(){

        bebe = NouveauNe.builder()
                .id(1L)
                .nom("Baby")
                .build();

        medecin = Utilisateur.builder()
                .id(2L)
                .nom("Diop")
                .role(Role.MEDECIN)
                .build();

        alerte = Alerte.builder()
                .id(10L)
                .titre("Poids faible")
                .message("Surveillance nécessaire")
                .typeAlerte(TypeAlerte.POIDS_ANORMAL)
                .nouveauNe(bebe)
                .medecin(medecin)
                .traite(false)
                .build();

        request = new AlerteRequest();

        request.setTitre("Poids faible");
        request.setMessage("Surveillance nécessaire");
        request.setTypeAlerte(TypeAlerte.POIDS_ANORMAL);
        request.setNouveauNeId(1L);
        request.setMedecinId(2L);

    }

    @Test
    void shouldCreateAlerteSuccessfully(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.of(medecin));

        when(alerteRepository.save(any(Alerte.class)))
                .thenReturn(alerte);

        AlerteResponse response =
                service.create(request);

        assertNotNull(response);

        assertEquals(
                "Poids faible",
                response.getTitre()
        );

        verify(alerteRepository)
                .save(any(Alerte.class));

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
    void shouldThrowWhenMedecinNotFound(){

        when(nouveauNeRepository.findById(1L))
                .thenReturn(Optional.of(bebe));

        when(utilisateurRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.create(request));

        assertEquals(
                "Médecin introuvable",
                exception.getMessage()
        );

    }

    @Test
    void shouldGetAllAlertes(){

        when(alerteRepository.findAllWithFetch())
                .thenReturn(List.of(alerte));

        List<AlerteResponse> result =
                service.getAll();

        assertEquals(
                1,
                result.size()
        );

    }

    @Test
    void shouldGetAlertesByBebe(){

        when(alerteRepository.findByNouveauNeIdWithFetch(1L))
                .thenReturn(List.of(alerte));

        List<AlerteResponse> result =
                service.getByBebe(1L);

        assertEquals(
                1,
                result.size()
        );

    }

    @Test
    void shouldMarkAlerteAsTreated(){

        when(alerteRepository.findById(10L))
                .thenReturn(Optional.of(alerte));

        when(alerteRepository.save(any(Alerte.class)))
                .thenReturn(alerte);

        AlerteResponse response =
                service.marquerCommeTraite(10L);


        assertTrue(
                response.isTraite()
        );

        verify(alerteRepository)
                .save(alerte);
    }

    @Test
    void shouldThrowWhenAlerteNotFound(){

        when(alerteRepository.findById(10L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.marquerCommeTraite(10L));

        assertEquals(
                "Alerte introuvable",
                exception.getMessage()
        );
    }

    @Test
    void shouldDeleteAlerte(){

        service.delete(10L);

        verify(alerteRepository)
                .deleteById(10L);
    }

}