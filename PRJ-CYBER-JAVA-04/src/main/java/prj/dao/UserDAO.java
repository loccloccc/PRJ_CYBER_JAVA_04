package prj.dao;

import prj.model.User;

import java.util.List;

public interface UserDAO {
    User login(String username, String password);
    boolean register(User user);

    List<User> getAllUser();
    boolean updateUser(User user);
    boolean deleteUser(User user);
    boolean addUser(User user);

}
