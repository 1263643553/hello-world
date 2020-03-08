package com.example.wxs.entity;


import javax.persistence.*;

@Entity(name = "users")
public class Users{

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users() {

    }
}
