package com.example.oucare.model;

public class user {
    private int id;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String birthday;
    private int sex;
    private int id_role;
    private int id_department;
    public user(){

    }
    public user(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
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
