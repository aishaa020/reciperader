package com.example.reciperader;

import java.util.Objects;

public class Users {
    private String username;
    private String password;


    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    // Override equals method to compare objects based on their content
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users users = (Users) obj;
        return username.equals(users.username) && password.equals(users.password);
    }

    // You may also want to override hashCode when overriding equals
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
