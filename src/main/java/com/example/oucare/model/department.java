package com.example.oucare.model;

public class department {
    private int id;
    private String name;

    public department(int id) {
        this.id = id;
    }
    
    public department() {
        this.id = 0;
        this.name = null;
    }
    public department(int id, String name) {
        this.id = id;
        this.name = name;
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
    @Override
    public String toString() {
        return "ID: "+ this.id + "\nPhòng ban: "  + this.name;
    }
}
