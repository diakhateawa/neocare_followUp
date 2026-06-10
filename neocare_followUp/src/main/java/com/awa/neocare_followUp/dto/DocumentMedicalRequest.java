package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.TypeDocument;
import lombok.Data;
@Data
public class DocumentMedicalRequest {

    private String nomFichier;
    private TypeDocument typeDocument;
    private String cheminFichier;

    private Long nouveauNeId;
    private Long consultationId;
    private Long uploaderId;
}