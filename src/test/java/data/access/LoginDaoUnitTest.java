package data.access;

import data.access.user.LoginDao;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the Login DAO
 * Created by Michael on 2015/08/08.
 */
public class LoginDaoUnitTest {

    /**
     * A method to test the Login DAO isUser method
     */
    @Test
    public void loginDaoIsUserTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        LoginDao loginDao = daoFactory.getLoginDao();

        assertFalse(loginDao.isUser("XXX", "XXX"));
        assertTrue(loginDao.isUser("KYYMIC001", "kyymic001"));

    }
}
