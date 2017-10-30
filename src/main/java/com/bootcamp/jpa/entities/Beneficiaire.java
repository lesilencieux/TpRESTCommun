/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Administrateur
 */
@Entity
@DiscriminatorValue(value = "Beneficiaire")
public class Beneficiaire extends Personne implements Serializable {

    /*
    *Debut de la 
    *declaration des variables de la classe
     */
 /*
    *fin de la
    *declaration des variables de la classe
     */
    //Les mappings
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "beneficiaire")
    private final List<BeneficiaireProgramme> programmes = new ArrayList<BeneficiaireProgramme>();

    //
    @ManyToMany(mappedBy = "beneficiaires")
    private final List<Projet> projets = new ArrayList<Projet>();

    public List<BeneficiaireProgramme> getProgrammes() {
        return programmes;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    @Override
    public String toString() {
        return "Beneficiaire{" + "programmes=" + programmes + ", projets=" + projets + '}';
    }

}
