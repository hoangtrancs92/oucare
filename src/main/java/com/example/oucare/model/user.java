package com.example.oucare.model;

import java.util.Date;

public class user {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private java.sql.Date birthday;
    private int sex;
    private int id_role;
    private int id_department;
    private String name_department;


    public String getName_department() {
        return name_department;
    }

    public void setName_department(String name_department) {
        this.name_department = name_department;
    }

    // Constructor nay dung cho doctor
    public user(int id, String email, String phone, String address, java.sql.Date birthday, int sex, int id_role, int id_department, String password, String name) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
        this.id_role = id_role;
        this.id_department = id_department;
    }
    public user(int id, String name, String email, String phone, String address, java.sql.Date birthday, int sex, int id_role, int id_department) {}

    public user(int id, String name, String email, String phone, String address, java.sql.Date birthday, String string, int sex, int id_role, int id_department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
        this.id_role = id_role;
        this.id_department = id_department;
    }

    public user(String name, String email, String password, String phone, String address, java.sql.Date birthday, int sex) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
    }

    public user(){

    }
    public user(int id, String email, String password, String phone, String address, java.sql.Date birthday, int sex, int id_role, int id_department, String string){

    }

    public user(String email, String password){
        this.email = email;
        this.password = password;
    }

    public user(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public user(int id, String name, String email, String address, java.sql.Date birthday, String phone, int sex, int id_role, int id_department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
        this.id_role = id_role;
        this.id_department = id_department;
    }
    public user(int id, String email, String password, int id_role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.id_role = id_role;
    }

    public user(int customer_id) {
        this.id = customer_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //    @Override
//    public String toString() {
//        return "user{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                ", birthday=" + birthday +
//                ", sex=" + sex +
//                ", id_role=" + id_role +
//                ", id_department=" + id_department +
//                '}';
//    }
    @Override
    public String toString() {
        return this.name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }
    public int getSex(){
        return sex;
    }

    public void setSex(int sex){
        this.sex = sex;
    }
    public int getId_role(){
        return id_role;
    }
    public void setId_role(int id_role){
        this.id_role = id_role;
    }
    public int getId_department(){
        return id_department;
    }
    public void setId_department(int id_department){
        this.id_department = id_department;
    }
}