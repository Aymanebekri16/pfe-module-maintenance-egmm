package com.egmm.maintenance.service;

import com.egmm.maintenance.entity.Anomalie;
import com.egmm.maintenance.entity.BonDeTravaux;
import com.egmm.maintenance.entity.StatutAnomalie;
import com.egmm.maintenance.entity.StatutBonTravaux;
import com.egmm.maintenance.repository.AnomalieRepository;
import com.egmm.maintenance.repository.BonDeTravauxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BonDeTravauxService {

    @Autowired
    private BonDeTravauxRepository bonDeTravauxRepository;

    @Autowired
    private AnomalieRepository anomalieRepository;

    public BonDeTravaux createBonDeTravaux(Long anomalieId, BonDeTravaux bonDeTravauxDetails) {
        Anomalie anomalie = anomalieRepository.findById(anomalieId)
                .orElseThrow(() -> new RuntimeException("Anomalie non trouvée avec l'ID : " + anomalieId));

        if (bonDeTravauxRepository.findByAnomalieId(anomalieId).isPresent()) {
            throw new IllegalStateException("Un bon de travaux existe déjà pour cette anomalie.");
        }

        if (anomalie.getStatut() == StatutAnomalie.RESOLUE || anomalie.getStatut() == StatutAnomalie.FERMEE) {
            throw new IllegalStateException("Impossible de créer un bon de travaux pour une anomalie déjà résolue ou fermée.");
        }

        bonDeTravauxDetails.setAnomalie(anomalie);
        bonDeTravauxDetails.setEquipement(anomalie.getEquipement()); // <-- CORRECTION APPLIQUÉE ICI
        bonDeTravauxDetails.setDateCreation(LocalDate.now());
        bonDeTravauxDetails.setStatut(StatutBonTravaux.PLANIFIEE);
        bonDeTravauxDetails.setDegreUrgence(anomalie.getDegreUrgence());

        anomalie.setStatut(StatutAnomalie.EN_TRAITEMENT);
        anomalieRepository.save(anomalie);

        return bonDeTravauxRepository.save(bonDeTravauxDetails);
    }

    public BonDeTravaux findById(Long id) {
        return bonDeTravauxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de travaux non trouvé avec l'ID : " + id));
    }

    public List<BonDeTravaux> findAll() {
        return bonDeTravauxRepository.findAll();
    }

    public BonDeTravaux updateStatut(Long id, StatutBonTravaux nouveauStatut) {
        BonDeTravaux bonDeTravaux = findById(id);
        bonDeTravaux.setStatut(nouveauStatut);
        return bonDeTravauxRepository.save(bonDeTravaux);
    }
}
