package data.access.user;

import data.access.Dao;

import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoginDaoImpl.java
 * A class that handles checking whether or not a user is
 * Created by Michael on 2015/08/07.
 */

public class LoginDaoImpl extends Dao implements LoginDao, Serializable
{
    private transient Logger logger = Logger.getLogger(LoginDaoImpl.class.getName());

    public LoginDaoImpl(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * A method that returns a true if the credentials provided by the user are correct
     * @param userID - ID of the user
     * @param password - password of the user
     * @return true if is user or false if not
     */
    @Override
    public boolean isUser(String userID, String password) {
        Connection connection = null;
        PreparedStatement pst = null;
        boolean isUser = false;
        try{
            connection = super.getConnection();
            pst = connection.prepareStatement("SELECT Password FROM Passwords WHERE UserID= ?");
            pst.setString(1, userID);
            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next())
            {
                if(resultSet.getString(1).equals(password))
                    isUser = true;
            }
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(pst != null)
                    pst.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return isUser;
    }
}
