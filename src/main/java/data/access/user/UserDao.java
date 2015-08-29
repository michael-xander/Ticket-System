package data.access.user;

import model.domain.user.User;

import java.util.List;

/**
 * UserDao.java
 * An interface for the User class DAO
 * Created by Michael on 2015/08/07.
 */

public interface UserDao {

    User getUser(String userID);

    List<User> getAllUsers();

    void updateUser(User user);

    void addUser(User user);

    void deleteUser(String userID);

}
