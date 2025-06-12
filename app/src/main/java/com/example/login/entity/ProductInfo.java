package com.example.login.entity;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private String productName;
    private int productId;
    private int productImg;
    private double grade;
    private String actor;
    private String type;
    private String dataTime;
    private String detail;
    private int price;
    private String location;


    public ProductInfo(int productId, String productName, int productImg, double grade, String actor, String type, String dataTime, String location, String detail, int price) {
        this.productName = productName;
        this.productId = productId;
        this.productImg = productImg;
        this.grade = grade;
        this.actor = actor;
        this.type = type;
        this.dataTime = dataTime;
        this.location = location;
        this.detail = detail;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductImg() {
        return productImg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
