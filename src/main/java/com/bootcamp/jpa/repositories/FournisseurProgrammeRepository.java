/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.FournisseurProgramme;

/**
 *
 * @author Administrateur
 */
public class FournisseurProgrammeRepository  extends BaseRepository<FournisseurProgramme>{

    public FournisseurProgrammeRepository(String unitPersistence) {
        super(unitPersistence,FournisseurProgramme.class);
    }
    
    
    
}
