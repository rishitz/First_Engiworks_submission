/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static com.sun.faces.facelets.util.Path.context;
import entity.Tblcity;
import entity.Tblgroup;
import entity.Tbljobcategory;
import entity.Tblrequirement;
import entity.Tbluser;
import entity.Tblusergroup;
import entity.Tblusergroup_;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JList;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

/**
 *
 * @author sebatsian
 */
@Stateless
public class Userbean implements UserbeanLocal {
    

        //        Pbkdf2PasswordHashImpl pass = new Pbkdf2PasswordHashImpl();
//        u.setPassword(pass.generate(password.toCharArray()));
@PersistenceContext(unitName = "Engiworks-ejbPU")
    EntityManager em;

    @Override
    public Collection<Tblcity> getallcity() {
          Collection<Tblcity> city=em.createNamedQuery("Tblcity.findAll").getResultList();
    return city; 
    }

    @Override
    public void addUser(String userName, String gender, int cityId, String address, String email, String password, int jobCategoryId, int status) {
        Tbluser u=new Tbluser();
//        Tblcity c=em.find(Tblcity.class, cityId);
//        Tbljobcategory j=em.find(Tbljobcategory.class,jobCategoryId);
//        Collection<Tbluser> ulist=c.getTbluserCollection();
//        Collection<Tbluser> ulist1=j.getTbluserCollection();
        Pbkdf2PasswordHashImpl pass = new Pbkdf2PasswordHashImpl();
        u.setUserName(userName);
        u.setGender(gender);
        u.setCityId(new Tblcity(cityId));
        u.setAddress(address);
        u.setEmail(email);
        u.setPassword(pass.generate(password.toCharArray()));
        u.setJobCategoryId(new Tbljobcategory(jobCategoryId));
        u.setStatus(status);
//        ulist.add(u);
//        ulist1.add(u);
//        em.merge(c);
//        em.merge(j);
        em.persist(u);
        em.flush();
        System.out.println("LastID = "+u.getUserId());
       int lastuid=u.getUserId();
       Tblusergroup ug=new Tblusergroup();
       ug.setUserId(new Tbluser(lastuid));
       ug.setGroupId(new Tblgroup(2));
       em.persist(ug);
       
       
        
        //for usergroup:
        
        //int res=(int) query.getSingleResult();       
    }

    @Override
    public Collection<Tbljobcategory> getalljob() {
         Query query=em.createQuery("select t from Tblusergroup t order by t.userId desc");
        //System.out.println(query.setMaxResults(1));
        
        Collection<Tbljobcategory> job=em.createNamedQuery("Tbljobcategory.findAll").getResultList();
        return job;
       
        
    }

    @Override
    public void addJob(int uid,String title, String description, float budget, int duration, String pdf) {
        Tblrequirement r=new  Tblrequirement();
        //Tbluser u1=user();
        System.out.println("UserID:"+uid);
        Tbluser u2=em.find(Tbluser.class, uid);
        
        r.setUserId(new Tbluser(u2.getUserId()));
        r.setTitle(title);
        r.setDescription(description);
       
        r.setBudget(budget);
        r.setDuration(duration);
        r.setPdf(pdf);
        em.persist(r);
        em.merge(u2);
    }

    @Override
    public Tbluser user() {
        FacesContext context=FacesContext.getCurrentInstance();
        String username=(String)context.getExternalContext().getSessionMap().get("userName");
        System.out.println();
        Tbluser u=(Tbluser)em.createNamedQuery("Tbluser.findByUserName").setParameter("userName",username).getSingleResult();
        return u;
    }

    @Override
    public Tbluser userDetail(int uid) {
//        Tbluser u1=user();
//        FacesContext context=FacesContext.getCurrentInstance();
//        String username=(String)context.getExternalContext().getSessionMap().get("userName");
//        Tbluser u2=(Tbluser) em.createNativeQuery("select * from tbluser where userName='"+ username +"'").getSingleResult();        
        
         return em.find(Tbluser.class,uid);
        //System.out.println("hey"+u2.getUserId());
        
    }

    @Override
    public void updateUser(int uid,String userName, int cityId, String address, String email, int jobCategoryId) {
        //Tbluser u1=user();
        Tbluser u=em.find(Tbluser.class, uid);
        Tblcity c=em.find(Tblcity.class, cityId);
        Collection<Tbluser> ulist=c.getTbluserCollection();
        Tbljobcategory j=em.find(Tbljobcategory.class, jobCategoryId);
        Collection<Tbluser> jlist=j.getTbluserCollection();
        u.setUserName(userName);
        u.setCityId(c);
        u.setAddress(address);
        u.setEmail(email);
        u.setJobCategoryId(j);
        em.merge(u);
        ulist.add(u);
        jlist.add(u);
        em.merge(c);
        em.merge(j);
                }

    @Override
    public List<Object[]> userPostJob(int uid) {
        //Tbluser u=user();
        //Tblrequirement r=em.find(Tblrequirement.class, u.getUserId());
        //int ui=u.getUserId();
       return em.createNativeQuery("select * from tblrequirement where userId="+uid).getResultList();
       
        
    }

    @Override
    public Tbluser soreSession(String userName) {
        Tbluser u=(Tbluser)em.createNamedQuery("Tbluser.findByUserName").setParameter("userName",userName).getSingleResult();
        return u;
    }

    @Override
    public Collection<Tblrequirement> getJob(String title) {
        Collection<Tblrequirement> rlist=em.createNamedQuery("Tblrequirement.findByTitle").setParameter("title", title).getResultList();
        return rlist;
    }
}

    