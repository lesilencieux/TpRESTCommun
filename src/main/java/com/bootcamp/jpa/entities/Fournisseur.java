/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Administrateur
 */
@Entity
@DiscriminatorValue(value = "Fournisseur")
public class Fournisseur extends Personne {

    /*
    *Debut de la 
    *declaration des variables de la classe
     */
 /*
    *fin de la
    *declaration des variables de la classe
     */
 /*
    *
    *Les mappings
    *
     */
    //Representation de la collection de programme dans fournisseur
    @OneToMany(mappedBy = "fournisseur")
    private final List<FournisseurProgramme> programmes = new ArrayList<FournisseurProgramme>();

    //
    @ManyToMany(mappedBy = "fournisseurs")
    private final List<Projet> projets = new ArrayList<Projet>();

    public List<FournisseurProgramme> getProgrammes() {
        return programmes;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    @Override
    public String toString() {
        return "Fournisseur{" + "programmes=" + programmes + ", projets=" + projets + '}';
    }

}
