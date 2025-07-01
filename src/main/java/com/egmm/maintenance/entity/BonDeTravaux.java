package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "bons_travaux")
public class BonDeTravaux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro de fiche est obligatoire")
    @Size(max = 20)
    @Column(name = "num_fiche", nullable = false, unique = true, length = 20)
    private String numFiche;

    @Size(max = 20)
    @Column(name = "num_pvca", length = 20)
    private String numPVCA;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "degre_urgence", nullable = false)
    private Integer degreUrgence;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutBonTravaux statut;

    @Size(max = 500)
    @Column(name = "nature_defaut", length = 500)
    private String natureDefaut;

    @Size(max = 200)
    @Column(name = "moyens_humains", length = 200)
    private String moyensHumains;

    @Size(max = 500)
    @Column(name = "besoin_pieces_rechange", length = 500)
    private String besoinPiecesRechange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anomalie_id")
    private Anomalie anomalie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipement_id", nullable = false)
    private Equipement equipement;

    public BonDeTravaux() {}

    public BonDeTravaux(String numFiche, LocalDate dateCreation, String description, Integer degreUrgence, Equipement equipement) {
        this.numFiche = numFiche;
        this.dateCreation = dateCreation;
        this.description = description;
        this.degreUrgence = degreUrgence;
        this.equipement = equipement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumFiche() { return numFiche; }
    public void setNumFiche(String numFiche) { this.numFiche = numFiche; }

    public String getNumPVCA() { return numPVCA; }
    public void setNumPVCA(String numPVCA) { this.numPVCA = numPVCA; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDegreUrgence() { return degreUrgence; }
    public void setDegreUrgence(Integer degreUrgence) { this.degreUrgence = degreUrgence; }

    public StatutBonTravaux getStatut() { return statut; }
    public void setStatut(StatutBonTravaux statut) { this.statut = statut; }

    public String getNatureDefaut() { return natureDefaut; }
    public void setNatureDefaut(String natureDefaut) { this.natureDefaut = natureDefaut; }

    public String getMoyensHumains() { return moyensHumains; }
    public void setMoyensHumains(String moyensHumains) { this.moyensHumains = moyensHumains; }

    public String getBesoinPiecesRechange() { return besoinPiecesRechange; }
    public void setBesoinPiecesRechange(String besoinPiecesRechange) { this.besoinPiecesRechange = besoinPiecesRechange; }

    public Anomalie getAnomalie() { return anomalie; }
    public void setAnomalie(Anomalie anomalie) { this.anomalie = anomalie; }

    public Equipement getEquipement() { return equipement; }
    public void setEquipement(Equipement equipement) { this.equipement = equipement; }
}