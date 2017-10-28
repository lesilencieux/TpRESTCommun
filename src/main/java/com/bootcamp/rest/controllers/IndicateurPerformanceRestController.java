/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.repositories.*;
import com.bootcamp.jpa.entities.*;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;

/**
 *
 * @author Iso-Doss
 */
@Path("/indicateur_performances")
public class IndicateurPerformanceRestController {

    private final IndicateurPerformanceRepository mysql = new IndicateurPerformanceRepository("tpJpa");

    /**
     * Méthode qui récupère la liste de tous les indicateurs de performance
     * Ressources : localhost:8080/rest/indicateur_performances/ avec la méthode
     * GET
     *
     * @return
     * @throws SQLException
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException {
        List<IndicateurPerformance> indicateurPerformances = mysql.findAll();
        return Response.status(200).entity(indicateurPerformances).build();
    }

    /**
     * Méthode qui récupère l'indicateurs de performance grace a l'id Ressources
     * : localhost:8080/rest/indicateur_performances/{id} avec la méthode GET
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListById(@PathParam("id") int id) throws SQLException {
        IndicateurPerformance indicateurPerformance = mysql.findById(id);

        if (indicateurPerformance == null) {
            return Response.status(404).entity(indicateurPerformance).build();
        } else {
            return Response.status(200).entity(indicateurPerformance).build();
        }
    }

    /**
     * Méthode qui récupère la liste de tous les indicateurs de performance
     * grace a un parametre, un des champs de l'entité IndicateurPerformance
     * Ressources : localhost:8080/rest/indicateur_performances/{param} avec la
     * méthode GET
     *
     * @param param
     * @return
     * @throws SQLException
     */
    @GET
    @Path("/parametre/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListByParam(@PathParam("param") String param) throws SQLException {
        List<IndicateurPerformance> indicateurPerformances = (List<IndicateurPerformance>) mysql.findByProperty("nom", param);

        if (indicateurPerformances == null) {
            return Response.status(404).entity(indicateurPerformances).build();
        } else {
            return Response.status(200).entity(indicateurPerformances).build();
        }
    }

    /**
     * Méthode qui supprime un indicateurs de performance Ressources :
     * localhost:8080/rest/indicateur_performances/ avec la méthode DELETE
     *
     * @param indicateurPerformance
     * @return
     * @throws SQLException
     */
    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeleteListByParam(IndicateurPerformance indicateurPerformance) throws SQLException {
        mysql.delete(indicateurPerformance);
        return Response.status(200).entity(indicateurPerformance).build();
    }

    /**
     * Méthode qui crée un indicateurs de performance Ressources :
     * localhost:8080/rest/indicateur_performances/ avec la méthode POST
     *
     * @param indicateurPerformance
     * @throws SQLException
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(IndicateurPerformance indicateurPerformance) throws SQLException {
        mysql.create(indicateurPerformance);
    }

    /**
     * Méthode qui modifie un indicateurs de performance Ressources :
     * localhost:8080/rest/indicateur_performances/ avec la méthode PUT
     *
     * @param indicateurPerformance
     * @throws SQLException
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(IndicateurPerformance indicateurPerformance) throws SQLException {
        mysql.create(indicateurPerformance);
    }

    /**
     * Méthode qui récupère un indicateur de performance grace a son id Et
     * retourne uniquement les champs spécifié par l'utilisateur dans le fields
     * Récupérer uniquement les informations dont il abesoin(passer par
     * l'utilisateur).
     * localhost:8080/rest/indicateur_performances/reponses_partielles/{id}?fields=nom
     * avec la méthode GET
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
    @GET
    @Path("/reponses_partielles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReponsePartielleByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String[] fieldArray = fields.split(",");
        IndicateurPerformance indicateurPerformance = mysql.findById(id);
        Map<String, Object> responseMap = new HashMap<>();
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Bailleur.class).getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            Method method = propertyDescriptor.getReadMethod();
            if (check(fieldArray, propertyDescriptor.getName())) {
                responseMap.put(propertyDescriptor.getName(), method.invoke(indicateurPerformance));
            }
            System.out.println(method.invoke(indicateurPerformance));
        }
        return Response.status(200).entity(responseMap).build();
    }

    /**
     * Methode privé qui permet de verifier si un string fait partir des
     * éléments d'un tableau
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
     * Méthode qui recupére les indicateurs de performances et les pagine en
     * fonction Des parematres fournies par l'utilisateur et des valeurs par
     * défaut sinon.
     *
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Path("/paginer")
    @Produces("application/json")
    public Response findAndPaginer(@QueryParam("offset")
            @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
        return Response.status(200).entity(mysql.findPerPager(offset, limit)).build();
    }

    /**
     * Méthode qui récupère des indicateurs de performance grace a des filtre
     * Limite le nombre de ressources requêtées, en spécifiant des attributs et
     * leurs valeurs correspondantes attendues
     * localhost:8080/rest/indicateur_performances/filtres?nom=iso,doss&prenom=roro
     * avec la méthode GET
     *
     * @param champs
     * @param valeur
     * @return
     * @throws IntrospectionException
     * @throws SQLException
     */
    @GET
    @Path("/filtres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByFilters(@QueryParam("champs") String champs, @QueryParam("valeur") String valeur) throws IntrospectionException, SQLException {
        List<IndicateurPerformance> indicateurPerformances = new ArrayList<>();
        List<PropertyDescriptor> propertyDescriptors = mysql.returnAskedPropertiesIfExist(IndicateurPerformance.class, champs);
        if (!propertyDescriptors.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                indicateurPerformances = mysql.findSearche(champs, valeur);
            }
        }
        return Response.status(200).entity(indicateurPerformances).build();
    }
}
