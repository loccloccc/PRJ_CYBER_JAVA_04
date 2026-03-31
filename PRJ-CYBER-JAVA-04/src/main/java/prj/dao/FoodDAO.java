package prj.dao;

import prj.model.Food;

import java.util.List;

public interface FoodDAO {
    boolean addFood(Food f);
    List<Food> getAllFood();
    boolean deletePcsFoodName(String foodName);
    boolean updateFood(Food f);

    boolean updateQuantity(String foodName, int quantity);

}
