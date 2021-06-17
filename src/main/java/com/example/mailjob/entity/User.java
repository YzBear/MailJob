package com.example.mailjob.entity;


import java.util.Date;

/**
 * @author Bear
 */
public class User {

    private String id;
    private String name;
    private Date birthday;
    private String email;

    public User(String id, String name, Date birthday, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}