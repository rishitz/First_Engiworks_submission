/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Tbljobcategory;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sebatsian
 */
@Stateless
public class Adminbean implements AdminbeanLocal {
    
    @PersistenceContext(unitName = "Engiworks-ejbPU")
    EntityManager em;

    @Override
    public void addJobCategory(String jcn) {
        Tbljobcategory j=new Tbljobcategory();
        j.setJobCategoryName(jcn);
        em.persist(j);
    }

    @Override
    public Collection<Tbljobcategory> getalljob() {
        Collection<Tbljobcategory> jlist=em.createNamedQuery("Tbljobcategory.findAll").getResultList();
        return jlist;
    }

    @Override
    public Tbljobcategory getjob(int jobid) {
        return em.find(Tbljobcategory.class, jobid);
        
        
        
    }

    @Override
    public void updateJob(int jid, String jobname) {
        Tbljobcategory j=em.find(Tbljobcategory.class, jid);
        j.setJobCategoryName(jobname);
        em.merge(j);
    }

    @Override
    public void removeJob(int jid) {
        Tbljobcategory j=em.find(Tbljobcategory.class, jid);
        em.remove(j);
    }

    
}
