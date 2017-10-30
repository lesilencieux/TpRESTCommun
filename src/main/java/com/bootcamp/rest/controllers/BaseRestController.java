/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.Bailleur;
import com.bootcamp.jpa.enums.TypeBailleur;
import com.bootcamp.jpa.repositories.*;
import java.beans.*;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

public class BaseRestController<C, CR> {

    C classeName;
    CR classeRepositoryNane;

    public BaseRestController(C classeName, CR classeRepositoryNane) {
        this.classeName = classeName;
        this.classeRepositoryNane = classeRepositoryNane;
    }

}
