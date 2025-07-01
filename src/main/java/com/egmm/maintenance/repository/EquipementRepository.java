package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {

    Optional<Equipement> findByCode(String code);

    boolean existsByCode(String code);

    List<Equipement> findByNomContainingIgnoreCase(String nom);

    List<Equipement> findByModele(String modele);

    List<Equipement> findByProchainEntretienLessThanEqual(LocalDate date);

    Optional<Equipement> findByNumeroSerie(String numeroSerie);

    List<Equipement> findByProchainEntretienIsNull();
}