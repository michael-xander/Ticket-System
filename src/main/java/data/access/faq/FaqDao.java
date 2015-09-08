package data.access.faq;

import model.domain.faq.Faq;
import java.util.List;
import java.util.Set;

/**
 * FaqDao.java
 * An interface for the Faq DAO
 * @author Marcelo Dauane
 */
public interface FaqDao {

    Faq getFaq(int faqID);

    List<Faq> getAllFaqs();

    List<Faq> getAllFaqsForCourse(String courseID);

    void addFaq(Faq faq);

    void deleteFaq(int faqID);

    void updateFaq(Faq faq);
}
