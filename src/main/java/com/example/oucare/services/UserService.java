package com.example.oucare.services;

import java.security.PublicKey;
import java.sql.*;
import java.util.List;

import com.example.oucare.model.ticket;
import com.example.oucare.model.user;
import com.example.oucare.config.JdbcUtils;

public class UserService {
    final String secretKey = "12345678";

    public void addUsers(user u) throws SQLException {
        try {
            Connection cnn = JdbcUtils.getCnn();
            PreparedStatement stm1 = cnn.prepareStatement("INSERT INTO users(name,password,email,phone,address,birthday,sex,id_role) VALUES(?,?,?,?,?,?,?,'3')");
            stm1.setString(1, u.getName());
            stm1.setString(2, u.getPassword());
            stm1.setString(3, u.getEmail());
            stm1.setString(4, u.getPhone());
            stm1.setString(5, u.getAddress());
            stm1.setDate(6, (Date) u.getBirthday());
            stm1.setInt(7, u.getSex());
            stm1.executeUpdate();

            stm1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public user getUserByEmail(String email) throws SQLException {
        user u = new user();
        try (Connection cnn = JdbcUtils.getCnn()) {
            PreparedStatement stm = cnn.prepareCall("SELECT users.id , users.email, users.password, users.id_role FROM  users WHERE email=?");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u = new user(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getInt("id_role"));
                System.err.println(u);
            }
            stm.close();
        }
        return u;
    }

    public user getUserById(int id) throws SQLException {
        user u = new user();
        try (Connection cnn = JdbcUtils.getCnn()) {
            PreparedStatement stm = cnn.prepareCall("SELECT * FROM  users WHERE id=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u = new user(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("address"), rs.getDate("birthday"), rs.getString("phone"),
                        rs.getInt("sex"), rs.getInt("id_role"), rs.getInt("id_department"));
            }
            stm.close();
        }
        return u;
    }

    public user getEmailUser(String email) {
        user u = null;
        try (Connection cnn = JdbcUtils.getCnn()) {
            PreparedStatement stm = cnn.prepareCall("SELECT * FROM users where(email = ?)");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u = new user(rs.getInt(1), rs.getString(4));
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println(u);
        return u;
    }

}
