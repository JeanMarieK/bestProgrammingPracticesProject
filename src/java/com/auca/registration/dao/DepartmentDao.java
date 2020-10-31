package com.auca.registration.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.auca.registration.domain.*;

/**
 *
 * @author JK
 */
public class DepartmentDao {

    public void saveDepartmentCategory(String name) {
        PreparedStatement ps = null;
        try {
            Connection con=con();
            String query = "INSERT INTO department_category(name)VALUES (?)";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.executeQuery();
            ps.close();
            con.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("DEPT ERROR!: " + e);
        }
    }
    public void saveDepartment(Department d) {
        PreparedStatement ps = null;
        try {
            Connection con=con();
            String query = "INSERT INTO department(name,faculty_id)VALUES (?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, d.getName());
            ps.setInt(2, d.getFaculty_id());
            ps.executeQuery();
            ps.close();
            con.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("DEPT ERROR!: " + e);
        }
    }
    public void saveFaculty(Faculty d) {
        PreparedStatement ps = null;
        try {
            Connection con=con();
            String query = "INSERT INTO faculty (name)VALUES (?)";
            ps = con.prepareStatement(query);
            ps.setString(1, d.getName());
            ps.executeQuery();
            ps.close();
            con.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("DEPT ERROR!: " + e);
        }
    }
    public List<Department> allDepartmentss() {
        List<Department> Names = new ArrayList<>();
        Connection con=con();
        PreparedStatement ps=null;
        ResultSet rs =null;
        int i=0;
        try {
            String sql = "select DISTINCT id,name from department order by name";
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()) {
                Department d=new Department();
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                Names.add(i,d);
                i=i+1;
            }
        } catch (Exception e) {
        }finally{
            try {
                con.close();
                ps.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return Names;
    }
    public List<Faculty> allFaculties() {
        List<Faculty> Names = new ArrayList<>();
        Connection con=con();
        PreparedStatement ps=null;
        ResultSet rs =null;
        int i=0;
        try {
            String sql = "select DISTINCT id,name from faculty order by name";
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()) {
                Faculty d=new Faculty();
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                Names.add(i,d);
                i=i+1;
            }
        } catch (Exception e) {
        }finally{
            try {
                con.close();
                ps.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return Names;
    }
    public List<Department> findDepartmentByFacultyId(int n) {
        List<Department> Names = new ArrayList<>();
        int i=0;
        try {
            Connection con=con();
            String sql = "select DISTINCT id,name, faculty_id from department where faculty_id=? order by name";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department d=new Department();
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                d.setFaculty_id(rs.getInt(3));
                Names.add(i,d);
                i=i+1;
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return Names;
    }
    public String findDepartmentById(int n) {
            String Names="";
        try {
            Connection con=con();
            String sql = "select name from department where id=? order by name";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Names=rs.getString(1);
                
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return Names;
    }
    public List<Department> departmentsByFacultyId(int cat) {
        List<Department> Names = new ArrayList<>();
        int i=0;
        try {
            Connection con=con();
            String sql = "select id,name from department where faculty_id=? order by name";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department d=new Department();
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                Names.add(i,d);
                i=i+1;
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return Names;
    }
    public Connection con() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/WebTechnologies", "postgres", "123");
        } catch (Exception e) {
        }
        return connection;
    }
}
