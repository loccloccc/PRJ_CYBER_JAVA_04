package prj.model;

import java.util.Date;

public class User {

    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String role;
    private double balance;
    private Date created_at;

    public User() {}

    public User(String userId, String username, String password, String fullName, String phone, String role, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.balance = balance;
    }

    // getter & setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public Date getCreate_at() { return created_at; }
    public void setCreate_at(Date created_at) { this.created_at = created_at; }
}