package com.Project.StudentManagementSystem;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private int marks;
    private String type;

    public Student() {}

    public Student(String name, String email, int marks, String type) {
        this.name = name;
        this.email = email;
        this.marks = marks;
        this.type = type;
    }

    // GETTERS AND SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", email=" + email + ", marks=" + marks + ", type=" + type + "]";
    }
}