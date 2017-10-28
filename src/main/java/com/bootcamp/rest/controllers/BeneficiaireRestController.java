/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Beneficiaire;
import com.bootcamp.jpa.repositories.BeneficiaireRepository;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @GET//Signifie que c est une methode de lecture
    @Path("/list")//URI pour retourner toutes les  instances de beneficiaires persistees
    @Produces("application/json")//Signifie que la methode produit du JSON
    public Response getListBaailleurFromDB() {
        try {
            beneficiaires = br.findAll();//Recuperation des instances de beneficiaire depuis la base de donnee
        } catch (Exception e) {
            //Retourne l erreur de recuperation
            return Response.status(404).entity("Erreur de de creation de la nouvelle instance ").build();
        }
        //Retourne la liste recuperee de la base de donnee
        return Response.status(200).entity(beneficiaires).build();
    }

    @GET//Signifie que c est une methode de lecture
    @Path("/{id}")//Retourne le beneficiaire dont l identifiant est id
    @Produces("application/json")
    public Response getBaailleurByIdFromDB(@PathParam("id") int id) {
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

    @GET//Signifie qu il s agit d une methode de lecture 
    @Path("/attribut/essentiels/{id}")//Avec cette URI on donnera seulement les attributs que l on souhaite afficher pour un beneficiaire donne
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

    @GET
    @Path("/list/paginer")
    @Produces("application/json")
    public Response findPagingBeneficiaire(@QueryParam("offset")
            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
        return Response.status(200).entity(br.findPerPager(offset, limit)).build();
    }

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
