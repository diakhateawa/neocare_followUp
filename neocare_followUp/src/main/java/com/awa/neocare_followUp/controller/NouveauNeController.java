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

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','INFIRMIER')")
    public NouveauNeResponse create(@RequestBody NouveauNeRequest request) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','INFIRMIER')")
    public List<?> getAll() {
        return service.getAll();
    }
}