package data.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DaoFactory.java
 * A class that generates DAOs
 * Created by Michael on 2015/08/07.
 */
public class DaoFactory
{
    private String dbUrl;
    private String password;
    private String userName;
    private Connection connection;
    private Logger logger = Logger.getLogger(DaoFactory.class.getName());

    private LoginDao loginDao;
    private CourseDao courseDao;

    public DaoFactory(String DbUrl, String userName, String password)
    {
        this.dbUrl = DbUrl;
        this.userName = userName;
        this.password = password;
    }

    public void startConnection()
    {
        try
        {
            connection = DriverManager.getConnection(dbUrl, userName, password);
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void endConnection()
    {
        if(connection != null)
        {
            try
            {
                connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public CourseDao getCourseDao() {
        if (courseDao == null)
            courseDao = new CourseDaoImpl(dbUrl, userName, password);

        return  courseDao;
    }

    public LoginDao getLoginDao()
    {
        if(loginDao == null)
            loginDao = new LoginDaoImpl(dbUrl, userName, password);

        return loginDao;
    }
}