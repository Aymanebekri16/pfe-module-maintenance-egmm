package com.egmm.maintenance.service;

import com.egmm.maintenance.entity.Equipement;
import com.egmm.maintenance.repository.EquipementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EquipementServiceTest {

    @Autowired
    private EquipementService equipementService;

    @Autowired
    private EquipementRepository equipementRepository;

    @Test
    void testCreerEtObtenirEquipement() {
        // Créer un équipement
        Equipement equipement = new Equipement("Broyeur Test", "TEST-001");
        equipement.setModele("PM20");
        equipement.setDateMiseEnService(LocalDate.now());

        // Sauvegarder
        Equipement sauvegarde = equipementService.creerEquipement(equipement);

        // Vérifications
        assertNotNull(sauvegarde.getId());
        assertEquals("TEST-001", sauvegarde.getCode());
        assertEquals("Broyeur Test", sauvegarde.getNom());

        // Récupérer par ID
        Equipement recupere = equipementService.obtenirEquipementParId(sauvegarde.getId());
        assertEquals("TEST-001", recupere.getCode());
    }

    @Test
    void testRechercheParNom() {
        // Créer des équipements de test
        equipementService.creerEquipement(new Equipement("Broyeur Alpha", "ALPHA-001"));
        equipementService.creerEquipement(new Equipement("Concasseur Beta", "BETA-001"));

        // Rechercher
        List<Equipement> resultats = equipementService.rechercherEquipementsParNom("broyeur");

        // Vérifications
        assertEquals(1, resultats.size());
        assertEquals("ALPHA-001", resultats.get(0).getCode());
    }
}