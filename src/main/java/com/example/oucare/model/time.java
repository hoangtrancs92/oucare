package com.example.oucare.model;

public class time {
    int id;
    int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time + ":00";
    }

    public void setTime(int time) {
        this.time = time;
    }



    public time(int id, int time) {
        this.id = id;
        this.time = time;
    }
}
