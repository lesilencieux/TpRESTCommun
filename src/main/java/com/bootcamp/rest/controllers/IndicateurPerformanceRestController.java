/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.IndicateurPerformance;
import com.bootcamp.jpa.entities.IndicateurQualitatif;
import com.bootcamp.jpa.entities.IndicateurQuantitatif;
import com.bootcamp.jpa.repositories.IndicateurPerformanceRepository;
import com.bootcamp.jpa.repositories.IndicateurQualitatifRepository;
import com.bootcamp.jpa.repositories.IndicateurQuantitatifRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author root
 */
@Path("/indicateurs")
public class IndicateurPerformanceRestController {

    IndicateurPerformanceRepository ir = new IndicateurPerformanceRepository("punit-mysql");
    //Cette methode cree une nouvelle instance de indicateur et la retourne
    @GET
    @Path("/unpersist_list")
    @Produces("application/json")
    public Response getList(){
        return Response.status(200).entity(new IndicateurPerformance()).build();
    }
    /*
    *
    *La methode ci-dessous  cree une instance de IndicateurPerformanceRepository (ir) , 
    *applique sur cette derniere la methode findAll de cette classe
    *puis essaie de retourner la liste des indicateurs persistés.
    *si tout ce passe bien la liste est retournee si l'exeption est levee 
    *et le message d'erreur est retournee dans le catch.
    * Cette liste est accessible avec URI /list
    *
    */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getListIndicateurPerformanceFromDB(){
        try {
            return Response.status(200).entity(ir.findAll()).build();
        } catch (Exception e) {
            return Response.status(404).entity("Erreur ! Veuillez revoir l' URL et reessayez").build();
        }      
    }
    /*
    *
    *La methode  ci-dessous cree une instance de IndicateurPerformanceRepository (ir) , 
    *applique sur cette derniere la methode findById de la classe
    *pour retourner le indicateur   persisté avec l'indentifiant specifié dans 
    *l'URI d'acces qui est /list/id ou id est l'identifiant.
    *
    */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getIndicateurPerformanceByIdFromDB(@PathParam("id") int id) throws SQLException{
        IndicateurPerformance indicateur = ir.findById(id);
        try {
            //Verification de l'existance de l'id dans la base de donnee
            
            if(indicateur == null){
            return Response.status(500).entity("Aucun indicateur ne correspond a l'indifiant indique").build();
        } else
           return Response.status(200).entity(indicateur).build();
        } catch (Exception e) {
             return Response.status(401).entity("Veuillez verifier si c'est bien un URL correcte qque vous entrez").build();
        }  
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createBene(IndicateurPerformance indicateur) throws SQLException {
               
       
        if(ir.create(indicateur)){
            
            return Response.status(202).entity("l'entite est cree est succes").build();
        }else{
          return Response.status(404).entity("Erreur de creation de l'entite").build();  
        }

    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBene(IndicateurPerformance indicateur) throws SQLException {
        
        
        ir.update(indicateur);
        return Response.status(202).entity("l'entite est bien mise a jour ou cree s'il n'existait pas").build();
       
    }
    
    @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          IndicateurPerformance b= ir.findById(id);
        try {
            ir.delete(b);
            return Response.status(202).entity("l'entite est supprime est succes").build();
        } catch (Exception e) {
             return Response.status(404).entity("Erreur de suppression de l'entite").build();
        }
     
    }
    

    
    public List indicateurQuantitatifAllreadyExist(IndicateurPerformance  indiq){
        
        boolean b=false;

         IndicateurQuantitatifRepository inqantre = new IndicateurQuantitatifRepository("punit-mysql");
        List< IndicateurQuantitatif> indicateurQuantitatifs =inqantre.findAll();
        List< IndicateurQuantitatif> NotExitListOfIndicateurQuantitatifs = new ArrayList<IndicateurQuantitatif>();
        NotExitListOfIndicateurQuantitatifs.clear();
        for (int i = 0; i < indicateurQuantitatifs.size()-1; i++) {
            for (IndicateurQuantitatif indicateurQuantitatif : indicateurQuantitatifs) {
             if(!(indiq.getIndicateurQuantitatifs().get(i).equals(indicateurQuantitatif))){
                NotExitListOfIndicateurQuantitatifs.add(indicateurQuantitatif);
             }
           }
        }
        return NotExitListOfIndicateurQuantitatifs;
    }
    
    public List indicateurQualitatifAllreadyExist(IndicateurPerformance  indiq){
        
        boolean b=false;
        IndicateurQualitatifRepository inqalre = new IndicateurQualitatifRepository("punit-mysql");
        List< IndicateurQualitatif> indicateurQualitatifs =inqalre.findAll();
        List< IndicateurQualitatif> NotExistListOfIndicateurQualitatifs = new ArrayList<>();
            NotExistListOfIndicateurQualitatifs.clear();
        for (int i = 0; i < indicateurQualitatifs.size()-1; i++) {
            for (IndicateurQualitatif indicateurQualitatif : indicateurQualitatifs) {
                if(!(indiq.getIndicateurQualitatifs().get(i).equals(indicateurQualitatif))){
                    NotExistListOfIndicateurQualitatifs.add(indicateurQualitatif);
                }
            }
        }
        return NotExistListOfIndicateurQualitatifs;
    }
}
