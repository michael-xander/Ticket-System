package data.access;

import data.access.category.CategoryDao;
import data.access.category.CategoryDaoImpl;
import data.access.course.CourseDao;
import data.access.course.CourseDaoImpl;
import data.access.faq.FaqDao;
import data.access.faq.FaqDaoImpl;
import data.access.message.QueryDao;
import data.access.message.QueryDaoImpl;
import data.access.message.ReplyDao;
import data.access.message.ReplyDaoImpl;
import data.access.user.LoginDao;
import data.access.user.LoginDaoImpl;
import data.access.user.UserDao;
import data.access.user.UserDaoImpl;

import java.io.Serializable;
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

public class DaoFactory implements Serializable
{
    private String dbUrl;
    private String password;
    private String userName;
    private Connection connection;
    private transient Logger logger = Logger.getLogger(DaoFactory.class.getName());

    private LoginDao loginDao;
    private CourseDao courseDao;
    private UserDao userDao;
    private QueryDao queryDao;
    private ReplyDao replyDao;
    private CategoryDao categoryDao;
    private FaqDao faqDao;

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

    /**
     * A method to get a Category DAO instance.
     * @return a Category DAO instance
     */
    public CategoryDao getCategoryDao()
    {
        if(categoryDao == null)
            categoryDao = new CategoryDaoImpl(dbUrl, userName, password);

        return categoryDao;
    }

    /**
     * A method to get a Query DAO instance.
     * @return a Query DAO instance
     */
    public QueryDao getQueryDao()
    {
        if(queryDao == null)
            queryDao = new QueryDaoImpl(dbUrl, userName, password);

        return queryDao;
    }

    /**
     * A method to get a Reply DAO instance.
     * @return a Reply DAO instance
     */
    public ReplyDao getReplyDao()
    {
        if(replyDao == null)
            replyDao = new ReplyDaoImpl(dbUrl, userName, password);

        return replyDao;
    }

    /**
     * A method to get a Course DAO instance.
     * @return a Course DAO instance
     */
    public CourseDao getCourseDao() {
        if (courseDao == null)
            courseDao = new CourseDaoImpl(dbUrl, userName, password);

        return  courseDao;
    }

    /**
     * A method to get a Login DAO instance
     * @return a Login DAO instance
     */
    public LoginDao getLoginDao()
    {
        if(loginDao == null)
            loginDao = new LoginDaoImpl(dbUrl, userName, password);

        return loginDao;
    }

    /**
     * A method to get a User DAO instance
     * @return a User DAO instance
     */
    public UserDao getUserDao()
    {
        if(userDao == null)
            userDao = new UserDaoImpl(dbUrl, userName, password);

        return userDao;
    }

    /**
     * A method to get a Faq DAO instance
     * @return a Faq DAO instance
     */
    public FaqDao getFaqDao()
    {
        if(faqDao == null)
            faqDao = new FaqDaoImpl(dbUrl, userName, password);

        return faqDao;
    }
}
