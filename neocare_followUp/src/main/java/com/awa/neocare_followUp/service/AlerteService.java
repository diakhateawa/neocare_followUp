package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.AlerteRequest;
import com.awa.neocare_followUp.dto.AlerteResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.AlerteRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlerteService {

    private final AlerteRepository alerteRepository;

    public AlerteService(AlerteRepository alerteRepository) {
        this.alerteRepository = alerteRepository;
    }

    public List<Alerte> getAll() {
        return alerteRepository.findAllWithFetch();
    }

    public List<Alerte> getByBebe(Long id) {
        return alerteRepository.findByNouveauNeIdWithFetch(id);
    }
}