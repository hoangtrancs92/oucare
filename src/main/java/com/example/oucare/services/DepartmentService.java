package com.example.oucare.services;

import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.department;
import com.example.oucare.model.user;

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

    public boolean isExits(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getCnn()) {
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM departments WHERE id = ?");
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public int saveDep(department dep) throws SQLException {
        try (Connection conn = JdbcUtils.getCnn()) {
            Statement stm = conn.createStatement();
            String sql = "insert into departments (id, name) values (?, ?) ";
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setInt(1, dep.getId());
            pstm.setString(2, dep.getName());

            return pstm.executeUpdate();
        }
    }

    /**
     * update
     */
    public int updateDep(int id, department dep) {
        try (Connection conn = JdbcUtils.getCnn()) {
            String sql = "update  departments set  name=?  where id=? ";
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, dep.getName());
            pstm.setInt(2, id);

            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
