package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "equipements")
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le code est obligatoire")
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Size(max = 50)
    @Column(length = 50)
    private String modele;

    @Size(max = 50)
    @Column(name = "numero_serie", length = 50)
    private String numeroSerie;

    @Column(name = "date_mise_en_service")
    private LocalDate dateMiseEnService;

    @Column(name = "dernier_entretien")
    private LocalDate dernierEntretien;

    @Column(name = "prochain_entretien")
    private LocalDate prochainEntretien;

    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Anomalie> anomalies = new ArrayList<>();

    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore

    private List<Intervention> interventions = new ArrayList<>();
    public Equipement() {}

    public Equipement(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public LocalDate getDateMiseEnService() { return dateMiseEnService; }
    public void setDateMiseEnService(LocalDate dateMiseEnService) { this.dateMiseEnService = dateMiseEnService; }

    public LocalDate getDernierEntretien() { return dernierEntretien; }
    public void setDernierEntretien(LocalDate dernierEntretien) { this.dernierEntretien = dernierEntretien; }

    public LocalDate getProchainEntretien() { return prochainEntretien; }
    public void setProchainEntretien(LocalDate prochainEntretien) { this.prochainEntretien = prochainEntretien; }

    public List<Anomalie> getAnomalies() { return anomalies; }
    public void setAnomalies(List<Anomalie> anomalies) { this.anomalies = anomalies; }

    public List<Intervention> getInterventions() { return interventions; }
    public void setInterventions(List<Intervention> interventions) { this.interventions = interventions; }
}