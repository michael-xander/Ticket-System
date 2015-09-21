package data.access;

import data.access.user.UserDao;
import model.domain.user.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the functionality of the User DAO
 * Created by Michael on 2015/08/08.
 */
public class UserDaoUnitTest
{
    /**
     * A method to test the User DAO getUser method
     */
    @Test
    public void userDaoGetUserTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        UserDao userDao = daoFactory.getUserDao();

        User user = userDao.getUser("XXX");
        assertNull(user);

        user = userDao.getUser("KYYMIC001");
        assertNotNull(user);

        assertTrue(user.getUserID().equals("KYYMIC001"));
        assertTrue(user.getFirstName().equals("Michael"));
        assertTrue(user.getLastName().equals("Kyeyune"));
        assertTrue(user.getEmail().equals("kyymic001@myuct.ac.za"));
        assertTrue(user.getPassword().equals("kyymic001"));
    }

    /**
     * A method to test the getAllUsers method for the UserDAO
     */
    @Test
    public void userDaoGetAllUsersTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        UserDao userDao = daoFactory.getUserDao();
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        User user = new User();

        user.setUserID("KYYMIC001");

        assertTrue(users.contains(user));
    }
}
