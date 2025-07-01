package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "anomalies")
public class Anomalie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro PVCA est obligatoire")
    @Size(max = 20)
    @Column(name = "num_pvca", nullable = false, length = 20)
    private String numPVCA;

    @NotNull
    @Column(name = "date_constat", nullable = false)
    private LocalDate dateConstat;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull
    @Column(name = "degre_urgence", nullable = false)
    private Integer degreUrgence;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutAnomalie statut = StatutAnomalie.NOUVELLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipement_id", nullable = false)
    private Equipement equipement;

    public Anomalie() {}

    public Anomalie(String numPVCA, LocalDate dateConstat, String description, Integer degreUrgence, Equipement equipement) {
        this.numPVCA = numPVCA;
        this.dateConstat = dateConstat;
        this.description = description;
        this.degreUrgence = degreUrgence;
        this.equipement = equipement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumPVCA() { return numPVCA; }
    public void setNumPVCA(String numPVCA) { this.numPVCA = numPVCA; }

    public LocalDate getDateConstat() { return dateConstat; }
    public void setDateConstat(LocalDate dateConstat) { this.dateConstat = dateConstat; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDegreUrgence() { return degreUrgence; }
    public void setDegreUrgence(Integer degreUrgence) { this.degreUrgence = degreUrgence; }

    public StatutAnomalie getStatut() { return statut; }
    public void setStatut(StatutAnomalie statut) { this.statut = statut; }

    public Equipement getEquipement() { return equipement; }
    public void setEquipement(Equipement equipement) { this.equipement = equipement; }
}