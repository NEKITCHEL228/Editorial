package domain.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;

    public User(int id, String username, String email, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId() {return id;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getPasswordhash() {return passwordHash;}
    public Role getRole() {return role;}

    public enum Role {
        ADMIN,
        EDITOR,
        AUTHOR
    }
}

