/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.IndicateurPerformance;

/**
 *
 * @author Administrateur
 */
public class IndicateurPerformanceRepository extends BaseRepository<IndicateurPerformance>{

     public IndicateurPerformanceRepository( String unitPersistence) {
         
         super(unitPersistence,IndicateurPerformance.class);
    }
     
}
