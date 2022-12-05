package com.example.oucare.services;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.ticket;
import com.example.oucare.model.user;
import com.example.oucare.model.department;
import javafx.scene.control.DatePicker;

public class TicketService {
    public List<department> getDepartments(){
        List<department> departmentList = new ArrayList<>();
        departmentList.add(new department(0,""));
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
    public List<ticket> getTickets(DatePicker date , department category, String name) throws SQLException {
        List<ticket> tickets = new ArrayList<>();
        List<user> users = new ArrayList<>();
        PreparedStatement stm = null;
        try(Connection connection = JdbcUtils.getCnn()){
            String sql = "SELECT * FROM users";
            if(category.getId() == 0)
            {
                sql += " WHERE id_role > 1";
                if(name != null && !name.isEmpty()){
                    sql += " and name = ? ";
                }
                stm = connection.prepareStatement(sql);
                if(name != null && !name.isEmpty()){
                    stm.setString(1, name);
                }
            }
            else{
                sql += " WHERE id_role > 1 and id_department = " + category.getId();
                if(name != null && !name.isEmpty()){
                    sql += " and name = ? ";
                }
                stm = connection.prepareStatement(sql);
                if(name != null && !name.isEmpty()){
                    stm.setString(1, name);
                }
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                user u = new user(rs.getInt("id"),rs.getString("name"));
                users.add(u);
            }
            Statement stmx = connection.createStatement();
            sql = "SELECT * FROM oucare.tickets";
            sql += " WHERE date_start > 7 ";
            if(users.size() > 0){
                sql += "and ( id_customer IN (";
                for (int i = 0; i < users.size(); i++){
                    if(i < users.size()-1)
                        sql += + users.get(i).getId() + ", ";
                    else
                        sql += + users.get(i).getId();
                }
                sql += ") OR id_doctor IN (";
                for (int i = 0; i < users.size(); i++){
                    if(i < users.size()-1)
                        sql += + users.get(i).getId() + ", ";
                    else
                        sql += + users.get(i).getId();
                }
                sql += "))";
            } else {
                sql += "and ( id_customer IN ('') OR id_doctor IN (''))";
            }
            if(date != null){
                if(date.getValue() != null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dates = date.getValue();
                    if (dates != null) {
                        sql += " and date_start = " + "'" + formatter.format(dates) + "'";
                    }

                }
            }
            System.err.println(sql);
            ResultSet rsx = stmx.executeQuery(sql);
            while(rsx.next()){
                user customer = new user();
                user doctor = new user();
                //get name customer
                int customer_id = rsx.getInt("id_customer");
                sql = "SELECT * FROM users";
                sql += " WHERE id = " + customer_id;
                ResultSet rs1 = stm.executeQuery(sql);
                while (rs1.next()){
                    customer.setId(customer_id);
                    customer.setName(rs1.getString("name"));
                }
                //get name doctor
                int doctor_id = rsx.getInt("id_doctor");
                sql = "SELECT * FROM users";
                sql += " WHERE id = " + doctor_id;
                ResultSet rs2 = stm.executeQuery(sql);
                while (rs2.next()){
                    doctor.setId(doctor_id);
                    doctor.setName(rs2.getString("name"));
                }
                //create new ticket
                ticket t = new ticket(rsx.getInt("id"),rsx.getString("date_start"), rsx.getString("time_start")+":00", customer.getName() , doctor.getName());
                tickets.add(t);
            }
        }

        return tickets;
    }

    public List<ticket> getTicketsForDoctor(DatePicker date, int doctor_id) throws SQLException {
        List<ticket> tickets = new ArrayList<>();
        List<user> users = new ArrayList<>();
        PreparedStatement stm = null;
        try(Connection connection = JdbcUtils.getCnn()){
            String sql = "SELECT * FROM tickets";
            sql += " WHERE id_doctor = ?";
            if(date != null){
                if(date.getValue() != null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dates = date.getValue();
                    if (dates != null) {
                        sql += " and date_start = " + "'" + formatter.format(dates) + "'";
                    }
                }
            }
            System.err.println(sql);
            stm = connection.prepareStatement(sql);
            stm.setInt(1, doctor_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                int customer_id = rs.getInt("id_customer");
                user customer = new user(customer_id);
                String subSql = "SELECT users.name FROM users";
                subSql += " WHERE id = " + customer_id;
                PreparedStatement subStm = connection.prepareStatement(subSql);
                ResultSet subRs = subStm.executeQuery(subSql);
                while (subRs.next()){
                    customer.setName(subRs.getString("name"));
                }
                ticket t = new ticket(rs.getInt("id"),rs.getString("date_start"), rs.getString("time_start")+":00", customer.getName());
                tickets.add(t);
            }
        }

        return tickets;
    }

}