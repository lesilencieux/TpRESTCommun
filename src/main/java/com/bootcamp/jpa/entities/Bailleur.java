/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import com.bootcamp.jpa.enums.TypeBailleur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@DiscriminatorValue(value = "Bailleur")
public class Bailleur extends Personne implements Serializable {

    /*
    *Debut de la 
    *declaration des variables de la classe
     */
    @NotNull(message = "Ce champs ne doit pas etre vide ")
    @Enumerated(EnumType.STRING)
    private TypeBailleur typeBailleur;

    /*
    *fin de la
    *declaration des variables de la classe
     */
 /*
    *
    *Les mappings
    *
     */
    //Representation de la collection de programme dans bailleur
    @OneToMany(mappedBy = "bailleur")
    private final List<BailleurProgramme> programmes = new ArrayList<BailleurProgramme>();

    //Representation de la collection de projet dans bailleur
    @ManyToMany(mappedBy = "bailleurs")
    private final List<Projet> projets = new ArrayList<Projet>();

    public TypeBailleur getTypeBailleur() {
        return typeBailleur;
    }

    public void setTypeBailleur(TypeBailleur typeBailleur) {
        this.typeBailleur = typeBailleur;
    }

    @Override
    public String toString() {
        return "Bailleur{ " + "typeBailleur=" + typeBailleur + ", programmes=" + programmes + ", projets=" + projets + " , Nom du Baiilleur" + this.getNom() + "Id " + this.getId() + '}';
    }

}
