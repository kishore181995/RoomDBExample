package com.example.appcaremachtas.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    int id;

    public Person(String toString, String name, String dept, String company, String userUId) {
        this.name = name;
        this.userUId = userUId;
        this.dept = dept;
        this.company = company;
    }

    public String getUserUId() {
        return userUId;
    }

    public void setUserUId(String userUId) {
        this.userUId = userUId;
    }

    String userUId;
    String name;
    String dept;
    String company;

    @Ignore
    public Person(String s, String name, String dept, String company) {
        this.name = name;
        this.dept = dept;
        this.company = company;
    }

    public Person(int id, String name, String dept, String company,String userUId) {
        this.id = id;
        this.name = name;
        this.userUId = userUId;
        this.dept = dept;
        this.company = company;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
