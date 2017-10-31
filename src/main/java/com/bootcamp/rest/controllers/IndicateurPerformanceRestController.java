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
@Path("/indicateur_performances")
public class IndicateurPerformanceRestController extends BaseRestController<IndicateurPerformance, IndicateurPerformanceRepository> {

    /**
     * Constructeur
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public IndicateurPerformanceRestController() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        super(IndicateurPerformance.class, IndicateurPerformanceRepository.class);
    }

}
