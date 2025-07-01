package com.egmm.maintenance.controller;

import com.egmm.maintenance.entity.Intervention;
import com.egmm.maintenance.entity.StatutIntervention;
import com.egmm.maintenance.entity.TypeIntervention;
import com.egmm.maintenance.service.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interventions")
@CrossOrigin(origins = "*")
public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    // GET /api/interventions - Obtenir toutes les interventions
    @GetMapping
    public ResponseEntity<List<Intervention>> obtenirToutesLesInterventions() {
        List<Intervention> interventions = interventionService.obtenirToutesLesInterventions();
        return ResponseEntity.ok(interventions);
    }

    // GET /api/interventions/{id} - Obtenir une intervention par ID
    @GetMapping("/{id}")
    public ResponseEntity<Intervention> obtenirInterventionParId(@PathVariable Long id) {
        try {
            Intervention intervention = interventionService.obtenirInterventionParId(id);
            return ResponseEntity.ok(intervention);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/interventions - Créer une nouvelle intervention
    @PostMapping
    public ResponseEntity<?> creerIntervention(@Valid @RequestBody Intervention intervention) {
        try {
            Intervention nouvelleIntervention = interventionService.creerIntervention(intervention);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleIntervention);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // POST /api/interventions/equipement/{equipementId} - Créer intervention pour un équipement
    @PostMapping("/equipement/{equipementId}")
    public ResponseEntity<?> creerInterventionPourEquipement(
            @PathVariable Long equipementId,
            @RequestBody Map<String, Object> requestBody) {
        try {
            String description = (String) requestBody.get("description");
            String typeStr = (String) requestBody.get("typeIntervention");
            Integer degreUrgence = (Integer) requestBody.get("degreUrgence");

            TypeIntervention type = TypeIntervention.valueOf(typeStr);

            Intervention intervention = interventionService.creerInterventionPourEquipement(
                    equipementId, description, type, degreUrgence);
            return ResponseEntity.status(HttpStatus.CREATED).body(intervention);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // PUT /api/interventions/{id} - Modifier une intervention
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierIntervention(@PathVariable Long id,
                                                  @Valid @RequestBody Intervention intervention) {
        try {
            Intervention interventionModifiee = interventionService.modifierIntervention(id, intervention);
            return ResponseEntity.ok(interventionModifiee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/interventions/{id} - Supprimer une intervention
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerIntervention(@PathVariable Long id) {
        try {
            interventionService.supprimerIntervention(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/interventions/equipement/{equipementId} - Interventions par équipement
    @GetMapping("/equipement/{equipementId}")
    public ResponseEntity<List<Intervention>> obtenirInterventionsParEquipement(@PathVariable Long equipementId) {
        List<Intervention> interventions = interventionService.obtenirInterventionsParEquipement(equipementId);
        return ResponseEntity.ok(interventions);
    }

    // GET /api/interventions/statut/{statut} - Interventions par statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Intervention>> obtenirInterventionsParStatut(@PathVariable StatutIntervention statut) {
        List<Intervention> interventions = interventionService.obtenirInterventionsParStatut(statut);
        return ResponseEntity.ok(interventions);
    }

    // PUT /api/interventions/{id}/demarrer - Démarrer une intervention
    @PutMapping("/{id}/demarrer")
    public ResponseEntity<?> demarrerIntervention(@PathVariable Long id) {
        try {
            Intervention intervention = interventionService.demarrerIntervention(id);
            return ResponseEntity.ok(intervention);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/interventions/{id}/terminer - Terminer une intervention
    @PutMapping("/{id}/terminer")
    public ResponseEntity<?> terminerIntervention(@PathVariable Long id,
                                                  @RequestBody(required = false) Map<String, String> requestBody) {
        try {
            String commentaires = requestBody != null ? requestBody.get("commentaires") : null;
            Intervention intervention = interventionService.terminerIntervention(id, commentaires);
            return ResponseEntity.ok(intervention);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/interventions/urgentes - Interventions urgentes
    @GetMapping("/urgentes")
    public ResponseEntity<List<Intervention>> obtenirInterventionsUrgentes() {
        List<Intervention> interventions = interventionService.obtenirInterventionsUrgentes();
        return ResponseEntity.ok(interventions);
    }

    // GET /api/interventions/statistiques - Statistiques des interventions
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Long>> obtenirStatistiques() {
        Map<String, Long> stats = Map.of(
                "planifiees", interventionService.compterInterventionsParStatut(StatutIntervention.PLANIFIEE),
                "enCours", interventionService.compterInterventionsParStatut(StatutIntervention.EN_COURS),
                "terminees", interventionService.compterInterventionsParStatut(StatutIntervention.TERMINEE)
        );
        return ResponseEntity.ok(stats);
    }

    // PUT /api/interventions/{id}/planifier-arret - Planifier arrêt équipement
    @PutMapping("/{id}/planifier-arret")
    public ResponseEntity<?> planifierArretEquipement(@PathVariable Long id,
                                                      @RequestBody Map<String, String> requestBody) {
        try {
            LocalDateTime dateArret = LocalDateTime.parse(requestBody.get("dateArret"));
            LocalDateTime dateRemiseService = LocalDateTime.parse(requestBody.get("dateRemiseService"));

            Intervention intervention = interventionService.planifierArretEquipement(id, dateArret, dateRemiseService);
            return ResponseEntity.ok(intervention);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}