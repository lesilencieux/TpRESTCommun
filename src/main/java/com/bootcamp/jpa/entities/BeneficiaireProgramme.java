/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "tp_beneficiaire_programme")
public class BeneficiaireProgramme implements Serializable {
    
    /*
    *Debut de la 
    *declaration des variables de la classe
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
     /*
    *fin de la
    *declaration des variables de la classe
    */
    
     //mapping avec beneficiaire
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "beneficiaire_id",referencedColumnName = "id")
    private Beneficiaire beneficiaire;
    
    //mapping avec programme
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "programme_id",referencedColumnName = "id")
    private Programme programme;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }
    
    
}
