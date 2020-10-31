package com.auca.registration.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author JK
 */
public class AddressDao {

    public List<String> provinces() {
        List<String> prov = new ArrayList<>();
        try {
            Connection con=con();
            String sql = "select DISTINCT province from address order by province";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prov.add(rs.getString(1));
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return prov;
    }
    public List<String> districts(String name) {
        List<String> dist = new ArrayList<>();
        try {
            Connection con=con();
            String sql = "select DISTINCT district from address where province=? order by district";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dist.add(rs.getString(1));
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return dist;
    }
    public List<String>  sectors(String name) {
        List<String>  sec = new ArrayList<>();
        try {
            Connection con=con();
            String sql = "select DISTINCT  sector from address where district=? order by  sector";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 sec.add(rs.getString(1));
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return  sec;
    }
    public List<String>  cells(String name) {
        List<String>  cel = new ArrayList<>();
        try {
            Connection con=con();
            String sql = "select DISTINCT  cell from address where sector=? order by  cell";
            PreparedStatement ps = con().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 cel.add(rs.getString(1));
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return  cel;
    }
    public List<String>  villages(String name) {
        List<String>  vil = new ArrayList<>();
        try {
            Connection con=con();
            String sql = "select DISTINCT  village from address where cell=? order by  village";
            PreparedStatement ps = con().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 vil.add(rs.getString(1));
            }
            con.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        return  vil;
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
