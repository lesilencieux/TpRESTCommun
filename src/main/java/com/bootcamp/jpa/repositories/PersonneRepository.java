/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.Personne;

/**
 *
 * @author Iso-Doss
 */
public class PersonneRepository extends BaseRepository<Personne> {

    public PersonneRepository(String UnitPersistence) {
        super(UnitPersistence, Personne.class);
    }
}
