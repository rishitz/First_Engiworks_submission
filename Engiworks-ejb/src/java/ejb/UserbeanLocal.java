/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;
import entity.Tblcity;
import entity.Tbljobcategory;
import entity.Tblrequirement;
import entity.Tbluser;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sebatsian
 */
@Local
public interface UserbeanLocal {
     Collection<Tbljobcategory> getalljob();
     Collection<Tblcity> getallcity();
     void addUser(String userName,String gender,int cityId,String address, String email,String password,int jobCategoryId,int status);
     void addJob(int uid,String title,String description,float budget,int duration,String pdf);
     Tbluser user();
     Tbluser userDetail(int uid);
     void updateUser(int uid,String userName,int cityId,String address, String email,int jobCategoryId);
     public List<Object[]> userPostJob(int uid);
     Tbluser soreSession(String userName);
     Collection<Tblrequirement> getJob(String title);
    // void addAchivement()
     
}
