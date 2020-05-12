/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;



import RestClient.NewJerseyClient;
import RestClient.requirementClient;
import ejb.UserbeanLocal;
import entity.Tblrequirement;
import entity.Tbluser;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.http.Part;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sebatsian
 */
@Named(value = "jobBean")
@RequestScoped
public class JobBean {

    @EJB
    private UserbeanLocal userbean;
    requirementClient rc=new requirementClient();
    NewJerseyClient nc=new NewJerseyClient();
    /**
     * Creates a new instance of JobBean
     */
    
   private int uid,status,duration;
   private String title,description,pdf,bud1,dur1,job;
  private Collection<Tblrequirement> joblist;

    public Collection<Tblrequirement> getJoblist() {
        return joblist;
    }

    public void setJoblist(Collection<Tblrequirement> joblist) {
        this.joblist = joblist;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDur1() {
        return dur1;
    }

    public void setDur1(String dur1) {
        this.dur1 = dur1;
    }

    public String getBud1() {
        return bud1;
    }

    public void setBud1(String bud1) {
        this.bud1 = bud1;
    }
   private float budget;
   private Part filename;

    public Part getFilename() {
        return filename;
    }

    public void setFilename(Part filename) {
        this.filename = filename;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
   
    public JobBean() {
    }
    @PostConstruct
    public void init()
    {
        joblist= new ArrayList<Tblrequirement>();
    }
    
    public void addJob()
    {
        String folder="/home/sebatsian/NetBeansProjects/Engiworks/Engiworks-war/PDF";
       String f1=null;
        try(InputStream input=filename.getInputStream()){
            f1=filename.getSubmittedFileName();
            Files.copy(input,new File(folder,f1).toPath());
          
            
        }catch(Exception e)
        {
            
        }
       // userbean.addJob(title, description, status, budget, duration,f1);
        Tblrequirement r=new Tblrequirement();
//        
     HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session=req.getSession(false);
        int uid =  (int) session.getAttribute("userId");
        System.out.println("rqi"+uid);
        r.setUserId(new Tbluser(uid));
        r.setTitle(title);
        r.setDescription(description);
        r.setBudget(budget);
        r.setDuration(duration);
        
        r.setPdf(f1);
        rc.addJob(r);
        
        
    }
    public String allJob()
    {
        System.out.println("job="+job);
        Tblrequirement r=new Tblrequirement();
         Response response=nc.searchJob(Response.class,job);
  
         GenericType<ArrayList<Tblrequirement>> gAdd = new GenericType<ArrayList<Tblrequirement>>(){};
       joblist = (ArrayList<Tblrequirement>) response.readEntity(gAdd);
       //return joblist;
        //joblist= userbean.getJob(job);
        return "Home1.xhtml";
    }
    
}
