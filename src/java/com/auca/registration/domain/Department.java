
package com.auca.registration.domain;

/**
 *
 * @author JK
 */
public class Department {
    private int id;
    private String name;
    private int faculty_id;

    public Department() {
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

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }
    @Override
    public String toString() {
        return name;
    }
    
}
