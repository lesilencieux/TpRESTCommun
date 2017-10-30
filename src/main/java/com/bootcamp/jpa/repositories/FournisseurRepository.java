/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.Fournisseur;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class FournisseurRepository extends BaseRepository<Fournisseur> {

    public FournisseurRepository(String unitPersistence) {
        super(unitPersistence, Fournisseur.class);
    }

    public List<Fournisseur> getFournisseurOfProgramme(long id) {
        String str = "SELECT  four FROM Fournisseur four\n"
                + "JOIN four.programmes four_prog JOIN four_prog.programme prog WHERE prog.id =:identifiant";
        Query query = getEntityManager().createQuery(str);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }

    public List<Fournisseur> getFournisseurOfProjet(long id) {
        String str = "SELECT  four FROM Fournisseur four\n"
                + "JOIN four.projets prog  WHERE prog.id =:identifiant";
        Query query = getEntityManager().createQuery(str);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }

}
