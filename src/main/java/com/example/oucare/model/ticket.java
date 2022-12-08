package com.example.oucare.model;

public class ticket {

    private int id;
    private String date_start;
    private String time_start;
    private int id_customer;
    private int id_doctor;
    private String doctor_name;
    private String customer_name;
    private String department_name;
    public ticket(int id, String date_start, String time_start, int id_customer, int id_doctor) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.id_customer = id_customer;
        this.id_doctor = id_doctor;
    }


    public ticket(String date_start, String time_start, int id_customer, int id_doctor) {
        this.date_start = date_start;
        this.time_start = time_start;
        this.id_customer = id_customer;
        this.id_doctor = id_doctor;
    }
    public ticket(int id, String date_start, String time_start, String doctor_name, String department_name,int So) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.doctor_name = doctor_name;
        this.department_name = department_name;

    }
    public ticket(int id, String date_start, String time_start, String customer_name, String doctor_name){
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.customer_name = customer_name;
        this.doctor_name = doctor_name;
    }
    public ticket(int id, String date_start, String time_start, String name) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.customer_name = name;
    }
    public ticket(int id, String date_start, String time_start, String doctor_name, String department_name, String customer_name) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.doctor_name = doctor_name;
        this.department_name = department_name;
        this.customer_name = customer_name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }
    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

}
