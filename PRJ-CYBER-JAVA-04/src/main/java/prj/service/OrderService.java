package prj.service;

import prj.dao.FoodDAO;
import prj.dao.OrderDAO;
import prj.dao.UserDAO;
import prj.dao.impl.FoodDaoImpl;
import prj.dao.impl.OrderDaoImpl;
import prj.dao.impl.UserDaoImpl;
import prj.model.Food;
import prj.model.Order;
import prj.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class OrderService {

    private OrderDAO orderDAO = new OrderDaoImpl();
    private UserDAO userDAO = new UserDaoImpl();
    private FoodDAO foodDAO = new FoodDaoImpl();
    private Scanner sc = new Scanner(System.in);

    // tìm userId theo username
    public String searchIDUser(String name){

        List<User> list = userDAO.getAllUser();

        for(User u : list){
            if(u.getUsername().equalsIgnoreCase(name.trim())){
                return u.getUserId();
            }
        }

        return null;
    }

    // tìm foodId theo tên
    public Food searchFood(String name){

        List<Food> list = foodDAO.getAllFood();

        for(Food f : list){
            if(f.getName().equalsIgnoreCase(name.trim())){
                return f;
            }
        }

        return null;
    }

    // check so luon
    public boolean checkQuantity(Food food , int quantity){
        return food.getStock() >= quantity;
    }
    // thêm order
    public void addOrder(){

        String username;
        String foodName;
        int quantity;

        String userId;
        Food food;

        // nhập username
        do{

            System.out.print("Nhập username: ");
            username = sc.nextLine().trim();

            userId = searchIDUser(username);

            if(username.isEmpty()){
                System.out.println("Không được để trống.");
            }
            else if(userId == null){
                System.out.println("User không tồn tại.");
            }

        }while(username.isEmpty() || userId == null);

        // nhập food
        do{

            System.out.print("Nhập tên món ăn: ");
            foodName = sc.nextLine().trim();

            food = searchFood(foodName);

            if(foodName.isEmpty()){
                System.out.println("Không được để trống.");
            }
            else if(food == null){
                System.out.println("Food không tồn tại.");
            }

        }while(foodName.isEmpty() || food == null);

        // nhập quantity
        do{

            System.out.print("Nhập số lượng: ");

            while(!sc.hasNextInt()){
                System.out.println("Phải nhập số.");
                sc.next();
            }

            quantity = sc.nextInt();
            sc.nextLine();

            if(quantity <= 0){
                System.out.println("Số lượng phải > 0");
                continue;
            }

            if(!checkQuantity(food, quantity)){
                System.out.println("Số lượng sản phẩm không đủ");
                quantity = -1;
            }

        }while(quantity <= 0);
        // nhap thoi gian oeder
        LocalDateTime orderTime = LocalDateTime.now();

        double totalPrice = food.getPrice() * quantity;

        System.out.println("Tổng tiền: " + totalPrice + " VND");

        Order o = new Order(
                0,
                userId,
                food.getId(),
                quantity,
                totalPrice,
                orderTime,
                "pending"
        );

        boolean result = orderDAO.addOrder(o);
        boolean result2 = foodDAO.updateQuantity(foodName, quantity);
        if(result && result2){
            System.out.println("Thêm order thành công.");
        }
        else{
            System.out.println("Thêm order thất bại.");
        }
    }

    // hiển thị orders
    public void getAllOrders(){

        List<Order> list = orderDAO.getAllOrders();

        if(list.isEmpty()){
            System.out.println("Không có order.");
            return;
        }

        System.out.printf("%-8s %-10s %-8s %-8s %-12s %-20s %-12s\n",
                "ID","User","Food","Qty","Total","Order Time","Status");

        for(Order o : list){

            System.out.printf("%-8d %-10s %-8d %-8d %-12.2f %-20s %-12s\n",
                    o.getOrder_id(),
                    o.getUser_id(),
                    o.getFood_id(),
                    o.getQuantity(),
                    o.getTotal_price(),
                    o.getOrder_time(),
                    o.getStatus());
        }
    }

    // update status
    public void updateOrderStatus(){

        String username;
        String userId;
        String status;

        do{

            System.out.print("Nhập username: ");
            username = sc.nextLine().trim();

            userId = searchIDUser(username);

            if(username.isEmpty()){
                System.out.println("Không được để trống.");
            }
            else if(userId == null){
                System.out.println("User không tồn tại.");
            }

        }while(username.isEmpty() || userId == null);

        do{

            System.out.print("Nhập status(pending/preparing/completed/cancelled): ");
            status = sc.nextLine().trim().toLowerCase();

        }while(!status.equals("pending") &&
                !status.equals("preparing") &&
                !status.equals("completed") &&
                !status.equals("cancelled"));

        boolean result = orderDAO.updateOrderStatus(userId,status);

        if(result){
            System.out.println("Cập nhật thành công.");
        }else{
            System.out.println("Cập nhật thất bại.");
        }
    }

    //
    public void viewOrdersByUsername(){

        String username;
        String userId;

        // nhập username
        do{
            System.out.print("Nhập username: ");
            username = sc.nextLine().trim();

            if(username.isEmpty()){
                System.out.println("Username không được để trống.");
            }

        }while(username.isEmpty());

        // tìm userId
        userId = searchIDUser(username);

        if(userId == null){
            System.out.println("User không tồn tại.");
            return;
        }

        List<Order> list = orderDAO.getAllOrders();

        boolean found = false;

        System.out.printf("%-8s %-10s %-8s %-8s %-12s %-20s %-12s\n",
                "ID","User","Food","Qty","Total","Order Time","Status");

        for(Order o : list){

            if(o.getUser_id().equals(userId) && (o.getStatus().equals("pending") || o.getStatus().equals("preparing"))){
                System.out.printf("%-8d %-10s %-8d %-8d %-12.2f %-20s %-12s\n",
                        o.getOrder_id(),
                        o.getUser_id(),
                        o.getFood_id(),
                        o.getQuantity(),
                        o.getTotal_price(),
                        o.getOrder_time(),
                        o.getStatus());

                found = true;
            }
        }

        if(!found){
            System.out.println("User này chưa có đơn hàng nào.");
        }
    }

    // hien thi trang thai don dang cho
    public void getAllOrdersPending(){

        List<Order> list = orderDAO.getAllOrders();
        List<User> list1 = userDAO.getAllUser();
        if(list.isEmpty()){
            System.out.println("Không có order.");
            return;
        }

        System.out.printf("%-8s %-10s %-8s %-8s %-12s %-20s %-12s\n",
                "ID","User","Food","Qty","Total","Order Time","Status");

        for(Order o : list){
            for(User u : list1){
                if (o.getUser_id().equalsIgnoreCase(u.getUserId())){
                    if (o.getStatus().equals("pending") || o.getStatus().equalsIgnoreCase("preparing")) {
                        System.out.printf("%-8d %-10s %-8d %-8d %-12.2f %-20s %-12s\n",
                                o.getOrder_id(),
                                u.getUsername(),
                                o.getFood_id(),
                                o.getQuantity(),
                                o.getTotal_price(),
                                o.getOrder_time(),
                                o.getStatus());
                    }
                }
            }


        }
    }
}