package com.example.oucare.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.user;
import com.example.oucare.model.department;

public class TicketService {
    public List<department> getDepartments(){
        List<department> departmentList = new ArrayList<>();
        try(Connection connection = JdbcUtils.getCnn()){
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM departments");
            while(rs.next()){
                department d = new department(rs.getInt("id"),rs.getString("name"));
                departmentList.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return departmentList;
    }
}
