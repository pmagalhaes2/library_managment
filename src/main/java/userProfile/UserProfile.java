package main.java.userProfile;

import java.util.UUID;

public class UserProfile {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserType userType;

    public enum UserType {
        ADMIN,
        STANDARD
    }

    public UserProfile(String name, String email, String password, UserType userType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", id, name, email, password, userType);
    }

}