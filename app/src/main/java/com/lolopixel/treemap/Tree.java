package com.lolopixel.treemap;

import java.io.Serializable;

public class Tree implements Serializable {
    private double height;
    private double circumference;
    private String developmentState;
    private String type; // genre
    private String address;
    private double X;
    private double Y;
    private String name;

    public Tree(double height, double circumference, String developmentState, String type, String address, double x, double y, String name) {
        this.height = height;
        this.circumference = circumference;
        this.developmentState = developmentState;
        this.type = type;
        this.address = address;
        X = x;
        Y = y;
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getCircumference() {
        return circumference;
    }

    public void setCircumference(float circumference) {
        this.circumference = circumference;
    }

    public String getDevelopmentState() {
        return developmentState;
    }

    public void setDevelopmentState(String developmentState) {
        this.developmentState = developmentState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
