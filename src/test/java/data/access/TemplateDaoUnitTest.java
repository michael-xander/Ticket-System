package data.access;

import data.access.answer.template.TemplateAnswerDAO;
import model.domain.answer.template.TemplateAnswer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * A class to test the functionality of the TemplateAnswer DAO
 * Created by Michael on 2015/09/20.
 */
public class TemplateDaoUnitTest {
    /**
     * A method to test the getTemplateAnswersForUser method for
     */
    @Test
    public void templateDaoGetTemplateAnswersForUserTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        TemplateAnswerDAO templateDao = daoFactory.getTemplateAnswerDAO();
        List<TemplateAnswer> templates = templateDao.getTemplateAnswersForUser("XXX");

        assertNotNull(templates);
        assertTrue(templates.isEmpty());

        templates = templateDao.getTemplateAnswersForUser("12345");
        assertFalse(templates.isEmpty());
    }

    /**
     * A method that test the getTemplate method of the TemplateAnswer DAO
     */
    @Test
    public void templateDaoGetTemplateTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        TemplateAnswerDAO templateDao = daoFactory.getTemplateAnswerDAO();

        TemplateAnswer template = templateDao.getTemplateAnswer(-1);
        assertNull(template);
    }
}
