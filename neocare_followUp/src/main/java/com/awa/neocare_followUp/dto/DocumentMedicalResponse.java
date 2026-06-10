package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.TypeDocument;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentMedicalResponse {

    private Long id;
    private String nomFichier;
    private TypeDocument typeDocument;
    private String cheminFichier;
    private LocalDateTime dateUpload;

    private Long nouveauNeId;
    private Long consultationId;
    private Long uploaderId;
}