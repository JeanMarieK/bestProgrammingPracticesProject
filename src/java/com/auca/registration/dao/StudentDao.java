package com.auca.registration.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.auca.registration.domain.*;
import com.auca.registration.Interface.StudentInterface;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author JK
 */
public class StudentDao {

    public int idMax(String table_name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int max = 0;
        String sql = "select max(id) from " + table_name;
        Connection con = con();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return max;
    }

    public int idMaxStudent(String table_name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int max = 0;
        String sql = "select max(studentid) from " + table_name;
        Connection con = con();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return max;
    }

    public String findVillageByLocationId(int n) {
        String names = "";
        try {
            Connection con = con();
            String sql = "SELECT village from address where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                names = rs.getString(1);
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return names;
    }

    public void approve(int n) {
        PreparedStatement ps = null;
        PreparedStatement p = null;
        int id = 0;
        try {
            String query = "update student set status='Approved' where studentid=?";
            ps = con().prepareStatement(query);
            ps.setInt(1, n);
            ps.executeQuery();

            String msg = "";
            String number = "";

//            String q = "select phonenumber from student where studentid=?";
//            p = con().prepareStatement(q);
//            p.setInt(1, n);
//            ResultSet rs = p.executeQuery();
//            while (rs.next()) {
//                number = rs.getString(1);
//            }
            
        } catch (Exception e) {
        }
    }

    public void testSample(String number, String number2) {

       
    }

    public byte[] retrieve(int n) throws ClassNotFoundException {
        byte[] img = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = con();
            System.out.println("Connected to PostgreSQL database!");
            System.out.println("saving photo...");
            PreparedStatement ps = connection.prepareStatement("select file from documents where id=?");
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                img = rs.getBytes(1);
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return img;
    }

    public void reject(int n) {
        PreparedStatement ps = null;
        try {
            String query = "update student set status='Rejected' where studentid=?";
            ps = con().prepareStatement(query);
            ps.setInt(1, n);
            ps.executeQuery();
        } catch (Exception e) {
        }
    }

    public List<Student> allPendingStudents() {
        List<Student> Names = new ArrayList<>();
        int i = 0;
        try {
            Connection con = con();
            String sql = "SELECT studentid, firstname, lastname, nid, dob, gender, email, phonenumber,  location, department, income, residence FROM student where status ='Pending'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student d = new Student();
                d.setStudentId(rs.getInt(1));
                d.setFirstName(rs.getString(2));
                d.setLastName(rs.getString(3));
                d.setNid(rs.getString(4));
                d.setDob(rs.getDate(5));
                d.setGender(rs.getString(6));
                d.setEmail(rs.getString(7));
                d.setPhoneNumber(rs.getString(8));
                d.setVillage(findVillageByLocationId(rs.getInt(9)));
                d.setDepartment_name(new DepartmentDao().findDepartmentById(rs.getInt(10)));
                d.setIncome(rs.getDouble(11));
                d.setResidence(rs.getString(12));
                Names.add(i, d);
                i = i + 1;
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Names;
    }

    public void saveLocation(Location loc) {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO location(province, district, sector, cell, village) VALUES (?, ?, ?, ?, ?)";
            ps = con().prepareStatement(query);
            ps.setString(1, loc.getProvince());
            ps.setString(2, loc.getDistrict());
            ps.setString(3, loc.getSector());
            ps.setString(4, loc.getCell());
            ps.setString(5, loc.getVillage());

            ps.executeQuery();
        } catch (Exception e) {
        }
    }

    public void save(Student st) {
        PreparedStatement ps = null;
        Connection con = con();
        try {
            String query = "insert into student(firstname, lastName, nid, dob, gender, disability, email, phoneNumber, motherName, motherNumber, fatherName, fatherNumber, location, department, status, income, residence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, st.getFirstName().toUpperCase());
            ps.setString(2, st.getLastName().toUpperCase());
            ps.setString(3, st.getNid());
            ps.setDate(4, new java.sql.Date(st.getDob().getTime()));
            ps.setString(5, st.getGender());
            ps.setString(6, st.getDisability());
            ps.setString(7, st.getEmail());
            ps.setString(8, st.getPhoneNumber());
            ps.setString(9, st.getMotherName().toUpperCase());
            ps.setString(10, st.getMotherNumber());
            ps.setString(11, st.getFatherName().toUpperCase());
            ps.setString(12, st.getFatherNumber());
            ps.setInt(13, st.getLocation());
            ps.setInt(14, st.getDepartment());
            ps.setString(15, "Pending");
            ps.setDouble(16,st.getIncome());
            ps.setString(17,st.getResidence());

            ps.executeQuery();
            System.out.println("AFTER EXECUTING QUERY TO SAVE ST");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", st.getLastName() + " was saved successfully!"));
        } catch (Exception e) {
            System.out.println("ERROR IN DAO " + e);
            e.printStackTrace();
        } finally {
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
            }
        }
    }

    public void savePic(UploadedFile file1) throws IOException, ClassNotFoundException {
        if (file1 != null) {
            int len = (int) file1.getSize();
            long fileSize = file1.getSize();
            InputStream myInputStream = file1.getInputstream();
            Connection con = con();
            PreparedStatement ps = null;
            try {
                String sql = "INSERT INTO documents(file, name, student_id, type) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setBinaryStream(1, myInputStream, len);
                ps.setString(2, file1.getFileName());
                ps.setInt(3, idMaxStudent("student"));
                ps.setString(4, "photo");
                ps.executeQuery();
            } catch (SQLException e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                    ps.close();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.println("THE PICTURE IS NULL.............");
        }
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

    public String loginCheck(String username, String password) {
        Connection con = con();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String navigation = "/index";
        try {
            String query = "select * from login where username=? and password=?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                navigation = "/RegistraForm";
            }
        } catch (Exception e) {
        }
        return navigation;
    }

    public List<Student> allApprovedStudents() {
        List<Student> Names = new ArrayList<>();
        int i = 0;
        try {
            Connection con = con();
            String sql = "SELECT studentid, firstname, lastname, nid, dob, gender, email, phonenumber,  location, department FROM student where status ='Approved'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student d = new Student();
                d.setStudentId(rs.getInt(1));
                d.setFirstName(rs.getString(2));
                d.setLastName(rs.getString(3));
                d.setNid(rs.getString(4));
                d.setDob(rs.getDate(5));
                d.setGender(rs.getString(6));
                d.setEmail(rs.getString(7));
                d.setPhoneNumber(rs.getString(8));
                d.setVillage(findVillageByLocationId(rs.getInt(9)));
                d.setDepartment_name(new DepartmentDao().findDepartmentById(rs.getInt(10)));

                Names.add(i, d);
                i = i + 1;
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return Names;
    }

    public List<Student> allRejectedStudents() {
        List<Student> Names = new ArrayList<>();
        int i = 0;
        try {
            Connection con = con();
            String sql = "SELECT studentid, firstname, lastname, nid, dob, gender, email, phonenumber,  location, department FROM student where status ='Rejected'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student d = new Student();
                d.setStudentId(rs.getInt(1));
                d.setFirstName(rs.getString(2));
                d.setLastName(rs.getString(3));
                d.setNid(rs.getString(4));
                d.setDob(rs.getDate(5));
                d.setGender(rs.getString(6));
                d.setEmail(rs.getString(7));
                d.setPhoneNumber(rs.getString(8));
                d.setVillage(findVillageByLocationId(rs.getInt(9)));
                d.setDepartment_name(new DepartmentDao().findDepartmentById(rs.getInt(10)));

                Names.add(i, d);
                i = i + 1;
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return Names;
    }

    public int studentsCount() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=con();
        int num=0;
        try {
            String query="select count(*) from student";
            ps=con.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                num=rs.getInt(1);
            }
        } catch (Exception e) {
        }finally{
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return num;
    }

    public int studentsCountRejected() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=con();
        int num=0;
        try {
            String query="select count(*) from student where status='Rejected'";
            ps=con.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                num=rs.getInt(1);
            }
        } catch (Exception e) {
        }finally{
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return num;
    }

    public int studentsCountApproved() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=con();
        int num=0;
        try {
            String query="select count(*) from student where status='Approved'";
            ps=con.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                num=rs.getInt(1);
            }
        } catch (Exception e) {
        }finally{
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return num;
    }
}
