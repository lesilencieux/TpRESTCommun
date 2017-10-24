/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.BailleurProgramme;

/**
 *
 * @author Administrateur
 */
public class BailleurProgrammeRepository extends BaseRepository<BailleurProgramme>{
 
    
    public BailleurProgrammeRepository(String unitPersistence) {
        super(unitPersistence,BailleurProgramme.class);
    }
    
}
