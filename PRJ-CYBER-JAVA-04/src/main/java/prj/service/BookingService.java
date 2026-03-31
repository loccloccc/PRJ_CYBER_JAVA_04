package prj.service;

import prj.dao.BookingDAO;
import prj.dao.OrderDAO;
import prj.dao.PcDAO;
import prj.dao.UserDAO;
import prj.dao.impl.BookingDaoImpl;
import prj.dao.impl.OrderDaoImpl;
import prj.dao.impl.PcDaoImpl;
import prj.dao.impl.UserDaoImpl;
import prj.model.Booking;
import prj.model.Order;
import prj.model.Pc;
import prj.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BookingService {
    private BookingDAO bookingDAO = new BookingDaoImpl();
    private Scanner sc = new Scanner(System.in);
    private UserDAO userDAO = new UserDaoImpl();
    private PcDAO pcDAO = new PcDaoImpl();
    private OrderDAO orderDAO = new OrderDaoImpl();


    private static int pcId = 0;
    private static String userId = null;
    private LocalDateTime startTime;



    // Hiển thị danh sách booking
    public void renderBooking() {
        List<Booking> list = bookingDAO.getBookings();
        if (list.isEmpty()) {
            System.out.println("Chưa có booking nào.");
            return;
        }

        System.out.println("===================================== DANH SÁCH BOOKING ==================================");
        System.out.printf("%-10s | %-10s | %-10s | %-20s | %-20s | %-10s\n",
                "BookingID", "UserID", "PCID", "Start Time", "End Time", "Total");
        System.out.println("------------------------------------------------------------------------------------------");

        for (Booking b : list) {
            String start = b.getStart_time() != null ? b.getStart_time().toString() : "-";
            String end = b.getEnd_time() != null ? b.getEnd_time().toString() : "-";
            System.out.printf("%-10d | %-10s | %-10d | %-20s | %-20s | %-10.2f\n",
                    b.getBooking_id(),
                    b.getUser_id(),
                    b.getPc_id(),
                    start,
                    end,
                    b.getTotal_cost());
        }
    }

    // Lấy user id theo username
    public String idUser(String name) {
        for (User u : userDAO.getAllUser()) {
            if (u.getUsername().equalsIgnoreCase(name)) {
                return u.getUserId();
            }
        }
        return null;
    }

    // Lấy PC id theo tên (PC phải free)
    public int idPc(String name) {
        for (Pc p : pcDAO.getAllPc()) {
            if (p.getName().equalsIgnoreCase(name)
                    && !p.getStatus().equalsIgnoreCase("booking")
                    && !p.getStatus().equalsIgnoreCase("maintenance")
                    && !p.getStatus().equalsIgnoreCase("using")) {
                return p.getId();
            }
        }
        return 0;
    }

    // tìm tiền theo tên Pc
    public double pricePc(int id) {
        for (Pc p : pcDAO.getAllPc()){
            if (p.getId() == id){
                return p.getPriceHoue();
            }
        }
        return 0;
    }
    // Thêm booking mới . mo may
    public void addBooking() {
        // Nhập user
        String username;
        do {
            System.out.print("Nhập user name: ");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Không được để trống");
            } else if (idUser(username) == null) {
                System.out.println("User name này không tồn tại");
            }
        } while (username.isEmpty() || idUser(username) == null);

        // Nhập PC
        String pcName;
        do {
            System.out.print("Nhập tên PC: ");
            pcName = sc.nextLine().trim();
            if (pcName.isEmpty()) {
                System.out.println("Không được để trống");
            } else if (idPc(pcName) == 0) {
                System.out.println("PC không hợp lệ hoặc đang bận.");
            }
        } while (pcName.isEmpty() || idPc(pcName) == 0);

        // lấy thời gian hiện tại
        startTime = LocalDateTime.now();

        // Nhập thời gian bắt đầu
//        LocalDateTime startTime = null;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        while (true) {
//            System.out.print("Nhập thời gian bắt đầu (yyyy-MM-dd HH:mm:ss): ");
//            String input = sc.nextLine().trim();
//
//            try {
//                startTime = LocalDateTime.parse(input, formatter);
//                if(startTime.isBefore(timeNow)){
//                    System.out.println("Thời gian bắt đầu phải lớn hơn thời gian hiện tại!");
//                }else{
//                    break;
//                }
//
//            } catch (DateTimeParseException e) {
//                System.out.println("Sai định dạng, vui lòng nhập lại!");
//            }
//        }

        // Nhập thời gian kết thúc
//        LocalDateTime endTime = null;
//
//        while (true) {
//            System.out.print("Nhập thời gian kết thúc (yyyy-MM-dd HH:mm:ss): ");
//            String input = sc.nextLine().trim();
//
//            try {
//                endTime = LocalDateTime.parse(input, formatter);
//                if(endTime.isBefore(startTime) || endTime.isEqual(startTime)){
//                    System.out.println("Thời gian kết thúc phải lớn hơn  thời gian bắt đầu!");
//                }else{
//                    break;
//                }
//
//            } catch (DateTimeParseException e) {
//                System.out.println("Sai định dạng, vui lòng nhập lại!");
//            }
//        }

        userId = idUser(username);
        pcId = idPc(pcName);

        Booking b = new Booking(0, userId, pcId, startTime, null, 0);

        if (bookingDAO.createBooking(b)) {
            System.out.println("Đặt máy thành công.");
            pcDAO.updateStatus(pcId);

        } else {
            System.out.println("Đặt máy thất bại.");
        }
    }

    // laays total so tien order
    public double priceHoue(String userId) {
        for (Order o : orderDAO.getAllOrders()) {
            if (o.getUser_id().equals(userId)) {
                return o.getTotal_price();
            }
        }
        return 0;
    }


    // tawts may
    public void closeBooking() {
        // cập nhật lại thời gian kết thúc
        LocalDateTime endTime = LocalDateTime.now();
        // tinhs tiền
        // order
        double priceOrder = priceHoue(userId);
        // gia tien Pc
        double pricePc = pricePc(pcId);
        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        double costPc = (minutes / 60.0) * pricePc;
        double totalCost = costPc + priceOrder;

        System.out.printf("Tiền PC: %.2f\n", costPc);
        System.out.printf("Tiền order: %.2f\n", priceOrder);
        System.out.printf("Tổng tiền: %.2f\n", totalCost);


        if (pcDAO.closeStatus(pcId)) {
            pcDAO.closeStatus(pcId);
            System.out.println("Tắt máy thành công");
        }
    }
}