/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import RestClient.NewJerseyClient;
import RestClient.requirementClient;
import entity.Tbluser;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


/**
 *
 * @author sebatsian
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class loginManagedBean {

     @Inject SecurityContext sc;
   private String email,password,userName;
    requirementClient rc=new requirementClient();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
   private String errmsg;

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

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
   
    public loginManagedBean() {
    }
    
    public String login()
    {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            Credential credential = new UsernamePasswordCredential(getUserName(), new Password(getPassword()));
            AuthenticationStatus status = sc.authenticate(req, res, withParams().credential(credential));
            
            if(status.equals(SUCCESS))
            {
                HttpSession session = req.getSession(true);
                if(sc.isCallerInRole("Admin"))
                {
                    session.setAttribute("admin", "Admin");
                    System.out.println("admin");
//                    session.setAttribute("AdminName", getUserName());
                    return "/AdminSite/DashBoard.xhtml?faces-redirect=true";
                }
                else if(sc.isCallerInRole("User"))
                {
                    //session.setAttribute("Role", "Student");
                   session.setAttribute("userName", getUserName());
                   storesession();
                   System.out.println("beans.loginManagedBean.login()"+getUserName());
                   return "/UserSite/Home.xhtml?faces-redirect=true";
                }
                
                else
                {
                    errmsg = "Username or Password not found!";
                    System.out.println("incorrect");
                }
            }
            else if(status.equals(SEND_FAILURE))
            {
                errmsg = "Username or Password is incorrect!";
                System.out.println("send_failuer");
                return "/Login.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String logout()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpSession session = req.getSession(false);
        try {
            
                session.invalidate();
                req.logout();
                return "/UserSite/Login.xhtml?faces-redirect=true";
                //res.sendRedirect(req.getContextPath()+"/login.xhtml");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void storesession()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session=req.getSession(false);
        String uname=(String) session.getAttribute("userName");
        Response response=rc.setSession(Response.class, uname);
        GenericType<Tbluser> u=new GenericType<Tbluser>(){};
        Tbluser u1=response.readEntity(u);
        session.setAttribute("userId", u1.getUserId());
        System.out.println("session Id"+u1.getUserId());
        
        
    }
}
    

