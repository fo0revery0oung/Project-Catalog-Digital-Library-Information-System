package model;

public class Admin {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final Role role;

    public enum Role { ADMIN, KAPRODI }

    public Admin(int id, String username, String password, String name, Role role) {
        this.id = id; this.username = username; this.password = password; this.name = name; this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public Role getRole() { return role; }
}
