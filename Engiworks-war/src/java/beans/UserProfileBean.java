/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import RestClient.NewJerseyClient;
import RestClient.requirementClient;

import ejb.UserbeanLocal;
import entity.Tblcity;
import entity.Tbljobcategory;
import entity.Tblrequirement;
import entity.Tbluser;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sebatsian
 */
@Named(value = "userProfileBean")
@RequestScoped
public class UserProfileBean {

    @EJB
    private UserbeanLocal userbean;

    
    private int jid,cid,status,uid;
    private String userName,gender,address,email,password,job;
    private Collection<Tbljobcategory> jlist;
    private Collection<Tblcity> clist;
    
     NewJerseyClient jc=new NewJerseyClient();
   requirementClient rc=new requirementClient();
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public UserbeanLocal getUserbean() {
        return userbean;
    }

    public void setUserbean(UserbeanLocal userbean) {
        this.userbean = userbean;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Tbljobcategory> getJlist() {
        return jlist;
    }

    public void setJlist(Collection<Tbljobcategory> jlist) {
        this.jlist = jlist;
    }

    public Collection<Tblcity> getClist() {
        return clist;
    }

    public void setClist(Collection<Tblcity> clist) {
        this.clist = clist;
    }
    /**
     * Creates a new instance of UserProfileBean
     */
    public UserProfileBean() {
    }
    @PostConstruct
    public void init()
    {
        clist=userbean.getallcity();
        jlist=userbean.getalljob();
    }
    
    public void userDetail()
    {
       
        //Tbluser u=userbean.userDetail();
        
//        userName=u.getUserName();
//        email=u.getEmail();
//        cid=u.getCityId().getCityId();
//        jid=u.getJobCategoryId().getJobCategoryId();
//        address= u.getAddress();
        
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session=req.getSession(false);
       int u1= (int)session.getAttribute("userId");
        System.out.println("userId"+u1);
        Response response=rc.getUser(Response.class,u1+"");
      GenericType<Tbluser> gAdd = new GenericType<Tbluser>(){};
       Tbluser u2=response.readEntity(gAdd);
        this.uid=u2.getUserId();
        userName=u2.getUserName();
        email=u2.getEmail();
        cid=u2.getCityId().getCityId();
        jid=u2.getJobCategoryId().getJobCategoryId();
        address=u2.getAddress();
        System.out.println("Uid & Name"+uid+userName);
       /*
      Response response=jc.userDetail(Response.class);
       GenericType<Tbluser> gAdd = new GenericType<Tbluser>(){};
       Tbluser u=response.readEntity(gAdd);
       
        this.uid=u.getUserId();
        userName=u.getUserName();
       gender= u.getGender();
       cid= u.getCityId().getCityId();
       address= u.getAddress();
       email= u.getEmail();
       password= u.getPassword();
       jid= u.getJobCategoryId().getJobCategoryId();
        System.out.println("msg:"+userName);*/
    }
    public void updateUser()
    {
        //userbean.updateUser(userName, cid, address, email, jid);
        System.out.println("update Id"+uid);
        Tbluser u=new Tbluser();
        u.setUserId(uid);
        u.setUserName(userName);
        u.setEmail(email);
        u.setCityId(new Tblcity(cid));
        u.setJobCategoryId(new Tbljobcategory(jid));
       u.setAddress(address);
        rc.updateUser(u);
    }
    public List<Object[]> userPostJob()
    {
       //rlist=userbean.userPostJob();
        
         HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session=req.getSession(false);
       int u1= (int)session.getAttribute("userId");
         Response response=rc.getuserJob(Response.class,u1+"");
         List<Object[]> alist=new ArrayList<Object[]>();
         GenericType<List<Object[]>> gAdd = new GenericType<List<Object[]>>(){};
         alist = (List<Object[]>) response.readEntity(gAdd);
       return alist;
    }
    public Collection<Tblrequirement> allJob()
    {
        
        Collection<Tblrequirement> joblist= userbean.getJob(job);
        return joblist;
    }
    
}
