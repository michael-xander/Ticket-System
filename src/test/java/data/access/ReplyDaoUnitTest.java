package data.access;

import data.access.message.ReplyDao;
import model.domain.message.Reply;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * A class to test the functionality of the Reply DAO
 * Created by Michael on 2015/08/08.
 */
public class ReplyDaoUnitTest {

    /**
     * A method that tests the getReply method of the Reply DAO
     */
    @Test
    public void replyDaoGetReplyTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        ReplyDao replyDao = daoFactory.getReplyDao();

        Reply reply = replyDao.getReply(-1);
        assertNull(reply);
    }
}
