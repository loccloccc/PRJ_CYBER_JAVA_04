package prj.presentation;

import prj.service.BookingService;
import prj.service.OrderService;
import prj.util.ValidateInput;

import java.util.Scanner;

public class StaffMenu {
    private static Scanner sc = new Scanner(System.in);
    private static BookingService bS =  new BookingService();
    private static OrderService oS =  new OrderService();
    public static ValidateInput validateInput  = new ValidateInput();
    public static void show(){
        while (true){
            System.out.println("\n ================= Menu Staff ================");
            System.out.println("1 . Hiển thị danh sách máy đang sử dụng");
            System.out.println("2 . Hiển thị danh sách đơn hàng F&B đang chờ");
            System.out.println("3 . Cập nhập trạng thái đơn hàng");
            System.out.println("0 . Thoát");

            int choice = validateInput.inputChoice(sc);
            switch (choice) {
                case 1:
                    bS.renderBooking();
                    break;
                case 2:
                    oS.getAllOrdersPending();
                    break;
                case 3:
                    oS.updateOrderStatus();
                    break;
                case 0:
                    System.out.println("Thoát thành công ");
                    return;
                default:
                    System.out.println("Không có lựa chọn này !");
            }
        }
    }
}
