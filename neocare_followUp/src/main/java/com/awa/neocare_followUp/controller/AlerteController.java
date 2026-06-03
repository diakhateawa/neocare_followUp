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

    @GetMapping
    public List<Alerte> getAll() {
        return alerteService.getAll();
    }

    @GetMapping("/bebe/{id}")
    public List<Alerte> getByBebe(@PathVariable Long id) {
        return alerteService.getByBebe(id);
    }
}