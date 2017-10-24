/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Bailleur;
import com.bootcamp.jpa.enums.TypeBailleur;
import com.bootcamp.jpa.repositories.BailleurRepository;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
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
@Path("/bailleurs")
public class BailleurRestController {

    BailleurRepository br = new BailleurRepository("punit-mysql");
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getList(){
        Bailleur bailleur = new Bailleur();
        bailleur.setTypeBailleur(TypeBailleur.PRIVE);
        return Response.status(200).entity(bailleur).build();
    }
    
    @GET
    @Path("/listsdebase")
    @Produces("application/json")
    public Response getListBaailleurFromDB(){
        
        return Response.status(200).entity(br.findAll()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getBaailleurByIdFromDB(@PathParam("id") int id) throws SQLException{
        BailleurRepository br = new BailleurRepository("punit-mysql");
        Bailleur bailleur = br.findById(id);
        if(bailleur.getId()!=id){
            return Response.status(200).entity(bailleur).build();
        }
       if(bailleur != null) {
           return Response.status(200).entity(bailleur).build();
        }else return Response.status(404).build();
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Bailleur bailleur) throws SQLException {
        br.create(bailleur);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Bailleur bailleur) throws SQLException {
       br.update(bailleur);
    }
    
   @GET
   @Path("/pers/param/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
       String[] fieldArray = fields.split(",");
       BailleurRepository bailleurRepository = new BailleurRepository("punit-mysql");
       Bailleur bailleur = bailleurRepository.findById(id);
       Map<String, Object> responseMap = new HashMap<>();
       PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Bailleur
                                                                .class).getPropertyDescriptors();

       for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

           Method method = propertyDescriptor.getReadMethod();
           if (check(fieldArray, propertyDescriptor.getName())) {
               responseMap.put(propertyDescriptor.getName(), method.invoke(bailleur));
           }
             System.out.println(method.invoke(bailleur));
       }
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
   
   public Response triBailleur(){
       List<Bailleur>  bails = br.findAll();
       Collections.sort(bails, new Comparator<Bailleur>() {
           @Override
           public int compare(Bailleur b, Bailleur b1) {
               if(b==null){
                   if(b1==null)return -1;
               }
               if(b1==null) return 1;
                   int result = b.getNom().compareToIgnoreCase(b1.getNom());
                  return result;
        }
       });
       
       return Response.status(200).entity(bails).build();
   }
}
