/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.Beneficiaire;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class BeneficiaireRepository extends BaseRepository<Beneficiaire> {

    public BeneficiaireRepository(String unitPersistence) {
        super(unitPersistence, Beneficiaire.class);
    }

    public List<Beneficiaire> getBeneficiaireOfProgramme(int id) {
        String str = "SELECT  bene FROM Beneficiaire bene \n"
                + "JOIN bene.programmes bene__prog JOIN beneprog.programme prog WHERE prog.id =:identifiant";
        Query query = getEntityManager().createQuery(str);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }

    public List<Beneficiaire> getBeneficiaireOfProjet(int id) {
        String s = "SELECT   bene FROM Beneficiaire bene\n"
                + "JOIN bene.projets prog WHERE prog.id =:identifiant";
        Query query = getEntityManager().createQuery(s);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }
}
