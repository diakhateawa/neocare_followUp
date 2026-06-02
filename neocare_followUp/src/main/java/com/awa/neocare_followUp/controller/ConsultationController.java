package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.ConsultationRequest;
import com.awa.neocare_followUp.dto.ConsultationResponse;
import com.awa.neocare_followUp.entity.Alerte;
import com.awa.neocare_followUp.service.AlerteService;
import com.awa.neocare_followUp.service.ConsultationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    // =========================
    // CREATE CONSULTATION
    // =========================
    @PostMapping
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN')")
    public ConsultationResponse create(@RequestBody ConsultationRequest request) {
        return service.create(request);
    }

    // =========================
    // GET ALL CONSULTATIONS
    // =========================
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public List<ConsultationResponse> getAll() {
        return service.getAll();
    }

    // =========================
    // GET CONSULTATIONS BY BEBE
    // =========================
    @GetMapping("/bebe/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public List<ConsultationResponse> getByBebe(@PathVariable Long id) {
        return service.getByBebe(id);
    }
}