package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name = "num_fiche", length = 20)
    private String numFiche;

    @Size(max = 20)
    @Column(name = "num_pvca", length = 20)
    private String numPVCA;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "date_arret_equipement")
    private LocalDateTime dateArretEquipement;

    @Column(name = "date_remise_service")
    private LocalDateTime dateRemiseService;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutIntervention statut = StatutIntervention.PLANIFIEE;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_intervention", length = 20)
    private TypeIntervention typeIntervention;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @Column(name = "duree_estimee")
    private Float dureeEstimee;

    @Column(name = "duree_reelle")
    private Float dureeReelle;

    @Column(name = "degre_urgence")
    private Integer degreUrgence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipement_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Equipement equipement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_travaux_id")
    private BonDeTravaux bonTravaux;

    @ManyToMany
    @JoinTable(
            name = "intervention_technicien",
            joinColumns = @JoinColumn(name = "intervention_id"),
            inverseJoinColumns = @JoinColumn(name = "technicien_id")
    )
    private List<Technicien> techniciens = new ArrayList<>();

    public Intervention() {}

    public Intervention(LocalDateTime dateDebut, String description, TypeIntervention typeIntervention, Equipement equipement) {
        this.dateDebut = dateDebut;
        this.description = description;
        this.typeIntervention = typeIntervention;
        this.equipement = equipement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumFiche() { return numFiche; }
    public void setNumFiche(String numFiche) { this.numFiche = numFiche; }

    public String getNumPVCA() { return numPVCA; }
    public void setNumPVCA(String numPVCA) { this.numPVCA = numPVCA; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public LocalDateTime getDateArretEquipement() { return dateArretEquipement; }
    public void setDateArretEquipement(LocalDateTime dateArretEquipement) { this.dateArretEquipement = dateArretEquipement; }

    public LocalDateTime getDateRemiseService() { return dateRemiseService; }
    public void setDateRemiseService(LocalDateTime dateRemiseService) { this.dateRemiseService = dateRemiseService; }

    public StatutIntervention getStatut() { return statut; }
    public void setStatut(StatutIntervention statut) { this.statut = statut; }

    public TypeIntervention getTypeIntervention() { return typeIntervention; }
    public void setTypeIntervention(TypeIntervention typeIntervention) { this.typeIntervention = typeIntervention; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Float getDureeEstimee() { return dureeEstimee; }
    public void setDureeEstimee(Float dureeEstimee) { this.dureeEstimee = dureeEstimee; }

    public Float getDureeReelle() { return dureeReelle; }
    public void setDureeReelle(Float dureeReelle) { this.dureeReelle = dureeReelle; }

    public Integer getDegreUrgence() { return degreUrgence; }
    public void setDegreUrgence(Integer degreUrgence) { this.degreUrgence = degreUrgence; }

    public Equipement getEquipement() { return equipement; }
    public void setEquipement(Equipement equipement) { this.equipement = equipement; }

    public BonDeTravaux getBonTravaux() { return bonTravaux; }
    public void setBonTravaux(BonDeTravaux bonTravaux) { this.bonTravaux = bonTravaux; }

    public List<Technicien> getTechniciens() { return techniciens; }
    public void setTechniciens(List<Technicien> techniciens) { this.techniciens = techniciens; }
}