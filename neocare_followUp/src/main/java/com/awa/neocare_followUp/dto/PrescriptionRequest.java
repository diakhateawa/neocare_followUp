package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescriptionRequest {

    private String medicaments;
    private String posologie;
    private String duree;
    private LocalDateTime datePrescription;
    private Long consultationId;
}