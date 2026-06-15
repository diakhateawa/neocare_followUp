package com.awa.neocare_followUp.service;
import com.awa.neocare_followUp.dto.NouveauNeRequest;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.entity.NouveauNe;
import com.awa.neocare_followUp.repository.MereRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NouveauNeServiceTest {

    @Mock
    private NouveauNeRepository nouveauNeRepository;

    @Mock
    private MereRepository mereRepository;

    @Mock
    private AgeService ageService;

    @InjectMocks
    private NouveauNeService nouveauNeService;

    @Test
    void shouldCreateNouveauNe() {
        NouveauNeRequest request = new NouveauNeRequest();
        request.setNom("Baby");
        request.setPrenom("Test");
        request.setMereId(1L);
        request.setDateNaissance(LocalDate.now());

        Mere mere = Mere.builder()
                .id(1L)
                .nom("Marie")
                .build();

        NouveauNe saved = NouveauNe.builder()
                .id(1L)
                .nom("Baby")
                .mere(mere)
                .ageGestationnel(38)
                .build();

        when(mereRepository.findById(1L)).thenReturn(Optional.of(mere));
        when(nouveauNeRepository.save(any())).thenReturn(saved);
        when(ageService.calculAgeCorrige(anyInt(), anyInt())).thenReturn(2);

        var result = nouveauNeService.create(request);

        assertNotNull(result);
        assertEquals("Baby", result.getNom());
    }

    @Test
    void shouldThrowExceptionWhenMereNotFound() {
        NouveauNeRequest request = new NouveauNeRequest();
        request.setMereId(1L);

        when(mereRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> nouveauNeService.create(request));
    }

    @Test
    void shouldGetNouveauNeById() {
        Mere mere = Mere.builder().id(1L).nom("Marie").build();

        NouveauNe bebe = NouveauNe.builder()
                .id(1L)
                .nom("Baby")
                .mere(mere)
                .ageGestationnel(38)
                .build();

        when(nouveauNeRepository.findById(1L)).thenReturn(Optional.of(bebe));
        when(ageService.calculAgeCorrige(anyInt(), anyInt())).thenReturn(1);

        var result = nouveauNeService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}