/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import com.bootcamp.jpa.entities.Bailleur;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class BailleurRepository extends BaseRepository<Bailleur>{

    public BailleurRepository(String unitPersistence) {
        super(unitPersistence,Bailleur.class);
    }
    
    public List<Bailleur> getBailleursOfProgramme(int id){
        String s = "SELECT  bail FROM Bailleur bail \n "+
                "JOIN bail.programmes bail_prog JOIN bail.programme prog WHERE prog.id = : identifiant";
        Query query = getEntityManager().createQuery(s);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }
    
    public List<Bailleur> getBailleurOfProjet(int id){
        String str = "SELECT   bail FROM Bailleur bail \n "+
                "JOIN bail.projets bail_prog JOIN  bail.projet prog WHERE prog.id = : identifiant";
        Query query =  getEntityManager().createQuery(str);
        query.setParameter("identifiant", id);
        return query.getResultList();
    }
    
    
    
    
}
