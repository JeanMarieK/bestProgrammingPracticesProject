package com.auca.registration.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import com.auca.registration.dao.AddressDao;
import com.auca.registration.dao.DepartmentDao;
import com.auca.registration.domain.*;

/**
 *
 * @author JK
 */
@ManagedBean(name = "dm")
@SessionScoped
public class departmentModal implements Serializable {

    private String department_name;
    private String name;
    private int dept_id;
    private int faculty_id;
    private String faculty_name;
    private int faculty;
    private String facultyName;
    private String departmentName;
    
   
    public List<Department> allDepartmentss() {
        return new DepartmentDao().allDepartmentss();
    }
    public List<Faculty>allFaculties (){
        return new DepartmentDao().allFaculties();
    }
    
    public void asaveDept() {
        Department de = new Department();
        de.setName(departmentName);
        de.setFaculty_id(faculty_id);
        new DepartmentDao().saveDepartment(de);
    }
    public void saveFaculty() {
        Faculty de = new Faculty();
        de.setName(facultyName);
        new DepartmentDao().saveFaculty(de);
    }
    public List<Department> departmentsByFacultyId(){
        return new DepartmentDao().departmentsByFacultyId(faculty_id);
    }
    public void asaveFaculty() {
        Faculty s = new Faculty();
        s.setName(faculty_name);
        new DepartmentDao().saveFaculty(s);
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
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

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public int getFaculty() {
        return faculty;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

}
