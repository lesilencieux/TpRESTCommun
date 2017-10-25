/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Fournisseur;
import com.bootcamp.jpa.repositories.FournisseurRepository;
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
@Path("/fournisseur")
public class FournisseurRestController {

    FournisseurRepository fr = new FournisseurRepository("punit-mysql");
    Fournisseur f;
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getList(){
        Fournisseur fournisseur = new Fournisseur();
        return Response.status(200).entity(fournisseur).build();
    }
    
    @GET
    @Path("/listsdebase")
    @Produces("application/json")
    public Response getListBaailleurFromDB(){
        
        return Response.status(200).entity(fr.findAll()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getBaailleurByIdFromDB(@PathParam("id") int id) throws SQLException{
        fr = new FournisseurRepository("punit-mysql");
        Fournisseur fournisseur = fr.findById(id);
        if(fournisseur.getId()!=id){
            return Response.status(200).entity(fournisseur).build();
        }
       if(fournisseur != null) {
           return Response.status(200).entity(fournisseur).build();
        }else return Response.status(404).build();
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Fournisseur fournisseur) throws SQLException {
        fr.create(fournisseur);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Fournisseur fournisseur) throws SQLException {
       fr.update(fournisseur);
    }
    
     @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          f= fr.findById(id);
        try {
            fr.delete(f);
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
       FournisseurRepository fournisseurRepository = new FournisseurRepository("punit-mysql");
       Fournisseur fournisseur = fournisseurRepository.findById(id);
       Map<String, Object> responseMap = new HashMap<>();
       PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Fournisseur
                                                                .class).getPropertyDescriptors();

       for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

           Method method = propertyDescriptor.getReadMethod();
           if (check(fieldArray, propertyDescriptor.getName())) {
               responseMap.put(propertyDescriptor.getName(), method.invoke(fournisseur));
           }
             System.out.println(method.invoke(fournisseur));
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
   public Response triFournisseur(@QueryParam("sort") String attribut){
       List<Fournisseur>  bene = fr.findAll();
       Collections.sort(bene, new Comparator<Fournisseur>() {
           @Override
           public int compare(Fournisseur b, Fournisseur b1) {
               
                         
               int result =0;
                             
               String[] attributArray = attribut.split(",");
               
               PropertyDescriptor[] propertyDescriptors;
               try {
  
                   propertyDescriptors = Introspector.getBeanInfo(Fournisseur.class).getPropertyDescriptors();
                   
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
                   Logger.getLogger(FournisseurRestController.class.getName()).log(Level.SEVERE, null, ex);
               }
                  return result;
        }
       });
       
       return Response.status(200).entity(bene).build();
   }
   
        @GET
        @Path("/list/paginer")
        @Produces("application/json")
        public Response findPagingFournisseur(@QueryParam("offset") 
        @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
            return Response.status(200).entity(fr.findPerPager(offset, limit)).build();
        }
        
       @GET
        @Path("/list/recherche")
        @Produces("application/json")
        public Response SearcheFournisseur(@QueryParam("attribut") String attribut,@QueryParam("value") String value) throws IntrospectionException {
            
           List<Fournisseur>    b= new ArrayList<Fournisseur>();
            
            String[] attributArray = attribut.split(",");
             PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Fournisseur
                                                                .class).getPropertyDescriptors();

                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                    Method method = propertyDescriptor.getReadMethod();
                    if (check(attributArray, propertyDescriptor.getName())) {
                     b=  fr.findSearche(attribut, value);
                      
                    }
                }
            return Response.status(200).entity(b).build(); 
        }     
}
        
