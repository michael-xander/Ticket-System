package data.access;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * DaoFactoryUnitTest.java
 * A class that tests the methods of the DaoFactory class
 * Created by Michael on 2015/08/07.
 */
public class DaoFactoryUnitTest
{
    private Properties getDatabaseAccessProperties()
    {
        Properties props = new Properties();

        props.setProperty("db.url", "jdbc:mysql://localhost:3306/ticket_system");
        props.setProperty("db.user", "ticSysDev");
        props.setProperty("db.password", "ticSysDev458");

        return props;
    }

    private DaoFactory getDaoFactory()
    {
        Properties props = getDatabaseAccessProperties();

        DaoFactory daoFactory = new DaoFactory(props.getProperty("db.url"),
                props.getProperty("db.user"), props.getProperty("db.password"));

        return daoFactory;
    }

    @Test
    public void constructorTest()
    {
        Properties props = getDatabaseAccessProperties();

        DaoFactory daoFactory = new DaoFactory(props.getProperty("db.url"),
                props.getProperty("db.user"), props.getProperty("db.password"));

        daoFactory.startConnection();
        daoFactory.endConnection();
    }

    @Test
    public void loginDaoTest()
    {
        DaoFactory daoFactory = getDaoFactory();

        daoFactory.startConnection();

        LoginDao loginDao = daoFactory.getLoginDao();

        assertFalse(loginDao.isUser("123", "123"));
        assertTrue(loginDao.isUser("KYYMIC001", "kyymic001"));

        daoFactory.endConnection();
    }
}
