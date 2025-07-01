package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.BonDeTravaux;
import com.egmm.maintenance.entity.StatutBonTravaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonDeTravauxRepository extends JpaRepository<BonDeTravaux, Long> {

    Optional<BonDeTravaux> findByAnomalieId(Long anomalieId);

    List<BonDeTravaux> findByStatut(StatutBonTravaux statut);
}