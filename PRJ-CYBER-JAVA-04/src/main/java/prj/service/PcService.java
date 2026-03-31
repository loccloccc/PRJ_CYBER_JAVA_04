package prj.service;

import prj.dao.CategoryDAO;
import prj.dao.impl.CategoryDaoImpl;
import prj.dao.impl.PcDaoImpl;
import prj.model.Category;
import prj.model.Pc;

import java.util.List;
import java.util.Scanner;

public class PcService {
    private PcDaoImpl pcDAO =  new PcDaoImpl();
    private Scanner sc = new Scanner(System.in);
    private CategoryDaoImpl categoryDAO = new CategoryDaoImpl();
    // Lấy và in danh sách PC
    public void renderPc() {
        List<Pc> list = pcDAO.getAllPc();
        List<Category> list1 = categoryDAO.getAllCategories();
        System.out.println("======================== DANH SÁCH PC ===========================");
        if(list.isEmpty()) {
            System.out.println("Chưa có máy trạm nào.");
            return;
        }
        System.out.printf("%-10s | %-10s | %-12s | %-10s\n", "Tên", "Loại", "Trạng thái", "Giá/giờ");
        System.out.println("--------------------------------------------------------------------");
        for(Pc p : list) {
            for(Category c : list1) {
                if (p.getCate_id() ==  c.getId()) {
                    System.out.printf("%-10s | %-10s | %-12s | %-10.2f\n",
                            p.getName(), c.getName() , p.getStatus(), p.getPriceHoue());
                }
            }

        }
    }

    // Kiểm tra tên máy có tồn tại trong DB
    public boolean checkName(String name){
        List<Pc> list = pcDAO.getAllPc();
        return list.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }
    // timn id theo ten
    public int CheckID(String name){
        List<Category>  list = categoryDAO.getAllCategories();
        int id = 0;
        for (Category c : list) {
            if(c.getName().equalsIgnoreCase(name)){
                id = c.getId();
                return id;
            }
        }
        return 0;
    }
    // Thêm PC
    public void addPc(){
        String name;
        do {
            System.out.print("Nhập tên: ");
            name = sc.nextLine();
            if(name.isEmpty()){
                System.out.println("Không được để trống tên");
            } else if (checkName(name)){
                System.out.println("Tên này đã bị trùng");
            }
        } while(name.isEmpty() || checkName(name));

        String cate_name;
        do {
            System.out.print("Nhập loại ( normal | vip | standard ) : ");
            cate_name = sc.nextLine().trim();
            if(!cate_name.equalsIgnoreCase("normal") && !cate_name.equalsIgnoreCase("standard") && !cate_name.equalsIgnoreCase("vip")) {
                System.out.println("Hãy nhập đúng 3 loại ( normal | vip | standard ) ");
            }
        } while(!cate_name.equalsIgnoreCase("normal") && !cate_name.equalsIgnoreCase("standard") && !cate_name.equalsIgnoreCase("vip"));

        double priceHoue ;
        do {
            System.out.print("Nhập giá: ");
            priceHoue = sc.nextDouble();
            sc.nextLine();
            if(priceHoue < 0) {
                System.out.println("Giá không hợp lệ");
            }
        } while(priceHoue < 0);
        String status = "available";
        int cateId =  CheckID(cate_name);
        if(cateId == 0){
            System.out.println("Category không tồn tại");
            return;
        }
        Pc p = new Pc(0, name, cateId, status, priceHoue);
        if (pcDAO.addPc(p)){
            System.out.println("Thêm thành công");
        } else {
            System.out.println("Thêm thất bại");
        }
    }

    // Sửa PC
    public void updatePc() {
        System.out.print("Nhập tên bạn muốn sửa: ");
        String name = sc.nextLine();
        if(!checkName(name)) {
            System.out.println("Tên không tồn tại");
            return;
        }

        String cate_name;
        do {
            System.out.print("Nhập loại ( normal | vip | standard ) : ");
            cate_name = sc.nextLine().trim();
            if(!cate_name.equalsIgnoreCase("normal") && !cate_name.equalsIgnoreCase("standard") && !cate_name.equalsIgnoreCase("vip")) {
                System.out.println("Hãy nhập đúng 3 loại ( normal | vip | standard ) ");
            }
        } while(!cate_name.equalsIgnoreCase("normal") && !cate_name.equalsIgnoreCase("standard") && !cate_name.equalsIgnoreCase("vip"));

        String status;
        do {
            System.out.print("Nhập trạng thái (available | using | maintenance): ");
            status = sc.nextLine().trim();
            if(!status.equalsIgnoreCase("available") &&
                    !status.equalsIgnoreCase("using") &&
                    !status.equalsIgnoreCase("maintenance")) {
                System.out.println("Trạng thái không hợp lệ.");
            }
        } while(!status.equalsIgnoreCase("available") &&
                !status.equalsIgnoreCase("using") &&
                !status.equalsIgnoreCase("maintenance"));

        double priceHoue;
        do {
            System.out.print("Nhập giá: ");
            priceHoue = sc.nextDouble();
            sc.nextLine();
            if(priceHoue < 0) {
                System.out.println("Giá không hợp lệ.");
            }
        } while(priceHoue < 0);
        int cateId =  CheckID(cate_name);
        if(cateId == 0){
            System.out.println("Category không tồn tại");
            return;
        }
        Pc p = new Pc(name, cateId , status, priceHoue);
        if(pcDAO.updatePc(p)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    // Xóa PC
    public void deletePc() {
        System.out.print("Nhập tên bạn muốn xóa: ");
        String name = sc.nextLine();
        if(!checkName(name)) {
            System.out.println("Tên không tồn tại");
            return;
        }

        System.out.print("Xác nhận xóa (Y/N): ");
        String confirm = sc.nextLine();
        if(confirm.equalsIgnoreCase("Y")) {
            if(pcDAO.deletePcsByName(name)){
                System.out.println("Xóa thành công");
            } else {
                System.out.println("Xóa thất bại");
            }
        }
    }

    // lấy ra pc còn trống
    public void renderPcAvai() {
        List<Pc> list = pcDAO.getAllPc();
        List<Category> list1 = categoryDAO.getAllCategories();
        System.out.println("======================== DANH SÁCH PC ===========================");
        if(list.isEmpty()) {
            System.out.println("Chưa có máy trạm nào.");
            return;
        }
        System.out.printf("%-10s | %-10s | %-12s | %-10s\n", "Tên", "Loại", "Trạng thái", "Giá/giờ");
        System.out.println("--------------------------------------------------------------------");
        for(Pc p : list) {
            if (p.getStatus().equalsIgnoreCase("available")) {
                for(Category c : list1) {
                    if (p.getCate_id() ==  c.getId()) {
                        System.out.printf("%-10s | %-10s | %-12s | %-10.2f\n",
                                p.getName(), c.getName() , p.getStatus(), p.getPriceHoue());
                    }
                }
            }
        }
    }

    // suar theo id
    public void updateStatus(int id) {
        pcDAO.updateStatus(id);
    }


}