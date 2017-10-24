/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Beneficiaire;
import com.bootcamp.jpa.repositories.BeneficiaireRepository;
import java.sql.SQLException;
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
@Path("/beneficiaires")
public class BeneficiaireRestController {

    BeneficiaireRepository br = new BeneficiaireRepository("punit-mysql");
    //Cette methode cree une nouvelle instance de beneficiaire et la retourne
    @GET
    @Path("/unpersist_list")
    @Produces("application/json")
    public Response getList(){
        return Response.status(200).entity(new Beneficiaire()).build();
    }
    /*
    *
    *La methode ci-dessous  cree une instance de BeneficiaireRepository (br) , 
    *applique sur cette derniere la methode findAll de cette classe
    *puis essaie de retourner la liste des beneficiaires persistés.
    *si tout ce passe bien la liste est retournee si l'exeption est levee 
    *et le message d'erreur est retournee dans le catch.
    * Cette liste est accessible avec URI /list
    *
    */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getListBeneficiaireFromDB(){
        try {
            return Response.status(200).entity(br.findAll()).build();
        } catch (Exception e) {
            return Response.status(404).entity("Erreur ! Veuillez revoir l' URL et reessayez").build();
        }      
    }
    /*
    *
    *La methode  ci-dessous cree une instance de BeneficiaireRepository (br) , 
    *applique sur cette derniere la methode findById de la classe
    *pour retourner le beneficiaire   persisté avec l'indentifiant specifié dans 
    *l'URI d'acces qui est /list/id ou id est l'identifiant.
    *
    */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getBeneficiaireByIdFromDB(@PathParam("id") int id) throws SQLException{
        Beneficiaire beneficiaire = br.findById(id);
        try {
            //Verification de l'existance de l'id dans la base de donnee
            
            if(beneficiaire == null){
            return Response.status(500).entity("Aucun beneficiaire ne correspond a l'indifiant indique").build();
        } else
           return Response.status(200).entity(beneficiaire).build();
        } catch (Exception e) {
             return Response.status(401).entity("Veuillez verifier si c'est bien un URL correcte qque vous entrez").build();
        }  
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBene(Beneficiaire beneficiaire) throws SQLException {
 
        if(br.create(beneficiaire)){
            return Response.status(202).entity("l'entite est cree est succes").build();
        }else{
          return Response.status(404).entity("Erreur de creation de l'entite").build();  
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBene(Beneficiaire beneficiaire) throws SQLException {
        
        
        br.update(beneficiaire);
        return Response.status(202).entity("l'entite est bien mise a jour ou cree s'il n'existait pas").build();
       
    }
    
    @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          Beneficiaire b= br.findById(id);
        try {
            br.delete(b);
            return Response.status(202).entity("l'entite est supprime est succes").build();
        } catch (Exception e) {
             return Response.status(404).entity("Erreur de suppression de l'entite").build();
        }
     
    }
}
