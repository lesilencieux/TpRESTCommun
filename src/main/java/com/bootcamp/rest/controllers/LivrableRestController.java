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
@Path("/Livrable")
public class LivrableRestController {

    private LivrableRepository derby = new LivrableRepository("tpJpa");

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException {
        List<Livrable> Livrables = derby.findAll();
        return Response.status(200).entity(Livrables).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListById(@PathParam("id") int id) throws SQLException {
        Livrable Livrable = derby.findById("id", id);

        if (Livrable == null) {
            return Response.status(404).entity(Livrable).build();
        } else {
            return Response.status(200).entity(Livrable).build();
        }
    }

    @GET
    @Path("/list/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListByParam(@PathParam("param") String param) throws SQLException {
        List<Livrable> Livrables = (List<Livrable>) derby.findByProperty("nom", param);

        if (Livrables == null) {
            return Response.status(404).entity(Livrables).build();
        } else {
            return Response.status(200).entity(Livrables).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeleteListByParam(@PathParam("id") int id) throws SQLException {
        Livrable Livrable = derby.findById("id", id);
        derby.delete(Livrable);
        return Response.status(200).entity(Livrable).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Livrable Livrable) throws SQLException {
        derby.create(Livrable);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Livrable Livrable) throws SQLException {
        derby.create(Livrable);
    }
}
