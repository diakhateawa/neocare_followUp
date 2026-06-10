package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.DocumentMedicalRequest;
import com.awa.neocare_followUp.dto.DocumentMedicalResponse;
import com.awa.neocare_followUp.service.DocumentMedicalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentMedicalController {

    private final DocumentMedicalService service;

    public DocumentMedicalController(DocumentMedicalService service) {
        this.service = service;
    }

    @PostMapping
    public DocumentMedicalResponse create(@RequestBody DocumentMedicalRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<DocumentMedicalResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DocumentMedicalResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/bebe/{id}")
    public List<DocumentMedicalResponse> getByBebe(@PathVariable Long id) {
        return service.getByBebe(id);
    }

    @GetMapping("/consultation/{id}")
    public List<DocumentMedicalResponse> getByConsultation(@PathVariable Long id) {
        return service.getByConsultation(id);
    }

    @PutMapping("/{id}")
    public DocumentMedicalResponse update(@PathVariable Long id,
                                          @RequestBody DocumentMedicalRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}