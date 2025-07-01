package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "rapports_intervention")
public class RapportIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(length = 1000)
    private String description;

    @Column(name = "type_intervention", length = 100)
    private String typeIntervention;

    @Column(name = "nature_travaux_type", length = 100)
    private String natureTravauxType;

    @Column(name = "cause_defaillance", length = 500)
    private String causeDefaillance;

    @Column(name = "duree_reelle")
    private Float dureeReelle;

    @Column(name = "cout_total")
    private Float coutTotal;

    @Column(name = "mode_operatoire", length = 1000)
    private String modeOperatoire;

    @Column(length = 1000)
    private String observations;

    @Column(nullable = false)
    private Boolean valide = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intervention_id", nullable = false)
    private Intervention intervention;

    public RapportIntervention() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTypeIntervention() { return typeIntervention; }
    public void setTypeIntervention(String typeIntervention) { this.typeIntervention = typeIntervention; }

    public String getNatureTravauxType() { return natureTravauxType; }
    public void setNatureTravauxType(String natureTravauxType) { this.natureTravauxType = natureTravauxType; }

    public String getCauseDefaillance() { return causeDefaillance; }
    public void setCauseDefaillance(String causeDefaillance) { this.causeDefaillance = causeDefaillance; }

    public Float getDureeReelle() { return dureeReelle; }
    public void setDureeReelle(Float dureeReelle) { this.dureeReelle = dureeReelle; }

    public Float getCoutTotal() { return coutTotal; }
    public void setCoutTotal(Float coutTotal) { this.coutTotal = coutTotal; }

    public String getModeOperatoire() { return modeOperatoire; }
    public void setModeOperatoire(String modeOperatoire) { this.modeOperatoire = modeOperatoire; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public Boolean getValide() { return valide; }
    public void setValide(Boolean valide) { this.valide = valide; }

    public Intervention getIntervention() { return intervention; }
    public void setIntervention(Intervention intervention) { this.intervention = intervention; }
}