package prj.dao;

import prj.model.Order;
import java.util.List;

public interface OrderDAO {

    boolean addOrder(Order o);

    List<Order> getAllOrders();

    boolean updateOrderStatus(String userId, String status);

}