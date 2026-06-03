package com.awa.neocare_followUp.controller;

import com.awa.neocare_followUp.dto.MereRequest;
import com.awa.neocare_followUp.dto.MereResponse;
import com.awa.neocare_followUp.service.MereService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meres")
public class MereController {
    private final MereService mereService;

    public MereController(MereService mereService) {
        this.mereService = mereService;
    }

    @PostMapping
    public MereResponse create(@RequestBody MereRequest request) {
        return mereService.create(request);
    }

    @GetMapping
    public List<MereResponse> getAll() {
        return mereService.getAll();
    }

    @GetMapping("/{id}")
    public MereResponse getById(@PathVariable Long id) {
        return mereService.getById(id);
    }

    @PutMapping("/{id}")
    public MereResponse update(@PathVariable Long id,
                               @RequestBody MereRequest request) {
        return mereService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mereService.delete(id);
    }
}