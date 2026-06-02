package com.awa.neocare_followUp.service;

import org.springframework.stereotype.Service;

@Service
public class AgeService {

    public int calculAgeCorrige(int ageGestationnelSemaines, int ageReelSemaines) {

        int difference = 40 - ageGestationnelSemaines;

        int ageCorrige = ageReelSemaines - difference;

        return Math.max(0, ageCorrige);
    }
}