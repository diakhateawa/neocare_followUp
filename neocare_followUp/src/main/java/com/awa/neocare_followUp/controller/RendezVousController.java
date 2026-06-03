package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.RendezVousRequest;
import com.awa.neocare_followUp.dto.RendezVousResponse;
import com.awa.neocare_followUp.service.RendezVousService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendez-vous")
public class RendezVousController {

    private final RendezVousService service;

    public RendezVousController(RendezVousService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN')")
    public RendezVousResponse create(@RequestBody RendezVousRequest request) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN','MEDECIN','INFIRMIER')")
    public List<RendezVousResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/bebe/{id}")
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN','MEDECIN','INFIRMIER')")
    public List<RendezVousResponse> getByBebe(@PathVariable Long id) {
        return service.getByBebe(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN','MEDECIN','INFIRMIER')")
    public RendezVousResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN')")
    public RendezVousResponse update(
            @PathVariable Long id,
            @RequestBody RendezVousRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SECRETAIRE','ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}