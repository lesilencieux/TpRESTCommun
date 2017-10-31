/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.*;
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
@Path("/projet")
public class ProjetRestController {

    //instanciations
    ProjetRepository pr = new ProjetRepository("punit-mysql");
    Projet projet = new Projet();
    List<Projet> projets = new ArrayList<>();

    /**
     * Méthode qui .................
     *
     * @return
     */
    @GET //Signifie que c est une methode de lecture
    @Path("/test")//URI pour retourner l instance non persistee
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getOneProjet() {
        try {
            //Creation d un projet
            projet.setId(111);
            projet.setNom("Projet Test");
        } catch (Exception e) {
            //Retourne l erreur de message
            return Response.status(200).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne l instance cree
        return Response.status(200).entity(projet).build();
    }

    /**
     * Méthode qui récupre la liste de tous les Projets
     *
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/list")//URI pour retourner toutes les  instances de projets persistees
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getListProjetFromDB() {
        try {
            projets = pr.findAll();//Recuperation des instances de projet depuis la base de donnee
        } catch (Exception e) {
            //Retourne l erreur de recuperation
            return Response.status(404).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne la liste recuperee de la base de donnee
        return Response.status(200).entity(projets).build();
    }

    /**
     * Méthode qui récupre un Projet grace a son id
     *
     * @param id
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/{id}")//Retourne le projet dont l identifiant est id
    @Produces("application/json")
    public Response getProjetByIdFromDB(@PathParam("id") int id) {
        try {
            projet = pr.findById(id);//Recuperation du projet d id  depuis la base
            if (projet != null) {
                //Retourne l erreur signalant que il n y a pas de projet ayant ce is
                return Response.status(404).entity("Aucun projet pour cet identifiant").build();
            }
        } catch (SQLException ex) {
            //Retourne l erreur de resource non trouvee 
            Logger.getLogger(ProjetRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).entity("Veuillez verifier votre URL").build();
        }
        //Retourne le projet demande
        return Response.status(200).entity(projet).build();
    }

    /**
     * Méthode qui permet de crée un Projet
     *
     * @param projet
     * @return
     */
    @POST//Signifie que c est une methode d ecriture
    @Path("/create")//URI pour creer
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la creation
    public Response create(Projet projet) {
        try {
            pr.create(projet);
        } catch (SQLException ex) {
            Logger.getLogger(ProjetRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(200).entity("Probleme de creation").build();
        }
        //Retourne le message de creation
        return Response.status(200).entity("Enregistre avec succes").build();
    }

    /**
     * Méthode qui permet de mettre à jour un Projet
     *
     * @param projet
     * @return
     */
    @PUT//Signifie que c est une methode d ecriture
    @Path("/update")//URI  pour la mise a jour ou de creation si ce  existait pas
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la mise a jour
    public Response update(Projet projet) {

        try {
            pr.update(projet);
        } catch (Exception e) {
            return Response.status(400).entity("problemede mise a jour ").build();
        }
        return Response.status(200).entity("Mise a jour avec succes").build();
    }

    /**
     * Méthode qui récupre un Projet grace a son id et qui retourne une reponse
     * partielle en fonction des parametre de l'utilsateurs
     *
     * @param id
     * @param fields
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    @GET//Signifie qu il s agit d une methode de lecture 
    @Path("/attribut/essentiels/{id}")//Avec cette URI on donnera seulement les attributs que l on souhaite afficher pour un projet donne
    @Produces(MediaType.APPLICATION_JSON)//Production de JSON
    public Response getByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        HashMap<String, Object> responseMap = magik(id, fields);
        return Response.status(200).entity(responseMap).build();
    }

    /**
     *
     * @param fields
     * @param field
     * @return
     */
    private boolean check(String[] fields, String field) {

        for (String field1 : fields) {
            if (field.equals(field1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui récupre la liste de tous les Projets en applique un filtre
     * passé en parametre au résultats
     *
     * @param attribut
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/list/tries")//URI pour avoir la liste des Projets tries suivant un attribut donne
    @Produces("application/json")//Permet de dire a la methode quelle va produire du JSON
    public Response triProjet(@QueryParam("sort") String attribut) {
        List<Projet> bail = pr.findAll();//Recuperation de tous les projets
        //Comparaison de la liste retournee
        Collections.sort(bail, new Comparator<Projet>() {
            @Override
            public int compare(Projet b, Projet b1) {
                int result = 0;
                List<PropertyDescriptor> propertyDescriptors;
                try {
                    //La methode returnAskedPropertiesIfExist verifie si l attribut specifie 
                    //par l utilisateur fait partir des proprietse de la classe
                    //Si oui la liste d attribut 
                    propertyDescriptors = pr.returnAskedPropertiesIfExist(Projet.class, attribut);
                    if (!propertyDescriptors.isEmpty()) { //Verifions si la liste retournee n est vide
                        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                            //Suivant le nom de l attribut on compare pour le tri
                            switch (propertyDescriptor.getName()) {
                                case "nom":
                                    result = b.getNom().compareToIgnoreCase(b1.getNom());
                                    break;
                                default:
                                    result = 0;
                                    break;
                            }
                        }
                    }
                } catch (IntrospectionException ex) {
                    Logger.getLogger(ProjetRestController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ProjetRestController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return result;
            }
        });

        return Response.status(200).entity(bail).build();
    }

    /**
     * Méthode qui récupre la liste de tous les Projets et applique une
     * pagination au resultats retourné en fonction des parametres de filtres
     *
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Path("/list/paginer")
    @Produces("application/json")
    public Response findPagingProjet(@QueryParam("offset")
            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
        return Response.status(200).entity(pr.findPerPager(offset, limit)).build();
    }

    /**
     * Méthode qui effectue une recherche a partir d'un ou plusieur champs de
     * Projet
     *
     * @param attribut
     * @param value
     * @return
     * @throws IntrospectionException
     * @throws SQLException
     */
    @GET
    @Path("/list/recherche")
    @Produces("application/json")
    public Response SearcheProjet(@QueryParam("attribut") String attribut, @QueryParam("value") String value) throws IntrospectionException, SQLException {

        List<PropertyDescriptor> propertyDescriptors = pr.returnAskedPropertiesIfExist(Projet.class, attribut);
        if (!propertyDescriptors.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                projets = pr.findSearche(attribut, value);

            }
        }

        return Response.status(200).entity(projets).build();
    }

    /**
     *
     * @param id
     * @param flds
     * @return
     * @throws IntrospectionException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public HashMap magik(int id, String flds) throws IntrospectionException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        HashMap<String, Object> responseMap = new HashMap<>();
        projet = pr.findById(id);
        List<PropertyDescriptor> propertyDescriptors = pr.returnAskedPropertiesIfExist(Projet.class, flds);

        if (!(propertyDescriptors.isEmpty())) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method method = propertyDescriptor.getReadMethod();
                responseMap.put(propertyDescriptor.getName(), method.invoke(projet));
            }
        }
        return responseMap;
    }

}
