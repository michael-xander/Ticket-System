package data.access;

import data.access.faq.FaqDao;
import model.domain.faq.Faq;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the functions of the FAQ DAO
 * Created by Michael on 2015/09/15.
 */
public class FaqDaoUnitTest
{
    /**
     * A method to test the getFaq method of the Faq DAO
     */
    @Test
    public void faqDaoGetFaqTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        FaqDao faqDao = daoFactory.getFaqDao();
        Faq faq = faqDao.getFaq(-1);

        assertNull(faq);
    }

    /**
     * A method to test the getAllFaqs method of the Faq DAO
     */
    @Test
    public void faqDaoGetAllFaqs()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );
        FaqDao faqDao = daoFactory.getFaqDao();

        List<Faq> faqs = faqDao.getAllFaqs();
        assertNotNull(faqs);
    }

    /**
     * A method to test the getAllFaqsForCourse method of the Faq DAO
     */
    public void faqDaoGetAllFaqsForCourse()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );
        FaqDao faqDao = daoFactory.getFaqDao();
        List<Faq> faqs = faqDao.getAllFaqsForCourse("XXX");
        assertNotNull(faqs);
        assertTrue(faqs.isEmpty());
    }
}
