package com.egmm.maintenance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pieces_rechange")
public class PieceRechange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "code_ref", nullable = false, length = 50)
    private String codeRef;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String designation;

    @NotNull
    @Column(nullable = false)
    private Integer quantite;

    @Column(name = "num_bon_enlevement", length = 50)
    private String numBonEnlevement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intervention_id")
    private Intervention intervention;

    public PieceRechange() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodeRef() { return codeRef; }
    public void setCodeRef(String codeRef) { this.codeRef = codeRef; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public String getNumBonEnlevement() { return numBonEnlevement; }
    public void setNumBonEnlevement(String numBonEnlevement) { this.numBonEnlevement = numBonEnlevement; }

    public Intervention getIntervention() { return intervention; }
    public void setIntervention(Intervention intervention) { this.intervention = intervention; }
}