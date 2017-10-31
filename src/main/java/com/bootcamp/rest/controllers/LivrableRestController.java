/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.repositories.*;
import com.bootcamp.jpa.entities.*;
import java.beans.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

/**
 *
 * @author Iso-Doss
 */
@Path("/livrables")
public class LivrableRestController {

    private final LivrableRepository mysql = new LivrableRepository("punit-mysql");
//
//    /**
//     * Méthode qui récupère la liste de tous les livrables
//     * Ressources : localhost:8080/rest/livrables/ avec la méthode
//     * GET
//     *
//     * @return
//     * @throws SQLException
//     */
//    @GET
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getList() throws SQLException {
//        List<Livrable> Livrables = mysql.findAll();
//        return Response.status(200).entity(Livrables).build();
//    }
//
//    /**
//     * Méthode qui récupère le livrables grace a l'id Ressources
//     * localhost:8080/rest/livrables/{id} avec la méthode GET
//     *
//     * @param id
//     * @return
//     * @throws SQLException
//     */
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getListById(@PathParam("id") int id) throws SQLException {
//        Livrable indicateurQualitatif = mysql.findById(id);
//
//        if (indicateurQualitatif == null) {
//            return Response.status(404).entity(indicateurQualitatif).build();
//        } else {
//            return Response.status(200).entity(indicateurQualitatif).build();
//        }
//    }
//
//    /**
//     * Méthode qui récupère la liste de tous les livrables grace a
//     * un parametre, un des champs de l'entité Livrable Ressources
//     * : localhost:8080/rest/livrables/{param} avec la méthode GET
//     *
//     * @param param
//     * @return
//     * @throws SQLException
//     */
//    @GET
//    @Path("/parametre/{param}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getListByParam(@PathParam("param") String param) throws SQLException {
//        List<Livrable> Livrables = (List<Livrable>) mysql.findByProperty("nom", param);
//
//        if (Livrables == null) {
//            return Response.status(404).entity(Livrables).build();
//        } else {
//            return Response.status(200).entity(Livrables).build();
//        }
//    }
//
//    /**
//     * Méthode qui supprime un livrable Ressources :
//     * localhost:8080/rest/livrables/ avec la méthode DELETE
//     *
//     * @param indicateurQualitatif
//     * @return
//     * @throws SQLException
//     */
//    @DELETE
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getDeleteListByParam(Livrable indicateurQualitatif) throws SQLException {
//        mysql.delete(indicateurQualitatif);
//        return Response.status(200).entity(indicateurQualitatif).build();
//    }
//
//    /**
//     * Méthode qui crée un livrable Ressources :
//     * localhost:8080/rest/livrables/ avec la méthode POST
//     *
//     * @param indicateurQualitatif
//     * @throws SQLException
//     */
//    @POST
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void create(Livrable indicateurQualitatif) throws SQLException {
//        mysql.create(indicateurQualitatif);
//    }
//
//    /**
//     * Méthode qui modifie un livrable Ressources :
//     * localhost:8080/rest/livrables/ avec la méthode PUT
//     *
//     * @param indicateurQualitatif
//     * @throws SQLException
//     */
//    @PUT
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void update(Livrable indicateurQualitatif) throws SQLException {
//        mysql.create(indicateurQualitatif);
//    }
//
//    /**
//     * Méthode qui récupère un livrable grace a son id Et retourne
//     * uniquement les champs spécifié par l'utilisateur dans le fields Récupérer
//     * uniquement les informations dont il abesoin(passer par l'utilisateur).
//     * localhost:8080/rest/livrables/reponses_partielles/{id}?fields=nom
//     * avec la méthode GET
//     *
//     * @param id
//     * @param fields
//     * @return
//     * @throws SQLException
//     * @throws IllegalArgumentException
//     * @throws IllegalAccessException
//     * @throws IntrospectionException
//     * @throws InvocationTargetException
//     */
//    @GET
//    @Path("/reponses_partielles/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getReponsePartielleByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
//        String[] fieldArray = fields.split(",");
//        Livrable indicateurQualitatif = mysql.findById(id);
//        Map<String, Object> responseMap = new HashMap<>();
//        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Bailleur.class).getPropertyDescriptors();
//
//        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//
//            Method method = propertyDescriptor.getReadMethod();
//            if (check(fieldArray, propertyDescriptor.getName())) {
//                responseMap.put(propertyDescriptor.getName(), method.invoke(indicateurQualitatif));
//            }
//            System.out.println(method.invoke(indicateurQualitatif));
//        }
//        return Response.status(200).entity(responseMap).build();
//    }
//
//    /**
//     * Methode privé qui permet de verifier si un string fait partir des
//     * éléments d'un tableau
//     *
//     * @param fields
//     * @param field
//     * @return
//     */
//    private boolean check(String[] fields, String field) {
//
//        for (String field1 : fields) {
//            if (field.equals(field1)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Méthode qui recupére les livrabless et les pagine en
//     * fonction Des parematres fournies par l'utilisateur et des valeurs par
//     * défaut sinon.
//     *
//     * @param offset
//     * @param limit
//     * @return
//     */
//    @GET
//    @Path("/paginer")
//    @Produces("application/json")
//    public Response findAndPaginer(@QueryParam("offset")
//            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
//        return Response.status(200).entity(mysql.findPerPager(offset, limit)).build();
//    }
//
//    /**
//     * Méthode qui récupère des livrables grace a des filtre
//     * Limite le nombre de ressources requêtées, en spécifiant des attributs et
//     * leurs valeurs correspondantes attendues
//     * localhost:8080/rest/livrables/filtres?nom=iso,doss&prenom=roro
//     * avec la méthode GET
//     *
//     * @param champs
//     * @param valeur
//     * @return
//     * @throws IntrospectionException
//     * @throws SQLException
//     */
//    @GET
//    @Path("/filtres")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getByFilters(@QueryParam("champs") String champs, @QueryParam("valeur") String valeur) throws IntrospectionException, SQLException {
//        List<Livrable> Livrables = new ArrayList<>();
//        List<PropertyDescriptor> propertyDescriptors = mysql.returnAskedPropertiesIfExist(Livrable.class, champs);
//        if (!propertyDescriptors.isEmpty()) {
//            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//                Livrables = mysql.findSearche(champs, valeur);
//            }
//        }
//        return Response.status(200).entity(Livrables).build();
//    }

}
