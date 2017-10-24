/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.IndicateurPerformance;
import com.bootcamp.jpa.entities.IndicateurQualitatif;

/**
 *
 * @author Administrateur
 */
public class IndicateurQualitatifRepository extends BaseRepository<IndicateurQualitatif>{


    
    public IndicateurQualitatifRepository(String unitPersistence) {
        super(unitPersistence,IndicateurPerformance.class);
    }
}
