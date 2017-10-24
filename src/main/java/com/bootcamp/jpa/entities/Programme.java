/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "tp_programme")
public class Programme implements Serializable {
   /*
    *Debut de la 
    *declaration des variables de la classe
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message="Ce champs ne doit pas etre vide")
    private String nom, objectif ;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeDebut ,dateDeFin;
    @NotNull(message="Ce champs ne doit pas etre vide")
    private int budgetPrevisioonel,budgetEffectif;
    
    /*
    *fin de la
    *declaration des variables de la classe
    */
    
     /*
    *
    *Les mappings
    *
    */
    
    //mapping avec indicateur performance
    @OneToOne(fetch = FetchType.LAZY)
    private IndicateurPerformance indicateurPerformance;
    
    //Representation de la collection de beneficiaire dans programme
    @OneToMany(mappedBy = "programme")
    private final List<BeneficiaireProgramme> beneficiaires = new ArrayList<BeneficiaireProgramme>();
    
    //Representation de la collection de bailleur dans programme
    @OneToMany(mappedBy = "programme")
    private final List<BailleurProgramme> bailleurs = new ArrayList<BailleurProgramme>();
    
    //Representation de la collection de fournisseur dans programme
    @OneToMany(mappedBy = "programme")
    private final List<FournisseurProgramme> programmes = new ArrayList<FournisseurProgramme>();
    
    //Representation de la collection du projet dans progamme
    @OneToMany(cascade =  CascadeType.ALL,mappedBy = "programme")
    private final List<Projet> projets =new ArrayList<Projet>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    
    public Date getDateDeDebut() {
        return dateDeDebut;
    }

    public void setDateDeDebut(Date dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public Date getDateDeFin() {
        return dateDeFin;
    }

    public void setDateDeFin(Date dateDeFin) {
        this.dateDeFin = dateDeFin;
    }

    public int getBudgetPrevisioonel() {
        return budgetPrevisioonel;
    }

    public void setBudgetPrevisioonel(int budgetPrevisioonel) {
        this.budgetPrevisioonel = budgetPrevisioonel;
    }

    public int getBudgetEffectif() {
        return budgetEffectif;
    }

    public void setBudgetEffectif(int budgetEffectif) {
        this.budgetEffectif = budgetEffectif;
    }

    public IndicateurPerformance getIndicateurPerformance() {
        return indicateurPerformance;
    }

    public void setIndicateurPerformance(IndicateurPerformance indicateurPerformance) {
        this.indicateurPerformance = indicateurPerformance;
    }

    
}
