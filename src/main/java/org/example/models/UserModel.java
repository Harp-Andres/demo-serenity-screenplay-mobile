package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    @JsonProperty("usuario")
    private String user;

    @JsonProperty("password")
    private String password;

    // Getters
    public String getUser() { return user; }
    public String getPassword() { return password; }
}