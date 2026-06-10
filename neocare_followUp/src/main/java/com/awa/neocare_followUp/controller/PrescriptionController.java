package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.PrescriptionRequest;
import com.awa.neocare_followUp.dto.PrescriptionResponse;
import com.awa.neocare_followUp.service.PrescriptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public PrescriptionResponse create(@RequestBody PrescriptionRequest request) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public List<PrescriptionResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public PrescriptionResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/consultation/{consultationId}")
    @PreAuthorize("hasAnyRole('MEDECIN','ADMIN','INFIRMIER')")
    public PrescriptionResponse getByConsultation(@PathVariable Long consultationId) {
        return service.getByConsultation(consultationId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public PrescriptionResponse update(@PathVariable Long id,
                                       @RequestBody PrescriptionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}