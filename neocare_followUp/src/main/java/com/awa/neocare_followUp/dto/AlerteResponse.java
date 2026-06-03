package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.TypeAlerte;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlerteResponse {
    private Long id;
    private String titre;
    private String message;
    private TypeAlerte typeAlerte;
    private LocalDateTime dateCreation;
    private boolean traite;
    private Long nouveauNeId;
    private String nouveauNeNom;
    private Long medecinId;
    private String medecinNom;
}