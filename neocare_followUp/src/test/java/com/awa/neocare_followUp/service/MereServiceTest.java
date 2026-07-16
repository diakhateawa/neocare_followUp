package com.awa.neocare_followUp.service;
import com.awa.neocare_followUp.dto.MereRequest;
import com.awa.neocare_followUp.dto.MereResponse;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.repository.MereRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MereServiceTest {

    @Mock
    private MereRepository mereRepository;

    @InjectMocks
    private MereService mereService;

    private Mere mere;
    private MereRequest request;

    @BeforeEach
    void setUp() {

        request = new MereRequest();

        request.setNom("Diop");
        request.setPrenom("Fatou");
        request.setDateNaissance(LocalDate.of(1995, 5, 10));
        request.setTelephone("770000000");
        request.setAdresse("Dakar");
        request.setNumeroDossier("DOS001");
        request.setEmail("fatou@test.com");

        mere = Mere.builder()
                .id(1L)
                .nom("Diop")
                .prenom("Fatou")
                .dateNaissance(LocalDate.of(1995, 5, 10))
                .telephone("770000000")
                .adresse("Dakar")
                .numeroDossier("DOS001")
                .email("fatou@test.com")
                .build();
    }

    @Test
    void shouldCreateMereSuccessfully() {

        when(mereRepository.findByNumeroDossier("DOS001"))
                .thenReturn(Optional.empty());

        when(mereRepository.save(any(Mere.class)))
                .thenReturn(mere);

        MereResponse response =
                mereService.create(request);

        assertNotNull(response);
        assertEquals("Diop", response.getNom());
        assertEquals("DOS001", response.getNumeroDossier());

        verify(mereRepository)
                .findByNumeroDossier("DOS001");

        verify(mereRepository)
                .save(any(Mere.class));
    }




    @Test
    void shouldThrowExceptionWhenNumeroDossierAlreadyExists() {


        when(mereRepository.findByNumeroDossier("DOS001"))
                .thenReturn(Optional.of(mere));



        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> mereService.create(request));



        assertEquals(
                "Numéro dossier déjà existant",
                exception.getMessage()
        );


        verify(mereRepository, never())
                .save(any(Mere.class));
    }





    @Test
    void shouldGetMereByIdSuccessfully() {


        when(mereRepository.findById(1L))
                .thenReturn(Optional.of(mere));



        MereResponse response =
                mereService.getById(1L);



        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Diop", response.getNom());
    }





    @Test
    void shouldThrowExceptionWhenMereNotFoundById() {


        when(mereRepository.findById(1L))
                .thenReturn(Optional.empty());



        assertThrows(
                EntityNotFoundException.class,
                () -> mereService.getById(1L)
        );
    }





    @Test
    void shouldUpdateMereSuccessfully() {


        when(mereRepository.findById(1L))
                .thenReturn(Optional.of(mere));


        when(mereRepository.save(any(Mere.class)))
                .thenReturn(mere);



        request.setNom("Ndiaye");



        MereResponse response =
                mereService.update(1L, request);



        assertNotNull(response);


        verify(mereRepository)
                .save(any(Mere.class));
    }





    @Test
    void shouldThrowExceptionWhenUpdateMereNotFound() {


        when(mereRepository.findById(1L))
                .thenReturn(Optional.empty());



        assertThrows(
                EntityNotFoundException.class,
                () -> mereService.update(1L, request)
        );


        verify(mereRepository, never())
                .save(any(Mere.class));
    }





    @Test
    void shouldDeleteMereSuccessfully() {


        when(mereRepository.existsById(1L))
                .thenReturn(true);



        mereService.delete(1L);



        verify(mereRepository)
                .deleteById(1L);
    }





    @Test
    void shouldThrowExceptionWhenDeleteMereNotFound() {


        when(mereRepository.existsById(1L))
                .thenReturn(false);



        assertThrows(
                EntityNotFoundException.class,
                () -> mereService.delete(1L)
        );



        verify(mereRepository, never())
                .deleteById(anyLong());
    }





    @Test
    void shouldGetAllMeresSuccessfully() {


        when(mereRepository.findAllWithNouveauNes())
                .thenReturn(List.of(mere));



        List<MereResponse> responses =
                mereService.getAll();



        assertNotNull(responses);

        assertEquals(1, responses.size());

        assertEquals(
                "Diop",
                responses.get(0).getNom()
        );
    }

}