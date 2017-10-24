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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "tp_projet")
public class Projet implements Serializable {
    /*
    *Debut de la 
    *declaration des variables de la classe
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message="Ce champs ne doit pas etre vide")
    private String nom, objetif ;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeDebut ,dateDeFin;
    @NotNull(message="Ce champs ne doit pas etre vide")
    private int budgetPrevisioonel,budgetEffectif;
    
    /*
    *fin de la
    *declaration des variables de la classe
    */
    //mapping avec programme
    @ManyToOne
    @JoinColumn(name = "programme_id",referencedColumnName = "id")
    private Programme programme;
    
    //mapping avec indicateur de performance
    @OneToOne(fetch = FetchType.LAZY)
    private IndicateurPerformance indicateurPerformance;
    
    //Representation de la collection de livrable dans projet
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projet")
    private List<Livrable> livrables;
    
    //
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tp_projet_fournisseur",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "fournisseur_id")
    )
    private final List<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
    
    //
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tp_projet_bailleur",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "bailleur_id")
    )
    private final List<Bailleur> bailleurs = new ArrayList<Bailleur>();
    
    //
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tp_projet_beneficiaire",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficiaire_id")
    )
    private final List<Beneficiaire> beneficiaires = new ArrayList<Beneficiaire>();

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

    public String getObjetif() {
        return objetif;
    }

    public void setObjetif(String objetif) {
        this.objetif = objetif;
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

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public IndicateurPerformance getIndicateurPerformance() {
        return indicateurPerformance;
    }

    public void setIndicateurPerformance(IndicateurPerformance indicateurPerformance) {
        this.indicateurPerformance = indicateurPerformance;
    }

    public List<Livrable> getLivrables() {
        return livrables;
    }

    public void setLivrables(List<Livrable> livrables) {
        this.livrables = livrables;
    }


    
}
