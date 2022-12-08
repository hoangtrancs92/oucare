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
    public  void addTickets(ticket t) throws SQLException {
        try {
            Connection cnn = JdbcUtils.getCnn();
            PreparedStatement stm1 = cnn.prepareStatement("INSERT INTO tickets(date_start,time_start,id_customer, id_doctor) VALUES(?,?,?,?) ");
            stm1.setDate(1, Date.valueOf(t.getDate_start()));
            stm1.setString(2,t.getTime_start() );
            stm1.setInt(3,t.getId_customer());
            stm1.setInt(4,t.getId_doctor());

            stm1.executeUpdate();

            stm1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ticket> getTicketsForUser(DatePicker date, int user_id) throws SQLException {
        List<ticket> tickets = new ArrayList<>();
        List<user> users = new ArrayList<>();
        PreparedStatement stm = null;
        try (Connection connection = JdbcUtils.getCnn()) {
            String sql = "SELECT * FROM tickets";
            sql += " WHERE id_customer = ?";
            if (date != null) {
                if (date.getValue() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dates = date.getValue();
                    if (dates != null) {
                        sql += " and date_start = " + "'" + formatter.format(dates) + "'";
                    }
                }
            }

            stm = connection.prepareStatement(sql);
            stm.setInt(1, user_id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int doctor_id = rs.getInt("id_doctor");
                user doctor = new user(doctor_id);
                String subSql = "SELECT users.name, users.id_department FROM users";
                subSql += " WHERE id = " + doctor_id;
                PreparedStatement subStm = connection.prepareStatement(subSql);
                ResultSet subRs = subStm.executeQuery(subSql);

                while (subRs.next()) {
                    int department_id =  subRs.getInt("id_department");

                    String subSql_2 = "SELECT departments.name FROM departments";
                    subSql_2 += " WHERE id = " + department_id;
                    PreparedStatement subStm_2 = connection.prepareStatement(subSql_2);
                    ResultSet subRs_2 = subStm_2.executeQuery(subSql_2);
                    doctor.setName(subRs.getString("name"));
                    subRs_2.next();
                    doctor.setName_department(subRs_2.getString("name"));
                }
                ticket t = new ticket(rs.getInt("id"), rs.getString("date_start"), rs.getString("time_start") + ":00", doctor.getName(), doctor.getName_department());
                tickets.add(t);
            }
        }
        System.err.println(tickets);
        return tickets;
    }
    public List<ticket> getTicketByDepartment(int id_user, int id_department) throws SQLException {
        List<ticket> result = new ArrayList<>();
        try {
            Connection cnn = JdbcUtils.getCnn();

            String sql = "select t.id, t.date_start, t.time_start,ud.doctor, ud.department\n" +
                    "from \n" +
                    "\t(select * from tickets where id_customer=?) as t, \n" +
                    "\t(select u.id, u.name doctor, d.name department \n" +
                    "\t\tfrom (select id, name, id_department from users) as u, departments d \n" +
                    "\t\twhere u.id_department=d.id and d.id=?) \n" +
                    "\t\tas ud \n" +
                    "where t.id_doctor=ud.id";
            PreparedStatement stm = cnn.prepareStatement(sql) ;
            stm.setInt(1, id_user);
            stm.setInt(2, id_department);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                ticket u = new ticket(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5));
                result.add(u);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean checkQuantityTicket(int time_start, int id_doctor, String date) throws SQLException {
        int check =0;

        try {
            Connection cnn = JdbcUtils.getCnn();

            String sql = "select count(t.time_start) as quantity\n" +
                    "from \n" +
                    "\t(select id_doctor, time_start, date_start\n" +
                    "\t\tfrom tickets \n" +
                    "\t\twhere time_start = ?) \n" +
                    "        as t, \n" +
                    "\t(select id\n" +
                    "\t\tfrom users\n" +
                    "\t\twhere id=?) \n" +
                    "\t\tas u\n" +
                    "where t.id_doctor=u.id and t.date_start=?";
            PreparedStatement stm = cnn.prepareStatement(sql) ;
            stm.setInt(1, time_start);
            stm.setInt(2, id_doctor);
            stm.setString(3, date);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                check = rs.getInt(1);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(check==0) return true;
        return false;
    }
}