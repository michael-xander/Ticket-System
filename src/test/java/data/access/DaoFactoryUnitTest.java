package data.access;

import data.access.answer.template.TemplateAnswerDAO;
import data.access.category.CategoryDao;
import data.access.course.CourseDao;
import data.access.faq.FaqDao;
import data.access.message.QueryDao;
import data.access.message.ReplyDao;
import data.access.user.LoginDao;
import data.access.user.UserDao;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DaoFactoryUnitTest.java
 * A class that tests the methods of the DaoFactory class
 * Created by Michael on 2015/08/07.
 */
public class DaoFactoryUnitTest
{
    /**
     * A method to test the getter methods of the DAO Factory
     */
    @Test
    public void daoFactoryGettersTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        LoginDao loginDao = daoFactory.getLoginDao();
        assertNotNull(loginDao);

        CourseDao courseDao = daoFactory.getCourseDao();
        assertNotNull(courseDao);

        UserDao userDao = daoFactory.getUserDao();
        assertNotNull(userDao);

        QueryDao queryDao = daoFactory.getQueryDao();
        assertNotNull(queryDao);

        ReplyDao replyDao = daoFactory.getReplyDao();
        assertNotNull(replyDao);

        CategoryDao categoryDao = daoFactory.getCategoryDao();
        assertNotNull(categoryDao);

        FaqDao faqDao = daoFactory.getFaqDao();
        assertNotNull(faqDao);

        TemplateAnswerDAO templateAnswerDAO = daoFactory.getTemplateAnswerDAO();
        assertNotNull(templateAnswerDAO);

    }
}
