package prj.presentation;

import prj.service.BookingService;
import prj.service.FoodService;
import prj.service.OrderService;
import prj.service.PcService;
import prj.util.ValidateInput;

import java.util.Scanner;

public class CustomerMenu {
    public static Scanner sc = new Scanner(System.in);
    public static PcService pc = new  PcService();
    public static BookingService bs = new  BookingService();
    private static OrderService os = new  OrderService();
    private static FoodService fs =  new FoodService();
    private static ValidateInput validateInput = new ValidateInput();
    public static void show() {
        while (true) {
            System.out.println("======= CUSTOMER MENU =======");
            System.out.println("1 . Xem danh sách máy trống.");
            System.out.println("2 . Xem danh sách F&B .");
            System.out.println("3 . Bật máy.");
            System.out.println("4 . Đặt đồ ăn.");
            System.out.println("5 . Trạng thái đơn hàng.");
            System.out.println("0 . Tắt máy.");

            int choice = validateInput.inputChoice(sc);

            switch (choice) {
                case 1:
                    pc.renderPcAvai();
                    break;
                case 2:
                    fs.renderFood();
                    break;
                case 3:
                    bs.addBooking();
                    break;
                case 4:
                    os.addOrder();
                    break;
                case 5:
                    os.viewOrdersByUsername();
                    break;
                case 0:
                    bs.closeBooking();
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }
}
