package com.egmm.maintenance.controller;

import com.egmm.maintenance.entity.Equipement;
import com.egmm.maintenance.service.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@CrossOrigin(origins = "*")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

        @GetMapping
    public ResponseEntity<List<Equipement>> obtenirTousLesEquipements() {
        List<Equipement> equipements = equipementService.obtenirTousLesEquipements();
        return ResponseEntity.ok(equipements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> obtenirEquipementParId(@PathVariable Long id) {
        try {
            Equipement equipement = equipementService.obtenirEquipementParId(id);
            return ResponseEntity.ok(equipement);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> creerEquipement(@Valid @RequestBody Equipement equipement) {
        try {
            Equipement nouvelEquipement = equipementService.creerEquipement(equipement);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelEquipement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierEquipement(@PathVariable Long id, @Valid @RequestBody Equipement equipement) {
        try {
            Equipement equipementModifie = equipementService.modifierEquipement(id, equipement);
            return ResponseEntity.ok(equipementModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEquipement(@PathVariable Long id) {
        try {
            equipementService.supprimerEquipement(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Equipement>> rechercherEquipements(@RequestParam String nom) {
        List<Equipement> equipements = equipementService.rechercherEquipementsParNom(nom);
        return ResponseEntity.ok(equipements);
    }

    @GetMapping("/maintenance-requise")
    public ResponseEntity<List<Equipement>> equipementsNecessitantMaintenance() {
        List<Equipement> equipements = equipementService.obtenirEquipementsNecessitantMaintenance();
        return ResponseEntity.ok(equipements);
    }
}