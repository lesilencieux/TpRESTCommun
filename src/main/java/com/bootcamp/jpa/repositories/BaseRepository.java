/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.repositories;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 * @param <T>
 */
public class BaseRepository<T> {
    
  private final EntityManager em;
  private final String unitPersistence;
  private final EntityManagerFactory emf;
  private final Class entityClass;

 
    public BaseRepository(String unitPersistence,  Class entityClass) {
        this.unitPersistence = unitPersistence;
        emf=Persistence.createEntityManagerFactory(this.unitPersistence);
        this.em=emf.createEntityManager();
        this.entityClass = entityClass;
    }
    
    public EntityManager getEntityManager(){
        return this.em;
    }
    
    public Boolean create(T entity) throws SQLException {
       getEntityManager().getTransaction().begin();
       getEntityManager().persist(entity);
       getEntityManager().getTransaction().commit();
       return true;
    }
    
    public Boolean delete(T type){
        getEntityManager().getTransaction().begin();
              getEntityManager().remove(type);
         getEntityManager().getTransaction().commit();
         return true;
              
    }
    
    public List<T> findByProperty(String propertyName, Object value) throws SQLException {
           
            String className = entityClass.getSimpleName(); 
            String str = "SELECT DISTINCT ob FROM "+ className+ " ob WHERE ob."+propertyName +" = :param";
            Query query = getEntityManager().createQuery(str);
            query.setParameter("param", value);

            List<T> result = query.getResultList();
          
            return result;
        }
    
    public T findById(long value) throws SQLException {
           
            String className = entityClass.getSimpleName(); 
            String str = "SELECT DISTINCT ob FROM "+ className+ " ob WHERE ob.id = :param";
            Query query = getEntityManager().createQuery(str);
            query.setParameter("param", value);
            return  (T)   query.getSingleResult();
        }
    
    
    
    
    
    public List<T> findAll(){
        
            String className = entityClass.getSimpleName(); 
            String s = "select ob FROM "+className+" ob";
            Query query = getEntityManager().createQuery(s);
            List<T> result = query.getResultList();
            return result;
    }
    
    
     public List<T> findSearche(String str ,String value){
        
            String className = entityClass.getSimpleName(); 
            String s = "SELECT ob FROM "+className+" ob WHERE ob."+str+" LIKE '%"+value+"%'";
            Query query = getEntityManager().createQuery(s);
             List<T> result = query.getResultList();
            return result;
    }
    
    
    
     public List<T> findPerPager(int offset,int  limit){
        
            String className = entityClass.getSimpleName(); 
            String s = "select ob FROM "+className+" ob";
            Query query = getEntityManager().createQuery(s);
            query.setFirstResult(offset).setMaxResults(limit);
            List<T> result = query.getResultList();
            return result;
    }
    
    
    public Boolean update(T t){
       getEntityManager().getTransaction().begin();
       getEntityManager().merge(t);
       getEntityManager().getTransaction().commit();
       return true;
    }
    
}
