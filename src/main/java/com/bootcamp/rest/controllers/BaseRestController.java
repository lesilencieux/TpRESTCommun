/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

public class BaseRestController<C, CR> {

    private Class className;
    private Class classRepositoryName;
    private C c;
    private CR cr;

    /**
     * Contructeur paramétré
     *
     * @param className
     * @param classRepositoryName
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public BaseRestController(Class className, Class classRepositoryName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.className = className;
        this.classRepositoryName = classRepositoryName;
        
        /*Class<C> c = (Class<C>) Class.forName(className.getName());
        c.getDeclaredMethod(name, parameterTypes)*/
        c = (C) className.getClass().newInstance();
        cr = (CR) className.getClass().newInstance();
    }

    /**
     * @return the className
     */
    public Class getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(Class className) {
        this.className = className;
    }

    /**
     * @return the classRepositoryName
     */
    public Class getClassRepositoryName() {
        return classRepositoryName;
    }

    /**
     * @param classRepositoryName the classRepositoryName to set
     */
    public void setClassRepositoryName(Class classRepositoryName) {
        this.classRepositoryName = classRepositoryName;
    }

    /**
     * @return the c
     */
    public C getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(C c) {
        this.c = c;
    }

    /**
     * @return the cr
     */
    public CR getCr() {
        return cr;
    }

    /**
     * @param cr the cr to set
     */
    public void setCr(CR cr) {
        this.cr = cr;
    }

    /**
     * Méthode qui récupère la liste de tous les indicateurs qualitatif
     * Ressources : localhost:8080/rest/indicateur_qualitatifs/ avec la méthode
     * GET
     *
     * @return
     * @throws SQLException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() throws SQLException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = getCr().getClass().getMethod("findAll");
        List<C> cList = (List<C>) method.invoke(getCr());
        return Response.status(200).entity(cList).build();
    }

//    /**
//     * Méthode qui récupère l'indicateurs qualitatif grace a l'id Ressources
//     * localhost:8080/rest/indicateur_qualitatifs/{id} avec la méthode GET
//     *
//     * @param id
//     * @return
//     * @throws SQLException
//     */
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getListById(@PathParam("id") int id) throws SQLException, NoSuchFieldException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        Method method = getCr().getClass().getMethod(name, parameterTypes)
//        C c = (C) method.invoke(getC());
//
//        if (indicateurQualitatif == null) {
//            return Response.status(404).entity(indicateurQualitatif).build();
//        } else {
//            return Response.status(200).entity(indicateurQualitatif).build();
//        }
//    }

}
