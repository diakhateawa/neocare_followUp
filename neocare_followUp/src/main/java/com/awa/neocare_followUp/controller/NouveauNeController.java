package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.NouveauNeRequest;
import com.awa.neocare_followUp.dto.NouveauNeResponse;
import com.awa.neocare_followUp.service.NouveauNeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nouveau-nes")
public class NouveauNeController {

    private final NouveauNeService service;

    public NouveauNeController(NouveauNeService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','INFIRMIER')")
    public NouveauNeResponse create(@RequestBody NouveauNeRequest request) {
        return service.create(request);
    }

    // GET ALL
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','INFIRMIER')")
    public List<NouveauNeResponse> getAll() {
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','INFIRMIER')")
    public NouveauNeResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INFIRMIER')")
    public NouveauNeResponse update(@PathVariable Long id,
                                    @RequestBody NouveauNeRequest request) {
        return service.update(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}