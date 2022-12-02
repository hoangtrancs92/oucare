package com.example.oucare.model;

public class ticket {
    private int id;
    private String date_start;
    private String time_start;
    private int id_customer;
    private int id_doctor;

    public ticket(int id, String date_start, String time_start, int id_customer, int id_doctor) {
        this.id = id;
        this.date_start = date_start;
        this.time_start = time_start;
        this.id_customer = id_customer;
        this.id_doctor = id_doctor;
    }

    public int getId() {
        return id;
    }

    public String getDate_start() {
        return date_start;
    }

    public String getTime_start() {
        return time_start;
    }

    public int getId_customer() {
        return id_customer;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }
}
