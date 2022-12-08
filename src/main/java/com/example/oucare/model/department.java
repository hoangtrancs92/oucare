package com.example.oucare.model;

public class department {
    private int id;
    private String name;

    public department(int id) {
        this.id = id;
    }

    public department() {
        this.id = 0;
        this.setName(null);
    }
    public department(int id, String name) {
        this.id = id;
        this.setName(name);
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

        return this.id + " - "  + this.name;
    }

    public String setName(String name) {
        this.name = name;
        return "Phòng ban: "  + this.name;
    }
}
