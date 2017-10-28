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
@Path("/indicateurqualitatif")
public class IndicateurQualitatifRestController {

    private IndicateurPerformanceRepository derby = new IndicateurPerformanceRepository("tpJpa");

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException {
        List<IndicateurPerformance> indicateurPerformances = derby.findAll();
        return Response.status(200).entity(indicateurPerformances).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListById(@PathParam("id") int id) throws SQLException {
        IndicateurPerformance indicateurPerformance = derby.findById("id", id);

        if (indicateurPerformance == null) {
            return Response.status(404).entity(indicateurPerformance).build();
        } else {
            return Response.status(200).entity(indicateurPerformance).build();
        }
    }

    @GET
    @Path("/list/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListByParam(@PathParam("param") String param) throws SQLException {
        List<IndicateurPerformance> indicateurPerformances = (List<IndicateurPerformance>) derby.findByProperty("nom", param);

        if (indicateurPerformances == null) {
            return Response.status(404).entity(indicateurPerformances).build();
        } else {
            return Response.status(200).entity(indicateurPerformances).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeleteListByParam(@PathParam("id") int id) throws SQLException {
        IndicateurPerformance indicateurPerformance = derby.findById("id", id);
        derby.delete(indicateurPerformance);
        return Response.status(200).entity(indicateurPerformance).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(IndicateurPerformance indicateurPerformance) throws SQLException {
        derby.create(indicateurPerformance);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(IndicateurPerformance indicateurPerformance) throws SQLException {
        derby.create(indicateurPerformance);
    }
}
