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
@Path("/beneficiaire")
public class BeneficiaireRestController {

    //instanciations
    BeneficiaireRepository br = new BeneficiaireRepository("punit-mysql");
    Beneficiaire beneficiaire = new Beneficiaire();
    List<Beneficiaire> beneficiaires = new ArrayList<>();

    /**
     *
     * @return
     */
    @GET //Signifie que c est une methode de lecture
    @Path("/test")//URI pour retourner l instance non persistee
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getOneBeneficiaire() {
        try {
            //Creation d un beneficiaire
            beneficiaire.setId(111);
            beneficiaire.setNom("Beneficiaire Test");
        } catch (Exception e) {
            //Retourne l erreur de message
            return Response.status(200).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne l instance cree
        return Response.status(200).entity(beneficiaire).build();
    }

    /**
     * Méthode qui récupere le liste des Beneficiaire
     *
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/list")//URI pour retourner toutes les  instances de beneficiaires persistees
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getListBeneficiaireFromDB() {
        try {
            beneficiaires = br.findAll();//Recuperation des instances de beneficiaire depuis la base de donnee
        } catch (Exception e) {
            //Retourne l erreur de recuperation
            return Response.status(404).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne la liste recuperee de la base de donnee
        return Response.status(200).entity(beneficiaires).build();
    }

    /**
     * Méthode qui recupère un Beneficiaire grace a sin id
     *
     * @param id
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/{id}")//Retourne le beneficiaire dont l identifiant est id
    @Produces("application/json")
    public Response getBeneficiaireByIdFromDB(@PathParam("id") int id) {
        try {
            beneficiaire = br.findById(id);//Recuperation du beneficiaire d id  depuis la base
            if (beneficiaire != null) {
                //Retourne l erreur signalant que il n y a pas de beneficiaire ayant ce is
                return Response.status(404).entity("Aucun beneficiaire pour cet identifiant").build();
            }
        } catch (SQLException ex) {
            //Retourne l erreur de resource non trouvee 
            Logger.getLogger(BeneficiaireRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).entity("Veuillez verifier votre URL").build();
        }
        //Retourne le beneficiaire demande
        return Response.status(200).entity(beneficiaire).build();
    }

    /**
     * Méthode qui permet de crée un Beneficiaire
     *
     * @param beneficiaire
     * @return
     */
    @POST//Signifie que c est une methode d ecriture
    @Path("/create")//URI pour creer
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la creation
    public Response create(Beneficiaire beneficiaire) {
        try {
            br.create(beneficiaire);
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaireRestController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(200).entity("Probleme de creation").build();
        }
        //Retourne le message de creation
        return Response.status(200).entity("Enregistre avec succes").build();
    }

    /**
     * Méthode qui permet de mettre à jour un Beneficiaire
     *
     * @param beneficiaire
     * @return
     */
    @PUT//Signifie que c est une methode d ecriture
    @Path("/update")//URI  pour la mise a jour ou de creation si ce  existait pas
    @Consumes(MediaType.APPLICATION_JSON)//Signifie que l on consomme du JSON pour la mise a jour
    public Response update(Beneficiaire beneficiaire) {

        try {
            br.update(beneficiaire);
        } catch (Exception e) {
            return Response.status(400).entity("problemede mise a jour ").build();
        }
        return Response.status(200).entity("Mise a jour avec succes").build();
    }

    /**
     * Méthode qui permet de récuperé un Beneficiaire grace a son id et qui
     * reourne une réponse partielle en enfonction des champs lister par
     * l'utilisateur
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
    @Path("/attribut/essentiels/{id}")//Avec cette URI on donnera seulement les attributs que l on souhaite afficher pour un beneficiaire donne
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
     * Méthode qui récupere la liste des Beneficiaires et qui applique un trie
     * en fonction des filtre passé en parametre
     *
     * @param attribut
     * @return
     */
    @GET//Signifie que c est une methode de lecture
    @Path("/list/tries")//URI pour avoir la liste des Beneficiaires tries suivant un attribut donne
    @Produces("application/json")//Permet de dire a la methode quelle va produire du JSON
    public Response triBeneficiaire(@QueryParam("sort") String attribut) {
        List<Beneficiaire> bail = br.findAll();//Recuperation de tous les beneficiaires
        //Comparaison de la liste retournee
        Collections.sort(bail, new Comparator<Beneficiaire>() {
            @Override
            public int compare(Beneficiaire b, Beneficiaire b1) {
                int result = 0;
                List<PropertyDescriptor> propertyDescriptors;
                try {
                    //La methode returnAskedPropertiesIfExist verifie si l attribut specifie 
                    //par l utilisateur fait partir des proprietse de la classe
                    //Si oui la liste d attribut 
                    propertyDescriptors = br.returnAskedPropertiesIfExist(Beneficiaire.class, attribut);
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
                    Logger.getLogger(BeneficiaireRestController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BeneficiaireRestController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return result;
            }
        });

        return Response.status(200).entity(bail).build();
    }

    /**
     * Méthode qui recupère la liste des Beneficiaire et applique une pagination
     * sur le resulat retourné
     *
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Path("/list/paginer")
    @Produces("application/json")
    public Response findPagingBeneficiaire(@QueryParam("offset")
            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
        return Response.status(200).entity(br.findPerPager(offset, limit)).build();
    }

    /**
     * Méthode qui permet de fait des recherche sur une ou plusieurs champs de
     * Beneficiaire
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
    public Response SearcheBeneficiaire(@QueryParam("attribut") String attribut, @QueryParam("value") String value) throws IntrospectionException, SQLException {

        List<PropertyDescriptor> propertyDescriptors = br.returnAskedPropertiesIfExist(Beneficiaire.class, attribut);
        if (!propertyDescriptors.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                beneficiaires = br.findSearche(attribut, value);

            }
        }

        return Response.status(200).entity(beneficiaires).build();
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
        beneficiaire = br.findById(id);
        List<PropertyDescriptor> propertyDescriptors = br.returnAskedPropertiesIfExist(Beneficiaire.class, flds);

        if (!(propertyDescriptors.isEmpty())) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method method = propertyDescriptor.getReadMethod();
                responseMap.put(propertyDescriptor.getName(), method.invoke(beneficiaire));
            }
        }
        return responseMap;
    }

}
