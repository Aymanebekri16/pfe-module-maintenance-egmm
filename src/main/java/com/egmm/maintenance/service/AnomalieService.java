package com.egmm.maintenance.service;

import com.egmm.maintenance.entity.Anomalie;
import com.egmm.maintenance.entity.Equipement;
import com.egmm.maintenance.repository.AnomalieRepository;
import com.egmm.maintenance.repository.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnomalieService {

    @Autowired
    private AnomalieRepository anomalieRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    public List<Anomalie> findAll() {
        return anomalieRepository.findAll();
    }

    public Anomalie findById(Long id) {
        return anomalieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anomalie not found with id: " + id));
    }

    public Anomalie createAnomalie(Anomalie anomalie, Long equipementId) {
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new RuntimeException("Equipement not found with id: " + equipementId));
        anomalie.setEquipement(equipement);
        return anomalieRepository.save(anomalie);
    }
}