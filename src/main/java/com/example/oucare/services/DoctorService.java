package com.example.oucare.services;

import com.example.oucare.config.JdbcUtils;
import com.example.oucare.model.department;
import com.example.oucare.model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
//    public List<user> getDoctors() throws SQLException {
//        List<user> result = new ArrayList<>();
//        try(Connection conn = JdbcUtils.getCnn()){
//            Statement stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery("SELECT * FROM  users where id_role = 2");
//            while (rs.next()){
//                user info_cars = new user(rs.getInt(1),rs.getString(10));
//                result.add(info_cars);
//            }
//        }
//        return result;
//    }

    public user getDoctor(int id) throws SQLException {
       user result =new user();
        try(Connection conn = JdbcUtils.getCnn()){

            PreparedStatement stm = conn.prepareStatement("SELECT * FROM  users WHERE id=? and id_role=2");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
            {
                id=rs.getInt(1);
                String email = rs.getString(2);
                String phone=rs.getString(3);
                String address= rs.getString(4);
                Date birthday=rs.getDate(5);
                int sex=rs.getInt(6);
                int id_role=rs.getInt(7);
                int id_department=rs.getInt(8);
                String password=rs.getString(9);
                String name= rs.getString(10);
                return new user(id, email,  phone,  address,  birthday,  sex,  id_role,  id_department,  password,  name);
            }

        }
        return null;

    }
    public List<user> getDoctors() throws SQLException {
        List<user> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM  users where id_role = 2");
            while (rs.next()){
                int  id=rs.getInt(1);
                String email = rs.getString(2);
                String phone=rs.getString(3);
                String address= rs.getString(4);
                Date birthday=rs.getDate(5);
                int sex=rs.getInt(6);
                int id_role=rs.getInt(7);
                int id_department=rs.getInt(8);
                String password=rs.getString(9);
                String name= rs.getString(10);
                user user= new user(id, email,  phone,  address,  birthday,  sex,  id_role,  id_department,  password,  name);
                result.add(user);
            }
        }
        return result;
    }


    public List<user> getDoctors(department department) throws SQLException {
        List<user> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            ResultSet rs;
            PreparedStatement pstm= conn.prepareStatement("SELECT * FROM  users where id_department=? and id_role=2");
          pstm.setInt(1,department.getId());
          rs= pstm.executeQuery();
            while (rs.next()){
                int  id=rs.getInt(1);
                String email = rs.getString(2);
                String phone=rs.getString(3);
                String address= rs.getString(4);
                Date birthday=rs.getDate(5);
                int sex=rs.getInt(6);
                int id_role=rs.getInt(7);
                int id_department=rs.getInt(8);
                String password=rs.getString(9);
                String name= rs.getString(10);
                user user= new user(id, email,  phone,  address,  birthday,  sex,  id_role,  id_department,  password,  name);
                result.add(user);
            }
        }
        return result;
    }

    public List<user> getDoctors(String keyword) throws SQLException {
        List<user> result = new ArrayList<>();
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            ResultSet rs=stm.executeQuery("select * from Users where id_role=2 and  name like '%"+keyword+"%' or address like '%"+keyword+"%' or phone like '%"+keyword+"%' or email like '%"+keyword+"%'"  );

            while (rs.next()){
                int  id=rs.getInt(1);
                String email = rs.getString(2);
                String phone=rs.getString(3);
                String address= rs.getString(4);
                Date birthday=rs.getDate(5);
                int sex=rs.getInt(6);
                int id_role=rs.getInt(7);
                int id_department=rs.getInt(8);
                String password=rs.getString(9);
                String name= rs.getString(10);
                user user= new user(id, email,  phone,  address,  birthday,  sex,  id_role,  id_department,  password,  name);
                result.add(user);
            }
        }
        return result;
    }


    public int saveDoctor(user  doctor) throws SQLException {
        try(Connection conn = JdbcUtils.getCnn()){
            Statement stm = conn.createStatement();
            String sql="insert into Users (id, email, phone, address, birthday, sex, id_role, id_department, password, name ) values (?, ?, ?,?, ?, ?, ?, ?, ?, ? ) ";
            PreparedStatement pstm= conn.prepareStatement(sql);
            pstm.setInt(1, doctor.getId());
            pstm.setString(2, doctor.getEmail());
            pstm.setString(3, doctor.getPhone());
            pstm.setString(4, doctor.getAddress());
            pstm.setDate(5, (Date) doctor.getBirthday());
            pstm.setInt(6, doctor.getSex());
            pstm.setInt(7, doctor.getId_role());
            pstm.setInt(8, doctor.getId_department());
            pstm.setString(9, doctor.getPassword());
            pstm.setString(10, doctor.getName());
            return pstm.executeUpdate();
        }
    }

    public int updateDoctor(int id,user doctor){
        try(Connection conn = JdbcUtils.getCnn()){
            String sql="update  Users set  email=?, phone=?, address=?, birthday=?, sex=?, id_role=?, id_department=?, password=?, name=?  where id=? ";
            PreparedStatement pstm= conn.prepareStatement(sql);

            pstm.setString(1, doctor.getEmail());
            pstm.setString(2, doctor.getPhone());
            pstm.setString(3, doctor.getAddress());
            pstm.setDate(4, (Date) doctor.getBirthday());
            pstm.setInt(5, doctor.getSex());
            pstm.setInt(6, doctor.getId_role());
            pstm.setInt(7, doctor.getId_department());
            pstm.setString(8, doctor.getPassword());
            pstm.setString(9, doctor.getName());
            pstm.setInt(10, id);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
