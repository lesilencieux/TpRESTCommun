/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Projet;
import com.bootcamp.jpa.repositories.ProjetRepository;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/Projet")
public class ProjetRestController {

    ProjetRepository pr = new ProjetRepository("punit-mysql");
    Projet p;
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getList(){
        Projet projet = new Projet();
        return Response.status(200).entity(projet).build();
    }
    
    @GET
    @Path("/listsdebase")
    @Produces("application/json")
    public Response getListBaailleurFromDB(){
        
        return Response.status(200).entity(pr.findAll()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getBaailleurByIdFromDB(@PathParam("id") int id) throws SQLException{
        pr = new ProjetRepository("punit-mysql");
        Projet projet = pr.findById(id);
        if(projet.getId()!=id){
            return Response.status(200).entity(projet).build();
        }
       if(projet != null) {
           return Response.status(200).entity(projet).build();
        }else return Response.status(404).build();
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Projet projet) throws SQLException {
        pr.create(projet);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Projet projet) throws SQLException {
       pr.update(projet);
    }
    
     @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          p= pr.findById(id);
        try {
            pr.delete(p);
            return Response.status(202).entity("l'entite est supprime est succes").build();
        } catch (Exception e) {
             return Response.status(404).entity("Erreur de suppression de l'entite").build();
        }
     
    }
    
   @GET
   @Path("/pers/param/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
       String[] fieldArray = fields.split(",");
       ProjetRepository projetRepository = new ProjetRepository("punit-mysql");
       Projet projet = projetRepository.findById(id);
       Map<String, Object> responseMap = new HashMap<>();
       PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Projet
                                                                .class).getPropertyDescriptors();

       for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

           Method method = propertyDescriptor.getReadMethod();
           if (check(fieldArray, propertyDescriptor.getName())) {
               responseMap.put(propertyDescriptor.getName(), method.invoke(projet));
           }
             System.out.println(method.invoke(projet));
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
   
    @GET
    @Path("/list/triees")
    @Produces("application/json")
   public Response triProjet(@QueryParam("sort") String attribut){
       List<Projet>  bene = pr.findAll();
       Collections.sort(bene, new Comparator<Projet>() {
           @Override
           public int compare(Projet b, Projet b1) {
               
                         
               int result =0;
                             
               String[] attributArray = attribut.split(",");
               
               PropertyDescriptor[] propertyDescriptors;
               try {
  
                   propertyDescriptors = Introspector.getBeanInfo(Projet.class).getPropertyDescriptors();
                   
                      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                        Method method = propertyDescriptor.getReadMethod();
                        if (check(attributArray, propertyDescriptor.getName())) {

                            
                                switch (propertyDescriptor.getName()){
                                    case "nom":
                                        result = b.getNom().compareToIgnoreCase(b1.getNom());
                                        break;
                                       
                                }
                        }
                    }
               } catch (IntrospectionException ex) {
                   Logger.getLogger(ProjetRestController.class.getName()).log(Level.SEVERE, null, ex);
               }
                  return result;
        }
       });
       
       return Response.status(200).entity(bene).build();
   }
   
        @GET
        @Path("/list/paginer")
        @Produces("application/json")
        public Response findPagingProjet(@QueryParam("offset") 
        @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
            return Response.status(200).entity(pr.findPerPager(offset, limit)).build();
        }
        
       @GET
        @Path("/list/recherche")
        @Produces("application/json")
        public Response SearcheProjet(@QueryParam("attribut") String attribut,@QueryParam("value") String value) throws IntrospectionException {
            
           List<Projet>    b= new ArrayList<Projet>();
            
            String[] attributArray = attribut.split(",");
             PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Projet
                                                                .class).getPropertyDescriptors();

                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                    Method method = propertyDescriptor.getReadMethod();
                    if (check(attributArray, propertyDescriptor.getName())) {
                     b=  pr.findSearche(attribut, value);
                      
                    }
                }
            return Response.status(200).entity(b).build(); 
        }     
}
        
