/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Programme;
import com.bootcamp.jpa.repositories.ProgrammeRepository;
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
@Path("/programme")
public class ProgrammeRestController {

    ProgrammeRepository pr = new ProgrammeRepository("punit-mysql");
    Programme f;
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getList(){
        Programme programme = new Programme();
        return Response.status(200).entity(programme).build();
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
        pr = new ProgrammeRepository("punit-mysql");
        Programme programme = pr.findById(id);
        if(programme.getId()!=id){
            return Response.status(200).entity(programme).build();
        }
       if(programme != null) {
           return Response.status(200).entity(programme).build();
        }else return Response.status(404).build();
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Programme programme) throws SQLException {
        pr.create(programme);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Programme programme) throws SQLException {
       pr.update(programme);
    }
    
     @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          f= pr.findById(id);
        try {
            pr.delete(f);
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
       ProgrammeRepository programmeRepository = new ProgrammeRepository("punit-mysql");
       Programme programme = programmeRepository.findById(id);
       Map<String, Object> responseMap = new HashMap<>();
       PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Programme
                                                                .class).getPropertyDescriptors();

       for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

           Method method = propertyDescriptor.getReadMethod();
           if (check(fieldArray, propertyDescriptor.getName())) {
               responseMap.put(propertyDescriptor.getName(), method.invoke(programme));
           }
             System.out.println(method.invoke(programme));
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
   public Response triProgramme(@QueryParam("sort") String attribut){
       List<Programme>  bene = pr.findAll();
       Collections.sort(bene, new Comparator<Programme>() {
           @Override
           public int compare(Programme b, Programme b1) {
               
                         
               int result =0;
                             
               String[] attributArray = attribut.split(",");
               
               PropertyDescriptor[] propertyDescriptors;
               try {
  
                   propertyDescriptors = Introspector.getBeanInfo(Programme.class).getPropertyDescriptors();
                   
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
                   Logger.getLogger(ProgrammeRestController.class.getName()).log(Level.SEVERE, null, ex);
               }
                  return result;
        }
       });
       
       return Response.status(200).entity(bene).build();
   }
   
        @GET
        @Path("/list/paginer")
        @Produces("application/json")
        public Response findPagingProgramme(@QueryParam("offset") 
        @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
            return Response.status(200).entity(pr.findPerPager(offset, limit)).build();
        }
        
       @GET
        @Path("/list/recherche")
        @Produces("application/json")
        public Response SearcheProgramme(@QueryParam("attribut") String attribut,@QueryParam("value") String value) throws IntrospectionException {
            
           List<Programme>    b= new ArrayList<Programme>();
            
            String[] attributArray = attribut.split(",");
             PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Programme
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
        
