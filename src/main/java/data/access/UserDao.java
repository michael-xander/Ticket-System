package data.access;

import model.domain.user.User;

import java.util.List;

/**
 * UserDao.java
 * An interface for the User class DAO
 * Created by Michael on 2015/08/07.
 */

public interface UserDao {

    public User getUser(String userID);

    public List<User> getAllUsers();

    public void updateUser(User user);

    public void addUser(User user);

    public void deleteUser(User user);

}
