package prj.service;

import prj.dao.impl.FoodDaoImpl;
import prj.model.Food;

import java.util.List;
import java.util.Scanner;

public class FoodService {
    private FoodDaoImpl foodDAO = new FoodDaoImpl();
    static Scanner sc = new Scanner(System.in);
    // lay
    public void renderFood(){
        List<Food> list = foodDAO.getAllFood();
        System.out.println("======================== DANH SÁCH ẨM THỰC =====================");
        if (list.isEmpty()){
            System.out.println("Chưa có món nào ...");
        }
        System.out.printf("%-10s | %-10s | %-5s | %-15s\n", "Tên", "Giá", "Số lượng", "Nhóm");
        System.out.println("------------------------- FOOD -------------------------------------");
        for (Food f : list){
            if (f.getType().equalsIgnoreCase("food")){
                System.out.printf("%-10s | %-10s | %-5s | %-15s\n", f.getName() , f.getPrice() , f.getStock() , f.getType());
            }
        }
        System.out.println("------------------------- DRINK -------------------------------------");
        for (Food f : list){
            if (f.getType().equalsIgnoreCase("drink")){
                System.out.printf("%-10s | %-10s | %-5s | %-15s\n", f.getName() , f.getPrice() , f.getStock() , f.getType());
            }
        }

    }
    public boolean checkName(String name){
        List<Food> list = foodDAO.getAllFood();
        return list.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }
    // them
    public void addFood() {
        // Tên
        String name;
        do {
            System.out.print("Nhập tên: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Không được để trống tên");
            } else if (checkName(name)) {
                System.out.println("Tên này đã bị trùng");
            }
        } while (name.isEmpty() || checkName(name));

        // Giá
        double price = -1;
        while (true) {
            System.out.print("Nhập giá: ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if (price < 0) {
                    System.out.println("Giá không hợp lệ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ cho giá.");
            }
        }

        // Số lượng
        int stock = -1;
        while (true) {
            System.out.print("Nhập số lượng: ");
            try {
                stock = Integer.parseInt(sc.nextLine().trim());
                if (stock < 0) {
                    System.out.println("Số lượng nhập không hợp lệ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ cho số lượng.");
            }
        }

        // Loại
        String type;
        do {
            System.out.print("Nhập loại (food | drink): ");
            type = sc.nextLine().trim().toLowerCase();
            if (!type.equals("food") && !type.equals("drink")) {
                System.out.println("Loại không hợp lệ, chỉ được nhập 'food' hoặc 'drink'");
            }
        } while (!type.equals("food") && !type.equals("drink"));

        // Tạo object và thêm
        Food food = new Food(name, price, stock, type);
        if (foodDAO.addFood(food)) {
            System.out.println("Thêm thành công");
        } else {
            System.out.println("Thêm thất bại");
        }
    }
    // sủa
    public void updateFood() {
        System.out.print("Nhập tên bạn muốn sửa: ");
        String name = sc.nextLine().trim();
        if(!checkName(name)) {
            System.out.println("Tên không tồn tại");
            return;
        }

        // Giá
        double price = -1;
        while(true) {
            System.out.print("Nhập giá: ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if(price < 0) {
                    System.out.println("Giá không hợp lệ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ cho giá.");
            }
        }

        // Số lượng
        int stock = -1;
        while(true) {
            System.out.print("Nhập số lượng: ");
            try {
                stock = Integer.parseInt(sc.nextLine().trim());
                if(stock < 0) {
                    System.out.println("Số lượng nhập không hợp lệ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ cho số lượng.");
            }
        }

        // Loại
        String type;
        do {
            System.out.print("Nhập loại (food | drink): ");
            type = sc.nextLine().trim().toLowerCase();
            if(!type.equals("food") && !type.equals("drink")) {
                System.out.println("Loại không hợp lệ, chỉ được nhập 'food' hoặc 'drink'");
            }
        } while(!type.equals("food") && !type.equals("drink"));

        // Tạo object Food và update
        Food food = new Food(name, price, stock, type);
        if (foodDAO.updateFood(food)) {
            System.out.println("Cập nhật thành công");
        } else {
            System.out.println("Cập nhật thất bại");
        }
    }

    // xóa
    public void deleteFood(){
        System.out.print("Nhập tên bạn muốn xóa: ");
        String name = sc.nextLine();
        if(!checkName(name)) {
            System.out.println("Tên không tồn tại");
            return;
        }

        System.out.print("Xác nhận xóa (Y/N): ");
        String confirm = sc.nextLine();
        if(confirm.equalsIgnoreCase("Y")) {
            if(foodDAO.deletePcsFoodName(name)) {
                System.out.println("Xóa thành công");
            } else {
                System.out.println("Xóa thất bại");
            }
        }
    }

    // sửa số lượng
    public boolean updateQuantity(String foodName , int quantity){
        Food f = new Food(foodName, quantity);
        boolean result = foodDAO.updateQuantity(foodName, quantity);
        if(result){
            System.out.println("Trừ hàng thành công.");
        }else{
            System.out.println("Không đủ hàng hoặc food không tồn tại.");
        }

        return result;
    }

}
