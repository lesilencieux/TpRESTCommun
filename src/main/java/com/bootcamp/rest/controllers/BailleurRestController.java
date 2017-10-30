/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Bailleur;
import com.bootcamp.jpa.enums.TypeBailleur;
import com.bootcamp.jpa.repositories.*;
import java.beans.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author root
 */
@Path("/bailleur")
public class BailleurRestController {

    //instanciations
    BailleurRepository br = new BailleurRepository("punit-mysql");
    Bailleur bailleur = new Bailleur();
    List<Bailleur> bailleurs = new ArrayList<>();

    @GET //Signifie que c est une methode de lecture
    @Path("/test")//URI pour retourner l instance non persistee
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getOneBailleur() {
        try {
            //Creation d un bailleur
            bailleur.setId(111);
            bailleur.setNom("Bailleur Test");
            bailleur.setTypeBailleur(TypeBailleur.PRIVE);
        } catch (Exception e) {
            //Retourne l erreur de message
            return Response.status(200).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne l instance cree
        return Response.status(200).entity(bailleur).build();
    }

    @GET//Signifie que c est une methode de lecture
    @Path("/list")//URI pour retourner toutes les  instances de bailleurs persistees
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getListBaailleurFromDB() {
        try {
            bailleurs = br.findAll();//Recuperation des instances de bailleur depuis la base de donnee
        } catch (Exception e) {
            //Retourne l erreur de recuperation
            return Response.status(404).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne la liste recuperee de la base de donnee
        return Response.status(200).entity(bailleurs).build();
    }

    @GET//Signifie que c est une methode de lecture
    @Path("/{id}")//Retourne le bailleur dont l identifiant est id
    @Produces("application/json")
    public Response getBaailleurByIdFromDB(@PathParam("id") int id) {
        try {
            bailleur = br.findById(id);//Recuperation du bailleur d id  depuis la base
            if (bailleur != null) {
                //Retourne l erreur signalant que il n y a pas de bailleur ayant ce is
                return Response.status(404).entity("Aucun bailleur pour cet identifiant").build();
            }
        } catch (SQLException ex) {
            //Retourne l erreur de resource non trouvee 
            Logger.getLogger(BailleurRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).entity("Veuillez verifier votre URL").build();
        }
        //Retourne le bailleur demande
        return Response.status(200).entity(bailleur).build();
    }

    @POST//Signifie que c est une methode d ecriture
    @Path("/create")//URI pour creer
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la creation
    public Response create(Bailleur bailleur) {
        try {
            br.create(bailleur);
        } catch (SQLException ex) {
            Logger.getLogger(BailleurRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(200).entity("Probleme de creation").build();
        }
        //Retourne le message de creation
        return Response.status(200).entity("Enregistre avec succes").build();
    }

    @PUT//Signifie que c est une methode d ecriture
    @Path("/update")//URI  pour la mise a jour ou de creation si ce  existait pas
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la mise a jour
    public Response update(Bailleur bailleur) {

        try {
            br.update(bailleur);
        } catch (Exception e) {
            return Response.status(400).entity("problemede mise a jour ").build();
        }
        return Response.status(200).entity("Mise a jour avec succes").build();
    }

    @GET//Signifie qu il s agit d une methode de lecture 
    @Path("/attribut/essentiels/{id}")//Avec cette URI on donnera seulement les attributs que l on souhaite afficher pour un bailleur donne
    @Produces(MediaType.APPLICATION_JSON)//Production de JSON
    public Response getByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        HashMap<String, Object> responseMap = magik(id, fields);
        return Response.status(200).entity(responseMap).build();
    }

    private boolean check(String[] fields, String field) {

        for (String field1 : fields) {
            if (field.equals(field1)) {
                return true;
            }
        }
        return false;
    }

    @GET//Signifie que c est une methode de lecture
    @Path("/list/tries")//URI pour avoir la liste des Bailleurs tries suivant un attribut donne
    @Produces("application/json")//Permet de dire a la methode quelle va produire du JSON
    public Response triBailleur(@QueryParam("sort") String attribut) {
        List<Bailleur> bail = br.findAll();//Recuperation de tous les bailleurs
        //Comparaison de la liste retournee
        Collections.sort(bail, new Comparator<Bailleur>() {
            @Override
            public int compare(Bailleur b, Bailleur b1) {
                int result = 0;
                List<PropertyDescriptor> propertyDescriptors;
                try {
                    //La methode returnAskedPropertiesIfExist verifie si l attribut specifie 
                    //par l utilisateur fait partir des proprietse de la classe
                    //Si oui la liste d attribut 
                    propertyDescriptors = br.returnAskedPropertiesIfExist(Bailleur.class, attribut);
                    if (!propertyDescriptors.isEmpty()) { //Verifions si la liste retournee n est vide
                        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                            //Suivant le nom de l attribut on compare pour le tri
                            switch (propertyDescriptor.getName()) {
                                case "nom":
                                    result = b.getNom().compareToIgnoreCase(b1.getNom());
                                    break;
                                case "TypeBailleur":
                                    result = b.getTypeBailleur().compareTo(b1.getTypeBailleur());
                                    break;
                                default:
                                    result = 0;
                                    break;
                            }
                        }
                    }
                } catch (IntrospectionException ex) {
                    Logger.getLogger(BailleurRestController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BailleurRestController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return result;
            }
        });

        return Response.status(200).entity(bail).build();
    }

    @GET
    @Path("/list/paginer")
    @Produces("application/json")
    public Response findPagingBailleur(@QueryParam("offset")
            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
        return Response.status(200).entity(br.findPerPager(offset, limit)).build();
    }

    @GET
    @Path("/list/recherche")
    @Produces("application/json")
    public Response SearcheBailleur(@QueryParam("attribut") String attribut, @QueryParam("value") String value) throws IntrospectionException, SQLException {

        List<PropertyDescriptor> propertyDescriptors = br.returnAskedPropertiesIfExist(Bailleur.class, attribut);
        if (!propertyDescriptors.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                bailleurs = br.findSearche(attribut, value);

            }
        }

        return Response.status(200).entity(bailleurs).build();
    }

    public HashMap magik(int id, String flds) throws IntrospectionException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        HashMap<String, Object> responseMap = new HashMap<>();
        bailleur = br.findById(id);
        List<PropertyDescriptor> propertyDescriptors = br.returnAskedPropertiesIfExist(Bailleur.class, flds);

        if (!(propertyDescriptors.isEmpty())) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method method = propertyDescriptor.getReadMethod();
                responseMap.put(propertyDescriptor.getName(), method.invoke(bailleur));
            }
        }
        return responseMap;
    }

}
