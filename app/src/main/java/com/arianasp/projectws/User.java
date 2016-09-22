package com.arianasp.projectws;

/**
 * Created by mycomputer on 20/09/16.
 */

public class User {

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String email;
    private String password;

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    private String confirmpassword;
    private String token_auth;
    private String created_at;
    private String updated_at;

    public User(String name, String email, String password,String confirmpassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken_auth() {
        return token_auth;
    }

    public void setToken_auth(String token_auth) {
        this.token_auth = token_auth;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}