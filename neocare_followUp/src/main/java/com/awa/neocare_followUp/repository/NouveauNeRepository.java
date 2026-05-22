package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.NouveauNe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NouveauNeRepository extends JpaRepository<NouveauNe, Long> {
    List<NouveauNe> findByMereId(Long mereId);
}