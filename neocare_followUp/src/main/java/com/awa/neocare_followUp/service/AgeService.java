package com.awa.neocare_followUp.service;

import org.springframework.stereotype.Service;

@Service
public class AgeService {
    public int calculAgeCorrige(int ageGestationnel, int ageReelSemaines) {
        int difference = 40 - ageGestationnel;
        return ageReelSemaines - difference;
    }
}