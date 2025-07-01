package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "techniciens")
public class Technicien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nom;

    @Size(max = 100)
    @Column(length = 100)
    private String specialite;

    @Column(nullable = false)
    private Boolean disponibilite = true;

    @ManyToMany(mappedBy = "techniciens")
    private List<Intervention> interventions = new ArrayList<>();

    public Technicien() {}

    public Technicien(String nom, String specialite) {
        this.nom = nom;
        this.specialite = specialite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public Boolean getDisponibilite() { return disponibilite; }
    public void setDisponibilite(Boolean disponibilite) { this.disponibilite = disponibilite; }

    public List<Intervention> getInterventions() { return interventions; }
    public void setInterventions(List<Intervention> interventions) { this.interventions = interventions; }
}