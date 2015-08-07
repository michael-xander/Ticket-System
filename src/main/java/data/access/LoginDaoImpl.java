package data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoginDaoImpl.java
 * A class that handles checking whether or not a user is
 * Created by Michael on 2015/08/07.
 */
public class LoginDaoImpl implements LoginDao
{
    private Connection connection;
    private Logger logger = Logger.getLogger(LoginDaoImpl.class.getName());

    public LoginDaoImpl(Connection connection)
    {this.connection = connection;}

    @Override
    public boolean isUser(String userID, String password) {

        PreparedStatement pst;
        try{
            pst = connection.prepareStatement("SELECT Password FROM Passwords WHERE UserID= ?");
            pst.setString(1, userID);
            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next())
            {
                if(resultSet.getString(1).equals(password))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return false;

    }
}
