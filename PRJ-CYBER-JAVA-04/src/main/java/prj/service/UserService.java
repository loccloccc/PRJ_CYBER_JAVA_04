package prj.service;

import prj.dao.impl.UserDaoImpl;
import prj.model.User;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserService {

    private UserDaoImpl dao = new UserDaoImpl();
    private static Scanner sc = new Scanner(System.in);

    public User login(String username,String password){
        return dao.login(username,password);
    }

    public boolean register(String username,String password,String full_name,String phone,double balance){
        User u = new User();
        u.setUserId("U" + UUID.randomUUID().toString().substring(0,5));
        u.setUsername(username);
        u.setPassword(password);
        u.setFullName(full_name);
        u.setPhone(phone);
        u.setRole("customer");
        u.setBalance(balance);

        return dao.register(u);
    }

    public boolean checkUsernameExists(String username){
        List<User> users = dao.getAllUser();
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public void renderCustomr(){
        List<User> users = dao.getAllUser();
        if(users.isEmpty()){
            System.out.println("Danh sách rỗng");
            return ;
        }
        System.out.printf("%-12s | %-10s | %-12s | %-12s | %-20s\n",
                "UserName", "Pass", "Phone", "Balance", "Ngày tạo");
        for (User u : users) {
            System.out.printf("%-12s | %-10s | %-12s | %-12.2f | %-20s\n",
                    u.getUsername(), u.getPassword(), u.getPhone(), u.getBalance(), u.getCreate_at());
        }
    }

    // Thêm user
    public void addUser() {
        System.out.println("==== Thêm User ====");

        // Tên đăng nhập
        String username;
        do {
            System.out.print("Nhập username: ");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username không được để trống.");
            } else if (checkUsername(username)) {
                System.out.println("Username đã tồn tại.");
            }
        } while (username.isEmpty() || checkUsername(username));

        // Mật khẩu
        String password;
        do {
            System.out.print("Nhập password: ");
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password không được để trống.");
            }
            if (password.length() < 6) {
                System.out.println("Password phải lớn hơn 6 kí tự");
            }
        } while (password.isEmpty() || password.length() < 6);

        // Họ tên
        String fullName;
        do {
            System.out.print("Nhập full name: ");
            fullName = sc.nextLine().trim();
            if (fullName.isEmpty()) {
                System.out.println("Full name không được để trống.");
            }
        } while (fullName.isEmpty());

        // Số điện thoại
        String phone;
        do {
            System.out.print("Nhập số điện thoại: ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("Số điện thoại không được để trống.");
            }
            if (phone.length() != 11 || !phone.startsWith("0")) {
                System.out.println("SDT không hợp lệ");
            }
        } while (phone.isEmpty() || phone.length() != 11 || !phone.startsWith("0"));

        // Role
        String role;
        do {
            System.out.print("Nhập role (staff | customer): ");
            role = sc.nextLine().trim().toLowerCase();
            if (!role.equals("staff") && !role.equals("customer")) {
                System.out.println("Role không hợp lệ, chỉ được nhập 'staff' hoặc 'customer'.");
            }
        } while (!role.equals("staff") && !role.equals("customer"));
        String userId = "U" + UUID.randomUUID().toString().substring(0, 8);


        double balance ;
        do {
            System.out.println("Nhập số tiền nạp : ");
            balance = sc.nextDouble();
            sc.nextLine();
            if (balance < 0) {
                System.out.println("Số tiền nhập vào không hợp lệ");
            }
        }while (balance < 0);


        User user = new User(userId, username, password, fullName, phone, role, balance);

        if (dao.addUser(user)) {
            System.out.println("Thêm user thành công!");
        } else {
            System.out.println("Thêm user thất bại!");
        }
    }

    // Sửa user
    public void updateUser() {
        System.out.println("==== Sửa User ====");

        System.out.print("Nhập username muốn sửa: ");
        String username = sc.nextLine().trim();
        if (!checkUsername(username)) {
            System.out.println("Username không tồn tại.");
            return;
        }

        // Mật khẩu
        String password;
        do {
            System.out.print("Nhập password mới : ");
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password không được để trống.");
            }
            if (password.length() < 6) {
                System.out.println("Password phải lớn hơn 6 kí tự");
            }
        } while (password.isEmpty() || password.length() < 6);

        // Họ tên
        System.out.print("Nhập full name mới: ");
        String fullName = sc.nextLine().trim();

        // Số điện thoại
        String phone;
        do {
            System.out.print("Nhập số điện thoại mới : ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("Số điện thoại không được để trống.");
            }
            if (phone.length() != 11 || !phone.startsWith("0")) {
                System.out.println("SDT không hợp lệ");
            }
        } while (phone.isEmpty() || phone.length() != 11 || !phone.startsWith("0"));

        // Role
        String role;
        do {
            System.out.print("Nhập role mới (admin | staff | customer): ");
            role = sc.nextLine().trim().toLowerCase();
            if (!role.equals("admin") && !role.equals("staff") && !role.equals("customer")) {
                System.out.println("Role không hợp lệ.");
            }
        } while (!role.equals("admin") && !role.equals("staff") && !role.equals("customer"));

        // Balance
        double balance;
        do {
            System.out.print("Nhập số dư tài khoản: ");
            balance = sc.nextDouble();
            sc.nextLine();
            if (balance < 0) {
                System.out.println("Balance không hợp lệ.");
            }
        } while (balance < 0);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRole(role);
        user.setBalance(balance);

        if (dao.updateUser(user)) {
            System.out.println("Cập nhật user thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    // Xóa user
    public void deleteUser() {
        System.out.println("=== Xóa User ===");

        System.out.print("Nhập username muốn xóa: ");
        String username = sc.nextLine().trim();
        if (!checkUsername(username)) {
            System.out.println("Username không tồn tại.");
            return;
        }

        System.out.print("Xác nhận xóa (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            User user = new User();
            user.setUsername(username);
            if (dao.deleteUser(user)) {
                System.out.println("Xóa user thành công!");
            } else {
                System.out.println("Xóa thất bại!");
            }
        } else {
            System.out.println("Hủy xóa user.");
        }
    }

    // Kiểm tra username
    private boolean checkUsername(String username) {
        List<User> list = dao.getAllUser();
        return list.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }
}