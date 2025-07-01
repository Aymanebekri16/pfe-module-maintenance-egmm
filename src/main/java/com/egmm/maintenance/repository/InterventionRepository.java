package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.Intervention;
import com.egmm.maintenance.entity.StatutIntervention;
import com.egmm.maintenance.entity.TypeIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    // Recherche par équipement
    List<Intervention> findByEquipementId(Long equipementId);

    // Recherche par statut
    List<Intervention> findByStatut(StatutIntervention statut);

    // Compter par statut
    long countByStatut(StatutIntervention statut);

    // Recherche par degré d'urgence
    List<Intervention> findByDegreUrgence(Integer degreUrgence);

    // Recherche par type d'intervention
    List<Intervention> findByTypeIntervention(TypeIntervention typeIntervention);

    // Interventions entre deux dates
    List<Intervention> findByDateDebutBetween(LocalDateTime debut, LocalDateTime fin);

    // Interventions planifiées
    List<Intervention> findByStatutOrderByDateDebutAsc(StatutIntervention statut);

    // Interventions par équipement et statut
    List<Intervention> findByEquipementIdAndStatut(Long equipementId, StatutIntervention statut);

    // Recherche par numéro de fiche
    List<Intervention> findByNumFicheContaining(String numFiche);
}