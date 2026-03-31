package prj.presentation;

import prj.service.FoodService;
import prj.service.PcService;
import prj.service.UserService;
import prj.util.ValidateInput;

import java.util.Scanner;

public class AdminMenu {
    private  static Scanner sc = new Scanner(System.in);
    private static PcService pc =  new PcService();
    private static FoodService fs =  new FoodService();
    private static UserService us =  new UserService();
    private static ValidateInput validateInput = new ValidateInput();
    public static void show() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Quản lý máy trạm");
            System.out.println("2. Quản lý đồ ăn");
            System.out.println("3. Quản lí người chơi và nhân viên");
            System.out.println("0. Thoát");


            int choice = validateInput.inputChoice(sc);

            switch (choice) {
                case 1:
                    pcMenu();
                    break;
                case 2:
                    foodMenu();
                    break;
                case 3:
                    customerMenu();
                    break;
                case 0:
                    System.out.println("Thoát thành công ");
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }
    private static void pcMenu() {
        while (true) {
            System.out.println("\n--- PC MENU ---");
            System.out.println("1. Thêm máy");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Sửa máy");
            System.out.println("4. Xóa máy");
            System.out.println("0. Quay lại");

            int choice = validateInput.inputChoice(sc);

            switch (choice) {
                case 1:
                    pc.addPc();
                    break;
                case 2:
                    pc.renderPc();
                    break;
                case 3:
                    pc.updatePc();
                    break;
                case 4:
                    pc.deletePc();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }

    // ================= FOOD =================
    private static void foodMenu() {
        while (true) {
            System.out.println("\n--- FOOD MENU ---");
            System.out.println("1. Thêm món");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Sửa món");
            System.out.println("4. Xóa món");
            System.out.println("0. Quay lại");


            int choice = validateInput.inputChoice(sc);

            switch (choice) {
                case 1:
                    fs.addFood();
                    break;
                case 2:
                    fs.renderFood();
                    break;
                case 3:
                    fs.updateFood();
                    break;
                case 4:
                    fs.deleteFood();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }
    // ============== CUSTOMER ====================
    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Thêm người chơi | nhân viên : ");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Sửa thông tin .");
            System.out.println("4. Xóa thông tin .");
            System.out.println("0. Quay lại");

            int choice = validateInput.inputChoice(sc);

            switch (choice) {
                case 1:
                    us.addUser();
                    break;
                case 2:
                    us.renderCustomr();
                    break;
                case 3:
                    us.updateUser();
                    break;
                case 4:
                    us.deleteUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }
}

