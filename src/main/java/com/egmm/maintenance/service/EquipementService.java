package com.egmm.maintenance.service;

import com.egmm.maintenance.entity.Equipement;
import com.egmm.maintenance.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipementService {

    @Autowired
    private EquipementRepository equipementRepository;

    public Equipement creerEquipement(Equipement equipement) {
        if (equipementRepository.existsByCode(equipement.getCode())) {
            throw new IllegalArgumentException("Un équipement avec ce code existe déjà : " + equipement.getCode());
        }
        return equipementRepository.save(equipement);
    }

    public Equipement obtenirEquipementParId(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé avec l'ID : " + id));
    }

    public List<Equipement> obtenirTousLesEquipements() {
        return equipementRepository.findAll();
    }

    public List<Equipement> rechercherEquipementsParNom(String nom) {
        return equipementRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Equipement> obtenirEquipementsNecessitantMaintenance() {
        LocalDate aujourdhui = LocalDate.now();
        return equipementRepository.findByProchainEntretienLessThanEqual(aujourdhui);
    }

    public Equipement modifierEquipement(Long id, Equipement equipementModifie) {
        Equipement equipement = obtenirEquipementParId(id);

        equipement.setNom(equipementModifie.getNom());
        equipement.setCode(equipementModifie.getCode());
        equipement.setModele(equipementModifie.getModele());
        equipement.setNumeroSerie(equipementModifie.getNumeroSerie());
        equipement.setDateMiseEnService(equipementModifie.getDateMiseEnService());
        equipement.setDernierEntretien(equipementModifie.getDernierEntretien());
        equipement.setProchainEntretien(equipementModifie.getProchainEntretien());

        return equipementRepository.save(equipement);
    }

    public void supprimerEquipement(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new RuntimeException("Équipement non trouvé avec l'ID : " + id);
        }
        equipementRepository.deleteById(id);
    }

    public boolean equipementExisteParCode(String code) {
        return equipementRepository.existsByCode(code);
    }
}