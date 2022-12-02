package com.example.oucare.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcUtils {
    public static Connection cnn;
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getCnn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/oucare","root","");
    }
}
