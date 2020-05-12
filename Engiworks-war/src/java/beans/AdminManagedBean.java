/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import RestClient.AdminClient;
import entity.Tbljobcategory;
import entity.Tbluser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sebatsian
 */
@Named(value = "adminManagedBean")
@RequestScoped
public class AdminManagedBean {

    /**
     * Creates a new instance of AdminManagedBean
     */
    private String jobName;
    private int jobid;
    AdminClient ac=new AdminClient();

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public AdminManagedBean() {
    }
    public void addJob()
    {
        ac.addJob(jobName);
    }
    public Collection<Tbljobcategory> getalljob()
    {
         Response response=ac.getalljob(Response.class);
         ArrayList<Tbljobcategory> alist=new ArrayList<Tbljobcategory>();
         GenericType<ArrayList<Tbljobcategory>> gAdd = new GenericType<ArrayList<Tbljobcategory>>(){};
         alist = (ArrayList<Tbljobcategory>) response.readEntity(gAdd);
       return alist;
    }
    
    public String getJob(int jid)
    {
        //int jid=jobid;
        System.out.println("jobid"+jid);
        Response response=ac.getJob(Response.class, jid+"");
        GenericType<Tbljobcategory> gAdd = new GenericType<Tbljobcategory>(){};
       Tbljobcategory u2=response.readEntity(gAdd);
       this.jobid=u2.getJobCategoryId();
       jobName=u2.getJobCategoryName();
       return "EditJob.xhtml";
       
    }
    public String updateJob()
    {
        Tbljobcategory j=new Tbljobcategory();
        j.setJobCategoryId(jobid);
        j.setJobCategoryName(jobName);
        ac.updateJob(j);
        return "ViewJob.xhtml";
    }
    public String deleteJob(int jid)
    {
        ac.deleteJob(jid+"");
       return "ViewJob.xhtml";
    }
    
}
