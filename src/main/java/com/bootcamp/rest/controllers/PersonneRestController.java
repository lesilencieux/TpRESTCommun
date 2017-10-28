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
@Path("/personne")
public class PersonneRestController {

    private PersonneRepository derby = new PersonneRepository("tpJpa");

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException {
        List<Personne> personnes = derby.findAll();
        return Response.status(200).entity(personnes).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListById(@PathParam("id") int id) throws SQLException {
        Personne personne = derby.findById("id", id);

        if (personne == null) {
            return Response.status(404).entity(personne).build();
        } else {
            return Response.status(200).entity(personne).build();
        }
    }

    @GET
    @Path("/list/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListByParam(@PathParam("param") String param) throws SQLException {
        List<Personne> personnes = (List<Personne>) derby.findByProperty("nom", param);

        if (personnes == null) {
            return Response.status(404).entity(personnes).build();
        } else {
            return Response.status(200).entity(personnes).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeleteListByParam(@PathParam("id") int id) throws SQLException {
        Personne personne = derby.findById("id", id);
        derby.delete(personne);
        return Response.status(200).entity(personne).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Personne personne) throws SQLException {
        derby.create(personne);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Personne personne) throws SQLException {
        derby.create(personne);
    }
}
