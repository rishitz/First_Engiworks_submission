/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Tbljobcategory;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author sebatsian
 */
@Local
public interface AdminbeanLocal {
     public void addJobCategory(String jcn);
    public Collection<Tbljobcategory> getalljob();
    public Tbljobcategory getjob(int jobid);
    void updateJob(int jid,String jobname);
    public void removeJob(int jid);
}
