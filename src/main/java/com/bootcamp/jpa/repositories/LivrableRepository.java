/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.Livrable;

/**
 *
 * @author Administrateur
 */
public class LivrableRepository extends BaseRepository<Livrable> {

    public LivrableRepository(String unitPersistence) {
        super(unitPersistence, Livrable.class);
    }
}
