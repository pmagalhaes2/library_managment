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

    public UserProfile(String name, String email, String password, UserProfile userType) {
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

    public static void main(String[] args) {
        // Exemplo de uso:
        UserProfile adminProfile = UserProfile.ADMIN;
        String username = adminProfile.getUsername();
        String password = String.valueOf(adminProfile.getPassword());
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    public UserProfile getProfile() {
        return null;
    }

    public Object getEmail() {
        return null;
    }}
