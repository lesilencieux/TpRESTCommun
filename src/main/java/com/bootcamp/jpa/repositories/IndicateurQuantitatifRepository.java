/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.IndicateurQuantitatif;

/**
 *
 * @author Administrateur
 */
public class IndicateurQuantitatifRepository extends BaseRepository<IndicateurQuantitatif>{
 
    public IndicateurQuantitatifRepository(String unitPersistence) {
        super(unitPersistence,IndicateurQuantitatif.class);
    }
    
}
