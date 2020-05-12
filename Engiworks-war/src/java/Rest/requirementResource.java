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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
public class requirementResource {

    UserbeanLocal userbean = lookupUserbeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of requirementResource
     */
    public requirementResource() {
    }

    /**
     * Retrieves representation of an instance of Rest.requirementResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of requirementResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addJob")
    public void addJob(Tblrequirement r)
    {
        userbean.addJob(r.getUserId().getUserId(),r.getTitle(),r.getDescription(),r.getBudget(),r.getDuration(),r.getPdf());
      
    }
    
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getUsername/{userName}")
    public Tbluser setSession(@PathParam("userName") String userName)
    {
       return userbean.soreSession(userName);
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getuser/{uid}")
    public Tbluser getUser(@PathParam("uid") int uid)
    {
        return userbean.userDetail(uid);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updateUser")
    public void updateUser(Tbluser u)
    {
        userbean.updateUser(u.getUserId(), u.getUserName(), u.getCityId().getCityId(), u.getAddress(), u.getEmail(),u.getJobCategoryId().getJobCategoryId());
    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getUserJob/{uid}")
    public List<Object[]> getuserJob(@PathParam("uid") int uid)
    {
        System.out.println("In Rest = "+userbean.userPostJob(uid));
        return userbean.userPostJob(uid);
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
