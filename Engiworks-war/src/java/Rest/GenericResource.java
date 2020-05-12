/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import ejb.UserbeanLocal;
import entity.Tblrequirement;
import entity.Tbluser;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author sebatsian
 */
@Path("generic")
public class GenericResource {

    UserbeanLocal userbean = lookupUserbeanLocal();

    @Context
    private UriInfo context;
    

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of Rest.GenericResource
     * @return an instance of java.lang.String
     */
    

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addUser")
    public void addUser(Tbluser u)
    {
        userbean.addUser(u.getUserName(),u.getGender(),u.getCityId().getCityId(),u.getAddress(),u.getEmail(), u.getPassword(),u.getJobCategoryId().getJobCategoryId(),u.getStatus());
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("searchJob/{jname}")
    public Collection<Tblrequirement> searchJob(@PathParam("jname") String jname)
    {
        return userbean.getJob(jname);
    }    
    

    private UserbeanLocal lookupUserbeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserbeanLocal) c.lookup("java:global/Engiworks/Engiworks-ejb/Userbean!ejb.UserbeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
