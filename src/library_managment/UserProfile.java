package library_managment;

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

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }
}