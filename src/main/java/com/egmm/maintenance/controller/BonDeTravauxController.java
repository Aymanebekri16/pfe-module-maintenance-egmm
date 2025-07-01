package com.egmm.maintenance.controller;

import com.egmm.maintenance.entity.BonDeTravaux;
import com.egmm.maintenance.entity.StatutBonTravaux;
import com.egmm.maintenance.service.BonDeTravauxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bons-de-travaux")
@CrossOrigin(origins = "*")
public class BonDeTravauxController {

    @Autowired
    private BonDeTravauxService bonDeTravauxService;

    @PostMapping
    public ResponseEntity<?> createBonDeTravaux(@RequestParam Long anomalieId, @Valid @RequestBody BonDeTravaux bonDeTravaux) {
        try {
            BonDeTravaux createdBon = bonDeTravauxService.createBonDeTravaux(anomalieId, bonDeTravaux);
            return new ResponseEntity<>(createdBon, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonDeTravaux> getBonDeTravauxById(@PathVariable Long id) {
        return ResponseEntity.ok(bonDeTravauxService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BonDeTravaux>> getAllBonsDeTravaux() {
        return ResponseEntity.ok(bonDeTravauxService.findAll());
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<BonDeTravaux> updateStatutBonDeTravaux(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            StatutBonTravaux nouveauStatut = StatutBonTravaux.valueOf(requestBody.get("statut").toUpperCase());
            BonDeTravaux bonMisAJour = bonDeTravauxService.updateStatut(id, nouveauStatut);
            return ResponseEntity.ok(bonMisAJour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
