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
    private String DbUrl;
    private String password;
    private String userName;
    private Connection connection;
    private Logger logger = Logger.getLogger(DaoFactory.class.getName());

    private LoginDao loginDao;

    public DaoFactory(String DbUrl, String userName, String password)
    {
        this.DbUrl = DbUrl;
        this.userName = userName;
        this.password = password;
    }

    public void startConnection()
    {
        try
        {
            connection = DriverManager.getConnection(DbUrl, userName, password);
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

    public LoginDao getLoginDao()
    {
        if(loginDao == null)
            loginDao = new LoginDaoImpl(connection);

        return loginDao;
    }
}
