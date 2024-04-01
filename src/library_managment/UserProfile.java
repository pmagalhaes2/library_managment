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

    public static void main(String[] args) {
        UserProfile adminProfile = new UserProfile("Admin", "admin@example.com", "admin123", UserProfile.UserType.ADMIN);
        String username = adminProfile.getUsername();
        String password = adminProfile.getPassword();
        String email = adminProfile.getEmail();
        UserProfile.UserType userType = adminProfile.getUserType();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("User Type: " + userType);
    }
}