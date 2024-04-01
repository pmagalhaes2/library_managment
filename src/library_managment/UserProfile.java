package library_managment;

import java.util.UUID;

public class UserProfile {
    public static final UserProfile ADMIN = new UserProfile("Admin", "admin@example.com", "admin123", UserProfile.ADMIN);
    public static final UserProfile STANDARD = new UserProfile("User", "user@example.com", "user123", UserProfile.STANDARD);

    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserProfile userType;

    private UserProfile() {}
    UserProfile(String name, String email, String password, UserProfile userType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = UserProfile.STANDARD;
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

    public UserProfile getUserType() {
        return userType;
    }

    public static void main(String[] args) {
        UserProfile adminProfile = UserProfile.ADMIN;
        String username = adminProfile.getUsername();
        String password = adminProfile.getPassword();
        String email = adminProfile.getEmail();
        UserProfile userType = adminProfile.getUserType();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("User Type: " + userType.getUsername());
    }
}