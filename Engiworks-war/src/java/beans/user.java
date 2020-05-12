/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import RestClient.NewJerseyClient;
import ejb.UserbeanLocal;
import entity.Tblcity;
import entity.Tbljobcategory;
import entity.Tbluser;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author sebatsian
 */
@Named(value = "user")
@RequestScoped
public class user {

    

    @EJB
    private UserbeanLocal userbean;

    private int jid,cid,status;
    private String userName,gender,address,email,password;
    private Collection<Tbljobcategory> jlist;
    private Collection<Tblcity> clist;
    NewJerseyClient jc=new NewJerseyClient();

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Collection<Tblcity> getClist() {
        return clist;
    }

    public void setClist(Collection<Tblcity> clist) {
        this.clist = clist;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public Collection<Tbljobcategory> getJlist() {
        return jlist;
    }

    public void setJlist(Collection<Tbljobcategory> jlist) {
        this.jlist = jlist;
    }
    public user() {
    }
    @PostConstruct
    public void init()
    {
        jlist=userbean.getalljob();
        clist=userbean.getallcity();
    }
    public String adduser()
    {
        //userbean.addUser(userName, gender, cid, address, email, password, jid, status);
        
        Tbluser u = new Tbluser();
        u.setUserName(userName);
        u.setGender(gender);
        u.setCityId(new Tblcity(cid));
        u.setAddress(address);
        u.setEmail(email);
        u.setPassword(password);
        u.setJobCategoryId(new Tbljobcategory(jid));
        u.setStatus(status);
        jc.addUser(u);
        return "/UserSite/Login.xhtml?faces-redirect=true";
        
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
    
}
