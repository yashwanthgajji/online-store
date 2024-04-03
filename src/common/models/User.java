package common.models;

import common.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private UUID userID;
    private String name;
    private String email;
    private String password;
    private UserRole role;

    public User(String name, String email, String password) {
        userID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = UserRole.CUSTOMER;
    }

    public User(String name, String email, String password, UserRole role) {
        userID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                '}';
    }
}
