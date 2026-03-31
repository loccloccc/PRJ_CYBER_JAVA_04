package prj.presentation;

import prj.model.User;
import prj.service.UserService;
import prj.util.ValidateInput;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static ValidateInput validateInput = new ValidateInput();
    public static void show() {

        while (true) {
            System.out.println("\n===== CYBER GAME =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("0. Thoát");

            int choice = validateInput.inputChoice(sc);


            switch (choice) {
                case 1:
                    System.out.println("Đăng nhập");
                    logIn();
                    break;
                case 2:
                    System.out.println("Đăng kí");
                    signin();
                    break;
                case 0:
                    System.out.println("Thoát thành công");
                     System.exit(0);
                default:
                    System.out.println("Lựa chọn này không hợp lệ");
            }
        }
    }
    public static void logIn(){
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User u = userService.login(username.trim(), password.trim());
        if (u == null) {
            System.out.println("Đăng nhập thất bại");
        }else{
            System.out.println("Đăng nhập thành công");
            redirectByRole(u);
        }
    }
    public static void signin(){
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.println("Nhập đầy đủ và tên : ");
        String full_name = sc.nextLine();
        System.out.println("Nhập số điện thoại : ");
        String phone  = sc.nextLine();
        double balance;
        while(true){
            System.out.print("Nhập số tiền nạp: ");
            String input = sc.nextLine();
            try {
                balance = Double.parseDouble(input);
                if(balance < 0){
                    System.out.println("Số tiền phải >= 0");
                    continue;
                }
                break; // hợp lệ
            } catch(NumberFormatException e){
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        if (userService.register(username,password,full_name,phone,balance)){
            System.out.println("Đăng kí thành công");
        }else{
            System.out.println("Đăng kí thất bại");
        }
    }

    private static void redirectByRole(User user) {
        switch (user.getRole()) {
            case "admin":
                System.out.println(" Vào menu ADMIN");
                AdminMenu.show();
                break;
            case "staff":
                System.out.println(" Vào menu STAFF");
                StaffMenu.show();
                break;
            case "customer":
                System.out.println("Vào menu CUSTOMER");
                CustomerMenu.show();
                break;
            default:
                System.out.println(" Role không hợp lệ");
        }
    }


    // chương trình chạy
    static void main(String[] args) {
        show();
    }

}
