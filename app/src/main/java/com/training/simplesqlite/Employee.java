package com.training.simplesqlite;

public class Employee {

    private int id;
    private String name;
    private Long dob;
    private String designation;

    public Employee(int id, String name, Long dob, String designation) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getDob() {
        return dob;
    }

    public String getDesignation() {
        return designation;
    }
}
