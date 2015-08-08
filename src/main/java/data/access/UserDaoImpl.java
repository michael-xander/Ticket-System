package data.access;

import model.domain.user.User;

import java.util.List;

/**
 * UserDaoImpl.java
 * A DAO for the User class
 * Created by Michael on 2015/08/08.
 */
public class UserDaoImpl implements UserDao
{
    private String dbUser;
    private String dbUrl;
    private String dbPassword;

    public UserDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public User getUser(String userID) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }
}
