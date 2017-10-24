/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "tp_livrable")
public class Livrable implements Serializable {
    /*
    *Debut de la 
    *declaration des variables de la classe
    */
     @Id
    private int id;
    @NotNull(message="Ce champs ne doit pas etre vide")
    private String nom ;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeDebut ,dateDeFin;
    /*
    *fin de la
    *declaration des variables de la classe
    */
    
    //mapping avec projet
    @ManyToOne
    @JoinColumn(name = "projet", referencedColumnName = "id")
    private Projet projet;
    
    @OneToOne(fetch = FetchType.LAZY)
    private IndicateurPerformance indicateurPerformance;

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

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public IndicateurPerformance getIndicateurPerformance() {
        return indicateurPerformance;
    }

    public void setIndicateurPerformance(IndicateurPerformance indicateurPerformance) {
        this.indicateurPerformance = indicateurPerformance;
    }
      
    
}
