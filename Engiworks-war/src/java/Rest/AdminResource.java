/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import ejb.AdminbeanLocal;
import entity.Tbljobcategory;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author sebatsian
 */
@Path("generic")
@RequestScoped
public class AdminResource {

    AdminbeanLocal adminbean = lookupAdminbeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AdminResource
     */
    public AdminResource() {
    }

    /**
     * Retrieves representation of an instance of Rest.AdminResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AdminResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
     @POST
   // @Consumes(MediaType.APPLICATION_JSON)
    @Path("addJobCategory/{jobname}")
    public void addJob(@PathParam("jobname") String jobName)
    {
        adminbean.addJobCategory(jobName);
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getallJob")
    public Collection<Tbljobcategory> getalljob()
    {
        return adminbean.getalljob();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getJobd/{jid}")
    public Tbljobcategory getJob(@PathParam("jid") int jid)
    {
        return adminbean.getjob(jid);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updateJob")
    public void updateJob(Tbljobcategory j)
    {
        adminbean.updateJob(j.getJobCategoryId(),j.getJobCategoryName());
    }
    
    @DELETE
    @Path("delete/{jid}")
    public void deleteJob(@PathParam("jid") int jid)
    {
        adminbean.removeJob(jid);
    }
    
    private AdminbeanLocal lookupAdminbeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdminbeanLocal) c.lookup("java:global/Engiworks/Engiworks-ejb/Adminbean!ejb.AdminbeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
