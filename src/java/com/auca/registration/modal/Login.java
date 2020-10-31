
package com.auca.registration.modal;

import com.auca.registration.dao.StudentDao;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JK
 */
@ManagedBean(name="log")
@SessionScoped
public class Login implements Serializable{
    private String username;
    private String password;
    public String login(){
        if(new StudentDao().loginCheck(username,password).equals("RegistraForm")){
            new sessionBean().setCounter(new sessionBean().getCounter()+1);
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failure", " try again with valid credentials"));
        }
        return new StudentDao().loginCheck(username,password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
