
package com.auca.registration.modal;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean (name="sb")
@ApplicationScoped
public class sessionBean {
    private int counter;
    public String redirect(){
        counter++;
        return "/studentRegForm.xhtml?faces-redirect=true";
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
}
