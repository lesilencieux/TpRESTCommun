/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Bailleur;
import com.bootcamp.jpa.entities.Beneficiaire;
import com.bootcamp.jpa.entities.IndicateurPerformance;
import com.bootcamp.jpa.repositories.BeneficiaireRepository;
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
@Path("/beneficiaire")
public class BeneficiaireRestController {

    BeneficiaireRepository br = new BeneficiaireRepository("punit-mysql");
    Beneficiaire b;
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getList(){
        Beneficiaire beneficiaire = new Beneficiaire();
        return Response.status(200).entity(beneficiaire).build();
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
        br = new BeneficiaireRepository("punit-mysql");
        Beneficiaire beneficiaire = br.findById(id);
        if(beneficiaire.getId()!=id){
            return Response.status(200).entity(beneficiaire).build();
        }
       if(beneficiaire != null) {
           return Response.status(200).entity(beneficiaire).build();
        }else return Response.status(404).build();
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Beneficiaire beneficiaire) throws SQLException {
        br.create(beneficiaire);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Beneficiaire beneficiaire) throws SQLException {
       br.update(beneficiaire);
    }
    
     @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          b= br.findById(id);
        try {
            br.delete(b);
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
       BeneficiaireRepository beneficiaireRepository = new BeneficiaireRepository("punit-mysql");
       Beneficiaire beneficiaire = beneficiaireRepository.findById(id);
       Map<String, Object> responseMap = new HashMap<>();
       PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Beneficiaire
                                                                .class).getPropertyDescriptors();

       for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

           Method method = propertyDescriptor.getReadMethod();
           if (check(fieldArray, propertyDescriptor.getName())) {
               responseMap.put(propertyDescriptor.getName(), method.invoke(beneficiaire));
           }
             System.out.println(method.invoke(beneficiaire));
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
   public Response triBeneficiaire(@QueryParam("sort") String attribut){
       List<Beneficiaire>  bene = br.findAll();
       Collections.sort(bene, new Comparator<Beneficiaire>() {
           @Override
           public int compare(Beneficiaire b, Beneficiaire b1) {
               
                         
               int result =0;
                             
               String[] attributArray = attribut.split(",");
               
               PropertyDescriptor[] propertyDescriptors;
               try {
  
                   propertyDescriptors = Introspector.getBeanInfo(Beneficiaire.class).getPropertyDescriptors();
                   
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
                   Logger.getLogger(BeneficiaireRestController.class.getName()).log(Level.SEVERE, null, ex);
               }
                  return result;
        }
       });
       
       return Response.status(200).entity(bene).build();
   }
   
        @GET
        @Path("/list/paginer")
        @Produces("application/json")
        public Response findPagingBeneficiaire(@QueryParam("offset") 
        @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("2") Integer limit) {
            return Response.status(200).entity(br.findPerPager(offset, limit)).build();
        }
        
       @GET
        @Path("/list/recherche")
        @Produces("application/json")
        public Response SearcheBeneficiaire(@QueryParam("attribut") String attribut,@QueryParam("value") String value) throws IntrospectionException {
            
           List<Beneficiaire>    b= new ArrayList<Beneficiaire>();
            
            String[] attributArray = attribut.split(",");
             PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Beneficiaire
                                                                .class).getPropertyDescriptors();

                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                    Method method = propertyDescriptor.getReadMethod();
                    if (check(attributArray, propertyDescriptor.getName())) {
                     b=  br.findSearche(attribut, value);
                      
                    }
                }
            return Response.status(200).entity(b).build(); 
        }     
}
        
