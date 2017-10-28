/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.repositories.*;
import com.bootcamp.jpa.entities.*;
import java.sql.SQLException;
import javax.ws.rs.core.*;
import java.util.List;
import javax.ws.rs.*;

/**
 *
 * @author Iso-Doss
 */
@Path("/indicateurquantitatif")
public class IndicateurQuantitatifRestController {

    private IndicateurQuantitatifRepository derby = new IndicateurQuantitatifRepository("tpJpa");

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException {
        List<IndicateurQuantitatif> indicateurQuantitatifs = derby.findAll();
        return Response.status(200).entity(indicateurQuantitatifs).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListById(@PathParam("id") int id) throws SQLException {
        IndicateurQuantitatif indicateurQuantitatif = derby.findById("id", id);

        if (indicateurQuantitatif == null) {
            return Response.status(404).entity(indicateurQuantitatif).build();
        } else {
            return Response.status(200).entity(indicateurQuantitatif).build();
        }
    }

    @GET
    @Path("/list/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListByParam(@PathParam("param") String param) throws SQLException {
        List<IndicateurQuantitatif> indicateurQuantitatifs = (List<IndicateurQuantitatif>) derby.findByProperty("nom", param);

        if (indicateurQuantitatifs == null) {
            return Response.status(404).entity(indicateurQuantitatifs).build();
        } else {
            return Response.status(200).entity(indicateurQuantitatifs).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeleteListByParam(@PathParam("id") int id) throws SQLException {
        IndicateurQuantitatif indicateurQuantitatif = derby.findById("id", id);
        derby.delete(indicateurQuantitatif);
        return Response.status(200).entity(indicateurQuantitatif).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(IndicateurQuantitatif indicateurQuantitatif) throws SQLException {
        derby.create(indicateurQuantitatif);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(IndicateurQuantitatif indicateurQuantitatif) throws SQLException {
        derby.create(indicateurQuantitatif);
    }
}
