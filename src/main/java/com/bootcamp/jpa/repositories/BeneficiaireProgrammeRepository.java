/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.BeneficiaireProgramme;

/**
 *
 * @author Administrateur
 */
public class BeneficiaireProgrammeRepository  extends BaseRepository<BeneficiaireProgramme>{

    public BeneficiaireProgrammeRepository(String unitPersistence) {
        super(unitPersistence , BeneficiaireProgramme.class);
    }
}
