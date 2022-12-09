package com.example.oucare.testcase.com.example.oucare.config;

import com.example.oucare.config.JdbcUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class JdbcUtilsTest {

    private static Connection cnn;
    @BeforeAll
    public static void beforeAll() throws SQLException {
        cnn = JdbcUtils.getCnn();
    }
    @AfterAll
    public static void afterAll() throws SQLException{
        if (cnn != null){
            cnn.close();
        }
    }
    @Test
    public void testUnique() throws SQLException{
        cnn = JdbcUtils.getCnn();
        Statement stm = cnn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from departments");
        List<String> kq = new ArrayList<>();
        while (rs.next()){
            String name = rs.getString("name");
            kq.add(name);
        }
        Set<String> kq2 = new HashSet<>(kq);
        Assertions.assertEquals(kq.size(),kq2.size());
    }
}