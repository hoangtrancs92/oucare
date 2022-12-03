package com.example.oucare.services;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.department;
public class DepartmentService {
    public List<department> getDepartment() throws SQLException {
        List<department> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM  departments");
            while (rs.next()){
                department info_department = new department(rs.getInt(1),rs.getString(2));
                result.add(info_department);
            }
        }
        return result;
    }
}
