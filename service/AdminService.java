package service;

import model.Admin;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    // private default credentials (hanya di kode)
    private static final String DEFAULT_ADMIN_USER = "admin";
    private static final String DEFAULT_ADMIN_PASS = "admin123";
    private static final String DEFAULT_ADMIN_NAME = "Administrator";

    private static final String DEFAULT_KAPRODI_USER = "kaprodi";
    private static final String DEFAULT_KAPRODI_PASS = "kaprodi123";
    private static final String DEFAULT_KAPRODI_NAME = "Kepala Prodi";

    private final List<Admin> admins = new ArrayList<>();
    private int nextId = 1;

    public AdminService() {
        // seed default accounts (private)
        admins.add(new Admin(nextId++, DEFAULT_ADMIN_USER, DEFAULT_ADMIN_PASS, DEFAULT_ADMIN_NAME, Admin.Role.ADMIN));
        admins.add(new Admin(nextId++, DEFAULT_KAPRODI_USER, DEFAULT_KAPRODI_PASS, DEFAULT_KAPRODI_NAME, Admin.Role.KAPRODI));
    }

    public Admin login(String username, String password) {
        for (Admin a : admins) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }

    // optional: allow adding more admin accounts (not used by user)
    public Admin createAdmin(String username, String password, String name, Admin.Role role) {
        Admin a = new Admin(nextId++, username, password, name, role);
        admins.add(a);
        return a;
    }
}
