package data.access;

import model.domain.user.Course;
import model.domain.user.Role;
import model.domain.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    public UserDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * A method that returns a User object from the database with given user ID
     * @param userID - ID of the user
     * @return User object if exists in database or else null
     */
    @Override
    public User getUser(String userID) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE UserID = ?");
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                user = readUser(connection, resultSet);
            }
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return user;
    }

    /*
     * A method that reads in a user from the database
     */
    private User readUser(Connection connection, ResultSet resultSet)
            throws SQLException
    {
        User user = new User();
        user.setUserID(resultSet.getString(1));
        user.setFirstName(resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Passwords WHERE UserID = ?");
        preparedStatement.setString(1, user.getUserID());
        ResultSet tempResultSet = preparedStatement.executeQuery();

        if(tempResultSet.next())
            user.setPassword(tempResultSet.getString(2));

        preparedStatement.clearParameters();
        preparedStatement.close();

        preparedStatement = connection.prepareStatement("SELECT * FROM User_Courses WHERE UserID = ? ");
        preparedStatement.setString(1, user.getUserID());
        tempResultSet = preparedStatement.executeQuery();

        CourseDao courseDao = new CourseDaoImpl(dbUrl, dbUser, dbPassword);
        while(tempResultSet.next())
        {
            String courseID = tempResultSet.getString(2).trim();
            String roleString = tempResultSet.getString(3).trim();
            Course course = courseDao.getCourse(courseID);
            user.addCourse(course, Role.valueOf(roleString));
        }
        preparedStatement.close();

        return user;
    }

    /**
     * A method that returns of all Users in the database.
     * @return - List of all Users
     */
    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser , dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                User user = readUser(connection, resultSet);
                users.add(user);
            }
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return users;
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
