package com.example.oucare.services;

import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.time;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TimeService {
    public List<time> getTime() throws SQLException {
        List<time> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM  times");
            while (rs.next()){
                time info_time = new time(rs.getInt(1),rs.getInt(2));
                result.add(info_time);
            }
        }
        return result;
    }
}
