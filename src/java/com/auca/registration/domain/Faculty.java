
package com.auca.registration.domain;

/**
 *
 * @author JK
 */
public class Faculty {
    private int id;
    private String name;

    public Faculty() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }

   
   
    
}