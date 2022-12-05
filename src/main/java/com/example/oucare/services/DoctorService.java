package com.example.oucare.services;

import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    public List<user> getDoctor(Integer id) throws SQLException {
        List<user> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){

            PreparedStatement stm = conn.prepareStatement("SELECT * FROM  users WHERE id_department like concat('%','"+id+"','%')");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                result.add(new user(rs.getInt(1), rs.getString(10)));
            }
        }
        return result;
    }
    public List<user> getDoctors() throws SQLException {
        List<user> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM  users where id_role = 2");
            while (rs.next()){
                user info_cars = new user(rs.getInt(1),rs.getString(10));
                result.add(info_cars);
            }
        }
        return result;
    }
}
