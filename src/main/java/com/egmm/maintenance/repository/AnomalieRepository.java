package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.Anomalie;
import com.egmm.maintenance.entity.StatutAnomalie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnomalieRepository extends JpaRepository<Anomalie, Long> {

    List<Anomalie> findByEquipementId(Long equipementId);

    List<Anomalie> findByStatut(StatutAnomalie statut);

    long countByStatut(StatutAnomalie statut);

    List<Anomalie> findByDegreUrgence(Integer degreUrgence);

    Optional<Anomalie> findByNumPVCA(String numPVCA);

    List<Anomalie> findByDateConstatBetween(LocalDate debut, LocalDate fin);

    List<Anomalie> findByEquipementIdAndStatut(Long equipementId, StatutAnomalie statut);

    // Anomalies urgentes (degré 1) non résolues
    @Query("SELECT a FROM Anomalie a WHERE a.degreUrgence = 1 AND a.statut != 'RESOLUE'")
    List<Anomalie> findAnomaliesUrgentesNonResolues();

    List<Anomalie> findByEquipementIdOrderByDateConstatDesc(Long equipementId);

    List<Anomalie> findByDescriptionContainingIgnoreCase(String description);
}