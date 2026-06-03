package com.awa.neocare_followUp.dto;
import com.awa.neocare_followUp.entity.TypeAlerte;
import lombok.Data;

@Data
public class AlerteRequest {
    private String titre;
    private String message;
    private TypeAlerte typeAlerte;

    private Long nouveauNeId;
    private Long medecinId;
}