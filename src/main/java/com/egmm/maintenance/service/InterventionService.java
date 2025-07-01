package com.egmm.maintenance.service;

import com.egmm.maintenance.entity.Intervention;
import com.egmm.maintenance.entity.Equipement;
import com.egmm.maintenance.entity.StatutIntervention;
import com.egmm.maintenance.entity.TypeIntervention;
import com.egmm.maintenance.repository.InterventionRepository;
import com.egmm.maintenance.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    /**
     * Créer une nouvelle intervention
     */
    public Intervention creerIntervention(Intervention intervention) {
        // Validation métier
        if (intervention.getEquipement() == null) {
            throw new IllegalArgumentException("Une intervention doit être associée à un équipement");
        }

        // Vérifier que l'équipement existe
        Equipement equipement = equipementRepository.findById(intervention.getEquipement().getId())
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));

        intervention.setEquipement(equipement);

        // Définir le statut par défaut si non spécifié
        if (intervention.getStatut() == null) {
            intervention.setStatut(StatutIntervention.PLANIFIEE);
        }

        return interventionRepository.save(intervention);
    }

    /**
     * Créer une intervention pour un équipement spécifique
     */
    public Intervention creerInterventionPourEquipement(Long equipementId, String description,
                                                        TypeIntervention type, Integer degreUrgence) {
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé avec l'ID : " + equipementId));

        Intervention intervention = new Intervention();
        intervention.setEquipement(equipement);
        intervention.setDescription(description);
        intervention.setTypeIntervention(type);
        intervention.setDegreUrgence(degreUrgence);
        intervention.setDateDebut(LocalDateTime.now());
        intervention.setStatut(StatutIntervention.PLANIFIEE);

        return interventionRepository.save(intervention);
    }

    /**
     * Obtenir une intervention par son ID
     */
    @Transactional(readOnly = true)
    public Intervention obtenirInterventionParId(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée avec l'ID : " + id));
    }

    /**
     * Obtenir toutes les interventions
     */
    @Transactional(readOnly = true)
    public List<Intervention> obtenirToutesLesInterventions() {
        return interventionRepository.findAll();
    }

    /**
     * Obtenir les interventions par équipement
     */
    @Transactional(readOnly = true)
    public List<Intervention> obtenirInterventionsParEquipement(Long equipementId) {
        return interventionRepository.findByEquipementId(equipementId);
    }

    /**
     * Obtenir les interventions par statut
     */
    @Transactional(readOnly = true)
    public List<Intervention> obtenirInterventionsParStatut(StatutIntervention statut) {
        return interventionRepository.findByStatut(statut);
    }

    /**
     * Démarrer une intervention
     */
    public Intervention demarrerIntervention(Long interventionId) {
        Intervention intervention = obtenirInterventionParId(interventionId);

        if (intervention.getStatut() != StatutIntervention.PLANIFIEE) {
            throw new IllegalStateException("Seules les interventions planifiées peuvent être démarrées");
        }

        intervention.setStatut(StatutIntervention.EN_COURS);
        intervention.setDateDebut(LocalDateTime.now());

        return interventionRepository.save(intervention);
    }

    /**
     * Terminer une intervention
     */
    public Intervention terminerIntervention(Long interventionId, String commentaires) {
        Intervention intervention = obtenirInterventionParId(interventionId);

        if (intervention.getStatut() != StatutIntervention.EN_COURS) {
            throw new IllegalStateException("Seules les interventions en cours peuvent être terminées");
        }

        intervention.setStatut(StatutIntervention.TERMINEE);
        intervention.setDateFin(LocalDateTime.now());

        if (commentaires != null && !commentaires.trim().isEmpty()) {
            intervention.setDescription(intervention.getDescription() + "\n\nCommentaires : " + commentaires);
        }

        // Calculer la durée réelle
        if (intervention.getDateDebut() != null && intervention.getDateFin() != null) {
            long minutes = java.time.Duration.between(intervention.getDateDebut(), intervention.getDateFin()).toMinutes();
            intervention.setDureeReelle((float) minutes / 60); // Convertir en heures
        }

        return interventionRepository.save(intervention);
    }

    /**
     * Mettre à jour une intervention
     */
    public Intervention modifierIntervention(Long id, Intervention interventionModifiee) {
        Intervention intervention = obtenirInterventionParId(id);

        // Mise à jour des champs modifiables
        if (interventionModifiee.getDescription() != null) {
            intervention.setDescription(interventionModifiee.getDescription());
        }
        if (interventionModifiee.getTypeIntervention() != null) {
            intervention.setTypeIntervention(interventionModifiee.getTypeIntervention());
        }
        if (interventionModifiee.getDegreUrgence() != null) {
            intervention.setDegreUrgence(interventionModifiee.getDegreUrgence());
        }
        if (interventionModifiee.getDureeEstimee() != null) {
            intervention.setDureeEstimee(interventionModifiee.getDureeEstimee());
        }

        return interventionRepository.save(intervention);
    }

    /**
     * Supprimer une intervention
     */
    public void supprimerIntervention(Long id) {
        Intervention intervention = obtenirInterventionParId(id);

        // Vérification métier : ne pas supprimer une intervention en cours
        if (intervention.getStatut() == StatutIntervention.EN_COURS) {
            throw new IllegalStateException("Impossible de supprimer une intervention en cours");
        }

        interventionRepository.deleteById(id);
    }

    /**
     * Obtenir les interventions urgentes (degré 1)
     */
    @Transactional(readOnly = true)
    public List<Intervention> obtenirInterventionsUrgentes() {
        return interventionRepository.findByDegreUrgence(1);
    }

    /**
     * Obtenir le nombre d'interventions par statut
     */
    @Transactional(readOnly = true)
    public long compterInterventionsParStatut(StatutIntervention statut) {
        return interventionRepository.countByStatut(statut);
    }

    /**
     * Planifier l'arrêt et la remise en service d'un équipement
     */
    public Intervention planifierArretEquipement(Long interventionId, LocalDateTime dateArret,
                                                 LocalDateTime dateRemiseService) {
        Intervention intervention = obtenirInterventionParId(interventionId);

        if (dateArret != null && dateRemiseService != null && dateArret.isAfter(dateRemiseService)) {
            throw new IllegalArgumentException("La date d'arrêt ne peut pas être postérieure à la remise en service");
        }

        intervention.setDateArretEquipement(dateArret);
        intervention.setDateRemiseService(dateRemiseService);

        return interventionRepository.save(intervention);
    }
}