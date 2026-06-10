package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.AlerteRequest;
import com.awa.neocare_followUp.dto.AlerteResponse;
import com.awa.neocare_followUp.entity.Alerte;
import com.awa.neocare_followUp.service.AlerteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertes")
public class AlerteController {

    private final AlerteService alerteService;

    public AlerteController(AlerteService alerteService) {
        this.alerteService = alerteService;
    }

    // CREATE ALERTE
    @PostMapping
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN')")
    public AlerteResponse create(@RequestBody AlerteRequest request) {
        return alerteService.create(request);
    }

    // GET ALL ALERTES
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER','SECRETAIRE')")
    public List<AlerteResponse> getAll() {
        return alerteService.getAll();
    }

    // GET ALERTES BY BEBE
    @GetMapping("/bebe/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public List<AlerteResponse> getByBebe(@PathVariable Long id) {
        return alerteService.getByBebe(id);
    }

    // MARQUER COMME TRAITÉE
    @PutMapping("/{id}/traiter")
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN')")
    public AlerteResponse markAsTreated(@PathVariable Long id) {
        return alerteService.marquerCommeTraite(id);
    }

    // DELETE ALERTE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        alerteService.delete(id);
    }
}