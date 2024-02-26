package com.twosevensix.assi2.models;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // creates a SERIAL datatype or create for the next var vv
    private int sid; // student id
    private String name;
    private int weight;
    private int height;
    private String haircolour;
    private double gpa;
    
    public student() {
    }

    public student(String name, int weight, int height, String haircolour, double gpa) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.haircolour = haircolour;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHaircolour() {
        return haircolour;
    }

    public void setHaircolour(String haircolour) {
        this.haircolour = haircolour;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    
    
}