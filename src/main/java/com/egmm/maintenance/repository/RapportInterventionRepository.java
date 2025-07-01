package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.RapportIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportInterventionRepository extends JpaRepository<RapportIntervention, Long> {
}